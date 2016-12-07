package com.horstmann.violet.application.gui.util.yangjie;

import java.util.List;

/**
 * 用来表示markov链对象的类结构，包含一系列get，set方法。
 * 
 * @author YJ
 * @version 1.0
 * */

public class Markov {

	private List<State> states; // 表头结点集合
	private int numberOfStates; // 整个markov链的状态节点个数
	private List<Transition> transitions; // 整个markov链的迁移集合
	private State initialState; // 初始状态节点
	private State finalState; // 终止状态节点
	// 存放整个Markov链上的所有路径集合，每条路径是一个迁移序列集合
	private List<Route> routeList;
	private int tcNumber;

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	public List<Transition> getTransitions() {
		return transitions;
	}

	public void setTransitions(List<Transition> transitions) {
		this.transitions = transitions;
	}

	public State getInitialState() {
		return initialState;
	}

	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}

	public State getFinalState() {
		return finalState;
	}

	public void setFinalState(State finalState) {
		this.finalState = finalState;
	}

	public int getNumberOfStates() {
		return numberOfStates;
	}

	public void setNumberOfStates(int numberOfStates) {
		this.numberOfStates = numberOfStates;
	}

	public List<Route> getRouteList() {
		return routeList;
	}

	public void setRouteList(List<Route> routeList) {
		this.routeList = routeList;
	}

	public int getTcNumber() {
		return tcNumber;
	}

	public void setTcNumber(int tcNumber) {
		this.tcNumber = tcNumber;
	}

}
