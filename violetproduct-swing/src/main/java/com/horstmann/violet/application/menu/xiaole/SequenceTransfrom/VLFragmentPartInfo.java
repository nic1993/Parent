package com.horstmann.violet.application.menu.xiaole.SequenceTransfrom;

import java.util.ArrayList;
import java.util.List;

public class VLFragmentPartInfo {
 
	private String conditionText;
	private List<String> coverMessagesID;
	private String borderlineX1;
	private String borderlineY1;
	private String borderlineX2;
	private String borderlineY2;
	private String size;
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private ArrayList<String> nestingCombinedFragmentID;
	public String getConditionText() {
		return conditionText;
	}
	public void setConditionText(String conditionText) {
		this.conditionText = conditionText;
	}
	public List<String> getCoverMessagesID() {
		return coverMessagesID;
	}
	public void setCoverMessagesID(List<String> coverMessagesID) {
		this.coverMessagesID = coverMessagesID;
	}
	public String getBorderlineX1() {
		return borderlineX1;
	}
	public void setBorderlineX1(String borderlineX1) {
		this.borderlineX1 = borderlineX1;
	}
	public String getBorderlineY1() {
		return borderlineY1;
	}
	public void setBorderlineY1(String borderlineY1) {
		this.borderlineY1 = borderlineY1;
	}
	public String getBorderlineX2() {
		return borderlineX2;
	}
	public void setBorderlineX2(String borderlineX2) {
		this.borderlineX2 = borderlineX2;
	}
	public String getBorderlineY2() {
		return borderlineY2;
	}
	public void setBorderlineY2(String borderlineY2) {
		this.borderlineY2 = borderlineY2;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public ArrayList<String> getNestingCombinedFragmentID() {
		return nestingCombinedFragmentID;
	}
	public void setNestingCombinedFragmentID(ArrayList<String> nestingCombinedFragmentID) {
		this.nestingCombinedFragmentID = nestingCombinedFragmentID;
	}
	
	
}
