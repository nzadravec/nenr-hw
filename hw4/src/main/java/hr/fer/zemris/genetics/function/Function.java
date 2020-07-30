package hr.fer.zemris.genetics.function;

import hr.fer.zemris.genetics.decoder.IDecoder;

public class Function {
	
	public static <G> FitnessFunction<G> negate(FitnessFunction<G> function) {
		return new NegatedFunction<G>(function);
	}

	public static <G> FitnessFunction<G> function(int i, IDecoder<G> decoder) {
		
		if(i == 1) {
			return new ScalerFunction<G>(new Function1<G>(decoder));
		} else if(i == 3) {
			return new ScalerFunction<G>(new Function3<G>(decoder));
		} else if(i == 6) {
			return new ScalerFunction<G>(new Function6<G>(decoder));
		} else if(i == 7){
			return new ScalerFunction<G>(new Function7<G>(decoder));
		} else {
			throw new RuntimeException("error: function index");
		}
		
	}
	
	public static <G> FitnessFunction<G> function1(int i, IDecoder<G> decoder) {
		
		if(i == 1) {
			return new NegatedFunction<G>(new Function1<G>(decoder));
		} else if(i == 3) {
			return new NegatedFunction<G>(new Function3<G>(decoder));
		} else if(i == 6) {
			return new NegatedFunction<G>(new Function6<G>(decoder));
		} else if(i == 7){
			return new NegatedFunction<G>(new Function7<G>(decoder));
		} else {
			throw new RuntimeException("error: function index");
		}
		
	}
	
}
