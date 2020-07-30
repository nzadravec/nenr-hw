package hr.fer.zemris.genetics.genotype.neural;

public class LayerTypeTwo extends Layer {
	
	private TransferFunction function;
	
	protected int[] offsets; // for each neuron in layer
	private double[] output;
	
	protected int numOfNeuronsInThisLayer;
	protected int numOfNeuronsInPrevLayer;
	
	public LayerTypeTwo(int numOfNeuronsInPrevLayer, int numOfNeuronsInThisLayer, TransferFunction function, int[] offsets, NeuralNetwork ffnn) {
		super(ffnn);
		this.numOfNeuronsInPrevLayer = numOfNeuronsInPrevLayer;
		this.numOfNeuronsInThisLayer = numOfNeuronsInThisLayer;
		this.function = function;
		this.offsets = offsets;
		this.ffnn = ffnn;

		output = new double[numOfNeuronsInThisLayer];
	}
	
	public double[] calcOutput(double[] input) {
		
//		System.out.println("LayerTypeTwo");

		for (int i = 0; i < numOfNeuronsInThisLayer; i++) {
//			System.out.println("neuron "+i);
			output[i] = function.activation(calcNetOfNeuron(i, input));
		}

		return output;
	}
	
	private double calcNetOfNeuron(int index, double[] input) {
		double net = 0;
		double[] weights = ffnn.getParams();
		
		for (int i = 0; i < numOfNeuronsInPrevLayer; i++) {
//			System.out.println("w"+i+" "+(offsets[index] + i)+" "+weights[offsets[index] + i]);
			net += weights[offsets[index] + i] * input[i];
		}
//		System.out.println("w"+numOfNeuronsInPrevLayer+" "+(offsets[index] + numOfNeuronsInPrevLayer)+" "+weights[offsets[index] + numOfNeuronsInPrevLayer]);
		net += weights[offsets[index] + numOfNeuronsInPrevLayer];

		return net;
	}

}
