package hr.fer.zemris.genetics.mutation;

import java.util.Random;

import hr.fer.zemris.genetics.GA;

public class UniformMutation implements IMutation<double[]> {

	private double pm;
	private double minBound;
	private double maxBound;
	
	public UniformMutation(double pm, double minBound, double maxBound) {
		super();
		this.pm = pm;
		this.minBound = minBound;
		this.maxBound = maxBound;
	}

	@Override
	public void mutate(double[] genotype) {
		Random rand = GA.rand;
		for(int i = 0; i < genotype.length; i++) {
			if(rand.nextDouble() < pm) {
				genotype[i] = rand.nextDouble()*(maxBound - minBound) + minBound;
			}
		}
	}

}
