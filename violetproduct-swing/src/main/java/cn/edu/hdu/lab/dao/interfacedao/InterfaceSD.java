package cn.edu.hdu.lab.dao.interfacedao;

public class InterfaceSD {
	private String ID;
	private String name;
	private double pro;
	private String postCondition;
	public InterfaceSD(){}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPro() {
		return pro;
	}
	public void setPro(double pro) {
		this.pro = pro;
	}
	public String getpostCondition() {
		return postCondition;
	}
	public void setpostCondition(String postCondition) {
		this.postCondition = postCondition;
	}
	
	

}
