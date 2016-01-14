package ProjectQwirkle;

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

    public void makeCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Encodes an x, y coordinate to a String.
     *
     * @return a String representing the coordinate
     */
    public String toString() {
        return String.format("%d,%d", this.x, this.y);
    }
    
}
