package hr.fer.zemris.fuzzy.set;

import hr.fer.zemris.fuzzy.domain.DomainElement;
import hr.fer.zemris.fuzzy.domain.IDomain;

public class MutableFuzzySet implements IFuzzySet {

	private String name;
	private double[] memberships;
	private IDomain domain;
	
	public MutableFuzzySet(IDomain domain) {
		this.domain = domain;
		memberships = new double[domain.getCardinality()];
	}
	
	public MutableFuzzySet(String name, IDomain domain) {
		this(domain);
		this.name = name;
	}
	
	public IDomain getDomain() {
		return domain;
	}
	
	public double getValueAt(DomainElement de) {
		int index = domain.indexOfElement(de);
		return memberships[index];
	}
	
	public MutableFuzzySet set(DomainElement de, double value) {
		int index = domain.indexOfElement(de);
		memberships[index] = value;
		return this;
	}
	
}
