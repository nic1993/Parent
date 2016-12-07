package com.horstmann.violet.application.Stepone;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.PanelUI;

import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.framework.theme.ThemeManager;
import com.l2fprod.common.swing.JTaskPane;
import com.l2fprod.common.swing.JTaskPaneGroup;

public class ChangePanelUI extends PanelUI{
       
       public ChangePanelUI(StepOneButton panel,MainFrame mainFrame)
       {
    	   this.panel = panel;
    	   this.mainFrame = mainFrame;   
       }
       
       @Override
        public void installUI(JComponent c) {
    	// TODO Auto-generated method stub
    	c.removeAll();
    	c.setLayout(new BorderLayout());
//    	c.setLayout(new GridBagLayout());
    	this.taskPane = new JTaskPane();
    	this.taskPane.setLayout(new GridBagLayout());
    	this.taskPane.setBorder(new EmptyBorder(0,0,0,0));
    	addElementToTaskPane(this.panel.getSequenceJtree(), "顺序图                                 ", 1);
    	addElementToTaskPane(this.panel.getUseCaseJtree(), "用例图                                   ", 2);
    	c.add(taskPane,BorderLayout.NORTH);
    	c.setBorder(new MatteBorder(0, 1, 0, 1, ThemeManager.getInstance().getTheme().getSidebarBorderColor()));
    	this.panel.doLayout();
//    	initlisten();
       }
       
       private void addElementToTaskPane(final Component c,String title, int order)
       {
    	   JTaskPaneGroup group = new JTaskPaneGroup();
           Font font = group.getFont().deriveFont(Font.PLAIN);
           group.setFont(font);
           group.setTitle(title);
           group.setFont(new Font("微软黑体", Font.PLAIN, 16));
           group.setLayout(new BorderLayout());
           group.add(c, BorderLayout.CENTER);
           GridBagConstraints gbc = new GridBagConstraints();
           gbc.anchor = GridBagConstraints.FIRST_LINE_START;
           gbc.fill = GridBagConstraints.BOTH;
           gbc.gridx = 0;
           gbc.gridy = order;
//           group.setExpanded(false);
           this.taskPane.add(group,gbc);
       }
       
//       public void initlisten()
//       {
//    	   this.taskPane.getComponent(0).addMouseListener(new MouseListener() {
//			
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				// TODO Auto-generated method stub
//				mainFrame.getOpreationPart().validate();
//				mainFrame.getOpreationPart().revalidate();
//			}
//			
//			@Override
//			public void mousePressed(MouseEvent e) {
//				// TODO Auto-generated method stub
//				mainFrame.getOpreationPart().validate();
//				wakeupPanel();
//				mainFrame.getCenterTabPanel().removeAll();
//				mainFrame.getCenterTabPanel().add(mainFrame.getStepOneCenterSequenceTabbedPane());
//				mainFrame.getCenterTabPanel().updateUI();
//				sequenceLabel.setFont(new Font("黑体", Font.PLAIN, 16));
//				mainFrame.getpanel().removeAll();
//				mainFrame.getpanel().setLayout(new GridLayout(1, 1));
//				mainFrame.getpanel().add(mainFrame.getOperationButton());
//				mainFrame.getOperationButton().getOtherPanel().setLayout(new GridBagLayout());
//				mainFrame.getOperationButton().getOtherPanel().add(sequenceLabel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10,10,10,10));
//				mainFrame.getpanel().updateUI();
//				mainFrame.getinformationPanel().removeAll();
//				mainFrame.getinformationPanel().setLayout(new GridLayout(1, 1));
//				mainFrame.getinformationPanel().add(mainFrame.getouOutputinformation());
//				mainFrame.getReduceOrEnlargePanel().setLayout(new GridLayout(1, 1));
//				mainFrame.getReduceOrEnlargePanel().add(mainFrame.getstepOneCenterRightPanel());
//				mainFrame.getOpreationPart().revalidate();
//
//			}
//			
//			@Override
//			public void mouseExited(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//				
////				if(bottompanel.getComponentCount() == 0){
////				bottompanel.setLayout(new GridLayout(1, 1));
////				bottompanel.add(mainFrame.getProjectTree());
////				bottompanel.updateUI();
////				}
////				if (bottompanel.getComponentCount() != 0) {
////					if (!bottompanel.getComponent(0).equals(mainFrame.getProjectTree())) {
////						bottompanel.removeAll();
////						bottompanel.setLayout(new GridLayout(1, 1));
////						bottompanel.add(mainFrame.getProjectTree());
////						bottompanel.updateUI();
////					}
////				}
//				mainFrame.getOpreationPart().validate();
//				mainFrame.getOpreationPart().revalidate();
//				
//			}
//			
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				// TODO Auto-generated method stub
//				mainFrame.getOpreationPart().validate();
//				mainFrame.getOpreationPart().revalidate();
//			}
//			
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				// TODO Auto-generated method stub
//				mainFrame.getOpreationPart().validate();
//				mainFrame.getOpreationPart().revalidate();
//			}
//		});
//    	   
//    	   this.taskPane.getComponent(1).addMouseListener(new MouseListener() {
//			
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				// TODO Auto-generated method stub
//				mainFrame.getOpreationPart().validate();
//				mainFrame.getOpreationPart().revalidate();
//			}
//			
//			@Override
//			public void mousePressed(MouseEvent e) {
//				// TODO Auto-generated method stub
//				wakeupPanel();
//				mainFrame.getCenterTabPanel().removeAll();
//				mainFrame.getCenterTabPanel().add(mainFrame.getStepOneCenterUseCaseTabbedPane());
//				mainFrame.getCenterTabPanel().updateUI();
//				
//				mainFrame.getpanel().removeAll();
//				mainFrame.getpanel().setLayout(new GridLayout(1, 1));
//				mainFrame.getpanel().add(mainFrame.getOperationButton());
//				mainFrame.getpanel().updateUI();
//				
//				mainFrame.getinformationPanel().removeAll();
//				mainFrame.getinformationPanel().setLayout(new GridLayout(1, 1));
//				mainFrame.getinformationPanel().add(mainFrame.getouOutputinformation());
//				mainFrame.getOpreationPart().validate();
//				mainFrame.getOpreationPart().revalidate();
//			}
//			
//			@Override
//			public void mouseExited(MouseEvent e) {
//				// TODO Auto-generated method stub
//				mainFrame.getOpreationPart().validate();
//				mainFrame.getOpreationPart().revalidate();
//			}
//			
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				// TODO Auto-generated method stub
//				mainFrame.getOpreationPart().validate();
//				mainFrame.getOpreationPart().revalidate();
//			}
//			
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				// TODO Auto-generated method stub
//				mainFrame.getOpreationPart().validate();
//				mainFrame.getOpreationPart().revalidate();
//			}
//		});
//       }
//       private void wakeupPanel()
//   	{
//   		mainFrame.getpanel().setVisible(true);
//   		mainFrame.getinformationPanel().setVisible(true);
//   		mainFrame.getbotoomJSplitPane().setDividerLocation(600);
//   		mainFrame.getbotoomJSplitPane().setDividerSize(4);
//   		mainFrame.getReduceOrEnlargePanel().setVisible(true);
////   		if(mainFrame.getsteponeButton().getbottompanel().getComponentCount() != 0)
////   			mainFrame.getsteponeButton().getbottompanel().getComponent(0).setVisible(true);
//   	}
       private StepOneButton panel;
        
       private JTaskPane taskPane;
       
       private MainFrame mainFrame;
       
       private JLabel sequenceLabel = new JLabel("顺序图是将交互关系表示为一个二维图。纵向是时间轴，时间沿竖线向下延伸。横向轴代表了在协作中各独立对象的类元角色。");
       
       private JLabel uescaseLabel = new JLabel();;
}
