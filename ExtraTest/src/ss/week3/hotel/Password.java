package ss.week3.hotel;

import ss.week3.pw.*;

public class Password {

	private String pass;
	private Checker checker;
	private String factoryPassword;
	
	public Password(Checker checker) {
		this.checker = checker;
		factoryPassword = checker.generatePassword();
		pass = factoryPassword;
	}
	
	public Password() {
		 this(new BasicChecker());
	}
	
	
	public String getFactorypassword() {
		return factoryPassword;
	}
	
	public Checker checker() {
		return checker;
	}
	
	public boolean acceptable(java.lang.String suggestion) {
		return checker.acceptable(suggestion);
	}
	
	//@ pure
	public boolean testWord(java.lang.String test) {
		if (pass.equals(test)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean setWord(java.lang.String oldpass, java.lang.String newpass) {
		if (testWord(oldpass) == true && acceptable(newpass) == true) {
			pass = newpass;
			return true;
		} else {
			return false;
		}
	}
}
