package com.horstmann.violet.application.menu.xiaole.SequenceTransfrom;

import org.dom4j.DocumentException;

import com.horstmann.violet.application.menu.xiaole.SequenceTransfrom.ReadVioletSequence;



public class MainTransVioletToEA {
        public static void main(String agrs[]) throws DocumentException
        {
        	ReadVioletSequence read=new ReadVioletSequence();
        	read.find();
      	    read.writeTiming("D:/×ª»»ºóµÄXML/sequence1.xml");
        }
}
