import java.util.ArrayList;
import java.util.TreeSet;


public class Algoritme3 extends Algoritme {

	public Algoritme3(ArrayList<Cirkel> cirkels, Main main) {
		super(main);
		run(cirkels);
	}

	@Override
	public void run(ArrayList<Cirkel> cirkels) {
		long startTime = System.nanoTime();
		Cirkel.count = 0;
		ArrayList<Event> evenementen = maakEvenementen(cirkels);

		TreeSet<Cirkel> queue = new TreeSet<Cirkel>((c1, c2) -> Double.compare(c1.getMiddelpunt()
				.getY(), c2.getMiddelpunt().getY()));

		TreeSet<Cirkel> stralen = new TreeSet<Cirkel>((c1, c2) -> -Double.compare(c1.getStraal(),
				c2.getStraal()));

		double maxStraal = 0;
		for (Event evenement : evenementen) {
			if (evenement.links) {
				for (Cirkel cirkel : queue
						.subSet(new Cirkel(new Punt(evenement.x, evenement.cirkel.getTop()
								- maxStraal), 0D), new Cirkel(new Punt(evenement.x,
								evenement.cirkel.getBottom() + maxStraal), 0D))) {
					if (cirkel.getBottom() >= evenement.cirkel.getTop()
							&& cirkel.getTop() <= evenement.cirkel.getBottom())
						addSnijpunten(evenement.cirkel, cirkel);
				}

				if (evenement.cirkel.getStraal() > maxStraal)
					maxStraal = evenement.cirkel.getStraal();

				stralen.add(evenement.cirkel);
				queue.add(evenement.cirkel);
			}
			else {
				stralen.remove(evenement.cirkel);

				if (evenement.cirkel.getStraal() == maxStraal) {
					maxStraal = stralen.isEmpty() ? 0d : stralen.first().getStraal();
				}

				queue.remove(evenement.cirkel);
			}
		}
		setRunningTime((int) ((System.nanoTime() - startTime) / 1000000));

		if (Main.DEBUG) {
			printInfo(3);
		}
	}
}
