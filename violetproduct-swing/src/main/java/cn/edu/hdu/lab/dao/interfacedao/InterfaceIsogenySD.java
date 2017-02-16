package cn.edu.hdu.lab.dao.interfacedao;

import java.util.List;

public class InterfaceIsogenySD {
	private String ucID;
	private String ucName;
	private List<InterfaceSD> ISDList;
	
	public InterfaceIsogenySD(){}
	public String getUcID() {
		return ucID;
	}
	public void setUcID(String ucID) {
		this.ucID = ucID;
	}
	public String getUcName() {
		return ucName;
	}
	public void setUcName(String ucName) {
		this.ucName = ucName;
	}
	public List<InterfaceSD> getISDList() {
		return ISDList;
	}
	public void setISDList(List<InterfaceSD> iSDList) {
		ISDList = iSDList;
	}
	
	

}
