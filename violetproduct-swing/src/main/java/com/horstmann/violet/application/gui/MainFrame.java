/*
 Violet - A program for editing UML diagrams.

 Copyright (C) 2008 Cay S. Horstmann (http://horstmann.com)
 Alexandre de Pellegrin (http://alexdp.free.fr);

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.horstmann.violet.application.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.beans.BeanInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.synth.SynthSeparatorUI;
import javax.swing.tree.DefaultMutableTreeNode;

import org.eclipse.draw2d.geometry.Rectangle;


import com.horstmann.violet.application.StepTwoCaseExpand.StepTwoCaseExpandTabbedPane;
import com.horstmann.violet.application.StepTwoCaseExpand.StepTwoCaseExpandTree;
import com.horstmann.violet.application.StepTwoCaseExpand.StepTwoCaseExpandOperation;
import com.horstmann.violet.application.StepTwoEvaluate.StepTwoEvaluateOperation;
import com.horstmann.violet.application.StepTwoEvaluate.StepTwoEvaluateTabbedPane;
import com.horstmann.violet.application.StepTwoExchange.StepTwoExchangeMarkovTree;
import com.horstmann.violet.application.StepTwoExchange.StepTwoExchangeOperation;
import com.horstmann.violet.application.StepTwoExchange.StepTwoExchangeTabbedPane;
import com.horstmann.violet.application.StepTwoModelExpand.JRadionPanel;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoExpandBottom;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoCenterRightPanel;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoExpand;

import com.horstmann.violet.application.StepTwoModelExpand.StepTwoModelExpandTabbedPane;

import com.horstmann.violet.application.StepTwoModelExpand.StepTwoModelOperation;


import com.horstmann.violet.application.SteponeBuildModel.HeadTitle;
import com.horstmann.violet.application.SteponeBuildModel.Outputinformation;
import com.horstmann.violet.application.SteponeBuildModel.SequenceTabPanel;
import com.horstmann.violet.application.SteponeBuildModel.SequenceTreePanel;
import com.horstmann.violet.application.SteponeBuildModel.StepOneButton;
import com.horstmann.violet.application.SteponeBuildModel.StepOneCenterRightPanel;
import com.horstmann.violet.application.SteponeBuildModel.StepOneCenterSequenceTabbedPane;
import com.horstmann.violet.application.SteponeBuildModel.StepOneCenterUseCaseTabbedPane;
import com.horstmann.violet.application.SteponeBuildModel.StepOneOperationButton;
import com.horstmann.violet.application.SteponeBuildModel.SteponeBottomPanel;
import com.horstmann.violet.application.SteponeBuildModel.TitlePanel;
import com.horstmann.violet.application.SteponeBuildModel.UsecaseTabPanel;
import com.horstmann.violet.application.SteponeBuildModel.UsecaseTreePanel;
import com.horstmann.violet.application.consolepart.ConsolePart;
import com.horstmann.violet.application.help.AboutDialog;
import com.horstmann.violet.application.menu.MenuFactory;
import com.horstmann.violet.framework.dialog.DialogFactory;
import com.horstmann.violet.framework.file.GraphFile;
import com.horstmann.violet.framework.file.IFile;
import com.horstmann.violet.framework.file.IGraphFile;
import com.horstmann.violet.framework.file.chooser.IFileChooserService;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.BeanInjector;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.InjectedBean;
import com.horstmann.violet.framework.injection.resources.ResourceBundleInjector;
import com.horstmann.violet.framework.injection.resources.annotation.ResourceBundleBean;
import com.horstmann.violet.framework.theme.ITheme;
import com.horstmann.violet.framework.theme.ThemeManager;
import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.node.AbstractNode;
import com.horstmann.violet.product.diagram.abstracts.property.ArrowHead;
import com.horstmann.violet.product.diagram.abstracts.property.BentStyle;
import com.horstmann.violet.product.diagram.abstracts.property.LineStyle;
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.IWorkspaceListener;
import com.horstmann.violet.workspace.Workspace;

/**
 * This desktop frame contains panes that show graphs.
 * 
 * @author Alexandre de Pellegrin
 */
@ResourceBundleBean(resourceReference = AboutDialog.class)
public class MainFrame extends JFrame
{
    /**
     * Constructs a blank frame with a desktop pane but no graph windows.
     * 
     */
    public MainFrame()
    {
        BeanInjector.getInjector().inject(this);
        ResourceBundleInjector.getInjector().inject(this);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.dialogFactory.setDialogOwner(this);
        decorateFrame();
        setInitialSize();
        createMenuBar();
        getContentPane().add(this.getMainPanel());
        this.addComponentListener(new ComponentAdapter(){
        	public void componentResized(ComponentEvent e) {
                botoomJSplitPane.setDividerLocation(0.7);
        }
    });
    }

    /**
     * Sets initial size on startup
     */
    private void setInitialSize()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        setBounds(screenWidth / 16, screenHeight / 16, screenWidth * 7 / 8, screenHeight * 7 / 8);
        // For screenshots only -> setBounds(50, 50, 850, 650);
    }

    /**
     * Decorates the frame (title, icon...)
     */
    private void decorateFrame()
    {
        setTitle(this.applicationName);
        setIconImage(this.applicationIcon);        
    }

    /**
     * Creates menu bar
     */
    private void createMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setFont(this.themeManager.getTheme().getMenubarFont());
        MenuFactory menuFactory = getMenuFactory();
        menuBar.add(menuFactory.getFileMenu(this));
        menuBar.add(menuFactory.getEditMenu(this));
        menuBar.add(menuFactory.getViewMenu(this));
        menuBar.add(menuFactory.getHelpMenu(this));
        //setJMenuBar(menuBar);
      
    }

    /**
     * Adds a tabbed pane (only if not already added)
     * 
     * @param c the component to display in the internal frame
     */
    public void  addTabbedPane(final IWorkspace workspace)
    {
        replaceWelcomePanelByTabbedPane(); 
    	//在添加图形元素的时候，首先判断下是哪种图形
        if(workspace.getTitle().toString().endsWith(".ucase.violet.xml")
        		||workspace.getTitle().toString().substring(2, 4).equals("Us"))//如果是用例图
     	{
     		if(this.UseCaseworkspaceList.contains(workspace))
     		{
     			
     			return;
     		}
     		this.UseCaseworkspaceList.add(workspace);
     		int pos = this.UseCaseworkspaceList.indexOf(workspace);
     		JPanel panel = new JPanel();
     		panel.setLayout(new GridBagLayout());
     		UsecaseTabPanel usecaseTabPanel =  new UsecaseTabPanel(this, "用例图", pos);    		
     		this.getStepOneCenterUseCaseTabbedPane().getUsecaseDiagramTabbedPane().add(panel);
     		this.getStepOneCenterUseCaseTabbedPane().getUsecaseDiagramTabbedPane().get(pos).add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
     		this.getStepOneCenterUseCaseTabbedPane().addTab("用例图",this.getStepOneCenterUseCaseTabbedPane().getUsecaseDiagramTabbedPane().get(pos));
     		this.getStepOneCenterUseCaseTabbedPane().setTabComponentAt(pos,usecaseTabPanel);
     		this.getListUsecaseTabPanel().add(usecaseTabPanel);
//     		this.getStepOneCenterTabbedPane().getSequenceDiagramTabbedPane()
//     		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
     		listenToDiagramPanelEvents(workspace,UseCaseworkspaceList);   		
     	    repaint();    		              
     	}

     	if(workspace.getTitle().toString().endsWith(".seq.violet.xml")
     			||workspace.getTitle().toString().substring(2, 4).equals("Se"))//如果是顺序图
     			
 		{
     	
     		if(this.SequencespaceList.contains(workspace))
     		{
     			return;
     		}
     		//this.SequencespaceList.clear();
     		this.SequencespaceList.add(workspace);
     		int pos = this.SequencespaceList.indexOf(workspace);
     		//this.getStepOneCenterTabbedPane().getSequenceDiagramTabbedPane().removeAll();
     		JPanel panel = new JPanel();
     		panel.setLayout(new GridBagLayout());
     		SequenceTabPanel sequenceTabPanel = new SequenceTabPanel(this, "顺序图", pos);
     		this.getStepOneCenterSequenceTabbedPane().getSequenceDiagramTabbedPane().add(panel);
     		this.getStepOneCenterSequenceTabbedPane().getSequenceDiagramTabbedPane().get(pos).add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
     		this.getStepOneCenterSequenceTabbedPane().add("顺序图",this.getStepOneCenterSequenceTabbedPane().getSequenceDiagramTabbedPane().get(pos));
     		this.getStepOneCenterSequenceTabbedPane().setTabComponentAt(pos,sequenceTabPanel);
//     		this.getStepOneCenterTabbedPane().getSequenceDiagramTabbedPane()
//     		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
     		this.getListSequenceTabPanel().add(sequenceTabPanel);
     		listenToDiagramPanelEvents(workspace,SequencespaceList);   		
     	    repaint();    		              
 		}    
     	if(workspace.getTitle().toString().endsWith(".markov.violet.xml")
     			||workspace.getTitle().toString().substring(2, 4).equals("Ma"))//如果是状态图
 		{
     		if(this.MarkovpaceList.contains(workspace))
     		{
     			return;
     		}
     	
     		this.MarkovpaceList.clear();
     		this.MarkovpaceList.add(workspace);
     		this.getStepTwoExchangeTabbedPane().getExchangeResults().removeAll();
     		this.getStepTwoExchangeTabbedPane().getExchangeResults()
     		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
     		
     		 listenToDiagramPanelEvents(workspace,MarkovpaceList);
    	  
     	    repaint();     		    
 		}

    }
    public void  addTabbedPane(final IWorkspace workspace ,String title)
    {
        replaceWelcomePanelByTabbedPane(); 
    	//在添加图形元素的时候，首先判断下是哪种图形
        if(workspace.getTitle().toString().endsWith(".ucase.violet.xml")
        		||workspace.getTitle().toString().substring(2, 4).equals("Us"))//如果是用例图
     	{
     		if(this.UseCaseworkspaceList.contains(workspace))
     		{
     			
     			return;
     		}
     		this.UseCaseworkspaceList.add(workspace);
     		int pos = this.UseCaseworkspaceList.indexOf(workspace);
     		
     		//this.getStepOneCenterTabbedPane().getSequenceDiagramTabbedPane().removeAll();
     		JPanel panel = new JPanel();
     		panel.setLayout(new GridBagLayout());
     		
     		UsecaseTabPanel usecaseTabPanel =  new UsecaseTabPanel(this, title, pos);
     		this.getStepOneCenterUseCaseTabbedPane().getUsecaseDiagramTabbedPane().add(panel);
     		this.getStepOneCenterUseCaseTabbedPane().getUsecaseDiagramTabbedPane().get(pos).add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
     		this.getStepOneCenterUseCaseTabbedPane().addTab("用例图",this.getStepOneCenterUseCaseTabbedPane().getUsecaseDiagramTabbedPane().get(pos));
     		this.getStepOneCenterUseCaseTabbedPane().setTabComponentAt(pos,usecaseTabPanel);
     		this.getListUsecaseTabPanel().add(usecaseTabPanel);
//     		this.getStepOneCenterTabbedPane().getSequenceDiagramTabbedPane()
//     		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
     		listenToDiagramPanelEvents(workspace,UseCaseworkspaceList);   		
     	    repaint();    		              
     	}

     	if(workspace.getTitle().toString().endsWith(".seq.violet.xml")
     			||workspace.getTitle().toString().substring(2, 4).equals("Se"))//如果是顺序图
     			
 		{
     	
     		if(this.SequencespaceList.contains(workspace))
     		{
     			return;
     		}
     		//this.SequencespaceList.clear();
     		this.SequencespaceList.add(workspace);
     		int pos = this.SequencespaceList.indexOf(workspace);
     		//this.getStepOneCenterTabbedPane().getSequenceDiagramTabbedPane().removeAll();
     		JPanel panel = new JPanel();
     		panel.setLayout(new GridBagLayout());
     		SequenceTabPanel sequenceTabPanel = new SequenceTabPanel(this, "顺序图", pos);
     		this.getStepOneCenterSequenceTabbedPane().getSequenceDiagramTabbedPane().add(panel);
     		this.getStepOneCenterSequenceTabbedPane().getSequenceDiagramTabbedPane().get(pos).add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
     		this.getStepOneCenterSequenceTabbedPane().add("顺序图",this.getStepOneCenterSequenceTabbedPane().getSequenceDiagramTabbedPane().get(pos));
     		this.getStepOneCenterSequenceTabbedPane().setTabComponentAt(pos,sequenceTabPanel);
//     		this.getStepOneCenterTabbedPane().getSequenceDiagramTabbedPane()
//     		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
     		this.getListSequenceTabPanel().add(sequenceTabPanel);
     		listenToDiagramPanelEvents(workspace,SequencespaceList);   		
     	    repaint();    		              
 		}    
     	if(workspace.getTitle().toString().endsWith(".markov.violet.xml")
     			||workspace.getTitle().toString().substring(2, 4).equals("Ma"))//如果是状态图
 		{
     		if(this.MarkovpaceList.contains(workspace))
     		{
     			return;
     		}
     	
     		this.MarkovpaceList.clear();
     		this.MarkovpaceList.add(workspace);
     		this.getStepTwoExchangeTabbedPane().getExchangeResults().removeAll();
     		this.getStepTwoExchangeTabbedPane().getExchangeResults()
     		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
     		
     		 listenToDiagramPanelEvents(workspace,MarkovpaceList);
    	  
     	    repaint();     		    
 		}
    }
    /**
     * Add a listener to perform action when something happens on this diagram
     * 
     * @param diagramPanel
     */
    private void listenToDiagramPanelEvents(final IWorkspace diagramPanel,final List<IWorkspace> workspaceList)
    {
        diagramPanel.addListener(new IWorkspaceListener()
        {
            public void titleChanged(String newTitle)
            {
                int pos = workspaceList.indexOf(diagramPanel);
            }

            public void graphCouldBeSaved()
            {
                // nothing to do here
            }

            public void mustOpenfile(IFile file)
            {
                try
                {
                    IGraphFile graphFile = new GraphFile(file);
                    IWorkspace newWorkspace = new Workspace(graphFile);
                    addTabbedPane(newWorkspace);
                }
                catch (IOException e)
                {
                    DialogFactory.getInstance().showErrorDialog(e.getMessage());
                }
            }
        });
    }

    private void replaceWelcomePanelByTabbedPane()
    {
        WelcomePanel welcomePanel = this.getWelcomePanel();
        //JTabbedPane tabbedPane = getTabbedPane();     
        getMainPanel().remove(welcomePanel);        
       // getMainPanel().add(tabbedPane, new GBC(1,1,1,1).setFill(GBC.BOTH).setWeight(1, 1));
        repaint();
    }

    private void replaceTabbedPaneByWelcomePanel()
    {
        this.welcomePanel = null;
        WelcomePanel welcomePanel = this.getWelcomePanel();
        JTabbedPane tabbedPane = getTabbedPane();
        getMainPanel().remove(tabbedPane);
        getMainPanel().add(welcomePanel, BorderLayout.CENTER);
        repaint();
    }

    /**
     * @return the tabbed pane that contains diagram panels
     */
    public JTabbedPane getTabbedPane()
    {
        if (this.tabbedPane == null)
        {
            this.tabbedPane = new JTabbedPane()
            {
                public void paint(Graphics g)
                {
                    Graphics2D g2 = (Graphics2D) g;
                    Paint currentPaint = g2.getPaint();
                    ITheme LAF = themeManager.getTheme();
                    GradientPaint paint = new GradientPaint(getWidth() / 2, -getHeight() / 4, LAF.getWelcomeBackgroundStartColor(),
                            getWidth() / 2, getHeight() + getHeight() / 4, LAF.getWelcomeBackgroundEndColor());
                    g2.setPaint(paint);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.setPaint(currentPaint);
                    super.paint(g);
                }
            };
            this.tabbedPane.setOpaque(false);
            MouseWheelListener[] mouseWheelListeners = this.tabbedPane.getMouseWheelListeners();
            for (int i = 0; i < mouseWheelListeners.length; i++)
            {
                this.tabbedPane.removeMouseWheelListener(mouseWheelListeners[i]);
            }
        }
        return this.tabbedPane;
    }

    /**
     * Removes a diagram panel from this editor frame
     * 
     * @param diagramPanel
     */
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
    public void removeDiagramPanel(IWorkspace diagramPanel)
    {
    	 if(getCenterTabPanel().getComponent(0).equals(getStepOneCenterUseCaseTabbedPane())){
        if (this.UseCaseworkspaceList.contains(diagramPanel))       		
        {   
        int pos = this.UseCaseworkspaceList.indexOf(diagramPanel);
        System.out.println("删除pos:"+pos);
        for(UsecaseTabPanel usecaseTabPanel : this.getListUsecaseTabPanel())
        {
        	if(usecaseTabPanel.getPos() > pos){
        		if(usecaseTabPanel.getPos() - 1 == pos)
        		{
        			usecaseTabPanel.getPanel().setBackground(Color.white);
        			usecaseTabPanel.getDeletelabel().setIcon(new ImageIcon("resources\\icons\\22x22\\beforeClose.png"));
        			usecaseTabPanel.setPos(usecaseTabPanel.getPos() - 1);
        		}
        		else {
        			usecaseTabPanel.setPos(usecaseTabPanel.getPos() - 1);
				}
        		
        	}
        }
        this.getListUsecaseTabPanel().remove(pos);
        if(pos == UseCaseworkspaceList.size()-1 && pos != 0)
        {
        	this.getListUsecaseTabPanel().get(pos - 1).getPanel().setBackground(Color.white);
        	this.getListUsecaseTabPanel().get(pos - 1).getDeletelabel().setIcon(new ImageIcon("resources\\icons\\22x22\\beforeClose.png"));
        }

        getStepOneCenterUseCaseTabbedPane().remove(pos);
        this.UseCaseworkspaceList.remove(diagramPanel);
        
        //删除树上节点
        Map<DefaultMutableTreeNode, JPanel> map = getUsecaseTree().getHashMap();
        JPanel panel = this.getStepOneCenterUseCaseTabbedPane().getUsecaseDiagramTabbedPane().get(pos);
        DefaultMutableTreeNode removeNode = getKey(map, panel);
        this.getUsecaseTree().getUsecasetreemodel().removeNodeFromParent(removeNode);
        this.getStepOneCenterUseCaseTabbedPane().getUsecaseDiagramTabbedPane().remove(pos);
        repaint();
        }
    	}

        if(getCenterTabPanel().getComponent(0).equals(getStepOneCenterSequenceTabbedPane())){
        if (this.SequencespaceList.contains(diagramPanel))       		
        {   
        int pos = getStepOneCenterSequenceTabbedPane().getSelectedIndex();
            for(SequenceTabPanel sequenceTabPanel : this.getListSequenceTabPanel())
            {
            	if(sequenceTabPanel.getPos() > pos){
            		if(sequenceTabPanel.getPos() - 1 == pos)
            		{
            			sequenceTabPanel.getPanel().setBackground(Color.white);
            			sequenceTabPanel.getDeletelabel().setIcon(new ImageIcon("resources\\icons\\22x22\\beforeClose.png"));
            		}
            		sequenceTabPanel.setPos(sequenceTabPanel.getPos() - 1);
            	}
            }
            this.getListSequenceTabPanel().remove(pos);
            if(pos == SequencespaceList.size()-1 && pos != 0)
            { 
            	this.getListSequenceTabPanel().get(pos - 1).getPanel().setBackground(Color.white);
            	this.getListSequenceTabPanel().get(pos - 1).getDeletelabel().setIcon(new ImageIcon("resources\\icons\\22x22\\beforeClose.png"));
            }
        getStepOneCenterSequenceTabbedPane().remove(pos);        
        this.SequencespaceList.remove(diagramPanel);
        
        Map<DefaultMutableTreeNode, JPanel> map = getsequencetree().getHashMap();
        JPanel panel = this.getStepOneCenterSequenceTabbedPane().getSequenceDiagramTabbedPane().get(pos);
        DefaultMutableTreeNode removeNode = getKey(map, panel);
        this.getsequencetree().getSequencetreemodel().removeNodeFromParent(removeNode);
        this.getStepOneCenterSequenceTabbedPane().getSequenceDiagramTabbedPane().remove(pos);
        repaint();
        }
        }
        
        if (this.MarkovpaceList.contains(diagramPanel))       		
        {   
        int pos = this.MarkovpaceList.indexOf(diagramPanel);
        getStepOneCenterSequenceTabbedPane().getUMLTabbedPane(diagramPanel,pos).remove(pos);
        this.MarkovpaceList.remove(diagramPanel);
        repaint();
        }
        if (this.UppaalspaceList.contains(diagramPanel))       		
        {   
        int pos = this.UppaalspaceList.indexOf(diagramPanel);
        getStepTwoCenterTabbedPane().getUppaalDiagramTabbedPane().remove(pos);
        this.UppaalspaceList.remove(diagramPanel);
        repaint();
        }
        
    }
    
    /**
     * Looks for an opened diagram from its file path and focus it
     * 
     * @param diagramFilePath diagram file path
     */
    public void setActiveDiagramPanel(IFile aGraphFile)
    {
        if (aGraphFile == null) return;
        for (IWorkspace aDiagramPanel : this.UseCaseworkspaceList)
        {
            IFile toCompare = aDiagramPanel.getGraphFile();
            boolean isSameFilename = aGraphFile.getFilename().equals(toCompare.getFilename());
            if (isSameFilename)
            {
                int pos = this.UseCaseworkspaceList.indexOf(aDiagramPanel);
//                JTabbedPane tp = getStepOneCenterTabbedPane().getUsecaseDiagramTabbedPane();
//                tp.setSelectedIndex(pos);
                return;
            }
        }
        for (IWorkspace aDiagramPanel : this.TimingDiagramspaceList)
        {
            IFile toCompare = aDiagramPanel.getGraphFile();
            boolean isSameFilename = aGraphFile.getFilename().equals(toCompare.getFilename());
            if (isSameFilename)
            {
                int pos = this.TimingDiagramspaceList.indexOf(aDiagramPanel);
//                JTabbedPane tp = getStepOneCenterTabbedPane().getTimingDiagramTabbedPane();
//                tp.setSelectedIndex(pos);
                return;
            }
        }
        for (IWorkspace aDiagramPanel : this.SequencespaceList)
        {
            IFile toCompare = aDiagramPanel.getGraphFile();
            boolean isSameFilename = aGraphFile.getFilename().equals(toCompare.getFilename());
            if (isSameFilename)
            {
                int pos = this.SequencespaceList.indexOf(aDiagramPanel);
//                JTabbedPane tp = getStepOneCenterTabbedPane().getSequenceDiagramTabbedPane();
//                tp.setSelectedIndex(pos);
                return;
            }
        }
        for (IWorkspace aDiagramPanel : this.MarkovpaceList)
        {
            IFile toCompare = aDiagramPanel.getGraphFile();
            boolean isSameFilename = aGraphFile.getFilename().equals(toCompare.getFilename());
            if (isSameFilename)
            {
                int pos = this.MarkovpaceList.indexOf(aDiagramPanel);
//                JTabbedPane tp = getStepOneCenterTabbedPane().getStateDiagramTabbedPane();
//                tp.setSelectedIndex(pos);
                return;
            }
        }
    }

    /**
     * @return true if at least a diagram is displayed
     */
    public boolean isThereAnyDiagramDisplayed()
    {
        return !this.UseCaseworkspaceList.isEmpty()
        		||!this.TimingDiagramspaceList.isEmpty()
        		||!this.MarkovpaceList.isEmpty()
        		||!this.SequencespaceList.isEmpty()
        		||!this.UppaalspaceList.isEmpty();
    }

    public List<IWorkspace> getUseCaseWorkspaceList()
    {
        return UseCaseworkspaceList;
    }
    public List<IWorkspace> getTimingWorkspaceList()
    {
        return TimingDiagramspaceList;
    }
    public List<IWorkspace> getSequenceWorkspaceList()
    {
        return SequencespaceList;
    }
    public List<IWorkspace> getMarkovWorkspaceList()
    {
        return MarkovpaceList;
    }
    /**
     * @return selected diagram file path (or null if not one is selected; that should never happen)
     */
    public IWorkspace getActiveWorkspace()
    {
        JTabbedPane Onesequence = getStepOneCenterSequenceTabbedPane();
        JTabbedPane Oneusecase = getStepOneCenterUseCaseTabbedPane();
        JTabbedPane Twotp=getStepTwoCenterTabbedPane();
        int onepos1 = Onesequence.getSelectedIndex(); 
        int onepos2 = Oneusecase.getSelectedIndex();
        int pos2=Twotp.getSelectedIndex();
        if(getCenterTabPanel().getComponentCount() != 0)
        {       	
        if(getCenterTabPanel().getComponent(0).equals(getStepOneCenterSequenceTabbedPane())){
        if (SequencespaceList.size()>0)
        {
            return this.SequencespaceList.get(onepos1);
        }
        }
//        if (pos1==1&&TimingDiagramspaceList.size()>0)
//        {
//            return this.TimingDiagramspaceList.get(0);
//        }
//        if (pos1==2&&MarkovpaceList.size()>0)
//        {
//            return this.MarkovpaceList.get(0);
//        }
        if(getCenterTabPanel().getComponent(0).equals(getStepOneCenterUseCaseTabbedPane()))
        {
        if (UseCaseworkspaceList.size()>0)
        {
            return this.UseCaseworkspaceList.get(onepos2);
        }
        }
        if (pos2==1&&UppaalspaceList.size()>0)
        {
        	return this.UppaalspaceList.get(0);
        }       
        }
//        else{//说明没有workspace UML图形需要保存
//           return null;
//        }
        return null;
    }
    
    public IWorkspace getActiveListWorkspace()
    {
        JTabbedPane Onetp = getStepOneCenterSequenceTabbedPane();
        JTabbedPane Twotp=getStepTwoCenterTabbedPane();
        int pos1=Onetp.getSelectedIndex(); 
        int pos2=Twotp.getSelectedIndex();
        if (SequencespaceList.size()>0)
        {
            return this.SequencespaceList.get(pos1);
        }
//        if (pos1==1&&TimingDiagramspaceList.size()>0)
//        {
//            return this.TimingDiagramspaceList.get(0);
//        }
//        if (pos1==2&&MarkovpaceList.size()>0)
//        {
//            return this.MarkovpaceList.get(0);
//        }
//        if (pos1==3&&UseCaseworkspaceList.size()>0)
//        {
//            return this.UseCaseworkspaceList.get(0);
//        }
//        if (pos2==1&&UppaalspaceList.size()>0)
//        {
//        	return this.UppaalspaceList.get(0);
//        }
        else{//说明没有workspace UML图形需要保存
           return null;
        }
    }
    
   public WelcomePanel getWelcomePanel()
    {
        if (this.welcomePanel == null)
        {
            this.welcomePanel = new WelcomePanel(this.getMenuFactory().getFileMenu(this));
        }
        return this.welcomePanel;
    }
   public HomePanel getHomePanel()
   {
   	if(this.homepanel==null)
   	{
   		this.homepanel=new HomePanel();
   	}
   	return this.homepanel;
   }
    public StepOneButton getsteponeButton()
    {
    	if(this.stepOneButton == null)
    	{
    		this.stepOneButton = new StepOneButton(this,this.getMenuFactory().getFileMenu(this));
    	}
    	return stepOneButton;
    }

    public JPanel getstepleftbuttonJPanel()
    {
    	if(this.StepLeftButtonPanel == null)
    	{
    		this.StepLeftButtonPanel = new JPanel();
    	}
    	return StepLeftButtonPanel;
    }
//    public ConsolePart getConsolePart()
//    {
//    	if(this.consolePart==null)
//    	{
//    		this.consolePart=new ConsolePart(this);
//    	}
//    	return this.consolePart;
//    }
    public HeadTitle getHeadTitle() {
		if(this.headTitle == null)
		{
			this.headTitle = new HeadTitle(this);
		}
		return headTitle;
	}
    public StepOneOperationButton getOperationButton()
    {
    	if(this.stepOneOperationButton == null)
    	{
    		this.stepOneOperationButton = new StepOneOperationButton(this, this.getMenuFactory().getFileMenu(this))
    		{
    			public void paint(Graphics g) {
    	            super.paint(g);
    	            java.awt.Rectangle rect = this.getBounds();
    	            int width = (int) rect.getWidth() - 1;
    	            int height = (int) rect.getHeight() - 1;
    	            Graphics2D g2 = (Graphics2D)g;
    	            g2.setStroke(new BasicStroke(3f));
    	            g2.setColor(new Color(188,188,188));
//    	            g2.drawLine(width, 0, width, height);
//    	            g2.drawLine(0, 0, 0, height);
    	          }
    		};
    	}
    	return stepOneOperationButton;
    }
    public SequenceTreePanel getsequencetree()
    {
    	if(this.sequenceTree == null){
    		this.sequenceTree = new SequenceTreePanel(this.getMenuFactory().getFileMenu(this), this);
    	}       
    	return sequenceTree;  	
    }
    public UsecaseTreePanel getUsecaseTree() {
    	if(this.usecaseTree == null){
    		this.usecaseTree = new UsecaseTreePanel(this.getMenuFactory().getFileMenu(this), this);
    	}       
    	return usecaseTree;  	
	}
    public ModelTransformationPanel getModelTransformationPanel()
    {
    	if(this.modelTransformationPanel==null)
    	{
    		this.modelTransformationPanel=new ModelTransformationPanel(this);
    		
    	}
    	return this.modelTransformationPanel;
    }
    public JPanel getMainPanel() {//主面板布局
    	setUI();
        if (this.mainPanel == null) {	
        	
        	botoomJSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        	botoomJSplitPane.setLeftComponent(this.getCenterTabPanel());
        	botoomJSplitPane.setRightComponent(this.getinformationPanel());
        	botoomJSplitPane.setDividerSize(0);
        	this.getworkpanel().setLayout(new GridLayout(1, 1));
        	this.getworkpanel().add(botoomJSplitPane);
        	
        	GridBagLayout layout=new GridBagLayout();
            this.mainPanel = new JPanel(layout);
            this.mainPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
            this.mainPanel.add(this.getStepJLabel());
            this.mainPanel.add(this.getconsolepartPanel());
            this.mainPanel.add(this.getOpreationPart());  
            this.mainPanel.add(this.getpanel());
            this.mainPanel.add(this.getworkpanel());
            this.mainPanel.add(this.getReduceOrEnlargePanel());
//            this.mainPanel.add(this.getStepTwoPanel());
            this.getStepJLabel().setBackground(new Color(222,222,222));
            this.getCenterTabPanel().setLayout(new GridLayout(1, 1));
            this.getCenterTabPanel().add(this.getHeadTitle());

            this.getStepJLabel().setVisible(false);
            this.getOpreationPart().setVisible(false);
            this.getconsolepartPanel().setVisible(false);
            this.getpanel().setVisible(false);
            this.getinformationPanel().setVisible(false);
            this.getReduceOrEnlargePanel().setVisible(false);
           
            
            layout.setConstraints(this.getStepJLabel(), new GBC(0,0,3,1).setFill(GBC.BOTH).setWeight(1, 0));    //标题区域
            layout.setConstraints(this.getOpreationPart(), new GBC(0,1,1,2).setFill(GBC.BOTH).setWeight(0, 1)); //编辑树区域
            layout.setConstraints(this.getconsolepartPanel(), new GBC(0,3,4,1).setFill(GBC.BOTH).setWeight(1, 0.02));  
          
            layout.setConstraints(this.getpanel(), new GBC(1,1,2,1).setFill(GBC.BOTH).setWeight(1, 0));           //按钮区域
            layout.setConstraints(this.getworkpanel(), new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98));//绘图区域
            layout.setConstraints(this.getReduceOrEnlargePanel(), new GBC(2,2,1,1).setFill(GBC.BOTH).setWeight(0, 1));   
        }
        return this.mainPanel;
    }
    public void setUI() {
    	UIManager.put("SplitPane.background", new Color(188,188,188));
	}
      	
    public StepOneCenterSequenceTabbedPane getStepOneCenterSequenceTabbedPane()
    {
    if (this.stepOneCenterSequenceTabbedPane== null)
    {
       stepOneCenterSequenceTabbedPane=new StepOneCenterSequenceTabbedPane();
    }
    return this.stepOneCenterSequenceTabbedPane;
    	
    }
    public StepOneCenterUseCaseTabbedPane getStepOneCenterUseCaseTabbedPane()
    {
    if (this.stepOneCenterUseCaseTabbedPane== null)
    {
    	stepOneCenterUseCaseTabbedPane=new StepOneCenterUseCaseTabbedPane();
    }
    return this.stepOneCenterUseCaseTabbedPane;
    	
    }
    public StepTwoCenterTabbedPane getStepTwoCenterTabbedPane()
    {
    if (this.stepTwoCenterTabbedPane== null)
    {
       stepTwoCenterTabbedPane=new StepTwoCenterTabbedPane();
    }
    return this.stepTwoCenterTabbedPane;
    	
    }   
    public List<UsecaseTabPanel> getListUsecaseTabPanel() {
    	if(this.listUsecaseTabPanel == null)
    		listUsecaseTabPanel = new ArrayList<UsecaseTabPanel>();
		return listUsecaseTabPanel;
	}
	public List<SequenceTabPanel> getListSequenceTabPanel() {
		if(this.listSequenceTabPanel == null)
			listSequenceTabPanel = new ArrayList<SequenceTabPanel>();
		return listSequenceTabPanel;
	}
	public JPanel getOpreationPart() {
		// TODO Auto-generated method stub
		return this.opreationpanel;
	}
	public JPanel getCenterTabPanel(){
		// TODO Auto-generated method stub
		return this.centerTabPanel;
	}
	public JPanel getconsolepartPanel()
	{

		return consolePartPanel;
	}
	/**
     * @return the menu factory instance
     */
    public MenuFactory getMenuFactory()
    {
        if (this.menuFactory == null)
        {
            menuFactory = new MenuFactory();
        }
        return this.menuFactory;
    }

    public JPanel getStepJLabel() {   	
		return stepJLabel;
	}
	public JPanel getpanel()
	{
		return this.panel;
	}
    
	public TitlePanel getTitlePanel() {
		if(this.titlePanel == null)
		{
			titlePanel = new TitlePanel(this);
		}
		return titlePanel;
	}

	public SteponeBottomPanel getsteponebottmopanel()
	{
		if(this.steponeBottomPanel == null)
		{
			this.steponeBottomPanel = new SteponeBottomPanel(this);
		}	
		return this.steponeBottomPanel;
	}
	public JPanel getinformationPanel()
	{
		return informationPanel;
	}
	private JPanel getworkpanel()
	{
		return workPanel;
	}
	public Outputinformation getouOutputinformation()
	{
		if(this.outputinformation == null)
		{
			outputinformation = new Outputinformation(this)
			{
				public void paint(Graphics g) {
		            super.paint(g);
		            java.awt.Rectangle rect = this.getBounds();
		            int width = (int) rect.getWidth() - 1;
		            int height = (int) rect.getHeight() - 1;
		            Graphics2D g2 = (Graphics2D)g;
		            g2.setStroke(new BasicStroke(3f));
		            g2.setColor(new Color(188,188,188));
		            g2.drawLine(0, 0, width, 0);
		          }
			};
		}
		return outputinformation;
	}
	public JPanel getReduceOrEnlargePanel()
	{
		if(this.ReduceOrEnlargePanel == null)
		{
			ReduceOrEnlargePanel = new JPanel();
		}
		return ReduceOrEnlargePanel;
	}
	public JPanel getstepOneCenterRightPanel()
	{
		if(this.stepOneCenterRightPanel == null)
		{
			stepOneCenterRightPanel = new StepOneCenterRightPanel(this)
			{
				public void paint(Graphics g) {
		            super.paint(g);
		            java.awt.Rectangle rect = this.getBounds();
		            int width = (int) rect.getWidth() - 1;
		            int height = (int) rect.getHeight() - 1;
		            Graphics2D g2 = (Graphics2D)g;
		            g2.setStroke(new BasicStroke(3f));
		            g2.setColor(new Color(188,188,188));
		            g2.drawLine(0, 0, 0, height);
		          }
			};
		}
		return stepOneCenterRightPanel;
	}
	public JSplitPane getbotoomJSplitPane()
	{
		return this.botoomJSplitPane;
	}
	
	
    public JPanel getStepTwoPanel() {
		if(this.stepTwoPanel == null)
		{
			stepTwoPanel = new JPanel();
		}
		return stepTwoPanel;
	}

    
	public JRadionPanel getjRadionPanel() {
		if(this.jRadionPanel == null)
		{
			jRadionPanel = new JRadionPanel(this);
		}
		return jRadionPanel;
	}
    
	public List<String> getNameList() {
		if(this.nameList == null)
		{
			nameList = new ArrayList<String>();
		}
		return nameList;
	}

	public StepTwoExpand getStepTwoExpand() {
		if(this.stepTwoExpand == null)
		{
			stepTwoExpand = new StepTwoExpand(this);
		}
		return stepTwoExpand;
	}
	public StepTwoExpandBottom getStepTwoBottomPanel() {
		if(this.stepTwoExpandBottom == null)
		{
			stepTwoExpandBottom = new StepTwoExpandBottom(this);
		}
		return stepTwoExpandBottom;
	}
	public StepTwoCenterRightPanel getStepTwoCenterRightPanel() {
		if(this.stepTwoCenterRightPanel == null)
		{
			stepTwoCenterRightPanel = new StepTwoCenterRightPanel(this)
			{
				public void paint(Graphics g) {
		            super.paint(g);
		            java.awt.Rectangle rect = this.getBounds();
		            int width = (int) rect.getWidth() - 1;
		            int height = (int) rect.getHeight() - 1;
		            Graphics2D g2 = (Graphics2D)g;
		            g2.setStroke(new BasicStroke(3f));
		            g2.setColor(new Color(188,188,188));
		            g2.drawLine(0, 0, 0, height);
		          }
			};;
		}
		return stepTwoCenterRightPanel;
	}
	public StepTwoModelOperation getStepTwoModelOperation() {
		if(this.stepTwoModelOperation == null)
		{
			stepTwoModelOperation = new StepTwoModelOperation(this);
		}
		return stepTwoModelOperation;
	}
	
	public StepTwoModelExpandTabbedPane getStepTwoModelExpandTabbedPane() {
		if(this.stepTwoModelExpandTabbedPane == null)
		{
			stepTwoModelExpandTabbedPane = new StepTwoModelExpandTabbedPane();
		}
		return stepTwoModelExpandTabbedPane;
	}	

	public StepTwoExchangeMarkovTree getStepTwoExchangeMarkovTree() {
	
		if(this.stepTwoExchangeMarkovTree == null)
		{
			stepTwoExchangeMarkovTree = new StepTwoExchangeMarkovTree(this);
		}
		return stepTwoExchangeMarkovTree;
	}
  
	public List<String> getModelNameList() {
		return modelNameList;
	}

	public void setModelNameList(List<String> modelNameList) {
		this.modelNameList = modelNameList;
	}

	public StepTwoExchangeOperation getStepTwoExchangeOperation() {
		if(this.stepTwoExchangeOperation == null)
		{
			stepTwoExchangeOperation = new StepTwoExchangeOperation(this,this.getMenuFactory().getFileMenu(this));
		}
		return stepTwoExchangeOperation;
	}
	
	
	public StepTwoExchangeTabbedPane getStepTwoExchangeTabbedPane() {
		if(this.stepTwoExchangeTabbedPane == null)
		{
			stepTwoExchangeTabbedPane = new StepTwoExchangeTabbedPane();
		}
		return stepTwoExchangeTabbedPane;
	}

   
	public StepTwoCaseExpandTabbedPane getStepTwoCaseExpandTabbedPane() {
		if(this.stepTwoCaseExpandTabbedPane == null)
		{
			stepTwoCaseExpandTabbedPane = new StepTwoCaseExpandTabbedPane();
		}
		return stepTwoCaseExpandTabbedPane;
	}
    
	

	public StepTwoCaseExpandOperation getStepTwoCaseOperation() {
		if(this.stepTwoCaseExpandOperation == null)
		{
			stepTwoCaseExpandOperation = new StepTwoCaseExpandOperation(this);
		}
		return stepTwoCaseExpandOperation;
	}

	public StepTwoCaseExpandTree getStepTwoCaseExpandTree() {
		if(this.stepTwoCaseExpandTree == null)
		{
			stepTwoCaseExpandTree = new StepTwoCaseExpandTree();
		}
		return stepTwoCaseExpandTree;
	}

	public StepTwoEvaluateOperation getStepTwoEvaluateOperation() {
		if(this.stepTwoEvaluateOperation == null)
		{
			stepTwoEvaluateOperation = new StepTwoEvaluateOperation(this);
		}
		return stepTwoEvaluateOperation;
	}
    



	public StepTwoEvaluateTabbedPane getStepTwoEvaluateTabbedPane() {
		if(this.stepTwoEvaluateTabbedPane == null)
		{
			stepTwoEvaluateTabbedPane = new StepTwoEvaluateTabbedPane();
		}
		return stepTwoEvaluateTabbedPane;
	}
	/**
     * Tabbed pane instance
     */
    private JTabbedPane tabbedPane;
    
    /**
     * Panel added is not diagram is opened
     */
    private WelcomePanel welcomePanel;
    private HomePanel homepanel;
    private JPanel stepJLabel=new JPanel(){
    	public void paint(Graphics g) {
            super.paint(g);
            java.awt.Rectangle rect = this.getBounds();
            int width = (int) rect.getWidth() - 1;
            int height = (int) rect.getHeight() - 1;
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(3f));
            g2.setColor(new Color(188,188,188));
            g2.drawLine(this.getX(), this.getY()+height, this.getX()+width, this.getY()+height);
          }
    };
    
    private ModelTransformationPanel modelTransformationPanel;
    private StepOneCenterSequenceTabbedPane stepOneCenterSequenceTabbedPane; //顺序图
    private StepOneCenterUseCaseTabbedPane stepOneCenterUseCaseTabbedPane;  //用例图
    private StepTwoCenterTabbedPane stepTwoCenterTabbedPane;
	private List<UsecaseTabPanel> listUsecaseTabPanel;
    private List<SequenceTabPanel> listSequenceTabPanel;

	//private ConsolePart consolePart;
    private JPanel consolePartPanel = new JPanel()
    { 	
    	public void paint(Graphics g){
            super.paint(g);
            java.awt.Rectangle rect = this.getBounds();
            int width = (int) rect.getWidth() - 1;
            int height = (int) rect.getHeight() - 1;
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(3f));
            g2.setColor(new Color(188,188,188));
            g2.drawLine(0, 0, width, 0);
          }
    };
    
//    private ProjectTree projectTree;
    private UsecaseTreePanel usecaseTree;
    
	private SequenceTreePanel sequenceTree;
    
    private JPanel opreationpanel=new JPanel()
    {
    	public void paint(Graphics g) {
            super.paint(g);
            java.awt.Rectangle rect = this.getBounds();
            int width = (int) rect.getWidth() - 1;
            int height = (int) rect.getHeight() - 1;
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(3f));
            g2.setColor(new Color(188,188,188));
            g2.drawLine(width, 0, width, height);
          }
    };
    
    private JPanel centerTabPanel=new JPanel();
    
    private JPanel workPanel = new JPanel();  //绘图与消息区域
//    {
//    	public void paint(Graphics g) {
//            super.paint(g);
//            java.awt.Rectangle rect = this.getBounds();
//            int width = (int) rect.getWidth() - 1;
//            int height = (int) rect.getHeight() - 1;
//            Graphics2D g2 = (Graphics2D)g;
//            g2.setStroke(new BasicStroke(3f));
//            g2.setColor(new Color(188,188,188));
//            g2.drawLine(0, 0, 0, height);
//          }
//    }
    private HeadTitle headTitle;
    
    private TitlePanel titlePanel;
    private JPanel StepLeftButtonPanel; 
    
    private StepOneButton stepOneButton;
    
    private StepOneOperationButton stepOneOperationButton;
    
    private JPanel stepTwoPanel;
    

	private SteponeBottomPanel steponeBottomPanel; 
    
    private JSplitPane botoomJSplitPane = new JSplitPane();
    
    private JPanel panel = new JPanel()
    {
    	public void paint(Graphics g) {
            super.paint(g);
            java.awt.Rectangle rect = this.getBounds();
            int width = (int) rect.getWidth() - 1;
            int height = (int) rect.getHeight() - 1;
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(3f));
            g2.setColor(new Color(188,188,188));
            g2.drawLine(0, height, width, height);
//            g2.drawLine(width, 0, width, height);
          }
    };
    private JPanel informationPanel = new JPanel();
//    {
//    	public void paint(Graphics g) {
//            super.paint(g);
//            java.awt.Rectangle rect = this.getBounds();
//            int width = (int) rect.getWidth() - 1;
//            int height = (int) rect.getHeight() - 1;
//            Graphics2D g2 = (Graphics2D)g;
//            g2.setStroke(new BasicStroke(3f));
//            g2.setColor(new Color(188,188,188));
//            g2.drawLine(0, height, width, height);
//            g2.drawLine(0, 0, 0, height);
//          }
//    };
    private Outputinformation outputinformation;
    
    private JPanel ReduceOrEnlargePanel;
    
    private StepOneCenterRightPanel stepOneCenterRightPanel; //缩放组件
    

	/**
     * Main panel
     */
    private JPanel mainPanel;

    /**
     * Menu factory instance
     */
    private MenuFactory menuFactory;

    /**
     * GUI Theme manager
     */
    @InjectedBean
    private ThemeManager themeManager;

    /**
     * Needed to display dialog boxes
     */
    @InjectedBean
    private DialogFactory dialogFactory;

    /**
     * Needed to open files
     */
    @InjectedBean
    private IFileChooserService fileChooserService;
    
    @ResourceBundleBean(key="app.name")
    private String applicationName;
    
    @ResourceBundleBean(key="app.icon")
    private Image applicationIcon;

    
    //第二步扩展
    private JRadionPanel jRadionPanel;
    
    private List<String> nameList;
    
    private StepTwoExpand stepTwoExpand;

    private StepTwoExpandBottom stepTwoExpandBottom;

    private StepTwoCenterRightPanel stepTwoCenterRightPanel;
    
    private StepTwoModelOperation stepTwoModelOperation;
    
    private StepTwoModelExpandTabbedPane stepTwoModelExpandTabbedPane;

	
   //用例扩展
    private StepTwoCaseExpandTabbedPane stepTwoCaseExpandTabbedPane;
    
    private StepTwoCaseExpandOperation stepTwoCaseExpandOperation;
    
    private StepTwoCaseExpandTree stepTwoCaseExpandTree;
    
    //评估
    private StepTwoEvaluateOperation stepTwoEvaluateOperation;
    
    private StepTwoEvaluateTabbedPane stepTwoEvaluateTabbedPane;
   //第二步验证  
        
    private StepTwoExchangeMarkovTree stepTwoExchangeMarkovTree;
    
    private List<String> modelNameList;
    
    private StepTwoExchangeOperation stepTwoExchangeOperation;
    
    private StepTwoExchangeTabbedPane stepTwoExchangeTabbedPane;
	/**
     * All disgram workspaces
     */
    private List<IWorkspace> UseCaseworkspaceList = new ArrayList<IWorkspace>(); //用例图    
    private List<IWorkspace> SequencespaceList=new ArrayList<IWorkspace>();//顺序图
    private List<IWorkspace> TimingDiagramspaceList=new ArrayList<IWorkspace>();//时序图
    private List<IWorkspace> MarkovpaceList=new ArrayList<IWorkspace>();//状态图
    private List<IWorkspace> UppaalspaceList=new ArrayList<IWorkspace>();//时间自动机
    // workaround for bug #4646747 in J2SE SDK 1.4.0
    private static java.util.HashMap<Class<?>, BeanInfo> beanInfos;
    static
    {
        beanInfos = new java.util.HashMap<Class<?>, BeanInfo>();
        Class<?>[] cls = new Class<?>[]
        {
                Point2D.Double.class,
                BentStyle.class,
                ArrowHead.class,
                LineStyle.class,
                IGraph.class,
                AbstractNode.class,
        };
        for (int i = 0; i < cls.length; i++)
        { 
            try
            {
                beanInfos.put(cls[i], java.beans.Introspector.getBeanInfo(cls[i]));
            }
            catch (java.beans.IntrospectionException ex)
            {
            }
        }
    }
}
