package com.horstmann.violet.application.StepTwoModelExpand;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.gui.util.tanchao.TanchaoMarkovNode;
import com.horstmann.violet.application.gui.util.tanchao.TanchaoMarkovStartNode;
import com.horstmann.violet.application.menu.ViewMenu;
import com.horstmann.violet.framework.file.IGraphFile;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.markov.MarkovNode;
import com.horstmann.violet.product.diagram.markov.MarkovStartNode;
import com.horstmann.violet.product.diagram.markov.MarkovTransitionEdge;
import com.horstmann.violet.product.diagram.usecase.UseCaseNode;
import com.horstmann.violet.workspace.sidebar.SideBar;

public class StepTwoCenterRightPanel extends JPanel{
	private MainFrame mainFrame;
	private ViewMenu viewMenu;
	private JButton consoleButton;
	private JButton zoominButton;
	private JButton zoomoutButton;
	private JButton graphButton;
	private JPanel bottomPanel;
	private Map<Object, String> nodeTextMap;
	private Map<Object, String> edgeTextMap;
	private int flag = 0;	
	private int SideBarFlag = 0;
	public StepTwoCenterRightPanel(MainFrame mainFrame,ViewMenu viewMenu)
	{
		this.mainFrame = mainFrame;
		this.viewMenu = viewMenu;
		init();
	}
	 public void setflag1(int flag) {
			this.flag = flag;
		}
    private void init()
    {
    	initButton();
    	graphButton.setVisible(false);
    	zoominButton.setVisible(false);
    	zoomoutButton.setVisible(false);
    	
    	this.setLayout(new GridBagLayout());
    	this.add(consoleButton,new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(14, 10, 7, 10));
    	this.add(graphButton,new GBC(0, 1, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(3, 10, 7, 10));
    	this.add(zoominButton,new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(3, 10, 7, 10));
    	this.add(zoomoutButton,new GBC(0, 3, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(3, 10, 7, 10));
    	this.add(bottomPanel,new GBC(0, 4, 1, 1).setFill(GBC.BOTH).setWeight(1, 1));
    	buttonListen();
    }
    private void initButton()
    {
    	consoleButton = new JButton();
    	zoominButton = new JButton();
    	zoomoutButton = new JButton();
    	graphButton = new JButton();
    	bottomPanel = new JPanel();
    	Icon newIcon = new ImageIcon("resources/icons/22x22/console.png");
//    	Icon newIcon = new ImageIcon("resources/icons/22x22/zoomin.png");
    	consoleButton.setIcon(newIcon);
    	consoleButton.setBorderPainted(false);
    	consoleButton.setFocusPainted(false);
    	consoleButton.setMargin(new Insets(0, 0, 0, 0));
    	consoleButton.setContentAreaFilled(false);
    	consoleButton.setFocusable(true);
    	consoleButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(flag == 0)
				{
					mainFrame.getinformationPanel().setVisible(false);
					mainFrame.getbotoomJSplitPane().setDividerSize(0);
					flag = 1;	
				}
				else if (flag == 1) {
					mainFrame.getinformationPanel().setVisible(true);
					mainFrame.getbotoomJSplitPane().setDividerSize(4);
					mainFrame.getbotoomJSplitPane().setDividerLocation(0.7);
					flag = 0;
				}
				mainFrame.renewPanel();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
//				consoleButton.setBorderPainted(false);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				consoleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				mainFrame.renewPanel();
//				consoleButton.setBorderPainted(true);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    	Icon sidBarcon = new ImageIcon("resources/icons/22x22/zoomin.png");
    	zoominButton.setIcon(sidBarcon);
    	zoominButton.setBorderPainted(false);
    	zoominButton.setHorizontalTextPosition(SwingConstants.CENTER);
    	zoominButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    	zoominButton.setFocusPainted(false);
    	zoominButton.setMargin(new Insets(0, 0, 0, 0));
    	zoominButton.setContentAreaFilled(false);
    	zoominButton.setFocusable(true);
    	zoominButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				viewMenu.getItem(1).doClick();
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				zoominButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    
    	
    
    	Icon zoomoutIcon = new ImageIcon("resources/icons/22x22/zoomout.png");
    	zoomoutButton.setIcon(zoomoutIcon);
    	zoomoutButton.setBorderPainted(false);
    	zoomoutButton.setHorizontalTextPosition(SwingConstants.CENTER);
    	zoomoutButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    	zoomoutButton.setFocusPainted(false);
    	zoomoutButton.setMargin(new Insets(0, 0, 0, 0));
    	zoomoutButton.setContentAreaFilled(false);
    	zoomoutButton.setFocusable(true);
    	zoomoutButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				viewMenu.getItem(0).doClick();
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				zoomoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	Icon graphIcon = new ImageIcon("resources/icons/22x22/infromation.png");
    	graphButton.setIcon(graphIcon);
    	graphButton.setBorderPainted(false);
    	graphButton.setHorizontalTextPosition(SwingConstants.CENTER);
    	graphButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    	graphButton.setFocusPainted(false);
    	graphButton.setMargin(new Insets(0, 0, 0, 0));
    	graphButton.setContentAreaFilled(false);
    	graphButton.setFocusable(true);
    	graphButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
				if(mainFrame.getCenterTabPanel().getComponentCount() != 0)
				{
					if(mainFrame.getCenterTabPanel().getComponent(0).equals(mainFrame.getStepTwoExchangeTabbedPane()))
					{
						if(mainFrame.getStepTwoExchangeTabbedPane().getExchangeResults().getComponentCount() != 0)
		        		{
		        			setNodeTextMap(mainFrame.getStepTwoExchangeOperation().getNodeTextMap());
		        			setEdgeTextMap(mainFrame.getStepTwoExchangeOperation().getEdgeTextMap());	
		        		}
					}
					else if (mainFrame.getCenterTabPanel().getComponent(0).equals(mainFrame.getTimeExpandTabbedPane())) {
		        		if(mainFrame.getTimeExpandTabbedPane().getExpandResults().getComponentCount() != 0)
		        		{
		        			setNodeTextMap(mainFrame.getTimeExpandOperation().getNodeTextMap());
		        			setEdgeTextMap(mainFrame.getTimeExpandOperation().getEdgeTextMap());
		        		}
					}
					else {
						setNodeTextMap(null);
						setEdgeTextMap(null);
					}
				}
				else {
					setNodeTextMap(null);
					setEdgeTextMap(null);
				}
				if(nodeTextMap != null && edgeTextMap != null){
					boolean isShowInformation = false;
					IGraphFile graphFile = mainFrame.getActiveWorkspace().getGraphFile();
					Collection<INode> nodes = graphFile.getGraph().getAllNodes();
					Collection<IEdge> edges = graphFile.getGraph().getAllEdges();
					for(INode node : nodes)
					{
						if(node.getClass().getSimpleName().equals("MarkovNode"))
						{
//							((MarkovNode)node).setName(nodeTextMap.get(node.hashCode()));
							if(((MarkovNode)node).getName() != "")
								isShowInformation = true;
						}
						if(node.getClass().getSimpleName().equals("MarkovStartNode")) {
//							((MarkovStartNode)node).setName(nodeTextMap.get(node.hashCode()));
							if(((MarkovStartNode)node).getName() != "")
								isShowInformation = true;
						}
					}
					for(IEdge edge : edges)
					{
						if(edge.getClass().getSimpleName().equals("MarkovTransitionEdge"))
						{
//							((MarkovTransitionEdge)edge).setLabel(edgeTextMap.get(edge.hashCode()));
							
							if(((MarkovTransitionEdge)edge).getPro() != ""){
								isShowInformation = true;
							}
						}
					}
					
					if(!isShowInformation)
					{
						for(INode node : nodes)
						{
							if(node.getClass().getSimpleName().equals("MarkovNode"))
							{
								((MarkovNode)node).setName(nodeTextMap.get(node.hashCode()));
							}
							if(node.getClass().getSimpleName().equals("MarkovStartNode")) {
								((MarkovStartNode)node).setName(nodeTextMap.get(node.hashCode()));
							}
						}
						for(IEdge edge : edges)
						{
							if(edge.getClass().getSimpleName().equals("MarkovTransitionEdge"))
							{
								((MarkovTransitionEdge)edge).setPro(edgeTextMap.get(edge.hashCode()));
							}
						}
					}
					
					else {
						for(INode node : nodes)
						{
							if(node.getClass().getSimpleName().equals("MarkovNode"))
							{
								((MarkovNode)node).setName("");
							}
							if(node.getClass().getSimpleName().equals("MarkovStartNode")) {
								((MarkovStartNode)node).setName("");
							}
						}
						for(IEdge edge : edges)
						{
							if(edge.getClass().getSimpleName().equals("MarkovTransitionEdge"))
							{
								((MarkovTransitionEdge)edge).setPro("");
							}
						}
					}
				}
				
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				graphButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	bottomPanel.setBackground(new Color(233,233,233));
    	nodeTextMap = new HashMap<Object, String>();
    	edgeTextMap = new HashMap<Object,String>();
    }
    private void buttonListen()
    {
//    	consoleButton.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				if(flag == 0)
//				{
//					mainFrame.getOutputinformation().setVisible(false);
//					mainFrame.getbotoomJSplitPane().setDividerSize(0);
//					flag = 1;
//					mainFrame.renewPanel();
//				}
//				else if (flag == 1) {
//					mainFrame.getOutputinformation().setVisible(true);
//					mainFrame.getbotoomJSplitPane().setDividerSize(4);
//					mainFrame.getbotoomJSplitPane().setDividerLocation(0.7);
//					flag = 0;
//					mainFrame.renewPanel();
//				}
//			}
//		});
//    	zoominButton.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				viewMenu.getItem(1).doClick();
//				double zoom = mainFrame.getActiveWorkspace().getEditorPart().getZoomFactor();
//				if(zoom > 0.7)
//				{
//					//对节点信息处理
//					IGraphFile graphFile = mainFrame.getActiveWorkspace().getGraphFile();
//					Collection<INode> nodes = graphFile.getGraph().getAllNodes();
//					Collection<IEdge> edges = graphFile.getGraph().getAllEdges();
//					for(INode node : nodes)
//					{
//						if(node.getClass().getSimpleName().equals("MarkovNode"))
//						{
//							((MarkovNode)node).setName(nodeTextMap.get(node.hashCode()));
//						}
//						if(node.getClass().getSimpleName().equals("MarkovStartNode")) {
//							((MarkovStartNode)node).setName(nodeTextMap.get(node.hashCode()));
//						}
//					}
//					for(IEdge edge : edges)
//					{
//						if(edge.getClass().getSimpleName().equals("MarkovTransitionEdge"))
//						{
//							((MarkovTransitionEdge)edge).setLabel(edgeTextMap.get(edge.hashCode()));
//						}
//					}
//				}
//				mainFrame.renewPanel();
//			}
//		});
//    	zoomoutButton.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				viewMenu.getItem(0).doClick();
//				double zoom = mainFrame.getActiveWorkspace().getEditorPart().getZoomFactor();
//				if(zoom < 0.5)
//				{
//					//对节点信息处理
//					IGraphFile graphFile = mainFrame.getActiveWorkspace().getGraphFile();
//					Collection<INode> nodes = graphFile.getGraph().getAllNodes();
//					Collection<IEdge> edges = graphFile.getGraph().getAllEdges();
//					for(INode node : nodes)
//					{
//						if(node.getClass().getSimpleName().equals("MarkovNode"))
//						{
//							((MarkovNode)node).setName("");
//						}
//						if(node.getClass().getSimpleName().equals("MarkovStartNode")) {
//							((MarkovStartNode)node).setName("");
//						}
//					}
//					for(IEdge edge : edges)
//					{
//						if(edge.getClass().getSimpleName().equals("MarkovTransitionEdge"))
//						{
//							((MarkovTransitionEdge)edge).setLabel("");
//						}
//					}
//				}
//				mainFrame.renewPanel();
//			}
//		});
    	
    }
    public boolean getIsVisible()
    {
    	return (mainFrame.getActiveWorkspace().getAWTComponent().getScrollableEditorPart().getHorizontalScrollBar().isVisible()
    			|| mainFrame.getActiveWorkspace().getAWTComponent().getScrollableSideBar().getVerticalScrollBar().isVisible());
    }
	public JButton getZoominButton() {
		return zoominButton;
	}
	public JButton getZoomoutButton() {
		return zoomoutButton;
	}
	public JButton getGraphButton() {
		return graphButton;
	}
	public Map<Object, String> getNodeTextMap() {
		return nodeTextMap;
	}
	public Map<Object, String> getEdgeTextMap() {
		return edgeTextMap;
	}
	public void setNodeTextMap(Map<Object, String> nodeTextMap) {
		this.nodeTextMap = nodeTextMap;
	}
	public void setEdgeTextMap(Map<Object, String> edgeTextMap) {
		this.edgeTextMap = edgeTextMap;
	}
	
}
