package ss.week7.account;

public class Account {
	protected double balance = 0.0;

	public synchronized void transaction(double amount) {
		while (getBalance() + amount < -1000) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		balance = balance + amount;
		notifyAll();
	}
	public double getBalance() {
		return balance;

	}
}
