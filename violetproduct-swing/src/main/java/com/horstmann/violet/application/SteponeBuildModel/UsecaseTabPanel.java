package com.horstmann.violet.application.SteponeBuildModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class UsecaseTabPanel extends JPanel{

	private MainFrame mainFrame;
	private JPanel panel;
	private JLabel titlelabel;
	private JLabel deletelabel;
	private JLabel picturelJLabel;
	private String title;
	private Icon icon;
	private Icon beforeCloseIcon;
	private Icon pressCloseIcon;
	private int pos;

	public UsecaseTabPanel(MainFrame mainFrame,String title,int pos) {
		// TODO Auto-generated constructor stub
		this.mainFrame = mainFrame;
		this.title = title;
		this.pos = pos;
		init(title);
        this.setLayout(new GridLayout(1, 1));
        panel.setBackground(Color.white);
        this.add(panel);
	}
	public void init(String title)
	{
		titlelabel = new JLabel(title+"  ");
		titlelabel.setFont(new Font("ËÎÌו", Font.PLAIN, 16));
		deletelabel = new JLabel();
		picturelJLabel = new JLabel();
		pressCloseIcon = new ImageIcon("resources\\icons\\22x22\\pressClose.png");
		beforeCloseIcon = new ImageIcon("resources\\icons\\22x22\\beforeClose.png");
		icon = new ImageIcon("resources\\icons\\22x22\\tabpicture.png");
		deletelabel.setIcon(beforeCloseIcon);
		picturelJLabel.setIcon(icon);
		
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.add(picturelJLabel,new GBC(0, 0).setFill(GBC.BOTH).setInsets(0, 0, 0,3));
		panel.add(titlelabel,new GBC(1, 0).setFill(GBC.BOTH).setWeight(1, 1).setInsets(0, 1, 0, 6));
		panel.add(deletelabel,new GBC(2, 0).setFill(GBC.NONE).setWeight(0, 0));
		panelListene();
		
	}
	private void panelListene()
	{
		panel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.getStepOneCenterUseCaseTabbedPane().setSelectedIndex(pos);
				panel.setBackground(Color.white);
				int count = mainFrame.getStepOneCenterUseCaseTabbedPane().getTabCount();
				deletelabel.setIcon(beforeCloseIcon);
				for(int i = 0;i < count;i++)
				{
					if(i!= pos)
					{
						mainFrame.getListUsecaseTabPanel().get(i).getPanel().setBackground(new Color(246,246,246));
						mainFrame.getListUsecaseTabPanel().get(i).getDeletelabel().setIcon(null);
					}
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		deletelabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.getOperationButton().getCloseButton().doClick();
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				deletelabel.setIcon(beforeCloseIcon);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				deletelabel.setIcon(pressCloseIcon);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	public JPanel getPanel() {
		return panel;
	}
	public JLabel getDeletelabel() {
		return deletelabel;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public JLabel getTitlelabel() {
		return titlelabel;
	}
	public void setTitlelabel(JLabel titlelabel) {
		this.titlelabel = titlelabel;
	}
}
