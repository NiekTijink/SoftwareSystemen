	package ss.week3.pw;

public class StrongChecker extends BasicChecker {
	
	private static final String INITPASS = "password1";
	
	public StrongChecker() {
		super();
	}
	
	public String generatePassword() {
		return INITPASS;
	}
	
	public boolean acceptable(String password) {
		return Character.isLetter(password.charAt(0)) && Character.isDigit(password.charAt(password.length()-1));
		
	}
}
