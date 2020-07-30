package hr.fer.zemris.genetics.decoder;

import static java.lang.Math.ceil;
import static java.lang.Math.log;
import static java.lang.Math.pow;

public class GrayBinaryDecoder extends BitvectorDecoder {

	private NaturalBinaryDecoder decoder;
	
	public GrayBinaryDecoder(double[] mins, double[] maxs, int[] bits, int n) {
        super(mins, maxs, bits, n);
        decoder = new NaturalBinaryDecoder(mins, maxs, bits, n);
    }

	public GrayBinaryDecoder(double min, double max, int bits, int n) {
		super(min, max, bits, n);
		decoder = new NaturalBinaryDecoder(min, max, bits, n);
	}
	
	public GrayBinaryDecoder(double min, double max, double precision, int n) {
		this(min, max, (int)ceil(log(1 + (max - min) * pow(10, precision)) / log(2)), n);
	}

	@Override
	public double[] decode(boolean[] genotype) {
		return decoder.decode(grayToNaturalCode(genotype));
	}
	
}
