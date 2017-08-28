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

public class StepThreeNoTimeCustomTabbedPane extends JTabbedPane{
	private JPanel abstractSequence;
	private JPanel testData;
	private JPanel testRoute;
    private List<ScenceTabelPanel> CaseValidationList;
    
    private JScrollPane testDataScroll;
    private  JScrollPane AbstractScroll;
    private  JScrollPane testRouteScroll;
    private MainFrame mainFrame;
	public StepThreeNoTimeCustomTabbedPane(MainFrame mainFrame)
	{   
		this.mainFrame = mainFrame;
		init();
		
		this.add("≥ÈœÛ≤‚ ‘–Ú¡–",AbstractScroll);
		this.add("≤‚ ‘ ˝æ›",testDataScroll);
		this.add("≤‚ ‘¬∑æ∂",testRouteScroll);
	}

	private void init()
	{
		abstractSequence = new JPanel();
		testData = new JPanel();
		testRoute = new JPanel();
		testRoute.setLayout(new GridLayout(1, 1));
		testDataScroll = new JScrollPane(testData);
		testDataScroll.getVerticalScrollBar().setValue(testDataScroll.getVerticalScrollBar().getMaximum());
		AbstractScroll = new JScrollPane(abstractSequence);
		testRouteScroll = new JScrollPane(testRoute);
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
		
		AbstractScroll.setBorder(null);
		JScrollBar HorizontalBar1 = AbstractScroll.getHorizontalScrollBar();
		JScrollBar VerticalBar1 = AbstractScroll.getVerticalScrollBar();
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
		
		testRouteScroll.setBorder(null);
		JScrollBar HorizontalBar2 = testRouteScroll.getHorizontalScrollBar();
		JScrollBar VerticalBar2 = testRouteScroll.getVerticalScrollBar();
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
	
	public JScrollPane getTestDataScroll() {
		return testDataScroll;
	}
	public JScrollPane getAbstractScroll() {
		return AbstractScroll;
	}
	public JPanel getAbstractSequence() {
		return abstractSequence;
	}
	public JPanel getTestData() {
		return testData;
	}
	public JPanel getTestRoute() {
		return testRoute;
	}

	public List<ScenceTabelPanel> getCaseValidationList() {
		return CaseValidationList;
	}
	
}

