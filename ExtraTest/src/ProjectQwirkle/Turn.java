package ProjectQwirkle;

public class Turn {
	
	private int turnNr;
	private Board board;
	private Tile[] hand;
	private int choice;
	private int[][] move;

	public Turn(int turnNr, Board board, Tile[] hand) {
		this.turnNr = turnNr;
		this.board = board;
		this.hand = hand;
		
		move = new int[6][3];
		
		
	}
	
	public void makeTurn() {
		Board deepcopy = board.deepcopy();
		iniMove();
		if(turnNr == 0) {
			determinefirstmove(hand);
		} else {
			determinemove(deepcopy, hand);
		}
	}
	

	//Bepaal eerste move
	private void determinefirstmove(Tile[] hand) {
		//Kleur combinatie
		int i = 1;
		while(i < 6) {
			if(hand[0].getColor() == hand[i].getColor() && hand[0].getShape() != hand[i].getShape()) {
				
			}
			
		}
		//Shape combinatie
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
	
	private void iniMove() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 3; j++) {
				move[i][j] = 0;
			}
		}
	}

	
}
