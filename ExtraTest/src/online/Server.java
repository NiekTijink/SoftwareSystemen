package online;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
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

	public String addClientName(String msg) {
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
			currentGames.add(new Game(names));
			removeNames(names);
		} else if (choice == 2 || choice == 3 || choice == 4) {
			if (waitingRooms.get(choice - 2).size() == choice - 1) {
				names = new String[choice];
				names[0] = clientName;
				for (int i = 0; i <= choice - 2; i++) {
					names[choice - 1 - i] = waitingRooms.get(choice - 2).get(i);
				}
				Game game = new Game(names); // GAME MOET GESTART WORDEN (nieuwe
												// thread?)
				currentGames.add(game);
				game.start();
				String gameWith = "";
				for (String name : names) {
					gameWith += name + "_";
				}
				gameWith = gameWith.substring(0, gameWith.length() - 1);
				removeNames(names);
				broadcast(Protocol.Server.STARTGAME + "_" + gameWith, game);
				for (ClientHandler c : getHandler(game)) {
					for (Player player : game.getPlayers()) {
						if (player.getName().equals(c.getName())) {
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
					Game game = new Game(names);
					currentGames.add(game);
					for (String name : names) {
						gameWith += name + "_";
					}
					gameWith = gameWith.substring(0, gameWith.length() - 1);
					removeNames(names);
					broadcast(Protocol.Server.STARTGAME + "_" + gameWith, game);
					for (ClientHandler c : getHandler(game)) {
						for (Player player : game.getPlayers()) {
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

	public String makeMove(String clientName, String completeMsg) {
		String[] msg = completeMsg.split(Character.toString(Protocol.Settings.DELIMITER));
		Game thisGame = getCurrentGame(clientName);
		if (thisGame == null) {
			return Protocol.Server.ERROR + "_unknowncommand";
		}
		Player thisPlayer = getCurrentPlayer(clientName, thisGame);
		int[][] moves = new int[Player.HANDSIZE][3];
		for (int i = 1; i < msg.length; i++) {
			String[] move = msg[i].split(Character.toString(Protocol.Settings.DELIMITER2));
			moves[i][0] = Integer.parseInt(move[1]);
			moves[i][1] = Integer.parseInt(move[2]);
			moves[i][2] = getPlaceInHand(thisPlayer.getHand(), new Tile(move[0].charAt(0), move[0].charAt(1)));
		}
		int score = thisGame.getBoard().deepcopy().testMove(moves, thisPlayer.getHand());
		if (score < 0) {
			return Protocol.Server.ERROR + "_unvalidmove";
		} else {
			int i = 0;
			while (moves[i][0] != -1) {
				thisGame.getBoard().setField(moves[i][0], moves[i][1], thisPlayer.getHand()[moves[i][2]]);
			}
			thisPlayer.addScore(score);
			broadcast(Protocol.Server.MOVE + "_" + clientName + "_" + "nextPlayer" + completeMsg.substring(9),
					thisGame);
		}
		return "";
	}

	public int getPlaceInHand(Tile[] hand, Tile tile) {
		for (int i = 0; i < hand.length; i++) {
			if (tile.getColor() == hand[i].getColor() && tile.getShape() == hand[i].getShape()) {
				return i;
			}
		}
		return -1;
	}

	public Player getCurrentPlayer(String clientName, Game game) {
		for (Player player : game.getPlayers()) {
			if (player.getName().equals(clientName)) {
				return player;
			}
		}
		return null;
	}

	public Game getCurrentGame(String clientName) {
		for (Game game : currentGames) {
			for (Player player : game.getPlayers()) {
				if (player.getName().equals(clientName)) {
					return game;
				}
			}
		}
		return null;
	}

	public ArrayList<ClientHandler> getHandler(Game game) {
		ArrayList<ClientHandler> handlers = new ArrayList<ClientHandler>();
		for (Player player : game.getPlayers()) {
			boolean go = true;
			int i = 0;
			while (go) {
				if (threads.get(i).getName().equals(player.getName())) {
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
