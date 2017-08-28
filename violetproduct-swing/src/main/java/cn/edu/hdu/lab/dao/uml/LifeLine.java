package cn.edu.hdu.lab.dao.uml;
import java.util.ArrayList;
public class LifeLine {
	/* 
	 * 生命线的id 和 name，以及本身包含的位点结点；
	 * 
	 */
	private String id;
	private String name;
	private ArrayList<Node> nodes=new ArrayList<Node>();
	
	public LifeLine() {}
	
	public LifeLine(String id) 
	{
		this.id = id;
	}
	
	public LifeLine(String id, String name) 
	{
		set(id, name);
	}
	public void set(String id, String name) 
	{
		this.id = id;
		this.name = name;
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
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
	public void addNode(Node node) {
		this.nodes.add(node);
	}
	
	public String toString() 
	{
		return "LifeLine: "+ id + "\t" + name;
	}
	
	//以下两种方法有待重新整合
//	public void print_nodes()
//	{
//		for (Node n : this.nodes)
//			n.print_node();
//		
//	}
	
	public void print_LifeLine() {
		System.out.println("Lifeline: name=" +this.name +"\tID="+this.id+"\t生命线下位点没有输出"); 
		//print_nodes();
	}
	
}
