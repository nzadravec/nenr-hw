import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class GlavniProzor extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static class ObradaMisa implements MouseListener, MouseMotionListener {
		
		private boolean flag = true;
		private PointPanel srednjiPanel;
		
		public ObradaMisa(PointPanel srednjiPanel) {
			this.srednjiPanel = srednjiPanel;
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			if(flag) {
				int x = e.getX();
				int y = e.getY();
				srednjiPanel.getPointList().add(new Point(x, y));
				srednjiPanel.repaint();
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			flag = false;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class PointPanel extends JPanel {
		
		private List<Point> pointList = new ArrayList<>();
		
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

		    Graphics2D g2d = (Graphics2D) g;

		    g2d.setColor(Color.red);

		    for (Point point : pointList) {

		      int x = point.x;
		      int y = point.y;

		      g2d.drawLine(x, y, x, y);
		    }
		}
		
		public List<Point> getPointList() {
			return pointList;
		}
		
		
	}
	
	private List<List<Point>> gestures = new LinkedList<>();
	private ObradaMisa obradaMisa;
	private int frames;
	private int frameIndex;
	
	private PointPanel srednjiPanel = new PointPanel();

	public GlavniProzor() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		addWindowListener((new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent event) {
	        	System.out.println("kraj");
	            //exitProcedure();
	        }
	    }));
		//setLocation(20, 50);
		setSize(500, 500);
		setTitle("Moj prvi prozor!");
		
		obradaMisa = new ObradaMisa(srednjiPanel);
		addMouseListener(obradaMisa);
		addMouseMotionListener(obradaMisa);
		
		initGUI();
		setLocationRelativeTo(null); 
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		JButton prev = new JButton("prev");
		JButton reset = new JButton("reset");
		JButton next = new JButton("next");
		JButton done = new JButton("done");
		
		
		JPanel donjiPanel = new JPanel();
		
		
		ActionListener l = e -> { 
			obradiKlik(e); 
		};
		
		prev.addActionListener(l);
		reset.addActionListener(l);
		next.addActionListener(l);
		done.addActionListener(l);

		donjiPanel.add(prev);
		donjiPanel.add(reset);
		donjiPanel.add(next);
		//donjiPanel.add(done);
		cp.add(srednjiPanel, BorderLayout.CENTER);
		cp.add(donjiPanel, BorderLayout.PAGE_END);
	}
	
	private void obradiKlik(ActionEvent e) {
		String naredba = e.getActionCommand();
		switch (naredba) {
		case "prev":
			gestures.add(frameIndex, srednjiPanel.pointList);
			
			if(frameIndex > 0) {
				frameIndex--;
				obradaMisa.flag = false;
				srednjiPanel.pointList = gestures.get(frameIndex);
			}
			break;
			
		case "reset":
			obradaMisa.flag = true;
			gestures.add(frameIndex, new ArrayList<>());
			srednjiPanel.pointList = new ArrayList<>();
			break;
			
		case "next":
			gestures.add(frameIndex, srednjiPanel.pointList);
			
			frameIndex++;
			if(gestures.size() > frameIndex) {
				System.out.println("vnsyoidv");
				srednjiPanel.pointList = gestures.get(frameIndex);
				//srednjiPanel.repaint();
			} else {
				obradaMisa.flag = true;
				srednjiPanel.pointList = new ArrayList<>();
			}
			break;
			
		case "done":
			spremiGeste();
			// kraj unosa, spremi podatke u datoteku i zaustavi program
			this.dispose();
			break;

		default:
			break;
		}
		
		this.repaint();
	}
	
	private void spremiGeste() {
		
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new GlavniProzor().setVisible(true);
		});
	}
}