
package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test_Id {
	public List<String> exchangeTestCase(String xml) {
		//String xml="UAVForXStream.xml";
		//read_radioForXStream.xml    最新xml文档
		Automatic auto=GetAutomatic.getAutomatic(xml);//获得原始的时间自动机
		Automatic automatic=AddType.addType(auto);
		List<String> list=new ArrayList<String>();//用于返回的测试用例的集合
		String stringCase="";//保存每一串需要保存的测试用例
		ArrayList<Automatic> testCase=StateCoverage__1.testCase(automatic);//获得满足状态覆盖的抽象测试序列
		System.out.println("抽象测试序列个数："+testCase.size());
		for(Automatic a :testCase){
			
			System.out.println("测试用例名字:"+a.getName());
		
			List<Transition> listTran=a.getTransitionSet();
			List<State> listState=a.getStateSet();
			String source;
			String target;
			int sourceId;
			int targetId;
			//System.out.println("长度"+listTran.size());
//			for(int j=0;j<listState.size();j++){
//				//System.out.print(listState.get(j).getId()+",");
//			}
			//System.out.println();
			for(int i=0;i<listTran.size();i++){
				//System.out.print(listTran.get(i).getId()+",");
				if(i==0){//第一个节点单独处理
					source=listTran.get(0).getSource();
					for(int j=0;j<listState.size();j++){
						if(listState.get(j).getName().equals(source)){
							sourceId=listState.get(j).getId();
							stringCase="s"+sourceId+",t"+listTran.get(i).getId();
							break;
						}
					}
					target=listTran.get(0).getTarget();
					for(int j=0;j<listState.size();j++){
						if(listState.get(j).getName().equals(target)){
							targetId=listState.get(j).getId();
							stringCase+=",s"+targetId;
							break;
						}
					}
				}//if
				else{//其他的节点
					stringCase+=",t"+listTran.get(i).getId();
					target=listTran.get(i).getTarget();
					for(int j=0;j<listState.size();j++){
						if(listState.get(j).getName().equals(target)){
							targetId=listState.get(j).getId();
							stringCase+=",s"+targetId;
							break;
						}
					}
				}//for else
				
			}//for TranList
		//将每一个测试用例的string添加到list集合中
		list.add(stringCase);
        
		}//for testCase
		return list;
	    //遍历list里面的
//		System.out.println();
//		System.out.println("***");
//	    for(int h=0;h<list.size();h++){
//	    	System.out.println(list.get(h));
//	    }
	    }
}

