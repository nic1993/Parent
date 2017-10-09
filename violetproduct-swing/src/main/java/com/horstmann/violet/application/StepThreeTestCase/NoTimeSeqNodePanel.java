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
       public void insertNodeLabel(NoTimeSeqNode noTimeSeqNode,JPanel AbstractSequencePanel)
       {
    	   isExist = false;
    	   if(SeqNodeLabels.size() != 0)
    	   {
    		   for(NoTimeSeqNode NodeLabel :SeqNodeLabels)
    		   {
    			if(NodeLabel.getTitle().equals(noTimeSeqNode.getTitle()))
    			{
    				isExist = true;	
    				NodeLabel.setAbstractSequencePanel(AbstractSequencePanel);
    			}
    		   }
    	   }
    	   
    	   if(isExist == false)
    	   {
    		   SeqNodeLabels.add(noTimeSeqNode);
        	   int i = SeqNodeLabels.size() - 1;
        	   mainFrame.getStepThreeLeftButton().getNoTimeSeqNode().add(noTimeSeqNode, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 10, 0, 0));
        	   mainFrame.getStepThreeLeftButton().getNoTimeSeqNode().repaint();
    	   }
    	   
       }
	public List<NoTimeSeqNode> getTestCaseNodeLabels() {
		return SeqNodeLabels;
	}
}
