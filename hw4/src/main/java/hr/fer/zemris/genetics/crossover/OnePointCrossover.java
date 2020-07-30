package hr.fer.zemris.genetics.crossover;

import java.util.Random;

import hr.fer.zemris.genetics.GA;
import hr.fer.zemris.genetics.individual.Individual;
import hr.fer.zemris.genetics.individual.IndividualPair;

public class OnePointCrossover implements ICrossover<boolean[]> {

	@Override
	public IndividualPair<boolean[]> crossover(boolean[] genotype1, boolean[] genotype2) {
		boolean[] childsGenotype1 = new boolean[genotype1.length];
		boolean[] childsGenotype2 = new boolean[genotype1.length];
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
				new Individual<boolean[]>(childsGenotype1), new Individual<boolean[]>(childsGenotype2));
	}

}
