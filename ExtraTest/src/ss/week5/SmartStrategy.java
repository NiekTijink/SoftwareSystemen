package ss.week5;

import java.util.ArrayList;

import ss.week4.tictactoe.Board;
import ss.week4.tictactoe.Mark;


public class SmartStrategy implements Strategy {
	public String name = "Smart";
	private int randomNr;
	private ArrayList<Integer> emptyfields = new ArrayList<Integer>();
	private Board board;
	private Board deepcopy;
	private Mark m;

	public String getName() {
		return name;
	}

	
	public int determineMove(Board b, Mark m) {
		emptyfields.clear();
		board = b;
		this.m = m;
		for (int i = 0; i < Board.DIM * Board.DIM; i++) {
			if (board.getField(i) == Mark.EMPTY) {
				emptyfields.add(i);
			}
		}
		if (board.getField(4) == Mark.EMPTY) { 
			return 4;
		} else if (directWin(board, m) < Board.DIM * Board.DIM){
			return directWin(board, m);
		} else if (blockWin(board, m) < Board.DIM * Board.DIM) {
			return blockWin(board, m);
		} else {
			randomNr = (int)(Math.random() * (emptyfields.size()));
			return emptyfields.get(randomNr);
		}
	}


	private int directWin(Board b, Mark m) { 
		for (int i = 0; i <= emptyfields.size() - 1; i++) {
			deepcopy = board.deepCopy();
			deepcopy.setField(emptyfields.get(i), m);

			if (deepcopy.isWinner(m)) {
				return emptyfields.get(i);
			}
			
		}
		return Board.DIM * Board.DIM;
	}

	private int blockWin(Board b, Mark m) {
		for (int i = 0; i <= emptyfields.size() - 1; i++) {
			deepcopy = board.deepCopy();
			deepcopy.setField(emptyfields.get(i), m.other());
			if (deepcopy.isWinner(m.other())) {
				return emptyfields.get(i);
			}
		}
		return Board.DIM * Board.DIM;
	}
		
}
