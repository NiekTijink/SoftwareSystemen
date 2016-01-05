package ss.week7.threads;

public class TestConsole extends Thread {

	public void run() {
		sum();
	}

	private void sum() {
		int nr1 = Console.readInt(getName() + ": get number 1? ");
		int nr2 = Console.readInt(getName() + ": get number 2? ");
		String sum = String.valueOf(nr1 + nr2);
		Console.println(getName() + ": " + nr1 + " + " + nr2 + " = " + sum);
		
		
	}
	
	public static void main(String[] args) {
		String A = "Thread A";
		Thread a = new Thread(new TestConsole(),A);
		//a.setName(A);
		a.start();
		System.out.println(a.getName());
		//Thread b = new Thread(new TestConsole(), "Thread B");
		//b.start();
		
	}
}

