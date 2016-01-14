package qwirkle;

import java.util.Scanner;

public class Game extends Thread {
	private Board board;
	private Deck deck;
	private Player[] players;
	
	// maakt een game aan met twee spelers, een bord en een deck.
	// moet later worden overgenomen door de server
	public Game(String[] names) {
		board = new Board();
		deck = new Deck();
		players = new Player[names.length];
		for (int i = 0; i < names.length; i++) {
			players[i] = new Player(names[i], deck);
		}
	}
	public Player[] getPlayers() {
		return players;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	// geen idee of dit goed is
	public void run() {
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
			players[moveNr % players.length].updateHand();
    		players[moveNr % players.length].makeMove(board, players[moveNr % 2].getHand());
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
		Game game = new Game(args);
		game.run();
	 }
}
