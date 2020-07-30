package hr.fer.zemris.fuzzy.operation;

import hr.fer.zemris.fuzzy.domain.DomainElement;
import hr.fer.zemris.fuzzy.domain.IDomain;
import hr.fer.zemris.fuzzy.set.IFuzzySet;
import hr.fer.zemris.fuzzy.set.MutableFuzzySet;

public class Operations {
	
	public static IFuzzySet unaryOperation(IFuzzySet set, IUnaryFunction function) {
		IDomain domain = set.getDomain();
		MutableFuzzySet newSet = new MutableFuzzySet(domain);
		for(DomainElement de : domain) {
			newSet.set(de, function.valueAt(set.getValueAt(de)));
		}
		
		return newSet;
	}
	
	public static IFuzzySet binaryOperation(IFuzzySet set1, IFuzzySet set2, IBinaryFunction function) {
		IDomain domain = set1.getDomain();
		MutableFuzzySet newSet = new MutableFuzzySet(domain);
		for(DomainElement de : domain) {
			newSet.set(de, function.valueAt(set1.getValueAt(de), set2.getValueAt(de)));
		}
		
		return newSet;
	}
	
	public static IUnaryFunction zadehNot() {
		return new IUnaryFunction() {

			@Override
			public double valueAt(double x) {
				return 1-x;
			}
			
		};
	}
	
	public static IBinaryFunction zadehAnd() {
		return new IBinaryFunction() {

			@Override
			public double valueAt(double x, double y) {
				return Math.min(x, y);
			}
			
		};
	}
	
	public static IBinaryFunction product() {
		return new IBinaryFunction() {

			@Override
			public double valueAt(double x, double y) {
				return x*y;
			}
			
		};
	}
	
	public static IBinaryFunction zadehOr() {
		return new IBinaryFunction() {

			@Override
			public double valueAt(double x, double y) {
				return Math.max(x, y);
			}
			
		};
	}
	
	public static IBinaryFunction hamacherTNorm(double p) {
		return new IBinaryFunction() {

			@Override
			public double valueAt(double x, double y) {
				return x*y / (p+(1-p)*(x+y-x*y));
			}
			
		};
	}
	
	public static IBinaryFunction hamacherSNorm(double p) {
		return new IBinaryFunction() {

			@Override
			public double valueAt(double x, double y) {
				return (x+y-(2-p)*x*y)/(1-(1-p)*x*y);
			}
			
		};
	}
	
	public static IBinaryFunction zadehImpl() {
		return new IBinaryFunction() {

			@Override
			public double valueAt(double x, double y) {
				return Math.max(Math.min(x, y), 1-x);
			}
			
		};
	}
	
	public static IBinaryFunction mandaniImpl() {
		return zadehAnd();
	}
	
}
