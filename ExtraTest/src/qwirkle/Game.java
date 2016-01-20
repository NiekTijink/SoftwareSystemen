package qwirkle;

import java.util.Scanner;

import online.Server;
import protocol.Protocol;

public class Game extends Thread {
	private Board board;
	private Deck deck;
	private Player[] players;
	private Server server;
	private int moveNr;
	private String[] firstMove;
	private int[] firstMoveScores;
	public int startingPlayer;
	public int playersTurn;
	int[][] move = new int[Player.HANDSIZE][3];


	public Game(String[] names, Server server) {
		this.server = server;
		board = new Board();
		deck = new Deck();
		moveNr = 0;
		if (names.length == 1) { 
			players = new Player[2];
			players[1] = new ComputerPlayer(deck); 
		}
		players = new Player[names.length];
		firstMove = new String[players.length];
		firstMoveScores = new int[players.length];
		for (int i = 0; i < names.length; i++) { //
			players[i] = new HumanPlayer(names[i], deck);
		}
		
	}

	public String addToFirstMove(Player player, String msg) {
		initialiseMove(move);
		String[] splitInput = msg.split(Character.toString(Protocol.Settings.DELIMITER));
		for (int i = 0; i < (splitInput.length / 3); i++) {
			Tile tile = new Tile(splitInput[1 + 3 * i].charAt(0), splitInput[1 + 3 * i].charAt(0));
			move[i][2] = player.getPlaceInHand(tile);
			move[i][0] = Integer.parseInt(splitInput[2 + 3 * i]);
			move[i][1] = Integer.parseInt(splitInput[3 + 3 * i]);
		}
		for (int i = 0; i < players.length; i++) {
			if (players[i].getName() == player.getName()) {
				firstMove[i] = msg;
				int score = board.testMove(move, player.getHand());
				if (score < 0) { // TODO for firstMove
					return Protocol.Server.ERROR + "_invalidmove";
				}
				firstMoveScores[i] = score; // als deze score gelijk is aan -1
											// mag dit niet
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

	private void initialiseMove(int[][] move) {
		for (int i = 0; i < 6; i++) { // initialise
			for (int j = 0; j < 3; j++) {
				move[i][j] = -1;
			}
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
		for (int i = 0; i < players.length; i++) {
			Player p = players[i];
			if (p instanceof ComputerPlayer) {
				firstMove[i] = ((ComputerPlayer) p).makeFirstMove(board);
			}
		}
		if (firstMove[0] != null && firstMoveScores[0] > 0) {
			calcBestFirstMove();
		}

		while (!(board.gameOver())) {
			/* 	NIET LANGER NODIG GELOOF IK, DIT KAN DE SERVER (ICM EEN TIMER) DOEN

			players[(moveNr + startingPlayer) % players.length].makeMove(board);
			server.updateHand(players[(moveNr + startingPlayer) % players.length],
					players[(moveNr + startingPlayer) % players.length].updateHand());
			moveNr++;*/
		}
	}

	public void calcBestFirstMove() {
		int bestScore = -1;
		int bestPlayer = -1;
		for (int i = 0; i < firstMoveScores.length; i++) {
			if (firstMoveScores[i] > bestScore) {
				bestScore = firstMoveScores[i];
				bestPlayer = i;
			}
		}
		startingPlayer = bestPlayer;
		players[bestPlayer].makeMove(board,firstMove[bestPlayer]);
		playersTurn = (bestPlayer + 1) % players.length;
		server.makeMove(this, players[bestPlayer], players[playersTurn], firstMove[bestPlayer]);
		server.updateHand(players[bestPlayer], players[bestPlayer].updateHand());
		moveNr++;
	}

	private boolean readBoolean(String prompt, String yes, String no) {
		String answer;
		Scanner in = new Scanner(System.in);
		do {
			System.out.print(prompt);
			try /* (Scanner in = new Scanner(System.in)) */ {
				answer = in.hasNextLine() ? in.nextLine() : null;
			} finally {
			}
		} while (answer == null || (!answer.equals(yes) && !answer.equals(no)));
		return answer.equals(yes);
	}

	public static void main(String[] args) {

	}
}
