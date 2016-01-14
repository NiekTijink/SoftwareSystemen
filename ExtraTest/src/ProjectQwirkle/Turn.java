package ProjectQwirkle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import ProjectQwirkle.Tile;
import ProjectQwirkle.Tile.Color;
import ProjectQwirkle.Tile.Shape;


public class Turn {
	
	private int turnNr;
	private Board board;
	private Tile[] hand;
	private int choice = 0; // 0 of 1 (leggen of swappen)
	private int[][] move;
	private static final int NROFTILESINHAND = 6;
	private static final int INDEXMOVE = 3;
	private static final int NUMBEROFSHAPESCOLORS = 12;
	private static final int MAXHALFARRAY = 50;
	private static final int ARRAYORGINX = 50;
	private static final int ARRAYORGINY = 50;
	private int[] boundaries = new int[4];
	String name;

	public Turn(String name, int turnNr, Board board, Tile[] hand) {
		this.turnNr = turnNr;
		this.board = board;
		this.hand = hand;
		move = new int[NROFTILESINHAND][INDEXMOVE];
		this.name = name;
		
		for (int i = 0; i < 6; i++) { // initialise
			for (int j = 0; j < 3; j++) {
				move[i][j] = -1;
			}
		}
	}
	
	public void makeTurn() {
		Board deepcopy = board.deepcopy();
		if(turnNr == 0 && name.equals("Thomas")) {
			determinefirstmove(deepcopy, hand);
		} else {
			determinemove(deepcopy, hand);
		}
	}
	
	public int testMove(int[][] move, Board b) {
		int score = b.testMove(move, hand);
		System.out.println("score: " + score);
		return score; //geeft de score van de geteste move terug (-1 als zet niet mag)
	}

	//Bepaal eerste move
	private void determinefirstmove(Board deepcopy, Tile[] hand) {
		//Kleur combinatie
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
				while(i < 6) {
					if(hand[i].getColor() == Color.BLUE && !blueshape.contains(hand[i].getShape())) {
						blueshape.add(hand[i].getShape());
						blue.add(hand[i]);
					}else if(hand[i].getColor() == Color.ORANGE && !cyanshape.contains(hand[i].getShape())) {
						cyanshape.add(hand[i].getShape());
						cyan.add(hand[i]);
					}else if(hand[i].getColor() == Color.GREEN&& !greenshape.contains(hand[i].getShape())) {
						greenshape.add(hand[i].getShape());
						green.add(hand[i]);
					}else if(hand[i].getColor() == Color.PURPLE && !magnetashape.contains(hand[i].getShape())) {
						magnetashape.add(hand[i].getShape());
						magneta.add(hand[i]);
					}else if(hand[i].getColor() == Color.RED && !redshape.contains(hand[i].getShape())) {
						redshape.add(hand[i].getShape());
						red.add(hand[i]);
					}else if(hand[i].getColor() == Color.YELLOW && !yellowshape.contains(hand[i].getShape())) {
						yellowshape.add(hand[i].getShape());
						yellow.add(hand[i]);
					} i++;
				}
				//Shape combinatie
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
				while(j < 6) {
					if(hand[j].getShape() == Shape.CIRCLE && !circlecolor.contains(hand[j].getColor())) {
						circlecolor.add(hand[j].getColor());
						circle.add(hand[j]);
					}else if(hand[j].getShape() == Shape.PLUS && !clubcolor.contains(hand[j].getColor())) {
						clubcolor.add(hand[j].getColor());
						club.add(hand[j]);
					}else if(hand[j].getShape() == Shape.CROSS && !crosscolor.contains(hand[j].getColor())) {
						crosscolor.add(hand[j].getColor());
						cross.add(hand[j]);
					}else if(hand[j].getShape() == Shape.DIAMOND && !diamondcolor.contains(hand[j].getColor())) {
						diamondcolor.add(hand[j].getColor());
						diamond.add(hand[j]);
					}else if(hand[j].getShape() == Shape.SQUARE && !squarecolor.contains(hand[j].getColor())) {
						squarecolor.add(hand[j].getColor());
						square.add(hand[j]);
					}else if(hand[j].getShape() == Shape.STAR && !starburstcolor.contains(hand[j].getColor())) {
						starburstcolor.add(hand[j].getColor());
						starburst.add(hand[j]);
					} j++;
				}
				//Bepaal langste zet, door middel van langste ArrayList.
				ArrayList<ArrayList<Tile>> sizes = new ArrayList<ArrayList<Tile>>(NUMBEROFSHAPESCOLORS);
				sizes.add(blue);;
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
				for (int k =0; k < NUMBEROFSHAPESCOLORS; k++) {
					if (sizes.get(k).size() > max) {
						max = sizes.get(k).size();
						maxIndex = k;
					}
				}
				int i1 = 0;
				while(i1 < sizes.get(maxIndex).size()) {
					move[i1][0] = 50;
					move[i1][1] = i1+50;
					move[i1][2] = getPlaceInHand(sizes.get(maxIndex).get(i1));
					i1++;
				}
	}

	public int getPlaceInHand(Tile tile) {
		//System.out.println(tile.getShape() + " " + tile.getColor());
		for (int i = 0; i < NROFTILESINHAND; i++) {
			if (hand[i].getShape() == tile.getShape() && hand[i].getColor() == tile.getColor()) {
				return i;
			}
		}
		return -1;
	}
	
	private void determinemove(Board deepcopy, Tile[] hand) {
		System.out.println("Player " + name + " Hand:");
		for (int i = 0; i < NROFTILESINHAND; i++) {
			System.out.println(hand[i].toString());
		}
		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
		Tile[][] fields = deepcopy.getBoard();
		
		for(int i = 0; i <= 3; i++) {
			for(int k = 0; k <= (2*i); k++) {
				if (fields[(ARRAYORGINX - i) + k][ARRAYORGINY + i] == null && movePossible(fields,(ARRAYORGINX - i) + k, ARRAYORGINY + i)) {
					coordinates.add(new Coordinate((ARRAYORGINX - i) + k, ARRAYORGINY + i));
				}
				if (fields[(ARRAYORGINX - i) + k][ARRAYORGINY - i] == null && movePossible(fields,(ARRAYORGINX - i) + k, ARRAYORGINY - i)) {
					coordinates.add(new Coordinate((ARRAYORGINX - i) + k, ARRAYORGINY - i));
				}
			}
			for (int k = 1; k < (2*i); k++) {
				if (fields[ARRAYORGINX - i][(ARRAYORGINY + i) - k] == null && movePossible(fields,(ARRAYORGINX - i) , (ARRAYORGINY + i) - k)) {
					coordinates.add(new Coordinate((ARRAYORGINX - i) , (ARRAYORGINY + i) - k));
				}
				if (fields[ARRAYORGINX + i][(ARRAYORGINY + i) - k] == null && movePossible(fields,(ARRAYORGINX + i) , (ARRAYORGINY + i) - k)) {
					coordinates.add(new Coordinate((ARRAYORGINX + i) , (ARRAYORGINY + i) - k));
				}
				
			}
		}
		System.out.println("Board State :");
		System.out.println(board.toString(board.getBoundaries(coordinates)));
		
		String[] splitInput = readChoice();
		for (int i = 0; i < (splitInput.length / 4); i++) {
			Tile tile = new Tile(getShape(splitInput[0+4*i].charAt(0)),getColor(splitInput[1+4*i].charAt(0)));
		move[i][2] = getPlaceInHand(tile);
		move[i][0] = Integer.parseInt(splitInput[2+4*i]);
		move[i][1] = Integer.parseInt(splitInput[3+4*i]);
		}
		
		
		if (choice == 0) {
			testMove(move, deepcopy);
		}
		
		
		/*boolean goOn = true;
		while (goOn = true) {
			if (checkQwirkle(coordinates, fields)) {
			goOn = false;
			}*/
		
	}
		


	private boolean checkQwirkle( ArrayList<Coordinate> coordinates, Tile[][] fields) {
		//Qwirkle, 5 op een rij?
		boolean qwirkle = false;
		for( Coordinate temp : coordinates) {
			int i = 0;
			while(i<6) {
				if (testHorizontalQwirkle(fields, temp.getX(),temp.getY(), hand[i].getColor(), hand[i].getShape())) {
					move[0][0] = temp.getX();
					move[0][1] = temp.getY();
					move[0][2] = i;
					qwirkle = true;
				} else if (testVerticalQwirkle(fields, temp.getX(),temp.getY(), hand[i].getColor(), hand[i].getShape())) {
					move[0][0] = temp.getX();
					move[0][1] = temp.getY();
					move[0][2] = i;
					qwirkle = true;
				}
				i++;
			}
		}
		return qwirkle;
	}
	
	private boolean movePossible(Tile[][] fields, int i, int j) {
		if(fields[i][j+1] != null || fields[i][j-1] != null || fields[i-1][j] != null || fields[i+1][j] != null ) {
			return true;
		} else {
		return false;
		}
	}

	public String[] readChoice() {
		Scanner sc = new Scanner(System.in);
		String[] temp;
		int i = 0;
		while (sc.hasNextLine()) {
			String s = sc.nextLine();
			temp = s.split("-");
			return temp;
		}
		return null;
	}
	
	public int[][] getMove() {
		return move;
	}

	public int getChoice() {
		return choice;
	}
	
	   public Tile.Shape getShape(char i) {
	    	if (i == 'D') {
	    		return Tile.Shape.SQUARE;
	    	} else if (i == 'A') {
	    		return Tile.Shape.CIRCLE;
	    	} else if (i == 'C') {
	    		return Tile.Shape.DIAMOND;
	    	} else if (i == 'F') {
	    		return Tile.Shape.PLUS;
	    	} else if (i == 'B') {
	    		return Tile.Shape.CROSS;
	    	} else if (i == 'E') {
	    		return Tile.Shape.STAR;
	    	} else {
	    		return null;
	    	}
	    }
	   
	   public Tile.Color getColor(char i) {
	    	if (i == 'A') {
	    		return Tile.Color.RED;
	    	} else if (i == 'D') {
	    		return Tile.Color.GREEN;
	    	} else if (i == 'C') {
	    		return Tile.Color.YELLOW;
	    	} else if (i == 'E') {
	    		return Tile.Color.BLUE;
	    	} else if (i == 'B') {
	    		return Tile.Color.ORANGE;
	    	} else if (i == 'F') {
	    		return Tile.Color.PURPLE;
	    	} else {
	    		return null;
	    	}
	    }

	   public boolean testHorizontalQwirkle(Tile[][] fields, int xValue, int yValue, Tile.Color color, Tile.Shape shape) {
			char typeOfRow = ' ';
			ArrayList<Tile.Color> colors = new ArrayList<Tile.Color>();
			colors.add(color);
			ArrayList<Tile.Shape> shapes = new ArrayList<Tile.Shape>();
			shapes.add(shape);
			
			boolean doorgaan = true;
			int c = 0; //counter
			while (doorgaan) { // omhoog kijken
				c++;
				if (!(fields[xValue+c][yValue] == null)) { // kijken of vakje leeg is
					if (fields[xValue+c][yValue].getColor().equals(color) // kijken of kleur gelijk is
							&& !(shapes.contains(fields[xValue+c][yValue].getShape())) // als kleur gelijk is, vorm mag niet gelijk
							&& typeOfRow != 'S') { 
						typeOfRow = 'C'; // rij van het type Color
						shapes.add(fields[xValue+c][yValue].getShape());
					} else if (fields[xValue+c][yValue].getShape().equals(shape) 
							&& !(colors.contains(fields[xValue+c][yValue].getColor()))
							&& typeOfRow != 'C') {
						typeOfRow = 'S';
						colors.add(fields[xValue+c][yValue].getColor());
					} else {
						return false;
					}
				}
				doorgaan = false; // stoppen met zoeken als de steen leeg is
			}
			c = 0; //counter reset
			doorgaan = true;
			while (doorgaan) { //omlaag
				c++;
				if (!(fields[xValue-c][yValue] == null)) {
					if (fields[xValue-c][yValue].getColor().equals(color) // kijken of kleur gelijk is
							&& !(shapes.contains(fields[xValue-c][yValue].getShape())) // als kleur gelijk is, vorm mag niet gelijk
							&& typeOfRow != 'S') {
						typeOfRow = 'C';
						shapes.add(fields[xValue-c][yValue].getShape());
					} else if (fields[xValue-c][yValue].getShape().equals(shape) 
							&& !(colors.contains(fields[xValue-c][yValue].getColor()))
							&& typeOfRow != 'C') {
						typeOfRow = 'S';
						colors.add(fields[xValue-c][yValue].getColor());
					} else {
						return false;
					}
				} else {
				doorgaan = false;
				}
			}
			boolean isQwirkle = false;
			if (shapes.size() == 6 || colors.size() == 6) {
				isQwirkle = true;
			}
			return isQwirkle;
			
		}
	   
	   public boolean testVerticalQwirkle(Tile[][] fields, int xValue, int yValue, Tile.Color color, Tile.Shape shape) {
			char typeOfRow = ' ';
			ArrayList<Tile.Color> colors = new ArrayList<Tile.Color>();
			colors.add(color);
			ArrayList<Tile.Shape> shapes = new ArrayList<Tile.Shape>();
			shapes.add(shape);
			
			boolean doorgaan = true;
			int c = 0; //counter
			while (doorgaan) { // omhoog kijken
				c++;
				if (!(fields[xValue][yValue + c] == null)) { // kijken of vakje leeg is
					if (fields[xValue][yValue + c].getColor().equals(color) // kijken of kleur gelijk is
							&& !(shapes.contains(fields[xValue][yValue + c].getShape())) // als kleur gelijk is, vorm mag niet gelijk
							&& typeOfRow != 'S') {
						typeOfRow = 'C';
						shapes.add(fields[xValue][yValue + c].getShape());
					} else if (fields[xValue][yValue + c].getShape().equals(shape) 
							&& !(colors.contains(fields[xValue][yValue + c].getColor()))
							&& typeOfRow != 'C') {
						typeOfRow = 'S';
						colors.add(fields[xValue][yValue + c].getColor());
					} else {
						return false;
					}
				} else {
					doorgaan = false;
				}
			}
			c = 0; //counter reset
			doorgaan = true;
			while (doorgaan) { //omlaag
				c++;
				if (!(fields[xValue][yValue - c] == null)) {
					if (fields[xValue][yValue - c].getColor().equals(color) // kijken of kleur gelijk is
							&& !(shapes.contains(fields[xValue][yValue - c].getShape())) // als kleur gelijk is, vorm mag niet gelijk
							&& typeOfRow != 'S') {
						typeOfRow = 'C';
						shapes.add(fields[xValue][yValue - c].getShape());
					} else if (fields[xValue][yValue - c].getShape().equals(shape) 
							&& !(colors.contains(fields[xValue][yValue - c].getColor()))
							&& typeOfRow != 'C') {
						typeOfRow = 'S';
						colors.add(fields[xValue][yValue - c].getColor());
					} else {
						return false;
					}
				} else {
				doorgaan = false;
				}
			}
			boolean isQwirkle = false;
			if (shapes.size() == 6 || colors.size() == 6) {
				isQwirkle = true;
			}
			return isQwirkle;
		}

}

