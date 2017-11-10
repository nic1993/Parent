package com.horstmann.violet.application.gui.util.suna.TimeExtend;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLParser {
	static SAXReader reader = new SAXReader();			              //创建SAXReader的对象reader
	private File file;
	public XMLParser(File file)
	{
		this.file = file;
	}
	
	public Model readXML() throws Exception{
		Document document = reader.read(file);  
		Element model = document.getRootElement();                   
		
		Model modelEntity = new Model();                             
		List<State> states = new ArrayList<State>();                 
		modelEntity.setName(model.elementText("name"));
		

		Iterator iterator = model.elementIterator();                  
		while(iterator.hasNext()){                                    
			Element  state = (Element)iterator.next();             
			State stateEntity = new State();                          
			
			stateEntity.setLabel(state.attributeValue("label"));     
			
			stateEntity.setName(state.elementText("name"));
			
			stateEntity.setTime(state.elementText("time"));
//			Element stateName = state.element("name");
//			if(stateName != null){
//				stateEntity.setName(stateName.getStringValue());
//			}
			
			Iterator iterator2 = state.elementIterator();		
			List<Arc> arcs = new ArrayList<Arc>();                   
			while(iterator2.hasNext()){
				Element arc = (Element) iterator2.next();            
				Arc arcEntity = new Arc();                           
				arcEntity.setLabel(arc.attributeValue("label"));     
				arcEntity.setType(arc.attributeValue("type"));          
				arcEntity.setName(arc.elementText("name"));          
				arcEntity.setToStateName(arc.elementText("to"));     
				arcEntity.setTime(arc.elementText("time"));
				arcEntity.setOwned(arc.elementText("owned"));
				arcEntity.setConditions(arc.elementText("conditions"));
				
				String arcProb = arc.elementText("prob");
				if(arcProb != null){
					double prob = Double.valueOf(arcProb);
					arcEntity.setProb(prob);                         
				}

			
				Stimulate stimulateEntity = new Stimulate();
				
				Iterator iterator3 = arc.elementIterator("stimulate");
				while (iterator3.hasNext()) {
					Element stimulate = (Element) iterator3.next();
					
					
					List<Element> constraints = stimulate.elements("constraint");
					List<String> constrainsList = new ArrayList<>();
					for(Element constraint : constraints){
						constrainsList.add(constraint.getText());
					}
					stimulateEntity.setConstraintList(constrainsList);
					
					
					List<Element> parameters = stimulate.elements("parameter");      
					List<Parameter> parametersList = new ArrayList<>();              
					for(Element parameter : parameters ){
						Parameter parameterEntity = new Parameter();
						parameterEntity.setName(parameter.elementText("paramName"));
						parameterEntity.setParamType(parameter.elementText("paramType"));
						parameterEntity.setDomain(parameter.elementText("domain"));
						
						
						Iterator iterator4= parameter.elementIterator();
						while(iterator4.hasNext()){
							Element findDomain = (Element)iterator4.next();
							if(findDomain.getName().equals("domain")){
								parameterEntity.setDomainType(findDomain.attributeValue("type"));
							}
						}
	
						parametersList.add(parameterEntity);
						parameterEntity = null;
					}
					stimulateEntity.setParameterList(parametersList);
				}
				arcEntity.setStimulate(stimulateEntity);				
				
				
				if(arcEntity.getName() != null){
					arcs.add(arcEntity);
				}			
				arcEntity = null;
			}
			stateEntity.setArcList(arcs);                       


		
			if(stateEntity.getName() != null){
				states.add(stateEntity);
			}
			stateEntity = null;
			modelEntity.setStateList(states);
		}

		for(State SEState : modelEntity.getStateList()){
			if(SEState.getLabel() != null){
				if(SEState.getLabel().equals("initial")){
					modelEntity.setInitialState(SEState);
				}
				else if(SEState.getLabel().equals("final")){
					modelEntity.setFinalState(SEState);
				}
			}
		}
		
		return modelEntity;
	} 

}
