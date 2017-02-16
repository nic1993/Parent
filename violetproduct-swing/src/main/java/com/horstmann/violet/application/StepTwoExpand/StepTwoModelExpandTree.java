package com.horstmann.violet.application.StepTwoExpand;

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

public class StepTwoModelExpandTree extends JPanel{

	private MainFrame mainFrame;

	private JLabel sequenceLabel;
	private Map<DefaultMutableTreeNode, JPanel> hashMap;
	private JTree ModelExpandTree;
	private static DefaultTreeModel ModelExpandTreeModel;
	private static DefaultMutableTreeNode ModelExpandTreeRoot;
	public StepTwoModelExpandTree( MainFrame mainFrame){
		this.mainFrame=mainFrame;
		this.setBackground(new Color(233,233,233));
		init();
		this.setLayout(new GridLayout());
		this.add(ModelExpandTree);
			
	}

	private void init() {
		// TODO Auto-generated method stub
		hashMap = new HashMap<DefaultMutableTreeNode,JPanel>();
		ModelExpandTreeRoot=new DefaultMutableTreeNode("用例模型扩展列表");
		ModelExpandTreeModel=new DefaultTreeModel(ModelExpandTreeRoot);
//		DefaultMutableTreeNode usecasedeDefaultMutableTreeNode = new DefaultMutableTreeNode("usec_ase1");
//		sequencetreemodel.insertNodeInto(usecasedeDefaultMutableTreeNode, sequencetreerootnode, sequencetreerootnode.getChildCount());
//		DefaultMutableTreeNode nodeDefaultMutableTreeNode = new DefaultMutableTreeNode("usecase1");
//		sequencetreemodel.insertNodeInto(nodeDefaultMutableTreeNode, usecasedeDefaultMutableTreeNode, usecasedeDefaultMutableTreeNode.getChildCount());
//		DefaultMutableTreeNode sequenceDefaultMutableTreeNode1 = new DefaultMutableTreeNode("scene1");
//		DefaultMutableTreeNode sequenceDefaultMutableTreeNode2 = new DefaultMutableTreeNode("scene2");
//		sequencetreemodel.insertNodeInto(sequenceDefaultMutableTreeNode1, nodeDefaultMutableTreeNode, nodeDefaultMutableTreeNode.getChildCount());
//		sequencetreemodel.insertNodeInto(sequenceDefaultMutableTreeNode2, nodeDefaultMutableTreeNode, nodeDefaultMutableTreeNode.getChildCount());
		ModelExpandTree=new JTree(ModelExpandTreeModel);
		DefaultTreeCellRenderer cellRender=new DefaultTreeCellRenderer();
		cellRender.setBackgroundNonSelectionColor(new Color(233,233,233));
		cellRender.setFont(new Font("宋体", Font.PLAIN, 16));
		ModelExpandTree.setOpaque(false);
		ModelExpandTree.setCellRenderer(cellRender);	
	}
	   private void wakeupPanel()
	   	{

	   	}
	   
	public JTree getSequencetree() {
		return ModelExpandTree;
	}
    
	public DefaultTreeModel getSequencetreemodel() {
		return this.ModelExpandTreeModel;
	}

	public DefaultMutableTreeNode getSequencetreerootnode() {
		return ModelExpandTreeRoot;
	}
	public Map<DefaultMutableTreeNode, JPanel> getHashMap() {
		return hashMap;
	}
	
	
}
