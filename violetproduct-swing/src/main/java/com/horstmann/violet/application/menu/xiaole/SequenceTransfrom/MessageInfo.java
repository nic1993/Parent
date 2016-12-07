package com.horstmann.violet.application.menu.xiaole.SequenceTransfrom;

import java.util.ArrayList;

import org.dom4j.Element;

public class MessageInfo {
	String connectorId;//可获取
	String sourceId;//可获取
	String tragetId;//可获取
	String name;//可获取
	String sendEvent;//fragment 上面和生命线ID相对应，获取Activation
	String type;//可获取
	String messageSort;//可获取
	String sequence_points;//可获取 
	String receiveEvent;//为了导入EA后来加的 //获取Activation
	String diagram_id;//为了导入EA后来加的//固定，可获取
	String ea_type;//为了导入EA后来加的//为图的类型，可获取
	String privatedata5;//为了导入EA后来加的//前面private1 不知道什么意思
	Boolean isNavigable;
	String LocationY;//为了对Seqno标签进行处理
	String ID;
	
	public String getLocationY() {
		return LocationY;
	}
	public void setLocationY(String locationY) {
		LocationY = locationY;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	ArrayList<Element> frag=new ArrayList<Element>();
	
	public String getPrivatedata5() {
		return privatedata5;
	}
	public void setPrivatedata5(String privatedata5) {
		this.privatedata5 = privatedata5;
	}
	public String getEa_type() {
		return ea_type;
	}
	public void setEa_type(String ea_type) {
		this.ea_type = ea_type;
	}
	public String getReceiveEvent() {
		return receiveEvent;
	}
	public void setReceiveEvent(String receiveEvent) {
		this.receiveEvent = receiveEvent;
	}
	public String getDiagram_id() {
		return diagram_id;
	}
	public void setDiagram_id(String diagram_id) {
		this.diagram_id = diagram_id;
	}
	public String getSequence_points() {
		return sequence_points;
	}
	public void setSequence_points(String sequence_points) {
		this.sequence_points = sequence_points;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	public String getConnectorId() 
	{
		return connectorId;
	}
	public void setConnectorId(String connectorId)
	{
		this.connectorId = connectorId;
	}
	public String getSourceId()
	{
		return sourceId;
	}
	public void setSourceId(String sourceId)
	{
		this.sourceId = sourceId;
	}
	public String getTragetId() 
	{
		return tragetId;
	}
	public void setTragetId(String tragetId) 
	{
		this.tragetId = tragetId;
	}
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public String getSendEvent() 
	{
		return sendEvent;
	}
	public void setSendEvent(String sendEvent) 
	{
		this.sendEvent = sendEvent;
	}
	public String getMessageSort() 
	{
		return messageSort;
	}
	public void setMessageSort(String messageSort) 
	{
		this.messageSort = messageSort;
	}
	
	public Boolean getIsNavigable() {
		return isNavigable;
	}
	public void setIsNavigable(Boolean isNavigable) {
		this.isNavigable = isNavigable;
	}
	public ArrayList<Element> getFrag()
	{
		return frag;
	}
	public void setFrag(ArrayList<Element> frag)
	{
		this.frag=frag;
	}
}
