package hr.fer.zemris.neuro;

import java.util.Random;

import hr.fer.zemris.neuro.util.RandomGen;

public class Layer {
	
	private ActivationFunction activation;
	private boolean hiddenLayer;
	
	private int numOfNeurons;
	private int numOfNeuronsInPrevLayer;
	private int numOfNeuronsInNextLayer;
	
	private double[][] inputWeights;
	private double[][] outputWeights;
	private double[] biases;
	
	private double[] output;
	private double[] error;
	
	public Layer(ActivationFunction activation, boolean hiddenLayer, double[][] inputWeights, double[][] outputWeights) {
		super();
		this.activation = activation;
		this.hiddenLayer = hiddenLayer;
		this.inputWeights = inputWeights;
		this.outputWeights = outputWeights;
		
		numOfNeurons = inputWeights[0].length;
		numOfNeuronsInPrevLayer = inputWeights.length;
		
		biases = new double[numOfNeurons];
		initializeBiases();
		
		if(hiddenLayer) {
			numOfNeuronsInNextLayer = outputWeights[0].length;
		}
		
		output = new double[numOfNeurons];
		error = new double[numOfNeurons];
	}
	
	private void initializeBiases() {
		Random rand = RandomGen.getRand();
		for(int i = 0; i < biases.length; i++) {
			biases[i] = (2*rand.nextDouble() - 1) / 1;
		}
	}
	
	public double[] processInput(double[] input) {
		
		for(int i = 0; i < numOfNeurons; i++) {
			output[i] = calcOutputOfNeuron(i, input);
		}
		
		return output;
	}
	
	private double calcOutputOfNeuron(int i, double[] input) {
		double net = biases[i];

		for(int j = 0; j < numOfNeuronsInPrevLayer; j++) {
			net += inputWeights[j][i] * input[j];
		}
		
//		if(hiddenLayer) {
//			net += biases[i];
//		}

		return activation.activation(net);
	}
	
	public double[] processError(double[] error) {
		if(!hiddenLayer) {
			for(int i = 0; i < numOfNeurons; i++) {
				this.error[i] = activation.deriv(output[i]) * error[i];
			}
		} else {
			for(int i = 0; i < numOfNeurons; i++) {
				double sum = 0;
				for(int j = 0; j < numOfNeuronsInNextLayer; j++) {
					sum += error[j]*outputWeights[i][j];
				}
				this.error[i] = activation.deriv(output[i]) * sum;
			}
		}
		
		return this.error;
	}
	
	public void updateWeights(double[][] weightsUpdate) {
		for(int i = 0; i < numOfNeuronsInPrevLayer; i++) {
			for(int j = 0; j < numOfNeurons; j++) {
				this.inputWeights[i][j] += weightsUpdate[i][j];
			}
		}
	}
	
	public void updateBiases(double[] biasesUpdate) {
		for(int i = 0; i < numOfNeurons; i++) {
			this.biases[i] += biasesUpdate[i];
		}
	}
	
	public double[] getOutput() {
		return output;
	}

	public double[] getError() {
		return error;
	}

	public double[][] getInputWeights() {
		return inputWeights;
	}

	public void setInputWeights(double[][] inputWeights) {
		this.inputWeights = inputWeights;
	}

	public double[][] getOutputWeights() {
		return outputWeights;
	}

	public void setOutputWeights(double[][] outputWeights) {
		this.outputWeights = outputWeights;
	}

	public double[] getBiases() {
		return biases;
	}

	public void setBiases(double[] biases) {
		this.biases = biases;
	}

	public void setOutput(double[] output) {
		this.output = output;
	}
	
}
