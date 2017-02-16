package cn.edu.hdu.lab.dao.uml;

import java.util.ArrayList;

public class UseCase {

	private String useCaseID;
	private String useCaseName;
	private ArrayList<Behavior> behaviors;
	private ArrayList<Diagram> diagrams;
	private ArrayList<SDSet> sdSets;
	private String preCondition;
	private double useCasePro;
	
	public UseCase(){}
	public String getUseCaseID() {
		return useCaseID;
	}
	public void setUseCaseID(String useCaseID) {
		this.useCaseID = useCaseID;
	}
	public String getUseCaseName() {
		return useCaseName;
	}
	public void setUseCaseName(String useCaseName) {
		this.useCaseName = useCaseName;
	}
	public ArrayList<Behavior> getBehaviors() {
		return behaviors;
	}
	public void setBehaviors(ArrayList<Behavior> behaviors) {
		this.behaviors = behaviors;
	}
	public ArrayList<Diagram> getDiagrams() {
		return diagrams;
	}
	public void setDiagrams(ArrayList<Diagram> diagrams) {
		this.diagrams = diagrams;
	}
	public ArrayList<SDSet> getSdSets() {
		return sdSets;
	}
	public void setSdSets(ArrayList<SDSet> sdSets) {
		this.sdSets = sdSets;
	}
	public String getPreCondition() {
		return preCondition;
	}
	public void setPreCondition(String preCondition) {
		this.preCondition = preCondition;
	}
	
	
	public double getUseCasePro() {
		return useCasePro;
	}
	public void setUseCasePro(double useCasePro) {
		this.useCasePro = useCasePro;
	}
	@Override
	public String toString() {
		return "UseCase [useCaseID=" + useCaseID + ", useCaseName="
				+ useCaseName + ", behaviors=" + behaviors + ", diagrams="
				+ diagrams + "]";
	}
	
	public void print_useCase()
	{
		System.out.println("\n******useCaseName="+useCaseName+"---useCaseID="+useCaseID+"\n" +
				"\tpreCondition="+preCondition+"\n\tUseCaseProbability="+useCasePro);
		if(behaviors!=null)
		{
			System.out.println("\tbehaviors="+behaviors.toString());
		}
		if(diagrams!=null)
		{
			System.out.println("\tdiagrams="+diagrams.toString());
			
		}
		
		if(sdSets!=null)
		{
			for(SDSet sd:sdSets)
			{
				sd.print_SDSet();
			}
		}
		
		
	}
	
}
