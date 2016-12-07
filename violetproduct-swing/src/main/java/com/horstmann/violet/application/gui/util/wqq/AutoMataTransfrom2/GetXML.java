package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;
//生成测试用例 xml文件
import java.util.ArrayList;
import org.junit.Test;

import com.horstmann.violet.application.gui.util.yangjie.Mathematica2;
import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class GetXML {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String xml="UAV2ForXStream.xml";
		//String xml="read_radioForXStream.xml";
		String xml="read_radio3ForXStream.xml";//tcs1.xml
		//String xml="loop2ForXStream.xml";//tcs2.xml
		
		Automatic auto=GetAutomatic.getAutomatic(xml);//获得原始的时间自动机

		ArrayList<Automatic> testCase=StateCoverage__1.testCase(auto);//获得满足状态覆盖的抽象测试序列
		
		System.out.println("抽象测试序列个数："+testCase.size());
		int i = 1;
		String s=null;
		String s1=null;
		// 1、创建document对象，代表整个xml文档
		Document dom = DocumentHelper.createDocument();
		// 2、创建根节点TCS
		org.dom4j.Element tcs = dom.addElement("TCS");
		// 3、向TCS节点中添加version属性
		for(Automatic a:testCase){		
			System.out.println();
			System.out.println("测试用例名字:"+a.getName());
			System.out.println("============第"+i+"条测试用例开始============");
			
			// 4、生成子节点及节点内容
			Element testcase = tcs.addElement("testcase");
	
			for(Transition tran:a.getTransitionSet()){
				//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

				//System.out.println("--------->激励名称--------> "+tran.getName());
				//System.out.println(tran.getSource()+"---->"+tran.getTarget()+"约束： "+tran.getEventSet());
			//未处理的约束条件	
			//System.out.println("约束："+tran.getEventSet());//约束不等式
			//数字型不等式和参数
				String bds1=Get_str.get_bds(tran.getEventSet().toString());
				//System.out.println("数字不等式:"+bds1);  //一条迁移上数字不等式
				String cs1=Get_str.get_cs(bds1);
				//System.out.println("数字参数:"+cs1);//一条迁移上数字不等式中的参数
				//System.out.println("bds---------->"+bds);
          //布尔型不等式和参数
				String boolbds=Get_str.get_bool_bds(tran.getEventSet().toString());
				//System.out.println("布尔不等式:"+boolbds);//一条迁移上布尔不等式
				String boolcs=Get_str.get_bool_cs(boolbds);
				//System.out.println("布尔参数"+boolcs);//一条迁移上布尔不等式中的参数
		  //==0的不等式即为解 ==换为=
				String bds0=Get_str.get_bds_0(tran.getEventSet().toString().replace("(", "").replace(")", ""));
				//System.out.println("==0的不等式："+bds0);
				//
				/*if(bds0!=null){
					System.out.println("==0的不等式解为："+bds0);
				}*/
				
				//添加节点
				Element process = testcase.addElement("process");
				Element operation = process.addElement("operation");
				if(tran.getName().contains("(")){
					int index11=tran.getName().indexOf("(");
					String sss=tran.getName().substring(0,index11);
					operation.setText(sss);
				}
				else{
					operation.setText(tran.getName());
				}
				
				Element input = process.addElement("input");
				//
			//调用mma软件求解方程组
				if(((bds1==null)&&(cs1==null))&&((boolbds==null)&&(boolcs==null))){
					System.out.println("没有约束即为：null");
					input.setText("null");
				}
				if((bds1!=null)&&(cs1!=null)){
					System.out.println("*******数值不等式*******"+bds1);
					System.out.println("*******数值参数*******"+cs1);
					String solution1 = Mathematica2.getSolution2(bds1, cs1);
					s=solution1.toString().replace("{", "").replace("}", "").replace(" ", "").replace("->", "=").replace("(", "").replace(")", "");
					System.out.println("数值型约束解为："+solution1);
					//System.out.println("数值型约束解为：");
					//System.out.println(solution1);
					//System.out.println("##########"+s);
				}
				if((boolbds!=null)&&(boolcs!=null)){
					String solution2 = Mathematica2.getSolution3(boolbds, boolcs);
					s1=solution2.toString().replace("{", "").replace("}", "").replace(" ", "").replace("->", "=").replace("(", "").replace(")", "");
					System.out.println("布尔型值解为："+solution2);
					//System.out.println("布尔型值解为：");
					//System.out.println(solution2);
					//System.out.println("##########"+s1);
				}
				//==0的不等式即为解 ==换为=	
				if(bds0!=null){
					System.out.println("==0的不等式解为："+bds0);
					if((bds1!=null)&&(cs1!=null)&&(boolbds!=null)&&(boolcs!=null)){
						input.setText(s+","+s1+","+bds0);
					}
					if((bds1!=null)&&(cs1!=null)&&(boolbds==null)&&(boolcs==null)){
						input.setText(s+","+bds0);
					}
					if((bds1==null)&&(cs1==null)&&(boolbds!=null)&&(boolcs!=null)){
						input.setText(s1+","+bds0);
					}
				}
				else{
					if((bds1!=null)&&(cs1!=null)&&(boolbds!=null)&&(boolcs!=null)){
						input.setText(s+","+s1);
					}
					if((bds1!=null)&&(cs1!=null)&&(boolbds==null)&&(boolcs==null)){
						input.setText(s);
					}
					if((bds1==null)&&(cs1==null)&&(boolbds!=null)&&(boolcs!=null)){
						input.setText(s1);
					}
				}
				
			//调用mma软件求解方程组
				//String solution1 = Mathematica2.getSolution2(bds1, cs1);
				//String solution2 = Mathematica2.getSolution3(boolbds, boolcs);
				
				
				
				//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				
			}
			System.out.println("============第"+i+"条测试用例结束============");
			i++;
		}
		OutputFormat format = OutputFormat.createPrettyPrint();
		// 6、生成xml文件
	    File file = new File("C:\\Users\\Administrator\\Desktop\\read_radio.xml");
		//File file = new File("E:\\xml\\tcs4.xml");
		//File file = new File("E:\\xml\\tcs2.xml");
		
	    XMLWriter writer;
		try {
			writer = new XMLWriter(new FileOutputStream(file), format);
			writer.write(dom);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}

}

