package ss.week6;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Hello {
	
	public void Scanner() {
    	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		Scanner input = new Scanner(in);
		try {
			while (input.hasNext()) {
				String value = input.next();
				if (value.equals("exit")) {
					input.close();
					break;
				} else {
				System.out.println("hello " + value);	
				}
			}
		}finally {	
		input.close();
		
		}
	}
	
    public static void main(String[] args) {
    	Hello hello = new Hello();
    	hello.Scanner();
    }
	
}
