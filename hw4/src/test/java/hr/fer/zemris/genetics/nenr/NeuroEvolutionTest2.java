package hr.fer.zemris.genetics.nenr;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.genetics.GA;
import hr.fer.zemris.genetics.GAEvolvingListener;
import hr.fer.zemris.genetics.GenerationalGA;
import hr.fer.zemris.genetics.condition.FixedIterationCount;
import hr.fer.zemris.genetics.condition.FixedPrecision;
import hr.fer.zemris.genetics.condition.StopCondition;
import hr.fer.zemris.genetics.condition.StopConditions;
import hr.fer.zemris.genetics.crossover.ArithmeticalCrossover;
import hr.fer.zemris.genetics.crossover.Crossovers;
import hr.fer.zemris.genetics.crossover.DiscreteCrossover;
import hr.fer.zemris.genetics.crossover.FlatCrossover;
import hr.fer.zemris.genetics.crossover.ICrossover;
import hr.fer.zemris.genetics.crossover.NeuronCrossover;
import hr.fer.zemris.genetics.crossover.OnePointCrossover2;
import hr.fer.zemris.genetics.crossover.PassThroughCrossover;
import hr.fer.zemris.genetics.decoder.PassThroughDecoder;
import hr.fer.zemris.genetics.function.FitnessFunction;
import hr.fer.zemris.genetics.function.Function;
import hr.fer.zemris.genetics.function.MSE;
import hr.fer.zemris.genetics.genotype.neural.Dataset;
import hr.fer.zemris.genetics.genotype.neural.NeuralNetwork;
import hr.fer.zemris.genetics.individual.Individual;
import hr.fer.zemris.genetics.mutation.GaussMutation;
import hr.fer.zemris.genetics.mutation.GaussMutation2;
import hr.fer.zemris.genetics.mutation.IMutation;
import hr.fer.zemris.genetics.mutation.TwoMutations;
import hr.fer.zemris.genetics.selection.ISelection;
import hr.fer.zemris.genetics.selection.RouletteWheelSelection;

public class NeuroEvolutionTest2 {
	
	private static final int inputSize = 2;
	private static final int outputSize = 3;
	
	private static final int[] neuronsPerLayer = new int[] {inputSize, 8, outputSize};
	
	private static final int tournament_size = 3;
	
	private static int popSize = 100;
	private static int numOfParams;
	
	private static double pm1 = 0.04;
	private static double pm2 = 0.04;
	private static double constant1 = 1;
	private static double constant2 = 1;
	private static double prob = 0.6;
	
	private static int maxIters = 50_000;
	private static double epsilon = 1e-7;
	
	public static void main(String[] args) {
		
		NeuralNetwork neural = new NeuralNetwork(neuronsPerLayer);
		numOfParams = neural.getNumOfParams();
		Dataset dataset = Dataset.load("zad7-dataset.txt", inputSize, outputSize);
		
		PassThroughDecoder decoder = new PassThroughDecoder();
		FitnessFunction<double[]> negatedMSE = Function.negate(new MSE<>(dataset, decoder, neural));
		
		ArithmeticalCrossover crossover1 = new ArithmeticalCrossover();
		DiscreteCrossover crossover2 = new DiscreteCrossover();
		FlatCrossover crossover3 = new FlatCrossover();
		OnePointCrossover2 crossover4 = new OnePointCrossover2();
		NeuronCrossover crossover5 = new NeuronCrossover(neural);
		PassThroughCrossover crossover6 = new PassThroughCrossover();
		ArrayList<ICrossover<double[]>> crossovers = new ArrayList<>();
		crossovers.add(crossover1);
		crossovers.add(crossover2);
		//crossovers.add(crossover3);
		//crossovers.add(crossover4);
		crossovers.add(crossover5);
		//crossovers.add(crossover6);
		ICrossover<double[]> crossover = new Crossovers<>(crossovers);
		
		GaussMutation mutation1 = new GaussMutation(pm1, constant1);
		GaussMutation2 mutation2 = new GaussMutation2(pm2, constant2);
		IMutation<double[]> mutation = new TwoMutations<>(mutation1, mutation2, prob);
		
		ISelection<double[]> selection = new RouletteWheelSelection<>();
		
		GenerationalGA<double[]> ga = new GenerationalGA<>(
				negatedMSE, selection, crossover, mutation, true);

		GAEvolvingListener listener1 = (timestep, fitness) -> System.out.println(timestep+ ".: " +fitness);
		ga.addListener(listener1);
		
		Individual<double[]>[] initial = getInitialPopulation();
		
		StopCondition<double[]> cond1 = new FixedIterationCount<>(maxIters);
		StopCondition<double[]> cond2 = new FixedPrecision<>(epsilon);
		List<StopCondition<double[]>> conds = new ArrayList<>();
		conds.add(cond1);
		conds.add(cond2);
		
		StopCondition<double[]> stopCondition = new StopConditions<>(conds);
		
		Individual<double[]>[] finalPopulation = ga.evolve(initial, stopCondition);

	}
	
	private static Individual<double[]>[] getInitialPopulation() {
		
		@SuppressWarnings("unchecked")
		Individual<double[]>[] population = (Individual<double[]>[]) Array.newInstance(Individual.class, popSize);

		Random rand = GA.rand;
		
		for(int i = 0; i < popSize; i++) {
			double[] genotype = new double[numOfParams];
			for(int j = 0; j < numOfParams; j++) {
				//genotype[j] = (-1 + 2*rand.nextDouble()) / 1;
				genotype[j] = rand.nextGaussian();
			}
			
			population[i] = new Individual<double[]>(genotype);
		}
		
		return population;
	}

}
