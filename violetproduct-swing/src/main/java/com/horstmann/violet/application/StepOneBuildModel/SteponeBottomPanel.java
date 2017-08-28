package com.horstmann.violet.application.StepOneBuildModel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.WorkspacePanel;

public class SteponeBottomPanel extends JPanel{
	private MainFrame mainFrame;
	private JButton backButton;
	private JButton nextbutton;
	private JPanel leftpanel;
	private JLabel stepTwoLabel;
	private IWorkspace lastWorkSpace;
	private int stepTwo;
	
	private String StepTwoModelName;
	public SteponeBottomPanel(MainFrame mainFrame){
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
				updatePanelUI();
				mainFrame.getCenterTabPanel().removeAll();
				mainFrame.getCenterTabPanel().add(mainFrame.getHeadTitle());
				mainFrame.getbotoomJSplitPane().setDividerSize(0);
				mainFrame.renewPanel();
				mainFrame.getpanel().updateUI();
				
				JPanel mainPanel = mainFrame.getMainPanel();
				for(int i = 0;i < mainPanel.getComponentCount();i++)
				{
					if(mainPanel.getComponent(i).equals(mainFrame.getworkpanel()))
					{
						mainPanel.remove(mainPanel.getComponent(i));
						mainPanel.add(mainFrame.getworkpanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98));
					}
				}
			}
		});
		nextbutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				initStepTwoPanel();
				mainFrame.getTitlePanel().getBigTitle().setText("第二步:Markov链使用模型构建");
				mainFrame.getTitlePanel().getSmallTitle().setText("对第一步扩展模型进行评估、转换成Markov链模型");			
				
				mainFrame.getjRadionPanel().initFile();
				mainFrame.getOpreationPart().add(mainFrame.getStepTwoExpand());
				mainFrame.getOpreationPart().updateUI();
				
				mainFrame.getconsolepartPanel().add(mainFrame.getStepTwoExpandBottom());
				mainFrame.getconsolepartPanel().updateUI();
				
				mainFrame.getReduceOrEnlargePanel().add(mainFrame.getStepTwoCenterRightPanel());
				mainFrame.getReduceOrEnlargePanel().updateUI();
				mainFrame.getReduceOrEnlargePanel().setVisible(true);
				
				stepTwo = mainFrame.getStepTwoExpandBottom().getStep();
				
				switch (stepTwo) {
//				case 1:
//					mainFrame.getpanel().add(mainFrame.getStepTwoModelOperation());
//					if(mainFrame.getjRadionPanel().getRadioList().size() == 0)
//					{
//						mainFrame.getStepTwoModelOperation().getLabel().setText("当前无可以选择的模型!");
//					}
//					else {
//						mainFrame.getStepTwoModelOperation().getLabel().setText("请在左侧选择用例模型并填写用户数进行扩展！");
//					}
//					mainFrame.getpanel().setVisible(true);
//					mainFrame.getpanel().updateUI();
//					mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoModelExpandTabbedPane());
//					
//					mainFrame.getStepTwoCenterRightPanel().getGraphButton().setVisible(false);
//					mainFrame.getStepTwoCenterRightPanel().getZoominButton().setVisible(false);
//					mainFrame.getStepTwoCenterRightPanel().getZoomoutButton().setVisible(false);
//					break;
//                case 2:
//                	mainFrame.getpanel().add(mainFrame.getStepTwoCaseOperation());
//					mainFrame.getpanel().setVisible(true);
//					mainFrame.getpanel().updateUI();
//					mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoCaseExpandTabbedPane());
//					
//					mainFrame.getStepTwoCenterRightPanel().getGraphButton().setVisible(false);
//					mainFrame.getStepTwoCenterRightPanel().getZoominButton().setVisible(false);
//					mainFrame.getStepTwoCenterRightPanel().getZoomoutButton().setVisible(false);
//					break;
                case 1:
                	mainFrame.getpanel().add(mainFrame.getStepTwoEvaluateOperation());
					mainFrame.getpanel().setVisible(true);
					mainFrame.getpanel().updateUI();
					
					mainFrame.getCenterTabPanel().removeAll();
					mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoEvaluateTabbedPane());
					
					if(!mainFrame.getStepTwoCaseOperation().isFinish())
					{
						mainFrame.getStepTwoEvaluateOperation().getTopLabel().removeAll();
						mainFrame.getStepTwoEvaluateOperation().getTopLabel().setText("当前无可以评估的模型!");
						mainFrame.getStepTwoEvaluateOperation().getEvaluateButton().setEnabled(false);
						mainFrame.getStepTwoExpand().getEstimateLabel().setEnabled(false);
					}
					else {
						mainFrame.getStepTwoEvaluateOperation().getEvaluateButton().setEnabled(true);
						mainFrame.getStepTwoExpand().getEstimateLabel().setEnabled(true);
					}
					
					mainFrame.getStepTwoCenterRightPanel().getGraphButton().setVisible(false);
					mainFrame.getStepTwoCenterRightPanel().getZoominButton().setVisible(false);
					mainFrame.getStepTwoCenterRightPanel().getZoomoutButton().setVisible(false);
	                break;
                case 2:
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
				StepTwoModelName = mainFrame.getStepTwoModelOperation().getModel_name();
				if(StepTwoModelName != null)
				{
					List<Radio> lists = mainFrame.getjRadionPanel().getRadioList();
					for(Radio radio : lists)
					{
						if(radio.getText().equals(StepTwoModelName))
						{
							radio.setSelected(true);
						}
					}
				}
				mainFrame.renewPanel();
//				mainFrame.getpanel().add(mainFrame.getStepTwoModelOperation());
//				if(mainFrame.getjRadionPanel().getRadioList().size() == 0)
//				{
//					mainFrame.getStepTwoModelOperation().getLabel().setText("当前无可以选择的模型!");
//				}
//				else {
//					mainFrame.getStepTwoModelOperation().getLabel().setText("请在左侧选择用例模型并填写用户数进行扩展！");
//				}
//				mainFrame.getpanel().setVisible(true);
//				mainFrame.getpanel().updateUI();
//				
//				mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoModelExpandTabbedPane());
               
			}
		});
	}
	private void updatePanelUI()
	{
		mainFrame.getStepJLabel().setVisible(false);
		mainFrame.getconsolepartPanel().setVisible(false);
		mainFrame.getinformationPanel().setVisible(false);
        mainFrame.getpanel().setVisible(false);
		mainFrame.getOpreationPart().setVisible(false);
		mainFrame.getReduceOrEnlargePanel().setVisible(false);
	}
	private void initStepTwoPanel()
	{
		mainFrame.getStepTwoPanel().setVisible(true);
		mainFrame.getStepTwoPanel().setBackground(new Color(200,200,200));
		mainFrame.getStepTwoPanel().setLayout(new GridLayout(1, 1));
		
		mainFrame.getOpreationPart().removeAll();
		mainFrame.getOpreationPart().setLayout(new GridLayout(1, 1));
		
		mainFrame.getconsolepartPanel().removeAll();
		mainFrame.getconsolepartPanel().setLayout(new GridLayout(1, 1));
		
		mainFrame.getReduceOrEnlargePanel().removeAll();
		mainFrame.getReduceOrEnlargePanel().setLayout(new GridLayout(1, 1));
		
		mainFrame.getpanel().removeAll();
		mainFrame.getpanel().setLayout(new GridLayout(1, 1));
		
	}
	public JButton getNextbutton() {
		return nextbutton;
	}
	public IWorkspace getLastWorkSpace() {
		return lastWorkSpace;
	}	
}
