package com.horstmann.violet.application.StepThreeTestCase;

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

public class TimeExpandNodePanel {
	   private MainFrame mainFrame;
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
       public void insertNodeLabel(ExpandNode expandNode,IWorkspace workspace,Map<Object, String> nodeTextMap,Map<Object, String> edgeTextMap,JScrollPane XMLPanel,String quota)
       {
    	   isExist = false;
    	   if(expandNodeLabels.size() != 0)
    	   {
    		   for(ExpandNode NodeLabel :expandNodeLabels)
    		   {
    			System.out.println("first: " + NodeLabel.getName());
    			System.out.println("now: " + expandNode.getName());
    			if(NodeLabel.getName().equals(expandNode.getName()))
    			{
    				NodeLabel.setWorkspace(workspace);
    				NodeLabel.setNodeTextMap(nodeTextMap);
    				NodeLabel.setEdgeTextMap(edgeTextMap);
    				NodeLabel.setQuota(quota);
    				NodeLabel.setXMLPanel(XMLPanel);
    				isExist = true;
    			}	
    		   }
    	   }
    	   if(isExist == false)
    	   {
    		   int i = expandNodeLabels.size();
    		   System.out.println("location: " + i);
        	   mainFrame.getStepThreeLeftButton().getTimeExpandLabel().add(expandNode, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 10, 0, 0));
        	   mainFrame.getStepThreeLeftButton().getTimeExpandLabel().repaint();
        	   expandNodeLabels.add(expandNode);
    	   }
       }
	public List<ExpandNode> getExpandNodeLabels() {
		return expandNodeLabels;
	}
       

}
