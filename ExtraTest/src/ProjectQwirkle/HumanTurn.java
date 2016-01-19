package ProjectQwirkle;

import java.util.ArrayList;
import java.util.Scanner;

public class HumanTurn extends Turn{

	public HumanTurn(String name, Board board, Tile[] hand) {
		super(name, board, hand);
	}
	
	public void determinemove(Board deepcopy, Tile[] hand, ArrayList<Coordinate> coordinates) {
		System.out.print("Player " + name + " Hand: ");
		for (int i = 0; i < NROFTILESINHAND; i++) {
			System.out.print(hand[i].toString() + "; ");
		}
		System.out.println("Enter Move:");
		String[] splitInput = readChoice();
		for (int i = 0; i < (splitInput.length / 4); i++) {
			Tile tile = new Tile(splitInput[0+4*i].charAt(0),splitInput[1+4*i].charAt(0));
		move[i][2] = getPlaceInHand(tile);
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
		int i = 0;
		while (sc.hasNextLine()) {
			String s = sc.nextLine();
			temp = s.split("-");
			return temp;
		}
		return null;
	}
	
	
	
	 


}
