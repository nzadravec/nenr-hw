package hr.fer.zemris.fuzzy.domain;

public interface IDomain extends Iterable<DomainElement> {

	int getCardinality();
	IDomain getComponent(int index);
	int getNumberOfComponents();
	int indexOfElement(DomainElement de);
	DomainElement elementForIndex(int index);
	
}
