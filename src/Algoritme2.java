import java.util.ArrayList;
import java.util.HashSet;


public class Algoritme2 extends Algoritme{

	public Algoritme2(ArrayList<Cirkel> cirkels, Main main) {
		super(main);
		run(cirkels);

	}

	@Override
	public void run(ArrayList<Cirkel> cirkels) {
		long startTime = System.nanoTime();
		Cirkel.count = 0;
		ArrayList<Event> evenementen = maakEvenementen(cirkels);

		HashSet<Cirkel> activa = new HashSet<Cirkel>();
		for(Event evenement : evenementen){
			if(evenement.links){
				for(Cirkel actief : activa){
					addSnijpunten(evenement.cirkel,actief);
				}
				activa.add(evenement.cirkel);
			}
			else
			{
				activa.remove(evenement.cirkel);
			}
		}

		setRunningTime((int) ((System.nanoTime()-startTime)/1000000));

		if(Main.DEBUG){
			printInfo(2);
		}
	}
}
