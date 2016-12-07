package com.horstmann.violet.application.gui;

import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import com.horstmann.violet.application.consolepart.ConsolePartScrollPane;
import com.horstmann.violet.application.gui.util.wujun.TDVerification.ExistTest;

public class StepSixCenterTabbedPane extends JTabbedPane{
		private JPanel ConsolePartScrollPane;
		public StepSixCenterTabbedPane() 
		{			
			ConsolePartScrollPane=new JPanel();
			this.add("一致性的验证",ConsolePartScrollPane);			
		}
		public JPanel getConsolePartScrollPane() {
			return ConsolePartScrollPane;
		}
		public void setConsolePartScrollPane(JPanel consolePartScrollPane) {
			ConsolePartScrollPane = consolePartScrollPane;
		}
		
	}

