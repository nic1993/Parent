package com.horstmann.violet.application.gui.util.chenzuo.Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.horstmann.violet.application.gui.DisplayForm;

public class TestFileSpilt {

	public static void main(String[] args) {
		
		TestFileSpilt testFileSpilt=new TestFileSpilt();
		
		File file=new File(System.getProperty("user.dir")+"//src//xx#1.xml");
		
		testFileSpilt.FileSpilt(file);
		
	}
	
	public File[] FileSpilt(File file){
		
		int spiltindex=0;
		String filename=file.getName().replaceAll(".xml", "");
		String name=filename.split("#")[0];
		String type=filename.split("#")[1];
		String baseUrl = "D:/ModelDriverProjectFile"+"/TestCase/";
		String filename1=name+"_1#"+type+".xml";
		String filename2=name+"_2#"+type+".xml";
		File[] files=new File[2];
		
		SAXReader reader = new SAXReader();
		
		try {
			
			Document dom1 = reader.read(file);
			Document dom2 = reader.read(file);
			
			Element TCS1=dom1.getRootElement();
			List<Element> testcaseElements1=TCS1.elements("testcase");
			Element TCS2=dom2.getRootElement();
			List<Element> testcaseElements2=TCS2.elements("testcase");
			
			spiltindex=testcaseElements1.size()/2;
			Controller.offsetTestCaseId=spiltindex;
			
			for(int i=0;i<testcaseElements1.size()-spiltindex;i++){
				testcaseElements2.remove(0);
			}
			
			for(int i=0;i<spiltindex;i++){
				testcaseElements1.remove(testcaseElements1.size()-1);
			}
			
			DomWriteFile(dom1, baseUrl+filename1);
			DomWriteFile(dom2, baseUrl+filename2);
			
			files[0]=new File(baseUrl+filename1);
			files[1]=new File(baseUrl+filename2);
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return files;
		
	}
	
	public void DomWriteFile(Document dom, String path){
		
		try {
			// 定义输出流的目的地
			FileWriter fw = new FileWriter(path);

			// 定义输出格式和字符集
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");

			// 定义用于输出xml文件的XMLWriter对象
			XMLWriter xmlWriter = new XMLWriter(fw, format);
			xmlWriter.write(dom);// *****
			xmlWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
