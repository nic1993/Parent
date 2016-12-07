package com.horstmann.violet.application.gui.util.yangjie;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class WriteTestCases {

	public static  File  getFile(){
		
		File file = new File("F:/testCases.txt");
		return file;
	}
	@Test
	public static  void writeCases(List<List<String>> list) throws IOException {

		File file = getFile();
		
		BufferedWriter bufw = new BufferedWriter(new FileWriter(file));
		bufw.write(list.toString());
		bufw.newLine();
		bufw.flush();
		bufw.close();
	}

	
}
