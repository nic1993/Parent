package cn.edu.hdu.lab.dao.condao;

public class ConSD {
	private String ID;
	private String name;
	private double prob;
	private String postCondition;
	
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
	public double getProb() {
		return prob;
	}
	public void setProb(double prob) {
		this.prob = prob;
	}
	public String getPostCondition() {
		return postCondition;
	}
	public void setPostCondition(String postCondition) {
		this.postCondition = postCondition;
	}
}
