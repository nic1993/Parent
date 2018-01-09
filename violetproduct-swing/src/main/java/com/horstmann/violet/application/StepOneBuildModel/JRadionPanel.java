package com.horstmann.violet.application.StepOneBuildModel;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class JRadionPanel extends JPanel {
	private ButtonGroup buttonGroup = new ButtonGroup();
	private MainFrame mainFrame;
	private String EABathRoute;
	private String VioletBathRoute;
	private List<String> list = new ArrayList<String>();
//	private List<Radio> radioList = new ArrayList<Radio>();
	private Map<String, Radio> radios = new HashMap<String,Radio>();
	private boolean isSameName;
	private String currentName;

	private boolean caseExpand = false;
	private boolean evaluate = false;
	private boolean exchange = false;

	public JRadionPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.EABathRoute = mainFrame.getBathRoute() + "/UseCaseDiagram/EAXML";
		this.VioletBathRoute = mainFrame.getBathRoute() + "/UseCaseDiagram/VioletXML";
		initFile();
	}

	public void initFile() {
		this.removeAll();
		List<String> removelists = new ArrayList<String>();
		//radios清除已经删除的元素
		for(String title : radios.keySet())
		{
			int flag = 0;
			for (ModelPanel modelPanel : mainFrame.getModelPanelMap().keySet()) {
				if(title.equals(modelPanel.getTitle().getText()))
					flag = 1;
			}
			if(flag == 0)
			{
				removelists.add(title);
			}
		}
		
		for(String str : removelists){
			radios.remove(str);
		}
		

		// 读取EA的xml
		for (ModelPanel modelPanel : mainFrame.getModelPanelMap().keySet()) {
			if(radios.get(modelPanel.getTitle().getText()) == null)
			{
				Radio radioButton = new Radio();
				radioButton.setFont(new Font("宋体", Font.PLAIN, 16));
				radioButton.setText(modelPanel.getTitle().getText());
				buttonGroup.add(radioButton);
				radios.put(modelPanel.getTitle().getText(), radioButton);
			}
			list.add(modelPanel.getTitle().getText());
		}
//		init(list);
		this.setLayout(new GridBagLayout());
		int i = 0;
		for(String title : radios.keySet()){
			this.add(radios.get(title), new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 0, 0, 0));
			i++;
		}
		this.repaint();
		mainFrame.renewPanel();
		
		initListen();
	}

//	private void init(List<String> list) {
//		radioList = new ArrayList<Radio>();
//		buttonGroup = new ButtonGroup();
//		for (String s : list) {
//			Radio radioButton = new Radio();
//			radioButton.setFont(new Font("宋体", Font.PLAIN, 16));
//			radioButton.setText(s);
////			buttonGroup.add(radioButton);
//			radioList.add(radioButton);
//		}
//	}

	private void initListen() {
//		for (Radio radio : radioList) {
			
		for(String str : radios.keySet()){
			Radio radio = radios.get(str);
			radio.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					mainFrame.renewPanel();
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					if (e.getClickCount() == 2) {
						if (((Radio) e.getSource()).getScenceTabelPanel() != null) {
//							for (Radio radio : radioList) {
//								radio.setFont(new Font("宋体", Font.PLAIN, 16));
//							}
//							((Radio) e.getSource()).setFont(new Font("宋体", Font.BOLD, 16));

							mainFrame.getStepTwoModelOperation().getLabel().removeAll();
							mainFrame.getStepTwoModelOperation().getLabel().setText(((Radio) e.getSource()).getQuota());
							
							mainFrame.getStepTwoModelExpandTabbedPane().getValidationResults().removeAll();
							mainFrame.getStepTwoModelExpandTabbedPane().getValidationResults()
									.add(((Radio) e.getSource()).getScenceTabelPanel());
							mainFrame.getStepTwoModelExpandTabbedPane().setSelectedIndex(1);
							mainFrame.getStepTwoModelExpandTabbedPane().getValidationResults().repaint();
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

	public String getSelectName() {
		for(String str : radios.keySet())
		{
			if(radios.get(str).isSelected())
				return radios.get(str).getText();
		}
		return null;
	}

//	public Radio getSelectRadion() {
//		for (Radio radio : radioList) {
//			if (radio.isSelected())
//				return radio;
//		}
//		return null;
//	}

//	public List<Radio> getRadioList() {
//		return radioList;
//	}
//	
	

	public Map<String, Radio> getRadios() {
		return radios;
	}

	public boolean isSameName() {
		return isSameName;
	}
}
