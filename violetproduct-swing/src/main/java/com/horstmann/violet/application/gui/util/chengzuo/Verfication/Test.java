package com.horstmann.violet.application.gui.util.chengzuo.Verfication;

import static org.junit.Assert.*;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom1.Main;

public class Test extends JFrame{
  public Test() {
     super("表格");
     JPanel jp=new JPanel();
     List<TestCase> list=new ArrayList<TestCase>() ;
     for(int i=0;i<3;i++){
     	int j=1;
     	TestCase tc=new TestCase();
     	if(i==2){
     		tc.setTestCaseID(String.valueOf(j));
     		tc.setContent("content"+j);
     		tc.setState("state"+j);
     		tc.setResult("失败");
     	}
     	else{
     		tc.setTestCaseID(String.valueOf(j));
     		tc.setContent("content"+j);
     		tc.setState("state"+j);
     		tc.setResult("成功");
     	}
     	j++;
     	list.add(tc);
     }
//     JScrollPane jsp=new JScrollPane(new ConsolePartTestCaseInfoTable(list));
    // this.add(new ConsolePartTestCaseInfoTable(list),BorderLayout.NORTH);
     jp.add(new ConsolePartTestCaseInfoTable(list));
     this.add(jp);
     
     this.pack();
     this.setVisible(true);
  }
  public static void main(String[] args) {
	new Test();
}
}
