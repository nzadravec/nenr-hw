package hr.fer.zemris.genetics.decoder;

import static java.lang.Math.*;

public abstract class BitvectorDecoder implements IDecoder<boolean[]> {

	protected double[] mins;
    protected double[] maxs;
    
    protected int[] bits;
    protected int n;
    protected int totalBits;
    
    public BitvectorDecoder(double[] mins, double[] maxs, int[] bits, int n) {
        this.mins = mins;
        this.maxs = maxs;
        this.bits = bits;
        this.n = n;

        totalBits = 0;
        for (int i = 0; i < n; ++i) {
            totalBits += bits[i];
        }
    }
    
    public BitvectorDecoder(double min, double max, int bits, int n) {
        this.mins = new double[n];
        this.maxs = new double[n];
        this.bits = new int[n];
        this.n = n;

        for (int i = 0; i < n; ++i) {
            mins[i] = min;
            maxs[i] = max;
            this.bits[i] = bits;
        }

        totalBits = n * bits;
    }
    
    public BitvectorDecoder(double min, double max, double decimals, int n) {
    	this(min, max, (int)ceil(log(1 + (max - min) * pow(10, decimals)) / log(2)), n);
    }
    
    public int getTotalBits() {
        return totalBits;
    }
    
    public int getDimensions() {
        return n;
    }
	
    public abstract double[] decode(boolean[] genotype);

	@Override
	public void decode(boolean[] genotype, double[] result) {
		result = decode(genotype);
	}
	
	public static boolean[] grayToNaturalCode(boolean[] grayCode) {
		boolean tmp = grayCode[0];
		boolean[] naturalCode = new boolean[grayCode.length];
		naturalCode[0] = grayCode[0];
		
		for(int i = 1; i < grayCode.length; i++) {
			if(grayCode[i]) {
				tmp = !tmp;
			}
			naturalCode[i] = tmp;
		}
		
		return naturalCode;
	}
	
	public static int numOfBitsFor(double precision, double minBound, double maxBound) {
		return (int)ceil(log(1 + (maxBound - minBound) * pow(10, precision)) / log(2));
	}

}
