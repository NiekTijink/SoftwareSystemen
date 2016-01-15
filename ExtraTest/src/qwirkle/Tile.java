
package qwirkle;

public class Tile {
	public enum Shape {
		CIRCLE('A'), CROSS('B'), DIAMOND('C'), SQUARE('D'), STAR('E'), PLUS('F');
		
		//constructor
		private Shape(final char s) {
			this.s = s;
		}
		// Internal state
	    private char s;
	 
	    public char getShape() {
	        return s;
	    }
	}
	
	public enum Color {
		RED('A'), ORANGE('B'), YELLOW('C'), GREEN('D'), BLUE('E'), PURPLE('F');

		public final char c;
		
		Color(char c){
			this.c = c;
		}
		
		public char getColor() {
			return c;
		}
	}
	
	private final Shape shape;
    private final Color color;

    public Tile(Shape shape, Color color) {
        this.shape = shape;
        this.color = color;
    }
    
    public Tile(char s, char c) {
    	shape = getShape(s);
    	color = getColor(c);
    }
    
    public Shape getShape() {
    	return shape;
    }
   
    
    public Color getColor() {
    	return color;
    }  
    
	   public Tile.Shape getShape(char i) {
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
	   
	   public Tile.Color getColor(char i) {
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
	   
    public String toString() {
    	return getColor() + " " + getShape();
    }
    
    public static void main(String[] args) {
    	Tile tile = new Tile('A','B');
    	System.out.println(tile.getColor());
    	String msg = "Makemove_Hoi";
    	System.out.println(msg.substring(9));
    }

}