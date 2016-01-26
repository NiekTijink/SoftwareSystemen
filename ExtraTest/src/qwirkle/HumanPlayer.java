package qwirkle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import online.ClientHandler;
import protocol.Protocol;

public class HumanPlayer extends Player {

	private int move[][];
	private int bound[];
	private int turnNr = 0;
	private int choice;
	private HumanTurn humanTurn;

	public HumanPlayer(String name) {
		super(name);
	}

	public String determineMove(Board board) {
		boolean valid = false;
		String antw = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			while (!valid) {
				System.out.println("Geef je move: ");
				System.out.println("Usage: <MAKEMOVE_MOVE_MOVE> or <CHANGESTONE_TILE_TILE>");
				antw = in.readLine();
				if (antw.startsWith(Protocol.Client.MAKEMOVE) || antw.startsWith(Protocol.Client.CHANGESTONE)) {
					valid = true;
				}
			}
		} catch (IOException e) {

		}

		return (antw == null) ? "" : antw;
	}

}
