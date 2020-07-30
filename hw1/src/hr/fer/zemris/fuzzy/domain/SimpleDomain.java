package hr.fer.zemris.fuzzy.domain;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleDomain extends Domain {

	private int first;
	private int last;
	
	public SimpleDomain(int first, int last) {
		
		this.first = first;
		this.last = last;
	}

	@Override
	public int getCardinality() {
		return last - first;
	}

	@Override
	public IDomain getComponent(int index) {
		if(index != 0) {
			throw new IllegalArgumentException();
		}
		
		return this;
	}

	@Override
	public int getNumberOfComponents() {
		return 1;
	}

	@Override
	public Iterator<DomainElement> iterator() {
		return new Iterator<DomainElement>() {
			private int tmp = first;

			@Override
			public boolean hasNext() {
				return tmp < last;
			}

			@Override
			public DomainElement next() {
				if(!hasNext()) {
					throw new NoSuchElementException();
				}
				
				return new DomainElement(new int[] {tmp++});
			}
		};
	}
	
	public int getFirst() {
		return first;
	}
	
	public int getLast() {
		return last;
	}
	
}
