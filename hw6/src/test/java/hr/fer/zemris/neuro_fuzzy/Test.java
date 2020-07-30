package hr.fer.zemris.neuro_fuzzy;

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
import java.util.List;
import java.util.Scanner;

public class Test {
	
	private static final String fileName = "./data/samples2.txt";
	private static final int inputSize = 2;
	
	private static final int batchSize = 81;
	
	private static double[] etas1 = new double[] {0.001, 0.0001, 0.00001};
	private static double[] etas2 = new double[] {0.001, 0.0001, 0.00001};
	
	private static double epsilon = 1e-6;
	private static int maxIter = 20000;
	
	private static int numOfRules;
	
	private static List<List<Double>> dss = new ArrayList<>();
	private static final String fileName1 = "./data/etas.txt";
	
	//private static final int batchSize = 1;
	//private static double[] etas = new double[] {0.009, 1e-3, 1e-8};
	
	//private static final String fileName1 = "./data/etas2.txt";
	
	public static void main(String[] args) {
		
		if(args.length != 1) {
			throw new RuntimeException("Fali argument, broj pravila");
		}
		
		numOfRules = Integer.parseInt(args[0]);
		
		List<List<Sample>> batchSamples = createBatches(loadSamplesFromFile(fileName));
		
		for(int i = 0; i < etas1.length; i++) {
			dss.add(new ArrayList<>());
		}
		
		for(int i = 0; i < etas1.length; i++) {
			
			BackPropANFIS backPropAnfis = new BackPropANFIS(inputSize, numOfRules, etas1[i], etas2[i], epsilon, maxIter);
			List<Double> ds = dss.get(i);
			Listener listener1 = (epoch, error) -> ds.add(error);
			backPropAnfis.addListener(listener1);
			
			backPropAnfis.learn(batchSamples);
			System.out.println();
		}
		
		try {
			saveEtas();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void saveEtas() throws FileNotFoundException, IOException {
		
		try (Writer w = new BufferedWriter(new OutputStreamWriter(
				new BufferedOutputStream(new FileOutputStream(fileName1)), StandardCharsets.UTF_8))) {
			
			int numOfEpochs = dss.get(0).size();
			for(int i = 0; i < numOfEpochs; i++) {
				for(int j = 0; j < etas1.length; j++) {
					w.write(dss.get(j).get(i)+" ");
				}
				w.write("\n");
			}
			
		}
		
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
		try (Scanner sc = new Scanner(new File(fileName))) {

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
			
			double target = Double.parseDouble(ss[inputSize].trim());
			
			samples.add(new Sample(input, target));
		}
		
		return samples;
	}

}
