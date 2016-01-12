package qwirkle;

import java.util.ArrayList;

public class TestMove {
	private Tile[][] fields;
	
	public TestMove(Tile[][] tiles) {
		fields = tiles;
	}
	
	public boolean isLegalMove(int[][] move) { // mag maar in rij/kolom & moet andere stenen raken
		int i = 0;
		while (move[i][0] != -1) {
			if (!isLegalTile(move[i][0],move[i][1])) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isLegalTile(int xValue, int yValue) { // Nog niet genoeg, mag maar in 1 rij/kolom plaatsen
		Tile.Color color = fields[xValue][yValue].getColor();
		Tile.Shape shape = fields[xValue][yValue].getShape();
		return (testVertical(xValue, yValue, color, shape) && testHorizontal(xValue, yValue, color, shape));
	}
	
	public boolean testHorizontal(int xValue, int yValue, Tile.Color color, Tile.Shape shape) {
		int easternTiles = 0;
		char typeOfRow = ' ';
		ArrayList<Tile.Color> colors = new ArrayList<Tile.Color>();
		colors.add(color);
		ArrayList<Tile.Shape> shapes = new ArrayList<Tile.Shape>();
		shapes.add(shape);
		
		boolean doorgaan = true;
		int c = 0; //counter
		while (doorgaan) { // omhoog kijken
			c++;
			if (!(fields[xValue+c][yValue] == null)) { // kijken of vakje leeg is
				if (fields[xValue+c][yValue].getColor().equals(color) // kijken of kleur gelijk is
						&& !(shapes.contains(fields[xValue][yValue+c].getShape())) // als kleur gelijk is, vorm mag niet gelijk
						&& typeOfRow != 'S') {
					typeOfRow = 'C';
					colors.add(fields[xValue+c][yValue].getColor());
				} else if (fields[xValue][yValue+c].getShape().equals(shape) 
						&& !(shapes.contains(fields[xValue+c][yValue].getShape()))
						&& typeOfRow != 'C') {
					typeOfRow = 'S';
					shapes.add(fields[xValue+c][yValue].getShape());
				} else {
					return false;
				}
			doorgaan = false;
			}
		}
		easternTiles = c - 1;
		c = 0; //counter reset
		doorgaan = true;
		while (doorgaan) { //omlaag
			c++;
			if (!(fields[xValue-c][yValue] == null)) {
				if (fields[xValue-c][yValue].getColor().equals(color) // kijken of kleur gelijk is
						&& !(shapes.contains(fields[xValue-c][yValue].getShape())) // als kleur gelijk is, vorm mag niet gelijk
						&& typeOfRow != 'S') {
					colors.add(fields[xValue-c][yValue].getColor());
				} else if (fields[xValue-c][yValue].getShape().equals(shape) 
						&& !(shapes.contains(fields[xValue-c][yValue].getShape()))
						&& typeOfRow != 'C') {
				} else {
					return false;
				}
			} return (c + easternTiles > 1);
		}
		return true;
	}
	
	public boolean testVertical(int xValue, int yValue, Tile.Color color, Tile.Shape shape) {
		int northernTiles = 0;
		char typeOfRow = ' ';
		ArrayList<Tile.Color> colors = new ArrayList<Tile.Color>();
		colors.add(color);
		ArrayList<Tile.Shape> shapes = new ArrayList<Tile.Shape>();
		shapes.add(shape);
		
		boolean doorgaan = true;
		int c = 0; //counter
		while (doorgaan) { // omhoog kijken
			c++;
			if (!(fields[xValue][yValue + c] == null)) { // kijken of vakje leeg is
				if (fields[xValue][yValue + c].getColor().equals(color) // kijken of kleur gelijk is
						&& !(shapes.contains(fields[xValue][yValue + c].getShape())) // als kleur gelijk is, vorm mag niet gelijk
						&& typeOfRow != 'S') {
					typeOfRow = 'C';
					colors.add(fields[xValue][yValue + c].getColor());
				} else if (fields[xValue][yValue + c].getShape().equals(shape) 
						&& !(shapes.contains(fields[xValue][yValue + c].getShape()))
						&& typeOfRow != 'C') {
					typeOfRow = 'S';
					shapes.add(fields[xValue][yValue + c].getShape());
				} else {
					return false;
				}
			} else {
				doorgaan = false;
			}
		}
		northernTiles = c - 1;
		c = 0; //counter reset
		doorgaan = true;
		while (doorgaan) { //omlaag
			c++;
			if (!(fields[xValue][yValue - c] == null)) {
				if (fields[xValue][yValue - c].getColor().equals(color) // kijken of kleur gelijk is
						&& !(shapes.contains(fields[xValue][yValue - c].getShape())) // als kleur gelijk is, vorm mag niet gelijk
						&& typeOfRow != 'S') {
					colors.add(fields[xValue][yValue - c].getColor());
				} else if (fields[xValue][yValue - c].getShape().equals(shape) 
						&& !(shapes.contains(fields[xValue][yValue - c].getShape()))
						&& typeOfRow != 'C') {
				} else {
					return false;
				}
			} return (c + northernTiles > 1);
		}
		return true;
	}
}
