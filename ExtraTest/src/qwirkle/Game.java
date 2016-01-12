package qwirkle;

import java.util.Scanner;

public class Game {
	private Board board;
	private Deck deck;
	private Player[] players = new Player[2];
	
	// maakt een game aan met twee spelers, een bord en een deck.
	// moet later worden overgenomen door de server
	public Game(String name1, String name2) {
		board = new Board();
		deck = new Deck();
		Player player1 = new Player(name1, deck);
		Player player2 = new Player(name2,deck);
		players[0] = player1;
		players[1] = player2;
	}
	
	// geen idee of dit goed is
	public void start() {
		boolean doorgaan = true;
		while (doorgaan) {
			play();
			doorgaan = readBoolean("\n> Play another time? (y/n)?", "y", "n");
		}
	}
	
	// geen idee of dit goed is
	private void play() {
		int moveNr = 0;
		while(!(board.gameOver())) {
			players[moveNr % 2].updateHand();
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
	 
	public static void main(String[] args) {
		Game game = new Game("Thomas","Niek");
		game.start();
	 }
}
