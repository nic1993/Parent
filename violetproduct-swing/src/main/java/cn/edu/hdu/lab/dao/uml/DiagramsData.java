package cn.edu.hdu.lab.dao.uml;

import java.util.ArrayList;

public class DiagramsData{
	
	private String diagramID;
	private String name;
	
	private ArrayList <String> ids = new ArrayList<String>(); // 这张图包含的组合片段、引用、对象和消息的id集合
	private ArrayList<Node> nodes=new ArrayList<Node>();
	private ArrayList <LifeLine> lifelineArray = new ArrayList <LifeLine>();
	private ArrayList <Fragment> fragmentArray = new ArrayList <Fragment>();
	private ArrayList <Message> messageArray = new ArrayList <Message>();
	private ArrayList <REF> refArray = new ArrayList <REF>(); //直接引用
	private ArrayList <REF> tempRefArray = new ArrayList <REF>(); //直接引用和嵌套引用集合

	int displayCount = 0;
	public int getDisplayCount() {
		return displayCount;
	}
	public void setDisplayCount(int displayCount) {
		this.displayCount = displayCount;
	}

	int RefEndTo;
	
	public ArrayList<String> getIds() {
		return ids;
	}
	public void setIds(ArrayList<String> ids) {
		this.ids = ids;
	}
	public ArrayList<LifeLine> getLifelineArray() {
		return lifelineArray;
	}
	public void setLifelineArray(ArrayList<LifeLine> lifelineArray) {
		this.lifelineArray = lifelineArray;
	}
	public ArrayList<Fragment> getFragmentArray() {
		return fragmentArray;
	}
	public void setFragmentArray(ArrayList<Fragment> fragmentArray) {
		this.fragmentArray = fragmentArray;
	}
	public ArrayList<Message> getMessageArray() {
		return messageArray;
	}
	public void setMessageArray(ArrayList<Message> messageArray) {
		this.messageArray = messageArray;
	}
	public ArrayList<REF> getRefArray() {
		return refArray;
	}
	public void setRefArray(ArrayList<REF> refArray) {
		this.refArray = refArray;
	}
	public String getDiagramID() {
		return diagramID;
	}
	public void setDiagramID(String diagramID) {
		this.diagramID = diagramID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRefEndTo() {
		return RefEndTo;
	}
	public void setRefEndTo(int refEndTo) {
		RefEndTo = refEndTo;
	}
	
	public ArrayList<REF> getTempRefArray() {
		return tempRefArray;
	}
	public void setTempRefArray(ArrayList<REF> tempRefArray) {
		this.tempRefArray = tempRefArray;
	}
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
	@Override
	public String toString() {
		return "DiagramsData [diagramID=" + diagramID + ", name=" + name
				+ ", ids=" + ids + ", lifelineArray=" + lifelineArray
				+ ", fragmentArray=" + fragmentArray + ", messageArray="
				+ messageArray + ", refArray=" + refArray + ", displayCount="
				+ displayCount + ", RefEndTo=" + RefEndTo + "]";
	}
	
}
