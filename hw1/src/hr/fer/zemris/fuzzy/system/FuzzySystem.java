package hr.fer.zemris.fuzzy.system;

import java.util.List;

import hr.fer.zemris.fuzzy.operation.IBinaryFunction;
import hr.fer.zemris.fuzzy.operation.Operations;
import hr.fer.zemris.fuzzy.set.IFuzzySet;

public abstract class FuzzySystem implements IFuzzySystem {
	
	private IDefuzzifier def;
	private IBinaryFunction sNorm;
	private List<Rule> rules;
	
	public FuzzySystem(IDefuzzifier def, IBinaryFunction tNorm, 
			IBinaryFunction impl, IBinaryFunction sNorm) {
        this.def = def;
        this.sNorm = sNorm;
        this.rules = generateRules(tNorm, impl);
    }
	
	protected abstract List<Rule> generateRules(IBinaryFunction tNorm, IBinaryFunction impl);
	
	@Override
	public List<Rule> getRules() {
		return rules;
	}

	@Override
	public int infer(int ... inputs) {
		
		IFuzzySet outputSet = rules.get(0).conclude(inputs);
		
		for(int i = 1; i < rules.size(); i++) {
 			Rule rule = rules.get(i);
 			
 			IFuzzySet rSet = rule.conclude(inputs);
 			outputSet = Operations.binaryOperation(outputSet, rSet, sNorm);
	 	}
		
		return def.defuzzify(outputSet);
	}

}
