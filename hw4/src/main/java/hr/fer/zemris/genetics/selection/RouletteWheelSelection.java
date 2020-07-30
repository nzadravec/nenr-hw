package hr.fer.zemris.genetics.selection;

import java.util.Random;

import hr.fer.zemris.genetics.GA;
import hr.fer.zemris.genetics.individual.Individual;
import hr.fer.zemris.genetics.util.EvoUtil;

public class RouletteWheelSelection<G> implements ISelection<G> {

	@Override
	public Individual<G> select(Individual<G>[] population) {
		
		double worstFit = EvoUtil.min(population).fitness;
		
		double sum = 0;
		for(Individual<G> individual : population) {
			sum += individual.fitness - worstFit;
		}
		
		Random rand = GA.rand;
		
		double r = rand.nextDouble();
		double limit = r * sum;
		// Nadi jedinku koju smo time pogodili:
		int chosen = 0;
		double upperLimit = population[chosen].fitness - worstFit;
		while(limit > upperLimit && chosen < population.length-1) {
			chosen++;
			upperLimit += population[chosen].fitness - worstFit;
		}
		
		return population[chosen];
	}

}
