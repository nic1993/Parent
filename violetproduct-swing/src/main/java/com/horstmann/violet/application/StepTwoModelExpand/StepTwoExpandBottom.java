package com.horstmann.violet.application.StepTwoModelExpand;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class StepTwoExpandBottom extends JPanel{
	private MainFrame mainFrame;
	private JButton backButton;
	private JButton nextbutton;
	private JPanel leftpanel;
	private int step = 1;
	
	private int stepThree;
	public StepTwoExpandBottom(MainFrame mainFrame){
		this.mainFrame = mainFrame;
		init();
	}
	public void init()
	{
		initButton();
		this.setBackground(new Color(233,233,233));
		GridBagLayout layout=new GridBagLayout();
		this.setLayout(layout);
		layout.setConstraints(leftpanel, new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setWeight(1, 1));
		layout.setConstraints(backButton, new GBC(1, 0, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(15, 0, 15, 10));
		layout.setConstraints(nextbutton, new GBC(2, 0, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(15, 0, 15, 10));
		this.add(backButton);
		this.add(nextbutton);
		this.add(leftpanel);
		buttonlisten();
	}
	public void initButton()
	{
		//初始化按钮
		backButton = new JButton();
		nextbutton = new JButton();
		leftpanel = new JPanel();
		backButton.setBackground(Color.red);
		
		backButton.setText("上一步");
		nextbutton.setText("下一步");
		backButton.setFont(new Font("宋体",Font.PLAIN,12));
		nextbutton.setFont(new Font("宋体",Font.PLAIN,12));
		backButton.setPreferredSize(new Dimension(88, 34));
		backButton.setMinimumSize(new Dimension(88, 34));
		backButton.setMaximumSize(new Dimension(88, 34));
		nextbutton.setPreferredSize(new Dimension(88, 34));
		nextbutton.setMinimumSize(new Dimension(88, 34));
		nextbutton.setMaximumSize(new Dimension(88, 34));
        leftpanel.setBackground(new Color(233,233,233)); 
	}
	public void buttonlisten()
	{
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//                mainFrame.getHeadTitle().getHomebutton().doClick();
//                mainFrame.getReduceOrEnlargePanel().setVisible(true);
				JPanel labelpanel=mainFrame.getStepJLabel();
				labelpanel.setLayout(new GridLayout(1, 1));
				labelpanel.add(mainFrame.getTitlePanel());
				mainFrame.getTitlePanel().getBigTitle().setText("第一步:UML模型的可靠性测试扩展");
				mainFrame.getTitlePanel().getSmallTitle().setText("对UML图形进行正确绘制、扩展");
				labelpanel.revalidate();
				
				//修改操作区域
				mainFrame.getOpreationPart().removeAll();
				mainFrame.getOpreationPart().setLayout(new GridLayout());
	
				JScrollPane jScrollPane = new JScrollPane(mainFrame.getsteponeButton());
				jScrollPane.setBorder(null);
				JScrollBar HorizontalBar = jScrollPane.getHorizontalScrollBar();
				JScrollBar VerticalBar = jScrollPane.getVerticalScrollBar();
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
				
				mainFrame.getOpreationPart().add(jScrollPane);
				mainFrame.getOpreationPart().updateUI();
				
				mainFrame.getconsolepartPanel().removeAll();
				mainFrame.getconsolepartPanel().setLayout(new GridLayout(1, 1));
				mainFrame.getconsolepartPanel().add(mainFrame.getsteponebottmopanel());
				
				int step = mainFrame.getsteponeButton().getCurrentStep();
				switch (step) {
				case 1:
					mainFrame.getCenterTabPanel().removeAll();
					if(mainFrame.getsteponebottmopanel().getLastWorkSpace() != null)
					{
						mainFrame.getCenterTabPanel().add(mainFrame.getsteponebottmopanel().getLastWorkSpace().getAWTComponent());
					}
					mainFrame.getCenterTabPanel().updateUI();
					
					mainFrame.getpanel().removeAll();
					mainFrame.getpanel().setLayout(new GridLayout(1, 1));
					mainFrame.getpanel().add(mainFrame.getStepOperationButton());
					mainFrame.getpanel().updateUI();
					break;
				case 2:
					mainFrame.getCenterTabPanel().removeAll();
					mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoModelExpandTabbedPane());
					mainFrame.getCenterTabPanel().updateUI();
					
					mainFrame.getpanel().removeAll();
					mainFrame.getpanel().setLayout(new GridLayout(1, 1));
					mainFrame.getpanel().add(mainFrame.getStepTwoModelOperation());
					mainFrame.getpanel().updateUI();
					break;
				case 3:
					mainFrame.getCenterTabPanel().removeAll();
					mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoCaseExpandTabbedPane());
					mainFrame.getCenterTabPanel().updateUI();
					
					mainFrame.getpanel().removeAll();
					mainFrame.getpanel().setLayout(new GridLayout(1, 1));
					mainFrame.getpanel().add(mainFrame.getStepTwoCaseOperation());
					mainFrame.getpanel().updateUI();
					break;
				default:
					break;
				}
				
				mainFrame.renewPanel();

				mainFrame.getReduceOrEnlargePanel().removeAll();
				mainFrame.getReduceOrEnlargePanel().setLayout(new GridLayout(1, 1));
				mainFrame.getReduceOrEnlargePanel().add(mainFrame.getstepOneCenterRightPanel());
			}
		});
		nextbutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mainFrame.getTitlePanel().getBigTitle().setText("第三步:可靠性测试数据生成");
				mainFrame.getTitlePanel().getSmallTitle().setText("对第二步生成的Mavkov模型解析生成测试用例");			
				
				mainFrame.getOpreationPart().removeAll();
				mainFrame.getOpreationPart().setLayout(new GridLayout(1, 1));
				mainFrame.getOpreationPart().add(mainFrame.getStepThreeJScrollPane());
				mainFrame.getOpreationPart().updateUI();
				
				
			    stepThree = mainFrame.getStepThreeLeftButton().getStepThree();
			    
			    if(mainFrame.getStepTwoExchangeOperation().getModel_Name() != null){
					if(mainFrame.getStepTwoExchangeOperation().isTime())
					{
						mainFrame.getStepThreeLeftButton().getTimeModelLabel().setEnabled(true);
						mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(false);
						
						if(stepThree == 2 || stepThree == 3 ||stepThree == 4 ||stepThree == 5){
							stepThree = 1;
						}
					}
					else if(!mainFrame.getStepTwoExchangeOperation().isTime()){
						mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(true);
						mainFrame.getStepThreeLeftButton().getTimeModelLabel().setEnabled(false);
						
						if(stepThree == 6 || stepThree == 7 ||stepThree == 8 ||stepThree == 9){
							stepThree = 1;
						}
					}
				}
				else {
					mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(false);
					mainFrame.getStepThreeLeftButton().getTimeModelLabel().setEnabled(false);
				}
				JPanel mainPanel = mainFrame.getMainPanel();
				
				switch (stepThree) {
				case 1:
					for(int i = 0;i < mainPanel.getComponentCount();i++)
					{
						if(mainPanel.getComponent(i).equals(mainFrame.getworkpanel()))
						{
							mainPanel.remove(mainPanel.getComponent(i));
							mainPanel.add(mainFrame.getworkpanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98).setInsets(10, 15, 20, 15));
						}
					}
					
					mainFrame.getCenterTabPanel().removeAll();
					mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeChoosePattern());
					
					
					mainFrame.getpanel().removeAll();
					mainFrame.getpanel().setVisible(false);
					
					mainFrame.getinformationPanel().setVisible(false);
					
					mainFrame.getReduceOrEnlargePanel().setVisible(false);
            		
					break;
                case 2:
                	mainFrame.getpanel().removeAll();
            		mainFrame.getCenterTabPanel().removeAll();
                	
                	for(int i = 0;i < mainPanel.getComponentCount();i++)
    				{
    					if(mainPanel.getComponent(i).equals(mainFrame.getworkpanel()))
    					{
    						mainPanel.remove(mainPanel.getComponent(i));
    						mainPanel.add(mainFrame.getworkpanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98).setInsets(10, 15, 20, 15));
    					}
    				}
    				mainFrame.getReduceOrEnlargePanel().setVisible(true);
    				
    				mainFrame.getpanel().add(mainFrame.getNoTimeSeqOperation());
    				mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeNoTimeSeqTabbedPane());
    				
    				mainFrame.renewPanel();
    				
    				
					break;
                case 3:
                	mainFrame.getpanel().removeAll();
            		mainFrame.getCenterTabPanel().removeAll();
                	
                	for(int i = 0;i < mainPanel.getComponentCount();i++)
    				{
    					if(mainPanel.getComponent(i).equals(mainFrame.getworkpanel()))
    					{
    						mainPanel.remove(mainPanel.getComponent(i));
    						mainPanel.add(mainFrame.getworkpanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98).setInsets(10, 15, 20, 0));
    					}
    				}
    				mainFrame.getReduceOrEnlargePanel().setVisible(true);
    				
                    mainFrame.getpanel().add(mainFrame.getNoTimeSeqOperation1());
					
					mainFrame.getpanel().updateUI();	
					mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeNoTimeSeqTabbedPane());
					mainFrame.getCenterTabPanel().updateUI();
					mainFrame.renewPanel();
					break;
                case 4:
                	mainFrame.getpanel().removeAll();
            		mainFrame.getCenterTabPanel().removeAll();
                	
            		mainFrame.getReduceOrEnlargePanel().setVisible(true);
                	for(int i = 0;i < mainPanel.getComponentCount();i++)
    				{
    					if(mainPanel.getComponent(i).equals(mainFrame.getworkpanel()))
    					{
    						mainPanel.remove(mainPanel.getComponent(i));
    						mainPanel.add(mainFrame.getworkpanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98).setInsets(10, 15, 20, 0));
    					}
    				}
                	mainFrame.getpanel().add(mainFrame.getNoTimeCaseOperation());
                	mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeNoTimeTabbedPane());
					mainFrame.renewPanel();
	                break;
                case 5:
                	mainFrame.getpanel().removeAll();
            		mainFrame.getCenterTabPanel().removeAll();
                	
            		mainFrame.getReduceOrEnlargePanel().setVisible(true);
                	for(int i = 0;i < mainPanel.getComponentCount();i++)
    				{
    					if(mainPanel.getComponent(i).equals(mainFrame.getworkpanel()))
    					{
    						mainPanel.remove(mainPanel.getComponent(i));
    						mainPanel.add(mainFrame.getworkpanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98).setInsets(10, 15, 20, 0));
    					}
    				}
                	mainFrame.getpanel().add(mainFrame.getNoTimeCaseOperation1());
					mainFrame.getpanel().updateUI();	
					mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeNoTimeTabbedPane());
					mainFrame.getCenterTabPanel().updateUI();
    				mainFrame.renewPanel();
	                break;
                case 6:
                	mainFrame.getpanel().removeAll();
            		mainFrame.getCenterTabPanel().removeAll();
                	
                	for(int i = 0;i < mainPanel.getComponentCount();i++)
    				{
    					if(mainPanel.getComponent(i).equals(mainFrame.getworkpanel()))
    					{
    						mainPanel.remove(mainPanel.getComponent(i));
    						mainPanel.add(mainFrame.getworkpanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98).setInsets(10, 15, 20, 0));
    					}
    				}
    				mainFrame.getReduceOrEnlargePanel().setVisible(true);
    				mainFrame.getpanel().setVisible(true);
					mainFrame.getpanel().add(mainFrame.getTimeExpandOperation());
					mainFrame.getCenterTabPanel().add(mainFrame.getTimeExpandTabbedPane());
					mainFrame.renewPanel();
	                break;
                case 7:
                	mainFrame.getpanel().removeAll();
            		mainFrame.getCenterTabPanel().removeAll();
                	
                	for(int i = 0;i < mainPanel.getComponentCount();i++)
    				{
    					if(mainPanel.getComponent(i).equals(mainFrame.getworkpanel()))
    					{
    						mainPanel.remove(mainPanel.getComponent(i));
    						mainPanel.add(mainFrame.getworkpanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98).setInsets(10, 15, 20, 0));
    					}
    				}
    				mainFrame.getReduceOrEnlargePanel().setVisible(true);
    				mainFrame.getpanel().setVisible(true);
					mainFrame.getpanel().add(mainFrame.getTimeSeqOperation());
					mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeTimeSeqTabbedPane());
					mainFrame.renewPanel();
					break;
                case 8:
                	mainFrame.getpanel().removeAll();
            		mainFrame.getCenterTabPanel().removeAll();
                	
                	for(int i = 0;i < mainPanel.getComponentCount();i++)
    				{
    					if(mainPanel.getComponent(i).equals(mainFrame.getworkpanel()))
    					{
    						mainPanel.remove(mainPanel.getComponent(i));
    						mainPanel.add(mainFrame.getworkpanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98).setInsets(10, 15, 20, 0));
    					}
    				}
    				mainFrame.getReduceOrEnlargePanel().setVisible(true);
    				mainFrame.getpanel().setVisible(true);
					mainFrame.getpanel().add(mainFrame.getTimeSeqOperation1());
					mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeTimeSeqTabbedPane());
					mainFrame.renewPanel();
					break;
                case 9:
                	mainFrame.getpanel().removeAll();
            		mainFrame.getCenterTabPanel().removeAll();
                	
                	for(int i = 0;i < mainPanel.getComponentCount();i++)
    				{
    					if(mainPanel.getComponent(i).equals(mainFrame.getworkpanel()))
    					{
    						mainPanel.remove(mainPanel.getComponent(i));
    						mainPanel.add(mainFrame.getworkpanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98).setInsets(10, 15, 20, 0));
    					}
    				}
    				mainFrame.getReduceOrEnlargePanel().setVisible(true);
    				mainFrame.getpanel().setVisible(true);
					mainFrame.getpanel().add(mainFrame.getTimeCaseOperation());
					mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeTimeTabbedPane());
					mainFrame.renewPanel();
					break;
					
                case 10:
                	mainFrame.getpanel().removeAll();
            		mainFrame.getCenterTabPanel().removeAll();
                	
                	for(int i = 0;i < mainPanel.getComponentCount();i++)
    				{
    					if(mainPanel.getComponent(i).equals(mainFrame.getworkpanel()))
    					{
    						mainPanel.remove(mainPanel.getComponent(i));
    						mainPanel.add(mainFrame.getworkpanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98).setInsets(10, 15, 20, 0));
    					}
    				}
    				mainFrame.getReduceOrEnlargePanel().setVisible(true);
    				mainFrame.getpanel().setVisible(true);
					mainFrame.getpanel().add(mainFrame.getTimeCaseOperation1());
					mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeTimeSeqTabbedPane());
					mainFrame.renewPanel();
					break;
				default:
					break;
				}

				mainFrame.getconsolepartPanel().removeAll();
				mainFrame.getconsolepartPanel().setLayout(new GridLayout(1, 1));
				mainFrame.getconsolepartPanel().add(mainFrame.getStepThreeBottom());
			}
		});
	}
	public JButton getNextbutton() {
		return nextbutton;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	
}
