package qwirkle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import qwirkle.Tile;
import qwirkle.Tile.Color;
import qwirkle.Tile.Shape;

public class Turn {
	
	private int turnNr;
	private Board board;
	private Tile[] hand;
	private int choice = 0; // 0 of 1 (leggen of swappen)
	private int[][] move;
	private final int NROFTILESINHAND = 6;
	String name;

	public Turn(String name, int turnNr, Board board, Tile[] hand) {
		this.turnNr = turnNr;
		this.board = board;
		this.hand = hand;
		move = new int[NROFTILESINHAND][3];
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
		System.out.println("Eerste beurt: Gaat automatisch. HAND:");
		for (int k = 0; k < 6; k++) {
			System.out.println(hand[k].getColor() + " " + hand[k].getShape());
		}
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
				ArrayList<ArrayList<Tile>> sizes = new ArrayList<ArrayList<Tile>>(NROFTILESINHAND * 2);
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
				for (int k =0; k < NROFTILESINHAND*2; k++) {
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
					System.out.println("X-waarde: " + move[i1][0] + " Y-waarde: " + move[i1][1] 
							+ " Steen: " + hand[move[i1][2]].toString());
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
		System.out.println(name + ": Hand");
		for (int i = 0; i < NROFTILESINHAND; i++) {
			System.out.println(hand[i].toString());
		}
		
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
		
		// algoritme die berekend wat de beste optie is
	    // misschien iets van een deepcopy maken
	   	// hier moet dus ergens een algoritme komen
	    	//int[] data = {0, 1,1,3};
	    	// eerste getal: commando, tweede getal: xwaarde, derde getal: ywaarde, 
	    	//vierde getal: nr van steen in je hand. (ligt altijd tussen 0 en 5)
	    	//Dus 2 betekent hier dat je de (2+1 =) 3e steen uit je hand neerlegt
	    	// misschien mogelijk om meteen meerdere stenen mee te geven tegelijk(234 = 1 steen)(567 volgende)
	  
	    	
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
		// TODO Auto-generated method stub
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

	
}

