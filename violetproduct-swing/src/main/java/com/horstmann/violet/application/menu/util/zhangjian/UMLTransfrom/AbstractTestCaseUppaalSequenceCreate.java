package com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom1.XML2UppaalUtil;
import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.Automatic;
import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.State;
import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.Transition;
import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.UppaalLocation;
import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.UppaalTemPlate;
import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.UppaalTransition;
import com.horstmann.violet.application.gui.util.xiaole.GraghLayout.ReadXml;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class AbstractTestCaseUppaalSequenceCreate {

	public void createXML(String fileName)throws Exception{
		int x=30,y=30;
		String targetPath="sequence.xml";
		
		XML2UppaalUtil util = new XML2UppaalUtil(new File(fileName));
		UppaalTemPlate temPlate = util.getTemplates().get(0);
		
		ArrayList<UppaalTransition> transitions=temPlate.getTransitions();//automatic中的转换集合
		ArrayList<UppaalLocation> locations=temPlate.getLocations();//获取时间自动机中的所有状态
		
		for(UppaalLocation ul:locations){
			System.out.println(ul.toString());
		}
		
		for(UppaalTransition ut:transitions){
			System.out.println(ut.toString());
		}

//		ReadXml readxml=new ReadXml(fileName);		
//	    int TemplateNum=readxml.getTemplateNum();
//	    System.out.println("-+-+-+-+-+-+-+-+-"+TemplateNum);
		
		System.out.println("开始生成XML文件");
		Document document = DocumentHelper.createDocument();  //创建文档   
		Element nta=document.addElement("nta");  
		Element declaration=nta.addElement("declaration");
		declaration.addText("// Place global declarations here.");
		
	    Element tem=nta.addElement("template");
	    Element nameElement=tem.addElement("name");
	    String xx = String.valueOf(x++);
	    String yy=String.valueOf(y++);
	    nameElement.addAttribute("x",xx );
	    nameElement.addAttribute("y",yy );
	    nameElement.setText("template_");
	    tem.addElement("declaration");
	    
	    
	    for(UppaalLocation ul:locations){
	    	Element loc =tem.addElement("location");
	    	loc.addAttribute("id",ul.getName());
	    	loc.addAttribute("x", xx);
	    	loc.addAttribute("y",yy);
	    	
	    	Element name2=loc.addElement("name");
	    	name2.addAttribute("x", xx);
	    	name2.addAttribute("y", yy);
	    	name2.setText(ul.getName());
	    }
	    
	    for(UppaalTransition ut:transitions){
	    	Element tran=tem.addElement("transition");
	    	tran.addAttribute("id","tran_id"+ut.getSource()+ut.getTarget());
	    	tran.addElement("source").addAttribute("ref",ut.getSource());
	    	tran.addElement("target").addAttribute("ref",ut.getTarget());
	    	Element lable=tran.addElement("label");
	    	lable.addAttribute("kind", "synchronisation");
	    	lable.addAttribute("x", xx);
	    	lable.addAttribute("y", yy);
	    	lable.setText("tran_id"+ut.getSource()+ut.getTarget());
	    	
	    }
	    
	    Element sysElement =nta.addElement("system");
		String doString=document.asXML();
		
//		System.out.println(doString);
		
		File newFile=new File("C:\\Users\\Admin\\Desktop\\项目最新代码\\violetumleditor-master\\violetproduct-swing\\"+targetPath);
		FileOutputStream outputStream = new FileOutputStream(newFile);
		outputStream.write(doString.getBytes());
		
		outputStream.close();	
		System.out.println("转换完成!");   
		System.out.println("***************************************");
		
		
	}
}
