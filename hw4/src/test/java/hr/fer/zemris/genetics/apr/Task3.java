package hr.fer.zemris.genetics.apr;

import hr.fer.zemris.genetics.EliminationGA;
import hr.fer.zemris.genetics.condition.FixedIterationCount;
import hr.fer.zemris.genetics.condition.StopCondition;
import hr.fer.zemris.genetics.crossover.ArithmeticalCrossover;
import hr.fer.zemris.genetics.crossover.ICrossover;
import hr.fer.zemris.genetics.crossover.OnePointCrossover;
import hr.fer.zemris.genetics.crossover.UniformCrossover;
import hr.fer.zemris.genetics.decoder.BitvectorDecoder;
import hr.fer.zemris.genetics.decoder.GrayBinaryDecoder;
import hr.fer.zemris.genetics.decoder.IDecoder;
import hr.fer.zemris.genetics.decoder.PassThroughDecoder;
import hr.fer.zemris.genetics.function.FitnessFunction;
import hr.fer.zemris.genetics.function.Function;
import hr.fer.zemris.genetics.individual.Individual;
import hr.fer.zemris.genetics.mutation.GaussMutation;
import hr.fer.zemris.genetics.mutation.IMutation;
import hr.fer.zemris.genetics.mutation.Mutation;
import hr.fer.zemris.genetics.mutation.SimpleMutation;
import hr.fer.zemris.genetics.util.EvoUtil;

public class Task3 {
	
	private static final double epsilon = 1e-6;
	
	private static final double MIN_BOUND = -50;
	private static final double MAX_BOUND = 150;

	private static final int NUM_ITERS = 1_000_000;
	private static final int POP_SIZE = 50;

	private static final double pm = 0.3;
	private static final double constant = 10;

	private static final int tournament_size = 3;
	private static final ICrossover<double[]> crossover = new ArithmeticalCrossover();
	private static final IMutation<double[]> mutation = new GaussMutation(pm, constant, MIN_BOUND, MAX_BOUND);
	
	private static final ICrossover<boolean[]> crossover1 = new OnePointCrossover();
	private static final IMutation<boolean[]> mutation1 = new Mutation(pm);

	private static final IDecoder<double[]> decoder = new PassThroughDecoder();
	
	private static final double decimals = 4;
	private static final int num_bits = BitvectorDecoder.numOfBitsFor(decimals, MIN_BOUND, MAX_BOUND);
	
	private static final IDecoder<boolean[]> decoder1 = 
			new GrayBinaryDecoder(MIN_BOUND, MAX_BOUND, num_bits, 3);
	
	private static final IDecoder<boolean[]> decoder2 = 
			new GrayBinaryDecoder(MIN_BOUND, MAX_BOUND, num_bits, 6);
	
	private static final int func_index = 7;
	
	private static final int num_of_executions = 50;
	
	public static void main(String[] args) {
		
		FitnessFunction<double[]> function = Function.function(func_index, decoder);
		
		FitnessFunction<boolean[]> function2 = Function.function(func_index, decoder1);
		FitnessFunction<boolean[]> function3 = Function.function(func_index, decoder2);

		EliminationGA<double[]> ga = new EliminationGA<>(tournament_size, function, crossover, mutation);
		
		EliminationGA<boolean[]> ga1 = new EliminationGA<>(tournament_size, function2, crossover1, mutation1);
		EliminationGA<boolean[]> ga2 = new EliminationGA<>(tournament_size, function3, crossover1, mutation1);
		
		
		StopCondition<double[]> condition = new FixedIterationCount<>(NUM_ITERS);
		StopCondition<boolean[]> condition2 = new FixedIterationCount<>(NUM_ITERS);

		int numOfHits = 0; 
		for(int i = 0; i < num_of_executions; i++) {
			
			Individual<double[]>[] initialPopulation = 
					EvoUtil.getInitialPopulation(POP_SIZE, 3, MIN_BOUND, MAX_BOUND);
			
			Individual<double[]>[] initialPopulation2 = 
					EvoUtil.getInitialPopulation(POP_SIZE, 6, MIN_BOUND, MAX_BOUND);
			
			Individual<double[]>[] initialPopulation1 = 
					EvoUtil.getInitialPopulation(POP_SIZE, 9, MIN_BOUND, MAX_BOUND);
			
			Individual<boolean[]>[] initialPopulation3 = 
					EvoUtil.getInitialPopulation(POP_SIZE, 3 * num_bits);
			
			Individual<boolean[]>[] initialPopulation4 = 
					EvoUtil.getInitialPopulation(POP_SIZE, 6 * num_bits);
			
			Individual<boolean[]>[] initialPopulation5 = 
					EvoUtil.getInitialPopulation(POP_SIZE, 9 * num_bits);
			
			Individual<double[]>[] finalPopulation = ga.evolve(initialPopulation, condition);
			Individual<double[]> best = EvoUtil.max(finalPopulation);
			finalPopulation = ga.evolve(initialPopulation2, condition);
			Individual<double[]> best2 = EvoUtil.max(finalPopulation);
			finalPopulation = ga.evolve(initialPopulation1, condition);
			Individual<double[]> best1 = EvoUtil.max(finalPopulation);
			
			Individual<boolean[]>[] finalPopulation2 = ga1.evolve(initialPopulation3, condition2);
			Individual<boolean[]> best3 = EvoUtil.max(finalPopulation2);
			finalPopulation2 = ga2.evolve(initialPopulation4, condition2);
			Individual<boolean[]> best4 = EvoUtil.max(finalPopulation2);
			finalPopulation2 = ga2.evolve(initialPopulation5, condition2);
			Individual<boolean[]> best5 = EvoUtil.max(finalPopulation2);
			
			System.out.println(best.fitness+","+best2.fitness+","+best1.fitness+","+best3.fitness+","+best4.fitness+","+best5.fitness);
			if(Math.abs(best.fitness) < epsilon) {
				numOfHits++;
			}
		}
		
 	}

}
