package com.horstmann.violet.application.StepTwoModelExpand;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.workspace.sidebar.SideBar;

public class StepTwoCenterRightPanel extends JPanel{
	private MainFrame mainFrame;
	private JButton consoleButton;
	private JButton zoominButton;
	private JButton zoomoutButton;
	private JPanel bottomPanel;
	private int flag = 0;
	
	private int SideBarFlag = 0;
	public StepTwoCenterRightPanel(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		init();
	}
	 public void setflag1(int flag) {
			this.flag = flag;
		}
    private void init()
    {
    	initButton();
    	this.setLayout(new GridBagLayout());
    	this.add(consoleButton,new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(14, 10, 7, 10));
    	this.add(zoominButton,new GBC(0, 1, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(3, 10, 7, 10));
    	this.add(zoomoutButton,new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(3, 10, 7, 10));
    	this.add(bottomPanel,new GBC(0, 3, 1, 1).setFill(GBC.BOTH).setWeight(1, 1));
    	buttonListen();
    }
    private void initButton()
    {
    	consoleButton = new JButton();
    	zoominButton = new JButton();
    	zoomoutButton = new JButton();
    	bottomPanel = new JPanel();
    	Icon newIcon = new ImageIcon("resources/icons/22x22/console.png");
//    	Icon newIcon = new ImageIcon("resources/icons/22x22/zoomin.png");
    	consoleButton.setIcon(newIcon);
    	consoleButton.setBorderPainted(false);
    	consoleButton.setFocusPainted(false);
    	consoleButton.setMargin(new Insets(0, 0, 0, 0));
    	consoleButton.setContentAreaFilled(false);
    	consoleButton.setFocusable(true);
    	consoleButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				consoleButton.setContentAreaFilled(false);
//				consoleButton.setBorderPainted(false);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				consoleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				consoleButton.setContentAreaFilled(true);
//				consoleButton.setBorderPainted(true);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    	Icon sidBarcon = new ImageIcon("resources/icons/22x22/zoomin.png");
    	zoominButton.setIcon(sidBarcon);
    	zoominButton.setBorderPainted(false);
    	zoominButton.setHorizontalTextPosition(SwingConstants.CENTER);
    	zoominButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    	zoominButton.setFocusPainted(false);
    	zoominButton.setMargin(new Insets(0, 0, 0, 0));
    	zoominButton.setContentAreaFilled(false);
    	zoominButton.setFocusable(true);
    	zoominButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				zoominButton.setContentAreaFilled(false);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				zoominButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				zoominButton.setContentAreaFilled(true);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    	bottomPanel.setBackground(new Color(233,233,233));
    	
    
    	Icon zoomoutIcon = new ImageIcon("resources/icons/22x22/zoomout.png");
    	zoomoutButton.setIcon(zoomoutIcon);
    	zoomoutButton.setBorderPainted(false);
    	zoomoutButton.setHorizontalTextPosition(SwingConstants.CENTER);
    	zoomoutButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    	zoomoutButton.setFocusPainted(false);
    	zoomoutButton.setMargin(new Insets(0, 0, 0, 0));
    	zoomoutButton.setContentAreaFilled(false);
    	zoomoutButton.setFocusable(true);
    }
    private void buttonListen()
    {
    	consoleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(flag == 0)
				{
					mainFrame.getinformationPanel().setVisible(false);
					mainFrame.getbotoomJSplitPane().setDividerSize(0);
					flag = 1;
				}
				else if (flag == 1) {
					mainFrame.getinformationPanel().setVisible(true);
					mainFrame.getbotoomJSplitPane().setDividerSize(4);
					mainFrame.getbotoomJSplitPane().setDividerLocation(0.7);
					flag = 0;
				}
			}
		});
    	zoominButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    }
   
}
