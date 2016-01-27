package qwirkleTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import exception.OutOfSyncException;
import qwirkle.*;

public class firstTurnTest {
	 
	private static final int NROFTILESINHAND = 6;
	private static final int INDEXMOVE = 3;
	private Board b;
	private ComputerPlayer p;
	private Tile[] hand;
	
	@Before
    public void setUp() throws OutOfSyncException {
		p = new ComputerPlayer("Computer");
		p.addToHand("AE_DA_DB_CC_CA_DF");
	}

	@Test
	public void test() {
		FirstTurn ft = new FirstTurn(p);
		ft.makefirstTurn();
		//int[][] move1 = initmove1();
		//int[][] move2 = ft.getFirstMove();
		String s1 = ("MAKEMOVE_DA*0*0_DB*0*-1_DF*0*-2");
		String s2 = ft.getFirstMoveString();
		assertEquals(s1 ,s2);
	
	}
	/*
	private int[][] initmove1() {
		int[][] result = new int[NROFTILESINHAND][INDEXMOVE];
		for (int i = 0; i < NROFTILESINHAND; i++) { 
			for (int j = 0; j < INDEXMOVE; j++) {
				result[i][j] = -1;
			}
		}
		result[0][0] = 75; result[0][1] = 75; result[0][2] = 1;
		result[1][0] = 75; result[1][1] = 76; result[1][2] = 2;
		result[2][0] = 75; result[2][1] = 77; result[2][2] = 5;
		return result;	
		}*/
	
	

}
