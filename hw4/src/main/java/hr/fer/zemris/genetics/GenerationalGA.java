package hr.fer.zemris.genetics;

import java.lang.reflect.Array;

import hr.fer.zemris.genetics.condition.StopCondition;
import hr.fer.zemris.genetics.crossover.ICrossover;
import hr.fer.zemris.genetics.function.FitnessFunction;
import hr.fer.zemris.genetics.individual.Individual;
import hr.fer.zemris.genetics.individual.IndividualPair;
import hr.fer.zemris.genetics.mutation.IMutation;
import hr.fer.zemris.genetics.selection.ISelection;
import hr.fer.zemris.genetics.util.EvoUtil;

public class GenerationalGA<G> extends GA<G> {
	
	private FitnessFunction<G> function;
	
	private ISelection<G> selection;
	private ICrossover<G> crossover;
	private IMutation<G> mutation;
	
	private boolean elitism;
	
	public GenerationalGA(FitnessFunction<G> function, ISelection<G> selection, ICrossover<G> crossover,
			IMutation<G> mutation, boolean elitism) {
		super();
		this.function = function;
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
		this.elitism = elitism;
	}

	@Override
	public Individual<G>[] evolve(Individual<G>[] initial, StopCondition<G> condition) {
		
		int popSize = initial.length;
		
		Individual<G>[] current = initial;
		for(Individual<G> individual : current) {
			individual.fitness = function.fitnessOf(individual.genotype);
		}
		
		Individual<G> bestSoFar = EvoUtil.max(current);
		int generationsEvolved = 0;
		notifyListeners(generationsEvolved, bestSoFar.fitness);
		
		@SuppressWarnings("unchecked")
		Individual<G>[] nextGeneration = (Individual<G>[]) Array.newInstance(initial.getClass().getComponentType(), popSize);
		
		while(!condition.isSatisfied(current)) {
			
			int i = 0;
			if (elitism) {
				nextGeneration[i] = EvoUtil.max(current);
				i++;
			}
			
			while(i < popSize) {
				
				Individual<G> parent1 = selection.select(current);
				Individual<G> parent2 = selection.select(current);
				IndividualPair<G> pair = crossover.crossover(parent1.genotype, parent2.genotype);
				Individual<G> child1 = pair.first;
				mutation.mutate(child1.genotype);
				child1.fitness = function.fitnessOf(child1.genotype);
				nextGeneration[i] = child1;
				i++;
				
				Individual<G> child2 = pair.second;
				if(child2 != null && i < popSize) {
					mutation.mutate(child2.genotype);
					child2.fitness = function.fitnessOf(child2.genotype);
					nextGeneration[i] = child2;
					i++;
				}
				
			}
			
			current = nextGeneration;
			generationsEvolved++;
			
			if(bestSoFar.fitness < EvoUtil.max(current).fitness) {
				bestSoFar = EvoUtil.max(current);
				notifyListeners(generationsEvolved, bestSoFar.fitness);
	        }
		}
		
		return current;
	}

}
