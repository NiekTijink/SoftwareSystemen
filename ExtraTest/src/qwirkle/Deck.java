package qwirkle;

import java.util.ArrayList;
import java.util.Collections;

import protocol.Protocol;
 /** A deck where all tiles are stored
  * @author Niek Tijink
  *
  */
public class Deck {
	public static final int SIZE = 108;
	private ArrayList<Tile> tiles;

	/** Creates a deck with 108 Tiles
	 * Every Tile 3 times
	 */
	public Deck() {
		tiles = new ArrayList<Tile>(SIZE);
		for (Tile.Shape s : Tile.Shape.values()) {
			for (Tile.Color c : Tile.Color.values()) {
				for (int i = 0; i < 3; i++) {
					tiles.add(new Tile(c, s));
				}
			}
		}
	}

	/** Get the number of tiles left in the deck
	 * @return the number of tiles
	 */
	public int tilesRemaining() {
		return tiles.size();
	}

	/** shuffles the deck
	 */
	private void shuffle() {
		Collections.shuffle(tiles);
	}

	/** Draw an entire hand from the deck
	 * @return the new hand
	 */
	public Tile[] drawHand() {
		shuffle();
		Tile[] hand = new Tile[6];
		for (int i = 0; i < 6; i++) {
			hand[i] = drawTile();
		}
		return hand;
	}

	/** Gets one tile from the deck
	 * @return the Tile
	 */
	public Tile drawTile() {
		if (tilesRemaining() == 0) {
			return null;
		}
		shuffle();
		return tiles.remove(0);
	}

	/** Swaps one tile from the deck
	 * @param tile The Tile you want to swap
	 * @return The new Tile
	 */
	public Tile swapTile(Tile tile) {
		shuffle();
		tiles.add(tiles.size(), tile);
		return tiles.remove(0);
	}

	/** Gives the number of stones in the deck (as String)
	 * @return the number of stones in deck
	 */
	public String getStonesInBag() {
		return Protocol.Server.STONESINBAG + "_" + tilesRemaining();
	}

}