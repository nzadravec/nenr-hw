package hr.fer.zemris.fuzzy.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import hr.fer.zemris.fuzzy.system.AkcelFuzzySystemMin;
import hr.fer.zemris.fuzzy.system.COADefuzzifier;
import hr.fer.zemris.fuzzy.system.IDefuzzifier;
import hr.fer.zemris.fuzzy.system.IFuzzySystem;
import hr.fer.zemris.fuzzy.system.KormiloFuzzySystemMin;

public class myFuzzyControlSys {

	public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        
        IDefuzzifier def = new COADefuzzifier();
		
		IFuzzySystem fsAkcel = new AkcelFuzzySystemMin(def);
		IFuzzySystem fsKormilo = new KormiloFuzzySystemMin(def);

	    int L=0,D=0,LK=0,DK=0,V=0,S=0,akcel,kormilo;
	    String line = null;
		while(true){
			if((line = input.readLine())!=null){
				if(line.charAt(0)=='K') break;
				Scanner s = new Scanner(line);
				L = s.nextInt();
				D = s.nextInt();
				LK = s.nextInt();
				DK = s.nextInt();
				V = s.nextInt();
				S = s.nextInt();
	        }
			
			System.err.println();
			System.err.println(L+" "+D+" "+LK+" "+DK+" "+V+" "+S);

			akcel = fsAkcel.infer(L, D, LK, DK, V, S);
			kormilo = fsKormilo.infer(L, D, LK, DK, V, S);
			
			System.err.println(akcel + " " + kormilo);

	        System.out.println(akcel + " " + kormilo);
	        System.out.flush();
	   }
	}
	
}
