package com.horstmann.violet.application.gui.util.wujun.TDVerification;

import java.util.ArrayList;


public class aoutomatic {
	
	String name;
	ArrayList<OutInterFace> outerInterFace;//½Ó¿Ú

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<OutInterFace> getOuterInterFace() {
		return outerInterFace;
	}

	public void setOuterInterFace(ArrayList<OutInterFace> outerInterFace) {
		this.outerInterFace = outerInterFace;
	}

	@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "name="+name+outerInterFace.toString();
		}
}
