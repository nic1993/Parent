package com.horstmann.violet.application.gui.util.suna.TimeExtend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class WriteToXML {
	
	public WriteToXML(){}

	/**
	 * 传入扩展和概率转化后的模型，生成相应的XMl文件
	 * @param mm Markov模型
	 * @param fileName 新生成的XMl文件名
	 * @throws Exception
	 */
	public void writeMarkov2XML(Model mm, String fileName) throws Exception{
		
		//1.创建document对象，代表整个文档
		Document document=DocumentHelper.createDocument();
				
		//2.创建根节点并未根节点添加属性
		Element model=document.addElement("Model");
		model.addAttribute("version", "1.0");
		model.addAttribute("type", "uml:Model");
		model.addAttribute("name", "Markov_Model");
		model.addAttribute("visibility", "public");
		model.addAttribute("authorNmae", "SN");
				
		//3.添加子节点，设置模型名
		Element modelName=model.addElement("name");
		modelName.setText(mm.getName());
		
		//遍历并添加State节点及其属性
		for(State stateEntity:mm.getStateList()){
			
			Element state = model.addElement("state");                 //创建子节点state
			if (stateEntity.getLabel()!=null) {                        //设置state的属性label
				state.addAttribute("label", stateEntity.getLabel());   
			}

			Element stateName=state.addElement("name");                //state的子节点name
			stateName.setText(stateEntity.getName());  
			
			if (stateEntity.getTime()!=null) {
				Element stateTime = state.addElement("time");
				stateTime.setText(stateEntity.getTime());
			}
			
			//设置迁移arc节点
			for(Arc arcEntity:stateEntity.getArcList()){
					Element arc=state.addElement("arc");                //添加迁移节点
					arc.addAttribute("label", "prob");                  //所有迁移都添加属性label，并设为prob
					arc.addAttribute("type", arcEntity.getType());      //设置type属性
					
					Element arcName = arc.addElement("name");           //增加并设置子节点name
					arcName.setText(arcEntity.getName());
					
					Element arcProb = arc.addElement("prob");
					//prob为double类型，转化为String类型后才能在xml中输出
					arcProb.setText(Double.toString(arcEntity.getProb()));
					//arcProb.setData(arcEntity.getProb());
					
					if(arcEntity.getTime()!=null){
						Element arcTime = arc.addElement("time");
						arcTime.setText(arcEntity.getTime());
						arc.setAttributeValue("label", "time");
					}
					
					Element toState = arc.addElement("to");
					toState.setText(arcEntity.getToStateName());
					
					Element arcOwned = arc.addElement("owned");
					if (arcEntity.getOwned() != null) {
						arcOwned.setText(arcEntity.getOwned());
					}else{
						arcOwned.setText("null");
					}
					
					Element arcConditions = arc.addElement("conditions");
					if (arcEntity.getConditions() != null) {
						String newConditions = arcEntity.getConditions().replaceAll("<", "&lt;");
						newConditions = newConditions.replaceAll(">", "&gt;");
						arcConditions.setText(newConditions);
					}
					
					//设置激励stimulate
					if (arcEntity.getStimulate()!=null && arcEntity.getStimulate().getParameterList()!=null) {
						Element stimulate = arc.addElement("stimulate");              
						Stimulate stimulateEntity = arcEntity.getStimulate();
						
						if(stimulateEntity.getParameterList()!=null){
							for(int i=0; i<stimulateEntity.getParameterList().size();i++){
								Parameter parameterEntity = stimulateEntity.getParameterList().get(i);   //当前Parameter实体
								
								Element parameter = stimulate.addElement("parameter");
								
								Element paraName = parameter.addElement("paramName");
								paraName.setText(parameterEntity.getName());
								
								Element paraType = parameter.addElement("paramType");
								paraType.setText(parameterEntity.getParamType());
								
								if (parameterEntity.getDomain() != null) {
									
//									Element paramDomain = parameter.addElement("domain");
//									paramDomain.addAttribute("type", "XXXXXX");
//									
									
									System.out.println("~~~~"+stateEntity.getName()+"/" + parameterEntity.getName()+"/"+ parameterEntity.getDomainType() + "/" + parameterEntity.getDomain());
									
									String domainTypy = parameterEntity.getDomainType();
									Element paramDomain = parameter.addElement("domain");
									paramDomain.addAttribute("type", domainTypy);
									System.out.println(domainTypy);
									if (domainTypy.equals("serial")) {
										String newDomain = parameterEntity.getDomain().replaceAll("<", "&lt;");
										paramDomain.setText(newDomain);
										System.out.println("serial:" + newDomain);
									}else {
										paramDomain.setText(parameterEntity.getDomain());
										System.out.println("else:" + parameterEntity.getDomain());
									}
								}
							}
							
							//遍历参数约束表达式的个数，加入约束表达式信息；
							if (stimulateEntity.getConstraintList() != null) {
								for(Iterator<String> it = stimulateEntity.getConstraintList().iterator();it.hasNext();){
									String consStr = it.next();
									Element constraint = stimulate.addElement("constraint");
									constraint.setText(consStr);
								}
							}
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
