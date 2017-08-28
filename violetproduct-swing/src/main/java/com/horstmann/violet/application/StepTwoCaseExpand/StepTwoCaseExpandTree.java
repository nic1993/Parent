package com.horstmann.violet.application.StepTwoCaseExpand;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

public class StepTwoCaseExpandTree extends JPanel{
	private JScrollPane jScrollPanel;
	private JTree jTree;
	private DefaultTreeModel CaseTreeModel;
	private DefaultMutableTreeNode CaseTreerootnode;
	public StepTwoCaseExpandTree()
	{
		init();
		this.setLayout(new GridLayout(1, 1));
		this.add(jTree);
	}
	private void init()
	{
		CaseTreerootnode = new DefaultMutableTreeNode(222);
		CaseTreeModel = new DefaultTreeModel(CaseTreerootnode);
		jTree = new JTree(CaseTreeModel);
//		CaseTreeModel.insertNodeInto(new DefaultMutableTreeNode(111), CaseTreerootnode, CaseTreerootnode.getChildCount());
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
        
        jScrollPanel = new JScrollPane(jTree);
	}
	private void caseTreeListen()
	{
		
	}
	
	public JTree getjTree() {
		return jTree;
	}
	public  DefaultTreeModel getCaseTreeModel() {
		return CaseTreeModel;
	}
	public  DefaultMutableTreeNode getCaseTreerootnode() {
		return CaseTreerootnode;
	}
	
}
