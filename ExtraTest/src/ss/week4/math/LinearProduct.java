package ss.week4.math;

public class LinearProduct extends Product {
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
	
	//TODO make toString() ???
}
