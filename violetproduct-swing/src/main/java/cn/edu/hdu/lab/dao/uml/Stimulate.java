package cn.edu.hdu.lab.dao.uml;

import java.util.ArrayList;
import java.util.List;

public class Stimulate {
	List<String> parameterNameList=new ArrayList<String>();
	List<String> parameterTypeList=new ArrayList<String>();
	List<String> domains=new ArrayList<String>();
	List<String> constraintExpresstions=new ArrayList<String>();
	
	public Stimulate(){}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Stimulate [parameterNameList=");
		builder.append(parameterNameList);
		builder.append(", parameterTypeList=");
		builder.append(parameterTypeList);
		builder.append(", domains=");
		builder.append(domains);
		builder.append(", constraintExpresstions=");
		builder.append(constraintExpresstions);
		builder.append("]");
		return builder.toString();
	}
	
	public List<String> getParameterNameList() {
		return parameterNameList;
	}

	public void setParameterNameList(List<String> parameterNameList) {
		this.parameterNameList = parameterNameList;
	}
	public List<String> getParameterTypeList() {
		return parameterTypeList;
	}
	public void setParameterTypeList(List<String> parameterTypeList) {
		this.parameterTypeList = parameterTypeList;
	}
	public List<String> getDomains() {
		return domains;
	}
	public void setDomains(List<String> domains) {
		this.domains = domains;
	}
	public List<String> getConstraintExpresstions() {
		return constraintExpresstions;
	}
	public void setConstraintExpresstions(List<String> constraintExpresstions) {
		this.constraintExpresstions = constraintExpresstions;
	}
	public void addParameterName(String str)
	{
		parameterNameList.add(str);
	}
	public void addParameterType(String str)
	{
		parameterTypeList.add(str);
	}
	public void addDomain(String str)
	{
		domains.add(str);
	}
	public void addConstraintExpresstion(String str)
	{
		constraintExpresstions.add(str);
	}


}
