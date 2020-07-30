package hr.fer.zemris.neuro;

import java.util.Arrays;

import hr.fer.zemris.neuro.ActivationFunction;
import hr.fer.zemris.neuro.FFNN;
import hr.fer.zemris.neuro.Layer;
import hr.fer.zemris.neuro.Sigmoid;

public class Test {
	
	private static final int inputSize = 2;
	private static final int outputSize = 2;
	
	private static ActivationFunction sigmoid = new Sigmoid();
	
	private static final int[] neuronsPerLayer = new int[] {inputSize, 1, outputSize};
	private static final ActivationFunction[] activationPerLayer = 
			new ActivationFunction[] {sigmoid, sigmoid};
	
	public static void main(String[] args) {
		
		FFNN ffnn = new FFNN(neuronsPerLayer, activationPerLayer);
		Layer[] ls = ffnn.getLayers();
		double[][] wss = new double[inputSize][1];
		wss[0][0] = 0.13436424411240122;
		wss[1][0] = 0.8474337369372327;
		double[] bias = new double[1];
		bias[0] = 0.763774618976614;
		ls[0].setInputWeights(wss);
		ls[0].setBiases(bias);
		
		wss = new double[1][outputSize];
		wss[0][0] = 0.2550690257394217;
		wss[0][1] = 0.4494910647887381;
		bias = new double[outputSize];
		bias[0] = 0.49543508709194095;
		bias[1] = 0.651592972722763;
		ls[1].setInputWeights(wss);
		ls[1].setBiases(bias);
		
		double[] input = new double[] {1, 0};
		double[] output = ffnn.processInput(input);
		//System.out.println(Arrays.toString(output));
		
		output = new double[] {0.6213859615555266, 0.6573693455986976};
		ls[1].setOutput(output);
		output = new double[] {0.7105668883115941};
		ls[0].setOutput(output);
		
		double[] error = new double[] {0,1};
		
		ffnn.processError(error);
		System.out.println(Arrays.toString(ls[0].getError()));
		System.out.println(Arrays.toString(ls[1].getError()));
	}

}
