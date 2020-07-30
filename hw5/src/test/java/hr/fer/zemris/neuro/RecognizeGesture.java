package hr.fer.zemris.neuro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.neuro.util.Util;

public class RecognizeGesture extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private static final int M = 20;
	
	class DrawingPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		public DrawingPanel() {
			setBackground(Color.lightGray);
		}

		private List<Point> gesture = new ArrayList<>();

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.blue);

			for (int i = 1; i < gesture.size(); i++) {
				Point p1 = gesture.get(i - 1);
				Point p2 = gesture.get(i);
				g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
			}
		}

	}
	
	private FFNN ffnn;
	
	private DrawingPanel middlePanel = new DrawingPanel();
	private MouseAdapter mouse;
	private boolean flag = true;
	
	public RecognizeGesture(FFNN ffnn) {
		this.ffnn = ffnn;
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		setResizable(false);
		
		mouse = new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) {
				middlePanel.gesture = new ArrayList<>();
			}

			public void mouseDragged(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					int x = e.getX();
					int y = e.getY();
					middlePanel.gesture.add(new Point(x, y));
					middlePanel.repaint();
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					if(middlePanel.gesture.size() < M) {
						middlePanel.gesture = new ArrayList<>();
						flag = true;
					} else {
						flag = false;
					}
				}
			}
		};
		
		addMouseListener(mouse);
		addMouseMotionListener(mouse);

		initGUI();
		setLocationRelativeTo(null);
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		JButton predict = new JButton("predict");
		predict.addActionListener(e -> {
			double[] input = Util.samplePreparation(middlePanel.gesture);
			double[] output = ffnn.processInput(input);
			int argmax = Util.argmax(output);
			double sum = Util.sum(output);
			if(argmax == 0) {
				System.out.println("alpha ("+output[argmax]/sum+"%)");
			} else if(argmax == 1) {
				System.out.println("beta ("+output[argmax]/sum+"%)");
			} else if(argmax == 2) {
				System.out.println("gamma ("+output[argmax]/sum+"%)");
			} else if(argmax == 3) {
				System.out.println("delta ("+output[argmax]/sum+"%)");
			} else {
				System.out.println("epsilon ("+output[argmax]/sum+"%)");
			}
		});
		
		JPanel donjiPanel = new JPanel();
		donjiPanel.setBackground(Color.darkGray);
		
		donjiPanel.add(predict);
		
		cp.add(middlePanel, BorderLayout.CENTER);
		cp.add(donjiPanel, BorderLayout.PAGE_END);
	}
	
	private static final String fileName = "./data/samples.txt";
	private static final int inputSize = 40;
	private static final int outputSize = 5;
	private static final int batchSize = 10;
	
	private static ActivationFunction sigmoid = new Sigmoid();
	
	private static final int[] neuronsPerLayer = new int[] {inputSize, inputSize, outputSize};
	private static final ActivationFunction[] activationPerLayer = 
			new ActivationFunction[] {sigmoid, sigmoid};
	
	private static final double learningRate = 0.1;
	private static final double epsilon = 1e-4;
	private static final int maxIter = 1000000;
	
	public static void main(String[] args) {
		
		FFNN ffnn = new FFNN(neuronsPerLayer, activationPerLayer);
		List<List<Sample>> batchSamples = createBatches(loadSamplesFromFile(fileName));
		BackPropLearning backProp = new BackPropLearning(learningRate, epsilon, maxIter);
		backProp.learn(ffnn, batchSamples);
		
		SwingUtilities.invokeLater(() -> {
			new RecognizeGesture(ffnn).setVisible(true);
		});
	}
	
	private static List<List<Sample>> createBatches(List<Sample> samples) {
		
		List<List<Sample>> batchSamples = new ArrayList<>();
		
		int index = 0;
		while(index < samples.size()) {
			
			List<Sample> l = new ArrayList<>();
			for(int i = 0; i < batchSize && index < samples.size(); i++) {
				l.add(samples.get(index));
				index++;
			}
			
			batchSamples.add(l);
			
		}
		
		return batchSamples;
	}
	
	private static List<Sample> loadSamplesFromFile(String fileName) {
		List<Sample> samples = new ArrayList<>();
		
		List<String> lines = new ArrayList<>();
		try (Scanner sc = new Scanner(new File(fileName))) {

			while (sc.hasNext()) {
				String s = sc.nextLine();
				lines.add(s);
			}

		} catch (FileNotFoundException exc) {
		}
		
		double[] t1 = new double[] {1,0,0,0,0};
		double[] t2 = new double[] {0,1,0,0,0};
		double[] t3 = new double[] {0,0,1,0,0};
		double[] t4 = new double[] {0,0,0,1,0};
		double[] t5 = new double[] {0,0,0,0,1};
		double[] target = t1;
		
		for(int i = 0; i < lines.size(); i++) {
			String[] ss = lines.get(i).split(" ");
			
			double[] input = new double[inputSize];
			for(int j = 0; j < inputSize; j++) {
				input[j] = Double.parseDouble(ss[j].trim());
			}
			
//			double[] target = new double[outputSize];
//			for(int j = 0; j < outputSize; j++) {
//				target[j] = Double.parseDouble(ss[inputSize+j].trim());
//			}
			
			if(i == 20) {
				target = t2;
			} else if(i == 40) {
				target = t3;
			} else if(i == 60) {
				target = t4;
			} else if(i == 80) {
				target = t5;
			}
			
			samples.add(new Sample(input, target));
		}
		
		return samples;
	}

}
