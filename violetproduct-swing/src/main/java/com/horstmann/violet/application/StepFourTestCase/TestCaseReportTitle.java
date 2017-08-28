package com.horstmann.violet.application.StepFourTestCase;

import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.horstmann.violet.application.gui.GBC;

public class TestCaseReportTitle extends JPanel{
      private JTable jTable;
      private JScrollPane jsp;
      public TestCaseReportTitle()
      {
    	  this.setLayout(new GridBagLayout());
    	  init();
    	 
      }
      private void init()
      {
    	  TableTitleMode tableTitleMode = new TableTitleMode();
    	  
    	  jTable = new JTable(tableTitleMode);
    	  jTable.getTableHeader().setVisible(true);
    	  
    	  JScrollPane jsp = new JScrollPane(jTable);
    	  jsp.setViewportView(jTable);
    	  
    	  this.add(jsp,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 1));
      }
      
}
