package com.horstmann.violet.application.gui.util.tiancai.markov;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class Write {
	
	public Write(){}
	
	//*******杨杰版*****************************************************************************
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
	
	public static void writeMarkov2XML(Tmc tmc ,String fileName)
	{

		//1.创建document对象，代表整个文档
		Document document=DocumentHelper.createDocument();
		//2.创建根节点并未根节点添加属性
		Element model=document.addElement("Model");
		model.addAttribute("version", "1.0");
		model.addAttribute("xmi:type", "uml:Model");
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
					System.out.println(tmc_state.getName()+"%%%%%"+tmc_state.getLabel());
					state.addAttribute("label", "final");
					stateName.setText("Exit");
				}
			else if(tmc_state.getLabel()!=null&&tmc_state.getLabel().contains("timeDelay"))
				{
					state.addAttribute("label", "timeDelay");
					stateName.setText(tmc_state.getName());
					Element time=state.addElement("time");
					time.setText("lowTime,highTime");
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
					if(transition.getLabel()!=null&&transition.getLabel().contains("time"))
					{
						arc.addAttribute("label", "time");
					}
					else
					{
						arc.addAttribute("label", "prob");
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
					if(transition.getLabel()!=null&&transition.getLabel().contains("time"))
					{
						Element time=arc.addElement("time");
						time.addAttribute("key", "time");
						time.setText("lowTime,highTime");
					}
					else//不存在时间约束
					{
						Element prob=arc.addElement("prob");
						prob.setText(transition.getTransFlag().getProb()+"");
						
						if(transition.getTransFlag().getName()!=null)
						{
							//首先要遍历参数的个数；然后加入参数信息	
							if(transition.getTransFlag().getStimulate()!=null
									&&transition.getTransFlag().getStimulate().getParameterNameList().size()!=0)
							{
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
										if(tempStimulate.getDomains()!=null)
										{
											for(String domainStr:tempStimulate.getDomains())
											{
												if(domainStr.contains(tempStimulate.getParameterNameList().get(i)))
												{
													Element domain=parameter.addElement("domain");
													domain.addAttribute("type", "serial");
													domain.setText(domainStr);
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
		File file=new File(fileName);
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
}
