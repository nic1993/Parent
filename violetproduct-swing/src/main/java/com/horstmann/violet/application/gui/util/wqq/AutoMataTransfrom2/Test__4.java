package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;
//第四个例子，两条路径
import java.util.ArrayList;

public class Test__4 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String xml="throttle_zero_flagForXStream.xml";
		Automatic auto=GetAutomatic.getAutomatic(xml);//获得原始的时间自动机
		Automatic automatic=AddType.addType(auto);
		/*//ArrayList<State> new_stateSet=Minimization__1.minimization(automatic);
		Automatic new_automatic=IPR__1.iPR(automatic);//获得拆分后的时间自动机
		Automatic aTDRTAutomatic=ATDTR__1.aTDRT(new_automatic,automatic);//获得去除抽象时间迁移后的时间自动机
		//Automatic DFStree=StateCoverage__1.DFSTree(aTDRTAutomatic);
		ArrayList<Automatic> testCase=StateCoverage__1.testCase(aTDRTAutomatic);//获得满足状态覆盖的抽象测试序列
		ArrayList<ArrayList<String>> all_inequalitys=Get_inequality__1.get_AllInequalitys(testCase);//每个抽象测试序列有一个不等式组
		*/
		ArrayList<Automatic> testCase=StateCoverage__1.testCase(automatic);//获得满足状态覆盖的抽象测试序列
		/*System.out.println("时间自动机名字:"+automatic.getName());
		System.out.println("时间自动机时钟集合：");*/
		/*for(String c:automatic.getClockSet()){
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
		/*System.out.println("状态个数："+automatic.getStateSet().size());
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
			System.out.println("状态名称: "+state.getName());
			System.out.println("状态位置: "+state.getPosition());
			System.out.println("是否为终止状态 : "+state.isFinalState());
			System.out.println("Type类型是否为初始："+state.getType());
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
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
			System.out.println("--------------------");
		}*/
		
		/*System.out.println("迁移个数"+automatic.getTransitionSet().size());
		int p=0;
		for(Transition tran:automatic.getTransitionSet()){
			System.out.println("第"+p+"条迁移");
			p++;
			if(tran.getConstraintDBM()!=null){
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
			}
			else System.out.println("时钟约束为空");
			
			System.out.println("源:"+tran.getSource());
			System.out.println("目的："+tran.getTarget());
			
			
			if(tran.getEventSet()==null){
				System.out.println("事件为空");
			}
			else if(tran.getEventSet().size()==0){
				System.out.println("事件为不空，但是没有内容");
			}
			else{
				ArrayList<String> events=tran.getEventSet();
				for(String e:events){
					System.out.println("事件："+e);
				}
			}
			
			if(tran.getResetClockSet()==null){
				System.out.println("重置时钟为空");
			}
			else if(tran.getResetClockSet().size()==0){
				System.out.println("重置时钟为不空，但是没有内容");
			}
			else{
				ArrayList<String> reset=tran.getResetClockSet();
				for(String r:reset){
					System.out.println("重置的时钟："+r);
				}
			}
			
			if(tran.getTypeIds()==null){
				System.out.println("typeID为空");
			}
			else if(tran.getTypeIds().size()==0){
				System.out.println("typeID为不空，但是没有内容");
			}
			else{
				ArrayList<String> typeid=tran.getTypeIds();
				for(String i:typeid){
					System.out.println("typeid:"+i);
				}
			}
			
			if(tran.getTypes()==null){
				System.out.println("types为空");
			}
			else if(tran.getTypes().size()==0){
				System.out.println("types为不空，但是没有内容");
			}
			else{
				ArrayList<String> type=tran.getTypes();
				for(String t:type){
					System.out.println("types:"+t);
				}
			}
			
			System.out.println("********************");
		}*/
		
		
		
		
		
		System.out.println("抽象测试序列个数："+testCase.size());
		for(Automatic a:testCase){
			
			System.out.println("测试用例名字:"+a.getName());
			/*//System.out.println("时间自动机时钟集合：");
			for(String c:a.getClockSet()){
				System.out.println(c);
			}
			State iniState=a.getInitState();
			System.out.println("初始状态名字："+iniState.getName());
			System.out.println(iniState.getPosition());
			System.out.println(iniState.isFinalState());
			DBM_element[][] DBM=iniState.getInvariantDBM();
			for(int i=0;i<a.getClockSet().size()+1;i++){
				for(int j=0;j<a.getClockSet().size()+1;j++){
					DBM_element cons=DBM[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());					
				}
			}
			System.out.println("-----------------");*/
			
			/*System.out.println("状态个数："+a.getStateSet().size());
			int k=0;
			for(State state:a.getStateSet()){
				System.out.println("第"+k+"个状态");
				k++;
				DBM_element[][] dbm=state.getInvariantDBM();
				for(int i=0;i<a.getClockSet().size()+1;i++){
					for(int j=0;j<a.getClockSet().size()+1;j++){
						DBM_element cons=dbm[i][j];
						//System.out.println("DBM_i:"+cons.getDBM_i());
						//System.out.println("DBM_j:"+cons.getDBM_j());
						System.out.println("value:"+cons.getValue());
						System.out.println("Strictness:"+cons.isStrictness());					
					}
				}
				System.out.println("状态名称: "+state.getName());
				System.out.println("状态位置: "+state.getPosition());
				System.out.println("是否为终止状态 : "+state.isFinalState());
				DBM_element[][] adddbm=state.getAddClockRelationDBM();
				for(int i=0;i<a.getClockSet().size()+1;i++){
					for(int j=0;j<a.getClockSet().size()+1;j++){
						DBM_element cons=adddbm[i][j];
						//System.out.println("DBM_i:"+cons.getDBM_i());
						//System.out.println("DBM_j:"+cons.getDBM_j());
						System.out.println("value:"+cons.getValue());
						System.out.println("Strictness:"+cons.isStrictness());					
					}
				}
				System.out.println("------");
			}
			System.out.println("*****************");*/
			
			/*System.out.println("迁移个数"+a.getTransitionSet().size());
			int p=0;
			for(Transition tran:a.getTransitionSet()){
				System.out.println("第"+p+"条迁移");
				p++;
				if(tran.getConstraintDBM()!=null){
					DBM_element[][] dbm=tran.getConstraintDBM();
					for(int i=0;i<a.getClockSet().size()+1;i++){
						for(int j=0;j<a.getClockSet().size()+1;j++){
							DBM_element cons=dbm[i][j];
							//System.out.println("DBM_i:"+cons.getDBM_i());
							//System.out.println("DBM_j:"+cons.getDBM_j());
							System.out.println("value:"+cons.getValue());
							System.out.println("Strictness:"+cons.isStrictness());					
						}
					}
				}
				else System.out.println("时钟约束为空");
				
				System.out.println("源:"+tran.getSource());
				System.out.println("目的："+tran.getTarget());
				
				
				if(tran.getEventSet()==null){
					System.out.println("事件为空");
				}
				else if(tran.getEventSet().size()==0){
					System.out.println("事件为不空，但是没有内容");
				}
				else{
					ArrayList<String> events=tran.getEventSet();
					for(String e:events){
						System.out.println("事件："+e);
					}
				}
				
				if(tran.getResetClockSet()==null){
					System.out.println("重置时钟为空");
				}
				else if(tran.getResetClockSet().size()==0){
					System.out.println("重置时钟为不空，但是没有内容");
				}
				else{
					ArrayList<String> reset=tran.getResetClockSet();
					for(String r:reset){
						System.out.println("重置的时钟："+r);
					}
				}
				
				if(tran.getTypeIds()==null){
					System.out.println("typeID为空");
				}
				else if(tran.getTypeIds().size()==0){
					System.out.println("typeID为不空，但是没有内容");
				}
				else{
					ArrayList<String> typeid=tran.getTypeIds();
					for(String i:typeid){
						System.out.println("typeid:"+i);
					}
				}
				
				if(tran.getTypes()==null){
					System.out.println("types为空");
				}
				else if(tran.getTypes().size()==0){
					System.out.println("types为不空，但是没有内容");
				}
				else{
					ArrayList<String> type=tran.getTypes();
					for(String t:type){
						System.out.println("types:"+t);
					}
				}
				
				System.out.println("********************");
			}
			System.out.println("_________________");*/
			
			for(Transition tran:a.getTransitionSet()){
				System.out.println(tran.getSource()+"---->"+tran.getTarget()+"约束： "+tran.getEventSet());
			}
			System.out.println("*********************************");
		}
		
		
		
		
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
		
	}

}
