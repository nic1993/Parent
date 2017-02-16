package com.horstmann.violet.application.StepTwoExpand;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import com.horstmann.violet.application.SteponeBuildModel.UsecaseTreePanel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.menu.FileMenu;
import com.horstmann.violet.product.diagram.abstracts.AbstractGraph;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.abstracts.property.MultiLineString;
import com.horstmann.violet.product.diagram.usecase.UseCaseNode;
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.editorpart.EditorPart;
import com.horstmann.violet.workspace.editorpart.behavior.AddNodeBehavior;
import com.horstmann.violet.workspace.editorpart.behavior.CutCopyPasteBehavior;

public class StepTwoUsecaseModel extends JPanel{
	private MainFrame mainFrame;
	private FileMenu fileMenu;
	private JLabel usecasejJLabel;
	public JPopupMenu popupMenu;
	public JMenuItem newDiagram;
	private JTree usecaseTree;
	private UsecaseTreePanel clonedPanel;
	private static DefaultTreeModel usecasetreemodel;
	private static DefaultMutableTreeNode usecasetreerootnode;
    private JPanel currentUsecasePanel;

	public StepTwoUsecaseModel( MainFrame mainFrame){
		
		this.mainFrame=mainFrame;
		this.setBackground(new Color(233,233,233));
		init();
		this.setLayout(new GridLayout(1,1));
		this.add(usecaseTree);
	}

	private void init() {
		// TODO Auto-generated method stub
		usecasetreerootnode=new DefaultMutableTreeNode("用例图");
		usecasetreemodel=new DefaultTreeModel(usecasetreerootnode);
		usecaseTree = new JTree(usecasetreemodel);
		//后期修改
		DefaultMutableTreeNode usecaseNode1 = new DefaultMutableTreeNode("usecase1");
		DefaultMutableTreeNode usec_case1 = new DefaultMutableTreeNode("usec_ase1");
		DefaultMutableTreeNode usec_case2 = new DefaultMutableTreeNode("usec_ase2");
		DefaultMutableTreeNode scence1 = new DefaultMutableTreeNode("scence1");
		DefaultMutableTreeNode scence2 = new DefaultMutableTreeNode("scence2");
		DefaultMutableTreeNode scence3 = new DefaultMutableTreeNode("scence3");
		DefaultMutableTreeNode scence4 = new DefaultMutableTreeNode("scence4");
		
		DefaultMutableTreeNode usecaseNode2 = new DefaultMutableTreeNode("usecase2");
		
		usecasetreemodel.insertNodeInto(usecaseNode1, usecasetreerootnode, usecasetreerootnode.getChildCount());
		
		usecasetreemodel.insertNodeInto(usec_case1, usecaseNode1, usecaseNode1.getChildCount());
		usecasetreemodel.insertNodeInto(usec_case2, usecaseNode1, usecaseNode1.getChildCount());
		usecasetreemodel.insertNodeInto(scence1, usec_case1, usec_case1.getChildCount());
		usecasetreemodel.insertNodeInto(scence2, usec_case1, usec_case1.getChildCount());
		usecasetreemodel.insertNodeInto(scence3, usec_case1, usec_case1.getChildCount());
		
		usecasetreemodel.insertNodeInto(scence4, usec_case2, usec_case2.getChildCount());
		
		

		DefaultTreeCellRenderer cellRender=new DefaultTreeCellRenderer();
		cellRender.setBackgroundNonSelectionColor(new Color(233,233,233));
		cellRender.setFont(new Font("宋体", Font.PLAIN, 16));
		usecaseTree.setBackground(new Color(233,233,233));		
		usecaseTree.setCellRenderer(cellRender);
		usecaseTree.removeMouseListener(mainFrame.getUsecaseTree().getMouseListener());
	}


	
	 private void wakeupPanel()
	   	{

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
}
