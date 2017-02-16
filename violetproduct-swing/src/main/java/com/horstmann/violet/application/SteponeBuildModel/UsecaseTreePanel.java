package com.horstmann.violet.application.SteponeBuildModel;

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

public class UsecaseTreePanel extends JPanel implements Cloneable{
	private MainFrame mainFrame;
	private FileMenu fileMenu;
	private JLabel usecasejJLabel;
	public JPopupMenu popupMenu;
	public JMenuItem newDiagram;
	public JMenuItem importDiagram;
	private Map<DefaultMutableTreeNode, JPanel> hashMap;
	private static Map<DefaultMutableTreeNode, UseCaseNode> INodeMap;
	private Set<DefaultMutableTreeNode> treeNodes;
	public  JTree usecaseTree;
	private  static DefaultTreeModel usecasetreemodel;
	private static DefaultMutableTreeNode usecasetreerootnode;
	
    private Collection<INode> nodes; //用例图中所有的节点
    private IWorkspace usecaseWorkspace; //当前选择的用例图
    private JPanel currentUsecasePanel;
    private DefaultMutableTreeNode currentUsecaseNode;
    private MyMouseAdapter mouseAdapter;
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
		INodeMap = new HashMap<DefaultMutableTreeNode, UseCaseNode>();
		usecasetreerootnode=new DefaultMutableTreeNode("用例图");
		usecasetreemodel=new DefaultTreeModel(usecasetreerootnode);
		usecaseTree=new JTree(usecasetreemodel);
		DefaultTreeCellRenderer cellRender=new DefaultTreeCellRenderer();
		cellRender.setBackgroundNonSelectionColor(new Color(233,233,233));
		cellRender.setFont(new Font("宋体", Font.PLAIN, 16));
		usecaseTree.setBackground(new Color(233,233,233));
		
		usecaseTree.setCellRenderer(cellRender);
		usecasejJLabel = new JLabel("用例图是指由参与者（Actor）、用例（Use Case）以及它们之间的关系构成的用于描述系统功能的视图。");
		usecasejJLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		usecaseTree.addMouseListener(new MouseAdapter(){
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
					mainFrame.getUsecaseTree().setVisible(true);
					
				}
				
				if(e.getButton()==e.BUTTON3 ){
					if(((DefaultMutableTreeNode)usecaseTree.getLastSelectedPathComponent()).equals(usecasetreerootnode)){
					popupMenu = new JPopupMenu();
					newDiagram = new JMenuItem("新建     ",new ImageIcon("resources/icons/16x16/new.png"));
					importDiagram = new JMenuItem("导入    ",new ImageIcon("resources/icons/16x16/open.png"));
					newDiagram.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK));
					importDiagram.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK));
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
				if(((DefaultMutableTreeNode)usecaseTree.getLastSelectedPathComponent()).getLevel() == 2)
				{
					if(((DefaultMutableTreeNode)usecaseTree.getLastSelectedPathComponent()).getParent().getParent().equals(usecasetreerootnode))
					{
						DefaultMutableTreeNode UseCaseNode =(DefaultMutableTreeNode)(usecaseTree.getLastSelectedPathComponent());
						popupMenu = new JPopupMenu();
						newDiagram = new JMenuItem("新建",new ImageIcon("resources/icons/16x16/new.png"));
						importDiagram = new JMenuItem("导入");
						popupMenu.add(newDiagram);
						popupMenu.add(importDiagram);
						popupMenu.show(e.getComponent(), e.getX(), e.getY());
						newDiagram.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								((JMenu) fileMenu.getFileNewMenu().getItem(1)).getItem(3).doClick();
								DefaultMutableTreeNode sequenceTreeNode = mainFrame.getsequencetree().getSequencetreerootnode();
								int count = sequenceTreeNode.getChildCount();
								String label = ((DefaultMutableTreeNode) sequenceTreeNode.getChildAt(count - 1)).toString();
								DefaultMutableTreeNode usecaseChildNode = new DefaultMutableTreeNode(label);
								usecasetreemodel.insertNodeInto(usecaseChildNode, ((DefaultMutableTreeNode)usecaseTree.getLastSelectedPathComponent()), ((DefaultMutableTreeNode)usecaseTree.getLastSelectedPathComponent()).getChildCount());
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
				}
				}

				if(e.getClickCount() == 2){
					if(((DefaultMutableTreeNode)usecaseTree.getLastSelectedPathComponent()).getParent() == null)
					{
						
					}
					else if(((DefaultMutableTreeNode)usecaseTree.getLastSelectedPathComponent()).getParent().equals(usecasetreerootnode)){
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
			}
		});
		
        new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					while (true) {
						Iterator iter = INodeMap.entrySet().iterator();
						while (iter.hasNext()) {
							Map.Entry entry = (Map.Entry) iter.next();
							DefaultMutableTreeNode useceseTreeNode = (DefaultMutableTreeNode) entry.getKey();
							UseCaseNode useceseNode = (UseCaseNode) entry.getValue();
							useceseTreeNode.setUserObject(useceseNode.getName().getText());
							usecaseTree.validate();
							usecaseTree.revalidate();
						}
					    AddNodeBehavior.lock.take();	//加锁				
						int currentPos = mainFrame.getStepOneCenterUseCaseTabbedPane().getSelectedIndex(); 
						usecaseWorkspace = mainFrame.getUseCaseWorkspaceList().get(currentPos);//获取当前用例图workspace
						currentUsecasePanel = mainFrame.getStepOneCenterUseCaseTabbedPane().getUsecaseDiagramTabbedPane().get(currentPos); //获取当前Jpanel
						currentUsecaseNode = getKey(mainFrame.getUsecaseTree().getHashMap(), currentUsecasePanel); //获取当前用例图树节点
						nodes = usecaseWorkspace.getGraphFile().getGraph().getAllNodes();
		                List<INode> allnodes=new ArrayList<INode>();
		                for(INode node: nodes)
		                {
		                     allnodes.add(node);          		 
		                }
		                Icon icon = new ImageIcon("resources/icons/22x22/open.png");
		            	String str = (String) JOptionPane.showInputDialog(null,"请输入用例名称:\n","title",JOptionPane.PLAIN_MESSAGE,icon,null,"在这输入");
                        UseCaseNode usecaseNode = (UseCaseNode) allnodes.get(0);
                        usecaseNode.getName().setText(str);
		                String usecaseLabel = ((UseCaseNode) usecaseNode).getName().getText();
		                DefaultMutableTreeNode usecaseTreeNode = new DefaultMutableTreeNode(usecaseLabel);
		                usecasetreemodel.insertNodeInto(usecaseTreeNode, currentUsecaseNode, currentUsecaseNode.getChildCount());
		                TreePath treePath = new TreePath(usecasetreerootnode.getPath());
		                usecaseTree.makeVisible(treePath);
		                usecaseTree.expandPath(treePath);
					    INodeMap.put(usecaseTreeNode,(UseCaseNode) allnodes.get(0));
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
        
        new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					Iterator iter = INodeMap.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry entry = (Map.Entry) iter.next();
						DefaultMutableTreeNode useceseTreeNode = (DefaultMutableTreeNode) entry.getKey();
						UseCaseNode useceseNode = (UseCaseNode) entry.getValue();
						useceseTreeNode.setUserObject(useceseNode.getName().getText());
						usecaseTree.repaint();
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					iter = null;
				}	
			}
		}).start();
        
        new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					try {
						AbstractGraph.lock.take();
							int currentPos = mainFrame.getStepOneCenterUseCaseTabbedPane().getSelectedIndex(); 
							usecaseWorkspace = mainFrame.getUseCaseWorkspaceList().get(currentPos);//获取当前用例图workspace
							currentUsecasePanel = mainFrame.getStepOneCenterUseCaseTabbedPane().getUsecaseDiagramTabbedPane().get(currentPos); //获取当前Jpanel
							currentUsecaseNode = getKey(mainFrame.getUsecaseTree().getHashMap(), currentUsecasePanel); //获取当前用例图树节点
							nodes = usecaseWorkspace.getGraphFile().getGraph().getAllNodes();
							
							Iterator iter = INodeMap.entrySet().iterator();
							while (iter.hasNext()) {
								Map.Entry entry = (Map.Entry) iter.next();
								DefaultMutableTreeNode useceseTreeNode = (DefaultMutableTreeNode) entry.getKey();
								UseCaseNode useceseNode = (UseCaseNode) entry.getValue();
                                if(!nodes.contains(useceseNode))
                                {
                                	usecasetreemodel.removeNodeFromParent(useceseTreeNode);
                                	INodeMap.remove(useceseTreeNode);
                                }
								usecaseTree.repaint();
							}
							
						}
						 catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				}
			}
		}).start();
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
	   
	 private static DefaultMutableTreeNode getKey(Map<DefaultMutableTreeNode, JPanel> map,JPanel panel)
	    {
	    	Iterator<DefaultMutableTreeNode> it = map.keySet().iterator();
	    	while (it.hasNext()) {
				DefaultMutableTreeNode key = it.next();
				if(map.get(key).equals(panel))
					return key;
			}
	    	return null;
	    }
	public UsecaseTreePanel clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (UsecaseTreePanel) super.clone();
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
	public MouseListener getMouseListener()
	{
	    return this.mouseAdapter;
	}
}
