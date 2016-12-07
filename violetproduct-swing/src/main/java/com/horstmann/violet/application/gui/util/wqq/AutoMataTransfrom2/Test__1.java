package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;


/**
 * 验证l0有没有被正确拆分
 * @author Seryna
 *
 */
public class Test__1 {
	public static void main(String[] args) {
		Automatic automatic=getAutomatic();
		Automatic new_automatic=IPR__1.iPR(automatic);
		Automatic aTDRTAutomatic=ATDTR__1.aTDRT(new_automatic,automatic);  
		//Automatic DFStree=StateCoverage__1.DFSTree(aTDRTAutomatic);
		ArrayList<Automatic> testCase=StateCoverage__1.testCase(aTDRTAutomatic);
		ArrayList<ArrayList<String>> all_inequalitys=Get_inequality__1.get_AllInequalitys(testCase);
		
		
		/*System.out.println("总共"+all_inequalitys.size()+"个不等式组");
		int e=1;
		for(ArrayList<String> inequalitys:all_inequalitys){
			System.out.println("第"+e+"个不等式组");
			for(String s:inequalitys){
				System.out.println(s);
			}
			System.out.println("***************");
			e++;
		}*/
		
		/*for(Automatic a:testCase){
			for(Transition tran:a.getTransitionSet()){
				System.out.println(tran.getSource()+"-->"+tran.getTarget());
			}
			System.out.println("*********");
		}*/
		
	/*	int e=1;
		for(Automatic a:testCase){
			System.out.println("第"+e+"个不等式组");
			ArrayList<String> Inequalitys=Get_inequality__1.get_Inequalitys(a);
			for(String s:Inequalitys){
				System.out.println(s);
			}
			e++;
			System.out.println("------------");
		}*/
	}
	
	/**
	 * 获得 一个时间自动机
	 * @return
	 */
	public static Automatic getAutomatic(){
		UppaalTemPlate temPlate=new UppaalTemPlate();
		ArrayList<UppaalTransition> T_transitions=new ArrayList<UppaalTransition>();
		ArrayList<UppaalLocation> T_locations=new ArrayList<UppaalLocation>();
		UppaalLocation T_InitState=new UppaalLocation();
		String T_name="第一个时间自动机";
		ArrayList<String> T_Clocks=new ArrayList<String>();
		T_Clocks.add("x");
		T_Clocks.add("y");

		ArrayList<String> ar0 =new ArrayList<String>();
		ar0.add("x<2");
		ar0.add("x-y<=0");
		ar0.add("y-x<=0");
		ArrayList<String> ar1 =new ArrayList<String>();
		ar1.add("y<=2");
		ArrayList<String> ar2 =new ArrayList<String>();
		ArrayList<String> ar3 =new ArrayList<String>();
		
		UppaalLocation l0=new UppaalLocation();
		l0.setName("l0");
		l0.setInvariant(ar0);
		l0.setFinalState(false);
		UppaalLocation l1=new UppaalLocation();
		l1.setName("l1");
		l1.setInvariant(ar1);
		l1.setFinalState(false);
		UppaalLocation l2=new UppaalLocation();
		l2.setName("l2");
		l2.setInvariant(ar2);
		l2.setFinalState(false);
		UppaalLocation l3=new UppaalLocation();
		l3.setName("l3");
		l3.setInvariant(ar3);
		l3.setFinalState(false);
			
		T_locations.add(l0);
		T_locations.add(l1);
		T_locations.add(l2);
		T_locations.add(l3);
		
		UppaalTransition e0 =new UppaalTransition();
		e0.setSource(l0.getName());
		e0.setTarget(l1.getName());
		ArrayList<String> reset0 =new ArrayList<String>();
		reset0.add("y");
		e0.setResetClocks(reset0);
		ArrayList<String>constraint0 =new ArrayList<String>();
		constraint0.add("x>1");
		e0.setConstraint(constraint0);
		ArrayList<String> events0=new ArrayList<String>();
		events0.add("?a");
		e0.setEvents(events0);
		ArrayList<String> types0=new ArrayList<String>();
		e0.setTypes(types0);
		ArrayList<String> typeIds0=new ArrayList<String>();
		e0.setTypeIds(typeIds0);
		
		UppaalTransition e1=new UppaalTransition();
		e1.setSource(l1.getName());
		e1.setTarget(l3.getName());
		ArrayList<String> reset1 =new ArrayList<String>();
		e1.setResetClocks(reset1);
		ArrayList<String> constraint1 =new ArrayList<String>();
		constraint1.add("y>1");
		constraint1.add("x<=3");
		e1.setConstraint(constraint1);
		ArrayList<String> events1=new ArrayList<String>();
		events1.add("!c");
		e1.setEvents(events1);
		ArrayList<String> types1=new ArrayList<String>();
		e1.setTypes(types1);
		ArrayList<String> typeIds1=new ArrayList<String>();
		e1.setTypeIds(typeIds1);
		
		UppaalTransition e2 =new UppaalTransition();
		e2.setSource(l3.getName());
		e2.setTarget(l1.getName());
		ArrayList<String> reset2 =new ArrayList<String>();
		e2.setResetClocks(reset2);
		ArrayList<String> constraint2 =new ArrayList<String>();
		constraint2.add("y<2");
		e2.setConstraint(constraint2);
		ArrayList<String> events2=new ArrayList<String>();
		events2.add("?a");
		e2.setEvents(events2);
		ArrayList<String> types2=new ArrayList<String>();
		e2.setTypes(types2);
		ArrayList<String> typeIds2=new ArrayList<String>();
		e2.setTypeIds(typeIds2);
		
		UppaalTransition e3 =new UppaalTransition();
		e3.setSource(l1.getName());
		e3.setTarget(l2.getName());
		ArrayList<String> reset3 =new ArrayList<String>();
		e3.setResetClocks(reset3);
		ArrayList<String> constraint3 =new ArrayList<String>();
		constraint3.add("y<=2");
		constraint3.add("y>=2");
		e3.setConstraint(constraint3);
		ArrayList<String> events3=new ArrayList<String>();
		events3.add("!b");
		e3.setEvents(events3);
		ArrayList<String> types3=new ArrayList<String>();
		e3.setTypes(types3);
		ArrayList<String> typeIds3=new ArrayList<String>();
		e3.setTypeIds(typeIds3);
		
		UppaalTransition e4 =new UppaalTransition();
		e4.setSource(l2.getName());
		e4.setTarget(l3.getName());
		ArrayList<String> reset4 =new ArrayList<String>();
		e4.setResetClocks(reset4);
		ArrayList<String> constraint4 =new ArrayList<String>();
		constraint4.add("x<=3");
		e4.setConstraint(constraint4);
		ArrayList<String> events4=new ArrayList<String>();
		events4.add("!c");
		e4.setEvents(events4);
		ArrayList<String> types4=new ArrayList<String>();
		e4.setTypes(types4);
		ArrayList<String> typeIds4=new ArrayList<String>();
		e4.setTypeIds(typeIds4);
		
		
		T_transitions.add(e0);
		T_transitions.add(e1);
		T_transitions.add(e2);
		T_transitions.add(e3);
		T_transitions.add(e4);
		
		temPlate.setTransitions(T_transitions);
		temPlate.setLocations(T_locations);
		temPlate.setClockSet(T_Clocks);
		temPlate.setInitState(l0);
		temPlate.setName(T_name);
		
		T_InitState=l0;
		temPlate.setInitState(T_InitState);
		
		Automatic automatic=new Automatic();
		ArrayList<Transition> TransitionSet=new ArrayList<Transition>();//automatic中的转换集合
		ArrayList<State> StateSet = new ArrayList<State>();//automatic中的状态集合

		
		ArrayList<String> Clocks=temPlate.getClocks();//获取时间自动机中的时钟集合
		
		ArrayList<UppaalLocation> locations=temPlate.getLocations();//获取时间自动机中的所有状态
	
		for(UppaalLocation loc:locations){//遍历所有状态
		
			if(loc.getInvariant().size()!=0){
				ArrayList<String> invar=loc.getInvariant();
				ArrayList<String> invariant=new ArrayList<String>();
				for(String i:invar){
					String[] s=i.split("s");
					invariant.add(s[0]);
				}
			
				DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element__1.stringToDBM_element(Clocks, invariant));//将状态中的不变式转成DBM矩阵
				
				State state=new State();
				state.setName(loc.getName());
				state.setInvariantDBM(DBM);
				//state.setFinalState(loc.isFinalState());
				state.setPosition(loc.getName());
				state.setFinalState(loc.isFinalState());
				StateSet.add(state);
			}
			else{
				ArrayList<String> invariant=new ArrayList<String>();
				DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, invariant));//将状态中的不变式为空，则DBM矩阵为全集
				
				State state=new State();
				state.setName(loc.getName());
				state.setInvariantDBM(DBM);
				//state.setFinalState(loc.isFinalState());
				state.setFinalState(loc.isFinalState());
				state.setPosition(loc.getName());
				StateSet.add(state);
			}
			
		}
		automatic.setStateSet(StateSet);//设定automatic中的状态集合
		
		ArrayList<UppaalTransition> transitions=temPlate.getTransitions();//获取template中的所有转换
		for(UppaalTransition tran:transitions){//遍历转换集合
			if(tran.getConstraint().size()!=0){
				ArrayList<String> cons=tran.getConstraint();//获取转换中的约束
				ArrayList<String> constraint=new ArrayList<String>();
				for(String c:cons){
					String[] s=c.split("s");
					constraint.add(s[0]);
				}
				
				DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element__1.stringToDBM_element(Clocks, constraint));//将转换中的约束转成DBM矩阵
				
				Transition transition=new Transition();
				transition.setConstraintDBM(DBM);
						
				ArrayList<String> events=new ArrayList<String>();
				if(tran.getEvents().size()!=0){
					String event=new String();
					
					for(String e:tran.getEvents())
					{
						event+=e+";";
					}
					events.add(event);
				}
				transition.setEventSet(events);
				
				transition.setResetClockSet(tran.getResetClocks());
				transition.setSource(tran.getSource());
				transition.setTarget(tran.getTarget());
				transition.setTypeIds(tran.getTypeIds());
				transition.setTypes(tran.getTypes());
				//临时添加
				//ArrayList<DBM_element[][]> com_constraint=Complement.complement(constraint, Clocks);
				//transition.setCom_constraint(com_constraint);
				
				TransitionSet.add(transition);
			}
			else{
				ArrayList<String> constraint=new ArrayList<String>();
				DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, constraint));//转换中的约束为空，则DBM矩阵为全集
				
				Transition transition=new Transition();
				transition.setConstraintDBM(DBM);
				
				ArrayList<String> events=new ArrayList<String>();
				if(tran.getEvents().size()!=0){
					String event=new String();
					
					for(String e:tran.getEvents())
					{
						event+=e+";";
					}
					events.add(event);
				}
				transition.setEventSet(events);
				
				transition.setResetClockSet(tran.getResetClocks());
				transition.setSource(tran.getSource());
				transition.setTarget(tran.getTarget());
				TransitionSet.add(transition);
				transition.setTypeIds(tran.getTypeIds());
				transition.setTypes(tran.getTypes());
			}
			
		}
		automatic.setTransitionSet(TransitionSet);//设定automatic中的转换集合
		
		
		
		ArrayList<String> ClockSet=temPlate.getClocks();
		automatic.setClockSet(ClockSet);//设定autotimatic中的时钟集合
		
		//设定automatic中的初始状态
		State initstate=new State();
		initstate.setFinalState(temPlate.getInitState().isFinalState());
		initstate.setName(temPlate.getInitState().getName());
		if(temPlate.getInitState().getInvariant().size()!=0){
			initstate.setInvariantDBM(DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element__1.stringToDBM_element(Clocks, temPlate.getInitState().getInvariant())));
			initstate.setAddClockRelationDBM(initstate.getInvariantDBM());
			initstate.setPosition(temPlate.getInitState().getName());
		}
		else{
			ArrayList<String> invariant=new ArrayList<String>();
			DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, invariant));//将状态中的不变式转成DBM矩阵
			initstate.setInvariantDBM(DBM);
			initstate.setAddClockRelationDBM(initstate.getInvariantDBM());
			initstate.setPosition(temPlate.getInitState().getName());
		}
		automatic.setInitState(initstate);
		//设定automatic的name
		String name=temPlate.getName();
		automatic.setName(name);
		
		
		ArrayList<State> States=automatic.getStateSet();
		for(State s:States){//设置添加了时钟复位后的时钟约束
			s.setAddClockRelationDBM(s.getInvariantDBM());
		}
		//初始状态中时钟复位后的约束也设置下
		//automatic.getInitState().setAddClockRelationDBM(States.get(0).getAddClockRelationDBM());
		
		/*System.out.println("时间自动机名字:"+automatic.getName());
		System.out.println("时间自动机时钟集合：");
		for(String c:automatic.getClockSet()){
			System.out.println(c);
		}
		State iniState=automatic.getInitState();
		System.out.println("初始状态名字："+iniState.getName());
		System.out.println(iniState.getPosition());
		System.out.println(iniState.isFinalState());
		DBM_element[][] DBM=iniState.getInvariantDBM();
		for(int i=0;i<automatic.getClockSet().size()+1;i++){
			for(int j=0;j<automatic.getClockSet().size()+1;j++){
				DBM_element cons=DBM[i][j];
				//System.out.println("DBM_i:"+cons.getDBM_i());
				//System.out.println("DBM_j:"+cons.getDBM_j());
				System.out.println("value:"+cons.getValue());
				System.out.println("Strictness:"+cons.isStrictness());					
			}
		}*/
		/*
		System.out.println("状态个数："+automatic.getStateSet().size());
		int k=0;
		for(State state:automatic.getStateSet()){
			System.out.println("第"+k+"个状态");
			k++;
			DBM_element[][] dbm=state.getInvariantDBM();
			for(int i=0;i<automatic.getClockSet().size()+1;i++){
				for(int j=0;j<automatic.getClockSet().size()+1;j++){
					DBM_element cons=dbm[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());					
				}
			}
			System.out.println(state.getName());
			DBM_element[][] adddbm=state.getAddClockRelationDBM();
			for(int i=0;i<automatic.getClockSet().size()+1;i++){
				for(int j=0;j<automatic.getClockSet().size()+1;j++){
					DBM_element cons=adddbm[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());					
				}
			}
			//System.out.println(state.getPosition());
			//System.out.println(state.isFinalState());
			System.out.println("--------------------");
		}*/
		/*
		System.out.println("迁移个数"+automatic.getTransitionSet().size());
		int p=0;
		for(Transition tran:automatic.getTransitionSet()){
			System.out.println("第"+p+"条迁移");
			p++;
			DBM_element[][] dbm=tran.getConstraintDBM();
			for(int i=0;i<automatic.getClockSet().size()+1;i++){
				for(int j=0;j<automatic.getClockSet().size()+1;j++){
					DBM_element cons=dbm[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());					
				}
			}
			
			System.out.println("源:"+tran.getSource());
			System.out.println("目的："+tran.getTarget());
			
			ArrayList<String> events=tran.getEventSet();
			//System.out.println(events.size());
			for(String e:events){
				System.out.println("事件："+e);
			}
			
			ArrayList<String> reset=tran.getResetClockSet();
			for(String r:reset){
				System.out.println("重置的时钟："+r);
			}
			
			ArrayList<String> typeid=tran.getTypeIds();
			for(String i:typeid){
				System.out.println("typeid:"+i);
			}
			
			ArrayList<String> type=tran.getTypes();
			for(String t:type){
				System.out.println("type:"+t);
			}
			
			
			System.out.println("********************");
		}*/
		
		return automatic;
	}
	
	
	
}
