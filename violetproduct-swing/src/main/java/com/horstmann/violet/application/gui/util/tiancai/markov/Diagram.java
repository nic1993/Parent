package com.horstmann.violet.application.gui.util.tiancai.markov;

public class Diagram {
	private String diagramID;
	private String diagramName;
	private String diagramType;
	private String behaviorID;
	private String notation;
	private double prob;   //³¡¾°Ö´ÐÐ¸ÅÂÊ
	
	public Diagram() {}
	
	@Override
	public String toString() 
	{
		return "Diagram [diagramID=" + diagramID + ", diagramName="
				+ diagramName + ", diagramType=" + diagramType
				+ ", behaviorID=" + behaviorID + ", notation=" + notation
				+ ", prob=" + prob + "]";
	}

	public String getDiagramID() {
		return diagramID;
	}

	public void setDiagramID(String diagramID) {
		this.diagramID = diagramID;
	}

	public String getDiagramName() {
		return diagramName;
	}

	public void setDiagramName(String diagramName) {
		this.diagramName = diagramName;
	}

	public String getDiagramType() {
		return diagramType;
	}

	public void setDiagramType(String diagramType) {
		this.diagramType = diagramType;
	}

	public String getBehaviorID() {
		return behaviorID;
	}

	public void setBehaviorID(String behaviorID) {
		this.behaviorID = behaviorID;
	}

	public String getNotation() {
		return notation;
	}

	public void setNotation(String notation) {
		this.notation = notation;
	}

	public double getProb() {
		return prob;
	}

	public void setProb(double prob) {
		this.prob = prob;
	}
	
	
}
