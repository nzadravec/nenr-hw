package hr.fer.zemris.neuro_fuzzy;

public class AlgebraicProduct implements ITNorm {

	@Override
	public double tNorm(double... ds) {
		double output = 1;
		for(int i = 0; i < ds.length; i++) {
			if(ds[i] < 0 || ds[i] > 1) {
				throw new RuntimeException();
			}
			output *= ds[i];
		}
		return output;
	}

}
