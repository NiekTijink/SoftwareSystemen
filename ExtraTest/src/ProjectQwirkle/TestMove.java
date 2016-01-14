package ProjectQwirkle;

import java.util.ArrayList;

public class TestMove {
	private Tile[][] fields;
	private Tile[] hand;
	private int[][] move;
	private int score;
	private char typeRow;
	private int horizontalRow;
	private int verticalRow;
	private int nrOfMoves;
	private boolean isConnected = false; //checks whether the move is connnected to tiles already on the board
	
	public TestMove(Board board, int[][] move, Tile[] hand) {
		Board b = board;
		fields = board.getBoard();
		this.move = move;
		this.hand = hand; // nu nog niet nodig, later wel lijkt me
		int i = 0;
		while (move[i][0] != -1) {
			nrOfMoves++;
			i++;
			b.setField(move[i][0], move[i][1], hand[move[i][2]]);
		}
	}
	
	public boolean isLegalMove() { // mag maar in rij/kolom & moet andere stenen raken
		if (move[0][0] == move[1][0]) { // moves met zelfde x-waarde
			int i = 2;
			while (move[i][0] != -1) {
				if (move[i][0] != move[0][0]) {
					return false; // niet allemaal zelfde x-waarde
				}
				i++;
			}
			typeRow = 'x';
			if (!testVertical(move[0][0],move[0][1],
					fields[move[0][0]][move[0][1]].getColor(),fields[move[0][0]][move[0][1]].getShape())) {
				return false;
			}
		} else if (move[0][1] == move[1][1]) { // moves met zelfde y-waarde
			int i = 2;
			while (move[i][0] != -1) {
				if (move[i][1] != move[0][1]) {
					return false; // niet allemaal zelfde y-waarde
				}
				i++;
			}
			typeRow = 'y';
			if (!testHorizontal(move[0][0],move[0][1],
					fields[move[0][0]][move[0][1]].getColor(),fields[move[0][0]][move[0][1]].getShape())) {
				return false;
			}
		} else if (move[1][0] == -1) { // maar 1 zet
			typeRow = 'z';
		}
		
		if (typeRow == 'x') {
			int i = 0;
			while (move[i][0] != -1) {
				if (!testHorizontal(move[i][0],move[i][1],
						fields[move[i][0]][move[i][1]].getColor(),fields[move[i][0]][move[i][1]].getShape())) {
					return false;
				}
				i++;
			}
			return true;
		} else if (typeRow == 'y') {
			int i = 0;
			while (move[i][0] != -1) {
				if (!testVertical(move[i][0],move[i][1],
						fields[move[i][0]][move[i][1]].getColor(),fields[move[i][0]][move[i][1]].getShape())) {
					return false;
				}
				i++;
			}
		} else if (typeRow == 'z') {
			Tile.Color color = fields[move[0][0]][move[0][1]].getColor();
			Tile.Shape shape = fields[move[0][0]][move[0][1]].getShape();
			return (testHorizontal(move[0][0],move[0][1],color,shape)&& testVertical(move[0][0],move[0][1],color,shape) && isConnected);
		}
		return false;

	}
		
	public int getScore() {
		return score;
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
						&& !(shapes.contains(fields[xValue+c][yValue].getShape())) // als kleur gelijk is, vorm mag niet gelijk
						&& typeOfRow != 'S') { 
					typeOfRow = 'C'; // rij van het type Color
					shapes.add(fields[xValue+c][yValue].getShape());
				} else if (fields[xValue+c][yValue].getShape().equals(shape) 
						&& !(colors.contains(fields[xValue+c][yValue].getColor()))
						&& typeOfRow != 'C') {
					typeOfRow = 'S';
					colors.add(fields[xValue+c][yValue].getColor());
				} else {
					return false;
				}
			}
			doorgaan = false; // stoppen met zoeken als de steen leeg is

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
					typeOfRow = 'C';
					shapes.add(fields[xValue-c][yValue].getShape());
				} else if (fields[xValue-c][yValue].getShape().equals(shape) 
						&& !(colors.contains(fields[xValue-c][yValue].getColor()))
						&& typeOfRow != 'C') {
					typeOfRow = 'S';
					colors.add(fields[xValue-c][yValue].getColor());
				} else {
					return false;
				}
			} else {
			doorgaan = false;
			}
		}
		if (c + easternTiles < 6 && c+easternTiles > 1) {
			score += c + easternTiles;
		} else if (c+easternTiles == 6) {
			score += 12;
		}
		
		if (typeRow == 'x' && c+easternTiles > 1) {
			isConnected = true;
		} else if (typeRow == 'y' && c+easternTiles > nrOfMoves) {
			isConnected = true;
		} else if (typeRow == 'z' && c+easternTiles > 1) {
			isConnected = true;
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
					shapes.add(fields[xValue][yValue + c].getShape());
				} else if (fields[xValue][yValue + c].getShape().equals(shape) 
						&& !(colors.contains(fields[xValue][yValue + c].getColor()))
						&& typeOfRow != 'C') {
					typeOfRow = 'S';
					colors.add(fields[xValue][yValue + c].getColor());
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
					typeOfRow = 'C';
					shapes.add(fields[xValue][yValue - c].getShape());
				} else if (fields[xValue][yValue - c].getShape().equals(shape) 
						&& !(colors.contains(fields[xValue][yValue - c].getColor()))
						&& typeOfRow != 'C') {
					typeOfRow = 'S';
					colors.add(fields[xValue][yValue - c].getColor());
				} else {
					return false;
				}
			} else {
			doorgaan = false;
			}
		}

		if (typeRow == 'y' && c + northernTiles > 1) {
			isConnected = true;
		} else if (typeRow == 'x' && c+northernTiles > nrOfMoves) {
			isConnected = true;
		} else if (typeRow == 'z' && c+northernTiles > 1) {
			isConnected = true;
		}
		
		if (c+northernTiles < 6 && c+northernTiles > 1) {
				score += c+northernTiles;
		} else if (c+northernTiles == 6) {
				score += 12;
		}
		return true;
	}
}
