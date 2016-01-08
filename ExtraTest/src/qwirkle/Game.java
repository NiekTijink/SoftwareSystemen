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
		// hier moet iets staan voor de eerste beurt (nu begint speler 1 gewoon)
		while(!(board.gameOver())) {
			updateHand(players[moveNr % 2]);
    		players[moveNr % 2].makeMove(board, players[moveNr % 2].getHand(), deck);
    		moveNr++;
		}
	}
	
	// Na elke hand moet de speler nieuwe stenen krijgen
	private void updateHand(Player player) { // WERKEND
		Tile[] hand = player.getHand();
		int nrOfNewTile = 0;
		for (int i = 0; i < player.NROFTILESINHAND; i++) { // telt het aantal lege stenen in hand
			if (hand[i] == null) {
				nrOfNewTile++;
			}
		}
		for (int i = 0; i < nrOfNewTile; i++) { // geeft de speler nieuwe stenen
			for (int j = 0; j < player.NROFTILESINHAND; i++) { 
				if (hand[j] == null) {
					hand[j] = deck.drawTile();
					System.out.println("Nieuwe Steen voor: " + player.getName() + 
							": " + hand[j].getColor() + 
							hand[j].getShape());
				}
			}
			
		}
		for (int i = 0; i < player.NROFTILESINHAND; i++) { // slaat nieuwe hand op
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
	
	public static void main(String[] args) {
		Player niek = new Player("Niek");
		Player thomas = new Player("Thomas");
			
		Game game = new Game(niek, thomas);
		game.start();
	}
}
