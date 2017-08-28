package com.horstmann.violet.application.gui.util.tanchao.markovlayout;

import org.dom4j.DocumentException;

public class LayoutMarkov {
  public static void layout(String route,String fileName,String newFileName) {
	  ReadXml readXml=new ReadXml(fileName);
	  TestGraph testGraph=new TestGraph(fileName);
	  try {
		UpdateXml updateXml=new UpdateXml(route,fileName,newFileName);
		try {
			updateXml.update();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} catch (DocumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
//  public static void main(String[] args) {
//	LayoutMarkov.layout("C:\\Users\\Admin\\Desktop\\markov\\Seq_MarkovChainModel2_(601).markov.violet.xml");
//    System.out.println("hello");
//  }
}
