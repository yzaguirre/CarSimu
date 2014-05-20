package fiusac.modela1.graficas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
// 

















import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import fiusac.modela1.main.Punto;
import fiusac.modela1.main.Vehiculo;
public class PanelGrafico extends JFrame{
	private ArrayList<Vehiculo> vehiculos;
	private JScrollPane jsp;
	private JPanel jp;
	private int tlimit, xlimit;
	/**
	 * @param vehiculos
	 */
	public PanelGrafico(String title, ArrayList<Vehiculo> vehiculos, double tlimit, int xlimit) {
		super(title);
		this.vehiculos = vehiculos;
		jsp = new JScrollPane();
		jp = new JPanel();
		this.tlimit = (int) tlimit;
		this.xlimit = xlimit;
		preparar();
	}
	
	public void mostrar() {
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 1500, 500);
		jsp.setViewportView(jp);
		add(jsp, BorderLayout.CENTER);
        setVisible(true);
	}
	private void preparar(){
		int i = 1;
		for (Vehiculo v: this.vehiculos){
			ArrayList<Punto> puntosxvt = v.puntosxvt;
			ArrayList<XYDataset> series = createData(puntosxvt); // quizs deba devolver un XYDataSet
			
			XYDataset datasetVT = series.get(0);
			XYDataset datasetXT = series.get(1);
			
			String titleVT = v.marca + " _ " + v.modelo + " -- VELOCIDAD - TIEMPO";
			String titleXT = v.marca + " _ " + v.modelo + " -- DESPLAZAMIENTO - TIEMPO";
			
			JFreeChart chartVT = createXYChart(datasetVT, titleVT, "Velocidad", "Tiempo", 		(int)v.velmax + 10);
			JFreeChart chartXT = createXYChart(datasetXT, titleXT, "Desplazamiento", "Tiempo",	 xlimit + 10);
			try {
	            ChartUtilities.saveChartAsPNG(new File(titleVT + ".png"), chartVT, 500, 500);
	            ChartUtilities.saveChartAsPNG(new File(titleXT + ".png"), chartXT, 500, 500);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			// we put the chart into a panel
			ChartPanel chartPanelXT = new ChartPanel(chartXT);
			ChartPanel chartPanelVT = new ChartPanel(chartVT);
	        // default size
			chartPanelXT.setBounds(0, 270 * i, 500, 270);
			chartPanelVT.setBounds(510, 270 * i, 500, 270);
	        jp.add(chartPanelVT);
	        jp.add(chartPanelXT);
	        i++;
		}	
	}
	private ArrayList<XYDataset> createData(ArrayList<Punto> puntosxvt) {
		ArrayList<XYDataset> returnable = new ArrayList<>(2);
        XYSeriesCollection dataVT = new XYSeriesCollection();
        XYSeriesCollection dataXT = new XYSeriesCollection();
        returnable.add(dataVT);
        returnable.add(dataXT);
        XYSeries seriesVT = new XYSeries("Velocidad"); // Velocidad - Tiempo
        XYSeries seriesXT = new XYSeries("Desplazamiento"); // Distancia - Tiempo
    	for(Punto punto: puntosxvt){
    		seriesVT.add(punto.t, punto.v); // eje y eje x
    		seriesXT.add(punto.t, punto.x); // eje y eje x
    	}
    	dataVT.addSeries(seriesVT);
    	dataXT.addSeries(seriesXT);
        // return data;
        return returnable;
    }
	private JFreeChart createXYChart(XYDataset data, String titulo, String yName, String xName, int limit) {
        JFreeChart chart =
                ChartFactory.createXYLineChart(titulo, xName, yName,
                data, PlotOrientation.VERTICAL,
                true, true, false);
        XYPlot plot = chart.getXYPlot();
//    StandardXYItemRenderer renderer = (StandardXYItemRenderer) plot.getRenderer();
        Font font = new Font("Meiryo", Font.PLAIN, 12);
        Font font2 = new Font("Meiryo", Font.PLAIN, 8);
        chart.getLegend().setItemFont(font);
        chart.getTitle().setFont(font);
        XYPlot xyp = chart.getXYPlot();
        xyp.getDomainAxis().setLabelFont(font); // X
        xyp.getRangeAxis().setLabelFont(font); // Y
        xyp.getDomainAxis().setRange(0, tlimit + 10);
        xyp.getRangeAxis().setRange(0, limit);
        xyp.getDomainAxis().setTickLabelFont(font2);
        xyp.getRangeAxis().setTickLabelFont(font2);
        xyp.getDomainAxis().setVerticalTickLabels(true);
        xyp.getDomainAxis().setFixedAutoRange(100);
        xyp.getRangeAxis().setFixedAutoRange(100);

        // fill and outline
        XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) plot.getRenderer();
        r.setSeriesOutlinePaint(0, Color.RED);
        r.setSeriesOutlinePaint(1, Color.BLUE);
        r.setSeriesShapesFilled(0, false);
        r.setSeriesShapesFilled(1, false);

        return chart;
    }
}
