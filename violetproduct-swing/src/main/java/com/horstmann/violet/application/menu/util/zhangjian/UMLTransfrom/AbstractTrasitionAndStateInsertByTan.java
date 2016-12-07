package com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom1.GetAutomatic;
import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.AddType;
import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.Automatic;
import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.State;
import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.Transition;
import com.horstmann.violet.application.menu.util.zhangjian.Database.AbstractState;
import com.horstmann.violet.application.menu.util.zhangjian.Database.AbstractTransition;
import com.horstmann.violet.application.menu.util.zhangjian.Database.DataBaseUtil;

public class AbstractTrasitionAndStateInsertByTan {
	private List<AbstractState> abStateList =new ArrayList<AbstractState>();
	private List<AbstractTransition> abTransList =new ArrayList<AbstractTransition>();
	public void w2zExchange(String fileName){
		//获得wqq的相关的信息
		Automatic automatic=GetAutomatic.getAutomatic(fileName);
		Automatic am=AddType.addType(automatic);
		ArrayList<State> stateList=am.getStateSet();//获得State的集合
		ArrayList<Transition> tranList = am.getTransitionSet();//获得transition的集合
		
		//int num =DataBaseUtil.getObjNum("select count(*) from abstract_state");
		int num;
		List<AbstractState> list1=DataBaseUtil.getAllState("select * from abstract_state order by sid desc");
		if(list1==null||list1.isEmpty()){
			num=0;
		}
		else{
			num=list1.get(0).getSid();
		}
		//int num=0;
		for(State s :stateList){
			//将wqq的相关的信息--->转换为zhangjian的相关的信息(state)
			AbstractState abState =new AbstractState();
			abState.setSid(++num);//查询数据库里面状态节点的个数
			abState.setSname(s.getName());
			abState.setPosition(s.getPosition());
			abState.setType(s.getType());
			abState.setInvariantDBM(s.getInvariantDBM().toString());
			abStateList.add(abState);
			
		}
		
		int num3;
		List<AbstractTransition> list2=DataBaseUtil.getAllTranstion("select * from abstract_transition order by tid desc");
		if(list2==null||list2.isEmpty()){
			num3=0;
		}
		else{
			num3=list2.get(0).getTid();
		}
	
		System.out.println(num3);
		for(Transition t :tranList){
			//将wqq的相关的信息--->转换为zhangjian的相关的信息(transition)
			
			AbstractTransition abTrans =new AbstractTransition();
//			int num1 =DataBaseUtil.getObjNum("select count(*) from abstract_transition");
			abTrans.setTid(++num3);
			abTrans.setSourceID(this.getStateIdByName(abStateList, t.getSource())+"");
			try {
				abTrans.setSource(new String(t.getSource().getBytes(),"utf-8"));
			
			abTrans.setTargetID(this.getStateIdByName(abStateList, t.getTarget())+"");
			abTrans.setTarget(new String(t.getTarget().getBytes(),"utf-8"));
			abTrans.setType(new String(t.getTypes().toString().getBytes(),"utf-8"));
			StringBuilder sb =new StringBuilder();
			for(int i=0;i<t.getResetClockSet().size();i++){
				sb.append(t.getResetClockSet().get(i));
				if(i!=t.getResetClockSet().size()-1){
					sb.append(";");
				}
			}
			abTrans.setResetClockSet(new String(sb.toString().getBytes(),"utf-8"));
			abTrans.setConstraintDBM(new String(t.getConstraintDBM().toString().getBytes(),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			abTransList.add(abTrans);
		}
	
		
////		Iterator iterator=abTransList.iterator();
////		System.out.println("hello");
////		while(iterator.hasNext()){
////		
////			System.out.println(iterator.next().toString());
////		}
		DataBaseUtil.insert2State(abStateList, "insert into abstract_state(sid,sname,invariantDBM,position,stype) values(?,?,?,?,?)",list1);
		DataBaseUtil.insert2Transition(abTransList, "insert into abstract_transition(tid,ttype,tname,targetid,target,sourceid,source,constraintDBM,ResetClockSet) "
				+ "values(?,?,?,?,?,?,?,?,?)",list2);
		}
		
		public int getStateIdByName(List<AbstractState> list ,String name){
			int num=0;
			for(AbstractState s :list){
				if(name.equals(s.getSname())){
					num=s.getSid();
				}
			}
			return num;
		}
		
	}
