package ss.week6.voteMachine;

import java.util.*;

public class VoteList {
	Map<String,Integer> voteList;
	
	public VoteList() {
		voteList = new HashMap<String,Integer>();
	}
	
	public Map<String,Integer> getVotes() {
		return voteList;
	}
	
	public boolean addVote(String party) {
		if (voteList.containsKey(party)) {
			voteList.replace(party, voteList.get(party), (voteList.get(party)+1));
			return true;
		} else {
			return false;
		}
		
	}

}
