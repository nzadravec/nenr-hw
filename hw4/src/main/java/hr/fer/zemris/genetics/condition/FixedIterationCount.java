package hr.fer.zemris.genetics.condition;

import hr.fer.zemris.genetics.individual.Individual;

public class FixedIterationCount<G> implements StopCondition<G> {
	
	private int maxIters;
	private int numIter = 0;
	
	public FixedIterationCount(int maxIters) {
		super();
		this.maxIters = maxIters;
	}

	@Override
	public boolean isSatisfied(Individual<G>[] population) {
		numIter++;
		return numIter > maxIters;
	}

}
