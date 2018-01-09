package com.horstmann.violet.application.StepTwoEvaluate;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.horstmann.violet.application.StepTwoModelExpand.EvaluateNodeLabel;
import com.horstmann.violet.application.StepTwoModelExpand.ExpandNodeLabel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class EvaluateNodePanel {
	   private MainFrame mainFrame;
       private JPanel evaluatePanel;
       private List<EvaluateNodeLabel> evaluateNodeLabels;
       private boolean isExist;
       public EvaluateNodePanel(MainFrame mainFrame)
       {
    	   this.mainFrame = mainFrame;
    	   init();
       }
       public void init()
       {
    	   this.evaluatePanel = mainFrame.getStepTwoExpand().getEstimatepPanel();
    	   this.evaluatePanel.setLayout(new GridBagLayout());
    	   evaluateNodeLabels = new ArrayList<EvaluateNodeLabel>();
       }
       public void insertNodeLabel(EvaluateNodeLabel evaluateNodeLabel,JPanel HomogeneityPanel,JPanel CertaintyPanel,JPanel AccessibilityPanel)
       {
    	   isExist = false;
    	   if(evaluateNodeLabels.size() != 0)
    	   {
    		   for(EvaluateNodeLabel NodeLabel :evaluateNodeLabels)
    		   {
    			if(NodeLabel.getName().equals(evaluateNodeLabel.getName()))
    			{
    				isExist = true;
    				NodeLabel.setHomogeneityPanel(HomogeneityPanel);
    				NodeLabel.setCertaintyPanel(CertaintyPanel);
    				NodeLabel.setAccessibilityPanel(AccessibilityPanel);
    			}	
    		   }
    	   }
    	   if(isExist == false)
    	   {
    		   evaluateNodeLabel.setHomogeneityPanel(HomogeneityPanel);
    		   evaluateNodeLabel.setCertaintyPanel(CertaintyPanel);
    		   evaluateNodeLabel.setAccessibilityPanel(AccessibilityPanel);
        	   int i = evaluateNodeLabels.size();
        	   evaluatePanel.add(evaluateNodeLabel, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 25, 0, 0));
        	   evaluatePanel.repaint();
        	   evaluateNodeLabels.add(evaluateNodeLabel);
    	   }
    	   mainFrame.renewPanel();
       }
	public List<EvaluateNodeLabel> getEvaluateNodeLabels() {
		return evaluateNodeLabels;
	}

       

}
