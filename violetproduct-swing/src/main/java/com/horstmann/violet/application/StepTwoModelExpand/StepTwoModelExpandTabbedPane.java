package com.horstmann.violet.application.StepTwoModelExpand;

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

public class StepTwoModelExpandTabbedPane extends JTabbedPane {
	private MainFrame mainFrame;
	private JPanel ValidationResults;
	private JPanel modelExpandPanel;
	private List<ScenceTabelPanel> modelValidationList;
	private JScrollPane jScrollPane;
	private JScrollPane jScrollPane1;

	public StepTwoModelExpandTabbedPane(MainFrame mainFrame) {
		init();
		this.mainFrame = mainFrame;
		this.add("∆¿∑÷æÿ’Û", jScrollPane);
		this.add("”√¿˝«®“∆∏≈¬ ", jScrollPane1);
	}

	private void init() {
		ValidationResults = new JPanel();
		ValidationResults.setLayout(new GridLayout());
		modelExpandPanel = new JPanel();
		modelExpandPanel.setLayout(new GridBagLayout());
		jScrollPane = new JScrollPane(modelExpandPanel);
		jScrollPane.setBorder(null);
		jScrollPane1 = new JScrollPane(ValidationResults);
		jScrollPane1.setBorder(null);
		modelValidationList = new ArrayList<ScenceTabelPanel>();
		listen();
	}

	public void listen() {
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

	public JPanel getmodelExpandPanel() {
		return modelExpandPanel;
	}

	public List<ScenceTabelPanel> getModelValidationList() {
		return modelValidationList;
	}

}
