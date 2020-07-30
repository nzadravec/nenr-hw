package hr.fer.zemris.fuzzy.system;

import hr.fer.zemris.fuzzy.domain.DomainElement;
import hr.fer.zemris.fuzzy.domain.IDomain;
import hr.fer.zemris.fuzzy.set.IFuzzySet;

public class COADefuzzifier implements IDefuzzifier {

	@Override
	public int defuzzify(IFuzzySet set) {
		IDomain domain = set.getDomain();
		
		float numerator = 0;
		float denominator = 0;
		
		for(DomainElement de : domain) {
			double value = set.getValueAt(de);
			numerator += value * de.getComponentValue(0);
			denominator += value;
		}
		
		float centerOfArea = numerator / denominator;
		
		return Math.round(centerOfArea);
	}
	
}
