package hr.fer.zemris.genetics.crossover;

import hr.fer.zemris.genetics.individual.Individual;
import hr.fer.zemris.genetics.individual.IndividualPair;

public class PassThroughCrossover implements ICrossover<double[]> {

	@Override
	public IndividualPair<double[]> crossover(double[] genotype1, double[] genotype2) {
		return new IndividualPair<>(
				new Individual<double[]>(genotype1), new Individual<double[]>(genotype2));
	}

}
