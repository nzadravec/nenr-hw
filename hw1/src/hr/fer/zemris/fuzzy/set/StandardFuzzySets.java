package hr.fer.zemris.fuzzy.set;

import hr.fer.zemris.fuzzy.operation.IIntUnaryFunction;

public class StandardFuzzySets {

	public static IIntUnaryFunction lFunction(int alpha, int beta) {
		return new IIntUnaryFunction() {

			@Override
			public double valueAt(int x) {
				if (x < alpha) {
					return 1;
				} else if (x < beta) {
					return (beta - x) / (double)(beta - alpha);
				} else {
					return 0;
				}
			}

		};
	}

	public static IIntUnaryFunction gammaFunction(int alpha, int beta) {
		return new IIntUnaryFunction() {

			@Override
			public double valueAt(int x) {
				if (x < alpha) {
					return 0;
				} else if (x < beta) {
					return (x - alpha) / (double)(beta - alpha);
				} else {
					return 1;
				}
			}

		};
	}

	public static IIntUnaryFunction lambdaFunction(int alpha, int beta, int gamma) {
		return new IIntUnaryFunction() {

			@Override
			public double valueAt(int x) {
				if (x < alpha) {
					return 0;
				} else if(x < beta) {
					return (x - alpha) / (double)(beta - alpha);
				} else if(x < gamma) {
					return (gamma - x) / (double)(gamma - beta);
				} else {
					return 0;
				}
			}

		};
	}

}
