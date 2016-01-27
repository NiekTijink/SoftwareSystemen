package online;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import protocol.Protocol;
import qwirkle.*;
/** A clienthandler is used to facilitate communication between the client and the server
 * @author Niek Tijink & Thomas Kolner
 *
 */
public class ClientHandler extends Thread {

	private Server server;
	private BufferedReader in;
	private BufferedWriter out;
	private String clientName;
	public static final String NOREPLY = "";
	private Game currentGame;
	private Player currentPlayer;

	/** creates a new clienthandler 
	 * @param server the server 
	 * @param sockArg the socket with the client
	 * @throws IOException
	 */
	public ClientHandler(Server server, Socket sockArg) throws IOException {
		this.server = server;
		in = new BufferedReader(new InputStreamReader(sockArg.getInputStream()));
		out = new BufferedWriter(new OutputStreamWriter(sockArg.getOutputStream()));
	}

	/** while the client and server are running, the clienthandler reads messages from the client
	 */
	public void run() {
		try {
			String msg = " ";
			while (msg != null) {
				msg = in.readLine();
				String msgback = handleMessageFromClient(msg);
				if (!(msgback.equals(NOREPLY))) {
					sendMessage(msgback);
				}
				//server.broadcast(msg);
				// hier krijgt server commando, moet hij dingetjes gaan doen
			}
			shutDown();
		} catch (IOException e) {
			if (currentGame != null) {
				server.shutDownGame(currentGame);
			}
			shutDown();
		}
	}

	/** returns the name of the client
	 * @return name of the client
	 */
	public String getClientName() {
		return clientName;
	}
 
	/** handles the messages from the client
	 * @param msg the message from the client
	 * @return the msg back to the client ("" if no message back)
	 */
	public String handleMessageFromClient(String msg) {
		System.out.println(msg);
		String[] splitMsg = msg.split(Character.toString(Protocol.Settings.DELIMITER));
		if (msg.startsWith(Protocol.Client.HALLO)) {
			String msgback = server.addClientName(splitMsg[1], this);
			if (!(msgback.startsWith(Protocol.Server.ERROR))) {
				clientName = splitMsg[1];
			}
			return msgback;
		} else if (msg.equals(Protocol.Client.QUIT)) {
			sendMessage("Quiting...");
			shutDown(); 
			return NOREPLY;
		} else if (msg.startsWith(Protocol.Client.REQUESTGAME)) {
			return server.requestGame(clientName, Integer.parseInt(splitMsg[1]));
		} else if (msg.startsWith(Protocol.Client.MAKEMOVE)) {
			if (currentGame == null || currentPlayer == null) {
				return Protocol.Server.ERROR + "_invalidmove";
			} else if (currentGame.getMoveNr() == 0) {
				return firstMove(currentGame, currentPlayer, msg);
			} else {
				if (currentGame.getPlayers()[currentGame.playersTurn] == currentPlayer) {
					String newStones = currentGame.makeMove(currentPlayer, msg);
					if (newStones != NOREPLY && !(newStones.startsWith(Protocol.Server.ERROR))) {
						for (int i = 0; i < currentGame.getPlayers().length; i++) {
							if (currentGame.getPlayers()[i] == currentPlayer) {
								currentGame.playersTurn = (i + 1) % currentGame.getPlayers().length;
								server.makeMove(currentGame, currentPlayer,
									   currentGame.getPlayers()[currentGame.playersTurn], msg);
								return newStones;
							}
						}
						if (currentGame.gameOver()) {
							System.out.println("AFGELOPEN");
							System.exit(0);
							// hier iets doen als game is afgelopen
						}
					} else {
						return Protocol.Server.ERROR + "_invalidmove";
					}
				} else {
					return Protocol.Server.ERROR + "_notyourmove";
				}
			}
		} else if (msg.startsWith(Protocol.Client.CHANGESTONE)) {
			if (currentGame.getPlayers()[currentGame.playersTurn] == currentPlayer) {
				String newStones = currentPlayer.changeStones(splitMsg, currentGame.getDeck());
				if (newStones != NOREPLY) {
					for (int i = 0; i < currentGame.getPlayers().length; i++) {
						if (currentGame.getPlayers()[i] == currentPlayer) {
							currentGame.playersTurn = (i + 1) % currentGame.getPlayers().length;
							server.makeMove(currentGame, currentPlayer,
									  currentGame.getPlayers()[currentGame.playersTurn], "");
							return newStones;
						}
					}
				} else {
					return Protocol.Server.ERROR + "_notyourstones";
				}
			} else {
				return Protocol.Server.ERROR + "_notyourmove";
			}
		} else if (msg.equals(Protocol.Client.GETSTONESINBAG)) {
			return currentGame.getDeck().getStonesInBag();
		}
		return Protocol.Server.ERROR + "_generalerror";
	}
	
	/** during the first move this method is called, because the move needs to be saved instead of placed
	 * @param game the currentgame
	 * @param player the currentplayer
	 * @param msg the move
	 * @return Error if invalid move, else Nothing
	 */
	public String firstMove(Game game, Player player, String msg) {
		String msgback = currentGame.addToFirstMove(player, msg);
		if (msgback.equals("true")) {
			game.calcBestFirstMove();
		} else if (msgback.startsWith(Protocol.Server.ERROR)) {
			return msgback;
		}
		return NOREPLY;
	}
	

	/** sends a message to the client
	 * @param msg the message
	 */
	public void sendMessage(String msg) {
		try {
			out.write(msg);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			shutDown();
		}
	}

	/** when a new game is constructed, the server will call this method and set the currentGame of this clienthandler
	 * @param g the new Game
	 */
	public void setCurrentGame(Game g) {
		currentGame = g;
	}

	/** get the currentGame
	 * @return the currentGame
	 */
	public Game getCurrentGame() {
		return currentGame;
	}

	/**
	 * @return the currentplayer
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/** when a new game is constructed, the server will call this method and set the currentPlayer of this clienthandler
	 * @param g the new Player
	 */
	public void setCurrentPlayer(Player p) {
		currentPlayer = p;
	}

	/** shuts the connection down
	 */
	public void shutDown() {
		server.removeHandler(this);
		server.broadcast("[" + clientName + " has left]");

	}
}
