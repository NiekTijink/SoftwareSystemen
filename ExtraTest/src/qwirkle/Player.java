package qwirkle;



public class Player {
    private String name;
    private Deck deck;
    private Tile[] hand;
    private int move[][];
    public static int HANDSIZE = 6;
    private int turnNr = 0;
    private int score;
       
    public Player(String name, Deck deck) {
    	this.name = name;
    	this.deck = deck;
    	this.hand = deck.drawHand();
    }
    
    public int getScore() {
    	return score;
    }
    
    public void addScore(int score) {
    	score += score;
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
    
    public Tile[] getHand() {
    	return hand;
    }
    
    public int getPlaceInHand(Tile tile) {
		for (int i = 0; i < HANDSIZE; i++) {
			if (hand[i].getShape() == tile.getShape() && hand[i].getColor() == tile.getColor()) {
				return i;
			}
		}
		return -1;
	}
    
    
    // makemove geeft de beslissingen die je maakt door aan het bord (dmv setfield of swap)
    // later zal makemove deze beslissingen door moeten geven aan de server dmv protocol
    public void makeMove(Board board, Tile[] hand) {
       	Turn turn = new Turn(name, turnNr, board, hand);
    	System.out.println("TEST");

    	turn.makeTurn();
    	int choice = turn.getChoice();
    	move = new int[HANDSIZE][3];
    	move = turn.getMove(); 
    	
        if (choice == 0 || turnNr == 0 && name.equals("Thomas")) { // 0 houdt in dat je stenen gaat plaatsen
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
