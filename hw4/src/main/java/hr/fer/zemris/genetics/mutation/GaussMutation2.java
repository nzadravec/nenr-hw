package hr.fer.zemris.genetics.mutation;

import java.util.Random;

import hr.fer.zemris.genetics.GA;

public class GaussMutation2 implements IMutation<double[]> {
	
	private double pm;
	private double constant;
	private double minBound;
	private double maxBound;
	
	public GaussMutation2(double pm, double constant) {
		this(pm, constant, -Double.MAX_VALUE, Double.MAX_VALUE);
	}

	public GaussMutation2(double pm, double constant, double minBound, double maxBound) {
		super();
		this.pm = pm;
		this.constant = constant;
		this.minBound = minBound;
		this.maxBound = maxBound;
	}

	@Override
	public void mutate(double[] genotype) {
		
		Random rand = GA.rand;
		for(int i = 0; i < genotype.length; i++) {
			if(rand.nextDouble() < pm) {
				genotype[i] = rand.nextGaussian()*constant;
				if(genotype[i] > maxBound) {
					genotype[i] = maxBound;
				} else if(genotype[i] < minBound) {
					genotype[i] = minBound;
				}
			}
		}
		
	}

}
