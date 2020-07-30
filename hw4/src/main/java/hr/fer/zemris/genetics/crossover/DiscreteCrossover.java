package hr.fer.zemris.genetics.crossover;

import java.util.Random;

import hr.fer.zemris.genetics.GA;
import hr.fer.zemris.genetics.individual.Individual;
import hr.fer.zemris.genetics.individual.IndividualPair;

public class DiscreteCrossover implements ICrossover<double[]> {

	@Override
	public IndividualPair<double[]> crossover(double[] genotype1, double[] genotype2) {
		double[] childsGenotype1 = new double[genotype1.length];
		double[] childsGenotype2 = new double[genotype1.length];
		
		Random rand = GA.rand;
		for(int i = 0; i < childsGenotype1.length; i++) {
			if(rand.nextBoolean()) {
				childsGenotype1[i] = genotype1[i];
				childsGenotype2[i] = genotype2[i];
			} else {
				childsGenotype1[i] = genotype2[i];
				childsGenotype2[i] = genotype1[i];
			}
		}
		
		return new IndividualPair<double[]>(
				new Individual<double[]>(childsGenotype1), new Individual<double[]>(childsGenotype2));
	}

}
