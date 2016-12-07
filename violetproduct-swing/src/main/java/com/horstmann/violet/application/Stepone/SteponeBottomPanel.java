package com.horstmann.violet.application.Stepone;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class SteponeBottomPanel extends JPanel{
	private MainFrame mainFrame;
	private JButton backButton;
	private JButton nextbutton;
	private JPanel leftpanel;
	
	public SteponeBottomPanel(MainFrame mainFrame){
		this.mainFrame = mainFrame;
		init();
	}
	public void init()
	{
		initButton();
		this.setBackground(new Color(233,233,233));
		GridBagLayout layout=new GridBagLayout();
		this.setLayout(layout);
		layout.setConstraints(leftpanel, new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setWeight(0.94, 1));
		layout.setConstraints(backButton, new GBC(1, 0, 1, 1).setFill(GBC.BOTH).setWeight(0.03, 1).setInsets(20, 0, 20, 10));
		layout.setConstraints(nextbutton, new GBC(2, 0, 1, 1).setFill(GBC.BOTH).setWeight(0.03, 1).setInsets(20, 0, 20, 20));
		this.add(backButton);
		this.add(nextbutton);
		this.add(leftpanel);
		buttonlisten();
	}
	public void initButton()
	{
		//初始化按钮
		backButton = new JButton();
		nextbutton = new JButton();
		leftpanel = new JPanel();
		backButton.setText("上一步");
		nextbutton.setText("下一步");
		backButton.setFont(new Font("黑体",Font.PLAIN,16));
		nextbutton.setFont(new Font("黑体",Font.PLAIN,16));
        leftpanel.setBackground(new Color(233,233,233)); 
	}
	public void buttonlisten()
	{
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updatePanelUI();
				mainFrame.getCenterTabPanel().removeAll();
				mainFrame.getCenterTabPanel().add(mainFrame.getHeadTitle());
				mainFrame.getbotoomJSplitPane().setDividerSize(0);
				mainFrame.getCenterTabPanel().updateUI();
				mainFrame.getOpreationPart().updateUI();
				mainFrame.getStepJLabel().updateUI();
				mainFrame.getconsolepartPanel().updateUI();
				mainFrame.getinformationPanel().updateUI();
				mainFrame.getpanel().updateUI();
			}
		});
	}
	private void updatePanelUI()
	{
		mainFrame.getStepJLabel().setVisible(false);
		mainFrame.getconsolepartPanel().setVisible(false);
		mainFrame.getinformationPanel().setVisible(false);
        mainFrame.getpanel().setVisible(false);
		mainFrame.getOpreationPart().setVisible(false);
		mainFrame.getReduceOrEnlargePanel().setVisible(false);
	}
	
}
