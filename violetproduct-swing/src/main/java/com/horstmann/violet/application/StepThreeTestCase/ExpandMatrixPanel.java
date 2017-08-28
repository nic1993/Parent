package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.BasicStroke;
import java.awt.Color;
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

import com.horstmann.violet.application.gui.GBC;

public class ExpandMatrixPanel extends JPanel{
	   private JPanel titlePanel;
	   private JLabel titleLabel;
	   private JPanel gapPanel;
	   private JLabel reduceTabel; 
	   private JPanel tabelPanel;
	   public ExpandMatrixPanel()
	   {
		   initComponent();
		  this.setLayout(new GridBagLayout());
		  this.add(titlePanel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
		  this.add(tabelPanel, new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10,0,0,0));
	   }
	   private void initComponent()
	   {
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
		   titleLabel = new JLabel();
		   titleLabel.setFont(new Font("ËÎÌו", Font.PLAIN, 18));
		   gapPanel = new JPanel();
		   reduceTabel = new JLabel();
		   reduceTabel.setFont(new Font("ËÎÌו", Font.PLAIN, 18));
		   tabelPanel = new JPanel();
		   tabelPanel.setLayout(new GridLayout(1, 1));
		   reduceTabel.setIcon(new ImageIcon("resources/icons/16x16/smallDown.png"));
		   reduceTabel.addMouseListener(new MouseAdapter() {
			   @Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				   if(tabelPanel.isVisible())
				   {
					   tabelPanel.setVisible(false);
				   }
				   else {
					   tabelPanel.setVisible(true);
				}
  
			}
		});   
		   titlePanel.setLayout(new GridBagLayout());
		   titlePanel.add(titleLabel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(0, 0).setInsets(5));
		   titlePanel.add(gapPanel,new GBC(1, 0).setFill(GBC.BOTH).setWeight(1, 0));
		   titlePanel.add(reduceTabel,new GBC(2, 0).setFill(GBC.BOTH).setWeight(0, 0).setInsets(5));
	   }
	public JLabel getTitleLabel() {
		return titleLabel;
	}
	public JPanel getTabelPanel() {
		return tabelPanel;
	}
       
}
