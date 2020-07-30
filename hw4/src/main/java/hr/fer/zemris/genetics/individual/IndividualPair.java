package hr.fer.zemris.genetics.individual;

public class IndividualPair<G> {

	public Individual<G> first;
	public Individual<G> second;
	
	public IndividualPair(Individual<G> first, Individual<G> second) {
		super();
		this.first = first;
		this.second = second;
	}
	
	public Individual<G> getFirst() {
		return first;
	}
	public Individual<G> getSecond() {
		return second;
	}
	
}
