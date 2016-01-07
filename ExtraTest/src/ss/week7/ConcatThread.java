package ss.week7;

import java.util.concurrent.locks.*;

public class ConcatThread extends Thread {
    private static String text = ""; // global variable
    private String toe;
    private static Lock lock = new ReentrantLock();

    public ConcatThread(String toeArg) {
        this.toe = toeArg;
    }

    public void run() {
    	while (toe == "two" && text == "") {
    		try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
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
