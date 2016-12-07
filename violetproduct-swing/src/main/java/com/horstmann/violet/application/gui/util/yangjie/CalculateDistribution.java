package com.horstmann.violet.application.gui.util.yangjie;

import org.junit.Test;



/**
 * 专门用来计算markov链平稳分布的工具类
 * @author YJ
 * @version 1.0
 * */

public class CalculateDistribution {

	/**
	 * 利用此静态方法计算指定markov链的平稳分布
	 * @param markov 接受一个markov链对象
	 * @return 返回指定markov链对象的平稳分布数组
	 * */
	public static double[] stationaryDistribution(Markov markov){
		
		double[][]	p = new double[markov.getNumberOfStates()+1][markov.getNumberOfStates()+1];
		for (State state : markov.getStates()) {
			for (Transition t : state.getOutTransitions()) {
				p[state.getStateNum()][t.getNextState().getStateNum()] += t.getProbability();
			}
		}
		
		p[markov.getNumberOfStates()-1][0] = 0;
		p[markov.getNumberOfStates()-1][markov.getNumberOfStates()] = 1;
		p[markov.getNumberOfStates()][0] = 0.5;
		p[markov.getNumberOfStates()][markov.getNumberOfStates()] = 0.5;
		
		double[] y = new double[markov.getNumberOfStates()+1];
		for (int i = 0; i < y.length; i++) {
			y[i] = 1.0/(markov.getNumberOfStates()+1);
		}
		
		double[] result = new double[markov.getNumberOfStates()+1];
		double d = 0.0;
		
		do{
			d = 0.0;
			
			for (int j = 0; j < markov.getNumberOfStates()+1; j++) {
				
				double s = 0.0;
				for (int i = 0; i < markov.getNumberOfStates()+1; i++) {
					
					s+=  y[i] * p[i][j];
				}
				result[j] = s;
				d += Math.pow(result[j]-y[j], 2);
			}
			System.arraycopy(result, 0, y, 0, markov.getNumberOfStates()+1);//将result数组的值全部赋给数组y
			
		}while(Math.sqrt(d) >= 1e-10 );
		
		//去掉result最后一个元素并归一化、
		result[markov.getNumberOfStates()] = 0;
		double sum = 0.0;
		double[] PI = new double[markov.getNumberOfStates()];
		for (double e : result) {
			sum += e;
		}
		for (int i = 0; i < result.length-1; i++) {
			PI[i]  = result[i] / sum;
		}
		
		return PI;
	}
	
}
