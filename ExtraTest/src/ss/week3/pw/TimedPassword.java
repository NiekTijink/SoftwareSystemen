package ss.week3.pw;

import ss.week2.hotel.Password;

public class TimedPassword extends Password {

	private long validTime;

	
	public TimedPassword(long experationTime) {
		validTime = experationTime * 1000 + java.lang.System.currentTimeMillis();
	}
	
	public TimedPassword() {
		this(60);
	}
	
	public boolean isExpired() {
		return java.lang.System.currentTimeMillis() > validTime; 
	}
	
	public boolean setWord(java.lang.String oldpass, java.lang.String newpass) {
		if (testWord(oldpass) == true && acceptable(newpass) == true) {
			super.setWord(oldpass, newpass);
			validTime = java.lang.System.currentTimeMillis() + 60000;
			return true;
		} else {
			return false;
		}
	}
}
