package com.horstmann.violet.application.gui.util.suna.TimeExtend;

import java.util.List;

public class Model {
	private String name;
	private List<State> stateList;
	private State initialState;							
	private State finalState;		
	private List<State> timeStateList;   //用来存放和时间约束有关的状态，包括带有时间约束的状态及其扩展状态
	private List<Arc> timeArcList;       //用来存放有时间约束的迁移
	
	public Model(){ }
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<State> getStateList() {
		return stateList;
	}
	public void setStateList(List<State> stateList) {
		this.stateList = stateList;
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

	public List<State> getTimeStateList() {
		return timeStateList;
	}

	public void setTimeStateList(List<State> timeStateList) {
		this.timeStateList = timeStateList;
	}

	public List<Arc> getTimeArcList() {
		return timeArcList;
	}

	public void setTimeArcList(List<Arc> timeArcList) {
		this.timeArcList = timeArcList;
	}
 
}
