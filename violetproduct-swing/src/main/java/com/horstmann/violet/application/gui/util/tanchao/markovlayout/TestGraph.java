package com.horstmann.violet.application.gui.util.tanchao.markovlayout;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.eclipse.draw2d.graph.DirectedGraph;
import org.eclipse.draw2d.graph.DirectedGraphLayout;
import org.eclipse.draw2d.graph.Edge;
import org.eclipse.draw2d.graph.Node;
import org.jgraph.JGraph;
import org.jgraph.graph.ConnectionSet;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphModel;

import com.realpersist.gef.layout.NodeJoiningDirectedGraphLayout;

public class TestGraph {
   public Map gefNodeMap=null;//建立gef图，初始化
   public Map graphNodeMap=null;//建立graph图，初始化
   public List edgeList=null;//边初始化
   DirectedGraph directedGraph=null;
   JGraph graph=null;
   String filename = "";
   public TestGraph(String name){
	   
	   this.filename=name;
	   
   }
   
   public int[] init(){
	   graphInit();
	   return (paintGraph());
   }
   public int[] paintGraph(){
	   int EdgePosition[]=new int[1000];
	   ReadXml read=new ReadXml(filename);
	   int i=0,I,J;
	   String[] vertexID=read.getIdValue(filename);

	   int vertexNum=read.getNodeNum(filename);

	   int edgeNum=read.getEdgeNum(filename);

	   List<EdgeBean> edgeBeanList=new ArrayList<EdgeBean>();
	   List<NodeBean> list=new ArrayList<NodeBean>();
	   List<EdgeBean> List=new ArrayList<EdgeBean>();
	   int A[][]=read.find(filename);
	   int B[]={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
			   ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
			   ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
			   ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	   int flag=0;
	   int Flag=0;
	   while(i<vertexNum){
		   list.add(new NodeBean(vertexID[i]));
		   i++;
	   }
//	   System.out.println("list---"+list.size());
//	   //888888
//	   int num=0; int c=0,r=0;
//	   for(I=0;I<vertexNum;I++){
//		   for(J=0;J<vertexNum;J++){
////			   System.out.print(A[I][J]+" ");
//			   if(A[I][J]==1&&A[I][J]!=A[J][I]&&J!=I)
//			   {
//     			   System.out.print("  i  "+I+"----"+"j  "+J+"  ");
//     			   System.out.println("list--i"+list.get(I)+"  list--j"+list.get(J)+" ");
//			   }    
////				   num++;
//			   
//		   }
//		   System.out.println();
//	   }
//	   System.out.println("num----*"+num);
	   //888888
	   for(I=0;I<vertexNum;I++){
		   for(J=0;J<vertexNum;J++){
			   if(A[I][J]==1&&A[I][J]!=A[J][I]&&J!=I){//找到存在的边，但是不是对角线上的边
//				   System.out.println("list--I  "+list.get(I)+"list--J "+list.get(J));
				   List.add(new EdgeBean(list.get(I),list.get(J),new Long(100)));
			   }
			   else if(A[I][J]==1&&A[I][J]==A[J][I]&&J!=I&&B[I]!=1){
				   //存在回路的边用B[]记录一下 用flag记录一下回路边的数目
//				   System.out.println("aaa----");
				   List.add(new EdgeBean(list.get(I), list.get(J), new Long(100)));
			       B[I]=B[J]=1;
			       flag++;
			   }
			   else if(A[I][J]==1&&I==J){//对角线的边
				   Flag++;
			   }
		    }
		   }
//	          for(int k=0;k<B.length;k++){
//	        	  System.out.print(B[k]+"  ");
//	          }
//	           System.out.println();
//			   System.out.println("List"+List.size()+"  flag--"+flag+"  Flag"+Flag);
			   for(int k=0;k<edgeNum-flag-Flag-1;k++){
				   if(List.size()!=0){
					   List.get(k);
					   edgeBeanList.add(List.get(k));
				   }
//				   System.err.println("%%%%%%%"+List.size());
//				   System.out.println("******"+(edgeNum-flag-Flag));
			   }
			   gefNodeMap=new HashMap();
			   graphNodeMap=new HashMap();
			   edgeList=new ArrayList();
			   directedGraph=new DirectedGraph();
			   GraphModel model=new DefaultGraphModel();
			   graph.setModel(model);
			   Map attributes=new Hashtable();
			   Map edgeAttrib=new Hashtable();
			   GraphConstants.setLineEnd(edgeAttrib,GraphConstants.ARROW_CLASSIC);
			   GraphConstants.setEndFill(edgeAttrib, true);
			   graph.setJumpToDefaultPort(true);
			   if(edgeBeanList==null||edgeBeanList.size()==0){
				   graph.repaint();
				   return null;
			   }
			   Iterator edgeBeanIt=edgeBeanList.iterator();
			   while(edgeBeanIt.hasNext()){
				   EdgeBean edgeBean=(EdgeBean)edgeBeanIt.next();
				   NodeBean sourceAction=edgeBean.getSourceNodeBean();
				   NodeBean targetAction=edgeBean.getTargetNodeBean();
				   long ageLong=edgeBean.getAgeLong();
				   String edgeString="("+ageLong+")";
				   addEdge(sourceAction,targetAction,100,edgeString);
			   }
			   //自动布局 首先用DirectedGraphLayout 如果出现异常(图不是连通的) 采用NodeJoiningDirectedGraphLayout
			   //后者可以对非连通图进行布局坐标计算 但是效果不如前者好，所以选前者，当前这不可以处理时采用后者
			   try{
				   
				   new DirectedGraphLayout().visit(directedGraph);
			   }catch(Exception e1){
				   new NodeJoiningDirectedGraphLayout().visit(directedGraph);
			   }
			   int self_x=50;
			   int self_y=50;
			   int base_y=50;
			   if(graphNodeMap!=null&&gefNodeMap!=null&&graphNodeMap.size()>gefNodeMap.size()){
				   base_y=self_y+GraphProp.NODE_HEIGHT;
			   }
			   //向图中添加node
			   Collection nodeCollection=graphNodeMap.values();
			   if(nodeCollection!=null){
				   Iterator nodeIterator=nodeCollection.iterator();
				   if(nodeIterator!=null){
					   while(nodeIterator.hasNext()){
						   DefaultGraphCell node=(DefaultGraphCell)nodeIterator.next();
						   NodeBean userObject=(NodeBean) node.getUserObject();
						   if(userObject==null){
							   continue;
						   }
						   Node gefNode=(Node)gefNodeMap.get(userObject);
						   if(gefNode==null){
							   //这是因为当一个节点有一个指向自身的边的时候，我们再gefGraph中并没有计算这条边(gefGraph不能计算包含指向自己变的布局)
							   //所以当在所要画的图中该节点只有一条指向自身的边的时候，我们在gefNodeMap中找不到相应的节点
							   //这个时候，我们手工的给出该节点的x，y坐标
							   gefNode=new Node();
							   gefNode.x=self_x;
							   gefNode.y=self_y-base_y;
							   self_x+=(10+GraphProp.NODE_WIDTH);
						   }
						   for(int h=0;h<vertexNum;h++){
							   if(list.get(h).equals(userObject)){
								   EdgePosition[2*h]=gefNode.x;
								   EdgePosition[2*h+1]=gefNode.y;
							   }
						   }
						   Map nodeAttrib=new Hashtable();
						   GraphConstants.setBorderColor(nodeAttrib, Color.black);
						   Rectangle2D Bounds =new Rectangle2D.Double(gefNode.x,gefNode.y+base_y,GraphProp.NODE_WIDTH,GraphProp.NODE_HEIGHT);
					       GraphConstants.setBounds(nodeAttrib, Bounds);
					       attributes.put(node, nodeAttrib);
					   }
				   }
			   }
			   //向图中添加边
			   if(edgeList==null){
				   return null;
			   }
			   for(int i1=0;i1<edgeList.size();i1++){
				   UnionEdge unionEdge=(UnionEdge)edgeList.get(i1);
				   if(unionEdge==null){
					   continue;
				   }
				   ConnectionSet cs=new ConnectionSet(unionEdge.getEdge(),unionEdge.getSourceNode().getChildAt(0),unionEdge.getTargetNode().getChildAt(0));
			       Object[] cells=new Object[]{
			    	  unionEdge.getEdge(),
			    	  unionEdge.getSourceNode(),
			    	  unionEdge.getTargetNode()
			       };
			       attributes.put(unionEdge.getEdge(), edgeAttrib);
			       model.insert(cells, attributes, cs, null, null);
			   }
	   return EdgePosition;
   }
   
   private void addEdge(NodeBean source,NodeBean target,int weight,String edgeString){
	   if(source==null||target==null){
		   return ;
	   }
	   if(gefNodeMap==null){
		   gefNodeMap=new HashMap();
	   }
	   if(graphNodeMap==null){
		   graphNodeMap=new HashMap();
	   }
	   if (edgeList == null) {
			edgeList = new ArrayList();
	   }
	   if (directedGraph == null) {
			directedGraph = new DirectedGraph();
		}
	   //建立GEF的node edge 将来用于计算graph node的layout
	   addEdgeGef(source, target,weight,edgeString);
	   
	   //建立真正要用的Graph的node edge
	   DefaultGraphCell sourceNode=null;
	   DefaultGraphCell targetNode=null;
	   sourceNode=(DefaultGraphCell)graphNodeMap.get(source);
	   if(sourceNode==null){
		   sourceNode=new DefaultGraphCell(source);
		   sourceNode.addPort();
		   graphNodeMap.put(source, sourceNode);
	   }
	   targetNode=(DefaultGraphCell)graphNodeMap.get(target);
	   if(targetNode==null){
		   targetNode=new DefaultGraphCell(target);
		   targetNode.addPort();
		   graphNodeMap.put(target, targetNode);
	   }
	   DefaultEdge edge=new DefaultEdge(edgeString);
	   UnionEdge unionEdge=new UnionEdge();
	   unionEdge.setEdge(edge);
	   unionEdge.setSourceNode(sourceNode);
	   unionEdge.setTargetNode(targetNode);
	   edgeList.add(unionEdge);
   }
   
   
   private void addEdgeGef(NodeBean source,NodeBean target,int weight,String edgeString){
	   if(source.equals(target)){//如果开始的点==终止点
		   return;
	   }
	   //建立GEF的Node Edge将来用来计算graph node的layout
	   Node gefSourceNode=null;
	   Node gefTargetNode=null;
	   gefSourceNode=(Node)gefNodeMap.get(source);
	   if(gefSourceNode==null){
		   gefSourceNode=new Node();
		   gefSourceNode.width=GraphProp.NODE_WIDTH;
		   gefSourceNode.height=GraphProp.NODE_WIDTH;
		   directedGraph.nodes.add(gefSourceNode);
		   gefNodeMap.put(source, gefSourceNode);
	   }
	   gefTargetNode=(Node)gefNodeMap.get(target);
	   if(gefTargetNode==null){
		   gefTargetNode=new Node();
		   gefTargetNode.width=GraphProp.NODE_WIDTH;
		   gefTargetNode.height=GraphProp.NODE_WIDTH;
		   directedGraph.nodes.add(gefTargetNode);
		   gefNodeMap.put(target, gefTargetNode);
	   }
	   
	   Edge gefEdge1=null;
	   gefEdge1=new Edge(gefSourceNode, gefTargetNode);
	   gefEdge1.weight=weight;
	   directedGraph.edges.add(gefEdge1);
   }
   private void graphInit(){
	   GraphModel model=new DefaultGraphModel();
	   graph=new JGraph(model);
	   graph.setSelectionEnabled(true);
	   //显示applet
//	   JScrollPane scroll=new JScrollPane(graph);
//	   this.getContentPane().add(scroll);
//	   this.setSize(new Dimension(800,800));
	   
   }
   
   
}
