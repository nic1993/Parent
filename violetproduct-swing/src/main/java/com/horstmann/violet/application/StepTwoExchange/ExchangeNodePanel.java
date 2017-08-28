package com.horstmann.violet.application.StepTwoExchange;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.horstmann.violet.application.StepTwoModelExpand.ExchangeNodeLabel;
import com.horstmann.violet.application.StepTwoModelExpand.EvaluateNodeLabel;
import com.horstmann.violet.application.StepTwoModelExpand.ExpandNodeLabel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class ExchangeNodePanel {
	   private MainFrame mainFrame;
       private JPanel evaluatePanel;
       private List<ExchangeNodeLabel> exchangeNodeLabels;
       private boolean isExist = false;
       public ExchangeNodePanel(MainFrame mainFrame)
       {
    	   this.mainFrame = mainFrame;
    	   init();
       }
       public void init()
       {
    	   this.evaluatePanel = mainFrame.getStepTwoExpand().getExchangepPanel();
    	   this.evaluatePanel.setLayout(new GridBagLayout());
    	   exchangeNodeLabels = new ArrayList<ExchangeNodeLabel>();
       }
       public void insertNodeLabel(ExchangeNodeLabel evaluateNodeLabel)
       {
    	   if(exchangeNodeLabels.size() != 0)
    	   {
    		   for(ExchangeNodeLabel NodeLabel :exchangeNodeLabels)
    		   {
    			if(NodeLabel.getName().equals(evaluateNodeLabel.getName()))
    			{
    				isExist = true;
    			}
    		   }
    	   }
    	   if(isExist == false)
    	   {
    		   exchangeNodeLabels.add(evaluateNodeLabel);
        	   int i = exchangeNodeLabels.size() - 1;
        	   evaluatePanel.add(evaluateNodeLabel, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 25, 0, 0));
        	   evaluatePanel.repaint();
    	   }
    	   
       }
	public List<ExchangeNodeLabel> getExchangeNodeLabels() {
		return exchangeNodeLabels;
	}
       

}
