package qwirkle;

import java.util.Scanner;

import online.Server;
import protocol.Protocol;
  
/**
* Game created from Server or Client
* @author Niek Tijink & Thomas Kolner
* @version 1.2
*/

public class Game extends Thread {
	private Board board;
	private Deck deck;
	private Player[] players;
	private Server server;
	public int moveNr;
	public String[] firstMove;
	public int[] firstMoveScores;
	public int startingPlayer;
	public int playersTurn;
	int[][] move = new int[protocol.Protocol.Settings.NROFTILESINHAND][protocol.Protocol.Settings.INDEXMOVE];

	/** Creates a new <code>Game</code> for the Client
	 * @param name name of the Client
	 * @param typeOfPlayer if (typeOfPlayer) there will be played as computerplayer
	 */
	//@ requires !(name.equals(""));
	//@ ensures getPlayers().length == 2;
	public Game(String name, Boolean typeOfPlayer) { 
		board = new Board();
		moveNr = 0;
		players = new Player[1];
		if (typeOfPlayer) {
			players[0] = new ComputerPlayer(name);
		} else {
			players[0] = new HumanPlayer(name);
		}
	} 
	/** Creates a new <code>Game</code> for the Server
	 * @param names Array with all the names from the clients
	 * @param server The <code>Server</code> the <code>Game</code> is played on
	 */
	//@ requires names.length >= 1;
	//@ requires server != null;
	//@ ensures getPlayers().length >= 2;
	public Game(String[] names, Server server) { 
		this.server = server;
		board = new Board();
		deck = new Deck();
		moveNr = 0;
		if (names.length == 1) {
			players = new Player[2];
			players[0] = new HumanPlayer(names[0]);
			players[1] = new ComputerPlayer("Computer");
			players[1].updateHand(deck);
		} else {
			players = new Player[names.length];
			for (int i = 0; i < names.length; i++) { //
				players[i] = new HumanPlayer(names[i]);
				players[i].updateHand(deck);
			}
		}
		firstMove = new String[players.length];
		firstMoveScores = new int[players.length];
	}
	
	/** Gets a move from the <code>ClientHandler</code> and tests it. 
	 * If the move is valid, the move is saved on the board.
	 * New Tiles are returned to 
	 * @param player the player that made the move
	 * @param msg the move itself
	 * @return new Tiles (in a String) if the method is valid or an Error
	 */
	//@ requires player != null;
	//@ requires !msg.equals("");
	//@ ensures \result.startsWith("ADDTOHAND") || \result.startsWith("ERROR");
	public String makeMove(Player player, String msg) {
		if (player.makeMove(board, msg).equals("true")) {
			return player.updateHand(deck);
		}
		return Protocol.Server.ERROR + "_invalidmove";
	}

	/** When a <code>ClientHandler</code> receives a firstmove from the client it is send here
	 * The move is tested and saved if it is valid 
	 * @param player The player that made the firstmove
	 * @param msg The firstmove
	 * @return Error if invalid move, true if everyone made their first move, false if not everyone made their first move.
	 */
	//@ requires player != null;
	//@ requires !msg.equals("");
	//@ ensures \result.startsWith("ERROR") || \result.equals("true") || \result.equals("false");
	public String addToFirstMove(Player player, String msg) {
		initialiseMove(); 
		String[] splitInput = msg.split(Character.toString(Protocol.Settings.DELIMITER));
		for (int i = 1; i < (splitInput.length); i++) {
			String s = splitInput[i];
			String[] splitMoves = s.split("\\*");
			Tile tile = new Tile(splitMoves[0].charAt(0), splitMoves[0].charAt(1));
			move[i - 1][2] = player.getPlaceInHand(tile);
			move[i - 1][0] = protocol.Protocol.Settings.ORGINX + (Integer.parseInt(splitMoves[1]));
			move[i - 1][1] = protocol.Protocol.Settings.ORGINY - (Integer.parseInt(splitMoves[2]));
		}
		for (int i = 0; i < players.length; i++) {
			if (players[i].getName() == player.getName()) {
				firstMove[i] = msg;
				int score = board.testMove(move, player.getHand());
				if (score < 0) { 
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

	/** initializes a move 
	 * After initialising it can be filled and tested
	 */
	//@ ensures \forall int i, j; 0 <= i && i <= 6 & 0<= j && j <= 3; move[i][j] == -1;
	private void initialiseMove() {
		for (int i = 0; i < 6; i++) { 
			for (int j = 0; j < 3; j++) {
				move[i][j] = -1;
			}
		}
	}

	/** get the playerobjects of all players in this game
	 * @return Player[] of all players
	 */
	//@ pure;
	public Player[] getPlayers() {
		return players;
	}

	/** get the <code>board</code> of the game
	 * @return the board
	 */
	//@ pure;
	public Board getBoard() {
		return board;
	}

	/** get the <code>deck</code> of the game
	 * @return the deck
	 */
	//@ pure;
	public Deck getDeck() {
		return deck;
	}

	/** get the moveNr of the game
	 * @return the moveNr
	 */
	//@ pure;
	public int getMoveNr() {
		return moveNr;
	}

	/** when a game is started, this method is called
	 */
	public void run() {
		boolean doorgaan = true;
		while (doorgaan) {
			play();
		}
	}

	/** after the game is started, this method is called
	 * It initializes the firstMove-objects
	 */
	//@ ensures firstMove.length == players.length;
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
	}
	
	/** after every move it is tested if the game is over
	 * THIS METHOD IS NOT CORRECTLY IMPLEMENTED YET
	 * @return True if the game is over, false if the game is not over
	 */
	//@ ensures \result == true || \result == false;
	public boolean gameOver() {
		if (getDeck().tilesRemaining() == 0) {
			return true;
		} else {
			for (Player p : players) {
				boolean empty = true;
				for (Tile t : p.getHand()) {
					if (t != null) {
						empty = false;
					}
				}
				if (empty) {
					return true;
				}
			}
		}
		return false;
	}

	/** After every player send their first move the best move is calculated
	 */
	//@ requires (\forall int i; 0 <= i && i < getPlayers().length; !(firstMove[i].equals("")));
	//@ ensures moveNr == \old(moveNr);
	//@ ensures \forall int j; 0 <= j && j < getPlayers().length; 0 < firstMoveScores[j];
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
		String newStones = makeMove(players[bestPlayer], firstMove[bestPlayer]);
		playersTurn = (bestPlayer + 1) % players.length;
		moveNr++;
		server.makeMove(this, players[bestPlayer], players[playersTurn], firstMove[bestPlayer]);
		server.updateHand(players[bestPlayer], newStones);
	}

}
