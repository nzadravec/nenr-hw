package hr.fer.zemris.fuzzy.domain;

import java.util.Arrays;

public class DomainElement {
	
	private int[] values;
	
	public DomainElement(int[] values) {
		this.values = new int[values.length];
		System.arraycopy( values, 0, this.values, 0, values.length);
	}
	
	public int getNumberOfComponents() {
		return values.length;
	}
	
	public int getComponentValue(int index) {
		if(index < 0 || index >= values.length) {
			throw new IllegalArgumentException();
		}
		
		return values[index];
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(values);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DomainElement other = (DomainElement) obj;
		if (!Arrays.equals(values, other.values))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if(values.length > 1) {
			sb.append("("+values[0]);
			for(int i = 1; i < values.length; i++) {
				sb.append(","+values[i]);
			}
			sb.append(")");
		} else {
			sb.append(values[0]);
		}
		
		return sb.toString();
	}

	public static DomainElement of(int ... values) {
		return new DomainElement(values);
	}
	
}
