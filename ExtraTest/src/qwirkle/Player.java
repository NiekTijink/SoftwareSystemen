package qwirkle;


public class Player {
    private String name;
    private Tile[] hand;
    public final static int NROFTILESINHAND = 6;
	private int score;
    
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
    
    public int getScore() {
    	return score;
    }
    
    // makemove geeft de beslissingen die je maakt door aan het bord (dmv setfield of changetiles)
    // later zal makemove deze beslissingen door moeten geven aan de server dmv protocol
    public void makeMove(Board board, Tile[] hand, Deck deck) {
        int[] keuze = determineMove(board, hand);
        if (keuze[0] == 0) { // 0 houdt in dat je stenen gaat plaatsen
        	for (int i = 0; i < ((keuze.length - 1) / 3); i++) {
        		// ALLE STENEN PLAATSEN
            	if (!(board.setField(keuze[1 + 3 * i], keuze[2 + 3 * i], hand[keuze[3 + 3 * i]]))) { // voor meerdere zetten
            		// error message (veld is niet leeg of veld bestaat niet)
            		// hoeft nu nog niet (omdat we eigen algoritme hebben), maar later moet de server dit wel checken
            	}
        	}
        	for (int i = 0; i < ((keuze.length - 1) / 3); i++) {
        		// CONTROLEREN OF HET EIGENLIJK WEL MAG
        		if (!(board.isLegalTile(keuze[1+3*i], keuze[2+3*i]))) {
        			// FOUTMELDING: MOVE MAG NIET. STENEN TERUGDRAAIEN EN ERROR GEVEN
        			for (int j = 0; j < ((keuze.length - 1) / 3); j++) {
        				board.deleteField(keuze[1 + 3 * j], keuze[2 + 3 * j]);
        			}
        		} else { // alles mag en alles is oke ==> punten berekenen
        			int[] coordinates = new int[(keuze.length-1)*2/3]; // x en y coordinaten opslaan
        			for (int j = 0; j < ((keuze.length-1)/3); j++) {
        				coordinates[j * 2] = keuze[1+j*3];
        				coordinates[1+j*2] = keuze[2+j*3];
        			}
    				score += board.calculateScore(coordinates);

        		}
        	}
 
        } else if (keuze[0] == 1 && keuze.length <= 7) { // 1 houdt in dat je gaat swappen
        	Tile[] tiles = new Tile[keuze.length - 1];
        	for (int i = 1; i < keuze.length; i++) {
        		tiles[i-1] = hand[keuze[i]];
        	}
    		Tile[] newTiles = deck.changeTile(tiles); // nieuwe stenen genereren
    		for (int i = 0; i < newTiles.length; i++) { // nieuwe stenen opslaan
    			hand[keuze[i+1]] = newTiles[i];
    		}
        } /*else { // altijd 0 of 1, anders opnieuw aanroepen (of foutmeldingen oid)
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
    	int[] data = {0, 1,2,3};
    	// eerste getal: commando, tweede getal: xwaarde, derde getal: ywaarde, 
    	//vierde getal: nr van steen in je hand. (ligt altijd tussen 0 en 5)
    	//Dus 2 betekent hier dat je de (2+1 =) 3e steen uit je hand neerlegt
    	// misschien mogelijk om meteen meerdere stenen mee te geven tegelijk(234 = 1 steen)(567 volgende)
    	
    	return data;
    }
}
