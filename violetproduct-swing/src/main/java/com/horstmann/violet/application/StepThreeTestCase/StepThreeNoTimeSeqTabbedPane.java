package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
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
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.workspace.IWorkspace;

public class StepThreeNoTimeSeqTabbedPane extends JTabbedPane{
	private JPanel totalPanel;
	private CaseTableHeaderPanel caseTableHeaderPanel;
	private JPanel abstractSequence; 
    private List<ScenceTabelPanel> CaseValidationList;
    private  JScrollPane abstractScroll;
    private MainFrame mainFrame;
	public StepThreeNoTimeSeqTabbedPane(MainFrame mainFrame)
	{   
		this.mainFrame = mainFrame;
		init();
		this.add("�����������",totalPanel);
	}

	private void init()
	{
		caseTableHeaderPanel = new CaseTableHeaderPanel();
		
		abstractSequence = new JPanel();
		abstractSequence.setLayout(new GridLayout());
		abstractScroll = new JScrollPane(abstractSequence);
		
		totalPanel = new JPanel();
		totalPanel.setLayout(new BorderLayout());
		totalPanel.add(caseTableHeaderPanel,BorderLayout.NORTH);
		totalPanel.add(abstractSequence, BorderLayout.CENTER);
		
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
	
	public JScrollPane getAbstractScroll() {
		return abstractScroll;
	}
	public JPanel getAbstractSequence() {
		return abstractSequence;
	}
	public List<ScenceTabelPanel> getCaseValidationList() {
		return CaseValidationList;
	}
	
}

