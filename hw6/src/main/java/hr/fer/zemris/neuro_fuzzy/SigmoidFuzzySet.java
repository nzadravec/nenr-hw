package hr.fer.zemris.neuro_fuzzy;

public class SigmoidFuzzySet implements IFuzzySet {
	
	private double a;
	private double b;
	
	public SigmoidFuzzySet(double a, double b) {
		super();
		this.a = a;
		this.b = b;
	}

	@Override
	public double valueAt(double x) {
		return 1 / (1 + Math.exp(b*(x - a)));
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

}
