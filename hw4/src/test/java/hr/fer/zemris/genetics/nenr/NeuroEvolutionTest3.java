package hr.fer.zemris.genetics.nenr;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.genetics.genotype.neural.Dataset;
import hr.fer.zemris.genetics.genotype.neural.NeuralNetwork;

public class NeuroEvolutionTest3 {
	
	private static final int inputSize = 2;
	private static final int outputSize = 3;
	
	private static final int[] neuronsPerLayer = new int[] {inputSize, 8, outputSize};
	
	private static String labelsFile = "./data/labels.txt"; 
	private static boolean saveLabels = true;
	
	private static String paramsFile = "./data/params.txt";
	private static boolean saveParams = true;
	
	private static String storedSamplesFile = "./data/stored_samples.txt";
	private static boolean saveStoredSamples = true;

	public static void main(String[] args) {
		
		NeuralNetwork neural = new NeuralNetwork(neuronsPerLayer);
		int numOfParams = neural.getNumOfParams();
		double[] params = new double[numOfParams];
		Dataset dataset = Dataset.load("zad7-dataset.txt", inputSize, outputSize);
		
		double s = 0.05;
		double d = 9;
		
		double[] xs = new double[] {0.125,  0.375,  0.625,  0.875,  0.125,  0.375,  0.625,  0.875};
		double[] ys = new double[] {0.25,  0.25,  0.25,  0.25,  0.75,  0.75,  0.75,  0.75};
		
		for(int i = 0; i < xs.length; i++) {
			
			params[i*4] = xs[i];
			params[i*4 + 1] = s;
			params[i*4 + 2] = ys[i];
			params[i*4 + 3] = s;
			
		}
		
		int[] a = new int[] {0,3,6};
		int[] b = new int[] {1,4,7};
		int[] c = new int[] {2,5};
		
		int index = 32;
		
		for(int i = index; i < params.length; i++) {
			params[i] = -3;
		}
		
		params[index + 0] = d;
		params[index + 3] = d;
		params[index + 6] = d;
		params[index + 8] = -1;
		
		//
		
		params[index + 9 + 1] = d;
		params[index + 9 + 4] = d;
		params[index + 9 + 7] = d;
		params[index + 9 + 8] = -1;
		
		//
		
		params[index + 2*9 + 2] = d;
		params[index + 2*9 + 5] = d;
		params[index + 2*9 + 8] = -1;
		
		System.out.println(Arrays.toString(params));
		//params = loadParams(paramsFile);
		//System.out.println(Arrays.toString(params));
		
		//int[] labels = null;
		int[] labels = neural.predict(dataset, params);
		
		System.out.println();
		System.out.println("greska:");
		System.out.println(neural.calcError(dataset, params));
		
		//System.out.println(Arrays.toString(labels));
		
		if(saveLabels) {
			try {
				saveLabels(labels, labelsFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(saveParams) {
			try {
				saveParams(params, paramsFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(saveStoredSamples) {
			try {
				saveStoredSamples(xs, ys, storedSamplesFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static double[] loadParams(String fileName) {
		List<String> lines = new ArrayList<>();

		try (Scanner sc = new Scanner(new File(fileName))) {

			while (sc.hasNext()) {
				String s = sc.nextLine();
				lines.add(s);
			}

		} catch (FileNotFoundException exc) {
		}
		
		double[] params = new double[lines.size()];
		for(int i = 0; i < lines.size(); i++) {
			params[i] = Double.valueOf(Double.parseDouble(lines.get(i)));
		}
		
		return params;
	}
	
	private static void saveStoredSamples(double[] xs, double[] ys, String fileName) throws FileNotFoundException, IOException {
		
		try (Writer w = new BufferedWriter(new OutputStreamWriter(
				new BufferedOutputStream(new FileOutputStream(fileName)), StandardCharsets.UTF_8))) {
		
			for(int i = 0; i < xs.length; i++) {
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
	
}
