package hr.fer.zemris.genetics.condition;

import java.util.List;

import hr.fer.zemris.genetics.individual.Individual;

public class StopConditions<G> implements StopCondition<G> {
	
	private List<StopCondition<G>> stopConditions;

	public StopConditions(List<StopCondition<G>> stopConditions) {
		super();
		this.stopConditions = stopConditions;
	}

	@Override
	public boolean isSatisfied(Individual<G>[] population) {
		for(StopCondition<G> stopCondition : stopConditions) {
			if(stopCondition.isSatisfied(population)) {
				return true;
			}
		}
		
		return false;
	}

}
