package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;
//输出测试用例组
import java.util.ArrayList;

import org.junit.Test;

import com.horstmann.violet.application.gui.util.yangjie.Mathematica2;
import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;

public class Get_testcase {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String xml="UAV2ForXStream.xml";
		String xml="read_radio2ForXStream.xml";
		Automatic auto=GetAutomatic.getAutomatic(xml);//获得原始的时间自动机

		ArrayList<Automatic> testCase=StateCoverage__1.testCase(auto);//获得满足状态覆盖的抽象测试序列
		
		System.out.println("抽象测试序列个数："+testCase.size());
		int i = 1;
		for(Automatic a:testCase){	
			System.out.println();
			System.out.println("测试用例名字:"+a.getName());
			int j=1;
			System.out.println("==================第"+i+"条测试用例开始==================");
			for(Transition tran:a.getTransitionSet()){
				System.out.println("                  ==================第"+j+"条迁移开始==================");
				System.out.println("--------->激励名称--------> "+tran.getName());
				//System.out.println(tran.getSource()+"---->"+tran.getTarget()+"约束： "+tran.getEventSet());
			//未处理的约束条件	
			System.out.println("约束："+tran.getEventSet());//约束不等式
			//数字型不等式和参数
				String bds1=Get_str.get_bds(tran.getEventSet().toString());
				//System.out.println(bds1);  //一条迁移上数字不等式
				String cs1=Get_str.get_cs(bds1);
				//System.out.println(cs1);//一条迁移上数字不等式中的参数
				//System.out.println("bds---------->"+bds);
            //布尔型不等式和参数
				String boolbds=Get_str.get_bool_bds(tran.getEventSet().toString());
				//System.out.println(boolbds);//一条迁移上布尔不等式
				String boolcs=Get_str.get_bool_cs(boolbds);
				//System.out.println(boolcs);//一条迁移上布尔不等式中的参数
		   //==0的不等式即为解 ==换为=
				String bds0=Get_str.get_bds_0(tran.getEventSet().toString());
				//
				/*if(bds0!=null){
					System.out.println("==0的不等式解为："+bds0);
				}*/
			
			//调用mma软件求解方程组
				if(((bds1==null)&&(cs1==null))&&((boolbds==null)&&(boolcs==null))){
					System.out.println("没有约束即为：null");
				}
				if((bds1!=null)&&(cs1!=null)){
					String solution1 = Mathematica2.getSolution2(bds1, cs1);
					System.out.println("数值型约束解为："+solution1);
					//System.out.println("数值型约束解为：");
					//System.out.println(solution1);
				}
				if((boolbds!=null)&&(boolcs!=null)){
					String solution2 = Mathematica2.getSolution3(boolbds, boolcs);
					System.out.println("布尔型值解为："+solution2);
					//System.out.println("布尔型值解为：");
					//System.out.println(solution2);
				}
			//==0的不等式即为解 ==换为=	
				if(bds0!=null){
					System.out.println("==0的不等式解为："+bds0);
				}
				
			//调用mma软件求解方程组
				//String solution1 = Mathematica2.getSolution2(bds1, cs1);
				//String solution2 = Mathematica2.getSolution3(boolbds, boolcs);
				
				System.out.println("                  ==================第"+j+"条迁移结束==================");
				j++;
			}
			System.out.println("==================第"+i+"条测试用例结束==================");
			i++;
		}
		
		
	
	}

}

