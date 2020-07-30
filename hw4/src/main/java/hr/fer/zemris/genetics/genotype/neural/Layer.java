package hr.fer.zemris.genetics.genotype.neural;

public abstract class Layer {
	
	protected NeuralNetwork ffnn;
	
	public Layer(NeuralNetwork ffnn) {
		this.ffnn = ffnn;
	}
	
	public abstract double[] calcOutput(double[] input);

}
