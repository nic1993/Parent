package com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.horstmann.violet.application.menu.util.zhangjian.Database.AbstractState;
import com.horstmann.violet.application.menu.util.zhangjian.Database.AbstractTransition;


/**
 * 创建自动机的XML文件
 * @author ZhangJian
 *
 */
public class CreateAbstractUppaalXML {
	private  List<AbstractState> abNodeList;
	private  List<AbstractTransition> abTransitionList;
	//为本类中属性赋值
	public CreateAbstractUppaalXML(List<AbstractState> abNodeList, List<AbstractTransition> abTransitionList) {
		this.abNodeList = abNodeList;
		this.abTransitionList = abTransitionList;
	}
	private  Map<String, String > map =new HashMap<String, String>();
	private int l;
	
	
	private Map <String,String> locationmap=new HashMap<String, String>();
	private Map <String,String> transitionmap=new HashMap<String, String>();
	private String x;
	private String y;
	private int index;
	private String xy;

	public  void create(String path) throws IOException, DocumentException {
		
		getLayoutUppaalInfo();
		
		File f =new File(path);
		if(!f.exists()){
			f.createNewFile();
		}
		Document document=createDocument();
		OutputFormat xmlFormat=new OutputFormat();  
		xmlFormat.setEncoding("gbk");
		xmlFormat.setNewlines(true);
		xmlFormat.setIndent(true);
		xmlFormat.setIndent("    ");
		XMLWriter xmlWriter=new XMLWriter(new FileWriter(path),xmlFormat);
		xmlWriter.write(document); 	
		xmlWriter.close();
	}
	
	private void getLayoutUppaalInfo() throws DocumentException{
		SAXReader reader = new SAXReader();
		Document dom=reader.read("stabilize_run.xml");
		Element root =dom.getRootElement();
		Element template=root.element("template");
		List<Element>locations=template.elements("location");
		List<Element>transitions=template.elements("transition");
		for(Element element:locations){
//			System.out.println(element.attributeValue("id")+"++"+element.attributeValue("x")+"++"+element.attributeValue("y"));
			locationmap.put(element.attributeValue("id"), element.attributeValue("x")+"-"+element.attributeValue("y"));
		}
		for(Element element:transitions){
//			System.out.println(element.attributeValue("id").split("tran_id")[1]);
//			System.out.println(element.element("source").attributeValue("ref")+"---"+element.element("target").attributeValue("ref")+"-"+element.element("label").attributeValue("x")+"-"+element.element("label").attributeValue("y"));
			transitionmap.put(element.attributeValue("id").split("tran_id")[1], element.element("label").attributeValue("x")+"-"+element.element("label").attributeValue("y"));
		}
	}
	
	
	private  Document createDocument() {
		String backgroundid="3";
		String borderid="4";
		String textid="5";
		int k =9;
		Document document=DocumentHelper.createDocument();	
			Element root=document.addElement("UppaalGraph");
					root.addAttribute(" id","1");
					root.addText("");		
			createState(backgroundid, borderid, textid, k, root);
			createTransition(root);					
			return document;		
		}

	/**
	 * 创建时间自动机中的消息迁移节点
	 * @param root
	 * @throws UnsupportedEncodingException 
	 */
	private  void createTransition(Element root) {
		int k;
		Element edges =root.addElement("edges").addAttribute("id", l+1+"");
		k=l+2;
		for(int j =0;j<abTransitionList.size();j++){
			AbstractTransition edge=abTransitionList.get(j);
			
			System.out.println("*************************");
			xy=transitionmap.get(edge.getSource().concat(edge.getTarget()));
			index=xy.indexOf('-');
			x=xy.substring(0, index);
			y=xy.substring(index+1);
			System.out.println(edge.getSource().concat(edge.getTarget()));
			System.out.println(x+"---"+y);
			System.out.println("*************************");
			
			int bentid=6;  
				Element TransitionEdge=edges.addElement("TransitionEdge").addAttribute("id",k+"").addText("");
					String sourceID =edge.getSourceID();
					String targetID =edge.getTargetID();
//					TransitionEdge.addElement("start").addAttribute("class", XMLUtils.getState(sourceID,abNodeList).getType()).addAttribute("reference", XMLUtils.getMapId(map, sourceID));
//					TransitionEdge.addElement("end").addAttribute("class", XMLUtils.getState(targetID,abNodeList).getType()).addAttribute("reference", XMLUtils.getMapId(map, targetID));
					
					TransitionEdge.addElement("start").addAttribute("class","CircularNode"  ).addAttribute("reference", XMLUtils.getMapId(map, sourceID));
					TransitionEdge.addElement("end").addAttribute("class", "CircularNode" ).addAttribute("reference", XMLUtils.getMapId(map, targetID));
					
					
					TransitionEdge.addElement("startLocation").addAttribute("class", "Point2D.Double")
															.addAttribute("id", k+1+"")
															.addAttribute("x",x)
															.addAttribute("y", y);
					
					TransitionEdge.addElement("endLocation").addAttribute("class", "Point2D.Double")
														  .addAttribute("id",k+2+"")
														  .addAttribute("x", x)
														  .addAttribute("y", y);
					TransitionEdge.addElement("id").addAttribute("id", k+3+"");
						TransitionEdge.addElement("labelText").addText("type:"+edge.getType()//new String(edge.getType().getBytes(),"utf-8")
								                                        +" source："+edge.getSource()//new String(edge.getSource().getBytes(),"utf-8")
								                                        +" target："+edge.getTarget()//new String(edge.getTarget().getBytes(),"utf-8")
								                                        +" ResetClockSet："+edge.getResetClockSet()//new String(edge.getResetClockSet().getBytes(),"utf-8")
								                                        +" DBM："+edge.getConstraintDBM());//new String(edge.getConstraintDBM().getBytes(),"utf-8"));
					k=k+4;
			}	
		}
	/**
	 * 创建时间自动机中的状态节点
	 * @param backgroundid
	 * @param borderid
	 * @param textid
	 * @param k
	 * @param root
	 */
	private  void createState(String backgroundid, String borderid,
			String textid, int k, Element root) {
		
		Element nodes=root.addElement("nodes");
			nodes.addAttribute("id", "2");
			nodes.addText("");
			System.out.println(abNodeList.size());
			System.out.println(abNodeList.get(0).getType());
			for(int i=0 ;i<abNodeList.size();i++){
				
				AbstractState node=abNodeList.get(i);
				
				System.out.println("*************************");
				xy=locationmap.get(node.getSname());
				index=xy.indexOf('-');
				x=xy.substring(0, index);
				y=xy.substring(index+1);
				System.out.println(node.getSname());
				System.out.println(x+"---"+y);
				System.out.println("*************************");
				
				
				if("Start".equals(node.getType().trim())){
					
					Element cStartNode =nodes.addElement("CircularNode").addAttribute("id", k+"");
					
					map.put(node.getSid()+"",k+"");
					
					cStartNode.addElement("children")
								.addAttribute("id", k+1+"");
					cStartNode.addElement("location")
								.addAttribute("class", "Point2D.Double")
								.addAttribute("id", k+2+"")
								.addAttribute("x", x)//这里的坐标后期添加
								.addAttribute("y",y);
					cStartNode.addElement("id")
								.addAttribute("id", k+3+"");
						if(i==0){
						Element backcolor=cStartNode.addElement("backgroundColor").addAttribute("id", backgroundid).addText("");
							backcolor.addElement("red").addText("255");
							backcolor.addElement("green").addText("255");
							backcolor.addElement("blue").addText("255");
							backcolor.addElement("alpha").addText("255");
							
						Element bordercolor=cStartNode.addElement("borderColor").addAttribute("id", borderid).addText("");
							bordercolor.addElement("red").addText("191");
							bordercolor.addElement("green").addText("191");
							bordercolor.addElement("blue").addText("191");
							bordercolor.addElement("alpha").addText("255");
							
						Element textcolor=cStartNode.addElement("textColor").addAttribute("id", textid).addText("");
							textcolor.addElement("red").addText("51");
							textcolor.addElement("green").addText("51");
							textcolor.addElement("blue").addText("51");
							textcolor.addElement("alpha").addText("255");
						}else{
							Element backcolor=cStartNode.addElement("backgroundColor").addAttribute("reference", backgroundid);
							Element bordercolor=cStartNode.addElement("borderColor").addAttribute("reference", borderid);
							Element textcolor=cStartNode.addElement("textColor").addAttribute("reference", textid);
							
						}		
							
						Element name =cStartNode.addElement("name").addAttribute("id", k+4+"");
								name.addText(node.getSname());
								k=k+5;
						
				}else if("CircularNode".equals(node.getType().trim())){
						Element cNode =nodes.addElement("CircularNode").addAttribute("id", k+"");
						map.put(node.getSid()+"", k+"");
							cNode.addElement("children").addAttribute("id", k+1+"");
								cNode.addElement("location")
									.addAttribute("class", "Point2D.Double")
									.addAttribute("id", k+2+"")
									.addAttribute("x",x)//这里的坐标需要设计
									.addAttribute("y",y);
							
							cNode.addElement("id").addAttribute("id", k+3+"");
							if(i==0){
								Element backcolor=cNode.addElement("backgroundColor").addAttribute("id", backgroundid).addText("");
									backcolor.addElement("red").addText("255");
									backcolor.addElement("green").addText("255");
									backcolor.addElement("blue").addText("255");
									backcolor.addElement("alpha").addText("255");
									
								Element bordercolor=cNode.addElement("borderColor").addAttribute("id", borderid).addText("");
									bordercolor.addElement("red").addText("191");
									bordercolor.addElement("green").addText("191");
									bordercolor.addElement("blue").addText("191");
									bordercolor.addElement("alpha").addText("255");
									
								Element textcolor=cNode.addElement("textColor").addAttribute("id", textid).addText("");
									textcolor.addElement("red").addText("51");
									textcolor.addElement("green").addText("51");
									textcolor.addElement("blue").addText("51");
									textcolor.addElement("alpha").addText("255");
								}else{
									cNode.addElement("backgroundColor").addAttribute("reference", backgroundid);
									cNode.addElement("borderColor").addAttribute("reference",  borderid);
									cNode.addElement("textColor").addAttribute("reference", textid);
								}
							Element name =cNode.addElement("name").addAttribute("id", k+4+"");
								name.addText(" name:"+node.getSname()+";DBM:"+node.getInvariantDBM()+";position:"+node.getPosition());
							
							k=k+5;
						}
					l=k;
			}
	}
}
