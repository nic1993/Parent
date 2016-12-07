package com.horstmann.violet.application.gui.util.yangjie;


/**
 * 专门用来计算使用链和测试链的相似度的工具类，包括利用平稳分布和欧氏距离的两种求法。
 * @author YJ
 * @version 1.0
 * */

public class CalculateSimilarity {
	
	/**
	 * 利用平稳分布求使用链和测试链的相似度
	 * @param markov 接受一个markov链对象
	 * @param PI 接受指定markov链对象的平稳分布数组
	 * @return 返回利用平稳分布计算出来的指定markov链的使用链和测试链的相似度
	 * */
	public static double statistic(Markov markov, double[] PI) {

		double dis = 0.0;
		
		for (State state : markov.getStates()) {
			
			int totalTimes = 0;
			for (Transition t : state.getOutTransitions()) {
				
				totalTimes += t.getAccessTimes();
			}
			for (Transition t : state.getOutTransitions()) {
				
				dis += PI[state.getStateNum()] * t.getProbability() * (Math.log10(t.getProbability()/(t.getAccessTimes()*1.0/totalTimes)) / Math.log10(2));
			}
		}
		
		return dis;
	}
	
	/**
	 * 利用欧氏距离求使用链和测试链的相似度
	 * @param markov 接受一个markov链对象
	 * @return 返回利用欧氏距离法求出的指定markov链的使用链和测试链的相似度
	 * */
	public static double statistic_1(Markov markov) {
		
		double distance = 0.0;
		for (State state : markov.getStates()) {
			
			int totalTimes = 0;
			for (Transition t : state.getOutTransitions()) {
				
				totalTimes += t.getAccessTimes();
			}
			for (Transition t : state.getOutTransitions()) {
				
				distance += Math.pow(t.getAccessTimes()*1.0/totalTimes-t.getProbability(), 2);
			}
		}
		return Math.sqrt(distance);
	}
	
}
