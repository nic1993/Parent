package com.horstmann.violet.application.StepOneBuildModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.editorpart.behavior.AddNodeBehavior;

public class SequenceTreePanel extends JPanel{

	private MainFrame mainFrame;
	private FileMenu fileMenu;
	private ModelPanel modelPanel;
	private JLabel sequenceLabel;
	public JPopupMenu popupMenu;
	public JMenuItem newDiagram;
	public JMenuItem importDiagram;
	private Map<DefaultMutableTreeNode, IWorkspace> hashMap;
	public  JTree sequencetree;
	private DefaultTreeModel sequencetreemodel;
	private DefaultMutableTreeNode sequencetreerootnode;
	public SequenceTreePanel(FileMenu fileMenu, MainFrame mainFrame,ModelPanel modelPanel){
		this.mainFrame=mainFrame;
		this.fileMenu=fileMenu;
		this.modelPanel = modelPanel;
		this.setBackground(new Color(233,233,233));
		init();
		this.setLayout(new GridLayout());
		this.add(sequencetree);		
	}

	private void init() {
		// TODO Auto-generated method stub
		hashMap = new HashMap<DefaultMutableTreeNode,IWorkspace>();
		sequencetreerootnode=new DefaultMutableTreeNode("顺序图");
		sequencetreemodel=new DefaultTreeModel(sequencetreerootnode);
		sequencetree=new JTree(sequencetreemodel);
		DefaultTreeCellRenderer cellRender=new DefaultTreeCellRenderer();
		cellRender.setBackgroundNonSelectionColor(new Color(233,233,233));
		cellRender.setFont(new Font("宋体", Font.PLAIN, 16));
		sequencetree.setOpaque(false);
		sequencetree.setCellRenderer(cellRender);
		sequenceLabel =  new JLabel("顺序图是将交互关系表示为一个二维图。纵向是时间轴，时间沿竖线向下延伸。横向轴代表了在协作中各独立对象的类元角色。");
		sequenceLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		sequencetree.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getButton() == e.BUTTON1)
				{
					mainFrame.renewPanel();
				}
				if(e.getButton()==e.BUTTON3){
					if (((DefaultMutableTreeNode)sequencetree.getLastSelectedPathComponent()).equals(sequencetreerootnode)) {
						popupMenu = new JPopupMenu();
						newDiagram = new JMenuItem("新建",new ImageIcon("resources/icons/16x16/new.png"));
						importDiagram = new JMenuItem("导入",new ImageIcon("resources/icons/16x16/open.png"));
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
					
					//添加删除标志
					if(((DefaultMutableTreeNode)sequencetree.getLastSelectedPathComponent()).getLevel() == 1)
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
//								fileMenu.getItem(3).doClick();
								String name = ((DefaultMutableTreeNode)sequencetree.getLastSelectedPathComponent()).toString();
								IWorkspace removeworkspace = null;
							    //从保存模型中删除模型
								for(IWorkspace workspace : modelPanel.getSequencespaceList())
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
									modelPanel.getSequencespaceList().remove(removeworkspace);
								}
								//从树中删除模型
								int index = 0;
								int count = sequencetreerootnode.getChildCount();
								for(int i = 0;i < count;i++)
								{
									if(sequencetreerootnode.getChildAt(i).toString().equals(name))
									{
										index = i;
									}
								}
								sequencetreemodel.removeNodeFromParent((MutableTreeNode) sequencetreerootnode.getChildAt(index));
								sequencetree.repaint();
								
								mainFrame.renewPanel();
							}
						});
						saveDiagram.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								IWorkspace saveWorkSpace = null;
								int count = sequencetreerootnode.getChildCount();
								String name = ((DefaultMutableTreeNode)sequencetree.getLastSelectedPathComponent()).getUserObject().toString();
								for(IWorkspace workspace : modelPanel.getSequencespaceList())
								{
									if(workspace.getName().equals(name))
									{
										saveWorkSpace = workspace;
									}
								}
								if(saveWorkSpace != null)
								fileMenu.initFileSaveAsItem(saveWorkSpace);
								mainFrame.renewPanel();
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
									((DefaultMutableTreeNode)sequencetree.getLastSelectedPathComponent()).setUserObject(str);
									
									sequencetreemodel.reload();
									sequencetree.setSelectionPath(sequencetree.getSelectionPath());
								}
							}
						});
					}
					mainFrame.renewPanel();
				}
				
				
				
				if(e.getClickCount()==2){
					 DefaultMutableTreeNode node = (DefaultMutableTreeNode) sequencetree
							.getLastSelectedPathComponent();
					 boolean isExist = false;
					 if(!node.equals(sequencetreerootnode)){
						 if(node.getLevel() == 1)
						 {
							 mainFrame.setActiveModelPanel(modelPanel);
							 String seqname = node.toString();
							 for(int i = 0;i < modelPanel.getSequencespaceList().size();i++)
							 {
								 if(seqname.equals(modelPanel.getSequencespaceList().get(i).getName()))
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
								 mainFrame.getStepOperationButton().getPromptLabel().setText("顺序图是将交互关系表示为一个二维图。纵向是时间轴，时间沿竖线向下延伸。横向轴代表了在协作中各独立对象的类元角色。");
								 mainFrame.getpanel().updateUI();
								 mainFrame.getReduceOrEnlargePanel().setLayout(new GridLayout(1, 1));
								 mainFrame.getReduceOrEnlargePanel().add(mainFrame.getstepOneCenterRightPanel());
								 mainFrame.renewPanel();
							 }
							 else if(isExist == false) {

							}
						 }	 
					 }
					 mainFrame.renewPanel();
				}
			}
		});
		
	}
	   private void wakeupPanel()
	   	{
	   		mainFrame.getpanel().setVisible(true);
	   		mainFrame.getbotoomJSplitPane().setDividerLocation(0.7);
	   		mainFrame.getbotoomJSplitPane().setDividerSize(4);
	   		mainFrame.getReduceOrEnlargePanel().setVisible(true);
	   	}
	   
	public JTree getSequencetree() {
		return sequencetree;
	}
    
	public DefaultTreeModel getSequencetreemodel() {
		return this.sequencetreemodel;
	}

	public DefaultMutableTreeNode getSequencetreerootnode() {
		return sequencetreerootnode;
	}
	public Map<DefaultMutableTreeNode, IWorkspace> getHashMap() {
		return hashMap;
	}
	
	
}
