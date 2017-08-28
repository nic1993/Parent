package com.horstmann.violet.application.gui.util.tanchao.markovlayout;

public class EdgeBean {
	
   private NodeBean sourceNodeBean;
   private NodeBean targetNodeBean;
   private long ageLong;
   
   public EdgeBean(NodeBean sourceNodeBean,NodeBean targetNodeBean,long ageLong){
	   this.sourceNodeBean=sourceNodeBean;
	   this.targetNodeBean=targetNodeBean;
	   this.ageLong=ageLong;
   }   
public NodeBean getSourceNodeBean() {
	return sourceNodeBean;
}
public void setSourceNodeBean(NodeBean sourceNodeBean) {
	this.sourceNodeBean = sourceNodeBean;
}
public NodeBean getTargetNodeBean() {
	return targetNodeBean;
}
public void setTargetNodeBean(NodeBean targetNodeBean) {
	this.targetNodeBean = targetNodeBean;
}
public long getAgeLong() {
	return ageLong;
}
public void setAgeLong(long ageLong) {
	this.ageLong = ageLong;
}
}
