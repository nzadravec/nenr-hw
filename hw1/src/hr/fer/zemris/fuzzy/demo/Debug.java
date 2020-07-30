package hr.fer.zemris.fuzzy.demo;

import hr.fer.zemris.fuzzy.domain.DomainElement;
import hr.fer.zemris.fuzzy.domain.IDomain;
import hr.fer.zemris.fuzzy.set.IFuzzySet;

public class Debug {

	public static void print(IDomain domain, String headingText) {
		if (headingText != null) {
			System.out.println(headingText);
		}
		for (DomainElement e : domain) {
			System.out.println("Element domene: " + e);
		}
		System.out.println("Kardinalitet domene je: " + domain.getCardinality());
		System.out.println();
	}
	
	public static void print(IFuzzySet set, String headingText) {
		if (headingText != null) {
			System.err.println(headingText);
		}
		IDomain domain = set.getDomain();
		for(DomainElement e : domain) {
			if(set.getValueAt(e) != 0)
				System.err.printf("d(%d)=%f\n", e.getComponentValue(0), set.getValueAt(e));
		}
	}

}
