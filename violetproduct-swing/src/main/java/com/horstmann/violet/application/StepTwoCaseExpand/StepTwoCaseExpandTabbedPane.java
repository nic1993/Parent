package com.horstmann.violet.application.StepTwoCaseExpand;

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

public class StepTwoCaseExpandTabbedPane extends JTabbedPane{
	private MainFrame mainFrame;
	private JPanel ValidationResults;
	private JPanel CaseExpandPanel;
    private List<ScenceTabelPanel> CaseValidationList;
    private JScrollPane jScrollPane;
    private JScrollPane jScrollPane1;
	public StepTwoCaseExpandTabbedPane(MainFrame mainFrame)
	{
		init();
		this.mainFrame = mainFrame;
		this.add("同源评分矩阵",jScrollPane);
		this.add("场景执行概率",jScrollPane1);
	}

	private void init()
	{
		ValidationResults = new JPanel();
		ValidationResults.setLayout(new GridLayout());
		CaseExpandPanel = new JPanel();
		CaseExpandPanel.setLayout(new GridBagLayout());
		jScrollPane = new JScrollPane(CaseExpandPanel);
		jScrollPane.setBorder(null);
		jScrollPane1 = new JScrollPane(ValidationResults);
		jScrollPane1.setBorder(null);
		CaseValidationList = new ArrayList<ScenceTabelPanel>();
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
	    }
	public JPanel getValidationResults() {
		return ValidationResults;
	}

	public JPanel getCaseExpandPanel() {
		return CaseExpandPanel;
	}

	public List<ScenceTabelPanel> getCaseValidationList() {
		return CaseValidationList;
	}
	
}

