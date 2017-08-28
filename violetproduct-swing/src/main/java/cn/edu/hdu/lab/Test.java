package cn.edu.hdu.lab;


import java.util.ArrayList;
import java.util.List;

import cn.edu.hdu.lab.config.StaticConfig;
import cn.edu.hdu.lab.dao.uml.UseCase;
import cn.edu.hdu.lab.service.parserHDU.XMLReaderHDU;

public class Test {

	public static void main(String[] args) throws Throwable
	{
		/*List<Integer> inList=new ArrayList<Integer>();
		inList.add(1);
		inList.add(2);
		inList.add(3);
		System.out.println(inList.toString());*/
		
		/*XMLReaderHDU read =new XMLReaderHDU(StaticConfig.umlPathHDU);
		List<UseCase> ucList=read.parser();
		for(UseCase uc:ucList)
		{
			uc.print_useCase();
		}*/
		String value="PtStartX=193;PtStartY=-565;PtEndX=693;PtEndY=-565;";
		String[] strs=value.split("PtStartY=-");
		for(String str:strs)
		{
			System.out.println(str);
		}
		
		List<String> cons=new ArrayList<String>();
		String s="";
		cons.add("Hello");
		cons.add("Hi");
		cons.add("Hello");
		for(String str:cons)
		{
			//if(str.equals(anObject))
		}
	}

}
