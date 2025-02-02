package com.horstmann.violet.application.StepOneBuildModel;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JScrollPane;
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
import com.horstmann.violet.workspace.WorkspacePanel;
import com.horstmann.violet.workspace.editorpart.EditorPart;
import com.horstmann.violet.workspace.editorpart.behavior.AddNodeBehavior;
import com.horstmann.violet.workspace.editorpart.behavior.CutCopyPasteBehavior;
import com.horstmann.violet.workspace.editorpart.behavior.EditSelectedBehavior;

public class UsecaseTreePanel extends JPanel implements Cloneable{
	private MainFrame mainFrame;
	private FileMenu fileMenu;
	private ModelPanel modelPanel;
	private JLabel usecasejJLabel;
	public JPopupMenu popupMenu;
	public JMenuItem newDiagram;
	public JMenuItem importDiagram;
	private Map<DefaultMutableTreeNode, IWorkspace> hashMap;
	private Map<DefaultMutableTreeNode, UseCaseNode> NodeMap;
	private Set<DefaultMutableTreeNode> treeNodes;
	private JScrollPane jScrollPane;
	private JTree usecaseTree;
	private DefaultTreeModel usecasetreemodel;
	private DefaultMutableTreeNode usecasetreerootnode;
	
    private Collection<INode> nodes; //用例图中所有的节点
    private IWorkspace usecaseWorkspace; //当前选择的用例图
    private IWorkspace currentUsecaseWorkspace;
    private DefaultMutableTreeNode currentUsecaseNode;
//    private MyMouseAdapter mouseAdapter;
	public UsecaseTreePanel(FileMenu fileMenu, MainFrame mainFrame,ModelPanel modelPanel){
		
		this.mainFrame=mainFrame;
		this.fileMenu=fileMenu;
		this.modelPanel = modelPanel;
		this.setBackground(new Color(233,233,233));
		init();
		this.setLayout(new GridLayout());
		this.add(usecaseTree);
	}

	private void init() {
		// TODO Auto-generated method stub
		hashMap = new HashMap<DefaultMutableTreeNode,IWorkspace>();
		NodeMap = new HashMap<DefaultMutableTreeNode, UseCaseNode>();
		
		usecasetreerootnode=new DefaultMutableTreeNode("用例图");
		usecasetreemodel=new DefaultTreeModel(usecasetreerootnode);
		usecaseTree=new JTree(usecasetreemodel);
		DefaultTreeCellRenderer cellRender=new DefaultTreeCellRenderer();
		cellRender.setBackgroundNonSelectionColor(new Color(233,233,233));
		cellRender.setFont(new Font("宋体", Font.PLAIN, 16));
		usecaseTree.setBackground(new Color(233,233,233));
		
		usecaseTree.setCellRenderer(cellRender);
		usecaseTree.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getButton() == e.BUTTON1)
				{
					mainFrame.renewPanel();
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
							mainFrame.setActiveModelPanel(modelPanel);
							((JMenu) fileMenu.getFileNewMenu().getItem(1)).getItem(0).doClick();
							mainFrame.renewPanel();
							
							mainFrame.getpanel().removeAll();
							mainFrame.getpanel().setLayout(new GridLayout(1, 1));
							mainFrame.getpanel().add(mainFrame.getStepOperationButton());
							mainFrame.getStepOperationButton().getPromptLabel().removeAll();
							mainFrame.getStepOperationButton().getPromptLabel().setText("用例图是指由参与者（Actor）、用例（Use Case）以及它们之间的关系构成的用于描述系统功能的视图。");
							mainFrame.getpanel().updateUI();
							mainFrame.getReduceOrEnlargePanel().setLayout(new GridLayout(1, 1));
							mainFrame.getReduceOrEnlargePanel().add(mainFrame.getstepOneCenterRightPanel());
							mainFrame.getOpreationPart().revalidate();
							mainFrame.renewPanel();
						}
					});
					importDiagram.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							mainFrame.setActiveModelPanel(modelPanel);
							fileMenu.fileOpenItem.doClick();	
							mainFrame.renewPanel();
							
							mainFrame.getpanel().removeAll();
							mainFrame.getpanel().setLayout(new GridLayout(1, 1));
							mainFrame.getpanel().add(mainFrame.getStepOperationButton());
							mainFrame.getStepOperationButton().getPromptLabel().removeAll();
							mainFrame.getStepOperationButton().getPromptLabel().setText("用例图是指由参与者（Actor）、用例（Use Case）以及它们之间的关系构成的用于描述系统功能的视图。");
							mainFrame.getpanel().updateUI();
							mainFrame.getReduceOrEnlargePanel().setLayout(new GridLayout(1, 1));
							mainFrame.getReduceOrEnlargePanel().add(mainFrame.getstepOneCenterRightPanel());
							mainFrame.getOpreationPart().revalidate();
							mainFrame.renewPanel();
						}
					});	
				}
					
				
				if(((DefaultMutableTreeNode)usecaseTree.getLastSelectedPathComponent()).getLevel() == 2)
				{
					if(((DefaultMutableTreeNode)usecaseTree.getLastSelectedPathComponent()).getParent().getParent().equals(usecasetreerootnode))
					{
						DefaultMutableTreeNode UseCaseNode =(DefaultMutableTreeNode)(usecaseTree.getLastSelectedPathComponent());
						popupMenu = new JPopupMenu();
						newDiagram = new JMenuItem("新建     ",new ImageIcon("resources/icons/16x16/new.png"));
						importDiagram = new JMenuItem("导入     ",new ImageIcon("resources/icons/16x16/open.png"));
						newDiagram.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK));
						importDiagram.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK));
						popupMenu.add(newDiagram);
						popupMenu.add(importDiagram);
						popupMenu.show(e.getComponent(), e.getX(), e.getY());
						newDiagram.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								mainFrame.setActiveModelPanel(modelPanel);
								((JMenu) fileMenu.getFileNewMenu().getItem(1)).getItem(3).doClick();
								DefaultMutableTreeNode sequenceTreeNode = modelPanel.getSequenceTreePanel().getSequencetreerootnode();
								int count = sequenceTreeNode.getChildCount();
								String label = ((DefaultMutableTreeNode) sequenceTreeNode.getChildAt(count - 1)).toString();
								DefaultMutableTreeNode usecaseChildNode = new DefaultMutableTreeNode(label);
								usecasetreemodel.insertNodeInto(usecaseChildNode, ((DefaultMutableTreeNode)usecaseTree.getLastSelectedPathComponent()), ((DefaultMutableTreeNode)usecaseTree.getLastSelectedPathComponent()).getChildCount());
								
								mainFrame.getpanel().removeAll();
								mainFrame.getpanel().setLayout(new GridLayout(1, 1));
								mainFrame.getpanel().add(mainFrame.getStepOperationButton());
								mainFrame.getStepOperationButton().getPromptLabel().removeAll();
								mainFrame.getStepOperationButton().getPromptLabel().setText("顺序图是将交互关系表示为一个二维图。纵向是时间轴，时间沿竖线向下延伸。横向轴代表了在协作中各独立对象的类元角色。");
								mainFrame.getpanel().updateUI();
								mainFrame.getReduceOrEnlargePanel().setLayout(new GridLayout(1, 1));
								mainFrame.getReduceOrEnlargePanel().add(mainFrame.getstepOneCenterRightPanel());
								mainFrame.renewPanel();
							}
						});
						importDiagram.addActionListener(new ActionListener() {	
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub		
								mainFrame.setActiveModelPanel(modelPanel);
								fileMenu.fileOpenItem.doClick();
								
								mainFrame.getpanel().removeAll();
								mainFrame.getpanel().setLayout(new GridLayout(1, 1));
								mainFrame.getpanel().add(mainFrame.getStepOperationButton());
								mainFrame.getStepOperationButton().getPromptLabel().removeAll();
								mainFrame.getStepOperationButton().getPromptLabel().setText("顺序图是将交互关系表示为一个二维图。纵向是时间轴，时间沿竖线向下延伸。横向轴代表了在协作中各独立对象的类元角色。");
								mainFrame.getpanel().updateUI();
								mainFrame.getReduceOrEnlargePanel().setLayout(new GridLayout(1, 1));
								mainFrame.getReduceOrEnlargePanel().add(mainFrame.getstepOneCenterRightPanel());
								mainFrame.renewPanel();
							}
						});	
					}	
					
				}
				
				//添加删除标志
				if(((DefaultMutableTreeNode)usecaseTree.getLastSelectedPathComponent()).getLevel() == 1)
				{
					popupMenu = new JPopupMenu();
					newDiagram = new JMenuItem("删除     ",new ImageIcon("resources/icons/16x16/De.png"));
					JMenuItem saveDiagram = new JMenuItem("保存     ",new ImageIcon("resources/icons/16x16/saveas.png"));
					JMenuItem changeName = new JMenuItem("改名");
					newDiagram.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_MASK));
					saveDiagram.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK));
					popupMenu.add(newDiagram);
					popupMenu.add(saveDiagram);
					popupMenu.add(changeName);
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
					newDiagram.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
//							fileMenu.getItem(3).doClick();
							String name = ((DefaultMutableTreeNode)usecaseTree.getLastSelectedPathComponent()).toString();
							IWorkspace removeworkspace = null;
						    //从保存模型中删除模型
							for(IWorkspace workspace : modelPanel.getUseCaseworkspaceList())
							{
								if(workspace.getName().equals(name))
								{
									removeworkspace = workspace;
									if(mainFrame.getActiveWorkspace() != null)
									{
										if(mainFrame.getActiveWorkspace().equals(workspace))
										{
											mainFrame.getCenterTabPanel().removeAll();
										}
									}
								}
							}
							if(removeworkspace != null)
							{
								modelPanel.getUseCaseworkspaceList().remove(removeworkspace);
							}
							//从树中删除模型
							int index = 0;
							int count = usecasetreerootnode.getChildCount();
							for(int i = 0;i < count;i++)
							{
								if(usecasetreerootnode.getChildAt(i).toString().equals(name))
								{
									index = i;
								}
							}
							usecasetreemodel.removeNodeFromParent((MutableTreeNode) usecasetreerootnode.getChildAt(index));
							usecaseTree.repaint();
							mainFrame.renewPanel();
						}
					});
					
					saveDiagram.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							fileMenu.getItem(5).doClick();
						}
					});
					changeName.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							Icon icon = new ImageIcon("resources/icons/22x22/open.png");
							String str = (String) JOptionPane.showInputDialog(null,"请输入名称:\n","名称",JOptionPane.PLAIN_MESSAGE,icon,null,"在这输入");
							if(str.equals("") || str == null)
							{
								JOptionPane.showMessageDialog(null, "工程名称不能为空!","标题",JOptionPane.WARNING_MESSAGE); 
							}
							else {
								((DefaultMutableTreeNode)usecaseTree.getLastSelectedPathComponent()).setUserObject(str);
								
								usecasetreemodel.reload();
								usecaseTree.setSelectionPath(usecaseTree.getSelectionPath());
							}
						}
					});
				}
				if(((DefaultMutableTreeNode)usecaseTree.getLastSelectedPathComponent()).getLevel() == 3)
				{
					System.out.println(1234);
					popupMenu = new JPopupMenu();
					newDiagram = new JMenuItem("删除     ",new ImageIcon("resources/icons/16x16/De.png"));
					popupMenu.add(newDiagram);
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
					newDiagram.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							DefaultMutableTreeNode node = (DefaultMutableTreeNode)usecaseTree.getLastSelectedPathComponent();
							usecasetreemodel.removeNodeFromParent(node);
						}
					});
				}
				mainFrame.renewPanel();
				}
				if(e.getClickCount() == 2){
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) usecaseTree
							.getLastSelectedPathComponent();
					 boolean isExist = false;
					if(!node.equals(usecasetreerootnode)){
						mainFrame.setActiveModelPanel(modelPanel);
						String ucasename = node.toString();
						int tabconut = modelPanel.getUseCaseworkspaceList().size();
						for(int i = 0;i < tabconut;i++)
						 {
							 if(ucasename.equals(modelPanel.getUseCaseworkspaceList().get(i).getName()))
							 {
								 isExist = true;
							 }
						 }
						if(hashMap.get(node) != null && isExist == true)
						 {
							mainFrame.getCenterTabPanel().removeAll();
							mainFrame.getCenterTabPanel().add(hashMap.get(node).getAWTComponent());
							mainFrame.getCenterTabPanel().repaint();
							
							mainFrame.getpanel().removeAll();
							mainFrame.getpanel().setLayout(new GridLayout(1, 1));
							mainFrame.getpanel().add(mainFrame.getStepOperationButton());
							mainFrame.getStepOperationButton().getPromptLabel().removeAll();
							mainFrame.getStepOperationButton().getPromptLabel().setText("用例图是指由参与者（Actor）、用例（Use Case）以及它们之间的关系构成的用于描述系统功能的视图。");
							mainFrame.getpanel().updateUI();
							mainFrame.getReduceOrEnlargePanel().setLayout(new GridLayout(1, 1));
							mainFrame.getReduceOrEnlargePanel().add(mainFrame.getstepOneCenterRightPanel());
							mainFrame.getOpreationPart().revalidate();
						 }
						else if(isExist == false) {
//							 mainFrame.getStepOneCenterUseCaseTabbedPane().addTab(ucasename,new ImageIcon("resources\\icons\\22x22\\tabpicture.png"),hashMap.get(node));
						}

					}
					mainFrame.renewPanel();
				}
			}
		});	
		
		
        new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					while (true ) {
							AddNodeBehavior.lock.take();	//加锁				
							if(modelPanel.hashCode() == mainFrame.getActiveModelPanel().hashCode()){
						    currentUsecaseWorkspace = ((WorkspacePanel)mainFrame.getCenterTabPanel().getComponent(0)).getWorkspace();//获取当前用例图workspace
							currentUsecaseNode = getKey(hashMap, currentUsecaseWorkspace); //获取当前用例图树节点
							nodes = currentUsecaseWorkspace.getGraphFile().getGraph().getAllNodes();
			                List<INode> allnodes=new ArrayList<INode>();
			                for(INode node: nodes)
			                {
			                     allnodes.add(node);          		 
			                }
			                Icon icon = new ImageIcon("resources/icons/22x22/open.png");
			            	String str = (String) JOptionPane.showInputDialog(null,"请输入用例名称:\n","用例名称",JOptionPane.PLAIN_MESSAGE,icon,null,"在这输入");
	                        UseCaseNode usecaseNode = (UseCaseNode) allnodes.get(0);
	                        if(str == null)
	                        {
	                        	str = " ";
	                        }
	                        usecaseNode.getName().setText(str);
			                DefaultMutableTreeNode usecaseTreeNode = new DefaultMutableTreeNode(str);
			                
			                usecasetreemodel.insertNodeInto(usecaseTreeNode, currentUsecaseNode, currentUsecaseNode.getChildCount());
          
			                TreePath treePath = new TreePath(usecasetreerootnode.getPath());
			                usecaseTree.makeVisible(treePath);
			                usecaseTree.getSelectionModel().setSelectionPath(new TreePath(usecaseTreeNode.getPath()));
			                usecaseTree.expandPath(treePath);
			                
			                NodeMap.put(usecaseTreeNode,(UseCaseNode) allnodes.get(0));
						    mainFrame.renewPanel();
						}
							else {
								AddNodeBehavior.lock.push(1);
							}
					    
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
					try {
						EditSelectedBehavior.lock.take();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}		
					if(modelPanel.hashCode() == mainFrame.getActiveModelPanel().hashCode()){
                    INode node = EditSelectedBehavior.getselectNode();
                    if(node != null && node instanceof UseCaseNode)
                    {
                    	UseCaseNode useCaseNode = (UseCaseNode) node;
                    	DefaultMutableTreeNode useceseTreeNode = getKey(NodeMap, useCaseNode);
                    	useceseTreeNode.setUserObject(useCaseNode.getName().getText());
                    	usecaseTree.updateUI();
                    }
					}
					else {
						EditSelectedBehavior.lock.push(1);
					}
				}	
			}
		}).start();
        
        new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					try {
						DefaultMutableTreeNode removeTreeNode = null;
						INode removeNode = null;
						AbstractGraph.lock.take();
						if(modelPanel.hashCode() == mainFrame.getActiveModelPanel().hashCode()){
							currentUsecaseWorkspace = ((WorkspacePanel)mainFrame.getCenterTabPanel().getComponent(0)).getWorkspace();//获取当前用例图workspace
							currentUsecaseNode = getKey(getHashMap(), currentUsecaseWorkspace); //获取当前用例图树节点
							nodes = mainFrame.getActiveWorkspace().getGraphFile().getGraph().getAllNodes();
							Iterator iter = NodeMap.entrySet().iterator();	
							while (iter.hasNext()) {
								Map.Entry entry = (Map.Entry) iter.next();
								DefaultMutableTreeNode useceseTreeNode = (DefaultMutableTreeNode) entry.getKey();
								UseCaseNode useceseNode = (UseCaseNode) entry.getValue();

                                if(!nodes.contains(useceseNode))
                                {
                                	removeTreeNode = useceseTreeNode;
                                	removeNode = useceseNode;
                                }
								usecaseTree.repaint();
								
							}
							if(removeTreeNode != null && removeNode!= null){
								usecasetreemodel.removeNodeFromParent(removeTreeNode);
								NodeMap.remove(removeTreeNode);
							}
							mainFrame.renewPanel();
						}
						else {
							AbstractGraph.lock.push(1);
						}
					}
						 catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				}
			}
		}).start();
        
        new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true)
				{
					try {
						CutCopyPasteBehavior.pastelock.take();
						if(modelPanel.hashCode() == mainFrame.getActiveModelPanel().hashCode()){
							currentUsecaseWorkspace = ((WorkspacePanel)mainFrame.getCenterTabPanel().getComponent(0)).getWorkspace();//获取当前用例图workspace
							currentUsecaseNode = getKey(hashMap, currentUsecaseWorkspace); //获取当前用例图树节点
							nodes = currentUsecaseWorkspace.getGraphFile().getGraph().getAllNodes();
				            List<UseCaseNode> allnodes=new ArrayList<UseCaseNode>();
				            for(INode node: nodes)
				            {
				                if(node.getClass().getSimpleName().equals("UseCaseNode"))
				                {
				                	allnodes.add((UseCaseNode)node);
				                }
				            }
				            
				            boolean flag = true;
				            List<UseCaseNode> caseNodes = new ArrayList<UseCaseNode>();
				            int count = currentUsecaseNode.getChildCount();
				            for(UseCaseNode node : allnodes)
				            {
				            	for(int i = 0;i < count;i++)
				            	{
				            		DefaultMutableTreeNode aChild = (DefaultMutableTreeNode) currentUsecaseNode.getChildAt(i);
				            		
				            		if(node.getName().getText().equals(aChild.toString()))
				            		{
				            			if(node.hashCode() == NodeMap.get(aChild).hashCode())
				            			{
				            				flag = false;
				            				break;
				            			}
				            		}
				            	}
				            	if(flag == true)
				            	{
				            		caseNodes.add(node);
				            	}
				            }
				            
				            if(caseNodes.size() != 0)
				            {
				            	for(UseCaseNode node : caseNodes)
				            	{
				            		DefaultMutableTreeNode child = new DefaultMutableTreeNode(node.getName().getText());
				            		usecasetreemodel.insertNodeInto(child, currentUsecaseNode, currentUsecaseNode.getChildCount());
				            		NodeMap.put(child, node);
				            	}
				            }
				            mainFrame.renewPanel();
						}
						else {
							CutCopyPasteBehavior.pastelock.push(1);
						}
						
					} catch (InterruptedException e) {
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
	   		mainFrame.getbotoomJSplitPane().setDividerLocation(0.7);
	   		mainFrame.getbotoomJSplitPane().setDividerSize(4);
	   		mainFrame.getReduceOrEnlargePanel().setVisible(true);
	   	}
	   
	 private static DefaultMutableTreeNode getKey(Map<DefaultMutableTreeNode, IWorkspace> map,IWorkspace panel)
	    {
	    	Iterator<DefaultMutableTreeNode> it = map.keySet().iterator();
	    	while (it.hasNext()) {
				DefaultMutableTreeNode key = it.next();
				if(map.get(key).hashCode() == panel.hashCode())
					return key;
			}
	    	return null;
	    }
	 private  DefaultMutableTreeNode getKey(Map<DefaultMutableTreeNode, UseCaseNode> INodeMap,UseCaseNode useCaseNode)
	    {
	    	Iterator<DefaultMutableTreeNode> it = INodeMap.keySet().iterator();
	    	while (it.hasNext()) {
				DefaultMutableTreeNode key = it.next();
				if(INodeMap.get(key).equals(useCaseNode))
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
	public Map<DefaultMutableTreeNode, IWorkspace> getHashMap() {
		return hashMap;
	}

	public Map<DefaultMutableTreeNode, UseCaseNode> getNodeMap() {
		return NodeMap;
	}

}
