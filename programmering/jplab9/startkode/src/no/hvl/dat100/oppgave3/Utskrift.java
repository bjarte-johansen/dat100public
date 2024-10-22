package no.hvl.dat100.oppgave3;

import java.util.Arrays;
import no.hvl.dat100.oppgave3.*;

/*
 * no values was cached during this execution, for simplicity
 * Utskrift-klassen er besvarelsen
 * (class AverageSumAndFactorsPrinter) is practice, used in utskrift with asci-dices
 */

class AverageSumAndFactorsPrinter{
	private AverageSumAndFactorsPrinter(){}
	
	private static int[] computeFactors(int value) {
		int[] count = new int[6];
		int factor = 6;
		while(value > 0 && factor >= 1) {
			int d = value / factor;
			count[factor - 1] = d;
			value = value - d * factor;
			factor--;
		}
		return count;
	}
	
	public static String computeString(int value) {				
		if(value == 1) {
			return Utskrift.formatRollValue(value);
		}
		
		var count = computeFactors(value);

		var sb = new StringBuilder(256);
		for(int i=0; i<count.length; i++) {
			if(count[i] > 0) {
				if(sb.length() > 0) 
					sb.append(" + ");
				
				String term = Utskrift.formatRollValue(i + 1); 
				
				if(count[i] > 1) {
					String tmp = computeString(count[i]);
					
					if(tmp.indexOf("+") > -1 || tmp.indexOf("x") > -1) {
						tmp = "(" + tmp + ")";
					}
					
					term += " x " + tmp;
					term = "(" + term + ")";
				}
				
				sb.append(term);				
			}
		}
		
		return sb.toString();
	}
}

public class Utskrift {
	static boolean USE_DICE_GRAPHICS = true;
	
	static int ROLL_TABLE_DICE_NUM_COLS = 10;
	static int ROLL_TABLE_VALUE_NUM_COLS = 20;
	
	static int DICE_GRAPHICS_FORMAT_WIDTH = 6;
	
	static String[] DICE_ASCI_ART= {"[ . ]", "[ : ]", "[...]", "[: :]", "[:.:]", "[:::]"};
	
	Simulering sim;
	
	// ------------
	
	public Utskrift(Simulering sim){
		this.sim = sim;
	}
	
	
	// -----------
	
	public static int getNumberOfRollTableColumns() {
		return USE_DICE_GRAPHICS ? ROLL_TABLE_DICE_NUM_COLS : ROLL_TABLE_VALUE_NUM_COLS;
	}
	
	// -----------
	// format roll value as number or dice-graphics + optional postfix string
	// if no postfix is given, numbers will be formatted with a width of 4
	
	public static String formatRollValue(int value) {
		return formatRollValue(value, null);
		
	}
	public static String formatRollValue(int value, String postfix) {
		if(USE_DICE_GRAPHICS) {
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
	
	public static void printStatsLine(String label, int value) {
		printStatsLine(label, String.valueOf(value), 14);
	}
	public static void printStatsLine(String label, String value) {
		printStatsLine(label, value, 14);
	}		
	public static void printStatsLine(String label, String value, int label_width) {
		String fmt = "%-" + label_width + "s: %s\n";
		System.out.printf(fmt, label, value);	
	}
	
	// -----------
	
	public static void printNumericRollValueTable(Simulering sim) {
		int numRolls = sim.rolls.length;
		int numCols = getNumberOfRollTableColumns();
		int numRows = (numRolls + numCols - 1) / numCols;
		
		for(int i=0; i<numRows; i++) {
			for(int j=0; j<numCols; j++) {
				// check that were in bounds, not necessary in example
				// note the extra space so that asci-dice have a space between them 
				if(i * numCols + j < numRolls)
					System.out.printf("%s ", formatRollValue(sim.rolls[i * numCols + j]));
			}
			System.out.println();
		}
	}
	
	// -----------	
	
	public static void printHistogram(Simulering sim) {
		int numRolls = sim.rolls.length;
		var histogram = sim.histogram;
		
		printStatsLine("Antall kast", String.valueOf(numRolls));
		
		for(int i=histogram.length-1; i>=0; i--) {
			printStatsLine("Antall " + formatRollValue(i + 1, "-ere"), histogram[i]);	
		}
	}

	// lag histogram og finn histogram-indeksene med maksimalt antall trill
	// og skriv ut til skjerm. Legg merke til at vi kan ha 50 1-ere og 50 6-ere
	public static void printHistogramMaxIndex(Simulering sim) {
		var histogram = sim.histogram;
		
		// we track count[] and max_count
		int[] count = {histogram[0],0,0,0,0,0};
		int max_count = histogram[0];
		
		// iterate over histogram starting at index 1
		for(int i=1; i<histogram.length; i++) {
			if(histogram[i] > max_count) {
				// clear count array and set current index with new max_count
				java.util.Arrays.fill(count, 0);
				count[i] = histogram[i];
				max_count = histogram[i];
			}else if(histogram[i] == max_count) {
				// if current histogram value equals max_count, update count[i]
				count[i] = histogram[i];
			}
		}
		
		// make table of roll values
		StringBuilder sb = new StringBuilder(128);
		for(int i=0; i<count.length; i++) {
			if(count[i] > 0) {
				if(sb.length() > 0)
					sb.append(" og ");
				
				sb.append(formatRollValue(i + 1).trim());
			}
		}
		
		// append how many times it was rolled for extra coolness
		sb.append(" (" + max_count + " ganger)");
		
		printStatsLine("Terningverdien(e) det var flest av", sb.toString());
	}
	
	// -----------
	
	public static int sum(int[] values) {
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
	
	public static void printAverageValue(Simulering sim) {		
		printStatsLine("Gjennomsnittskast", String.valueOf(average(sim.rolls)));
	}
	
	// -----------
	
	public static int indexOf(int[] data, int val) {
		for(int i=0; i<data.length; i++) {
			if(data[i] == val)
				return i;
		}
		return -1;
	}
	
	public static void printNumberOfRollsBeforeRollingSix(Simulering sim) {
		int s = indexOf(sim.rolls, 6);
		if(s == -1) {
			printStatsLine("Antall kast for å få den første "+formatRollValue(6, "-eren"), "-");
		}else {
			printStatsLine("Antall kast for å få den første "+formatRollValue(6, "-eren"), s + 1);			
		}
	}
	
	// -----------	
	
	public static void printStatistics(Simulering sim) {
		System.out.println("TERNINGKASTSIMULATOR");
		System.out.println();
		
		printNumericRollValueTable(sim);
		System.out.println();
		
		printHistogram(sim);
		System.out.println();
		
		printAverageValue(sim);
		System.out.println();
		
		if(USE_DICE_GRAPHICS) {
			String numerator = AverageSumAndFactorsPrinter.computeString(sum(sim.rolls));
			String denumerator = AverageSumAndFactorsPrinter.computeString(sim.rolls.length);
			
			System.out.printf("Gjennomsnittskast som fraksjon skrevet med dices (%d/%d), ikke grundig testet\n", sum(sim.rolls), sim.rolls.length);
			System.out.println(numerator);
			System.out.println("-".repeat(numerator.length()));
			System.out.println(denumerator);

			System.out.println();
		}
		
		printNumberOfRollsBeforeRollingSix(sim);
		System.out.println();
		
		printHistogramMaxIndex(sim);
		System.out.println();			
	}
}
