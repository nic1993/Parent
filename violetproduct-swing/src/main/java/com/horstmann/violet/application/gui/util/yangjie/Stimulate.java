package com.horstmann.violet.application.gui.util.yangjie;

import java.util.List;

public class Stimulate {

	private String name;
	List<Parameter> parameters;
	List<String> constraints;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public List<String> getConstraints() {
		return constraints;
	}

	public void setConstraints(List<String> constraints) {
		this.constraints = constraints;
	}

	@Override
	public String toString() {
		return "[" + name + "," + parameters.toString() + ","
				+ constraints.toString() + "]";
	}

}
