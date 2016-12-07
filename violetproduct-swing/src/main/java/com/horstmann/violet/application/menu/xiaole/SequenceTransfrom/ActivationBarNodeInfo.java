package com.horstmann.violet.application.menu.xiaole.SequenceTransfrom;

import java.util.ArrayList;
import java.util.List;

public class ActivationBarNodeInfo {

	private String Id;
	private String parentId;
	private String LocationX;
	private String LocationY;
	private String Height;//这里引入Height,为了确定returnEdge的结束点
	//是哪一个activationBarNode
	private List<ActivationBarNodeInfo> children = new ArrayList<ActivationBarNodeInfo>();//引入孩子节点
	private String LifeID;
	private String EdgeID;

	public void setEdgeID(String edgeID) {
		EdgeID = edgeID;
	}
	public String getId() {
		return Id;
	}
	public String getHeight() {
		return Height;
	}
	public void setHeight(String height) {
		Height = height;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
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
	public List<ActivationBarNodeInfo> getChildren() {
		return children;
	}
	public void setChildren(List<ActivationBarNodeInfo> children) {
		this.children = children;
	}
	public String getLifeID() {
		return LifeID;
	}
	public void setLifeID(String lifeID) {
		LifeID = lifeID;
	}
	public String getEdgeID() {
		return EdgeID;
	}
}
