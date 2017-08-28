package com.horstmann.violet.application.menu.xiaole.SequenceTransfrom;

import java.util.List;

public class MainTransEAToViolet {
        public static void TransEAToViolet(String url,String path,String filename,EADiagram eADiagram) throws Exception
        {
        	TransEAToViolet read = new TransEAToViolet(url,filename,eADiagram);
        	read.ReadEATimingGraph(url);
      	    read.WriteVioletSequence(path);
        }
}
