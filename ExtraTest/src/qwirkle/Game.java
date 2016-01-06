package qwirkle;

import java.util.Scanner;

public class Game {
	private Board board;
	private Player[] players;
	
	public Game(Player player1, Player player2) {
		board = new Board();
		players = new Player[2];
		players[0] = player1;
		players[1] = player2;
	}
	
	public void start() {
		boolean doorgaan = true;
		while (doorgaan) {
			play();
			doorgaan = readBoolean("\n> Play another time? (y/n)?", "y", "n");
		}
	}
	
	private void play() {
		int moveNr = 0;
		while(!(board.gameOver())) {
    		players[moveNr % 2].makeMove(board, players[moveNr % 2].getHand());
    		moveNr++;
		}
	}
	
	private boolean readBoolean(String prompt, String yes, String no) {
	        String answer;
	        Scanner in = new Scanner(System.in);
	        do {
	            System.out.print(prompt);
	            try /*(Scanner in = new Scanner(System.in))*/ {
	                answer = in.hasNextLine() ? in.nextLine() : null;
	            } finally { }
	        } while (answer == null || (!answer.equals(yes) && !answer.equals(no)));
	        return answer.equals(yes);
	    }
}
