package hr.fer.zemris.fuzzy.system;

import hr.fer.zemris.fuzzy.domain.DomainElement;
import hr.fer.zemris.fuzzy.domain.IDomain;
import hr.fer.zemris.fuzzy.operation.IBinaryFunction;
import hr.fer.zemris.fuzzy.set.IFuzzySet;
import hr.fer.zemris.fuzzy.set.MutableFuzzySet;

public class Rule2 {

	private IFuzzySet[] sets;
	private IBinaryFunction tNorm;
	
	public Rule2(IFuzzySet[] sets, IBinaryFunction tNorm) {
		this.sets = sets;
		this.tNorm = tNorm;
	}
	
	public IFuzzySet conclude(int[] inputs) {
		
		IDomain domain = sets[sets.length-1].getDomain();
		MutableFuzzySet set = new MutableFuzzySet(domain);
		
		for(DomainElement de : domain) {
			
			double min = sets[0].getValueAt(DomainElement.of(inputs[0]));
			
			for(int i = 1; i < sets.length; i++) {
				min = tNorm.valueAt(min, sets[i].getValueAt(DomainElement.of(inputs[i])));
			}

			set.set(de, min);
			
		}
		
		return set;
	}
	
}
