package qwirkleTest;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;
import qwirkle.*;


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
		initHand1();
		move[0][0] = 50; move[0][1] = 50; move[0][2] = 0; //move[1][0] = 50; move[1][1] = 51; move[1][2] = 1;
		t = new TestMove(b, move, hand);
		assertTrue(t.isLegalMove());
		assertEquals(1, t.getScore());
	}
	
	@Test
	public void InvalidMoveTest() {
		initMove();
		initHand1();
		move[0][0] = 75; move[0][1] = 75; move[0][2] = 0; 
		move[1][0] = 75; move[1][1] = 79; move[1][2] = 1;
		t = new TestMove(b, move, hand);
		assertFalse(t.isLegalMove());
		assertThat(1, not(t.getScore()));
	}
	
	@Test
	public void ComplicatedMoveTest() {
		initMove();
		initHand1();
		initBoard1();
		move[0][0] = 74; move[0][1] = 75; move[0][2] = 0; 
		move[1][0] = 74; move[1][1] = 76; move[1][2] = 1;
		t = new TestMove(b, move, hand);
		assertTrue(t.isLegalMove());
		assertEquals(9, t.getScore());	
	}
	
	@Test
	public void InvalidComplicatedMoveTest() {
		initMove();
		initHand1();
		initBoard2();
		move[0][0] = 76; move[0][1] = 75; move[0][2] = 3; 
		t = new TestMove(b, move, hand);
		assertFalse(t.isLegalMove());
		assertThat(1, not(t.getScore()));
	}
	
	@Test
	public void qwrikleTest() {
		initMove();
		initHand1();
		initBoard1();
		move[0][0] = 74; move[0][1] = 75; move[0][2] = 0; 
		move[1][0] = 71; move[1][1] = 75; move[1][2] = 1; 
		move[2][0] = 70; move[2][1] = 75; move[2][2] = 2;
		t = new TestMove(b, move, hand);
		assertTrue(t.isLegalMove());
		assertEquals(12, t.getScore());	
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
		hand[1] = new Tile('D','E');
		hand[2] = new Tile('D','F');
		hand[3] = new Tile('C','E');
	}
	
	private void initHand2() {
		hand[0] = new Tile('A','A');
		hand[1] = new Tile('A','B');
		hand[2] = new Tile('A','C');
		hand[3] = new Tile('A','D');
		hand[4] = new Tile('A','E');
		hand[5] = new Tile('A','F');		
	}
	
	
	private void initBoard1() {
		b.setField(75, 74, new Tile('D', 'A'));
		b.setField(75, 75, new Tile('D', 'B'));
		b.setField(75, 76, new Tile('D', 'C'));
		b.setField(76, 76, new Tile('D', 'F'));
		b.setField(77, 75, new Tile('B', 'B'));
		b.setField(73, 75, new Tile('D', 'A'));
		b.setField(72, 75, new Tile('D', 'D'));
	}
	
	private void initBoard2() {
		b.setField(75, 74, new Tile('D', 'A'));
		b.setField(75, 75, new Tile('D', 'B'));
		b.setField(75, 76, new Tile('D', 'C'));
		b.setField(76, 76, new Tile('A', 'C'));
		b.setField(77, 75, new Tile('B', 'B'));
		b.setField(73, 75, new Tile('D', 'A'));
		b.setField(72, 75, new Tile('D', 'D'));
	}


}
