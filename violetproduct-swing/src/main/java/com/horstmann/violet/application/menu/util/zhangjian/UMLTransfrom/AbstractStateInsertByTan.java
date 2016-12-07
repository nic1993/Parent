//package com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom1.GetAutomatic;
//import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.AddType;
//import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.Automatic;
//import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.State;
//import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.Transition;
//import com.horstmann.violet.application.menu.util.zhangjian.Database.AbstractState;
//import com.horstmann.violet.application.menu.util.zhangjian.Database.AbstractTransition;
//import com.horstmann.violet.application.menu.util.zhangjian.Database.DataBaseUtil;
//
//public class AbstractStateInsertByTan {
//	//用于接收wqq的抽象状态
//	private List<AbstractState> abStateList =new ArrayList<AbstractState>();
//	
//	public void wqq2zhangExchange(String fileName){
//		//获得wqq的相关的信息
//		Automatic automatic=GetAutomatic.getAutomatic(fileName);
//		Automatic am=AddType.addType(automatic);
//		ArrayList<State> stateList=am.getStateSet();//获得State的集合
//		
//		//int num=DataBaseUtil.getObjNum("select count(*) from abstract_state");
//		int num;
//		List<AbstractState> list=DataBaseUtil.getAllState("select * from abstract_state order by sid desc");
//		if(list==null||list.isEmpty()){
//			num=0;
//		}
//		else{
//			num=list.get(0).getSid();
//		}
//	
//		for(State s :stateList){
//			//将wqq的相关的信息--->转换为zhangjian的相关的信息(state)
//			AbstractState abState =new AbstractState();
//			abState.setSid(++num);//查询数据库里面状态节点的个数
//			abState.setSname(s.getName());
//			abState.setPosition(s.getPosition());
//			abState.setType(s.getType());
//			abState.setInvariantDBM(s.getInvariantDBM().toString());
//			abStateList.add(abState);
//			
//		}
//		DataBaseUtil.insert2State(abStateList, "insert into abstract_state(sid,sname,invariantDBM,position,stype) values(?,?,?,?,?)");
//		
//   }
//}
