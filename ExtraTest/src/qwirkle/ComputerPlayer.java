package qwirkle;

 
import java.util.ArrayList;

import online.ClientHandler;
import protocol.Protocol;

 /** ComputerPlayer that extends Player
  * Used by the server when a client wants to play against the computer
  * Used by the client when a client wants to play as computer
  * @author Niek Tijink & Thomas Kolner
  *
  */
public class ComputerPlayer extends Player {
	
	/** creates a new ComputerPlayer
	 * @param name the Name of the ComputerPlayer
	 */
	public ComputerPlayer(String name) {
		super(name);
	}
 
	/** Makes a first move for the computerplayer
	 * @param board the (empty) board of the game
	 * @return the move (as String)
	 */
	public String makeFirstMove(Board board) {
		FirstTurn ft = new FirstTurn(this);
		ft.makefirstTurn();
		return ft.getFirstMoveString();
	}

	/** the method that determines what a computerplayer will do in a move (except for the first move)
	 * It will first collect all possible places where a tile can be placed
	 * After that, the AI will try to place one Tile on every possible place, test the move and save the score
	 * After all places and all tiles have been tried, the method returns the move with the best score
	 * THIS AI ONLY PLACES ONE TILE AT A TIME
	 * @return the move (as String)
	 */
	public String determineMove(Board board) {
		long startTime = System.currentTimeMillis();
		initialiseMove();
		ArrayList<Coordinate> possibleMoves = getCoordinates(board);
		int bestScore = 0;
		String bestMove = "";
		while (System.currentTimeMillis() - startTime < 0.2 * Protocol.Settings.TIMEOUTSECONDS * 1000) {
			Board dc = board.deepcopy();
			for (Coordinate c : possibleMoves) {
				for (Tile t : getHand()) {
					if (t != null) {
						dc.setField(c.getX(), c.getY(), t);
						move[0][0] = c.getX();
						move[0][1] = c.getY();
						move[0][2] = getPlaceInHand(t);
						TestMove test = new TestMove(dc, move, getHand());
						if (test.isLegalMove()) {
							if (test.getScore() > bestScore) {
								bestScore = test.getScore();
								int x = c.getX() - protocol.Protocol.Settings.ORGINX ;
								int y = protocol.Protocol.Settings.ORGINY - c.getY();
								bestMove = Protocol.Client.MAKEMOVE + "_" + t.getColor().getCharColor()
										+ t.getShape().getCharShape() + "*" + x + "*" + y ;
							}
						} else {
							dc.getBoard()[c.getX()][c.getY()] = null;
						}
					}
				}
			}
		}
		if (!bestMove.equals(ClientHandler.NOREPLY) && !bestMove.equals(Protocol.Client.MAKEMOVE)) {
			String[] split = bestMove.split(Character.toString(Protocol.Settings.DELIMITER));
			String msg = Protocol.Client.MAKEMOVE;
			for (int i = 1; i < split.length; i++) {
				msg += Protocol.Settings.DELIMITER + split[i];
			}
			return msg;
		}

		String swap = Protocol.Client.CHANGESTONE;
		for (Tile t : getHand()) {
			swap += "_" + t.getColor().getCharColor() + t.getShape().getCharShape();
		}
		for (int i = 0; i < protocol.Protocol.Settings.NROFTILESINHAND; i++) {
			getHand()[i] = null;
		}
		return swap;
	}

	/** gets all possible coordinates where a tile might be placed
	 * this method works in squares from the origin. Square 0 is only one coordinate (75,75)
	 * square 2 contains 8 coordinates (76,76) ( 76,75) (76,74) (75,74) (75,76) (74,76) (74,75) (74,74)
	 * square X contains 8 * X squares
	 * When a coordinate is emty (no tile yet) and at least one of the surrounding tiles has a tile, it is selected
	 * When a complete square is empty, the search for new coordinate is stopped and the possible coordinates are returned
	 * When a complete square is empty, the count of possiblemoves is 8 * NROFSQUARE (i)
	 * @param board the current state of the board
	 * @return an arraylist with all the possible coordinates
	 */
	private ArrayList<Coordinate> getCoordinates(Board board) {
		int i = 0;
		ArrayList<Coordinate> possiblecoor = new ArrayList<Coordinate>();
		Tile[][] fields = board.getBoard();
		boolean doorgaan = true;

		while (doorgaan) {
			int count = 0;
			for (int k = 0; k <= (2 * i); k++) {
				if (fields[(protocol.Protocol.Settings.ORGINX - i) + k][protocol.Protocol.Settings.ORGINY + i] == null) {
					count++;
					if (movePossible(fields, (protocol.Protocol.Settings.ORGINX - i) + k, protocol.Protocol.Settings.ORGINY + i)) {
						possiblecoor.add(new Coordinate((protocol.Protocol.Settings.ORGINX - i) + k, protocol.Protocol.Settings.ORGINY + i));
					}
				}
				if (fields[(protocol.Protocol.Settings.ORGINX - i) + k][protocol.Protocol.Settings.ORGINY - i] == null) {
					count++;
					if (movePossible(fields, (protocol.Protocol.Settings.ORGINX - i) + k, protocol.Protocol.Settings.ORGINY - i)) {
						possiblecoor.add(new Coordinate((protocol.Protocol.Settings.ORGINX - i) + k, protocol.Protocol.Settings.ORGINY - i));
					}
				}
			}
			for (int k = 1; k < (2 * i); k++) { 
				if (fields[protocol.Protocol.Settings.ORGINX - i][(protocol.Protocol.Settings.ORGINY + i) - k] == null) {
					count++;
					if (movePossible(fields, protocol.Protocol.Settings.ORGINX - i, (protocol.Protocol.Settings.ORGINY + i) - k)) {
						possiblecoor.add(new Coordinate(protocol.Protocol.Settings.ORGINX - i, (protocol.Protocol.Settings.ORGINY + i) - k));
					}
				}
				if (fields[protocol.Protocol.Settings.ORGINX + i][(protocol.Protocol.Settings.ORGINY + i) - k] == null) {
					count++;
					if (movePossible(fields, protocol.Protocol.Settings.ORGINX + i, (protocol.Protocol.Settings.ORGINY + i) - k)) {
						possiblecoor.add(new Coordinate(protocol.Protocol.Settings.ORGINX + i, (protocol.Protocol.Settings.ORGINY + i) - k));
					}
				}
			}
			if (i > 0 && count == (i * 8)) {
				doorgaan = false;
			} else {
				i++;
			}
		}
		return possiblecoor;
	}

	/** checks whether one of the surrounding fields is filled with a tile
	 * @param fields current state of the board
	 * @param i x-value of the coordinate
	 * @param j y-value of the coordinate
	 * @return true if one (or more) of the coordinates is filled, false if they are all empty
	 */
	private boolean movePossible(Tile[][] fields, int i, int j) {
		return fields[i][j + 1] != null || fields[i][j - 1] != null || fields[i - 1][j] != null
				|| fields[i + 1][j] != null;
	}

	public static void main(String[] args) {
		long starttime = System.currentTimeMillis();
		ComputerPlayer cp = new ComputerPlayer("Niek");
		Deck deck = new Deck();
		try {
			cp.addToHand("AE_DA_DB_CC_CA_DF");
		} catch (Exception e) {
			System.exit(0);
		}
		cp.updateHand(deck);
		Board b = new Board();
		b.setField(50, 50, new Tile('A', 'A'));
		b.setField(50, 51, new Tile('A', 'B'));
		b.setField(50, 52, new Tile('A', 'D'));
		b.setField(50, 53, new Tile('A', 'C'));
		b.setField(50, 49, new Tile('A', 'F'));
		ArrayList<Coordinate> test = cp.getCoordinates(b);
		System.out.println("Mogelijke coordinaten");
		for (Coordinate c : test) {
			System.out.println("X-coordinaat: " + c.getX() + " Y-coordinaat: " + c.getY());
		}
		System.out.println(cp.determineMove(b));
		System.out.println(System.currentTimeMillis() - starttime);
	}

}
