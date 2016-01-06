package qwirkle;


public class Player {
    private String name;
    private Tile[] hand;
    
    public Player(String name) {
    	this.name = name;
    }

    public String getName() {
    	return name;
    }
    
    public Tile[] getHand() {
    	return hand;
    }
    
    public void makeMove(Board board, Tile[] hand) {
        int[] keuze = determineMove(board, hand);
        board.setField(keuze[0], keuze[1], hand[keuze[2]]);
    }
    
    public int[] determineMove(Board board, Tile[] hand) {
    	for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				board.getBoard()[i][j] = null;
			}
		}
    }
}
