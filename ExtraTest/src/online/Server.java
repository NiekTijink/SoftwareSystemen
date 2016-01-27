package online;
 
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import protocol.Protocol;
import qwirkle.*;
/** a server is used to play games on
 * @author Niek Tijink & thomas Kolner
 *
 */
public class Server {
	private static final String USAGE = "usage: " + Server.class.getName() + " <name><port>";
	protected static String name;
 
	/** tries to open a serversocket
	 * @param args 0: name, 1: port
	 */
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

	/** makes a new server 
	 * @param port port of the server
	 */
	public Server(int port) {
		threads = new ArrayList<ClientHandler>();
		clientNames = new ArrayList<String>();
		currentGames = new ArrayList<Game>();
		try {
			serversock = new ServerSocket(port);
			System.out.println("Server listens at port " + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i <= 2; i++) { // 3 wachtruimtes aanmaken (2 pers, 3
										// pers, 4pers)
			waitingRooms.add(new ArrayList<String>());
		}
	}

	/** While the server is running, it is waiting for clients to connect
	 */
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

	/** used to broadcast a message to all clienthandlers connected to the serer
	 * @param msg the message
	 */
	public void broadcast(String msg) {
		for (int i = 0; i < threads.size(); i++) {
			threads.get(i).sendMessage(msg);
		}
	}

	/** used to broadcast a message to all players in a game
	 * @param msg the message
	 * @param game the game
	 */
	public void broadcast(String msg, Game game) {
		ArrayList<ClientHandler> handlers = getHandler(game);
		for (int i = 0; i < handlers.size(); i++) {
			handlers.get(i).sendMessage(msg);
		}
	}

	/** adds a handler to the list of handlers
	 * @param c the clientHandler to be added
	 */
	public void addHandler(ClientHandler c) {
		threads.add(c);
	}
	
	/** removes a handler of the list of handlers
	 * @param c the clientHandler to be removed
	 */
	public void removeHandler(ClientHandler c) {
		threads.remove(c);
	}

	/** adds a name to the list of clientNames when a new clienthandler connects
	 * A clientName must be unique
	 * @param c the new clientHandler
	 */
	public String addClientName(String msg, ClientHandler c) {
		if (msg.equals(ClientHandler.NOREPLY)) {
			return Protocol.Server.ERROR + "_nametooshort";
		}
		for (int i = 0; i < clientNames.size(); i++) {
			if (clientNames.get(i).equals(msg)) {
				return Protocol.Server.ERROR + "_nameexists";
			}
		}
		clientNames.add(msg);
		lobby.add(msg);
		return Protocol.Server.HALLO + "_ServerP03";
	}

	/** used when a clienthandler requests a game
	 * When a waitingroom is full, a new game is started
	 * otherwise the clienthandler is placed in the waitingroom
	 * @param clientName the name of the client
	 * @param choice the number of players the client wants to play with
	 * @return
	 */
	public String requestGame(String clientName, int choice) {
		String[] names;
		if (choice == 1) { 
			names = new String[1];
			names[0] = clientName;
			Game game = new Game(names, this);
			currentGames.add(game);
			Thread g = game;
			g.start();
			removeNames(names);
			broadcast(Protocol.Server.STARTGAME + "_" + clientName, game);
			for (ClientHandler c : getHandler(game)) {
				for (Player player : game.getPlayers()) {
					c.setCurrentGame(game);
					if (player.getName().equals(c.getClientName())) {
						c.setCurrentPlayer(player);
						c.sendMessage(initiateHand(player));
					}
				}
			}
		} else if (choice == 2 || choice == 3 || choice == 4) {
			if (waitingRooms.get(choice - 2).contains(clientName)) {
				return Protocol.Server.ERROR + "_generalerror: Already in waitingroom for: "
						+ (choice - 2);
			}
			if (waitingRooms.get(choice - 2).size() == choice - 1) {
				names = new String[choice];
				names[0] = clientName;
				for (int i = 0; i <= choice - 2; i++) {
					names[choice - 1 - i] = waitingRooms.get(choice - 2).get(i);
				}
				Game game = new Game(names, this); 
				currentGames.add(game);
				Thread g = game;
				g.start();
				String gameWith = "";
				for (String temp : names) {
					gameWith += temp + "_";
				}
				gameWith = gameWith.substring(0, gameWith.length() - 1);
				removeNames(names);
				broadcast(Protocol.Server.STARTGAME + "_" + gameWith, game);
				for (ClientHandler c : getHandler(game)) {
					for (Player player : game.getPlayers()) {
						c.setCurrentGame(game);
						if (player.getName().equals(c.getClientName())) {
							c.setCurrentPlayer(player);
							c.sendMessage(initiateHand(player));
						}
					}
				}
			} else {
				waitingRooms.get(choice - 2).add(clientName);
				int waitFor = choice - waitingRooms.get(choice - 2).size();
				return Protocol.Server.OKWAITFOR + "_" + waitFor;
			}
		} else if (choice == 0) {
			for (int i = 2; i <= 4; i++) {
				if (waitingRooms.get(i - 2).contains(clientName)) {
					return Protocol.Server.ERROR + "_generalerror: Already in waitingroom for: " 
							+ (i - 2) + " persons";
				}
				if (waitingRooms.get(i - 2).size() == i - 1) {
					names = new String[i];
					names[0] = clientName;
					for (int j = 0; j <= i - 2; j++) {
						names[i - 1 - j] = waitingRooms.get(i - 2).get(j);
					}
					String gameWith = "";
					Game game = new Game(names, this);
					currentGames.add(game);
					Thread t = game;
					t.start();
					for (String temp : names) {
						gameWith += temp + "_";
					}
					gameWith = gameWith.substring(0, gameWith.length() - 1);
					removeNames(names);
					broadcast(Protocol.Server.STARTGAME + "_" + gameWith, game);
					for (ClientHandler c : getHandler(game)) {
						for (Player player : game.getPlayers()) {
							c.setCurrentGame(game);
							if (player.getName().equals(c.getName())) {
								c.setCurrentPlayer(player);
								c.sendMessage(initiateHand(player));
							}
						}
					}
					return ClientHandler.NOREPLY;
				}
				waitingRooms.get(i - 2).add(clientName);
			}
		}
		return ClientHandler.NOREPLY;
	}

	/** sends (via broadcast) a message to all players
	 * @param game the game where the message needs to be send to
	 * @param player the player that made the move
	 * @param nextPlayer the player that now has to make a move
	 * @param completeMsg the move 
	 */
	public void makeMove(Game game, Player player, Player nextPlayer, String completeMsg) { 
		if (completeMsg.length() > 0) {
			broadcast(Protocol.Server.MOVE + "_" + player.getName() + "_" + 
				   nextPlayer.getName() + "_" + completeMsg.substring(9), game);
		} else {
			broadcast(Protocol.Server.MOVE + "_" + player.getName() + "_" + 
				   nextPlayer.getName() + "_" + "", game);
		}
	}
	
	/** updates a hand after the player made a move
	 * @param player the player that made a move
	 * @param msg the message send to the player 
	 */
	public void updateHand(Player player, String msg) {
		for (ClientHandler c : threads) {
			if (c.getClientName().equals(player.getName())) {
				c.sendMessage(msg);
				break;
			}
		}
	}
		
	/** gets all clienthandler playing in a game
	 * @param game the game
	 * @return a list with all clienthandlers in this game
	 */
	public ArrayList<ClientHandler> getHandler(Game game) {
		ArrayList<ClientHandler> handlers = new ArrayList<ClientHandler>();
		for (Player player : game.getPlayers()) {
			if (player instanceof HumanPlayer) {
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
		}
		return handlers;
	}
	
	/** initiates a hand
	 * The player already has a hand (in the game of the server), but now the server sends the tiles in the hand to the client
	 * @param player the player
	 * @return the message with the tiles
	 */
	public String initiateHand(Player player) {
		String hand = Protocol.Server.ADDTOHAND;
		for (Tile tile : player.getHand()) {
			hand += "_" + Character.toString(tile.getColor().getCharColor()) 
				+ Character.toString(tile.getShape().getCharShape());
		}
		return hand;
	}
	
	/** removes some names from all waitingRooms and the lobby when a game is started
	 * @param names the names to be removed (thus starting a game with those names)
	 */
	public void removeNames(String[] names) { // de namen die beginnen uit alle
												// wachtruimtes en evt lobby
												// verwijderen
		for (String temp : names) {
			for (int i = 2; i <= 4; i++) {
				for (int j = 0; j < waitingRooms.get(i - 2).size(); j++) {
					if (waitingRooms.get(i - 2).get(j).equals(temp)) {
						waitingRooms.get(i - 2).remove(j);
					}
				}
			}
			for (int k = 0; k < lobby.size(); k++) {
				if (lobby.get(k).equals(temp)) {
					lobby.remove(k);
				}
			}
		}
	}

	/** shuts down a game
	 * Not implemented yet
	 * @param game The game
	 */
	public void shutDownGame(Game game) {
		//Gamesluiten
		//message naar elke speler
		
	}

}
