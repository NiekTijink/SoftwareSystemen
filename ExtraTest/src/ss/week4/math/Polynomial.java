package ss.week4.math;

public class Polynomial implements Function{
	private double[] arr;
	private Function[][] linearProduct;
	
	public Polynomial(double[] arr) {
		this.arr = arr;
		for (int i = 0; i < arr.length; i++) {
			linearProduct[new Constant(arr[i])][new Exponent(i)];
		}
	}
	
	public double apply(num) {
		for (int i = 0; i < arr.length; i++) {
			
		}
	}
}
	

