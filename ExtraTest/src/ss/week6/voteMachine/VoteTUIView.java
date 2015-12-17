package ss.week6.voteMachine;

import java.util.*;

public class VoteTUIView  {
	private VoteMachine voteMachine;

	public VoteTUIView() {
		voteMachine = new VoteMachine();
	}
	
	public void start() {
		boolean doorgaan = true;
		Scanner sc = new Scanner(System.in);

		while (doorgaan) {
			System.out.println("Choose: VOTE [party], ADDPARTY [party], VOTES, PARTIES, EXIT");
			String firstWord = sc.next();
			if (firstWord.equals("VOTE")) {
				voteMachine.vote(sc.next());
			} else if (firstWord.equals("ADDPARTY")) {
				voteMachine.addParty(sc.next());
			} else if (firstWord.equals("EXIT")) {
				doorgaan = false;
			} else if (firstWord.equals("VOTES")) {
				showVotes(voteMachine.getVoteList());
			} else if (firstWord.equals("PARTIES")) {
				showParties(voteMachine.getPartyList());
			} else { 
				System.out.println("error");
			}
			 
		}
		sc.close(); 

	}
	
	public void showVotes(Map<String,Integer> map) {
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String s = it.next();
			System.out.println(s + " " + map.get(s));
		}
		
		
	}
	
	public void showParties(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println("Party: " + list.get(i));
		}
	}
	
	public Map<String,Integer> getVotes() {
		return voteMachine.getVoteList();
	}

	
}
