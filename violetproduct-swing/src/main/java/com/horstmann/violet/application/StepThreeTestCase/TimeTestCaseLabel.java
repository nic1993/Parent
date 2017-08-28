package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.horstmann.violet.application.gui.MainFrame;

public class TimeTestCaseLabel extends JLabel{
       private Icon icon;
       private String name;
       private String quota;
       private StepThreeTabelPanel stepThreeTabelPanel;
       private JPanel centerPanel;
       private MainFrame mainFrame;
       public TimeTestCaseLabel(String name,MainFrame mainFrame)
       {
    	   init();
    	   this.mainFrame = mainFrame;
    	   this.name = name;
    	   this.setIcon(icon);
    	   this.setText(name);
    	   this.setFont(new Font("宋体", Font.PLAIN, 16));
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
			mainFrame.getTimeExpandTabbedPane().getExpandResults().removeAll();
			mainFrame.getTimeExpandTabbedPane().getExpandResults().add(stepThreeTabelPanel);
			mainFrame.getTimeExpandTabbedPane().getExpandResport().removeAll();
			mainFrame.getTimeExpandTabbedPane().getExpandResport().add(centerPanel);
			mainFrame.getTimeExpandTabbedPane().repaint();
			
			if(mainFrame.getStepThreeChoosePattern().getselectString().equals("自定义测试用例个数生成"))
			{
					mainFrame.getTimeCaseOperation1().getTopLabel().removeAll();
					mainFrame.getTimeCaseOperation1().getTopLabel().setText(((NoTimeTestCaseNode)e.getSource()).getQuota());
			}
			else {
					mainFrame.getTimeCaseOperation().getTopLabel().removeAll();
					mainFrame.getTimeCaseOperation().getTopLabel().setText(((NoTimeTestCaseNode)e.getSource()).getQuota());
			}
			
			for(NoTimeTestCaseNode noTimeTestCaseLabel : mainFrame.getStepThreeLeftButton().getNoTimeCaseNodePanel().getTestCaseNodeLabels())
			{
				noTimeTestCaseLabel.setFont(new Font("宋体", Font.PLAIN, 16));
			}
			((NoTimeTestCaseNode)e.getSource()).setFont(new Font("宋体", Font.BOLD, 16));
    		};
		});
    }
//	public void clearPanel()
//	{
//		mainFrame.getpanel().removeAll();
//		mainFrame.getCenterTabPanel().removeAll();
//	}
   
 
   	public StepThreeTabelPanel getStepThreeTabelPanel() {
		return stepThreeTabelPanel;
	}
	public void setStepThreeTabelPanel(StepThreeTabelPanel stepThreeTabelPanel) {
		this.stepThreeTabelPanel = stepThreeTabelPanel;
	}
  	public JPanel getCenterPanel() {
   		return centerPanel;
   	}
	public void setCenterPanel(JPanel centerPanel) {
   		this.centerPanel = centerPanel;
   	}
	public String getQuota() {
		return quota;
	}
	public void setQuota(String quota) {
		this.quota = quota;
	}
}
