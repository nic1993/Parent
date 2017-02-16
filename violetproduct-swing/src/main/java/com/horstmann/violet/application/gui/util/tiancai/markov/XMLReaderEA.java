package com.horstmann.violet.application.gui.util.tiancai.markov;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class XMLReaderEA {

	private String xmlFile;
	private Element root;
	
	private ArrayList<UseCase> useCases=new ArrayList<UseCase>();
	//private ArrayList<Behavior> behaviors;
	
	private ArrayList<Diagram> diagrams=new ArrayList<Diagram>();	
	private ArrayList<LifeLine> lifeLines=new ArrayList<LifeLine>();
	private ArrayList<Node> nodes=new ArrayList<Node>();
	private ArrayList<Message> messages=new ArrayList<Message>();
	private ArrayList<Fragment> fragments = new ArrayList<Fragment>(); //顺序的总组合片段；
	
	public XMLReaderEA(String xmlFile) {
		
		// TODO Auto-generated constructor stub
		this.xmlFile=xmlFile;
		
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

	/////////////////以下retrieveuseCases()和retrieveDiagrams()是在用例图中执行的函数
	/*
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
					
					useCases.add(useCase);
				}
			}
		}
		return this.useCases;
	}

	/*
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
	
	//以下方法                    是在Diagram对应的顺序图中获取信息的方法，此时root已经变换了
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
				 * lifeLine上的位点没有设置
				 * 考虑是否将生命线上的结点封装在生命线上……………………………………………………???
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
	 * @throws InvalidTagException
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Node> retrieveNodes() throws InvalidTagException
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
				for(Iterator<LifeLine> lifeLineIt=lifeLines.iterator();lifeLineIt.hasNext();)
				{
					LifeLine lifeLine=lifeLineIt.next();
					if(lifeLine.getId().equals(frag.attribute("covered").getText()))
						node.setLifeLineName(lifeLine.getName());
				}
				nodes.add(node);
			}
			
			//如果是组合片段上的位点,则将其抽取出来
			
			if(frag.attribute("type").getText().contains("CombinedFragment"))
			{
				ArrayList<Element> operandList=new ArrayList<Element>();
				operandList.addAll(frag.elements("operand"));
				for(Iterator<Element> operandIt=operandList.iterator();operandIt.hasNext();)
				{
					Element oper=operandIt.next();
					Operand operand=new Operand();
					operand.setId(oper.attribute("id").getText());
					
					//操作下的位点集合
					ArrayList<Element> operFragment=new ArrayList<Element>();
					operFragment.addAll(oper.elements("fragment"));
					for(Element oe:operFragment)//一层组合片段，下面是普通结点，需要重新改编…………………………？？？？
					{
						Node node=new Node();
						node.setId(oe.attribute("id").getText());
						node.setCoverdID( oe.attribute("covered").getText());
						for(LifeLine lifeLine:lifeLines)
						{
							if(lifeLine.getId().equals(oe.attribute("covered").getText()))
							{
								node.setLifeLineName(lifeLine.getName());
							}
						}
						nodes.add(node);
					} 
				}
			}
		}
		return this.nodes;
	}
	
	/**
	 * 获取XML文件中的组合片段信息…………………………………………………………同时生成所有的nodes
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
			if (elFragment.attribute("type").getValue().contains("OccurrenceSpecification")) 
			{
				Node node=new Node();
				node.setId(elFragment.attribute("id").getText());
				node.setCoverdID(elFragment.attribute("covered").getText());
				for(Iterator<LifeLine> lifeLineIt=lifeLines.iterator();lifeLineIt.hasNext();)
				{
					LifeLine lifeL=lifeLineIt.next();
					if(lifeL.getId().equals(elFragment.attribute("covered").getText()))
						node.setLifeLineName(lifeL.getName());
				}
				nodes.add(node);
			}
			
			if(elFragment.attribute("type").getText().contains("CombinedFragment"))
			{
				//设置组合片段的id,name,type,依附生命线                                          
				Fragment localFragment = new Fragment();
				localFragment.setId(elFragment.attribute("id").getValue());
				localFragment.setName(elFragment.attributeValue("name"));
				localFragment.setType(elFragment.attribute("interactionOperator").getValue());
				
				ArrayList<String> coveredIDs = new ArrayList<String>();
				ArrayList<Element> coveredList = new ArrayList<Element>();
				coveredList.addAll(elFragment.elements("covered"));
				for (Iterator<Element> coveredIterator = coveredList.iterator(); coveredIterator.hasNext();) 
				{
					Element elCovered = coveredIterator.next();
					String coveredID = elCovered.attribute("idref").getValue();
					coveredIDs.add(coveredID);
				}
				localFragment.setCoveredIDs(coveredIDs);
				
				//将操作域封装进操作域
				ArrayList<Operand> localOperands = new ArrayList<Operand>();
				ArrayList<Element> operandList = new ArrayList<Element>();
				operandList.addAll(elFragment.elements("operand"));
				//设置每个操作域的id,condition,包含的位点集,包含的位点Id集;…………………………………………还有消息没有封装;
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
					
					ArrayList<Node> localNodes = new ArrayList<Node>();
					ArrayList<String> localNodeIds = new ArrayList<String>();
					ArrayList<Fragment> fragmentsInOperand=new ArrayList<Fragment>();
					ArrayList<Element> childFragmentInOperandList = new ArrayList<Element>();
					childFragmentInOperandList.addAll(elOperand.elements("fragment"));
					for (Iterator<Element> elmentIterator = childFragmentInOperandList.iterator(); elmentIterator.hasNext();)
					{
						Element childFragmentInOperand = elmentIterator.next();
						if(childFragmentInOperand.attributeValue("type").contains("OccurrenceSpecification"))
						{
							Node node = new Node();
							node.setId(childFragmentInOperand.attribute("id").getValue());
							node.setCoverdID(childFragmentInOperand.attribute("covered").getValue());
							for(Iterator<LifeLine> lifeLineIt=lifeLines.iterator();lifeLineIt.hasNext();)
							{
								LifeLine lifeLine=lifeLineIt.next();
								if(lifeLine.getId().equals(childFragmentInOperand.attribute("covered").getText()))
									node.setLifeLineName(lifeLine.getName());
							}
							localNodes.add(node);
							nodes.add(node);
							localNodeIds.add(node.getId());
						}
						if(childFragmentInOperand.attributeValue("type").contains("CombinedFragment"))
						{
							/*
							 * 如果操作域下有组合片段，设置标记，递归调用组合片段获取函数
							 */
							operand.setHasFragment(true);
							if(childFragmentInOperand!=null)
							{
								fragmentsInOperand.add(retrieveChildFragment(childFragmentInOperand));
								
							}
						
						}
						
					}
					operand.setNodeIds(localNodeIds);
					operand.setNodes(localNodes);
					if(fragmentsInOperand!=null)
					{
						operand.setFragments(fragmentsInOperand);
					}
					//还有messages没有封装
					localOperands.add(operand);
				}
				localFragment.setOperands(localOperands);
				this.fragments.add(localFragment);
			}
		}
		return this.fragments;
	}
	
	
	//多层组合片段嵌套,  从此开始循环对组合片段封装--------------
		//利用新的根节点 调用位点和组合片段获取函数
		
		
		 /*
		  * 
		  *^^^^^^^^^^^^^^^^^^^^^^^^操作域下的位点片段+组合片段
		  * 
		  * 初始化当前组合片段集合对象localFrgment；
		  * 对localFragment设置id,name,type,依附生命线
		  *
				  * 初始化operands;
				  * 获取所有operand所在的包；
				  * for(遍历当前层嵌套的每一条操作域operand)
				  *         设置操作ID
				  *         设置操作执行Condition
				  *         初始化 localNodes用于存储当前操作域中一般的位点
				  *         初始化 localNodeIds用于存储当前操作域中的一般位点ID
				  *         获取该操作域下的所有fragment
				  *         for(遍历每一条fragment)
				  *            如果是一般位点
				  *               将该位点加入localNodes;
				  *               将该位点加入全局nodes中;
				  *               将该位点相应ID加入localNodeIds;
				  *            如果是组合片段
				  *               给当前操作域的isHasFragment=true;(默认为false)
				  *               调用函数encapsulationFragment(当前fragment组合片段所在的包结点),
				  *                                    返回的组合片段添加到当前操作域下的组合片段集中;………
				  *         end;
				  *         将localNodes封装入operand;
				  *         将localNodeIds封装如operand;
				  * end
				  * 将operands封装入当前层localFragment； 
			    *将localFragment封装入localFrgments;
			end
		  * 
		  * 
		  */
	
	@SuppressWarnings("unchecked")
	public Fragment retrieveChildFragment(Element newRoot)
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
			ArrayList<Fragment> fragmentsInOperand=new ArrayList<Fragment>();   //改动
			operand.setId(elOperand.attribute("id").getValue());
			if (elOperand.element("guard").element("specification").attribute("body") != null)
			{
				operand.setCondition(elOperand.element("guard").element("specification").attribute("body").getValue());
			}
			ArrayList<Node> localNodes = new ArrayList<Node>();
			ArrayList<String> localNodeIds = new ArrayList<String>();		
			ArrayList<Element> childFragmentInOperandList = new ArrayList<Element>();
			childFragmentInOperandList.addAll(elOperand.elements("fragment"));
			for (Iterator<Element> elmentIterator = childFragmentInOperandList.iterator(); elmentIterator.hasNext();)
			{
				Element childFragmentInOperand = elmentIterator.next();
				if(childFragmentInOperand.attributeValue("type").contains("OccurrenceSpecification"))
				{
					Node node = new Node();
					node.setId(childFragmentInOperand.attribute("id").getValue());
					node.setCoverdID(childFragmentInOperand.attribute("covered").getValue());
					for(Iterator<LifeLine> lifeLineIt=lifeLines.iterator();lifeLineIt.hasNext();)
					{
						LifeLine lifeLine=lifeLineIt.next();
						if(lifeLine.getId().equals(childFragmentInOperand.attribute("covered").getText()))
							node.setLifeLineName(lifeLine.getName());
					}
					localNodes.add(node);
					nodes.add(node);
					localNodeIds.add(node.getId());
				}
				if(childFragmentInOperand.attributeValue("type").equals("uml:CombinedFragment"))
				{
							/*
							 * 如果操作域下有组合片段，设置标记，递归调用组合片段获取函数
							 */
					operand.setHasFragment(true);
					fragmentsInOperand.add(retrieveChildFragment(childFragmentInOperand));		
				}	
			}
			
			operand.setNodeIds(localNodeIds);
			operand.setNodes(localNodes);
			if(operand.isHasFragment()==true)
			{
				operand.setFragments(fragmentsInOperand);
			}
			//还有messages没有封装
			localOperands.add(operand);
			
		}
		
		localFragment.setOperands(localOperands);
		return localFragment;
		
	}
	
	/*
	 * 获取所有消息message
	 * 并将组合片段里面各个操作域对应的消息封装进操作域
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
			if(elMessage==messageList.get(messageList.size()-1))
				message.setLast(true);//最后一条信息设置isLast为真；
			
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
				//在名字当中寻找   参数类型
				if(tag==1)
				{
					int k=0;
					for(int i=0;i<(message.getName()).length();i++)
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
				//寻找名字
				List<Element> argumentElements=new ArrayList<Element>();
				argumentElements.addAll(elMessage.elements());
				for(Element e:argumentElements)
				{
					if(e.getName().equals("argument"))
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
			 */
			for (Fragment fragment : fragments) 
			{
				for (Operand operand : fragment.getOperands()) 
				{
					if(message.isInFragment()==false)
					{
						if(operand.isHasFragment()==true)
						{
							//因为此处消息是局部变量，需要返回一个有关于消息的值；
							isInChildFragment(message,operand,fragment);
							
						}
						
						//出错点…………
						else
						{
							for (Node node : operand.getNodes()) 
							{
								if (node.getId().equals(message.getSenderID())) 
								{
									message.setInFragment(true);
									message.setFragmentId(fragment.getId());
									message.setFragType(fragment.getType());
									message.setOperandId(operand.getId());
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
			 */
			ArrayList<Element> connectorList = new ArrayList<Element>();
			connectorList.addAll(root.element("Extension").element("connectors").elements("connector"));

			for (Iterator<Element> connectorIterator = connectorList.iterator(); connectorIterator.hasNext();) 
			{
				Element elConnector = connectorIterator.next();
				//if消息的id和链接的id相等
				// 第一，判断消息属于哪个组合片段
				//第二，可以获取标签label的字符串中的返回值和返回值类型
				if (message.getId().equals(elConnector.attribute("idref").getValue())) 
				{
					if (elConnector.element("documentation").hasContent())//是否需要变化判断条件，去掉叹号（后续输出看看有何不同判断一下）…………………………？？？？？
					{
						if (elConnector.element("documentation").attribute("value") != null) 
						{
							message.setFragFlag(elConnector.element("documentation").attribute("value").getValue());
						}
					}
					Element extendedProperties=elConnector.element("extendedProperties");
					if(extendedProperties.attribute("conditional")!=null)
					{
						String domainsStr=extendedProperties.attribute("conditional").getText()+",";
						stimulate.setDomains(SerachParametersType(domainsStr));
					}
					if(extendedProperties.attribute("constraint")!=null)
					{
						String expressionsStr=extendedProperties.attribute("constraint").getText()+",";
						stimulate.setConstraintExpresstions(SerachParametersType(expressionsStr));
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
				}//消息还有其他的属性没有设置(probability,exectime,notation,fragmentId,fragType,islast)
			}
			message.setStimulate(stimulate);
			messages.add(message);
		}
		encapsulationMessages(this.fragments);//给组合片段里面的操作封装消息
		return this.messages;
	}
	
	//设置深层信息是否有组合片段，组合片段ID，组合片段类型，所属操作ID；
	public void isInChildFragment(Message message,Operand operand,Fragment fragment)
	{
		
		for (Node node : operand.getNodes()) 
		{
			if (node.getId().equals(message.getSenderID())) 
			{
				
				message.setInFragment(true);
				message.setFragmentId(fragment.getId());
				message.setFragType(fragment.getType());
				message.setOperandId(operand.getId());
				
			}
				
		}
		if(message.isInFragment()==false)
		{
			if(operand.isHasFragment()==true)
			{
				ArrayList<Fragment> messageFragments=new ArrayList<Fragment>();
				messageFragments=operand.getFragments();
				for(Fragment messageFragment:messageFragments)
				{
					ArrayList<Operand> messageOperands=new ArrayList<Operand>();
					messageOperands=messageFragment.getOperands();
					for(Operand messageOperand:messageOperands)
					{
						isInChildFragment(message,messageOperand,messageFragment);
					}
				}
				
			}
		}
	}
	
	/*
	 * 将各个消息封装在相应的操作域下面
	 * 进入每一个操作域，每进一个操作域，要遍历一次所有的消息，判断该消息是否属于该操作域
	 */
	public void encapsulationMessages(ArrayList<Fragment> newFragments) 
	{
				
		for (Fragment fragment : newFragments) 
		{
			for (Operand operand : fragment.getOperands()) 
			{
				ArrayList<Message> operandMessages = new ArrayList<Message>();
				for (Message message : messages) 
				{
					if (operand.getNodeIds().contains(message.getSenderID())
							&& operand.getNodeIds().contains(message.getReceiverID()))
					{
						operandMessages.add(message);
					}
				}
				//System.out.println(operandMessages);
				operand.setMessages(operandMessages);
				
				if(operand.isHasFragment()==true)
				{
					ArrayList<Fragment> childFragments=new ArrayList<Fragment>();
					childFragments.addAll(operand.getFragments());
					encapsulationMessages(childFragments);
				}
			}
		}
		
	}
	public List<String> SerachParametersType(String typeStr)
	{
		List<String> types=new ArrayList<String>();
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
