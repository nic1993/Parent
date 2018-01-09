package com.horstmann.violet.application.gui.util.chenzuo.Bean;

import java.io.Serializable;
import java.text.DecimalFormat;

public class TestCaseResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1106050374724867452L;

	//result of executor
	private String id;
	
	private String resultDetail = "";

	public String getResultDetail() {
		return resultDetail;
	}

	public void setResultDetail(String resultDetail) {
		this.resultDetail = resultDetail;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
