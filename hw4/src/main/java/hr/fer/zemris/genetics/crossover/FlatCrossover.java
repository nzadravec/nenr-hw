package hr.fer.zemris.genetics.crossover;

import java.util.Random;

import hr.fer.zemris.genetics.GA;
import hr.fer.zemris.genetics.individual.Individual;
import hr.fer.zemris.genetics.individual.IndividualPair;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class FlatCrossover implements ICrossover<double[]> {

	@Override
	public IndividualPair<double[]> crossover(double[] genotype1, double[] genotype2) {
		double[] childsGenotype1 = new double[genotype1.length];
		Random rand = GA.rand;
		for(int i = 0; i < childsGenotype1.length; i++) {
			double maxBound = max(genotype1[i], genotype2[i]);
			double minBound = min(genotype1[i], genotype2[i]);
			childsGenotype1[i] = rand.nextDouble()*(maxBound - minBound) + minBound;
		}
		
		return new IndividualPair<>(new Individual<double[]>(childsGenotype1), null);
	}

}
