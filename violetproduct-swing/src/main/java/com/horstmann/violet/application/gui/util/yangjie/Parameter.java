package com.horstmann.violet.application.gui.util.yangjie;

import java.util.List;

public class Parameter {

	private String name;
	private String paramType;
	private String domainType;
	private String domain;
	private List<String> borderValue;// 参数边界值
	private String value;
	private List<String> values;// 参数可取的有效值集合

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getDomainType() {
		return domainType;
	}

	public void setDomainType(String domainType) {
		this.domainType = domainType;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[" + name + "," + paramType + "," + domainType + "," + domain
				+ "]";
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public List<String> getBorderValue() {
		return borderValue;
	}

	public void setBorderValue(List<String> borderValue) {
		this.borderValue = borderValue;
	}

}
