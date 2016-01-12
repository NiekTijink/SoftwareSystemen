
package qwirkle;

public class Tile {
	public enum Shape {
		SQUARE('A'), CIRCLE('B'), DIAMOND('C'), CLUB('D'), STARBURST('E'), CROSS('F');
		//SQUARE, CIRCLE, DIAMOND, CLUB, STARBURST, CROSS;
		
		//constructor
		private Shape(final char s) {
			this.s = s;
		}
		// Internal state
	    private char s;
	 
	    public int getShape() {
	        return s;
	    }
	}
	
	public enum Color {
		RED('1'), GREEN('2'), YELLOW('3'), BLUE('4'), MAGNETA('5'), CYAN('6');
		//RED, GREEN, YELLOW, BLUE, MAGNETA, CYAN;
		public final int c;
		
		Color(char c){
			this.c = c;
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
    
    public static void main(String[] args) {
    	Tile a = new Tile(Shape.CIRCLE , Color.CYAN);
    	a.getShape();
    	
    }

}