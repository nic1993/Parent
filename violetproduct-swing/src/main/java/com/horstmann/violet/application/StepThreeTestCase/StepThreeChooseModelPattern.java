package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class StepThreeChooseModelPattern extends JPanel{
	private JLabel label;
	private JLabel label1;
	private JLabel label2;
	private JRadioButton jRadioButton;
	private JRadioButton jRadioButton1;
	private ButtonGroup buttonGroup;
	private JPanel label1Panel;
	private JPanel label2Panel;
	private JPanel panel;
	private JPanel panel1;
	private MainFrame mainFrame;
	private NoTimeMarkovFileRadion noTimeMarkovFileRadion;
	private TimeMarkovFileRadio timeMarkovFileRadio;
	private List<JRadioButton> timeRadioButtons;
    public StepThreeChooseModelPattern(MainFrame mainFrame)
    {
    	this.mainFrame = mainFrame;
    	this.noTimeMarkovFileRadion = mainFrame.getNoTimeMarkovFileRadion();
    	this.timeMarkovFileRadio = mainFrame.getTimeMarkovFileRadio();
    	init();
    	this.setBackground(new Color(233,233,233));
    	this.setLayout(new GridBagLayout());
    	this.add(label, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(15, 10, 30, 0));
    	this.add(jRadioButton,new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 40, 10, 0));
    	this.add(label1Panel,new GBC(0, 2).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 60, 20, 0));
    	this.add(jRadioButton1,new GBC(0, 3).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 40, 10, 0));
    	this.add(label2Panel,new GBC(0, 4).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 60, 0, 0));
    	this.add(new JPanel(),new GBC(0, 5).setFill(GBC.BOTH).setWeight(1, 1));
    }
    private void init()
    {
    	timeRadioButtons = new ArrayList<JRadioButton>();
    	label = new JLabel("请选择模型类别");
    	label1 = new JLabel("请选择需要生成测试用例的Markov链模型");
    	label2 = new JLabel("请选择需要生成测试用例的Markov链模型");
    	
    	jRadioButton = new JRadioButton("不带时间约束的模型");
    	jRadioButton1 = new JRadioButton("带有时间约束的模型");
    	buttonGroup = new ButtonGroup();
    	
    	label1Panel = new JPanel();
    	label2Panel = new JPanel();
    	panel = new JPanel();
    	panel1 = new JPanel();

    	label1Panel.setVisible(false);
    	label2Panel.setVisible(false);
    	
    	buttonGroup.add(jRadioButton);
    	buttonGroup.add(jRadioButton1);
    	label.setFont(new Font("微软雅黑", Font.PLAIN, 18));
    	label1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
    	label2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
    	
    	jRadioButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
    	jRadioButton1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
    	
    	label1Panel.setBackground(new Color(233,233,233));
    	label2Panel.setBackground(new Color(233,233,233));
    	panel.setBackground(new Color(233,233,233));
    	panel1.setBackground(new Color(233,233,233));
    	
    	label1Panel.setLayout(new GridBagLayout());
    	label1Panel.add(label1,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 0, 10, 0));
    	label1Panel.add(noTimeMarkovFileRadion,new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0));

    	
    	label2Panel.setLayout(new GridBagLayout());	
    	label2Panel.add(label2,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 0, 10, 0));
    	label2Panel.add(timeMarkovFileRadio,new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0));
    	listen();
    }
    public void listen()
    {
    	jRadioButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(jRadioButton.isSelected())
				{
					System.out.println(jRadioButton.getText());
					label1Panel.setVisible(true);
					label2Panel.setVisible(false);
					mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeModelLabel().setEnabled(false);
					mainFrame.getStepThreeLeftButton().getTimeModelPanel().setVisible(false);
				}
				mainFrame.renewPanel();
			}
		});
    	jRadioButton.addMouseListener(new MouseListener() {
			
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
    	
        jRadioButton1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(jRadioButton1.isSelected())
				{
					label1Panel.setVisible(false);
					label2Panel.setVisible(true);
					mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(false);
					mainFrame.getStepThreeLeftButton().getNoTimeModelPanel().setVisible(false);
					mainFrame.getStepThreeLeftButton().getTimeModelLabel().setEnabled(true);
				}
				mainFrame.renewPanel();
			}
		});
        jRadioButton1.addMouseListener(new MouseListener() {
			
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
	public List<JRadioButton> getTimeRadioButtons() {
		return timeRadioButtons;
	}
	public JRadioButton getjRadioButton() {
		return jRadioButton;
	}
	public JRadioButton getjRadioButton1() {
		return jRadioButton1;
	}
    
}
