package hr.fer.zemris.genetics.crossover;

import java.util.Random;

import hr.fer.zemris.genetics.GA;
import hr.fer.zemris.genetics.individual.Individual;
import hr.fer.zemris.genetics.individual.IndividualPair;

public class UniformCrossover implements ICrossover<boolean[]> {

	@Override
	public IndividualPair<boolean[]> crossover(boolean[] genotype1, boolean[] genotype2) {
		boolean[] childsGenotype1 = new boolean[genotype1.length];
		boolean[] childsGenotype2 = new boolean[genotype1.length];
		Random rand = GA.rand;
		for(int i = 0; i < childsGenotype1.length; i++) {
			if(genotype1[i] == genotype2[i]) {
				childsGenotype1[i] = genotype1[i];
				childsGenotype2[i] = genotype1[i];
			} else {
				boolean b = rand.nextBoolean();
				childsGenotype1[i] = b;
				childsGenotype2[i] = !b;
			}
		}
		
		return new IndividualPair<>(
				new Individual<boolean[]>(childsGenotype1), new Individual<boolean[]>(childsGenotype2));
	}

}
