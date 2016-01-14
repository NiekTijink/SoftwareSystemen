package ProjectQwirkle;

import java.util.ArrayList;

public class Board {
	private Tile[][] fields = new Tile[100][100];
	private int[] boundaries = new int[4];

	// maakt een bord aan
	// misschien kunnen we dit variabel maken, denk dat dat wel kan
	// eerst maar eens zo werkend krijgen
	public Board() {
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				fields[i][j] = null;
			}
		}
	}
	
	public Tile[][] getBoard() {
		return fields;
	}
	
	// checkt of een bepaald veld wel een veld is
	public boolean isField(int xValue, int yValue) {
		return (xValue >= 0 || xValue < 100 || yValue >= 0 || yValue < 100);
	}
	
	public Tile getField(int xValue, int yValue) {
		if (isField(xValue, yValue)) {
			return fields[xValue][yValue];
		} else {
			return null;
		}
	}
	
	public boolean isEmptyField(int xValue, int yValue) {
		return (isField(xValue, yValue) && fields[xValue][yValue] == null);
	}
	
	public void setField(int xValue, int yValue, Tile tile) {
		if (isField(xValue, yValue) && isEmptyField(xValue, yValue)) {
			fields[xValue][yValue] = tile;
		}
	}
	
	public int testMove(int[][] move, Tile[] hand) {
		TestMove test = new TestMove(this, move, hand);
		if (test.isLegalMove()) {
			return test.getScore();
		}
		return -1;
	}
	
	
	public boolean gameOver() {
		return false;
	}
	
	public Board deepcopy() {
		Board deepcopy = new Board();
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				deepcopy.fields[i][j] = this.fields[i][j];
			}
		}
		return deepcopy;
	}
	
	public int[] getBoundaries(ArrayList<Coordinate> c) {
        for( Coordinate temp : c){
            boundaries[0] = Math.max(boundaries[0], temp.getY());
            boundaries[1] = Math.max(boundaries[1], temp.getX());
            boundaries[2] = Math.min(boundaries[2], temp.getY());
            boundaries[3] = Math.min(boundaries[3], temp.getX());
        }
        return boundaries;
     }

	public String toString(int [] bound) {
        String result = "";
        for (int y = boundaries[2]; y <= boundaries[0]; y++) {
            for (int x = boundaries[3]; x < boundaries[1]; x++) {
                Tile tile = getField(x, y);
                result += tile == null ? "  " : tile.toString() + " ";
            }
            result += "\n";
        }
        return result;
    }
}