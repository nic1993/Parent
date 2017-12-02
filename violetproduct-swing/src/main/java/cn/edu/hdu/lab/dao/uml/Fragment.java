package cn.edu.hdu.lab.dao.uml;

import java.util.ArrayList;
/*
 * 组合片段信息
 * 组合片段ID，类型，所依附生命线ID，组合片段面所有执行路径对应的操作域
 */

import com.horstmann.violet.application.gui.DisplayForm;

public class Fragment implements Cloneable{
	@Override
	public Object clone() {   
		Fragment o = null;   
        try {   
            o = (Fragment) super.clone();   
        } catch (CloneNotSupportedException e) {   
            e.printStackTrace();   
        }  
        o.setCoveredIDs(new ArrayList<String>(this.coveredIDs));
        ArrayList<Operand> copyOpers=new ArrayList<Operand>();
        for(Operand oper:this.operands)
        {
        	copyOpers.add((Operand)oper.clone());
        }
        o.setOperands(copyOpers);
        o.setRectangle((SDRectangle)rectangle.clone());
        return o;   
    }
	private String id;
	private String name;
	private String type;
	private ArrayList<String> coveredIDs=new ArrayList<String>(); //依附生命线
	private ArrayList<Operand> operands=new ArrayList<Operand>(); //所包含操作
	private boolean isTranslationed=false;
	
	private boolean enFlag=false;  //是否被封装或嵌套
	
	private SDRectangle rectangle=new SDRectangle(); //组合片段坐标信息
	private String inID; //所在操作ID
	
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
		System.out.println("Fragment:Id=" +id+ "\t name="+name +"\t type="+ type+" "+"\n"+rectangle.toString());
		DisplayForm.mainFrame.getOutputinformation().geTextArea().append("Fragment:Id=" +id+ "\t name="+name +"\t type="+ type+" "+"\n"+rectangle.toString() + "\n");
		int length11 = DisplayForm.mainFrame.getOutputinformation().geTextArea().getText().length(); 
		DisplayForm.mainFrame.getOutputinformation().geTextArea().setCaretPosition(length11);
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
	public boolean isEnFlag() {
		return enFlag;
	}
	public void setEnFlag(boolean enFlag) {
		this.enFlag = enFlag;
	}
	public SDRectangle getRectangle() {
		return rectangle;
	}
	public void setRectangle(SDRectangle rectangle) {
		this.rectangle = rectangle;
	}
	public String getInID() {
		return inID;
	}
	public void setInID(String inID) {
		this.inID = inID;
	}
	  
}


