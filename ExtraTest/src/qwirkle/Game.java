package qwirkle;

import java.util.Scanner;

public class Game extends Thread {
	private Board board;
	private Deck deck;
	private Player[] players;
	private int moveNr;
	private String[] firstMove;
	private int[] firstMoveScores;
	
	// maakt een game aan met twee spelers, een bord en een deck.
	// moet later worden overgenomen door de server
	public Game(String[] names) {
		board = new Board();
		deck = new Deck();
		moveNr = 0;
		players = new Player[names.length];
		firstMove = new String[players.length];
		for (int i = 0; i < names.length; i++) { // als names.length = 1, dan ook een computerplayer aanmaken
			players[i] = new Player(names[i], deck);
		}
	}
	
	public String addToFirstMove(Player player, String msg) {
		int[][] move = new int[Player.HANDSIZE][3];
		String[] splitInput = msg.split(Character.toString(Protocol.Settings.DELIMITER));
		for (int i = 0; i < (splitInput.length / 4); i++) {
			Tile tile = new Tile(splitInput[1+3*i].charAt(0),splitInput[1+3*i].charAt(0));
		move[i][2] = player.getPlaceInHand(tile);
		move[i][0] = Integer.parseInt(splitInput[2+3*i]);
		move[i][1] = Integer.parseInt(splitInput[3+3*i]);
		}
		for (int i = 0; i < players.length; i++) {
			if (players[i].getName() == player.getName()) {
				firstMove[i] = msg;
				TestMove test = new TestMove(board,move,player.getHand());
				if (!(test.isLegalMove())) { //TODO for firstMove
					return Protocol.Server.ERROR + "_invalidmove";
				}
				firstMoveScores[i] = test.getScore(); // als deze score gelijk is aan -1 mag dit niet
				boolean full = true;
				for (int j = 0; j < players.length; j++) {
					if (firstMove[j] == null) {
						full = false;
					}
				}
				return full + "";
			}
		}
		return Protocol.Server.ERROR + "_invalidmove";
	}
	
	public String[] getFirstMove() {
		return firstMove;
	}
	public int[] getFirstMoveScores() {
		return firstMoveScores;
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
	
	public int getMoveNr() {
		return moveNr;
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
