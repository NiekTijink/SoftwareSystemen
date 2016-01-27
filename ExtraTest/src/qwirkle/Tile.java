
package qwirkle;
/** Using Tiles
 * 
 * @author Niek Tijink && Thomas Kolner
 */
public class Tile {
	/** There are 6 different shapes
	 */
	public enum Shape {
		CIRCLE('A'), CROSS('B'), DIAMOND('C'), SQUARE('D'), STAR('E'), PLUS('F');

		
		private Shape(final char s) {
			this.s = s;
		}

		
		private char s;

		public char getCharShape() {
			return s;
		}
	}
	/** There are also 6 different colors
	 */
	public enum Color {
		RED('A'), ORANGE('B'), YELLOW('C'), GREEN('D'), BLUE('E'), PURPLE('F');

		private char c;

		Color(char c) {
			this.c = c;
		}

		public char getCharColor() {
			return c;
		}
	}

	private final Shape shape;
	private final Color color;

	/** Creating a new Tile using a Color and Shape
	 * @param color the color
	 * @param shape the shape
	 */
	public Tile(Color color, Shape shape) {
		this.shape = shape;
		this.color = color;
	}

	/** Creating a new Tile using two characters
	 * @param c character for Color
	 * @param s character for Shape
	 */
	public Tile(char c, char s) {
		shape = getShape(s);
		color = getColor(c);
	}

	/** get the shape
	 * @return the shape
	 */
	public Shape getShape() {
		return shape;
	}

	/** get the color
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/** Get the Shape (ENUM-type) using a character
	 * @param i Character (A-F)
	 * @return The Shape
	 */
	public static Tile.Shape getShape(char i) {
		if (i == 'D') {
			return Tile.Shape.SQUARE;
		} else if (i == 'A') {
			return Tile.Shape.CIRCLE;
		} else if (i == 'C') {
			return Tile.Shape.DIAMOND;
		} else if (i == 'F') {
			return Tile.Shape.PLUS;
		} else if (i == 'B') {
			return Tile.Shape.CROSS;
		} else if (i == 'E') {
			return Tile.Shape.STAR;
		} else {
			return null;
		}
	}

	/** Get the Color (ENUM-type) using a character
	 * @param i Character (A-F)
	 * @return The Color
	 */
	public static Tile.Color getColor(char i) {
		if (i == 'A') {
			return Tile.Color.RED;
		} else if (i == 'D') {
			return Tile.Color.GREEN;
		} else if (i == 'C') {
			return Tile.Color.YELLOW;
		} else if (i == 'E') {
			return Tile.Color.BLUE;
		} else if (i == 'B') {
			return Tile.Color.ORANGE;
		} else if (i == 'F') {
			return Tile.Color.PURPLE;
		} else {
			return null;
		}
	}

	/** shows the Tile as string (for the TUI)
	 * @return the Tile as string
	 */
	public String toString() {
		return getColor() + " " + getShape();
	}

}