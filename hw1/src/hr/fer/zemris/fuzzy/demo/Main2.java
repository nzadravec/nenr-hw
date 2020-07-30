package hr.fer.zemris.fuzzy.demo;

import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.fuzzy.operation.IBinaryFunction;
import hr.fer.zemris.fuzzy.operation.Operations;
import hr.fer.zemris.fuzzy.set.IFuzzySet;
import hr.fer.zemris.fuzzy.system.COADefuzzifier;
import hr.fer.zemris.fuzzy.system.IDefuzzifier;
import hr.fer.zemris.fuzzy.system.KormiloFuzzySystemMin;
import hr.fer.zemris.fuzzy.system.Rule;

public class Main2 {
	
	private static IBinaryFunction sNorm = Operations.zadehOr();

	public static void main(String[] args) {
		
		IDefuzzifier def = new COADefuzzifier();
		List<Rule> rules = new KormiloFuzzySystemMin(def).getRules();
		int[] inputs = new int[6];
		
		Scanner sc = new Scanner(System.in);
		System.out.println(rules.size()+" rules, from 0 to "+(rules.size()-1));
		while(true) {
			
			System.out.println("L, D, LK, DK, V, S: ");
			String line = sc.nextLine();
			if(line.equals("q")) {
				break;
			}
			
			String[] ss = line.split(" ");
			for(int i = 0; i < inputs.length; i++) {
				inputs[i] = Integer.valueOf(ss[i]);
			}
			
			
			
			IFuzzySet set = rules.get(0).conclude(inputs);
			for(int i = 1; i < rules.size(); i++) {
				Debug.print(set, "");
				IFuzzySet rSet = rules.get(i).conclude(inputs);
				set = Operations.binaryOperation(set, rSet, sNorm);
			}
			
			Debug.print(set, "");
			
			System.out.println();
			int decodedValue = def.defuzzify(set);
			System.out.println("decoded value: "+decodedValue);
			
		}
		
		sc.close();
		
	}

}
