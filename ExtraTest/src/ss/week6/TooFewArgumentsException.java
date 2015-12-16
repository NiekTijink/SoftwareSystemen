package ss.week6;

public class TooFewArgumentsException extends WrongArgumentException{

	public TooFewArgumentsException() {
		super("error: must pass two command line arguments");
	}
}
