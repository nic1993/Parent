package com.horstmann.violet.application.gui.util.wujun.TDVerification;

import java.util.ArrayList;

public class UppaalTemPlate {

	String name;
	ArrayList<UppaalTransition> transitions;
	ArrayList<UppaalLocation> locations;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<UppaalTransition> getTransitions() {
		return transitions;
	}
	public void setTransitions(ArrayList<UppaalTransition> transitions) {
		this.transitions = transitions;
	}
	public ArrayList<UppaalLocation> getLocations() {
		return locations;
	}
	public void setLocations(ArrayList<UppaalLocation> locations) {
		this.locations = locations;
	}
	
}
