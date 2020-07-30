package hr.fer.zemris.neuro;

import java.util.Random;

import hr.fer.zemris.neuro.util.RandomGen;

public class FFNN {
	
	private int[] neuronsPerLayer;
	
	private Layer[] layers;
	
	public FFNN(int[] neuronsPerLayer, ActivationFunction[] activationPerLayer) {
		
		if(activationPerLayer.length != neuronsPerLayer.length-1) {
			throw new RuntimeException();
		}
		
		this.neuronsPerLayer = neuronsPerLayer;
		
		layers = new Layer[neuronsPerLayer.length - 1];
		
		double[][] inputWeights = new double[neuronsPerLayer[0]][neuronsPerLayer[1]];
		initializeWeigths(inputWeights);
		
		for(int i = 0; i < layers.length-1; i++) {
			
			int numOfNeurons = neuronsPerLayer[i+1];
			int numOfNeuronsInNextLayer = neuronsPerLayer[i+2];
			ActivationFunction activation = activationPerLayer[i];
			
			double[][] outputWeights = new double[numOfNeurons][numOfNeuronsInNextLayer];
			initializeWeigths(outputWeights);
			
			layers[i] = new Layer(activation, true, inputWeights, outputWeights);
			
			inputWeights = outputWeights;
		}
		
		ActivationFunction activation = activationPerLayer[layers.length-1];
		layers[layers.length-1] = new Layer(activation, false, inputWeights, null);
		
	}
	
	private void initializeWeigths(double[][] wss) {
		Random rand = RandomGen.getRand();
		for(int i = 0; i < wss.length; i++) {
			for(int j = 0; j < wss[i].length; j++) {
				//wss[i][j] = rand.nextDouble();
				wss[i][j] = (-1 + 2*rand.nextDouble()) / 1;
				//wss[i][j] = rand.nextGaussian();
			}
		}
	}
	
	public double[] processInput(double[] input) {
		double[] output = null;
		for(int i = 0; i < layers.length-1; i++) {
			output = layers[i].processInput(input);
			input = output;
		}
		
		return layers[layers.length-1].processInput(input);
	}

	public void processError(double[] error) {
		
		for(int i = layers.length-1; i >= 0; i--) {
			error = layers[i].processError(error);
		}
		
	}
	
	public int[] getNeuronsPerLayer() {
		return neuronsPerLayer;
	}
	
	public Layer[] getLayers() {
		return layers;
	}

}
