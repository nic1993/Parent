package com.horstmann.violet.application.StepThreeTestCase;

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

public class TimeExpandTabbedPane extends JTabbedPane{
	private JPanel expandResults;
	private JPanel exppandResport;
	 private JScrollPane jScrollPane;
	public TimeExpandTabbedPane()
	{
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
	}

	public JPanel getExpandResults() {
		return expandResults;
	}

	public JPanel getExpandResport() {
		return exppandResport;
	}
}

