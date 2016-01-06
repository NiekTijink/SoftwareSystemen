package qwirkle;

public class Tile {
	public enum Shape {
		SQUARE('A'), CIRCLE('B'), DIAMOND('C'), CLUB('D'), STARBURST('E'), CROSS('F');
		//, CIRCLE, DIAMOND, CLUB, STARBURST, CROSS;
		public final char shape;
		
		Shape(char s) {
			this.shape = s;
		}
	}
	
	public enum Color {
		RED(1), GREEN(2), YELLOW(3), BLUE(4), MAGNETA(5), CYAN(6);
		//RED, GREEN, YELLOW, BLUE, MAGNETA, CYAN;
		public final int color;
		
		Color(int c){
			this.color = c;
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
    }

}
