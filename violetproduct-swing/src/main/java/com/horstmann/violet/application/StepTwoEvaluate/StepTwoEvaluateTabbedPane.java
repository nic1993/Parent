package com.horstmann.violet.application.StepTwoEvaluate;

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

import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.workspace.IWorkspace;

public class StepTwoEvaluateTabbedPane extends JTabbedPane{
	private MainFrame mainFrame;
	private JPanel HomogeneityResults;
	private JPanel CertaintyResults;
	private JPanel AccessibilityResults;
    private JScrollPane HomogeneityScroll;
    private JScrollPane CertaintyScroll;
    private JScrollPane AccessibilityScroll;
    
	public StepTwoEvaluateTabbedPane(MainFrame mainFrame)
	{
		init();
		this.mainFrame = mainFrame;
		this.add("归一性验证",HomogeneityScroll);
		this.add("确定性验证",CertaintyScroll);
		this.add("可达性验证",AccessibilityScroll);
	}

	private void init()
	{
		HomogeneityResults = new JPanel();
		HomogeneityResults.setLayout(new GridLayout());
		HomogeneityScroll = new JScrollPane(HomogeneityResults);
		
		CertaintyResults = new JPanel();
		CertaintyResults.setLayout(new GridLayout());
		CertaintyScroll = new JScrollPane(CertaintyResults);
		
		AccessibilityResults = new JPanel();
		AccessibilityResults.setLayout(new GridLayout());
		AccessibilityScroll = new JScrollPane(AccessibilityResults);
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
		    	
		    	JScrollBar HorizontalBar = HomogeneityScroll.getHorizontalScrollBar();
				JScrollBar VerticalBar = HomogeneityScroll.getVerticalScrollBar();
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
		    	
				JScrollBar HorizontalBar1 = CertaintyScroll.getHorizontalScrollBar();
				JScrollBar VerticalBar1 = CertaintyScroll.getVerticalScrollBar();
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
				
				JScrollBar HorizontalBar2 = AccessibilityScroll.getHorizontalScrollBar();
				JScrollBar VerticalBar2 = AccessibilityScroll.getVerticalScrollBar();
				HorizontalBar2.addAdjustmentListener(new AdjustmentListener() {
					@Override
					public void adjustmentValueChanged(AdjustmentEvent e) {
						// TODO Auto-generated method stub
						mainFrame.renewPanel();
					}
				});
				VerticalBar2.addAdjustmentListener(new AdjustmentListener() {			
					@Override
					public void adjustmentValueChanged(AdjustmentEvent e) {
						// TODO Auto-generated method stub
						mainFrame.renewPanel();
					}
				});
		    }
	public JPanel getHomogeneityResults(){
		return this.HomogeneityResults;
	}

	public JPanel getCertaintyResults() {
		return CertaintyResults;
	}

	public JPanel getAccessibilityResults() {
		return AccessibilityResults;
	}

	public JScrollPane getHomogeneityScroll() {
		return HomogeneityScroll;
	}

	public JScrollPane getCertaintyScroll() {
		return CertaintyScroll;
	}

	public JScrollPane getAccessibilityScroll() {
		return AccessibilityScroll;
	}
	
}

