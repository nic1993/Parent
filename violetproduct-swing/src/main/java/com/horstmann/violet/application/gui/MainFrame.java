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

import static org.hamcrest.CoreMatchers.nullValue;

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
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.beans.BeanInfo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam.Mode;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.synth.SynthSeparatorUI;
import javax.swing.tree.DefaultMutableTreeNode;

import org.eclipse.draw2d.geometry.Rectangle;

import com.horstmann.violet.application.StepFourTestCase.NameRadionPanel;
import com.horstmann.violet.application.StepFourTestCase.StepFourBottom;
import com.horstmann.violet.application.StepFourTestCase.StepFourOperation;
import com.horstmann.violet.application.StepFourTestCase.StepFourTabbedPane;
import com.horstmann.violet.application.StepFourTestCase.StepFourTestCase;
import com.horstmann.violet.application.StepOneBuildModel.HeadTitle;
import com.horstmann.violet.application.StepOneBuildModel.JRadionPanel;
import com.horstmann.violet.application.StepOneBuildModel.ModelPanel;
import com.horstmann.violet.application.StepOneBuildModel.Outputinformation;
import com.horstmann.violet.application.StepOneBuildModel.SequenceTreePanel;
import com.horstmann.violet.application.StepOneBuildModel.StepOneButton;
import com.horstmann.violet.application.StepOneBuildModel.StepOneCenterRightPanel;
import com.horstmann.violet.application.StepOneBuildModel.StepOneCenterSequenceTabbedPane;
import com.horstmann.violet.application.StepOneBuildModel.StepOneCenterUseCaseTabbedPane;
import com.horstmann.violet.application.StepOneBuildModel.StepOneOperationButton;
import com.horstmann.violet.application.StepOneBuildModel.SteponeBottomPanel;
import com.horstmann.violet.application.StepOneBuildModel.TitlePanel;
import com.horstmann.violet.application.StepOneBuildModel.UsecaseTreePanel;
import com.horstmann.violet.application.StepThreeTestCase.NoTimeCaseOperation;
import com.horstmann.violet.application.StepThreeTestCase.NoTimeCaseOperation1;
import com.horstmann.violet.application.StepThreeTestCase.NoTimeMarkovFileRadion;
import com.horstmann.violet.application.StepThreeTestCase.NoTimeSeqOperation;
import com.horstmann.violet.application.StepThreeTestCase.NoTimeSeqOperation1;
import com.horstmann.violet.application.StepThreeTestCase.StepThreeBottom;
import com.horstmann.violet.application.StepThreeTestCase.StepThreeChooseModelPattern;
import com.horstmann.violet.application.StepThreeTestCase.StepThreeChoosePattern;
import com.horstmann.violet.application.StepThreeTestCase.StepThreeLeftButton;
import com.horstmann.violet.application.StepThreeTestCase.StepThreeNoTimeCustomTabbedPane;
import com.horstmann.violet.application.StepThreeTestCase.StepThreeNoTimeSeqTabbedPane;
import com.horstmann.violet.application.StepThreeTestCase.StepThreeNoTimeCaseTabbedPane;
import com.horstmann.violet.application.StepThreeTestCase.StepThreeTimeCustomTabbedPane;
import com.horstmann.violet.application.StepThreeTestCase.StepThreeTimeTabbedPane;
import com.horstmann.violet.application.StepThreeTestCase.TimeCaseOperation;
import com.horstmann.violet.application.StepThreeTestCase.TimeCaseOperation1;
import com.horstmann.violet.application.StepThreeTestCase.TimeExpandOperation;
import com.horstmann.violet.application.StepThreeTestCase.TimeExpandTabbedPane;
import com.horstmann.violet.application.StepThreeTestCase.TimeMarkovFileRadio;
import com.horstmann.violet.application.StepTwoCaseExpand.StepTwoCaseExpandTabbedPane;
import com.horstmann.violet.application.StepTwoCaseExpand.StepTwoCaseExpandTree;
import com.horstmann.violet.application.StepTwoCaseExpand.StepTwoCaseExpandOperation;
import com.horstmann.violet.application.StepTwoEvaluate.StepTwoEvaluateOperation;
import com.horstmann.violet.application.StepTwoEvaluate.StepTwoEvaluateTabbedPane;
import com.horstmann.violet.application.StepTwoEvaluate.StepTwoEvaluateTree;
import com.horstmann.violet.application.StepTwoExchange.StepTwoExchangeOperation;
import com.horstmann.violet.application.StepTwoExchange.StepTwoExchangeTabbedPane;
import com.horstmann.violet.application.StepTwoExchange.StepTwoExchangeTree;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoExpandBottom;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoCenterRightPanel;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoExpand;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoModelExpandTabbedPane;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoModelOperation;
import com.horstmann.violet.application.consolepart.ConsolePart;
import com.horstmann.violet.application.help.AboutDialog;
import com.horstmann.violet.application.menu.MenuFactory;
import com.horstmann.violet.framework.dialog.DialogFactory;
import com.horstmann.violet.framework.file.GraphFile;
import com.horstmann.violet.framework.file.IFile;
import com.horstmann.violet.framework.file.IGraphFile;
import com.horstmann.violet.framework.file.chooser.IFileChooserService;
import com.horstmann.violet.framework.file.persistence.JFileWriter;
import com.horstmann.violet.framework.file.persistence.XStreamBasedPersistenceService;
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
import com.horstmann.violet.product.diagram.sequence.CombinedFragment;
import com.horstmann.violet.product.diagram.sequence.RefNode;
import com.horstmann.violet.product.diagram.usecase.UseCaseNode;
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.IWorkspaceListener;
import com.horstmann.violet.workspace.Workspace;
import com.horstmann.violet.workspace.WorkspacePanel;
import com.horstmann.violet.workspace.editorpart.behavior.EditSelectedBehavior;

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
        createFile();
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
    private void createFile()
    {
    	File[] roots = File.listRoots();
    	String DefaultRoute = roots[1].getAbsolutePath() + "ModelDriverProjectFile\\";
    	File defaultFile = new File(DefaultRoute);
    	if(!defaultFile.exists())
    		defaultFile.mkdirs();
    	setBathRoute(DefaultRoute);
    	//添加文件
    	File ucaseEAFile = new File(getBathRoute() + "/UseCaseDiagram/EAXML"); 
        if(!ucaseEAFile.exists())
        	ucaseEAFile.mkdirs();
        
    	File ucaseVioletFile = new File(getBathRoute() + "/UseCaseDiagram/Violet");
    	if(!ucaseVioletFile.exists())
    		ucaseVioletFile.mkdirs();
    	
    	File seqEAFile = new File(getBathRoute() + "/SequenceDiagram/EAXML"); 
    	if(!seqEAFile.exists())
    		seqEAFile.mkdirs();
    
    	File seqVioletFile = new File(getBathRoute() + "/SequenceDiagram/Violet");
    	if(!seqVioletFile.exists())
    		seqVioletFile.mkdirs();
    	
    	File notimeMarkovFile = new File(getBathRoute() + "/NoTimeMarkov");
    	if(!notimeMarkovFile.exists())
    		notimeMarkovFile.mkdirs();

    	File timeMarkovFile = new File(getBathRoute() + "/TimeMarkov");
    	if(!timeMarkovFile.exists())
    		timeMarkovFile.mkdirs();

    	File testcaseFile = new File(getBathRoute() + "/TestCase");
    	if(!testcaseFile.exists())
    		testcaseFile.mkdirs();
    	
    	File extendMarkovFile = new File(getBathRoute() + "/extendMarkov");
    	if(!extendMarkovFile.exists())
    		extendMarkovFile.mkdirs();
    }
    /**
     * Adds a tabbed pane (only if not already added)
     * 
     * @param c the component to display in the internal frame
     */
    public void  addTabbedPane(final IWorkspace workspace)
    {
        replaceWelcomePanelByTabbedPane(); 
        workspace.getAWTComponent().getScrollableEditorPart().getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// TODO Auto-generated method stub
				renewPanel();
			}
		});
        workspace.getAWTComponent().getScrollableEditorPart().getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// TODO Auto-generated method stub
				renewPanel();
			}
		});
    	//在添加图形元素的时候，首先判断下是哪种图形
        if(workspace.getTitle().toString().endsWith(".ucase.violet.xml")
        		||workspace.getTitle().toString().substring(2, 4).equals("Us"))//如果是用例图
     	{
        	
     		if(this.getActiveModelPanel().getUseCaseworkspaceList().contains(workspace))
     		{
     			return;
     		}
     		this.getActiveModelPanel().getUseCaseworkspaceList().add(workspace);
     		
     		this.getCenterTabPanel().removeAll();
     		this.getCenterTabPanel().add(workspace.getAWTComponent());
     		
     		listenToDiagramPanelEvents(workspace,UseCaseworkspaceList);   		
     	    repaint();    		              
     	}

     	if(workspace.getTitle().toString().endsWith(".seq.violet.xml")
     			||workspace.getTitle().toString().substring(2, 4).equals("Se"))//如果是顺序图
     			
 		{
     	
     		if(this.getActiveModelPanel().getSequencespaceList().contains(workspace))
     		{
     			return;
     		}
     		
     		this.getActiveModelPanel().getSequencespaceList().add(workspace);
            
     		this.getCenterTabPanel().removeAll();
            this.getCenterTabPanel().add(workspace.getAWTComponent());
     		
     		listenToDiagramPanelEvents(workspace,this.getActiveModelPanel().getSequencespaceList());   		
     	    repaint();    		              
 		}    
     	if(workspace.getTitle().toString().endsWith(".markov.violet.xml")
     			||workspace.getTitle().toString().substring(2, 4).equals("Ma"))//如果是状态图
 		{
     		if(this.MarkovpaceList.contains(workspace) || this.ExpandMarkovpaceList.contains(workspace) )
     		{
     			if(this.getCenterTabPanel().getComponent(0).equals(getStepTwoExchangeTabbedPane())){
     	     		this.getStepTwoExchangeTabbedPane().getExchangeResults().removeAll();
     	     		this.getStepTwoExchangeTabbedPane().getExchangeResults()
     	     		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
     	     		}
     	     		else if (this.getCenterTabPanel().getComponent(0).equals(getTimeExpandTabbedPane())) {
     	     			this.getTimeExpandTabbedPane().getExpandResults().removeAll();
     	         		this.getTimeExpandTabbedPane().getExpandResults()
     	         		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
     				}
     			return;
     		}
     		
     		if(this.getCenterTabPanel().getComponent(0).equals(getStepTwoExchangeTabbedPane())){
     		this.getStepTwoExchangeTabbedPane().getExchangeResults().removeAll();
     		this.getStepTwoExchangeTabbedPane().getExchangeResults()
     		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
     		this.MarkovpaceList.add(workspace);
     		}
     		
     		else if (this.getCenterTabPanel().getComponent(0).equals(getTimeExpandTabbedPane())) {
     			this.getTimeExpandTabbedPane().getExpandResults().removeAll();
         		this.getTimeExpandTabbedPane().getExpandResults()
         		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
         		this.ExpandMarkovpaceList.add(workspace);
			}
     		 listenToDiagramPanelEvents(workspace,MarkovpaceList);
     	    repaint();     
     	   renewPanel();
 		}
    }
    
    
    public void  addTabbedPane(final IWorkspace workspace ,String title)
    {
//        replaceWelcomePanelByTabbedPane(); 
        workspace.getAWTComponent().getScrollableEditorPart().getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// TODO Auto-generated method stub
				renewPanel();
			}
		});
        workspace.getAWTComponent().getScrollableEditorPart().getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// TODO Auto-generated method stub
				renewPanel();
			}
		});
    	//在添加图形元素的时候，首先判断下是哪种图形
        if(workspace.getTitle().toString().endsWith(".ucase.violet.xml")
        		||workspace.getTitle().toString().substring(2, 4).equals("Us"))//如果是用例图
     	{
        	if(this.getActiveModelPanel().getUseCaseworkspaceList().contains(workspace))
     		{
     			return;
     		}
        	workspace.setName(title);
     		this.getActiveModelPanel().getUseCaseworkspaceList().add(workspace);
     		this.getCenterTabPanel().removeAll();
            this.getCenterTabPanel().add(workspace.getAWTComponent());
            

     		listenToDiagramPanelEvents(workspace,UseCaseworkspaceList);   		
     	    repaint();    		                           
     	}

     	if(workspace.getTitle().toString().endsWith(".seq.violet.xml")
     			||workspace.getTitle().toString().substring(2, 4).equals("Se"))//如果是顺序图
     			
 		{
     		if(this.getActiveModelPanel().getSequencespaceList().contains(workspace))
     		{
     			return;
     		}
     		workspace.setName(title);
     		this.getActiveModelPanel().getSequencespaceList().add(workspace);
     		this.getCenterTabPanel().removeAll();
            this.getCenterTabPanel().add(workspace.getAWTComponent());
     		listenToDiagramPanelEvents(workspace,SequencespaceList);   		
     	    repaint();    		              
 		}    
     	if(workspace.getTitle().toString().endsWith(".markov.violet.xml")
     			||workspace.getTitle().toString().substring(2, 4).equals("Ma"))//如果是状态图
 		{
     		if(this.MarkovpaceList.contains(workspace) || this.ExpandMarkovpaceList.contains(workspace) )
     		{
     			if(title.equals("step3")){
     	     		this.getStepTwoExchangeTabbedPane().getExchangeResults().removeAll();
     	     		this.getStepTwoExchangeTabbedPane().getExchangeResults()
     	     		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
     	     		}
     	     		else {
     	     			this.getTimeExpandTabbedPane().getExpandResults().removeAll();
     	         		this.getTimeExpandTabbedPane().getExpandResults()
     	         		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
     				}
     			return;
     		}
     		
     		if(this.getCenterTabPanel().getComponent(0).equals(getStepTwoExchangeTabbedPane())){
     		this.getStepTwoExchangeTabbedPane().getExchangeResults().removeAll();
     		this.getStepTwoExchangeTabbedPane().getExchangeResults()
     		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
     		this.MarkovpaceList.add(workspace);
     		}
     		else if (this.getCenterTabPanel().getComponent(0).equals(getTimeExpandTabbedPane())) {
     			this.getTimeExpandTabbedPane().getExpandResults().removeAll();
         		this.getTimeExpandTabbedPane().getExpandResults()
         		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
         		this.ExpandMarkovpaceList.add(workspace);
			}
     		 listenToDiagramPanelEvents(workspace,MarkovpaceList);
     	    repaint();     
 		}
     	renewPanel();
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
    
    public void removeModelPanel(ModelPanel modelPanel)
    {
    	//如果当为当前活动的modelPanel
    	if(getActiveModelPanel() == modelPanel)
    	{
    		this.getCenterTabPanel().removeAll();
    		setActiveModelPanel(null);
    	}
    	this.modelPanels.remove(modelPanel);
    	this.getsteponeButton().getTreePanel().remove(modelPanel);
    	if(this.getPackageRoute().get(modelPanel.getTitle().getText()) != null)
    	{
    		this.getPackageRoute().remove(modelPanel.getTitle().getText());
    	}
        renewPanel();
    }
    
    public void saveModelPanel(ModelPanel modelPanel)
    {
    	JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int i = jFileChooser.showSaveDialog(null);
        if(i== jFileChooser.APPROVE_OPTION){ //打开文件
        	File file = jFileChooser.getSelectedFile(); 
            String path = jFileChooser.getSelectedFile().getAbsolutePath() ; 
            String name = jFileChooser.getSelectedFile().getName();
            //生成存放用例图的文件夹
            String packagePath = path + "/" +  modelPanel.getTitle().getText();
            this.getModelPanelMap().put(modelPanel, packagePath);
            
            File packagefile =  new File(packagePath);
            if(!packagefile.exists())
            {
            	packagefile.mkdirs();
            }
            
            String ucasepath = packagePath + "/用例图";
            File ucasefile = new File(ucasepath);
            if(!ucasefile.exists())
            {
            	ucasefile.mkdirs();
            }
            //生成存放顺序图的文件夹
            String seqpath = packagePath + "/顺序图";
            File seqfile = new File(seqpath);
            if(!seqfile.exists())
            {
            	seqfile.mkdirs();
            }
            
            modelPanel.setTemporarySeqFile(seqpath);
            modelPanel.setTemporaryUcaseFile(ucasepath);
            
            XStreamBasedPersistenceService xStreamBasedPersistenceService = new XStreamBasedPersistenceService();
            
            //保存所有用例图
            for(int j = 0;j < modelPanel.getUseCaseworkspaceList().size();j++)
            {
            try {
            IWorkspace workspace = modelPanel.getUseCaseworkspaceList().get(j);
            File ucaseFile = new File(ucasepath+"/"+workspace.getName()+".ucase.violet.xml");
			JFileWriter jFileWriter = new JFileWriter(ucaseFile);
            OutputStream out = jFileWriter.getOutputStream();
            IGraph graph = workspace.getGraphFile().getGraph();
            xStreamBasedPersistenceService.write(graph, out);
            } catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            }
            
            //保存所有顺序图
            for(int j = 0;j < modelPanel.getSequencespaceList().size();j++)
            {
            try {
            IWorkspace workspace = modelPanel.getSequencespaceList().get(j);
            File ucaseFile = new File(seqpath+"/"+workspace.getName()+".seq.violet.xml");
			JFileWriter jFileWriter = new JFileWriter(ucaseFile);
            OutputStream out = jFileWriter.getOutputStream();
            IGraph graph = workspace.getGraphFile().getGraph();
            xStreamBasedPersistenceService.write(graph, out);
            } catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            }
            modelPanel.setSave(true);
            getPackageRoute().put(modelPanel.getTitle().getText(), packagePath);
        }else{
            System.out.println("没有选中文件");
        }
    }
    
    public void saveTemporary(ModelPanel modelPanel)
    {
    	    
    	    
            //生成存放用例图的文件夹
            String packagePath = this.getBathRoute()  +  modelPanel.getTitle().getText();
            this.getModelPanelMap().put(modelPanel, packagePath);
            
            System.out.println(packagePath);
            File packagefile =  new File(packagePath);
            if(!packagefile.exists())
            {
            	packagefile.mkdirs();
            }
            
            String ucasepath = packagePath + "/用例图/";
            File ucasefile = new File(ucasepath);
            if(!ucasefile.exists())
            {
            	ucasefile.mkdirs();
            }
            //生成存放顺序图的文件夹
            String seqpath = packagePath + "/顺序图/";
            File seqfile = new File(seqpath);
            if(!seqfile.exists())
            {
            	seqfile.mkdirs();
            }
            
            modelPanel.setTemporaryUcaseFile(ucasepath);
            modelPanel.setTemporarySeqFile(seqpath);
            getPackageRoute().put(modelPanel.getTitle().getText(), packagePath);
       
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
    private static DefaultMutableTreeNode getKey(Map<DefaultMutableTreeNode, IWorkspace> map,IWorkspace panel)
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
//    	 if(getCenterTabPanel().getComponent(0).equals(getStepOneCenterUseCaseTabbedPane())){
//        if (this.UseCaseworkspaceList.contains(diagramPanel))       		
//        {   
//        int pos = getStepOneCenterUseCaseTabbedPane().getSelectedIndex();
//        getStepOneCenterUseCaseTabbedPane().remove(pos);
//		if(getStepOneCenterUseCaseTabbedPane().getTabCount() == 0)
//		{
//			getCenterTabPanel().removeAll();
//		}
//        this.UseCaseworkspaceList.remove(diagramPanel);
//        
//        //删除树上节点
////        Map<DefaultMutableTreeNode, JPanel> map = getUsecaseTree().getHashMap();
////        JPanel panel = this.getStepOneCenterUseCaseTabbedPane().getUsecaseDiagramTabbedPane().get(pos);
////        DefaultMutableTreeNode removeNode = getKey(map, panel);
////        this.getUsecaseTree().getUsecasetreemodel().removeNodeFromParent(removeNode);
////        this.getStepOneCenterUseCaseTabbedPane().getUsecaseDiagramTabbedPane().remove(pos);
//        repaint();
//        }
//    	}
//
//    	else if(getCenterTabPanel().getComponent(0).equals(getStepOneCenterSequenceTabbedPane())){
//        if (this.SequencespaceList.contains(diagramPanel))       		
//        {   
//        int pos = getStepOneCenterSequenceTabbedPane().getSelectedIndex();
//        getStepOneCenterSequenceTabbedPane().remove(pos);     
//        if(getStepOneCenterSequenceTabbedPane().getTabCount() == 0)
//		{
//			getCenterTabPanel().removeAll();
//		}
//        this.SequencespaceList.remove(diagramPanel);
//        
//        Map<DefaultMutableTreeNode, IWorkspace> map = getsequencetree().getHashMap();
//        DefaultMutableTreeNode removeNode = getKey(map, diagramPanel);
//        this.getsequencetree().getSequencetreemodel().removeNodeFromParent(removeNode);
//        repaint();
//        }
//        }
//    	else if (this.MarkovpaceList.contains(diagramPanel))       		
//        {   
//        int pos = this.MarkovpaceList.indexOf(diagramPanel);
//        getStepOneCenterSequenceTabbedPane().getUMLTabbedPane(diagramPanel,pos).remove(pos);
//        this.MarkovpaceList.remove(diagramPanel);
//        repaint();
//        }
        if(diagramPanel.getTitle().equals("1.Use case diagram"))
        {
        	UseCaseworkspaceList.remove(diagramPanel);
        	getCenterTabPanel().removeAll();
        }
        else {
        	SequencespaceList.remove(diagramPanel);
        	getCenterTabPanel().removeAll();
		}
        renewPanel();
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
        		||!this.MarkovpaceList.isEmpty()
        		||!this.SequencespaceList.isEmpty()
        		||!this.ExpandMarkovpaceList.isEmpty();
    }

    public List<IWorkspace> getUseCaseWorkspaceList()
    {
        return UseCaseworkspaceList;
    }
    public List<IWorkspace> getSequenceWorkspaceList()
    {
        return SequencespaceList;
    }
    public List<IWorkspace> getMarkovWorkspaceList()
    {
        return MarkovpaceList;
    }
    public List<IWorkspace> getExpandMarkovpaceList() {
		return ExpandMarkovpaceList;
	}

    
	public ModelPanel getActiveModelPanel() {
		return ActiveModelPanel;
	}

	public void setActiveModelPanel(ModelPanel activeModelPanel) {
		ActiveModelPanel = activeModelPanel;
	}

	/**
     * @return selected diagram file path (or null if not one is selected; that should never happen)
     */
    public IWorkspace getStepOneActiveWorkspace()
    {
    	if(getCenterTabPanel().getComponentCount() != 0)
    	{
    		IWorkspace workspace = ((WorkspacePanel)getCenterTabPanel().getComponent(0)).getWorkspace();//获取当前workspace
    		return workspace;
    	}
        return null;
    }
    
    public IWorkspace getActiveWorkspace()
    {
    	if(getCenterTabPanel().getComponentCount() != 0)
    	{
    		if(getCenterTabPanel().getComponent(0).equals(getStepTwoExchangeTabbedPane()))
        	{
        		if(getStepTwoExchangeTabbedPane().getExchangeResults().getComponentCount() != 0)
        		{
        			int index = MarkovpaceList.size() -1;
        			return MarkovpaceList.get(index);
        		}
        		else{
        			return null;
        		}
        	}
        	else if (getCenterTabPanel().getComponent(0).equals(getTimeExpandTabbedPane())) {
        		if(getTimeExpandTabbedPane().getExpandResults().getComponentCount() != 0)
        		{
        			int index = ExpandMarkovpaceList.size() -1;
        			return ExpandMarkovpaceList.get(index);
        		}
        		else {
    				return null;
    			}
    		}
        	else
        	{
        		if(getCenterTabPanel().getComponent(0).getClass().getSimpleName().toString().equals("WorkspacePanel"))
        		{
            		IWorkspace workspace = ((WorkspacePanel)getCenterTabPanel().getComponent(0)).getWorkspace();//获取当前用例图workspace
            		return workspace;
        		}

        	}
    	}
        return null;
    }
    
    public IWorkspace getActiveListWorkspace()
    {
           return null;
    }
    
   public WelcomePanel getWelcomePanel()
    {
        if (this.welcomePanel == null)
        {
            this.welcomePanel = new WelcomePanel(this.getMenuFactory().getFileMenu(this));
        }
        return this.welcomePanel;
    }
   
   
   public static String getBathRoute() {
	return BathRoute;
}

public void setBathRoute(String bathRoute) {
	BathRoute = bathRoute;
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
    public StepOneOperationButton getStepOperationButton()
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
//    public SequenceTreePanel getsequencetree()
//    {
//    	if(this.sequenceTree == null){
//    		this.sequenceTree = new SequenceTreePanel(this.getMenuFactory().getFileMenu(this), this);
//    	}       
//    	return sequenceTree;  	
//    }
//    public UsecaseTreePanel getUsecaseTree() {
//    	if(this.usecaseTree == null){
//    		this.usecaseTree = new UsecaseTreePanel(this.getMenuFactory().getFileMenu(this), this);
//    	}       
//    	return usecaseTree;  	
//	}

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

            this.getStepJLabel().setBackground(new Color(222,222,222));
            this.getCenterTabPanel().setLayout(new GridLayout());
            this.getCenterTabPanel().add(this.getHeadTitle());
            
            this.getOpreationPart().setMinimumSize(new Dimension(250, 1000));
            this.getOpreationPart().setMaximumSize(new Dimension(250, 1000));
            this.getOpreationPart().setPreferredSize(new Dimension(250, 1000));
            
            this.getpanel().setMinimumSize(new Dimension(400, 120));
            this.getpanel().setMaximumSize(new Dimension(400, 120));
            this.getpanel().setPreferredSize(new Dimension(400, 120));

            this.getStepJLabel().setVisible(false);
            this.getOpreationPart().setVisible(false);
            this.getconsolepartPanel().setVisible(false);
            this.getpanel().setVisible(false);
            this.getinformationPanel().setVisible(false);
            this.getReduceOrEnlargePanel().setVisible(false);
                           
            layout.setConstraints(this.getStepJLabel(), new GBC(0,0,3,1).setFill(GBC.BOTH).setWeight(1, 0));    //标题区域
            layout.setConstraints(this.getOpreationPart(), new GBC(0,1,1,2).setFill(GBC.BOTH).setWeight(0, 1)); //编辑树区域
            layout.setConstraints(this.getconsolepartPanel(), new GBC(0,3,4,1).setFill(GBC.BOTH).setWeight(1, 0));  
            layout.setConstraints(this.getpanel(), new GBC(1,1,2,1).setFill(GBC.BOTH).setWeight(1, 0));           //按钮区域
            layout.setConstraints(this.getReduceOrEnlargePanel(), new GBC(2,2,1,1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(10, 0, 20, 15));
            layout.setConstraints(this.getworkpanel(), new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 1));//绘图区域
            
            new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while (true) {
						try {
							EditSelectedBehavior.Reflock.take();
							RefNode refNode = (RefNode) ((WorkspacePanel)getCenterTabPanel().getComponent(0)).getWorkspace().getEditorPart().getSelectionHandler().getLastSelectedNode();
				            String name = refNode.getText().getText();
				            for(int i = 0;i <getActiveModelPanel().getSequencespaceList().size();i++)
				            {
				            	if(getActiveModelPanel().getSequencespaceList().get(i).getName().equals(name)){
				            		getCenterTabPanel().removeAll();
				            		getCenterTabPanel().add(getActiveModelPanel().getSequencespaceList().get(i).getAWTComponent());
				            	}
				            }
				            renewPanel();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();

        }
        return this.mainPanel;
    }
    public void setUI() {
    	UIManager.put("SplitPane.back  ground", new Color(188,188,188));
    	UIManager.put("Tree.collapsedIcon", new ImageIcon("resources/icons/22x22/collapsed.png"));
		UIManager.put("Tree.expandedIcon", new ImageIcon("resources/icons/22x22/expanded.png")); 	
		
		UIDefaults uiDefaults = UIManager.getLookAndFeelDefaults();
		uiDefaults.put("Label.disabledForeground", Color.gray);
	}
    public void renewPanel()
    {
    	getOpreationPart().updateUI();
    	getworkpanel().updateUI();
    	getconsolepartPanel().updateUI();
    	getReduceOrEnlargePanel().updateUI();
    	getpanel().updateUI();
    }

    public StepTwoCenterTabbedPane getStepTwoCenterTabbedPane()
    {
    if (this.stepTwoCenterTabbedPane== null)
    {
       stepTwoCenterTabbedPane=new StepTwoCenterTabbedPane();
    }
    return this.stepTwoCenterTabbedPane;
    	
    }   

	public  JPanel getOpreationPart() {
		// TODO Auto-generated method stub
		return opreationpanel;
	}
	public JPanel getCenterTabPanel(){
		// TODO Auto-generated method stub
		return this.centerTabPanel;
	}
	public  JPanel getconsolepartPanel()
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
	public  JPanel getpanel()
	{
		return panel;
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
	public  JPanel getworkpanel()
	{
		return workPanel;
	}
	public Outputinformation getOutputinformation()
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
		            g2.drawLine(0, height, width, height);
		          }
			};
		}
		return outputinformation;
	}
	public  JPanel getReduceOrEnlargePanel()
	{
		if(ReduceOrEnlargePanel == null)
		{
			ReduceOrEnlargePanel = new JPanel()
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
		            g2.drawLine(width, 0, width, height);
		            g2.drawLine(0, height, width, height);
		          }
			};
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
//		            g2.drawRect(0, 0, width, height);
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

	public StepTwoExpand getStepTwoExpand() {
		if(this.stepTwoExpand == null)
		{
			stepTwoExpand = new StepTwoExpand(this);
		}
		return stepTwoExpand;
	}
	public StepTwoExpandBottom getStepTwoExpandBottom() {
		if(this.stepTwoExpandBottom == null)
		{
			stepTwoExpandBottom = new StepTwoExpandBottom(this);
		}
		return stepTwoExpandBottom;
	}
	public StepTwoCenterRightPanel getStepTwoCenterRightPanel() {
		if(this.stepTwoCenterRightPanel == null)
		{
			stepTwoCenterRightPanel = new StepTwoCenterRightPanel(this,this.getMenuFactory().getViewMenu(this))
			{
				public void paint(Graphics g) {
		            super.paint(g);
		            java.awt.Rectangle rect = this.getBounds();
		            int width = (int) rect.getWidth() - 1;
		            int height = (int) rect.getHeight() - 1;
		            Graphics2D g2 = (Graphics2D)g;
		            g2.setStroke(new BasicStroke(3f));
		            g2.setColor(new Color(188,188,188));
//		            g2.drawLine(0, 0, 0, height);
		          }
			};
		}
		return stepTwoCenterRightPanel;
	}
	public StepTwoModelOperation getStepTwoModelOperation() {
		if(this.stepTwoModelOperation == null)
		{
			stepTwoModelOperation = new StepTwoModelOperation(this,getMenuFactory().getFileMenu(this));
		}
		return stepTwoModelOperation;
	}
	
	public StepTwoModelExpandTabbedPane getStepTwoModelExpandTabbedPane() {
		if(this.stepTwoModelExpandTabbedPane == null)
		{
			stepTwoModelExpandTabbedPane = new StepTwoModelExpandTabbedPane(this);
		}
		return stepTwoModelExpandTabbedPane;
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
			stepTwoExchangeTabbedPane = new StepTwoExchangeTabbedPane(this);
		}
		return stepTwoExchangeTabbedPane;
	}

   
	public StepTwoExchangeTree getStepTwoExchangeTree() {
		if(this.stepTwoExchangeTree == null)
		{
			stepTwoExchangeTree = new StepTwoExchangeTree();
		}
		return stepTwoExchangeTree;
	}

	public StepTwoCaseExpandTabbedPane getStepTwoCaseExpandTabbedPane() {
		if(this.stepTwoCaseExpandTabbedPane == null)
		{
			stepTwoCaseExpandTabbedPane = new StepTwoCaseExpandTabbedPane(this);
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
			stepTwoEvaluateTabbedPane = new StepTwoEvaluateTabbedPane(this);
		}
		return stepTwoEvaluateTabbedPane;
	}
	 
	
	public StepTwoEvaluateTree getStepTwoEvaluateTree() {
		if(this.stepTwoEvaluateTree == null)
		{
			stepTwoEvaluateTree = new StepTwoEvaluateTree();
		}
		return stepTwoEvaluateTree;
	}

	public StepThreeLeftButton getStepThreeLeftButton() {
		if(this.stepThreeLeftButton == null)
		{
			stepThreeLeftButton = new StepThreeLeftButton(this);
		}
		return stepThreeLeftButton;
	}
    
	public JScrollPane getStepThreeJScrollPane() {
		if(this.stepThreeJScrollPane == null)
		{
			this.stepThreeJScrollPane = new JScrollPane(getStepThreeLeftButton());
		}
		
		stepThreeJScrollPane.setBorder(null);
		JScrollBar HorizontalBar = stepThreeJScrollPane.getHorizontalScrollBar();
		JScrollBar VerticalBar = stepThreeJScrollPane.getVerticalScrollBar();
		HorizontalBar.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// TODO Auto-generated method stub
				renewPanel();
			}
		});
		VerticalBar.addAdjustmentListener(new AdjustmentListener() {			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// TODO Auto-generated method stub
				renewPanel();
			}
		});
		return stepThreeJScrollPane;
	}

	public StepThreeChoosePattern getStepThreeChoosePattern() {
		if(this.stepThreeChoosePattern == null)
		{
			stepThreeChoosePattern = new StepThreeChoosePattern(this);
		}
		return stepThreeChoosePattern;
	}


	public NoTimeCaseOperation getNoTimeCaseOperation() {
		if(this.noTimeCaseOperation == null)
		{
			noTimeCaseOperation = new NoTimeCaseOperation(this);
		}
		return noTimeCaseOperation;
	}
	
	

	public StepThreeNoTimeSeqTabbedPane getStepThreeNoTimeSeqTabbedPane() {
		if(this.stepThreeNoTimeSeqTabbedPane == null)
		{
			stepThreeNoTimeSeqTabbedPane = new StepThreeNoTimeSeqTabbedPane(this);
		}
		return stepThreeNoTimeSeqTabbedPane;
	}

	public StepThreeNoTimeCaseTabbedPane getStepThreeNoTimeTabbedPane() {
		if(this.stepThreeNoTimeTabbedPane == null)
		{
			this.stepThreeNoTimeTabbedPane = new StepThreeNoTimeCaseTabbedPane(this);
		}
		return stepThreeNoTimeTabbedPane;
	}

	public StepThreeNoTimeCustomTabbedPane getStepThreeNoTimeCustomTabbedPane() {
		if(this.stepThreeNoTimeCustomTabbedPane == null)
		{
			this.stepThreeNoTimeCustomTabbedPane = new StepThreeNoTimeCustomTabbedPane(this);
		}
		return stepThreeNoTimeCustomTabbedPane;
	}

	public StepThreeTimeTabbedPane getStepThreeTimeTabbedPane() {
		if(this.stepThreeTimeTabbedPane == null){
			this.stepThreeTimeTabbedPane = new StepThreeTimeTabbedPane(this);
		}
		return stepThreeTimeTabbedPane;
	}
	
	public StepThreeTimeCustomTabbedPane getStepThreeTimeCustomTabbedPane() {
		if(this.stepThreeTimeCustomTabbedPane == null){
			this.stepThreeTimeCustomTabbedPane = new StepThreeTimeCustomTabbedPane(this);
		}
		return stepThreeTimeCustomTabbedPane;
	}

	public StepThreeChooseModelPattern getStepThreeChooseModelPattern() {
		if(this.stepThreeChooseModelPattern == null)
		{
			stepThreeChooseModelPattern = new StepThreeChooseModelPattern(this);
		}
		return stepThreeChooseModelPattern;
	}
    
	public NoTimeSeqOperation getNoTimeSeqOperation() {
		if(noTimeSeqOperation == null)
		{
			noTimeSeqOperation = new NoTimeSeqOperation(this);
		}
		return noTimeSeqOperation;
	}

	public NoTimeSeqOperation1 getNoTimeSeqOperation1() {
		if(this.noTimeSeqOperation1 == null)
		{
			noTimeSeqOperation1 = new NoTimeSeqOperation1(this);
		}
		return noTimeSeqOperation1;
	}

	public NoTimeCaseOperation1 getNoTimeCaseOperation1() {
		if(this.noTimeCaseOperation1 == null)
		{
			noTimeCaseOperation1 = new NoTimeCaseOperation1(this);
		}
		return noTimeCaseOperation1;
	}
    
	public TimeExpandOperation getTimeExpandOperation() {
		if(this.timeExpandOperation == null)
		{
			timeExpandOperation = new TimeExpandOperation(this);
		}
		return timeExpandOperation;
	}

    
	public TimeExpandTabbedPane getTimeExpandTabbedPane() {
		if(this.timeExpandTabbedPane == null)
		{
			timeExpandTabbedPane = new TimeExpandTabbedPane();
		}
		return timeExpandTabbedPane;
	}
	
	public TimeCaseOperation getTimeCaseOperation() {
		if(this.timeCaseOperation == null)
		{
			timeCaseOperation = new TimeCaseOperation(this);
		}
		return timeCaseOperation;
	}

	public TimeCaseOperation1 getTimeCaseOperation1() {
		if(this.timeCaseOperation1 == null)
		{
			timeCaseOperation1 = new TimeCaseOperation1(this);
		}
		return timeCaseOperation1;
	}
	public NoTimeMarkovFileRadion getNoTimeMarkovFileRadion() {
		if(this.noTimeMarkovFileRadion == null)
		{
			noTimeMarkovFileRadion = new NoTimeMarkovFileRadion(this);
		}
		return noTimeMarkovFileRadion;
	}
	public TimeMarkovFileRadio getTimeMarkovFileRadio() {
		if(this.timeMarkovFileRadio == null)
		{
			timeMarkovFileRadio = new TimeMarkovFileRadio(this);
		}
		return timeMarkovFileRadio;
	}

	public StepThreeBottom getStepThreeBottom() {
		if(this.stepThreeBottom == null)
		{
			stepThreeBottom = new StepThreeBottom(this);
		}
		return stepThreeBottom;
	}

	public JScrollPane getStepFourTestCase() {
		if(this.stepFourTestCase == null)
		{
			stepFourTestCase = new StepFourTestCase(this);
			stepFourJScrollPane = new JScrollPane(stepFourTestCase);
			stepFourJScrollPane.setBorder(null);
		}
		return stepFourJScrollPane;
	}
	public NameRadionPanel getNameRadionPanel() {
		if(this.nameRadionPanel == null)
		{
			nameRadionPanel = new NameRadionPanel(this);
		}
		return nameRadionPanel;
	}
	public StepFourOperation getStepFourOperation() {
		if(this.stepFourOperation == null)
		{
			stepFourOperation = new StepFourOperation(this);
		}
		return stepFourOperation;
	}
	public StepFourTabbedPane getStepFourTabbedPane() {
		if(this.stepFourTabbedPane == null)
		{
			stepFourTabbedPane = new StepFourTabbedPane(this);
		}
		return stepFourTabbedPane;
	}
	public StepFourBottom getStepFourBottom() {
		if(this.stepFourBottom == null)
		{
			stepFourBottom = new StepFourBottom(this);
		}
		return stepFourBottom;
	} 
	
	
	public Map<DefaultMutableTreeNode, UseCaseNode> getNodeMap() {
		if(this.NodeMap == null)
		{
			NodeMap = new HashMap<DefaultMutableTreeNode,UseCaseNode>();
		}
		return NodeMap;
	}
    
	

	public List<ModelPanel> getModelPanels() {
		return modelPanels;
	}

	public Map<ModelPanel, String> getModelPanelMap() {
		return modelPanelMap;
	}

	public Map<String, String> getPackageRoute() {
		if(packageRoute == null)
		{
			packageRoute = new HashMap<String,String>();
		}
		return packageRoute;
	}



	/*
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
    
    private StepTwoCenterTabbedPane stepTwoCenterTabbedPane;
  
	//private ConsolePart consolePart;
    private  JPanel consolePartPanel = new JPanel()
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
    
    private  JPanel opreationpanel=new JPanel()
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
    
    private JPanel centerTabPanel = new JPanel();
    
    private  JPanel workPanel = new JPanel()  //绘图与消息区域
    {
    	public void paint(Graphics g) {
            super.paint(g);
            java.awt.Rectangle rect = this.getBounds();
            int width = (int) rect.getWidth() - 1;
            int height = (int) rect.getHeight() - 1;
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(3f));
            g2.setColor(new Color(188,188,188));
            g2.drawRect(0, 0, width, height);
          }
    };
    private HeadTitle headTitle;
    
    private TitlePanel titlePanel;
    private JPanel StepLeftButtonPanel; 
    
    private StepOneButton stepOneButton;
    
    private StepOneOperationButton stepOneOperationButton;
    
    private JPanel stepTwoPanel;
    

	private SteponeBottomPanel steponeBottomPanel; 
    
    private JSplitPane botoomJSplitPane = new JSplitPane();
    
    private  JPanel panel = new JPanel()
    {
    	public void paint(Graphics g) {
            super.paint(g);
            java.awt.Rectangle rect = this.getBounds();
            int width = (int) rect.getWidth() - 1;
            int height = (int) rect.getHeight() - 1;
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(3f));
            g2.setColor(new Color(188,188,188));
//            g2.drawLine(0, height, width, height);
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
    
    private static JPanel ReduceOrEnlargePanel;
    
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

    private Icon deleteIcon;
    
    //第二步扩展
    private JRadionPanel jRadionPanel;
    
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
    
    private StepTwoEvaluateTree stepTwoEvaluateTree;
   //第二步验证           
    private List<String> modelNameList;
    
    private StepTwoExchangeOperation stepTwoExchangeOperation;
    
    private StepTwoExchangeTabbedPane stepTwoExchangeTabbedPane;
    
    private StepTwoExchangeTree stepTwoExchangeTree;
    
     //测试用例生成
    private StepThreeLeftButton stepThreeLeftButton;
    
    private JScrollPane stepThreeJScrollPane;
    
    private StepThreeChoosePattern stepThreeChoosePattern;
    
    private StepThreeChooseModelPattern stepThreeChooseModelPattern;
    
    private NoTimeSeqOperation noTimeSeqOperation;
    
    private NoTimeSeqOperation1 noTimeSeqOperation1;
    
    private NoTimeCaseOperation noTimeCaseOperation;
    
    private NoTimeCaseOperation1 noTimeCaseOperation1;
    
    private StepThreeNoTimeSeqTabbedPane stepThreeNoTimeSeqTabbedPane;
    
    private StepThreeNoTimeCaseTabbedPane stepThreeNoTimeTabbedPane;
    
    private StepThreeNoTimeCustomTabbedPane stepThreeNoTimeCustomTabbedPane;
    
    private TimeExpandOperation timeExpandOperation;
    
    private TimeExpandTabbedPane timeExpandTabbedPane;
    
    private TimeCaseOperation timeCaseOperation;
    
    private TimeCaseOperation1 timeCaseOperation1;
    
    private StepThreeTimeTabbedPane stepThreeTimeTabbedPane;
    
    private StepThreeTimeCustomTabbedPane stepThreeTimeCustomTabbedPane;
    
    private StepThreeBottom stepThreeBottom;
    
    private NoTimeMarkovFileRadion noTimeMarkovFileRadion;
    
    private TimeMarkovFileRadio timeMarkovFileRadio;
    
    //测试用例验证
    private JScrollPane stepFourJScrollPane;
    
    private StepFourTestCase stepFourTestCase;
    
    private NameRadionPanel nameRadionPanel;
    
    private StepFourOperation stepFourOperation;
    
    private StepFourTabbedPane stepFourTabbedPane;
    
    private StepFourBottom stepFourBottom;
    
    private Map<DefaultMutableTreeNode, UseCaseNode> NodeMap;
    
    
	/**
     * All disgram workspaces
     */
    private List<IWorkspace> UseCaseworkspaceList = new ArrayList<IWorkspace>(); //用例图    
    private List<IWorkspace> SequencespaceList=new ArrayList<IWorkspace>();//顺序图
    private List<IWorkspace> MarkovpaceList=new ArrayList<IWorkspace>();//状态图
    private List<IWorkspace> ExpandMarkovpaceList=new ArrayList<IWorkspace>();//时间自动机
    private List<ModelPanel> modelPanels = new ArrayList<ModelPanel>();
    private Map<ModelPanel, String> modelPanelMap = new HashMap<ModelPanel, String>();
    private ModelPanel ActiveModelPanel;
    
    //存放工程路径
	private Map<String, String> packageRoute;
    
    private static String BathRoute;
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
