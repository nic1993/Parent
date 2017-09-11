package com.horstmann.violet.application.StepOneBuildModel;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class JRadionPanel extends JPanel{
	private ButtonGroup buttonGroup;
	private MainFrame mainFrame;
	private String EABathRoute;
	private String VioletBathRoute;
	private List<String> list = new ArrayList<String>();
	private List<Radio> radioList = new ArrayList<Radio>();
	private boolean isSameName;
	private String currentName;
	
	private boolean caseExpand = false;
	private boolean evaluate = false;
	private boolean exchange = false;
	public JRadionPanel(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		this.EABathRoute = mainFrame.getBathRoute() + "/UseCaseDiagram/EAXML";
		this.VioletBathRoute = mainFrame.getBathRoute() + "/UseCaseDiagram/VioletXML";
		initFile();
	}
	public void initFile()
	{
		this.removeAll();
		list.clear();
		radioList.clear();
		//读取EA的xml
//				File f = new File(EABathRoute);
//				if(!f.exists())
//					f.mkdirs();
//				
//				File files[] = f.listFiles();
//				for(File file : files)
//				{
//					String FileRoute = file.getName().split("\\.")[0];
//					list.add(FileRoute);
//				}
//				 //读取Violet的XML
//				File VioletFile = new File(VioletBathRoute);
//				if(!VioletFile.exists())
//					VioletFile.mkdirs();
//				File VioletFiles[] = VioletFile.listFiles();
//				for(File file : VioletFiles)
//				{
//					String VioletFileName = file.getName().split("\\.")[0];
//					list.add(VioletFileName);
//				}
//				init(list);
//				this.setLayout(new GridBagLayout());
//				for(int i = 0;i < radioList.size();i++)
//				{
//					this.add(radioList.get(i), new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 0, 0, 0));
//				}
//				this.repaint();
		
		        for(ModelPanel modelPanel : mainFrame.getModelPanelMap().keySet())
		        {
		        	list.add(modelPanel.getTitle().getText());
		        }	
		        init(list);
				this.setLayout(new GridBagLayout());
				for(int i = 0;i < radioList.size();i++)
				{
					this.add(radioList.get(i), new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 0, 0, 0));
				}
				this.repaint();
				
				initListen();
	}
    private void init(List<String> list)
    {
    	radioList = new ArrayList<Radio>();
    	buttonGroup = new ButtonGroup();
    	for(String s : list)
    	{
    		Radio radioButton = new Radio();
    		radioButton.setFont(new Font("宋体", Font.PLAIN, 16));
    		radioButton.setText(s);
    		buttonGroup.add(radioButton);
    		radioList.add(radioButton);
    	}
    }
    private void initListen()
    {
    	for(Radio radio : radioList)
    	{
    		radio.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					mainFrame.renewPanel();
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub	
					if(e.getClickCount() == 2)
					{
						if(((Radio)e.getSource()).getScenceTabelPanel() != null)
						{
							for(Radio radio : radioList)
							{
								radio.setFont(new Font("宋体", Font.PLAIN, 16));
							}
							((Radio)e.getSource()).setFont(new Font("宋体", Font.BOLD, 16));
							
							mainFrame.getStepTwoModelExpandTabbedPane().getValidationResults().removeAll();
							mainFrame.getStepTwoModelExpandTabbedPane().getValidationResults().add(((Radio)e.getSource()).getScenceTabelPanel());
							mainFrame.getStepTwoModelExpandTabbedPane().getValidationResults().repaint();
							mainFrame.getStepTwoModelExpandTabbedPane().setSelectedIndex(1);
						}
					}
					mainFrame.renewPanel();
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					mainFrame.renewPanel();
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					mainFrame.renewPanel();
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					mainFrame.renewPanel();
				}
			});
    	}
    }
	public String getSelectName()
	{
		for(Radio radio : radioList)
		{
			if(radio.isSelected())
				return radio.getText();
		}
		return null;
	}
	public Radio getSelectRadion() {
		for(Radio radio : radioList)
		{
			if(radio.isSelected())
				return radio;
		}
		return null;
	}
	public List<Radio> getRadioList() {
		return radioList;
	}
	public boolean isSameName() {
		return isSameName;
	}
}
