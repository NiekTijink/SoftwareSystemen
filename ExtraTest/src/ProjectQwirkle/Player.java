package ProjectQwirkle;

import java.util.ArrayList;

public class Player {
    private String name;
    private Deck deck;
    private Tile[] hand;
    private int move[][];
    private static int HANDSIZE = 6;
    private int turnNr = 0;
    private int choice;
       
    public Player(String name, Deck deck) {
    	this.name = name;
    	this.deck = deck;
    	this.hand = deck.drawHand();
    }

	public String getName() {
    	return name;
    }
    
    public void updateHand() {
		for (int i = 0; i < HANDSIZE; i++) {
			if (hand[i] == null) {
				hand[i] = deck.drawTile();
			}
		}
		
	}
    
    /*public Tile[] drawHand() {
    	return deck.drawHand();
    }*/
    
    public Tile[] getHand() {
    	return hand;
    }
    
    
    // makemove geeft de beslissingen die je maakt door aan het bord (dmv setfield of swap)
    // later zal makemove deze beslissingen door moeten geven aan de server dmv protocol
    public void makeMove(Board board, Tile[] hand) {
    	if (turnNr == 0 && name.equals("Thomas")) {
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
    	}
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
    
    private ArrayList<Coordinate> getCoordinates(Board board) {
       	final int MAXHALFARRAY = (50-2);
    	final int ARRAYORGINX = 50;
    	final int ARRAYORGINY = 50;
    	ArrayList<Coordinate> possiblecoor = new ArrayList<Coordinate>();
    	Tile[][] fields = board.getBoard();
	
	for(int i = 0; i <= MAXHALFARRAY; i++) {
		for(int k = 0; k <= (2*i); k++) {
			if (fields[(ARRAYORGINX - i) + k][ARRAYORGINY + i] == null && movePossible(fields,(ARRAYORGINX - i) + k, ARRAYORGINY + i)) {
				possiblecoor.add(new Coordinate((ARRAYORGINX - i) + k, ARRAYORGINY + i));
			}
			if (fields[(ARRAYORGINX - i) + k][ARRAYORGINY - i] == null && movePossible(fields,(ARRAYORGINX - i) + k, ARRAYORGINY - i)) {
				possiblecoor.add(new Coordinate((ARRAYORGINX - i) + k, ARRAYORGINY - i));
			}
		}
		for (int k = 1; k < (2*i); k++) {
			if (fields[ARRAYORGINX - i][(ARRAYORGINY + i) - k] == null && movePossible(fields,(ARRAYORGINX - i) , (ARRAYORGINY + i) - k)) {
				possiblecoor.add(new Coordinate((ARRAYORGINX - i) , (ARRAYORGINY + i) - k));
			}
			if (fields[ARRAYORGINX + i][(ARRAYORGINY + i) - k] == null && movePossible(fields,(ARRAYORGINX + i) , (ARRAYORGINY + i) - k)) {
				possiblecoor.add(new Coordinate((ARRAYORGINX + i) , (ARRAYORGINY + i) - k));
			}
			
			}
		}
		return possiblecoor;
    }
    
    private boolean movePossible(Tile[][] fields, int i, int j) {
		if(fields[i][j+1] != null || fields[i][j-1] != null || fields[i-1][j] != null || fields[i+1][j] != null ) {
			return true;
		} else {
		return false;
		}
	}
}
