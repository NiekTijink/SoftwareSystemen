package ss.week6;

public class ArgumentLengthsDifferException extends WrongArgumentException{
	
	public ArgumentLengthsDifferException(int s1, int s2) {
		super("error: length of command line arguments "
                    + "differs (" + s1 + ", " + s2 + ")");
	}
}
