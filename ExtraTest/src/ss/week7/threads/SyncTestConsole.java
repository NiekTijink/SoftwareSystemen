package ss.week7.threads;

import java.util.concurrent.locks.ReentrantLock;

public class SyncTestConsole extends Thread {
	static ReentrantLock l = new ReentrantLock();
	public SyncTestConsole(String name) {
		super(name);
	}

	public void run() {
		l.lock();
		sum();
		l.unlock();
	}

	private void sum() {
		
		int nr1 = SyncConsole.readInt(getName() + ": get number 1? ");
		int nr2 = SyncConsole.readInt(getName() + ": get number 2? ");
		String sum = String.valueOf(nr1 + nr2);
		SyncConsole.println(getName() + ": " + nr1 + " + " + nr2 + " = " + sum);
		
	}
	
	public static void main(String[] args) {
		Thread a = new Thread(new SyncTestConsole("Thread A"));
		a.start();
		Thread b = new Thread(new SyncTestConsole("Thread B"));
		b.start();
		
	}
}

