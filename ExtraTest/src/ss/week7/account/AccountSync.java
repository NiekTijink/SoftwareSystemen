package ss.week7.account;

public class AccountSync {

	public static void main(String[] args) {
		Account account = new Account();
		Runnable pluss = new MyThread(1000.0, 1000, account);
		Thread plus = new Thread(pluss);
		Runnable minuss = new MyThread(-1000.0, 1000, account);
		Thread minus = new Thread(minuss);
		plus.start();
		minus.start();
	}
}
