package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.horstmann.violet.application.gui.MainFrame;

public class NoTimeTestCaseNode extends JLabel{
       private Icon icon;
       private String name;
       private String quota;
       private JPanel TestDataPanel;
       private JPanel TestRoute;
       private MainFrame mainFrame;
       public NoTimeTestCaseNode(String name,MainFrame mainFrame)
       {
    	   init();
    	   this.mainFrame = mainFrame;
    	   this.name = name;
    	   this.setIcon(icon);
    	   this.setText(name);
    	   this.setFont(new Font("ËÎÌו", Font.PLAIN, 16));
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
//			if(TestRoute != null)
//			{
//				mainFrame.getCenterTabPanel().removeAll();
//				mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeNoTimeCustomTabbedPane());
//				mainFrame.getStepThreeNoTimeCustomTabbedPane().getTestData().removeAll();
//				mainFrame.getStepThreeNoTimeCustomTabbedPane().getTestData().setLayout(new GridLayout());
//				mainFrame.getStepThreeNoTimeCustomTabbedPane().getTestData().add(TestDataPanel);
//				mainFrame.getStepThreeNoTimeCustomTabbedPane().getTestRoute().removeAll();
//				mainFrame.getStepThreeNoTimeCustomTabbedPane().getTestRoute().setLayout(new GridLayout());
//				mainFrame.getStepThreeNoTimeCustomTabbedPane().getTestRoute().add(TestRoute);
//				mainFrame.getStepThreeNoTimeCustomTabbedPane().repaint();
//				mainFrame.getNoTimeCaseOperation1().getTopLabel().removeAll();
//				mainFrame.getNoTimeCaseOperation1().getTopLabel().setText(((NoTimeTestCaseNode)e.getSource()).getQuota());
//			}
//			else {
				mainFrame.getCenterTabPanel().removeAll();
				mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeNoTimeTabbedPane());
				mainFrame.getStepThreeNoTimeTabbedPane().getTestData().removeAll();
				mainFrame.getStepThreeNoTimeTabbedPane().getTestData().setLayout(new GridLayout());
				mainFrame.getStepThreeNoTimeTabbedPane().getTestData().add(TestDataPanel);
				mainFrame.getStepThreeNoTimeTabbedPane().repaint();
				mainFrame.getNoTimeCaseOperation().getTopLabel().removeAll();
				mainFrame.getNoTimeCaseOperation().getTopLabel().setText(((NoTimeTestCaseNode)e.getSource()).getQuota());
//			}
			for(NoTimeTestCaseNode noTimeTestCaseLabel : mainFrame.getStepThreeLeftButton().getNoTimeCaseNodePanel().getTestCaseNodeLabels())
			{
				noTimeTestCaseLabel.setFont(new Font("ËÎÌו", Font.PLAIN, 16));
			}
			((NoTimeTestCaseNode)e.getSource()).setFont(new Font("ËÎÌו", Font.BOLD, 16));
			mainFrame.renewPanel();
    		}
    	
		});
    }
   
	public String getQuota() {
		return quota;
	}
	public JPanel getTestDataPanel() {
		return TestDataPanel;
	}
	public void setTestDataPanel(JPanel testDataPanel) {
		TestDataPanel = testDataPanel;
	}
	public JPanel getTestRoute() {
		return TestRoute;
	}
	public void setTestRoute(JPanel testRoute) {
		TestRoute = testRoute;
	}
	public void setQuota(String quota) {
		this.quota = quota;
	}
	public String getName() {
		return name;
	}
	
}
