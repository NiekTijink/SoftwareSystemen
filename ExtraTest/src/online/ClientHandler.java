package online;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import qwirkle.*;

public class ClientHandler extends Thread {

	private Server server;
	private BufferedReader in;
	private BufferedWriter out;
	private String clientName;
	private Socket sock;
	public static final String NOREPLY = "";
	private Game currentGame;
	private Player currentPlayer;

	public ClientHandler(Server server, Socket sockArg) throws IOException {
		this.server = server;
		sock = sockArg;
		in = new BufferedReader(new InputStreamReader(sockArg.getInputStream()));
		out = new BufferedWriter(new OutputStreamWriter(sockArg.getOutputStream()));
	}

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
			// TODO Auto-generated catch block
			shutDown();
		}
	}

	public String getClientName() {
		return clientName;
	}

	public String handleMessageFromClient(String msg) {
		String[] splitMsg = msg.split(Character.toString(Protocol.Settings.DELIMITER));
		;
		if (msg.startsWith(Protocol.Client.HALLO)) {
			String msgback = server.addClientName(splitMsg[1]);
			if (!(msgback.startsWith(Protocol.Server.ERROR))) {
				clientName = splitMsg[1];
			}
			return msgback;
		} else if (msg.equals(Protocol.Client.QUIT)) {
			System.out.println("Quiting...");
			shutDown();
			return NOREPLY;
		} else if (msg.startsWith(Protocol.Client.REQUESTGAME)) {
			return server.requestGame(clientName, Integer.parseInt(splitMsg[1]));
		} else if (msg.startsWith(Protocol.Client.MAKEMOVE)) {
			if (currentGame == null) {
				return Protocol.Server.ERROR + "_invalidmove";
			} else if (currentGame.getMoveNr() == 0) {
				return firstMove(currentGame,currentPlayer,msg);
			} else {
			return server.makeMove(clientName, msg);
			}
		}
		return NOREPLY;
	}
	
	public String firstMove(Game game, Player player, String msg) {
		String msgback = currentGame.addToFirstMove(player, msg);
		if (msgback.equals("true")) {
			server.calcBestFirstMove(currentGame, currentGame.getFirstMove(), currentGame.getFirstMoveScores());
		} else if (msgback.startsWith(Protocol.Server.ERROR)) {
			return msgback;
		}
		return NOREPLY;
		/*boolean full = true;
		for (int i = 0; i < currentGame.getPlayers().length;i++) {
			if (currentGame.getFirstMove()[i] == null) {
				full = false;
			}
		}
		if (full) {
			return server.calcBestFirstMove(currentGame.getFirstMove());
		} else {
			// TODO Error: één of meerdere clients reageert niet
		}*/
	}
	

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

	public void setCurrentGame(Game g) {
		currentGame = g;
	}

	public Game getCurrentGame() {
		return currentGame;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player p) {
		currentPlayer = p;
	}

	public void shutDown() {
		server.removeHandler(this);
		server.broadcast("[" + clientName + " has left]");

	}
}
