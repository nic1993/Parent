package com.horstmann.violet.application.gui.util.suna.TimeExtend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StateTimeExtend {

	public Model resolveStateTime(Model model){
	        int i = 0;
	        TimeToProb tp = new TimeToProb();                       
	        List<State> timeStates = new ArrayList<State>();        
	        Map<String, State> statesMap = new HashMap<>();
	        
	        for (State state : model.getStateList()) {
	            statesMap.put(state.getName(), state);                              
	        }
	        for (State state : model.getStateList()) {                             
	            if (state.getName() != "Exit" && state.getArcList() != null) {      
	                List<Arc> arcsList = state.getArcList();
	                Iterator<Arc> iters = arcsList.iterator();
	                while(iters.hasNext()) {
	                    Arc arc = iters.next();
	                    String targetStateName = arc.getToStateName();              
	                    if (targetStateName != null) {
	                        State arcToState = statesMap.get(targetStateName);
	                        if (arcToState.getTime() != null) {                    
	                            System.out.println("TimeDelayState name is:" + arcToState.getName());
	                          
	                  
	                            Arc timeViolationOutArc  = new Arc("timeViolationState" + i++ + "OutArc", "Exit", 1);
	                            List<Arc> TVStateOutArcList = new ArrayList<>();
	                            TVStateOutArcList.add(timeViolationOutArc);
	                            
	                        
	                            State timeViolationState = new State( arcToState.getName() + "-timeViolationState", TVStateOutArcList);
	                            
	
	                            Arc timeViolationFromArc = new Arc(arcToState.getName() + "-TVStateFromArc", timeViolationState.getName(),1-tp.calProb(arcToState.getTime().toString()) );
	                            Arc timeDelayFromArc = new Arc(arcToState.getName() + "-TDStateFromArc", arcToState.getName(), tp.calProb(arcToState.getTime().toString()) );


	                            List<Arc> tempStateOutArcList = new ArrayList<>();
	                            tempStateOutArcList.add(timeDelayFromArc);
	                            tempStateOutArcList.add(timeViolationFromArc);
	                            State tempState = new State(arc.getToStateName() + "-extend", tempStateOutArcList);
	                            
	                          
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
	        
	        for (State state : statesMap.values()) { 
	        	extendStatesList.add(state); 
	        	}
	        
	        model.setStateList(extendStatesList);
	        model.setTimeStateList(timeStates);
	        
	        return model;
	}

}
