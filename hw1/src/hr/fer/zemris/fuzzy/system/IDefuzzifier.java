package hr.fer.zemris.fuzzy.system;

import hr.fer.zemris.fuzzy.set.IFuzzySet;

public interface IDefuzzifier {

	int defuzzify(IFuzzySet set);
	
}
