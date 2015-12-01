package ss.week3.hotel;

import java.io.PrintStream;

public class Bill {
	private double sum;
	private PrintStream printstream;
	
	public interface Item {
		public double getAmount();
		public String toString();
	}
	
	public Bill(PrintStream theOutStream) { 
		printstream = theOutStream;
	}
	
	public void close() {	
		if (printstream != null) { 
			printstream.format("\n%-20s %14.2f\n", "Sum of the bill: ", sum);
		}
	}
	
	public double getSum() {
		return sum;
	}
	
	public void newItem(Bill.Item item) { 
		sum = sum + item.getAmount();
		if (printstream != null) {
			printstream.format("%-20s\n", item.toString());
		
		}
	}
	
}
