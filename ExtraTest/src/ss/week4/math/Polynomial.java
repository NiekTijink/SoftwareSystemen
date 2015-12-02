package ss.week4.math;

public class Polynomial implements Function, Integrandable {
	private Function[] linearProduct;

	
	public Polynomial(double[] arr) {
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
	
	public Function derivative() { //Dit is dus fout. Het is geen nieuwe som maar een polymial
		Function sum = linearProduct[1];
		for (int i = 2; i < linearProduct.length; i++) {
			sum = new Sum(sum, linearProduct[i].derivative());
			System.out.println(sum.apply(1));
		}
		return sum;
	}
	
	public Function integrand() { //Dit is dus fout. Het is geen nieuwe som maar een polymial
		Function sum = null;
		for (int i = 0; i < linearProduct.length; i++) {
			sum = new Sum(sum, ((Integrandable) linearProduct[i]).integrand());
		}
		return sum;
	}
}
	

