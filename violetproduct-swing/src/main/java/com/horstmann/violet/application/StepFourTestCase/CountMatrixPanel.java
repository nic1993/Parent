package com.horstmann.violet.application.StepFourTestCase;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

import com.horstmann.violet.application.gui.GBC;

public class CountMatrixPanel extends JPanel{
       private FailReportTableHeaderPanel failReportTableHeaderPanel;
       private TypeFailReportTableHeaderPanel typeFailReportTableHeaderPanel;
       private TestCaseTabelPanel testCaseTabelPanel;
       
       public CountMatrixPanel(FailReportTableHeaderPanel failReportTableHeaderPanel,TestCaseTabelPanel testCaseTabelPanel)
       {
    	   this.testCaseTabelPanel = testCaseTabelPanel;
    	   this.failReportTableHeaderPanel = failReportTableHeaderPanel;
    	   this.setLayout(new GridBagLayout());
 		   this.add(failReportTableHeaderPanel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
 		   this.add(testCaseTabelPanel, new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 1));
       }
       public CountMatrixPanel(TypeFailReportTableHeaderPanel typeFailReportTableHeaderPanel,TestCaseTabelPanel testCaseTabelPanel)
       {
    	   this.testCaseTabelPanel = testCaseTabelPanel;
    	   this.typeFailReportTableHeaderPanel = typeFailReportTableHeaderPanel;
    	   this.setLayout(new GridBagLayout());
 		   this.add(typeFailReportTableHeaderPanel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
 		   this.add(testCaseTabelPanel, new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 1));
       }

}
