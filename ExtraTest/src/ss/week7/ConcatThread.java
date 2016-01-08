package ss.week7;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcatThread extends Thread {
    private static String text = ""; // global variable
    private String toe;
    public static Lock lock = new ReentrantLock();
    private boolean available;

    public ConcatThread(String toeArg) {
        this.toe = toeArg;
    }

    public void run() {
    	lock.lock();
    	text = text.concat(toe);
		lock.unlock();
	}
    	
    public static void main(String[] args) {
        ConcatThread a = new ConcatThread("one;");
        ConcatThread b = new ConcatThread("two;");
        a.start();
        b.start();
        try {
            a.join();
            b.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(text);
    }
}
