package hr.fer.zemris.genetics.genotype.neural;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Dataset {
	
	private List<Data> dataset;
	
	public Dataset(List<Data> dataset) {
		this.dataset = dataset;
	}
	
	public int size() {
		return dataset.size();
	}
	
	public Data get(int i) {
		return dataset.get(i);
	}
	
	public List<Data> getAll() {
		return dataset;
	}
	
	public static Dataset load(String fileName, int inputSize, int outputSize) {
		List<String> lines = new ArrayList<>();

		try (Scanner sc = new Scanner(new File("./data/" + fileName))) {

			while (sc.hasNext()) {
				String s = sc.nextLine();
				lines.add(s);
			}

		} catch (FileNotFoundException exc) {
		}
		
		List<Data> dataset = new ArrayList<>();
		
		for(int i = 0; i < lines.size(); i++) {
			String[] ss = lines.get(i).split("\t");
			
			double[] input = new double[inputSize];
			for(int j = 0; j < inputSize; j++) {
				input[j] = Double.parseDouble(ss[j].trim());
			}
			
			double[] output = new double[outputSize];
			for(int j = 0; j < outputSize; j++) {
				output[j] = Double.parseDouble(ss[inputSize + j].trim());
			}
			
			dataset.add(new Data(input, output));
		}
		
		return new Dataset(dataset);
	}
	
	public static class Data {
		
		private double[] x;
		private double[] y;
		
		public Data(double[] x, double[] y) {
			super();
			this.x = x;
			this.y = y;
		}
		
		public double[] getX() {
			return x;
		}
		public double[] getY() {
			return y;
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(Arrays.toString(x));
			sb.append(Arrays.toString(y));
			return sb.toString();
		}
	}

}
