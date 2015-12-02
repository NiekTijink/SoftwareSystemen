package ss.week4.math;

public class LinearProduct extends Product implements Integrandable {
	private Function a;
	private Constant b;
	
	public LinearProduct(Function a, Constant b) {
		super(a, b); 
		this.a = a;
		this.b = b;
	}
	
	public Function derivative() {
		return new LinearProduct(a.derivative(), b);
	}
	
	public Function integrand() {
		return new LinearProduct(a.integrand(), b);
	}
	//TODO make toString() ???
}
