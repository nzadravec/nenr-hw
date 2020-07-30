package hr.fer.zemris.genetics.apr;

import hr.fer.zemris.genetics.EliminationGA;
import hr.fer.zemris.genetics.GAEvolvingListener;
import hr.fer.zemris.genetics.condition.FixedIterationCount;
import hr.fer.zemris.genetics.condition.StopCondition;
import hr.fer.zemris.genetics.crossover.ArithmeticalCrossover;
import hr.fer.zemris.genetics.crossover.ICrossover;
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
import hr.fer.zemris.genetics.util.EvoUtil;

public class Task1 {

	private static final double MIN_BOUND = -50;
	private static final double MAX_BOUND = 150;

	private static final int NUM_ITERS = 2_000_000;
	private static final int POP_SIZE = 50;

	private static final double pm = 0.3;
	private static final double constant = 10;

	private static final int tournament_size = 3;
	private static final ICrossover<double[]> crossover = new ArithmeticalCrossover();
	private static final IMutation<double[]> mutation = new GaussMutation(pm, constant, MIN_BOUND, MAX_BOUND);
	//private static final IMutation<double[]> mutation = new UniformMutation(pm, MIN_BOUND, MAX_BOUND);

	private static final int func_index = 1;
	private static final int num_params = 3;
	
	private static final ICrossover<boolean[]> crossover1 = new UniformCrossover();
	private static final IMutation<boolean[]> mutation1 = new Mutation(pm);
	
	private static final double decimals = 4;
	private static final int num_bits = BitvectorDecoder.numOfBitsFor(decimals, MIN_BOUND, MAX_BOUND);
	private static final IDecoder<boolean[]> decoder1 =
	new GrayBinaryDecoder(MIN_BOUND, MAX_BOUND, num_bits, num_params);
	
	public static void main1(String[] args) {
		
		FitnessFunction<boolean[]> function = Function.function1(func_index, decoder1);
		StopCondition<boolean[]> condition = new FixedIterationCount<>(NUM_ITERS);
		
		EliminationGA<boolean[]> ga = new EliminationGA<>(tournament_size, function, crossover1, mutation1);
		
		GAEvolvingListener listener1 = (timestep, fitness) -> System.out.println(timestep + ".: " + fitness);
		ga.addListener(listener1);
		
		Individual<boolean[]>[] initialPopulation = 
				EvoUtil.getInitialPopulation(POP_SIZE, num_params * num_bits);
		Individual<boolean[]>[] finalPopulation = ga.evolve(initialPopulation, condition);
		Individual<boolean[]> best = EvoUtil.max(finalPopulation);
		
	}
	
	public static void main(String[] args) {
		
		FitnessFunction<double[]> function = Function.function1(func_index, new PassThroughDecoder());

		EliminationGA<double[]> ga = new EliminationGA<>(tournament_size, function, crossover, mutation);

		GAEvolvingListener listener1 = (timestep, fitness) -> System.out.println(timestep + ".: " + fitness);
		ga.addListener(listener1);
		
		Individual<double[]>[] initialPopulation = 
				EvoUtil.getInitialPopulation(POP_SIZE, num_params, MIN_BOUND, MAX_BOUND);
		StopCondition<double[]> condition = new FixedIterationCount<>(NUM_ITERS);
		
		Individual<double[]>[] finalPopulation = ga.evolve(initialPopulation, condition);
		Individual<double[]> best = EvoUtil.max(finalPopulation);
		
		System.out.println();
		//System.out.println(best.fitness);
		
	}

}
