package ss.week3.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import ss.week3.hotel.*;

public class BillTest {

	private Bill bill;
	private BillRoom room;
	private BillRoom safe;
	private static final double PRICE1 = 12; 
	private static final double PRICE2 = 2;
	
	@Before
	public void setUp() throws Exception {
		bill = new Bill(System.out);
		room = new BillRoom("Stay in room: ", PRICE1); 
		safe = new BillRoom("Use of Safe: ", PRICE2);
		
	}

	@Test
	public void test() {
		bill.newItem(room); 
		bill.newItem(safe);
		assertTrue(bill.getSum() == 14);
		assertEquals(room.toString(), "Stay in room: ");
		bill.close();
		
		// test format
		//System.out.format("%-10s %14.2f", room.toString(), room.getAmount());
	}

}
