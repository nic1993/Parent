package cn.edu.hdu.lab.dao.uml;

import java.util.ArrayList;
/*
 * 组合片段信息
 * 组合片段ID，类型，所依附生命线ID，组合片段面所有执行路径对应的操作域
 */
public class Fragment {

	private String id;
	private String name;
	private String type;
	private ArrayList<String> coveredIDs;
	private ArrayList<Operand> operands;
	private boolean isTranslationed=false;
	public Fragment(){}
	public Fragment(String id,String type)
	{
		Set(id,type);
	}
	public void Set(String id,String type)
	{
		this.id=id;
		this.type=type;
	}
	
	public String toString()
	{
		return "Fragment: id="+id+", Type="+type;
	}
	
	public void print_Fragment()
	{
		System.out.println("Fragment:Id=" +id+ "\t name="+name +"\t type="+ type+" ");
		for(Operand operand:operands)
		{
			operand.print_Operand();
		}
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ArrayList<String> getCoveredIDs() {
		return coveredIDs;
	}
	public void setCoveredIDs(ArrayList<String> coveredIDs) {
		this.coveredIDs = coveredIDs;
	}
	public ArrayList<Operand> getOperands() {
		return operands;
	}
	public void setOperands(ArrayList<Operand> operands) {
		this.operands = operands;
	}
	public boolean isTranslationed() {
		return isTranslationed;
	}
	public void setTranslationed(boolean isTranslationed) {
		this.isTranslationed = isTranslationed;
	}
	
	
}


