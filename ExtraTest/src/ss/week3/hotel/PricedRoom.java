package ss.week3.hotel;

public class PricedRoom extends ss.week3.hotel.Room implements ss.week3.hotel.Bill.Item {
	private double roomPrice;
	
	public PricedRoom(int roomNo, double roomPrice, double safePrice) {
		super(roomNo, new PricedSafe(safePrice));
		this.roomPrice = roomPrice;
	}
	
	public double getAmount() {
		return roomPrice;
	}
	
	public String toString() {
		return String.format("%-20s %14.2f", "Price per night: ", roomPrice);
	}
}
