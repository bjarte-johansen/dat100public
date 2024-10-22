package no.hvl.dat100.oppgave1;

/*
 * Oppgave 1c)
 * oppdatering fungerer i DataTabell fordi tab nå er en tabell av referanser til objekter. 
 * enchanced for-loop går itererer over referanser og kaller funksjoner på faktiske objektet
 * som ligger i minnet og og det fungerer derfor som forutsett. Merk at dersom vi hadde prøvd
 * å skrive d = new Data(4) så ville vi ikke fått endret verdi i tabell. Da hadde vi kun overskrevet
 * den lokale d-variabelen. 
 *   
 * Med int i oppgave 1a så var ikke x (ref int x : tab) en referanse til en int verdi og 
 * vi oppdatert derfor ikke verdien i array
 */

public class DataTabell {
	
	public static void main(String[] args) {
	
		Data[] tab = new Data[3];
		
		tab[0] = new Data(1);
		tab[1] = new Data(2);
		tab[2] = new Data(3);
		
		for (Data d : tab) {
			System.out.print(d + " ");
		}
		System.out.println();
		
		for (Data d : tab) {
			d.setData(d.getData() + 1);
		}
		
		
		for (Data d : tab) {
			System.out.print(d + " ");
		}
		System.out.println();
		
	}
}
