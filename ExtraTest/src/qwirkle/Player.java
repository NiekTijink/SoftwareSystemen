package qwirkle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import online.ClientHandler;
import protocol.Protocol;

public abstract class Player {
    private String name;
    private Tile[] hand;
    private int move[][];
    public final static int HANDSIZE = 6;
    private int turnNr = 0;
    private int score;
       
    public Player(String name) {
    	this.name = name;
    	this.hand = new Tile[HANDSIZE];
    }
    
    public int getScore() {
    	return score;
    }
    
    public void addScore(int score) {
    	this.score += score;
    }

	public String getName() {
    	return name;
    }
	   
    public String updateHand(Deck deck) {
		String newStones = Protocol.Server.ADDTOHAND;
    	ArrayList<Tile> temp = new ArrayList<Tile>();
		for (int i = 0; i < HANDSIZE; i++) {
			if (hand[i] == null) {
				Tile tempTile = deck.drawTile();
				hand[i] = tempTile;
				temp.add(tempTile);
			}
		}
    	for (Tile t : temp) {
        	newStones += "_" + t.getColor().getCharColor() + t.getShape().getCharShape();
    	}
    	return newStones;
	}
    
    public Tile[] getHand() {
    	return hand;
    }
    
    public int getPlaceInHand(Tile tile) {
		for (int i = 0; i < HANDSIZE; i++) {
			if (!(hand[i] == null)) {
				if (hand[i].getShape() == tile.getShape() && hand[i].getColor() == tile.getColor()) {
					return i;
				}
			}
		}
		return -1;
	}
    
    
    // makemove geeft de beslissingen die je maakt door aan het bord (dmv setfield of swap)
    // later zal makemove deze beslissingen door moeten geven aan de server dmv protocol
 public abstract String makeMove(Board board, String msg);
 
 public String changeStones(String[] msg, Deck deck) {
	 String msgback = Protocol.Server.ADDTOHAND;
	 ArrayList<Tile> newTiles = new ArrayList<Tile>();
	 for (int i = 1; i < msg.length; i++) {
		 Tile temp = new Tile(msg[i].charAt(0),msg[i].charAt(1));
		 int placeInHand = getPlaceInHand(temp);
		 if (placeInHand<0) {
			 return ClientHandler.NOREPLY;
		 } else {
			 hand[placeInHand] = null;
			 Tile newTile = deck.swapTile(temp);
			 msgback += "_" + newTile.getColor().getCharColor() + newTile.getShape().getCharShape();
			 newTiles.add(newTile);
		 }
	 }
	 for (int i = 0; i < HANDSIZE; i++) {
			if (hand[i] == null) {
				hand [i] = newTiles.remove(0); // KAN DIT?
				Collections.shuffle(newTiles);
			}
	 }
	 return msgback;
	 
 }
    
    protected ArrayList<Coordinate> getCoordinates(Board board) {
       	final int MAXHALFARRAY = (50-2);
    	final int ARRAYORGINX = 50;
    	final int ARRAYORGINY = 50;
    	ArrayList<Coordinate> possiblecoor = new ArrayList<Coordinate>();
    	Tile[][] fields = board.getBoard();
	
    	for(int i = 0; i <= MAXHALFARRAY; i++) {
    		for(int k = 0; k <= (2*i); k++) {
    			if (fields[(ARRAYORGINX - i) + k][ARRAYORGINY + i] == null && movePossible(fields,(ARRAYORGINX - i) + k, ARRAYORGINY + i)) {
				possiblecoor.add(new Coordinate((ARRAYORGINX - i) + k, ARRAYORGINY + i));
    			}
    			if (fields[(ARRAYORGINX - i) + k][ARRAYORGINY - i] == null && movePossible(fields,(ARRAYORGINX - i) + k, ARRAYORGINY - i)) {
				possiblecoor.add(new Coordinate((ARRAYORGINX - i) + k, ARRAYORGINY - i));
    			}
		}
    		for (int k = 1; k < (2*i); k++) {
    			if (fields[ARRAYORGINX - i][(ARRAYORGINY + i) - k] == null && movePossible(fields,(ARRAYORGINX - i) , (ARRAYORGINY + i) - k)) {
				possiblecoor.add(new Coordinate((ARRAYORGINX - i) , (ARRAYORGINY + i) - k));
    			}
    			if (fields[ARRAYORGINX + i][(ARRAYORGINY + i) - k] == null && movePossible(fields,(ARRAYORGINX + i) , (ARRAYORGINY + i) - k)) {
				possiblecoor.add(new Coordinate((ARRAYORGINX + i) , (ARRAYORGINY + i) - k));
    			}
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

	public void addToHand(String tiles) {
		String[] splitTiles = tiles.split(Character.toString(Protocol.Settings.DELIMITER));
		int countEmptyTiles = 0;
		for (Tile t : hand) {
			if (t == null) {
				countEmptyTiles++;
			}
		}
		if (countEmptyTiles != splitTiles.length) {
			// SUPER GROTE ERROR (Want je krijgt meer/minder stenen dan je nodig bent. Server of Client is uit sync)
		}
		int j = 0;
		for (int i = 0; i < HANDSIZE; i++) {
			if (hand[i] == null) {
				hand[i] = new Tile(splitTiles[j].charAt(0),splitTiles[j].charAt(1));
				j++;
			}
		}
	}

	public String getHandString() {
		String answ = getName() + " : Maak een zet" + System.lineSeparator() + "Hand: ";
		for (Tile t : hand) {
			answ += t.toString() + ", ";
		}
		return answ.substring(0, answ.length()-2);
	}
	
	
	public String determineMove(Board board) {
		System.out.println("Geef je move: ");
			String antw = null;
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						System.in));
				antw = in.readLine();
			} catch (IOException e) {
			}

			return (antw == null) ? "" : antw;
		
	}
}
