package ss.week6.voteMachine;

import java.util.*;

public class PartyList {
	 List<String> partyList;


	public PartyList() {
		partyList = new ArrayList<String>();
	}
	
	public void addParty(String party) {
		partyList.add(party);
	}
	
	public boolean hasParty(String party) {
		for (int i = 0; i < partyList.size(); i++) {
			if (partyList.get(i) == party) {
				return true;
			}
		}
		return false;
	}
	
	public List<String> getParties() {
		return partyList;
	}
}
