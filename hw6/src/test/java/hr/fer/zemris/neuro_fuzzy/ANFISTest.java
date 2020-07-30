package hr.fer.zemris.neuro_fuzzy;

import static java.lang.Math.cos;
import static java.lang.Math.pow;

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

public class ANFISTest {
	
	private static final String fileName = "./data/samples2.txt";
	private static final int inputSize = 2;
	
	//private static final int batchSize = 81;
	private static final int batchSize = 1;
	
	// 1 pravila
//	private static int numOfRules = 1;
//	private static double eta = 0.00001;
//	private static double eta1 = 0.000001;
	
	// 2 pravila
//	private static int numOfRules = 2;
//	private static double eta = 0.001;
//	private static double eta1 = 0.000005;
	
	// 3 pravila
//	private static int numOfRules = 3;
//	private static double eta = 0.0001;
//	private static double eta1 = 0.00001;
	
	// 4 pravila
//	private static int numOfRules = 4;
//	private static double eta = 0.0001;
//	private static double eta1 = 0.00001;
	
	// 4 pravila
	private static int numOfRules = 4;
	private static double eta = 0.001;
	private static double eta1 = 0.00005;
	
	// 6 pravila
//	private static int numOfRules = 6;
//	private static double eta = 1;
//	private static double eta1 = 0.1;
	
	// 10 pravila
//	private static double eta = 0.1;
//	private static double eta1 = 0.004;
	
	private static double epsilon = 1e-6;
	private static int maxIter = 100_000;
	
	private static final boolean saveSets = false;
	private static final String fileName1 = "./data/sets.txt";
	
	private static final boolean saveSampleErrs = false;
	private static final String fileName2 = "./data/sample_errs2.txt";
	
	private static final boolean saveSamples = false;
	private static final String fileName4 = "./data/samples.txt";
	
	private static final boolean saveErrs = false;
	private static List<String> errList = new ArrayList<>();
	private static final String fileName3 = "./data/errs.txt";
	
	public static void main(String[] args) {
		
		if(args.length != 1) {
			throw new RuntimeException("Fali argument, broj pravila");
		}
		
		//numOfRules = Integer.parseInt(args[0]);
		
		BackPropANFIS backPropAnfis = new BackPropANFIS(inputSize, numOfRules, eta, eta1, epsilon, maxIter);
		List<List<Sample>> batchSamples = createBatches(loadSamplesFromFile(fileName));
		
		Listener listener1 = (epoch, error) -> errList.add(epoch+" "+error);
		backPropAnfis.addListener(listener1);
		
		backPropAnfis.learn(batchSamples);
		
		if(saveSets) {
		
			try {
				saveSets(backPropAnfis.getSetsPerRule());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if(saveSamples) {
			try {
				saveSamples(backPropAnfis.getAnfis());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(saveSampleErrs) {
			try {
				saveSampleErrs(backPropAnfis.getAnfis());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(saveErrs) {
			try {
				saveErrs();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private static void saveErrs() throws FileNotFoundException, IOException {
		
		try (Writer w = new BufferedWriter(new OutputStreamWriter(
				new BufferedOutputStream(new FileOutputStream(fileName3)), StandardCharsets.UTF_8))) {
		
			for(String err : errList) {
				w.write(err+"\n");
			}
			
		}
		
	}
	
	private static double f(double x, double y) {
		return (pow(x-1, 2) + pow(y+2, 2)-5*x*y + 3)*pow(cos(x/5),2);
	}
	
	private static void saveSamples(ANFIS anfis) throws FileNotFoundException, IOException {
		
		try (Writer w = new BufferedWriter(new OutputStreamWriter(
				new BufferedOutputStream(new FileOutputStream(fileName4)), StandardCharsets.UTF_8))) {
		
			for(double x = -4; x < 4; x+=.1) {
				for(double y = -4; y < 4; y+=.1) {
					double output = anfis.predict(x, y);
					double target = f(x, y);
					//w.write(x+" "+y+" "+(output-target)+"\n");
					w.write(x+" "+y+" "+(output)+" "+target+"\n");
				}
			}
			
		}
		
	}
	
	private static void saveSampleErrs(ANFIS anfis) throws FileNotFoundException, IOException {
		
		try (Writer w = new BufferedWriter(new OutputStreamWriter(
				new BufferedOutputStream(new FileOutputStream(fileName2)), StandardCharsets.UTF_8))) {
		
			for(double x = -4; x < 4; x++) {
				for(double y = -4; y < 4; y++) {
					double output = anfis.predict(x, y);
					double target = f(x, y);
					w.write(x+" "+y+" "+(output-target)+"\n");
				}
			}
			
		}
		
	}
	
	private static void saveSets(SigmoidFuzzySet[][] setsPerRule) throws FileNotFoundException, IOException {
		
		try (Writer w = new BufferedWriter(new OutputStreamWriter(
				new BufferedOutputStream(new FileOutputStream(fileName1)), StandardCharsets.UTF_8))) {
		
			for(int i = 0; i < numOfRules; i++) {
				for(int j = 0; j < inputSize; j++) {
					SigmoidFuzzySet set = setsPerRule[i][j];
					w.write(set.getA()+" "+set.getB()+" ");
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
