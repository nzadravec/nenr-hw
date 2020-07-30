package hr.fer.zemris.genetics.mutation;

import java.util.Random;

import hr.fer.zemris.genetics.GA;

public class SimpleMutation implements IMutation<boolean[]> {

	@Override
	public void mutate(boolean[] genotype) {
		Random rand = GA.rand;
		int index = rand.nextInt(genotype.length);
		genotype[index] = !genotype[index];
	}

}
