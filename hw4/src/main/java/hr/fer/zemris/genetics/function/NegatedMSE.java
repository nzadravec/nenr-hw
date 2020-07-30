package hr.fer.zemris.genetics.function;

import static java.lang.Math.cos;
import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.genetics.decoder.IDecoder;

public class NegatedMSE<G> implements FitnessFunction<G> {
	
	private Sample[] samples;
	private IDecoder<G> decoder;
	
	public NegatedMSE(Sample[] samples, IDecoder<G> decoder) {
		this.samples = samples;
		this.decoder = decoder;
	}

	@Override
	public double fitnessOf(G genotype) {
		
		double[] inputs = decoder.decode(genotype);
		
		double squareError = 0;
		for(Sample s : samples) {
			squareError += Math.pow(s.output - predict(s.inputs, inputs), 2);
		}
		
		return -(squareError / samples.length);
	}
	
	private double predict(double[] inputs, double[] params) {
		double x = inputs[0];
		double y = inputs[1];
	
		double b0 = params[0];
		double b1 = params[1];
		double b2 = params[2];
		double b3 = params[3];
		double b4 = params[4];
		
		return sin(b0 + b1*x) + b2*cos(x*(b3 + y))*(1/(1 + exp(pow(x - b4, 2))));
	}

	public static Sample[] samplesFromFile(String textFile) {
		List<String> lines = new ArrayList<>();

		try (Scanner sc = new Scanner(new File("./data/" + textFile))) {

			while (sc.hasNext()) {
				String s = sc.nextLine();
				lines.add(s);
			}

		} catch (FileNotFoundException exc) {
		}
		
		Sample[] samples = new Sample[lines.size()];
		int inputSize = lines.get(0).split("\t").length - 1;
		
		for(int i = 0; i < lines.size(); i++) {
			String[] ss = lines.get(i).split("\t");
			
			double[] inputs = new double[inputSize];
			for(int j = 0; j < inputSize; j++) {
				inputs[j] = Double.parseDouble(ss[j].trim());
			}
			double output = Double.parseDouble(ss[inputSize].trim());
			
			samples[i] = new Sample(inputs, output);
		}
		
		return samples;
	}
	
	public static class Sample {
		private double[] inputs;
		private double output;
		
		public Sample(double[] inputs, double output) {
			super();
			this.inputs = inputs;
			this.output = output;
		}

		public double[] getInputs() {
			return inputs;
		}

		public double getOutput() {
			return output;
		}
		
	}
	
}
