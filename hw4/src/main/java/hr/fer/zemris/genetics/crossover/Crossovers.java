package hr.fer.zemris.genetics.crossover;

import java.util.ArrayList;

import hr.fer.zemris.genetics.GA;
import hr.fer.zemris.genetics.individual.IndividualPair;

public class Crossovers<G> implements ICrossover<G> {
	
	private ArrayList<ICrossover<G>> crossovers;
	
	public Crossovers(ArrayList<ICrossover<G>> crossovers) {
		this.crossovers = crossovers;
	}

	@Override
	public IndividualPair<G> crossover(G genotype1, G genotype2) {
		ICrossover<G> crossover = crossovers.get(GA.rand.nextInt(crossovers.size()));
		return crossover.crossover(genotype1, genotype2);
	}

}
