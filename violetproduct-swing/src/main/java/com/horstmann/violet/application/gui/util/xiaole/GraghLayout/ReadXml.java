package com.horstmann.violet.application.gui.util.xiaole.GraghLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
/*
 * 读取XML文件
 */
	public class ReadXml {
		SAXReader reader= new SAXReader();	
		Document dom;
		Element root;
		List<Element> Templatelist=new ArrayList<Element>();//XMl中Template集合
		int A[][]=new int[20][20];	
		int TransitionNum=0,
		    LocationNum=0,
		    TemplateNum=0;
		List<Element> transitionlist=new ArrayList<Element>();//transition集合
		List<Element> locationlist=new ArrayList<Element>();//location集合
	  public ReadXml(String filename) throws DocumentException
	  {
			dom=reader.read(new File(filename));				
			root=dom.getRootElement();	
			Templatelist=root.elements("template");
	  }
	    //获得某一个index的template的信息(transition,location)
		public int[][] find(int a) throws Exception {	//a表示template的index
			transitionlist=Templatelist.get(a).elements("transition");
			locationlist=Templatelist.get(a).elements("location");
			int m=0,n=0,N=0;		
		    for(Element transition :transitionlist)
		    {//遍历边的集合
		    	String source=transition.element("source").attribute("ref").getValue();//获得起点
		    	String target=transition.element("target").attribute("ref").getValue();//获得终点  	  
		    		for(Element location: locationlist)			    		
		    		 {		    			
		    			if(source.equals(location.attribute("id").getValue())==true)
		    			{ //如果边的的开始的位置在locationList的集合中找到，说明存在    				
		    				N=locationlist.indexOf(location);
		    				m=N;
		    			}		    					    		 		    			    	
		    		    if(target.equals(location.attribute("id").getValue())==true)
		    		    { 
		    		       N=locationlist.indexOf(location);
		    			   n=N;		    			  	    			   
		    		    }	    		   		    		          
		    		}	
		    		A[m][n]=1; //表示在二维数组中这条边存在		    		    		
		   		}		  
		    return A;//返回每个Template中边的集合，用二维数组表示
			}		
		public int getTransitionNum(int a) {//获得某一个template中边的数目
		transitionlist=Templatelist.get(a).elements("transition");			
		Iterator transitionIterator = transitionlist.iterator();
			while(transitionIterator.hasNext())
			{
				TransitionNum++;
				transitionIterator.next();//
			}			
			return TransitionNum;
		}
		public void setTransitionNum(int transitionNum) {
			TransitionNum = transitionNum;
		}
		public int getLocationNum(int a) {//获得某一个template中的location的数目	
			locationlist=Templatelist.get(a).elements("location");
			Iterator locationIterator = locationlist.iterator();					
			while(locationIterator.hasNext())
			{
				LocationNum++;
				locationIterator.next();//
			}										
			return LocationNum;
		}
		public void setLocationNum(int locationNum) {
			LocationNum = locationNum;
		}		
		public int getTemplateNum() throws DocumentException {//获取Template个数
		
			Iterator TemplateIterator = Templatelist.iterator();
			while(TemplateIterator.hasNext())
			{
			    TemplateNum++;
			    TemplateIterator.next();
			}		
			return TemplateNum;
		}
		public void setTemplateNum(int templateNum) {
			TemplateNum = templateNum;
		}
		public String[] GetID(int a) throws Exception//获取每个Template中的顶点数组
		{
			locationlist=Templatelist.get(a).elements("location");
		    int j=0;
			String VertexID[]=new String[20];
			for(Element location:locationlist)		
       		{			
			 VertexID[j]=location.attribute("id").getValue();
			 j++;
       	    }
			return VertexID;
		}//这里所有的方法函数的参数都是a,a在这里控制着输入的是第几个Template	
	}	
		    	
		    	
		    
		
			
		
	