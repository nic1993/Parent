package com.horstmann.violet.application.StepTwoExchange;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.framework.propertyeditor.customeditor.GBC;
import com.horstmann.violet.workspace.IWorkspace;

public class StepTwoExchangeTabbedPane extends JTabbedPane{
	private MainFrame mainFrame;
	private JPanel exchangeResults;
	private JPanel exchangeResport;
   
	private JLabel estimateLabel;
	private JLabel exchangeLabel;
	
	private JPanel totalPanel;
	private JPanel leftPanel;
	public StepTwoExchangeTabbedPane(MainFrame mainFrame)
	{
		init();
		this.mainFrame = mainFrame;
		this.add("Markov",exchangeResults);
		this.add("Markov文件结构",exchangeResport);
//		this.add("测试用例生成",totalPanel);
////		this.add("模型优化",exchangeResport);
////		this.add("测试用例生成", new JPanel());
//		this.setUI(new BasicTabbedPaneUI(){
//			protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) { 
//			    return super.calculateTabWidth(tabPlacement, tabIndex, metrics); 
//			} 
//		});
	
	}

	private void init()
	{
		exchangeResults = new JPanel();
		exchangeResults.setLayout(new GridLayout(1, 1));
		exchangeResport = new JPanel();
		exchangeResport.setLayout(new GridLayout(1, 1));
		
		
		totalPanel = new JPanel();
		leftPanel = new JPanel();
		
		leftPanel.setLayout(new GridBagLayout());
		JLabel label1 = new JLabel("相似度");
		JLabel label2 = new JLabel("覆盖率");
//		JLabel label3 = new JLabel("激励");
//		JLabel label4 = new JLabel("时间约束");
		
		JTextField field1 = new JTextField();
		JTextField field2 = new JTextField();
//		JTextField field3 = new JTextField();
//		JTextField field4 = new JTextField();
		field1.setPreferredSize(new Dimension(100,25));
		
		JButton button = new JButton("开始生成");
		JButton button1 = new JButton("保存结果");
		
//		estimateLabel = new JLabel("模型检验");
//		exchangeLabel = new JLabel("Markov优化");
//		estimateLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
//		exchangeLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
		
		
		leftPanel.add(label1, new GBC(0, 0).setFill(GBC.BOTH).setWeight(0, 0).setInsets(10, 10, 10, 0));
		leftPanel.add(field1, new GBC(0, 1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0,10,10,0));
		leftPanel.add(label2, new GBC(0, 2).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0,10,10,0));
		leftPanel.add(field2, new GBC(0, 3).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0,10,10,0));
		leftPanel.add(button, new GBC(0, 4).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0,10,10,0));
		leftPanel.add(button1, new GBC(0, 5).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0,10,10,0));
		leftPanel.add(new JPanel(), new GBC(0, 6).setFill(GBC.BOTH).setWeight(0, 1));
		
		totalPanel.setLayout(new GridBagLayout());
		totalPanel.add(leftPanel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 0, 0, 10));
		totalPanel.add(exchangeResults,new GBC(1, 0).setFill(GBC.BOTH).setWeight(1, 1));
		
		exchangeResults.setBackground(Color.WHITE);
		listen();
	}
    public void listen()
    {
    	this.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
		});
    }
	public JPanel getExchangeResults() {
		return exchangeResults;
	}

	public JPanel getExchangeResport() {
		return exchangeResport;
	}
}

