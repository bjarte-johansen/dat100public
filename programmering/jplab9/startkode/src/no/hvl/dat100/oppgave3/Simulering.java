package no.hvl.dat100.oppgave3;

import no.hvl.dat100.oppgave3.*;

public class Simulering {
	private static int NUMBER_OF_ROLLS = 100;	
	
	int[] rolls;
	int[] histogram;
	
	public static int[] createHistogram(int[] rolls) {
		int[] histogram = new int[6];
		for(int roll : rolls) {
			histogram[roll - 1]++;
		}
		return histogram;
	}
	
	public static int[] createRolls(int n) {
		var dice = new Terning();
		var rolls = new int[n];
		for(int i=0; i<n; i++) {
			dice.trill();
			rolls[i] = dice.getOyne();
		}
		return rolls;
	}
	
	public static void main(String[] args) {
		Simulering sim = new Simulering();		
		
		sim.rolls = createRolls(NUMBER_OF_ROLLS);		
		sim.histogram = createHistogram(sim.rolls);
		
		/*
		 * print stats
		 * - vi bruker en global variabel USE_DICE_GRAPHICS til å styre om vi skal vise
		 * tall eller dice, så bruker formatRollValue denne verdien til å bestemme om den
		 * skal slå opp asci-terning eller vise verdien som tall
		 */		
		
		Utskrift.USE_DICE_GRAPHICS = true;
		Utskrift.printStatistics(sim);
		System.out.println("--------------------");
		System.out.println();
		
		Utskrift.USE_DICE_GRAPHICS = false;
		Utskrift.printStatistics(sim);		
	}
}