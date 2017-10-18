package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.horstmann.violet.application.StepTwoModelExpand.ExpandNodeLabel;
import com.horstmann.violet.application.StepTwoModelExpand.ScenceTabelPanel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class TimeSeqNodePanel {
	   private MainFrame mainFrame;
       private List<TimeSeqNode> SeqNodeLabels;
       private boolean isExist = false;
       public TimeSeqNodePanel(MainFrame mainFrame)
       {
    	   this.mainFrame = mainFrame;
    	   init();
       }
       public void init()
       {
    	   SeqNodeLabels = new ArrayList<TimeSeqNode>();
       }
       public void insertNodeLabel(TimeSeqNode timeSeqNode,AbstractPagePanel abstractPagePanel)
       {
    	   isExist = false;
    	   if(SeqNodeLabels.size() != 0)
    	   {
    		   for(TimeSeqNode NodeLabel :SeqNodeLabels)
    		   {
    			if(NodeLabel.getTitle().equals(timeSeqNode.getTitle()))
    			{
    				isExist = true;	
    				NodeLabel.setAbstractPagePanel(abstractPagePanel);
    			}
    		   }
    	   }
    	   if(isExist == false)
    	   {
    		   SeqNodeLabels.add(timeSeqNode);
    		   int i = 0;
    		   if(SeqNodeLabels.size() > 0){
    			   i = SeqNodeLabels.size() - 1;
    		   }
        	   
        	   mainFrame.getStepThreeLeftButton().getTimeSeqNode().add(timeSeqNode, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 10, 0, 0));
        	   mainFrame.getStepThreeLeftButton().getTimeSeqNode().repaint();
    	   }
    	   
       }
	public List<TimeSeqNode> getTestCaseNodeLabels() {
		return SeqNodeLabels;
	}
}
