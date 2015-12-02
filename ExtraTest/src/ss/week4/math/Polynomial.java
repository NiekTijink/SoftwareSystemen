package ss.week4.math;

public class Polynomial implements Function, Integrandable {
	private Function[] linearProduct;
	
	public Polynomial(double[] arr) {
		for (int i = 0; i < arr.length; i++) {
			linearProduct[i] = new LinearProduct(new Exponent(i), new Constant(arr[i]));
		}
	}
	
	public double apply(double num) {
		double sum = 0;
		for (int i = 0; i < linearProduct.length; i++) {
			sum = sum + linearProduct[i].apply(num);
		}
		return sum;
	}
	
	public Function derivative() {
		Function sum = null;
		for (int i = 0; i < linearProduct.length; i++) {
			sum = new Sum(sum, linearProduct[i].derivative());
		}
		return sum;
	}
	
	public Function integrand() {
		Function sum = null;
		for (int i = 0; i < linearProduct.length; i++) {
			sum = new Sum(sum, linearProduct[i].integrand());
		}
		return sum;
	}
}
	

