package hr.fer.zemris.fuzzy.system;

import hr.fer.zemris.fuzzy.domain.DomainElement;
import hr.fer.zemris.fuzzy.domain.IDomain;
import hr.fer.zemris.fuzzy.operation.IBinaryFunction;
import hr.fer.zemris.fuzzy.set.IFuzzySet;
import hr.fer.zemris.fuzzy.set.MutableFuzzySet;

public class Rule {

	private IFuzzySet[] antecedent;
	private IFuzzySet konsekvent;
	
	private IBinaryFunction tNorm;
	private IBinaryFunction impl;
	
	public Rule(IFuzzySet[] antecedent, IFuzzySet konsekvent, IBinaryFunction tNorm, IBinaryFunction impl) {
		this.antecedent = antecedent;
		this.konsekvent = konsekvent;
		this.tNorm = tNorm;
		this.impl = impl;
	}
	
	public IFuzzySet conclude(int[] inputs) {
		
		IDomain domain = konsekvent.getDomain();
		MutableFuzzySet set = new MutableFuzzySet(domain);
		
		for(DomainElement de : domain) {
			
			double min = antecedent[0].getValueAt(DomainElement.of(inputs[0]));
			
			for(int i = 1; i < antecedent.length; i++) {
				min = tNorm.valueAt(min, antecedent[i].getValueAt(DomainElement.of(inputs[i])));
			}

			set.set(de, impl.valueAt(min, konsekvent.getValueAt(de)));
			
		}
		
		return set;
	}
	
}
