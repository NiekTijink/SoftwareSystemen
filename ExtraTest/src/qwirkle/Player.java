package qwirkle;


public class Player {
    private String name;
    private Tile[] hand;
    public final static int NROFTILESINHAND = 6;
    
    public Player(String name) {
    	this.name = name;
    	hand = new Tile[NROFTILESINHAND];
    }

    public String getName() {
    	return name;
    }
    
    public Tile[] getHand() {
    	return hand;
    }
    
    
    // makemove geeft de beslissingen die je maakt door aan het bord (dmv setfield of swap)
    // later zal makemove deze beslissingen door moeten geven aan de server dmv protocol
    public void makeMove(Board board, Tile[] hand) {
        int[] keuze = determineMove(board, hand);
        if (keuze[0] == 0) { // 0 houdt in dat je stenen gaat plaatsen
        	board.setField(keuze[1], keuze[2], hand[keuze[3]]);
        }/* else if (keuze[1] == 1) { // 1 houdt in dat je gaat swappen
        	board.swap(keuze) // swap moet nog gebouwd worden
        } else { // altijd 0 of 1, anders opnieuw aanroepen (of foutmeldingen oid)
        	determineMove(board,hand);
        }*/
    }
    
    // algoritme die berekend wat de beste optie is
    // misschien iets van een deepcopy maken
    public int[] determineMove(Board board, Tile[] hand) {
    	// hier moet dus ergens een algoritme komen
    	for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				board.getBoard()[i][j] = null;
			}
		}
    	int[] data = {0, 1,1,3};
    	// eerste getal: commando, tweede getal: xwaarde, derde getal: ywaarde, 
    	//vierde getal: nr van steen in je hand. (ligt altijd tussen 0 en 5)
    	//Dus 2 betekent hier dat je de (2+1 =) 3e steen uit je hand neerlegt
    	// misschien mogelijk om meteen meerdere stenen mee te geven tegelijk(234 = 1 steen)(567 volgende)
    	
    	return data;
    }
}
