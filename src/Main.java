import java.io.File;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class Main {

	public static final boolean DEBUG = true;

	public static void main(String[] args) {
		new Main();
	}

	public Main(){
		view = new View(this);
	}

	public View getView() {
		return view;
	}

	private final View view;

	public int getAlgoritmeVersie() {
		return algoritmeVersie;
	}

	public void setAlgoritmeVersie(int algoritmeVersie) {
		this.algoritmeVersie = algoritmeVersie;
	}

	private int algoritmeVersie = 0;

	public void addCirkel(Cirkel cirkel){
		getCirkels().add(cirkel);
	}

	public void vervangCirkels(ArrayList<Cirkel> cirkels){
		if(getSnijpunten() != null)
			getSnijpunten().clear();
		setCirkels(cirkels);
	}

	private void setCirkels(ArrayList<Cirkel> cirkels) {
		this.cirkels = cirkels;
	}

	public ArrayList<Cirkel> getCirkels() {
		return cirkels;
	}

	private ArrayList<Cirkel> cirkels = new ArrayList<Cirkel>();

	public void runAlgoritme(){
		snijpunten = new ArrayList<Punt>(Algoritme.schattingAantalSnijpunten(cirkels.size(), 1/(deler*2)));

		switch(getAlgoritmeVersie()){
		case 1:	setAlgoritme(new Algoritme1(getCirkels(),this));
		break;
		case 2:	setAlgoritme(new Algoritme2(getCirkels(),this));
		break;
		case 3:	setAlgoritme(new Algoritme3(getCirkels(),this));
		break;
		}
	}

	public Algoritme getAlgoritme() {
		return algoritme;
	}

	public void setAlgoritme(Algoritme algoritme) {
		this.algoritme = algoritme;
	}

	private Algoritme algoritme;

	public ArrayList<Punt> getSnijpunten() {
		return snijpunten;
	}

	public void setSnijpunten(ArrayList<Punt> snijpunten) {
		this.snijpunten = snijpunten;
	}

	private ArrayList<Punt> snijpunten;

	public ArrayList<Cirkel> readCircles(File file){
		try{
			ArrayList<Cirkel> cirkels = new ArrayList<Cirkel>();
			Scanner reader = new Scanner(file);

			setAlgoritmeVersie(Integer.valueOf(reader.nextLine()));
			int aantalCirkels = Integer.valueOf(reader.nextLine());
			double somStralen = 0;
			for(int i = 0; i < aantalCirkels; i++){
				String cirkel = reader.nextLine();
				String[] cirkelSplit = cirkel.split(" ");
				cirkels.add(new Cirkel(new Punt(Double.valueOf(cirkelSplit[0]), Double.valueOf(cirkelSplit[1])), Double.valueOf(cirkelSplit[2])));
				somStralen += Double.valueOf(cirkelSplit[2]);
			}
			double gemiddeldeStraal = somStralen / aantalCirkels;
			deler = 1/(2*gemiddeldeStraal);
			return cirkels;
		}
		catch (Exception e) {
			System.err.println("File not found!" + e.toString());
			return new ArrayList<Cirkel>();
		}
	}

	public void writeCircles(File file){
		try {
			PrintWriter writer = new PrintWriter(file);
			writer.write("1\n");
			writer.write(getCirkels().size()+"\n");
			for (Cirkel cirkel : getCirkels()){
				writer.write(cirkel.getMiddelpunt().getX() + " " + cirkel.getMiddelpunt().getY() + " " + cirkel.getStraal() + "\n");
			}
			writer.close();
		} catch (Exception e) {
			System.err.println("There was an error opening the file");
		}
	}

	public void writeSnijpunten(File file){
		if(getSnijpunten() == null || getSnijpunten().isEmpty()){
			if(getAlgoritmeVersie() == 0)
				view.getBtnRunAlgoritme().doClick();
			runAlgoritme();
		}

		try {
			PrintWriter writer = new PrintWriter(file);
			for (Punt snijpunt : getSnijpunten()){
				writer.write(snijpunt.getX() + " " + snijpunt.getY() + "\n");
			}
			writer.write("\n");
			writer.write(getAlgoritme().getRunningTime() +"");
			writer.close();
		} catch (Exception e) {
			System.err.println("There was an error opening the file");
		}
	}

	public static ArrayList<Cirkel> generateCircles(int n, double deler){
		ArrayList<Cirkel> cirkels = new ArrayList<Cirkel>();
		Random r = new Random();
		for(int i = 0; i < n; i++)
			cirkels.add(new Cirkel(new Punt(r.nextDouble(), r.nextDouble()),r.nextDouble()/deler));
		return cirkels;
	}

	public static ArrayList<Cirkel> generateCircles(int n){
		return generateCircles(n, deler);
	}

	public static double deler = 1d;
}
