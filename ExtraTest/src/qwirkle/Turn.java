package qwirkle;


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
		if(turnNr == 0) {
			determinefirstmove(deepcopy, hand);
		} else {
			determinemove(deepcopy, hand);
		}
	}
	
	public int testMove() {
		board.isLegalMove(move);
		
	}

	//Bepaal eerste move
	private void determinefirstmove(Board deepcopy, Tile[] hand) {
		// TODO Auto-generated method stub
		
	}

	
	private void determinemove(Board deepcopy, Tile[] hand) {
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
	
	

	
}

