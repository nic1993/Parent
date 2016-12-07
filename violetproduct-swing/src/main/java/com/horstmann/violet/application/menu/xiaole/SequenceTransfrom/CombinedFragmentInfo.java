package com.horstmann.violet.application.menu.xiaole.SequenceTransfrom;

import java.util.List;

public class CombinedFragmentInfo {
 
	private String type;//组合片段类型
	private String id;//组合片段ID
	private List<FragmentPartInfo> fragmentParts;//组合片段的分块
	private String name;//组合片段的名字
	private String Geometry;//位置信息
	private String ID;
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
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
	public List<FragmentPartInfo> getFragmentParts() {
		return fragmentParts;
	}
	public void setFragmentParts(List<FragmentPartInfo> fragmentParts) {
		this.fragmentParts = fragmentParts;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGeometry() {
		return Geometry;
	}
	public void setGeometry(String geometry) {
		Geometry = geometry;
	}
	
	
	
	
}
