package no.hvl.dat100.oppgave3;

public class Terning {

	// aktuell verdi for terningen
	private int value;

	public Terning() {
		value = 0;
	}

	public int getOyne() {
		if(value == 0) {
			throw new RuntimeException("Dice must be rolled before calling getOyne");
		}
		
		return value;
	}

	public void trill() {
		value = 1 + (int)(Math.random() * (6 - 1 + 1));
	}
}