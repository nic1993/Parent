package com.horstmann.violet.application.gui.util.tiancai.markov;

public class Node {
	private String id;
	private String coverdID;
	private String lifeLineName;
	
	public Node(){} //构造函数为空
	
	public Node(String id,String coverdID)
	{
		set(id,coverdID);
		
	}
	public void set(String id,String coverdID)
	{
		this.id=id;
		this.coverdID =coverdID;
	}
	
	public void setId(String id)
	{
		this.id=id;
	}
	public String getId()
	{
		return id;
	}
	public void setCoverdID(String coverdID)
	{
		this.coverdID=coverdID;
	}
	public void setLifeLineName(String lifeLineName)
	{
		this.lifeLineName=lifeLineName;
	}
	
	public String getLifeLineName() {
		return lifeLineName;
	}

	public String toString()
	{
		return "Node: " + id + "\t" + coverdID + "\t" + lifeLineName;
	}
	public void print_node()
	{
		System.out.println("Node: *nodeId=" + this.id + "\t*coveredId=" + this.coverdID + "\t*lifeLineName=" + this.lifeLineName);
	}
	
}