package hr.fer.zemris.neuro;

import java.util.ArrayList;
import java.util.List;

public class BackPropLearning {
	
	private double learningRate;
	private double epsilon;
	private int maxIter;
	
	public BackPropLearning(double learningRate, double epsilon, int maxIter) {
		this.learningRate = learningRate;
		this.epsilon = epsilon;
		this.maxIter = maxIter;
	}
	
	public void learn(FFNN ffnn, List<List<Sample>> batchSamples) {
		
		List<Sample> samples = new ArrayList<>();
		for(List<Sample> batch : batchSamples) {
			for(Sample s : batch) {
				samples.add(s);
			}
		}
		
		int[] neuronsPerLayer = ffnn.getNeuronsPerLayer();
		Layer[] layers = ffnn.getLayers();
		
		double[] input = null;
		double[] output = null;
		double[] target = null;
		double[] delta = null;
		double[] error = new double[neuronsPerLayer[neuronsPerLayer.length-1]];
		
		double[][][] weightsGradient = new double[layers.length][][];
		for(int i = 0; i < weightsGradient.length; i++) {
			weightsGradient[i] = new double[neuronsPerLayer[i]][neuronsPerLayer[i+1]];
		}
		
		//double[][] biasesGradient = new double[layers.length-1][];
		double[][] biasesGradient = new double[layers.length][];
		for(int i = 0; i < biasesGradient.length; i++) {
			biasesGradient[i] = new double[neuronsPerLayer[i+1]];
		}
		
		int iter = 0;
		while(iter < maxIter) {
			iter++;
			
			double mse = mse(ffnn, samples);
			if(mse < epsilon) {
				break;
			}
			
			if(iter % 1000 == 0) {
				System.out.println("iter: "+iter+", err = "+mse);
			}
		
			for(List<Sample> batch : batchSamples) {
				
				setWeightsGradientToZero(weightsGradient);
				setBiasesGradientToZero(biasesGradient);
				
				for(Sample s : batch) {
					
					input = s.getInput();
					target = s.getTarget();
					
					output = ffnn.processInput(input);
					//input = s.getInput();
					for(int i = 0; i < output.length; i++) {
						error[i] = target[i] - output[i];
					}
					ffnn.processError(error);
					
					for(int k = 0; k < layers.length; k++) {
						
						if(k == 0) {
							output = input;
						} else {
							output = layers[k-1].getOutput();
						}
						delta = layers[k].getError();
						
						for(int i = 0; i < weightsGradient[k].length; i++) {
							for(int j = 0; j < weightsGradient[k][i].length; j++) {
								weightsGradient[k][i][j] += delta[j] * output[i];
							}
						}
						
						if(k < biasesGradient.length) {
							for(int i = 0; i < biasesGradient[k].length; i++) {
								biasesGradient[k][i] += delta[i];
							}
						}
						
					}
					
				}
				
				for(int k = 0; k < weightsGradient.length; k++) {
					for(int i = 0; i < weightsGradient[k].length; i++) {
						for(int j = 0; j < weightsGradient[k][i].length; j++) {
							weightsGradient[k][i][j] *= learningRate;
						}
					}
				}
				
				for(int k = 0; k < biasesGradient.length; k++) {
					for(int i = 0; i < biasesGradient[k].length; i++) {
						biasesGradient[k][i] *= learningRate;
					}
				}
				
				for(int k = 0; k < layers.length; k++) {
					layers[k].updateWeights(weightsGradient[k]);
					
					if(k < biasesGradient.length) {
						layers[k].updateBiases(biasesGradient[k]);
					}
				}
				
//				for(int i = 0; i < layers.length-1; i++) {
//				double[][] wss = layers[i].getOutputWeights();
//				wss[0][0] = 0;
//				printWeights(layers[i].getOutputWeights());
//				System.out.println();
//				printWeights(layers[i+1].getInputWeights());
//				System.out.println();
//				System.out.println();
//			}
				
				//return; 
			}
		}
		
		double mse = mse(ffnn, samples);
		System.out.println("iter: "+iter+", err = "+mse+" - izlazim van!");
		
	}
	
	private double mse(FFNN ffnn, List<Sample> samples) {
		double mse = 0;
		
		double[] input = null;
		double[] output = null;
		double[] target = null;
		
		for(Sample s : samples) {
			input = s.getInput();
			target = s.getTarget();
			
			output = ffnn.processInput(input);
			for(int i = 0; i < output.length; i++) {
				mse += Math.pow(target[i]-output[i], 2);
			}
			
		}
		
		return mse / (2*samples.size());
	}
	
	private void printWeights(double[][] wss) {
		for(int i = 0; i < wss.length; i++) {
			for(int j = 0; j < wss[i].length; j++) {
				System.out.print(wss[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	private void setWeightsGradientToZero(double[][][] weightsGradient) {
		for(int k = 0; k < weightsGradient.length; k++) {
			for(int i = 0; i < weightsGradient[k].length; i++) {
				for(int j = 0; j < weightsGradient[k][i].length; j++) {
					weightsGradient[k][i][j] = 0;
				}
			}
		}
	}
	
	private void setBiasesGradientToZero(double[][] biasesGradient) {
		for(int k = 0; k < biasesGradient.length; k++) {
			for(int i = 0; i < biasesGradient[k].length; i++) {
				biasesGradient[k][i] = 0;
			}
		}
	}

}
