package qwirkle;

import java.util.ArrayList;

import online.ClientHandler;
import protocol.Protocol;

public class HumanPlayer extends Player {
	
	 private int move[][];
	 private int bound[];
	 private int turnNr = 0;
	 private int choice;
	 private HumanTurn humanTurn;

	public HumanPlayer(String name, Deck deck) {
		super(name, deck);
	}
	
	public String makeMove(Board board, String msg) {
		if (!(msg.equals(ClientHandler.NOREPLY))) {
			HumanTurn ht = new HumanTurn(this, board);
			int tempscore = ht.determinemove(board, msg);
			if (tempscore <= 0) {
				return ClientHandler.NOREPLY;
			}
			addScore(tempscore);
		} else {
			return ClientHandler.NOREPLY;
		}
		int i = 0;
    	while (move[i][0] != -1) {
    		board.setField(move[i][0], move[i][1], this.getHand()[move[i][2]]);
    		this.getHand()[move[i][2]] = null;
    		i++;
    	}
    	String newStones = Protocol.Server.ADDTOHAND;
    	ArrayList<Tile> temp = updateHand();
    	for (Tile t : temp) {
        	newStones += "_" + t.getColor().getCharColor() + t.getShape().getCharShape();
    	}
    	return newStones;
	}
	public void makeMove(Board board) {
		if (turnNr == 0 && getName().equals("Thomas")) {
			System.out.println("Board State :");
			bound = new int[4];
			bound[0] = 50; bound[1] = 50; bound[2] = 50; bound[3] = 50;
			System.out.println(board.toString(bound));
		} else {
			ArrayList<Coordinate> coordinates = super.getCoordinates(board);
			System.out.println("Board State :");
			System.out.println(board.toString(board.getBoundaries(coordinates)));
		}
		humanTurn = new HumanTurn(this, board);
		humanTurn.makeTurn(board, null); // hoe zit dit?
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
        		board.setField(move[i][0], move[i][1], this.getHand()[move[i][2]]);
        		this.getHand()[move[i][2]] = null;
        		i++;
        	}
      	} else if (choice == 1) { // 1 houdt in dat je gaat swappen
        	int i = 0;
        	while (move[i][2] != -1) {
        		this.getHand()[move[i][2]] = getDeck().swapTile(this.getHand()[move[i][2]]);
        		i++;
       		}
       	} else { // altijd 0 of 1, anders opnieuw aanroepen (of foutmeldingen oid)
        	// TODO Auto-generated method stub
        }
        turnNr++;
    }

}
