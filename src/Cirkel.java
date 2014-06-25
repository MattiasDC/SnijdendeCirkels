
public class Cirkel implements Comparable<Cirkel>{

	public Cirkel(Punt middelpunt, Double straal){
		this.middelpunt = middelpunt;
		this.straal = straal;
	}

	public Punt getMiddelpunt() {
		return middelpunt;
	}

	private final Punt middelpunt;

	public double getStraal() {
		return straal;
	}

	private final double straal;

	public Double getLeft(){
		return getMiddelpunt().getX()-getStraal();
	}

	public Double getRight(){
		return getMiddelpunt().getX()+getStraal();
	}
	
	public Double getBottom(){
		return getMiddelpunt().getY()+getStraal();
	}
	
	public Double getTop(){
		return getMiddelpunt().getY()-getStraal();
	}

	@Override
	public String toString() {
		return "m(" + getMiddelpunt().getX() + ", " + getMiddelpunt().getY() + "), r: " + getStraal();
	}

	@Override
	public int compareTo(Cirkel o) {
		if (this.getLeft() == o.getLeft())
			return 0;
		return (this.getLeft() < o.getLeft()) ? -1 : 1;
	}

	private boolean snijdt(Cirkel other){
		count++;
		if(this.equals(other)) return false;

		double afstandMiddelpunten = Punt.afstandTussen(this.getMiddelpunt(), other.getMiddelpunt());

		return (Math.abs(this.straal-other.straal) <= afstandMiddelpunten && afstandMiddelpunten <= Math.abs(this.straal+other.straal));
	}

	public static int count = 0;
	
	public Punt[] snijpunten(Cirkel other){
		if(!snijdt(other)) return new Punt[0];

		double d    = Punt.afstandTussen(this.getMiddelpunt(), other.getMiddelpunt());
		double d1   = (this.getStraal()*this.getStraal() - other.getStraal()*other.getStraal() + d*d) / (2*d);
		double h    = Math.sqrt(this.getStraal()*this.getStraal() - d1*d1);
		double hulpX   = this.getMiddelpunt().getX() + (d1 * (other.getMiddelpunt().getX() - this.getMiddelpunt().getX())) / d;
		double hulpY   = this.getMiddelpunt().getY() + (d1 * (other.getMiddelpunt().getY() - this.getMiddelpunt().getY())) / d;
		double x_1 = hulpX + (h * (other.getMiddelpunt().getY() - this.getMiddelpunt().getY())) / d;
		double y_1 = hulpY - (h * (other.getMiddelpunt().getX() - this.getMiddelpunt().getX())) / d;
		if(Punt.afstandTussen(getMiddelpunt(), other.getMiddelpunt()) != this.getStraal()+other.getStraal())
		{
			double x_2 = hulpX - (h * (other.getMiddelpunt().getY() - this.getMiddelpunt().getY())) / d;
			double y_2 = hulpY + (h * (other.getMiddelpunt().getX() - this.getMiddelpunt().getX())) / d;
			return new Punt[]{new Punt(x_1,y_1), new Punt(x_2,y_2)};
		}
		return new Punt[]{new Punt(x_1,y_1)};
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((middelpunt == null) ? 0 : middelpunt.hashCode());
		long temp;
		temp = Double.doubleToLongBits(straal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cirkel other = (Cirkel) obj;
		if (middelpunt == null) {
			if (other.middelpunt != null)
				return false;
		} else if (!middelpunt.equals(other.middelpunt))
			return false;
		if (Double.doubleToLongBits(straal) != Double
				.doubleToLongBits(other.straal))
			return false;
		return true;
	}
}
