package ProjectQwirkle;

import java.util.ArrayList;

public abstract class Turn {
	
	public String name;
	public Board board;
	public Tile[] hand;
	public int choice = 0;
	public int[][] move;
	
	public static final int NROFTILESINHAND = 6;
	public static final int INDEXMOVE = 3;
	public static final int NUMBEROFSHAPESCOLORS = 12;

	public Turn(String name, Board board, Tile[] hand) {
		this.name = name;
		this.board = board;
		this.hand = hand;
		
		move = new int[NROFTILESINHAND][INDEXMOVE];
		for (int i = 0; i < 6; i++) { // initialise
			for (int j = 0; j < 3; j++) {
				move[i][j] = -1;
			}
		}
	}
	
	public abstract void determinemove(Board deepcopy, Tile[] hand, ArrayList<Coordinate> coordinates);
	
	public void makeTurn(Board board, Tile[] hand, ArrayList<Coordinate> coordinates) {
		Board deepcopy = board.deepcopy();
		determinemove(deepcopy, hand, coordinates);
		}
	
	public int[][] getMove() {
		return move;
	}
	
	public int testMove(int[][] move, Board b) {
		int score = b.testMove(move, hand);
		System.out.println("score: " + score);
		return score; //geeft de score van de geteste move terug (-1 als zet niet mag)
	}
	
	public int getPlaceInHand(Tile tile) {
		//System.out.println(tile.getShape() + " " + tile.getColor());
		for (int i = 0; i < NROFTILESINHAND; i++) {
			if (hand[i].getShape() == tile.getShape() && hand[i].getColor() == tile.getColor()) {
				return i;
			}
		}
		return -1;
	}

	public int getChoice() {
		return choice;
	}
}
