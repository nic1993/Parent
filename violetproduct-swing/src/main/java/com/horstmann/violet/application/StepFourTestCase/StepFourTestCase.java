package com.horstmann.violet.application.StepFourTestCase;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.eclipse.draw2d.FlowLayout;


import com.horstmann.violet.application.StepOneBuildModel.UsecaseTreePanel;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.menu.FileMenu;

public class StepFourTestCase extends JPanel{

	private JLabel testCaseLabel;	
	private JPanel testCasePanel;	
	private NameRadionPanel jRadionPanel;
	private JScrollPane scrollPane;
	private static JPanel bottompanel;
	private MainFrame mainFrame;
	public StepFourTestCase(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;	
		init();
		this.setLayout(new GridBagLayout());
		this.add(testCaseLabel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(15, 10, 0, 0));
		this.add(testCasePanel,new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 10, 0, 0));	
		this.add(new JPanel(),new GBC(0, 2).setFill(GBC.BOTH).setWeight(1, 1).setInsets(0, 10, 0, 0));	
	}
	public void init()
	{
		this.setBackground(new Color(233,233,233));	
		testCaseLabel = new JLabel("≤‚ ‘”√¿˝—È÷§");
     	testCaseLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 18));
		testCasePanel = new JPanel();		
		testCasePanel.setBackground(new Color(233,233,233));	
		
		jRadionPanel = mainFrame.getNameRadionPanel();
		scrollPane = new JScrollPane(jRadionPanel);
		scrollPane.setBorder(null);
		
		testCasePanel.setLayout(new GridBagLayout());
		testCasePanel.add(scrollPane, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 15, 0, 0));
		
	}
	public void clearPanel()
	{
		mainFrame.getpanel().removeAll();
		mainFrame.getCenterTabPanel().removeAll();
	}
    
	public JLabel getExpandModelLabel() {
		return testCaseLabel;
	}	
}
