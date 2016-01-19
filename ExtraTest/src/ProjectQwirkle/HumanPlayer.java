package ProjectQwirkle;

import java.util.ArrayList;

public class HumanPlayer extends Player {
	
	 private int move[][];
	 private int bound[];
	 private static int HANDSIZE = 6;
	 private int turnNr = 0;
	 private int choice;
	 private HumanTurn humanTurn;

	public HumanPlayer(String name, Deck deck) {
		super(name, deck);
	}
	
	public void makeMove(Board board, Tile[] hand) {
		if (turnNr == 0 && name.equals("Thomas")) {
			System.out.println("Board State :");
			bound = new int[4];
			bound[0] = 50; bound[1] = 50; bound[2] = 50; bound[3] = 50;
			System.out.println(board.toString(bound));
		} else {
			ArrayList<Coordinate> coordinates = super.getCoordinates(board);
			System.out.println("Board State :");
			System.out.println(board.toString(board.getBoundaries(coordinates)));
		}
		humanTurn = new HumanTurn(name, board, hand);
		humanTurn.makeTurn(board, hand, null);
		choice = humanTurn.getChoice();
		move = humanTurn.getMove();
		
		
    	/*if (turnNr == 0 && name.equals("Thomas")) {
    		firstTurn firstTurn = new firstTurn(name, board, hand);
    		firstTurn.makefirstTurn();
    		choice = firstTurn.getfirstChoice();
    		move = new int[HANDSIZE][3];
    		move = firstTurn.getfirstMove();  		
    	} else {
    		ArrayList<Coordinate> coordinates = getCoordinates(board);
    		System.out.println("Board State :");
    		System.out.println(board.toString(board.getBoundaries(coordinates)));
    		Turn turn = new Turn(name, turnNr, board, hand, coordinates);
    		turn.makeTurn();
    		choice = turn.getChoice();
    		move = new int[HANDSIZE][3];
    		move = turn.getMove();  	
    	}*/
        if (choice == 0) { // 0 houdt in dat je stenen gaat plaatsen
        	int i = 0;
        	while (move[i][0] != -1) {
        		board.setField(move[i][0], move[i][1], hand[move[i][2]]);
        		hand[move[i][2]] = null;
        		i++;
        	}
      	} else if (choice == 1) { // 1 houdt in dat je gaat swappen
        	int i = 0;
        	while (move[i][2] != -1) {
        		hand[move[i][2]] = deck.changeTile(hand[move[i][2]]);
        		i++;
       		}
       	} else { // altijd 0 of 1, anders opnieuw aanroepen (of foutmeldingen oid)
        	// TODO Auto-generated method stub
        }
        turnNr++;
    }

}
