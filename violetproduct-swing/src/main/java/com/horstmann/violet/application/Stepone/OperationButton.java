package com.horstmann.violet.application.Stepone;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.text.html.ImageView;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.menu.FileMenu;

public class OperationButton extends JPanel{
	private JButton openButton;
	private JButton saveButton;
	
	private JButton closeButton;
	private JButton newButton; 
	private JPanel rightpanel;
	private MainFrame mainFrame;
	private FileMenu fileMenu;
	private JPanel otherPanel;
	public OperationButton(MainFrame mainFrame, FileMenu fileMenu)
	{
		this.mainFrame = mainFrame;
		this.fileMenu = fileMenu;
		this.setBackground(new Color(233,233,233));
		init();
	}
	public void init()
	{
		initButton();
	    GridBagLayout layout = new GridBagLayout();
	    this.setLayout(layout);
		this.add(newButton);
		this.add(openButton);
		this.add(saveButton);
		this.add(closeButton);
		this.add(rightpanel);
		this.add(otherPanel);
		
		layout.setConstraints(newButton, new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(10));
		layout.setConstraints(openButton, new GBC(1, 0, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(10));
		layout.setConstraints(saveButton, new GBC(2, 0, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(10));
		layout.setConstraints(closeButton, new GBC(3, 0, 1, 1).setFill(GBC.NONE).setWeight(0, 1).setInsets(10));
		layout.setConstraints(rightpanel, new GBC(4, 0, 1, 1).setFill(GBC.BOTH).setWeight(1, 0));
		layout.setConstraints(otherPanel, new GBC(0, 1, 6, 1).setFill(GBC.BOTH).setWeight(1, 1).setInsets(0));
		buttonListen();
	}
	public void initButton()
	{
		openButton = new JButton();
		saveButton = new JButton();
		newButton =  new JButton();
		closeButton = new JButton();
		rightpanel = new JPanel();
		otherPanel = new JPanel()
		{
			public void paint(Graphics g) {
	            super.paint(g);
	            java.awt.Rectangle rect = this.getBounds();
	            int width = (int) rect.getWidth() - 1;
	            int height = (int) rect.getHeight() - 1;
	            Graphics2D g2 = (Graphics2D)g;
	            g2.setStroke(new BasicStroke(2f));
	            g2.setColor(new Color(188,188,188));
	            g2.drawLine(10, 0, width - 10, 0);
	          }
		};
		otherPanel.setBackground(new Color(233,233,233));
		rightpanel.setBackground(new Color(233,233,233));
	    Icon openIcon = new ImageIcon("resources/icons/22x22/open.png");
	    openButton.setIcon(openIcon);
	    
	    openButton.setHorizontalTextPosition(SwingConstants.CENTER);
	    openButton.setVerticalTextPosition(SwingConstants.BOTTOM);
	    openButton.setFocusPainted(false);
		//saveButton.setSize(icon.getIconHeight(),icon.getIconWidth());
	    openButton.setMargin(new Insets(0, 0, 0, 0));
	    openButton.setContentAreaFilled(false);
	    openButton.setFocusable(true);
	    

		Icon icon = new ImageIcon("resources/icons/22x22/save.png");
		saveButton.setIcon(icon);
		saveButton.setHorizontalTextPosition(SwingConstants.CENTER);
		saveButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		saveButton.setFocusPainted(false);
		saveButton.setBorderPainted(false);
		//saveButton.setSize(icon.getIconHeight(),icon.getIconWidth());
		saveButton.setMargin(new Insets(0, 0, 0, 0));
		saveButton.setContentAreaFilled(false);
		saveButton.setFocusable(true);
		
		saveButton.addMouseListener(new MouseListener() {
			
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
				saveButton.setForeground(Color.black);
				saveButton.setContentAreaFilled(false);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				saveButton.setContentAreaFilled(true);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	
	
		

		Icon newIcon = new ImageIcon("resources/icons/22x22/new.png");
		newButton.setIcon(newIcon);
		newButton.setHorizontalTextPosition(SwingConstants.CENTER);
		newButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		newButton.setFocusPainted(false);
		newButton.setBorderPainted(false);
		//saveButton.setSize(icon.getIconHeight(),icon.getIconWidth());
		newButton.setMargin(new Insets(0, 0, 0, 0));
		newButton.setContentAreaFilled(false);
		newButton.setFocusable(true);
		newButton.addMouseListener(new MouseListener() {	
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
				newButton.setForeground(Color.black);
				newButton.setContentAreaFilled(false);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				newButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				newButton.setContentAreaFilled(true);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

	    Icon closeIcon = new ImageIcon("resources/icons/22x22/close.png");
	    closeButton.setIcon(closeIcon);
	    closeButton.setHorizontalTextPosition(SwingConstants.CENTER);
	    closeButton.setVerticalTextPosition(SwingConstants.BOTTOM);
	    closeButton.setFocusPainted(false);
		//saveButton.setSize(icon.getIconHeight(),icon.getIconWidth());
	    closeButton.setMargin(new Insets(0, 0, 0, 0));
	    closeButton.setContentAreaFilled(false);
	    closeButton.setFocusable(true);

		
		openButton.setBorderPainted(false);
		saveButton.setBorderPainted(false);
		closeButton.setBorderPainted(false);
        newButton.setBorderPainted(false);

        
        
        
	
	}
	public void buttonListen()
	{
		openButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fileMenu.getItem(2).doClick();
			}
		});
		
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fileMenu.getItem(0).doClick();	
			}
		});
		
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fileMenu.getItem(3).doClick();
			}
		});
		
		newButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub		
				JMenu newMenu = fileMenu.getFileNewMenu();
				JMenuItem menuItem = newMenu.getItem(1);
				JMenuItem sequenceItem = ((JMenu)menuItem).getItem(3);
				JMenuItem usecaseItem = ((JMenu)menuItem).getItem(0);			
				if(mainFrame.getCenterTabPanel().getComponent(0).equals(mainFrame.getStepOneCenterSequenceTabbedPane()))
				{		
					sequenceItem.doClick();
				}
				else if (mainFrame.getCenterTabPanel().getComponent(0).equals(mainFrame.getStepOneCenterUseCaseTabbedPane())) 
				{
					 usecaseItem.doClick();
				}
			}
		});
	}	
	public JPanel getOtherPanel(){
		return otherPanel;
	}
	public JButton getOpenButton() {
		return openButton;
	}
	public JButton getSaveButton() {
		return saveButton;
	}
	public JButton getCloseButton() {
		return closeButton;
	}
	public JButton getNewButton() {
		return newButton;
	}
}
