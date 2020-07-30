package hr.fer.zemris.genetics.decoder;

public class NaturalBinaryDecoder extends BitvectorDecoder {

	public NaturalBinaryDecoder(double min, double max, int bits, int n) {
        super(min, max, bits, n);
    }

    public NaturalBinaryDecoder(double[] mins, double[] maxs, int[] bits, int n) {
        super(mins, maxs, bits, n);
    }

	@Override
	public double[] decode(boolean[] genotype) {
		double[] values = new double[n];

        int index = 0;
        int sum = 0;
        int length = bits[0];
        
        for (int i = 0; i < totalBits; i++) {
            boolean bit = genotype[i];
            sum *= 2;
            if (bit) {
                sum++;
            }

            length--;

            if (length == 0) {            	
                double range = maxs[index] - mins[index];
                double maxValue = Math.pow(2, bits[index]) - 1.0;
                values[index] = mins[index] + (sum / maxValue)* range;

                index++;
                if (index >= bits.length) {
                    break;
                }
                length = bits[index];
                sum = 0;
            }
        }

        return values;
	}

}
