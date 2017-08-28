package cn.edu.hdu.lab.dao.condao;

import java.util.ArrayList;
import java.util.List;

public class ConUseCase {
	private String ID;
	private String useCaseName;
	private double prob;
	private String preCondition;
	private List<ConSD> conSDset=new ArrayList<ConSD>();

	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getUseCaseName() {
		return useCaseName;
	}
	public void setUseCaseName(String useCaseName){
		this.useCaseName = useCaseName;
	}
	public double getProb() {
		return prob;
	}
	public void setProb(double prob) {
		this.prob = prob;
	}
	
	public String getPreCondition() {
		return preCondition;
	}
	public void setPreCondition(String preCondition) {
		this.preCondition = preCondition;
	}
	public List<ConSD> getConSDset() {
		return conSDset;
	}
	public void setConSDset(List<ConSD> conSDset) {
		this.conSDset = conSDset;
	}
	public void addconSD(ConSD conSd)
	{
		conSDset.add(conSd);
	}
	
}
