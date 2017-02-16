package com.horstmann.violet.application.gui.util.tiancai.markov;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLReaderHDU {
	private String xmlFile;
	private String fileName;
	private Element root;
	
	private  ArrayList<UseCase> useCases=new ArrayList<UseCase>();
	//private ArrayList<Behavior> behaviors;
	
	private ArrayList<Diagram> diagrams=new ArrayList<Diagram>();	
	private ArrayList<LifeLine> lifeLines=new ArrayList<LifeLine>();
	private ArrayList<Node> nodes=new ArrayList<Node>();
	private ArrayList<Message> messages=new ArrayList<Message>();
	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
	
	public XMLReaderHDU(){}

	public XMLReaderHDU(String xmlFile) {
		super();
		this.xmlFile = xmlFile;
		if(xmlFile.contains("\\"))
		{
			this.fileName=xmlFile.substring(0, xmlFile.indexOf("\\"));
			System.out.println(fileName);
		}
		try
		{
			SAXReader reader=new SAXReader();
			Document dom=reader.read(xmlFile);
			root=dom.getRootElement();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void readInformation() throws Exception
	{
		List<Element> ucElementsList=new ArrayList<Element>();
		ucElementsList.addAll(root.element("nodes").elements("UseCaseNode"));
		System.out.println(ucElementsList.size());
		for(Iterator<Element> it=ucElementsList.iterator();it.hasNext();)
		{
			UseCase uc=new UseCase();
			
			//设定 用例的ID、name、preCondition、probability;
			Element e=it.next();
			uc.setUseCaseID(e.attributeValue("id"));
			uc.setUseCaseName(e.element("name").elementText("text"));
			String langStr=root.element("nodes").element("NoteNode").element("text").elementText("text");
			//System.out.println(langStr);
			String[] strList=langStr.split("#");
			for(String str:strList)
			{
				if(str.contains(uc.getUseCaseName()))
				{
					String[] ss=str.split(";");
					for(String s:ss)
					{
						if(s.contains("preCondition"))
						{
							uc.setPreCondition(s.substring(s.indexOf(":")+1));
						}
						if(s.contains("probability"))
						{
							uc.setUseCasePro(Double.parseDouble(s.substring(s.indexOf(":")+1)));
						}
					}
					break;
				}
			}
			
			//向用例 封装场景对应的顺序图
			encapsulationSD2UC(uc);
			useCases.add(uc);
		}
	}
	public void encapsulationSD2UC(UseCase uc) throws Exception
	{
		
		File file=new File(fileName);
		File[] tempList=file.listFiles();
		for(File f:tempList)
		{
			//System.out.println(f.getName());
		}
		ArrayList<SDSet> sdSets=new ArrayList<SDSet>();
		for(File f:tempList)
		{
			if(f.isFile())
			{
				//识别不了别的文件，只能识别xml文件
				SAXReader reader=new SAXReader();
				Document dom=reader.read(fileName+"\\"+f.getName());
				Element sdRoot=dom.getRootElement();
				if(sdRoot!=null)
				{
					if(sdRoot.getName().contains("SequenceDiagramGraph"))
					{
						String noteStr=sdRoot.element("nodes").element("NoteNode").element("text").elementText("text");
						if(noteStr.contains("owned")&&noteStr.contains(uc.getUseCaseName()));
						{
							SDSet sd=new SDSet();
							sd.setId(sdRoot.attributeValue("id"));
							sd.setName(f.getName());
							
							//该场景属于当前用例
							//开始寻找信息封装
							String[] strList=noteStr.split(";");
							
							for(String tempStr:strList)
							{
								if(tempStr.contains("postCondition"))
								{
									sd.setPostSD(tempStr.substring(tempStr.indexOf(":")+1));
								}
								if(tempStr.contains("probability"))
								{
									try
									{
										sd.setProb(Double.parseDouble(tempStr.substring(tempStr.indexOf(":")+1)));
									}
									catch (Exception e){
										e.printStackTrace();
									}
								}
							}
							//添加生命线
							@SuppressWarnings("unchecked")
							List<Element> lifeElementList=sdRoot.element("nodes").elements("LifelineNode");
							ArrayList<LifeLine> lifeLineList=new ArrayList<LifeLine>();;
							for(Iterator<Element> it=lifeElementList.iterator();it.hasNext();)
							{
								
								Element e=it.next();
								LifeLine lifeLine=new LifeLine(e.attributeValue("id"),e.element("name").elementText("text"));
								List<Node> nodeList=new ArrayList<Node>();
								//待封装 消息结点？  问题：消息结点，只有ActivationBarNode,没有node
								//…………………………………………………………………………
								lifeLineList.add(lifeLine);
							}
							sd.setLifeLines(lifeLineList);							
							//没有封装结点
							//………………………………………………………………………………
							
							//封装消息
							List<Element> messageElementList=sdRoot.element("edges").elements();
							ArrayList<Message> messageList=new ArrayList<Message>();
							for(Element e:messageElementList)
							{
								Message message=new Message();
								message.set(e.attributeValue("id"),e.elementText("middleLabel") 
										, e.element("start").attributeValue("reference"), e.element("end").attributeValue("reference"));
								message.setProb(sd.getProb());
								//消息属性不全
								//………………
								//执行时间
								messageList.add(message);
							}
							sd.setMessages(messageList);
							sdSets.add(sd);		
						}
					}
				}
				
			}
			
		}
		uc.setSdSets(sdSets);
		
	}
	public static void main(String[] args) throws Exception
	{
		XMLReaderHDU xmlReaderHDU=new XMLReaderHDU("umlXmlDocumentsHDU\\primaryUseCase.ucase.violet.xml");
		xmlReaderHDU.readInformation();
		for(UseCase uc:xmlReaderHDU.getUseCases())
		{
			uc.print_useCase();
		}
	}

	public String getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}

	public Element getRoot() {
		return root;
	}

	public void setRoot(Element root) {
		this.root = root;
	}

	public ArrayList<UseCase> getUseCases() {
		return useCases;
	}

	public void setUseCases(ArrayList<UseCase> useCases) {
		this.useCases = useCases;
	}

	public ArrayList<Diagram> getDiagrams() {
		return diagrams;
	}

	public void setDiagrams(ArrayList<Diagram> diagrams) {
		this.diagrams = diagrams;
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
	
	
}
