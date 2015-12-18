package ss.week6;
import java.util.Scanner;

public class Hello {
	
	public void Scanner() {
		Scanner in = new Scanner(System.in);
		try {
			while (in.hasNext()) {
				String value = in.next();
				if (value.equals("exit")) {
					in.close();
					break;
				} else {
				System.out.println("hello " + value);	
				}
			}
		}finally {	
		in.close();
		
		}
	}
	
    public static void main(String[] args) {
    	Hello hello = new Hello();
    	hello.Scanner();
    }
	
}
