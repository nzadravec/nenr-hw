package hr.fer.zemris.genetics;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;

public class LineChart extends ApplicationFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LineChart(String title, String chartTitle, String xAxisLabel, String yAxisLabel, final XYDataset dataset) {
		super(title);
		JFreeChart lineChart = ChartFactory.createXYLineChart(
		         chartTitle,
		         xAxisLabel, yAxisLabel,
		         dataset,
		         PlotOrientation.VERTICAL,
		         true,true,false
		);
		
		ChartPanel chartPanel = new ChartPanel( lineChart );
	    chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
	    setContentPane( chartPanel );
	}

}
