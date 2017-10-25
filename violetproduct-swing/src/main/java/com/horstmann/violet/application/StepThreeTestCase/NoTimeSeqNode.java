package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.horstmann.violet.application.gui.MainFrame;

public class NoTimeSeqNode extends JLabel{
       private Icon icon;
       private String title;
       private AbstractPagePanel abstractPagePanel;
       private MainFrame mainFrame;
       public NoTimeSeqNode(String name,MainFrame mainFrame)
       {
    	   init();
    	   this.mainFrame = mainFrame;
    	   this.title = name;
    	   this.setIcon(icon);
    	   this.setText(name);
    	   this.setFont(new Font("ËÎÌו", Font.PLAIN, 16));
       }
       public void init()
       {
    	   icon = new ImageIcon("resources/icons/22x22/nodeLabel.png");
    	   listen();
       }

    private void listen()
    {
    	this.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mousePressed(MouseEvent e) {
    			// TODO Auto-generated method stub
    			
    			mainFrame.getCenterTabPanel().removeAll();
				mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeNoTimeSeqTabbedPane());
    			
                mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().removeAll();
                mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().setLayout(new GridLayout());
                mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().add(abstractPagePanel);
                mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().repaint();
                
                for(NoTimeSeqNode noTimeSeqNode : mainFrame.getStepThreeLeftButton().getNoTimeSeqNodePanel().getTestCaseNodeLabels())
                {
                	noTimeSeqNode.setFont(new Font("ËÎÌו", Font.PLAIN, 16));
                }
                ((NoTimeSeqNode)e.getSource()).setFont(new Font("ËÎÌו", Font.BOLD, 16));
                mainFrame.renewPanel();
    		}
    	
		});
    }

	
	
	public AbstractPagePanel getAbstractPagePanel() {
		return abstractPagePanel;
	}
	public void setAbstractPagePanel(AbstractPagePanel abstractPagePanel) {
		this.abstractPagePanel = abstractPagePanel;
	}
	public String getTitle() {
		return title;
	}
    
}
