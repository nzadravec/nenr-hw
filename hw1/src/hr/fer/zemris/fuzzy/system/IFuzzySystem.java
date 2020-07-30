package hr.fer.zemris.fuzzy.system;

import java.util.List;

public interface IFuzzySystem {
	
	List<Rule> getRules();
	
	int infer(int ... inputs);
	
}
