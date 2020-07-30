package hr.fer.zemris.genetics.apr;

import hr.fer.zemris.genetics.EliminationGA;
import hr.fer.zemris.genetics.condition.FixedIterationCount;
import hr.fer.zemris.genetics.condition.StopCondition;
import hr.fer.zemris.genetics.crossover.ICrossover;
import hr.fer.zemris.genetics.crossover.OnePointCrossover;
import hr.fer.zemris.genetics.crossover.UniformCrossover;
import hr.fer.zemris.genetics.decoder.BitvectorDecoder;
import hr.fer.zemris.genetics.decoder.GrayBinaryDecoder;
import hr.fer.zemris.genetics.decoder.IDecoder;
import hr.fer.zemris.genetics.function.FitnessFunction;
import hr.fer.zemris.genetics.function.Function;
import hr.fer.zemris.genetics.individual.Individual;
import hr.fer.zemris.genetics.mutation.IMutation;
import hr.fer.zemris.genetics.mutation.Mutation;
import hr.fer.zemris.genetics.util.EvoUtil;

public class Task4 {

	private static final double MIN_BOUND = -50;
	private static final double MAX_BOUND = 150;

	private static final int NUM_ITERS = 1_000_000;
	private static final int[] pop_sizes = new int[] { 30, 50, 100, 200};
	
	private static final double[] pms = new double[] { 0.1, 0.3, 0.6, 0.9};

	private static final int tournament_size = 3;

	private static final int func_index = 7;
	private static final int num_params = 2;
	
	private static final int num_of_executions = 50;
	
	private static final double decimals = 4;
	private static final int num_bits = BitvectorDecoder.numOfBitsFor(decimals, MIN_BOUND, MAX_BOUND);
	private static final IDecoder<boolean[]> decoder1 =
	new GrayBinaryDecoder(MIN_BOUND, MAX_BOUND, num_bits, num_params);
	
	private static final ICrossover<boolean[]> crossover1 = new UniformCrossover();
	
	public static void main(String[] args) {

		FitnessFunction<boolean[]> function = Function.function(func_index, decoder1);
		StopCondition<boolean[]> condition = new FixedIterationCount<>(NUM_ITERS);
		EliminationGA<boolean[]> ga = null;
		
		double[][][] dsss = new double[pop_sizes.length][pms.length][num_of_executions];
		for(int i = 0; i < pop_sizes.length; i++) {
			for(int j = 0; j < pms.length; j++) {
				for(int k = 0; k < num_of_executions; k++) {
					
					IMutation<boolean[]> mutation = new Mutation(pms[j]);

					ga = new EliminationGA<>(tournament_size, function, crossover1, mutation);
					
					Individual<boolean[]>[] initialPopulation = 
							EvoUtil.getInitialPopulation(pop_sizes[i], num_params * num_bits);
					Individual<boolean[]>[] finalPopulation = ga.evolve(initialPopulation, condition);
					Individual<boolean[]> best = EvoUtil.max(finalPopulation);
					
					dsss[i][j][k] = best.fitness;
				}
			}
		}
		
		for(int i = 0; i < pop_sizes.length; i++) {
			for(int j = 0; j < pms.length; j++) {
				
				if(i == pop_sizes.length-1 && j == pms.length-1) {
					System.out.print(pop_sizes[i]+"x"+pms[j]);
				} else {
					System.out.print(pop_sizes[i]+"x"+pms[j]+",");
				}
			}
		}
		System.out.println();
		
		for(int k = 0; k < num_of_executions; k++) {
			for(int i = 0; i < pop_sizes.length; i++) {
				for(int j = 0; j < pms.length; j++) {
					if(i == pop_sizes.length-1 && j == pms.length-1) {
						System.out.print(dsss[i][j][k]);
					} else {
						System.out.print(dsss[i][j][k]+",");
					}
				}
			}
			System.out.println();
		}
		
	}

}
