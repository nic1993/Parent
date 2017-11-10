package com.horstmann.violet.application.StepOneBuildModel;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicButtonUI;

import com.horstmann.violet.application.consolepart.ConsoleMessageTabbedPane;
import com.horstmann.violet.application.consolepart.ConsolePart;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class HeadTitle extends JPanel{
	private MainFrame mainFrame;
	private JButton homebutton;
	private JPanel rightPanel;
	private JPanel leftpicturePanel;
	private JPanel rightpicturePanel;
	private JPanel buttonPanel;
	private JLabel firstLabel;
	private JLabel secondLabel;
	private JLabel thirdLabel;
	private JLabel fourthLabel;
	
	public HeadTitle(MainFrame mainFrame){
		this.mainFrame = mainFrame;
		init();
	}
	
	public void init() {
		initButton();
	    GridBagLayout layout = new GridBagLayout();
	    this.setLayout(layout);
	    this.add(leftpicturePanel);
	    this.add(rightPanel);
	    layout.setConstraints(leftpicturePanel, new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setWeight(0.4, 1));
	    layout.setConstraints(rightPanel, new GBC(1, 0, 1, 1).setFill(GBC.BOTH).setWeight(0.6, 1));
		SetButtonListener();
		
	}
	public void initButton() {	
		try {
		firstLabel = new JLabel();
		secondLabel = new JLabel();
		thirdLabel = new JLabel();
		fourthLabel = new JLabel();
		homebutton =new JButton();
		rightPanel = new JPanel();
//		Icon openIcon = new ImageIcon("resources/icons/72x72/start.png");
		File openFile = new File("resources/icons/72x72/start.png");
		Icon openIcon;
		
			openIcon = new ImageIcon(ImageIO.read(openFile));
		
		homebutton.setIcon(openIcon);
		
		homebutton.setHorizontalTextPosition(SwingConstants.CENTER);
		homebutton.setVerticalTextPosition(SwingConstants.BOTTOM);
		homebutton.setFocusPainted(false);
		homebutton.setMargin(new Insets(0, 0, 0, 0));
		homebutton.setContentAreaFilled(false);
		homebutton.setBorderPainted(false);
		homebutton.setFocusable(true);
		rightpicturePanel = new ImagePanel("resources\\flowchart.PNG",0,0);
		leftpicturePanel = new JPanel(){
		public void paint(Graphics g){
            super.paint(g);
            java.awt.Rectangle rect = this.getBounds();
            int width = (int) rect.getWidth() - 1;
            int height = (int) rect.getHeight() - 1;
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(1f));
            g2.setColor(Color.white);
            g2.drawLine(30,235,width - 30,235);
          }
		};
		leftpicturePanel.setBackground(new Color(218,218,218));
		rightpicturePanel.setBackground(Color.white);
		rightPanel.setBackground(Color.white);
		rightPanel.setLayout(new GridBagLayout());
		rightPanel.add(rightpicturePanel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 1).setInsets(10));
		rightPanel.add(homebutton,new GBC(0, 1).setFill(GBC.VERTICAL).setInsets(10, 0, 20, 0));
		
		firstLabel.setText("第一步:UML模型的可靠性测试扩展");
		secondLabel.setText("第二步:Markov链使用模型构建");
		thirdLabel.setText("第三步:可靠性测试数据生成");
		fourthLabel.setText("第四步:可靠性测试数据执行");
		
		firstLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		secondLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		thirdLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		fourthLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));

		leftpicturePanel.setLayout(null);
		leftpicturePanel.add(firstLabel);
		leftpicturePanel.add(secondLabel);
		leftpicturePanel.add(thirdLabel);
		leftpicturePanel.add(fourthLabel);
		
		firstLabel.setBounds(30, 20, 350, 60);
		secondLabel.setBounds(30, 90, 350, 60);
		thirdLabel.setBounds(30, 160, 350, 60);
		fourthLabel.setBounds(30, 230, 350, 60);

//		sequenceLabel = new JLabel("顺序图是将交互关系表示为一个二维图。纵向是时间轴，时间沿竖线向下延伸。横向轴代表了在协作中各独立对象的类元角色。");
//		sequenceLabel = new JLabel("填写用例的迁移概率，即为当前用例执行完后后续满足条件用例的执行概率。");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void SetButtonListener() {
		homebutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updatePanel();
				//添加标题
				JPanel labelpanel=mainFrame.getStepJLabel();
				labelpanel.setLayout(new GridLayout(1, 1));
				labelpanel.add(mainFrame.getTitlePanel());
				mainFrame.getTitlePanel().getBigTitle().setText("第一步:UML模型的可靠性测试扩展");
				mainFrame.getTitlePanel().getSmallTitle().setText("对UML图形进行正确绘制、扩展。");
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
				
				//添加步骤区域
				mainFrame.getconsolepartPanel().removeAll();
				mainFrame.getconsolepartPanel().setLayout(new GridLayout(1, 1));
				mainFrame.getconsolepartPanel().add(mainFrame.getsteponebottmopanel());
				firstLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
				
				wakeupPanel();
			    //调整边框
				JPanel mainPanel = mainFrame.getMainPanel();
				for(int i = 0;i < mainPanel.getComponentCount();i++)
				{
					if(mainPanel.getComponent(i).equals(mainFrame.getworkpanel()))
					{
						mainPanel.remove(mainPanel.getComponent(i));
						mainPanel.add(mainFrame.getworkpanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98).setInsets(10, 15, 20, 0));
					}
				}
				int step = mainFrame.getsteponeButton().getCurrentStep();
				switch (step) {
				case 1:
					mainFrame.getCenterTabPanel().removeAll();
//					mainFrame.getCenterTabPanel().add(mainFrame.getStepOneCenterUseCaseTabbedPane());
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
//				mainFrame.getCenterTabPanel().removeAll();
//				mainFrame.getCenterTabPanel().add(mainFrame.getStepOneCenterUseCaseTabbedPane());
//				mainFrame.getCenterTabPanel().updateUI();
//
//				mainFrame.getpanel().removeAll();
//				mainFrame.getpanel().setLayout(new GridLayout(1, 1));
//				mainFrame.getpanel().add(mainFrame.getStepOperationButton());
//				mainFrame.getpanel().updateUI();
				
				
				
				mainFrame.getinformationPanel().removeAll();
				mainFrame.getinformationPanel().setLayout(new GridLayout(1, 1));
				mainFrame.getinformationPanel().add(mainFrame.getOutputinformation());
				
				mainFrame.getReduceOrEnlargePanel().setLayout(new GridLayout(1, 1));
				mainFrame.getReduceOrEnlargePanel().add(mainFrame.getstepOneCenterRightPanel());
				
			}
		});
	}

	private void updatePanel()
	{
		mainFrame.getStepJLabel().setVisible(true); //初始化将头标题隐藏
		mainFrame.getconsolepartPanel().setVisible(true); 
		mainFrame.getOpreationPart().setVisible(true);
		mainFrame.getsteponebottmopanel().setVisible(true);
//		if(mainFrame.getsteponeButton().getbottompanel().getComponentCount() != 0)
//		mainFrame.getsteponeButton().getbottompanel().getComponent(0).setVisible(false);
	}
	 private void wakeupPanel()
	   	{
	   		mainFrame.getpanel().setVisible(true);
	   		mainFrame.getinformationPanel().setVisible(true);
	   		mainFrame.getbotoomJSplitPane(). setDividerLocation(0.5);
	   		mainFrame.getbotoomJSplitPane().setDividerSize(4);
	   		mainFrame.getReduceOrEnlargePanel().setVisible(true);
	   		mainFrame.getReduceOrEnlargePanel().removeAll();
//	   		if(mainFrame.getStepOneCenterSequenceTabbedPane().getComponentCount() == 0)
//	   		{
//	   		mainFrame.getStepOneCenterSequenceTabbedPane().setVisible(false);
//	   		mainFrame.getStepOneCenterUseCaseTabbedPane().setVisible(false);
//	   		}
//	   		if(mainFrame.getsteponeButton().getbottompanel().getComponentCount() != 0)
//	   			mainFrame.getsteponeButton().getbottompanel().getComponent(0).setVisible(true);
	   	}

	public JButton getHomebutton() {
		return homebutton;
	}
	 
	
}
