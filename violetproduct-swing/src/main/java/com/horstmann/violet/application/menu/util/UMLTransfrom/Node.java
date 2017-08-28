package com.horstmann.violet.application.menu.util.UMLTransfrom;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Node  {

	private String type;
	private String id;
	private String name;
	private String attribute;
	private String method;
	private Point leftLocation;//节点左上角的坐标
	private Point rightLocation;//节点右下角的坐标
	
	
	private List<String> constraintName = new ArrayList<String>();
	private List<String> constraintContent= new ArrayList<String>();
	private List<String> constraintType= new ArrayList<String>();
	
	private List<String> sequenceContent = new ArrayList<String>();
	private List<String> sequenceName= new ArrayList<String>();
	private List<String> sequenceType = new ArrayList<>();
	private List<String> sequenceConstraintName = new ArrayList<String>();
	
	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Point getLeftLocation() {
		return leftLocation;
	}

	public void setLeftLocation(Point leftLocation) {
		this.leftLocation = leftLocation;
	}

	public Point getRightLocation() {
		return rightLocation;
	}

	public void setRightLocation(Point rightLocation) {
		this.rightLocation = rightLocation;
	}

	public Node(){}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public Point getLocation() {
		return leftLocation;
	}

	public void setLocation(Point location) {
		this.leftLocation = location;
	}
    
	


	public List<String> getConstraintName() {
		return constraintName;
	}

	public void setConstraintName(List<String> constraintName) {
		this.constraintName = constraintName;
	}

	public List<String> getConstraintContent() {
		return constraintContent;
	}

	public void setConstraintContent(List<String> constraintContent) {
		this.constraintContent = constraintContent;
	}

	public List<String> getConstraintType() {
		return constraintType;
	}

	public void setConstraintType(List<String> constraintType) {
		this.constraintType = constraintType;
	}

	public List<String> getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(List<String> sequenceName) {
		this.sequenceName = sequenceName;
	}
    
	public List<String> getSequenceContent() {
		return sequenceContent;
	}

	public void setSequenceContent(List<String> sequenceContent) {
		this.sequenceContent = sequenceContent;
	}

	public List<String> getSequenceType() {
		return sequenceType;
	}

	public void setSequenceType(List<String> sequenceType) {
		this.sequenceType = sequenceType;
	}

	public List<String> getSequenceConstraintName() {
		return sequenceConstraintName;
	}

	public void setSequenceConstraintName(List<String> sequenceConstraintName) {
		this.sequenceConstraintName = sequenceConstraintName;
	}

	@Override
	public String toString() {
		return "Node [type=" + type + ", id=" + id + ", name=" + name
				+ ", attribute=" + attribute + ", method=" + method
				+ ", leftLocation=" + leftLocation + ", rightLocation="
				+ rightLocation + "]";
	}
	
	
}
