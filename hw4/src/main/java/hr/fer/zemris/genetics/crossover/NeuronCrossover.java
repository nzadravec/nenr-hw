package hr.fer.zemris.genetics.crossover;

import java.util.Random;

import hr.fer.zemris.genetics.GA;
import hr.fer.zemris.genetics.genotype.neural.NeuralNetwork;
import hr.fer.zemris.genetics.individual.Individual;
import hr.fer.zemris.genetics.individual.IndividualPair;

public class NeuronCrossover implements ICrossover<double[]> {
	
	private NeuralNetwork neural;
	
	public NeuronCrossover(NeuralNetwork neural) {
		this.neural = neural;
	}

	@Override
	public IndividualPair<double[]> crossover(double[] genotype1, double[] genotype2) {
		double[] childsGenotype1 = new double[genotype1.length];
		double[] childsGenotype2 = new double[genotype1.length];
		Random rand = GA.rand;
		int[] startIndices = neural.getStartIndices();
		int[] endIndices = neural.getEndIndices();
		for(int i = 0; i < neural.getNumOfNeurons(); i++) {
			if(rand.nextBoolean()) {
				for(int j = startIndices[i]; j < endIndices[i]; j++) {
					childsGenotype1[j] = genotype1[j];
					childsGenotype2[j] = genotype2[j];
				}
			} else {
				for(int j = startIndices[i]; j < endIndices[i]; j++) {
					childsGenotype1[j] = genotype2[j];
					childsGenotype2[j] = genotype1[j];
				}
			}
		}
		
		return new IndividualPair<>(
				new Individual<double[]>(childsGenotype1), new Individual<double[]>(childsGenotype2));
	}

}
