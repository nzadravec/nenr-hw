package hr.fer.zemris.genetics.util;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.genetics.GA;
import hr.fer.zemris.genetics.individual.Individual;

public class EvoUtil {

	public static <G> Individual<G> max(Individual<G>[] population) {
		
		Individual<G> max = population[0];
		for(int i = 1; i < population.length; i++) {
			if(max.fitness < population[i].fitness) {
				max = population[i];
			}
		}
		
		return max;
	}
	
	public static <G> int argmax(Individual<G>[] population) {
		
		int argmax = 0; 
		for(int i = 1; i < population.length; i++) {
			if(population[argmax].fitness < population[i].fitness) {
				argmax = i;
			}
		}
		
		return argmax;
	}
	
	public static <G> Individual<G> min(Individual<G>[] population, int from, int to) {
		
		Individual<G> min = population[from];
		for(int i = from + 1; i < to; i++) {
			if(min.fitness > population[i].fitness) {
				min = population[i];
			}
		}
		
		return min;
	}
	
	public static <G> Individual<G> min(Individual<G>[] population) {
		return min(population, 0, population.length);
	}
	
	public static <G> int argmin(Individual<G>[] population, int from, int to) {
		
		int argmin = from;
		for(int i = from + 1; i < to; i++) {
			if(population[argmin].fitness > population[i].fitness) {
				argmin = i;
			}
		}
		
		return argmin;
	}
	
	public static <G> int argmin(Individual<G>[] population) {
		return argmin(population, 0, population.length);
	}
	
	public static Individual<boolean[]>[] getInitialPopulation(int popSize, int numBits) {
		
		@SuppressWarnings("unchecked")
		Individual<boolean[]>[] population = (Individual<boolean[]>[]) Array.newInstance(Individual.class, popSize);
		
		Random rand = GA.rand;
		
		for (int i = 0; i < popSize; i++) {
			boolean[] genotype = new boolean[numBits];
			for (int j = 0; j < numBits; j++) {
				genotype[j] = rand.nextBoolean();
			}
			
			population[i] = new Individual<boolean[]>(genotype);
		}
		
		return population;
	}
	
	public static Individual<double[]>[] getInitialPopulation(int popSize, int numParams, double minBound, double maxBound) {
		
		@SuppressWarnings("unchecked")
		Individual<double[]>[] population = (Individual<double[]>[]) Array.newInstance(Individual.class, popSize);

		Random rand = GA.rand;

		for (int i = 0; i < popSize; i++) {
			double[] genotype = new double[numParams];
			for (int j = 0; j < numParams; j++) {
				genotype[j] = rand.nextDouble() * (maxBound - minBound) + minBound;
			}

			population[i] = new Individual<double[]>(genotype);
		}

		return population;
	}
	
	public static <G> Individual<G>  proportionalSimpleChoose(Individual<G>[] population, int from, int to, Random rand) {
		
		double worstFit = EvoUtil.min(population, from, to).fitness;
		
		double sum = 0;
		for(int i = from; i < to; i++) {
			sum += population[i].fitness - worstFit;
		}
		
		double r = rand.nextDouble();
		double limit = r * sum;
		// Nadi jedinku koju smo time pogodili:
		int chosen = from;
		double upperLimit = population[chosen].fitness - worstFit;
		while(limit > upperLimit && chosen < to - 1) {
			chosen++;
			upperLimit += population[chosen].fitness - worstFit;
		}
		
		return population[chosen];
	}
	
	public static <T> T proportionalSimpleChoose(List<T> list, double[] probs, Random rand) {
		
		double limit = rand.nextDouble();
		int index = 0;
		double upperLimit = probs[index];
		
		while(limit > upperLimit && index < list.size() - 1) {
			index++;
			upperLimit += probs[index];
		}
		
		return list.get(index);
	}
	
}
