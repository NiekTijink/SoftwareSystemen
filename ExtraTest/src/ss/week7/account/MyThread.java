package ss.week7.account;

public class MyThread implements Runnable {
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
	
	public void transaction() {
		for (int i = 1; i <= theFrequency; i++) {		
			theAccount.transaction(theAmount);			
			if (i == 1000) {
				System.out.println(theAccount.getBalance());
			}
		}
	}
}
