package hr.fer.zemris.fuzzy.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class CompositeDomain extends Domain {
	
	private SimpleDomain[] sds;
	private int card;
	
	public CompositeDomain(SimpleDomain[] sds) {
		if(sds.length < 2) {
			throw new IllegalArgumentException();
		}
		
		this.sds = sds;
		card = 1;
		for(SimpleDomain sd : sds) {
			card *= sd.getCardinality();
		}
	}

	@Override
	public int getCardinality() {
		return card;
	}

	@Override
	public IDomain getComponent(int index) {
		if(index < 0 || index >= sds.length) {
			throw new IllegalArgumentException();
		}
		return sds[index];
	}

	@Override
	public int getNumberOfComponents() {
		return sds.length;
	}

	@Override
	public Iterator<DomainElement> iterator() {
		return new Iterator<DomainElement>() {
			
			private List<Iterator<DomainElement>> iters;
			private int[] values;
			private int counter;
			
			{
				iters = new ArrayList<>();
				for(int i = 0; i < sds.length; i++) {
					iters.add(sds[i].iterator());
				}
				
				values = new int[sds.length];
				for(int i = 0; i < sds.length; i++) {
					DomainElement de = iters.get(i).next();
					values[i] = de.getComponentValue(0);
				}
			}

			@Override
			public boolean hasNext() {
				return counter < card;
			}

			@Override
			public DomainElement next() {
				if(!hasNext()) {
					throw new NoSuchElementException();
				}
				
				DomainElement de = new DomainElement(values);
				counter++;
				
				int i = iters.size()-1;
				while(i > -1) {
					if(!iters.get(i).hasNext()) {
						iters.set(i, sds[i].iterator());
						values[i] = iters.get(i).next()
								.getComponentValue(0);
						i--;
						
					} else {
						values[i] = iters.get(i).next()
								.getComponentValue(0);
						break;
					}
				}

				return de;
			}
		};
	}

}
