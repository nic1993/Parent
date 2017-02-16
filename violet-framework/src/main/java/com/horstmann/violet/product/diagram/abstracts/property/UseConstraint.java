package com.horstmann.violet.product.diagram.abstracts.property;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UseConstraint implements Serializable, Cloneable{
//    private List<String> NameList = new ArrayList<String>();
//
//	private List<String> contentList = new ArrayList<String>();
// 
//	private List<String> typeList = new ArrayList<String>();
//	   public void setContentList(List<String> contentList) {
//			this.contentList = contentList;
//		}
//	public void setNameList(List<String> nameList) {
//		NameList = nameList;
//	}
//    public void setTypeList(List<String> typeList) {
//		this.typeList = typeList;
//	}
//	public List<String> getNameList() {
//		return NameList;
//	}
//	public List<String> getContentList() {
//		return contentList;
//	}
//
//	public List<String> getTypeList() {
//		return typeList;
//	}
	private List<Usecaseconstraint> constraints = new ArrayList<Usecaseconstraint>();
	
	public List<Usecaseconstraint> getConstraints() {
		return constraints;
	}
	public void setConstraints(List<Usecaseconstraint> constraints) {
		this.constraints = constraints;
	}
	public UseConstraint clone() {
		UseConstraint cloned = new UseConstraint();
		return cloned;
	}
}
