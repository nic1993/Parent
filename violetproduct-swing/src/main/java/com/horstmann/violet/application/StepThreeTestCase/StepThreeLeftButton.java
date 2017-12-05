package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.eclipse.draw2d.FlowLayout;

import com.horstmann.violet.application.StepOneBuildModel.UsecaseTreePanel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.gui.util.yangjie.CollectRoute;
import com.horstmann.violet.application.gui.util.yangjie.Markov;
import com.horstmann.violet.application.gui.util.yangjie.ReadMarkov2;
import com.horstmann.violet.application.gui.util.yangjie.Route;
import com.horstmann.violet.application.gui.util.yangjie.SearchConditions;
import com.horstmann.violet.application.menu.FileMenu;

public class StepThreeLeftButton extends JPanel{
	private JLabel choosePatternLabel;
	private JLabel noTimeModelLabel;
	private JLabel timeModelLabel;
	
	//不带时间约束
	private JLabel noTimeSeq;
	private JLabel noTimeCase;
	
	//带时间约束
	private JLabel modelExpand;
	private JLabel timeSeq;
	private JLabel timeCase;
		
	
	private JPanel noTimeModelPanel;
	private JPanel timeExpand;
	private JPanel panel;
	
	private MainFrame mainFrame;
	
	private ReadMarkov2 rm;
	private Markov markov;
	private int min;
	private double p;
	private double c;
	
	private String route;
	
	private String ModelName;
	
	private int stepThree = 1;
	
	//不带时间约束
	private JPanel noTimeSeqNode;
	private JPanel noTimeNode;
	
	//带有时间约束
	private JPanel TimeExpandLabel;
	private JPanel TimeSeqNode;
	private JPanel TimeNode;
	
	private NoTimeSeqNodePanel noTimeSeqNodePanel;
	private NoTimeCaseNodePanel noTimeCaseNodePanel;
	
	private TimeSeqNodePanel timeSeqNodePanel; 
	private TimeCaseNodePanel timeCaseNodePanel;
	private TimeExpandNodePanel timeExpandNodePanel;
	
	private boolean isNew;
	public StepThreeLeftButton(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;	
		init();
		noTimeModelPanel.setVisible(false);
		timeExpand.setVisible(false);
		
		noTimeModelLabel.setEnabled(false);
		timeModelLabel.setEnabled(false);
		
		this.setLayout(new GridBagLayout());
		this.add(choosePatternLabel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(15, 10, 0, 80));	

		this.add(noTimeModelLabel,new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(15, 10, 0, 80));
		this.add(noTimeModelPanel,new GBC(0, 2).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 30, 0, 0));
		
		this.add(timeModelLabel,new GBC(0, 3).setFill(GBC.BOTH).setWeight(1, 0).setInsets(15, 10, 0, 80));
		this.add(timeExpand,new GBC(0, 4).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 30, 0, 0));
		
		this.add(panel,new GBC(0, 5).setFill(GBC.BOTH).setWeight(1, 1));	
		
		noTimeCaseNodePanel = new NoTimeCaseNodePanel(mainFrame);
		noTimeSeqNodePanel = new NoTimeSeqNodePanel(mainFrame);
		
		timeSeqNodePanel = new TimeSeqNodePanel(mainFrame);
		timeCaseNodePanel = new TimeCaseNodePanel(mainFrame);
		timeExpandNodePanel = new TimeExpandNodePanel(mainFrame);
	}

	public void init()
	{
		this.setBackground(new Color(233,233,233));	
		choosePatternLabel = new JLabel("生成可靠性测试数据模式");
		noTimeModelLabel = new JLabel("不带时间约束模型");
		timeModelLabel = new JLabel("带时间约束模型");
		
		noTimeSeq = new JLabel("抽象测试序列生成");
		noTimeCase = new JLabel("可靠性测试数据生成");
		
		modelExpand = new JLabel("模型扩展");
		timeSeq = new JLabel("抽象测试序列生成");
		timeCase = new JLabel("可靠性测试数据生成");
		
		choosePatternLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
		noTimeModelLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		timeModelLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		noTimeSeq.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		noTimeCase.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		modelExpand.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		timeSeq.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		timeCase.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		
		noTimeModelLabel.setEnabled(false);
		noTimeCase.setEnabled(false);
		
		timeModelLabel.setEnabled(false);
		modelExpand.setEnabled(false);
		timeSeq.setEnabled(false);
		timeCase.setEnabled(false);
		
		noTimeModelPanel = new JPanel();
		noTimeSeqNode = new JPanel();
		noTimeNode = new JPanel();
		
		
		timeExpand = new JPanel();
		TimeExpandLabel = new JPanel();
		TimeSeqNode = new JPanel();
		TimeNode = new JPanel();
        panel = new JPanel();

        panel.setBackground(new Color(233,233,233));
		noTimeModelPanel.setBackground(new Color(233,233,233));
		noTimeNode.setBackground(new Color(233,233,233));
		timeExpand.setBackground(new Color(233,233,233));
		TimeExpandLabel.setBackground(new Color(233,233,233));
		TimeNode.setBackground(new Color(233,233,233));
		
		noTimeModelPanel.setLayout(new GridBagLayout());
		noTimeCase.setLayout(new GridBagLayout());
		noTimeModelPanel.add(noTimeSeq,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5,0,0,0));
		noTimeModelPanel.add(noTimeSeqNode,new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5,0,0,0));
		noTimeModelPanel.add(noTimeCase,new GBC(0, 2).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5,0,0,0));
		noTimeModelPanel.add(noTimeNode,new GBC(0, 3).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5,0,0,0));

		noTimeSeqNode.setLayout(new GridBagLayout());
		noTimeNode.setLayout(new GridBagLayout());
		timeExpand.setLayout(new GridBagLayout());
		TimeExpandLabel.setLayout(new GridBagLayout());
		TimeSeqNode.setLayout(new GridBagLayout());
		TimeNode.setLayout(new GridBagLayout());
		
		timeExpand.add(modelExpand,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 0, 0, 0));
		timeExpand.add(TimeExpandLabel,new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 0, 0, 0));
		
		timeExpand.add(timeSeq,new GBC(0, 2).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 0, 0, 0));
		timeExpand.add(TimeSeqNode,new GBC(0, 3).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 0, 0, 0));
		
		timeExpand.add(timeCase,new GBC(0, 4).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 0, 0, 0));
		timeExpand.add(TimeNode,new GBC(0, 5).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 0, 0, 0));
		labelListen();
	}
	
	private void labelListen()
	{
		choosePatternLabel.addMouseListener(new MouseAdapter(){	
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(((JLabel)e.getSource()).isEnabled())
				{
					stepThree = 1;
					choosePatternLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
					noTimeModelLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
					timeModelLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));	
				
					clearPanel();
					JPanel mainPanel = mainFrame.getMainPanel();
					for(int i = 0;i < mainPanel.getComponentCount();i++)
					{
						if(mainPanel.getComponent(i).equals(mainFrame.getworkpanel()))
						{
							mainPanel.remove(mainPanel.getComponent(i));
							mainPanel.add(mainFrame.getworkpanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98).setInsets(10, 15, 20, 15));
						}
					}
					mainFrame.getOutputinformation().setVisible(false);
					mainFrame.getReduceOrEnlargePanel().setVisible(false);
					mainFrame.getpanel().setVisible(false);
					noTimeModelPanel.setVisible(false);
					timeExpand.setVisible(false);
					mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeChoosePattern());
					mainFrame.getCenterTabPanel().repaint();
					mainFrame.renewPanel();
				}
			}
		});
		noTimeModelLabel.addMouseListener(new MouseAdapter(){	
			@Override
			public void mousePressed(MouseEvent e) {
				if(((JLabel)e.getSource()).isEnabled())
				{
					mainFrame.getNoTimeSeqOperation().setModelName(ModelName);
					mainFrame.getNoTimeSeqOperation1().setModelName(ModelName);
						
					noTimeSeq.setFont(new Font("微软雅黑", Font.BOLD, 16));
					noTimeCase.setFont(new Font("微软雅黑", Font.PLAIN, 16));
					choosePatternLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
					noTimeModelPanel.setVisible(true);
					timeExpand.setVisible(false);
					clearPanel();
						
					noTimeSeqNode.setVisible(true);
					noTimeNode.setVisible(false);
						
					JPanel mainPanel = mainFrame.getMainPanel();
					for(int i = 0;i < mainPanel.getComponentCount();i++)
					{
						if(mainPanel.getComponent(i).equals(mainFrame.getworkpanel()))
						{
							mainPanel.remove(mainPanel.getComponent(i));
							mainPanel.add(mainFrame.getworkpanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98).setInsets(10, 15, 20, 0));
						}
					}
					if(mainFrame.getStepThreeChoosePattern().getselectString() == null)
					{
						stepThree = 2;
						mainFrame.getOutputinformation().setVisible(true);
						mainFrame.getReduceOrEnlargePanel().setVisible(true);
						mainFrame.getpanel().setVisible(true);
							
						mainFrame.getpanel().add(mainFrame.getNoTimeSeqOperation());
						mainFrame.getNoTimeSeqOperation().getTopLabel().setText("请选择测试数据生成方式!");
						mainFrame.getNoTimeSeqOperation().getButton().setEnabled(false);
						mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeNoTimeSeqTabbedPane());
						mainFrame.getStepTwoCenterRightPanel().getGraphButton().setVisible(false);
						mainFrame.getStepTwoCenterRightPanel().getZoominButton().setVisible(false);
						mainFrame.getStepTwoCenterRightPanel().getZoomoutButton().setVisible(false);
							
					}
						//后期添加选择是否选择了模型
						else if(mainFrame.getStepThreeChoosePattern().getselectString().equals("根据模型相似度生成"))
						{
							stepThree = 2;
							mainFrame.getOutputinformation().setVisible(true);
							mainFrame.getReduceOrEnlargePanel().setVisible(true);
							mainFrame.getpanel().setVisible(true);
							mainFrame.renewPanel();
							
							mainFrame.getpanel().add(mainFrame.getNoTimeSeqOperation());
							if(isNew == true && ModelName != null){
								mainFrame.getNoTimeSeqOperation().getTopLabel().setText("当前选择的模型为:"+ModelName);
								mainFrame.getNoTimeSeqOperation().getButton().setEnabled(true);
								
							}
							if(ModelName == null)
							{
								mainFrame.getNoTimeSeqOperation().getTopLabel().setText("当前选择的模型为:"+ModelName);
								mainFrame.getNoTimeSeqOperation().getButton().setEnabled(false);
							}
							else {
								mainFrame.getNoTimeSeqOperation().getButton().setEnabled(true);
							}
							mainFrame.getpanel().updateUI();
							
							mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeNoTimeSeqTabbedPane());
							mainFrame.getStepTwoCenterRightPanel().getGraphButton().setVisible(false);
							mainFrame.getStepTwoCenterRightPanel().getZoominButton().setVisible(false);
							mainFrame.getStepTwoCenterRightPanel().getZoomoutButton().setVisible(false);
						}
						else if(mainFrame.getStepThreeChoosePattern().getselectString().equals("自定义可靠性测试数据个数生成"))
						{
							try {
							stepThree = 3;
							File files = new File(mainFrame.getBathRoute()+"/NoTimeMarkov/");
							if(isNew == true  && ModelName != null){
								mainFrame.getNoTimeSeqOperation1().getTopLabel().removeAll();
								mainFrame.getNoTimeSeqOperation1().getTopLabel().setText("当前选择的模型为:"+ModelName);
								
								for(File selectFile : files.listFiles())
								{
									if(selectFile.getName().equals(ModelName+".xml"))
										route = selectFile.getAbsolutePath();
								}
								if(mainFrame.getStepThreeChoosePattern().getReliabilityIndex() == null || 
										mainFrame.getStepThreeChoosePattern().getConfidence() == null)
								{
									mainFrame.getNoTimeSeqOperation1().getLabel2().removeAll();
								    mainFrame.getNoTimeSeqOperation1().getLabel2().setText("请在自定义生成可靠性测试数据方式中填写置信度与可靠性指标！");
								    mainFrame.getNoTimeSeqOperation1().getButton().setEnabled(false);
								}
								else if (mainFrame.getStepThreeChoosePattern().getFile() == null) {
									mainFrame.getNoTimeSeqOperation1().getLabel2().removeAll();
								    mainFrame.getNoTimeSeqOperation1().getLabel2().setText("请在自定义生成可靠性测试数据方式中选择程序源码文件！");
								    mainFrame.getNoTimeSeqOperation1().getButton().setEnabled(false);
								}
								else {
									mainFrame.getNoTimeSeqOperation1().getButton().setEnabled(true);
									p = mainFrame.getStepThreeChoosePattern().getReliabilityIndex();
									c = mainFrame.getStepThreeChoosePattern().getConfidence();
									try {
									rm = new ReadMarkov2();
									markov = rm.readMarkov(route);
									min = getMinTCNum(markov,p,c);
									} catch (RuntimeException e2) {
										// 处理异常
										mainFrame.getNoTimeSeqOperation1().getTopLabel().removeAll();
										mainFrame.getNoTimeSeqOperation1().getTopLabel().setText(e2.getLocalizedMessage());
										mainFrame.getNoTimeCaseOperation1().getButton().setEnabled(false);
										
										mainFrame.getOutputinformation().setVisible(true);
										mainFrame.getReduceOrEnlargePanel().setVisible(true);
										mainFrame.getpanel().setVisible(true);
										clearPanel();
										mainFrame.getpanel().add(mainFrame.getNoTimeSeqOperation1());
										
										mainFrame.getpanel().updateUI();	
										mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeNoTimeSeqTabbedPane());
										mainFrame.getCenterTabPanel().updateUI();
										mainFrame.getStepTwoCenterRightPanel().getGraphButton().setVisible(false);
										mainFrame.getStepTwoCenterRightPanel().getZoominButton().setVisible(false);
										mainFrame.getStepTwoCenterRightPanel().getZoomoutButton().setVisible(false);
										mainFrame.renewPanel();
									}
									mainFrame.getNoTimeSeqOperation1().getLabel2().removeAll();
									mainFrame.getNoTimeSeqOperation1().getLabel2().setText("(不少于)"+String.valueOf(min)+"条");
								}
							}
							else if(ModelName == null){
								mainFrame.getNoTimeSeqOperation1().getTopLabel().setText("当前选择的模型为:"+ModelName);
								mainFrame.getNoTimeCaseOperation1().getButton().setEnabled(false);
							}
							mainFrame.getOutputinformation().setVisible(true);
							mainFrame.getReduceOrEnlargePanel().setVisible(true);
							mainFrame.getpanel().setVisible(true);
							clearPanel();
							mainFrame.getpanel().add(mainFrame.getNoTimeSeqOperation1());
							
							mainFrame.getpanel().updateUI();	
							mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeNoTimeSeqTabbedPane());
							mainFrame.getCenterTabPanel().updateUI();
							mainFrame.getStepTwoCenterRightPanel().getGraphButton().setVisible(false);
							mainFrame.getStepTwoCenterRightPanel().getZoominButton().setVisible(false);
							mainFrame.getStepTwoCenterRightPanel().getZoomoutButton().setVisible(false);
							mainFrame.renewPanel();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						else {
							return;
						}
				}
			}
		});
		noTimeSeq.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub	
				if(((JLabel)e.getSource()).isEnabled())
				{
					noTimeSeq.setFont(new Font("微软雅黑", Font.BOLD, 16));
					noTimeCase.setFont(new Font("微软雅黑", Font.PLAIN, 16));

					noTimeSeqNode.setVisible(true);
					noTimeNode.setVisible(false);
					clearPanel();		
					
					if(mainFrame.getStepThreeChoosePattern().getselectString() == null)
					{
						stepThree = 2;
						mainFrame.getOutputinformation().setVisible(true);
						mainFrame.getReduceOrEnlargePanel().setVisible(true);
						mainFrame.getpanel().setVisible(true);
						
						mainFrame.getpanel().add(mainFrame.getNoTimeSeqOperation());
						mainFrame.getNoTimeSeqOperation().getTopLabel().setText("请选择测试数据生成方式!");
						mainFrame.getNoTimeSeqOperation().getButton().setEnabled(false);
						mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeNoTimeSeqTabbedPane());
						mainFrame.getStepTwoCenterRightPanel().getGraphButton().setVisible(false);
						mainFrame.getStepTwoCenterRightPanel().getZoominButton().setVisible(false);
						mainFrame.getStepTwoCenterRightPanel().getZoomoutButton().setVisible(false);
						
					}
					else if(mainFrame.getStepThreeChoosePattern().getselectString().equals("根据模型相似度生成"))
					{
						stepThree = 2;
						mainFrame.getOutputinformation().setVisible(true);
						mainFrame.getReduceOrEnlargePanel().setVisible(true);
						mainFrame.getpanel().setVisible(true);
						mainFrame.renewPanel();
						
						mainFrame.getpanel().add(mainFrame.getNoTimeSeqOperation());
						if(isNew == true && ModelName != null){
							mainFrame.getNoTimeSeqOperation().getTopLabel().setText("当前选择的模型为:"+ModelName);
							mainFrame.getNoTimeSeqOperation().getButton().setEnabled(true);
						}
						if(ModelName == null)
						{
							mainFrame.getNoTimeSeqOperation().getTopLabel().setText("当前选择的模型为:"+ModelName);
							mainFrame.getNoTimeSeqOperation().getButton().setEnabled(false);
						}
						else {
							mainFrame.getNoTimeSeqOperation().getButton().setEnabled(true);
						}
						mainFrame.getpanel().updateUI();
						
						mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeNoTimeSeqTabbedPane());
						mainFrame.getStepTwoCenterRightPanel().getGraphButton().setVisible(false);
						mainFrame.getStepTwoCenterRightPanel().getZoominButton().setVisible(false);
						mainFrame.getStepTwoCenterRightPanel().getZoomoutButton().setVisible(false);
					}
					else{
						try {
						stepThree = 3;
						File files = new File(mainFrame.getBathRoute()+"/NoTimeMarkov/");
						if(isNew == true && ModelName != null){
							mainFrame.getNoTimeSeqOperation1().getTopLabel().setText("当前选择的模型为:"+ModelName);
							
							for(File selectFile : files.listFiles())
							{
								if(selectFile.getName().replaceAll(".xml", "").equals(ModelName))
									route = selectFile.getAbsolutePath();
							}
							if(mainFrame.getStepThreeChoosePattern().getReliabilityIndex() == null || 
									mainFrame.getStepThreeChoosePattern().getConfidence() == null )
							{
								mainFrame.getNoTimeSeqOperation1().getLabel2().removeAll();
							    mainFrame.getNoTimeSeqOperation1().getLabel2().setText("请在自定义生成可靠性测试数据方式中填写置信度与可靠性指标！");
							    mainFrame.getNoTimeSeqOperation1().getButton().setEnabled(false);
							}
							else if (mainFrame.getStepThreeChoosePattern().getFile() == null) {
								mainFrame.getNoTimeSeqOperation1().getLabel2().removeAll();
							    mainFrame.getNoTimeSeqOperation1().getLabel2().setText("请在自定义生成可靠性测试数据方式中选择程序源码文件！");
							    mainFrame.getNoTimeSeqOperation1().getButton().setEnabled(false);
							}
							else {
								mainFrame.getNoTimeSeqOperation1().getButton().setEnabled(true);	
								p = mainFrame.getStepThreeChoosePattern().getReliabilityIndex();
								c = mainFrame.getStepThreeChoosePattern().getConfidence();
								rm = new ReadMarkov2();
								markov = rm.readMarkov(route);
								min = getMinTCNum(markov,p,c);
								mainFrame.getNoTimeSeqOperation1().getLabel2().removeAll();
								mainFrame.getNoTimeSeqOperation1().getLabel2().setText("(不少于)"+String.valueOf(min)+"条");
							}
						}
						else if(ModelName == null){
							mainFrame.getNoTimeSeqOperation1().getTopLabel().setText("当前选择的模型为:"+ModelName);
							mainFrame.getNoTimeSeqOperation1().getButton().setEnabled(false);
						}

						clearPanel();
						mainFrame.getpanel().add(mainFrame.getNoTimeSeqOperation1());
						
						mainFrame.getpanel().updateUI();	
						mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeNoTimeSeqTabbedPane());
						mainFrame.getCenterTabPanel().updateUI();
						mainFrame.renewPanel();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
					
			}
		});
		
		noTimeCase.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(((JLabel)e.getSource()).isEnabled())
				{
					noTimeSeq.setFont(new Font("微软雅黑", Font.PLAIN, 16));
					noTimeCase.setFont(new Font("微软雅黑", Font.BOLD, 16));
					noTimeSeqNode.setVisible(false);
					noTimeNode.setVisible(true);
					clearPanel();		
					
					if(mainFrame.getStepThreeChoosePattern().getselectString().equals("根据模型相似度生成"))
					{				
						stepThree = 4;
						mainFrame.getpanel().add(mainFrame.getNoTimeCaseOperation());
						if(isNew == true && ModelName != null){
							mainFrame.getNoTimeCaseOperation().getTopLabel().setText("当前选择的模型为:"+ModelName);
							mainFrame.getNoTimeCaseOperation().getButton().setEnabled(true);
						}
						if(ModelName == null)
						{
							mainFrame.getNoTimeCaseOperation().getTopLabel().setText("当前选择的模型为:"+ModelName);
							mainFrame.getNoTimeCaseOperation().getButton().setEnabled(false);
						}
						mainFrame.getpanel().updateUI();
						
						mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeNoTimeTabbedPane());
						mainFrame.renewPanel();
					}
					else if(mainFrame.getStepThreeChoosePattern().getselectString().equals("自定义可靠性测试数据个数生成")){
						try {
							stepThree = 5;
							if(isNew == true && ModelName != null){
								mainFrame.getNoTimeCaseOperation1().getTopLabel().setText("当前选择的模型为:"+ModelName);
									mainFrame.getNoTimeCaseOperation1().getButton().setEnabled(true);
									int minCase = mainFrame.getNoTimeSeqOperation1().getMinSeq();
									mainFrame.getNoTimeCaseOperation1().getTextField().setText(String.valueOf(minCase));
								}
							
							else if(ModelName == null){
								mainFrame.getNoTimeCaseOperation1().getTopLabel().setText("当前选择的模型为:"+ModelName);
								mainFrame.getNoTimeCaseOperation1().getButton().setEnabled(false);
							}
							clearPanel();
							mainFrame.getpanel().add(mainFrame.getNoTimeCaseOperation1());
							mainFrame.getpanel().updateUI();	
							mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeNoTimeTabbedPane());
							mainFrame.getCenterTabPanel().updateUI();
							mainFrame.renewPanel();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					}
				}
			}
		});
		
		timeModelLabel.addMouseListener(new MouseAdapter(){	
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(((JLabel)e.getSource()).isEnabled())
				{
					stepThree = 6;
					choosePatternLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
					modelExpand.setFont(new Font("微软雅黑", Font.BOLD, 18));
					timeSeq.setFont(new Font("微软雅黑", Font.PLAIN, 18));
					timeCase.setFont(new Font("微软雅黑", Font.PLAIN, 18));
					
					noTimeModelPanel.setVisible(false);
					
					timeExpand.setVisible(true);
					TimeExpandLabel.setVisible(true);
					TimeSeqNode.setVisible(false);
					TimeNode.setVisible(false);
					modelExpand.setEnabled(true);
					
					mainFrame.getOutputinformation().setVisible(true);
					mainFrame.getReduceOrEnlargePanel().setVisible(true);
					mainFrame.getpanel().setVisible(true);
					
					
					JPanel mainPanel = mainFrame.getMainPanel();
					for(int i = 0;i < mainPanel.getComponentCount();i++)
					{
						if(mainPanel.getComponent(i).equals(mainFrame.getworkpanel()))
						{
							mainPanel.remove(mainPanel.getComponent(i));
							mainPanel.add(mainFrame.getworkpanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98).setInsets(10, 15, 20, 0));
						}
					}		
				
				mainFrame.getTimeExpandOperation().setModelName(ModelName);
				clearPanel();
				if(isNew == true && ModelName != null){
					mainFrame.getTimeExpandOperation().getExpandlabel().setText("当前选择的模型为:"+ModelName);
					mainFrame.getTimeExpandOperation().getModelExchange().setEnabled(true);
					
				}
				else if(ModelName == null){
					mainFrame.getTimeExpandOperation().getExpandlabel().setText("当前选择的模型为:"+ModelName);
					mainFrame.getTimeExpandOperation().getModelExchange().setEnabled(false);
					
				}
				else{
					mainFrame.getTimeExpandOperation().getModelExchange().setEnabled(true);
				}
				mainFrame.getpanel().setVisible(true);
				mainFrame.getpanel().add(mainFrame.getTimeExpandOperation());
				mainFrame.getCenterTabPanel().add(mainFrame.getTimeExpandTabbedPane());
				
				mainFrame.getStepTwoCenterRightPanel().getGraphButton().setVisible(true);
				mainFrame.getStepTwoCenterRightPanel().getZoominButton().setVisible(true);
				mainFrame.getStepTwoCenterRightPanel().getZoomoutButton().setVisible(true);
				mainFrame.renewPanel();
				}
			}  
		});
		modelExpand.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(((JLabel)e.getSource()).isEnabled())
				{
					stepThree = 6;
					clearPanel();
					modelExpand.setFont(new Font("微软雅黑", Font.BOLD, 18));
					timeSeq.setFont(new Font("微软雅黑", Font.PLAIN, 18));
					timeCase.setFont(new Font("微软雅黑", Font.PLAIN, 18));
					
					TimeExpandLabel.setVisible(true);
					TimeSeqNode.setVisible(false);
					TimeNode.setVisible(false);
					if(isNew == true && ModelName != null){
						mainFrame.getTimeExpandOperation().getExpandlabel().setText("当前选择的模型为:"+ModelName);
						mainFrame.getTimeExpandOperation().getModelExchange().setEnabled(true);
					}
					else if(ModelName == null){
						mainFrame.getTimeExpandOperation().getExpandlabel().setText("当前选择的模型为:"+ModelName);
						mainFrame.getTimeExpandOperation().getModelExchange().setEnabled(false);
					}
					else{
						mainFrame.getTimeExpandOperation().getModelExchange().setEnabled(true);
					}
					mainFrame.getpanel().setVisible(true);
					mainFrame.getpanel().add(mainFrame.getTimeExpandOperation());
					mainFrame.getCenterTabPanel().add(mainFrame.getTimeExpandTabbedPane());
					
					mainFrame.getStepTwoCenterRightPanel().getGraphButton().setVisible(true);
					mainFrame.getStepTwoCenterRightPanel().getZoominButton().setVisible(true);
					mainFrame.getStepTwoCenterRightPanel().getZoomoutButton().setVisible(true);
					mainFrame.renewPanel();
				}
			}
		});
		
		timeSeq.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub	
				if(((JLabel)e.getSource()).isEnabled())
				{
					timeCase.setFont(new Font("微软雅黑", Font.PLAIN, 18));
					timeSeq.setFont(new Font("微软雅黑", Font.BOLD, 18));
					modelExpand.setFont(new Font("微软雅黑", Font.PLAIN, 18));

					TimeExpandLabel.setVisible(false);
					TimeSeqNode.setVisible(true);
					TimeNode.setVisible(false);
					
					mainFrame.getOutputinformation().setVisible(true);
					mainFrame.getReduceOrEnlargePanel().setVisible(true);
					mainFrame.getpanel().setVisible(true);
					
					mainFrame.getStepTwoCenterRightPanel().getGraphButton().setVisible(false);
					mainFrame.getStepTwoCenterRightPanel().getZoominButton().setVisible(false);
					mainFrame.getStepTwoCenterRightPanel().getZoomoutButton().setVisible(false);
					markov = null;
					clearPanel();		
					
					if(mainFrame.getStepThreeChoosePattern().getselectString() == null)
					{
						stepThree = 7;
						
						mainFrame.getpanel().add(mainFrame.getTimeSeqOperation());
						mainFrame.getTimeSeqOperation().getTopLabel().setText("请选择测试数据生成方式!");
						mainFrame.getTimeSeqOperation().getButton().setEnabled(false);
						mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeTimeSeqTabbedPane());
						
					}
					else if(mainFrame.getStepThreeChoosePattern().getselectString().equals("根据模型相似度生成"))
					{
						stepThree = 7;
						
						mainFrame.getpanel().add(mainFrame.getTimeSeqOperation());
						if(isNew == true && ModelName != null){
							mainFrame.getTimeSeqOperation().getTopLabel().setText("当前选择的模型为:"+ModelName);
							mainFrame.getTimeSeqOperation().getButton().setEnabled(true);
						}
						if(ModelName == null)
						{
							mainFrame.getTimeSeqOperation().getTopLabel().setText("当前选择的模型为:"+ModelName);
							mainFrame.getTimeSeqOperation().getButton().setEnabled(false);
						}
						else {
							mainFrame.getTimeSeqOperation().getButton().setEnabled(true);
						}
						
						mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeTimeSeqTabbedPane());
					}
					else{
						try {
						stepThree = 8;
						File files = new File(mainFrame.getBathRoute()+"/extendMarkov/");
						if(isNew == true && ModelName != null){
							mainFrame.getTimeSeqOperation1().getTopLabel().setText("当前选择的模型为:"+ModelName);
							for(File selectFile : files.listFiles())
							{
								if(selectFile.getName().replaceAll("_TimeExtend.xml", "").equals(ModelName))
									route = selectFile.getAbsolutePath();
							}
							if(mainFrame.getStepThreeChoosePattern().getReliabilityIndex() == null || 
									mainFrame.getStepThreeChoosePattern().getConfidence() == null)
							{
								mainFrame.getTimeSeqOperation1().getLabel2().removeAll();      
							    mainFrame.getTimeSeqOperation1().getLabel2().setText("请在自定义生成可靠性测试数据方式中填写置信度与可靠性指标！");
							    mainFrame.getTimeSeqOperation1().getButton().setEnabled(false);
							    
							}
							else if (mainFrame.getStepThreeChoosePattern().getFile() == null) {
								mainFrame.getTimeSeqOperation1().getLabel2().removeAll();
							    mainFrame.getTimeSeqOperation1().getLabel2().setText("请在自定义生成可靠性测试数据方式中选择程序源码文件！");
							    mainFrame.getTimeSeqOperation1().getButton().setEnabled(false);
							}
							else {
								mainFrame.getTimeSeqOperation1().getButton().setEnabled(true);
								p = mainFrame.getStepThreeChoosePattern().getReliabilityIndex();
								c = mainFrame.getStepThreeChoosePattern().getConfidence();

								rm = new ReadMarkov2();
								markov = rm.readMarkov(route);
								min = getMinTCNum(markov,p,c);
								mainFrame.getTimeSeqOperation1().getLabel2().removeAll();
								mainFrame.getTimeSeqOperation1().getLabel2().setText("(不少于)"+String.valueOf(min)+"条");
							}
						}
						else if(ModelName == null){
							mainFrame.getTimeSeqOperation1().getButton().setEnabled(false);
						}

						clearPanel();
						mainFrame.getpanel().add(mainFrame.getTimeSeqOperation1());
						
						mainFrame.getpanel().updateUI();	
						mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeTimeSeqTabbedPane());
						mainFrame.getCenterTabPanel().updateUI();
						mainFrame.renewPanel();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}	
					mainFrame.renewPanel();
				}
			}
		});
		
		timeCase.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(((JLabel)e.getSource()).isEnabled())
				{
					clearPanel();
					modelExpand.setFont(new Font("微软雅黑", Font.PLAIN, 18));
					timeSeq.setFont(new Font("微软雅黑", Font.PLAIN, 18));
					timeCase.setFont(new Font("微软雅黑", Font.BOLD, 18));
					TimeExpandLabel.setVisible(false);
					TimeSeqNode.setVisible(false);
					TimeNode.setVisible(true);
					
					mainFrame.getStepTwoCenterRightPanel().getGraphButton().setVisible(false);
					mainFrame.getStepTwoCenterRightPanel().getZoominButton().setVisible(false);
					mainFrame.getStepTwoCenterRightPanel().getZoomoutButton().setVisible(false);
					
					if(mainFrame.getStepThreeChoosePattern().getselectString().equals("根据模型相似度生成"))
					{					
						stepThree = 9;
						mainFrame.getOutputinformation().setVisible(true);
						mainFrame.getReduceOrEnlargePanel().setVisible(true);
						mainFrame.getpanel().setVisible(true);
						mainFrame.getpanel().add(mainFrame.getTimeCaseOperation());
						if(mainFrame.getTimeMarkovFileRadio().getIsSameName() == false){
							mainFrame.getTimeCaseOperation().getTopLabel().setText("当前选择的模型为:"+ModelName);
						}
						mainFrame.getpanel().updateUI();
						mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeTimeTabbedPane());

						mainFrame.renewPanel();
					}
					else if(mainFrame.getStepThreeChoosePattern().getselectString().equals("自定义可靠性测试数据个数生成"))
					{
						
						stepThree = 10;
						int minCase = mainFrame.getTimeSeqOperation1().getMinSeq();
						mainFrame.getTimeCaseOperation1().getTextField().setText(String.valueOf(minCase));
						
						mainFrame.getOutputinformation().setVisible(true);
						mainFrame.getReduceOrEnlargePanel().setVisible(true);
						mainFrame.getpanel().setVisible(true);
						clearPanel();
						mainFrame.getpanel().add(mainFrame.getTimeCaseOperation1());
//						mainFrame.getpanel().add(mainFrame.getTimeCaseOperation());
						if(mainFrame.getTimeMarkovFileRadio().getIsSameName() == false){
							mainFrame.getTimeCaseOperation1().getTopLabel().setText("当前选择的模型为:"+ModelName);
						}
						mainFrame.getpanel().updateUI();	
						mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeTimeTabbedPane());
						mainFrame.renewPanel();
					}	
				}
			}
		});
	}
	private  int getMinTCNum(Markov markov, double p,double c) throws Exception {
		// 按照pc公式计算最小可靠性测试数据数目
//		int min_pc = (int) Math.ceil(Math.log10(1 - c) / Math.log10(1 - p));
//
//		// 按照固定最小概率路径个数为一来计算最小可靠性测试数据数目
//		new CollectRoute().collect(markov);
//		double prob = 1;
//		for (Route r : markov.getRouteList()) {
//			if (r.getRouteProbability() < prob) {
//				prob = r.getRouteProbability();
//			}
//		}
//		int min_routePro = (int) Math.round(1.0 / prob);
//
//		// 按照DO-178B MCDC准则计算最小可靠性测试数据数目(条件数+1)
//		int min_mcdc = SearchConditions.findConditionNum() + 1;
//
//		int temp = Math.max(min_pc, min_routePro);
//
//		
//		return Math.max(temp, min_mcdc);
//		int min_pc = (int) Math.ceil(Math.log10(1 - c) / Math.log10(1 - p));
//
//		// 按照DO-178B MCDC准则计算最小可靠性测试数据数目(条件数+1)
//		int min_mcdc = SearchConditions.findConditionNum(mainFrame.getStepThreeChoosePattern().getFile()) + 1;
//		
//		return Math.max(min_pc, min_mcdc);
		
		int min_pc = (int) Math.ceil(Math.log10(1 - c) / Math.log10(1 - p));

//		System.out.println(min_pc);
		// 按照DO-178B MCDC准则计算最小可靠性测试数据数目(条件数+1)
		int min_mcdc = SearchConditions.findConditionNum(mainFrame.getStepThreeChoosePattern().getFile()) + 1;
		
		return Math.max(min_pc, min_mcdc);
	}
	public void clearPanel()
	{
		mainFrame.getpanel().removeAll();
		mainFrame.getCenterTabPanel().removeAll();
	}

	public ReadMarkov2 getRm() {
		return rm;
	}

	public int getMin() {
		return min;
	}

	public Markov getMarkov() {
		return markov;
	}
    
	public JLabel getChoosePatternLabel() {
		return choosePatternLabel;
	}
	public JLabel getNoTimeModelLabel() {
		return noTimeModelLabel;
	}

	public JLabel getTimeModelLabel() {
		return timeModelLabel;
	}
    
	public JPanel getNoTimeModelPanel() {
		return noTimeModelPanel;
	}
	
	
	public JLabel getNoTimeCase() {
		return noTimeCase;
	}

	public JLabel getNoTimeSeq() {
		return noTimeSeq;
	}

	public JPanel getTimeModelPanel() {
		return timeExpand;
	}
    
	
	public JLabel getModelExpand() {
		return modelExpand;
	}

	public JLabel getTimeCase() {
		return timeCase;
	}
    
	public JLabel getTimeSeq() {
		return timeSeq;
	}
    
	public int getStepThree() {
		return stepThree;
	}

	public String getModelName() {
		return ModelName;
	}

	public void setModelName(String modelName) {
		ModelName = modelName;
	}
	public JPanel getNoTimeSeqNode() {
		return noTimeSeqNode;
	}
   
	public JPanel getNoTimeNode() {
		return noTimeNode;
	}

	public JPanel getTimeExpandLabel() {
		return TimeExpandLabel;
	}
    
	public JPanel getTimeSeqNode() {
		return TimeSeqNode;
	}

	public JPanel getTimeNode() {
		return TimeNode;
	}
	
	public NoTimeSeqNodePanel getNoTimeSeqNodePanel() {
		return noTimeSeqNodePanel;
	}

	public NoTimeCaseNodePanel getNoTimeCaseNodePanel() {
		return noTimeCaseNodePanel;
	}
	
	public TimeSeqNodePanel getTimeSeqNodePanel() {
		return timeSeqNodePanel;
	}

	public TimeCaseNodePanel getTimeCaseNodePanel() {
		return timeCaseNodePanel;
	}

	public TimeExpandNodePanel getTimeExpandNodePanel() {
		return timeExpandNodePanel;
	}
  
	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}	
	
}
