package com.horstmann.violet.application.gui.util.tiancai.markov;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FuncTest {

	//private static String xmlFileName="umlXmlDocuments\\useCases.xml"; //UML鐢ㄤ緥鏂囦欢鍚�
	private static String xmlFileName="ResetMapXML\\useCases.xml"; //UML鐢ㄤ緥鏂囦欢鍚�
	private static String mcXMLFileName="mcResetMapXML\\";
//	public static void main(String[] args) throws InvalidTagException, IOException {
//
//		//String xmlFileName="PrimaryUseCases.xml";
//		
//		Worker worker=new Worker();
//		worker.transInitial(xmlFileName);
//		
//		//
//		worker.transVerify();
//		worker.transToMarckov();
//		worker.probabilityTest();
//		worker.writeMarkov(mcXMLFileName);
//	}
	
	public static void tranMorkovByTanCai(String formFileName,String toFileName) throws InvalidTagException, IOException {
        
		//String xmlFileName="PrimaryUseCases.xml";
		
		Worker worker=new Worker();
		worker.transInitial(xmlFileName);
		
		//
		worker.transVerify();
		worker.transToMarckov();
		worker.probabilityTest();
		worker.writeMarkov(mcXMLFileName);
	}
}
