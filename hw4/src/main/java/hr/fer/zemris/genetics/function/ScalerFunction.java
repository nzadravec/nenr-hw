package hr.fer.zemris.genetics.function;

public class ScalerFunction<G> implements FitnessFunction<G> {

	private FitnessFunction<G> function;
	
	public ScalerFunction(FitnessFunction<G> function) {
		this.function = function;
	}
	
	@Override
	public double fitnessOf(G genotype) {
		return 1 / (1 + function.fitnessOf(genotype));
	}

}
