package hr.fer.zemris.genetics.function;

import hr.fer.zemris.genetics.decoder.IDecoder;

import static java.lang.Math.*;

public class Function7<G> implements FitnessFunction<G> {
	
	private IDecoder<G> decoder;

	public Function7(IDecoder<G> decoder) {
		super();
		this.decoder = decoder;
	}

	@Override
	public double fitnessOf(G genotype) {
		double[] x = decoder.decode(genotype);
		
		double sumOfSquares = 0;
		for(int i = 0; i < x.length; i++) {
			sumOfSquares += pow(x[i], 2);
		}
		
		return pow(sumOfSquares, 0.25)*(1 + pow(sin(50*pow(sumOfSquares, 0.1)), 2));
	}

}
