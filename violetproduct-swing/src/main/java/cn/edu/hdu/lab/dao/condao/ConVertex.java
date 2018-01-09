package cn.edu.hdu.lab.dao.condao;

import java.util.ArrayList;
import java.util.List;

public class ConVertex {
	private String ID; //用例ID
	private String name; //用例名字
	boolean visited=false;
	private int nickID;   //映射的节点ID；
	private List<Integer> rearNodeList=new ArrayList<Integer>(); //后继结点集合；
	public ConVertex(){}
	public ConVertex(String ID,String name,int nickID)
	{
		this.ID=ID;
		this.name=name;
		this.nickID=nickID;
		rearNodeList.clear();
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	public int getNickID() {
		return nickID;
	}
	public void setNickID(int nickID) {
		this.nickID = nickID;
	}
	
	public void addRearNode(int nickID)
	{
		rearNodeList.add(nickID);
	}
	public List<Integer> getRearNodeList() {
		return rearNodeList;
	}
	public void setRearNodeList(List<Integer> rearNodeList) {
		this.rearNodeList = rearNodeList;
	}
	
	
	
}
