package ss.week3.pw;

public class BasicChecker implements Checker {
	
	public final static String INITPASS = "password";

	public BasicChecker() {
	}
	
	public String generatePassword() {
		return INITPASS;
	}
		
	public boolean acceptable(String password) {
		return password.length() > 5 && !password.contains(" ");
	}
	

}

