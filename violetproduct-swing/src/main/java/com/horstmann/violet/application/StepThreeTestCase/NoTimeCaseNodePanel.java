package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.horstmann.violet.application.StepTwoModelExpand.ExpandNodeLabel;
import com.horstmann.violet.application.StepTwoModelExpand.ScenceTabelPanel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class NoTimeCaseNodePanel {
	   private MainFrame mainFrame;
       private List<NoTimeTestCaseNode> TestCaseNodeLabels;
       private boolean isExist = false;
       public NoTimeCaseNodePanel(MainFrame mainFrame)
       {
    	   this.mainFrame = mainFrame;
    	   init();
       }
       public void init()
       {
    	   TestCaseNodeLabels = new ArrayList<NoTimeTestCaseNode>();
       }
       public void insertNodeLabel(NoTimeTestCaseNode noTimeTestCaseLabel,JPanel TestDataPanel,String quota)
       {
    	   isExist = false;
    	   if(TestCaseNodeLabels.size() != 0)
    	   {
    		   for(NoTimeTestCaseNode NodeLabel :TestCaseNodeLabels)
    		   {
    			   
    			if(NodeLabel.getText().equals(noTimeTestCaseLabel.getText()))
    			{
    				isExist = true;	
    				NodeLabel.setTestDataPanel(TestDataPanel);
    				NodeLabel.setQuota(quota);
    			}
    		   }
    	   }
    	   if(isExist == false)
    	   {
    		   TestCaseNodeLabels.add(noTimeTestCaseLabel);
        	   int i = TestCaseNodeLabels.size() - 1;
        	   mainFrame.getStepThreeLeftButton().getNoTimeNode().add(noTimeTestCaseLabel, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 10, 0, 0));
        	   mainFrame.getStepThreeLeftButton().getNoTimeNode().repaint();
    	   }
    	   
       }
       
       public void insertCustomNodeLabel(NoTimeTestCaseNode noTimeTestCaseLabel,JPanel TestDataPanel,JPanel TestRoute,String quota)
       {
    	   isExist = false;
    	   if(TestCaseNodeLabels.size() != 0)
    	   {
    		   for(NoTimeTestCaseNode NodeLabel :TestCaseNodeLabels)
    		   {
    			   
    			if(NodeLabel.getText().equals(noTimeTestCaseLabel.getText()))
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
    		   TestCaseNodeLabels.add(noTimeTestCaseLabel);
        	   int i = TestCaseNodeLabels.size() - 1;
        	   mainFrame.getStepThreeLeftButton().getNoTimeNode().add(noTimeTestCaseLabel, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 10, 0, 0));
        	   mainFrame.getStepThreeLeftButton().getNoTimeNode().repaint();
    	   }
    	   
       }
	public List<NoTimeTestCaseNode> getTestCaseNodeLabels() {
		return TestCaseNodeLabels;
	}
}
