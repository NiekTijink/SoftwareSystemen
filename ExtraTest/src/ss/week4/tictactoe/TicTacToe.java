package ss.week4.tictactoe;

/**
 * Executable class for the game Tic Tac Toe. The game can be played against the
 * computer. Lab assignment Module 2
 * 
 * @author Theo Ruys
 * @version $Revision: 1.4 $
 */
public class TicTacToe {
    public static void main(String[] args) {
    	HumanPlayer p = new HumanPlayer("Niek", Mark.XX);
    	HumanPlayer q = new HumanPlayer("Thomas", Mark.OO);
    	
        Game game = new Game(p,q);
        game.start();
    }
}