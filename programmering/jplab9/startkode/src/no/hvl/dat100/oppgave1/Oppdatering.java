package no.hvl.dat100.oppgave1;

/*
 * Oppgave 1a)
 * vi kan ikke bruke enchanced for-loop direkte til å oppdatere verdier med da enchanced
 * for-loop ikke bruker referanser til primitives i java. 
 * løsning: vi endrer til en vanlig for loop og oppdaterer på index
 */

public class Oppdatering {
	static void print_tab(int[] tab) {
		// skriv ut
		System.out.print("[ ");	
		for (int x : tab) {
			System.out.print(x + " ");
		}
		System.out.println("]");
	}
	
	public static void main(String[] args) {
		
		int[] tab = {1,2,3,4,5,6};
		
		// skriv ut		
		print_tab(tab);
						
		// oppdater, increment values by 1 
		for (int i=0; i<tab.length; i++) {
			tab[i] = tab[i] + 1;
		}

		// skriv ut
		print_tab(tab);
	}

}
