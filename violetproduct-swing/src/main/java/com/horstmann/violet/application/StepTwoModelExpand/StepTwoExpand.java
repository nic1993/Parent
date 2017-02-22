package com.horstmann.violet.application.StepTwoModelExpand;

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
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.eclipse.draw2d.FlowLayout;

import com.horstmann.violet.application.SteponeBuildModel.StepOneChangePanelUI;
import com.horstmann.violet.application.SteponeBuildModel.UsecaseTreePanel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.menu.FileMenu;

public class StepTwoExpand extends JPanel{
	private JLabel expandModelLabel;
	private JLabel expandCaseModel;
	private JLabel estimateLabel;
	private JLabel exchangeLabel;
	
	private JPanel expandModelPanel;
	private JPanel expandCasePanel;
	private JPanel estimatepPanel;
	private JPanel exchangepPanel;
	
	private JRadionPanel jRadionPanel;
	private static JPanel bottompanel;
	private MainFrame mainFrame;
	private boolean usecase;
	public StepTwoExpand(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;	
		init();
		expandModelPanel.setVisible(false);
		expandCasePanel.setVisible(false);
		estimatepPanel.setVisible(false);
		exchangepPanel.setVisible(true);
		this.setLayout(new GridBagLayout());
		this.add(expandModelLabel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 10, 0, 150));
		this.add(expandModelPanel,new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 10, 0, 150));
		
		this.add(expandCaseModel,new GBC(0, 2).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 10, 0, 150));
		this.add(expandCasePanel,new GBC(0, 3).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 10, 0, 150));
		expandCasePanel.setLayout(new GridLayout(1, 1));
		expandCasePanel.add(mainFrame.getStepTwoCaseExpandTree());
		
		this.add(estimateLabel,new GBC(0, 4).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 10, 0, 150));
		this.add(estimatepPanel,new GBC(0, 5).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 10, 0, 150));
		
		this.add(exchangeLabel,new GBC(0, 6).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 10, 0, 150));
		this.add(exchangepPanel,new GBC(0, 7).setFill(GBC.BOTH).setWeight(1, 1).setInsets(0, 10, 0, 150));
		
		jRadionPanel = mainFrame.getjRadionPanel();
		expandModelPanel.setLayout(new GridBagLayout());
		expandModelPanel.add(jRadionPanel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 25, 0, 0));	
	}
	
//	private void setupUI()
//	{
//		setUI(this.getChangePanelUI());
//	}
	public void init()
	{
		this.setBackground(new Color(233,233,233));	
		expandModelLabel = new JLabel("Ä£ÐÍÀ©Õ¹");
		expandCaseModel = new JLabel("ÓÃÀýÀ©Õ¹");
		estimateLabel = new JLabel("Ä£ÐÍÆÀ¹À");
		exchangeLabel = new JLabel("Markov×ª»»");
		
		expandModelLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
		expandCaseModel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
		estimateLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
		exchangeLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));

		expandModelPanel = new JPanel();
		expandCasePanel = new JPanel();
		estimatepPanel = new JPanel();
		exchangepPanel = new JPanel();
		
		expandModelPanel.setBackground(new Color(233,233,233));
		expandCasePanel.setBackground(new Color(233,233,233));
		estimatepPanel.setBackground(new Color(233,233,233));
		exchangepPanel.setBackground(new Color(233,233,233));
		
		mainFrame.getNameList().add("Primary");
		labelListen();
	}
	
	private void labelListen()
	{
		expandModelLabel.addMouseListener(new MouseAdapter(){	
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				expandModelLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 18));
				expandCaseModel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
				estimateLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
				exchangeLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
				
				mainFrame.getsteponebottmopanel().getNextbutton().doClick();
				if(expandModelPanel.isVisible())
				expandModelPanel.setVisible(false);
				else {
					expandModelPanel.setVisible(true);
				}
			}
		});
		expandCaseModel.addMouseListener(new MouseAdapter(){	
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				expandModelLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
				expandCaseModel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 18));
				estimateLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
				exchangeLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
				
				if(expandModelPanel.isVisible())
					expandCasePanel.setVisible(false);
					else {
						expandCasePanel.setVisible(true);
					}
				expandCasePanel.updateUI();
				clearPanel();
				mainFrame.getpanel().add(mainFrame.getStepTwoCaseOperation());
				mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoCaseExpandTabbedPane());
				mainFrame.getpanel().updateUI();
			}
		});
		estimateLabel.addMouseListener(new MouseAdapter(){	
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				expandModelLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
				expandCaseModel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
				estimateLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 18));
				exchangeLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
				
				clearPanel();
				mainFrame.getpanel().add(mainFrame.getStepTwoEvaluateOperation());
				mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoEvaluateTabbedPane());
				mainFrame.getpanel().updateUI();
			}
		});
		exchangeLabel.addMouseListener(new MouseAdapter(){	
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				expandModelLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
				expandCaseModel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
				estimateLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
				exchangeLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 18));	
				clearPanel();
    	        mainFrame.getpanel().add(mainFrame.getStepTwoExchangeOperation());  
    	        mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoExchangeTabbedPane());
			}
		});
	}

	public void clearPanel()
	{
		mainFrame.getpanel().removeAll();
		mainFrame.getCenterTabPanel().removeAll();
	}
}
