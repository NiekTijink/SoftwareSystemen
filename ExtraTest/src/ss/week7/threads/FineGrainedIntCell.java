package ss.week7.threads;

import java.util.concurrent.locks.*;

public class FineGrainedIntCell implements IntCell {
	private int value;
	public static Lock lock = new ReentrantLock();
	final Condition a = lock.newCondition();
	final Condition b = lock.newCondition();
	private Boolean available = false;

	public void setValue(int val) {
		lock.lock();
		try {
			while (available) {
				a.await();
			}
			value = val;
			available = true;
			b.signalAll();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public int getValue() {
		lock.lock();
		try {
			while (available == false) {
				b.wait();
			}
			available = false;
			a.signalAll();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
			return value;
		}
	}
}