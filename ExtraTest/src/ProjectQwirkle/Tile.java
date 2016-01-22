
package ProjectQwirkle;

public class Tile {
	public enum Shape {
		CIRCLE('A'), CROSS('B'), DIAMOND('C'), SQUARE('D'), STAR('E'), PLUS('F');
		//CIRCLE('A', '\u25CF'), CROSS('B', '\u2716'), DIAMOND('C', '\u25C6'), SQUARE('D', '\u25A0'), STAR('E', '\u22C6'), PLUS('F' , '\u002B');
		public final char c;
        //public final char u;
		//constructor
		Shape(char c) {
			this.c = c;
			//this.u = u;
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

    public Tile(Color color, Shape shape) {
        this.shape = shape;
        this.color = color;
    }
    
    public Tile(char charAt, char charAt2) {
		color = getShape(charAt);
		shape = getColor(charAt2);
	}

	public Shape getShape() {
    	return shape;
    }
   
    
    public Color getColor() {
    	return color;
    }  
    
    public String toString() {
    	return getColor() + " " + getShape();
    	//return String.format("\u001B[0;%sm %s \u001B[m", this.color.c + 30, this.shape.c);
    }

    public static void main(String [ ] args) {
    	Tile tile = new Tile(Shape.CIRCLE, Color.BLUE);
    	System.out.println(tile.toString());
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

}


