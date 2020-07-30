package hr.fer.zemris.genetics.genotype.neural;

public class Sigmoid implements TransferFunction {

	@Override
	public double activation(double net) {
		return 1. / (1 + Math.exp(-net));
	}
	
}