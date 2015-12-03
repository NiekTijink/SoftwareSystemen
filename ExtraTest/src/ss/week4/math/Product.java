package ss.week4.math;

public class Product implements Function {
	protected Function a;
	protected Function b;
	
	public Product(Function a, Function b) {
		this.a = a;
		this.b = b;
	}
	
	public double apply(double num) {
		return a.apply(num) * b.apply(num);
	}
	
	public Function derivative() {
		return new Sum(new Product(a.derivative(), b), new Product(b.derivative(), a));
	}
	
	public String toString() {
		return a + " * " + b;
	}
}
