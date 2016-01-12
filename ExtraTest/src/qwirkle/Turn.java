package qwirkle;

import java.util.ArrayList;
import java.util.Scanner;

import qwirkle.Tile;
import qwirkle.Tile.Color;
import qwirkle.Tile.Shape;

public class Turn {
	
	private int turnNr;
	private Board board;
	private Tile[] hand;
	private int choice; // 0 of 1 (leggen of swappen)
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
		if(turnNr == 0) {
			determinefirstmove(deepcopy, hand);
		} else {
			determinemove(deepcopy, hand);
		}
	}
	
	public int testMove(int[][] move, Board b) {
		int score = b.testMove(move);
		System.out.println("score: " + score);
		return score; //geeft de score van de geteste move terug
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
				int i = 1;
				while(i < 6) {
					if(hand[i].getColor() == Color.BLUE && !blueshape.contains(hand[i].getShape())) {
						blueshape.add(hand[0].getShape());
						blue.add(hand[i]);
					}else if(hand[i].getColor() == Color.CYAN && !cyanshape.contains(hand[i].getShape())) {
						cyanshape.add(hand[0].getShape());
						cyan.add(hand[i]);
					}else if(hand[i].getColor() == Color.GREEN&& !greenshape.contains(hand[i].getShape())) {
						greenshape.add(hand[0].getShape());
						green.add(hand[i]);
					}else if(hand[i].getColor() == Color.MAGNETA && !magnetashape.contains(hand[i].getShape())) {
						magnetashape.add(hand[0].getShape());
						magneta.add(hand[i]);
					}else if(hand[i].getColor() == Color.RED && !redshape.contains(hand[i].getShape())) {
						redshape.add(hand[0].getShape());
						red.add(hand[i]);
					}else if(hand[i].getColor() == Color.YELLOW && !yellowshape.contains(hand[i].getShape())) {
						yellowshape.add(hand[0].getShape());
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
						circlecolor.add(hand[0].getColor());
						circle.add(hand[j]);
					}else if(hand[j].getShape() == Shape.CLUB && !clubcolor.contains(hand[j].getColor())) {
						clubcolor.add(hand[0].getColor());
						club.add(hand[j]);
					}else if(hand[j].getShape() == Shape.CROSS && !crosscolor.contains(hand[j].getColor())) {
						crosscolor.add(hand[0].getColor());
						cross.add(hand[j]);
					}else if(hand[j].getShape() == Shape.DIAMOND && !diamondcolor.contains(hand[j].getColor())) {
						diamondcolor.add(hand[0].getColor());
						diamond.add(hand[j]);
					}else if(hand[j].getShape() == Shape.SQUARE && !squarecolor.contains(hand[j].getColor())) {
						squarecolor.add(hand[0].getColor());
						square.add(hand[j]);
					}else if(hand[j].getShape() == Shape.STARBURST && !starburstcolor.contains(hand[j].getColor())) {
						starburstcolor.add(hand[0].getColor());
						starburst.add(hand[j]);
					} j++;
				}
				//Bepaal langste zet, door middel van langste ArrayList.
				int bl = blue.size();
				int cy = cyan.size();
				int gr = green.size();
				int ma =  magneta.size();
				int re = red.size();
				int ye = yellow.size();
				int ci = circle.size();
				int cl = club.size();
				int cr = cross.size();
				int di = diamond.size();
				int sq = square.size();
				int st = starburst.size();
				
				if(bl>=cy && bl>=gr && bl>=ma && bl>=re && bl>=ye && bl>=ci && bl>=cl && bl>=cr && bl>=di && bl>=sq && bl>=st) {
					int i1 = 0;
					while(i1 < blue.size()) {
						move[i1][0] = 0;
						move[i1][1] = i;
						move[i1][2] = getPlaceInHand(blue.get(i1));
						i1++;
					}
				}else if (cy>=bl && cy>=gr && cy>=ma && cy>=re && cy>=ye && cy>=ci && cy>=cl && cy>=cr && cy>=di && cy>=sq && cy>=st) {
					int i1 = 0;
					while(i1 < cyan.size()) {
						move[i1][0] = 0;
						move[i1][1] = i;
						move[i1][2] = getPlaceInHand(cyan.get(i1));
						i1++;
					}
				}else if (gr>=bl && gr>=cy && gr>=ma && gr>=re && gr>=ye && gr>=ci && gr>=cl && gr>=cr && gr>=di && gr>=sq && gr>=st) {
					int i1 = 0;
					while(i1 < green.size()) {
						move[i1][0] = 0;
						move[i1][1] = i;
						move[i1][2] = getPlaceInHand(green.get(i1));
						i1++;
					}
				}else if (ma>=bl && ma>=cy && ma>=gr && ma>=re && ma>=ye && ma>=ci && ma>=cl && ma>=cr && ma>=di && ma>=sq && ma>=st) {
					int i1 = 0;
					while(i1 < magneta.size()) {
						move[i1][0] = 0;
						move[i1][1] = i;
						move[i1][2] = getPlaceInHand(magneta.get(i1));
						i1++;
					}
				}else if (re>=bl && re>=cy && re>=gr && re>=ma && re>=ye && re>=ci && re>=cl && re>=cr && re>=di && re>=sq && re>=st) {
					int i1 = 0;
					while(i1 < red.size()) {
						move[i1][0] = 0;
						move[i1][1] = i;
						move[i1][2] = getPlaceInHand(red.get(i1));
						i1++;
					}
				}else if (ye>=bl && ye>=cy && ye>=gr && ye>=ma && ye>=re && ye>=ci && ye>=cl && ye>=cr && ye>=di && ye>=sq && ye>=st) {
					int i1 = 0;
					while(i1 < yellow.size()) {
						move[i1][0] = 0;
						move[i1][1] = i;
						move[i1][2] = getPlaceInHand(yellow.get(i1));
						i1++;
					}
				}else if (ci>=bl && ci>=cy && ci>=gr && ci>=ma && ci>=re && ci>=ye && ci>=cl && ci>=cr && ci>=di && ci>=sq && ci>=st) {
					int i1 = 0;
					while(i1 < circle.size()) {
						move[i1][0] = 0;
						move[i1][1] = i;
						move[i1][2] = getPlaceInHand(circle.get(i1));
						i1++;
					}
				}else if (cl>=bl && cl>=cy && cl>=gr && cl>=ma && cl>=re && cl>=ye && cl>=ci && cl>=cr && cl>=di && cl>=sq && cl>=st) {
					int i1 = 0;
					while(i1 < club.size()) {
						move[i1][0] = 0;
						move[i1][1] = i;
						move[i1][2] = getPlaceInHand(club.get(i1));
						i1++;
					}
				}else if (cr>=bl && cr>=cy && cr>=gr && cr>=ma && cr>=re && cr>=ye && cr>=ci && cr>=cl && cr>=di && cr>=sq && cr>=st) {
					int i1 = 0;
					while(i1 < cross.size()) {
						move[i1][0] = 0;
						move[i1][1] = i;
						move[i1][2] = getPlaceInHand(cross.get(i1));
						i1++;
					}
				}else if (di>=bl && di>=cy && di>=gr && di>=ma && di>=re && di>=ye && di>=ci && di>=cl && di>=cr && di>=sq && di>=st) {
					int i1 = 0;
					while(i1 < diamond.size()) {
						move[i1][0] = 0;
						move[i1][1] = i;
						move[i1][2] = getPlaceInHand(diamond.get(i1));
						i1++;
					}
				}else if (sq>=bl && sq>=cy && sq>=gr && sq>=ma && sq>=re && sq>=ye && sq>=ci && sq>=cl && sq>=cr && sq>=sq && sq>=st) {
					int i1 = 0;
					while(i1 < square.size()) {
						move[i1][0] = 0;
						move[i1][1] = i;
						move[i1][2] = getPlaceInHand(square.get(i1));
						i1++;
					}
				}else if (st>=bl && st>=cy && st>=gr && st>=ma && st>=re && st>=ye && st>=ci && st>=cl && st>=cr && st>=st && st>=sq) {
					int i1 = 0;
					while(i1 < starburst.size()) {
						move[i1][0] = 0;
						move[i1][1] = i;
						move[i1][2] = getPlaceInHand(starburst.get(i1));
						i1++;
					}
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
		for (int i = 0; i < NROFTILESINHAND; i++) {
			System.out.println(hand[i].getColor() + " " + hand[i].getShape());
		}
		System.out.println(name + ": Aan de beurt");
		String[] splitInput = readChoice();
		/*String[] splitInput = new String[10];
		int j = 0;
		Scanner stdin = new Scanner(System.in);
		stdin.useDelimiter("-");
		System.out.println(name + ": AAN DE BEURT");
		while (stdin.hasNext()) {
			splitInput[j] = stdin.next();
			j++;
			//splitInput = input.split("-");
			//input = "";
		}
		stdin.close();*/

		for (int i = 0; i < (splitInput.length / 4); i++) {
			Tile tile = new Tile(getShape(splitInput[0+4*i].charAt(0)),getColor(splitInput[1+4*i].charAt(0)));
		move[i][2] = getPlaceInHand(tile);
		move[i][0] = Integer.parseInt(splitInput[2+4*i]);
		move[i][1] = Integer.parseInt(splitInput[3+4*i]);
		deepcopy.setField(move[i][0], move[i][1], hand[move[i][2]]);
		}
		testMove(move, deepcopy);
		
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
			/*sct.useDelimiter("-");
			while (sct.hasNext()) {
				temp[i] = sct.next();
				i++;
			}*/
			//sc.close();
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
	    	if (i == 'A') {
	    		return Tile.Shape.SQUARE;
	    	} else if (i == 'B') {
	    		return Tile.Shape.CIRCLE;
	    	} else if (i == 'C') {
	    		return Tile.Shape.DIAMOND;
	    	} else if (i == 'D') {
	    		return Tile.Shape.CLUB;
	    	} else if (i == 'E') {
	    		return Tile.Shape.CROSS;
	    	} else if (i == 'F') {
	    		return Tile.Shape.STARBURST;
	    	} else {
	    		return null;
	    	}
	    }
	   
	   public Tile.Color getColor(char i) {
	    	if (i == '1') {
	    		return Tile.Color.RED;
	    	} else if (i == '2') {
	    		return Tile.Color.GREEN;
	    	} else if (i == '3') {
	    		return Tile.Color.YELLOW;
	    	} else if (i == '4') {
	    		return Tile.Color.BLUE;
	    	} else if (i == '5') {
	    		return Tile.Color.MAGNETA;
	    	} else if (i == '6') {
	    		return Tile.Color.CYAN;
	    	} else {
	    		return null;
	    	}
	    }

	
}

