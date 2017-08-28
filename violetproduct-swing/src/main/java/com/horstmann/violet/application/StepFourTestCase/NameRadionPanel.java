package com.horstmann.violet.application.StepFourTestCase;

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

public class NameRadionPanel extends JPanel{
	private ButtonGroup buttonGroup;
	private MainFrame mainFrame;
	private List<String> list = new ArrayList<String>();
	private List<JRadioButton> radioList = new ArrayList<JRadioButton>();
	private String route;
	public NameRadionPanel(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		this.route = mainFrame.getBathRoute() + "/TestCase/";
		initFile();
	}
	public void initFile()
	{
		this.removeAll();
		list.clear();
		radioList.clear();
		File f = new File(route);
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
	}
    private void init(List<String> list)
    {
    	buttonGroup = new ButtonGroup();
    	for(String s : list)
    	{
    		JRadioButton radioButton = new JRadioButton();
    		radioButton.setFont(new Font("ËÎÌו", Font.PLAIN, 16));
    		radioButton.setText(s);
    		buttonGroup.add(radioButton);
    		radioList.add(radioButton);
    	}
    	initListen();
    }
    private void initListen()
    {
    	for(JRadioButton radioButton : radioList)
    	{
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
    
}
