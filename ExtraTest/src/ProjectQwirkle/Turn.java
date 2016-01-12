package ProjectQwirkle;

import java.util.ArrayList;
import java.util.Arrays;

import ProjectQwirkle.Tile.Color;
import ProjectQwirkle.Tile.Shape;


public class Turn {
	
	private int turnNr;
	private Board board;
	private Tile[] hand;
	private int choice;
	private int[][] move;

	public Turn(int turnNr, Board board, Tile[] hand) {
		this.turnNr = turnNr;
		this.board = board;
		this.hand = hand;
		
		move = new int[6][3];
		
		
	}
	
	public void makeTurn() {
		Board deepcopy = board.deepcopy();
		iniMove();
		if(turnNr == 0) {
			choice = 0;
			determinefirstmove(hand);
		} else {
			determinemove(deepcopy, hand);
		}
	}
	
	private int getplaceInHand(Tile tile) {
		// TODO Auto-generated method stub
		return 0;
	}
	//Bepaal eerste move
	private void determinefirstmove(Tile[] hand) {
		//Kleur combinatie
		ArrayList<Tile.Shape> blueshape = new ArrayList<Tile.Shape>();
		ArrayList<Tile> blue = new ArrayList<Tile>();
		ArrayList<Tile.Shape> cyanshape = new ArrayList<Tile.Shape>();
		ArrayList<Tile> cyan = new ArrayList<Tile>();
		ArrayList<Tile.Shape> greenshape = new ArrayList<Tile.Shape>();
		ArrayList<Tile> green = new ArrayList<Tile>();
		ArrayList<Tile.Shape> magnetashape = new ArrayList<Tile.Shape>();
		ArrayList<Tile> magneta = new ArrayList<Tile>();
		ArrayList<Tile.Shape> redshape = new ArrayList<Tile.Shape>();
		ArrayList<Tile> red = new ArrayList<Tile>();
		ArrayList<Tile.Shape> yellowshape = new ArrayList<Tile.Shape>();
		ArrayList<Tile> yellow = new ArrayList<Tile>();
		int i = 1;
		while(i < 6) {
			if(hand[i].getColor() == Color.BLUE && !blueshape.contains(hand[i].getShape())) {
				blueshape.add(hand[0].getShape());
				blue.add(hand[i]);
			}else if(hand[i].getColor() == Color.CYAN && !cyanshape.contains(hand[i].getShape())) {
				cyanshape.add(hand[0].getShape());
				cyan.add(hand[i]);
			}else if(hand[i].getColor() == Color.GREEN&& !greenshape.contains(hand[i].getShape())) {
				greenshape.add(hand[0].getShape());
				green.add(hand[i]);
			}else if(hand[i].getColor() == Color.MAGNETA && !magnetashape.contains(hand[i].getShape())) {
				magnetashape.add(hand[0].getShape());
				magneta.add(hand[i]);
			}else if(hand[i].getColor() == Color.RED && !redshape.contains(hand[i].getShape())) {
				redshape.add(hand[0].getShape());
				red.add(hand[i]);
			}else if(hand[i].getColor() == Color.YELLOW && !yellowshape.contains(hand[i].getShape())) {
				yellowshape.add(hand[0].getShape());
				yellow.add(hand[i]);
			} i++;
		}
		//Shape combinatie
		ArrayList<Tile.Color> circlecolor = new ArrayList<Tile.Color>();
		ArrayList<Tile> circle = new ArrayList<Tile>();
		ArrayList<Tile.Color> clubcolor = new ArrayList<Tile.Color>();
		ArrayList<Tile> club = new ArrayList<Tile>();
		ArrayList<Tile.Color> crosscolor = new ArrayList<Tile.Color>();
		ArrayList<Tile> cross = new ArrayList<Tile>();
		ArrayList<Tile.Color> diamondcolor = new ArrayList<Tile.Color>();
		ArrayList<Tile> diamond = new ArrayList<Tile>();
		ArrayList<Tile.Color> squarecolor = new ArrayList<Tile.Color>();
		ArrayList<Tile> square = new ArrayList<Tile>();
		ArrayList<Tile.Color> starburstcolor = new ArrayList<Tile.Color>();
		ArrayList<Tile> starburst = new ArrayList<Tile>();
		int j = 1;
		while(j < 6) {
			if(hand[i].getShape() == Shape.CIRCLE && !circlecolor.contains(hand[i].getColor())) {
				circlecolor.add(hand[0].getColor());
				circle.add(hand[i]);
			}else if(hand[i].getShape() == Shape.CLUB && !clubcolor.contains(hand[i].getColor())) {
				clubcolor.add(hand[0].getColor());
				club.add(hand[i]);
			}else if(hand[i].getShape() == Shape.CROSS && !crosscolor.contains(hand[i].getColor())) {
				crosscolor.add(hand[0].getColor());
				cross.add(hand[i]);
			}else if(hand[i].getShape() == Shape.DIAMOND && !diamondcolor.contains(hand[i].getColor())) {
				diamondcolor.add(hand[0].getColor());
				diamond.add(hand[i]);
			}else if(hand[i].getShape() == Shape.SQUARE && !squarecolor.contains(hand[i].getColor())) {
				squarecolor.add(hand[0].getColor());
				square.add(hand[i]);
			}else if(hand[i].getShape() == Shape.STARBURST && !starburstcolor.contains(hand[i].getColor())) {
				starburstcolor.add(hand[0].getColor());
				starburst.add(hand[i]);
			} j++;
		}
		//Bepaal langste zet, door middel van langste ArrayList.
		int bl = blue.size();
		int cy = cyan.size();
		int gr = green.size();
		int ma =  magneta.size();
		int re = red.size();
		int ye = yellow.size();
		int ci = circle.size();
		int cl = club.size();
		int cr = cross.size();
		int di = diamond.size();
		int sq = square.size();
		int st = starburst.size();
		
		if(bl>=cy && bl>=gr && bl>=ma && bl>=re && bl>=ye && bl>=ci && bl>=cl && bl>=cr && bl>=di && bl>=sq && bl>=st) {
			int i1 = 0;
			while(i1 < blue.size()) {
				move[i][0] = 0;
				move[i][1] = i;
				move[i][2] = getplaceInHand(blue.get(i));
				i++;
			}
		}else if (cy>=bl && cy>=gr && cy>=ma && cy>=re && cy>=ye && cy>=ci && cy>=cl && cy>=cr && cy>=di && cy>=sq && cy>=st) {
			int i1 = 0;
			while(i1 < cyan.size()) {
				move[i][0] = 0;
				move[i][1] = i;
				move[i][2] = getplaceInHand(cyan.get(i));
				i++;
			}
		}else if (gr>=bl && gr>=cy && gr>=ma && gr>=re && gr>=ye && gr>=ci && gr>=cl && gr>=cr && gr>=di && gr>=sq && gr>=st) {
			int i1 = 0;
			while(i1 < green.size()) {
				move[i][0] = 0;
				move[i][1] = i;
				move[i][2] = getplaceInHand(green.get(i));
				i++;
			}
		}else if (ma>=bl && ma>=cy && ma>=gr && ma>=re && ma>=ye && ma>=ci && ma>=cl && ma>=cr && ma>=di && ma>=sq && ma>=st) {
			int i1 = 0;
			while(i1 < magneta.size()) {
				move[i][0] = 0;
				move[i][1] = i;
				move[i][2] = getplaceInHand(magneta.get(i));
				i++;
			}
		}else if (re>=bl && re>=cy && re>=gr && re>=ma && re>=ye && re>=ci && re>=cl && re>=cr && re>=di && re>=sq && re>=st) {
			int i1 = 0;
			while(i1 < red.size()) {
				move[i][0] = 0;
				move[i][1] = i;
				move[i][2] = getplaceInHand(red.get(i));
				i++;
			}
		}else if (ye>=bl && ye>=cy && ye>=gr && ye>=ma && ye>=re && ye>=ci && ye>=cl && ye>=cr && ye>=di && ye>=sq && ye>=st) {
			int i1 = 0;
			while(i1 < yellow.size()) {
				move[i][0] = 0;
				move[i][1] = i;
				move[i][2] = getplaceInHand(yellow.get(i));
				i++;
			}
		}else if (ci>=bl && ci>=cy && ci>=gr && ci>=ma && ci>=re && ci>=ye && ci>=cl && ci>=cr && ci>=di && ci>=sq && ci>=st) {
			int i1 = 0;
			while(i1 < circle.size()) {
				move[i][0] = 0;
				move[i][1] = i;
				move[i][2] = getplaceInHand(circle.get(i));
				i++;
			}
		}else if (cl>=bl && cl>=cy && cl>=gr && cl>=ma && cl>=re && cl>=ye && cl>=ci && cl>=cr && cl>=di && cl>=sq && cl>=st) {
			int i1 = 0;
			while(i1 < club.size()) {
				move[i][0] = 0;
				move[i][1] = i;
				move[i][2] = getplaceInHand(club.get(i));
				i++;
			}
		}else if (cr>=bl && cr>=cy && cr>=gr && cr>=ma && cr>=re && cr>=ye && cr>=ci && cr>=cl && cr>=di && cr>=sq && cr>=st) {
			int i1 = 0;
			while(i1 < cross.size()) {
				move[i][0] = 0;
				move[i][1] = i;
				move[i][2] = getplaceInHand(cross.get(i));
				i++;
			}
		}else if (di>=bl && di>=cy && di>=gr && di>=ma && di>=re && di>=ye && di>=ci && di>=cl && di>=cr && di>=sq && di>=st) {
			int i1 = 0;
			while(i1 < diamond.size()) {
				move[i][0] = 0;
				move[i][1] = i;
				move[i][2] = getplaceInHand(diamond.get(i));
				i++;
			}
		}else if (sq>=bl && sq>=cy && sq>=gr && sq>=ma && sq>=re && sq>=ye && sq>=ci && sq>=cl && sq>=cr && sq>=sq && sq>=st) {
			int i1 = 0;
			while(i1 < square.size()) {
				move[i][0] = 0;
				move[i][1] = i;
				move[i][2] = getplaceInHand(square.get(i));
				i++;
			}
		}else if (st>=bl && st>=cy && st>=gr && st>=ma && st>=re && st>=ye && st>=ci && st>=cl && st>=cr && st>=st && st>=sq) {
			int i1 = 0;
			while(i1 < starburst.size()) {
				move[i][0] = 0;
				move[i][1] = i;
				move[i][2] = getplaceInHand(starburst.get(i));
				i++;
			}
		}
		
	}

	
	private void determinemove(Board deepcopy, Tile[] hand) {
		// algoritme die berekend wat de beste optie is
	    // misschien iets van een deepcopy maken
	   	// hier moet dus ergens een algoritme komen
	    	//int[] data = {0, 1,1,3};
	    	// eerste getal: commando, tweede getal: xwaarde, derde getal: ywaarde, 
	    	//vierde getal: nr van steen in je hand. (ligt altijd tussen 0 en 5)
	    	//Dus 2 betekent hier dat je de (2+1 =) 3e steen uit je hand neerlegt
	    	// misschien mogelijk om meteen meerdere stenen mee te geven tegelijk(234 = 1 steen)(567 volgende)
	  
	    	
	    }
	
	public int[][] getMove() {
		return move;
			
	}

	public int getChoice() {
		// TODO Auto-generated method stub
		return choice;
	}
	
	private void iniMove() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 3; j++) {
				move[i][j] = 0;
			}
		}
	}

	
}
