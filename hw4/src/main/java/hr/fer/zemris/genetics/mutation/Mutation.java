package hr.fer.zemris.genetics.mutation;

import java.util.Random;

import hr.fer.zemris.genetics.GA;

public class Mutation implements IMutation<boolean[]> {
	
	private double pm;

	public Mutation(double pm) {
		super();
		this.pm = pm;
	}

	@Override
	public void mutate(boolean[] genotype) {
		Random rand = GA.rand;
		for(int i = 0; i < genotype.length; i++) {
			if(rand.nextDouble() < pm) {
				genotype[i] = !genotype[i];
			}
		}
	}

}