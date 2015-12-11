package ss.week5;

import java.util.ArrayList;

import ss.week4.tictactoe.Board;
import ss.week4.tictactoe.Mark;


public class SmartStrategy implements Strategy {
	public String name = "Smart";
	private int randomNr;
	private ArrayList<Integer> emptyfields ;
	private Board board;
	private Board deepcopy;

	private Mark m;
	
	public String getName() {
		return name;
	}

	
	public int determineMove(Board b, Mark m) {
		board = b;
		this.m = m;
		for (int i = 0; i < Board.DIM * Board.DIM; i++) {
			ArrayList<Integer> emptyfields = new ArrayList<Integer>();
			if (b.getField(i) == Mark.EMPTY) {
				emptyfields.add(i);
			}
		}
		if (b.getField(4) == Mark.EMPTY) { //Middelste vrij.
			return 4;
		} else if (directWin() < Board.DIM * Board.DIM){
			return directWin();
		} else if (blockWin() < Board.DIM * Board.DIM) {
			return blockWin();
		} else {
			randomNr = (int)(Math.random() * (emptyfields.size()));
			System.out.println(randomNr);
			System.out.println(emptyfields.get(randomNr));
			return emptyfields.get(randomNr);
		}
	}


	private int directWin() { 
		for (int i = 0; i <= emptyfields.size() - 1; i++) {
			deepcopy = board.deepCopy();
			deepcopy.setField(emptyfields.get(i), m);
			if (deepcopy.isWinner(m)) {
				return emptyfields.get(i);
			}
		}
		return Board.DIM * Board.DIM + 1;
	}

	private int blockWin() {
		for (int i = 0; i <= emptyfields.size() - 1; i++) {
			deepcopy = board.deepCopy();
			deepcopy.setField(emptyfields.get(i), m.other());
			if (deepcopy.isWinner(m.other())) {
				return emptyfields.get(i);
			}
		}
		return Board.DIM * Board.DIM + 1;
	}
		
}
