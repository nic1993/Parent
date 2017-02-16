package com.horstmann.violet.application.StepTwoExchange;

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
import com.horstmann.violet.workspace.editorpart.behavior.AddNodeBehavior;

public class StepTwoExchangeMarkovTree extends JPanel{

	private MainFrame mainFrame;

	private Map<DefaultMutableTreeNode, JPanel> hashMap;
	private JTree exchangeTree;
	private static DefaultTreeModel exchangeTreeModel;
	private static DefaultMutableTreeNode exchangeTreeRoot;
	public StepTwoExchangeMarkovTree(MainFrame mainFrame){
		this.mainFrame=mainFrame;
		this.setBackground(new Color(233,233,233));
		init();
		this.setLayout(new GridLayout());
		exchangeTree.setRootVisible(false);
		this.add(exchangeTree);		
	}

	private void init() {
		// TODO Auto-generated method stub
		hashMap = new HashMap<DefaultMutableTreeNode,JPanel>();
		exchangeTreeRoot=new DefaultMutableTreeNode("Markov");
		exchangeTreeModel=new DefaultTreeModel(exchangeTreeRoot);
		exchangeTree.setRootVisible(false);
//		DefaultMutableTreeNode usecasedeDefaultMutableTreeNode = new DefaultMutableTreeNode("usec_ase1");
//		sequencetreemodel.insertNodeInto(usecasedeDefaultMutableTreeNode, sequencetreerootnode, sequencetreerootnode.getChildCount());
//		DefaultMutableTreeNode nodeDefaultMutableTreeNode = new DefaultMutableTreeNode("usecase1");
//		sequencetreemodel.insertNodeInto(nodeDefaultMutableTreeNode, usecasedeDefaultMutableTreeNode, usecasedeDefaultMutableTreeNode.getChildCount());
//		DefaultMutableTreeNode sequenceDefaultMutableTreeNode1 = new DefaultMutableTreeNode("scene1");
//		DefaultMutableTreeNode sequenceDefaultMutableTreeNode2 = new DefaultMutableTreeNode("scene2");
//		sequencetreemodel.insertNodeInto(sequenceDefaultMutableTreeNode1, nodeDefaultMutableTreeNode, nodeDefaultMutableTreeNode.getChildCount());
//		sequencetreemodel.insertNodeInto(sequenceDefaultMutableTreeNode2, nodeDefaultMutableTreeNode, nodeDefaultMutableTreeNode.getChildCount());
		exchangeTree=new JTree(exchangeTreeModel);
		DefaultTreeCellRenderer cellRender=new DefaultTreeCellRenderer();
		cellRender.setBackgroundNonSelectionColor(new Color(233,233,233));
		cellRender.setFont(new Font("ËÎÌו", Font.PLAIN, 16));
		exchangeTree.setOpaque(false);
		exchangeTree.setCellRenderer(cellRender);	
	}
	   private void wakeupPanel()
	   	{

	   	}
	   
	public JTree getSequencetree() {
		return exchangeTree;
	}
    
	public DefaultTreeModel getSequencetreemodel() {
		return this.exchangeTreeModel;
	}

	public DefaultMutableTreeNode getSequencetreerootnode() {
		return exchangeTreeRoot;
	}
	public Map<DefaultMutableTreeNode, JPanel> getHashMap() {
		return hashMap;
	}
	
	
}
