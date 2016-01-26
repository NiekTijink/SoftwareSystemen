package exception;

public class OutOfSyncException extends Exception {
	
	public OutOfSyncException() {
		super("Client/Server is out of sync");
	}
}
