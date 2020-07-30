package hr.fer.zemris.neuro.util;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.awt.Point;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.List;

public class Util {
	
	public static int M = 20;
	
	public static double[] samplePreparation(List<Point> gesture) {
		
		double meanX = gesture.stream().mapToDouble(p -> p.getX()).sum() / gesture.size();
		double meanY = gesture.stream().mapToDouble(p -> p.getY()).sum() / gesture.size();

		List<Double> points = new ArrayList<>();

		for (int i = 0; i < gesture.size(); i++) {
			points.add(new Double(gesture.get(i).getX() - meanX, gesture.get(i).getY() - meanY));
		}

		double maxX = points.stream().mapToDouble(p -> abs(p.x)).max().getAsDouble();
		double maxY = points.stream().mapToDouble(p -> abs(p.y)).max().getAsDouble();
		double max = max(maxX, maxY);

		for (int i = 0; i < points.size(); i++) {
			Double point = points.get(i);
			point.x = point.x / max;
			point.y = point.y / max;
		}

		double gestureLength = 0;
		for (int i = 1; i < points.size(); i++) {
			Double point1 = points.get(i - 1);
			Double point2 = points.get(i);
			gestureLength += sqrt(pow(point2.x - point1.x, 2) + pow(point2.y - point1.y, 2));
		}

		List<Double> points2 = new ArrayList<>();
		points2.add(points.get(0));

		double distance = 0;
		int index = 1;
		for (int k = 1; k < M; k++) {

			while (distance < k * gestureLength / (M - 1)) {
				//System.out.println(index);
				Double point1 = points.get(index - 1);
				Double point2 = points.get(index);
				distance += sqrt(pow(point2.x - point1.x, 2) + pow(point2.y - point1.y, 2));

				index++;
			}

			Double point1 = points.get(index - 2);
			Double point2 = points.get(index - 1);
			double distance2 = distance - sqrt(pow(point2.x - point1.x, 2) + pow(point2.y - point1.y, 2));

			if (index < gesture.size()
					&& abs(distance - k * gestureLength / (M - 1)) < abs(distance2 - k * gestureLength / (M - 1))) {

				points2.add(points.get(index));

			} else {
				points2.add(points.get(index - 1));
			}

		}
		
		double[] output = new double[points2.size()*2];
		for(int i = 0; i < points2.size(); i++) {
			output[i*2] = points2.get(i).getX();
			output[i*2+1] = points2.get(i).getY();
		}
		
		return output;
	}
	
	public static int argmax(double[] array) {
		int argmax = 0;
		for(int i = 1; i < array.length; i++) {
			if(array[i] > array[argmax]) {
				argmax = i;
			}
		}
		
		return argmax;
	}
	
	public static double sum(double[] array) {
		double sum = 0;
		for(int i = 0; i < array.length; i++) {
			sum += array[i];
		}
		
		return sum;
	}

}
