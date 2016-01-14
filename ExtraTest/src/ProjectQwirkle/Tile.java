
package ProjectQwirkle;

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
    
    public Shape getShape() {
    	return shape;
    }
   
    
    public Color getColor() {
    	return color;
    }  
    
    public String toString() {
    	return getColor() + " " + getShape();
    }
    
    public static void main(String[] args) {
    	String test = "CIRCLE";
    	//Tile a = new Tile(Shape.test , Color.CYAN);
    //	a.getShape();
    	
    }

}