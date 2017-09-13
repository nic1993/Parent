package com.horstmann.violet.application.StepFourTestCase;

import java.awt.Color;
import java.awt.Dimension;
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

public class StepFourBottom extends JPanel{
	private MainFrame mainFrame;
	private JButton backButton;
	private JButton nextbutton;
	private JPanel leftpanel;
	
	private int stepThree;
	public StepFourBottom(MainFrame mainFrame){
		this.mainFrame = mainFrame;
		init();
	}
	public void init()
	{
		initButton();
		this.setBackground(new Color(233,233,233));
		GridBagLayout layout=new GridBagLayout();
		this.setLayout(layout);
		layout.setConstraints(leftpanel, new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setWeight(1, 1));
		layout.setConstraints(backButton, new GBC(1, 0, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(15, 0, 15, 10));
		layout.setConstraints(nextbutton, new GBC(2, 0, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(15, 0, 15, 10));
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
		backButton.setPreferredSize(new Dimension(88, 34));
		backButton.setMinimumSize(new Dimension(88, 34));
		backButton.setMaximumSize(new Dimension(88, 34));
		nextbutton.setPreferredSize(new Dimension(88, 34));
		nextbutton.setMinimumSize(new Dimension(88, 34));
		nextbutton.setMaximumSize(new Dimension(88, 34));
		leftpanel = new JPanel();
		backButton.setText("上一步");
		nextbutton.setText("退出");
		backButton.setFont(new Font("黑体",Font.PLAIN,12));
		nextbutton.setFont(new Font("黑体",Font.PLAIN,12));
        leftpanel.setBackground(new Color(233,233,233)); 
	}
	public void buttonlisten()
	{
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mainFrame.getStepTwoExpandBottom().getNextbutton().doClick();
			}
		});
		nextbutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	public JButton getBackButton() {
		return backButton;
	}
}
