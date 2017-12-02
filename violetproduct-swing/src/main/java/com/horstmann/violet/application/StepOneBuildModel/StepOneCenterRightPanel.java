package com.horstmann.violet.application.StepOneBuildModel;

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
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.sidebar.SideBar;

public class StepOneCenterRightPanel extends JPanel{
	private MainFrame mainFrame;
	private JButton consoleButton;
	private JButton sidbarbButton;
	private JButton zoomoutButton;
	private JPanel bottomPanel;
	private int flag = 0;
	
	private int SeSideBarFlag = 0;
	private int UsSideBarFlag = 0;
	public StepOneCenterRightPanel(MainFrame mainFrame)
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
    	this.add(consoleButton,new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setInsets(14, 10, 7, 10));
    	this.add(sidbarbButton,new GBC(0, 1, 1, 1).setFill(GBC.BOTH).setInsets(3, 10, 7, 10));
    	this.add(bottomPanel,new GBC(0, 3, 1, 1).setFill(GBC.BOTH).setWeight(1, 1));
    	buttonListen();
    }
    private void initButton()
    {
    	consoleButton = new JButton();
    	sidbarbButton = new JButton();
    	bottomPanel = new JPanel();
    	Icon newIcon = new ImageIcon("resources/icons/22x22/console.png");
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
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				consoleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				consoleButton.setContentAreaFilled(true);
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    	Icon sidBarcon = new ImageIcon("resources/icons/22x22/sidBarReduceOrEnlarge.png");
    	sidbarbButton.setIcon(sidBarcon);
    	sidbarbButton.setBorderPainted(false);
    	sidbarbButton.setHorizontalTextPosition(SwingConstants.CENTER);
    	sidbarbButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    	sidbarbButton.setFocusPainted(false);
    	sidbarbButton.setMargin(new Insets(0, 0, 0, 0));
    	sidbarbButton.setContentAreaFilled(false);
    	sidbarbButton.setFocusable(true);
    	sidbarbButton.addMouseListener(new MouseListener() {
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
				sidbarbButton.setContentAreaFilled(false);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				sidbarbButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				sidbarbButton.setContentAreaFilled(true);
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    	bottomPanel.setBackground(new Color(233,233,233));
    	
    	zoomoutButton = new JButton();
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
				mainFrame.renewPanel();
			}
		});
    	
    	sidbarbButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				IWorkspace workspace = mainFrame.getActiveWorkspace();
				if(workspace != null)
				{
					boolean isVisible = workspace.getAWTComponent().getScrollableSideBar().isVisible();
					if(!isVisible)
					{
						workspace.getAWTComponent().getScrollableSideBar().setVisible(true);
					}
					else {
						workspace.getAWTComponent().getScrollableSideBar().setVisible(false);
					}
					workspace.getAWTComponent().repaint();
					mainFrame.renewPanel();
				}
			}
		});
    }
   
}
