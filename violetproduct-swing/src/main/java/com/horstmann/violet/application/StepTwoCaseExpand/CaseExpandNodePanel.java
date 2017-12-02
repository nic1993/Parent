package com.horstmann.violet.application.StepTwoCaseExpand;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.horstmann.violet.application.StepTwoModelExpand.ExpandNodeLabel;
import com.horstmann.violet.application.StepTwoModelExpand.ScenceTabelPanel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class CaseExpandNodePanel {
	   private MainFrame mainFrame;
       private JPanel expandCasePanel;
       private List<ExpandNodeLabel> expandNodeLabels;
       private boolean isExist = false;
       public CaseExpandNodePanel(MainFrame mainFrame)
       {
    	   this.mainFrame = mainFrame;
    	   init();
       }
       public void init()
       {
    	   this.expandCasePanel = mainFrame.getsteponeButton().getExpandCasePanel();
    	   this.expandCasePanel.setLayout(new GridBagLayout());
    	   expandNodeLabels = new ArrayList<ExpandNodeLabel>();
       }
       public void insertNodeLabel(ExpandNodeLabel expandNodeLabel,JPanel panel)
       {
    	   if(expandNodeLabels.size() != 0)
    	   {
    		   for(ExpandNodeLabel NodeLabel :expandNodeLabels)
    		   {
    			if(NodeLabel.getName().equals(expandNodeLabel.getName()))
    			{
    				NodeLabel.setResultPanel(panel);
    				isExist = true;
    			}
    		   }
    	   }
    	   
    	   if(isExist == false)
    	   {
    		   expandNodeLabel.setResultPanel(panel);
    		   expandNodeLabels.add(expandNodeLabel);
        	   int i = expandNodeLabels.size() - 1;
        	   expandCasePanel.add(expandNodeLabel, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 25, 0, 0));
        	   expandCasePanel.repaint();
    	   }
    	   mainFrame.renewPanel();
       }
	public List<ExpandNodeLabel> getExpandNodeLabels() {
		return expandNodeLabels;
	}
       

}
