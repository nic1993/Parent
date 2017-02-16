package com.horstmann.violet.application.StepTwoExpand;

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

public class StepTwoLeftButton extends JPanel{
      private JLabel expandlLabel;
      private JLabel evaluatelLabel;
      private JLabel exchangeLabel;
      private List<String> nameList; //ÑéÖ¤
      private List<String> modelNameList; //Ä£ÐÍ×ª»»
      private MainFrame mainFrame;
      public StepTwoLeftButton(MainFrame mainFrame)
      {
    	  this.mainFrame = mainFrame;
    	  init();
    	  this.setBackground(new Color(200,200,200));
    	  this.setLayout(new GridLayout(9, 1));
    	  this.add(expandlLabel);
          this.add(evaluatelLabel);
          this.add(exchangeLabel);
      }
      private void init()
      {
    	  expandlLabel = new JLabel("Ä£ÐÍÀ©Õ¹",JLabel.CENTER);
          evaluatelLabel = new JLabel("Ä£ÐÍÆÀ¹À",JLabel.CENTER);
          exchangeLabel = new JLabel("Markov×ª»»",JLabel.CENTER);
          expandlLabel.setFont(new Font("Î¢ÈíÑÅºÚ",Font.BOLD,16));
          evaluatelLabel.setFont(new Font("Î¢ÈíÑÅºÚ",Font.PLAIN,16));
          exchangeLabel.setFont(new Font("Î¢ÈíÑÅºÚ",Font.PLAIN,16));
          
          expandlLabel.setHorizontalTextPosition(JLabel.CENTER);
          expandlLabel.setVerticalTextPosition(JLabel.BOTTOM);
          
          evaluatelLabel.setHorizontalTextPosition(JLabel.CENTER);
          evaluatelLabel.setVerticalTextPosition(JLabel.BOTTOM);
          
          exchangeLabel.setHorizontalTextPosition(JLabel.CENTER);
          exchangeLabel.setVerticalTextPosition(JLabel.BOTTOM);
          
          expandlLabel.setIcon(new ImageIcon("resources/icons/64x64/expand.png"));
          evaluatelLabel.setIcon(new ImageIcon("resources/icons/64x64/evaluate.png"));
          exchangeLabel.setIcon(new ImageIcon("resources/icons/64x64/change.png"));
          
          nameList = new ArrayList<String>();
          nameList.add("use_case1");
          
          modelNameList = new ArrayList<String>();
          modelNameList.add("usecase1");
          labelListener();
      }
      private void labelListener()
      {
    	  expandlLabel.addMouseListener(new MouseAdapter() {
    		  @Override
    		public void mousePressed(MouseEvent e) {
    			// TODO Auto-generated method stub
    			//ÎÄ×Ö¼Ó´Ö
    			  expandlLabel.setFont(new Font("Î¢ÈíÑÅºÚ",Font.BOLD,16));
    	          evaluatelLabel.setFont(new Font("Î¢ÈíÑÅºÚ",Font.PLAIN,16));
    	          exchangeLabel.setFont(new Font("Î¢ÈíÑÅºÚ",Font.PLAIN,16));
    	          
    			  mainFrame.getsteponebottmopanel().getNextbutton().doClick();
    		}
		});
    	  evaluatelLabel.addMouseListener(new MouseAdapter() {
    		  @Override
    		public void mousePressed(MouseEvent e) {
    			// TODO Auto-generated method stub
    			  updatePanel(); 
    			  //ÎÄ×Ö¼Ó´Ö
    			  expandlLabel.setFont(new Font("Î¢ÈíÑÅºÚ",Font.PLAIN,16));
    	          evaluatelLabel.setFont(new Font("Î¢ÈíÑÅºÚ",Font.BOLD,16));
    	          exchangeLabel.setFont(new Font("Î¢ÈíÑÅºÚ",Font.PLAIN,16));
    			  //ÁÐ±íÇøÓò
	  
    			  mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoVerificationTabbedPane());
    			  mainFrame.getCenterTabPanel().updateUI();
    			  
    			  mainFrame.getpanel().add(mainFrame.getStepTwoVerificationOperation());
    			  mainFrame.getpanel().updateUI();
    		}
		});
    	  exchangeLabel.addMouseListener(new MouseAdapter() {
                  @Override
                public void mousePressed(MouseEvent e) {
                	// TODO Auto-generated method stub
                	updatePanel();
                	
                	 //ÎÄ×Ö¼Ó´Ö
      			  expandlLabel.setFont(new Font("Î¢ÈíÑÅºÚ",Font.PLAIN,16));
      	          evaluatelLabel.setFont(new Font("Î¢ÈíÑÅºÚ",Font.PLAIN,16));
      	          exchangeLabel.setFont(new Font("Î¢ÈíÑÅºÚ",Font.BOLD,16));
      	          
      	          mainFrame.setModelNameList(modelNameList);
      	          
      	          mainFrame.getpanel().add(mainFrame.getStepTwoExchangeOperation());
      	          
      	          mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoExchangeTabbedPane());
      	          
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
