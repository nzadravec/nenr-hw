package hr.fer.zemris.genetics.function;

public class NegatedFunction<G> implements FitnessFunction<G> {
	
	private FitnessFunction<G> function;
	
	public NegatedFunction(FitnessFunction<G> function) {
		this.function = function;
	}
	
	@Override
	public double fitnessOf(G genotype) {
		return -function.fitnessOf(genotype);
	}

}
