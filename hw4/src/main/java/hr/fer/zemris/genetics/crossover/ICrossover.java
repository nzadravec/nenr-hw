package hr.fer.zemris.genetics.crossover;

import hr.fer.zemris.genetics.individual.IndividualPair;

public interface ICrossover<G> {
	IndividualPair<G> crossover(G genotype1, G genotype2);
}
