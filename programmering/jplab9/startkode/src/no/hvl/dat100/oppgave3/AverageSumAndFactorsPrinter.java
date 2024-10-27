package no.hvl.dat100.oppgave3;

//AverageSumAndFactorsPrinter is practice & fun, used in utskrift with asci-dices
//to show numerator / denumerator for average with dice-values, gå tilbake og se hvis
//dere kommer så langt, se utskrift "gjennomsnittskast som fraksjon skrevet med ..."

public class AverageSumAndFactorsPrinter{
	private AverageSumAndFactorsPrinter(){}
	
	private static int[] computeFactorsTable(int value) {
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
		if(value <= 6) {
			return Utskrift.formatRollValue(value);
		}
		
		var count = computeFactorsTable(value);

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
