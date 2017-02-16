package com.horstmann.violet.application.StepTwoExchange;

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
import javax.swing.JTabbedPane;

import com.horstmann.violet.workspace.IWorkspace;

public class StepTwoExchangeTabbedPane extends JTabbedPane{
	private JPanel exchangeResults;
	private JPanel exchangeResport;

	public StepTwoExchangeTabbedPane()
	{
		init();
		this.add("Markov",exchangeResults);
		this.add("Markov文件结构",exchangeResport);
	}

	private void init()
	{
		exchangeResults = new JPanel();
		exchangeResults.setLayout(new GridLayout(1, 1));
		exchangeResport = new JPanel();
		exchangeResport.setLayout(new GridLayout(1, 1));
	}

	public JPanel getExchangeResults() {
		return exchangeResults;
	}

	public JPanel getExchangeResport() {
		return exchangeResport;
	}
}

