package ss.week7.account;

public class AccountSync {

	public static void main(String[] args) {
		Account account = new Account();
		Thread plus = new MyThread(10.0, 1000, account) ;
		Thread minus = new MyThread(-10.0, 1000, account);
		plus.start();
		minus.start();
	}
}
