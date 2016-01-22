package qwirkle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import protocol.Protocol;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name) {
		super(name);
	}
	
	public String makeMove(Board board, String msg) {
		 return "";
	}

	
	public String makeFirstMove(Board board) {
		firstTurn ft = new firstTurn(this, board);
		ft.makefirstTurn();
		return ft.getFirstMoveString();
	}
	
	public String determineMove(Board board) {
		int[][] moves = new int[Player.HANDSIZE][3];
		initialiseMove(moves);
		Board dc = board.deepcopy();
		ArrayList<Coordinate> possibleMoves = getCoordinates(board);
		for (Coordinate c : possibleMoves) {
			for (Tile t: getHand()) {
				dc.setField(c.getX(), c.getY(), t);
				moves[0][0]=c.getX();
				moves[0][1]=c.getY();
				moves[0][2]=getPlaceInHand(t);
				TestMove test = new TestMove(dc,moves,getHand());
				if (test.isLegalMove()) {
					return Protocol.Client.MAKEMOVE + "_" + t.getColor().getCharColor() 
							+ t.getShape().getCharShape() + "*" + c.getX() + "*" + c.getY();
				} else {
					dc.getBoard()[c.getX()][c.getY()] = null;
				}
			}
		}
		String swap = Protocol.Client.CHANGESTONE;
		for (Tile t : getHand()) {
			swap += "_" + t.getColor().getCharColor() + t.getShape().getCharShape();
		}
		for (int i = 0; i < HANDSIZE; i++) {
			getHand()[i] = null;
		}
		return swap;
	}
	
    private ArrayList<Coordinate> getCoordinates(Board board) {
       	int i = 0;
    	final int ARRAYORGINX = 50;
    	final int ARRAYORGINY = 50;
    	ArrayList<Coordinate> possiblecoor = new ArrayList<Coordinate>();
    	Tile[][] fields = board.getBoard();
    	boolean doorgaan = true;
    	
    	while (doorgaan) {
    		int count = 0;
    		for(int k = 0; k <= (2*i); k++) {
    			if (fields[(ARRAYORGINX - i) + k][ARRAYORGINY + i] == null) {
    				count++;
    				if (movePossible(fields,(ARRAYORGINX - i) + k, ARRAYORGINY + i)) {
    					possiblecoor.add(new Coordinate((ARRAYORGINX - i) + k, ARRAYORGINY + i));
    				}
    			}
    			if (fields[(ARRAYORGINX - i) + k][ARRAYORGINY - i] == null) {
    				count++;
    				if (movePossible(fields,(ARRAYORGINX - i) + k, ARRAYORGINY - i)) {
    					possiblecoor.add(new Coordinate((ARRAYORGINX - i) + k, ARRAYORGINY - i));
    				}
    			}
    		}
    		for (int k = 1; k < (2*i); k++) {
    			if (fields[ARRAYORGINX - i][(ARRAYORGINY + i) - k] == null) {
    				count++;
    				if (movePossible(fields,(ARRAYORGINX - i) , (ARRAYORGINY + i) - k)) {
    					possiblecoor.add(new Coordinate((ARRAYORGINX - i) , (ARRAYORGINY + i) - k));
    				}
    			}
    			if (fields[ARRAYORGINX + i][(ARRAYORGINY + i) - k] == null) {
    				count++;
    				if (movePossible(fields,(ARRAYORGINX + i) , (ARRAYORGINY + i) - k)) {
        				possiblecoor.add(new Coordinate((ARRAYORGINX + i) , (ARRAYORGINY + i) - k));
    				}
    			}
			}
    		if (i > 0 && count == (i*8)) {
    			doorgaan = false;
    		} else {
    			i++;
    		}
		}
		return possiblecoor;
    }
    
    private boolean movePossible(Tile[][] fields, int i, int j) {
		if(fields[i][j+1] != null || fields[i][j-1] != null || fields[i-1][j] != null || fields[i+1][j] != null ) {
			return true;
		} else {
		return false;
		}
	}
    
    public static void main(String[] args) {
    	ComputerPlayer cp = new ComputerPlayer("Niek");
    	Board b = new Board();
    	b.setField(50, 50, new Tile('A','A'));
    	b.setField(50, 51, new Tile('A','B'));
    	b.setField(50, 52, new Tile('A','D'));
    	b.setField(50, 53, new Tile('A','C'));
    	b.setField(50, 49, new Tile('A','F'));
    	ArrayList<Coordinate> test = cp.getCoordinates(b);
    	System.out.println("Mogelijke coordinaten");
    	for (Coordinate c : test) {
    		System.out.println("X-coordinaat: " + c.getX() + " Y-coordinaat: " + c.getY());
    	}
    }

}
