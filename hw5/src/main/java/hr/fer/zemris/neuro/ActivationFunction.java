package hr.fer.zemris.neuro;

public interface ActivationFunction {
	
	double activation(double net);

	double deriv(double net);

}
