package hr.fer.zemris.genetics.function;

import hr.fer.zemris.genetics.decoder.IDecoder;

import static java.lang.Math.pow;

public class Function1<G> implements FitnessFunction<G> {

	private IDecoder<G> decoder;
	
	public Function1(IDecoder<G> decoder) {
		this.decoder = decoder;
	}

	@Override
	public double fitnessOf(G genotype) {
		double[] x = decoder.decode(genotype);
		double x1 = x[0];
		double x2 = x[1];
		return (100 * pow(x2 - pow(x1, 2), 2) + pow(1 - x1, 2));
	}

}
