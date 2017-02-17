package com.horstmann.violet.application.StepTwoModelExpand;

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

import com.horstmann.violet.workspace.IWorkspace;

public class StepTwoModelExpandTabbedPane extends JTabbedPane{
	private JPanel ValidationResults;
	private JPanel modelExpandPanel;
    private List<ScenceTabelPanel> modelValidationList;
    private JScrollPane jScrollPane;
	public StepTwoModelExpandTabbedPane()
	{
		init();
		this.add("验证报告",ValidationResults);
		this.add("用例模型扩展矩阵",jScrollPane);
	}

	private void init()
	{
		ValidationResults = new JPanel();
		ValidationResults.setLayout(new GridLayout(1, 1));
		modelExpandPanel = new JPanel();
		modelExpandPanel.setLayout(new GridBagLayout());
		jScrollPane = new JScrollPane(modelExpandPanel);
		modelValidationList = new ArrayList<ScenceTabelPanel>();
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

