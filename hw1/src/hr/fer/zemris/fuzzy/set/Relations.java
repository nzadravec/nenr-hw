package hr.fer.zemris.fuzzy.set;

import java.util.Iterator;

import hr.fer.zemris.fuzzy.domain.Domain;
import hr.fer.zemris.fuzzy.domain.DomainElement;
import hr.fer.zemris.fuzzy.domain.IDomain;

public class Relations {

	public static boolean isSymmetric(IFuzzySet set) {
		if(!isUtimesURelation(set)) {
			return false;
		}
		
		IDomain domain = set.getDomain();
		for(DomainElement de : domain) {
			int x = de.getComponentValue(0);
			int y = de.getComponentValue(1);
			if(set.getValueAt(de) != set.getValueAt(DomainElement.of(y, x))) {
				return false;
			}
		}
			
		return true;
	}
	
	public static boolean isReflexive(IFuzzySet set) {
		if(!isUtimesURelation(set)) {
			return false;
		}
		
		IDomain domain = set.getDomain().getComponent(0);
		for(DomainElement de : domain) {
			int x = de.getComponentValue(0);
			if(set.getValueAt(DomainElement.of(x, x)) != 1) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean isMaxMinTransitive(IFuzzySet set) {
		if(!isUtimesURelation(set)) {
			return false;
		}
		
		IDomain domain1 = set.getDomain();
		IDomain domain2 = set.getDomain().getComponent(0);
		for(DomainElement de1 : domain1) {
			int x = de1.getComponentValue(0);
			int z = de1.getComponentValue(1);
			
			double max = 0;
			for(DomainElement de2 : domain2) {
				int y = de2.getComponentValue(0);
				double value = Math.min(set.getValueAt(DomainElement.of(x, y)), 
						set.getValueAt(DomainElement.of(y, z)));
				
				if(value > max) {
					max = value;
				}
			}
			
			if(set.getValueAt(de1) < max) {
				return false;
			}
		}
		
		return true;
	}
	
	public static IFuzzySet compositionOfBinaryRelations(IFuzzySet set1, IFuzzySet set2) {
		IDomain domain1 = set1.getDomain().getComponent(0);
		IDomain domain2 = set2.getDomain().getComponent(1);
		IDomain domain3 = set2.getDomain().getComponent(0);
		
		IDomain newDomain = Domain.combine(domain1, domain2);
		MutableFuzzySet newSet = new MutableFuzzySet(newDomain);
		
		for(DomainElement de1 : newDomain) {
			int x = de1.getComponentValue(0);
			int z = de1.getComponentValue(1);
			
			double max = 0;
			for(DomainElement de2 : domain3) {
				int y = de2.getComponentValue(0);
				double value = Math.min(set1.getValueAt(DomainElement.of(x, y)), 
						set2.getValueAt(DomainElement.of(y, z)));
				
				if(value > max) {
					max = value;
				}
			}
			
			newSet.set(de1, max);
		}
		
		return newSet;
	}
	
	public static IFuzzySet cartesianProduct(IFuzzySet ... sets) {
		
		IDomain domain = sets[0].getDomain();
		for(int i = 1; i < sets.length; i++) {
			System.out.println(domain.getCardinality());
			domain = Domain.combine(domain, sets[i].getDomain());
		}
		
		MutableFuzzySet set = new MutableFuzzySet(domain);
		for(DomainElement de : domain) {
					
			int value = de.getComponentValue(0);
			double min = sets[0].getValueAt(DomainElement.of(value));
			for(int i = 1; i < sets.length; i++) {
				value = de.getComponentValue(i);
				min = Math.min(min, sets[i].getValueAt(DomainElement.of(value)));
			}
			
			set.set(de, min);
		}
		
		return set;
	}
	
	public static boolean isFuzzyEquivalence(IFuzzySet set) {
		return isReflexive(set) && isSymmetric(set) && isMaxMinTransitive(set);
	}
	
	public static boolean isUtimesURelation(IFuzzySet set) {
		IDomain domain = set.getDomain();
		if(domain.getNumberOfComponents() != 2) {
			return false;
		}
		
		IDomain d1 = domain.getComponent(0);
		IDomain d2 = domain.getComponent(0);
		if(d1.getCardinality() != d2.getCardinality()) {
			return false;
		}
		
		Iterator<DomainElement> iter1 = d1.iterator();
		Iterator<DomainElement> iter2 = d1.iterator();
		while(iter1.hasNext()) {
			if(!iter1.next().equals(iter2.next())) {
				return false;
			}
		}
		
		return true;
	}
	
}
