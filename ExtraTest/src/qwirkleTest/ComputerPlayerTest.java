package qwirkleTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import exception.OutOfSyncException;
import qwirkle.*;

public class ComputerPlayerTest {

	private static final int NROFTILESINHAND = 6;
	private static final int INDEXMOVE = 3;
	private Board b;
	private ComputerPlayer p;
	private Tile[] hand;
	
	@Before
    public void setUp() throws OutOfSyncException {
		b = new Board();
		p = new ComputerPlayer("Computer");
		p.addToHand("AE_DA_DB_CC_CA_DF");
	}
	@Test
	public void qwirkleTest() {
		initBoard2();
		String s1 = ("MAKEMOVE_AE*50*54");
		String s2 = p.determineMove(b);
		assertEquals(s1,s2);
	}
	
	@Test
	public void swapTest() {
		initBoard();
		String s1 = ("CHANGESTONE_AE_DA_DB_CC_CA_DF");
		String s2 = p.determineMove(b);
		assertEquals(s1,s2);
	}
	
	private void initBoard() {
		b.setField(50, 50, new Tile('E', 'D'));
	}
	
	private void initBoard2() {
		b.setField(50, 50, new Tile('A', 'A'));
		b.setField(50, 51, new Tile('A', 'B'));
		b.setField(50, 52, new Tile('A', 'D'));
		b.setField(50, 53, new Tile('A', 'C'));
		b.setField(50, 49, new Tile('A', 'F'));
		b.setField(50, 47, new Tile('C', 'F'));
	} 
}
