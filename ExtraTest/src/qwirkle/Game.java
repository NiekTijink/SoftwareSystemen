package qwirkle;

import java.util.Scanner;

public class Game {
	private Board board;
	private Player[] players;
	private Deck deck;
	
	// maakt een game aan met twee spelers.
	// moet later worden overgenomen door de server
	public Game(Player player1, Player player2) {
		board = new Board();
		deck = new Deck();
		players = new Player[2];
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
			updateHand(players[moveNr % 2]);
    		players[moveNr % 2].makeMove(board, players[moveNr % 2].getHand());
    		moveNr++;
		}
	}
	
	// Na elke hand moet de speler nieuwe stenen krijgen
	// Je moet ervoor zorgen dat altijd de laatste X plekken in de hand leeg zijn
	// Dus stel je wilt 2 nieuwe stenen, dan moeten die op plek 4 en 5 komen 
	// Dus bij plaatsten van stenen (determinemove oid) moeten de stenen al in deze volgorde gelegd worden
	private void updateHand(Player player) {
		Tile[] hand = player.getHand();
		int nrOfNewTile = 0;
		for (int i = 0; i < player.NROFTILESINHAND; i++) { // telt het aantal lege stenen in hand
			if (hand[i] == null) {
				nrOfNewTile++;
			}
		}
		for (int i = 0; i < nrOfNewTile; i++) { // geeft de speler nieuwe stenen
			deck.Shuffle();
			deck.Shuffle();
			hand[player.NROFTILESINHAND - 1 - i] = deck.drawTile(); 
		}
		for (int i = 0; i < player.NROFTILESINHAND; i++) {
			player.getHand()[i] = hand[i];
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
