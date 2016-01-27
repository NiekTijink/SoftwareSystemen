package qwirkle;

import java.util.ArrayList;
 
public class TestMove {
	private Tile[][] fields;
	private int[][] move;
	private int score; 
	private char typeRow;
	private int nrOfMoves;
	private boolean isConnected = false;
	private boolean isFirstMove = false;
	
	public TestMove(Board board, int[][] move, Tile[] hand) {
		Board b = board;
		fields = board.getBoard();
		this.move = move;
		isFirstMove = fields[protocol.Protocol.Settings.ORGINX][protocol.Protocol.Settings.ORGINY] == null;
		int i = 0;
		while (move[i][0] != -1 && i < protocol.Protocol.Settings.NROFTILESINHAND) {
			nrOfMoves++;
			b.setField(move[i][0], move[i][1], hand[move[i][2]]);
			i++;
		}
	}
	
	public boolean isLegalMove() { // mag maar in rij/kolom & moet andere stenen raken
		boolean legalMove = false;
		if (move[0][0] == -1) {
			legalMove = false;
		}
		if (move[0][0] == move[1][0]) { // moves met zelfde x-waarde
			int i = 2;
			while (move[i][0] != -1) {
				if (move[i][0] != move[0][0]) {
					legalMove = false; // niet allemaal zelfde x-waarde
				}
				i++;
			}
			typeRow = 'x';
			if (testVertical(move[0][0], move[0][1], fields[move[0][0]][move[0][1]].getColor(),  
					  fields[move[0][0]][move[0][1]].getShape())) {
				legalMove = true;
			}
		} else if (move[0][1] == move[1][1]) { // moves met zelfde y-waarde
			int i = 2;
			while (move[i][0] != -1) {
				if (move[i][1] != move[0][1]) {
					legalMove = false; // niet allemaal zelfde y-waarde
				}
				i++;
			}
			typeRow = 'y';
			if (testHorizontal(move[0][0], move[0][1], fields[move[0][0]][move[0][1]].getColor(), 
					  fields[move[0][0]][move[0][1]].getShape())) {
				legalMove = true;
			}
		} else if (move[1][0] == -1) { // maar 1 zet
			typeRow = 'z';
		}
		
		if (typeRow == 'x') {
			int i = 0;
			while (move[i][0] != -1) {
				if (!testHorizontal(move[i][0], move[i][1], 
						  fields[move[i][0]][move[i][1]].getColor(), 
						  fields[move[i][0]][move[i][1]].getShape()) && !legalMove) {
					legalMove = false;
				}
				i++;
			}
		} else if (typeRow == 'y') {
			int i = 0;
			while (move[i][0] != -1) {
				if (!testVertical(move[i][0], move[i][1], fields[move[i][0]][move[i][1]].getColor(),
						  fields[move[i][0]][move[i][1]].getShape()) && !legalMove) {
					legalMove = false;
				}
				i++;
			}
		} else if (typeRow == 'z') {
			if (isFirstMove) { 
				score = 1; 
			}
			Tile.Color color = fields[move[0][0]][move[0][1]].getColor();
			Tile.Shape shape = fields[move[0][0]][move[0][1]].getShape();
			legalMove = testHorizontal(move[0][0], move[0][1], color, shape) && 
						  testVertical(move[0][0], move[0][1], color, shape) && isConnected;
		}
		if (!isConnected) {
			legalMove = false;
		}
		return legalMove;

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
			if (!(fields[xValue + c][yValue] == null)) { // kijken of vakje leeg is
				if (fields[xValue + c][yValue].getColor().equals(color) // kijken of kleur gelijk is
						  && !(shapes.contains(fields[xValue + c][yValue].getShape())) 
						  && typeOfRow != 'S') { 
					typeOfRow = 'C'; // rij van het type Color
					shapes.add(fields[xValue + c][yValue].getShape());
				} else if (fields[xValue + c][yValue].getShape().equals(shape) 
						  && !(colors.contains(fields[xValue + c][yValue].getColor()))
						  && typeOfRow != 'C') {
					typeOfRow = 'S';
					colors.add(fields[xValue + c][yValue].getColor());
				} else {
					return false;
				}
			} else {
				doorgaan = false; // stoppen met zoeken als de steen leeg is
			}
		}
		easternTiles = c - 1;
		c = 0; //counter reset
		doorgaan = true;
		while (doorgaan) { //omlaag
			c++;
			if (!(fields[xValue - c][yValue] == null)) {
				if (fields[xValue - c][yValue].getColor().equals(color) // kijken of kleur gelijk is
						  && !(shapes.contains(fields[xValue - c][yValue].getShape())) 
						  && typeOfRow != 'S') {
					typeOfRow = 'C';
					shapes.add(fields[xValue - c][yValue].getShape());
				} else if (fields[xValue - c][yValue].getShape().equals(shape) 
						  && !(colors.contains(fields[xValue - c][yValue].getColor()))
						  && typeOfRow != 'C') {
					typeOfRow = 'S';
					colors.add(fields[xValue - c][yValue].getColor());
				} else {
					return false;
				}
			} else {
				doorgaan = false;
			}
		}
		if (c + easternTiles < 6 && c + easternTiles > 1) {
			score += c + easternTiles;
		} else if (c + easternTiles == 6) {
			score += 12;
		}
		
		if (typeRow == 'x' && c + easternTiles > 1) {
			isConnected = true;
		} else if (typeRow == 'y' && c + easternTiles > nrOfMoves) {
			isConnected = true;
		} else if (typeRow == 'z' && c + easternTiles > 1) {
			isConnected = true;
		} else if (isFirstMove && fields[protocol.Protocol.Settings.ORGINX][protocol.Protocol.Settings.ORGINY] != null && typeRow == 'y' && c + easternTiles > 1) {
			isConnected = true;
		} else if (isFirstMove && fields[protocol.Protocol.Settings.ORGINX][protocol.Protocol.Settings.ORGINY] != null && typeRow == 'z') {
			isConnected = true;
		} else if (isFirstMove && fields[50][50] != null) {
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
						  && !(shapes.contains(fields[xValue][yValue + c].getShape())) 
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
						  && !(shapes.contains(fields[xValue][yValue - c].getShape())) 
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
		} else if (typeRow == 'x' && c + northernTiles > nrOfMoves) {
			isConnected = true;
		} else if (typeRow == 'z' && c + northernTiles > 1) {
			isConnected = true;
		} else if (isFirstMove && fields[protocol.Protocol.Settings.ORGINX][protocol.Protocol.Settings.ORGINY] != null && typeRow == 'x' && c + northernTiles > 1) {
			isConnected = true;
		} else if (isFirstMove && fields[protocol.Protocol.Settings.ORGINX][protocol.Protocol.Settings.ORGINY] != null && typeRow == 'z') {
			isConnected = true;
		}
		
		if (c + northernTiles < 6 && c + northernTiles > 1) {
			score += c + northernTiles;
		} else if (c + northernTiles == 6) {
			score += 12;
		}
		return true;
	}
}
