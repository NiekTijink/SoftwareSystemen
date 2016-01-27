package qwirkle;
/** A coordinate shows a position on the board using a xvalue and a yvalue
 * @author Niek Tijink & Thomas Kolner
 *
 */
public class Coordinate {
	private int x;
	private int y;

	/** Creating a coordinate using a xvalue and a yvalue
	 * @param x the xvalue
	 * @param y the yvalue
	 */
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/** get the xValue of an coordinate
	 * @return the xValue
	 */
	public int getX() {
		return this.x;
	}

	/** get the yValue of an coordinate
	 * @return the yValue
	 */
	public int getY() {
		return this.y;
	}

	/** gives String that shows the values of the coordinate, used for board
	 * @return the toString of this coordinate
	 */
	public String toString() {
		return String.format("%d,%d", this.x, this.y);
	}

}
