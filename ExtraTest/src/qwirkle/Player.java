package qwirkle;

import java.util.ArrayList;
import java.util.Collections;

import online.ClientHandler;
import protocol.Protocol;
import exception.*;
  /*** Creating a player
   * @author Niek Tijink & Thomas Kolner
   * @version 1.1
   */ 
public abstract class Player {
	private String name;
	private Tile[] hand;
	public int[][] move;
	private int score;

	/** Creates a new <code>Player</code> with an empty hand 
	 * @param name The Name of the player
	 */
	//@ requires !name.equals("");
	public Player(String name) {
		this.name = name;
		this.hand = new Tile[protocol.Protocol.Settings.NROFTILESINHAND];
		move = new int[protocol.Protocol.Settings.NROFTILESINHAND][protocol.Protocol.Settings.INDEXMOVE];
	}
	/** returns the current score
	 * @return the score
	 */
	//@pure;
	public int getScore() {
		return score;
	}
	/** increases the score
	 * @param a the number which the score should be increased
	 */
	//@pure;
	public void addScore(int a) {
		this.score += a;
	}

	/** gives the name of the player
	 * @return the name
	 */
	//@pure;
	public String getName() {
		return name;
	}
	/** fills the hand of the player with new stones
	 * This method is used by the Server to get the stones
	 * @param deck Deck where the new stones come from
	 * @return a String containing all new Tiles (as character)
	 */

	//@ ensures \result.startsWith("ADDTOHAND");
	public String updateHand(Deck deck) {
		String newStones = Protocol.Server.ADDTOHAND;
		ArrayList<Tile> temp = new ArrayList<Tile>();
		for (int i = 0; i < protocol.Protocol.Settings.NROFTILESINHAND; i++) {
			if (hand[i] == null) {
				Tile tempTile = deck.drawTile();
				hand[i] = tempTile;
				temp.add(tempTile);
			}
		}
		for (Tile t : temp) {
			if (!(t == null)) {
				newStones += "_" + t.getColor().getCharColor() + t.getShape().getCharShape();
			}
		}
		if (newStones.equals(Protocol.Server.ADDTOHAND)) {
			return newStones + "_notilesremaining";
		}
		return newStones;
	}

	/** gives the current hand (an array of tiles) of the player
	 * @return the hand
	 */
	//@pure;
	public Tile[] getHand() {
		return hand;
	}

	/** get the place in your hand (0-5) of a tile
	 * @param tile The Tile you want to check
	 * @return the place in your hand (0-5) or -1 if the tile is not in your hand
	 */ 
	//@requires tile != null;
	//@ensures \exists int i; 0 <= i && i < protocol.Protocol.Settings.NROFTILESINHAND; getHand()[i].getColor() == tile.getColor() && getHand()[i].getShape() == tile.getShape();
	//@pure
	public int getPlaceInHand(Tile tile) {
		for (int i = 0; i < protocol.Protocol.Settings.NROFTILESINHAND; i++) {
			if (!(hand[i] == null) && tile != null) {
				if (hand[i].getShape() == tile.getShape() && 
						  hand[i].getColor() == tile.getColor()) {
					return i;
				}	

			}
		}
		return -1;
	}
	
	/** initializes a move 
	 * After initialising it can be filled and tested
	 */
	//@ ensures \forall int i, j; 0 <= i && i <= 6 & 0<= j && j <= 3; move[i][j] == -1;
	public void initialiseMove() {
		for (int i = 0; i < protocol.Protocol.Settings.NROFTILESINHAND; i++) { // initialise
			for (int j = 0; j < protocol.Protocol.Settings.INDEXMOVE; j++) {
				move[i][j] = -1;
			}
		}
	}

	/** Method used by the server to determine if a move is valid 
	 * if the move is valid, it is placed on the board
	 * @param board the current board
	 * @param msg the move (as a string)
	 * @return ERROR if the move invalid, "true" if the move is valid
	 */
	//@ requires board != null;
	//@ requires !msg.equals("");
	//@ ensures \result.startsWith("ERROR") || \result.equals("TRUE");
	public String makeMove(Board board, String msg) {
		if (!(msg.equals(ClientHandler.NOREPLY))) {
			move = new int[protocol.Protocol.Settings.NROFTILESINHAND][protocol.Protocol.Settings.INDEXMOVE];
			initialiseMove();
			String[] split = msg.split(Character.toString(Protocol.Settings.DELIMITER));
			for (int i = 1; i < split.length; i++) {
				String[] splitInput = split[i].split("\\" 
			  + Character.toString(Protocol.Settings.DELIMITER2));
				Tile tile = new Tile(splitInput[0].charAt(0), splitInput[0].charAt(1));
				move[i - 1][2] = getPlaceInHand(tile);
				move[i - 1][0] = protocol.Protocol.Settings.ORGINX + (Integer.parseInt(splitInput[1]));
				move[i - 1][1] = protocol.Protocol.Settings.ORGINY - (Integer.parseInt(splitInput[2]));
			}
			int tempscore = board.testMove(move, getHand());
			if (tempscore <= 0) {
				return Protocol.Server.ERROR + "invalidMove";
			}
			addScore(tempscore);
		} else {
			return ClientHandler.NOREPLY;
		}
		int i = 0;
		while (move[i][0] != -1) {
			board.setField(move[i][0], move[i][1], this.getHand()[move[i][2]]);
			getHand()[move[i][2]] = null;
			i++;
		}
		return true + "";
	}

	/** gets a new move from either ComputerPlayer or HumanPlayer
	 * @param board the current board
	 * @return the move (as string)
	 */
	//@ requires board != null;
	//@ensures \result.startsWith("MAKEMOVE") || \result.startsWith("CHANGESTONES");
	public abstract String determineMove(Board board);

	/** When a client decides to swap, this method is used by the server to change stones
	 * @param msg the new Tiles (as String)
	 * @param deck the deck where the new stones come from
	 * @return Nothing if the player doesnt have the tiles he wants to swap, else new Tiles (as string)
	 */
	//@ requires msg.length > 0;
	//@ requires deck != null;
	public String changeStones(String[] msg, Deck deck) {
		String msgback = Protocol.Server.ADDTOHAND;
		ArrayList<Tile> newTiles = new ArrayList<Tile>();
		for (int i = 1; i < msg.length; i++) {
			Tile temp = new Tile(msg[i].charAt(0), msg[i].charAt(1));
			int placeInHand = getPlaceInHand(temp);
			if (placeInHand < 0) {
				return ClientHandler.NOREPLY;
			} else {
				hand[placeInHand] = null;
				Tile newTile = deck.swapTile(temp);
				msgback += "_" + newTile.getColor().getCharColor() 
						+ newTile.getShape().getCharShape();
				newTiles.add(newTile);
			}
		}
		for (int i = 0; i < protocol.Protocol.Settings.NROFTILESINHAND; i++) {
			if (hand[i] == null) {
				hand[i] = newTiles.remove(0); 
				Collections.shuffle(newTiles);
			}
		}
		return msgback;

	}
	/** This method is used by the client to fill the hand with the new tiles he received
	 * @param tiles the new tiles
	 * @throws OutOfSyncException if the client gets more new tiles than he has empty spots in his hand
	 */
	//@ requires !tiles.equals("");
	public void addToHand(String tiles) throws OutOfSyncException {
		String[] splitTiles = tiles.split(Character.toString(Protocol.Settings.DELIMITER));
		int countEmptyTiles = 0;
		for (Tile t : hand) {
			if (t == null) {
				countEmptyTiles++;
			}
		}
		if (countEmptyTiles < splitTiles.length) {
			throw new OutOfSyncException();
		}
		int j = 0;
		for (int i = 0; i < protocol.Protocol.Settings.NROFTILESINHAND; i++) {
			if (hand[i] == null) {
				hand[i] = new Tile(splitTiles[j].charAt(0), splitTiles[j].charAt(1));
				j++;
			}
		}
	}

	/** translates the hand to a string to print on the TUI
	 * @return the hand (as String)
	 */
	public String getHandString() {
		String answ = getName() + " : Maak een zet" + System.lineSeparator() + "Hand: ";
		for (Tile t : hand) {
			if (t != null) {
				answ += t.toString() + ", ";
			}
		}
		return answ.substring(0, answ.length() - 2);
	}
	/** Used by the client to delete tiles from his hand
	 * This is necessary because the client does not delete his tiles when he sends his move to the server
	 * He deletes the tiles when the server says the move is valid
	 * @param tiles the tiles to be deleted
	 */
	//@ requires !tiles.equals("");
	public void deleteTiles(String tiles) {
		String[] splitTiles = tiles.split(Character.toString(Protocol.Settings.DELIMITER));
		for (int i = 0; i < splitTiles.length; i++) {
			int index = getPlaceInHand(new Tile(splitTiles[i].charAt(0), splitTiles[i].charAt(1)));
			hand[index] = null;
		}
	}
}
