package com.horstmann.violet.application.menu.xiaole.SequenceTransfrom;

public class LifeLineInfo 
{
	
	String lifeLineId;//可获取
	String lifeLineName;//可获取
	String type;//可获取
	String geometry;//可获取
	
	public void setlifeLineId(String lifeLineId)
	{
		this.lifeLineId=lifeLineId;
	}
	public void setlifeLineName(String lifeLineName)
	{
		this.lifeLineName=lifeLineName;
	}
	public void setType(String type)
	{
		this.type=type;
	}
	public String getlifeLineId()
	{
		return lifeLineId;
	}
	public String getlifeLineName()
	{
		return lifeLineName;
	}
	public String getType()
	{
		return type;
	}
	public String getGeometry() {
		return geometry;
	}
	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}
	
}
