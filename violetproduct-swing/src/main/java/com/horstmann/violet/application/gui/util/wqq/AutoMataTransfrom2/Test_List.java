package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.horstmann.violet.application.menu.util.zhangjian.Database.AbstractTestCase;


public class Test_List {
	public List<String> exchangeTestCaseList(String xml) {
		// TODO Auto-generated method stub
		//="throttle_zero_flagForXStream.xml";
		//String xml="UAVForXStream.xml";	
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
		
				
		/*System.out.println("抽象测试序列个数："+testCase.size());
		for(Automatic a:testCase){		
			System.out.println("测试用例名字:"+a.getName());	
			for(Transition tran:a.getTransitionSet()){
				System.out.println(tran.getSource()+"---->"+tran.getTarget()+"约束： "+tran.getEventSet());
			}
			System.out.println("*********************************");
		}*/
		
		
		ArrayList<String> list = new ArrayList<String>();  
		System.out.println("抽象测试序列个数："+testCase.size());
		String str = "";
		for(Automatic aut:testCase){
			System.out.println("测试用例名字:"+aut.getName()); 
			int k=0;
			for(Transition tran:aut.getTransitionSet()){
				
				//String str2 = tran.getEventSet().toString().replace("[", "").replace("]", "").replace(";", "");
				String str2 = tran.getEventSet().toString().replace("[", "").replace("]", "");
				String str1 = tran.getSource().toString();
				String str3 = tran.getTarget().toString();

				if(k==0){
					str = str+str1+","+str2+","+str3;
					k=1;
				}
				else{
					str = str+","+str2+","+str3;
				}
			}
			list.add(str);
			System.out.println("list:"+list);
			}
		
		//System.out.println("list的长度为："+list.size());
		/*try {
			System.setOut(new PrintStream("D:\\eclipse\\workspace\\Automatic_43\\a.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*for(int i=0;i<list.size();i++){
		System.out.println("list"+i+"为:"+list.get(i));
		}*/
		System.out.println("list的长度为："+list.size());
		return list;
		}

			
	}



		
		
		
		
