package ss.week4.math;

public class Exponent implements Function, Integrandable {
	private int n;
	
	public Exponent(int n) {
		this.n = n;
	}
	
	public double apply(double num) {
		return Math.pow(num, n);
	}
	
	public Function derivative() {
		return new LinearProduct(new Exponent(n - 1), new Constant(n));
	}
	
	public Function integrand() {
		double m = n;
		return new LinearProduct(new Exponent(n + 1), new Constant(1 / (m + 1)));
	}
	
	public String toString() {
		return "x^" + n;
	}
}
