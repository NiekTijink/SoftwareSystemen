package online;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import protocol.Protocol;
import qwirkle.*;

public class Server {
	private static final String USAGE = "usage: " + Server.class.getName() + " <name><port>";
	protected static String name;

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println(USAGE);
			System.exit(0);
		}
		name = args[0];
		int port = 0;

		try {
			port = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.out.println(USAGE);
			System.out.println("ERROR: port " + args[2] + " is not an integer");
			System.exit(0);
		}

		Server server = new Server(port);
		server.run();
	}

	private List<ClientHandler> threads;
	private List<String> clientNames;
	private ArrayList<ArrayList<String>> waitingRooms = new ArrayList<ArrayList<String>>();
	private ArrayList<String> lobby = new ArrayList<String>();
	private ArrayList<Game> currentGames;
	private ServerSocket serversock;

	public Server(int port) {
		threads = new ArrayList<ClientHandler>();
		clientNames = new ArrayList<String>();
		currentGames = new ArrayList<Game>();
		try {
			serversock = new ServerSocket(port);
			System.out.println("Server listens at port " + port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i <= 2; i++) { // 3 wachtruimtes aanmaken (2 pers, 3
										// pers, 4pers)
			waitingRooms.add(new ArrayList<String>());
		}
	}

	public void run() {
		while (!serversock.isClosed()) {
			try {
				ClientHandler c = new ClientHandler(this, serversock.accept());
				addHandler(c);
				c.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void broadcast(String msg) {
		for (int i = 0; i < threads.size(); i++) {
			threads.get(i).sendMessage(msg);
		}
	}

	public void broadcast(String msg, Game game) {
		ArrayList<ClientHandler> handlers = getHandler(game);
		for (int i = 0; i < handlers.size(); i++) {
			handlers.get(i).sendMessage(msg);
		}
		// TODO insert body
	}

	public void addHandler(ClientHandler c) {
		threads.add(c);
	}

	public void removeHandler(ClientHandler c) {
		threads.remove(c);
	}

	public String addClientName(String msg, ClientHandler c) {
		for (int i = 0; i < clientNames.size(); i++) {
			if (clientNames.get(i).equals(msg)) {
				return Protocol.Server.ERROR + "_nameexists";
			}
		}
		clientNames.add(msg);
		lobby.add(msg);
		return Protocol.Server.HALLO + "_ServerP03";
	}

	public String requestGame(String clientName, int choice) {
		String[] names;
		if (choice == 1) { // NOG NIET GOED
			names = new String[1];
			names[0] = clientName;
			Game game= new Game(names,this);
			currentGames.add(game);
			Thread g = game;
			g.start();
			removeNames(names);
			broadcast(Protocol.Server.STARTGAME + "_" + clientName, game);
		} else if (choice == 2 || choice == 3 || choice == 4) {
			if (waitingRooms.get(choice - 2).size() == choice - 1) {
				names = new String[choice];
				names[0] = clientName;
				for (int i = 0; i <= choice - 2; i++) {
					names[choice - 1 - i] = waitingRooms.get(choice - 2).get(i);
				}
				Game game = new Game(names,this); // GAME MOET GESTART WORDEN (nieuwe
												// thread?)
				currentGames.add(game);
				Thread g = game;
				g.start();
				String gameWith = "";
				for (String name : names) {
					gameWith += name + "_";
				}
				gameWith = gameWith.substring(0, gameWith.length() - 1);
				removeNames(names);
				broadcast(Protocol.Server.STARTGAME + "_" + gameWith, game);
				for (ClientHandler c : getHandler(game)) {
					for (Player player : game.getPlayers()) {
						c.setCurrentGame(game);
						c.setCurrentPlayer(player);
						if (player.getName().equals(c.getClientName())) {
							c.sendMessage(initiateHand(player));
						}
					}
				}
			} else {
				waitingRooms.get(choice - 2).add(clientName);
				String waitingFor = "";
				for (String name : lobby) {
					waitingFor += name + "_";
				}
				if (waitingFor.equals("")) {
					return Protocol.Server.OKWAITFOR + "_";
				}
				waitingFor = waitingFor.substring(0, waitingFor.length() - 1);
				return Protocol.Server.OKWAITFOR + "_" + waitingFor;
			}
		} else if (choice == 0) {
			for (int i = 2; i <= 4; i++) {
				if (waitingRooms.get(i - 2).size() == i - 1) {
					names = new String[i];
					names[0] = clientName;
					for (int j = 0; j <= i - 2; j++) {
						names[i - 1 - j] = waitingRooms.get(i - 2).get(j);
					}
					String gameWith = "";
					Game game = new Game(names,this);
					currentGames.add(game);
					Thread t = game;
					t.start();
					for (String name : names) {
						gameWith += name + "_";
					}
					gameWith = gameWith.substring(0, gameWith.length() - 1);
					removeNames(names);
					broadcast(Protocol.Server.STARTGAME + "_" + gameWith, game);
					for (ClientHandler c : getHandler(game)) {
						for (Player player : game.getPlayers()) {
							c.setCurrentGame(game);
							c.setCurrentPlayer(player);
							if (player.getName().equals(c.getName())) {
								c.sendMessage(initiateHand(player));
							}
						}
					}
				}
				waitingRooms.get(i - 2).add(clientName);
			}
		}
		return ClientHandler.NOREPLY;
	}

	public void makeMove(Game game, Player player, Player nextPlayer, String completeMsg) { //checken of het wel zijn beurt is
		broadcast(Protocol.Server.MOVE + "_" + player.getName() + "_" + 
				nextPlayer.getName() + completeMsg.substring(9),game);
	}
	
	public void updateHand(Player player, ArrayList<Tile> tiles) {
		String msg = Protocol.Server.ADDTOHAND;
		for (Tile t : tiles) {
			msg += "_" + t.getColor().getColor() + t.getShape().getShape();
		}
		for (ClientHandler c : threads) {
			if (c.getClientName().equals(player.getName())) {
				c.sendMessage(msg);
				break;
			}
		}
	}
		
	
	public ArrayList<ClientHandler> getHandler(Game game) {
		ArrayList<ClientHandler> handlers = new ArrayList<ClientHandler>();
		for (Player player : game.getPlayers()) {
			boolean go = true;
			int i = 0;
			while (go) {
				if (threads.get(i).getClientName().equals(player.getName())) {
					handlers.add(threads.get(i));
					go = false;
				}
				i++;
			}
		}
		return handlers;
	}
	
	public String initiateHand(Player player) {
		String hand = Protocol.Server.ADDTOHAND;
		for (Tile tile : player.getHand()) {
			hand += "_" + Character.toString(tile.getColor().getColor()) + Character.toString(tile.getShape().getShape());
		}
		return hand;
	}
	
	public void removeNames(String[] names) { // de namen die beginnen uit alle
												// wachtruimtes en evt lobby
												// verwijderen
		for (String name : names) {
			for (int i = 2; i <= 4; i++) {
				for (int j = 0; j < waitingRooms.get(i - 2).size(); j++) {
					if (waitingRooms.get(i - 2).get(j).equals(name)) {
						waitingRooms.get(i - 2).remove(j);
					}
				}
			}
			for (int k = 0; k < lobby.size(); k++) {
				if (lobby.get(k).equals(name)) {
					lobby.remove(k);
				}
			}
		}
	}

}
