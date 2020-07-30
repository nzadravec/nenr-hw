package hr.fer.zemris.neuro_fuzzy;

public class ANFIS {

	private IFuzzySet[][] setsPerRule;
	private IFunction[] functions;
	private ITNorm tNorm;

	private int numOfInputs;
	private int numOfRules;

	private double[] ds;
	private double[] outputs;
	private double[] weights;

	public ANFIS(IFuzzySet[][] setsPerRule, IFunction[] functions, ITNorm tNorm) {
		this.setsPerRule = setsPerRule;
		this.functions = functions;
		this.tNorm = tNorm;
		this.numOfInputs = setsPerRule[0].length;
		this.numOfRules = setsPerRule.length;

		ds = new double[numOfInputs];
		outputs = new double[numOfRules];
		weights = new double[numOfRules];
	}

	public double predict(double ... input) {
		double weightedOutput = 0;
		double weightsSum = 0;
		
		for(int i = 0; i < numOfRules; i++) {
			for(int j = 0; j < numOfInputs; j++) {
				ds[j] = setsPerRule[i][j].valueAt(input[j]);
			}

			weights[i] = tNorm.tNorm(ds);
			weightsSum += weights[i];
			outputs[i] = functions[i].valueAt(input);
			weightedOutput += weights[i] * outputs[i];	
		}

		return weightedOutput / weightsSum;
	}

	public double[] getWeights() {
		return weights;
	}

	public void setWeights(double[] weights) {
		this.weights = weights;
	}

	public double[] getOutputs() {
		return outputs;
	}
	
}
