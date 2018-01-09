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


	public void writeMarkov2XML(Model mm, String fileName) throws Exception{
		

		Document document=DocumentHelper.createDocument();
				
	
		Element model=document.addElement("Model");
		model.addAttribute("version", "1.0");
		model.addAttribute("type", "uml:Model");
		model.addAttribute("name", "Markov_Model");
		model.addAttribute("visibility", "public");
		model.addAttribute("authorNmae", "SN");
				
		
		Element modelName=model.addElement("name");
		modelName.setText(mm.getName());
		
	
		for(State stateEntity:mm.getStateList()){
			
			Element state = model.addElement("state");                 
			if (stateEntity.getLabel()!=null) {                        
				state.addAttribute("label", stateEntity.getLabel());   
			}

			Element stateName=state.addElement("name");                
			stateName.setText(stateEntity.getName());  
			
			if (stateEntity.getTime()!=null) {
				Element stateTime = state.addElement("time");
				stateTime.setText(stateEntity.getTime());
			}
			
			for(Arc arcEntity:stateEntity.getArcList()){
					Element arc=state.addElement("arc");                
					arc.addAttribute("label", "prob");                
					arc.addAttribute("type", arcEntity.getType());      
					
					Element arcName = arc.addElement("name");           
					arcName.setText(arcEntity.getName());
					
					Element arcProb = arc.addElement("prob");
				
					arcProb.setText(Double.toString(arcEntity.getProb()));
				
					
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
					
			
					if (arcEntity.getStimulate()!=null && arcEntity.getStimulate().getParameterList()!=null) {
						Element stimulate = arc.addElement("stimulate");              
						Stimulate stimulateEntity = arcEntity.getStimulate();
						
						if(stimulateEntity.getParameterList()!=null){
							for(int i=0; i<stimulateEntity.getParameterList().size();i++){
								Parameter parameterEntity = stimulateEntity.getParameterList().get(i);   
								
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
