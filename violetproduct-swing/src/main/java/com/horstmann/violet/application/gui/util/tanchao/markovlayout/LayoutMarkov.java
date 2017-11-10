package com.horstmann.violet.application.gui.util.tanchao.markovlayout;


import com.horstmann.violet.application.gui.MainFrame;

public class LayoutMarkov {
  public static void layout(String route,String fileName,String newFileName,MainFrame mainFrame) {
	  try {
//	  TestGraph testGraph=new TestGraph(fileName);
//	  JOptionPane.showMessageDialog(null, "testGraph","БъЬт",JOptionPane.WARNING_MESSAGE); 
//	  ReadXml readXml=new ReadXml(fileName);
	  UpdateXml updateXml=new UpdateXml(route,fileName,newFileName,mainFrame); 
	  updateXml.update();

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  public static void main(String[] args) {
//	LayoutMarkov.layout("E:\\markov", "D:\\ModelDriverProjectFile\\NoTimeMarkov\\EADemo2Seq_MarkovChainModel2.markov.violet.xml", 
//			"D:\\ModelDriverProjectFile\\NoTimeMarkov\\EADemo2Seq_MarkovChainModel2layout.markov.violet.xml");
//    System.out.println("hello");
  }
}
