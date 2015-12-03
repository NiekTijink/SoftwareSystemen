package ss.week4.math;

public class Polynomial implements Function, Integrandable {
	private Function[] linearProduct;
	private double[] arr;
	
	public Polynomial(double[] arr) {
		this.arr = arr;
		linearProduct = new Function[arr.length];
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
		double[] array;
		array = new double[linearProduct.length - 1];
		for (int i = 1; i < linearProduct.length; i++) {
			array[i - 1] =  arr[i] * i;
		}
		return new Polynomial(array);
	}
	
	public Function integrand() { 
		double[] array;
		array = new double[linearProduct.length + 1];
		array[0] = 0;
		for (int i = 0; i < linearProduct.length; i++) {
			array[i + 1] = arr[i] / (i + 1);
		}
		return new Polynomial(array);
	}
}
	

