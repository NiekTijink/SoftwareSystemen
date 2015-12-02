package ss.week4.math;

public class Constant implements Function, Integrandable {
	private double constant;
	
	public Constant(double constant) {
		this.constant = constant;
	}
	
	public double apply(double num) {
		return constant;
	}
	
	public Function derivative() {
		return new Constant(0);
	}
	
	public Function integrand() {
		return new LinearProduct(new Exponent(1), new Constant(constant));
	}
}
