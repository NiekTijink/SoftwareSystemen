package ss.week3;

public class Multiplication implements OperatorWithIdentity {
	
	public Multiplication() {
	}
	
	public int operate(int left, int right) {
		return left * right;
	}
	
	public int identity() {
		return 1;
	}
}
