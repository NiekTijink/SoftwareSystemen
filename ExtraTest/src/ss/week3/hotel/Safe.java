package ss.week3.hotel;

public class Safe {
	private boolean active;
	private boolean open;
	private Password pass;
	
	public Safe() {
		active = false;
		open = false;
		pass = new Password();
	}
	
	//returns the state of the safe (active, inactive)
	//@ pure
	public boolean isActive() {
		return active;
	}
	
	//returns the state of the safe (open/closed)
	//@ pure
	public boolean isOpen() {
		return open;
	}
	
	//returns the password object of this safe
	//@ pure
	public Password getPassword() {
		return pass;
	}
	
	//activates the safe if the password is correct and the safe is active
	/*
	 //@ requires isActive() == false;
	 //@ ensures getPassword().testWord(password) => isActive();
	 */
	public boolean activate(java.lang.String password) {
		assert pass.testWord(password) == true;
		if (pass.testWord(password) == true && isActive() == false) {
			active = true;
			return true;
		} else {
			return false;
		}
	}
	
	// deactivates the safe
	/*@ requires isActive() == true;
	 */
	public boolean deactivate() {
		assert active == true;
		if (active == true) {
			active = false; 
			open = false;
			return true;
		} else {
			return false;
		}
	}
	
	// opens the safe if the password is correct and if it active
	/*@ requires getPassword().testWord(password) == true; 
	 //@ ensures isOpen() == true;
	 */
	public boolean open(String password) {
		assert active == true;
		if (isActive() == true && this.pass.testWord(password)) {
			open = true;
			return true;
		} else {
			return false;
		}
	}
	
	// closes the safe
	public boolean close() {
		assert active;
		assert open == true;
		if (active == true && open == true) {
			open = false;
			return true;
		} else {
			return false;
		}
	}
	 public static void main(String[] args) {
		Safe safe;
		safe = new Safe();
		safe.activate("fout");
	}
}
