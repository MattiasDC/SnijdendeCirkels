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


public class MainBench {
	static double deler = 2;
	public static void main(String[] args) {

		Main main = new Main();
		XYSeriesCollection[] dataset;

		Scanner sc = new Scanner(System.in);

		//int aantal = sc.nextInt();
		int aantal = 2000;
		int sprong = aantal/100;

		Main.deler = deler;
		dataset = run(main,aantal,sprong);


		JFreeChart timeChart = ChartFactory.createScatterPlot("Algoritme 1, 2 en 3 bij een straal van max " + 1d/deler, "Aantal Cirkels", "Tijd in ms", dataset[0], PlotOrientation.VERTICAL, true, false, false),
				countChart = ChartFactory.createScatterPlot("Algoritme 1, 2 en 3 bij een straal van max " + 1d/deler, "Aantal Cirkels", "Checks op snijdingen", dataset[1], PlotOrientation.VERTICAL, true, false, false);
		File file;

		try {
			//			file = new File("new_timeBenchmark"+ 1d/deler + ".png");
			//			ChartUtilities.saveChartAsPNG(file,timeChart,500,500);
			file = new File("new_countBenchmark"+ 1d/deler + ".png");
			ChartUtilities.saveChartAsPNG(file,countChart,500,500);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "There was an error creating the scatterplot!");
		}
	}

	public static XYSeriesCollection[] run(Main main,int aantal, int sprong){
		XYDataItem item;
		XYSeries serie1Time = new XYSeries("Algoritme 1"),
				serie2Time = new XYSeries("Algoritme 2"),
				serie3Time = new XYSeries("Algoritme 3"),
				serie1Count = new XYSeries("Algoritme 1"),
				serie2Count = new XYSeries("Algoritme 2"),
				serie3Count = new XYSeries("Algoritme 3");

		int aantaltests = 5;

		for(int i = 0; i <= aantal; i+=sprong){
			System.out.println("aantal cirkels: " + i);

			int runningTime1 = 0,runningTime2 = 0,runningTime3 = 0,
					count1 = 0, count2 = 0, count3 = 0;

			for(int j = 1; j <=aantaltests;j++){
				main.vervangCirkels(Main.generateCircles(i, deler));

				if(i < 5000){
					main.setAlgoritmeVersie(1);
					main.runAlgoritme();
					count1 += Cirkel.count;
					runningTime1+= main.getAlgoritme().getRunningTime();
				}

				main.setAlgoritmeVersie(2);
				main.runAlgoritme();
				count2 += Cirkel.count;
				runningTime2+= main.getAlgoritme().getRunningTime();

				main.setAlgoritmeVersie(3);
				main.runAlgoritme();
				count3 += Cirkel.count;
				runningTime3+= main.getAlgoritme().getRunningTime();
			}
			if(i < 5000)
				serie1Time.add(new XYDataItem(i,runningTime1/aantaltests));
			serie2Time.add(new XYDataItem(i,runningTime2/aantaltests));		
			serie3Time.add(new XYDataItem(i,runningTime3/aantaltests));
			if(i < 5000)
				serie1Count.add(new XYDataItem(i,count1/aantaltests));
			serie2Count.add(new XYDataItem(i,count2/aantaltests));		
			serie3Count.add(new XYDataItem(i,count3/aantaltests));
		}

		XYSeriesCollection datasetTime = new XYSeriesCollection(), datasetCount = new XYSeriesCollection();
		datasetTime.addSeries(serie1Time);
		datasetTime.addSeries(serie2Time);
		datasetTime.addSeries(serie3Time);
		datasetCount.addSeries(serie1Count);
		datasetCount.addSeries(serie2Count);
		datasetCount.addSeries(serie3Count);
		XYSeriesCollection dataset[] = {datasetTime,datasetCount};

		return dataset;
	}
}
