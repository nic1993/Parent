package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.horstmann.violet.application.StepTwoModelExpand.ExpandNodeLabel;
import com.horstmann.violet.application.StepTwoModelExpand.ScenceTabelPanel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class NoTimeSeqNodePanel {
	   private MainFrame mainFrame;
       private List<NoTimeSeqNode> SeqNodeLabels;
       private boolean isExist = false;
       public NoTimeSeqNodePanel(MainFrame mainFrame)
       {
    	   this.mainFrame = mainFrame;
    	   init();
       }
       public void init()
       {
    	   SeqNodeLabels = new ArrayList<NoTimeSeqNode>();
       }
       public void insertNodeLabel(NoTimeSeqNode noTimeSeqNode,AbstractPagePanel abstractPagePanel,String quota,int type)
       {
    	   isExist = false;
    	   if(SeqNodeLabels.size() != 0)
    	   {
    		   for(NoTimeSeqNode NodeLabel :SeqNodeLabels)
    		   {
    			if(NodeLabel.getTitle().equals(noTimeSeqNode.getTitle()))
    			{
    				NodeLabel.setAbstractPagePanel(abstractPagePanel);
    				NodeLabel.setQuota(quota);
    				NodeLabel.setType(type);
    				isExist = true;	
    				
    			}
    		   }
    	   }
    	   
    	   if(isExist == false)
    	   {
        	   int i = SeqNodeLabels.size();
        	   mainFrame.getStepThreeLeftButton().getNoTimeSeqNode().add(noTimeSeqNode, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 10, 0, 0));
        	   mainFrame.getStepThreeLeftButton().getNoTimeSeqNode().repaint();
        	   SeqNodeLabels.add(noTimeSeqNode);
        	  
    	   }
    	   mainFrame.renewPanel();
       }
	public List<NoTimeSeqNode> getTestCaseNodeLabels() {
		return SeqNodeLabels;
	}
}
