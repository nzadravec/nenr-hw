package hr.fer.zemris.genetics.crossover;

import java.util.Random;

import hr.fer.zemris.genetics.GA;
import hr.fer.zemris.genetics.individual.Individual;
import hr.fer.zemris.genetics.individual.IndividualPair;

public class OnePointCrossover2 implements ICrossover<double[]> {

	@Override
	public IndividualPair<double[]> crossover(double[] genotype1, double[] genotype2) {
		double[] childsGenotype1 = new double[genotype1.length];
		double[] childsGenotype2 = new double[genotype1.length];
		Random rand = GA.rand;
		int point = rand.nextInt(childsGenotype1.length);
		for(int i = 0; i < point; i++) {
			childsGenotype1[i] = genotype1[i];
			childsGenotype2[i] = genotype2[i];
		}
		
		for(int i = point; i < childsGenotype1.length; i++) {
			childsGenotype1[i] = genotype2[i];
			childsGenotype2[i] = genotype1[i];
		}
		
		return new IndividualPair<>(
				new Individual<double[]>(childsGenotype1), new Individual<double[]>(childsGenotype2));
	}

}
