package com.horstmann.violet.application.StepTwoCaseExpand;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

public class StepTwoCaseExpandTree extends JTree{
	private static DefaultTreeModel CaseTreeModel;
	private static DefaultMutableTreeNode CaseTreerootnode;
	public StepTwoCaseExpandTree()
	{
		init();
	}
	private void init()
	{
		CaseTreerootnode = new DefaultMutableTreeNode();
		CaseTreeModel = new DefaultTreeModel(CaseTreerootnode);
		DefaultTreeCellRenderer cellRender=new DefaultTreeCellRenderer();
		cellRender.setBackgroundNonSelectionColor(new Color(233,233,233));
		cellRender.setFont(new Font("ËÎÌו", Font.PLAIN, 16));
        this.setBackground(new Color(233,233,233));
        this.setCellRenderer(cellRender);
        this.setRootVisible(false);
        this.setShowsRootHandles(true);
        
        this.setModel(CaseTreeModel);
        CaseTreeModel.insertNodeInto(new DefaultMutableTreeNode(111), CaseTreerootnode, CaseTreerootnode.getChildCount());
        caseTreeListen();
	}
	private void caseTreeListen()
	{
		
	}
}
