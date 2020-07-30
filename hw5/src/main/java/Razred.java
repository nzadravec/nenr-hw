import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D.Double;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import static java.lang.Math.*;

public class Razred extends JFrame {
	
	private static final String fileName = "epsilon.txt";

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

	private DrawingPanel middlePanel = new DrawingPanel();
	private MouseAdapter mouse;
	private boolean flag = true;
	private List<List<Point>> gestures = new ArrayList<>();

	public Razred() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		addWindowListener((new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				saveGestures();
			}
		}));
		setSize(500, 500);
		setResizable(false);
		// setTitle("Moj prvi prozor!");

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
		JButton reset = new JButton("reset");
		JButton next = new JButton("next");

		JPanel donjiPanel = new JPanel();
		donjiPanel.setBackground(Color.darkGray);

		ActionListener l = e -> {
			processClick(e);
		};

		reset.addActionListener(l);
		next.addActionListener(l);

		//donjiPanel.add(reset);
		donjiPanel.add(next);

		cp.add(middlePanel, BorderLayout.CENTER);
		cp.add(donjiPanel, BorderLayout.PAGE_END);
	}

	private void saveGestures() {
		
		List<List<Double>> gestures2 = new ArrayList<>();

		for (List<Point> gesture : gestures) {

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
					System.out.println(index);
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
			
			gestures2.add(points2);
		}
		
		int[] t1 = new int[] {1,0,0,0,0};
		int[] t2 = new int[] {0,1,0,0,0};
		int[] t3 = new int[] {0,0,1,0,0};
		int[] t4 = new int[] {0,0,0,1,0};
		int[] t5 = new int[] {0,0,0,0,1};
		int[] t = t1;
		
		try (Writer w = new BufferedWriter(new OutputStreamWriter(
				new BufferedOutputStream(new FileOutputStream("./data/"+fileName)), StandardCharsets.UTF_8))) {
			
			int idx = 0;
			for(List<Double> gesture : gestures2) {
				
				idx++;
				if(idx == 10) {
					t = t2;
				} else if(idx == 20) {
					t = t3;
				} else if(idx == 30) {
					t = t4;
				} else if(idx == 40) {
					t = t5;
				}
				
				String line = "";
				for(Double point : gesture) {
					line += point.x + " " + point.y + " ";
				}
				
//				for(int i = 0; i < t.length; i++) {
//					line += t[i] + " ";
//				}
				
				w.write(line + " \n");
			}
			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void processClick(ActionEvent e) {
		String command = e.getActionCommand();
		switch (command) {
		case "reset":
			middlePanel.gesture = new ArrayList<>();
			flag = true;
			break;

		case "next":
			gestures.add(middlePanel.gesture);
			middlePanel.gesture = new ArrayList<>();
			flag = true;
			break;

		default:
			break;
		}

		this.repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Razred().setVisible(true);
		});
	}

}
