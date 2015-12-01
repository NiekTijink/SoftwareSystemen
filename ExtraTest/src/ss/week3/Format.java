package ss.week3;

public class Format { 

	public static String printLine(String text, double amount) {
		return String.format("%-20s %14.2f", text, amount);
	}

	public static void main(String[] args) {
		System.out.println(printLine("text1", 1));
		System.out.println(printLine("other text", -12.12));
		System.out.println(printLine("something", 0.2));
	}
}