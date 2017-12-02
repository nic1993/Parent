package cn.edu.hdu.lab.dao.uml;

import java.util.ArrayList;

import com.horstmann.violet.application.gui.DisplayForm;

public class SD {

	private String id;
	private String name;
	
	private ArrayList<LifeLine> lifeLines;
	private ArrayList<Node> nodes;
	
	private ArrayList<Message> messages;
	private ArrayList<Fragment> fragments;
	private double prob;
	private String postSD;//场景执行后置条件
	
	public SD(){}
	public String toString()
	{
		return "SDSet:"+id;
	}
	public void print_lifeLines()
	{
		
		for(LifeLine lifeLine:lifeLines)//foreach语句，如果没有实例，就不会执行此遍历语句 
		{
			lifeLine.print_LifeLine();
			
		}
	}
	public void print_nodes()
	{
		for(Node node:nodes)
		{
			node.print_node();
		}
	}
	public void print_messages()
	{
		for(Message message:messages)
		{
			message.print_Message();
		}
	}
	
	public void print_fragments()
	{
		for(Fragment fragment:fragments)
		{
			fragment.print_Fragment();
		}
	}
	
	public void print_SDSet()
	{
		System.out.println("---SDSet----\n[\nid="+id+", name="+name+"---probablity="+prob+",postSD="+postSD);
		DisplayForm.mainFrame.getOutputinformation().geTextArea().append("---SDSet----\n[\nid="+id+", name="+name+"---probablity="+prob+",postSD="+postSD + "\n");
		
		
		if(lifeLines!=null)
		{
			print_lifeLines();
		}
			
		if(nodes!=null)
			print_nodes();
		if(messages!=null)
			print_messages();
		if(fragments!=null)
			print_fragments();
		System.out.println(" ]");
		
		DisplayForm.mainFrame.getOutputinformation().geTextArea().append(" ]" + "\n");
		int length1 = DisplayForm.mainFrame.getOutputinformation().geTextArea().getText().length(); 
		DisplayForm.mainFrame.getOutputinformation().geTextArea().setCaretPosition(length1);
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
	public ArrayList<LifeLine> getLifeLines() {
		return lifeLines;
	}
	public void setLifeLines(ArrayList<LifeLine> lifeLines) {
		this.lifeLines = lifeLines;
	}
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
	public ArrayList<Message> getMessages() {
		return messages;
	}
	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}
	public ArrayList<Fragment> getFragments() {
		return fragments;
	}
	public void setFragments(ArrayList<Fragment> fragments) {
		this.fragments = fragments;
	}
	public double getProb() {
		return prob;
	}
	public void setProb(double prob) {
		this.prob = prob;
	}
	public String getPostSD() {
		return postSD;
	}
	public void setPostSD(String postSD) {
		this.postSD = postSD;
	}
	
}
