package com.horstmann.violet.application.gui.util.yangjie;

import java.util.List;

/**
 * 用来表示markov链中状态节点的类结构，包含一系列get，set方法。
 * @author YJ
 * @version 1.0
 * */

public class State {

	private String stateName; 							 //状态节点名称
	private int stateNum;									//状态节点的编号
	private String label = "null"; 						//状态标记
	private String notation = "null"; 				//状态附加信息
	private List<Transition> outTransitions;		//状态节点的出迁移（后续迁移）集合
	private int stateAccessTimes = 0;				//状态节点的访问次数
	
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public int getStateNum() {
		return stateNum;
	}
	public void setStateNum(int stateNum) {
		this.stateNum = stateNum;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getNotation() {
		return notation;
	}
	public void setNotation(String notation) {
		this.notation = notation;
	}
	public List<Transition> getOutTransitions() {
		return outTransitions;
	}
	public void setOutTransitions(List<Transition> outTransitions) {
		this.outTransitions = outTransitions;
	}
	public int getStateAccessTimes() {
		return stateAccessTimes;
	}
	public void setStateAccessTimes(int stateAccessTimes) {
		this.stateAccessTimes = stateAccessTimes;
	}
	
}
