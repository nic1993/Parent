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

public class TimeTestCaseNode extends JLabel{
       private Icon icon;
       private String name;
       private String quota;
       private CasePagePanel casePagePanel;
       private JPanel TestRoute;
       private MainFrame mainFrame;
       public TimeTestCaseNode(String name,MainFrame mainFrame)
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
			mainFrame.getCenterTabPanel().removeAll();
			mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeTimeTabbedPane());
			
			mainFrame.getStepThreeTimeTabbedPane().getTestData().removeAll();
			mainFrame.getStepThreeTimeTabbedPane().getTestData().setLayout(new GridLayout());
			mainFrame.getStepThreeTimeTabbedPane().getTestData().add(casePagePanel);
			mainFrame.getStepThreeTimeTabbedPane().repaint();
			
//			if(quota.contains("可靠性测试用例数据库覆盖率:")){
				mainFrame.getTimeCaseOperation1().getTopLabel().removeAll();
				mainFrame.getTimeCaseOperation1().getTopLabel().setText(((TimeTestCaseNode)e.getSource()).getQuota());
//			}
//			else {
				mainFrame.getTimeCaseOperation().getTopLabel().removeAll();
				mainFrame.getTimeCaseOperation().getTopLabel().setText(((TimeTestCaseNode)e.getSource()).getQuota());
			
			
			for(TimeTestCaseNode TimeTestCaseLabel : mainFrame.getStepThreeLeftButton().getTimeCaseNodePanel().getTestCaseNodeLabels())
			{
				TimeTestCaseLabel.setFont(new Font("宋体", Font.PLAIN, 16));
			}
			((TimeTestCaseNode)e.getSource()).setFont(new Font("宋体", Font.BOLD, 16));
			mainFrame.renewPanel();
    		}	
		});
    }
   
	public String getQuota() {
		return quota;
	}
	
	public CasePagePanel getCasePagePanel() {
		return casePagePanel;
	}
	public void setCasePagePanel(CasePagePanel casePagePanel) {
		this.casePagePanel = casePagePanel;
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
