package hr.fer.zemris.genetics.decoder;

public interface IDecoder<G> {
	
	double[] decode(G genotype);
	void decode(G genotype, double[] result);

}
