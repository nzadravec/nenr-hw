package hr.fer.zemris.genetics.genotype.neural;

import java.util.Arrays;

import hr.fer.zemris.genetics.genotype.neural.Dataset.Data;

public class NeuralNetwork {
	
	private double[] params;
	private LayerTypeOne firstHiddenLayer;
	private Layer[] layers;
	
	private int numOfInputs;
	private int numOfOutputs;
	private int numOfParams;
	
	private int numOfNeurons;
	private int[] startIndices;
	private int[] endIndices;
	
	private int startOfLayerType1 = 0;
	private int endOfLayerType1;
	private int startOfLayerType2;
	private int endOfLayerType2; 
	
	public NeuralNetwork(int[] neuronsPerLayer) {
		
		numOfNeurons = 1;
		for(int i = 0; i < neuronsPerLayer.length; i++) {
			numOfNeurons *= neuronsPerLayer[i];
		}
		startIndices = new int[numOfNeurons];
		endIndices = new int[numOfNeurons];
		
		numOfInputs = neuronsPerLayer[0];
		numOfOutputs = neuronsPerLayer[neuronsPerLayer.length - 1];
		layers = new Layer[neuronsPerLayer.length - 1];
		
		int index = 0;
		int offset = 0;
		Layer layer = null;
		int[] offsets = new int[neuronsPerLayer[1]];
		for (int j = 0; j < neuronsPerLayer[1]; j++) {
			startIndices[index] = offset;
			offsets[j] = offset;
			offset += 2*neuronsPerLayer[0];
			endIndices[index] = offset;
			index++;
		}
		firstHiddenLayer = new LayerTypeOne(neuronsPerLayer[0], neuronsPerLayer[1], offsets, this);
		layers[0] = firstHiddenLayer;
		
		endOfLayerType1 = offset;
		startOfLayerType2 = offset;
		
		TransferFunction tf = new Sigmoid();
		for (int i = 2; i < neuronsPerLayer.length; i++) {

			offsets = new int[neuronsPerLayer[i]];
			for (int j = 0; j < neuronsPerLayer[i]; j++) {
				startIndices[index] = offset;
				offsets[j] = offset;
				offset += neuronsPerLayer[i - 1] + 1;
				endIndices[index] = offset;
				index++;
			}

			layer = new LayerTypeTwo(neuronsPerLayer[i - 1], neuronsPerLayer[i], tf, offsets, this);
			layers[i - 1] = layer;
			
		}
		endOfLayerType2 = offset;
		numOfParams = offset;
	}
	
	public double[] calcOutput(double[] input, double[] params) {
		this.params = params;
		
		double[] output = null;
		for(int i = 0; i < layers.length-1; i++) {
			output = layers[i].calcOutput(input);
			input = output;
		}
		
		return layers[layers.length-1].calcOutput(input);
	}
	
	public double[] calcOutput(double[] input) {
		double[] output = null;
		for(int i = 0; i < layers.length-1; i++) {
			output = layers[i].calcOutput(input);
			input = output;
		}
		
		return layers[layers.length-1].calcOutput(input);
	}
	
	public double calcError(Dataset dataset, double[] params) {
		this.params = params;
		
		double squareError = 0;
		double[] input;
		double[] output;
		double[] target;
		for(int i = 0; i < dataset.size(); i++) {
			Data data = dataset.get(i);
			input = data.getX();
			target = data.getY();
			output = calcOutput(input);
			
			for(int j = 0; j < output.length; j++) {
				squareError += Math.pow(target[j] - output[j], 2);
			}
		}
		return squareError / dataset.size();
	}
	
	public int[] predict(Dataset dataset, double[] params) {
		this.params = params;
		
		int[] labels = new int[dataset.size()];
		
		double[] input;
		double[] output;
		double[] target;
		double[] output2 = new double[3];
		int correctPredictions = 0;
		for(int i = 0; i < dataset.size(); i++) {
			Data data = dataset.get(i);
			input = data.getX();
			target = data.getY();
			output = calcOutput(input);
			
			//System.out.println(Arrays.toString(firstHiddenLayer.getOutput()));
			//System.out.println("output: "+Arrays.toString(output));
			//System.out.println();
			
			int index = 0;
			for(int j = 1; j < output.length; j++) {
				if(output[j] > output[index]) {
					index = j;
				}
			}
			
			for(int j = 0; j < output.length; j++) {
				if(output[j] < 0.5) {
					output2[j] = 0;
				} else {
					output2[j] = 1;
				}
			}
			
			labels[i] = index;
			
			if(Arrays.equals(target, output2)) {
				//System.out.println("TRUE");
				correctPredictions++;
			} else {
				System.out.println();
				System.out.println("output: "+Arrays.toString(output));
				System.out.println("target: "+Arrays.toString(target));
				System.out.println("class:  "+Arrays.toString(output2));
			}
			//System.out.println();
		}
		
		System.out.println(correctPredictions+"/"+dataset.size());
		System.out.println((correctPredictions / (double)dataset.size()) * 100+" %");
		
		return labels;
	}
	
	public double[] getParams() {
		return params;
	}
	
	public int getNumOfInputs() {
		return numOfInputs;
	}

	public int getNumOfOutputs() {
		return numOfOutputs;
	}

	public int getNumOfParams() {
		return numOfParams;
	}

	public int getNumOfNeurons() {
		return numOfNeurons;
	}

	public int[] getStartIndices() {
		return startIndices;
	}

	public int[] getEndIndices() {
		return endIndices;
	}
	
	public int getStartIndexOfNeuron(int i) {
		return startIndices[i];
	}

	public int getEndIndexOfNeuron(int i) {
		return endIndices[i];
	}

	public int getStartOfLayerType1() {
		return startOfLayerType1;
	}

	public int getEndOfLayerType1() {
		return endOfLayerType1;
	}

	public int getStartOfLayerType2() {
		return startOfLayerType2;
	}

	public int getEndOfLayerType2() {
		return endOfLayerType2;
	}

}
