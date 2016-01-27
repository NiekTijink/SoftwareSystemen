package qwirkle;

import java.util.ArrayList;

import protocol.Protocol;
/** a board saved as a grid
 * @author Niek Tijink & Thomas Kolner
 *
 */

public class Board {
	private static final int GAMESIZE = 5;
	private Tile[][] fields = new Tile[protocol.Protocol.Settings.ARRAYSIZE][protocol.Protocol.Settings.ARRAYSIZE];
	private int[] boundaries = new int[4];
	
	/** instantiates a new board with 150 * 150 fields
	 */
	public Board() {
		for (int i = 0; i < protocol.Protocol.Settings.ARRAYSIZE; i++) {
			for (int j = 0; j < protocol.Protocol.Settings.ARRAYSIZE; j++) {
				fields[i][j] = null;
			}
		}
	} 
	
	/** get the current fields of the board
	 * @return the int[][] of all tiles
	 */
	public Tile[][] getBoard() {
		return fields;
	}
	
	/** checks whether a field is within the arraysize
	 * @param xValue xvalue of the field
	 * @param yValue yvalue of the field
	 * @return true if it is a field, false otherwise
	 */
	public boolean isField(int xValue, int yValue) {
		return xValue >= 0 || xValue < protocol.Protocol.Settings.ARRAYSIZE || yValue >= 0 || yValue < protocol.Protocol.Settings.ARRAYSIZE;
	}
	
	/** gets a field
	 * @param xValue xvalue of the field
	 * @param yValue yvalue of the field
	 * @return the Tile at that field (or null if it empty)
	 */
	public Tile getField(int xValue, int yValue) {
		if (isField(xValue, yValue)) {
			return fields[xValue][yValue];
		} else {
			return null;
		}
	}
	
	/** checks whether a field is empty
	 * @param xValue xvalue of the field
	 * @param yValue yvalue of the field
	 * @return true if it is a empty, false otherwise
	 */
	public boolean isEmptyField(int xValue, int yValue) {
		return isField(xValue, yValue) && fields[xValue][yValue] == null;
	}
	 
	public void setMove(String msg) {
		String[] splitMsg = msg.split(Character.toString(Protocol.Settings.DELIMITER));
		for (int i = 0; i < splitMsg.length; i++) {
			String[] move = splitMsg[i].split("\\" + Protocol.Settings.DELIMITER2);
			Tile temp = new Tile(move[0].charAt(0), move[0].charAt(1));
			setField(protocol.Protocol.Settings.ORGINX + (Integer.parseInt(move[1])), protocol.Protocol.Settings.ORGINY - (Integer.parseInt(move[2])), temp);
		}
	}
	
	/** fills a field with a tile if the field is empty
	 * @param xValue xvalue of the field
	 * @param yValue yvalue of the field
	 * @param tile the tile to be placed
	 */
	public void setField(int xValue, int yValue, Tile tile) {
		if (isField(xValue, yValue) && isEmptyField(xValue, yValue)) {
			fields[xValue][yValue] = tile;
		}
		
	}
	
	/** constructs a new TestMove, tests the move and returns the score
	 * @param move the move of the player
	 * @param hand the hand of the player
	 * @return the score of the move (or -1 if it is illegal)
	 */
	public int testMove(int[][] move, Tile[] hand) {
		Board dc = deepcopy();
		TestMove test = new TestMove(dc, move, hand);
		if (test.isLegalMove()) {
			return test.getScore();
		}
		return -1;
	}
	
	/** copy's the current board
	 * @return the copy of the board
	 */
	public Board deepcopy() {
		Board deepcopy = new Board();
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				deepcopy.fields[i][j] = this.fields[i][j];
			}
		}
		return deepcopy;
	}
	
	/** gets the boundaries of the current board
	 * @param c the list with coordinates where a move is possible (see ComputerPlayer)
	 * @return the boundaries (0 = north, 1 = east, 2 = south, 3 = west)
	 */
	public int[] getBoundaries(ArrayList<Coordinate> c) { // not used in the current implementation
		boundaries[2] = protocol.Protocol.Settings.ARRAYSIZE;
    	boundaries[3] = protocol.Protocol.Settings.ARRAYSIZE;
		for (Coordinate temp : c) {
        	boundaries[0] = Math.max(boundaries[0], temp.getY());
            boundaries[1] = Math.max(boundaries[1], temp.getX());
            boundaries[2] = Math.min(boundaries[2], temp.getY());
            boundaries[3] = Math.min(boundaries[3], temp.getX());
        }
        return boundaries;
    }

	/** gives a string of the current situation of the board
	 * @return the string
	 */
	public String toString() {
		int[] bound = new int[4];
		bound[0] = 85;
		bound[1] = 85;
		bound[2] = 65;
		bound[3] = 65;
        String result = "";
        for (int y = bound[2] - GAMESIZE; y <= bound[0] + GAMESIZE; y++) {
            for (int x = bound[3] - GAMESIZE; x <= bound[1] + GAMESIZE; x++) {
                Tile tile = getField(x, y);
                result += tile == null ? padString("|" + x + y + "|") : padString(tile.toString());
            }
            result += "\n";
        }
        return result;
    }
	/** Method used in the toString method
	 * It is used so that the every string in the toString is 14 characters long
	 * @param str the current String
	 * @return the adapted string
	 */
	private String padString(String str) {
        for (int i = str.length(); i <= 14; i++)
            str += " ";
        return str;
    }
	
	

}