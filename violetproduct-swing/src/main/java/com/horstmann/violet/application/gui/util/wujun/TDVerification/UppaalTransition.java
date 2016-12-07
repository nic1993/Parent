package com.horstmann.violet.application.gui.util.wujun.TDVerification;

import java.util.HashMap;

public class UppaalTransition {
	
	String name;
	String fromName;
	String toName;
	int target;
	int source;
	double x = 0;
	double time = 0;
	boolean out =false;
	double duration;
	
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getToName() {
		return toName;
	}
	public void setToName(String toName) {
		this.toName = toName;
	}
	public String getName() {
		if (name == null) {
			return "null";
		}
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isOut() {
		return out;
	}
	public void setOut(boolean out) {
		this.out = out;
	}
	HashMap<String, String> label;
	
	
	public int getTarget() {
		return target;
	}
	public void setTarget(int target) {
		this.target = target;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public HashMap<String, String> getLabel() {
		return label;
	}
	public void setLabel(HashMap<String, String> label) {
		this.label = label;
	}
	 
}
