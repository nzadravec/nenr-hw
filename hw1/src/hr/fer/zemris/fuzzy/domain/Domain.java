package hr.fer.zemris.fuzzy.domain;

public abstract class Domain implements IDomain {
	
	public static IDomain intRange(int first, int last) {
		return new SimpleDomain(first, last);
	}
	
	public static <T> Domain combine(IDomain domain1, IDomain domain2) {
		SimpleDomain[] domains = new SimpleDomain[domain1.getNumberOfComponents()
		                                          + domain2.getNumberOfComponents()];
        int index = 0;
        for (int i = 0; i < domain1.getNumberOfComponents(); i++) {
            domains[index++] = (SimpleDomain) domain1.getComponent(i);
        }
        for (int i = 0; i < domain2.getNumberOfComponents(); i++) {
            domains[index++] = (SimpleDomain) domain2.getComponent(i);
        }
        
        return new CompositeDomain(domains);
	}

	@Override
	public int indexOfElement(DomainElement de) {
		int index = 0;
		for (DomainElement de2 : this) {
            if (de2.equals(de)) {
                return index;
            }
            index++;
        }
		
		return -1;
	}

	@Override
	public DomainElement elementForIndex(int index) {
		if(index >= getCardinality()) {
			throw new IndexOutOfBoundsException();
		}
		
		int current = 0;
		for (DomainElement de : this) {
            if (current == index) {
                return de;
            }
            current++;
        }
		
		return null;
	}
	
}
