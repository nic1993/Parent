package com.horstmann.violet.application.Stepone;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.menu.FileMenu;

public class UsecaseTreePanel extends JPanel{
	private MainFrame mainFrame;
	private FileMenu fileMenu;
	private JLabel usecasejJLabel;
	public JPopupMenu popupMenu;
	public JMenuItem newDiagram;
	public JMenuItem importDiagram;
	private Map<DefaultMutableTreeNode, JPanel> hashMap;

	public JTree usecaseTree;
	private  static DefaultTreeModel usecasetreemodel;
	private static DefaultMutableTreeNode usecasetreerootnode;

	public UsecaseTreePanel(FileMenu fileMenu, MainFrame mainFrame){
		
		this.mainFrame=mainFrame;
		this.fileMenu=fileMenu;
		this.setBackground(new Color(233,233,233));
		init();
		
//		usecaseTree.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		this.setLayout(new GridLayout());
		this.add(usecaseTree);
			
	}

	private void init() {
		// TODO Auto-generated method stub
		hashMap = new HashMap<DefaultMutableTreeNode,JPanel>();
		usecasetreerootnode=new DefaultMutableTreeNode("用例图列表");
		usecasetreemodel=new DefaultTreeModel(usecasetreerootnode);
		usecaseTree=new JTree(usecasetreemodel);
		DefaultTreeCellRenderer cellRender=new DefaultTreeCellRenderer();
		cellRender.setBackgroundNonSelectionColor(new Color(233,233,233));
		cellRender.setFont(new Font("宋体", Font.PLAIN, 16));
		usecaseTree.setOpaque(false);
		usecaseTree.setCellRenderer(cellRender);
		usecasejJLabel = new JLabel("用例图是指由参与者（Actor）、用例（Use Case）以及它们之间的关系构成的用于描述系统功能的视图。");
		usecasejJLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		usecaseTree.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getButton() == e.BUTTON1)
				{
					mainFrame.getOpreationPart().validate();
					wakeupPanel();
					mainFrame.getCenterTabPanel().removeAll();
					mainFrame.getCenterTabPanel().add(mainFrame.getStepOneCenterUseCaseTabbedPane());
					mainFrame.getCenterTabPanel().updateUI();	
					mainFrame.getpanel().removeAll();
					mainFrame.getpanel().setLayout(new GridLayout(1, 1));
					mainFrame.getpanel().add(mainFrame.getOperationButton());
					mainFrame.getOperationButton().getOtherPanel().removeAll();
					mainFrame.getOperationButton().getOtherPanel().setLayout(new GridBagLayout());
					mainFrame.getOperationButton().getOtherPanel().add(usecasejJLabel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10,10,10,10));
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
							((JMenu) fileMenu.getFileNewMenu().getItem(1)).getItem(0).doClick();	
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
					 DefaultMutableTreeNode node = (DefaultMutableTreeNode) usecaseTree
							.getLastSelectedPathComponent();
					 if(!node.equals(usecasetreerootnode)){
					mainFrame.getStepOneCenterUseCaseTabbedPane().setSelectedComponent(hashMap.get(node));
					int index = mainFrame.getStepOneCenterUseCaseTabbedPane().getSelectedIndex();
					for(int i = 0;i < mainFrame.getListUsecaseTabPanel().size();i++)
					{
						if(i!= index)
						{
							mainFrame.getListUsecaseTabPanel().get(i).getPanel().setBackground(new Color(246,246,246));
							mainFrame.getListUsecaseTabPanel().get(i).getDeletelabel().setIcon(null);
						}
						else {
							mainFrame.getListUsecaseTabPanel().get(i).getPanel().setBackground(Color.white);
							mainFrame.getListUsecaseTabPanel().get(i).getDeletelabel().setIcon(new ImageIcon("resources\\icons\\22x22\\beforeClose.png"));
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
	   

	public JTree getUsecasetree() {
		return usecaseTree;
	}

	public DefaultTreeModel getUsecasetreemodel() {
		return this.usecasetreemodel;
	}

	public DefaultMutableTreeNode getUsecasetreerootnode() {
		return usecasetreerootnode;
	}
	public Map<DefaultMutableTreeNode, JPanel> getHashMap() {
		return hashMap;
	}
}
