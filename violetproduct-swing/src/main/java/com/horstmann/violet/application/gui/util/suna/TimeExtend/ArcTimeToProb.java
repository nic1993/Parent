package com.horstmann.violet.application.gui.util.suna.TimeExtend;

import java.util.ArrayList;
import java.util.List;


public class ArcTimeToProb {

	public Model resolveArcTime(Model model){
       
		List<Arc> timeArcs = new ArrayList<Arc>();     
		
        for (State state : model.getStateList()) {
            if (state.getName() != "Exit") {
                if (state.getArcList() != null) {
                    List<Arc> arcList = state.getArcList();

                    List<InnerArcTime> innerArcTimeList = new ArrayList<>();       
                    Double max = 0.0;                                               
                    Double min = Double.POSITIVE_INFINITY;                          
                    StringBuilder sb = new StringBuilder();                  
                    
                    for(Arc arc : arcList){                                       
                    	if(arc.getTime() != null){                                 
                        	String timeStr = arc.getTime();                      
                            sb.append(timeStr);                                     
                            timeArcs.add(arc);
                            
                    	}
                    }

                    for(Arc arc : arcList){
                    	if(arc.getTime() != null){
                     	   String timeStr = arc.getTime();
                           double lowBound,upBound;
                           InnerArcTime innerArcTime = new InnerArcTime();
                           if (!sb.toString().contains("+")){
                               if (timeStr.contains(",")) {
                                   String[] timeBounds = timeStr.split(",");

                                   if (!timeBounds[1].equals("+")) {                    
                                       lowBound = Double.valueOf(timeBounds[0]);        
                                       upBound = Double.valueOf(timeBounds[1]);
                                       System.out.println(arc.getName() +"(" + lowBound + "," + upBound + ")");
                                      
                                       innerArcTime.setLowBound(lowBound);
                                       innerArcTime.setUpBound(upBound);
                                       min = lowBound < min ? lowBound : min;
                                       max = upBound > max ? upBound : max;
                                   }
                               }
                           }else{
                              
                               if (timeStr.contains(",")) {
                                   String[] timeBounds = timeStr.split(",");
                                   if (timeBounds[1].equals("+")) {
                                       lowBound = Double.valueOf(timeBounds[0]);
                                       upBound = Double.POSITIVE_INFINITY;
                                       System.out.println(arc.getName() +"(" + lowBound + "," + upBound + ")");
                                      
                                       innerArcTime.setLowBound(lowBound);
                                       innerArcTime.setUpBound(upBound);
                                       
                                       double innerArcProb = expDisdributionProbs(lowBound, upBound);
                                       arc.setProb(innerArcProb);
                                       System.out.println("This expDistrubution of"+ arc.getName() + " prob is :" + innerArcProb);
                                   }
                                   else {
                                       lowBound = Double.valueOf(timeBounds[0]);
                                       upBound = Double.valueOf(timeBounds[1]);
                                       System.out.println(arc.getName() +"(" + lowBound + "," + upBound + ")");
                                       double innerArcProb =expDisdributionProbs(lowBound, upBound);
                                       arc.setProb(innerArcProb);
                                       System.out.println("This expDistrubution of "+ arc.getName() + " prob is :" + innerArcProb);
                                   }
                               }
                           }innerArcTimeList.add(innerArcTime);
                    	}
                    }
                    
                    for(Arc arc : arcList){
                    	if(arc.getTime() != null){
                            String timeStr = arc.getTime();
                            double lowBound,upBound;
                            if (!sb.toString().contains("+")) {
                                if (timeStr.contains(",")) {
                                    String[] timeBounds = timeStr.split(",");
                                  
                                    if (!timeBounds[1].equals("+")) {
                                        lowBound = Double.valueOf(timeBounds[0]);
                                        upBound = Double.valueOf(timeBounds[1]);
                                        double prob = averageDistributionProbs(lowBound, upBound, min, max);
                                        arc.setProb(prob);
                                        System.out.println("Current Average Prob of " + arc.getName() + "is:" + prob);
                                    }
                                }
                            }
                    	}
                    }
                }
             }
        }
        
        model.setTimeArcList(timeArcs);
        return model;
    }

	

    public static double expDisdributionProbs(double start,double end){
        final double lamda = 1;
        Double probDistrOfEnd = 1 - Math.exp(-lamda*end);
        Double probDistrOfStart = 1 - Math.exp(-lamda*start);
        Double result = probDistrOfEnd - probDistrOfStart;
        return result;
    }


     
    public static double averageDistributionProbs(double start,double end,double rangeStart,double rangeEnd){
        if (start >= end || rangeStart >= rangeEnd)  return 0.0;
        double current = end - start;
        double fullRange = rangeEnd - rangeStart;
        double prob = current / fullRange;
        return prob;
    }
	
    public static class InnerArcTime {
        private double lowBound;
        private double upBound;

        public double getLowBound() { return lowBound;}
        public void  setLowBound(double lowBound) { this.lowBound = lowBound;}

        public double getUpBound() { return upBound;}
        public void  setUpBound(double upBound) { this.upBound = upBound;}
    }
    
	    	
//	public static  void main(String[] args) throws Exception{
//		Model model = new Model();
//		model = 	StateTimeExtend.resolveStateTime(XMLParser.readXML());
//		
//		ArcTimeToProb atp = new ArcTimeToProb(); 
//		atp.resolveArcTime(model);
//		
//		Map<String, State> statesMap = new HashMap<>();
//	    Map<String, Arc> arcMap = new HashMap<>();
//     	for (State state : model.getStateList()) {
//            statesMap.put(state.getName(), state);
//            if (state.getName() != "Exit") {
//                if (state.getArcList() != null) {
//                    List<Arc> arcList = state.getArcList();
//                    
//                 
//                    for (Arc arc : arcList) {
//                        String targetStateName = arc.getToStateName();
//                        arcMap.put(arc.getName(), arc);
//                        if (targetStateName != null) {
//                            System.out.println(arc.getName() + " prob is: " + arc.getProb());
//                        }
//                        if (arc.getTime() != null) {
//                              arcMap.put(arc.getName(), arc);
//                              System.out.println("###" + arc.getName() + " prob is: " + arc.getProb());
//                        }
//                    }
//                }
//            }
//        }	
//	}
	

}
