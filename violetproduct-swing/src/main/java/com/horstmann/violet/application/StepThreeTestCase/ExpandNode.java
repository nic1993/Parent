package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.horstmann.violet.application.StepTwoModelExpand.ExpandNodeLabel;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.Workspace;

public class ExpandNode extends JLabel{
       private Icon icon;
       private String name;
       private String quota;
       private Map<Object, String> nodeTextMap;
   	   private Map<Object, String> edgeTextMap;
       private IWorkspace workspace;
       private JScrollPane XMLPanel;
       private MainFrame mainFrame;
       public ExpandNode(String name,MainFrame mainFrame)
       {
    	   init();
    	   this.mainFrame = mainFrame;
    	   this.name = name;
    	   this.setIcon(icon);
    	   this.setText(name);
    	   this.setFont(new Font("ËÎÌå", Font.PLAIN, 16));
       }
       public void init()
       {
    	   icon = new ImageIcon("resources/icons/22x22/picturenodeLabel.png");
    	   listen();
       }
       private void listen()
       {
       	this.addMouseListener(new MouseAdapter() {
       		@Override
       		public void mousePressed(MouseEvent e) {
       			// TODO Auto-generated method stub
//       			clearPanel();
//       			mainFrame.getStepTwoExpand().getExpandModelLabel().setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
//       			mainFrame.getStepTwoExpand().getExpandCaseModel().setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 18));
//   				mainFrame.getStepTwoExpand().getEstimateLabel().setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
//   				mainFrame.getStepTwoExpand().getExchangeLabel().setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
//   				mainFrame.getStepTwoExpand().getExpandCasePanel().setVisible(true);
       			
//       			mainFrame.getStepTwoExchangeTabbedPane().getExchangeResport().removeAll();
//   				mainFrame.getStepTwoExchangeTabbedPane().getExchangeResport().add(XMLPanel);
//   				mainFrame.getStepTwoExchangeTabbedPane().getExchangeResults().removeAll();
//   				mainFrame.addTabbedPane(workspace);
   				
//   				mainFrame.getStepTwoExchangeTabbedPane().setSelectedIndex(0);
       			mainFrame.getTimeExpandTabbedPane().getExpandResults().removeAll();
       			mainFrame.addTabbedPane(workspace);
       			mainFrame.getTimeExpandTabbedPane().getExpandResport().removeAll();
       			mainFrame.getTimeExpandTabbedPane().getExpandResport().add(XMLPanel);
       			
       			mainFrame.getTimeExpandOperation().setNodeTextMap(nodeTextMap);
       			mainFrame.getTimeExpandOperation().setEdgeTextMap(edgeTextMap);
       			
       			mainFrame.getTimeExpandOperation().getExpandlabel().removeAll();
       			mainFrame.getTimeExpandOperation().getExpandlabel().setText(quota);
				
   				for(ExpandNode expandNodeLabel : mainFrame.getStepThreeLeftButton().getTimeExpandNodePanel().getExpandNodeLabels())
   				{
   					expandNodeLabel.setFont(new Font("ËÎÌå", Font.PLAIN, 16));
   				}
       			setFont(new Font("ËÎÌå", Font.BOLD, 16));
       		};
       		@Override
       		public void mouseEntered(MouseEvent e) {
       			// TODO Auto-generated method stub
       			mainFrame.renewPanel();
       		}
   		});
       }
	public IWorkspace getWorkspace() {
		return workspace;
	}
	public void setWorkspace(IWorkspace workspace) {
		this.workspace = workspace;
	}
	public JScrollPane getXMLPanel() {
		return XMLPanel;
	}
	public void setXMLPanel(JScrollPane xMLPanel) {
		this.XMLPanel = xMLPanel;
	}
	public String getName() {
		return name;
	}
	public Map<Object, String> getNodeTextMap() {
		return nodeTextMap;
	}
	public void setNodeTextMap(Map<Object, String> nodeTextMap) {
		this.nodeTextMap = nodeTextMap;
	}
	public Map<Object, String> getEdgeTextMap() {
		return edgeTextMap;
	}
	public void setEdgeTextMap(Map<Object, String> edgeTextMap) {
		this.edgeTextMap = edgeTextMap;
	}
	public String getQuota() {
		return quota;
	}
	public void setQuota(String quota) {
		this.quota = quota;
	} 
	
}
