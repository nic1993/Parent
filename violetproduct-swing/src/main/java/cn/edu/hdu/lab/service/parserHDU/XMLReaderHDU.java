package cn.edu.hdu.lab.service.parserHDU;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.edu.hdu.lab.config.StaticConfig;
import cn.edu.hdu.lab.dao.uml.Diagram;
import cn.edu.hdu.lab.dao.uml.DiagramsData;
import cn.edu.hdu.lab.dao.uml.Fragment;
import cn.edu.hdu.lab.dao.uml.LifeLine;
import cn.edu.hdu.lab.dao.uml.Message;
import cn.edu.hdu.lab.dao.uml.Node;
import cn.edu.hdu.lab.dao.uml.Operand;
import cn.edu.hdu.lab.dao.uml.REF;
import cn.edu.hdu.lab.dao.uml.SD;
import cn.edu.hdu.lab.dao.uml.SDRectangle;
import cn.edu.hdu.lab.dao.uml.Stimulate;
import cn.edu.hdu.lab.dao.uml.UseCase;
import cn.edu.hdu.lab.service.parserEA.FixFragmentTool;
import cn.edu.hdu.lab.service.parserEA.UMLReader;

/**
 * 读取平台UML模型
 * @author Terence
 *
 */
public class XMLReaderHDU {
	private String xmlFile; //用例图路径名
	private String fileName;//UML模型所在包名
	private Element root;  //XML文件根节点
	
	
	//private ArrayList<Behavior> behaviors;
	
	private ArrayList<Diagram> diagrams=new ArrayList<Diagram>();	
	private ArrayList<LifeLine> lifeLines=new ArrayList<LifeLine>();
	private ArrayList<Node> nodes=new ArrayList<Node>();
	private ArrayList<Message> messages=new ArrayList<Message>();
	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
	
	private ArrayList<REF> umlREF = new ArrayList<REF>(); //引用信息
	static ArrayList<DiagramsData> umlAllDiagramData = new ArrayList<DiagramsData>();	
	private static int idCount=0;
	
	public XMLReaderHDU(){}

	/**
	 * 
	 * @param xmlFile 用例图路径名称
	 * @throws Exception
	 */
	public XMLReaderHDU(String xmlFile) throws Exception {
		super();
		this.xmlFile = xmlFile;
		this.fileName=StaticConfig.umlPathPrefixHDU;
		try
		{
			SAXReader reader=new SAXReader();
			Document dom=reader.read(xmlFile);
			root=dom.getRootElement();
		}
		catch(Exception e)
		{
			throw new Exception("文件读取异常，请查看文件是否存在，或路径名是否正确；\n可能原因："+e);
		}
	}

	public List<UseCase> parser() throws Throwable
	{
		
		List<UseCase> useCases=new ArrayList<UseCase>(); 
		useCases=readUCInformation();
		readSequencesInformation(useCases);
		return useCases;
	}
	
	/**
	 * 读取用例信息
	 * @return 返回用例集合
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	private List<UseCase> readUCInformation() throws Throwable
	{
		List<UseCase> useCases=new ArrayList<UseCase>(); 
		if(root==null)
		{
			throw new Exception("Exception:The document's root is null");
			
		}
		else
		{
			List<Element> ucElementsList=new ArrayList<Element>();
			ucElementsList.addAll(root.element("nodes").elements("UseCaseNode"));
			
			
			for(Iterator<Element> it=ucElementsList.iterator();it.hasNext();)
			{
				UseCase uc=new UseCase();
				
				//设定 用例的ID、name、preCondition、probability;
				Element e=it.next();
				//添加用例ID Name 前置条件
				uc.setUseCaseID(e.attributeValue("id"));
				uc.setUseCaseName(e.element("name").elementText("text"));
				
				//添加用例约束条件
				Element constraintsElement=e.element("useConstraint").element("constraints");
				if(constraintsElement.hasContent())
				{
					List<Element> constraintElementList=constraintsElement.elements("com.horstmann.violet.product.diagram.abstracts.property.Usecaseconstraint");
					for(Element e1:constraintElementList)
					{
						if(e1.hasContent())
						{
							//添加用例前置条件
							//if(e1.element("type").getText().equals("pre-condition")&&
									if(e1.element("name").getText().equals("preCondition"))
							{
								uc.setPreCondition(e1.elementText("content"));
								break;
							}
							else
							{
								throw new Exception("用例约束条件获取异常，可能原因：约束类型或约束名称错误");
							}
						}
					}										
				}
				
				//向用例 封装场景信息，待修改
				//平台用例图下没有Diagrams，所以此项为空
				if(e.element("sceneConstraint").hasContent()
						&&e.element("sceneConstraint").element("constraints").hasContent()
						&&e.element("sceneConstraint").element("constraints").element("com.horstmann.violet.product.diagram.abstracts.property.SequenceConstraint").hasContent())
				{
					List<Element> constraintsElementList=e.element("sceneConstraint").element("constraints").elements();
					
					List<Element> seqList=e.element("sceneConstraint").element("sequenceName").elements("string");
					if(seqList!=null&&seqList.size()>0)
					{
						ArrayList<SD> sdSets=new ArrayList<SD>();
						for(Element tempE:seqList)
						{
							if(tempE.getName().equals("string"))
							{
								SD sd=new SD();
								sd.setId(e.element("sceneConstraint").element("sequenceName").attributeValue("id")+"_"+tempE.getText().trim());
								sd.setName(tempE.getText().trim());
								if(constraintsElementList.size()>0)
								{
									for(Element conE:constraintsElementList)
									{
										if(conE.hasContent()
												&&conE.element("sequenceName").getText()!=null
												&&conE.element("name").getText()!=null
												&&conE.element("content").getText()!=null)
										{
											if(sd.getName().equals(conE.element("sequenceName").getText()))
											{
												if(conE.element("name").getText().equals("postCondition"))
												{
													sd.setPostSD(conE.element("content").getText());
												}
												if(conE.element("name").getText().equals("probability"))
												{
													sd.setProb(Double.parseDouble(conE.element("content").getText()));
												}
											}
										}
									}
								}
								sdSets.add(sd);
							}						
						}
						uc.setSdSets(sdSets);						
					}				
				}
//				System.out.print("用例："+uc.getUseCaseName());
//				System.out.println("----场景:"+uc.getSdSets().get(0).getName());				
      			useCases.add(uc);	
				
			}
			return useCases;			
		}
	}
	
	/**
	 * 带入用例信息，寻找对应场景，解析对应场景XML文件，并将其封装
	 * @param useCases
	 * @throws Throwable
	 */
	private void readSequencesInformation(List<UseCase> useCases) throws Throwable 
	{
		System.out.println("=================================解析场景===============================");
		for(UseCase uc:useCases)
		{
			File file=new File(fileName);
			File[] tempFileList=file.listFiles(); //获取该包下的所有文件			
			
			for(File f:tempFileList)
			{
				if(f.isFile()&&f.getName().contains("seq.violet.xml"))
				{
					
					String sequenceFileName=fileName+"\\"+f.getName();
					//System.out.println(sequenceFileName);
					DiagramsData dd=parserSequence2DiagramData(sequenceFileName);
					//System.out.println(f.getName().substring(0, f.getName().indexOf(".seq.violet.xml")));
					dd.setName(f.getName().substring(0, f.getName().indexOf(".seq.violet.xml")));
					umlAllDiagramData.add(dd);
				}
			}
			
			/*for(DiagramsData diagramData : umlAllDiagramData) 
			{
				System.out.println("\n顺序图名称："+diagramData.getName());
					for(LifeLine l:diagramData.getLifelineArray())
					{
						l.print_LifeLine();
					}
					for(Message m:diagramData.getMessageArray())
					{
						m.print_Message();
					}
					for(Fragment frag:diagramData.getFragmentArray())
					{
						frag.print_Fragment();
					}
					System.out.println("引用消息：");
					for(REF r:diagramData.getRefArray())
					{
						System.out.println(r.toString());
					}	
					System.out.println("*****所有引用消息：");
					for(REF r:diagramData.getTempRefArray())
					{
						System.out.println(r.toString());
					}
					
			}*/
			
			//深度复制，组合顺序图：将子图嵌套进主图当中			
			for(SD sd:uc.getSdSets())
			{
				
				assembleInfo2DiffDiagram(sd);
				
				System.out.println("*****************主场景信息**************");				
				sd.print_SDSet();			
				
			}
		}
	}
	
	/**
	 * 解析所有的顺序图对应文件，
	 * @param sequenceFileName 顺序图对应的文件全名
	 * @return 每个顺序图对应的存储结构
	 * @throws Exception 
	 */
	private DiagramsData parserSequence2DiagramData(String sequenceFileName) throws Exception
	{
		DiagramsData dd=new DiagramsData();
		//识别不了别的文件，只能识别xml文件
		SAXReader reader=new SAXReader();
		Document dom=reader.read(sequenceFileName);
		Element sdRoot=dom.getRootElement();
		if(sdRoot==null)
		{
			throw new Exception("文件内容异常.");
		}
		else
		{ 
			//umlAllDiagramData			
			
			if(sdRoot.getName().contains("SequenceDiagramGraph"))
			{
				if(sdRoot.element("nodes").hasContent())
				{
					//获取生命线集合
					dd.setLifelineArray(retrieveLifeLine(sdRoot));
					
					//获取结点集合
					dd.setNodes(retrieveNodes(dd.getLifelineArray()));
					
					//获取消息集合
					dd.setMessageArray(retrieveMessages(sdRoot,dd));	
					
					//获取引用片段引用
					dd.getRefArray().addAll(retrieveRefs(sdRoot));
					dd.getTempRefArray().addAll(dd.getRefArray()); //备份所有引用
					
					//获取修正(组合片段之间的嵌套，引用和组合片段之间的嵌套)后的组合片段集合
					dd.setFragmentArray(retrieveFragments(sdRoot,dd));	
				}
			}
			return dd;
		}
	}
		
		/**
		 * 
		 * @param root :SequenceDiagramGraph结点
		 * @return ：生命线集合
		 * @throws Exception 
		 */
		@SuppressWarnings("unchecked")
		private ArrayList<LifeLine> retrieveLifeLine(Element root) throws Exception
		{
			List<Element> lifeElementList=root.element("nodes").elements("LifelineNode");
			ArrayList<LifeLine> lifeLineList=new ArrayList<LifeLine>();
			if(lifeElementList.size()>0)
			{				
				for(Iterator<Element> it=lifeElementList.iterator();it.hasNext();)
				{
					Element tempE=it.next();
					LifeLine lifeLine=new LifeLine(tempE.attributeValue("id"),
							tempE.element("name").elementText("text"));					
					
					//封装该生命线上包含的结点
					ArrayList<Node> ownedNodeList=retrieveLifeLineNodes(tempE);					
					lifeLine.setNodes(ownedNodeList);			
					lifeLineList.add(lifeLine);
				}
			}
			else
			{
				throw new Exception("生命线信息异常");
			}
			return lifeLineList;
		}
		
		/**
		 * 获取该生命线下所有结点
		 * @param root :生命线结点
		 * @return ：所有结点集合
		 */
		@SuppressWarnings("unchecked")
		private ArrayList<Node> retrieveLifeLineNodes(Element lifeLineRoot) 
		{
			ArrayList<Node> nodesList=new ArrayList<Node>();
			String lifeLineID=lifeLineRoot.attributeValue("id");
			String lifeLineName=lifeLineRoot.element("name").elementText("text");
			if(lifeLineRoot.element("children").hasContent())				
			{
				List<Element> nodeElementList=lifeLineRoot.element("children").elements("ActivationBarNode");
				for(Element e:nodeElementList)
				{
					Node node=new Node(e.attributeValue("id"),lifeLineID) ;
					node.setLifeLineName(lifeLineName);
					nodesList.add(node);
					if(e.element("children").hasContent())
					{
						nodesList.addAll(dfsSearchNodes(e,lifeLineID,lifeLineName));
						
					}
				}				
			}
			return nodesList;
		}
		private ArrayList<Node> dfsSearchNodes(Element e,String lifeLineID,String lifeLineName)
		{
			ArrayList<Node> nodesList=new ArrayList<Node>();
			@SuppressWarnings("unchecked")
			List<Element> nodeElementList=e.element("children").elements("ActivationBarNode");
			for(Element e2:nodeElementList)
			{
				Node node=new Node(e2.attributeValue("id"),lifeLineID) ;
				node.setLifeLineName(lifeLineName);
				nodesList.add(node);
				if(e2.element("children").hasContent())
				{
					nodesList.addAll(dfsSearchNodes(e2,lifeLineID,lifeLineName));					
				}
			}
			return nodesList;
		}
		/**
		 * 根据所有生命线下的结点，获取所有结点
		 * @param lifeLineList
		 * @return
		 */
		private ArrayList<Node> retrieveNodes(ArrayList<LifeLine> lifeLineList) 
		{
			ArrayList<Node> nodesList=new ArrayList<Node>();
			if(lifeLineList.size()>0)
			{
				for(LifeLine lifeLine:lifeLineList)
				{
					if(lifeLine.getNodes().size()>0)
					{
						for(Node node:lifeLine.getNodes())
						{
							nodesList.add(node);
						}
					}
				}
			}
			return nodesList;			
		}
		
		/**
		 * 获取消息
		 * @param root
		 * @param sd
		 * @return 每个顺序图中的消息
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		private ArrayList<Message> retrieveMessages(Element root,DiagramsData sd) throws Exception
		{			
			//封装消息
			ArrayList<Element> messageElementList=(ArrayList<Element>) root.element("edges").elements("CallEdge");
			ArrayList<Element> returnMessageElementList=(ArrayList<Element>) root.element("edges").elements("ReturnEdge");
			ArrayList<Message> messageList=new ArrayList<Message>();
			
			for(Element e:messageElementList)
			{
				
				Message message=new Message();
				message.setId(e.elementText("ID"));
				message.setName(e.elementText("message"));				
				message.setSenderID(searchLifeLine(e.element("start"),sd).getId());
				
				message.setReceiverID(searchLifeLine(e.element("end"),sd).getId());
				
				message.setSender(searchLifeLine(e.element("start"),sd).getName());
				message.setReceiver(searchLifeLine(e.element("end"),sd).getName());
								
				message.setReturnValue(e.elementText("assign"));
				message.setReturnValueType(e.elementText("returnvalue"));
				message.setNoteID(e.elementText("ID"));
				
				//激励内容……
				Stimulate stimulate=new Stimulate();
				
				if(e.element("message").getText()!=null
						&&e.element("parameters").getText()!=null)
				{
					//参数类型列表
					String parametersTypeStr=handleParameterStr(message.getName().trim());
					stimulate.setParameterTypeList(SerachParametersType(parametersTypeStr));
//					System.out.println(message.getName()+"----"+stimulate.getParameterTypeList().size());
					//参数名字列表
					stimulate.setParameterNameList(SerachParametersType(e.element("parameters").getText()+","));
					
					//参数定义域
					if(e.element("condition").getText()!=null)
					{
						setStimulate(stimulate,e.element("condition").getText());
					}
					else
					{
						throw new Exception("参数没有定义域，对应消息为："+message.getName());
					}
				}
				message.setStimulate(stimulate);
				if(e.element("timeconstraint").hasContent())
				{
					message.setFromTimeConstraint(e.element("timeconstraint").getText());
				}
				
				message.setPointY(Double.parseDouble(e.element("startLocation").attributeValue("y")));
				
				//message.setProb(sd.getProb());
				/*if(e.equals(messageElementList.get(messageElementList.size()-1)))
				{
					message.setLast(true);
				}*/
				
				//消息是否在组合片段中，消息所属组合片段类型，所属组合片段ID，所属组合片段操作ID，进出组合片段的标记
				
				//消息属性不全
				//………………
				//执行时间				
				messageList.add(message);
			}
			for(Element e:returnMessageElementList)
			{
				
				Message message=new Message();
				message.setId(e.elementText("ID"));
				message.setName(e.elementText("message"));				
				message.setSenderID(searchLifeLine(e.element("start"),sd).getId());
				
				message.setReceiverID(searchLifeLine(e.element("end"),sd).getId());
				
				message.setSender(searchLifeLine(e.element("start"),sd).getName());
				message.setReceiver(searchLifeLine(e.element("end"),sd).getName());
								
				message.setReturnValue(e.elementText("assign"));
				message.setReturnValueType(e.elementText("returnvalue"));
				message.setNoteID(e.elementText("ID"));
				
				//激励内容……
				Stimulate stimulate=new Stimulate();
				
				if(e.element("message").getText()!=null
						&&e.element("parameters").getText()!=null)
				{
					//参数类型列表
					String parametersTypeStr=handleParameterStr(message.getName().trim());
					stimulate.setParameterTypeList(SerachParametersType(parametersTypeStr));
//					System.out.println(message.getName()+"----"+stimulate.getParameterTypeList().size());
					//参数名字列表
					stimulate.setParameterNameList(SerachParametersType(e.element("parameters").getText()+","));
					
					//参数定义域
					if(e.element("condition").getText()!=null)
					{
						setStimulate(stimulate,e.element("condition").getText());
					}
					else
					{
						throw new Exception("参数没有定义域，对应消息为："+message.getName());
					}
				}
				message.setStimulate(stimulate);
				if(e.element("timeconstraint").hasContent())
				{
					message.setFromTimeConstraint(e.element("timeconstraint").getText());
				}
				message.setPointY(Double.parseDouble(e.element("startLocation").attributeValue("y")));
				
				//message.setProb(sd.getProb());
				if(e.equals(messageElementList.get(messageElementList.size()-1)))
				{
					message.setLast(true);
				}
				
				//消息是否在组合片段中，消息所属组合片段类型，所属组合片段ID，所属组合片段操作ID，进出组合片段的标记
				
				//消息属性不全
				//………………
				//执行时间				
				messageList.add(message);
			}
			//对消息按照坐标进行一次排序
			FixTool.sortMesses(messageList);
			return messageList;
		}
		
		/**
		 * 获取顺序图中所有的引用片段
		 * @param sdRoot
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		private ArrayList<REF> retrieveRefs(Element sdRoot) throws Exception
		{
			ArrayList<REF> refs=new ArrayList<REF>();
			List<Element> refNodes=new ArrayList<Element>();
			refNodes.addAll(sdRoot.element("nodes").elements("RefNode"));
			for(Element e:refNodes)
			{
				REF ref=new REF();
				ref.setRefID(e.elementText("ID"));
				ref.setRefName(e.element("text").elementText("text"));
				ref.setRectangle(retrieveFragmentSDRectangle(e));
				refs.add(ref);
			}
			return refs;
			
		}
		/**
		 * 获取顺序图第一层组合片段（第一层组合片段已经嵌套完毕）
		 * @param root：SequenceDiagramGraph根节点
		 * @param sd：场景
		 * @return
		 */
		@SuppressWarnings("unchecked")
		private ArrayList<Fragment> retrieveFragments(Element root,DiagramsData dd)
		{
			ArrayList<Fragment> firstFragmentsList=new ArrayList<Fragment>();
			List<Element> firstLevelFragmentsList=new ArrayList<Element>();
			firstLevelFragmentsList.addAll(root.element("nodes").elements("CombinedFragment"));
			for(Element e:firstLevelFragmentsList)
			{
				//组合片段id,name,type
				Fragment fragment=new Fragment();
				firstFragmentsList.add(fragment);
				
				fragment.Set(e.elementText("ID"),e.elementText("fragmentType").toLowerCase());
				fragment.setName(e.elementText("name"));
				
				//操作集合：封装操作
				ArrayList<Operand> operandList=new ArrayList<Operand>();
				fragment.setOperands(operandList);//此条放在前面后面均可以,因为加入的是引用，不是空间存储值。
				
				ArrayList<Element> operandElementList= new ArrayList<Element>();
				operandElementList.addAll(e.element("fragmentParts").elements("com.horstmann.violet.product.diagram.abstracts.property.FragmentPart"));
				for(Element operandE:operandElementList)
				{
					Operand operand =new Operand();
					operandList.add(operand);
					operand.setCondition(operandE.elementText("conditionText"));
					operand.setId(operandE.attributeValue("id"));					
					operand.setRectangle(retrieveOperandSDRectangle(operandE));
					
				}
				//组合片段坐标获取
				fragment.setRectangle(retrieveFragmentSDRectangle(e));
				
			}
			//对组合片段封装消息，并修正组合片段嵌套关系和 引用片段与组合片段操作的关系
			FixTool.fixFragmentsOfOneDiagram(firstFragmentsList,dd);
			return firstFragmentsList;
		}
		
		
		/**
		 * 处理约束条件字符串
		 * @param name
		 * @return
		 */
		private String handleParameterStr(String name)
		{
			name=name.trim();
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
				for(int i=0;i<name.length();i++)
				{
					if(name.charAt(i)=='(')
					{
						k=i;
						break;
					}
					
				}
				String parametersTypeStr=name.substring(k+1,name.length()-1)+",";	
				return parametersTypeStr;
			}
			return "";
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
		@SuppressWarnings("null")
		private void setStimulate(Stimulate stimulate,String str)
		{
			if(str==null||str.trim().equals(""))
			{
				//System.out.println("null");
				return;
			}
			
//			System.out.println(stimulate);
//			System.out.println("***********"+str.trim());
			String[] strs=str.trim().replaceAll("\r|\n","").split("#");
			for(String s:strs)
			{
				if(!s.equals(""))
				{
//					System.out.println("*"+s);
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
		private List<String> SerachSubString(String str)
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
		 * 寻找该节点所依附的生命线
		 * @param e 
		 * @param sd 场景对应的顺序图
		 * @return 
		 */
		private LifeLine searchLifeLine(Element e,DiagramsData sd)
		{
			
			String ref=e.attributeValue("reference");
			for(LifeLine l: sd.getLifelineArray())
			{
				if(l.getNodes().size()>0)
				{
					for(Node node:l.getNodes())
					{
						if(ref.equals(node.getId()))
						{
						   return l;	
						}
					}
				}				
			}		
			return null;
		}
		
		
	/**
	 * 根据消息的附属ID，获取顺序图中对应的消息
	 * @param e :
	 * @return 
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private ArrayList<Message>searchMessagesByID(Element e,DiagramsData sd)
	{
		ArrayList<Message> messageList=new ArrayList<Message>();
		ArrayList<String> messageIDs=new ArrayList<String>();
		List<Element> messageIDElementList=e.elements("string");		
		//根据大ID寻找对应的Message
		for(Element strIDElement:messageIDElementList)
		{
			messageIDs.add(strIDElement.getText());
		}
		for(String str:messageIDs)
		{
//			System.out.println("场景内消息数量："+sd.getMessages().size());
			for(Message mess:sd.getMessageArray())
			{
				if(str.equals(mess.getNoteID()))
				{
					messageList.add(mess);
				}
			}
		}
		return messageList;
	}
	
	/**
	 * 将组装好组合片段内的
	 * 组合片段集、消息集、结点集、结点id集
	 * @param firstFragmentsList ：场景最外层组合片段集合
	 */
	@SuppressWarnings("unused")
	private void resetFragmentsInfo(ArrayList<Fragment> firstFragmentsList)
	{
		
		//重置：封装组合片段
		
		for(Fragment fragment:firstFragmentsList)
		{
			//递归寻找每个组合片段操作下包含组合片段的次数，并将count=1的组合片段封装到该操作下。				
			for(Operand oper:fragment.getOperands())
			{
				if(!oper.isHasFragment()) continue;		
				
				//创建当前操作的嵌套标志数组，并统计包含组合片段ID出现的次数
				FlagDao[] fd=new FlagDao[oper.getFragmentIDs().size()];
				for(String str:oper.getFragmentIDs())
				{
					fd[oper.getFragmentIDs().indexOf(str)].setCount(1);
					fd[oper.getFragmentIDs().indexOf(str)].setID(str);
				}
				for(String strID:oper.getFragmentIDs())
				{
					// 找到操作下每个ID对应组合片段下的操作中的ID集合，统计次数
					countFragments(strID,fd,firstFragmentsList);
				}
				
				//将所有ID统计次数为1的的组合片段封装在该操作下;
				for(int i=0;i<fd.length;i++)
				{
				    if(fd[i].getCount()==1)
				    {
				    	for(Fragment f:firstFragmentsList)
				    	{
				    		if(fd[i].getID().equals(f.getId()))
				    		{
				    		    oper.addFragment(f);
				    		    f.setEnFlag(true);
				    			break;
				    		}
				    	}
				    }
				}
			}		
		}		
		//过滤掉非场景直系组合片段部分。	
		for(Fragment fragment:firstFragmentsList)
		{
			if(fragment.isEnFlag())
			{
				firstFragmentsList.remove(fragment);
			}
		}
	}
	/**
	 * 统计当前组合片段下操作中对应组合片段ID，有则+1；
	 * @param fd：统计标记
	 * @param firstFragmentsList：所有组合片段
	 */
	private void countFragments(String fragID,FlagDao[] fd,ArrayList<Fragment> firstFragmentsList)
	{
		for(Fragment f:firstFragmentsList)
		{
			if(fragID.equals(f.getId()))
			{
				for(Operand oper:f.getOperands())
				{
					if(!oper.isHasFragment()) continue;	
					for(String strID:oper.getFragmentIDs())
					{
						for(int i=0;i<fd.length;i++)
						{
							if(strID.equals(fd[i].getID()))
							{
								fd[i].setCount(fd[i].getCount()+1);
								break;
							}
						}
					}	
					
				}
				break;				
			}
		}
		
	}
	
	/**
	 * 组装信息：将顺序图子图嵌套进主图当中
	 * @param sd
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	private static void assembleInfo2DiffDiagram(SD sd) throws Exception
	{
		for(DiagramsData diagramData : umlAllDiagramData) 
		{
			if(diagramData.getName().equals(sd.getName()))
			{

				//System.out.println("\n主图："+diagramData.getName());
				DFSDiagramByREF(diagramData);
				diagramData.getMessageArray().get(diagramData.getMessageArray().size()-1).setLast(true);
				//消息指针重定向
				redirectMessage(diagramData);
				//尾消息标志设置
				diagramData.getMessageArray().get(diagramData.getMessageArray().size()-1).setLast(true);
				
				//tagLastMessage(diagramData)
				//收集消息的所有执行条件
				UMLReader.searchOperConditionOfMess(diagramData);
				//赋给主顺序图的保留变量
				sd.setLifeLines(diagramData.getLifelineArray());
				sd.setMessages(diagramData.getMessageArray());
				
				sd.setFragments(diagramData.getFragmentArray());
				sd.setNodes(diagramData.getNodes());
				break;
			}
		}
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
			
			//设置引用在顺序图中的两种相对位置(Mindex和Findex)
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
				//根据Mindex引入引用片段所代表的子图
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
	private static DiagramsData findDiagramByName(String refName) {
		
		for(DiagramsData diagramsData :umlAllDiagramData)
		{
			if (diagramsData.getName().equals(refName))//包含这个子id
				return diagramsData;
		}
		return null;
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
				//System.out.println("深度复制："+op.getRef().getRefName());
				DiagramsData childDiagram = findDiagramByName(op.getRef().getRefName());
				if(childDiagram.getTempRefArray().size()>0)
				{
					DFSDiagramByREF(childDiagram);//回调继续处理子图
				}
				fixFragmentWithRef(diagramData,f,op,op.getRef());
				diagramData.getTempRefArray().remove(op.getRef());
				op.setRef(null);//清除引用片段的引用，便于递归的返回判断
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
			if(ref.getRefName().equals(childDiagram.getName()))
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
	
	/*private static void searchOperConditionOfMess(DiagramsData d)
	{
		for(Message message:d.getMessageArray())
		{
			if(message.isInFragment())
			{
				for(Fragment frag:d.getFragmentArray())
				{
					List<String> operConditions=retrieveOperConditions(frag,message);
					if(operConditions!=null&&operConditions.size()>0)
					{
						String condition=new String("");
						for(String str:operConditions)
						{
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
		}
		for(Message message:d.getMessageArray())
		{
			if(message.isInFragment()||(message.getFragmentId()!=null||message.getFragmentId()!=""))
			{
				
			}
		}		
	}*/

	/**
	 * 获取组合片段坐标
	 * @param e
	 * @return
	 */
	private SDRectangle retrieveFragmentSDRectangle(Element e)
	{
		SDRectangle rec=new SDRectangle();
		rec.setLeft(Double.parseDouble(e.element("location").attributeValue("x")));
		rec.setTop(Double.parseDouble(e.element("location").attributeValue("y")));
		rec.setRight(rec.getLeft()+Double.parseDouble(e.element("width").getText()));
		rec.setBottom(rec.getTop()+Double.parseDouble(e.element("height").getText()));		
		return rec;		
	}
	
	/**
	 * 获取操作域坐标
	 * @return
	 */
	private SDRectangle retrieveOperandSDRectangle(Element operE)
	{
		SDRectangle rec=new SDRectangle();
		rec.setLeft(Double.parseDouble(operE.element("borderline").elementText("x1")));
		rec.setTop(Double.parseDouble(operE.element("borderline").elementText("y1")));
		rec.setRight(Double.parseDouble(operE.element("borderline").elementText("x2")));
		rec.setBottom(Double.parseDouble(operE.element("size").getText())+Double.parseDouble(operE.element("borderline").elementText("y2")));		
		return rec;
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
