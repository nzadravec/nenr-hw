package hr.fer.zemris.neuro;

public class Sigmoid implements ActivationFunction {

	@Override
	public double activation(double net) {
		return 1. / (1 + Math.exp(-net));
	}

	@Override
	public double deriv(double output) {
		//double value = activation(net);
		return output * (1 - output);
	}
	
}