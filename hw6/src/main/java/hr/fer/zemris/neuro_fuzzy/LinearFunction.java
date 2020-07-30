package hr.fer.zemris.neuro_fuzzy;

public class LinearFunction implements IFunction {
	
	private double[] xs;
	
	public LinearFunction(double[] xs) {
		super();
		this.xs = xs;
	}

	@Override
	public double valueAt(double[] ds) {
		double value = 0;
		for(int i = 0; i < xs.length-1; i++) {
			value += xs[i] * ds[i];
		}
		value += xs[xs.length-1];
		
		return value;
	}

	public double[] getXs() {
		return xs;
	}

	public void setXs(double[] xs) {
		this.xs = xs;
	}
	
}
