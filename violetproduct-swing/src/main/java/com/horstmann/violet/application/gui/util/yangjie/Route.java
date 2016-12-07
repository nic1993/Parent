package com.horstmann.violet.application.gui.util.yangjie;

import java.util.List;

/**
 * 此类表示Markov链的一条路径
 * 
 * @author 夏沐
 * 
 */
public class Route {

	private List<Transition> transitionList;// 路径中迁移序列集合
	private double routeProbability; // 路径概率
	private int number; // 固定测试用例个数时，此路径所占个数

	public List<Transition> getTransitionList() {
		return transitionList;
	}

	public void setTransitionList(List<Transition> transitionList) {
		this.transitionList = transitionList;
	}

	public double getRouteProbability() {
		return routeProbability;
	}

	public void setRouteProbability(double routeProbability) {
		this.routeProbability = routeProbability;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Route [transitionList=" + transitionList
				+ ", routeProbability=" + routeProbability + ", number="
				+ number + "]";
	}

}
