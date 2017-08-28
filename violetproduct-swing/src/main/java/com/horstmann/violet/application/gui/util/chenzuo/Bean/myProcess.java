package com.horstmann.violet.application.gui.util.chenzuo.Bean;

import java.io.Serializable;

public class myProcess implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5505096620733312497L;
	// ID
	public int processID;
	// name
	public String processName;
	// parameters
	public String processParam;
	// status
	public String processStatus;
	// executor
	public boolean processExec;

	public myProcess(int processID, String processName, String processParam, String processStatus, boolean processExec) {
		this.processID = processID;
		this.processName = processName;
		this.processParam = processParam;
		this.processStatus = processStatus;
		this.processExec = processExec;
	}

	public myProcess() {
	}

	public int getProcessID() {
		return processID;
	}

	public void setProcessID(int processID) {
		this.processID = processID;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessParam() {
		return processParam;
	}

	public void setProcessParam(String processParam) {
		this.processParam = processParam;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public boolean isProcessExec() {
		return processExec;
	}

	public void setProcessExec(boolean processExec) {
		this.processExec = processExec;
	}
	
	@Override
	public String toString() {
		return "myProcess [processID=" + processID + ", processName=" + processName + ", processParam=" + processParam
				+ ", processStatus=" + processStatus + ", processExec=" + processExec + "]";
	}
}
