package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.horstmann.violet.application.gui.MainFrame;

public class TimeSeqNode extends JLabel {
	private Icon icon;
	private String title;
	private String quota;
	private int type;
	private AbstractPagePanel abstractPagePanel;
	private MainFrame mainFrame;

	public TimeSeqNode(String name, MainFrame mainFrame) {
		init();
		this.mainFrame = mainFrame;
		this.title = name;
		this.setIcon(icon);
		this.setText(name);
		this.setFont(new Font("ËÎÌו", Font.PLAIN, 16));
	}

	public void init() {
		icon = new ImageIcon("resources/icons/22x22/nodeLabel.png");
		listen();
	}

	private void listen() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.getCenterTabPanel().removeAll();
				mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeTimeSeqTabbedPane());

				mainFrame.getStepThreeTimeSeqTabbedPane().getAbstractSequence().removeAll();
				mainFrame.getStepThreeTimeSeqTabbedPane().getAbstractSequence().setLayout(new GridLayout());
				mainFrame.getStepThreeTimeSeqTabbedPane().getAbstractSequence().add(abstractPagePanel);
				mainFrame.getStepThreeTimeSeqTabbedPane().getAbstractSequence().repaint();

				mainFrame.getTimeSeqOperation().getTopLabel().removeAll();
				mainFrame.getTimeSeqOperation().getTopLabel().setText(quota);

				mainFrame.getTimeSeqOperation1().getTopLabel().removeAll();
				mainFrame.getTimeSeqOperation1().getTopLabel().setText(quota);

				for (TimeSeqNode TimeSeqNode : mainFrame.getStepThreeLeftButton().getTimeSeqNodePanel()
						.getTestCaseNodeLabels()) {
					TimeSeqNode.setFont(new Font("ËÎÌו", Font.PLAIN, 16));
				}
				((TimeSeqNode) e.getSource()).setFont(new Font("ËÎÌו", Font.BOLD, 16));
				mainFrame.renewPanel();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
		});
	}

	public AbstractPagePanel getAbstractPagePanel() {
		return abstractPagePanel;
	}

	public void setAbstractPagePanel(AbstractPagePanel abstractPagePanel) {
		this.abstractPagePanel = abstractPagePanel;
	}

	public String getTitle() {
		return title;
	}

	public String getQuota() {
		return quota;
	}

	public void setQuota(String quota) {
		this.quota = quota;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
