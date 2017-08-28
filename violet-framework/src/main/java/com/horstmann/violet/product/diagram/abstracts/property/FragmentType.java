package com.horstmann.violet.product.diagram.abstracts.property;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import com.horstmann.violet.framework.util.SerializableEnumeration;

public class FragmentType extends SerializableEnumeration{

	public FragmentType() {
		// TODO Auto-generated constructor stub
	}
	
	 
	 public void drawType(Graphics2D g2, Rectangle2D r) 
	 {
		 int TypeLocationx=(int) (r.getBounds().getX()+Distancex);
		 int TypeLocationy=(int) (r.getBounds().getY()+Distancey);
		
		 if (this==ALT) 
		 {
			   g2.drawString("alt",TypeLocationx,TypeLocationy); 
		 }
		 if (this==OPT) {  
			   g2.drawString("opt",TypeLocationx,TypeLocationy);
		 }
		 if (this==BREAK) {
			 g2.drawString("break",TypeLocationx,TypeLocationy);			 
		 }  
		 if (this==PAR) 
		 {
			 g2.drawString("par",TypeLocationx,TypeLocationy);
		 }  
		 if (this==LOOP){ 
			   g2.drawString("loop",TypeLocationx,TypeLocationy);
			   
		 }
		 if (this==REF){
			  g2.drawString("ref",TypeLocationx,TypeLocationy);
			   
		 }
	 }
	 public static  FragmentType ALT = new FragmentType();
	 public static  FragmentType OPT = new FragmentType();
	 public static  FragmentType BREAK = new FragmentType();
	 public static  FragmentType PAR = new FragmentType();
	 public static  FragmentType LOOP = new FragmentType(); 
	 public static  FragmentType REF = new FragmentType();
	 public static int Distancex=10;
	 public static int Distancey=15;
	 
	 private String type;

	public String getType() {
		return type;
	}	 
	
}
