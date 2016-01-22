package online;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import qwirkle.*;
import protocol.Protocol;


public class Client extends Thread{

	private static final String USAGE
    = "usage: java week7.cmdchat.Client <name> <address> <port>";

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println(USAGE);
			System.exit(0);
		}
		clientName = args[0];
		InetAddress host=null;
		int port =0;

		try {
			host = InetAddress.getByName(args[1]);
		} catch (UnknownHostException e) {
			print("ERROR: no valid hostname!");
			System.exit(0);
		}

		try {
			port = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			print("ERROR: no valid portnummer!");
			System.exit(0);
		}
		
	
		Client client = new Client(args[0], host, port);
		client.sendMessage("HALLO_" + args[0]);
		client.start();
		
		/*do{
			String input = readString("");
			client.sendMessage(input);
		}while(true);*/
	
	}
	
	private static String clientName;
	private Socket sock;
	private BufferedReader in;
	private BufferedWriter out;
	private Game currentGame;
	private Player currentPlayer;
	
	public Client(String name, InetAddress host, int port) {
		clientName = name;
		try {
			sock = new Socket(host,port);
			System.out.println("Client: " + name + " aangemaakt op poort: " + port);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	    	out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		String msg = "> ";
		while (msg != null) {
			try {
				msg = in.readLine();
				//print(msg);
				String msgback = handleMsgFromServer(msg);
				if (msgback != ClientHandler.NOREPLY) {
				sendMessage(msgback);
				}
				// hier krijgt client bericht van server. Moet hij automatisch afhandelen
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void sendMessage(String msg) {
		try {
			out.write(msg);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO insert body
	}
	public String handleMsgFromServer(String msg) {
		System.out.println(msg);
		String[] splitMsg = msg.split(Character.toString(Protocol.Settings.DELIMITER));
		if (msg.startsWith(Protocol.Server.HALLO)) {
			String prompt = "> " + clientName + ", with how many players do you want to play? ";
	        int choice = readInt(prompt);
	        boolean valid = choice >= 0 && choice <= 4;
	        while (!valid) {
	            System.out.println("ERROR: Must be between 0 and 4");
	            choice = readInt(prompt);
	            valid = choice >= 0 && choice <= 4;
	        }
	        return Protocol.Client.REQUESTGAME + "_" + choice;
		} else if (msg.startsWith(Protocol.Server.OKWAITFOR)) {
			print("Waiting for " + splitMsg[1] + "more player(s)");
		} else if (msg.startsWith(Protocol.Server.STARTGAME)) {
			currentGame = new Game(clientName);
			currentPlayer = currentGame.getPlayers()[0];
		} else if (msg.startsWith(Protocol.Server.ADDTOHAND)) {
			currentPlayer.addToHand(msg.substring(10));
			if (currentGame.getMoveNr() == 0) {
				System.out.println(currentPlayer.getHandString());
				firstTurn ft = new firstTurn(currentPlayer, currentGame.getBoard());
				ft.makefirstTurn();
				return ft.getFirstMoveString();
			}
		} else if (msg.startsWith(Protocol.Server.STONESINBAG)) {
			//nog niets
		} else if (msg.startsWith(Protocol.Server.MOVE)) {
			System.out.println(msg);
			String answ = "";
			for (int i = 3; i < splitMsg.length; i++) {
				answ += splitMsg[i] + Protocol.Settings.DELIMITER;
			}
			currentGame.getBoard().setMove(answ.substring(0,answ.length()-1));
			currentGame.moveNr++;
			if (splitMsg[2].equals(clientName)) {
				// dit gaat een String teruggeven.
				// Deze string printen we en sturen we door naar server
				System.out.println(currentGame.getBoard().toString());
				System.out.println(currentPlayer.getHandString());
				String msg2 = currentPlayer.determineMove(currentGame.getBoard());
				if (msg2.startsWith(Protocol.Client.MAKEMOVE)) { // jouw beurt
					return msg2;
				} // of swappen 
			} else if (splitMsg[1].equals(clientName)) {// stenen uit hand verwijderen
				currentPlayer.deleteTiles(answ.substring(0, answ.length() -1));
			} else if (msg.startsWith(Protocol.Server.ERROR)) {
				System.out.println(msg);
				String msg2 = currentPlayer.determineMove(currentGame.getBoard());
				if (msg2.startsWith(Protocol.Client.MAKEMOVE)) { // jouw beurt
					return msg2;
				}
			}
		}
		return ClientHandler.NOREPLY;
	}
	
	public static int readInt(String tekst) {
		System.out.print(tekst);
		int antw = -1;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			antw = Integer.parseInt(in.readLine());
		} catch (IOException e) {
		}

		return antw;
	}
	
	public static void print(String msg) {
		System.out.println(msg);
	}
	 
	  
	public void shutDown() {
		try {
			System.out.println("Closing socket connection...");
			sock.close();
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block			
			e.printStackTrace();
		}
	}
	public static String readString(String tekst) {
		System.out.print(tekst);
		String antw = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			antw = in.readLine();
		} catch (IOException e) {
		}

		return (antw == null) ? "" : antw;
	}
}
