package com.horstmann.violet.application.StepOneBuildModel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
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
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.eclipse.draw2d.FlowLayout;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.menu.FileMenu;
import com.horstmann.violet.workspace.IWorkspace;

public class StepOneButton extends JPanel{
	private JLabel bulitModel;
	private JPanel treePanel;
	
	private JLabel expandModelLabel;
	private JPanel expandModelPanel;
	
	private JLabel expandCaseModel;
	private JPanel expandCasePanel;
	
	private JRadionPanel jRadionPanel;
	private SequenceTreePanel SequenceJtree;
	private UsecaseTreePanel UseCaseJtree;
    private JPanel panel;   
	private FileMenu fileMenu;
	private MainFrame mainFrame;	
	private JScrollPane jScrollPane;
	private String model_Name;
	
	private boolean isSameName;
	private boolean sequence;
	private boolean usecase;
	
	private int currentStep = 1;
	
	public StepOneButton(MainFrame mainFrame,FileMenu fileMenu)
	{
		this.mainFrame = mainFrame;
		this.fileMenu = fileMenu;	
		init();
		this.setLayout(new GridBagLayout());
		this.add(bulitModel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 10, 0, 0));
		this.add(treePanel, new GBC(0, 1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 10, 0, 0));
		
		this.add(expandModelLabel, new GBC(0, 2).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 10, 0, 0));
		this.add(expandModelPanel, new GBC(0, 3).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 10, 0, 0));
		
		this.add(expandCaseModel, new GBC(0, 4).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 10, 0, 0));
		this.add(expandCasePanel, new GBC(0, 5).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 10, 0, 0));
		
		this.add(panel, new GBC(0, 6).setFill(GBC.BOTH).setWeight(0, 1));
		
	}
	public void init()
	{
		bulitModel = new JLabel("½¨Á¢UMLÄ£ÐÍ");
		expandModelLabel = new JLabel("ÓÃÀýÀ©Õ¹");
		expandCaseModel = new JLabel("³¡¾°À©Õ¹");
		
		bulitModel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 18));
		expandModelLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
		expandCaseModel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
		
		treePanel = new JPanel();
		expandModelPanel = new JPanel();
		expandCasePanel = new JPanel();
		panel = new JPanel();
		
		treePanel.setLayout(new GridBagLayout());
		expandModelPanel.setLayout(new GridBagLayout());
		expandCasePanel.setLayout(new GridBagLayout());
		
		jRadionPanel = mainFrame.getjRadionPanel();
		
//		SequenceJtree = new SequenceTreePanel(mainFrame.getMenuFactory().getFileMenu(mainFrame), mainFrame);
//		UseCaseJtree = new UsecaseTreePanel(mainFrame.getMenuFactory().getFileMenu(mainFrame), mainFrame);
//		treePanel.add(getUseCaseJtree(),new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 0, 0, 0));
//		treePanel.add(getSequenceJtree(),new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 0, 0, 0));
		
		expandCaseModel.setEnabled(false);
		expandModelPanel.setVisible(false);
		expandCasePanel.setVisible(false);
		
		this.setBackground(new Color(233,233,233));
		jScrollPane = new JScrollPane(this);
		jScrollPane.setBorder(null);
		JScrollBar HorizontalBar = jScrollPane.getHorizontalScrollBar();
		JScrollBar VerticalBar = jScrollPane.getVerticalScrollBar();
		HorizontalBar.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
		});
		VerticalBar.addAdjustmentListener(new AdjustmentListener() {			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
		});
		listen();
	}
	public void listen()
	{
		bulitModel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(((JLabel)e.getSource()).isEnabled())
				{
					expandModelLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
					expandCaseModel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
					bulitModel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 18));
					
					expandModelPanel.setVisible(false);
					expandCasePanel.setVisible(false);
					treePanel.setVisible(true);
					
					clearPanel();
					mainFrame.getpanel().add(mainFrame.getStepOperationButton());
					mainFrame.renewPanel();
					
					currentStep = 1;
				}
			}
		});
		expandModelLabel.addMouseListener(new MouseAdapter(){	
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(((JLabel)e.getSource()).isEnabled())
				{
					expandModelLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 18));
					expandCaseModel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
					bulitModel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
					
					expandModelPanel.setVisible(true);
					expandCasePanel.setVisible(false);
					treePanel.setVisible(false);
                    
					jRadionPanel.initFile();
					expandModelPanel.removeAll();
					expandModelPanel.add(jRadionPanel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 25, 0, 0));
					expandModelPanel.repaint();
					mainFrame.renewPanel();
					
					clearPanel();
					System.out.println(jRadionPanel.getRadios().size());
					if(jRadionPanel.getRadios().size() == 0)
					{
						mainFrame.getStepTwoModelOperation().getLabel().removeAll();
						mainFrame.getStepTwoModelOperation().getLabel().setText("µ±Ç°ÎÞ¿ÉÑ¡ÔñµÄÄ£ÐÍ!");
					}else {
						mainFrame.getStepTwoModelOperation().getLabel().removeAll();
						mainFrame.getStepTwoModelOperation().getLabel().setText("ÇëÑ¡ÔñÐèÒªÓÃÀýÀ©Õ¹µÄÄ£ÐÍ!");
					}
					
					mainFrame.getpanel().add(mainFrame.getStepTwoModelOperation());
					mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoModelExpandTabbedPane());
					mainFrame.renewPanel();
					
					currentStep = 2;
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
		});
		expandCaseModel.addMouseListener(new MouseAdapter(){	
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(((JLabel)e.getSource()).isEnabled())
				{
					expandModelLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
					expandCaseModel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 18));
					bulitModel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
					
					expandModelPanel.setVisible(false);
					expandCasePanel.setVisible(true);
					treePanel.setVisible(false);
					
					clearPanel();
					mainFrame.getStepTwoCaseOperation().getToplabel().removeAll();
					mainFrame.getStepTwoCaseOperation().getToplabel().setText("µ±Ç°Ä£ÐÍÎª: " + mainFrame.getStepTwoCaseOperation().getModel_Name());
					mainFrame.getpanel().add(mainFrame.getStepTwoCaseOperation());
					mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoCaseExpandTabbedPane());
					mainFrame.getpanel().updateUI();
					mainFrame.renewPanel();
					
					currentStep = 3;
				}
			}
		});
	}
	  private void isSanmeName(){
	    	if(model_Name == null){
	    		model_Name = mainFrame.getjRadionPanel().getSelectName();
	    		isSameName = false;
	    	}	
			else if (mainFrame.getjRadionPanel().getSelectName().equals(model_Name)) {
				model_Name = mainFrame.getjRadionPanel().getSelectName();
				isSameName = true;
			}
			else {
				isSameName = false;
			}
	    }
	public void clearPanel()
	{	
		mainFrame.getpanel().removeAll();
		mainFrame.getCenterTabPanel().removeAll();
		
	}
	public JPanel getTreePanel() {
		return treePanel;
	}
	public JLabel getExpandModelLabel() {
		return expandModelLabel;
	}
	public JPanel getExpandModelPanel() {
		return expandModelPanel;
	}
	public JPanel getExpandCasePanel() {
		return expandCasePanel;
	}
	public JLabel getExpandCaseModel() {
		return expandCaseModel;
	}
	public int getCurrentStep() {
		return currentStep;
	}
}
