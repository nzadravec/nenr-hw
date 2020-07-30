package hr.fer.zemris.genetics.genotype.neural;

public class Main {

	public static void main(String[] args) {
		
		int[] neuronsPerLayer = new int[] {2,8,3};
		NeuralNetwork neural = new NeuralNetwork(neuronsPerLayer);
		System.out.println(neural.getNumOfParams());
		double[] params = new double[neural.getNumOfParams()];
		neural.calcOutput(new double[] {1.0, 1.0}, params);
		
	}
	
}
