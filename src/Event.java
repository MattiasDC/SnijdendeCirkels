
public class Event implements Comparable<Event>{
	public Event(Cirkel cirkel, boolean links) {
		this.cirkel = cirkel;
		this.links = links;
		this.x = (links) ? cirkel.getLeft() : cirkel.getRight();
	}
	
	final Cirkel cirkel;
	final boolean links;
	final double x;
	
	@Override
	public int compareTo(Event o) {
		if (this.x == o.x)
			return 0;
		return (this.x < o.x) ? -1 : 1;
	}
}
