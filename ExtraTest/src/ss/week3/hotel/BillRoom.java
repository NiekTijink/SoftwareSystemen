package ss.week3.hotel;

public class BillRoom implements Bill.Item {
	private String text;
	private double amount;
	
	public BillRoom(String text, double amount) {
		this.text = text; 
		this.amount = amount;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public String toString() {
		return text;
	}
}
