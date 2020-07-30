package hr.fer.zemris.neuro;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FFNNTest {
	
	private static final String fileName = "samples2.txt";
	private static final int inputSize = 1;
	private static final int outputSize = 1;
	private static final int batchSize = 11;
	
	private static ActivationFunction sigmoid = new Sigmoid();
	
	private static final int[] neuronsPerLayer = new int[] {inputSize, 6, outputSize};
	private static final ActivationFunction[] activationPerLayer = 
			new ActivationFunction[] {sigmoid, sigmoid};
	
	private static final double learningRate = 0.1;
	private static final double epsilon = 1e-3;
	private static final int maxIter = 1000000;
	
	public static void main(String[] args) {
		
		FFNN ffnn = new FFNN(neuronsPerLayer, activationPerLayer);
		List<List<Sample>> batchSamples = createBatches(loadSamplesFromFile(fileName));
		BackPropLearning backProp = new BackPropLearning(learningRate, epsilon, maxIter);
		backProp.learn(ffnn, batchSamples);
		
	}
	
	private static List<List<Sample>> createBatches(List<Sample> samples) {
		
		List<List<Sample>> batchSamples = new ArrayList<>();
		
		int index = 0;
		while(index < samples.size()) {
			
			List<Sample> l = new ArrayList<>();
			for(int i = 0; i < batchSize && index < samples.size(); i++) {
				l.add(samples.get(index));
				index++;
			}
			
			batchSamples.add(l);
			
		}
		
		return batchSamples;
	}
	
	private static List<Sample> loadSamplesFromFile(String fileName) {
		List<Sample> samples = new ArrayList<>();
		
		List<String> lines = new ArrayList<>();
		try (Scanner sc = new Scanner(new File("./data/" + fileName))) {

			while (sc.hasNext()) {
				String s = sc.nextLine();
				lines.add(s);
			}

		} catch (FileNotFoundException exc) {
		}
		
		for(int i = 0; i < lines.size(); i++) {
			String[] ss = lines.get(i).split(",");
			
			double[] input = new double[inputSize];
			for(int j = 0; j < inputSize; j++) {
				input[j] = Double.parseDouble(ss[j].trim());
			}
			
			double[] target = new double[outputSize];
			for(int j = 0; j < outputSize; j++) {
				target[j] = Double.parseDouble(ss[inputSize+j].trim());
			}
			
			
			samples.add(new Sample(input, target));
		}
		
		return samples;
	}

}
