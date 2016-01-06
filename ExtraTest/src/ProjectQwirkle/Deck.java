package ProjectQwirkle;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	//Aantal Tiles
	 public static final int SIZE = 108;

	   	//Lijst van Tiles
	    private ArrayList<Tile> tiles;
	    
	    //Create deck, elke tile 3 keer
	    public Deck() {
	        this.tiles = new ArrayList<>(SIZE);

	        for (Tile.Shape s : Tile.Shape.values()) {
	            for (Tile.Color c : Tile.Color.values()) {
	                for (int i = 0; i < 3; i++) {
	                    this.tiles.add(new Tile(s, c));
	                }
	            }
	        }
	    }
	    //Schud Deck
	    public void Shuffle() {
	    	Collections.shuffle(this.tiles);
	    }
	    
	    //Krijg de bovenste Tile
	    public Tile drawTile() {
	    	return this.tiles.remove(0);
	    }
	    
	    
	    /*public static void main(String[] args) {
	    Deck d = new Deck();
	    System.out.println(d.tiles.get(107));
	    d.Shuffle();
	    d.drawTile();
	    d.drawTile();
	    System.out.println(d.tiles.size());
	    System.out.println(d.tiles.get(105));
	    
	    }*/
}
