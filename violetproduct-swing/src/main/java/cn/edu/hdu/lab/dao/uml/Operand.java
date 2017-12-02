package cn.edu.hdu.lab.dao.uml;

import java.util.ArrayList;
import java.util.Iterator;

import com.horstmann.violet.application.gui.DisplayForm;

/*
 * 操作域
 * 每个组合片段下面有多条执行路径
 * 每条执行路径对应一个操作域（操作id,执行条件，执行路径上含有的结点和信息）
 */
public class Operand implements Cloneable{
	@Override
	public Object clone() {   
		Operand o = null;   
        try {   
            o = (Operand) super.clone();   
        } catch (CloneNotSupportedException e) {   
            e.printStackTrace();   
        }  
        o.setNodeIds(new ArrayList<String>(this.nodeIds));
        
        ArrayList<Node> copyNodes=new ArrayList<Node>();
        for(Node node:this.nodes)
        {
        	copyNodes.add((Node)node.clone());
        }        
        o.setNodes(copyNodes);
        
        ArrayList<Message> copyMessages=new ArrayList<Message>();
        for(Message mess:this.messages)
        {
        	copyMessages.add((Message)mess.clone());
        }        
        o.setMessages(copyMessages);
        
        if(this.fragments.size()>0)
        {
        	ArrayList<Fragment> copyFragments=new ArrayList<Fragment>();
        	for(Fragment f:this.fragments)
            {
        		copyFragments.add((Fragment)f.clone());
            }        
            o.setFragments(copyFragments);        	
        }
        
        ArrayList<String> copyFragIDs=new ArrayList<String>(fragmentIDs);
        o.setFragmentIDs(copyFragIDs);
        o.setRectangle((SDRectangle)rectangle.clone());
        return o;
    }
	
	private String id;
	private String condition;
	private ArrayList<Node> nodes=new ArrayList<Node>();
	private ArrayList<String> nodeIds=new ArrayList<String>();
	private ArrayList<Message> messages=new ArrayList<Message>();
	
	private boolean isHasFragment=false;
	private ArrayList<String> fragmentIDs=new ArrayList<String>();
	private ArrayList<Fragment> fragments=new ArrayList<Fragment>();
	
	private SDRectangle rectangle; //操作域坐标信息
	private REF ref;
	
		
	
	public Operand(){}
	public String toString()
	{
		return "Operand:"+id+","+condition;
	}
	public void  print_Operand()
	{
		if(rectangle==null)
		{
			rectangle=new SDRectangle();
		}
		System.out.println("Operand: id="+id+",执行条件="+condition+",\tIsHasFragment="+isHasFragment
				+"\n坐标："+this.rectangle.toString());
		
		DisplayForm.mainFrame.getOutputinformation().geTextArea().append("Operand: id="+id+",执行条件="+condition+",\tIsHasFragment="+isHasFragment
				+"\n坐标："+this.rectangle.toString() + "\n");
		int length11 = DisplayForm.mainFrame.getOutputinformation().geTextArea().getText().length(); 
		DisplayForm.mainFrame.getOutputinformation().geTextArea().setCaretPosition(length11);
		
		for(Node node:nodes)
		{
			node.print_node();
		}
		//System.out.println("消息个数："+messages.size());
		if(messages!=null)
		{
			for(Iterator<Message> it=messages.iterator();it.hasNext();)
			{
				Message m=it.next();
				m.print_Message();
			}
		}

			if(fragments.size()>0)
			{
				
				for(Fragment childFragment:fragments)
				{
					childFragment.print_Fragment();
				}
			}
			if(ref!=null)
			{
				System.out.println("---------操作内引用片段："+ref.toString());
				
				DisplayForm.mainFrame.getOutputinformation().geTextArea().append("---------操作内引用片段："+ref.toString() + "\n");
				int length111 = DisplayForm.mainFrame.getOutputinformation().geTextArea().getText().length(); 
				DisplayForm.mainFrame.getOutputinformation().geTextArea().setCaretPosition(length111);
			}
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
	public ArrayList<String> getNodeIds() {
		return nodeIds;
	}
	public void setNodeIds(ArrayList<String> nodeIds) {
		this.nodeIds = nodeIds;
	}
	public ArrayList<Message> getMessages() {
		return messages;
	}
	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}
	public boolean isHasFragment() {
		return isHasFragment;
	}
	public void setHasFragment(boolean isHasFragment) {
		this.isHasFragment = isHasFragment;
	}
	public ArrayList<Fragment> getFragments() {
		return fragments;
	}
	public void setFragments(ArrayList<Fragment> fragments) {
		this.fragments = fragments;
	}
	public void addFragment(Fragment fragment) {
		this.fragments.add(fragment);
	}
	
	public ArrayList<String> getFragmentIDs() {
		return fragmentIDs;
	}
	public void setFragmentIDs(ArrayList<String> fragmentIDs) {
		this.fragmentIDs = fragmentIDs;
	}
	public SDRectangle getRectangle() {
		return rectangle;
	}
	public void setRectangle(SDRectangle rectangle) {
		this.rectangle = rectangle;
	}
	public REF getRef() {
		return ref;
	}
	public void setRef(REF ref) {
		this.ref = ref;
	}	
	public void addMessage(Message message)
	{
		this.messages.add(message);
	}
	
	
}
