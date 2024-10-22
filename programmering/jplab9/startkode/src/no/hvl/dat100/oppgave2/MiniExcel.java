package no.hvl.dat100.oppgave2;

public class MiniExcel {
	private static int[][] data = { 	
			{ 1, 2, 0 }, 
			{ 3, 4, 0 },
			{ 5, 6, 0 }, 
			{ 0, 0, 0 } 
	};

	public static void skrivUt() {
		System.out.println("---------------");

		// we use fixed-width for integers for output readability
		for(int[] row : data) {
			for(int val : row) {
				System.out.printf("%4d", val);
			}
			System.out.println();
		}

		System.out.println("---------------");
	}

	public static void beregnSum() {
		int numRows = data.length;
		int numCols = data[0].length;	// we assume its safe
		int sum = 0;
		
		
		// for hver rad (med unntak av siste rad) summer elementene 
		// og skriv summen i den siste posisjonen for den aktuelle raden
		
		for(int r=0; r<numRows - 1; r++) {
			// sum col values
			sum = 0;
			for(int c=0; c<numCols-1; c++) {
				sum += data[r][c];
			}
			
			// write sum
			data[r][numCols-1] = sum;
		}


		// for hver kolonne (med unntak av siste kolonne) summer elementene 
		// og skriv summen i siste rad i posisjonen svarende til den aktuelle kolonnen

		for(int c=0; c<numCols-1; c++) {
			// sum row values
			sum = 0;
			for(int r=0; r<numRows-1; r++) {
				sum += data[r][c];
			}
			
			// write sum
			data[numRows-1][c] = sum;
		}		
	}

	public static void main(String args[]) {
		skrivUt();

		beregnSum();

		skrivUt();
	}
}
