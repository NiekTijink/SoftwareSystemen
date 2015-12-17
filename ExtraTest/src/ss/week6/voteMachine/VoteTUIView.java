package ss.week6.voteMachine;

import java.util.*;

public class VoteTUIView  implements Observer {
	private VoteMachine voteMachine;

	public VoteTUIView(VoteMachine voteMachine) {
		this.voteMachine = voteMachine;
	}
	
	public void start() {
		boolean doorgaan = true;
		Scanner sc = new Scanner(System.in);

		while (doorgaan) {
			System.out.println("Choose: VOTE [party], ADDPARTY [party], VOTES, PARTIES, HELP, EXIT");
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
			} else if (firstWord.equals("HELP")) {
				System.out.println("Type a command to vote, add a party, see the votelist, see the partylist, or exit the system");
			} else { 
				showError("invalid command");
			}	 
		}
		sc.close(); 
	}
	
	public void showError(String s) {
		System.out.println("error " + s);
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

	public void update(Observable o, Object arg) {
		if (arg.equals("vote")) {
			System.out.println("er is een stem uitgebracht"); // dit moet nog nuttig worden
		} else if (arg.equals("party")) {
			System.out.println("er is een partij toegevoegd");
		}
	}
}
