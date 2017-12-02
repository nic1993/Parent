package com.horstmann.violet.application.gui.util.suna.TimeExtend;


public class TimeToProb {
	
	Double max = 0.0;                                              
    Double min = Double.POSITIVE_INFINITY;                          
	
	public double calProb(String timeStr){
		double lowBound,upBound;
		double prob = 0.0;
		
		if (timeStr.contains("+")) {
			if (timeStr.contains(",")) {
				String[] timeBounds = timeStr.split(",");   
				lowBound = Double.valueOf(timeBounds[0]);
				upBound = Double.POSITIVE_INFINITY;
				prob = expDisdributionProbs(lowBound*10, upBound*10);
				
			}
		}else{
			
			String[] timeBounds = timeStr.split(",");   
			lowBound = Double.valueOf(timeBounds[0]);
			upBound = Double.valueOf(timeBounds[1]);
			double prob1 = expDisdributionProbs(upBound, Double.POSITIVE_INFINITY);
			
			
			if(lowBound == 0) {
				prob = 1.0-prob1;
			}else {
				prob = (1.0-prob1)*lowBound/upBound;
			}
		}
		return prob;
	}
	
	
    public double expDisdributionProbs(double start,double end){
        final double lamda = 0.05;
        Double probDistrOfEnd = 1 - Math.exp(-lamda*end);
        Double probDistrOfStart = 1 - Math.exp(-lamda*start);
        Double result = probDistrOfEnd - probDistrOfStart;
        return result;
    }
}
