package com.horstmann.violet.application.gui.util.yangjie;

/**
 * 用来表示markov链中的迁移的类结构，包含一系列get，set方法。
 * 
 * @author YJ
 * @version 1.0
 * */

public class Transition {

	private String transitionType; // 迁移类型
	private String name; // 迁移名称（激励）
	private Stimulate stimulate;
	private double probability; // 迁移概率
	private String sender; // 迁移对应消息的发送对象
	private String receiver; // 迁移对应消息的接收对象
	private double accessTimes = 0; // 迁移的访问次数
	private String nextStateName; // 迁移的目标状态名称
	private State nextState; // 迁移的目标状态
	private boolean visited = false;// 设置迁移访问标记
	private String assignValue;// 迁移返回值
	private String assignType;// 迁移返回值类型
	private String conditions;// 存放各种测试所需条件
	private boolean isTime = false;// 是否时间扩展出来的

	@Override
	public String toString() {
		return "Transition [name=" + name + ", probability=" + probability
				+ ", nextStateName=" + nextStateName + "]";
	}

	public String getTransitionType() {
		return transitionType;
	}

	public void setTransitionType(String transitionType) {
		this.transitionType = transitionType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public double getAccessTimes() {
		return accessTimes;
	}

	public void setAccessTimes(double accessTimes) {
		this.accessTimes = accessTimes;
	}

	public String getNextStateName() {
		return nextStateName;
	}

	public void setNextStateName(String nextStateName) {
		this.nextStateName = nextStateName;
	}

	public State getNextState() {
		return nextState;
	}

	public void setNextState(State nextState) {
		this.nextState = nextState;
	}

	public Stimulate getStimulate() {
		return stimulate;
	}

	public void setStimulate(Stimulate stimulate) {
		this.stimulate = stimulate;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public String getAssignValue() {
		return assignValue;
	}

	public void setAssignValue(String assignValue) {
		this.assignValue = assignValue;
	}

	public String getAssignType() {
		return assignType;
	}

	public void setAssignType(String assignType) {
		this.assignType = assignType;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public boolean isTime() {
		return isTime;
	}

	public void setTime(boolean isTime) {
		this.isTime = isTime;
	}

}
