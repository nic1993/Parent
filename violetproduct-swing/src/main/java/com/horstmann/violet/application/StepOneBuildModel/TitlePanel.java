package com.horstmann.violet.application.StepOneBuildModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class TitlePanel extends JPanel{
    private MainFrame mainFrame;
    
    private JPanel picturePanel;
    private JPanel leftlabelpanel;
    private JPanel iconPanel;
    private JLabel bigTitle;
    private JLabel smallTitle;
    private JLabel iconLabel;
	public TitlePanel(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		init();
		this.setBackground(new Color(222,222,222));
		this.setLayout(new GridBagLayout());
		this.add(leftlabelpanel,new GBC(0, 0).setWeight(0, 0).setInsets(15,30,30,80));
		this.add(iconPanel,new GBC(1, 0).setFill(GBC.BOTH).setWeight(1, 1));
	}
	private void init()
	{
		try {
		picturePanel = new JPanel();				
		leftlabelpanel = new JPanel();
		
			iconPanel = new ImagePanel("resources/icons/72x72/steponeimage.png", 2, 2);
		
		Icon icon = new ImageIcon("resources/icons/72x72/violet.png");
		iconPanel.setBackground(new Color(211,211,211));
		iconLabel = new JLabel();
		iconLabel.setIcon(icon);
		bigTitle=new JLabel();
		bigTitle.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 22));
		bigTitle.setForeground(Color.black);
		leftlabelpanel.setLayout(new GridBagLayout());
		leftlabelpanel.setBackground(new Color(222,222,222));
		picturePanel.setBackground(new Color(222,222,222));
		picturePanel.setLayout(new GridLayout(1,1));
		picturePanel.add(iconLabel);
		smallTitle = new JLabel();
		smallTitle.setFont(new Font("ËÎÌå", Font.PLAIN, 16));
//		leftlabelpanel.add(picturePanel, new GBC(0,0,1,2).setFill(GBC.BOTH));
		leftlabelpanel.add(bigTitle, new GBC(0,0,1,1).setFill(GBC.BOTH).setInsets(10, 0, 0, 250));
		leftlabelpanel.add(smallTitle, new GBC(0,1,1,1).setFill(GBC.BOTH).setInsets(17, 28, 0, 0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public JLabel getBigTitle() {
		return bigTitle;
	}
	public JLabel getSmallTitle() {
		return smallTitle;
	}
	
}
