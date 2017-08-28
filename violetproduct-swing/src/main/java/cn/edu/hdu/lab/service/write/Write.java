package cn.edu.hdu.lab.service.write;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.horstmann.violet.application.gui.MainFrame;

import cn.edu.hdu.lab.dao.tmc.State;
import cn.edu.hdu.lab.dao.tmc.Tmc;
import cn.edu.hdu.lab.dao.tmc.Transition;
import cn.edu.hdu.lab.dao.uml.Stimulate;

public class Write {
	private static String FileName;
	public Write(){}
	
	//*******杨杰*****************************************************************************
	public void writeMarkovToXMLofYangJie(Tmc tmc) throws IOException
	{
		//1.创建document对象，代表整个文档
		Document document=DocumentHelper.createDocument();
		//2.创建根节点并未根节点添加属性
		Element model=document.addElement("markov");
		model.addAttribute("version", "1.0");
		model.addAttribute("authorNmae", "Terence");
		//3.添加子节点
		Element states=model.addElement("states");
		for(State tmc_state:tmc.getStates())
		{
			Element state=states.addElement("state");
			if(tmc_state.getLabel()!=null)
			{
				state.addAttribute("Label", tmc_state.getLabel());
			}
			Element stateName=state.addElement("name");
			stateName.setText(tmc_state.getName());
			Element arcs=state.addElement("arcs");
			for(Transition transition:tmc.getTransitions())
			{
				if(transition.getFrom().equals(tmc_state))
				{
					Element arc=arcs.addElement("arc");
					
					Element stimulate=arc.addElement("stimulate");
					Element stimulateName=stimulate.addElement("name");
					
					if(transition.getTransFlag().getName()==null)
					{
						stimulateName.setText("null");
						Element parameters=stimulate.addElement("parameters");
						Element constraints=parameters.addElement("constraints");
						//如果没有约束
						//不写参数
						//写空约束集合
						constraints.setText("");
					}
					else
					{
						stimulateName.setText(transition.getTransFlag().getName());
						Element parameters=stimulate.addElement("parameters");
						for(int i=0;i<3;i++)
						{
							Element parameter=parameters.addElement("parameter");
							Element paramName=parameter.addElement("paramName");
							paramName.setText("参数名");
							Element paramType=parameter.addElement("paramType");
							paramType.setText("参数类型");
							Element domain=parameter.addElement("domain");
							domain.addAttribute("type", "serial");
							domain.setText("域");
							Element constraints=parameters.addElement("constraints");
							for(int j=0;j<2;j++)
							{
								Element constraint=constraints.addElement("constraint");
								constraint.setText("约束条件");
							}
						}
					}
					//加激励内容
					
					//加to节点
					Element to=arc.addElement("to");
					to.setText(transition.getTo().getName());
					Element probability=arc.addElement("probability");
					probability.setText(transition.getTransFlag().getProb()+"");
					
				}
			}
			
		}
		
		OutputFormat format=OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		File file=new File("MarkovModelOfYangJie.xml");
		XMLWriter writer;
		try {
			writer=new XMLWriter(new FileOutputStream(file),format);
			writer.setEscapeText(false);
			writer.write(document);
			writer.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeMarkov2XML(Tmc tmc ,String fileName,MainFrame mainFrame) throws Exception
	{
		boolean isTime = false;
		//1.创建document对象，代表整个文档
		Document document=DocumentHelper.createDocument();
		//2.创建根节点并未根节点添加属性
		Element model=document.addElement("Model");
		model.addAttribute("version", "1.0");
		model.addAttribute("type", "uml:Model");
		model.addAttribute("name", "Markov_Model");
		model.addAttribute("visibility", "public");
		model.addAttribute("authorNmae", "Terence");
		//3.添加子节点
		Element type=model.addElement("ModelType");
		type.setText(tmc.getTmcType());
		Element owned=model.addElement("Owned");
		owned.setText(tmc.getOwned());
		Element name=model.addElement("name");
		name.setText(tmc.getNames());
		
		for(State tmc_state:tmc.getStates())
		{
			Element state=model.addElement("state");
			Element stateName=state.addElement("name");
			if(tmc_state.getLabel()!=null&&tmc_state.getLabel().contains("Initial"))
			{
				state.addAttribute("label", "initial");
				stateName.setText(tmc_state.getName());
			}
			else
				if(tmc_state.getLabel()!=null&&tmc_state.getLabel().contains("Exit"))
				{
					state.addAttribute("label", "final");
					stateName.setText("Exit");
				}
			else if(tmc_state.getLabel()!=null&&tmc_state.getLabel().contains("timeDelay"))
				{
				if(tmc_state.getStopTimeConstraint()!=null)
				{
					mainFrame.getStepTwoExchangeOperation().setTime(true);
					isTime = true;
					state.addAttribute("label", "timeDelay");
					stateName.setText(tmc_state.getName());
					Element time=state.addElement("time");
					time.setText(searchTimeInterval(tmc_state.getStopTimeConstraint()));
				}
					
					//time.setText("lowTime,highTime"); //状态上的驻留时间，暂时没有这方面的数据************************若有例子，则部分******************。
				} 
				else
				{
					stateName.setText(tmc_state.getName());
				}
			
			/*
			 * 判断该节点的出迁移是否是具有时间约束的迁移
			 * yes，需要添加arc,添加虚拟1.0的迁移，添加每个innerArc;
			 * no，添加各个arc即可，
			 */
			
			//如果    出迁移具有时间约束
			for(Transition transition:tmc.getTransitions())
			{
				if(transition.getFrom().equals(tmc_state))
				{
					Element arc=state.addElement("arc");
					//System.out.println(transition.getTransFlag().getStimulate().getTimeConstraints().toString());
					if(transition.getTransFlag().getStimulate()!=null
							&&transition.getTransFlag().getStimulate().getTimeConstraints().size()>0)
					{
						arc.addAttribute("label", "time");
						mainFrame.getStepTwoExchangeOperation().setTime(true);
						isTime = true;
					}
					else
					{
						arc.addAttribute("label", "prob");
					}
					if(transition.getTransFlag().getName()==null)
					{
						arc.addAttribute("type", "void");
					}
					else
					{
						arc.addAttribute("type", "common");
						
					}
					Element arcName=arc.addElement("name");
					if(transition.getTransFlag().getName()!=null)
					{
						arcName.setText(transition.getFrom().getName()+
								"_"+transition.getTo().getName()+
								"_"+transition.getTransFlag().getName());
					}
					else
					{
						arcName.setText(transition.getFrom().getName()+
								"_"+transition.getTo().getName());
					}
					Element arcCondition=arc.addElement("conditions");
					arcCondition.setText(replaceEscapeCharacter(transition.getTransFlag().getNotation()));
					Element owne=arc.addElement("owned");
					if(transition.getTransFlag().getName()!=null&&!transition.getTransFlag().getName().trim().equals(""))
					{
						if(transition.getTransFlag().getName().contains("("))
						{
							owne.setText(transition.getTransFlag().getName().substring(0, 
									transition.getTransFlag().getName().indexOf("(")));
						}
						else
						{
							throw new Exception("消息名称书写错误,请查看消息是否带参数类型列表，消息名："+transition.getTransFlag().getName());
						}
								
					}
					else
					{
						owne.setText("null");
					}
					
					if(transition.getTransFlag().getAssignValue()!=null&&!"".equals(transition.getTransFlag().getAssignValue().trim()))
					{
						Element assignValue=arc.addElement("assignValue");
						assignValue.setText(transition.getTransFlag().getAssignValue());
					}
					if(transition.getTransFlag().getAssignType()!=null&&!"".equals(transition.getTransFlag().getAssignType().trim()))
					{
						Element assignType=arc.addElement("assignType");
						assignType.setText(transition.getTransFlag().getAssignType());
					}
					
					if(transition.getTransFlag().getStimulate()!=null && transition.getTransFlag().getStimulate().getTimeConstraints().size()>0)
					{
						for(String str:transition.getTransFlag().getStimulate().getTimeConstraints())
						{
							Element time=arc.addElement("time");
							time.addAttribute("key", "time");
							time.setText(searchTimeInterval(str));
						}
						
					}
					else//不存在时间约束
					{
						Element element=arc.addElement("prob");
						element.setText(transition.getTransFlag().getProb()+"");
						
						if(transition.getTransFlag().getName()!=null)
						{
							//首先要遍历参数的个数；然后加入参数信息	
							if(transition.getTransFlag().getStimulate()!=null
									&&transition.getTransFlag().getStimulate().getParameterNameList().size()!=0)
							{
//								System.out.println("类型："+transition.getTransFlag().getStimulate().getParameterNameList().size());
								Element stimulate=arc.addElement("stimulate");
								
								Stimulate tempStimulate=transition.getTransFlag().getStimulate();
								if(tempStimulate.getParameterNameList()!=null)
								{
									for(int i=0;i<tempStimulate.getParameterNameList().size();i++)
									{
										Element parameter=stimulate.addElement("parameter");
										Element paramName=parameter.addElement("paramName");
										paramName.setText(tempStimulate.getParameterNameList().get(i));
										Element paramType=parameter.addElement("paramType");
										paramType.setText(tempStimulate.getParameterTypeList().get(i));
										
										//加入参数定义域
										if(tempStimulate.getDomains()!=null)
										{

											for(String dom:tempStimulate.getDomains())
											{
												String domainStr=replaceEscapeCharacter(dom);
												if(domainStr.contains(tempStimulate.getParameterNameList().get(i).trim()))
												{
													Element domain=parameter.addElement("domain");
													
													if(domainStr.contains("["))
													{
														domain.addAttribute("type", "discrete");
														domain.setText(domainStr.substring(domainStr.indexOf("[")+1,domainStr.indexOf("]")));
													}
													else
													{
														domain.addAttribute("type", "serial");
														domain.setText(domainStr);
													}
												}
											}
										}
										
									}
									
								}
								//其次遍历参数约束表达式的个数，加入约束表达式信息；
								if(tempStimulate.getConstraintExpresstions()!=null)
								{
									for(Iterator<String> it=tempStimulate.getConstraintExpresstions().iterator();it.hasNext();)
									{
										String str=it.next();
										Element constraint=stimulate.addElement("constraint");
										constraint.setText(str);
									}
									
								}
								
							}
							
						}//否则不存在激励
					}
					
					Element to=arc.addElement("to");
					if(transition.getTo().getLabel()!=null&&transition.getTo().getLabel().contains("Exit"))
					{
						to.setText("Exit");
					}
					else
					{
						to.setText(transition.getTo().getName());
					}
				}
			}
		}
		OutputFormat format=OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		if(isTime == true)
		{
			 FileName =mainFrame.getBathRoute()+"/TimeMarkov/"+fileName;
		}
		else {
			 FileName =mainFrame.getBathRoute()+"/NoTimeMarkov/"+fileName;
		}
		System.out.println("FileName: " + FileName);
		File file=new File(FileName);
		XMLWriter writer;
		try {
			writer=new XMLWriter(new FileOutputStream(file),format);
			writer.setEscapeText(false);
			writer.write(document);
			writer.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * 将时间约束表达式处理成约束区间
	 * 
	 * @param str：时间约束表达式
	 * @return 时间约束区间
	 * @throws Exception  时间约束信息异常
	 */
	public static String searchTimeInterval(String str) throws Exception
	{
		String intervalStr="lowTime,HighTime";
		List<Double> doubleList=searchInteger(str);
		if(doubleList.size()==1)
		{
			if(str.contains("&lt")||str.contains("<"))
			{
				intervalStr="0,"+doubleList.get(0).toString();
			}
			else
				if(str.contains("&gt")||str.contains(">"))
				{
					intervalStr=doubleList.get(0).toString()+",+";
				}
				else
				{
					throw new Exception("时间约束信息有误,请核对该时间约束："+str);
				}
		}
		else
			if(doubleList.size()==2)
			{
				intervalStr=doubleList.get(0).toString()+","+doubleList.get(1).toString();
			}
			else
				throw new Exception("时间约束信息有误,请核对该时间约束："+str);
		return intervalStr;
	}
	public static List<Double> searchInteger(String str)
	{
		if(str.charAt(str.length()-1)!=';')
		{
			str=str+";";
		}
		//System.out.println("===="+str);
		List<Double> integerList=new ArrayList<Double>();
		
		String tempStr="";
		for(int i=0;i<str.length();i++)
		{
			if((str.charAt(i)>='0'&&str.charAt(i)<='9')||str.charAt(i)=='.')
			{
				tempStr+=str.charAt(i);
			}
			else
			{
				//System.out.println("@@@@"+tempStr);
				if(!tempStr.equals(""))
				{
					//System.out.println("&&&&"+tempStr);
					integerList.add(Double.parseDouble(tempStr));
//					integerList.add(Integer.parseInt(tempStr));
					tempStr="";
				}
			}
//			if(i==str.length()-1)
//			{
//				System.out.println("&&&&"+tempStr);
//				integerList.add(Double.parseDouble(tempStr));
////				integerList.add(Integer.parseInt(tempStr));
//				tempStr="";
//			}
		}
		return integerList;
	}
	public static String replaceEscapeCharacter (String str)
	{
		str=str.trim();		
		
		{
			String[] strs=str.split("&&");
			str="";
			for(String s:strs)
			{
				if(str=="")
				{
					str+=s;
				}
				else
				{
					str+="&amp;&amp;"+s;
				}
			}
		}
		{
			String[] strs=str.split("<");
			str="";
			for(String s:strs)
			{
				if(str=="")
				{
					str+=s;
				}
				else
				{
					str+="&lt;"+s;
				}
			}
		}
		{
			String[] strs=str.split(">");
			str="";
			for(String s:strs)
			{
				if(str=="")
				{
					str+=s;
				}
				else
				{
					str+="&gt;"+s;
				}
			}
		}
		
		
		return str;
	}
	
	
}
