package hr.fer.zemris.genetics;

import java.lang.reflect.Array;

import hr.fer.zemris.genetics.condition.StopCondition;
import hr.fer.zemris.genetics.crossover.ICrossover;
import hr.fer.zemris.genetics.function.FitnessFunction;
import hr.fer.zemris.genetics.individual.Individual;
import hr.fer.zemris.genetics.individual.IndividualPair;
import hr.fer.zemris.genetics.mutation.IMutation;
import hr.fer.zemris.genetics.selection.ISelection;
import hr.fer.zemris.genetics.selection.RouletteWheelSelection;
import hr.fer.zemris.genetics.util.EvoUtil;

public class EliminationGA<G> extends GA<G> {
	
	private int tournamentSize;

	private FitnessFunction<G> function;
	
	private ICrossover<G> crossover;
	private IMutation<G> mutation;
	
	private ISelection<G> selection = new RouletteWheelSelection<G>();
	
	public EliminationGA(int tournamentSize, FitnessFunction<G> function, ICrossover<G> crossover, IMutation<G> mutation) {
		super();
		this.tournamentSize = tournamentSize;
		this.function = function;
		this.crossover = crossover;
		this.mutation = mutation;
	}

	@Override
	public Individual<G>[] evolve(Individual<G>[] population, StopCondition<G> stopCondition) {
		
		int popSize = population.length;
		
		for(Individual<G> individual : population) {
			individual.fitness = function.fitnessOf(individual.genotype);
		}
		
		Individual<G> bestSoFar = EvoUtil.max(population);
		int numIterations = 0;
		notifyListeners(numIterations, bestSoFar.fitness);
		
		@SuppressWarnings("unchecked")
		Individual<G>[] candidateArray = (Individual<G>[]) Array.newInstance(Individual.class, tournamentSize);
		int[] candToPopMapping = new int[tournamentSize];
		Individual<G> tmp = null;
		
		while(!stopCondition.isSatisfied(population)) {
			
			for(int i = 0; i < tournamentSize; i++) {
				int index = GA.rand.nextInt(popSize);
				candToPopMapping[i] = index;
				candidateArray[i] = population[index];
			}
			
			int argmin = EvoUtil.argmin(candidateArray);
			tmp = candidateArray[tournamentSize-1];
			candidateArray[tournamentSize-1] = candidateArray[argmin];
			candidateArray[argmin] = tmp;
			
//			if(argmin == 0 || argmin == 1) {
//				tmp = candidateArray[tournamentSize-1];
//				candidateArray[tournamentSize-1] = candidateArray[argmin];
//				candidateArray[argmin] = tmp;
//			}
			
//			IndividualPair<G> pair = crossover.
//					crossover(EvoUtil.proportionalSimpleChoose(candidateArray, 0, tournamentSize-1, rand).genotype, 
//							EvoUtil.proportionalSimpleChoose(candidateArray, 0, tournamentSize-1, rand).genotype);
			
			IndividualPair<G> pair = crossover.
					crossover(candidateArray[0].genotype, 
							candidateArray[1].genotype);
			
			
			mutation.mutate(pair.first.genotype);
			pair.first.fitness = function.fitnessOf(pair.first.genotype);
			population[candToPopMapping[argmin]] = pair.first;
			
			numIterations++;
			
			if(bestSoFar.fitness < pair.first.fitness) {
				bestSoFar = pair.first;
				notifyListeners(numIterations, bestSoFar.fitness);
	        }
			
		}
		
		return population;
	}

}
