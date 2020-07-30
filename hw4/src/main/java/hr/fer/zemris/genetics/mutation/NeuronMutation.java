package hr.fer.zemris.genetics.mutation;

import java.util.Random;

import hr.fer.zemris.genetics.GA;
import hr.fer.zemris.genetics.genotype.neural.NeuralNetwork;

public class NeuronMutation implements IMutation<double[]> {
	
	private NeuralNetwork neural;
	private double pm1;
	private double pm2;
	private double sigma1;
	private double sigma2;
	private double prob;
	
	public NeuronMutation(NeuralNetwork neural, double pm1, double pm2, double sigma1, double sigma2, double prob) {
		this.neural = neural;
		this.pm1 = pm1;
		this.pm2 = pm2;
		this.sigma1 = sigma1;
		this.sigma2 = sigma2;
		this.prob = prob;
	}

	@Override
	public void mutate(double[] genotype) {
		Random rand = GA.rand;
		if(rand.nextDouble() < prob) {
			
			for(int i = neural.getStartOfLayerType1(); i < neural.getEndOfLayerType1(); i+=2) {
				if(rand.nextDouble() < pm1) {
					genotype[i] = rand.nextGaussian()*sigma1;
					genotype[i+1] = rand.nextGaussian()*sigma1;
				}
			}
			
//			for(int i = neural.getStartOfLayerType1(); i < neural.getEndOfLayerType1(); i++) {
//				if(rand.nextDouble() < pm1) {
//					genotype[i] = rand.nextGaussian()*sigma1;
//				}
//			}
			
		} else {
			
			for(int i = neural.getStartOfLayerType2(); i < neural.getEndOfLayerType2(); i++) {
				if(rand.nextDouble() < pm2) {
					genotype[i] += rand.nextGaussian()*sigma2;
				}
			}
			
		}
		
	}

}
