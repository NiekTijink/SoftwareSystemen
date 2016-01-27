package qwirkle;

import java.util.ArrayList;
 
/*** A class used to test a move and get the score of this move
 * @author Niek Tijink & Thomas Kolner
 * @version 1.3
 */ 
public class TestMove {
	private Tile[][] fields;
	private int[][] move;
	private int score; 
	private char typeRow;
	public int nrOfMoves;
	private boolean isConnected = false;
	private boolean isFirstMove = false;
	
	/** creates a new instance of TestMove
	 * The move is set on the board. This is necessary because it wouldn't be possible to test the move otherwise
	 * Whether a tile is legally placed or not depends on the the other tiles you place
	 * @param board The current state of the board
	 * @param move The move the player decided to make
	 * @param hand The hand of the player
	 */
	//@ requires board != null;
	//@ requires move[0][0] != -1;
	//@ ensures nrOfMoves >= 0 && nrOfMoves <= 6;
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
	
	/** The method that tests whether a move is legal
	 * It does so by first determining what kind of move it is (All the same x-value or y-value, or just a single tile (z))
	 * All stones need to be placed in one row or column
	 * After this is checked, the placed near the new tiles are checked, vertically and horizontally
	 * When all tiles share the same x-value (typeOfRow = 'x'),vertically will be checked once and horizontally for every tile.
	 * When all tile share the same y-value (typeOfRow = 'y'), horizontally will be checked once and vertically for every tile.
	 * When there is only one tile placed (typeOfRow = 'z'), both horizontally and vertically are done once.
	 * There is also another part in checking whether a move is legal: The tiles you place should be connected to tiles already on the board
	 * This is checked using the boolean "ISCONNECTED".
	 * However, when it is the first move, the tiles don't have to connect to other tiles (there are no other tiles)
	 * But one of the tiles needs to be placed at the origin (75,75)
	 * @return True when a move is legal, False when a move is illegal
	 */
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
	/** gives the score from the move
	 * @return the score (-1 if illegal)
	 */
	public int getScore() {
		return score;
	}
	
	/** tests the tile horizontal
	 * Done once if typeOfRow = 'y' or 'z', done multiple times if typeOfRow = 'x'
	 * The method will first look right and then left. It checks whether all tiles share the same color or shape
	 * If all tiles share the same color, the method saves all the shapes of that color in the row
	 * If a certain shape already is in the row, the move is illegal
	 * After checking the length of the row, it is decided of the tiles are connected to tiles already on the board
	 * @param xValue the xvalue where tile is placed
	 * @param yValue the yvalue where tile is placed
	 * @param color the color of the tile
	 * @param shape the shape of the tile
	 * @return true if tile is legal, false is illegal
	 */
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
	/** tests the tile vertical
	 * Done once if typeOfRow = 'x' or 'z', done multiple times if typeOfRow = 'y'
	 * The method will first look up and then down. It checks whether all tiles share the same color or shape
	 * If all tiles share the same color, the method saves all the shapes of that color in the row
	 * If a certain shape already is in the row, the move is illegal
	 * After checking the length of the row, it is decided of the tiles are connected to tiles already on the board
	 * @param xValue the xvalue where tile is placed
	 * @param yValue the yvalue where tile is placed
	 * @param color the color of the tile
	 * @param shape the shape of the tile
	 * @return true if tile is legal, false is illegal
	 */
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
