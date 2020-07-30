package hr.fer.zemris.genetics.util;

import java.util.Arrays;
import java.util.Random;

public class ArrayUtil {
	
	public static double median(double[] array) {
		
		Arrays.sort(array);
		double pos1 = Math.floor((array.length - 1.0) / 2.0);
	    double pos2 = Math.ceil((array.length - 1.0) / 2.0);
	    if (pos1 == pos2 ) {
	    	return array[(int)pos1];
	    } else {
	    	return (array[(int)pos1] + array[(int)pos2]) / 2.0 ;
	    }
	}
	
	public static boolean[] byteToBooleanArray(byte[] byteArray) {
		boolean[] booleanArray = new boolean[byteArray.length];
		for(int i = 0; i < booleanArray.length; i++) {
			if(byteArray[i] == 1) {
				booleanArray[i] = true;
			} else {
				booleanArray[i] = false;
			}
		}
		
		return booleanArray;
	}
	
	public static double[] linspace(double min, double max, int points) {
		double[] d = new double[points];  
	    for (int i = 0; i < points; i++){  
	        d[i] = min + i * (max - min) / (points - 1);  
	    }  
	    return d;
	}
	
	public static void linearFillArray(int[] array) {
		for(int i = 0; i < array.length; i++) {
			array[i] = i;
		}
	}
	
	public static void shuffleArray(int[] array, Random rand) {
		for (int i = array.length; i > 1; i--) {
			int b = rand.nextInt(i);
			if (b != i - 1) {
				int e = array[i - 1];
				array[i - 1] = array[b];
				array[b] = e;
			}
		}
	}

}
