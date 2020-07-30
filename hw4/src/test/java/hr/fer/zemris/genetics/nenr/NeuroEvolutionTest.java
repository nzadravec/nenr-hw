package hr.fer.zemris.genetics.nenr;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.genetics.EliminationGA;
import hr.fer.zemris.genetics.GA;
import hr.fer.zemris.genetics.GAEvolvingListener;
import hr.fer.zemris.genetics.condition.FixedIterationCount;
import hr.fer.zemris.genetics.condition.FixedPrecision;
import hr.fer.zemris.genetics.condition.StopCondition;
import hr.fer.zemris.genetics.condition.StopConditions;
import hr.fer.zemris.genetics.crossover.ArithmeticalCrossover;
import hr.fer.zemris.genetics.crossover.Crossovers;
import hr.fer.zemris.genetics.crossover.DiscreteCrossover;
import hr.fer.zemris.genetics.crossover.ICrossover;
import hr.fer.zemris.genetics.crossover.NeuronCrossover;
import hr.fer.zemris.genetics.crossover.OnePointCrossover2;
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
import hr.fer.zemris.genetics.mutation.NeuronMutation;
import hr.fer.zemris.genetics.mutation.TwoMutations;
import hr.fer.zemris.genetics.util.EvoUtil;

public class NeuroEvolutionTest {
	
	private static final int inputSize = 2;
	private static final int outputSize = 3;
	
	private static final int tournament_size = 3;
	
	private static int popSize = 100;
	private static int numOfParams;
	
	private static int numOfSamples = 6;
	
//	private static final int[] neuronsPerLayer = new int[] {inputSize, numOfSamples, 4, outputSize};
//	private static double pm1 = 0.05;
//	private static double pm2 = 0.05;
//	private static double constant1 = 0.1;
//	private static double constant2 = 1;
//	private static double prob = 0.9;
	
	private static final int[] neuronsPerLayer = new int[] {inputSize, numOfSamples, outputSize};
	private static double pm1 = 0.05;
	private static double pm2 = 0.05;
	private static double constant1 = 0.1;
	private static double constant2 = 1;
	private static double prob = 0.9;
	
	private static int maxIters = 2_000_000;
	private static double epsilon = 1e-7;
	
	private static String labelsFile = "./data/labels.txt"; 
	private static boolean saveLabels = true;
	
	private static String paramsFile = "./data/params.txt";
	private static boolean saveParams = true;
	
	private static String storedSamplesFile = "./data/stored_samples.txt";
	private static boolean saveStoredSamples = true;
	
	public static void main(String[] args) {
		
		NeuralNetwork neural = new NeuralNetwork(neuronsPerLayer);
		numOfParams = neural.getNumOfParams();
		Dataset dataset = Dataset.load("zad7-dataset.txt", inputSize, outputSize);
		
		PassThroughDecoder decoder = new PassThroughDecoder();
		FitnessFunction<double[]> negatedMSE = Function.negate(new MSE<>(dataset, decoder, neural));
		
		DiscreteCrossover crossover2 = new DiscreteCrossover();
		NeuronCrossover crossover5 = new NeuronCrossover(neural);
		ArithmeticalCrossover crossover7 = new ArithmeticalCrossover();
		OnePointCrossover2 crossover8 = new OnePointCrossover2();
		//NeuronCrossover crossover8 = new NeuronCrossover(neural);
		ArrayList<ICrossover<double[]>> crossovers = new ArrayList<>();
		crossovers.add(crossover2);
		crossovers.add(crossover7);
		crossovers.add(crossover8);
		ICrossover<double[]> crossover = new Crossovers<>(crossovers);
		
		GaussMutation mutation1 = new GaussMutation(pm1, constant1);
		GaussMutation2 mutation2 = new GaussMutation2(pm2, constant2);
		IMutation<double[]> mutation = new TwoMutations<>(mutation1, mutation2, prob);
		
		EliminationGA<double[]> ga = new EliminationGA<>(
				tournament_size, negatedMSE, crossover, mutation);
		
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
		Individual<double[]> best = EvoUtil.max(finalPopulation);
		
		int[] labels = neural.predict(dataset, best.genotype);
		
		System.out.println(neural.calcError(dataset, best.genotype));
		
		if(saveLabels) {
			try {
				saveLabels(labels, labelsFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(saveParams) {
			try {
				saveParams(best.genotype, paramsFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		double[] xs = new double[numOfSamples];
		double[] ys = new double[numOfSamples];
		
		for(int i = 0; i < numOfSamples; i++) {
			
			xs[i] = best.genotype[i*4];
			ys[i] = best.genotype[i*4 + 2];
			
		}
		
		if(saveStoredSamples) {
			try {
				saveStoredSamples(xs, ys, storedSamplesFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	private static void saveStoredSamples(double[] xs, double[] ys, String fileName) throws FileNotFoundException, IOException {
		
		try (Writer w = new BufferedWriter(new OutputStreamWriter(
				new BufferedOutputStream(new FileOutputStream(fileName)), StandardCharsets.UTF_8))) {
		
			for(int i = 0; i < numOfSamples; i++) {
				w.write(xs[i]+" "+ys[i]+"\n");
			}
			
		}
		
	}
	
	private static void saveLabels(int[] labels, String fileName) throws FileNotFoundException, IOException {
		
		try (Writer w = new BufferedWriter(new OutputStreamWriter(
				new BufferedOutputStream(new FileOutputStream(fileName)), StandardCharsets.UTF_8))) {
		
			for(int i = 0; i < labels.length; i++) {
				w.write(labels[i]+"\n");
			}
			
		}
		
	}
	
	private static void saveParams(double[] params, String fileName) throws FileNotFoundException, IOException {
		
		try (Writer w = new BufferedWriter(new OutputStreamWriter(
				new BufferedOutputStream(new FileOutputStream(fileName)), StandardCharsets.UTF_8))) {
		
			for(int i = 0; i < params.length; i++) {
				w.write(params[i]+"\n");
			}
			
		}
		
	}
	
	private static Individual<double[]>[] getInitialPopulation() {
		
		@SuppressWarnings("unchecked")
		Individual<double[]>[] population = (Individual<double[]>[]) Array.newInstance(Individual.class, popSize);

		Random rand = GA.rand;
		
		for(int i = 0; i < popSize; i++) {
			double[] genotype = new double[numOfParams];
			for(int j = 0; j < numOfParams; j++) {
				//genotype[j] = 2*rand.nextDouble() - 1;
				genotype[j] = rand.nextGaussian();
			}
			
			population[i] = new Individual<double[]>(genotype);
		}
		
		return population;
	}

}
