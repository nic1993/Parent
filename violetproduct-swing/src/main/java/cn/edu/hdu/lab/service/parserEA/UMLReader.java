package cn.edu.hdu.lab.service.parserEA;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.edu.hdu.lab.dao.uml.Behavior;
import cn.edu.hdu.lab.dao.uml.Diagram;
import cn.edu.hdu.lab.dao.uml.DiagramsData;
import cn.edu.hdu.lab.dao.uml.Fragment;
import cn.edu.hdu.lab.dao.uml.LifeLine;
import cn.edu.hdu.lab.dao.uml.Message;
import cn.edu.hdu.lab.dao.uml.Node;
import cn.edu.hdu.lab.dao.uml.Operand;
import cn.edu.hdu.lab.dao.uml.REF;
import cn.edu.hdu.lab.dao.uml.SDRectangle;
import cn.edu.hdu.lab.dao.uml.Stimulate;
import cn.edu.hdu.lab.dao.uml.UseCase;


public class UMLReader {

	private String xmlFile;
	private Element root;
	private String sdName;
	
	private ArrayList<UseCase> useCases=new ArrayList<UseCase>();
	//private ArrayList<Behavior> behaviors;
	
	private ArrayList<Diagram> diagrams=new ArrayList<Diagram>();	
	private ArrayList<LifeLine> lifeLines=new ArrayList<LifeLine>();
	private ArrayList<Node> nodes=new ArrayList<Node>();
	private ArrayList<Message> messages=new ArrayList<Message>();
	private ArrayList<Fragment> fragments = new ArrayList<Fragment>(); //顺序的总组合片段；
	private ArrayList<REF> umlREF = new ArrayList<REF>(); //引用信息
	static ArrayList<DiagramsData> umlAllDiagramData = new ArrayList<DiagramsData>();
	
	private DiagramsData completeSD=new DiagramsData();
	private static int idCount=0;
    
	public UMLReader(String xmlFile) {		
		// TODO Auto-generated constructor stub
		this.xmlFile=xmlFile;
		load();		
	}
	
	public UMLReader(String xmlFile,String fileName) {
		
		// TODO Auto-generated constructor stub
		this.xmlFile=xmlFile;
		this.sdName=fileName;
		load();	
		
	}
	/**
	 * 加载UML模型文件
	 */
	public void load()
	{
		//同时获取根节点
		try
		{
			SAXReader reader=new SAXReader();
			
			Document dom=reader.read(new File(xmlFile)); 
			//此句代替了reader.read(xmlname),是为了使中文路径通过；否则会报错
			//InputStream ifile = new FileInputStream(xmlFile); 
			//InputStreamReader ir = new InputStreamReader(ifile, "UTF-8"); 
			//Document dom = reader.read(ir);// 读取XML文件
			root=dom.getRootElement();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}

	/*
	 * 用例图中
	 * 获取用例集
	 * 获取了每个用例的id,name,行为集；还有Diagrams集和用例preCondition没有获取；
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<UseCase> retrieveuseCases() throws InvalidTagException
	{
		//System.out.println(root.attributeValue("version"));
		ArrayList<Element> elementList=new ArrayList<Element>();
		elementList.addAll(root.element("Model").elements("packagedElement"));
		
		ArrayList<Element> extensionElementList=new ArrayList<Element>();//用于获取用例前置条件
		extensionElementList.addAll(root.element("Extension").element("elements").elements("element"));
		
		for(Iterator<Element> it=elementList.iterator();it.hasNext();)
		{
			Element e=it.next();
			if(!e.attribute("name").getValue().equals("Primary Use Cases"))
				continue;
			
			ArrayList<Element> useCasesList=new ArrayList<Element>();//添加用例所在的包
			useCasesList.addAll(e.elements("packagedElement"));
			for(Iterator<Element> useCaseIterator=useCasesList.iterator();useCaseIterator.hasNext();)
			{
				Element ue=useCaseIterator.next();
				if(ue.attribute("type").getText().equals("uml:UseCase"))
				{
					UseCase useCase=new UseCase();
					useCase.setUseCaseID(ue.attribute("id").getText());
					useCase.setUseCaseName(ue.attribute("name").getText());
					
					
					if(ue.elements("nestedClassifier").size()>0)
					{
						ArrayList<Behavior> behaviors=new ArrayList<Behavior>();//用例图对应的一系列行为
						
						ArrayList<Element> behaviorList=new ArrayList<Element>();						
						behaviorList.addAll(ue.elements("nestedClassifier"));
						for(Iterator<Element> behaviorIterator=behaviorList.iterator();behaviorIterator.hasNext();)
						{			
							Element be=behaviorIterator.next();
							Behavior behavior=new Behavior();
							behavior.setBehaviorID(be.element("ownedBehavior").attribute("id").getText());
							behavior.setBehaviorName(be.element("ownedBehavior").attribute("name").getText());
							behaviors.add(behavior);
						}
						
						useCase.setBehaviors(behaviors);
					}
					
					for(Element extensionElement:extensionElementList)
					{
						
						if(extensionElement.attributeValue("type").equals("uml:UseCase")
								&&extensionElement.attributeValue("idref").equals(useCase.getUseCaseID()))
						{
							ArrayList<Element> constraintList=new ArrayList<Element>();
							//需要判断屏蔽掉无约束情况
							if(extensionElement.element("constraints")!=null)
							{
								constraintList.addAll(extensionElement.element("constraints").elements("constraint"));
								for(Element ec:constraintList)
								{
									if(ec.attributeValue("name").equals("preCondition")&&ec.attributeValue("type").equals("Pre-condition"))
									{
										useCase.setPreCondition(ec.attributeValue("description"));
										
									}
									if(ec.attributeValue("name").equals("probability"))
									{
										String pro_String=ec.attributeValue("description");
										//double tempPro=Double.parseDouble(pro_String);
										useCase.setUseCasePro(Double.parseDouble(pro_String));
									}
								}
							}
						}
					}
					
					useCases.add(useCase);
				}
			}
		}
		return this.useCases;
	}

	/*
	 *用例图中
	 * 获取UseCase集合下每个用例下行为操作对应的顺序图Diagram；
	 * 二者通过BUseCase.nestedClassifier.ownedBehavior-----Diagram.modle.parent;
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Diagram> retrieveuseDiagrams() throws InvalidTagException
	{
		//获取所有的Interaction
		ArrayList<Element> interactionList=new ArrayList<Element>();
		interactionList.addAll(root.element("Extension").element("elements").elements("element"));
		
		//获取Diagram所有包
		ArrayList<Element> diagramList=new ArrayList<Element>();
		diagramList.addAll(root.element("Extension").element("diagrams").elements("diagram"));
		for(Iterator<Element> it=diagramList.iterator();it.hasNext();)
		{
			Element diag=it.next();
			//判断这个Diagram如果是顺序图类型的就建立顺序图对应的Diagram
			if("Sequence".equals(diag.element("properties").attribute("type").getText()))
			{
				
				Diagram diagram=new Diagram();
				diagram.setDiagramID(diag.attribute("id").getValue());
				diagram.setDiagramName(diag.element("properties").attribute("name").getValue());
				diagram.setDiagramType(diag.element("properties").attribute("type").getValue());
				diagram.setBehaviorID(diag.element("model").attribute("parent").getValue());
				for(Element element:interactionList)
				{
					if(element.attributeValue("type").equals("uml:Interaction")&&element.attributeValue("idref").equals(diagram.getBehaviorID()))
					{
						
						ArrayList<Element> constraintList=new ArrayList<Element>();
						constraintList.addAll(element.element("constraints").elements("constraint"));
						
						for(Element eConstraint:constraintList)
						{
							if(eConstraint.attributeValue("name").equals("postCondition"))
							{
								diagram.setNotation(eConstraint.attributeValue("description"));
							}
							if(eConstraint.attributeValue("name").equals("probability"))
							{
								try{
									diagram.setProb(Double.parseDouble(eConstraint.attributeValue("description")));
								}
								catch(Exception e)
								{
									System.out.println("场景概率约束值输入类型有误，请在UML模型对应场景约束中检查修改，将其修正为双精度小数类型！");
									e.printStackTrace();
								}
							}
							
						}
						
					}
				}
				//diagram.setNotation(diag.element("properties").attributeValue("document"));
				
				//diagram.setProb(0.5);
				diagrams.add(diagram);
			}
		}
		return this.diagrams; //要考虑是否根据标记将用用behaviorID将每个diagrams添加到相应的用例下…………………………^^^6^^^^^^^^^^^^^^^^^^
	}
	
	//以下方法是在Diagram对应的顺序图SD中获取信息的方法，此时root已经变换了
	
	/*
	 *一、获取所有的同一个Diagram下的所有子Diagram以及坐标信息
	 *二、获取操作域相对高度信息，并将其所属组合片段ID和含有高度信息的字符串存放起来
	 */
	@SuppressWarnings("unchecked")
	public void retrieveAllDiagramElements()
	{
		umlAllDiagramData=new ArrayList<DiagramsData>();
		ArrayList<Element> EADiagramsList = new ArrayList<Element>();//存放读取得到的element		
		//1.取得所有的diagram 
		EADiagramsList.addAll(root.element("Extension").element("diagrams").elements("diagram"));		
		//2.遍历EADiagramIDsList
		for(Iterator<Element>  EADiagramsListIterator=EADiagramsList.iterator();EADiagramsListIterator.hasNext();)
		{
			//取得第i张图
			Element diagramI=EADiagramsListIterator.next();
			
			//获得这张图所有elements 
			ArrayList <Element> elements = new ArrayList <Element>();
			try {
				elements.addAll(diagramI.element("elements").elements("element"));
			} catch (Exception e) {
				System.out.println("exception:图元素");			
			}
			
			//遍历elements 设置ids(所有信息的ID)
			ArrayList <String> ids = new ArrayList<String>();//存放对象的ID	
			for(Iterator<Element>  elementsIterator=elements.iterator();elementsIterator.hasNext();)
			{
				Element elementI = elementsIterator.next();
				elementI.attributeValue("id");
				String id = elementI.attributeValue("subject");
				String value = elementI.attributeValue("geometry");
				//ids.add(id.substring(13));//取得13位之后的id属性 因为actor的id只有后面13位是相符的
				ids.add(id);
				//解析fragments/refs/objects/messages等信息的坐标，并将这些坐标和对应的所属ID成对的存放起来
				SDRectangle rectangle = FixFragmentTool.rectangleFromValueString(value);
				FixFragmentTool.rectangleById.put(id, rectangle);
			}
			
			//创建DiagramsData对象
			DiagramsData diagramData = new DiagramsData();
			diagramData.setDiagramID(diagramI.attributeValue("id")); //图ID
			String name = diagramI.element("properties").attributeValue("name");
			diagramData.setName(name);  //图名
			diagramData.setIds(ids);    //图所包含的所有信息（组合片片段，引用，对象，消息）的ID
			
			//lastMessageIDByKeyWithDiagramID.put(diagramData.diagramID, "init");
			//将DiagramsData对象 添加到成员变量umlAllDiagramData中
			
			umlAllDiagramData.add(diagramData);
		}
		
		
//操作域坐标相对高度信息****************************************
		ArrayList<Element> EAElements = new ArrayList<>();
		EAElements.addAll(root.element("Extension").element("elements").elements("element"));
		for(Element element : EAElements) 
		{
			if(element.attributeValue("idref")!=null)
			{
				//这个字符串中有操作域的高度size
				FixFragmentTool.xrefValueById.put(element.attributeValue("idref"), element.element("xrefs").attributeValue("value"));
			}
		}
	}
	
	/* 
	 * 首先获取顺序图下对应的所有生命线信息
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<LifeLine> retrieveLifeLines() throws InvalidTagException
	{
		
		ArrayList<Element> lifeLineList=new ArrayList<Element>();
		lifeLineList.addAll(root.element("Model").element("packagedElement").
				element("packagedElement").element("ownedBehavior").elements("lifeline"));
		for(Iterator<Element> it=lifeLineList.iterator();it.hasNext();)
		{
			Element life=it.next();
			if(life.attribute("type").getText().equals("uml:Lifeline"))
			{
				LifeLine lifeLine=new LifeLine();
				lifeLine.setId(life.attribute("id").getText());
				lifeLine.setName(life.attribute("name").getText());
				
				/*
				 * lifeLine上的位点没有封装
				 */ 
				//lifeLine.setNodes(nodes);
				
				lifeLines.add(lifeLine);
			}
		}
		return this.lifeLines;
	}
	/**
	 * 获取XML文件中生命线下的位点信息
	 * 位点（节点）：一般节点+组合片段中节点
	 * @return Node 数组
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Node> retrieveNodes() throws Exception
	{
		ArrayList<Element> fragmentList=new ArrayList<Element>();
		fragmentList.addAll(root.element("Model").element("packagedElement").
				element("packagedElement").element("ownedBehavior").elements("fragment"));
		for(Iterator<Element> it=fragmentList.iterator();it.hasNext();)
		{
			Element frag=it.next();
			//如果是一般的顺序位点
			if("uml:OccurrenceSpecification".equals(frag.attribute("type").getText()))
			{
				
				Node node=new Node();
				node.setId(frag.attribute("id").getText());
				node.setCoverdID(frag.attribute("covered").getText());
				nodes.add(node);
			}
			
			//如果是组合片段上的位点,则将其抽取出来，如果组合片段有内嵌组合片段则将其循环递归
			
			if(frag.attribute("type").getText().contains("CombinedFragment"))
			{
				retrieveNodesInFragments(frag);
			}
		}
		
		handleInfoLifeLineAndNodes(lifeLines,nodes);
		
		return this.nodes;
	}
	@SuppressWarnings("unchecked")
	private void retrieveNodesInFragments(Element fragElement) throws Exception
	{
		ArrayList<Element> operandList=new ArrayList<Element>();
		if(fragElement.elements("operand").size()>0)
		{
			operandList.addAll(fragElement.elements("operand"));
			for(Iterator<Element> operandIt=operandList.iterator();operandIt.hasNext();)
			{
				Element oper=operandIt.next();				
				//操作下的位点集合
				ArrayList<Element> operFragmentList=new ArrayList<Element>();
				operFragmentList.addAll(oper.elements("fragment"));
				for(Element of:operFragmentList)//一层组合片段，下面是普通结点，需要重新改编…………………………？？？？
				{
					if("uml:OccurrenceSpecification".equals(of.attribute("type").getText()))
					{
						Node node=new Node();
						node.setId(of.attribute("id").getText());
						node.setCoverdID( of.attribute("covered").getText());
						nodes.add(node);
					}
					if(of.attribute("type").getText().contains("CombinedFragment"))
					{
						retrieveNodesInFragments(of);
					}
				}
				
			}
		}
		else
		{
			throw new Exception("组合片段下没有操作域，即没有执行条件");
		}
		
	}
	
	/**
	 * 完善生命线集和结点集的消息封装操作
	 * @param lifeLines  顺序图所包含的所有生命线
	 * @param nodes 顺序图所包含的所有结点
	 */
	private void handleInfoLifeLineAndNodes(ArrayList<LifeLine> lifeLines,ArrayList<Node> nodes)
	{
		//整合结点和生命线对象的相互封装，将生命线对应的结点集合封装在生命线上、将结点所属生命线的名称封装在结点属性里面
		
		for(Iterator<LifeLine> lifeLineIt=lifeLines.iterator();lifeLineIt.hasNext();)
		{
			LifeLine lifeLine=lifeLineIt.next();
			for(Node node:nodes)
			{
				if(node.getCoverdID().equals(lifeLine.getId()))
				{
					node.setLifeLineName(lifeLine.getName());
					lifeLine.addNode(node);
				}
			}
				
		}
	}
	/**
	 * 一、获取引用片段信息
	 * 二、获取XML文件中的所有组合片段信息（并列出现的组合片段和嵌套出现的组合片段，并列放在一个集合里面）
	 * 三、获取结点信息（并将相应结点封装进操作里面）
	 * id,type,依附生命线,封装了操作域集合
	 * @return Fragment 数组
	 * @throws InvalidTagException
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Fragment> retrieveFragments() throws InvalidTagException
	{	
		ArrayList<Element> firstFragmentsList=new ArrayList<Element>();
		
		firstFragmentsList.addAll(root.element("Model").element("packagedElement")
				.element("packagedElement").element("ownedBehavior").elements("fragment"));
		
		for (Iterator<Element> fragmentIterator = firstFragmentsList.iterator(); fragmentIterator.hasNext();) 
		{
			Element elFragment = fragmentIterator.next();
			if("uml:OccurrenceSpecification".equals(elFragment.attribute("type").getText()))
			{
				Node node=new Node();
				node.setId(elFragment.attribute("id").getText());
				node.setCoverdID(elFragment.attribute("covered").getText());
				nodes.add(node);
			}
			
			if (elFragment.attribute("type").getValue().equals("uml:InteractionUse")) {//如果是一个ref
				REF ref = new REF();
				ref.setRefID(elFragment.attributeValue("id"));
				ref.setRefName(elFragment.attributeValue("name"));
				
				ArrayList<Element> elCoveredIDs=new ArrayList<Element>();
				elCoveredIDs.addAll(elFragment.elements("covered"));
				if(elCoveredIDs.size()>0)
				{
					for(Element e:elCoveredIDs)
					{
						ref.getCoveredIDs().add(e.attributeValue("idref"));
					}
				}
				umlREF.add(ref);
			}
			
			if(elFragment.attribute("type").getText().contains("CombinedFragment"))
			{
				//设置组合片段的id,name,type,依附生命线 
				Fragment localFragment = new Fragment();
				localFragment.setId(elFragment.attribute("id").getValue());
				localFragment.setName(elFragment.attributeValue("name"));
				localFragment.setType(elFragment.attribute("interactionOperator").getValue());
				
				ArrayList<String> coveredIDs = new ArrayList<String>();
				List<Element> coveredList = new ArrayList<Element>();
				coveredList.addAll(elFragment.elements("covered"));
				for (Iterator<Element> coveredIterator = coveredList.iterator(); coveredIterator.hasNext();) 
				{
					Element elCovered = coveredIterator.next();
					String coveredID = elCovered.attribute("idref").getValue();
					coveredIDs.add(coveredID);
				}
				localFragment.setCoveredIDs(coveredIDs);
				
				//将操作域封装进组合片段
				ArrayList<Operand> localOperands = new ArrayList<Operand>();
				ArrayList<Element> operandList = new ArrayList<Element>();
				operandList.addAll(elFragment.elements("operand"));
				//设置每个操作域的id,condition,包含的位点集,包含的位点Id集(未封装消息和内嵌组合片段)
				//并将位点添加到总nodes中
				for (Iterator<Element> operandIterator = operandList.iterator(); operandIterator.hasNext();) 
				{
					
					Element elOperand = operandIterator.next();
					Operand operand = new Operand();
					operand.setId(elOperand.attribute("id").getValue());
					if (elOperand.element("guard").element("specification").attribute("body") != null)
					{
						operand.setCondition(elOperand.element("guard").element("specification").attribute("body").getValue());
					}					
					
					ArrayList<Element> childFragmentInOperandList = new ArrayList<Element>();
					childFragmentInOperandList.addAll(elOperand.elements("fragment"));
					for (Iterator<Element> elmentIterator = childFragmentInOperandList.iterator(); elmentIterator.hasNext();)
					{
						Element childFragmentInOperand = elmentIterator.next();						
						if(childFragmentInOperand.attributeValue("type").contains("CombinedFragment"))
						{/*
							 * 如果操作域下有组合片段，设置标记，递归调用组合片段获取函数
							 */
							operand.setHasFragment(true);
							if(childFragmentInOperand!=null)
							{
								//提取内嵌组合片段
								retrieveChildFragment(childFragmentInOperand); 
							}
						}
						if("uml:OccurrenceSpecification".equals(childFragmentInOperand.attribute("type").getText()))
						{
							
							Node node=new Node();
							node.setId(childFragmentInOperand.attribute("id").getText());
							node.setCoverdID(childFragmentInOperand.attribute("covered").getText());
							
							operand.getNodes().add(node);//封装进该操作
							
							nodes.add(node);
						}
						if (childFragmentInOperand.attribute("type").getValue().equals("uml:InteractionUse")) {//如果是一个ref
							REF ref = new REF();
							ref.setRefID(childFragmentInOperand.attributeValue("id"));
							ref.setRefName(childFragmentInOperand.attributeValue("name"));
							
							ArrayList<Element> elCoveredIDs=new ArrayList<Element>();
							elCoveredIDs.addAll(childFragmentInOperand.elements("covered"));
							if(elCoveredIDs.size()>0)
							{
								for(Element e:elCoveredIDs)
								{
									ref.getCoveredIDs().add(e.attributeValue("idref"));
								}
							}
							umlREF.add(ref);
						}
						
					}
					//还有messages没有封装
					localOperands.add(operand);
				}
				localFragment.setOperands(localOperands);
				this.fragments.add(localFragment);
			}
			
			
		}
		
		////完善生命线和节点的信息：生命线和结点的互相嵌套(已经获取了节点信息)
		
		for(Node node:nodes)
		{
			for(LifeLine lifeLine : lifeLines) {
				if(node.getCoverdID().contains(lifeLine.getId()))
				{
					node.setLifeLineName(lifeLine.getName());
					lifeLine.getNodes().add(node);
				}
			}
		}
		/*System.out.println("结点："+nodes.size());
		System.out.println("生命线："+lifeLines.size());
		for(LifeLine life:lifeLines)
		{
			life.print_LifeLine();
		}
		for(Node node:nodes)
		{
			node.print_node();
		}
		System.out.println(":::::::::::::::::::::::::::::::::::");*/
		//给引用片段和组合片段赋坐标信息		
		for(REF ref:umlREF)
		{
			ref.setRectangle(FixFragmentTool.rectangleById.get(ref.getRefID()));
		}
		for(int i=0;i<umlREF.size();i++)
		{
			umlREF.get(i).setCount(i);
		}
		for(Fragment frag:fragments)
		{
			frag.setRectangle(FixFragmentTool.rectangleById.get(frag.getId()));//组合片段的坐标信息获取
			
			FixFragmentTool.operandRectangle(frag) ;//操作域信息获取
		}
		
		
		/* System.out.println("\n引用片段信息数量："+umlREF.size());
		 for(REF ref:umlREF)
			{
				System.out.println("片段名称："+ref.getRefName()+"---"+ref.getRefID());
				System.out.println(ref.getRectangle().toString());
			}
		 System.out.println("*********************************************");*/
		 
		return this.fragments;
	}
	
	/**
	 * 寻找内嵌组合片段，并列放在集合里面
	 * 寻找操作下的结点，并将其放在对应操作
	 * @param newRoot
	 */
	@SuppressWarnings("unchecked")
	private void retrieveChildFragment(Element newRoot)
	{
		Fragment localFragment = new Fragment();
		localFragment.setId(newRoot.attribute("id").getValue());
		localFragment.setName(newRoot.attributeValue("name"));
		localFragment.setType(newRoot.attribute("interactionOperator").getValue());
		
		ArrayList<String> coveredIDs = new ArrayList<String>();
		ArrayList<Element> coveredList = new ArrayList<Element>();
		
		coveredList.addAll(newRoot.elements("covered"));
		for (Iterator<Element> coveredIterator = coveredList.iterator(); coveredIterator.hasNext();) 
		{
			Element elCovered = coveredIterator.next();
			String coveredID = elCovered.attribute("idref").getValue();
			coveredIDs.add(coveredID);
		}
		localFragment.setCoveredIDs(coveredIDs);
		
		ArrayList<Operand> localOperands = new ArrayList<Operand>();
		ArrayList<Element> operandList = new ArrayList<Element>();
		operandList.addAll(newRoot.elements("operand"));
		for (Iterator<Element> operandIterator = operandList.iterator(); operandIterator.hasNext();) 
		{
			//设置操作的id,condition
			Element elOperand = operandIterator.next();
					
			Operand operand = new Operand();
			
			operand.setId(elOperand.attribute("id").getValue());
			if (elOperand.element("guard").element("specification").attribute("body") != null)
			{
				operand.setCondition(elOperand.element("guard").element("specification").attribute("body").getValue());
			}
				
			ArrayList<Element> childFragmentInOperandList = new ArrayList<Element>();
			childFragmentInOperandList.addAll(elOperand.elements("fragment"));
			for (Iterator<Element> elmentIterator = childFragmentInOperandList.iterator(); elmentIterator.hasNext();)
			{
				Element childFragmentInOperand = elmentIterator.next();				
				if(childFragmentInOperand.attributeValue("type").equals("uml:CombinedFragment"))
				{
					/* 
					 * 如果操作域下有组合片段，设置标记，递归调用组合片段获取函数
				     */
					operand.setHasFragment(true);
					retrieveChildFragment(childFragmentInOperand);		
				}
				if("uml:OccurrenceSpecification".equals(childFragmentInOperand.attribute("type").getText()))
				{
					
					Node node=new Node();
					node.setId(childFragmentInOperand.attribute("id").getText());
					node.setCoverdID(childFragmentInOperand.attribute("covered").getText());
					operand.getNodes().add(node);
					nodes.add(node);
				}
				if (childFragmentInOperand.attribute("type").getValue().equals("uml:InteractionUse")) {//如果是一个ref
					REF ref = new REF();
					ref.setRefID(childFragmentInOperand.attributeValue("id"));
					ref.setRefName(childFragmentInOperand.attributeValue("name"));
					
					ArrayList<Element> elCoveredIDs=new ArrayList<Element>();
					elCoveredIDs.addAll(childFragmentInOperand.elements("covered"));
					if(elCoveredIDs.size()>0)
					{
						for(Element e:elCoveredIDs)
						{
							ref.getCoveredIDs().add(e.attributeValue("idref"));
						}
					}
					umlREF.add(ref);
				}
			}
			//还有messages没有封装
			localOperands.add(operand);
		}
		localFragment.setOperands(localOperands);
		this.fragments.add(localFragment);
		
	}
	
	
	/*
	 * 获取所有消息message
	 * 并将对应的消息封装进组合片段的操作域
	 */ 
	@SuppressWarnings("unchecked")
	public ArrayList<Message> retrieveMessages(double pro) throws InvalidTagException 
	{
		ArrayList<Element> messageList = new ArrayList<Element>();
		messageList.addAll(root.element("Model").element("packagedElement")
				.element("packagedElement").element("ownedBehavior").elements("message"));
		
		//遍历每个消息片段
		for (Iterator<Element> messageIterator = messageList.iterator(); messageIterator.hasNext();) 
		{
			Element elMessage = messageIterator.next();

			/*
			 * 实例化消息， 并获取基本信息(id,name,senderID,receiverID,参数,参数类型)
			 */
			Message message = new Message();
			Stimulate stimulate=new Stimulate();
			message.setId(elMessage.attribute("id").getValue());
			message.setName(elMessage.attribute("name").getValue());
			message.setProb(pro);
			message.setSenderID(elMessage.attribute("sendEvent").getValue());
			message.setReceiverID(elMessage.attribute("receiveEvent").getValue());
//			if(elMessage==messageList.get(messageList.size()-1))
//				message.setLast(true);//最后一条信息设置isLast为真；
			
			if(elMessage.hasContent())
			{
				String name=message.getName();
				int tag=0;
				for(int i=0;i<name.length();i++)
				{
					if(name.charAt(i)=='('&&name.charAt(i+1)!=')')
					{
					  tag=1;
					}
				}
				//在名字当中寻找 参数类型
				if(tag==1)
				{
					int k=0;
					for(int i=0;i<message.getName().length();i++)
					{
						if(message.getName().charAt(i)=='(')
						{
							k=i;
							break;
						}
						
					}
					String parametersTypeStr=message.getName().substring(k+1,message.getName().length()-1)+",";
					stimulate.setParameterTypeList(SerachParametersType(parametersTypeStr));
				}
				//寻找参数名字
				List<Element> argumentElements=new ArrayList<Element>();
				argumentElements.addAll(elMessage.elements());
				for(Element e:argumentElements)
				{
					if(e.getName().contains("argument"))
					{
						stimulate.addParameterName(e.attributeValue("name"));
					}
				}
				
			}
			/*
			 * 获取消息的发送、接受对象名称(sender,receiver)
			 * 遍历所有结点
			 * 如果该节点id等于消息发送结点id,则将该节点依附生命线名称赋给消息发送这sender；
			 * 同时以同样的方法设置消息接受者；
			 */
			for (Node node : nodes)  //根据信息上的结点所在生命线找到信息所依附的发出、接受生命线；
			{
				
				if (message.getSenderID().equals(node.getId())) 
				{
					message.setSender(node.getLifeLineName());
				}
				if (message.getReceiverID().equals(node.getId())) 
				{
					message.setReceiver(node.getLifeLineName());
				}
			}
			/*获取消息(infragment,operandId)
			 * 对每个消息都遍历一次组合片段，遍历组合片段里所有的操作，
			 * 如果操作里的结点id=消息的发送结点id,那么就说明该消息在组合片段里面，给该消息设置InFragment标记和所在组合片段的id
			 * 并将消息嵌套进这个操作里面
			 * message 暂时不再根据坐标加入组合片段操作域
			 */
			//System.out.println("组合片段数量："+fragments.size());
			for (Fragment fragment : fragments) 
			{
				for (Operand operand : fragment.getOperands())
				{
					if(message.isInFragment()==false)
					{
						if(operand.getNodes().size()>0)
						{
							for (Node node : operand.getNodes()) 
							{
								if (node.getId().contains(message.getSenderID())) 
								{
									message.setInFragment(true);
									message.setFragmentId(fragment.getId());
									message.setFragType(fragment.getType());
									message.setOperandId(operand.getId());
									operand.getMessages().add(message);
								
								}
							}
						}						
					}
				}
				
			}
			/*
			 * 获取消息在组合片段中的进出类型
			 * 遍历所有的Connector，如果有connector的文件对象ID=该信息的ID，
			 * 那么将信息的组合片段   进出标记   设置成value键下的值比如inAlt,outAlt,inPar,outPar等；
			 * ***********寻找域和约束表达式(两者的存在性没有关系)
			 * 寻找消息起始状态的时间约束条件
			 */
			ArrayList<Element> connectorList = new ArrayList<Element>();
			connectorList.addAll(root.element("Extension").element("connectors").elements("connector"));

			for (Iterator<Element> connectorIterator = connectorList.iterator(); connectorIterator.hasNext();) 
			{
				Element elConnector = connectorIterator.next();
				//if消息的id和链接的id相等
				// 第一，判断消息属于哪个组合片段
				//第二，可以获取标签label的字符串中的返回值和返回值类型
				if(message.getId().equals(elConnector.attribute("idref").getValue())) 
				{
					
					//根据Notes自定义输入规格获取参数类型和参数列表		
					if (elConnector.element("documentation").attribute("value")!= null) 
					{						
						String str=elConnector.element("documentation").attributeValue("value");
						setStimulate(stimulate, str);					
					}
					
					//获取消息返回值返回类型和消息名字符串，并解析返回值和返回值类型
					String labelStr=elConnector.element("labels").attributeValue("mt");
					if(labelStr.contains("="))
					{
						message.setReturnValue(labelStr.substring(0, labelStr.indexOf("=")));
					}
					if(labelStr.contains(":"))
					{
						message.setReturnValueType(labelStr.substring(labelStr.indexOf(":")+1,labelStr.length()));
					}
					//获取消息的高度坐标
					message.setPointY(FixFragmentTool.pointYFromValueString(elConnector.element("extendedProperties").attributeValue("sequence_points")));
				
					//获取消息起始状态的执行时间
					
					message.setFromTimeConstraint(FixFragmentTool.handleFromTimeConstraint(elConnector.element("style").attributeValue("value")));
				}//消息还有其他的属性没有设置(probability,exectime,notation,fragmentId,fragType,islast)
			}
			message.setStimulate(stimulate);
			
			messages.add(message);
		}
		return this.messages;
	}
	
	
	/*
	 * 将组合片段，生命线，消息，归属到各个子图当中
	 * 
	 */
	public void assembleInfo2DiffDiagram() throws Exception
	{
		
		for(DiagramsData diagramData : umlAllDiagramData) 
		{
			for(LifeLine lifeline : lifeLines) {
				for(String str:diagramData.getIds())
				{
					if(str.contains(lifeline.getId().substring(13)))
					{
						diagramData.getLifelineArray().add(lifeline);
					}
				}
			}
			for(LifeLine lifeline : lifeLines) {
				if (diagramData.getIds().contains(lifeline.getId())) {
					diagramData.getLifelineArray().add(lifeline);
				}
			}
			for(Fragment fragment : fragments) {
				if (diagramData.getIds().contains(fragment.getId())) {
					diagramData.getFragmentArray().add(fragment);
				}
			}
			
			for(Message message : messages) {
				if (diagramData.getIds().contains(message.getId())) {
					diagramData.getMessageArray().add(message);
				}
			}
			for(REF ref : umlREF) {
				if (diagramData.getIds().contains(ref.getRefID())) {
					diagramData.getRefArray().add(ref);										
				}
			}
			
			diagramData.getTempRefArray().addAll(diagramData.getRefArray()); //保存所有的组合片段地址引用（一般性的+内嵌的）；
			//根据坐标修正 每个子图的 fragment之间的嵌套关系  +  fragment与ref之间的嵌套关系
			//以top left right bottom 找到外一层的fragment
			FixFragmentTool.fixFragmentsOfOneDiagram(diagramData,fragments);
		}
		
	/*	System.out.println("\n\n各个子图的信息：");
		for(DiagramsData diagramData : umlAllDiagramData) 
		{
			System.out.println("\n名称："+diagramData.getName());
			for(LifeLine lifeline : diagramData.getLifelineArray()) {
				lifeline.print_LifeLine();	
			}
			
			for(Fragment fragment : diagramData.getFragmentArray()) {
				fragment.print_Fragment();
			}
			
			System.out.println("总消息："+diagramData.getMessageArray().size());
			for(Message message : diagramData.getMessageArray()) {
				message.print_Message();
			}
			System.out.println("引用片段："+diagramData.getRefArray().size());
			for(REF ref : diagramData.getRefArray()) {
				System.out.println(ref.toString());
			}
		}
		*/
		/*
		 * 图的连接
		 * 找到主顺序图，顺着主图 深层递归，对ref进行处理 合并子图到父图
		 * 		
		 */
		//
		for(DiagramsData diagramData : umlAllDiagramData) {
			if(diagramData.getName().equals(sdName))
			{
				//System.out.println("\n主图："+diagramData.getName());
				DFSDiagramByREF(diagramData);				
				//消息指针重定向
				redirectMessage(diagramData);
				//尾消息标志设置
				diagramData.getMessageArray().get(diagramData.getMessageArray().size()-1).setLast(true);
				
				//确定消息的所有执行条件
				searchOperConditionOfMess(diagramData);
				//赋给主顺序图的保留变量
				completeSD=diagramData;
				break;
			}
		}
		System.out.println("\n*******************完全图信息*********************");		
		for(DiagramsData diagramData : umlAllDiagramData) {
			if(diagramData.getName().equals(sdName))
			{
				System.out.println("顺序图名称："+diagramData.getName()+"---"+sdName);
				for(LifeLine l:diagramData.getLifelineArray())
				{
					l.print_LifeLine();
				}
				for(Message m:diagramData.getMessageArray())
				{
					m.print_Message();
				}
				for(Fragment f:diagramData.getFragmentArray())
				{
					f.print_Fragment();
				}
				for(REF r:diagramData.getRefArray())
				{
					System.out.println(r.toString());
				}				
				break;
			}
		}
	}	
	
	public static void searchOperConditionOfMess(DiagramsData d) throws Exception
	{
		for(Message message:d.getMessageArray())
		{
			if(message.isInFragment())
			{
				//System.out.println(message.getName());
				for(Fragment frag:d.getFragmentArray())
				{
					/*List<String> fragmentIds=retrieveFragmentIds(frag,message);
					if(fragmentIds!=null&&fragmentIds.size()>0)
					{
						for(String str:fragmentIds)
						{
							System.out.println(str);
						}
					}*/
					List<String> operConditions=retrieveOperConditions(frag,message);
					if(operConditions!=null&&operConditions.size()>0)
					{
						String condition=new String("");
						for(String str:operConditions)
						{
							if(str==null)
								throw new Exception("\n顺序图："+d.getName()+"\n组合片段："+frag.getName()+"\n缺少条件！");
							if(str==operConditions.get(operConditions.size()-1))
							{
								condition+=str.trim();
							}
							else
							{
								condition=condition+str.trim()+",";
							}
						}
						message.setNotation(message.getNotation()+condition);
						break;
					}
				}
			}
			//message.print_Message();
		}
		
	}

	
	public static List<String> retrieveOperConditions(Fragment frag,Message message)
	{
		
		//List<String> fragmentIds=new ArrayList<String>();
		List<String> operConditions=new ArrayList<String>();
		if(frag.getId().equals(message.getFragmentId()))
		{
			//fragmentIds.add(frag.getId());
			for(Operand oper:frag.getOperands())
			{
				int flag=0;
				for(Message mess:oper.getMessages())
				{
					if(mess.getId().equals(message.getId()))
					{
						operConditions.add(oper.getCondition());
						flag=1;
						break;
					}
				}
				if(flag==1)
				{
					break;
				}
			}
			return operConditions;
//			return fragmentIds;
		}
		else 
		{
			int tag=0;
			//fragmentIds.add(frag.getId());
			
			//List<String> newFragmentIds=null;
			
			for(Operand oper:frag.getOperands())
			{
				
				List<String> newOperConditions =null;
				int inTag=0;
				if(oper.isHasFragment()==true)
				{
					for(Fragment newFrag: oper.getFragments())
					{
						newOperConditions=retrieveOperConditions(newFrag,message);
						if(newOperConditions!=null)
						{
							inTag=1;
							break;
						}
					}
				}
				if(inTag==1)
				{
					operConditions.add(oper.getCondition());
					operConditions.addAll(newOperConditions);
					tag=1;
					break;
				}
			}
			if(tag==1)
			{
				return operConditions;
			}
			else return null;
		}
		
	}
	
	
	/**
	 * 解析参数列表
	 * 如果参数类型列表为空，则终止
	 * 如果参数名列表为1，并且仅仅是个逗号","，则返回空
	 * @param typeStr
	 * @return
	 */
	private List<String> SerachParametersType(String typeStr)
	{
		
		List<String> types=new ArrayList<String>();
		if(typeStr.length()==0)
			return types;
		else
			if(typeStr.length()==1&&typeStr.equals(","))
			{
				return types;
			}
		int k=0;//起始位置，遇到‘,’停止;
		for(int i=0;i<typeStr.length();i++)
		{
			if(typeStr.charAt(i)==',')
			{
				types.add(typeStr.substring(k,i)); //复制从第k个位置到i-1位置的字符串；
				k=i+1;
			}
		}
		return types;
	}
	
	public void setStimulate(Stimulate stimulate,String str)
	{
		String[] strs=str.replaceAll("\r|\n","").split("#");
		for(String s:strs)
		{
			if(!s.equals(""))
			{
				String tempStr=s.substring(s.indexOf('(')+1, s.indexOf(')'));
				if(s.substring(0, 9).equals("Condition"))
				{				
					stimulate.setDomains(SerachSubString(tempStr));
					continue;
				}
				if(s.substring(0, 10).equals("Constraint"))
				{				
					stimulate.setConstraintExpresstions(SerachSubString(tempStr));
					continue;
				}
				if(s.substring(0, 4).equals("Time"))
				{				
					stimulate.setTimeConstraints(SerachSubString(tempStr));
					continue;
				}
			}
			
		}
	}
	public List<String> SerachSubString(String str)
	{
		List<String> strs=new ArrayList<String>();
		
		String[] tempStrs=str.split(",");
		
		for(int i=0;i<tempStrs.length;i++)
		{
			if(tempStrs[i].substring(0,4).equals("bool"))
			{
				tempStrs[i]=tempStrs[i].substring(0,tempStrs[i].indexOf("["))+"[true,false]";
			}
		}
		Collections.addAll(strs, tempStrs);	
		
		return strs;
	}
	
	/**
	 * 递归引入子图，组合信息
	 * @param diagramData
	 */
	public static void DFSDiagramByREF(DiagramsData diagramData) {
		//System.out.println("~~~~~~~~~~"+diagramData.getName()+"--所有引用："+diagramData.getTempRefArray().size());
		if(diagramData.getTempRefArray().size()==0)
		{
			return;
		}
		
		//System.out.println("!!!!!!!!!!!!!!!主图名称："+diagramData.getName());
		//System.out.println("总引用："+diagramData.getTempRefArray().size());
		//如果有直系引用或者内嵌引用
		if(diagramData.getTempRefArray().size()>0)
		{
			/**
			 * 1:只有一条引用，什么都没有，直接调用深度复制函数，进行子图的深度复制
			 */
			//if( diagramData.getRefArray().size()==)
			/**
			 * 2：只有 组合片段+内嵌引用，只遍历组合片段进行深度复制
			 */
			
			/**
			 * 3:直系引用+组合片段
			 */
			
			/**
			 * 4：消息+引用
			 *   
			 *   消息+组合片段+直接引用+组合片段内嵌引用
			 */
			
			//设置该引用在顺序图中的两种相对位置(Mindex和Findex)
			for(REF ref:diagramData.getTempRefArray())
			{
				ref.setIndex(FixFragmentTool.refIndexInDiagram(ref, diagramData));
				if(!ref.isInFragFlag())
				{
					ref.setFindex(FixFragmentTool.refIndexBetweenFragsInDiagram(ref, diagramData));
				}
			}
			
			/*
			 * 一，先处理顺序图的直系引用片段（嵌套对象的深度复制）
			 * 确定图下直系ref的两种index(Mindex(出入信息)和Findex(插入组合片段))
			 * 消息添加，根据index插入位置，然后更新剩下的ref在消息中的Mindex(index+上一个ref中含有的消息数量)
			 * 组合片段的插入，根据当前ref在组合片段之间的位置Findex插入frags
			 * 更新后续ref的Findex(Findex+当前ref中外层frags中的数量)
			 * 为了方便后续组合片段中messages插入，需要更新frag中Mindex(根据frag和当前ref的相对位置确定是否更新frag下的Mindex,已经每个内嵌组合片段的Mindex)
			 */
			for(REF ref : diagramData.getRefArray()) 
			{
				//System.out.println("--直接引用："+ref.getRefName());
				DiagramsData childDiagram = findDiagramByName(ref.getRefName());
				if(childDiagram.getTempRefArray().size()>0)
				{
					DFSDiagramByREF(childDiagram);//处理子图 使其变成无引用的完全图
				}
				
				//根据Mindex引入引用片段所代表的子图 :深度复制子图
				fixDiagramDataWithRef(diagramData,childDiagram,ref);
				
				//修改图的所有引用(直系+内嵌)相对坐标Mindex 和直系引用的Findex的值				
				for(REF refi:diagramData.getTempRefArray())
				{
					refi.setIndex(refi.getIndex() + childDiagram.getMessageArray().size());
					if(!ref.isInFragFlag())
					{
						refi.setFindex(refi.getFindex()+childDiagram.getFragmentArray().size());	
					}
				}
				diagramData.getTempRefArray().remove(ref);
			}
			diagramData.getRefArray().clear();
			
			/*
			 * 二、再处理当前该图中frags下的内嵌引用片段(不用更新组合片段)(放在第二步)
			 * 深层遍历各个frags下是否有ref,若有，则对其：
			 * RMessage插入到当前顺序图的messages中（*********Mindex已经发生变化，如何确定？********）
			 * RMessages中没有在组合片段中的messes插入到当前oper下，同时修改消息组合片段标志为true;
			 * RMessages中frags加入到当前操作下
			 */
			//System.out.println("-------主图名称："+diagramData.getName());
			//System.out.println("&&&&&&&主图名称："+diagramData.getName());
			for(Fragment frag:diagramData.getFragmentArray()) 
			{
				//System.out.println("****组合片段："+frag.getName());
				//当前操作没有引用，则寻找是否有内嵌组合片段，若有，则寻找内嵌组合片段下是否有引用		
				for(Operand oper:frag.getOperands())
				{
					if(oper.getRef()==null&&oper.getFragments().size()>0)
					{
						for(Fragment f:oper.getFragments())
						{
							//System.out.println("组合片段："+f.getName());  
							handleRefInOper(diagramData,f);							
						}
					}
					else
					if(oper.getRef()!=null)
					{
						
						/*
						 * 对该内嵌引用进行处理
						 */
						//System.out.println("$$$$$$$$内嵌引用片段："+oper.getRef().getRefName());
						DiagramsData childDiagram = findDiagramByName(oper.getRef().getRefName());
						if(childDiagram.getTempRefArray().size()>0)
						{
							DFSDiagramByREF(childDiagram);//回调继续处理子图
						}
						fixFragmentWithRef(diagramData,frag,oper,oper.getRef());												
						
						diagramData.getTempRefArray().remove(oper.getRef());
						oper.setRef(null);//清除引用片段的引用，便于递归的返回判断
					}								
				}
			}
			
		}
		
		//System.out.println("###############33组合后的消息集合："+diagramData.getName());
		/*for(Message m:diagramData.getMessageArray())
		{
			m.print_Message();
		}*/		
				
	}
	
	private static void handleRefInOper(DiagramsData diagramData,Fragment f)
	{
		//System.out.println("组合片段："+f.getName()+"---操作数："+f.getOperands().size());
		for(Operand op:f.getOperands())
		{
			if(op.getRef()==null&&op.getFragments().size()>0)
			{
				for(Fragment frag:op.getFragments())
				{
					//System.out.println("组合片段："+frag.getName());
					handleRefInOper(diagramData,frag);
				}
			}
			if(op.getRef()!=null)
			{
				//System.out.println("\n组合片段内嵌引用："+op.getRef().getRefName());
				DiagramsData childDiagram = findDiagramByName(op.getRef().getRefName());
				if(childDiagram.getTempRefArray().size()>0)
				{
					//System.out.println("引用"+op.getRef().getRefName()+"所代表的子图含有引用数量"+childDiagram.getTempRefArray().size());

					DFSDiagramByREF(childDiagram);//回调继续处理子图
					
				}
					//System.out.println("深度复制："+op.getRef().getRefName());
					fixFragmentWithRef(diagramData,f,op,op.getRef());
					diagramData.getTempRefArray().remove(op.getRef());
					op.setRef(null);//清除引用片段的引用，便于递归的返回判断
								
				
			}
			
		}
		
		//System.out.println("#####组合后的消息集合："+diagramData.getName());
//		for(Message m:diagramData.getMessageArray())
//		{
//			m.print_Message();
//		}
	}
	
	/**
	 * 深度复制图的直接引用所代表的子图并修改id
	 * @param diagramData
	 * @param childDiagram
	 * @param ref
	 */
	private static void fixDiagramDataWithRef(DiagramsData diagramData, DiagramsData childDiagram, REF ref) {
		//System.out.println("11111复制直接引用的信息："+diagramData.getName());
		
		idCount++;//自增id后缀
		//添加lifeline
		for(LifeLine lifeline : childDiagram.getLifelineArray())
		{
			if (!diagramData.getLifelineArray().contains(lifeline)) {//添加父图没有的lifeline
				diagramData.getLifelineArray().add(lifeline);
			}
		}
		//是否引入Node????????????????????????????????????///
		
		//先复制一份子图的messageArray
		if(childDiagram.getMessageArray().size()>0)
		{
			ArrayList<Message> copyMessageArray = new ArrayList<>(); 
			for(Message message : childDiagram.getMessageArray()) {
						Message copyMessage = (Message) message.clone();
						//通过给消息添加后缀来改变Id
						copyMessage.setId(copyMessage.getId()+idCount);
						copyMessageArray.add(copyMessage);
			}
			diagramData.getMessageArray().addAll(ref.getIndex(), copyMessageArray);			
		}

		//修改最外层的fragment 复制一份到父图中
		if(childDiagram.getFragmentArray().size()>0)
		{
			ArrayList<Fragment> copyFragmentArray = new ArrayList<>();
			for(Fragment fragment : childDiagram.getFragmentArray()) {//添加所有的组合片段
				//组合片段内有循环嵌套现象，需要判断来进行复制。
				Fragment copyFragment = (Fragment) fragment.clone(); 
				changeAllIdByIdCount(diagramData,copyFragment,idCount);
				copyFragmentArray.add(copyFragment);	
			}
			diagramData.getFragmentArray().addAll(ref.getFindex(), copyFragmentArray);
		}
	}
	
	/**
	 * 给该组合片段id,操作id,操作下的消息id,以及内嵌的这些id增加后缀，使Id变得独一无二。
	 * @param copyFragment 复制得到的新组合片段
	 * @param postFix id后缀
	 */
	private static void changeAllIdByIdCount(DiagramsData diagramData,Fragment frag,int postFix)
	{
		frag.setId(frag.getId()+postFix);
		for(Operand oper:frag.getOperands())
		{
			oper.setId(oper.getId()+postFix);
			for(Message mess:oper.getMessages())
			{
				mess.setId(mess.getId()+postFix);
				
				mess.setFragmentId(frag.getId());
				mess.setOperandId(oper.getId());
				
				//然后寻找顺序图中组合片段中在组合片段外存放的相同message不同哈希地址的消息，修改fragmentId;
				for(Message m:diagramData.getMessageArray())
				{
					if(m.getId().equals(mess.getId()))
					{
						m.setFragmentId(mess.getFragmentId());
						m.setOperandId(oper.getId());
						break;
					}
				}
			}
			if(oper.isHasFragment()==true||oper.getFragmentIDs().size()>0||oper.getFragments().size()>0)
			{
				oper.getFragmentIDs().clear();
				for(Fragment f:oper.getFragments())
				{
					changeAllIdByIdCount(diagramData,f,postFix);
					oper.getFragmentIDs().add(f.getId());
				}
			}
						
		}
	}
	
	/**
	 * 将操作下的引用片段代表的子图深度复制进当前操作。
	 * @param diagramData 当前图
	 * @param frag 当前组合片段
	 * @param oper  当前含有引用的操作
	 * @param ref 当前含有的引用
	 */
	private static void fixFragmentWithRef(DiagramsData diagramData,Fragment frag,Operand oper,REF ref)
	{
		//System.out.println("………………………………深度复制："+oper.getRef().getRefName());
		/*
		 * 找到ref对应的子图
		 * RMessage插入到当前顺序图的messages中
		 * RMessages中没有在组合片段中的messes插入到当前oper下，同时修改消息组合片段标志为true;
		 * RMessages中frags加入到当前操作下
		 * 更新索引坐标Mindex和Findex;
		 */
		idCount++;
		for(DiagramsData childDiagram :umlAllDiagramData)
		{
			if(ref.getRefName().equals(childDiagram.getName())) //找到对应的顺序图
			{	
				
					//添加lifeline
					for(LifeLine lifeline : childDiagram.getLifelineArray())
					{
						if (!diagramData.getLifelineArray().contains(lifeline)) {//添加父图没有的lifeline
							diagramData.getLifelineArray().add(lifeline);
						}
					}
					//根据当前引用更新后的Mindex插入 深度复制 的信息
					if(childDiagram.getMessageArray().size()>0)
					{
						//赋值进总消息
						ArrayList<Message> copyMessageArray = new ArrayList<>(); 
						ArrayList<Message> copyMessesInOper = new ArrayList<>(); 
						for(Message message : childDiagram.getMessageArray()) 
						{
							Message copyMessage = (Message) message.clone();
							copyMessage.setId(copyMessage.getId()+idCount);
							copyMessageArray.add(copyMessage);
							if(!copyMessage.isInFragment())
							{
								copyMessage.setInFragment(true);
								copyMessage.setFragmentId(frag.getId());
								copyMessage.setFragType(frag.getType());
								copyMessage.setOperandId(oper.getId());
								copyMessesInOper.add(copyMessage);
							}		
						}
						diagramData.getMessageArray().addAll(ref.getIndex(), copyMessageArray);	
						//复制进当前操作
						if(copyMessesInOper.size()>0)
						{
							oper.getMessages().addAll(copyMessesInOper);
						}
					}

					//将子图最外层的组合片段集合深度赋值，给当前操作
					if(childDiagram.getFragmentArray().size()>0)
					{
						//System.out.println(childDiagram.getFragmentArray().size());
						//System.out.println("未引入时操作中组合片段数量："+oper.getFragments().size());
						ArrayList<Fragment> copyFragmentArray = new ArrayList<Fragment>();
						ArrayList<String> copyFragmentIds=new ArrayList<String>();
						for(Fragment fragment : childDiagram.getFragmentArray()) {//添加所有的组合片段
							//组合片段内有循环嵌套现象，需要判断来进行复制。
							Fragment copyFragment = (Fragment) fragment.clone(); 
							changeAllIdByIdCount(diagramData,copyFragment,idCount);
							copyFragmentIds.add(copyFragment.getId());
							copyFragmentArray.add(copyFragment);	
						}
						//System.out.println(copyFragmentArray.size());
						oper.getFragmentIDs().addAll(copyFragmentIds);
						oper.getFragments().addAll(copyFragmentArray);
						if(copyFragmentArray.size()>0)
						{
							oper.setHasFragment(true);
						}
						//System.out.println("引用引入的组合片段数量："+oper.getFragments().size());
					} 
					
					for(REF refi:diagramData.getTempRefArray())
					{
						refi.setIndex(refi.getIndex() + childDiagram.getMessageArray().size());
						if(!ref.isInFragFlag())
						{
							refi.setFindex(refi.getFindex()+childDiagram.getFragmentArray().size());	
						}
					}
					break;				
				
			}			
		}
	}
	
	
	private static DiagramsData findDiagramByName(String refName) {
		
		for(DiagramsData diagramsData :umlAllDiagramData)
		{
			if (diagramsData.getName().equals(refName))//包含这个子id
				return diagramsData;
		}
		return null;
	}
	/**
	 * 对组合后的主图进行消息指针的重定向，释放深度复制的多余message
	 * 根据消息Id，将组合片段中的消息  赋给 主图消息集合中对应的消息
	 * @param diagramData
	 */
	public static void redirectMessage(DiagramsData diagramData)
	{
		for(Fragment frag:diagramData.getFragmentArray())
		{
			redirectMessageInFragment(frag,diagramData.getMessageArray());
		}		
	}
	private static void redirectMessageInFragment(Fragment frag,ArrayList<Message> messes)
	{
		for(Operand oper:frag.getOperands())
		{
			for(Message m:oper.getMessages())
			{
				for(int i=0;i<messes.size();i++)
				{
					if(m.getId().equals(messes.get(i).getId()))
					{
						//m.setId(m.getId()+"新ID");
						messes.remove(i);
						messes.add(i, m);
					}
				}
				
			}
			if(oper.getFragments().size()>0)
			{
				for(Fragment f:oper.getFragments())
				{
					redirectMessageInFragment(f,messes);
				}
			}				
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

	public ArrayList<REF> getUmlREF() {
		return umlREF;
	}

	public void setUmlREF(ArrayList<REF> umlREF) {
		this.umlREF = umlREF;
	}

	public  DiagramsData getCompleteSD() {
		return completeSD;
	}

	public  void setCompleteSD(DiagramsData completeSD) {
		this.completeSD = completeSD;
	}
	
	
	
}

