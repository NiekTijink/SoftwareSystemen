package ss.week7.threads;

public class TestSyncConsole extends Thread {
	public TestSyncConsole(String name) {
		super(name);
	}

	public void run() {
		sum();
	}
	
	private  void  sum() {
		synchronized(System.in) { 
		int nr1 = SyncConsole.readInt(getName() + ": get number 1? ");
		int nr2 = SyncConsole.readInt(getName() + ": get number 2? ");
		int sum = nr1 + nr2;
		String sum2 = sum + "";
		SyncConsole.println(getName() + ": " + sum2);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread a = new Thread(new TestSyncConsole("Thread A"));
		//System.out.println(a.getName());
		a.start();
		Thread b = new Thread(new TestSyncConsole("Thread B"));	
		b.start();

	}
}
