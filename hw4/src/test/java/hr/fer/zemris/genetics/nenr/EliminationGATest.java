package hr.fer.zemris.genetics.nenr;

import java.lang.reflect.Array;
import java.util.Random;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

import hr.fer.zemris.genetics.EliminationGA;
import hr.fer.zemris.genetics.GA;
import hr.fer.zemris.genetics.GAEvolvingListener;
import hr.fer.zemris.genetics.LineChart;
import hr.fer.zemris.genetics.condition.FixedGenerationCount;
import hr.fer.zemris.genetics.condition.StopCondition;
import hr.fer.zemris.genetics.crossover.DiscreteCrossover;
import hr.fer.zemris.genetics.crossover.ICrossover;
import hr.fer.zemris.genetics.decoder.PassThroughDecoder;
import hr.fer.zemris.genetics.function.NegatedMSE;
import hr.fer.zemris.genetics.function.NegatedMSE.Sample;
import hr.fer.zemris.genetics.individual.Individual;
import hr.fer.zemris.genetics.mutation.GaussMutation;
import hr.fer.zemris.genetics.mutation.IMutation;

public class EliminationGATest {
	
	private static final String fileName1 = "zad4-dataset1.txt";
	private static final String fileName2 = "zad4-dataset2.txt";
	
	private static final int NUM_PARAMS = 5;
	
	private static final double MIN_BOUND = -4;
	private static final double MAX_BOUND = 4;
	private static final double constant = 1;
	
	private static final int NUM_GENS = 100_000;
	private static final int POP_SIZE = 30;
	
	private static final double pm = 0.2;
	
	private static final int tournament_size = 3;
	private static final ICrossover<double[]> crossover = new DiscreteCrossover();
	private static final IMutation<double[]> mutation = new GaussMutation(pm, constant, MIN_BOUND, MAX_BOUND);
	
	private static final boolean plot = false;
	private static final XYSeries series = new XYSeries("");
	
	public static void main(String[] args) {
		
		Sample[] samples = NegatedMSE.samplesFromFile(fileName1);
		PassThroughDecoder decoder = new PassThroughDecoder();
		NegatedMSE<double[]> function = new NegatedMSE<>(samples, decoder);
		
		EliminationGA<double[]> ga = new EliminationGA<>(tournament_size, function, crossover, mutation);
		
		GAEvolvingListener listener1 = (timestep, fitness) -> System.out.println(timestep+ ".: " +fitness);
		ga.addListener(listener1);
		
		if(plot) {
			GAEvolvingListener listener2 = (timestep, fitness) -> series.add(timestep, fitness);
			ga.addListener(listener2);
		}
		
		Individual<double[]>[] initial = getInitialPopulation();
		StopCondition<double[]> condition = new FixedGenerationCount<>(NUM_GENS);
		
		Individual<double[]>[] finalPopulation = ga.evolve(initial, condition);
		
		if(plot) {
			LineChart chart = new LineChart(null, null, "Generation","Fitness", new XYSeriesCollection(series));
	
		    chart.pack( );
		    RefineryUtilities.centerFrameOnScreen( chart );
		    chart.setVisible( true );
		}
		
	}
	
	private static Individual<double[]>[] getInitialPopulation() {
		
		@SuppressWarnings("unchecked")
		Individual<double[]>[] population = (Individual<double[]>[]) Array.newInstance(Individual.class, POP_SIZE);

		Random rand = GA.rand;
		
		for(int i = 0; i < POP_SIZE; i++) {
			double[] genotype = new double[NUM_PARAMS];
			for(int j = 0; j < NUM_PARAMS; j++) {
				genotype[j] = rand.nextDouble()*(MAX_BOUND - MIN_BOUND) + MIN_BOUND;
			}
			
			population[i] = new Individual<double[]>(genotype);
		}
		
		return population;
	}

}
