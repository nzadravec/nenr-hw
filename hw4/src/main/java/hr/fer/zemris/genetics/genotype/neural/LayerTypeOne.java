package hr.fer.zemris.genetics.genotype.neural;

public class LayerTypeOne extends Layer {
	
	protected int[] offsets; // for each neuron in layer
	private double[] output;
	
	protected int numOfNeuronsInThisLayer;
	protected int numOfNeuronsInPrevLayer;
	
	public LayerTypeOne(int numOfNeuronsInPrevLayer, int numOfNeuronsInThisLayer, int[] offsets, NeuralNetwork ffnn) {
		super(ffnn);
		this.numOfNeuronsInPrevLayer = numOfNeuronsInPrevLayer;
		this.numOfNeuronsInThisLayer = numOfNeuronsInThisLayer;
		this.offsets = offsets;
		this.ffnn = ffnn;

		output = new double[numOfNeuronsInThisLayer];
	}

	public double[] calcOutput(double[] input) {
		
//		System.out.println("LayerTypeOne");

		for (int i = 0; i < numOfNeuronsInThisLayer; i++) {
//			System.out.println("neuron "+i);
			output[i] = 1 / (1 + calcSimilarityForNeuron(i, input));
		}

		return output;
	}
	
	private double calcSimilarityForNeuron(int index, double[] input) {
		double similarity = 0;
		double[] weights = ffnn.getParams();
		
		for (int i = 0; i < numOfNeuronsInPrevLayer; i++) {
//			System.out.println("w"+i+" "+(offsets[index] + i*2)+" "+weights[offsets[index] + i*2]);
//			System.out.println("s"+i+" "+(offsets[index] + i*2 + 1)+" "+weights[offsets[index] + i*2 + 1]);
			similarity += Math.abs(input[i] - weights[offsets[index] + i*2]) / 
							Math.abs(weights[offsets[index] + i*2 + 1]);
		}
		
		return similarity;
	}

	public double[] getOutput() {
		return output;
	}

}
