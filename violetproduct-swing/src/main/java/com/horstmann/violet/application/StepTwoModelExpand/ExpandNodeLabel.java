package com.horstmann.violet.application.StepTwoModelExpand;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.horstmann.violet.application.gui.MainFrame;

public class ExpandNodeLabel extends JLabel{
       private Icon icon;
       private String name;
       private JPanel resultPanel;
       private String quota;
       private MainFrame mainFrame;
       public ExpandNodeLabel(String name,MainFrame mainFrame)
       {
    	   init();
    	   this.mainFrame = mainFrame;
    	   this.name = name;
    	   this.setIcon(icon);
    	   this.setText(name);
    	   this.setFont(new Font("����", Font.PLAIN, 16));
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
//    			clearPanel();
//    			mainFrame.getStepTwoExpand().getExpandModelLabel().setFont(new Font("΢���ź�", Font.PLAIN, 18));
//    			mainFrame.getStepTwoExpand().getExpandCaseModel().setFont(new Font("΢���ź�", Font.BOLD, 18));
//				mainFrame.getStepTwoExpand().getEstimateLabel().setFont(new Font("΢���ź�", Font.PLAIN, 18));
//				mainFrame.getStepTwoExpand().getExchangeLabel().setFont(new Font("΢���ź�", Font.PLAIN, 18));
//				mainFrame.getStepTwoExpand().getExpandCasePanel().setVisible(true);

    			mainFrame.getStepTwoCaseOperation().getToplabel().removeAll();
       			mainFrame.getStepTwoCaseOperation().getToplabel().setText(quota);
       			
				mainFrame.getStepTwoCaseExpandTabbedPane().getValidationResults().removeAll();
				mainFrame.getStepTwoCaseExpandTabbedPane().getValidationResults().add(resultPanel);		
				mainFrame.getStepTwoCaseExpandTabbedPane().setSelectedIndex(1);
				mainFrame.getStepTwoCaseExpandTabbedPane().repaint();
				for(ExpandNodeLabel expandNodeLabel : mainFrame.getStepTwoCaseOperation().getCaseExpandNodePanel().getExpandNodeLabels())
				{
					expandNodeLabel.setFont(new Font("����", Font.PLAIN, 16));
				}
    			setFont(new Font("����", Font.BOLD, 16));
    			mainFrame.renewPanel();
    		};
    		
    		@Override
       		public void mouseEntered(MouseEvent e) {
       			// TODO Auto-generated method stub
       			mainFrame.renewPanel();
       		}
    		
		});
    }
	public void clearPanel()
	{
		mainFrame.getpanel().removeAll();
		mainFrame.getCenterTabPanel().removeAll();
	}
	
   	public String getName() {
		return name;
	}
   	public JPanel getResultPanel() {
   		return resultPanel;
   	}
   	public void setResultPanel(JPanel resultPanel) {
   		this.resultPanel = resultPanel;
   	}
   	public String getQuota() {
		return quota;
	}
	public void setQuota(String quota) {
		this.quota = quota;
	}
}
