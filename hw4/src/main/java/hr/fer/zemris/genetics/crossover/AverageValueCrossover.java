package hr.fer.zemris.genetics.crossover;

import hr.fer.zemris.genetics.individual.Individual;
import hr.fer.zemris.genetics.individual.IndividualPair;

public class AverageValueCrossover implements ICrossover<double[]> {
	
	@Override
	public IndividualPair<double[]> crossover(double[] genotype1, double[] genotype2) {
		double[] childsGenotype = new double[genotype1.length];
		
		for(int i = 0; i < childsGenotype.length; i++) {
			childsGenotype[i] = (genotype1[i] + genotype2[i]) / 2;
		}
		
		return new IndividualPair<>(
				new Individual<double[]>(childsGenotype), new Individual<double[]>(null));
	}

}
