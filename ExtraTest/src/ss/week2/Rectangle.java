package ss.week2;

import static org.junit.Assert.assertTrue;

public class Rectangle {

	private int length;
	private int width;

	
	/*create a new rectangle with the specified length and width. 
	Length and wide must be non-negative */
	
	public Rectangle(int length, int width) {
		this.length = length;
		this.width = width;
	}
	
	//@	ensures length() >= 0;
	//@ pure;
	public int length() {
		assertTrue(length >= 0);
		return length;
	}
	
	//@ requires width() >= 0;
	//@ pure;
	public int width() {
		assertTrue(width >= 0);
		return width;	
	}
	
	 //@ requires length() >= 0 && width() >= 0;
	 //@ ensures \result == length() * width();
	 //@ pure;
	public int area() {
		return length() * width();

	}
	
	//@ requires length() >= 0 && width() >= 0;
	//@ ensures  2 * length() + 2 * width() == perimeter();
	//@ pure;
	public int perimeter() {
		return 2 * length() + 2 * width();
	}
}
