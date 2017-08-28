package com.horstmann.violet.application.menu.xiaole.SequenceTransfrom;

import java.util.ArrayList;
import java.util.List;

public class LifeLineNodeInfo implements Comparable{

	private String Id;
	private String LocationX;
	private String LocationY;
	private String name; 
	private List<ActivationBarNodeInfo> activationBarNodes=new ArrayList<ActivationBarNodeInfo>();
	private List<CallEdgeInfo> callEdges = new ArrayList<CallEdgeInfo>(); 
    private List<ReturnEdgeInfo> returnEdges = new ArrayList<ReturnEdgeInfo>(); 
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	
	public List<ActivationBarNodeInfo> getActivationBarNodes() {
		return activationBarNodes;
	}
	public void setActivationBarNodes(List<ActivationBarNodeInfo> activationBarNodes) {
		this.activationBarNodes = activationBarNodes;
	}
	public String getLocationX() {
		return LocationX;
	}
	public void setLocationX(String locationX) {
		LocationX = locationX;
	}
	public String getLocationY() {
		return LocationY;
	}
	public void setLocationY(String locationY) {
		LocationY = locationY;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<CallEdgeInfo> getCallEdges() {
		return callEdges;
	}
	public void setCallEdges(List<CallEdgeInfo> callEdges) {
		this.callEdges = callEdges;
	}
	public List<ReturnEdgeInfo> getReturnEdges() {
		return returnEdges;
	}
	public void setReturnEdges(List<ReturnEdgeInfo> returnEdges) {
		this.returnEdges = returnEdges;
	}
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof LifeLineNodeInfo)
		{
			LifeLineNodeInfo lifeLineNodeInfo = (LifeLineNodeInfo) o;
			int x1 = Integer.valueOf(this.getLocationX());
			int x2 = Integer.valueOf(lifeLineNodeInfo.getLocationX());
			return x1 - x2;
		}
		return 0;
	}
	
}
