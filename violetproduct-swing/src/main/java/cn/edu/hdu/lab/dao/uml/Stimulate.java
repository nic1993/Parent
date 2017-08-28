package cn.edu.hdu.lab.dao.uml;

import java.util.ArrayList;
import java.util.List;

public class Stimulate implements Cloneable {
	
	@Override
	public Object clone() {   
		Stimulate o = null;   
        try {   
            o = (Stimulate) super.clone();   
        } catch (CloneNotSupportedException e) {   
            e.printStackTrace();   
        }  
        o.setParameterNameList(new ArrayList<String>(this.parameterNameList));
        o.setParameterTypeList(new ArrayList<String>(this.parameterTypeList));
        o.setConstraintExpresstions(new ArrayList<String>(this.constraintExpresstions));
        o.setDomains(new ArrayList<String>(this.domains));
        o.setTimeConstraints(new ArrayList<String>(this.timeConstraints));
        return o;
    }
	
	List<String> parameterNameList=new ArrayList<String>();
	List<String> parameterTypeList=new ArrayList<String>();
	List<String> domains=new ArrayList<String>();
	List<String> constraintExpresstions=new ArrayList<String>();
	List<String> timeConstraints=new ArrayList<String>();
	
	public Stimulate(){}
	
	
	@Override
	public String toString() {
		return "Stimulate [parameterNameList=" + parameterNameList
				+ ", parameterTypeList=" + parameterTypeList + ", domains="
				+ domains + ", constraintExpresstions="
				+ constraintExpresstions + ", timeConstraints="
				+ timeConstraints + "]";
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
	public List<String> getTimeConstraints() {
		return timeConstraints;
	}
	public void setTimeConstraints(List<String> timeConstraints) {
		this.timeConstraints = timeConstraints;
	}

}
