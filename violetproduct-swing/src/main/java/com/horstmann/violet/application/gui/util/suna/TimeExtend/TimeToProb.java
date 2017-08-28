package com.horstmann.violet.application.gui.util.suna.TimeExtend;

/**
 *将状态时间区间转化为其到时间驻留状态出迁移的概率
 */
public class TimeToProb {
	
	Double max = 0.0;                                               //记录多个时间段的时间最大值
    Double min = Double.POSITIVE_INFINITY;                          //记录多个时间段的时间最小值
	
	public double calProb(String timeStr){
		double lowBound,upBound;
		double prob = 0.0;
		
		/**无界时间处理,使用指数概率分布计算概率**/
		if (timeStr.contains("+")) {
			if (timeStr.contains(",")) {
				String[] timeBounds = timeStr.split(",");   //提取时间约束区间边界值
				lowBound = Double.valueOf(timeBounds[0]);
				upBound = Double.POSITIVE_INFINITY;
				prob = expDisdributionProbs(lowBound, upBound);
				
			}
		}else{
			/**有界时间处理，先计算从上限及以上的无界概率，在计算其区间在0到上限之间的均匀分布概率**/
			String[] timeBounds = timeStr.split(",");   //提取时间约束区间边界值
			lowBound = Double.valueOf(timeBounds[0]);
			upBound = Double.valueOf(timeBounds[1]);
			double prob1 = expDisdributionProbs(upBound, Double.POSITIVE_INFINITY);
			
			//如果下限是0，直接是1-无界概率
			if(lowBound == 0) {
				prob = 1.0-prob1;
			}else {
				prob = (1.0-prob1)*lowBound/upBound;
			}
		}
		return prob;
	}
	
	  /**
     * 计算指数概率分布中某个时间范围的概率
     * @param start 起始时间
     * @param end   结束时间
     * @return result
     */
    public double expDisdributionProbs(double start,double end){
        final double lamda = 0.05;
        Double probDistrOfEnd = 1 - Math.exp(-lamda*end);
        Double probDistrOfStart = 1 - Math.exp(-lamda*start);
        Double result = probDistrOfEnd - probDistrOfStart;
        return result;
    }
}
