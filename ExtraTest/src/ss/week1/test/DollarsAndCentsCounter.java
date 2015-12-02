package ss.week1.test;

public class DollarsAndCentsCounter {
	//variables
	private int dollars = 0;
	private int cents = 0;
	
	//Constructor
	public DollarsAndCentsCounter() {
		dollars = 0;
		cents = 0;
	}
	
	//Queries
	
	public int dollars() {
		return dollars;
	}
	
	public int cents() {
		return cents;
	}
	
	//Commands
	
	/**
	 * increment nr of dollars and cents.
	 * @param dollars
	 * @param cents
	 */
	
	public void add(int dollars, int cents) {
		if (this.cents + cents < 100) { //Geen apart geval
			this.dollars = this.dollars + dollars; 
			this.cents = this.cents + cents; 
		} else {
			this.dollars = this.dollars + dollars + ((this.cents + cents) / 100);
			this.cents = (this.cents + cents) % 100;
		}
	}
	
	public void reset() {
		dollars = 0;
		cents = 0;
	}
}
	

