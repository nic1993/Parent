package com.horstmann.violet.application.gui.util.tiancai.markov;

public class Vertex {

	private int location;
	private double value;
	Vertex(int location,double value)
	{
		this.location=location;
		this.value=value;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
}
