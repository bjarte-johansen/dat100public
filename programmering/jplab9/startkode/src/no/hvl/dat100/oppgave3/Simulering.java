package no.hvl.dat100.oppgave3;


/*
 * generic histogram for int array, reusability of tested code
 */


public class Simulering {
	private static int NUMBER_OF_ROLLS = 100;
	
	// allow public access to type(s), we only need HistogramMaxValues
	//public static class HistogramMaxValues extends IntHistogramMaxValues{}
	//public static class Histogram extends IntHistogramMaxValues{}
	
	int[] rolls;
	int[] histogram;

	// create array with results from dice-roll
	public static int[] createRolls(int n) {
		var dice = new Terning();
		var rolls = new int[n];
		for(int i=0; i<n; i++) {
			dice.roll();
			rolls[i] = dice.value();
		}
		return rolls;
	}
	
	public static void main(String[] args) {
		// declare & init simulation
		Simulering sim = new Simulering();
		sim.rolls = createRolls(NUMBER_OF_ROLLS);		
		sim.histogram = IntHistogram.createHistogram(sim.rolls, 1, 6);
		
		/*
		 * print stats
		 * - vi bruker en global variabel USE_ASCI_GRAPHICS til å styre om vi skal vise
		 * tall eller dice, så bruker formatRollValue denne verdien til å bestemme om den
		 * skal slå opp asci-terning eller vise verdien som tall
		 */		
		
		// show stats with dices		
		Utskrift.USE_ASCI_GRAPHICS = true;
		System.out.println("TERNINGKASTSIMULATOR (3b)");
		System.out.println();		
		Utskrift.print(sim);
		
		System.out.println("-".repeat(60) + "\n");		
		
		// show stats without dices		
		Utskrift.USE_ASCI_GRAPHICS = false;
		System.out.println("TERNINGKASTSIMULATOR (3a)");
		System.out.println();		
		Utskrift.print(sim);		
	}
}