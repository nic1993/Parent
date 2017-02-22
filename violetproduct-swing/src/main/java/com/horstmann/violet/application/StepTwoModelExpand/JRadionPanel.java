package com.horstmann.violet.application.StepTwoModelExpand;

import java.awt.Font;
import java.awt.GridBagLayout;
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
	private List<String> list = new ArrayList<String>();
	private List<JRadioButton> radioList;
	public JRadionPanel(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		this.list = mainFrame.getNameList();
		init(list);
		this.setLayout(new GridBagLayout());
		for(int i = 0;i < radioList.size();i++)
		{
			this.add(radioList.get(i), new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5));
		}
	}
    private void init(List<String> list)
    {
    	radioList = new ArrayList<JRadioButton>();
    	buttonGroup = new ButtonGroup();
    	for(String s : list)
    	{
    		JRadioButton radioButton = new JRadioButton();
    		radioButton.setFont(new Font("ËÎÌו", Font.PLAIN, 16));
    		radioButton.setText(s);
    		buttonGroup.add(radioButton);
    		radioList.add(radioButton);
    	}
    }
	public String getSelectName()
	{
		for(JRadioButton jRadioButton : radioList)
		{
			if(jRadioButton.isSelected())
				return jRadioButton.getText();
		}
		return null;
	}
    
}
