package qwirkle;

import java.util.ArrayList;

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
	
	public boolean setField(int xValue, int yValue, Tile tile) { // eerst alles plaatsen, daarna of het mag
		if (isField(xValue, yValue) && isEmptyField(xValue, yValue)) {
				fields[xValue][yValue] = tile;
				return true;
		} else {
			return false;
		}
	}
	
	public void deleteField(int xValue, int yValue) {
		if (isField(xValue, yValue)) {
			fields[xValue][yValue] = null;
		}
	}
	
	public int calculateScore(int[] moves) {
		int score = 0;
		if (moves.length > 2 && moves[0] == moves[2]) { //zelfde xwaarde --> dus in een rij
			calcRowScore(moves);
		} else if (moves.length>2 && moves[1] == moves[3]) { // zelfde ywaarde, dus kolom 
			
		} else if (moves.length == 2) { // maar een steen
			
		} else {
			// foutmelding
		}
	}

	private void calcRowScore(int[] moves) {
		int c = 1;
		while (fields[moves[0]+c][moves[1]] != null) {
			c++;
		}
		int northRow = c-1;
		c = 1;
		while (fields[moves[0]-c][moves[1]] != null) {
			c++;
		}
		int rowLength = northRow + c;
		
	}
	
 	public boolean isLegalTile(int xValue, int yValue) { // Nog niet genoeg, mag maar in 1 rij/kolom plaatsen
		Tile.Color color = fields[xValue][yValue].getColor();
		Tile.Shape shape = fields[xValue][yValue].getShape();
		return (testVertical(xValue, yValue, color, shape) && testHorizontal(xValue, yValue, color, shape));
	}
	
	public boolean testHorizontal(int xValue, int yValue, Tile.Color color, Tile.Shape shape) {
		int easternTiles = 0;
		char typeOfRow = ' ';
		ArrayList<Tile.Color> colors = new ArrayList<Tile.Color>();
		colors.add(color);
		ArrayList<Tile.Shape> shapes = new ArrayList<Tile.Shape>();
		shapes.add(shape);
		
		boolean doorgaan = true;
		int c = 0; //counter
		while (doorgaan) { // omhoog kijken
			c++;
			if (!(fields[xValue+c][yValue] == null)) { // kijken of vakje leeg is
				if (fields[xValue+c][yValue].getColor().equals(color) // kijken of kleur gelijk is
						&& !(shapes.contains(fields[xValue][yValue+c].getShape())) // als kleur gelijk is, vorm mag niet gelijk
						&& typeOfRow != 'S') {
					typeOfRow = 'C';
					colors.add(fields[xValue+c][yValue].getColor());
				} else if (fields[xValue][yValue+c].getShape().equals(shape) 
						&& !(shapes.contains(fields[xValue+c][yValue].getShape()))
						&& typeOfRow != 'C') {
					typeOfRow = 'S';
					shapes.add(fields[xValue+c][yValue].getShape());
				} else {
					return false;
				}
			doorgaan = false;
			}
		}
		easternTiles = c - 1;
		c = 0; //counter reset
		doorgaan = true;
		while (doorgaan) { //omlaag
			c++;
			if (!(fields[xValue-c][yValue] == null)) {
				if (fields[xValue-c][yValue].getColor().equals(color) // kijken of kleur gelijk is
						&& !(shapes.contains(fields[xValue-c][yValue].getShape())) // als kleur gelijk is, vorm mag niet gelijk
						&& typeOfRow != 'S') {
					colors.add(fields[xValue-c][yValue].getColor());
				} else if (fields[xValue-c][yValue].getShape().equals(shape) 
						&& !(shapes.contains(fields[xValue-c][yValue].getShape()))
						&& typeOfRow != 'C') {
				} else {
					return false;
				}
			} return (c + easternTiles > 1);
		}
		return true;
	}
	
	public boolean testVertical(int xValue, int yValue, Tile.Color color, Tile.Shape shape) {
		int northernTiles = 0;
		char typeOfRow = ' ';
		ArrayList<Tile.Color> colors = new ArrayList<Tile.Color>();
		colors.add(color);
		ArrayList<Tile.Shape> shapes = new ArrayList<Tile.Shape>();
		shapes.add(shape);
		
		boolean doorgaan = true;
		int c = 0; //counter
		while (doorgaan) { // omhoog kijken
			c++;
			if (!(fields[xValue][yValue + c] == null)) { // kijken of vakje leeg is
				if (fields[xValue][yValue + c].getColor().equals(color) // kijken of kleur gelijk is
						&& !(shapes.contains(fields[xValue][yValue + c].getShape())) // als kleur gelijk is, vorm mag niet gelijk
						&& typeOfRow != 'S') {
					typeOfRow = 'C';
					colors.add(fields[xValue][yValue + c].getColor());
				} else if (fields[xValue][yValue + c].getShape().equals(shape) 
						&& !(shapes.contains(fields[xValue][yValue + c].getShape()))
						&& typeOfRow != 'C') {
					typeOfRow = 'S';
					shapes.add(fields[xValue][yValue + c].getShape());
				} else {
					return false;
				}
			} else {
				doorgaan = false;
			}
		}
		northernTiles = c - 1;
		c = 0; //counter reset
		doorgaan = true;
		while (doorgaan) { //omlaag
			c++;
			if (!(fields[xValue][yValue - c] == null)) {
				if (fields[xValue][yValue - c].getColor().equals(color) // kijken of kleur gelijk is
						&& !(shapes.contains(fields[xValue][yValue - c].getShape())) // als kleur gelijk is, vorm mag niet gelijk
						&& typeOfRow != 'S') {
					colors.add(fields[xValue][yValue - c].getColor());
				} else if (fields[xValue][yValue - c].getShape().equals(shape) 
						&& !(shapes.contains(fields[xValue][yValue - c].getShape()))
						&& typeOfRow != 'C') {
				} else {
					return false;
				}
			} return (c + northernTiles > 1);
		}
		return true;
	}
	
	public boolean gameOver() { // moet nog geïmplementeerd worden
		return false;
	}
}
