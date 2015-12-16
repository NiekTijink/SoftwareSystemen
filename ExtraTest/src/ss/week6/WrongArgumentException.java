package ss.week6;

public class WrongArgumentException extends Exception {
	private String msg;

	public WrongArgumentException() {
	}
	
	public WrongArgumentException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public String getMessage() {
		return msg;
	}
}
