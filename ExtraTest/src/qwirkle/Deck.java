package qwirkle;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	//Aantal Tiles
	 public static final int SIZE = 108;

	   	//Lijst van Tiles
	    private ArrayList<Tile> tiles;
	    
	    //Create deck, elke tile 3 keer
	    public Deck() {
	        tiles = new ArrayList<>(SIZE);

	        for (Tile.Shape s : Tile.Shape.values()) {
	            for (Tile.Color c : Tile.Color.values()) {
	                for (int i = 0; i < 3; i++) {
	                    tiles.add(new Tile(s, c));
	                }
	            }
	        }
	    }
	    
	  //Aantal Tiles over in deck
	    public int Tilesremaining() {
	        return tiles.size();
	    }
	    
	    //Schud Deck
	    public void Shuffle() {
	    	Collections.shuffle(tiles);
	    }
	    
	    //Krijg de bovenste Tile en verwijder deze
	    public Tile drawTile() {
	    	Shuffle();
	    	Shuffle();
	    	return tiles.remove(0);
	    }
	    
	    //Wissel Tile.
	    public Tile[] changeTile(Tile[] change) {
	    	Tile[] temp = new Tile[change.length];
	    	for (int i = 0; i < temp.length; i++) {
	    		Shuffle();
	    		Shuffle();
	    		temp[i] = tiles.remove(0);
	    	}
	    	for (int i = 0; i < temp.length; i++) {
		    	tiles.add(tiles.size(), temp[i]);   	
	    	}
	    	return temp;
	    }
	    
	    public static void main(String[] args) {
	    	Deck d = new Deck();
	    	d.Shuffle();
	    	Tile tile = new Tile(Tile.Shape.CIRCLE, Tile.Color.BLUE);
	    	System.out.println(tile.getColor());
	    }

}