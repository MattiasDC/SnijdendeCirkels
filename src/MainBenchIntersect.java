import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class MainBenchIntersect {
	static double deler;
	public static void main(String[] args) {

		Main main = new Main();
		XYSeriesCollection[] dataset;

		/*
		Scanner sc = new Scanner(System.in);
		int aantal = sc.nextInt();
		int sprong = sc.nextInt();
		*/
		int aantal = 10000;
		int sprong = aantal/100;
		deler = 10d;
		Main.deler = deler;
		while(deler <= 100){
			dataset = run(main,aantal,sprong);


			JFreeChart timeChart = ChartFactory.createScatterPlot("Snijpunten bij een straal van max " + 1d/deler, "Aantal Cirkels", "Aantal snijpunten", dataset[0], PlotOrientation.VERTICAL, true, false, false);
			File file;

			try {
				file = new File("new_intersectBenchmark"+ 1d/deler + ".png");
				ChartUtilities.saveChartAsPNG(file,timeChart,500,500);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "There was an error creating the scatterplot!");
			}
			
			if(deler == 10)
				deler=100;
			else if(deler == 100)
				break;
			Main.deler = deler;
		}
	}

	public static XYSeriesCollection[] run(Main main,int aantal, int sprong){
		XYDataItem item;
		XYSeries serie1intersect = new XYSeries("Werkelijk aantal snijpunten"),
				serie2intersect = new XYSeries("Schatting");

		int aantaltests = 4;
		
		for(int i = 0; i <= aantal; i+=sprong){
			System.out.println("aantal cirkels: " + i);

			int snijpunten = 0;
			
			for(int j = 1; j <=aantaltests;j++){
				main.vervangCirkels(Main.generateCircles(i, deler));
				main.setAlgoritmeVersie(3);
				main.runAlgoritme();
				snijpunten += main.getSnijpunten().size();
			}
			serie1intersect.add(new XYDataItem(i,snijpunten/aantaltests));
			serie2intersect.add(new XYDataItem(i,Algoritme.schattingAantalSnijpunten(i, 1d/(deler*2))));
		}

		XYSeriesCollection datasetTime = new XYSeriesCollection(), datasetCount = new XYSeriesCollection();
		datasetTime.addSeries(serie1intersect);
		datasetTime.addSeries(serie2intersect);
		XYSeriesCollection dataset[] = {datasetTime,datasetCount};

		return dataset;
	}
}
