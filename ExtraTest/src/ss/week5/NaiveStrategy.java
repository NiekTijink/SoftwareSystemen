package ss.week5;

import java.lang.Math;
import ss.week4.tictactoe.*;
import java.util.*;

public class NaiveStrategy implements Strategy {
	public String name = "Naive";
	
	public NaiveStrategy() {
		
	}
	
	public String getName() {
		return name;
	}
	
	public int determineMove(Board b, Mark m) {
		ArrayList<Integer> emptyfields = new ArrayList<Integer>();
		for (int i = 0; i < Board.DIM * Board.DIM; i++) {
			if (b.getField(i) == Mark.EMPTY) {
				emptyfields.add(i);
			}
		}
		int randomNr;
		randomNr = (int)(Math.random() * ((emptyfields.size()) + 1));
		return emptyfields.get(randomNr);
	}
	
	
	
}
