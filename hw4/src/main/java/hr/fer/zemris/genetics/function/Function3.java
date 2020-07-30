package hr.fer.zemris.genetics.function;

import hr.fer.zemris.genetics.decoder.IDecoder;

import static java.lang.Math.pow;

public class Function3<G> implements FitnessFunction<G> {
	
	private IDecoder<G> decoder;

	public Function3(IDecoder<G> decoder) {
		super();
		this.decoder = decoder;
	}

	@Override
	public double fitnessOf(G genotype) {
		double[] x = decoder.decode(genotype);
		double value = 0;
		for(int i = 1; i <= x.length; i++) {
			value += pow(x[i-1] - i, 2);
		}
		
		return value;
	}

}
