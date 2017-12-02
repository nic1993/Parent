package com.horstmann.violet.application.gui.util.yangjie;

import java.util.List;

public class Stimulate {

	private String name;
	List<Parameter> parameters;
	List<String> constraints;
	private String assignValue;// 激励返回值
	private String assignType;// 激励返回值类型
	private String conditions;// 存放各种测试所需条件
	private boolean isTime = false;// 是否时间扩展出来的

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
		return "[" + name + "," + parameters.toString() + /*
														 * "," +
														 * constraints.toString
														 * () +
														 */"]";
	}

	public String getAssignType() {
		return assignType;
	}

	public void setAssignType(String assignType) {
		this.assignType = assignType;
	}

	public String getAssignValue() {
		return assignValue;
	}

	public void setAssignValue(String assignValue) {
		this.assignValue = assignValue;
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
