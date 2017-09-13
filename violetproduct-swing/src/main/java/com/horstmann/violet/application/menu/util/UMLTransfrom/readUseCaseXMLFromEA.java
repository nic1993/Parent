package com.horstmann.violet.application.menu.util.UMLTransfrom;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.horstmann.violet.application.menu.xiaole.SequenceTransfrom.EADiagram;
import com.horstmann.violet.framework.file.IFile;

public class readUseCaseXMLFromEA{
    private List<String> elementID;
	private String packagedID;
	public readUseCaseXMLFromEA(String url,IFile file,EADiagram eaDiagram){
		this.elementID = eaDiagram.getElementid();
		this.packagedID = eaDiagram.getID();
		//Administrator
		getInformationFormXML(url);
	}

	private  void getInformationFormXML(String url) {
		Document document =XMLUtils.load(url);
		Element root=document.getRootElement();
		Element Model=root.element("Model");
		List<Element> packagedElement = null;
		if(Model.element("packagedElement").attributeValue("id").equals(packagedID))
		{
			packagedElement=Model.element("packagedElement").elements();
		}
		else {
			List<Element> packagedElements = Model.element("packagedElement").elements("packagedElement");
			for(Element packaged : packagedElements)
			{
				if(packaged.attributeValue("id").equals(packagedID))
					packagedElement=packaged.elements("packagedElement");
			}
		}
		for(Element element:packagedElement){
			String type=element.attributeValue("type");
			if(type.toString().equals("uml:Actor")||type.toString().equals("uml:UseCase")){
				Node an=new Node();
				if(type.toString().equals("uml:Actor")){
					an.setType("ActorNode");
				}else{
					an.setType("UseCaseNode");
				}
				an.setId(element.attributeValue("id"));
				an.setName(element.attributeValue("name"));
				nodeList.add(an);
			}else if(type.toString().equals("uml:Association")){
				Edge edge=new Edge();
				edge.setType(type);
				edge.setId(element.attributeValue("id"));
				if(element.attributeValue("name") != null)
				edge.setName(element.attributeValue("name")); 
				else {
					edge.setName(element.attributeValue("type"));
				}
				edgeList.add(edge);	
			}
		}
		
		Element extension= root.element("Extension");
		List<Element> connectors= extension.element("connectors").elements();
		for(Element conn:connectors){
			boolean flag=SelectEdge(conn.attributeValue("idref"),edgeList);
			if(flag){
				Edge edge=new Edge();
				edge.setId(conn.attributeValue("idref"));
				Element labels = conn.element("labels");

				edge.setName(labels.attributeValue("mb")); //ºóÆÚÐÞ¸Ä
				edge.setType(labels.attributeValue("mb"));
				edgeList.add(edge);
			}	
			
		}
		
		List<Element> elements =extension.element("elements").elements();
		List<Element> sequenceDiagrams =extension.element("diagrams").elements();
			for(Element element:elements){
				if(element.element("links")!=null){
					List<Element> links=element.element("links").elements();
					if(links!=null){
						for(Element link:links){
							String id=link.attributeValue("id");
							 for(Edge l:edgeList){
								 if(l.getId().trim().equals(id)){
									 l.setStarNodeid(link.attributeValue("start"));
									 l.setStarNodeType( getNodeType(link.attributeValue("start"), nodeList));
									 l.setEndNodeid(link.attributeValue("end"));
									 l.setEndNodeType(getNodeType(link.attributeValue("end"), nodeList));
								 }
							 }
						}
					}
				}
				if(element.attributeValue("type").toString().equals("uml:UseCase"))
				{
					for(Node node : nodeList)
					{
						if(node.getId().equals(element.attributeValue("idref")))
						{
							if(element.element("constraints") != null){
								List<Element> constraints = element.element("constraints").elements();
								for(Element element1 : constraints)
								{
									node.getConstraintName().add(element1.attributeValue("name").toString());
									node.getConstraintType().add(element1.attributeValue("type").toString());
									node.getConstraintContent().add(element1.attributeValue("description").toString());
								}
							}
						}
					}
				}
				if(element.attributeValue("type").toString().equals("uml:Interaction"))
				{
					String id = element.attributeValue("idref");
					for(Node node : nodeList)
					{
						if(node.getId().equals(element.element("model").attributeValue("owner")))
						{
							if(element.element("constraints") != null){	
								List<Element> constraints = element.element("constraints").elements();
								for(Element element1 : constraints)
								{
									node.getSequenceConstraintName().add(element1.attributeValue("name").toString());
									node.getSequenceType().add(element1.attributeValue("type").toString());
									node.getSequenceContent().add(element1.attributeValue("description").toString());
									for(Element element2 : sequenceDiagrams)
									{
										if(element2.element("model").attributeValue("parent") != null)
										{
											
											if(element2.element("model").attributeValue("parent").equals(id))
											{
												node.getSequenceName().add(element2.element("properties").attributeValue("name"));
											}
										}
									}
								}
							}
						}
					}
				}
			}
			
		List<Element> diagrams =extension.element("diagrams").element("diagram").element("elements").elements();//ÎªÔªï¿½Ø½Úµï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½×¼ï¿½ï¿½
			for(Element diagram:diagrams){
				for(Node node:nodeList){
					String id=diagram.attributeValue("subject");
					if(id!=null&&node.getId().equals(id)){
						String str=diagram.attributeValue("geometry");
						String[] strs=str.split(";");
//						
						int x =Integer.parseInt(XMLUtils.getIndex(strs[0]));
						int y =Integer.parseInt(XMLUtils.getIndex(strs[1]));
						Point p =new Point(x + 20,y);
						node.setLeftLocation(p);
						int x2=Integer.parseInt(XMLUtils.getIndex(strs[2]));
						int y2=Integer.parseInt(XMLUtils.getIndex(strs[3]));
						Point p2=new Point(x2 + 20,y2);
						node.setRightLocation(p2);
					}
				}	
			}
	}
	
	
	
	private  boolean SelectEdge(String id,List<Edge> list){
		for(Edge edge:list){
			String uid=edge.getId();
			if(id.equals(uid)){
				return false;
			}
		}
		return true;
	}
	
	private  String getNodeType(String id,List<Node> list){
		for(Node user:list){
			String uid=user.getId();
			if(id.equals(uid)){
				return user.getType().toString();
			}
		}
		return null;
	}
	

	public  List<Node> getNodeList() {
		return nodeList;
	}
	
	public  List<Edge> getEdgeList() {
		return edgeList;
	} 
	private   List<Node> nodeList=new ArrayList<Node>();
	private  List<Edge> edgeList= new ArrayList<Edge>();

}
