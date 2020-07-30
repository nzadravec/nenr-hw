package hr.fer.zemris.fuzzy.demo;

import hr.fer.zemris.fuzzy.domain.Domain;
import hr.fer.zemris.fuzzy.domain.IDomain;

public class Domene2 {
	
	public static void main(String[] args) {
		IDomain d1 = Domain.intRange(0, 5);
		IDomain d2 = Domain.intRange(0, 5);
		IDomain d3 = Domain.intRange(0, 5);
		IDomain d4 = Domain.intRange(0, 5);
		IDomain d5 = Domain.intRange(0, 5);
		
		IDomain d = Domain.combine(d1, d2);
		d = Domain.combine(d, d3);
		d = Domain.combine(d, d4);
		d = Domain.combine(d, d5);
		Debug.print(d, "...");
		
	}

}
