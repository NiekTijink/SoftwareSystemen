package ss.week6.voteMachine;

import java.util.*;

public class PartyList extends Observable {
	 List<String> partyList;


	public PartyList() {
		partyList = new ArrayList<String>();
	}
	
	public void addParty(String party) {
		partyList.add(party);
		setChanged();
		notifyObservers("party");
	}
	
	public boolean hasParty(String party) {
		return partyList.contains(party);
	}
	
	public List<String> getParties() {
		return partyList;
	}
}
