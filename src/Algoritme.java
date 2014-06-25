import java.util.ArrayList;
import java.util.Collections;


public abstract class Algoritme {
	
	public Algoritme(Main main){
		setMain(main);
	}
	
	public int getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(int runningTime) {
		this.runningTime = runningTime;
	}

	private int runningTime;
	
	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	private Main main;
	
	public abstract void run(ArrayList<Cirkel> cirkels);
	
	protected ArrayList<Event> maakEvenementen(ArrayList<Cirkel> cirkels){
		ArrayList<Event> evenementen = new ArrayList<Event>(2*cirkels.size());
		final boolean links = true;
		final boolean rechts = false;
		for(Cirkel cirkel : cirkels){
			evenementen.add(new Event(cirkel, links));
			evenementen.add(new Event(cirkel, rechts));
		}
		Collections.sort(evenementen);
		return evenementen;
	}
	
	public void addSnijpunten(Cirkel cirkel, Cirkel actief){
		Punt[] snijpunten = cirkel.snijpunten(actief);
		if(snijpunten.length == 2)
			getMain().getSnijpunten().add(snijpunten[1]);
		if(snijpunten.length >= 1)
			getMain().getSnijpunten().add(snijpunten[0]);
	}
	
	public static int schattingAantalSnijpunten(long aantal, double gemiddeldeStraal){
		double totaleOppervlakte = (1+gemiddeldeStraal)*(1+gemiddeldeStraal);
		double snijdingsOppervlakteCirkel = Math.pow(2*gemiddeldeStraal, 2)*Math.PI;
		if (snijdingsOppervlakteCirkel > totaleOppervlakte){
			if(gemiddeldeStraal <= 1)
				return (int) (aantal*(aantal-1)*gemiddeldeStraal);
			else
				return (int) (aantal*(aantal-1)/gemiddeldeStraal/2);
		}
		return (int) ((snijdingsOppervlakteCirkel/totaleOppervlakte)*(aantal*(aantal-1)));
	}
	
	protected void printInfo(final int algoritme){
		System.out.println("aantal snijpunten: " + getMain().getSnijpunten().size());
		System.out.println("verwachte aantal snijpunten: " + Algoritme.schattingAantalSnijpunten(getMain().getCirkels().size(),(1/Main.deler)/2));
		System.out.println("uitvoertijd (algoritme " + algoritme + ")" + getRunningTime());
		System.out.println("count :" + Cirkel.count);

	}
}
