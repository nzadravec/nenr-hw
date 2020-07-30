package hr.fer.zemris.genetics.condition;

import hr.fer.zemris.genetics.individual.Individual;

public class FixedGenerationCount<G> implements StopCondition<G> {
	
	private int maxGenerations;
	private int numGenerations = 0;
	
	public FixedGenerationCount(int maxGenerations) {
		super();
		this.maxGenerations = maxGenerations;
	}

	@Override
	public boolean isSatisfied(Individual<G>[] population) {
		numGenerations++;
		return numGenerations > maxGenerations;
	}

}
