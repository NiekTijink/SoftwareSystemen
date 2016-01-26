package qwirkle;
 
public class Coordinate {
	private int x;
	private int y;

	public Coordinate(int x, int y) {
		makeCoordinate(x, y);
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void makeCoordinate(int a, int b) {
		this.x = a;
		this.y = b;
	}

	public String toString() {
		return String.format("%d,%d", this.x, this.y);
	}

}
