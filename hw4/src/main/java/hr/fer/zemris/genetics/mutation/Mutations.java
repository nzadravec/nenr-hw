package hr.fer.zemris.genetics.mutation;

import java.util.ArrayList;

import hr.fer.zemris.genetics.GA;
import hr.fer.zemris.genetics.util.EvoUtil;

public class Mutations<G> implements IMutation<G> {
	
	private ArrayList<IMutation<G>> mutations;
	private double[] probs;
	
	public Mutations(ArrayList<IMutation<G>> mutations, double[] probs) {
		if(mutations.size() != probs.length) {
			throw new RuntimeException();
		}
		
		this.mutations = mutations;
		this.probs = probs;
	}

	@Override
	public void mutate(G genotype) {
		IMutation<G> mutation = EvoUtil.proportionalSimpleChoose(mutations, probs, GA.rand);
		mutation.mutate(genotype);
	}

}
