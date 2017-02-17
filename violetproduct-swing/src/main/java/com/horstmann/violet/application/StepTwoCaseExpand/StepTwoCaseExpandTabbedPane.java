package com.horstmann.violet.application.StepTwoCaseExpand;

import java.awt.BasicStroke;
import java.awt.Color;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import com.horstmann.violet.application.StepTwoModelExpand.ScenceTabelPanel;
import com.horstmann.violet.workspace.IWorkspace;

public class StepTwoCaseExpandTabbedPane extends JTabbedPane{
	private JPanel ValidationResults;
	private JPanel CaseExpandPanel;
    private List<ScenceTabelPanel> CaseValidationList;
    private JScrollPane jScrollPane;
	public StepTwoCaseExpandTabbedPane()
	{
		init();
		this.add("验证报告",ValidationResults);
		this.add("用例扩展矩阵",jScrollPane);
	}

	private void init()
	{
		ValidationResults = new JPanel();
		ValidationResults.setLayout(new GridLayout(1, 1));
		CaseExpandPanel = new JPanel();
		CaseExpandPanel.setLayout(new GridBagLayout());
		jScrollPane = new JScrollPane(CaseExpandPanel);
		CaseValidationList = new ArrayList<ScenceTabelPanel>();
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

