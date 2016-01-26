package qwirkle;

import java.util.ArrayList;

import protocol.Protocol;
 
public class FirstTurn {
	private Board board;
	private int[][] move = new int[6][3];
	private Player player;
	private static final int NUMBEROFSHAPESCOLORS = 12;


	public FirstTurn(Player player, Board board) {
		this.player = player;
		this.board = board;

		for (int i = 0; i < protocol.Protocol.Settings.NROFTILESINHAND; i++) { 
			for (int j = 0; j < protocol.Protocol.Settings.INDEXMOVE; j++) {
				move[i][j] = -1;
			}
		}
	}

	public void makefirstTurn() {
		Board deepcopy = board.deepcopy();
		determinefirstmove(deepcopy, player.getHand());
	}

	// Bepaal eerste move
	private void determinefirstmove(Board deepcopy, Tile[] hand) {
		// Kleur combinatie
		ArrayList<Tile.Shape> blueshape = new ArrayList<Tile.Shape>();
		ArrayList<Tile> blue = new ArrayList<Tile>();
		ArrayList<Tile.Shape> cyanshape = new ArrayList<Tile.Shape>();
		ArrayList<Tile> cyan = new ArrayList<Tile>();
		ArrayList<Tile.Shape> greenshape = new ArrayList<Tile.Shape>();
		ArrayList<Tile> green = new ArrayList<Tile>();
		ArrayList<Tile.Shape> magnetashape = new ArrayList<Tile.Shape>();
		ArrayList<Tile> magneta = new ArrayList<Tile>();
		ArrayList<Tile.Shape> redshape = new ArrayList<Tile.Shape>();
		ArrayList<Tile> red = new ArrayList<Tile>();
		ArrayList<Tile.Shape> yellowshape = new ArrayList<Tile.Shape>();
		ArrayList<Tile> yellow = new ArrayList<Tile>();
		int i = 0;
		while (i < 6) {
			if (hand[i].getColor() == Tile.Color.BLUE && !blueshape.contains(hand[i].getShape())) {
				blueshape.add(hand[i].getShape());
				blue.add(hand[i]);
			} else if (hand[i].getColor() == Tile.Color.ORANGE && 
					  !cyanshape.contains(hand[i].getShape())) {
				cyanshape.add(hand[i].getShape());
				cyan.add(hand[i]);
			} else if (hand[i].getColor() == Tile.Color.GREEN && 
					  !greenshape.contains(hand[i].getShape())) {
				greenshape.add(hand[i].getShape());
				green.add(hand[i]);
			} else if (hand[i].getColor() == Tile.Color.PURPLE && 
					  !magnetashape.contains(hand[i].getShape())) {
				magnetashape.add(hand[i].getShape());
				magneta.add(hand[i]);
			} else if (hand[i].getColor() == Tile.Color.RED && 
					  !redshape.contains(hand[i].getShape())) {
				redshape.add(hand[i].getShape());
				red.add(hand[i]);
			} else if (hand[i].getColor() == Tile.Color.YELLOW && 
					  !yellowshape.contains(hand[i].getShape())) {
				yellowshape.add(hand[i].getShape());
				yellow.add(hand[i]);
			}
			i++;
		}
		// Shape combinatie
		ArrayList<Tile.Color> circlecolor = new ArrayList<Tile.Color>();
		ArrayList<Tile> circle = new ArrayList<Tile>();
		ArrayList<Tile.Color> clubcolor = new ArrayList<Tile.Color>();
		ArrayList<Tile> club = new ArrayList<Tile>();
		ArrayList<Tile.Color> crosscolor = new ArrayList<Tile.Color>();
		ArrayList<Tile> cross = new ArrayList<Tile>();
		ArrayList<Tile.Color> diamondcolor = new ArrayList<Tile.Color>();
		ArrayList<Tile> diamond = new ArrayList<Tile>();
		ArrayList<Tile.Color> squarecolor = new ArrayList<Tile.Color>();
		ArrayList<Tile> square = new ArrayList<Tile>();
		ArrayList<Tile.Color> starburstcolor = new ArrayList<Tile.Color>();
		ArrayList<Tile> starburst = new ArrayList<Tile>();

		int j = 0;
		while (j < 6) {
			if (hand[j].getShape() == Tile.Shape.CIRCLE && 
					  !circlecolor.contains(hand[j].getColor())) {
				circlecolor.add(hand[j].getColor());
				circle.add(hand[j]);
			} else if (hand[j].getShape() == Tile.Shape.PLUS && 
					  !clubcolor.contains(hand[j].getColor())) {
				clubcolor.add(hand[j].getColor());
				club.add(hand[j]);
			} else if (hand[j].getShape() == Tile.Shape.CROSS && 
					  !crosscolor.contains(hand[j].getColor())) {
				crosscolor.add(hand[j].getColor());
				cross.add(hand[j]);
			} else if (hand[j].getShape() == Tile.Shape.DIAMOND && 
					  !diamondcolor.contains(hand[j].getColor())) {
				diamondcolor.add(hand[j].getColor());
				diamond.add(hand[j]);
			} else if (hand[j].getShape() == Tile.Shape.SQUARE && 
					  !squarecolor.contains(hand[j].getColor())) {
				squarecolor.add(hand[j].getColor());
				square.add(hand[j]);
			} else if (hand[j].getShape() == Tile.Shape.STAR && 
					  !starburstcolor.contains(hand[j].getColor())) {
				starburstcolor.add(hand[j].getColor());
				starburst.add(hand[j]);
			}
			j++;
		}

		// Bepaal langste zet, door middel van langste ArrayList.
		ArrayList<ArrayList<Tile>> sizes = new ArrayList<ArrayList<Tile>>(NUMBEROFSHAPESCOLORS);
		sizes.add(blue);
		sizes.add(cyan);
		sizes.add(green);
		sizes.add(magneta);
		sizes.add(red);
		sizes.add(yellow);
		sizes.add(circle);
		sizes.add(club);
		sizes.add(cross);
		sizes.add(diamond);
		sizes.add(square);
		sizes.add(starburst);

		int maxIndex = -1;
		int max = 0;
		for (int k = 0; k < NUMBEROFSHAPESCOLORS; k++) {
			if (sizes.get(k).size() > max) {
				max = sizes.get(k).size();
				maxIndex = k;
			}
		}
		int i1 = 0;
		while (i1 < sizes.get(maxIndex).size()) {
			move[i1][0] = protocol.Protocol.Settings.ORGINX;
			move[i1][1] = i1 + protocol.Protocol.Settings.ORGINY;
			move[i1][2] = player.getPlaceInHand(sizes.get(maxIndex).get(i1));
			i1++;
		}
	}

	public int[][] getFirstMove() {
		return move;
	}

	public String getFirstMoveString() {
		int nrMoves = 0;
		String msg = Protocol.Client.MAKEMOVE;
		while (move[nrMoves][0] != -1) {
			int x = move[nrMoves][0] - protocol.Protocol.Settings.ORGINX;
			int y = protocol.Protocol.Settings.ORGINY - move[nrMoves][1];
			msg += "_" + player.getHand()[move[nrMoves][2]].getColor().getCharColor()
					+ player.getHand()[move[nrMoves][2]].getShape().getCharShape() 
					+ "*" + x + "*" + y;
			nrMoves++;
		}
		System.out.println(msg);
		return msg;
	}
}
