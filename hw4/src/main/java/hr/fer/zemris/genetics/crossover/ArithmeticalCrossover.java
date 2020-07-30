package hr.fer.zemris.genetics.crossover;

import java.util.Random;

import hr.fer.zemris.genetics.GA;
import hr.fer.zemris.genetics.individual.Individual;
import hr.fer.zemris.genetics.individual.IndividualPair;

public class ArithmeticalCrossover implements ICrossover<double[]> {

	@Override
	public IndividualPair<double[]> crossover(double[] genotype1, double[] genotype2) {
		double[] childsGenotype1 = new double[genotype1.length];
		double[] childsGenotype2 = new double[genotype1.length];
		Random rand = GA.rand;
		double lambda = rand.nextDouble();
		for(int i = 0; i < childsGenotype1.length; i++) {
			childsGenotype1[i] = lambda*genotype1[i] + (1 - lambda)*genotype2[i];
			childsGenotype2[i] = lambda*genotype2[i] + (1 - lambda)*genotype1[i];
		}
		
		return new IndividualPair<>(
				new Individual<double[]>(childsGenotype1), new Individual<double[]>(childsGenotype2));
	}

}
