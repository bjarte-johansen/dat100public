package no.hvl.dat100.oppgave3;

import java.util.Arrays;


/*
 * Utskrift - klassen er besvarelsen
 * Note: jeg valgte å bruke samme klassen uavhengig av om terninger eller tall skulle brukes
 * og bare bygde den generisk i forhold til det. formatRollValue tar hensyn til USE_ASCI_GRAPHICS
 * og fixer slik at vi kan sende med String postfix å appende til resultat ved behov
 */


/*
 * int-array funksjoner er skilt ut klasse som statiske metoder
 */

class IntArrayUtils{
	public static int sum(int[] values) {
		if(values == null)
			throw new IllegalArgumentException("array must be and non-empty");
		
		int sum = 0;
		for(int val : values) {
			sum += val;
		}
		return sum;
	}
	
	public static double average(int[] values) {
		if(values == null || values.length == 0)
			throw new IllegalArgumentException("array must be non-null and non-empty");
			
		return (double) sum(values) / values.length;
	}
	
	public static int indexOf(int[] data, int val) {
		int n = data.length;
		for(int i=0; i<n; i++) {
			if(data[i] == val)
				return i;
		}
		return -1;
	}
}

public class Utskrift {
	static boolean USE_ASCI_GRAPHICS = true;
	
	private static int DEFAULT_LABEL_WIDTH = 20;
	
	// column counts
	private static int ROLL_TABLE_DICE_NUM_COLS = 10;
	private static int ROLL_TABLE_VALUE_NUM_COLS = 20;
		
	// asci-dices
	private static String[] DICE_ASCI_ART= {"[ . ]", "[ : ]", "[...]", "[: :]", "[:.:]", "[:::]"};
	
	
	// ------------
	
	private Utskrift(Simulering sim){}
	
	// -----------
	
	private static int getNumberOfRollTableColumns() {
		return USE_ASCI_GRAPHICS ? ROLL_TABLE_DICE_NUM_COLS : ROLL_TABLE_VALUE_NUM_COLS;
	}
	
	// -----------
	
	// format roll value as number or dice-graphics + optional postfix string
	// if no postfix is given, numbers will be formatted with a width of 4
	
	public static String formatRollValue(int value) {
		return formatRollValue(value, null);
		
	}
	public static String formatRollValue(int value, String postfix) {
		if(USE_ASCI_GRAPHICS) {
			if(postfix == null || postfix.equals("")) {
				return DICE_ASCI_ART[value - 1];
			}else {
				return DICE_ASCI_ART[value - 1] + postfix;				
			}
		}
		
		if(postfix == null || postfix.equals("")) {
			return String.format("%-4d", value);
		}else {
			return String.valueOf(value) + postfix;
		}
	}
	
	// -----------	
	
	private static void printStatsLine(String label, int value) {
		printStatsLine(label, String.valueOf(value), DEFAULT_LABEL_WIDTH);
	}
	private static void printStatsLine(String label, String value) {
		printStatsLine(label, value, DEFAULT_LABEL_WIDTH);
	}		
	private static void printStatsLine(String label, String value, int label_width) {
		String fmt = "%-" + label_width + "s: %s\n";
		System.out.printf(fmt, label, value);	
	}
	
	// -----------
	
	private static void printNumericRollValueTable(Simulering sim) {
		int numRolls = sim.rolls.length;
		int numCols = getNumberOfRollTableColumns();
		int numRows = (numRolls + numCols - 1) / numCols;
		
		for(int i=0; i<numRows; i++) {
			for(int j=0; j<numCols; j++) {
				// check that were in bounds
				if(i * numCols + j < numRolls) {
					// add extract space after %s
					String sRoll = formatRollValue(sim.rolls[i * numCols + j]);
					System.out.printf("%s ", sRoll);
				}
			}
			System.out.println();
		}
	}
	
	// -----------	
	
	private static void printHistogram(Simulering sim) {
		int numRolls = sim.rolls.length;
		var histogram = sim.histogram;
		
		printStatsLine("Antall kast", String.valueOf(numRolls));
		System.out.println();
		
		for(int i=histogram.length-1; i>=0; i--) {
			printStatsLine("Antall " + formatRollValue(i + 1, "-ere"), histogram[i]);	
		}
	}

	// ettersom det går an å få 50 6-ere og 50 5-ere blir vi nødt til å telle antall
	// terningkast og tracke de som de er flest, eller like mange som flest av
	// Fordi: "flest" impliserer at en mengde inneholder flere elementer en an ennen mengde 
	private static void printHistogramMaxIndex(Simulering sim) {
		// use IntHistogram to build histogram max values
		IntHistogramMaxValues max_histogram_vals = IntHistogram.findMaxValues(sim.histogram);
		
		// make table of roll values
		StringBuilder sb = new StringBuilder(128);
		for(int i=0; i<max_histogram_vals.indices.length; i++) {
			if(sb.length() > 0)
				sb.append(" og ");
			
			String sRoll = formatRollValue(max_histogram_vals.indices[i] + 1).trim();
			sb.append(sRoll);
		}
		
		// append how many times it was rolled for extra coolness
		sb.append(" (" + max_histogram_vals.max + " ganger)");
		
		// print
		printStatsLine("Terningverdien(e) det var flest av", sb.toString());
	}
	
	// -----------
	
	private static void printAverageValue(Simulering sim) {		
		double avg = IntArrayUtils.average(sim.rolls);
		printStatsLine("Gjennomsnittskast", String.valueOf(avg));
	}
	
	// -----------
	
	private static void printNumberOfRollsBeforeRollingSix(Simulering sim) {
		// vi finner index av første 6'er kast + 1
		int found_roll = IntArrayUtils.indexOf(sim.rolls, 6) + 1;
		
		// vi sjekker mot 0, da første kast nå er 1
		if(found_roll == 0) {
			// vi har ikke rullet 6
			printStatsLine("Antall kast for å få den første " + formatRollValue(6, "-eren"), "-");
		}else {
			// vi har rullet 6
			printStatsLine("Antall kast for å få den første " + formatRollValue(6, "-eren"), found_roll);			
		}
	}
	
	// -----------	
	
	public static void print(Simulering sim) {	
		printNumericRollValueTable(sim);
		System.out.println();
		
		printHistogram(sim);
		System.out.println();
		
		printAverageValue(sim);
		System.out.println();
			
		printNumberOfRollsBeforeRollingSix(sim);
		System.out.println();
		
		printHistogramMaxIndex(sim);
		System.out.println();
		
		if(USE_ASCI_GRAPHICS) {
			String numerator = AverageSumAndFactorsPrinter.computeString(IntArrayUtils.sum(sim.rolls));
			String denumerator = AverageSumAndFactorsPrinter.computeString(sim.rolls.length);
			
			System.out.printf("Gjennomsnittskast som fraksjon skrevet med dices (%d/%d), ikke grundig testet\n", IntArrayUtils.sum(sim.rolls), sim.rolls.length);
			System.out.println(numerator);
			System.out.println("-".repeat(numerator.length()));
			System.out.println(denumerator);

			System.out.println();
		}		
	}
}
