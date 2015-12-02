package ss.week4.math;

public class Sum implements Function {
	private Function a;
	private Function b;
	
	public Sum(Function a, Function b) {
		this.a = a;
		this.b = b;
	}
	
	public double apply(double num) {
		return a.apply(0) + b.apply(0);
	}
	
	public Function derivative() {
		return new Sum(a.derivative(), b.derivative());
	}
}
