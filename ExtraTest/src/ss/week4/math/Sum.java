package ss.week4.math;

public class Sum implements Function, Integrandable {
	private Function a;
	private Function b;
	
	public Sum(Function a, Function b) {
		this.a = a;
		this.b = b;
	}
	
	public double apply(double num) {
		return a.apply(num) + b.apply(num);
	}
	
	public Function derivative() {
		return new Sum(a.derivative(), b.derivative());
	}
	
	public Function integrand() {
		return new Sum(a.integrand(), b.integrand());
	}
}
