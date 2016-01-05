package ss.week7.threads;

public class TestConsole extends Thread {
	
	public TestConsole() {
	
	}

	public void run() {
		sum();
	}
	
	private void sum() {
		int nr1 = Console.readInt(getName() + ": get number 1? ");
		int nr2 = Console.readInt(getName() + ": get number 2? ");
		int sum = nr1 + nr2;
		Console.println(getName() + ": " + sum);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread a = new Thread(new TestConsole(), "Thread A");
		a.start();
		Thread b = new Thread(new TestConsole(), "Thread B");	
		b.start();

	}

}
