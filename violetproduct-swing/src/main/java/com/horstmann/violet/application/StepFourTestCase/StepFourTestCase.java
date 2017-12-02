package com.horstmann.violet.application.StepFourTestCase;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;


public class StepFourTestCase extends JPanel{

	private JLabel testCaseLabel;	
	private JPanel testCasePanel;	
	private NameRadionPanel jRadionPanel;
	private JScrollPane scrollPane;
	private static JPanel bottompanel;
	private MainFrame mainFrame;
	
	public StepFourTestCase(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;	
		init();
		this.setLayout(new GridBagLayout());
		this.add(testCaseLabel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(15, 10, 0, 0));
		this.add(testCasePanel,new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 10, 0, 0));	
		this.add(new JPanel(),new GBC(0, 2).setFill(GBC.BOTH).setWeight(1, 1).setInsets(0, 10, 0, 0));	
	}
	public void init()
	{
		this.setBackground(new Color(233,233,233));	
		testCaseLabel = new JLabel("可靠性测试数据执行");
     	testCaseLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
		testCasePanel = new JPanel();		
		testCasePanel.setBackground(new Color(233,233,233));	
		
		jRadionPanel = mainFrame.getNameRadionPanel();
		scrollPane = new JScrollPane(jRadionPanel);
		scrollPane.setBorder(null);
		
		testCasePanel.setLayout(new GridBagLayout());
		testCasePanel.add(scrollPane, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 15, 0, 0));
		
	}
	public void clearPanel()
	{
		mainFrame.getpanel().removeAll();
		mainFrame.getCenterTabPanel().removeAll();
	}
    
	public JLabel getExpandModelLabel() {
		return testCaseLabel;
	}
	public NameRadionPanel getjRadionPanel() {
		return jRadionPanel;
	}	
	
}
