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

import com.horstmann.violet.application.StepOneBuildModel.JRadionPanel;
import com.horstmann.violet.application.StepOneBuildModel.UsecaseTreePanel;
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
	private boolean isSameName;
	private String model_Name;
	public StepTwoExpand(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;	
		init();
		this.setLayout(new GridBagLayout());
		
		this.add(estimateLabel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(15, 10, 0, 0));
		this.add(estimatepPanel,new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 10, 0, 0));
		estimatepPanel.setLayout(new GridBagLayout());

		this.add(exchangeLabel,new GBC(0, 2).setFill(GBC.BOTH).setWeight(1, 0).setInsets(15, 10, 0, 0));
		this.add(exchangepPanel,new GBC(0, 3).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 10, 0, 0));
		exchangepPanel.setLayout(new GridBagLayout());
		
		this.add(new JPanel(),new GBC(0, 4).setFill(GBC.BOTH).setWeight(1, 1).setInsets(0, 10, 0, 0));		
	}
	
	public void init()
	{
		this.setBackground(new Color(233,233,233));	
		estimateLabel = new JLabel("Ä£ÐÍÆÀ¹À");
		exchangeLabel = new JLabel("Markov×ª»»");
		
		estimateLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 18));
		exchangeLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));

		estimatepPanel = new JPanel();
		exchangepPanel = new JPanel();
		
		estimatepPanel.setBackground(new Color(233,233,233));
		exchangepPanel.setBackground(new Color(233,233,233));
		 
		estimateLabel.setEnabled(true);
		exchangeLabel.setEnabled(false);
		
		estimatepPanel.setVisible(true);
		exchangepPanel.setVisible(false);
			
		labelListen();
	}
	
	private void labelListen()
	{
//		expandModelLabel.addMouseListener(new MouseAdapter(){	
//			@Override
//			public void mousePressed(MouseEvent e) {
//				// TODO Auto-generated method stub
//				if(((JLabel)e.getSource()).isEnabled())
//				{
//					expandModelLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 18));
//					expandCaseModel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
//					estimateLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
//					exchangeLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
//	                mainFrame.getStepTwoExpandBottom().setStep(1);
//					expandModelPanel.setVisible(true);
//					expandCasePanel.setVisible(false);
//					estimatepPanel.setVisible(false);
//					exchangepPanel.setVisible(false);
//					mainFrame.getStepTwoCenterRightPanel().getGraphButton().setVisible(false);
//					mainFrame.getStepTwoCenterRightPanel().getZoominButton().setVisible(false);
//					mainFrame.getStepTwoCenterRightPanel().getZoomoutButton().setVisible(false);
//					clearPanel();
//					mainFrame.getpanel().add(mainFrame.getStepTwoModelOperation());
//					mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoModelExpandTabbedPane());
//					mainFrame.renewPanel();
//				}
//			}
//		});
//		expandCaseModel.addMouseListener(new MouseAdapter(){	
//			@Override
//			public void mousePressed(MouseEvent e) {
//				// TODO Auto-generated method stub
//				if(((JLabel)e.getSource()).isEnabled())
//				{
//					expandModelLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
//					expandCaseModel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 18));
//					estimateLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
//					exchangeLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
//					mainFrame.getStepTwoExpandBottom().setStep(2);
//					expandModelPanel.setVisible(false);
//					expandCasePanel.setVisible(true);
//					estimatepPanel.setVisible(false);
//					exchangepPanel.setVisible(false);
//					mainFrame.getStepTwoCenterRightPanel().getGraphButton().setVisible(false);
//					mainFrame.getStepTwoCenterRightPanel().getZoominButton().setVisible(false);
//					mainFrame.getStepTwoCenterRightPanel().getZoomoutButton().setVisible(false);
//					clearPanel();
//					mainFrame.getpanel().add(mainFrame.getStepTwoCaseOperation());
//					isSameName = mainFrame.getStepTwoModelOperation().getIsSameName();
////					if(!isSameName){
////						mainFrame.getStepTwoCaseOperation().getToplabel().setText("µ±Ç°Ä£ÐÍÎª:"+model_Name);
////					}
//					mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoCaseExpandTabbedPane());
//					mainFrame.getpanel().updateUI();
//					mainFrame.renewPanel();
//				}
//			}
//		});
		estimateLabel.addMouseListener(new MouseAdapter(){	
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(((JLabel)e.getSource()).isEnabled())
				{
					estimateLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 18));
					exchangeLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
					mainFrame.getStepTwoExpandBottom().setStep(1);
					estimatepPanel.setVisible(true);
					exchangepPanel.setVisible(false);
					
					mainFrame.getStepTwoCenterRightPanel().getGraphButton().setVisible(false);
					mainFrame.getStepTwoCenterRightPanel().getZoominButton().setVisible(false);
					mainFrame.getStepTwoCenterRightPanel().getZoomoutButton().setVisible(false);
					clearPanel();
					mainFrame.getpanel().add(mainFrame.getStepTwoEvaluateOperation());
//					if(!isSameName)
//					{
//						mainFrame.getStepTwoEvaluateOperation().getTopLabel().setText("µ±Ç°Ä£ÐÍÎª:"+model_Name);
//					}
					mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoEvaluateTabbedPane());
					mainFrame.renewPanel();
				}
				
			}
		});
		exchangeLabel.addMouseListener(new MouseAdapter(){	
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(((JLabel)e.getSource()).isEnabled())
				{
//					expandModelLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
//					expandCaseModel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
					estimateLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
					exchangeLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 18));
					mainFrame.getStepTwoExpandBottom().setStep(2);
//					expandModelPanel.setVisible(false);
//					expandCasePanel.setVisible(false);
					estimatepPanel.setVisible(false);
					exchangepPanel.setVisible(true);
					
					mainFrame.getStepTwoCenterRightPanel().getGraphButton().setVisible(true);
					mainFrame.getStepTwoCenterRightPanel().getZoominButton().setVisible(true);
					mainFrame.getStepTwoCenterRightPanel().getZoomoutButton().setVisible(true);
					clearPanel();
	    	        mainFrame.getpanel().add(mainFrame.getStepTwoExchangeOperation());  
//	    	        if(!isSameName)
//					{
//	    	        	mainFrame.getStepTwoExchangeOperation().getToplabel().setText("µ±Ç°Ä£ÐÍÎª:"+model_Name);
//					}
	    	        mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoExchangeTabbedPane());
	    	        mainFrame.renewPanel();
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
    
	public String getModel_Name() {
		return model_Name;
	}
	public JLabel getExpandModelLabel() {
		return expandModelLabel;
	}

	public JLabel getExpandCaseModel() {
		return expandCaseModel;
	}

	public JLabel getEstimateLabel() {
		return estimateLabel;
	}

	public JLabel getExchangeLabel() {
		return exchangeLabel;
	}

	public JPanel getExpandModelPanel() {
		return expandModelPanel;
	}

	public JPanel getExpandCasePanel() {
		return expandCasePanel;
	}

	public JPanel getEstimatepPanel() {
		return estimatepPanel;
	}

	public JPanel getExchangepPanel() {
		return exchangepPanel;
	}
	
}
