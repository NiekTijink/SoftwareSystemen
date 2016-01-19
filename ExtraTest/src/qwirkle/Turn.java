package qwirkle;

import java.util.ArrayList;

public abstract class Turn {
	
	public Player player;
	public Board board;
	public int choice = 0;
	public int[][] move;
	
	public static final int NROFTILESINHAND = 6;
	public static final int INDEXMOVE = 3;
	public static final int NUMBEROFSHAPESCOLORS = 12;

	public Turn(Player player, Board board) {
		this.player = player;
		this.board = board;
		
		move = new int[NROFTILESINHAND][INDEXMOVE];
		for (int i = 0; i < 6; i++) { // initialise
			for (int j = 0; j < 3; j++) {
				move[i][j] = -1;
			}
		}
	}
	
	public abstract void determinemove(Board deepcopy, ArrayList<Coordinate> coordinates);
	
	public void makeTurn(Board board, ArrayList<Coordinate> coordinates) {
		Board deepcopy = board.deepcopy();
		determinemove(deepcopy, coordinates);
		}
	
	public int[][] getMove() {
		return move;
	}
	
	public int getChoice() {
		return choice;
	}
	
	public int testMove(int[][] move, Board b) {
		int score = b.testMove(move, player.getHand());
		//System.out.println("score: " + score);
		return score; //geeft de score van de geteste move terug (-1 als zet niet mag)
	}
	
}
