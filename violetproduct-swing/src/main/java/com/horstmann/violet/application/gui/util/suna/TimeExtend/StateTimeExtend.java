package com.horstmann.violet.application.gui.util.suna.TimeExtend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StateTimeExtend {

	/**获取stateName到state实体的map用于确定to标签对应的实体state**/
	public Model resolveStateTime(Model model){
	        int i = 0;
	        TimeToProb tp = new TimeToProb();                       //时间转化概率的类实例化
	        List<State> timeStates = new ArrayList<State>();        //用来跟时间约束有关的状态
	        Map<String, State> statesMap = new HashMap<>();
	        
	        for (State state : model.getStateList()) {
	            statesMap.put(state.getName(), state);                              //将所有state按名称放入statesMap
	        }
	        for (State state : model.getStateList()) {                              //遍历state
	            if (state.getName() != "Exit" && state.getArcList() != null) {      //对于每个state依次遍历其出迁移
	                List<Arc> arcsList = state.getArcList();
	                Iterator<Arc> iters = arcsList.iterator();
	                while(iters.hasNext()) {
	                    Arc arc = iters.next();
	                    String targetStateName = arc.getToStateName();              //获取迁移目的节点名，按名称查找到相应节点实体后赋给arcToState
	                    if (targetStateName != null) {
	                        State arcToState = statesMap.get(targetStateName);
	                        if (arcToState.getTime() != null) {                     //如果arcToState带有时间约束,则进行模型扩展
	                            System.out.println("TimeDelayState name is:" + arcToState.getName());
	                          
	                            /**构造新增的状态节点和弧，并把其中的关系连接起来**/
                                //创建TVState的出迁移timeViolationState，并放入其出迁移集合
	                            Arc timeViolationOutArc  = new Arc("timeViolationState" + i++ + "OutArc", "Exit", 1);
	                            List<Arc> TVStateOutArcList = new ArrayList<>();
	                            TVStateOutArcList.add(timeViolationOutArc);
	                            
	                            //构造新得状态 timeViolationState，并将完整其关系
	                            State timeViolationState = new State( arcToState.getName() + "-timeViolationState", TVStateOutArcList);
	                            
	                            //构造时间违背状态和时间驻留状态(原状态)的入迁移
	                            Arc timeViolationFromArc = new Arc(arcToState.getName() + "-TVStateFromArc", timeViolationState.getName(),1-tp.calProb(arcToState.getTime().toString()) );
	                            Arc timeDelayFromArc = new Arc(arcToState.getName() + "-TDStateFromArc", arcToState.getName(), tp.calProb(arcToState.getTime().toString()) );

	                            //构造中间状态并完善关系
	                            List<Arc> tempStateOutArcList = new ArrayList<>();
	                            tempStateOutArcList.add(timeDelayFromArc);
	                            tempStateOutArcList.add(timeViolationFromArc);
	                            State tempState = new State(arc.getToStateName() + "-extend", tempStateOutArcList);
	                            
	                            //构造中间状态入迁移，将时间违背状态原入迁移的目的节点改为中间状态即可 
	                            arc.setToStateName(tempState.getName());
	                            System.out.println(arc.toString());

	                            statesMap.put(tempState.getName(), tempState);
	                            statesMap.put(timeViolationState.getName(), timeViolationState);
	                            
	                            timeStates.add(arcToState);
	                            timeStates.add(timeViolationState);
	                            timeStates.add(tempState);
	                            
	                        } 
	                    }
	                }
	            }
	        }
	        
	        List<State> extendStatesList = new ArrayList<State>();
	        Iterator it = statesMap.keySet().iterator();
	        while (it.hasNext())  System.out.println(it.next());
	        
	        //将扩展后的状态加入模型中
	        for (State state : statesMap.values()) { 
	        	extendStatesList.add(state); 
	        	}
	        
	        model.setStateList(extendStatesList);
	        model.setTimeStateList(timeStates);
	        
	        return model;
	}

}
