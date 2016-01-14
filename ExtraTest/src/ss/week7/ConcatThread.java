package ss.week7;

import java.util.concurrent.locks.*;

public class ConcatThread extends Thread {
    private static String text = ""; // global variable
    private String toe;
    public final static Object MONITOR = new Object();

    public ConcatThread(String toeArg) {
        this.toe = toeArg;
    }
    
    @Override
    public void run() {
    	synchronized(MONITOR) {
    	while (toe.equals("two") && text.equals("")) {
    		try {
				MONITOR.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
        text = text.concat(toe);
    	MONITOR.notify();
    	}
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
