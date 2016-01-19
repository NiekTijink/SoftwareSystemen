package qwirkle;

import java.util.ArrayList;
import java.util.Scanner;

import protocol.Protocol;

public class HumanTurn extends Turn{

	public HumanTurn(Player player, Board board) {
		super(player, board);
	}
	
	public int determinemove(Board b, String msg) {
		String[] split = msg.split(Character.toString(Protocol.Settings.DELIMITER));
		for (int i = 1; i < split.length; i++) {
			String[] splitInput = split[i].split(Character.toString(Protocol.Settings.DELIMITER2));
			Tile tile = new Tile(splitInput[0].charAt(0),splitInput[0].charAt(1));
			move[i][2] = player.getPlaceInHand(tile);
			move[i][0] = Integer.parseInt(splitInput[1]);
			move[i][1] = Integer.parseInt(splitInput[2]);
		}
		return testMove(move, board);
	}
	
	
	public void determinemove(Board deepcopy, ArrayList<Coordinate> coordinates) {
		System.out.print("Player " + player.getName() + " Hand: ");
		for (int i = 0; i < NROFTILESINHAND; i++) {
			System.out.print(player.getHand()[i].toString() + "; ");
		}
		System.out.println("Enter Move:");
		String[] splitInput = readChoice();
		for (int i = 0; i < (splitInput.length / 4); i++) {
			Tile tile = new Tile(splitInput[0+4*i].charAt(0),splitInput[1+4*i].charAt(0));
		move[i][2] = player.getPlaceInHand(tile);
		move[i][0] = Integer.parseInt(splitInput[2+4*i]);
		move[i][1] = Integer.parseInt(splitInput[3+4*i]);
		}
		
		if (choice == 0) {
			super.testMove(move, deepcopy);
		}	
	}
	
	public String[] readChoice() {
		Scanner sc = new Scanner(System.in);
		String[] temp;
		while (sc.hasNextLine()) {
			String s = sc.nextLine();
			temp = s.split("-");
			return temp;
		}
		return null;
	}
	
	
	
	 


}
