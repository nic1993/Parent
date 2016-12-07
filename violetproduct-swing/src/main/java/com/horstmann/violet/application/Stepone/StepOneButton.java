package com.horstmann.violet.application.Stepone;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.menu.FileMenu;

public class StepOneButton extends JPanel{
	private SequenceTreePanel SequenceJtree;
	private UsecaseTreePanel UseCaseJtree;
	private static JPanel bottompanel;
	private FileMenu fileMenu;
	private ChangePanelUI changePanelUI;
	private MainFrame mainFrame;
	
	private boolean sequence;
	private boolean usecase;
	public StepOneButton(MainFrame mainFrame,FileMenu fileMenu)
	{
		this.mainFrame = mainFrame;
		this.fileMenu = fileMenu;	
		init();
		setupUI();
	}
	private void setupUI()
	{
		setUI(this.getChangePanelUI());
	}
	public void init()
	{
		initButton();
		this.setBackground(new Color(233,233,233));
//		GridBagLayout layout=new GridBagLayout();
//		this.setLayout(layout);
//		layout.setConstraints(SequenceButton, new GBC(0, 0, 1, 1).setFill(GBC.EAST).setWeight(1, 0).setInsets(10, 0, 10, 150));
//		layout.setConstraints(UseCaseButton, new GBC(0, 1, 1, 1).setFill(GBC.EAST).setWeight(1, 0).setInsets(0, 0, 10, 150));
//		layout.setConstraints(bottompanel, new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 1));
//		this.add(SequenceButton);
//		this.add(UseCaseButton);
//		this.add(bottompanel);
		leftButton1Listen();
			
	}
	public void initButton()
	{
		//组件初始化
		bottompanel = new JPanel();
		bottompanel.setBackground(new Color(233,233,233));
		SequenceJtree = new SequenceTreePanel(mainFrame.getMenuFactory().getFileMenu(mainFrame), mainFrame);
		UseCaseJtree = new UsecaseTreePanel(mainFrame.getMenuFactory().getFileMenu(mainFrame), mainFrame);
//		Icon sequenceIcon = new ImageIcon("resources/icons/72x72/sequencepicture.png");
//		SequenceButton.setIcon(sequenceIcon);
//		SequenceButton.setHorizontalTextPosition(SwingConstants.CENTER);
//		SequenceButton.setVerticalTextPosition(SwingConstants.BOTTOM);
//		SequenceButton.setFocusPainted(false);
//		//saveButton.setSize(icon.getIconHeight(),icon.getIconWidth());
//		SequenceButton.setMargin(new Insets(0, 0, 0, 0));
//		SequenceButton.setContentAreaFilled(false);
//		SequenceButton.setFocusable(false);
//		SequenceButton.setBorderPainted(false);
		
		
//		Icon useCaseIcon = new ImageIcon("resources/icons/72x72/usecasepicture.png");
//		UseCaseButton.setIcon(useCaseIcon);
//		UseCaseButton.setHorizontalTextPosition(SwingConstants.CENTER);
//		UseCaseButton.setVerticalTextPosition(SwingConstants.BOTTOM);
//		UseCaseButton.setFocusPainted(false);
//		//saveButton.setSize(icon.getIconHeight(),icon.getIconWidth());
//		UseCaseButton.setMargin(new Insets(0, 0, 0, 0));
//		UseCaseButton.setContentAreaFilled(false);
//		UseCaseButton.setFocusable(false);
//		UseCaseButton.setBorderPainted(false);

//		Icon icon = new ImageIcon("resources/icons/72x72/StepDisabled.png");
//		NextButton.setIcon(icon);
//		NextButton.setHorizontalTextPosition(SwingConstants.CENTER);
//		NextButton.setVerticalTextPosition(SwingConstants.BOTTOM);
//		NextButton.setFocusPainted(false);
//		//saveButton.setSize(icon.getIconHeight(),icon.getIconWidth());
//		NextButton.setMargin(new Insets(0, 0, 0, 0));
//		NextButton.setContentAreaFilled(false);
//		NextButton.setFocusable(false);
//		NextButton.setBorderPainted(false);
//		NextButton.addMouseListener(new MouseListener() {
//			
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				// TODO Auto-generated method stub
//				Icon icon = new ImageIcon("resources/icons/72x72/StepDisabled.png");
//				NextButton.setIcon(icon);
//				NextButton.updateUI();
//			}
//			
//			@Override
//			public void mousePressed(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void mouseExited(MouseEvent e) {
//				// TODO Auto-generated method stub
//				Icon icon = new ImageIcon("resources/icons/72x72/StepDisabled.png");
//				NextButton.setIcon(icon);
//				NextButton.updateUI();
//			}
//			
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				// TODO Auto-generated method stub
//				Icon icon = new ImageIcon("resources/icons/72x72/StepBlue.png");
//				NextButton.setIcon(icon);
//				NextButton.setHorizontalTextPosition(SwingConstants.CENTER);
//				NextButton.setVerticalTextPosition(SwingConstants.BOTTOM);
//				NextButton.setFocusPainted(false);
//				//saveButton.setSize(icon.getIconHeight(),icon.getIconWidth());
//				NextButton.setMargin(new Insets(0, 0, 0, 0));
//				NextButton.setContentAreaFilled(false);
//				NextButton.setFocusable(false);
//				NextButton.setBorderPainted(false);
//				NextButton.updateUI();
//			}
//			
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		
	}
	public void leftButton1Listen()
	{
//		SequenceJtree.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				wakeupPanel();
//				mainFrame.getCenterTabPanel().removeAll();
//				mainFrame.getCenterTabPanel().add(mainFrame.getStepOneCenterSequenceTabbedPane());
//				mainFrame.getCenterTabPanel().updateUI();
//				
//				mainFrame.getpanel().removeAll();
//				mainFrame.getpanel().setLayout(new GridLayout(1, 1));
//				mainFrame.getpanel().add(mainFrame.getOperationButton());
//				mainFrame.getpanel().updateUI();
//				
//				mainFrame.getinformationPanel().removeAll();
//				mainFrame.getinformationPanel().setLayout(new GridLayout(1, 1));
//				mainFrame.getinformationPanel().add(mainFrame.getouOutputinformation());
//				
//				mainFrame.getReduceOrEnlargePanel().setLayout(new GridLayout(1, 1));
//				mainFrame.getReduceOrEnlargePanel().add(mainFrame.getstepOneCenterRightPanel());
//				
////				if(bottompanel.getComponentCount() == 0){
////				bottompanel.setLayout(new GridLayout(1, 1));
////				bottompanel.add(mainFrame.getProjectTree());
////				bottompanel.updateUI();
////				}
////				if (bottompanel.getComponentCount() != 0) {
////					if (!bottompanel.getComponent(0).equals(mainFrame.getProjectTree())) {
////						bottompanel.removeAll();
////						bottompanel.setLayout(new GridLayout(1, 1));
////						bottompanel.add(mainFrame.getProjectTree());
////						bottompanel.updateUI();
////					}
////				}
//			}
//		});
//		UseCaseJtree.addActionListener(new ActionListener() {			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				wakeupPanel();
//				mainFrame.getCenterTabPanel().removeAll();
//				mainFrame.getCenterTabPanel().add(mainFrame.getStepOneCenterUseCaseTabbedPane());
//				mainFrame.getCenterTabPanel().updateUI();
//				
//				mainFrame.getpanel().removeAll();
//				mainFrame.getpanel().setLayout(new GridLayout(1, 1));
//				mainFrame.getpanel().add(mainFrame.getOperationButton());
//				mainFrame.getpanel().updateUI();
//				
//				mainFrame.getinformationPanel().removeAll();
//				mainFrame.getinformationPanel().setLayout(new GridLayout(1, 1));
//				mainFrame.getinformationPanel().add(mainFrame.getouOutputinformation());
//				
////				if(bottompanel.getComponentCount() == 0){
////					bottompanel.setLayout(new GridLayout(1, 1));
////					bottompanel.add(mainFrame.getProjectTree());
////					bottompanel.updateUI();
////					}
////					if (bottompanel.getComponentCount() != 0) {
////						if (bottompanel.getComponent(0).equals(mainFrame.getProjectTree())) {
////							bottompanel.removeAll();
////							bottompanel.setLayout(new GridLayout(1, 1));
////							bottompanel.add(mainFrame.getProjectTree());
////							bottompanel.updateUI();
////							System.out.println(1);
////						}
////					}
//			}
//		});
 
	}
	private ChangePanelUI getChangePanelUI()
	{
		if(this.changePanelUI == null)
			changePanelUI = new ChangePanelUI(this,mainFrame);
		return changePanelUI;
	}
	private void wakeupPanel()
	{
		mainFrame.getpanel().setVisible(true);
		mainFrame.getinformationPanel().setVisible(true);
		mainFrame.getbotoomJSplitPane().setDividerLocation(0.7);
		mainFrame.getbotoomJSplitPane().setDividerSize(4);
		mainFrame.getReduceOrEnlargePanel().setVisible(true);
//		if(mainFrame.getsteponeButton().getbottompanel().getComponentCount() != 0)
//			mainFrame.getsteponeButton().getbottompanel().getComponent(0).setVisible(true);
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
	public static JPanel getbottompanel() {
		return bottompanel;
	}
}
