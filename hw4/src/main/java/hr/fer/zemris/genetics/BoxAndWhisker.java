package hr.fer.zemris.genetics;

import java.awt.Font;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
import org.jfree.ui.ApplicationFrame;

public class BoxAndWhisker extends ApplicationFrame {

	private static final long serialVersionUID = 1L;

	public BoxAndWhisker(String title, String xAxisLabel, String yAxisLabel, BoxAndWhiskerCategoryDataset dataset) {
		super(title);
		
		final CategoryAxis xAxis = new CategoryAxis("Type");
		//final NumberAxis xAxis = new NumberAxis(xAxisLabel);
        final NumberAxis yAxis = new NumberAxis(yAxisLabel);
        //yAxis.setAutoRangeIncludesZero(false);
		final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
		renderer.setFillBox(false);
		//renderer.setToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
		final CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
		 
		final JFreeChart chart = new JFreeChart(
				"Box-and-Whisker Demo",
	            new Font("SansSerif", Font.BOLD, 14),
	            plot,
	            true
		);
		
		final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(450, 270));
        setContentPane(chartPanel);
		
	}

}
