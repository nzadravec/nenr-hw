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

public class Task5 {

	private static final double MIN_BOUND = -50;
	private static final double MAX_BOUND = 150;

	private static final int NUM_ITERS = 100_000;
	private static final int POP_SIZE = 30;

	private static final double pm = 0.3;
	private static final double constant = 10;

	private static final int[] tournaments = new int[] {3, 7, 12};
	private static final ICrossover<double[]> crossover = new ArithmeticalCrossover();
	private static final IMutation<double[]> mutation1 = new GaussMutation(pm, constant, MIN_BOUND, MAX_BOUND);

	private static final int func_index = 7;
	private static final int num_params = 3;
	
	private static final int num_of_executions = 30;
	
	public static void main(String[] args) {
		
		FitnessFunction<double[]> function = Function.function(func_index, new PassThroughDecoder());

		EliminationGA<double[]> ga = null;
		
		double[][] dss = new double[tournaments.length][num_of_executions];
		for(int i = 0; i < tournaments.length; i++) {
			for(int j = 0; j < num_of_executions; j++) {
				
				ga = new EliminationGA<>(tournaments[i], function, crossover, mutation1);
				
				Individual<double[]>[] initialPopulation = 
						EvoUtil.getInitialPopulation(POP_SIZE, num_params, MIN_BOUND, MAX_BOUND);
				StopCondition<double[]> condition = new FixedIterationCount<>(NUM_ITERS);
				
				Individual<double[]>[] finalPopulation = ga.evolve(initialPopulation, condition);
				Individual<double[]> best = EvoUtil.max(finalPopulation);
				
				dss[i][j] = best.fitness;
			}
			
		}
		
		for(int j = 0; j < num_of_executions; j++) {
			for(int i = 0; i < tournaments.length; i++) {
				if(i == 0) {
					System.out.print(dss[i][j]);
				} else {
					System.out.print(","+dss[i][j]);
				}
			}
			System.out.println();
		}
 		
	}

}
