package com.horstmann.violet.application.gui.util.xiaole.GraghLayout;

import java.io.File;

import com.horstmann.violet.application.gui.util.xiaole.GraghLayout.TestGraph;
import com.horstmann.violet.application.gui.util.xiaole.GraghLayout.UpdateXml;

public class LayoutUppaal {
	
	public static void layout(String filename) throws Exception{
		
	
		ReadXml readxml=new ReadXml(filename);		
	    int TemplateNum=readxml.getTemplateNum();
	    TestGraph testgraph=new TestGraph(filename); 
	    UpdateXml updatexml=new UpdateXml(filename);
	    for(int Index=0;Index<TemplateNum;Index++)
	    {   	    	
	    	testgraph.init(Index);
	    	updatexml.Update(Index);          		
	    }	   	  
	}
}
