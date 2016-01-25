package qwirkle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import online.ClientHandler;
import protocol.Protocol;
import qwirkle.Tile.Color;
import qwirkle.Tile.Shape;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name) {
		super(name);
	}

	public String makeMove(Board board, String msg) {
		return "";
	}

	public String makeFirstMove(Board board) {
		firstTurn ft = new firstTurn(this, board);
		ft.makefirstTurn();
		return ft.getFirstMoveString();
	}

	public String determineMove(Board board) {
		long startTime = System.currentTimeMillis();
		long elapsedTime = 0;
		int[][] moves = new int[Player.HANDSIZE][3];
		initialiseMove(moves);
		ArrayList<Coordinate> possibleMoves = getCoordinates(board);
		String qwirkle = getQwirkle(startTime, board, possibleMoves);
		//if (!(qwirkle.equals(ClientHandler.NOREPLY))) {
			// return qwirkle;
		//}
		ArrayList<ArrayList<Tile>> tileGrouping = getTileDistribution(getHand());

		int bestScore = 0;
		String bestMove = "";
		while (elapsedTime < 0.3 * Protocol.Settings.TIMEOUTSECONDS * 1000) {
			// pak degene waar er het meest van zijn (kleur of vorm)
			int maxIndex = -1;
			int max = 0;
			for (int k = 0; k < 12; k++) {
				if (tileGrouping.get(k).size() > max) {
					max = tileGrouping.get(k).size();
					maxIndex = k;
				}
			}
			if (maxIndex < 6 && max > 0) { // 0 t/m 5 betekent dezelfde kleur, 6
											// t/m 11 betekent dezelfde vorm
				Tile.Color color = tileGrouping.get(maxIndex).get(0).getColor();
				ArrayList<Tile.Shape> shapes = new ArrayList<Tile.Shape>();
				for (Tile t : tileGrouping.get(maxIndex)) {
					shapes.add(t.getShape());
				}
				for (Coordinate c : possibleMoves) {
					String col = checkCoordinateColor(board.deepcopy(), c, tileGrouping.get(maxIndex), color, shapes);
					if (!col.equals(ClientHandler.NOREPLY) && Integer
							.parseInt(col.split(Character.toString(Protocol.Settings.DELIMITER))[0]) > bestScore) {
						bestScore = Integer.parseInt(col.split(Character.toString(Protocol.Settings.DELIMITER))[0]);
						bestMove = col;
					}
				}
			} else if (maxIndex >= 6 &&  max > 0) {
				Tile.Shape shape = tileGrouping.get(maxIndex).get(0).getShape();
				ArrayList<Tile.Color> colors = new ArrayList<Tile.Color>();
				for (Tile t : tileGrouping.get(maxIndex)) {
					colors.add(t.getColor());
				}
				for (Coordinate c : possibleMoves) {
					String sha = checkCoordinateShape(board.deepcopy(), c, tileGrouping.get(maxIndex), shape, colors);
					if (!sha.equals(ClientHandler.NOREPLY) && Integer
							.parseInt(sha.split(Character.toString(Protocol.Settings.DELIMITER))[0]) > bestScore) {
						bestScore = Integer.parseInt(sha.split(Character.toString(Protocol.Settings.DELIMITER))[0]);
						bestMove = sha;
					}
				}

			}
			if (maxIndex >= 0) {
				tileGrouping.get(maxIndex).clear();
			}

			Board dc = board.deepcopy();
			for (Coordinate c : possibleMoves) {
				for (Tile t : getHand()) {
					if (t != null) {
						dc.setField(c.getX(), c.getY(), t);
						moves[0][0] = c.getX();
						moves[0][1] = c.getY();
						moves[0][2] = getPlaceInHand(t);
						TestMove test = new TestMove(dc, moves, getHand());
						if (test.isLegalMove()) {
							if (test.getScore() > bestScore) {
								bestScore = test.getScore();
								bestMove = Protocol.Client.MAKEMOVE + "_" + t.getColor().getCharColor()
										+ t.getShape().getCharShape() + "*" + c.getX() + "*" + c.getY();
							}
						} else {
							dc.getBoard()[c.getX()][c.getY()] = null;
						}
					}
				}
			}
			elapsedTime = System.currentTimeMillis() - startTime;
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
		for (int i = 0; i < HANDSIZE; i++) {
			getHand()[i] = null;
		}
		return swap;
	}

	

	private ArrayList<Coordinate> getCoordinates(Board board) {
		int i = 0;
		final int ARRAYORGINX = 50;
		final int ARRAYORGINY = 50;
		ArrayList<Coordinate> possiblecoor = new ArrayList<Coordinate>();
		Tile[][] fields = board.getBoard();
		boolean doorgaan = true;

		while (doorgaan) {
			int count = 0;
			for (int k = 0; k <= (2 * i); k++) {
				if (fields[(ARRAYORGINX - i) + k][ARRAYORGINY + i] == null) {
					count++;
					if (movePossible(fields, (ARRAYORGINX - i) + k, ARRAYORGINY + i)) {
						possiblecoor.add(new Coordinate((ARRAYORGINX - i) + k, ARRAYORGINY + i));
					}
				}
				if (fields[(ARRAYORGINX - i) + k][ARRAYORGINY - i] == null) {
					count++;
					if (movePossible(fields, (ARRAYORGINX - i) + k, ARRAYORGINY - i)) {
						possiblecoor.add(new Coordinate((ARRAYORGINX - i) + k, ARRAYORGINY - i));
					}
				}
			}
			for (int k = 1; k < (2 * i); k++) {
				if (fields[ARRAYORGINX - i][(ARRAYORGINY + i) - k] == null) {
					count++;
					if (movePossible(fields, (ARRAYORGINX - i), (ARRAYORGINY + i) - k)) {
						possiblecoor.add(new Coordinate((ARRAYORGINX - i), (ARRAYORGINY + i) - k));
					}
				}
				if (fields[ARRAYORGINX + i][(ARRAYORGINY + i) - k] == null) {
					count++;
					if (movePossible(fields, (ARRAYORGINX + i), (ARRAYORGINY + i) - k)) {
						possiblecoor.add(new Coordinate((ARRAYORGINX + i), (ARRAYORGINY + i) - k));
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
		return (fields[i][j + 1] != null || fields[i][j - 1] != null || fields[i - 1][j] != null
				|| fields[i + 1][j] != null);
	}

	public String getQwirkle(long startTime, Board board, ArrayList<Coordinate> possibleMoves) {
		long elapsedTime = System.currentTimeMillis() - startTime;
		while (elapsedTime < 10000) {
			for (Coordinate c : possibleMoves) {
				String vertical = verticalQwirkle(c.getX(), c.getY(), board.getBoard());
				String horizontal = horizontalQwirkle(c.getX(), c.getY(), board.getBoard());
				if (vertical != ClientHandler.NOREPLY) {
					return vertical;
				} else if (horizontal != ClientHandler.NOREPLY) {
					return horizontal;
				}
			}
			elapsedTime = System.currentTimeMillis() - startTime;
		}
		return ClientHandler.NOREPLY;
	}

	public String verticalQwirkle(int xValue, int yValue, Tile[][] fields) {
		ArrayList<Tile.Color> colors = new ArrayList<Tile.Color>();
		ArrayList<Tile.Shape> shapes = new ArrayList<Tile.Shape>();

		int c = 1;
		while (fields[xValue][yValue + c] != null) {
			if (!shapes.contains(fields[xValue][yValue + c].getShape())) {
				shapes.add(fields[xValue][yValue + c].getShape());
			}
			if (!colors.contains(fields[xValue][yValue + c].getColor())) {
				colors.add(fields[xValue][yValue + c].getColor());
			}
			c++;
		}
		if (shapes.size() == 5 && colors.size() == 1) {
			for (Tile.Shape s : Tile.Shape.values()) {
				if (!shapes.contains(s)) {
					Tile.Color col = colors.get(0);
					int index = getPlaceInHand(new Tile(col, s));
					if (index >= 0) {
						return Protocol.Client.MAKEMOVE + Protocol.Settings.DELIMITER
								+ getHand()[index].getColor().getCharColor()
								+ getHand()[index].getShape().getCharShape() + Protocol.Settings.DELIMITER2 + xValue
								+ Protocol.Settings.DELIMITER2 + yValue;
					}
				}
			}
		} else if (colors.size() == 5 && shapes.size() == 1) {
			for (Tile.Color col : Tile.Color.values()) {
				if (!colors.contains(col)) {
					Tile.Shape s = shapes.get(0);
					int index = getPlaceInHand(new Tile(col, s));
					if (index >= 0) {
						return Protocol.Client.MAKEMOVE + Protocol.Settings.DELIMITER
								+ getHand()[index].getColor().getCharColor()
								+ getHand()[index].getShape().getCharShape() + Protocol.Settings.DELIMITER2 + xValue
								+ Protocol.Settings.DELIMITER2 + yValue;
					}
				}
			}
		} else {
			c = 1;
			while (fields[xValue][yValue - c] != null) {
				if (!shapes.contains(fields[xValue][yValue - c].getShape())) {
					shapes.add(fields[xValue][yValue - c].getShape());
				}
				if (!colors.contains(fields[xValue][yValue - c].getColor())) {
					colors.add(fields[xValue][yValue - c].getColor());
				}
				c++;
			}
			if (shapes.size() == 5 && colors.size() == 1) {
				for (Tile.Shape s : Tile.Shape.values()) {
					if (!shapes.contains(s)) {
						Tile.Color col = colors.get(0);
						int index = getPlaceInHand(new Tile(col, s));
						if (index >= 0) {
							return Protocol.Client.MAKEMOVE + Protocol.Settings.DELIMITER
									+ getHand()[index].getColor().getCharColor()
									+ getHand()[index].getShape().getCharShape() + Protocol.Settings.DELIMITER2 + xValue
									+ Protocol.Settings.DELIMITER2 + yValue;
						}
					}
				}
			} else if (colors.size() == 5 && shapes.size() == 1) {
				for (Tile.Color col : Tile.Color.values()) {
					if (!colors.contains(col)) {
						Tile.Shape s = shapes.get(0);
						int index = getPlaceInHand(new Tile(col, s));
						if (index >= 0) {
							return Protocol.Client.MAKEMOVE + Protocol.Settings.DELIMITER
									+ getHand()[index].getColor().getCharColor()
									+ getHand()[index].getShape().getCharShape() + Protocol.Settings.DELIMITER2 + xValue
									+ Protocol.Settings.DELIMITER2 + yValue;
						}
					}
				}
			}
		}
		return ClientHandler.NOREPLY;
	}

	public String horizontalQwirkle(int xValue, int yValue, Tile[][] fields) {
		ArrayList<Tile.Color> colors = new ArrayList<Tile.Color>();
		ArrayList<Tile.Shape> shapes = new ArrayList<Tile.Shape>();

		int c = 1;
		while (fields[xValue + c][yValue] != null) {
			if (!shapes.contains(fields[xValue + c][yValue].getShape())) {
				shapes.add(fields[xValue + c][yValue].getShape());
			}
			if (!colors.contains(fields[xValue + c][yValue].getColor())) {
				colors.add(fields[xValue + c][yValue].getColor());
			}
			c++;
		}
		if (shapes.size() == 5 && colors.size() == 1) {
			for (Tile.Shape s : Tile.Shape.values()) {
				if (!shapes.contains(s)) {
					Tile.Color col = colors.get(0);
					int index = getPlaceInHand(new Tile(col, s));
					if (index >= 0) {
						return Protocol.Client.MAKEMOVE + Protocol.Settings.DELIMITER
								+ getHand()[index].getColor().getCharColor()
								+ getHand()[index].getShape().getCharShape() + Protocol.Settings.DELIMITER2 + xValue
								+ Protocol.Settings.DELIMITER2 + yValue;
					}
				}
			}
		} else if (colors.size() == 5 && shapes.size() == 1) {
			for (Tile.Color col : Tile.Color.values()) {
				if (!colors.contains(col)) {
					Tile.Shape s = shapes.get(0);
					int index = getPlaceInHand(new Tile(col, s));
					if (index >= 0) {
						return Protocol.Client.MAKEMOVE + Protocol.Settings.DELIMITER
								+ getHand()[index].getColor().getCharColor()
								+ getHand()[index].getShape().getCharShape() + Protocol.Settings.DELIMITER2 + xValue
								+ Protocol.Settings.DELIMITER2 + yValue;
					}
				}
			}
		} else {
			c = 1;
			while (fields[xValue - c][yValue] != null) {
				if (!shapes.contains(fields[xValue - c][yValue].getShape())) {
					shapes.add(fields[xValue - c][yValue].getShape());
				}
				if (!colors.contains(fields[xValue - c][yValue].getColor())) {
					colors.add(fields[xValue - c][yValue].getColor());
				}
				c++;
			}
			if (shapes.size() == 5 && colors.size() == 1) {
				for (Tile.Shape s : Tile.Shape.values()) {
					if (!shapes.contains(s)) {
						Tile.Color col = colors.get(0);
						int index = getPlaceInHand(new Tile(col, s));
						if (index >= 0) {
							return Protocol.Client.MAKEMOVE + Protocol.Settings.DELIMITER
									+ getHand()[index].getColor().getCharColor()
									+ getHand()[index].getShape().getCharShape() + Protocol.Settings.DELIMITER2 + xValue
									+ Protocol.Settings.DELIMITER2 + yValue;
						}
					}
				}
			} else if (colors.size() == 5 && shapes.size() == 1) {
				for (Tile.Color col : Tile.Color.values()) {
					if (!colors.contains(col)) {
						Tile.Shape s = shapes.get(0);
						int index = getPlaceInHand(new Tile(col, s));
						if (index >= 0) {
							return Protocol.Client.MAKEMOVE + Protocol.Settings.DELIMITER
									+ getHand()[index].getColor().getCharColor()
									+ getHand()[index].getShape().getCharShape() + Protocol.Settings.DELIMITER2 + xValue
									+ Protocol.Settings.DELIMITER2 + yValue;
						}
					}
				}
			}
		}
		return ClientHandler.NOREPLY;
	}

	public ArrayList<ArrayList<Tile>> getTileDistribution(Tile[] hand) {
		// voor iedere kleur de verschillende vormen bijhouden.
		// uiteindelijk heb je het aantal unieke vormen van een kleur
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
			} else if (hand[i].getColor() == Tile.Color.ORANGE && !cyanshape.contains(hand[i].getShape())) {
				cyanshape.add(hand[i].getShape());
				cyan.add(hand[i]);
			} else if (hand[i].getColor() == Tile.Color.GREEN && !greenshape.contains(hand[i].getShape())) {
				greenshape.add(hand[i].getShape());
				green.add(hand[i]);
			} else if (hand[i].getColor() == Tile.Color.PURPLE && !magnetashape.contains(hand[i].getShape())) {
				magnetashape.add(hand[i].getShape());
				magneta.add(hand[i]);
			} else if (hand[i].getColor() == Tile.Color.RED && !redshape.contains(hand[i].getShape())) {
				redshape.add(hand[i].getShape());
				red.add(hand[i]);
			} else if (hand[i].getColor() == Tile.Color.YELLOW && !yellowshape.contains(hand[i].getShape())) {
				yellowshape.add(hand[i].getShape());
				yellow.add(hand[i]);
			}
			i++;
		}

		// voor iedere vorm de verschillende kleuren bijhouden.
		// uiteindelijk heb je het aantal unieke kleuren van een vorm
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
			if (hand[j].getShape() == Tile.Shape.CIRCLE && !circlecolor.contains(hand[j].getColor())) {
				circlecolor.add(hand[j].getColor());
				circle.add(hand[j]);
			} else if (hand[j].getShape() == Tile.Shape.PLUS && !clubcolor.contains(hand[j].getColor())) {
				clubcolor.add(hand[j].getColor());
				club.add(hand[j]);
			} else if (hand[j].getShape() == Tile.Shape.CROSS && !crosscolor.contains(hand[j].getColor())) {
				crosscolor.add(hand[j].getColor());
				cross.add(hand[j]);
			} else if (hand[j].getShape() == Tile.Shape.DIAMOND && !diamondcolor.contains(hand[j].getColor())) {
				diamondcolor.add(hand[j].getColor());
				diamond.add(hand[j]);
			} else if (hand[j].getShape() == Tile.Shape.SQUARE && !squarecolor.contains(hand[j].getColor())) {
				squarecolor.add(hand[j].getColor());
				square.add(hand[j]);
			} else if (hand[j].getShape() == Tile.Shape.STAR && !starburstcolor.contains(hand[j].getColor())) {
				starburstcolor.add(hand[j].getColor());
				starburst.add(hand[j]);
			}
			j++;
		}

		// opslaan van de gegevens
		ArrayList<ArrayList<Tile>> sizes = new ArrayList<ArrayList<Tile>>();
		sizes.add(blue);
		;
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
		return sizes;
	}

	public String checkCoordinateColor(Board b, Coordinate c, ArrayList<Tile> tiles, Color color,
			ArrayList<Tile.Shape> shapes) {
		/*
		 * deze checkt alleen voor kleur en alleen oostelijk Zuid,Noord, West
		 * moet ook nog in deze methode Voor shape doen we in een andere methode
		 */

		int bestScore = 0;
		int[][] move = new int[HANDSIZE][3];
		int[][] bestMove = initialiseMove(move);
		if (b.getBoard()[c.getX() + 1][c.getY()] != null && b.getBoard()[c.getX() + 1][c.getY()].getColor() == color) {
			if (!shapes.contains(b.getBoard()[c.getX() + 1][c.getY()].getShape())) {
				initialiseMove(move);
				for (int i = 0; i < tiles.size(); i++) {
					move[i][0] = c.getX() - i;
					move[i][1] = c.getY();
					move[i][2] = getPlaceInHand(tiles.get(i));
				}
				TestMove test = new TestMove(b.deepcopy(), move, getHand());
				if (test.isLegalMove()) {
					int tempScore = test.getScore();
					if (tempScore > bestScore) {
						bestScore = tempScore;
						bestMove = move;
					}
				}
				if (b.getBoard()[c.getX()][c.getY() - 1] == null) {
					initialiseMove(move);
					for (int i = 0; i < tiles.size(); i++) {
						move[i][0] = c.getX();
						move[i][1] = c.getY() - i;
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
					TestMove t2 = new TestMove(b.deepcopy(), move, getHand());
					if (t2.isLegalMove()) {
						int tempScore = t2.getScore();
						if (tempScore > bestScore) {
							bestScore = tempScore;
							bestMove = move;
						}
					}
				}
				if (b.getBoard()[c.getX()][c.getY() + 1] == null) {
					initialiseMove(move);
					for (int i = 0; i < tiles.size(); i++) {
						move[i][0] = c.getX();
						move[i][1] = c.getY() + i;
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
					TestMove t2 = new TestMove(b.deepcopy(), move, getHand());
					if (t2.isLegalMove()) {
						int tempScore = t2.getScore();
						if (tempScore > bestScore) {
							bestScore = tempScore;
							bestMove = move;
						}
					}
				}

			} else {
				initialiseMove(move);
				for (int i = 0; i < tiles.size() - 1; i++) {
					if (tiles.get(i).getShape() == b.getBoard()[c.getX() + 1][c.getY()].getShape()) {
						i--;
					} else {
						move[i][0] = c.getX() - (i + 1);
						move[i][1] = c.getY();
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
				}
				TestMove test = new TestMove(b.deepcopy(), move, getHand());
				if (test.isLegalMove()) {
					int tempScore = test.getScore();
					if (tempScore > bestScore) {
						bestScore = tempScore;
						bestMove = move;
					}
				}
			}
		}
		if (b.getBoard()[c.getX() - 1][c.getY()] != null && b.getBoard()[c.getX() - 1][c.getY()].getColor() == color) {
			if (!shapes.contains(b.getBoard()[c.getX() - 1][c.getY()].getShape())) {
				initialiseMove(move);
				for (int i = 0; i < tiles.size(); i++) {
					move[i][0] = c.getX() + i;
					move[i][1] = c.getY();
					move[i][2] = getPlaceInHand(tiles.get(i));
				}
				TestMove test = new TestMove(b.deepcopy(), move, getHand());
				if (test.isLegalMove()) {
					int tempScore = test.getScore();
					if (tempScore > bestScore) {
						bestScore = tempScore;
						bestMove = move;
					}
				}
				if (b.getBoard()[c.getX()][c.getY() - 1] == null) {
					initialiseMove(move);
					for (int i = 0; i < tiles.size(); i++) {
						move[i][0] = c.getX();
						move[i][1] = c.getY() - i;
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
					TestMove t2 = new TestMove(b.deepcopy(), move, getHand());
					if (t2.isLegalMove()) {
						int tempScore = t2.getScore();
						if (tempScore > bestScore) {
							bestScore = tempScore;
							bestMove = move;
						}
					}
				}
				if (b.getBoard()[c.getX()][c.getY() + 1] == null) {
					initialiseMove(move);
					for (int i = 0; i < tiles.size(); i++) {
						move[i][0] = c.getX();
						move[i][1] = c.getY() + i;
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
					TestMove t2 = new TestMove(b.deepcopy(), move, getHand());
					if (t2.isLegalMove()) {
						int tempScore = t2.getScore();
						if (tempScore > bestScore) {
							bestScore = tempScore;
							bestMove = move;
						}
					}
				}

			} else {
				initialiseMove(move);
				for (int i = 0; i < tiles.size() - 1; i++) {
					if (tiles.get(i).getShape() == b.getBoard()[c.getX() - 1][c.getY()].getShape()) {
						i--;
					} else {
						move[i][0] = c.getX() + (i + 1);
						move[i][1] = c.getY();
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
				}
				TestMove test = new TestMove(b.deepcopy(), move, getHand());
				if (test.isLegalMove()) {
					int tempScore = test.getScore();
					if (tempScore > bestScore) {
						bestScore = tempScore;
						bestMove = move;
					}
				}
			}
		}

		if (b.getBoard()[c.getX()][c.getY() + 1] != null && b.getBoard()[c.getX()][c.getY() + 1].getColor() == color) {
			if (!shapes.contains(b.getBoard()[c.getX()][c.getY() + 1].getShape())) {
				initialiseMove(move);
				for (int i = 0; i < tiles.size(); i++) {
					move[i][0] = c.getX();
					move[i][1] = c.getY() - i;
					move[i][2] = getPlaceInHand(tiles.get(i));
				}
				TestMove test = new TestMove(b.deepcopy(), move, getHand());
				if (test.isLegalMove()) {
					int tempScore = test.getScore();
					if (tempScore > bestScore) {
						bestScore = tempScore;
						bestMove = move;
					}
				}
				if (b.getBoard()[c.getX() - 1][c.getY()] == null) {
					initialiseMove(move);
					for (int i = 0; i < tiles.size(); i++) {
						move[i][0] = c.getX() - i;
						move[i][1] = c.getY();
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
					TestMove t2 = new TestMove(b.deepcopy(), move, getHand());
					if (t2.isLegalMove()) {
						int tempScore = t2.getScore();
						if (tempScore > bestScore) {
							bestScore = tempScore;
							bestMove = move;
						}
					}
				}
				if (b.getBoard()[c.getX() + 1][c.getY()] == null) {
					initialiseMove(move);
					for (int i = 0; i < tiles.size(); i++) {
						move[i][0] = c.getX() + i;
						move[i][1] = c.getY();
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
					TestMove t2 = new TestMove(b.deepcopy(), move, getHand());
					if (t2.isLegalMove()) {
						int tempScore = t2.getScore();
						if (tempScore > bestScore) {
							bestScore = tempScore;
							bestMove = move;
						}
					}

				}
			} else {
				initialiseMove(move);
				for (int i = 0; i < tiles.size() - 1; i++) {
					if (tiles.get(i).getShape() == b.getBoard()[c.getX()][c.getY() + 1].getShape()) {
						i--;
					} else {
						move[i][0] = c.getX();
						move[i][1] = c.getY() - (i + 1);
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
				}
				TestMove test = new TestMove(b.deepcopy(), move, getHand());
				if (test.isLegalMove()) {
					int tempScore = test.getScore();
					if (tempScore > bestScore) {
						bestScore = tempScore;
						bestMove = move;
					}
				}
			}
		}
		if (b.getBoard()[c.getX()][c.getY() - 1] != null && b.getBoard()[c.getX()][c.getY() - 1].getColor() == color) {
			if (!shapes.contains(b.getBoard()[c.getX()][c.getY() - 1].getShape())) {
				initialiseMove(move);
				for (int i = 0; i < tiles.size(); i++) {
					move[i][0] = c.getX();
					move[i][1] = c.getY() + i;
					move[i][2] = getPlaceInHand(tiles.get(i));
				}
				TestMove test = new TestMove(b.deepcopy(), move, getHand());
				if (test.isLegalMove()) {
					int tempScore = test.getScore();
					if (tempScore > bestScore) {
						bestScore = tempScore;
						bestMove = move;
					}
				}
				if (b.getBoard()[c.getX() - 1][c.getY()] == null) {
					initialiseMove(move);
					for (int i = 0; i < tiles.size(); i++) {
						move[i][0] = c.getX() - i;
						move[i][1] = c.getY();
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
					TestMove t2 = new TestMove(b.deepcopy(), move, getHand());
					if (t2.isLegalMove()) {
						int tempScore = t2.getScore();
						if (tempScore > bestScore) {
							bestScore = tempScore;
							bestMove = move;
						}
					}
				}
				if (b.getBoard()[c.getX() + 1][c.getY()] == null) {
					initialiseMove(move);
					for (int i = 0; i < tiles.size(); i++) {
						move[i][0] = c.getX() + i;
						move[i][1] = c.getY();
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
					TestMove t2 = new TestMove(b.deepcopy(), move, getHand());
					if (t2.isLegalMove()) {
						int tempScore = t2.getScore();
						if (tempScore > bestScore) {
							bestScore = tempScore;
							bestMove = move;
						}
					}
				}

			} else {
				initialiseMove(move);
				for (int i = 0; i < tiles.size() - 1; i++) {
					if (tiles.get(i).getShape() == b.getBoard()[c.getX()][c.getY() + 1].getShape()) {
						i--;
					} else {
						move[i][0] = c.getX();
						move[i][1] = c.getY() - (i + 1);
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
				}
				TestMove test = new TestMove(b.deepcopy(), move, getHand());
				if (test.isLegalMove()) {
					int tempScore = test.getScore();
					if (tempScore > bestScore) {
						bestScore = tempScore;
						bestMove = move;
					}
				}
			}

		}

		if (bestMove[0][0] != -1) {
			String msg = bestScore + Protocol.Settings.DELIMITER + "";
			int i = 0;
			while (bestMove[i][0] != -1) {
				msg += getHand()[bestMove[i][2]].getColor().getCharColor()
						+ getHand()[bestMove[i][2]].getShape().getCharShape() + Protocol.Settings.DELIMITER2
						+ bestMove[i][0] + Protocol.Settings.DELIMITER2 + bestMove[i][1] + Protocol.Settings.DELIMITER;
				i++;
			}
			return msg.substring(msg.length() - 1);
		}
		return ClientHandler.NOREPLY;
	}

	public String checkCoordinateShape(Board b, Coordinate c, ArrayList<Tile> tiles, Shape shape,
			ArrayList<Color> colors) {
		
		int bestScore = 0;
		int[][] move = new int[HANDSIZE][3];
		int[][] bestMove = initialiseMove(move);
		if (b.getBoard()[c.getX() + 1][c.getY()] != null && b.getBoard()[c.getX() + 1][c.getY()].getShape() == shape) {
			if (!colors.contains(b.getBoard()[c.getX() + 1][c.getY()].getColor())) {
				initialiseMove(move);
				for (int i = 0; i < tiles.size(); i++) {
					move[i][0] = c.getX() - i;
					move[i][1] = c.getY();
					move[i][2] = getPlaceInHand(tiles.get(i));
				}
				TestMove test = new TestMove(b.deepcopy(), move, getHand());
				if (test.isLegalMove()) {
					int tempScore = test.getScore();
					if (tempScore > bestScore) {
						bestScore = tempScore;
						bestMove = move;
					}
				}
				if (b.getBoard()[c.getX()][c.getY() - 1] == null) {
					initialiseMove(move);
					for (int i = 0; i < tiles.size(); i++) {
						move[i][0] = c.getX();
						move[i][1] = c.getY() - i;
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
					TestMove t2 = new TestMove(b.deepcopy(), move, getHand());
					if (t2.isLegalMove()) {
						int tempScore = t2.getScore();
						if (tempScore > bestScore) {
							bestScore = tempScore;
							bestMove = move;
						}
					}
				}
				if (b.getBoard()[c.getX()][c.getY() + 1] == null) {
					initialiseMove(move);
					for (int i = 0; i < tiles.size(); i++) {
						move[i][0] = c.getX();
						move[i][1] = c.getY() + i;
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
					TestMove t2 = new TestMove(b.deepcopy(), move, getHand());
					if (t2.isLegalMove()) {
						int tempScore = t2.getScore();
						if (tempScore > bestScore) {
							bestScore = tempScore;
							bestMove = move;
						}
					}
				}

			} else {
				initialiseMove(move);
				for (int i = 0; i < tiles.size() - 1; i++) {
					if (tiles.get(i).getColor() == b.getBoard()[c.getX() + 1][c.getY()].getColor()) {
						i--;
					} else {
						move[i][0] = c.getX() - (i + 1);
						move[i][1] = c.getY();
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
				}
				TestMove test = new TestMove(b.deepcopy(), move, getHand());
				if (test.isLegalMove()) {
					int tempScore = test.getScore();
					if (tempScore > bestScore) {
						bestScore = tempScore;
						bestMove = move;
					}
				}
			}
		}
		if (b.getBoard()[c.getX() - 1][c.getY()] != null && b.getBoard()[c.getX() - 1][c.getY()].getShape() == shape) {
			if (!colors.contains(b.getBoard()[c.getX() - 1][c.getY()].getColor())) {
				initialiseMove(move);
				for (int i = 0; i < tiles.size(); i++) {
					move[i][0] = c.getX() + i;
					move[i][1] = c.getY();
					move[i][2] = getPlaceInHand(tiles.get(i));
				}
				TestMove test = new TestMove(b.deepcopy(), move, getHand());
				if (test.isLegalMove()) {
					int tempScore = test.getScore();
					if (tempScore > bestScore) {
						bestScore = tempScore;
						bestMove = move;
					}
				}
				if (b.getBoard()[c.getX()][c.getY() - 1] == null) {
					initialiseMove(move);
					for (int i = 0; i < tiles.size(); i++) {
						move[i][0] = c.getX();
						move[i][1] = c.getY() - i;
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
					TestMove t2 = new TestMove(b.deepcopy(), move, getHand());
					if (t2.isLegalMove()) {
						int tempScore = t2.getScore();
						if (tempScore > bestScore) {
							bestScore = tempScore;
							bestMove = move;
						}
					}
				}
				if (b.getBoard()[c.getX()][c.getY() + 1] == null) {
					initialiseMove(move);
					for (int i = 0; i < tiles.size(); i++) {
						move[i][0] = c.getX();
						move[i][1] = c.getY() + i;
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
					TestMove t2 = new TestMove(b.deepcopy(), move, getHand());
					if (t2.isLegalMove()) {
						int tempScore = t2.getScore();
						if (tempScore > bestScore) {
							bestScore = tempScore;
							bestMove = move;
						}
					}
				}

			} else {
				initialiseMove(move);
				for (int i = 0; i < tiles.size() - 1; i++) {
					if (tiles.get(i).getColor() == b.getBoard()[c.getX() - 1][c.getY()].getColor()) {
						i--;
					} else {
						move[i][0] = c.getX() + (i + 1);
						move[i][1] = c.getY();
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
				}
				TestMove test = new TestMove(b.deepcopy(), move, getHand());
				if (test.isLegalMove()) {
					int tempScore = test.getScore();
					if (tempScore > bestScore) {
						bestScore = tempScore;
						bestMove = move;
					}
				}
			}
		}

		if (b.getBoard()[c.getX()][c.getY() + 1] != null && b.getBoard()[c.getX()][c.getY() + 1].getShape() == shape) {
			if (!colors.contains(b.getBoard()[c.getX()][c.getY() + 1].getColor())) {
				initialiseMove(move);
				for (int i = 0; i < tiles.size(); i++) {
					move[i][0] = c.getX();
					move[i][1] = c.getY() - i;
					move[i][2] = getPlaceInHand(tiles.get(i));
				}
				TestMove test = new TestMove(b.deepcopy(), move, getHand());
				if (test.isLegalMove()) {
					int tempScore = test.getScore();
					if (tempScore > bestScore) {
						bestScore = tempScore;
						bestMove = move;
					}
				}
				if (b.getBoard()[c.getX() - 1][c.getY()] == null) {
					initialiseMove(move);
					for (int i = 0; i < tiles.size(); i++) {
						move[i][0] = c.getX() - i;
						move[i][1] = c.getY();
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
					TestMove t2 = new TestMove(b.deepcopy(), move, getHand());
					if (t2.isLegalMove()) {
						int tempScore = t2.getScore();
						if (tempScore > bestScore) {
							bestScore = tempScore;
							bestMove = move;
						}
					}
				}
				if (b.getBoard()[c.getX() + 1][c.getY()] == null) {
					initialiseMove(move);
					for (int i = 0; i < tiles.size(); i++) {
						move[i][0] = c.getX() + i;
						move[i][1] = c.getY();
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
					TestMove t2 = new TestMove(b.deepcopy(), move, getHand());
					if (t2.isLegalMove()) {
						int tempScore = t2.getScore();
						if (tempScore > bestScore) {
							bestScore = tempScore;
							bestMove = move;
						}
					}

				}
			} else {
				initialiseMove(move);
				for (int i = 0; i < tiles.size() - 1; i++) {
					if (tiles.get(i).getColor() == b.getBoard()[c.getX()][c.getY() + 1].getColor()) {
						i--;
					} else {
						move[i][0] = c.getX();
						move[i][1] = c.getY() - (i + 1);
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
				}
				TestMove test = new TestMove(b.deepcopy(), move, getHand());
				if (test.isLegalMove()) {
					int tempScore = test.getScore();
					if (tempScore > bestScore) {
						bestScore = tempScore;
						bestMove = move;
					}
				}
			}
		}
		if (b.getBoard()[c.getX()][c.getY() - 1] != null && b.getBoard()[c.getX()][c.getY() - 1].getShape() == shape) {
			if (!colors.contains(b.getBoard()[c.getX()][c.getY() - 1].getColor())) {
				initialiseMove(move);
				for (int i = 0; i < tiles.size(); i++) {
					move[i][0] = c.getX();
					move[i][1] = c.getY() + i;
					move[i][2] = getPlaceInHand(tiles.get(i));
				}
				TestMove test = new TestMove(b.deepcopy(), move, getHand());
				if (test.isLegalMove()) {
					int tempScore = test.getScore();
					if (tempScore > bestScore) {
						bestScore = tempScore;
						bestMove = move;
					}
				}
				if (b.getBoard()[c.getX() - 1][c.getY()] == null) {
					initialiseMove(move);
					for (int i = 0; i < tiles.size(); i++) {
						move[i][0] = c.getX() - i;
						move[i][1] = c.getY();
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
					TestMove t2 = new TestMove(b.deepcopy(), move, getHand());
					if (t2.isLegalMove()) {
						int tempScore = t2.getScore();
						if (tempScore > bestScore) {
							bestScore = tempScore;
							bestMove = move;
						}
					}
				}
				if (b.getBoard()[c.getX() + 1][c.getY()] == null) {
					initialiseMove(move);
					for (int i = 0; i < tiles.size(); i++) {
						move[i][0] = c.getX() + i;
						move[i][1] = c.getY();
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
					TestMove t2 = new TestMove(b.deepcopy(), move, getHand());
					if (t2.isLegalMove()) {
						int tempScore = t2.getScore();
						if (tempScore > bestScore) {
							bestScore = tempScore;
							bestMove = move;
						}
					}
				}

			} else {
				initialiseMove(move);
				for (int i = 0; i < tiles.size() - 1; i++) {
					if (tiles.get(i).getColor() == b.getBoard()[c.getX()][c.getY() + 1].getColor()) {
						i--;
					} else {
						move[i][0] = c.getX();
						move[i][1] = c.getY() - (i + 1);
						move[i][2] = getPlaceInHand(tiles.get(i));
					}
				}
				TestMove test = new TestMove(b.deepcopy(), move, getHand());
				if (test.isLegalMove()) {
					int tempScore = test.getScore();
					if (tempScore > bestScore) {
						bestScore = tempScore;
						bestMove = move;
					}
				}
			}

		}

		if (bestMove[0][0] != -1) {
			String msg = bestScore + Protocol.Settings.DELIMITER + "";
			int i = 0;
			while (bestMove[i][0] != -1) {
				msg += getHand()[bestMove[i][2]].getColor().getCharColor()
						+ getHand()[bestMove[i][2]].getShape().getCharShape() + Protocol.Settings.DELIMITER2
						+ bestMove[i][0] + Protocol.Settings.DELIMITER2 + bestMove[i][1] + Protocol.Settings.DELIMITER;
				i++;
			}
			return msg.substring(msg.length() - 1);
		}
		return ClientHandler.NOREPLY;
	}
	
	public static void main(String[] args) {
		long starttime = System.currentTimeMillis();
		ComputerPlayer cp = new ComputerPlayer("Niek");
		Deck deck = new Deck();
		cp.addToHand("AE_DA_DB_CC_CA_DF");
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
