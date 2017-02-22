package com.horstmann.violet.application.SteponeBuildModel;

import java.awt.Color;
import java.awt.Cursor;
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

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.eclipse.draw2d.FlowLayout;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.menu.FileMenu;

public class StepOneButton extends JPanel{
	private JLabel stepLabel;
	private SequenceTreePanel SequenceJtree;
	private UsecaseTreePanel UseCaseJtree;
    private JPanel panel;
    
	private FileMenu fileMenu;
	private StepOneChangePanelUI stepOneChangePanelUI;
	private MainFrame mainFrame;
	
	private boolean sequence;
	private boolean usecase;
	public StepOneButton(MainFrame mainFrame,FileMenu fileMenu)
	{
		this.mainFrame = mainFrame;
		this.fileMenu = fileMenu;	
		init();
		getUseCaseJtree().setVisible(false);
		getSequenceJtree().setVisible(false);
		this.setLayout(new GridBagLayout());
		this.add(stepLabel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 10, 10, 150));
		this.add(getUseCaseJtree(), new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 10, 0, 150));
		this.add(getSequenceJtree(), new GBC(0, 2).setFill(GBC.BOTH).setWeight(1, 1).setInsets(10, 10, 0, 150));
		this.add(panel, new GBC(0, 3).setFill(GBC.BOTH).setWeight(1, 1));
	}
	private void setupUI()
	{
		setUI(this.getStepOneChangePanelUI());
	}
	public void init()
	{
		stepLabel = new JLabel("½¨Á¢UMLÄ£ÐÍ");
		stepLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
		panel = new JPanel();
		initButton();
		this.setBackground(new Color(233,233,233));
	}
	public void initButton()
	{
		//×é¼þ³õÊ¼»¯
		SequenceJtree = new SequenceTreePanel(mainFrame.getMenuFactory().getFileMenu(mainFrame), mainFrame);
		UseCaseJtree = new UsecaseTreePanel(mainFrame.getMenuFactory().getFileMenu(mainFrame), mainFrame);
		stepLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				stepLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
				changeVisible();
			}
		});
 
	}
	private StepOneChangePanelUI getStepOneChangePanelUI()
	{
		if(this.stepOneChangePanelUI == null)
			stepOneChangePanelUI = new StepOneChangePanelUI(this,mainFrame);
		return stepOneChangePanelUI;
	}
	private void changeVisible()
	{
		if(getSequenceJtree().isVisible()){
		getSequenceJtree().setVisible(false);
		getUseCaseJtree().setVisible(false);
		stepLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
		this.updateUI();
		}
		else {
			getSequenceJtree().setVisible(true);
			getUseCaseJtree().setVisible(true);
			stepLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 18));
			this.updateUI();
		}
	}
	private void clearCenterTabPanel()
	{
		mainFrame.getCenterTabPanel().removeAll();		
	}
	public SequenceTreePanel getSequenceJtree() {
		return mainFrame.getsequencetree();
	}
	public UsecaseTreePanel getUseCaseJtree() {
		return mainFrame.getUsecaseTree();
	}

}
