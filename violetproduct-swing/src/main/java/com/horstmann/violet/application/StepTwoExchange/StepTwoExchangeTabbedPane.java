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
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.workspace.IWorkspace;

public class StepTwoExchangeTabbedPane extends JTabbedPane{
	private MainFrame mainFrame;
	private JPanel exchangeResults;
	private JPanel exchangeResport;
   
	public StepTwoExchangeTabbedPane(MainFrame mainFrame)
	{
		init();
		this.mainFrame = mainFrame;
		this.add("Markov",exchangeResults);
		this.add("Markov文件结构",exchangeResport);
	}

	private void init()
	{
		exchangeResults = new JPanel();
		exchangeResults.setLayout(new GridLayout(1, 1));
		exchangeResport = new JPanel();
		exchangeResport.setLayout(new GridLayout(1, 1));
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
    }
	public JPanel getExchangeResults() {
		return exchangeResults;
	}

	public JPanel getExchangeResport() {
		return exchangeResport;
	}
}

