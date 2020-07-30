package hr.fer.zemris.genetics.crossover;

import hr.fer.zemris.genetics.individual.Individual;
import hr.fer.zemris.genetics.individual.IndividualPair;

public class UniformCrossover2 implements ICrossover<double[]> {

	@Override
	public IndividualPair<double[]> crossover(double[] genotype1, double[] genotype2) {
		double[] childsGenotype1 = new double[genotype1.length];
		double[] childsGenotype2 = new double[genotype1.length];

		for(int i = 0; i < childsGenotype1.length; i++) {
			if(i % 2 == 0) {
				childsGenotype1[i] = genotype1[i];
				childsGenotype2[i] = genotype2[i];
			} else {
				childsGenotype1[i] = genotype2[i];
				childsGenotype2[i] = genotype1[i];
			}
		}
		
		return new IndividualPair<>(
				new Individual<double[]>(childsGenotype1), new Individual<double[]>(childsGenotype2));
	}

}