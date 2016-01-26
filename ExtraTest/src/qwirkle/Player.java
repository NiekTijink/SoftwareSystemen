package qwirkle;

import java.util.ArrayList;
import java.util.Collections;

import online.ClientHandler;
import protocol.Protocol;
import exception.*;
  
public abstract class Player {
	private String name;
	private Tile[] hand;
	public int[][] move;
	public final static int HANDSIZE = 6;
	private int score;

	public Player(String name) {
		this.name = name;
		this.hand = new Tile[HANDSIZE];
	}

	public int getScore() {
		return score;
	}

	public void addScore(int a) {
		this.score += a;
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
			if (!(t == null)) {
				newStones += "_" + t.getColor().getCharColor() + t.getShape().getCharShape();
			}
		}
		if (newStones.equals(Protocol.Server.ADDTOHAND)) {
			return newStones + "_notilesremaining";
		}
		return newStones;
	}

	public Tile[] getHand() {
		return hand;
	}

	public int getPlaceInHand(Tile tile) {
		for (int i = 0; i < HANDSIZE; i++) {
			if (!(hand[i] == null) && tile != null) {
				if (hand[i].getShape() == tile.getShape() && 
						  hand[i].getColor() == tile.getColor()) {
					return i;
				}
			}
		}
		return -1;
	}

	public void initialiseMove() {
		for (int i = 0; i < 6; i++) { // initialise
			for (int j = 0; j < 3; j++) {
				move[i][j] = -1;
			}
		}
	}

	// makemove geeft de beslissingen die je maakt door aan het bord (dmv
	// setfield of swap)
	// later zal makemove deze beslissingen door moeten geven aan de server dmv
	// protocol

	public String makeMove(Board board, String msg) {
		if (!(msg.equals(ClientHandler.NOREPLY))) {
			move = new int[HANDSIZE][3];
			initialiseMove();
			String[] split = msg.split(Character.toString(Protocol.Settings.DELIMITER));
			for (int i = 1; i < split.length; i++) {
				String[] splitInput = split[i].split("\\" 
			  + Character.toString(Protocol.Settings.DELIMITER2));
				Tile tile = new Tile(splitInput[0].charAt(0), splitInput[0].charAt(1));
				move[i - 1][2] = getPlaceInHand(tile);
				move[i - 1][0] = Integer.parseInt(splitInput[1]);
				move[i - 1][1] = Integer.parseInt(splitInput[2]);
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

	public abstract String determineMove(Board board);

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
		for (int i = 0; i < HANDSIZE; i++) {
			if (hand[i] == null) {
				hand[i] = newTiles.remove(0); // KAN DIT?
				Collections.shuffle(newTiles);
			}
		}
		return msgback;

	}

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
		for (int i = 0; i < HANDSIZE; i++) {
			if (hand[i] == null) {
				hand[i] = new Tile(splitTiles[j].charAt(0), splitTiles[j].charAt(1));
				j++;
			}
		}
	}

	public String getHandString() {
		String answ = getName() + " : Maak een zet" + System.lineSeparator() + "Hand: ";
		for (Tile t : hand) {
			if (t != null) {
				answ += t.toString() + ", ";
			}
		}
		return answ.substring(0, answ.length() - 2);
	}

	public void deleteTiles(String tiles) {
		String[] splitTiles = tiles.split(Character.toString(Protocol.Settings.DELIMITER));
		for (int i = 0; i < splitTiles.length; i++) {
			int index = getPlaceInHand(new Tile(splitTiles[i].charAt(0), splitTiles[i].charAt(1)));
			hand[index] = null;
		}
	}
}
