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
		Document document = reader.read(file);  //通过reader的read方法加载xml文件，获取document对象
		Element model = document.getRootElement();                    //获取根节点
		
		Model modelEntity = new Model();                              //创建模型实体对象
		List<State> states = new ArrayList<State>();                  //用来存储所有状态节点的集合，存储完成赋值给model相应变量
		modelEntity.setName(model.elementText("name"));
		
		//开始遍历状态节点
		Iterator iterator = model.elementIterator();                  //获取迭代器
		while(iterator.hasNext()){                                    //遍历迭代器，获取根节点中的信息,即State节点信息
			Element  state = (Element)iterator.next();                //将遍历到的state节点强制转换为Element类型并赋给state
			State stateEntity = new State();                          //每遍历到一个State就创建State实体对象
			//获取state属性label的值并设置到state实体对象中
			stateEntity.setLabel(state.attributeValue("label"));     
			//设置节点名，读取name节点的text设置给state实体对象
			stateEntity.setName(state.elementText("name"));
			//设置有时间约束的状态的time
			stateEntity.setTime(state.elementText("time"));
//			Element stateName = state.element("name");
//			if(stateName != null){
//				stateEntity.setName(stateName.getStringValue());
//			}
			
			//遍历state出迁移
			Iterator iterator2 = state.elementIterator();		
			List<Arc> arcs = new ArrayList<Arc>();                   // 用来存储当前状态下迁移集合
			while(iterator2.hasNext()){
				Element arc = (Element) iterator2.next();            //讲迭代器当前位置强制转换为Element类型，赋给arc，作为当前遍历到的迁移
				Arc arcEntity = new Arc();                           //创建迁移实体对象
				arcEntity.setLabel(arc.attributeValue("label"));     //读取xml中的的arc的label属性，赋给实体对象
				arcEntity.setType(arc.attributeValue("type"));          //
				arcEntity.setName(arc.elementText("name"));          //读取xml中arc名称name并赋给实体对象
				arcEntity.setToStateName(arc.elementText("to"));     //设置迁移目的节点名
				arcEntity.setTime(arc.elementText("time"));
				arcEntity.setOwned(arc.elementText("owned"));
				arcEntity.setConditions(arc.elementText("conditions"));
				
				String arcProb = arc.elementText("prob");
				if(arcProb != null){
					double prob = Double.valueOf(arcProb);
					arcEntity.setProb(prob);                         //设置迁移概率，注意类型转换为double，用Double.ValueOf()方法
				}

				//迁移上激励实体化
				Stimulate stimulateEntity = new Stimulate();
				//遍历迁移上激励
				Iterator iterator3 = arc.elementIterator("stimulate");
				while (iterator3.hasNext()) {
					Element stimulate = (Element) iterator3.next();
					
					//获取迁移的激励上约束表达式集
					List<Element> constraints = stimulate.elements("constraint");
					List<String> constrainsList = new ArrayList<>();
					for(Element constraint : constraints){
						constrainsList.add(constraint.getText());
					}
					stimulateEntity.setConstraintList(constrainsList);
					
					//获取激励上参数集合
					List<Element> parameters = stimulate.elements("parameter");      //存放xml中parameter元素的结合
					List<Parameter> parametersList = new ArrayList<>();              //存放信息存储完毕后的parameter实体的集合
					for(Element parameter : parameters ){
						Parameter parameterEntity = new Parameter();
						parameterEntity.setName(parameter.elementText("paramName"));
						parameterEntity.setParamType(parameter.elementText("paramType"));
						parameterEntity.setDomain(parameter.elementText("domain"));
						
						//查找domain节点的属性type赋给实体parameterEntity的domainType
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
				
				//将内容完善后的arcEntity加入到迁移集合中
				if(arcEntity.getName() != null){
					arcs.add(arcEntity);
				}			
				arcEntity = null;
			}
			stateEntity.setArcList(arcs);         //将加入所有集合的arcs赋给state实体                 


		//state节点实体设置完毕后加入states集合中
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
