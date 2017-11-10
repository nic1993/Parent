package com.horstmann.violet.application.gui.util.tanchao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
//默认xml的第一个state是开始节点
//我们先读状态(不管消息)，设置id
//再读消息的时候便于确定消息的开始和结尾的状态的id
public class TianWriteToVioletMarkov {
   SAXReader reader =new SAXReader();
   //状态的集合
   List<TanchaoMarkovNode> markovNodeInfo=new ArrayList<TanchaoMarkovNode>();
   //消息的集合
   List<TanchaoMarkovTransitionEdge> markovTransitionEdgeInfo=new ArrayList<TanchaoMarkovTransitionEdge>();
   //开始的状态
   TanchaoMarkovStartNode TanchaoMarkovStartNode=new TanchaoMarkovStartNode();
   int statesId=0;//用于遍历states集合的游标
   int i,j=0;//分别是状态的 list的集合的游标，消息list的游标
   //先固定坐标，后期再展开 (因为标签里面是String类型)
   String location_x="200.0";
   String location_y="200.0";
   
   /**
    * 读取天才生成的markov的信息，获取我们想要的信息;
    * @param path 读取文件的路径
    * @throws DocumentException
    */
   public void find(String path) throws DocumentException{ 
	   File file = new File(path);
	   Document dom=reader.read(file);
	   Element root= dom.getRootElement();
	   List<Element> states=root.elements("state");
	   int length=states.size();
	   //****先处理state的集合确定所有的state的唯一id****
       for(int i=0,j=0;i<length;i++){
    	   if(i==0){//第一个开始node单独处理
    		   TanchaoMarkovStartNode.setId(UUID.randomUUID().toString());
    		   TanchaoMarkovStartNode.setChildren_id(UUID.randomUUID().toString());
    		   TanchaoMarkovStartNode.setLocation_id(UUID.randomUUID().toString());
    		   TanchaoMarkovStartNode.setUnderLocation_id(UUID.randomUUID().toString());
    		   TanchaoMarkovStartNode.setName_id(UUID.randomUUID().toString());
    		   TanchaoMarkovStartNode.setName(states.get(0).element("name").getText());
    	   }
    	   else{//其他的node
    	   markovNodeInfo.add(new TanchaoMarkovNode());
    	   markovNodeInfo.get(j).setId(UUID.randomUUID().toString());
    	   markovNodeInfo.get(j).setChildren_id(UUID.randomUUID().toString());
    	   markovNodeInfo.get(j).setLocation_id(UUID.randomUUID().toString());
    	   markovNodeInfo.get(j).setChildren_id(UUID.randomUUID().toString());
    	   markovNodeInfo.get(j).setUnderLocation_id(UUID.randomUUID().toString());
    	   markovNodeInfo.get(j).setName(states.get(i).element("name").getText());
    	   j++;
    	   }
       }
	   //*******再处理消息********
	   //获得 开始的stateNode(处理初始的状态)
       Element startElement=states.get(statesId);
//       markovStartNode.setId(String.valueOf(markovNodeId++));
//	   markovStartNode.setName(startElement.element("name").getText());
	   //以该node出度的消息  出度的消息可能有多个
	   List<Element> arcs=startElement.elements("arc");
	   for(Element ele:arcs){
//		   List<Element> sonEles=ele.elements();
//		   for(Element e:sonEles){
//			   System.out.println(e.attributeValue("assignType"));
//		   }
		   		   
		   String arc_name=ele.element("name").getText();
		   String arcToNode=ele.element("to").getText();
		   String prob=ele.element("prob").getText();
		   
		   String conditions=ele.element("conditions").getText();
		   String owned=ele.element("owned").getText();
		   
//		   System.out.println(ele.element("assignType").getText());
		   String assignType="";
		   if(ele.element("assignType")!=null){
			   assignType=ele.element("assignType").getText();
		   }
		   
		   String assignValue="";
		   if(ele.element("assignValue")!=null){
			   assignValue=ele.element("assignValue").getText();
		   }
		   
//		   String assignType=ele.element("assignType").getText();
//		   String assignValue=ele.element("assignValue").getText();
		   
		   markovTransitionEdgeInfo.add(new TanchaoMarkovTransitionEdge());
		   markovTransitionEdgeInfo.get(j).setId(UUID.randomUUID().toString());
		   markovTransitionEdgeInfo.get(j).setName(arc_name);
		   markovTransitionEdgeInfo.get(j).setStart_reference(startElement.element("name").getText());
		   markovTransitionEdgeInfo.get(j).setProb(prob);
		   markovTransitionEdgeInfo.get(j).setEnd_reference(arcToNode);
		   markovTransitionEdgeInfo.get(j).setStartLocation_id(UUID.randomUUID().toString());
		   markovTransitionEdgeInfo.get(j).setEndLocation_id(UUID.randomUUID().toString());
		   markovTransitionEdgeInfo.get(j).setUnderLocation_id(UUID.randomUUID().toString());
		   
		   markovTransitionEdgeInfo.get(j).setConditions(conditions);
		   markovTransitionEdgeInfo.get(j).setOwned(owned);
		   markovTransitionEdgeInfo.get(j).setAssignType(assignType);
		   markovTransitionEdgeInfo.get(j).setAssignValue(assignValue);
		   j++;
	   }
	   statesId++;
	   
	   while(statesId<length){
		   //获得该状态
		   Element state=states.get(statesId);
		   String state_name=state.element("name").getText();
//		   markovNodeInfo.add(new MarkovNode());
//		   markovNodeInfo.get(i).setId(String.valueOf(markovNodeId++));
//		   markovNodeInfo.get(i).setName(state_name);
		   //获得该状态下的消息//可能有多个
           List<Element> arcs1=state.elements("arc");
           for(Element ele:arcs1){
        	   
		   String arcName=ele.element("name").getText();//消息name
		   String arcFromNode=state.element("name").getText();//消息开始node的name
		   String arcToNode1=ele.element("to").getText();//消息终点的name
		   
		   String conditions=ele.element("conditions").getText();
		   String owned=ele.element("owned").getText();
		   String assignType="";
		   if(ele.element("assignType")!=null){
			   assignType=ele.element("assignType").getText();
		   }
		   
		   String assignValue="";
		   if(ele.element("assignValue")!=null){
			   assignValue=ele.element("assignValue").getText();
		   }
		   String prob=ele.element("prob").getText();
		   
//		   String arccontent=getContent(ele);//消息上面的内容(在下面封装啦)
		   markovTransitionEdgeInfo.add(new TanchaoMarkovTransitionEdge());
		   markovTransitionEdgeInfo.get(j).setId(UUID.randomUUID().toString());
		   markovTransitionEdgeInfo.get(j).setName(arcName);
//		   markovTransitionEdgeInfo.get(j).setContent(arccontent);
		   markovTransitionEdgeInfo.get(j).setStart_reference(arcFromNode);
		   markovTransitionEdgeInfo.get(j).setEnd_reference(arcToNode1);
		   
		   markovTransitionEdgeInfo.get(j).setConditions(conditions);
		   markovTransitionEdgeInfo.get(j).setOwned(owned);
		   markovTransitionEdgeInfo.get(j).setAssignType(assignType);
		   markovTransitionEdgeInfo.get(j).setAssignValue(assignValue);
		   markovTransitionEdgeInfo.get(j).setProb(prob);;
		   j++;
           }
           i++;
           statesId++;
	   }
	   //----------------输出
//	   System.out.println("********"+markovStartNode.getName());
//	   System.out.println("$$$$$$$$$"+markovStartNode.getId());
//	   for(MarkovTransitionEdge edge:markovTransitionEdgeInfo){
//		   System.out.println(getId(edge.getStart_reference())+"------>"+getId(edge.getEnd_reference()));
//	   }
	   //----------------
   }
   /**
    * 获得当前标签下的所有标签的子内容(用于获得消息上的信息返回String)(封装)
    * @param state
    * @return
    */
   public static String getContent(Element state){
	   String content="";
	   List<Element> elements=state.elements();
	   for(Element ele:elements){
		   if("to".equals(ele.getName())){//当遍历到to的时候，终止
			   break;
		   }
//		   else if("stimulate".equals(ele.getName())){//当出现激励的时候单独处理
//			   //出现激励的时候构造String的类型是stimulate{paraName1:value1 paraName2:value2}
//			   content+=ele.getName();
//			   content+="{";
//			   List<Element> parameters=ele.elements();//获得parameter的element的集合
//			   for(Element eleChild:parameters){
//				   String name="paramName";
//				   content+=name;
//				   content+=":";
//				   String value=eleChild.element("paramName").getText();
//				   content+=value;
//				   content+=" ";
//				   
//				   String typeName="paramType";
//				   content+=typeName;
//				   content+=":";
//				   String typeValue=eleChild.element("paramType").getText();
//				   content+=typeValue;
//				   content+=" ";
//				   
//			   }
//			   String name=ele.getName();
//			   content+=name;
//			   content+="} ";
//		   }
		   else{//构造String的类型是 name1:value1 name2:value2
			   String name=ele.getName();
			   content+=name;
			   content+=":";
			   String value=ele.getText();
			   content+=value;
			   content+=" ";
		   }
		   
	   }
	   return content;
   }
     //将前面获得的xml的信息，转化为xml
	public void writeVioletMarkov(String toFileName){
		int idcount=0;//设置xml里面的id
		Document doc =DocumentHelper.createDocument();
		Element MarkovGraph=doc.addElement("MarkovGraph");
		MarkovGraph.addAttribute("id", String.valueOf(idcount++));//1
		//*************************nodes*****************************
		Element nodes=MarkovGraph.addElement("nodes");
		nodes.addAttribute("id", String.valueOf(idcount++));//2
		//处理startNode
		Element markovStartNode1=nodes.addElement("MarkovStartNode");
		markovStartNode1.addAttribute("id", TanchaoMarkovStartNode.getId());
		Element children=markovStartNode1.addElement("children");
		children.addAttribute("id", TanchaoMarkovStartNode.getChildren_id());
		Element location=markovStartNode1.addElement("location");
		location.addAttribute("class", "Point2D.Double").addAttribute("id", TanchaoMarkovStartNode.getChildren_id())
		.addAttribute("x", location_x).addAttribute("y", location_y);
		Element id=markovStartNode1.addElement("idN");
		id.addAttribute("id", TanchaoMarkovStartNode.getUnderLocation_id());
		//添加颜色(3个颜色)
		setColor(markovStartNode1);
		//添加name
		Element name=markovStartNode1.addElement("name");
		name.addAttribute("id", String.valueOf(idcount++));
		name.setText(TanchaoMarkovStartNode.getName());
		
		//处理剩下的node
		for(TanchaoMarkovNode node:markovNodeInfo){
			Element MarkovNode=nodes.addElement("MarkovNode");
			MarkovNode.addAttribute("id", node.getId());
			Element children1=MarkovNode.addElement("children");
			children1.addAttribute("id", node.getChildren_id());
			Element location1=MarkovNode.addElement("location");
      		location1.addAttribute("class", "Point2D.Double").addAttribute("id", node.getLocation_id())
      		    .addAttribute("x", location_x).addAttribute("y", location_y);
      		Element id1=MarkovNode.addElement("idN");
      		id1.addAttribute("id", node.getUnderLocation_id());
      		setColor(MarkovNode);
      		Element name1=MarkovNode.addElement("name");
      		name1.addAttribute("id", node.getName_id());
      		name1.setText(node.getName());
//      		name1.setText(node.getName());
		}
		//*********************edges***************************
		Element edges=MarkovGraph.addElement("edges");
		edges.addAttribute("id", String.valueOf(idcount++));
		for(TanchaoMarkovTransitionEdge edge:markovTransitionEdgeInfo){
			
			Element MarkovTransitionEdge=edges.addElement("MarkovTransitionEdge");
			MarkovTransitionEdge.addAttribute("id", edge.getId());
			Element start=MarkovTransitionEdge.addElement("start");
			start.addAttribute("class", "MarkovNode").addAttribute("reference", getId(edge.getStart_reference()));//添加开始node的id
			Element end=MarkovTransitionEdge.addElement("end");
			end.addAttribute("class", "MarkovNode").addAttribute("reference", getId(edge.getEnd_reference()));//添加结束node的id
			Element startLocation=MarkovTransitionEdge.addElement("startLocation");
            startLocation.addAttribute("class", "Point2D.Double").addAttribute("id", String.valueOf(idcount++))
            .addAttribute("x", location_x).addAttribute("y", location_y);
            Element endLocation=MarkovTransitionEdge.addElement("endLocation");
            endLocation.addAttribute("class", "Point2D.Double").addAttribute("id", String.valueOf(idcount++))
            .addAttribute("x", location_x).addAttribute("y", location_y);
            Element edgeId=MarkovTransitionEdge.addElement("idE");
            edgeId.addAttribute("id", edge.getUnderLocation_id());
//            Element probability=MarkovTransitionEdge.addElement("probability");
//            probability.setText(edge.getContent());
            Element conditions=MarkovTransitionEdge.addElement("conditions");
            conditions.setText(edge.getConditions());
            Element owned=MarkovTransitionEdge.addElement("owned");
            owned.setText(edge.getOwned());
            Element assignValue=MarkovTransitionEdge.addElement("assignValue");
            assignValue.setText(edge.getAssignValue());
            Element assignType=MarkovTransitionEdge.addElement("assignType");
            assignType.setText(edge.getAssignType());
            Element pro=MarkovTransitionEdge.addElement("pro");
            pro.setText(edge.getProb());
		}
		outputXML(doc,toFileName);
	}
	
	/**
	 * 
	 * @param name 根据传入的node 的name获得id
	 * @return String 类型
	 */
	public String getId(String name){
//		List<Object> list=new ArrayList<Object>();
//		list.add(markovStartNode);
//		list.addAll(1, markovNodeInfo);
//		for(Object o:list){
//			if(name.equals(((MarkovNode)o).getName())){
//				return .getId();//返回id(以String的类型返回)
//			}
//		}
//		return "";//目前返回" "，后期修改
		if(name.equals(TanchaoMarkovStartNode.getName()))
			return TanchaoMarkovStartNode.getId();
		else{
			for(TanchaoMarkovNode node:markovNodeInfo){
				if(name.equals(node.getName()))
					return node.getId();
			}
		}
		return " ";
	}
	/**
	 * 设置颜色(封装一下)
	 * @param node表示给该标签添加子标签(三种颜色的子标签)
	 * @param id 用于表示标签的id(目的，让标签id递增)
	 * @return 新的id用于下面用(保证id不重复)
	 */
	public void setColor(Element node){
		//1 backgroundColor
		Element backgroundColor=node.addElement("backgroundColor");
		backgroundColor.addAttribute("id", UUID.randomUUID().toString());
		Element red=backgroundColor.addElement("red");
		red.addText("255");
		Element green=backgroundColor.addElement("green");
		green.addText("255");
		Element blue=backgroundColor.addElement("blue");
		blue.addText("255");
		Element alpha=backgroundColor.addElement("alpha");
		alpha.addText("255");
		//2 borderColor
		Element borderColor=node.addElement("borderColor");
		borderColor.addAttribute("id", UUID.randomUUID().toString());
		Element red1 =borderColor.addElement("red");
		red1.addText("191");
		Element green1 =borderColor.addElement("green");
		green1.addText("191");
		Element blue1=borderColor.addElement("blue");
		blue1.addText("191");
		Element alpha1 =borderColor.addElement("alpha");
		alpha1.addText("255");
		
		//3 textColor
		Element textColor=node.addElement("textColor");
		textColor.addAttribute("id", UUID.randomUUID().toString());
		Element red2 =textColor.addElement("red");
		red2.addText("51");
		Element green2 =textColor.addElement("green");
		green2.addText("51");
		Element blue2=textColor.addElement("blue");
		blue2.addText("51");
		Element alpha2 =textColor.addElement("alpha");
		alpha2.addText("255");
		
	}
	
	/**
	 * 输出xml文件
	 * @param doc
	 * @param fileName 输出的文件名
	 */
	public void outputXML(Document doc,String fileName){
		try {
			FileWriter fw=new FileWriter(fileName);
			OutputFormat format=new OutputFormat("  ",true); 
			XMLWriter xmlWriter=new XMLWriter(fw,format);
			xmlWriter.write(doc);
			xmlWriter.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
   
	
	public static void main(String[] args) {
		TianWriteToVioletMarkov t=new TianWriteToVioletMarkov();
		try {
			t.find("D:\\ModelDriverProjectFile\\NoTimeMarkov\\EADemo2Seq_MarkovChainModel1.xml");
//			t.find("C:\\Users\\Admin\\Desktop\\markov\\Seq_MarkovChainModel2.xml");
			t.writeVioletMarkov("D:\\ModelDriverProjectFile\\NoTimeMarkov\\EADemo2Seq_MarkovChainModel1.markov.violet.xml");
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
