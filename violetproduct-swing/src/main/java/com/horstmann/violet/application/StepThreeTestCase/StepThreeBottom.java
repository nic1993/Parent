package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class StepThreeBottom extends JPanel{
	private MainFrame mainFrame;
	private JButton backButton;
	private JButton nextbutton;
	private JPanel leftpanel;
	
	private int stepTwo; //第二步执行的当前步骤
	private int step;
	public StepThreeBottom(MainFrame mainFrame){
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
		backButton.setText("上一步");
		nextbutton.setText("下一步");
		backButton.setFont(new Font("方正",Font.PLAIN,12));
		nextbutton.setFont(new Font("方正",Font.PLAIN,12));
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
				if(((JButton)e.getSource()).isEnabled())
				{
					
//					mainFrame.getsteponebottmopanel().getNextbutton().doClick();
					JPanel mainPanel = mainFrame.getMainPanel();
					for(int i = 0;i < mainPanel.getComponentCount();i++)
					{
						if(mainPanel.getComponent(i).equals(mainFrame.getworkpanel()))
						{
							mainPanel.remove(mainPanel.getComponent(i));
							mainPanel.add(mainFrame.getworkpanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98).setInsets(10, 15, 20, 0));
						}
					}
					
					mainFrame.getStepTwoPanel().setVisible(true);
					mainFrame.getStepTwoPanel().setBackground(new Color(200,200,200));
					mainFrame.getStepTwoPanel().setLayout(new GridLayout(1, 1));
					
					mainFrame.getconsolepartPanel().removeAll();
					mainFrame.getconsolepartPanel().setLayout(new GridLayout(1, 1));
					
					mainFrame.getReduceOrEnlargePanel().removeAll();
					mainFrame.getReduceOrEnlargePanel().setLayout(new GridLayout(1, 1));
					
					mainFrame.getTitlePanel().getBigTitle().setText("第二步:Markov链使用模型构建");
					mainFrame.getTitlePanel().getSmallTitle().setText("对第一步扩展模型进行评估、转换成Markov链模型");				
					
					mainFrame.getOpreationPart().removeAll();
					mainFrame.getOpreationPart().setLayout(new GridLayout(1, 1));
					mainFrame.getOpreationPart().add(mainFrame.getStepTwoExpand());
					stepTwo = mainFrame.getStepTwoExpandBottom().getStep();
					
					if(stepTwo == 2 && !mainFrame.getStepTwoExpand().getExchangeLabel().isEnabled())
					{
						stepTwo = 1;
						mainFrame.getStepTwoExpand().getEstimateLabel().setFont(new Font("微软雅黑", Font.BOLD, 18));
						mainFrame.getStepTwoExpand().getExchangepPanel().setVisible(false);
						mainFrame.renewPanel();
					}
					
					switch (stepTwo) {
	                case 1:
	                	mainFrame.getStepTwoExpand().getEstimateLabel().setFont(new Font("微软雅黑", Font.BOLD, 18));
	                	mainFrame.getStepTwoExpand().getExchangeLabel().setFont(new Font("微软雅黑", Font.PLAIN, 18));
	                	mainFrame.getpanel().removeAll();
	    				mainFrame.getpanel().setLayout(new GridLayout(1, 1));
	                	mainFrame.getpanel().add(mainFrame.getStepTwoEvaluateOperation());
						mainFrame.getpanel().setVisible(true);
						mainFrame.getpanel().updateUI();
						mainFrame.getCenterTabPanel().removeAll();
						mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoEvaluateTabbedPane());
						
						mainFrame.getStepTwoCenterRightPanel().getGraphButton().setVisible(false);
						mainFrame.getStepTwoCenterRightPanel().getZoominButton().setVisible(false);
						mainFrame.getStepTwoCenterRightPanel().getZoomoutButton().setVisible(false);
		                break;
	                case 2:
	                	mainFrame.getStepTwoExpand().getExchangeLabel().setFont(new Font("微软雅黑", Font.BOLD, 18));
	                	mainFrame.getpanel().removeAll();
	    				mainFrame.getpanel().setLayout(new GridLayout(1, 1));
	                	mainFrame.getpanel().add(mainFrame.getStepTwoExchangeOperation());
						mainFrame.getpanel().setVisible(true);
						mainFrame.getpanel().updateUI();
						mainFrame.getCenterTabPanel().removeAll();
						mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoExchangeTabbedPane());
						
						mainFrame.getStepTwoCenterRightPanel().getGraphButton().setVisible(true);
						mainFrame.getStepTwoCenterRightPanel().getZoominButton().setVisible(true);
						mainFrame.getStepTwoCenterRightPanel().getZoomoutButton().setVisible(true);
		                break;
					default:
						break;
					}
					
					mainFrame.getconsolepartPanel().add(mainFrame.getStepTwoExpandBottom());
					mainFrame.getconsolepartPanel().updateUI();
					
					mainFrame.getReduceOrEnlargePanel().add(mainFrame.getStepTwoCenterRightPanel());
					mainFrame.getReduceOrEnlargePanel().updateUI();
					mainFrame.getReduceOrEnlargePanel().setVisible(true);
					
					mainFrame.renewPanel();
				}
				
			}
			
		
		});
		nextbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(((JButton)e.getSource()).isEnabled())
				{
					clearPanel();
					JPanel mainPanel = mainFrame.getMainPanel();
					for(int i = 0;i < mainPanel.getComponentCount();i++)
					{
						if(mainPanel.getComponent(i).equals(mainFrame.getworkpanel()))
						{
							mainPanel.remove(mainPanel.getComponent(i));
							mainPanel.add(mainFrame.getworkpanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98).setInsets(10, 15, 20, 0));
						}
					}
//					mainFrame.getOutputinformation().setVisible(true);
					mainFrame.getReduceOrEnlargePanel().setVisible(true);
					
					mainFrame.getTitlePanel().getBigTitle().setText("第四步:可靠性测试数据执行");
					mainFrame.getTitlePanel().getSmallTitle().setText("对第三步生成的测试用例进行验证");	
					
					JScrollPane jScrollPane = new JScrollPane(mainFrame.getStepFourTestCase());
					jScrollPane.setBorder(null);
					mainFrame.getOpreationPart().add(jScrollPane);
					mainFrame.getOpreationPart().updateUI();
					
					mainFrame.getNameRadionPanel().initFile();
					mainFrame.getpanel().add(mainFrame.getStepFourOperation());
					mainFrame.getpanel().updateUI();
					
					mainFrame.getCenterTabPanel().add(mainFrame.getStepFourTabbedPane());
					mainFrame.getCenterTabPanel().updateUI();
					
					mainFrame.getconsolepartPanel().add(mainFrame.getStepFourBottom());
					mainFrame.getconsolepartPanel().updateUI();
					
					mainFrame.getStepTwoCenterRightPanel().getGraphButton().setVisible(false);
					mainFrame.getStepTwoCenterRightPanel().getZoominButton().setVisible(false);
					mainFrame.getStepTwoCenterRightPanel().getZoomoutButton().setVisible(false);
					
					mainFrame.renewPanel();
				}
			}
		});
	}
	private void clearPanel()
	{
		mainFrame.getOpreationPart().removeAll();
		mainFrame.getOpreationPart().setLayout(new GridLayout(1, 1));
		mainFrame.getOpreationPart().setVisible(true);
		
		mainFrame.getconsolepartPanel().removeAll();
		mainFrame.getconsolepartPanel().setLayout(new GridLayout(1, 1));
		
		mainFrame.getpanel().removeAll();
		mainFrame.getpanel().setLayout(new GridLayout(1, 1));
		mainFrame.getpanel().setVisible(true);
		
		mainFrame.getCenterTabPanel().removeAll();
	}
	
	public void UnEnable(){
		backButton.setEnabled(false);
		nextbutton.setEnabled(false);
	}
	
	public void Enable(){
		backButton.setEnabled(true);
		nextbutton.setEnabled(true);
	}
	
	public JButton getBackButton() {
		return backButton;
	}
	public JButton getNextbutton() {
		return nextbutton;
	}
	
	
}
