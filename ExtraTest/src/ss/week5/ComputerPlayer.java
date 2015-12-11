package ss.week5;

import ss.week4.tictactoe.*;

public class ComputerPlayer extends Player{
	private Strategy strategy;
	
	public ComputerPlayer(Mark mark, Strategy strategy) {
		super(strategy + " - " + mark, mark);
		this.strategy = strategy;
	}
	
	public ComputerPlayer(Mark mark) {
		super("NaiveStrategy - " + mark, mark);
		this.strategy = new NaiveStrategy();
	}
	
	
    public int determineMove(Board board){
    	return strategy.determineMove(board, super.getMark());
    }
    
    public Strategy getStrategy() {
    	return strategy;
    }
    
}
