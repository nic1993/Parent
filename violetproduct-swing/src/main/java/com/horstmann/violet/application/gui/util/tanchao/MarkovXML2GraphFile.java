package com.horstmann.violet.application.gui.util.tanchao;

import java.io.File;
import java.io.IOException;

import com.horstmann.violet.application.gui.util.tanchao.markovlayout.PathProp;
import com.horstmann.violet.framework.file.GraphFile;
import com.horstmann.violet.framework.file.IFile;
import com.horstmann.violet.framework.file.LocalFile;

public class MarkovXML2GraphFile {
    public static GraphFile toGraphFile(String path,String name){
    	GraphFile graphFile=null;
//    	String base="C:\\Users\\ccc\\Desktop\\markov\\";
    	File file=new File(path);
    	File[] files=file.listFiles();
    	for(File f:files){
    		String fName=f.getName();
    		if(name.equals(fName)){
    			try {
					IFile file1=new LocalFile(f);
					graphFile=new GraphFile(file1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		
    	}
    	return graphFile;
    }
   
    
}
