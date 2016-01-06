package qwirkle;

public class Board {
	private Tile[][] fields = new Tile[100][100];

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
	
	public boolean gameOver() {
		return false;
	}
}
