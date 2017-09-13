package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.horstmann.violet.application.StepTwoModelExpand.ExpandNodeLabel;
import com.horstmann.violet.application.StepTwoModelExpand.ScenceTabelPanel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class TimeCaseNodePanel {
	   private MainFrame mainFrame;
       private List<TimeTestCaseNode> TestCaseNodeLabels;
       private boolean isExist = false;
       public TimeCaseNodePanel(MainFrame mainFrame)
       {
    	   this.mainFrame = mainFrame;
    	   init();
       }
       public void init()
       {
    	   TestCaseNodeLabels = new ArrayList<TimeTestCaseNode>();
       }
       public void insertNodeLabel(TimeTestCaseNode TimeTestCaseLabel,JPanel TestDataPanel,String quota)
       {
    	   if(TestCaseNodeLabels.size() != 0)
    	   {
    		   for(TimeTestCaseNode NodeLabel :TestCaseNodeLabels)
    		   {
    			if(NodeLabel.getText().equals(TimeTestCaseLabel.getText()))
    			{
    				isExist = true;	
    				NodeLabel.setTestDataPanel(TestDataPanel);
    				NodeLabel.setQuota(quota);
    			}
    		   }
    	   }
    	   if(isExist == false)
    	   {
    		   TestCaseNodeLabels.add(TimeTestCaseLabel);
        	   int i = TestCaseNodeLabels.size() - 1;
        	   mainFrame.getStepThreeLeftButton().getTimeNode().add(TimeTestCaseLabel, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 10, 0, 0));
        	   mainFrame.getStepThreeLeftButton().getTimeNode().repaint();
    	   }
       }
       
       public void insertCustomNodeLabel(TimeTestCaseNode TimeTestCaseLabel,JPanel TestDataPanel,JPanel TestRoute,String quota)
       {
    	   if(TestCaseNodeLabels.size() != 0)
    	   {
    		   for(TimeTestCaseNode NodeLabel :TestCaseNodeLabels)
    		   {
    			   
    			if(NodeLabel.getText().equals(TimeTestCaseLabel.getText()))
    			{
    				isExist = true;	
    				NodeLabel.setTestDataPanel(TestDataPanel);
    				NodeLabel.setTestRoute(TestRoute);
    				NodeLabel.setQuota(quota);
    			}
    		   }
    	   }
    	   if(isExist == false)
    	   {
    		   TestCaseNodeLabels.add(TimeTestCaseLabel);
        	   int i = TestCaseNodeLabels.size() - 1;
        	   mainFrame.getStepThreeLeftButton().getTimeNode().add(TimeTestCaseLabel, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 10, 0, 0));
        	   mainFrame.getStepThreeLeftButton().getTimeNode().repaint();
    	   }
    	   
       }
	public List<TimeTestCaseNode> getTestCaseNodeLabels() {
		return TestCaseNodeLabels;
	}
}
