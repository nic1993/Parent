package com.horstmann.violet.application.gui.util.suna.TimeExtend;

import java.util.List;

public class State {
	private String name;                 
	private String time;                 
	private List<Arc> arcList;         
	private String label = null;      

	public State(){}
	
    public State(String name,List<Arc> arcList) {    
        this.name = name;
        this.arcList = arcList;
    }
    
	@Override
	public String toString() {
		return "State [" + name + ":迁移数：" + arcList.size() + ", time=" + time  + ", label=" + label + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public List<Arc> getArcList() {
		return arcList;
	}
	public void setArcList(List<Arc> arcList) {
		this.arcList = arcList;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
}
