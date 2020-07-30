package hr.fer.zemris.genetics.mutation;

import hr.fer.zemris.genetics.GA;

public class TwoMutations<G> implements IMutation<G> {

	private IMutation<G> mutation1;
	private IMutation<G> mutation2;
	private double prob;
	
	public TwoMutations(IMutation<G> mutation1, IMutation<G> mutation2, double prob) {
		this.mutation1 = mutation1;
		this.mutation2 = mutation2;
		this.prob = prob;
	}

	@Override
	public void mutate(G genotype) {
		if(GA.rand.nextDouble() < prob) {
			mutation1.mutate(genotype);
		} else {
			mutation2.mutate(genotype);
		}
	}
	
}
