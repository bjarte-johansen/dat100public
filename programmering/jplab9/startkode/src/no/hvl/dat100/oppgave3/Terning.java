package no.hvl.dat100.oppgave3;

import java.util.Random;

class RandomSupplier{
	private RandomSupplier() {}
	
	public static final Random rand = new Random();	
}

class RandomInt{
	private RandomInt() {}
	
	public static int random(int max) {
		return RandomSupplier.rand.nextInt(max);
	}	
	public static int random(int min, int max) {
		return min + random(max - min);
	}
}
/*
class RandomBit{
	public static boolean random_bit() {
		return RandomInt.random(1 + 1) > 0;
	}	
}
*/

public class Terning {
	private int value;

	public Terning() {
		value = 0;
	}

	public int value() {
		if(value == 0) {
			throw new RuntimeException("Dice must be rolled before calling getOyne");
		}
		
		return value;
	}

	public int roll() {		
		value = RandomInt.random(1, 6 + 1);
		return value;
	}
}