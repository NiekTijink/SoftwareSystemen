package online;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client extends Thread{

	private static final String USAGE
    = "usage: java week7.cmdchat.Client <name> <address> <port>";

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println(USAGE);
			System.exit(0);
		}
	
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
		
		do{
			String input = readString("");
			client.sendMessage(input);
		}while(true);
	}
	
	private String clientName;
	private Socket sock;
	private BufferedReader in;
	private BufferedWriter out;
	
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
				// hier krijgt client bericht van server. Moet hij automatisch afhandelen
				print(msg);
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
}
