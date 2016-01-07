package ss.week7.account;

public class MyThread extends Thread {
	private double theAmount;
	private int theFrequency;
	private Account theAccount;

	public MyThread(double amount, int frequency, Account account) {
		this.theAmount = amount;
		this.theFrequency = frequency;
		this.theAccount = account;
	}
	
	public void run() {

		transaction();
	}
	
	public synchronized void transaction() {
		for (int i = 1; i <= theFrequency; i++) {	
			while (theAccount.getBalance() + theAmount > -1000) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			theAccount.transaction(theAmount);
			notifyAll();
			
			if (i == theFrequency) {
				System.out.println(theAccount.getBalance());
			}
		}
	}
}
