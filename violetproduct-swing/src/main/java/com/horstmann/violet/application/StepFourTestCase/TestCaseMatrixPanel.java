package com.horstmann.violet.application.StepFourTestCase;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.junit.Test.None;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class TestCaseMatrixPanel extends JPanel{
	   private JPanel titlePanel;
	   private JPanel titleTabel;
	   private JPanel gapPanel;
	   private JLabel reduceTabel; 
	   private JPanel panel;
	   private JPanel tabelPanel;
	   private JLabel Predict;
	   private TestCaseTabelPanel titleCaseTabelPanel;
	   private TestCaseTabelPanel testCaseTabelPanel;
	   private MainFrame mainFrame;
	   public TestCaseMatrixPanel(MainFrame mainFrame)
	   {
		   this.mainFrame = mainFrame;
		   initComponent();
//		  this.setBackground(Color.green);
		  this.setLayout(new GridBagLayout());
		  this.add(titlePanel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
		  this.add(panel, new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 1).setInsets(10, 0, 0, 0));
		  this.setBackground(Color.white);
	   }
	   private void initComponent()
	   {
		   Predict = new JLabel("被测程序执行结果:");
		   Predict.setFont(new Font("微软雅黑", Font.BOLD, 16));
		   titlePanel = new JPanel()
		   {
			   public void paint(Graphics g) {
		            super.paint(g);
		            java.awt.Rectangle rect = this.getBounds();
		            int width = (int) rect.getWidth() - 1;
		            int height = (int) rect.getHeight() - 1;
		            Graphics2D g2 = (Graphics2D)g;
		            g2.setStroke(new BasicStroke(3f));
		            g2.setColor(new Color(188,188,188));
		            Stroke dash = new BasicStroke(2.5f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,
		            		3.5f,new float[]{15,10,},0f);
		            		g2.setStroke(dash);
		            g2.drawLine(0, height, width, height);
		          }
		   };
		   gapPanel = new JPanel();
		   titleTabel = new JPanel();  
		   titleTabel.setLayout(new BorderLayout());
		   reduceTabel = new JLabel();
		   reduceTabel.setFont(new Font("宋体", Font.PLAIN, 18));
		   tabelPanel = new JPanel();
		   tabelPanel.setLayout(new BorderLayout());
		   
		   JPanel panel1 = new JPanel();
		   panel1.setBackground(Color.white);
		   panel = new JPanel();
		   panel.setLayout(new GridBagLayout());
		   panel.add(Predict,new GBC(0, 0,1,1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 5, 8, 0));
		   panel.add(panel1,new GBC(1, 0,1,1).setFill(GBC.BOTH).setWeight(1, 0));
		   panel.add(tabelPanel, new GBC(0, 1,2,1).setFill(GBC.BOTH).setWeight(1, 1));
		   panel.setBackground(Color.white);
		   panel.setVisible(false);
		   
		   reduceTabel.setIcon(new ImageIcon("resources/icons/16x16/smallDown.png"));
		   reduceTabel.setMinimumSize(new Dimension(13, 13));
		   reduceTabel.setMaximumSize(new Dimension(13, 13));
		   reduceTabel.setPreferredSize(new Dimension(13, 13));
		   reduceTabel.setBackground(Color.red);
		   reduceTabel.addMouseListener(new MouseAdapter() {
			   @Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				   if(panel.isVisible())
				   {
					   panel.setVisible(false);
				   }
				   else {
					   panel.setVisible(true);
				}
				   mainFrame.renewPanel();
 
			}
		});   
		   titlePanel.setLayout(new GridBagLayout());
		   titlePanel.add(titleTabel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5));
		   titlePanel.add(reduceTabel,new GBC(1, 0).setFill(GBC.NONE).setWeight(0, 1).setInsets(5));
	   }
    
	public JPanel getTitleTabel() {
		return titleTabel;
	}
	public JPanel getTabelPanel() {
		return tabelPanel;
	}
	public TestCaseTabelPanel getTitleCaseTabelPanel() {
		return titleCaseTabelPanel;
	}
	public void setTitleCaseTabelPanel(TestCaseTabelPanel titleCaseTabelPanel) {
		this.titleCaseTabelPanel = titleCaseTabelPanel;
	}
	public TestCaseTabelPanel getTestCaseTabelPanel() {
		return testCaseTabelPanel;
	}
	public void setTestCaseTabelPanel(TestCaseTabelPanel testCaseTabelPanel) {
		this.testCaseTabelPanel = testCaseTabelPanel;
	}
	public JLabel getPredict() {
		return Predict;
	}
}
