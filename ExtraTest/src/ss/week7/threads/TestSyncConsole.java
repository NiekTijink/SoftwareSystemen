package ss.week7.threads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestSyncConsole extends Thread {
	static Lock l = new ReentrantLock();

	public TestSyncConsole(String name) {
		super(name);
	}

	public void run() {
		l.lock();
		sum();
		l.unlock();
	}
	
	private void  sum() {
		int nr1 = SyncConsole.readInt(getName() + ": get number 1? ");
		int nr2 = SyncConsole.readInt(getName() + ": get number 2? ");
		int sum = nr1 + nr2;
		SyncConsole.println(getName() + ": " + sum);
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
