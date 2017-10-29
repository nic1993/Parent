package com.horstmann.violet.application.menu.util.UMLTransfrom;



import java.awt.Point;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class CreateUseCaseDiagramVioletXml {
	private  List<Edge> edgeList;
	private  List<Node> nodeList;
	private   Map<String, String > map =new HashMap<String, String>();
	private  int l;

	public  void create(readUseCaseXMLFromEA ra,String path) throws IOException {
		nodeList=ra.getNodeList();
		edgeList=ra.getEdgeList();
		Document document=createDocument();
		FileOutputStream fw = new FileOutputStream(path);
		OutputFormat xmlFormat=new OutputFormat();  
		xmlFormat.setEncoding("UTF-8");
//		xmlFormat.setNewlines(true);
//		xmlFormat.setIndent(true);
//		xmlFormat.setIndent("    ");
		XMLWriter xmlWriter=new XMLWriter(fw,xmlFormat);
		xmlWriter.write(document); 	
		xmlWriter.close();
		
//		FileWriter fw = new FileWriter(path);
//		OutputFormat format 
//        = OutputFormat.createPrettyPrint();
//		format.setEncoding("UTF-8");
//		format.setNewlines(true);
//		format.setIndent(true);
//		format.setIndent("    ");
//	      //�����������xml�ļ���XMLWriter����
//	      XMLWriter xmlWriter 
//	        = new XMLWriter(fw, format);
//	      xmlWriter.write(document); 
//	      xmlWriter.close();
	}

	
	
	private  Document createDocument() {
		
		String backgroundid="3";
		String borderid="4";
		String textid="5";
		int k =10;
		Document doc = DocumentHelper.createDocument();	
			Element root=doc.addElement("UseCaseDiagramGraph");
					root.addAttribute(" id","1");
					root.addText("");	
					
			createNode(backgroundid, borderid, textid, k, root);
			createEdge(root);
			return doc;		
		}

	/**
	 * 创建edge节点
	 * @param root
	 */
	private  void createEdge(Element root) {
		int k;//定义k来计算转换成平台xml时的id�??
		Element edges =root.addElement("edges").addAttribute("id", l+1+"");
		k=l+2;
		for(int j =0;j<edgeList.size();j++){
			Edge edge=edgeList.get(j);
			int bentid=6;  
				Element UseCaseRelationshipEdge=edges.addElement("UseCaseRelationshipEdge").addAttribute("id",k+"").addText("");
					UseCaseRelationshipEdge.addElement("start").addAttribute("class", XMLUtils.getNode(edge.getStarNodeid(),nodeList).getType()).addAttribute("reference", XMLUtils.getMapId(map, edge.getStarNodeid()));
					UseCaseRelationshipEdge.addElement("end").addAttribute("class", XMLUtils.getNode(edge.getEndNodeid(),nodeList).getType()).addAttribute("reference", XMLUtils.getMapId(map, edge.getEndNodeid()));
					
					UseCaseRelationshipEdge.addElement("startLocation").addAttribute("class", "Point2D.Double")
															.addAttribute("id", k+1+"")
															.addAttribute("x", (XMLUtils.getEdgePontionList(edge, nodeList).get(0).getX()+""))
															.addAttribute("y", (XMLUtils.getEdgePontionList(edge, nodeList).get(0).getY()+""));
					
					UseCaseRelationshipEdge.addElement("endLocation").addAttribute("class", "Point2D.Double")
														  .addAttribute("id",k+2+"")
														  .addAttribute("x", (XMLUtils.getEdgePontionList(edge, nodeList).get(1).getX()+""))
														  .addAttribute("y", (XMLUtils.getEdgePontionList(edge, nodeList).get(1).getY()+""));
					
					UseCaseRelationshipEdge.addElement("idE").addAttribute("id", k+3+"");
					if(edge.getType().equals("uml:Association")){
						UseCaseRelationshipEdge.addElement("lineStyle").addAttribute("id", bentid+"").addAttribute("name", "SOLID");
						UseCaseRelationshipEdge.addElement("startArrowHead").addAttribute("id", bentid+1+"").addAttribute("name", "NONE");
						UseCaseRelationshipEdge.addElement("endArrowHead").addAttribute("id", bentid+3+"").addAttribute("name", "NONE");
					  
					}else if(edge.getType().equals("uml:Dependency")){
						
						UseCaseRelationshipEdge.addElement("lineStyle").addAttribute("id", bentid+"").addAttribute("name", "DOTTED");
						UseCaseRelationshipEdge.addElement("startArrowHead").addAttribute("id", bentid+1+"").addAttribute("name", "NONE");
						UseCaseRelationshipEdge.addElement("endArrowHead").addAttribute("id", bentid+3+"").addAttribute("name", "V");
						UseCaseRelationshipEdge.addElement("bentStyle").addAttribute("id", bentid+2+"").addAttribute("name", "STRAIGHT");
					 
					}else if(edge.getType().equals("Inheritance")){
						UseCaseRelationshipEdge.addElement("lineStyle").addAttribute("id", bentid+"").addAttribute("name", "SOLID");
						UseCaseRelationshipEdge.addElement("startArrowHead").addAttribute("id", bentid+1+"").addAttribute("name", "NONE");
					}
					if(edge.getType().contains("EXTEND"))
					{
						UseCaseRelationshipEdge.addElement("lineStyle").addAttribute("id", bentid+"").addAttribute("name", "DOTTED");
						UseCaseRelationshipEdge.addElement("startArrowHead").addAttribute("id", bentid+1+"").addAttribute("name", "NONE");
						UseCaseRelationshipEdge.addElement("endArrowHead").addAttribute("id", bentid+2+"").addAttribute("name", "V");
						
					}
					if(edge.getType().contains("INCLUDE"))
					{
						UseCaseRelationshipEdge.addElement("lineStyle").addAttribute("id", bentid+"").addAttribute("name", "DOTTED");
						UseCaseRelationshipEdge.addElement("startArrowHead").addAttribute("id", bentid+1+"").addAttribute("name", "NONE");
						UseCaseRelationshipEdge.addElement("endArrowHead").addAttribute("id", bentid+2+"").addAttribute("name", "V");
					}
					UseCaseRelationshipEdge.addElement("bentStyle").addAttribute("id", k+4+"").addAttribute("name", "STRAIGHT");
					UseCaseRelationshipEdge.addElement("middleLabel").addText(edge.getName());
					UseCaseRelationshipEdge.addElement("startLabel").addText(edge.getType());
					k=k+4;

			}	
		}


	/**
	 * 创建node节点
	 * @param root
	 */
	private  void createNode(String backgroundid, String borderid,
			String textid, int k, Element root) {
		
		Element nodes=root.addElement("nodes");
			nodes.addAttribute("id", "2");
			nodes.addText("");
			
			for(int i=0 ;i<nodeList.size();i++){
				Node node=nodeList.get(i);
				if(node.getLocation() != null){
					Element ActorNode =nodes.addElement(node.getType()).addAttribute("id", k+"");
					map.put(node.getId(),k+"");
					
					ActorNode.addElement("children")
								.addAttribute("id", k+1+"");
					ActorNode.addElement("location")
								.addAttribute("class", "Point2D.Double")
								.addAttribute("id", k+2+"")
								.addAttribute("x", node.getLocation().getX()+"")
								.addAttribute("y",node.getLocation().getY()+"");
					ActorNode.addElement("idN")
								.addAttribute("id", k+3+"");
					    
						if(i==0){
						Element backcolor=ActorNode.addElement("backgroundColor").addAttribute("id", backgroundid).addText("");
							backcolor.addElement("red").addText("255");
							backcolor.addElement("green").addText("255");
							backcolor.addElement("blue").addText("255");
							backcolor.addElement("alpha").addText("255");
							
						Element bordercolor=ActorNode.addElement("borderColor").addAttribute("id", borderid).addText("");
							bordercolor.addElement("red").addText("191");
							bordercolor.addElement("green").addText("191");
							bordercolor.addElement("blue").addText("191");
							bordercolor.addElement("alpha").addText("255");
							
						Element textcolor=ActorNode.addElement("textColor").addAttribute("id", textid).addText("");
							textcolor.addElement("red").addText("51");
							textcolor.addElement("green").addText("51");
							textcolor.addElement("blue").addText("51");
							textcolor.addElement("alpha").addText("255");
						}else{
							Element backcolor=ActorNode.addElement("backgroundColor").addAttribute("reference", backgroundid);
							Element bordercolor=ActorNode.addElement("borderColor").addAttribute("reference", borderid);
							Element textcolor=ActorNode.addElement("textColor").addAttribute("reference", textid);
						}		
						Element name =ActorNode.addElement("name").addAttribute("id", k+4+"");
								name.addElement("text").addText(node.getName());//节点的name属�?	
								name.addElement("justification").addText("1");
								k=k+5;
					if(node.getType().equals("UseCaseNode")){
						Element useConstraint =ActorNode.addElement("useConstraint").addAttribute("id", k+5+"");	
						Element element = useConstraint.addElement("constraints").addAttribute("id", k+6+"");
						if(node.getConstraintName().size() != 0)
						{
							for(int s = 0;s < node.getConstraintName().size();s++)
							{
								Element element1 = element.addElement("com.horstmann.violet.product.diagram.abstracts.property.Usecaseconstraint").addAttribute("id", ++k + "");						
								element1.addElement("name").addText(node.getConstraintName().get(s));								
								element1.addElement("content").addText(node.getConstraintContent().get(s));
								element1.addElement("type").addText(node.getConstraintType().get(s));
							}
						}
						
						Element sceneConstraint =ActorNode.addElement("sceneConstraint").addAttribute("id", k+7+"");
						Element element2 = sceneConstraint.addElement("constraints").addAttribute("id", k+8+"");
						if(node.getSequenceName().size() == 0)
						{
							Element element3 = element2.addElement("com.horstmann.violet.product.diagram.abstracts.property.SequenceConstraint").addAttribute("id", ++k + "");
							Element element4 = sceneConstraint.addElement("sequenceName").addAttribute("id", ++k + "");
						}
						if(node.getSequenceName().size() != 0)
						{
							for(int s = 0; s < node.getSequenceName().size();s++)
							{
								Element element3 = element2.addElement("com.horstmann.violet.product.diagram.abstracts.property.SequenceConstraint").addAttribute("id", ++k + "");
								element3.addElement("sequenceName").addText(node.getSequenceName().get(s));
								element3.addElement("name").addText(node.getSequenceConstraintName().get(s));
								element3.addElement("content").addText(node.getSequenceContent().get(s));
								element3.addElement("type").addText(node.getSequenceType().get(s));
							}
							Element element4 = sceneConstraint.addElement("sequenceName").addAttribute("id", ++k + "");
							for(int s = 0; s < node.getSequenceName().size();s++)
							{
								element4.addElement("string").addText(node.getSequenceName().get(s));
							}
						}
						
					}				
				l=k;
				}
			}
				
	}
	
}
