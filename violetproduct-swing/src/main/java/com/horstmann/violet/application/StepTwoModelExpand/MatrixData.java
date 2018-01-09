package com.horstmann.violet.application.StepTwoModelExpand;

import java.util.ArrayList;
import java.util.List;

public class MatrixData {
	private String usecaseName; //用例模型名称
	private String name;  //用例名 后期改id
    private List<String> scenceName; //场景名 后期id
    private Double[][] valuses;
    public MatrixData()
    {
    	scenceName = new ArrayList<String>();
    }
    
	public String getUsecaseName() {
		return usecaseName;
	}

	public void setUsecaseName(String usecaseName) {
		this.usecaseName = usecaseName;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getScenceName() {
		return scenceName;
	}
	public void setScenceName(List<String> scenceName) {
		this.scenceName = scenceName;
	}
	public Double[][] getValuses() {
		return valuses;
	}
	public void setValuses(Double[][] valuses) {
		this.valuses = valuses;
	}
    
    
}
