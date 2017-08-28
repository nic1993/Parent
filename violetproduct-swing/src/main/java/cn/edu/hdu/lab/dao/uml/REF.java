package cn.edu.hdu.lab.dao.uml;

import java.util.ArrayList;


public class REF{
	
	private String refID;
	private String refName;
	private ArrayList<String> coveredIDs=new ArrayList<String>();
	private SDRectangle rectangle=new SDRectangle();
	private int index=0; //在消息中的位置
	private int Findex=0;//最外层的ref和最外层的组合片段的相对位置：在第几个组合片段（整合后的）后面
	
	private boolean inFragFlag=false;
	private String inID;
	private String inName;
	private int count=0;
	
	
	
	public String getRefID() {
		return refID;
	}
	public void setRefID(String refID) {
		this.refID = refID;
	}
	public String getRefName() {
		return refName;
	}
	public void setRefName(String refName) {
		this.refName = refName;
	}
	public ArrayList<String> getCoveredIDs() {
		return coveredIDs;
	}
	public void setCoveredIDs(ArrayList<String> coveredIDs) {
		this.coveredIDs = coveredIDs;
	}
	public SDRectangle getRectangle() {
		return rectangle;
	}
	public void setRectangle(SDRectangle rectangle) {
		this.rectangle = rectangle;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	public String getInID() {
		return inID;
	}
	public void setInID(String inID) {
		this.inID = inID;
	}
	public String getInName() {
		return inName;
	}
	public void setInName(String inName) {
		this.inName = inName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public boolean isInFragFlag() {
		return inFragFlag;
	}
	public void setInFragFlag(boolean inFragFlag) {
		this.inFragFlag = inFragFlag;
	}
	
	public int getFindex() {
		return Findex;
	}
	public void setFindex(int findex) {
		Findex = findex;
	}
	@Override
	public String toString() {
		return "refName=" + refName +",REF [refID=" + refID +  ", coveredIDs="
				+ coveredIDs + ", rectangle=" + rectangle + ", index=" + index
				+ ", inFragFlag=" + inFragFlag + ", inID=" + inID + ", inName="
				+ inName + ", count=" + count + "]";
	}
	
	
	
}
