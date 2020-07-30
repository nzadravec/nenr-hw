package hr.fer.zemris.genetics.function;

import hr.fer.zemris.genetics.decoder.IDecoder;

import static java.lang.Math.*;

public class Function6<G> implements FitnessFunction<G> {
	
	private IDecoder<G> decoder;

	public Function6(IDecoder<G> decoder) {
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
		
		double numerator = pow(sin(sqrt(sumOfSquares)), 2)-0.5;
		double denominator = pow(1+0.001*sumOfSquares, 2);
		
		return (0.5 + numerator / denominator);
	}

}
