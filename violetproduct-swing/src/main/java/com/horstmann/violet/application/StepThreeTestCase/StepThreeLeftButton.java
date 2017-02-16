package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.horstmann.violet.application.gui.MainFrame;

public class StepThreeLeftButton extends JPanel{
      private JLabel MarkovExpandlLabel;
      private JLabel testCaseLabel;
      private MainFrame mainFrame;
      public StepThreeLeftButton(MainFrame mainFrame)
      {
    	  this.mainFrame = mainFrame;
    	  init();
    	  this.setBackground(new Color(200,200,200));
    	  this.setLayout(new GridLayout(9, 1));
    	  this.add(MarkovExpandlLabel);
          this.add(testCaseLabel);
      }
      private void init()
      {
    	  MarkovExpandlLabel = new JLabel("MarkovÀ©Õ¹",JLabel.CENTER);
          testCaseLabel = new JLabel("Ä£ÐÍÆÀ¹À",JLabel.CENTER);
          MarkovExpandlLabel.setFont(new Font("Î¢ÈíÑÅºÚ",Font.BOLD,16));
          testCaseLabel.setFont(new Font("Î¢ÈíÑÅºÚ",Font.PLAIN,16));
          
          MarkovExpandlLabel.setHorizontalTextPosition(JLabel.CENTER);
          MarkovExpandlLabel.setVerticalTextPosition(JLabel.BOTTOM);
          
          testCaseLabel.setHorizontalTextPosition(JLabel.CENTER);
          testCaseLabel.setVerticalTextPosition(JLabel.BOTTOM);         
 
          MarkovExpandlLabel.setIcon(new ImageIcon("resources/icons/64x64/expand.png"));
          testCaseLabel.setIcon(new ImageIcon("resources/icons/64x64/evaluate.png"));

          labelListener();
      }
      private void labelListener()
      {
    	  MarkovExpandlLabel.addMouseListener(new MouseAdapter() {
    		  @Override
    		public void mousePressed(MouseEvent e) {
    			// TODO Auto-generated method stub
    			//ÎÄ×Ö¼Ó´Ö
    			  MarkovExpandlLabel.setFont(new Font("Î¢ÈíÑÅºÚ",Font.BOLD,16));
    	          testCaseLabel.setFont(new Font("Î¢ÈíÑÅºÚ",Font.PLAIN,16));       
    		}
		});
    	  testCaseLabel.addMouseListener(new MouseAdapter() {
    		  @Override
    		public void mousePressed(MouseEvent e) {
    			// TODO Auto-generated method stub
    			  updatePanel();
    			  
    			  //ÎÄ×Ö¼Ó´Ö
    			  MarkovExpandlLabel.setFont(new Font("Î¢ÈíÑÅºÚ",Font.PLAIN,16));
    	          testCaseLabel.setFont(new Font("Î¢ÈíÑÅºÚ",Font.BOLD,16));
    			  //ÁÐ±íÇøÓò
   
    		}
		});
      }
      private void updatePanel()
      {  
    	  mainFrame.getOpreationPart().removeAll();
    	  mainFrame.getCenterTabPanel().removeAll();
    	  mainFrame.getpanel().removeAll();
      }
}
