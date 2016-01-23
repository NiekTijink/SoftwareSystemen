package qwirkle;

import java.util.ArrayList;

import protocol.Protocol;


public class Board {
	private Tile[][] fields = new Tile[MAXSIZE][MAXSIZE];
	private int[] boundaries = new int[4];
	private static final int MAXSIZE = 180;
	private static final int GAMESIZE = 5;

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
	
	public void setMove(String msg) {
		String[] splitMsg = msg.split(Character.toString(Protocol.Settings.DELIMITER));
		for (int i = 0; i < splitMsg.length; i++) {
			String[] move = splitMsg[i].split("\\" + Protocol.Settings.DELIMITER2);
			Tile temp = new Tile(move[0].charAt(0),move[0].charAt(1));
			setField(Integer.parseInt(move[1]),Integer.parseInt(move[2]),temp);
		}
	}
	
	public void setField(int xValue, int yValue, Tile tile) {
		if (isField(xValue, yValue) && isEmptyField(xValue, yValue)) {
			fields[xValue][yValue] = tile;
		}
		
	}
	
	public int testMove(int[][] move, Tile[] hand) {
		Board dc = deepcopy();
		TestMove test = new TestMove(dc, move, hand);
		if (test.isLegalMove()) {
			return test.getScore();
		}
		return -1;
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
		boundaries[2] = MAXSIZE;
    	boundaries[3] = MAXSIZE;
		for(Coordinate temp : c){
        	boundaries[0] = Math.max(boundaries[0], temp.getY());
            boundaries[1] = Math.max(boundaries[1], temp.getX());
            boundaries[2] = Math.min(boundaries[2], temp.getY());
            boundaries[3] = Math.min(boundaries[3], temp.getX());
        }
        return boundaries;
     }

	public String toString() {
		int [] bound = new int[4];
		bound[0] = 65;
		bound[1] = 65;
		bound[2] = 35;
		bound[3] = 35;
        String result = "";
        for (int y = bound[2] - GAMESIZE; y <= bound[0] + GAMESIZE; y++) {
            for (int x = bound[3] - GAMESIZE; x <= bound[1] + GAMESIZE; x++) {
                Tile tile = getField(x, y);
                result += tile == null ? "|"+x+","+y+"|" : tile.toString() + " ";
            }
            result += "\n";
        }
        return result;
    }
	

}