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
import java.util.List;
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

public class StepTwoCaseExpandTree extends JPanel{

	private MainFrame mainFrame;

	private JLabel caseExpandLabel;
	private Map<DefaultMutableTreeNode, ScenceTabelPanel> hashMap;
	private Map<String, MatrixData> matrixMap;
	private JTree UseCaseExpandTree;
	private  DefaultTreeModel UseCaseExpandTreeModel;
	private  DefaultMutableTreeNode UseCaseExpandTreeRoot;
	public StepTwoCaseExpandTree(MainFrame mainFrame){
		this.mainFrame = mainFrame;
		this.setBackground(new Color(233,233,233));
		init();
		this.setLayout(new GridLayout());
		this.add(UseCaseExpandTree);		
	}
	private void init() {
		// TODO Auto-generated method stub
		matrixMap = new HashMap<String, MatrixData>();
		hashMap = new HashMap<DefaultMutableTreeNode,ScenceTabelPanel>();
		UseCaseExpandTreeRoot=new DefaultMutableTreeNode("用例扩展列表");
		UseCaseExpandTreeModel=new DefaultTreeModel(UseCaseExpandTreeRoot);
//		DefaultMutableTreeNode usecasedeDefaultMutableTreeNode = new DefaultMutableTreeNode("usec_ase1");
//		sequencetreemodel.insertNodeInto(usecasedeDefaultMutableTreeNode, sequencetreerootnode, sequencetreerootnode.getChildCount());
//		DefaultMutableTreeNode nodeDefaultMutableTreeNode = new DefaultMutableTreeNode("usecase1");
//		sequencetreemodel.insertNodeInto(nodeDefaultMutableTreeNode, usecasedeDefaultMutableTreeNode, usecasedeDefaultMutableTreeNode.getChildCount());
//		DefaultMutableTreeNode sequenceDefaultMutableTreeNode1 = new DefaultMutableTreeNode("scene1");
//		DefaultMutableTreeNode sequenceDefaultMutableTreeNode2 = new DefaultMutableTreeNode("scene2");
//		sequencetreemodel.insertNodeInto(sequenceDefaultMutableTreeNode1, nodeDefaultMutableTreeNode, nodeDefaultMutableTreeNode.getChildCount());
//		sequencetreemodel.insertNodeInto(sequenceDefaultMutableTreeNode2, nodeDefaultMutableTreeNode, nodeDefaultMutableTreeNode.getChildCount());
		UseCaseExpandTree=new JTree(UseCaseExpandTreeModel);
		DefaultTreeCellRenderer cellRender=new DefaultTreeCellRenderer();
		cellRender.setBackgroundNonSelectionColor(new Color(233,233,233));
		cellRender.setFont(new Font("宋体", Font.PLAIN, 16));
		UseCaseExpandTree.setOpaque(false);
		UseCaseExpandTree.setCellRenderer(cellRender);	
	}
	   private void wakeupPanel()
	   	{

	   	}
	   
	public JTree getUseCaseExpandTree() {
		return UseCaseExpandTree;
	}
    
	public DefaultTreeModel getUseCaseExpandTreeModel() {
		return this.UseCaseExpandTreeModel;
	}

	public DefaultMutableTreeNode getUseCaseExpandTreeRoot() {
		return UseCaseExpandTreeRoot;
	}
	public Map<DefaultMutableTreeNode, ScenceTabelPanel> getHashMap() {
		return hashMap;
	}

	public Map<String, MatrixData> getMatrixMap() {
		return matrixMap;
	}
	
	
	
}
