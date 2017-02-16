package com.horstmann.violet.application.gui.util.tiancai.markov;

import java.util.List;

public class TransFlag {
	private String name;
	private String assignValue;
	private String assignType;
	private String sender;
	private String receiver;
	private double prob;
	
	private Stimulate stimulate; //消息中含有的激励：多个参数，参数类型，参数的域，参数的约束表达式；
	
	private List<String> expressions;
	public TransFlag(){}
	public TransFlag( Message message){
		this.assignValue=message.getReturnValue();
		this.assignType=message.getReturnValueType();
	    this.name=message.getName();
	    this.sender=message.getSender();
	    this.receiver=message.getReceiver();
	    this.prob=message.getProb();
	    this.stimulate=message.getStimulate();
	}
	public TransFlag( String rv,String rvt,String n,String s,String r,double p,Stimulate st ){
		this.assignValue=rv;
		this.assignType=rvt;
	    this.name=n;
	    this.sender=s;
	    this.receiver=r;
	    this.prob=p;
	    this.stimulate=st;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public double getProb() {
		return prob;
	}
	public void setProb(double prob) {
		this.prob = prob;
	}
	
	public Stimulate getStimulate() {
		return stimulate;
	}
	public void setStimulate(Stimulate stimulate) {
		this.stimulate = stimulate;
	}
	public List<String> getExpressions() {
		return expressions;
	}
	public void setExpressions(List<String> expressions) {
		this.expressions = expressions;
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
	@Override
	public String toString() {
		return "TransFlag [name=" + name + ", assignValue=" + assignValue
				+ ", assignType=" + assignType + ", sender=" + sender
				+ ", receiver=" + receiver + ", prob=" + prob + ", stimulate="
				+ stimulate + ", expressions=" + expressions + "]";
	}
	

}
