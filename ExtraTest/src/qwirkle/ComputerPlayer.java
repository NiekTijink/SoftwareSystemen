package qwirkle;

 
import java.util.ArrayList;

import online.ClientHandler;
import protocol.Protocol;

 
public class ComputerPlayer extends Player {
	
	public ComputerPlayer(String name) {
		super(name);
	}
 
	public String makeMove(Board board, String msg) {
		return "";
	} 

	public String makeFirstMove(Board board) {
		FirstTurn ft = new FirstTurn(this, board);
		ft.makefirstTurn();
		return ft.getFirstMoveString();
	}

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
