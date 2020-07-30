package hr.fer.zemris.genetics.decoder;

public class PassThroughDecoder implements IDecoder<double[]> {

	@Override
	public double[] decode(double[] genotype) {
		return genotype;
	}

	@Override
	public void decode(double[] genotype, double[] result) {
		result = genotype;
	}

}
