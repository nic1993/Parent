package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;

public class Test_ckt {
	public static void main(String[] args) {
		String xml="UAVForXStream.xml";
		Automatic auto=GetAutomatic.getAutomatic(xml);//获得原始的时间自动机
		Automatic automatic=AddType.addType(auto);
		ArrayList<Automatic> testCase=StateCoverage__1.testCase(automatic);//获得满足状态覆盖的抽象测试序列

		
		System.out.println("抽象测试序列个数："+testCase.size());
		for(Automatic a:testCase){			
			System.out.println("测试用例名字:"+a.getName());
			System.out.print("迁移id为：");
			for(Transition tran:a.getTransitionSet()){
				System.out.print(tran.getId()+",");//+"%%%%%%%%%"+tran.getSource()+"---->"+tran.getTarget()+"约束： "+tran.getEventSet());
				//System.out.println(tran.getId()+"%%%%%%%%%%%%%%%%%%%%%%%%");
			}
			System.out.println("");
			System.out.print("状态id为：");
			for(State s:a.getStateSet()){
				System.out.print(s.getId()+",");
			}

			System.out.println("");
			System.out.println("*********************************");
		}
		
	
	}
}
