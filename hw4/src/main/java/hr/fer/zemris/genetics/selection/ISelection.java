package hr.fer.zemris.genetics.selection;

import hr.fer.zemris.genetics.individual.Individual;

public interface ISelection<G> {
	Individual<G> select(Individual<G>[] population);
}
