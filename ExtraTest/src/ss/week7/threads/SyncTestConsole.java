package ss.week7.threads;

public class SyncTestConsole extends Thread {
	
	public class SyncTestConsole(String name) {
		super(name);
	}

	public void run() {
		sum();
	}

	private void sum() {
		
		int nr1 = SyncConsole.readInt(getName() + ": get number 1? ");
		int nr2 = SyncConsole.readInt(getName() + ": get number 2? ");
		String sum = String.valueOf(nr1 + nr2);
		SyncConsole.println(getName() + ": " + nr1 + " + " + nr2 + " = " + sum);
		
		
	}
	
	public static void main(String[] args) {
		String A = "Thread A";
		Thread a = new Thread(,A);
		//a.setName(A);
		a.start();
		//System.out.println(a.getName());
		Thread b = new Thread(new SyncTestConsole(), "Thread B");
		b.start();
		
	}
}

