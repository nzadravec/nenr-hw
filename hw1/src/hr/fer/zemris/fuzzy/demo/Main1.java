package hr.fer.zemris.fuzzy.demo;

import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.fuzzy.set.IFuzzySet;
import hr.fer.zemris.fuzzy.system.COADefuzzifier;
import hr.fer.zemris.fuzzy.system.IDefuzzifier;
import hr.fer.zemris.fuzzy.system.KormiloFuzzySystemMin;
import hr.fer.zemris.fuzzy.system.Rule;

public class Main1 {

	public static void main(String[] args) {
		
		IDefuzzifier def = new COADefuzzifier();
		List<Rule> rules = new KormiloFuzzySystemMin(def).getRules();
		int[] inputs = new int[6];
		int index = -1;
		
		Scanner sc = new Scanner(System.in);
		System.out.println(rules.size()+" rules, from 0 to "+(rules.size()-1));
		while(true) {
			
			System.out.println();
			System.out.println("rule index: ");
			String line = sc.nextLine();
			if(line.equals("q")) {
				break;
			}
			
			index = Integer.valueOf(line);
			
			System.out.println("L, D, LK, DK, V, S: ");
			line = sc.nextLine();
			if(line.equals("q")) {
				break;
			}
			
			String[] ss = line.split(" ");
			for(int i = 0; i < inputs.length; i++) {
				inputs[i] = Integer.valueOf(ss[i]);
			}
			
			IFuzzySet set = rules.get(index).conclude(inputs);
			Debug.print(set, "");
			
			System.out.println();
			int decodedValue = def.defuzzify(set);
			System.out.println("decoded value: "+decodedValue);
			
		}
		
		sc.close();
		
	}

}
