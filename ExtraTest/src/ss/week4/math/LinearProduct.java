package ss.week4.math;

public class LinearProduct extends Product implements Integrandable {
		
	public LinearProduct(Function a, Constant b) {
		super(a, b); 
		
	}
	
	public Function derivative() {
		return new LinearProduct(a.derivative(), (Constant) b);
	}
	
	public Function integrand() {
		if (a instanceof Integrandable) {
			return new LinearProduct(((Integrandable) a).integrand(), (Constant) b);
		} else {
			return null;
		}
	}
	
	//TODO make toString() ???
}
