package com.horstmann.violet.application.StepTwoEvaluate;

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

public class StepTwoEvaluateTabbedPane extends JTabbedPane{
	private JPanel EvaluateResults;
    private JScrollPane jScrollPane;
	public StepTwoEvaluateTabbedPane()
	{
		init();
		this.add("ÆÀ¹À±¨¸æ",jScrollPane);
	}

	private void init()
	{
		EvaluateResults = new JPanel();
		EvaluateResults.setLayout(new GridBagLayout());
		jScrollPane = new JScrollPane(EvaluateResults);
	}

	public JPanel getEvaluateResults() {
		return EvaluateResults;
	}
}

