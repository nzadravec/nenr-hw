package hr.fer.zemris.genetics.condition;

import hr.fer.zemris.genetics.individual.Individual;
import hr.fer.zemris.genetics.util.EvoUtil;

import static java.lang.Math.abs;

public class FixedPrecision<G> implements StopCondition<G> {
	
	private double epsilon;
	
	public FixedPrecision(double epsilon) {
		this.epsilon = epsilon;
	}

	@Override
	public boolean isSatisfied(Individual<G>[] population) {
		Individual<G> best = EvoUtil.max(population);
		return abs(best.fitness) < epsilon;
	}

}
