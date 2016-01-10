package ProjectQwirkle;

public class Qwirkle {
	public static void main(String[] args) {
    	Player p = null;
    	Player q = null;
    	if (args.length == 2) {
    			p = new Player(args[0]);		
    			q = new Player(args[1]);
    		}
    				
    	Game game = new Game();
        game.start();
    }
}
