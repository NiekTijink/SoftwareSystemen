package qwirkle;

import java.util.Scanner;

import qwirkle.Tile.Color;
import qwirkle.Tile.Shape;

public class Turn {
	
	private int turnNr;
	private Board board;
	private Tile[] hand;
	private int choice; // 0 of 1 (leggen of swappen)
	private int[][] move;
	private final int NROFTILESINHAND = 6;

	public Turn(int turnNr, Board board, Tile[] hand) {
		this.turnNr = turnNr;
		this.board = board;
		this.hand = hand;
		move = new int[NROFTILESINHAND][3];
		
		for (int i = 0; i < 6; i++) { // initialise
			for (int j = 0; j < 3; j++) {
				move[i][j] = -1;
			}
		}
	}
	
	public void makeTurn() {
		Board deepcopy = board.deepcopy();
		/*if(turnNr == 0) {
			determinefirstmove(deepcopy, hand);
		} else {*/
			determinemove(deepcopy, hand);
		//}
	}
	
	public int testMove(int[][] move, Board b) {
		int score = b.testMove(move);
		System.out.println("score: " + score);
		return score; //geeft de score van de geteste move terug
	}

	//Bepaal eerste move
	private void determinefirstmove(Board deepcopy, Tile[] hand) {
		// TODO Auto-generated method stub
		
	}

	public int getPlaceInHand(Tile tile) {
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
		String[] splitInput = new String[10];
		String input = " ";
		Scanner stdin = new Scanner(System.in);
		System.out.println("AAN DE BEURT");
		while (!input.equals("") && stdin.hasNextLine()) {
			input = stdin.nextLine();
			splitInput = input.split("-");
			input = "";
		}
		Tile tile = new Tile(getShape(splitInput[0].charAt(0)),getColor(splitInput[1].charAt(0)));
		move[0][2] = getPlaceInHand(tile);
		move[0][0] = Integer.parseInt(splitInput[2]);
		move[0][1] = Integer.parseInt(splitInput[3]);
		deepcopy.setField(move[0][0], move[0][1], hand[move[0][2]]);
		
		testMove(move, deepcopy);
		stdin.close();
		
		// algoritme die berekend wat de beste optie is
	    // misschien iets van een deepcopy maken
	   	// hier moet dus ergens een algoritme komen
	    	//int[] data = {0, 1,1,3};
	    	// eerste getal: commando, tweede getal: xwaarde, derde getal: ywaarde, 
	    	//vierde getal: nr van steen in je hand. (ligt altijd tussen 0 en 5)
	    	//Dus 2 betekent hier dat je de (2+1 =) 3e steen uit je hand neerlegt
	    	// misschien mogelijk om meteen meerdere stenen mee te geven tegelijk(234 = 1 steen)(567 volgende)
	  
	    	
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

