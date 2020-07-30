package hr.fer.zemris.genetics.condition;

import hr.fer.zemris.genetics.individual.Individual;

public interface StopCondition<G> {
	
	boolean isSatisfied(Individual<G>[] population);

}
