package com.horstmann.violet.application.gui.util.wujun.TDVerification;

import java.util.ArrayList;

public class UppaalLocation {
	String id;
	String name;
	double x = 0;
	ArrayList<UppaalTransition> transitions = new ArrayList<>();
	String finl = "false";
	String init = "false";
	
	public String getFinl() {
		return finl;
	}
	public void setFinl(String finl) {
		this.finl = finl;
	}
	public String getInit() {
		return init;
	}
	public void setInit(String init) {
		this.init = init;
	}
	public ArrayList<UppaalTransition> getTransitions() {
		return transitions;
	}
	public void setTransitions(ArrayList<UppaalTransition> transitions) {
		this.transitions = transitions;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	
}
