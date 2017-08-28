package com.horstmann.violet.application.gui.util.tanchao;

public class TanchaoMarkovStartNode {
	private String id;//id
	private String name;//name
	private String location_x;//对应的坐标（x,y）
	private String location_y;
	private String children_id;
	private String location_id;
	private String underLocation_id;
	private String name_id;
	
//	private String arc_name;//对应的以该状态为出度的迁移
//	private String arc_content;//对应的出度的迁移上的信息
//	public String getArc() {
//		return arc_name;
//	}
//	public void setArc_Name(String arc_name) {
//		this.arc_name = arc_name;
//	}
//	public String getArc_content() {
//		return arc_content;
//	}
//	public void setArc_content(String arc_content) {
//		this.arc_content = arc_content;
//	}
	
	
	
	public String getId() {
		return id;
	}
	public String getName_id() {
		return name_id;
	}
	public void setName_id(String name_id) {
		this.name_id = name_id;
	}
	public String getUnderLocation_id() {
		return underLocation_id;
	}
	public void setUnderLocation_id(String underLocation_id) {
		this.underLocation_id = underLocation_id;
	}
	public String getChildren_id() {
		return children_id;
	}
	public void setChildren_id(String children_id) {
		this.children_id = children_id;
	}
	public String getLocation_id() {
		return location_id;
	}
	public void setLocation_id(String location_id) {
		this.location_id = location_id;
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
	public String getLocation_x() {
		return location_x;
	}
	public void setLocation_x(String location_x) {
		this.location_x = location_x;
	}
	public String getLocation_y() {
		return location_y;
	}
	public void setLocation_y(String location_y) {
		this.location_y = location_y;
	}
}
