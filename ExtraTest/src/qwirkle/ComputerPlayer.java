package qwirkle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name) {
		super(name);
	}
	
	public String makeMove(Board board, String msg) {
		 return "";
	}

	
	public String makeFirstMove(Board board) {
		firstTurn ft = new firstTurn(this, board, this.getHand());
		ft.makefirstTurn();
		return ft.getFirstMoveString();
	}

}
