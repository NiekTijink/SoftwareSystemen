package qwirkleTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import qwirkle.*;

public class firstTurnTest {
	
	private static final int NROFTILESINHAND = 6;
	private static final int INDEXMOVE = 3;
	private Board b;
	private ComputerPlayer p;
	private Tile[] hand;
	
	@Before
    public void setUp() {
		b = new Board();
		p = new ComputerPlayer("Computer");
		p.addToHand("AE_DA_DB_CC_CA_DF");
	}

	@Test
	public void test() {
		firstTurn ft = new firstTurn(p,b);
		ft.makefirstTurn();
		int[][] move1 = initmove1();
		int[][] move2 = ft.getFirstMove();
		assertEquals(move1, move2);
		String s1 = ("MAKEMOVE_DA*50*50_DB*50*51_DF*50*52");
		String s2 = ft.getFirstMoveString();
		assertEquals(s1 ,s2);
	
	}

	private int[][] initmove1() {
		int[][] result = new int[NROFTILESINHAND][INDEXMOVE];
		for (int i = 0; i < NROFTILESINHAND; i++) { 
			for (int j = 0; j < INDEXMOVE; j++) {
				result[i][j] = -1;
			}
		}
		result[0][0] = 50; result[0][1] = 50; result[0][2] = 1;
		result[1][0] = 50; result[1][1] = 51; result[1][2] = 2;
		result[2][0] = 50; result[2][1] = 52; result[2][2] = 5;
		return result;
	}
	
	

}
