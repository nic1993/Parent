package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;


import java.util.ArrayList;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;

public class AddType {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String xml="UAVForXStream.xml";
		Automatic auto=GetAutomatic.getAutomatic(xml);//获得原始的时间自动机
		Automatic automatic=AddType.addType(auto);
		
		
		System.out.println("时间自动机名字:"+automatic.getName());
		
		State iniState=automatic.getInitState();
		System.out.println("初始状态名字："+iniState.getName());
		System.out.println(iniState.getPosition());
		System.out.println(iniState.isFinalState());
		System.out.println(iniState.getType());
		System.out.println(iniState.getId());
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		
	
		System.out.println("状态个数："+automatic.getStateSet().size());
		int k=0;
		for(State state:automatic.getStateSet()){
			System.out.println("第"+k+"个状态");
			k++;
			System.out.println("状态名称: "+state.getName());
			System.out.println("状态位置: "+state.getPosition());
			System.out.println("是否为终止状态 : "+state.isFinalState());
			System.out.println("Type类型是否为初始："+state.getType());
			System.out.println("状态id为："+state.getId());
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			
		}
		

	}
	/**
	 * 添加Type
	 * @param automatic
	 * @return
	 */
	public static Automatic addType(Automatic automatic) {
		ArrayList<State> new_stateSet=automatic.getStateSet();
		ArrayList<Transition> transitions=automatic.getTransitionSet();
		int k=0;
		int num=1;
		ArrayList<State> P=new ArrayList<State>();
		for(State s:new_stateSet){
			for(Transition t:transitions){
				if(t.getTarget().equals(s.getName())){
					k=1;
					break;
				}
		     }
			
			
			State ss=new State();
			ss.setName(s.getName());
			ss.setPosition(s.getPosition());
			ss.setInvariantDBM(s.getInvariantDBM());
			ss.setAddClockRelationDBM(s.getAddClockRelationDBM());
			ss.setFinalState(s.isFinalState());
			ss.setId(num++);
			if(k==0){
				ss.setType("Start");
				P.add(ss);
			}
			if(k==1){
				ss.setType("CircularNode");
				P.add(ss);
			}
		}
		/*//设定automatic中的初始状态
		State initstate=new State();
		initstate.setFinalState(automatic.getInitState().isFinalState());
		initstate.setName(automatic.getInitState().getName());
		initstate.setId(1);//////////////
		initstate.setType("start");////////////
		if(automatic.getInitState().getInvariant().size()!=0){
			initstate.setInvariantDBM(DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element__1.stringToDBM_element(Clocks, temPlate.getInitState().getInvariant())));
			initstate.setAddClockRelationDBM(initstate.getInvariantDBM());
			initstate.setPosition(temPlate.getInitState().getName());
		}
		else{
			ArrayList<String> invariant=new ArrayList<String>();
			DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, invariant));//将状态中的不变式转成DBM矩阵
			initstate.setInvariantDBM(DBM);
			initstate.setAddClockRelationDBM(initstate.getInvariantDBM());
			initstate.setPosition(automatic.getInitState().getName());
		}
		automatic.setInitState(initstate);*/
				
		Automatic newaotu=new Automatic();//构建一个新的时间自动机
		State intiState=new State();
		for(State s:new_stateSet){//判断哪个是初始状态
			int h=Minimization__1.includeZero(s.getInvariantDBM());
			if(s.getPosition().equals(automatic.getInitState().getPosition())&&h==1){
				intiState.setName(s.getName());
				intiState.setPosition(s.getPosition());
				intiState.setInvariantDBM(s.getInvariantDBM());
				intiState.setId(1);
				intiState.setType("start");
				//System.out.println(intiState.getName());
				//System.out.println(intiState.getPosition());
			}
		}
		newaotu.setName(automatic.getName());
		newaotu.setInitState(intiState);
		newaotu.setTransitionSet(automatic.getTransitionSet());
		newaotu.setClockSet(automatic.getClockSet());
		newaotu.setStateSet(P);
		
		return newaotu;
		

		}	
	}


