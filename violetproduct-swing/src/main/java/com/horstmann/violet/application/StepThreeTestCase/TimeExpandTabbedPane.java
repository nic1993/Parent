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

import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.workspace.IWorkspace;

public class TimeExpandTabbedPane extends JTabbedPane{
	private JPanel expandResults;
	private JPanel exppandResport;
	private MainFrame mainFrame;
	 private JScrollPane jScrollPane;
	public TimeExpandTabbedPane(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		init();
		this.add("Markov转换模型",expandResults);
		this.add("Markov转换信息",jScrollPane);
	}

	private void init()
	{
		expandResults = new JPanel();
		expandResults.setLayout(new GridLayout(1, 1));
		exppandResport = new JPanel();
		exppandResport.setLayout(new GridLayout(1, 1));
		jScrollPane = new JScrollPane(exppandResport);
		
		list();
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
		JScrollBar HorizontalBar1 = jScrollPane.getHorizontalScrollBar();
		JScrollBar VerticalBar1 = jScrollPane.getVerticalScrollBar();
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
	public JPanel getExpandResults() {
		return expandResults;
	}

	public JPanel getExpandResport() {
		return exppandResport;
	}
}

