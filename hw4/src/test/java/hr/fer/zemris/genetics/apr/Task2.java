package hr.fer.zemris.genetics.apr;

import hr.fer.zemris.genetics.EliminationGA;
import hr.fer.zemris.genetics.condition.FixedIterationCount;
import hr.fer.zemris.genetics.condition.StopCondition;
import hr.fer.zemris.genetics.crossover.ArithmeticalCrossover;
import hr.fer.zemris.genetics.crossover.ICrossover;
import hr.fer.zemris.genetics.decoder.PassThroughDecoder;
import hr.fer.zemris.genetics.function.FitnessFunction;
import hr.fer.zemris.genetics.function.Function;
import hr.fer.zemris.genetics.individual.Individual;
import hr.fer.zemris.genetics.mutation.GaussMutation;
import hr.fer.zemris.genetics.mutation.IMutation;
import hr.fer.zemris.genetics.util.EvoUtil;

public class Task2 {
	
	private static final double MIN_BOUND = -50;
	private static final double MAX_BOUND = 150;

	private static final int NUM_ITERS = 1_000_000;
	private static final int POP_SIZE = 50;

	private static final double pm = 0.3;
	private static final double constant = 10;

	private static final int tournament_size = 3;
	private static final ICrossover<double[]> crossover = new ArithmeticalCrossover();
	private static final IMutation<double[]> mutation1 = new GaussMutation(pm, constant, MIN_BOUND, MAX_BOUND);

	private static final int func_index = 7;
	private static final int[] num_params_array = new int[]{1, 3, 6, 10};
	
	public static void main(String[] args) {
		
		FitnessFunction<double[]> function = Function.function1(func_index, new PassThroughDecoder());

		EliminationGA<double[]> ga = new EliminationGA<>(tournament_size, function, crossover, mutation1);
		Individual<double[]>[] initialPopulation = null;
		StopCondition<double[]> condition = new FixedIterationCount<>(NUM_ITERS);
		
		for(int i = 0; i < num_params_array.length; i++) {
			
			initialPopulation = 
					EvoUtil.getInitialPopulation(POP_SIZE, num_params_array[i], MIN_BOUND, MAX_BOUND);
			
			Individual<double[]>[] finalPopulation = ga.evolve(initialPopulation, condition);
			Individual<double[]> best = EvoUtil.max(finalPopulation);
			System.out.println(num_params_array[i]+" "+best.fitness);
			
		}
		
	}
	
}
