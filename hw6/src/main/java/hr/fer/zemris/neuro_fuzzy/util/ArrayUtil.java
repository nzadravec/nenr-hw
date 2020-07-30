package hr.fer.zemris.neuro_fuzzy.util;

public class ArrayUtil {
	
	public static double[] add(double[] a, double[] b) {
		double[] c = new double[a.length];
		for(int i = 0; i < c.length; i++) {
			c[i] = a[i] + b[i];
		}
		
		return c;
	}
	
	public static double[] sub(double[] a, double[] b) {
		double[] c = new double[a.length];
		for(int i = 0; i < c.length; i++) {
			c[i] = a[i] - b[i];
		}
		
		return c;
	}
	
	public static double[] subi(double[] a, double[] b) {
		for(int i = 0; i < a.length; i++) {
			a[i] -= b[i];
		}
		
		return a;
	}
	
	public static void scalarMultiply(double scalar, double[][] array) {
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[i].length; j++) {
				array[i][j] *= scalar;
			}
		}
	}
	
	public static void scalarMultiply(double scalar, double[] array) {
		for(int i = 0; i < array.length; i++) {
			array[i] *= scalar;
		}
	}
	
	public static double dot(double[] a, double[] b) {
		double dotProduct = 0;
		for(int i = 0; i < a.length; i++) {
			dotProduct += a[i] * b[i];
		}
		
		return dotProduct;
	}

	public static void setToZero(double[] array) {
		for(int i = 0; i < array.length; i++) {
			array[i] = 0;
		}
	}
	
	public static void setToZero(double[][] array) {
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[i].length; j++) {
				array[i][j] = 0;
			}
		}
	}
	
	public static double sum(double[] array) {
		double sum = 0;
		for(int i = 0; i < array.length; i++) {
			sum += array[i];
		}
		
		return sum;
	}
	
}
