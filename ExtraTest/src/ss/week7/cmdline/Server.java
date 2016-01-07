
package ss.week7.cmdline;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Server. 
 * @author  Theo Ruys
 * @version 2005.02.21
 */
public class Server {
    private static final String USAGE
        = "usage: " + Server.class.getName() + " <name> <port>";

    /** Starts a Server-application. */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println(USAGE);
            System.exit(0);
        }

        String name = args[0];
        int port = 0;
        ServerSocket serversock = null;
     

        // parse args[1] - the port
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println(USAGE);
            System.out.println("ERROR: port " + args[2]
            		           + " is not an integer");
            System.exit(0);
        }

        // try to open a Socket to the server
        try {
            serversock = new ServerSocket(port);
            System.out.println("Serversocket aangemaakt op poort " + port);
        } catch (IOException e) {
            System.out.println("ERROR: could not create a socket on port " + port);
        }

        // create Peer object and start the two-way communication
        try {
            Socket clientsocket = serversock.accept();
            Peer server = new Peer(name, clientsocket);
            System.out.println("Server: " + server + " aangemaakt op: " + clientsocket);
            Thread streamInputHandler = new Thread(server);
            streamInputHandler.start();
            server.handleTerminalInput();
            server.shutDown();
            serversock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }

} // end of class Server
