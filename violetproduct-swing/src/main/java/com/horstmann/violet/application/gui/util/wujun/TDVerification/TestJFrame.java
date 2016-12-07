package com.horstmann.violet.application.gui.util.wujun.TDVerification;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class TestJFrame extends JFrame{
        public TestJFrame() throws Exception{
        	super("∏„–¶");
        	JSplitPane jp=ExistTest.existTest();
        	this.getContentPane().add(jp);
        	this.pack();
        	this.setVisible(true);
        }
        public static void main(String[] args) throws Exception {
			new TestJFrame();
		}
}
