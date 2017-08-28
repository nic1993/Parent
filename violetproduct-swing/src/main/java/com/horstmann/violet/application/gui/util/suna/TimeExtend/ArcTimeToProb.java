package com.horstmann.violet.application.gui.util.suna.TimeExtend;

import java.util.ArrayList;
import java.util.List;


public class ArcTimeToProb {

	public Model resolveArcTime(Model model){
        /**获取stateName到state实体的map用于确定to标签对应的实体state**/
		List<Arc> timeArcs = new ArrayList<Arc>();     //用来存放有时间约束的迁移
		
        for (State state : model.getStateList()) {
            if (state.getName() != "Exit") {
                if (state.getArcList() != null) {
                    List<Arc> arcList = state.getArcList();

                    List<InnerArcTime> innerArcTimeList = new ArrayList<>();        //时间约束集合
                    Double max = 0.0;                                               //记录多个时间段的时间最大值
                    Double min = Double.POSITIVE_INFINITY;                          //记录多个时间段的时间最小值
                    StringBuilder sb = new StringBuilder();                  
                    
                    for(Arc arc : arcList){                                         //遍历当前状态出迁移
                    	if(arc.getTime() != null){                                  //如果当前迁移带有时间约束
                        	String timeStr = arc.getTime();                         //timeStr记录当前迁移时间约束字符串
                            sb.append(timeStr);                                     //将当前迁移时间约束字符串拼接到sb
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
                                   /**有界时间处理，需要记录多个时间段的上下界max和min**/
                                   if (!timeBounds[1].equals("+")) {                    //时间约束中用“+”表示上限为无穷大
                                       lowBound = Double.valueOf(timeBounds[0]);        //valueOf(a)，把a强制转换成数值类型
                                       upBound = Double.valueOf(timeBounds[1]);
                                       System.out.println(arc.getName() +"(" + lowBound + "," + upBound + ")");
                                       /**传递上下界限信息**/
                                       innerArcTime.setLowBound(lowBound);
                                       innerArcTime.setUpBound(upBound);
                                       min = lowBound < min ? lowBound : min;
                                       max = upBound > max ? upBound : max;
                                   }
                               }
                           }else{
                               /**无界时间处理**/
                               if (timeStr.contains(",")) {
                                   String[] timeBounds = timeStr.split(",");
                                   if (timeBounds[1].equals("+")) {
                                       lowBound = Double.valueOf(timeBounds[0]);
                                       upBound = Double.POSITIVE_INFINITY;
                                       System.out.println(arc.getName() +"(" + lowBound + "," + upBound + ")");
                                       /**传递上下界限信息**/
                                       innerArcTime.setLowBound(lowBound);
                                       innerArcTime.setUpBound(upBound);
                                       /**无界时间使用指数分布计算概率**/
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
                                    /**有界时间处理，使用均匀分布计算概率**/
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

	
	  /**
     * 计算指数概率分布中某个时间范围的概率
     * @param start 起始时间
     * @param end   结束时间
     * @return result
     */
    public static double expDisdributionProbs(double start,double end){
        final double lamda = 1;
        Double probDistrOfEnd = 1 - Math.exp(-lamda*end);
        Double probDistrOfStart = 1 - Math.exp(-lamda*start);
        Double result = probDistrOfEnd - probDistrOfStart;
        return result;
    }

    /**
     * 计算均匀概率分布中某个时间范围的概率
     * @param start 目标范围起始时间
     * @param end   目标范围结束时间
     * @param rangeStart    整个均匀分布的范围起始时间
     * @param rangeEnd  ~结束时间
     * @return
     */
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
    
	    	//测试输出各迁移概率
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
//                    /**若时间约束状态入弧是一个普通弧Arc**/
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
