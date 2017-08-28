package com.horstmann.violet.application.StepTwoExchange;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

public class StepTwoExchangeTree extends JPanel{
	private JScrollPane jScrollPane;
	private  JTree jTree;
	private  DefaultTreeModel TreeModel;
	private  DefaultMutableTreeNode Treerootnode;
	public StepTwoExchangeTree()
	{
		init();
		this.setLayout(new GridLayout(1, 1));
		this.add(jTree);
	}
	private void init()
	{
		Treerootnode = new DefaultMutableTreeNode(333);
		TreeModel = new DefaultTreeModel(Treerootnode);
		jTree = new JTree(TreeModel);
//		TreeModel.insertNodeInto(new DefaultMutableTreeNode(111), Treerootnode, Treerootnode.getChildCount());
		DefaultTreeCellRenderer cellRender=new DefaultTreeCellRenderer();
		cellRender.setBackgroundNonSelectionColor(new Color(233,233,233));
		cellRender.setFont(new Font("ËÎÌו", Font.PLAIN, 16));
		jTree.setBackground(new Color(233,233,233));
		jTree.setCellRenderer(cellRender);
		jTree.expandRow(0);
		jTree.setShowsRootHandles(true);
		jTree.putClientProperty("JTree.lineStyle", "Angled");
		jTree.setRootVisible(false);  
        caseTreeListen();
        
        jScrollPane = new JScrollPane(jTree);
	}
	private void caseTreeListen()
	{
		
	}
	public JTree getJTree()
	{
		return jTree;
	}
	public  DefaultTreeModel getTreeModel() {
		return TreeModel;
	}
	public  DefaultMutableTreeNode getTreerootnode() {
		return Treerootnode;
	}
	
}
