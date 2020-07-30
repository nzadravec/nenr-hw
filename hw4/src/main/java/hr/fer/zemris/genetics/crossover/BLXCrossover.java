package hr.fer.zemris.genetics.crossover;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.Random;

import hr.fer.zemris.genetics.GA;
import hr.fer.zemris.genetics.individual.Individual;
import hr.fer.zemris.genetics.individual.IndividualPair;

public class BLXCrossover implements ICrossover<double[]> {

	private double alpha;
	
	public BLXCrossover(double alpha) {
		this.alpha = alpha;
	}
	
	@Override
	public IndividualPair<double[]> crossover(double[] genotype1, double[] genotype2) {
		double[] childsGenotype1 = new double[genotype1.length];
		Random rand = GA.rand;
		double max;
		double min;
		double I;
		double maxBound;
		double minBound;
		for(int i = 0; i < childsGenotype1.length; i++) {
			max = max(genotype1[i], genotype2[i]);
			min = min(genotype1[i], genotype2[i]);
			I = max - min;
			maxBound = max + I*alpha;
			minBound = min - I*alpha;
			childsGenotype1[i] = rand.nextDouble()*(maxBound - minBound) + minBound;
		}
		
		return new IndividualPair<>(
				new Individual<double[]>(childsGenotype1), null);
	}

}
