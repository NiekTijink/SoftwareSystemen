package ss.week6;

import java.util.Scanner;

public class Words {
	public void word() {
		Scanner in = new Scanner(System.in);
		Boolean doorgaan = true;
		System.out.println("Line (or end):");
		while (doorgaan) {
			//System.out.println("Line (or end):");
			Scanner line = new Scanner(in.nextLine());
			int counter = 1;
			String first = line.next();
			
			if (first.equals("end")) {
				System.out.println("End of programme."); 
				doorgaan = false;
			} else {
				System.out.println("Word 1: " + first);
				while (line.hasNext()) {
					counter++;
					System.out.println("Word " + counter + ": " + line.next());
				}
			}
			
			
			line.close();
			if (doorgaan) {
				System.out.println("Line (or end):");
			}
		}
	}
    public static void main(String[] args) {
    	Words word = new Words();
    	word.word();
    }
}
