package ss.week7.threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class FinegrainedInCell implements IntCell {
		
	private int value = 0;
	public boolean available;
	
	private final ReentrantLock lock = new ReentrantLock();
    private final Condition a = lock.newCondition();
    private final Condition b = lock.newCondition();

	public synchronized int getValue() {
		lock.lock();
		try {
			while (available) {
				a.wait(); 
			}
			available = true;
			b.signalAll();
			
	        } catch (InterruptedException e) {
			} finally { lock.unlock(); 
	    }
		return value;
	}
	
	public synchronized void setValue(int valueArg) {
		lock.lock();
		try {
			while (!available) {
				b.wait(); 
			}
			this.value = valueArg;
			available = false;
			a.signalAll();
	        
			} catch (InterruptedException e) {
			} finally { lock.unlock(); 
	    }
	}


}

