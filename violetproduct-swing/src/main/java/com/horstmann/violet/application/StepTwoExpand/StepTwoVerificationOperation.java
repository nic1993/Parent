package com.horstmann.violet.application.StepTwoExpand;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class StepTwoVerificationOperation extends JPanel{
       private JLabel Verificationlabel;
       private JProgressBar VerificationProgressBar;
       private JButton startVerification;
       private JButton restartVerification;
       private JPanel gapPanel;
       private MainFrame mainFrame;
       public StepTwoVerificationOperation(MainFrame mainFrame)
       {
            this.mainFrame = mainFrame;
            init();
            this.setLayout(new GridBagLayout());
            this.add(Verificationlabel, new GBC(0, 0,  3, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 44, 0, 44));
            this.add(VerificationProgressBar,new GBC(0, 1, 3, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 44, 0, 44));
            this.add(startVerification,new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 44, 10, 420));
            this.add(gapPanel, new GBC(1, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 0));
            this.add(restartVerification, new GBC(2, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 420, 10, 44));
       }
       public void init()
       {
    	   Verificationlabel = new JLabel("请在模型列表中选取需要评估的模型!");
    	   Verificationlabel.setFont(new Font("宋体",Font.PLAIN,16));
    	   
    	   VerificationProgressBar = new JProgressBar();
    	   VerificationProgressBar.setUI(new ProgressUI(VerificationProgressBar, Color.green));
    	   VerificationProgressBar.setPreferredSize(new Dimension(600, 30));
    	   
    	   startVerification = new JButton("开始评估");
//    	   startVerification.setFont(new Font("宋体",Font.PLAIN,10));
    	   restartVerification = new JButton("重新评估");
//    	   restartVerification.setFont(new Font("宋体",Font.PLAIN,10));
    	   gapPanel = new JPanel();
       }
       
}
