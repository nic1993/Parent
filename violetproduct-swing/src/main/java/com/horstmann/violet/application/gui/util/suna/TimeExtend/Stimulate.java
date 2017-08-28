package com.horstmann.violet.application.gui.util.suna.TimeExtend;

import java.util.List;

public class Stimulate {
	List<Parameter> parameterList;
	List<String> constraintList;
	
    public Stimulate() {}
	
	@Override
	public String toString() {
		return "Stimulate [parameterList=" + parameterList + ", constraintList=" + constraintList + "]";
	}
	public List<Parameter> getParameterList() {
		return parameterList;
	}
	public void setParameterList(List<Parameter> parameterList) {
		this.parameterList = parameterList;
	}
	public List<String> getConstraintList() {
		return constraintList;
	}
	public void setConstraintList(List<String> constraintList) {
		this.constraintList = constraintList;
	}
}
