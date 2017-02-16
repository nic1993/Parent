package cn.edu.hdu.lab.dao.uml;

import java.util.ArrayList;
import java.util.Iterator;

/*
 * 操作域
 * 每个组合片段下面有多条执行路径
 * 每条执行路径对应一个操作域（操作id,执行条件，执行路径上含有的结点和信息）
 */
public class Operand {
	private String id;
	private String condition;
	private ArrayList<Node> nodes;
	private ArrayList<String> nodeIds;
	private ArrayList<Message> messages;
	
	private boolean isHasFragment=false;
	private ArrayList<Fragment> fragments;
	
	public Operand(){}
	public String toString()
	{
		return "Operand:"+id+","+condition;
	}
	public void  print_Operand()
	{
		System.out.println("Operand:"+id+","+condition+",\tIsHasFragment="+isHasFragment);
		for(Node node:nodes)
		{
			node.print_node();
		}
		if(messages!=null)
		{
			for(Iterator<Message> it=messages.iterator();it.hasNext();)
			{
				Message m=it.next();
				m.print_Message();
			}
		}
//		if(isHasFragment==true)
//		{
			if(fragments!=null)
			{
				
				for(Fragment childFragment:fragments)
				{
					childFragment.print_Fragment();
				}
			}
//		}	
		
		
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
	
	
}
