package com.horstmann.violet.application.StepTwoEvaluate;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

public class StepTwoEvaluateTree extends JPanel{
	private JScrollPane jScrollPane;
	private  JTree jTree;
	private  DefaultTreeModel EvaluateTreeModel;
	private  DefaultMutableTreeNode EvaluateTreerootnode;
	public StepTwoEvaluateTree()
	{
		init();
		this.setLayout(new GridLayout(1, 1));
		this.add(jTree);
	}
	private void init()
	{
		EvaluateTreerootnode = new DefaultMutableTreeNode(111);
		EvaluateTreeModel = new DefaultTreeModel(EvaluateTreerootnode);
//		EvaluateTreeModel.insertNodeInto(new DefaultMutableTreeNode(111), EvaluateTreerootnode, EvaluateTreerootnode.getChildCount());
		jTree = new JTree(EvaluateTreeModel);
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
	
	public JTree getjTree() {
		return jTree;
	}
	public  DefaultTreeModel getEvaluateTreeModel() {
		return EvaluateTreeModel;
	}
	public  DefaultMutableTreeNode getEvaluateTreerootnode() {
		return EvaluateTreerootnode;
	}
	
}
