package ss.week4.tictactoe;

import ss.week5.*;

/**
 * Executable class for the game Tic Tac Toe. The game can be played against the
 * computer. Lab assignment Module 2
 * 
 * @author Theo Ruys
 * @version $Revision: 1.4 $
 */
public class TicTacToe {
    public static void main(String[] args) {
    	Player p = null;
    	Player q = null;
    	if (args.length == 2) {
    		if (args[0].charAt(0) == '-') {
    			if (args[0].charAt(1) == 'N') {
    				p  = new ComputerPlayer(Mark.XX);
    			} else if (args[0].charAt(1) == 'S') {
    				p = new ComputerPlayer(Mark.XX, new SmartStrategy());
    			}
    		} else {
    			p = new HumanPlayer(args[0], Mark.XX);
    		}
    		
    		if (args[1].charAt(0) == '-') {
    			if (args[1].charAt(1) == 'N') {
    				q  = new ComputerPlayer(Mark.OO);
    			} else if (args[1].charAt(1) == 'S') {
    				q = new ComputerPlayer(Mark.OO, new SmartStrategy());
    			}
    		} else {
    			q = new HumanPlayer(args[1], Mark.OO);
    		}
    	}
    			
    	
        Game game = new Game(p,q);
        game.start();
    }
}