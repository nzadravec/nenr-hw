package hr.fer.zemris.fuzzy.system;

import hr.fer.zemris.fuzzy.operation.IBinaryFunction;
import hr.fer.zemris.fuzzy.operation.Operations;
import hr.fer.zemris.fuzzy.set.FuzzySets;
import hr.fer.zemris.fuzzy.set.IFuzzySet;

import java.util.ArrayList;
import java.util.List;

public class AkcelFuzzySystemMin extends FuzzySystem {

	public AkcelFuzzySystemMin(IDefuzzifier def) {
		super(def, Operations.zadehAnd(), 
				Operations.zadehAnd(), Operations.zadehOr());
	}

	@Override
	protected List<Rule> generateRules(IBinaryFunction tNorm, IBinaryFunction impl) {
		List<Rule> rules = new ArrayList<>();
		
		// L, D, LK, DK, V, S
		
//        rules.add(new Rule(
//        		new IFuzzySet[] {
//        				FuzzySets.veryClose,
//        				FuzzySets.identityDistance,
//        				FuzzySets.identityDistance,
//        				FuzzySets.identityDistance,
//        				FuzzySets.identityVelocity,
//        				FuzzySets.rightWay
//        		},
//                FuzzySets.accelerate,
//                tNorm,
//                impl
//        		)
//        );
//        
//        rules.add(new Rule(
//        		new IFuzzySet[] {
//        				FuzzySets.identityDistance,
//                        FuzzySets.veryClose,
//                        FuzzySets.identityDistance,
//                        FuzzySets.identityDistance,
//                        FuzzySets.identityVelocity,
//                        FuzzySets.rightWay
//        		},
//                FuzzySets.accelerate,
//                tNorm,
//                impl
//        		)
//        );
//        
//        rules.add(new Rule(
//        		new IFuzzySet[] {
//        				FuzzySets.identityDistance,
//                        FuzzySets.identityDistance,
//                        FuzzySets.close,
//                        FuzzySets.identityDistance,
//                        FuzzySets.identityVelocity,
//                        FuzzySets.rightWay
//        		},
//                FuzzySets.accelerate,
//                tNorm,
//                impl
//        		)
//        );
//        
//        rules.add(new Rule(
//        		new IFuzzySet[] {
//        				FuzzySets.identityDistance,
//                        FuzzySets.identityDistance,
//                        FuzzySets.close,
//                        FuzzySets.identityDistance,
//                        FuzzySets.identityVelocity,
//                        FuzzySets.rightWay
//        		},
//                FuzzySets.accelerate,
//                tNorm,
//                impl
//        		)
//        );

        rules.add(new Rule(
        		new IFuzzySet[] {
        				FuzzySets.identityDistance,
                        FuzzySets.identityDistance,
                        FuzzySets.identityDistance,
                        FuzzySets.identityDistance,
                        FuzzySets.slow,
                        FuzzySets.rightWay
        		},
                FuzzySets.accelerate,
                tNorm,
                impl
        		)
        );
        
        rules.add(new Rule(
        		new IFuzzySet[] {
        				FuzzySets.identityDistance,
                        FuzzySets.identityDistance,
                        FuzzySets.identityDistance,
                        FuzzySets.identityDistance,
                        FuzzySets.fast,
                        FuzzySets.rightWay
        		},
                FuzzySets.decelerate,
                tNorm,
                impl
        		)
        );
        
        rules.add(new Rule(
        		new IFuzzySet[] {
        				FuzzySets.identityDistance,
                        FuzzySets.identityDistance,
                        FuzzySets.identityDistance,
                        FuzzySets.identityDistance,
                        FuzzySets.identityVelocity,
                        FuzzySets.wrongWay
        		},
                FuzzySets.decelerate,
                tNorm,
                impl
        		)
        );
        
//        rules.add(new Rule(
//        		new IFuzzySet[] {
//                        FuzzySets.identityDistance,
//                        FuzzySets.identityDistance,
//                        FuzzySets.veryClose,
//                        FuzzySets.identityDistance,
//                        FuzzySets.identityVelocity,
//                        FuzzySets.rightWay
//                },
//                FuzzySets.decelerate,
//                tNorm,
//                impl
//                )
//        );
//        
//        rules.add(new Rule(
//        		new IFuzzySet[] {
//                        FuzzySets.identityDistance,
//                        FuzzySets.identityDistance,
//                        FuzzySets.identityDistance,
//                        FuzzySets.veryClose,
//                        FuzzySets.identityVelocity,
//                        FuzzySets.rightWay
//                },
//                FuzzySets.decelerate,
//                tNorm,
//                impl
//        		)
//        );

        return rules;
	}

}
