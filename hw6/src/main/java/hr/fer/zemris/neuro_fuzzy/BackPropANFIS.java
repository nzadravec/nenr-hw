package hr.fer.zemris.neuro_fuzzy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.neuro_fuzzy.util.ArrayUtil;
import hr.fer.zemris.neuro_fuzzy.util.RandomGen;

import static java.lang.Math.pow;

public class BackPropANFIS {
	
	private List<Listener> listeners = new ArrayList<>();
	
	private double eta;
	private double eta1;
	private double epsilon;
	private int maxIter;
	
	private int numOfInputs;
	private int numOfRules;
	
	private SigmoidFuzzySet[][] setsPerRule;
	private LinearFunction[] functions;
	private ITNorm tNorm;
	
	private ANFIS anfis;
	
	public BackPropANFIS(int numOfInputs, int numOfRules, double eta, double eta1, double epsilon, int maxIter) {
		this.numOfInputs = numOfInputs;
		this.numOfRules = numOfRules;
		this.eta = eta;
		this.eta1 = eta1;
		this.epsilon = epsilon;
		this.maxIter = maxIter;
		
		Random rand = RandomGen.getRand();
		
		setsPerRule = new SigmoidFuzzySet[numOfRules][numOfInputs];
		for(int i = 0; i < numOfRules; i++) {
			for(int j = 0; j < numOfInputs; j++) {
				double a = -1 + 2*rand.nextDouble();
				double b = -1 + 2*rand.nextDouble();
				setsPerRule[i][j] = new SigmoidFuzzySet(a, b);
			}
		}
		
		functions = new LinearFunction[numOfRules];
		for(int i = 0; i < numOfRules; i++) {
			double[] xs = new double[numOfInputs+1];
			for(int j = 0; j < numOfInputs+1; j++) {
				xs[j] = -1 + 2*rand.nextDouble();
			}
			functions[i] = new LinearFunction(xs);
		}
		
		tNorm = new AlgebraicProduct();
		
		anfis = new ANFIS(setsPerRule, functions, tNorm);
	}
	
	public void addListener(Listener listener) {
		listeners.add(listener);
	}

	public void removeListener(Listener listener) {
		listeners.remove(listener);
	}

	protected void notifyListeners(int epoch, double error) {
		listeners.forEach(l -> l.listen(epoch, error));
	}
	
	public void learn(List<List<Sample>> batchSamples) {
		
		List<Sample> samples = new ArrayList<>();
		for(List<Sample> batch : batchSamples) {
			for(Sample s : batch) {
				samples.add(s);
			}
		}
		
		double[][] xssGrad = new double[numOfRules][numOfInputs+1];
		double[][] assGrad = new double[numOfRules][numOfInputs];
		double[][] bssGrad = new double[numOfRules][numOfInputs];
		
		int iter = 0;
		while(iter < maxIter) {
			iter++;
			
			double mse = mse(anfis, samples);
			if(mse < epsilon) {
				break;
			}
			
			if(iter % 1000 == 0 || iter <= 10) {
				System.out.println("iter: "+iter+", err = "+mse);
				//notifyListeners(iter, mse);
			}
			notifyListeners(iter, mse);
		
			for(List<Sample> batch : batchSamples) {
				
				ArrayUtil.setToZero(xssGrad);
				ArrayUtil.setToZero(assGrad);
				ArrayUtil.setToZero(bssGrad);
				
				for(Sample s : batch) {
					
					double[] input = s.getInput();
					double target = s.getTarget();
					double output = anfis.predict(input);
					
					double[] weights = anfis.getWeights();
					double[] zs = anfis.getOutputs();
					double weightedOutput = ArrayUtil.dot(weights, zs);
					double wsSum = ArrayUtil.sum(weights);
					double wsSumSquared = pow(wsSum,2);
					
					for(int i = 0; i < numOfRules; i++) {
						
						// pi, qi, ri
						for(int j = 0; j < numOfInputs; j++) {
							xssGrad[i][j] -= (target-output) * (weights[i]*input[j])/wsSum;
						}
						xssGrad[i][numOfInputs] -= (target-output) * weights[i]/wsSum;
						
						for(int j = 0; j < numOfInputs; j++) {
							SigmoidFuzzySet set = setsPerRule[i][j];
							
							double a = set.getA();
							double b = set.getB();
							double x = input[j];
							double alpha = set.valueAt(x);
							
							assGrad[i][j] -= (target-output) * 
									(zs[i]*wsSum - weightedOutput)/wsSumSquared *
									weights[i] / alpha * b * alpha * (1 - alpha);
							
							bssGrad[i][j] += (target-output) * 
									(zs[i]*wsSum - weightedOutput)/wsSumSquared *
									weights[i] / alpha * (x - a) * alpha * (1 - alpha);
							
						}
						
					}
					
				}
				
				ArrayUtil.scalarMultiply(eta, xssGrad);
				ArrayUtil.scalarMultiply(eta1, assGrad);
				ArrayUtil.scalarMultiply(eta1, bssGrad);
				
				for(int i = 0; i < numOfRules; i++) {
					
					double[] xs = functions[i].getXs();
					ArrayUtil.subi(xs, xssGrad[i]);
					functions[i].setXs(xs);
					
					for(int j = 0; j < numOfInputs; j++) {
						
						SigmoidFuzzySet set = setsPerRule[i][j];
						double a = set.getA();
						a -= assGrad[i][j];
						set.setA(a);
						
						double b = set.getB();
						b -= bssGrad[i][j];
						set.setB(b);
						
					}
					
				}
				
			}
		}
		
	}
	
	private double mse(ANFIS anfis, List<Sample> samples) {
		double mse = 0;
		
		double[] input = null;
		
		for(Sample s : samples) {
			input = s.getInput();
			double target = s.getTarget();
			
			double output = anfis.predict(input);
			mse += Math.pow(target-output, 2);
			
		}
		
		return mse / (2*samples.size());
	}

	public SigmoidFuzzySet[][] getSetsPerRule() {
		return setsPerRule;
	}

	public ANFIS getAnfis() {
		return anfis;
	}

}
