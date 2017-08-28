package com.horstmann.violet.application.gui.util.suna.TimeExtend;

import java.util.List;

public class Parameter {
	private String name;
	private String paramType;
	private String domainType;
	private String domain;
	private String value;

	private List<String> valueList;

	public Parameter () {}
	
	@Override
	public String toString() {
		return "Parameter [name=" + name + ", paramType=" + paramType + ", domainType=" + domainType + ", domain="
				+ domain + ", value=" + value + ", valueList=" + valueList + "]";
	}
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public List<String> getValueList() {
		return valueList;
	}
	public void setValueList(List<String> valueList) {
		this.valueList = valueList;
	}
}
