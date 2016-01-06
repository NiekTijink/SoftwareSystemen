package ss.week7.threads;

import java.nio.IntBuffer;

public class SynchronizedIntCell implements IntCell {
	private int value = 0;
	public boolean available = false;

	public synchronized void setValue(int valueArg) {
		while (available) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		value = valueArg;
		available = true;
		notifyAll();
	}

	
	public synchronized int getValue() {
		 while (available == false) {
			 try {
				 wait();
			 } catch (InterruptedException e) {
			 }
		 }
		 available = false;
		 notifyAll();
		 return value;
	}
		
}
