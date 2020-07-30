package hr.fer.zemris.genetics.function;

import hr.fer.zemris.genetics.decoder.IDecoder;
import hr.fer.zemris.genetics.genotype.neural.Dataset;
import hr.fer.zemris.genetics.genotype.neural.NeuralNetwork;

public class MSE<G> implements FitnessFunction<G> {
	
	private Dataset dataset;
	private IDecoder<G> decoder;
	private NeuralNetwork neural;
	
	public MSE(Dataset dataset, IDecoder<G> decoder, NeuralNetwork neural) {
		super();
		this.dataset = dataset;
		this.decoder = decoder;
		this.neural = neural;
	}

	@Override
	public double fitnessOf(G genotype) {
		double[] params = decoder.decode(genotype);
		return neural.calcError(dataset, params);
	}

}
