package ProjectQwirkleTest;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;
import ProjectQwirkle.*;
import ProjectQwirkle.Tile.Shape;

public class TestMoveTest {
	
	private static final int NROFTILESINHAND = 6;
	private static final int INDEXMOVE = 3;
	private Board b;
	private TestMove t;
	private int[][] move = new int[6][3];
	private Tile[] hand = new Tile[6];
	
	
	
	
	@Before
    public void setUp() {
		b = new Board();

        
    }
	
	@Test
	public void FirstMoveTest() {
		initMove();
		initHand2();
		move[0][0] = 50; move[0][1] = 50; move[0][2] = 0; move[1][0] = 50; move[1][1] = 51; move[1][2] = 1;
		t = new TestMove(b, move, hand);
		assertTrue(t.isLegalMove());
		t.isLegalMove();
	}
	
	@Test
	public void testVertical() {
		t.testVertical(50, 50, hand[1].getColor(), hand[1].getShape());
		fail("Not yet implemented");
	}
	
	@Test
	public void testHorizontal() {
		fail("Not yet implemented");
	}
	
	
	
	@Test
	public void SimpleMoveTest() {
		fail("Not yet implemented");
	}
	
	@Test
	public void qtest() {
		initMove();
		initHand1();
		initBoard();
		move[0][0] = 49; move[0][1] = 50; move[0][2] = 0; //move[1][0] = 50; move[1][1] = 51; move[1][2] = 1;
		t = new TestMove(b, move, hand);
		assertTrue(t.isLegalMove());
		t.isLegalMove();
		
	}
	
	@Test
	public void wtest() {
		fail("Not yet implemented");
	}
	
	private void initMove() {
		for (int i = 0; i < NROFTILESINHAND; i++) { // initialise
			for (int j = 0; j < INDEXMOVE; j++) {
				move[i][j] = -1;
			}
		}
		
	}
	private void initHand1() {
		hand[0] = new Tile('D','C');
		hand[1] = new Tile('A','B'); 
		/*for(Tile t : hand) {
			t = new Tile('A','A');
		}*/
		
	}
	
	private void initHand2() {
		hand[0] = new Tile('A','A');
		hand[1] = new Tile('A','B');
		hand[2] = new Tile('A','C');
		hand[3] = new Tile('A','D');
		hand[4] = new Tile('A','E');
		hand[5] = new Tile('A','F');		
	}
	
	
	private void initBoard() {
		b.setField(50, 49, new Tile('D', 'A'));
		b.setField(50, 50, new Tile('D', 'B'));
		b.setField(50, 51, new Tile('D', 'C'));
		b.setField(51, 51, new Tile('A', 'C'));
		//b.setField(49, 50, new Tile('A', 'C'));
	}

}
