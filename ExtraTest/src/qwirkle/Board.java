package qwirkle;


public class Board {
	private Tile[][] fields = new Tile[100][100];

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
}