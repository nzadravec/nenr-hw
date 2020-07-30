package hr.fer.zemris.genetics.individual;

public class Individual<G> implements Comparable<Individual<G>> {

	public G genotype;
	public double fitness;
	
	public Individual(G genotype) {
		this.genotype = genotype;
	}
	
	@Override
	public int compareTo(Individual<G> o) {
		return Double.compare(this.fitness, o.fitness);
	}

}
