package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.horstmann.violet.application.StepTwoModelExpand.ScenceTabelPanel;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.workspace.IWorkspace;

public class StepThreeTimeTabbedPane extends JTabbedPane{
	private JPanel abstractSequence;
	private JPanel testData;
    private List<ScenceTabelPanel> CaseValidationList;
    private JScrollPane testDataScroll;
    private  JScrollPane abstractScroll;
//    private JScrollPane 
    private MainFrame mainFrame;
	public StepThreeTimeTabbedPane(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		init();
		this.add("测试用例生成报告",abstractScroll);
		this.add("测试用例生成信息",testDataScroll);
	}

	private void init()
	{
		abstractSequence = new JPanel();
		abstractSequence.setLayout(new GridLayout(1, 1));
		testData = new JPanel();
		testData.setLayout(new GridBagLayout());
		testDataScroll = new JScrollPane(testData);
		
		abstractScroll = new JScrollPane(abstractSequence);
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
    	
    	testDataScroll.setBorder(null);
		JScrollBar HorizontalBar = testDataScroll.getHorizontalScrollBar();
		JScrollBar VerticalBar = testDataScroll.getVerticalScrollBar();
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
		
		abstractScroll.setBorder(null);
		JScrollBar HorizontalBar1 = abstractScroll.getHorizontalScrollBar();
		JScrollBar VerticalBar1 = abstractScroll.getVerticalScrollBar();
		HorizontalBar1.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
		});
		VerticalBar1.addAdjustmentListener(new AdjustmentListener() {			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
		});
    }
	public JScrollPane getTestDataScroll() {
		return testDataScroll;
	}
	
	public JScrollPane getAbstractScroll() {
		return abstractScroll;
	}

	public JPanel getAbstractSequence() {
		return abstractSequence;
	}
	public JPanel getTestData() {
		return testData;
	}
	public List<ScenceTabelPanel> getCaseValidationList() {
		return CaseValidationList;
	}
	
}

