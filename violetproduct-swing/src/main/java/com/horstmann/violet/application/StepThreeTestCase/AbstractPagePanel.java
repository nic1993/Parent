package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.apache.commons.collections.functors.IfClosure;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.gui.util.yangjie.TCDetail;

public class AbstractPagePanel extends JPanel{

	private List<String> list;
	private JPanel abstractPanel;
	private JScrollPane abstractScrollPane;
	
	private JPanel pagePanel;
	
	private JPanel firstPanel;
	private JLabel first;
	
	private JPanel previousPanel;
	private JLabel previous;
	
	private JPanel nextPanel;
	private JLabel next;
	
	private JPanel lastPanel;
	private JLabel last;
	private JTextField pageTestField;
	
	private MainFrame mainFrame;
	private int CurrentPage = 1;
	
	private int totalPage;
	public AbstractPagePanel(List<String> lists,MainFrame mainFrame){
		this.list = lists;
		this.mainFrame = mainFrame;
		
		init();
		this.setLayout(new BorderLayout());
		this.add(abstractScrollPane, BorderLayout.CENTER);
		this.add(pagePanel,BorderLayout.SOUTH);

	} 
	private void init()
	{
		abstractPanel = new JPanel();
		abstractPanel.setLayout(new GridBagLayout());
		abstractScrollPane = new JScrollPane(abstractPanel);
		
		pagePanel = new JPanel();
		pagePanel.setLayout(new GridBagLayout());
		
		firstPanel = new JPanel();
		previousPanel = new JPanel();
		nextPanel = new JPanel();
		lastPanel = new JPanel();
		
		first = new JLabel("首页");
		previous = new JLabel("上一页");
		next = new JLabel("下一页");
		last = new JLabel("尾页");
		pageTestField = new JTextField();
		pageTestField.setEditable(false);
		pageTestField.setPreferredSize(new Dimension(25, 18));
		
		first.setIcon(new ImageIcon("resources/icons/16x16/first.png"));
		firstPanel.setPreferredSize(new Dimension(30, 23));
		firstPanel.setLayout(new BorderLayout());
		firstPanel.add(first,BorderLayout.CENTER);
		first.setPreferredSize(new Dimension(25, 18));
		first.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				firstPanel.setBackground(new Color(211, 211, 211));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				firstPanel.setBackground(new Color(233, 233, 233));
			}
		});
		
		
		previous.setIcon(new ImageIcon("resources/icons/16x16/previous.png"));
		previousPanel.setPreferredSize(new Dimension(30, 23));
		previousPanel.add(previous);
		previous.setPreferredSize(new Dimension(25, 15));
		previous.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				previousPanel.setBackground(new Color(211, 211, 211));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				previousPanel.setBackground(new Color(233, 233, 233));
			}
		});
		
		next.setIcon(new ImageIcon("resources/icons/16x16/next.png"));
		nextPanel.setPreferredSize(new Dimension(30, 27));
		nextPanel.add(next);
		next.setPreferredSize(new Dimension(25, 17));
		next.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				nextPanel.setBackground(new Color(211, 211, 211));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				nextPanel.setBackground(new Color(233, 233, 233));
			}
		});
		
		last.setIcon(new ImageIcon("resources/icons/16x16/last.png"));
		lastPanel.setPreferredSize(new Dimension(30,23));
		lastPanel.add(last);
		last.setPreferredSize(new Dimension(25, 18));
		last.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				lastPanel.setBackground(new Color(211, 211, 211));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				lastPanel.setBackground(new Color(233, 233, 233));
			}
		});
		
		
		JPanel linePanel = new JPanel(){
			public void paint(Graphics g) {
	            super.paint(g);
	            java.awt.Rectangle rect = this.getBounds();
	            int width = (int) rect.getWidth() - 1;
	            int height = (int) rect.getHeight() - 1;
	            Graphics2D g2 = (Graphics2D)g;
	            g2.setStroke(new BasicStroke(2f));
	            g2.setColor(new Color(188,188,188));
	            g2.drawLine(0, height,width, height);
	          }
		};
		JPanel gapPanel = new JPanel();
		
		pagePanel.add(linePanel, new GBC(0, 0,6,1).setFill(GBC.BOTH).setWeight(1, 0));
		pagePanel.add(gapPanel, new GBC(0, 1,1,1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 0, 5, 0));
		pagePanel.add(firstPanel, new GBC(1, 1,1,1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(5));
		pagePanel.add(previousPanel, new GBC(2, 1,1,1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(5, 0, 5, 5));
		pagePanel.add(pageTestField, new GBC(3, 1,1,1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(5, 0, 5, 5));
		pagePanel.add(nextPanel, new GBC(4, 1,1,1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(8, 0, 5, 0));
		pagePanel.add(lastPanel, new GBC(5, 1,1,1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(5, 0, 5, 10));
		
		if(list.size() % 500 == 0)
		{
			totalPage = list.size() / 500;
		}
		else {
			totalPage = list.size() / 500 + 1;
		}
		
		listen();
	}
	public void listen()
	{
		first.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(list.size() > 500){
					abstractPanel.removeAll();
					for(int i = 0;i < 500;i++)
					{
						StepThreeTabelPanel testTabelPanel = new StepThreeTabelPanel(list.get(i), 1,
        						mainFrame);
						abstractPanel.add(testTabelPanel,
							new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
						mainFrame.renewPanel();
					}
					pageTestField.removeAll();
					pageTestField.setText("1");
				}
			}
		});
		last.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(list.size() > 500){
					abstractPanel.removeAll();
					int Remainder = list.size() % 500;
					if(Remainder == 0){
						for(int i = (totalPage - 1) *500;i < list.size();i++)
						{
							StepThreeTabelPanel testTabelPanel = new StepThreeTabelPanel(list.get(i), 1,
	        						mainFrame);
							abstractPanel.add(testTabelPanel,
								new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
							mainFrame.renewPanel();
						}
						pageTestField.removeAll();
						pageTestField.setText(String.valueOf(totalPage));
					}
					else {
						for(int i = (totalPage -1)*500;i < list.size();i++)
						{
							StepThreeTabelPanel testTabelPanel = new StepThreeTabelPanel(list.get(i), 1,
	        						mainFrame);
							abstractPanel.add(testTabelPanel,
								new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
							mainFrame.renewPanel();
						}
						pageTestField.removeAll();
						pageTestField.setText(String.valueOf(totalPage));
					}
				}
			}
		});
		next.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				int index = Integer.valueOf(pageTestField.getText());
				if(list.size() > 500 && index != totalPage){
					abstractPanel.removeAll();
						for(int i = 500 * index;i < 500 * (index + 1);i++)
						{
							StepThreeTabelPanel testTabelPanel = new StepThreeTabelPanel(list.get(i),1,
		        					mainFrame);
							abstractPanel.add(testTabelPanel,
								new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
							mainFrame.renewPanel();
					    }
						pageTestField.removeAll();
						pageTestField.setText(String.valueOf(index + 1));
				}
			}
		});
		previous.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				int index = Integer.valueOf(pageTestField.getText());
				if(list.size() > 500 && index != 1)
				{
					abstractPanel.removeAll();
					for(int i = 500 * index;i < 500 * (index - 1);i++)
					{
						StepThreeTabelPanel testTabelPanel = new StepThreeTabelPanel(list.get(i),1,
	        					mainFrame);
						abstractPanel.add(testTabelPanel,
							new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
						mainFrame.renewPanel();
				    }
					pageTestField.removeAll();
					pageTestField.setText(String.valueOf(index - 1));
				}
			}
		});
	}
	public JPanel getAbstractPanel() {
		return abstractPanel;
	}
	public JTextField getPageTestField() {
		return pageTestField;
	}
	
}
