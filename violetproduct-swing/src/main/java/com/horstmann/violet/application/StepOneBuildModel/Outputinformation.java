package com.horstmann.violet.application.StepOneBuildModel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

import antlr.debug.NewLineEvent;

public class Outputinformation extends JPanel{
     private JTextArea textField;
     private JPanel consolePanel;   
	 private JPanel jtreePanel;
     private JPanel gapPanel;
     private JPanel panel;
     private JPanel reducePanel;
     private JPanel enlargePanel;
     private JScrollPane scrollPane;
     private MainFrame mainFrame;
     
     public Outputinformation(MainFrame mainFrame) {
		// TODO Auto-generated constructor stub
    	 this.mainFrame = mainFrame;
    	 init();
    	 GridBagLayout layout = new GridBagLayout();
    	 this.setLayout(layout);
    	 this.add(panel);
    	 this.add(scrollPane);
    	 layout.setConstraints(panel, new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setWeight(1, 0));
    	 layout.setConstraints(scrollPane, new GBC(0, 1, 1, 1).setFill(GBC.BOTH).setWeight(1, 1));
	}
     public void init()
     {
    	 scrollPane = new JScrollPane();
    	 scrollPane.setBorder(null);
    	 textField = new JTextArea();
    	 panel = new JPanel();	 
    	 jtreePanel = new JPanel();
    	 consolePanel = new JPanel();
    	 gapPanel = new JPanel();
    	 consolePanel.setBackground(Color.white);
    	 gapPanel.setBackground(new Color(222,222,222));
    	 jtreePanel.setBackground(new Color(222,222,222));
    	 JLabel label = new JLabel();
    	 label.setText("Console");
         label.setIcon(new ImageIcon("resources/icons/22x22/console.png"));
    	 consolePanel.add(label);
    	 scrollPane.add(textField);
    	 consolePanel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				consolePanel.setBackground(Color.white);
				jtreePanel.setBackground(new Color(222,222,222));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    	 
    	 JLabel label2 = new JLabel();
    	 label2.setText("uml tree");
    	 jtreePanel.add(label2);
    	 jtreePanel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				jtreePanel.setBackground(Color.white);
				consolePanel.setBackground(new Color(222,222,222));
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    	 reducePanel = new ImagePanel("resources/icons/22x22/reduce.png", 0 , 5);
    	 reducePanel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.getinformationPanel().setVisible(false);
				mainFrame.getbotoomJSplitPane().setDividerSize(0);	
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    	 enlargePanel = new ImagePanel("resources/icons/22x22/enlarge.png", 0, 0);
    	 enlargePanel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.getbotoomJSplitPane().setDividerLocation(0);
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated me00000000000000000000000000thod stub
				
			}
		});
    	 panel.setBackground(new Color(222,222,222));
    	 reducePanel.setBackground(new Color(222,222,222));
    	 enlargePanel.setBackground(new Color(222,222,222)); 
    	 panel.setLayout(new GridBagLayout());
    	 panel.add(consolePanel, new GBC(0, 0).setFill(GBC.NONE).setWeight(0, 0).setInsets(1));
//    	 panel.add(jtreePanel, new GBC(1, 0).setFill(GBC.NONE).setWeight(0, 0).setInsets(1));
    	 panel.add(gapPanel, new GBC(1, 0).setFill(GBC.BOTH).setWeight(1, 0));
    	 panel.add(reducePanel, new GBC(2, 0).setFill(GBC.NONE).setWeight(0, 0).setInsets(5,15,5,15));
    	 panel.add(enlargePanel, new GBC(3, 0).setFill(GBC.NONE).setWeight(0, 0).setInsets(5,0,5,15));
    	 scrollPane.setViewportView(textField);
     }
     
     public JPanel getConsolePanel() {
 		return consolePanel;
 	}
     
     public JTextArea geTextArea()
     {
    	 return this.textField;
     }
}
