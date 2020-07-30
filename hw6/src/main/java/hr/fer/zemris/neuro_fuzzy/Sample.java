package hr.fer.zemris.neuro_fuzzy;

import java.util.Arrays;

public class Sample {
	
	private double[] input;
	private double target;
	
	public Sample(double[] input, double target) {
		super();
		this.input = input;
		this.target = target;
	}

	public double[] getInput() {
		return input;
	}

	public double getTarget() {
		return target;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Arrays.toString(input));
		sb.append(target);
		
		return sb.toString();
	}

}

