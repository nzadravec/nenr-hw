package hr.fer.zemris.neuro_fuzzy;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import static java.lang.Math.*;

public class SamplePreparation {
	
	private static final String fileName = "./data/samples2.txt";
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		try (Writer w = new BufferedWriter(new OutputStreamWriter(
				new BufferedOutputStream(new FileOutputStream(fileName)), StandardCharsets.UTF_8))) {
		
			for(int x = -4; x <= 4; x++) {
				for(int y = -4; y <= 4; y++) {
					w.write(x +","+y+","+f(x,y)+"\n");
				}
			}
			
		}
		
	}
	
	private static double f(double x, double y) {
		return (pow(x-1, 2) + pow(y+2, 2)-5*x*y + 3)*pow(cos(x/5),2);
	}

}
