import java.util.ArrayList;


public class Algoritme1 extends Algoritme{

	public Algoritme1(ArrayList<Cirkel> cirkels, Main main) {
		super(main);
		run(cirkels);	
	}

	public void run(ArrayList<Cirkel> cirkels){
		long startTime = System.nanoTime();
		Cirkel.count = 0;
		for(int i = 0; i < cirkels.size();i++){
			for(int j = i + 1; j < cirkels.size();j++){		
				addSnijpunten(cirkels.get(i), cirkels.get(j));
			}
		}
		setRunningTime((int) ((System.nanoTime()-startTime)/1000000));

		if(Main.DEBUG){
			printInfo(1);
		}
	}
}
