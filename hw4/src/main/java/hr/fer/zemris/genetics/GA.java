package hr.fer.zemris.genetics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.genetics.condition.StopCondition;
import hr.fer.zemris.genetics.individual.Individual;

public abstract class GA<G> {

public static final Random rand = new Random();
	
	private List<GAEvolvingListener> listeners = new ArrayList<>();
	
	public GA() {
		super();
	}

	public void addListener(GAEvolvingListener listener) {
		listeners.add(listener);
	}

	public void removeListener(GAEvolvingListener listener) {
		listeners.remove(listener);
	}

	protected void notifyListeners(int timestep, double fitness) {
		listeners.forEach(l -> l.listenForEvolution(timestep, fitness));
	}

	public static Random getRandom() {
		return rand;
	}
	
	public abstract Individual<G>[] evolve(Individual<G>[] initial, StopCondition<G> condition);
	
}
