package com.horstmann.violet.application.gui.util.wujun.TDVerification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import org.dom4j.Element;
public class Read {
		
	ArrayList<UppaalTemPlate> uppaalTemplates = new ArrayList<UppaalTemPlate>();
	ArrayList<UppaalLocation> locations = new ArrayList<UppaalLocation>();
	ArrayList<UppaalTransition> transitions = new ArrayList<UppaalTransition>();
			
	public ArrayList<UppaalTemPlate> getUppaalTemplates() {
		return uppaalTemplates;
	}
	
	public void load(Element root) throws Exception{
		
		ArrayList<Element> templateList = new ArrayList<Element>();
		
		
		templateList.addAll(root.elements("template"));
		
		
		for(Object oTemplate: templateList)
		{
			Element templateI = (Element)oTemplate;
			
			UppaalTemPlate template = new UppaalTemPlate();
			template.setName(templateI.element("name").getText());//设置template的名字
			
			locations = new ArrayList<UppaalLocation>();
			transitions = new ArrayList<UppaalTransition>();
			
			ArrayList<Element> locationList = new ArrayList<Element>();			
			locationList.addAll(templateI.elements("location"));//读取该template的locations		
			
			
			for(Object oLocation: locationList)
			{
				Element locationI = (Element)oLocation;	
				
				UppaalLocation location = new UppaalLocation();
				location.setId(locationI.attributeValue("id"));
				location.setName(locationI.element("name").getText());
				location.setFinl(locationI.attributeValue("finl"));
				location.setInit(locationI.attributeValue("init"));
				if (locationI.element("label") != null
						&& locationI.element("label").attributeValue("kind")
								.equals("invariant")) {//设置x
					
					String temp = locationI.element("label").getTextTrim();
					if (temp.contains("t...t")) {
						temp = temp.substring(5, temp.length());
					}
					StringBuilder sb = new StringBuilder();
					char[] chars = temp.toCharArray();
					
					for(int i = 0; i < temp.length(); i++) {
						
						if (Character.isDigit(chars[i]) || chars[i] == '.') {
							sb.append(chars[i]);
						}
					}
					
					location.setX(Double.valueOf(sb.toString()));  
				}
				
				
				locations.add(location);
			}
			template.setLocations(locations);
			
			ArrayList<Element> transitionList = new ArrayList<Element>();			
			transitionList.addAll(templateI.elements("transition"));
			for(Object oTransition: transitionList)//读取transition
			{
				Element transitionI = (Element)oTransition;	
				
				UppaalTransition transition = new UppaalTransition();
				
				ArrayList<Element> labels = (ArrayList) transitionI.elements("label");
				Iterator label_Iterator = labels.iterator();
				
				transition.setSource(Integer.parseInt(transitionI.element("source").attributeValue("ref").substring(2)));
				transition.setTarget(Integer.parseInt(transitionI.element("target").attributeValue("ref").substring(2)));
					
				
				while(label_Iterator.hasNext())
				{
					Element label = (Element) label_Iterator.next();
					if(label.attributeValue("kind").equals("assignment"))
					{
						String temp = label.getTextTrim();
						String[] t = temp.split("=");
						
						transition.setX(Double.valueOf(t[1]));  //设置x.
					}
					else if(label.attributeValue("kind").equals("synchronisation"))
					{
						transition.setFromName(label.attributeValue("from"));
						transition.setToName(label.attributeValue("to"));
						transition.setName(label.getTextTrim());
						transition.setTime(Double.valueOf(label.attributeValue("time")));//设置时间time
						
						if (!label.attributeValue("duration").equals("null")) {
							transition.setDuration(Double.valueOf(label.attributeValue("duration").split("<")[1]));
						}
						
						transition.setOut(true);//设置out
						
					}
				}
				
				
				transitions.add(transition);
			}
			
			template.setTransitions(transitions);
			
			
			uppaalTemplates.add(template);
		}
		
	}
}
