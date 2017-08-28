package com.horstmann.violet.application.StepTwoModelExpand;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.horstmann.violet.application.gui.MainFrame;

public class EvaluateNodeLabel extends JLabel{
       private Icon icon;
       private String name;
       private JPanel HomogeneityPanel;
       private JPanel CertaintyPanel;
       private JPanel AccessibilityPanel;
       private MainFrame mainFrame;
       public EvaluateNodeLabel(String nameString,MainFrame mainFrame)
       {
    	   init();
    	   this.mainFrame = mainFrame;
    	   this.name = nameString;
    	   this.setIcon(icon);
    	   this.setText(nameString);
    	   this.setFont(new Font("ËÎÌå", Font.PLAIN, 16));
       }
       public void init()
       {
    	   icon = new ImageIcon("resources/icons/22x22/nodeLabel.png");
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
   				mainFrame.getStepTwoEvaluateTabbedPane().getHomogeneityResults().removeAll();
   				mainFrame.getStepTwoEvaluateTabbedPane().getHomogeneityResults().add(HomogeneityPanel);		
   				mainFrame.getStepTwoEvaluateTabbedPane().getCertaintyResults().removeAll();
   				mainFrame.getStepTwoEvaluateTabbedPane().getCertaintyResults().add(CertaintyPanel);
   				mainFrame.getStepTwoEvaluateTabbedPane().getAccessibilityResults().removeAll();
   				mainFrame.getStepTwoEvaluateTabbedPane().getAccessibilityResults().add(AccessibilityPanel);
   				
   				mainFrame.getStepTwoEvaluateTabbedPane().setSelectedIndex(0);
   				for(EvaluateNodeLabel evaluateNodeLabel : mainFrame.getStepTwoEvaluateOperation().getEvaluateNodePanel().getEvaluateNodeLabels())
   				{
   					evaluateNodeLabel.setFont(new Font("ËÎÌå", Font.PLAIN, 16));
   				}
       			setFont(new Font("ËÎÌå", Font.BOLD, 16));
       		};
       		
   		});
       }

   	public JPanel getHomogeneityPanel() {
   		return HomogeneityPanel;
   	}
   	public void setHomogeneityPanel(JPanel resultPanel) {
   		this.HomogeneityPanel = resultPanel;
   	}
   	
	public JPanel getCertaintyPanel() {
		return CertaintyPanel;
	}
	public void setCertaintyPanel(JPanel certaintyPanel) {
		CertaintyPanel = certaintyPanel;
	}
	public JPanel getAccessibilityPanel() {
		return AccessibilityPanel;
	}
	public void setAccessibilityPanel(JPanel accessibilityPanel) {
		AccessibilityPanel = accessibilityPanel;
	}
	public String getName() {
		return name;
	}
   	
}
