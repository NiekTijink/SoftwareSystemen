package ss.week6.voteMachine;
import java.util.*;
import ss.week6.voteMachine.gui.*;

public class VoteMachine {
	private PartyList partyList;
	private VoteList voteList;
	private VoteView voteView;
	
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
	}
	
	public List<String> getPartyList() {
		return partyList.getParties();
	}
	
	public Map<String,Integer> getVoteList() {
		return voteList.getVotes();
	}
	
	public void start() {
		this.voteView = new VoteGUIView(this);
		voteList.addObserver(voteView);
		partyList.addObserver(voteView);
		voteView.start();
	}

	public static void main(String[] args) {
		VoteMachine voteMachine = new VoteMachine();
		voteMachine.start();

	}

}
