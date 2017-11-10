package com.horstmann.violet.application.StepOneBuildModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.omg.CORBA.Current;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

import antlr.debug.NewLineEvent;

public class Outputinformation extends JPanel{
     private JTextArea textField;
     private JPanel consolePanel;   
     private JPanel gapPanel;
     private JPanel panel;
     private JPanel reducePanel;
     private JPanel enlargePanel;
     private JPanel clearPanel;
     private JScrollPane scrollPane;
     
     private JLabel reduceLabel;
     private JLabel enlargeLabel;
     private JLabel clearLabel;
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
    	 textField.setEditable(false);
    	 panel = new JPanel();	 
    	 consolePanel = new JPanel();
    	 gapPanel = new JPanel();
    	 consolePanel.setBackground(Color.white);
    	 gapPanel.setBackground(new Color(222,222,222));
    	 JLabel label = new JLabel();
    	 label.setText("Console");
         label.setIcon(new ImageIcon("resources/icons/22x22/console.png"));
    	 consolePanel.add(label);
    	 scrollPane.add(textField);

    	 reducePanel = new JPanel();
    	 reduceLabel = new JLabel();
    	 reduceLabel.setIcon(new ImageIcon("resources/icons/22x22/reduce.png"));
    	 reducePanel.add(reduceLabel);
    	 reducePanel.setPreferredSize(new Dimension(25, 18));
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
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				reducePanel.setBackground(new Color(222, 222, 222));
				reducePanel.repaint();
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				reducePanel.setBackground(new Color(211, 211, 211));
				reducePanel.repaint();
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
//    	 enlargePanel = new ImagePanel("resources/icons/22x22/enlarge.png", 0, 0);
    	 enlargePanel = new JPanel();
    	 enlargeLabel = new JLabel();
    	 enlargeLabel.setIcon(new ImageIcon("resources/icons/22x22/enlarge.png"));
    	 enlargeLabel.setVerticalTextPosition(JLabel.BOTTOM);
    	 enlargePanel.add(enlargeLabel);
    	 enlargePanel.setPreferredSize(new Dimension(25, 18));
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
				enlargePanel.setBackground(new Color(222, 222, 222));
				enlargePanel.repaint();
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				enlargePanel.setBackground(new Color(211, 211, 211));
				enlargePanel.repaint();
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated me00000000000000000000000000thod stub
				
			}
		});
    	 clearLabel = new JLabel();
    	 clearLabel.setIcon(new ImageIcon("resources/icons/22x22/deleteInformation.png"));
    	 clearLabel.setVerticalTextPosition(JLabel.BOTTOM);
    	 clearPanel = new JPanel();
    	 clearPanel.setLayout(new BorderLayout());
    	 clearPanel.add(clearLabel, BorderLayout.CENTER);
    	 clearPanel.setBackground(new Color(222, 222, 222));
    	 clearLabel.addMouseListener(new MouseListener() {
			
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
				clearPanel.setBackground(new Color(222, 222, 222));
				clearPanel.repaint();
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				clearPanel.setBackground(new Color(211, 211, 211));
				clearPanel.repaint();
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				geTextArea().setText("");
				mainFrame.renewPanel();
			}
		});
    	 
    	 panel.setBackground(new Color(222,222,222));
    	 reducePanel.setBackground(new Color(222,222,222));
    	 enlargePanel.setBackground(new Color(222,222,222)); 
    	 panel.setLayout(new GridBagLayout());
    	 panel.add(consolePanel, new GBC(0, 0).setFill(GBC.NONE).setWeight(0, 0).setInsets(1));
//    	 panel.add(jtreePanel, new GBC(1, 0).setFill(GBC.NONE).setWeight(0, 0).setInsets(1));
    	 panel.add(gapPanel, new GBC(1, 0).setFill(GBC.BOTH).setWeight(1, 0));
    	 panel.add(clearPanel, new GBC(2, 0).setFill(GBC.NONE).setWeight(0, 0).setInsets(0,0,8,8));
    	 panel.add(reducePanel, new GBC(3, 0).setFill(GBC.NONE).setWeight(0, 0).setInsets(3,0,8,0));
    	 panel.add(enlargePanel, new GBC(4, 0).setFill(GBC.NONE).setWeight(0, 0).setInsets(0,0,8,8));

    	 scrollPane.setViewportView(textField);
    	 JScrollBar HorizontalBar = scrollPane.getHorizontalScrollBar();
 		JScrollBar VerticalBar = scrollPane.getVerticalScrollBar();
 		HorizontalBar.addAdjustmentListener(new AdjustmentListener() {
 			@Override
 			public void adjustmentValueChanged(AdjustmentEvent e) {
 				// TODO Auto-generated method stub
 				mainFrame.renewPanel();
 			}
 		});
 		VerticalBar.addAdjustmentListener(new AdjustmentListener() {			
 			@Override
 			public void adjustmentValueChanged(AdjustmentEvent e) {
 				// TODO Auto-generated method stub
 				mainFrame.renewPanel();
 			}
 		});
     }
     
     public JPanel getConsolePanel() {
 		return consolePanel;
 	}
     
     public JTextArea geTextArea()
     {
    	 return this.textField;
     }
}
