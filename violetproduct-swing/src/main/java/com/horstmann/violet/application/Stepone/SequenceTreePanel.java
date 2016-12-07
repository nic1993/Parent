package com.horstmann.violet.application.Stepone;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.menu.FileMenu;

public class SequenceTreePanel extends JPanel{

	private MainFrame mainFrame;
	private FileMenu fileMenu;
	private JLabel sequenceLabel;
	public JPopupMenu popupMenu;
	public JMenuItem newDiagram;
	public JMenuItem importDiagram;
	private Map<DefaultMutableTreeNode, JPanel> hashMap;

	public JTree sequencetree;
	private  static DefaultTreeModel sequencetreemodel;
	private static DefaultMutableTreeNode sequencetreerootnode;

	public SequenceTreePanel(FileMenu fileMenu, MainFrame mainFrame){
		
		this.mainFrame=mainFrame;
		this.fileMenu=fileMenu;
		this.setBackground(new Color(233,233,233));
		init();
		
//		sequencetree.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		this.setLayout(new GridLayout());
		this.add(sequencetree);
			
	}

	private void init() {
		// TODO Auto-generated method stub
		hashMap = new HashMap<DefaultMutableTreeNode,JPanel>();
		sequencetreerootnode=new DefaultMutableTreeNode("顺序图列表");
		sequencetreemodel=new DefaultTreeModel(sequencetreerootnode);
		sequencetree=new JTree(sequencetreemodel);
		DefaultTreeCellRenderer cellRender=new DefaultTreeCellRenderer();
		cellRender.setBackgroundNonSelectionColor(new Color(233,233,233));
		cellRender.setFont(new Font("宋体", Font.PLAIN, 16));
		sequencetree.setOpaque(false);
		sequencetree.setCellRenderer(cellRender);
		sequenceLabel =  new JLabel("顺序图是将交互关系表示为一个二维图。纵向是时间轴，时间沿竖线向下延伸。横向轴代表了在协作中各独立对象的类元角色。");
		sequenceLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		sequencetree.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getButton() == e.BUTTON1)
				{
					
					mainFrame.getOpreationPart().validate();
					wakeupPanel();
					mainFrame.getCenterTabPanel().removeAll();
					mainFrame.getCenterTabPanel().add(mainFrame.getStepOneCenterSequenceTabbedPane());
					mainFrame.getCenterTabPanel().updateUI();
					mainFrame.getpanel().removeAll();
					mainFrame.getpanel().setLayout(new GridLayout(1, 1));
					mainFrame.getpanel().add(mainFrame.getOperationButton());
					mainFrame.getOperationButton().getOtherPanel().removeAll();
					mainFrame.getOperationButton().getOtherPanel().setLayout(new GridBagLayout());
					mainFrame.getOperationButton().getOtherPanel().add(sequenceLabel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10,10,10,10));
					mainFrame.getpanel().updateUI();
					mainFrame.getinformationPanel().removeAll();
					mainFrame.getinformationPanel().setLayout(new GridLayout(1, 1));
					mainFrame.getinformationPanel().add(mainFrame.getouOutputinformation());
					mainFrame.getReduceOrEnlargePanel().setLayout(new GridLayout(1, 1));
					mainFrame.getReduceOrEnlargePanel().add(mainFrame.getstepOneCenterRightPanel());
					mainFrame.getOpreationPart().revalidate();
				}
				if(e.getButton()==e.BUTTON3){
					
					popupMenu = new JPopupMenu();
					newDiagram = new JMenuItem("新建");
					importDiagram = new JMenuItem("导入");
					popupMenu.add(newDiagram);
					popupMenu.add(importDiagram);
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
					
					newDiagram.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							((JMenu) fileMenu.getFileNewMenu().getItem(1)).getItem(3).doClick();	
						}
					});
					
					importDiagram.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub							
							fileMenu.fileOpenItem.doClick();		
						}
					});	
				}
				
				if(e.getClickCount()==2){
					 DefaultMutableTreeNode node = (DefaultMutableTreeNode) sequencetree
							.getLastSelectedPathComponent();
					 if(!node.equals(sequencetreerootnode)){
					mainFrame.getStepOneCenterSequenceTabbedPane().setSelectedComponent(hashMap.get(node));
					int index = mainFrame.getStepOneCenterSequenceTabbedPane().getSelectedIndex();
					for(int i = 0;i < mainFrame.getListSequenceTabPanel().size();i++)
					{
						if(i!= index)
						{
							mainFrame.getListSequenceTabPanel().get(i).getPanel().setBackground(new Color(246,246,246));
							mainFrame.getListSequenceTabPanel().get(i).getDeletelabel().setIcon(null);
						}
						else {
							mainFrame.getListSequenceTabPanel().get(i).getPanel().setBackground(Color.white);
							mainFrame.getListSequenceTabPanel().get(i).getDeletelabel().setIcon(new ImageIcon("resources\\icons\\22x22\\beforeClose.png"));
						}
					}
					 }
				}
			}
		});
		
	}
	   private void wakeupPanel()
	   	{
	   		mainFrame.getpanel().setVisible(true);
	   		mainFrame.getinformationPanel().setVisible(true);
	   		mainFrame.getbotoomJSplitPane().setDividerLocation(0.7);
	   		mainFrame.getbotoomJSplitPane().setDividerSize(4);
	   		mainFrame.getReduceOrEnlargePanel().setVisible(true);
//	   		if(mainFrame.getsteponeButton().getbottompanel().getComponentCount() != 0)
//	   			mainFrame.getsteponeButton().getbottompanel().getComponent(0).setVisible(true);
	   	}
	   
	public JTree getSequencetree() {
		return sequencetree;
	}
    
	public DefaultTreeModel getSequencetreemodel() {
		return this.sequencetreemodel;
	}

	public DefaultMutableTreeNode getSequencetreerootnode() {
		return sequencetreerootnode;
	}
	public Map<DefaultMutableTreeNode, JPanel> getHashMap() {
		return hashMap;
	}
	
	
}
