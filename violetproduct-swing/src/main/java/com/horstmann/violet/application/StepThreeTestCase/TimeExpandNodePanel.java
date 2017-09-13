package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.horstmann.violet.application.StepTwoModelExpand.ExchangeNodeLabel;
import com.horstmann.violet.application.StepTwoModelExpand.EvaluateNodeLabel;
import com.horstmann.violet.application.StepTwoModelExpand.ExpandNodeLabel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class TimeExpandNodePanel {
	   private MainFrame mainFrame;
       private JPanel expandLabelPanel;
       private List<ExpandNode> expandNodeLabels;
       private boolean isExist = false;
       public TimeExpandNodePanel(MainFrame mainFrame)
       {
    	   this.mainFrame = mainFrame;
    	   init();
       }
       public void init()
       {
    	   expandNodeLabels = new ArrayList<ExpandNode>();
       }
       public void insertNodeLabel(ExpandNode expandNode)
       {
    	   if(expandNodeLabels.size() != 0)
    	   {
    		   for(ExpandNode NodeLabel :expandNodeLabels)
    		   {
    			if(NodeLabel.getName().equals(expandNode.getName()))
    			{
    				isExist = true;
    			}	
    		   }
    	   }
    	   if(isExist == false)
    	   {
    		   expandNodeLabels.add(expandNode);
        	   int i = expandNodeLabels.size() - 1;
        	   mainFrame.getStepThreeLeftButton().getTimeExpandLabel().add(expandNode, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 10, 0, 0));
        	   expandLabelPanel.repaint();
    	   }
    	   
       }
	public List<ExpandNode> getExpandNodeLabels() {
		return expandNodeLabels;
	}
       

}
