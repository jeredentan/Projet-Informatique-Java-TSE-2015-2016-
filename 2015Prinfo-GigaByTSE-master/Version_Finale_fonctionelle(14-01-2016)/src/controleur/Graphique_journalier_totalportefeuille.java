
package controleur;

import java.awt.Color;
import java.text.SimpleDateFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;

import modele.Gestion_base_de_donnee;

public class Graphique_journalier_totalportefeuille extends ApplicationFrame {


	public static TimeSeries s1;

	private static final long serialVersionUID = 1L;

	static {
		
		ChartFactory.setChartTheme(new StandardChartTheme("",true));
	}
	public Graphique_journalier_totalportefeuille(String title) {
		super(title);
		ChartPanel chartPanel = (ChartPanel) createPanelgraph();
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);
	}

	private static JFreeChart createChart(XYDataset dataset) {
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Historique des dernieres 24 heures",  // title
				"Date",             // x-axis label
				"Valeur Portefeuille(euros)",   // y-axis label
				dataset,            // data
				true,               // create legend?
				true,               // generate tooltips?
				false               // generate URLs?
				);
		chart.setBackgroundPaint(Color.white);
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);
		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
			renderer.setDrawSeriesLineAsPath(true);
		}

		DateAxis axis = (DateAxis) plot.getDomainAxis(); 
		axis.setDateFormatOverride(new SimpleDateFormat("dd/MM hh'h'mm'm'ss's'"));
		return chart;

	}

	private static XYDataset createDataset() {

		s1 = new TimeSeries("total_portefeuille");

		Gestion_base_de_donnee.recuperertotauxdujour();

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(s1);
		return dataset;
	}


	public static ChartPanel createPanelgraph() {
		JFreeChart chart = createChart(createDataset());
		ChartPanel panel = new ChartPanel(chart);
		panel.setFillZoomRectangle(true);
		panel.setMouseWheelEnabled(true);
		return panel;
	}
}