package com.horstmann.violet.application.StepTwoExpand;

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

import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.workspace.IWorkspace;

public class StepTwoVerificationTabbedPane extends JTabbedPane{
	private JPanel ValidationResults;
	private JPanel VerificationResport;
    private List<ScenceTabelPanel> ValidationList;

    private MainFrame mainFrame;
	public StepTwoVerificationTabbedPane(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		init();
		this.add("∆¿π¿æÿ’Û",ValidationResults);
		this.add("∆¿π¿±®∏Ê",VerificationResport);
	}

	private void init()
	{
		ValidationList = new ArrayList<ScenceTabelPanel>();
		ValidationResults = new JPanel();
		ValidationResults.setLayout(new GridLayout(1, 1));
		VerificationResport = new JPanel();
		VerificationResport.setLayout(new GridLayout(1, 1));
	}


	public List<ScenceTabelPanel> getValidationList() {
		return ValidationList;
	}
	public JPanel getValidationResults() {
		return ValidationResults;
	}
	public JPanel getVerificationResport() {
		return VerificationResport;
	}
	
}

