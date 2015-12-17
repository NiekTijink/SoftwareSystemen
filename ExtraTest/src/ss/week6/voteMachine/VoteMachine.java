package ss.week6.voteMachine;
import java.util.*;

public class VoteMachine {
	private PartyList partyList;
	private VoteList voteList;
	
	public VoteMachine() {
		partyList = new PartyList();
		voteList = new VoteList();
	}
	
	public void addParty(String party) {
		partyList.addParty(party);
		voteList.getVotes().put(party, 0);
	}
	
	public void vote(String party) {
		if (!(voteList.addVote(party))) {
			addParty(party);
			voteList.addVote(party);
		}
		System.out.println(voteList.getVotes().get(party));

	}
	public List<String> getPartyList() {
		return partyList.getParties();
	}
	
	public Map<String,Integer> getVoteList() {
		return voteList.getVotes();
	}
	
	public void start() {
		VoteTUIView test = new VoteTUIView();
		test.start();
	}

	public static void main(String[] args) {
		VoteMachine voteMachine = new VoteMachine();
		voteMachine.start();

	}

}
