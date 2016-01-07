package ss.week7.threads;


public class SynchronizedIntCell implements IntCell {
	private int value = 0;
	public boolean available = false;

		public synchronized int getValue() {
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        available = false;
        notifyAll();
        return value;
    }

    public synchronized void setValue(int valueArg) {
        while (available) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        this.value = valueArg;
        available = true;
        notifyAll();
    }
}

