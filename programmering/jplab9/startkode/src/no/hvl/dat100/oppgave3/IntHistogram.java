package no.hvl.dat100.oppgave3;

public class IntHistogram{
	private IntHistogram() {}
	
	public static int[] createHistogram(int[] values, int min, int max) {
		int[] histogram = new int[max - min + 1];
	    for (int val : values) {
	        if (val >= min && val <= max) {
	            histogram[val - min]++;
	        }
	    }
	    return histogram;
	}
		
	public static IntHistogramMaxValues findMaxValues(int[] histogram) {
		int max = 0;
		int count = 0;
		for (int i = 0; i < histogram.length; i++) {
			if (histogram[i] > max) {
				max = histogram[i];
				count = 1;
			} else if (histogram[i] == max) {
				count++;
			}
		}
		
		IntHistogramMaxValues result = new IntHistogramMaxValues();
		result.indices = new int[count];
		result.max = max;
		count = 0;
		for (int i = 0; i < histogram.length; i++) {
			if (histogram[i] == max) {
				result.indices[count++] = i;
			}
		}
		return result;
	}
	
	/*
	// create histogram, store max count and indices that have value equal to max_count
	// - we could return the count array if it was an int-array but we chose to use
	//	an object to return the result for simplicity and hole-less experience
	public static IntHistogramMaxValues findMaxValues(int[] histogram) {		
		// allocate memory for tracking indices
		boolean[] count = new boolean[histogram.length];
		
		// track first entry if entry > 0
		int max_value = histogram[0];
		int max_count = 1;		
		count[0] = true;		

		// iterate over histogram starting at index 1
		for(int i=1; i<histogram.length; i++) {
			if(histogram[i] > max_value) {
				// clear count[], update count[], update max_count
				java.util.Arrays.fill(count, false);			
				max_value = histogram[i];				
				count[i] = true;
				max_count = 1;				
			}else if(histogram[i] == max_value) {
				// update count[], update max_count 
				count[i] = true;
				max_count++;				
			}
		}
		
		// create result
		IntHistogramMaxValues result = new IntHistogramMaxValues();
		result.indices = new int[max_count];
		result.max = max_value;
		
		int result_index = 0;		
		for(int i=0; i<count.length; i++) {
			if(count[i])
				result.indices[result_index++] = i;
		}

		return result;
	}
	*/
}