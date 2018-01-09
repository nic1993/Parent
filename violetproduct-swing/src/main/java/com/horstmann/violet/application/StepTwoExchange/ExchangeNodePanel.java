package com.horstmann.violet.application.StepTwoExchange;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.horstmann.violet.application.StepTwoModelExpand.ExchangeNodeLabel;
import com.horstmann.violet.application.StepTwoModelExpand.EvaluateNodeLabel;
import com.horstmann.violet.application.StepTwoModelExpand.ExpandNodeLabel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.workspace.IWorkspace;

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
       public void insertNodeLabel(ExchangeNodeLabel evaluateNodeLabel,IWorkspace workspace,JScrollPane XMLPanel,
    		   Map<Object, String> nodeTextMap,Map<Object, String> edgeTextMap)
       {
    	   isExist = false;
    	   if(exchangeNodeLabels.size() != 0)
    	   {
    		   for(ExchangeNodeLabel NodeLabel :exchangeNodeLabels)
    		   {
    			if(NodeLabel.getName().equals(evaluateNodeLabel.getName()))
    			{
    				NodeLabel.setWorkspace(workspace);
    				NodeLabel.setXMLPanel(XMLPanel);
    				NodeLabel.setNodeTextMap(nodeTextMap);
    				NodeLabel.setEdgeTextMap(edgeTextMap);
    				isExist = true;
    			}
    		   }
    	   }
    	   if(isExist == false)
    	   {
        	   int i = exchangeNodeLabels.size();
        	   evaluatePanel.add(evaluateNodeLabel, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 25, 0, 0));
        	   evaluatePanel.repaint();
        	   exchangeNodeLabels.add(evaluateNodeLabel);
    	   }
    	   mainFrame.renewPanel();
    	   
       }
	public List<ExchangeNodeLabel> getExchangeNodeLabels() {
		return exchangeNodeLabels;
	}
       

}
