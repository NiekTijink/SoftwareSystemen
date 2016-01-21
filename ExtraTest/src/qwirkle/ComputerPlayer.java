package qwirkle;

public class ComputerPlayer extends Player {

	public ComputerPlayer(Deck deck) {
		super("Computer", deck);
	}
	
	public String makeMove(Board board, String msg) {
		 return null;
	}
	
	public String makeFirstMove(Board board) {
		firstTurn ft = new firstTurn(this, board, this.getHand());
		ft.makefirstTurn();
		return ft.getFirstMoveString();
	}

}
