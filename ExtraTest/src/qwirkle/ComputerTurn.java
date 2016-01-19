package qwirkle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;



public class ComputerTurn extends Turn {
	
	private int[] boundaries = new int[4];
	private ArrayList<Coordinate> coordinates;

	public ComputerTurn(Player player, Board board, ArrayList<Coordinate> coordinates) {
		super(player, board);
		this.coordinates = coordinates;
		move = new int[NROFTILESINHAND][INDEXMOVE];
		
		for (int i = 0; i < 6; i++) { // initialise
			for (int j = 0; j < 3; j++) {
				move[i][j] = -1;
			}
		}
	}
	
	public void makeTurn() {
		Board deepcopy = board.deepcopy();
		determinemove(deepcopy, coordinates);
		}
	
	public int testMove(int[][] move, Board b) {
		int score = b.testMove(move, player.getHand());
		System.out.println("score: " + score);
		return score; //geeft de score van de geteste move terug (-1 als zet niet mag)
	}
	
	public void determinemove(Board deepcopy, ArrayList<Coordinate> coordinates) {
		System.out.println("Player " + player.getName() + " Hand:");
		for (int i = 0; i < NROFTILESINHAND; i++) {
			System.out.println(player.getHand()[i].toString());
		}

		String[] splitInput = readChoice();
		for (int i = 0; i < (splitInput.length / 4); i++) {
			Tile tile = new Tile(getShape(splitInput[0+4*i].charAt(0)),getColor(splitInput[1+4*i].charAt(0)));
		move[i][2] = player.getPlaceInHand(tile);
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
				if (testHorizontalQwirkle(fields, temp.getX(),temp.getY(), player.getHand()[i].getColor(), player.getHand()[i].getShape())) {
					move[0][0] = temp.getX();
					move[0][1] = temp.getY();
					move[0][2] = i;
					qwirkle = true;
				} else if (testVerticalQwirkle(fields, temp.getX(),temp.getY(), player.getHand()[i].getColor(), player.getHand()[i].getShape())) {
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


