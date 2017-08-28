package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class TimeMarkovFileRadio extends JPanel{
	private ButtonGroup buttonGroup;
	private MainFrame mainFrame;
	private String MarkovRoute;
	private List<String> list = new ArrayList<String>();
	private List<JRadioButton> radioList = new ArrayList<JRadioButton>();
	private boolean isSameName;
	private String currentName;
	public TimeMarkovFileRadio(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		this.MarkovRoute = mainFrame.getBathRoute() + "/TimeMarkov/";
		
		initFile();
	}
	public void initFile()
	{
		this.removeAll();
		list.clear();
		radioList.clear();
		System.out.println(MarkovRoute);
				File f = new File(MarkovRoute);
				if(!f.exists())
					f.mkdirs();
                
				File files[] = f.listFiles();
				for(File file : files)
				{
					String FileRoute = file.getName().split("\\.")[0];
					list.add(FileRoute);
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
    private void initListen()
    {
    	for(JRadioButton radioButton : radioList)
    	{
                radioButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					currentName = mainFrame.getStepThreeLeftButton().getModelName();
					if(currentName == null){
						isSameName = false;
					}
					else if (getSelectName().equals(currentName)) {
						isSameName = true;
					}
					else {
						isSameName = false;
					}
					mainFrame.getStepThreeLeftButton().setModelName(getSelectName());
					mainFrame.getTimeExpandOperation().setModelName(getSelectName());
					mainFrame.getTimeCaseOperation().setModelName(getSelectName());
					mainFrame.getTimeCaseOperation1().setModelName(getSelectName());
					
					mainFrame.renewPanel();
				}
			});
    		
    		radioButton.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					mainFrame.renewPanel();
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
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
		for(JRadioButton jRadioButton : radioList)
		{
			if(jRadioButton.isSelected())
				return jRadioButton.getText();
		}
		return null;
	}
	public List<JRadioButton> getRadioList() {
		return radioList;
	}
	public boolean getIsSameName() {
		return isSameName;
	}
	
    
}
