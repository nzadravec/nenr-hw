package hr.fer.zemris.fuzzy.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import hr.fer.zemris.fuzzy.system.AkcelFuzzySystemMin;
import hr.fer.zemris.fuzzy.system.COADefuzzifier;
import hr.fer.zemris.fuzzy.system.IDefuzzifier;
import hr.fer.zemris.fuzzy.system.IFuzzySystem;
import hr.fer.zemris.fuzzy.system.KormiloFuzzySystemMin;

public class Main4 {

	public static void main(String[] args) throws IOException  {
		
		int[] inputs = new int[6];
		
		IDefuzzifier def = new COADefuzzifier();
		
		IFuzzySystem fsAkcel = new AkcelFuzzySystemMin(def);
		IFuzzySystem fsKormilo = new KormiloFuzzySystemMin(def);
		
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
			
			System.err.println("labels: L, D, LK, DK, V, S");
			while(true) {
			
				String line = reader.readLine();
				
				if(line.equals("KRAJ")) {
					break;
				}
				
				String[] ss = line.split(" ");
				for(int i = 0; i < inputs.length; i++) {
					inputs[i] = Integer.valueOf(ss[i]);
				}
				
				int A = fsAkcel.infer(inputs);
				
				System.err.println();
				int K = fsKormilo.infer(inputs);
				
				System.err.println("input: "+line);
				
				line = A + " " + K;
				
				System.err.println("output: "+line);
				System.out.println(line);
				
				writer.write(A + " " + K + "\n");
				writer.flush();
				
			}
		}
		
	}
	
}
