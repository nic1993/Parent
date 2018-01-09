/*
 Violet - A program for editing UML diagrams.

 Copyright (C) 2007 Cay S. Horstmann (http://horstmann.com)
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

package com.horstmann.violet.application.menu;

import static org.hamcrest.CoreMatchers.nullValue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.dom4j.Document;
import org.dom4j.Element;

import com.horstmann.violet.application.ApplicationStopper;
import com.horstmann.violet.application.StepOneBuildModel.ModelPanel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.menu.util.UMLTransfrom.CreateActivityDiagramEAXml;
import com.horstmann.violet.application.menu.util.UMLTransfrom.CreateActivityDiagramVioletXML;
import com.horstmann.violet.application.menu.util.UMLTransfrom.CreateClassDiagramEAXML;
import com.horstmann.violet.application.menu.util.UMLTransfrom.CreateClassDiagramVioletXML;
import com.horstmann.violet.application.menu.util.UMLTransfrom.CreateStateDiagramEAXml;
import com.horstmann.violet.application.menu.util.UMLTransfrom.CreateStateDiagramVioletXML;
import com.horstmann.violet.application.menu.util.UMLTransfrom.CreateUseCaseDiagramEAXml;
import com.horstmann.violet.application.menu.util.UMLTransfrom.CreateUseCaseDiagramVioletXml;
import com.horstmann.violet.application.menu.util.UMLTransfrom.XMLUtils;
import com.horstmann.violet.application.menu.util.UMLTransfrom.readActivityXMLFormViolet;
import com.horstmann.violet.application.menu.util.UMLTransfrom.readActivityXMLFromEA;
import com.horstmann.violet.application.menu.util.UMLTransfrom.readClassXMLFormViolet;
import com.horstmann.violet.application.menu.util.UMLTransfrom.readClassXMLFromEA;
import com.horstmann.violet.application.menu.util.UMLTransfrom.readStateXMLFormViolet;
import com.horstmann.violet.application.menu.util.UMLTransfrom.readStateXMLFromEA;
import com.horstmann.violet.application.menu.util.UMLTransfrom.readUcaseXMLFormViolet;
import com.horstmann.violet.application.menu.util.UMLTransfrom.readUseCaseXMLFromEA;
import com.horstmann.violet.application.menu.xiaole.SequenceTransfrom.EADiagram;
import com.horstmann.violet.application.menu.xiaole.SequenceTransfrom.MainTransEAToViolet;
import com.horstmann.violet.framework.dialog.DialogFactory;
import com.horstmann.violet.framework.file.GraphFile;
import com.horstmann.violet.framework.file.IFile;
import com.horstmann.violet.framework.file.IGraphFile;
import com.horstmann.violet.framework.file.LocalFile;
import com.horstmann.violet.framework.file.chooser.IFileChooserService;
import com.horstmann.violet.framework.file.export.FileExportService;
import com.horstmann.violet.framework.file.naming.ExtensionFilter;
import com.horstmann.violet.framework.file.naming.FileNamingService;
import com.horstmann.violet.framework.file.persistence.IFilePersistenceService;
import com.horstmann.violet.framework.file.persistence.IFileReader;
import com.horstmann.violet.framework.file.persistence.IFileWriter;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.BeanInjector;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.InjectedBean;
import com.horstmann.violet.framework.injection.resources.ResourceBundleInjector;
import com.horstmann.violet.framework.injection.resources.annotation.ResourceBundleBean;
import com.horstmann.violet.framework.plugin.IDiagramPlugin;
import com.horstmann.violet.framework.plugin.PluginRegistry;
import com.horstmann.violet.framework.userpreferences.UserPreferencesService;
import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.usecase.UseCaseNode;
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.Workspace;
import com.thoughtworks.xstream.io.StreamException;

/**
 * Represents the file menu on the editor frame
 * 
 * @author Alexandre de Pellegrin
 * 
 */
@ResourceBundleBean(resourceReference = MenuFactory.class)
public class FileMenu extends JMenu
{

    /**
     * Default constructor
     * 
     * @param mainFrame
     */
    @ResourceBundleBean(key = "file")
    public FileMenu(MainFrame mainFrame)
    {
        ResourceBundleInjector.getInjector().inject(this);
        BeanInjector.getInjector().inject(this);
        this.mainFrame = mainFrame;
        createMenu();
        addWindowsClosingListener();
        
    }

    /**
     * @return 'new file' menu
     */
    public JMenu getFileNewMenu()
    {
        return this.fileNewMenu;
    }

    /**
     * @return recently opened file menu
     */
    public JMenu getFileRecentMenu()
    {
        return this.fileRecentMenu;
    }

    /**
     * Initialize the menu
     */
    private void createMenu()
    {
        initFileNewMenu();
        initFileOpenItem();
        initFileCloseItem();
        initFileRecentMenu();
        initFileSaveItem();
//        initFileSaveAsItem();
        initFileExportMenu();
        initFilePrintItem();
        initFileExitItem();
        initFileDsaveItem();//张建新加
        this.add(this.fileDsaveItem);//自定义保存
        this.add(this.fileNewMenu);
        this.add(this.fileOpenItem);
        this.add(this.fileCloseItem);
        this.add(this.fileRecentMenu);
        this.add(this.fileSaveItem);
        this.add(this.fileSaveAsItem);
        this.add(this.fileExportMenu);
        this.add(this.filePrintItem);
        this.add(this.fileExitItem);
    }
    
    /**
     * Add frame listener to detect closing request
     */
    private void addWindowsClosingListener()
    {
        this.mainFrame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent event)
            {
                stopper.exitProgram(mainFrame);
            }
        });
    }

    /**
     * Init exit menu entry
     */
    private void initFileExitItem()
    {
        this.fileExitItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {

                stopper.exitProgram(mainFrame);
            }
        });
        if (this.fileChooserService == null) this.fileExitItem.setEnabled(false);
    }

    /**
     * Init export submenu
     */
    private void initFileExportMenu()
    {
        initFileExportToImageItem();
        initFileExportToClipboardItem();
        initFileExportToJavaItem();
        initFileExportToPythonItem();

        this.fileExportMenu.add(this.fileExportToImageItem);
        this.fileExportMenu.add(this.fileExportToClipBoardItem);
       // this.fileExportMenu.add(this.fileExportToJavaItem);
       // this.fileExportMenu.add(this.fileExportToPythonItem);

        if (this.fileChooserService == null) this.fileExportMenu.setEnabled(false);
    }

    /**
     * Init export to python menu entry
     */
    private void initFileExportToPythonItem()
    {
        this.fileExportToPythonItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                IWorkspace workspace = (Workspace) mainFrame.getActiveWorkspace();
                if (workspace != null)
                {
                	
                }
            }
        });
    }

    /**
     * Init export to java menu entry
     */
    private void initFileExportToJavaItem()
    {
        this.fileExportToJavaItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                IWorkspace workspace = (Workspace) mainFrame.getActiveWorkspace();
                if (workspace != null)
                {
                	
                }
            }
        });
    }

    /**
     * Init export to clipboard menu entry
     */
    private void initFileExportToClipboardItem()
    {
        this.fileExportToClipBoardItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                IWorkspace workspace = (Workspace) mainFrame.getActiveWorkspace();
                if (workspace != null)
                {
                    workspace.getGraphFile().exportToClipboard();
                }
            }
        });
    }

    /**
     * Init export to image menu entry
     */
    private void initFileExportToImageItem()
    {
        this.fileExportToImageItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                IWorkspace workspace = (Workspace) mainFrame.getActiveWorkspace();
                if (workspace != null)
                {
                    try
                    {
                        ExtensionFilter[] exportFilters = fileNamingService.getImageExtensionFilters();
                        IFileWriter fileSaver = fileChooserService.chooseAndGetFileWriter(exportFilters);
                        OutputStream out = fileSaver.getOutputStream();
                        if (out != null)
                        {
                            String filename = fileSaver.getFileDefinition().getFilename();
                            for (ExtensionFilter exportFilter : exportFilters)
                            {
                                String extension = exportFilter.getExtension();
                                if (filename.toLowerCase().endsWith(extension.toLowerCase()))
                                {
                                    String format = extension.replace(".", "");
                                    workspace.getGraphFile().exportImage(out, format);
                                    break;
                                }
                            }
                        }
                    }
                    catch (Exception e1)
                    {
                        throw new RuntimeException(e1);
                    }
                }
            }
        });
    }

    /**
     * Init 'save as' menu entry
     */
//    private void initFileSaveAsItem()
//    {
//        this.fileSaveAsItem.addActionListener(new ActionListener()
//        {
//            public void actionPerformed(ActionEvent e)
//            {
//                IWorkspace workspace = (Workspace) mainFrame.getActiveWorkspace();
//                if (workspace != null)
//                {
//                    IGraphFile graphFile = workspace.getGraphFile();
//                    graphFile.saveToNewLocation();
//                    userPreferencesService.addRecentFile(graphFile);
//                }
//            }
//        });
//        if (this.fileChooserService == null) this.fileSaveAsItem.setEnabled(false);
//    }

    public void initFileSaveAsItem(IWorkspace workspace)
    {
          IGraphFile graphFile = workspace.getGraphFile();
          graphFile.saveToNewLocation();
          
    }
    /**
     * Init save menu entry
     */
    private void initFileSaveItem()
    {
        this.fileSaveItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                IWorkspace workspace = mainFrame.getActiveWorkspace();
                if (workspace != null)
                {
                    IGraphFile graphFile = workspace.getGraphFile();
                    graphFile.save();
                    userPreferencesService.addRecentFile(graphFile);
                }
            }
        });
        if (this.fileChooserService == null || (this.fileChooserService != null && this.fileChooserService.isWebStart()))
        {
            this.fileSaveItem.setEnabled(false);
        }
    }

    /**
     * Init print menu entry
     */
    private void initFilePrintItem()
    {
        this.filePrintItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                IWorkspace workspace = (Workspace) mainFrame.getActiveWorkspace();
                if (workspace != null)
                {
                    workspace.getGraphFile().exportToPrinter();
                }
            }
        });
        if (this.fileChooserService == null) this.filePrintItem.setEnabled(false);
    }

    /**
     * Init close menu entry
     */
    private void initFileCloseItem()
    {
        this.fileCloseItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                IWorkspace workspace = null;
                try
                {
                    workspace = (Workspace) mainFrame.getStepOneActiveWorkspace();
                }
                catch (RuntimeException e)
                {
                    // If no diagram is opened, close app
                    stopper.exitProgram(mainFrame);
                }
                if (workspace != null)
                {
                    IGraphFile graphFile = workspace.getGraphFile();
                    if (graphFile.isSaveRequired())
                    {
                        JOptionPane optionPane = new JOptionPane();
                        optionPane.setMessage(dialogCloseMessage);
                        optionPane.setOptionType(JOptionPane.YES_NO_CANCEL_OPTION);
                        optionPane.setIcon(dialogCloseIcon);
                        dialogFactory.showDialog(optionPane, dialogCloseTitle, true);

                        int result = JOptionPane.CANCEL_OPTION;
                        if (!JOptionPane.UNINITIALIZED_VALUE.equals(optionPane.getValue()))
                        {
                            result = ((Integer) optionPane.getValue()).intValue();
                        }

                        if (result == JOptionPane.YES_OPTION)
                        {
                            String filename = graphFile.getFilename();
                            if (filename == null)
                            {
                                graphFile.saveToNewLocation();
                                userPreferencesService.addRecentFile(graphFile);
                            }
                            if (filename != null)
                            {
                                graphFile.save();
                            }
                            if (!graphFile.isSaveRequired())
                            {
                                mainFrame.removeDiagramPanel(workspace);
                                
                                //新加
                                
                                
                                userPreferencesService.removeOpenedFile(graphFile);
                            }
                        }
                        if (result == JOptionPane.NO_OPTION)
                        {
                            mainFrame.removeDiagramPanel(workspace);
                            userPreferencesService.removeOpenedFile(graphFile);
                        }
                    }
                    if (!graphFile.isSaveRequired())
                    {
                        mainFrame.removeDiagramPanel(workspace);
                        userPreferencesService.removeOpenedFile(graphFile);                       
                    }
                }
            }
        });
    }

    private List<IFile> openEAXML(IFile selectedFile,IGraphFile graphFile,String url)
    {
    	List<IFile> EAFiles = new ArrayList<IFile>();
   	    String path = null;
   	    File ff=null;//用于生成在d盘中文件
   	    String name="";
		List<Object> information = judgeEAXML(url);
		List<EADiagram> EADiagrams = (List<EADiagram>) information.get(1);
		for(EADiagram eaDiagram : EADiagrams)
		{
		    if("Use Case".equals(eaDiagram.getType())){
			      try {
			 			path=mainFrame.getBathRoute()+"/UseCaseDiagram/";
			 			String aimPath=path+"EAXML";
			 			
			 	 		readUseCaseXMLFromEA ru =new readUseCaseXMLFromEA(url,selectedFile,eaDiagram);
			 	 		CreateUseCaseDiagramVioletXml cu =new CreateUseCaseDiagramVioletXml();
			 	 		name=selectedFile.getFilename().replaceAll(".xml", ".ucase.violet.xml");		
						cu.create(ru, path+"Violet/"+name);
						File f =new File(path+"Violet/"+name);
						selectedFile =new LocalFile(f);
						EAFiles.add(selectedFile);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 	}
			 else if("Sequence".equals(eaDiagram.getType())){
			 		path=mainFrame.getBathRoute()+"/SequenceDiagram/";
			 		String aimPath=path+"EAXML";
			 		name=eaDiagram.getName()+".seq.violet.xml";
			 		directory=selectedFile.getDirectory();
			 		fileName=selectedFile.getFilename();
			 		try {
						MainTransEAToViolet.TransEAToViolet(url,path+"Violet/"+name,name,eaDiagram);
						File f =new File(path+"Violet/"+name);
				 		deleteFileFirstLine(f);
						selectedFile =new LocalFile(f);
						EAFiles.add(selectedFile);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
			 	}	
		}
		return  EAFiles;
    }
    public void openFile(File[] files) throws IOException
    {
    	for (File file : files) {
    		IFile selectedFile = new LocalFile(file);
            String url = selectedFile.getDirectory() + "\\" + selectedFile.getFilename();
            //判断类型
            boolean flag = isVioletXML(url);//是EA格式的文件
            //判断是否可以打开
            boolean isNeedOpen = NeedOpen(selectedFile,url,flag);
            
            if(isNeedOpen == false)
            {
         	   JOptionPane.showMessageDialog(null, "该模型与已经存在的模型冲突!","标题",JOptionPane.WARNING_MESSAGE); 
            }
            else {
         	   //如果是平台保存的XML文件
                IGraphFile graphFile = null;
//              //增加转换的方法11
                if(flag == false)
                {
              	  List<IFile> eAFiles = openEAXML(selectedFile, graphFile, url);
              	  //保存selectedFile默认位置
//              	  if(eAFiles.get(0).getFilename().contains(".seq.violet.xml"))
//              	  {
//              		  String seqName = eAFiles.get(0).getFilename().replace(".seq.violet.xml", "");
//              		  XMLUtils.AutoSave(url, mainFrame.getBathRoute()+"/SequenceDiagram/EAXML", selectedFile.getFilename());
//              	  }
              	  for(IFile eafile : eAFiles)
              	  {
                      String filename=eafile.getFilename(); //导入名字
                      if(filename.contains(".seq.violet.xml")){
                     	 String name = filename.replace(".seq.violet.xml", "");
                     	 graphFile = new GraphFile(eafile);
                          
                          //显示文件图形																																																																															
                          IWorkspace workspace = new Workspace(graphFile);
                          workspace.setName(name);
                          mainFrame.addTabbedPane(workspace);
//                   	   if(mainFrame.getListSequenceTabPanel().size() != 0){
//                      		for(SequenceTabPanel sequenceTabPanel:mainFrame.getListSequenceTabPanel())
//                      		{
//                      			sequenceTabPanel.getPanel().setBackground(new Color(246,246,246));
//                      			sequenceTabPanel.getDeletelabel().setIcon(null);
//                      		}
//                      		mainFrame.getListSequenceTabPanel().get(mainFrame.getListSequenceTabPanel().size()-1).getPanel().setBackground(Color.white);
//                      		mainFrame.getListSequenceTabPanel().get(mainFrame.getListSequenceTabPanel().size()-1).getDeletelabel().setIcon(new ImageIcon("resources\\icons\\22x22\\beforeClose.png"));
//                      		}
                   	    JTree sequencetree = mainFrame.getActiveModelPanel().getSequenceTreePanel().getSequencetree();
                      	DefaultTreeModel sequencetreemodel = mainFrame.getActiveModelPanel().getSequenceTreePanel().getSequencetreemodel();
                      	DefaultMutableTreeNode sequencetreerootnode = mainFrame.getActiveModelPanel().getSequenceTreePanel().getSequencetreerootnode();
                      	
   						DefaultMutableTreeNode node=new DefaultMutableTreeNode(name);
   						
   						sequencetreemodel.insertNodeInto(node, sequencetreerootnode, sequencetreerootnode.getChildCount());
   						TreePath path=new TreePath(sequencetreerootnode.getPath());
   						if(!sequencetree.isVisible(path)){
   							sequencetree.makeVisible(path);
   						}
   						sequencetree.getSelectionModel().setSelectionPath(new TreePath(node.getPath()));
   						Map<DefaultMutableTreeNode, IWorkspace> hashMap = mainFrame.getActiveModelPanel().getSequenceTreePanel().getHashMap();
   						int length = mainFrame.getActiveModelPanel().getSequencespaceList().size() - 1;
   						hashMap.put(node, mainFrame.getActiveModelPanel().getSequencespaceList().get(length));

   						mainFrame.getStepOperationButton().getPromptLabel().removeAll();
   						mainFrame.getStepOperationButton().getPromptLabel().setText("顺序图是将交互关系表示为一个二维图。纵向是时间轴，时间沿竖线向下延伸。横向轴代表了在协作中各独立对象的类元角色。");
   						mainFrame.getOpreationPart().revalidate();  
   						mainFrame.getOutputinformation().geTextArea().append("打开顺序图: "+name+"\n");
                      }
                      if(filename.contains(".ucase.violet.xml")){
                        String name = filename.replace(".ucase.violet.xml", "");
                        graphFile = new GraphFile(eafile);
                        //显示文件图形																																																																															
                        IWorkspace workspace = new Workspace(graphFile);
                        mainFrame.addTabbedPane(workspace,name);

                   	    JTree usecasetree = mainFrame.getActiveModelPanel().getUsecaseTreePanel().getUsecasetree();
                      	DefaultTreeModel usecasetreemodel = mainFrame.getActiveModelPanel().getUsecaseTreePanel().getUsecasetreemodel();
                      	DefaultMutableTreeNode usecasetreerootnode = mainFrame.getActiveModelPanel().getUsecaseTreePanel().getUsecasetreerootnode();                        
   						DefaultMutableTreeNode node=new DefaultMutableTreeNode(name);
   						usecasetreemodel.insertNodeInto(node, usecasetreerootnode, usecasetreerootnode.getChildCount());
   						Collection<INode> nodes = graphFile.getGraph().getAllNodes();
   						int i=1;
   						for(INode ucase : nodes)
   						{
   							if(ucase.getClass().getSimpleName().equals("UseCaseNode"))
   							{   
   								UseCaseNode useCaseNode = (UseCaseNode)ucase;
   								String ucaseName = useCaseNode.getName().getText();
   								DefaultMutableTreeNode ucaseNode=new DefaultMutableTreeNode(ucaseName); //用例图节点
   								mainFrame.getActiveModelPanel().getUsecaseTreePanel().getNodeMap().put(ucaseNode, (UseCaseNode) ucase);
   								usecasetreemodel.insertNodeInto(ucaseNode, node, node.getChildCount());
   								List<String> sequenceNames = useCaseNode.getSceneConstraint().getSequenceName();
   								if(sequenceNames.size() != 0)
   								{
   									for(String sename : sequenceNames)
       								{
       									DefaultMutableTreeNode seNode = new DefaultMutableTreeNode(sename); //用例图节点
       									usecasetreemodel.insertNodeInto(seNode, ucaseNode, ucaseNode.getChildCount());
       								}
   								}
   							}
   						}
   						TreePath path=new TreePath(usecasetreerootnode.getPath());
   						if(!usecasetree.isVisible(path)){
   							usecasetree.makeVisible(path);
   						}
   						usecasetree.getSelectionModel().setSelectionPath(new TreePath(node.getPath()));
//   						Map<DefaultMutableTreeNode, IWorkspace> hashMap = mainFrame.getActiveModelPanel().getUsecaseTreePanel().getHashMap();
   						
   						int length = mainFrame.getActiveModelPanel().getUseCaseworkspaceList().size()-1;
   						
   						mainFrame.getActiveModelPanel().getUsecaseTreePanel().getHashMap().put(node, mainFrame.getActiveModelPanel().getUseCaseworkspaceList().get(length));
   						DefaultMutableTreeNode rootnode = mainFrame.getKey(mainFrame.getActiveModelPanel().getUsecaseTreePanel().getHashMap(), mainFrame.getActiveModelPanel().getUseCaseworkspaceList().get(length));
						
   						
   						//切换界面
   						mainFrame.getStepOperationButton().getPromptLabel().setText("用例图是指由参与者（Actor）、用例（Use Case）以及它们之间的关系构成的用于描述系统功能的视图。");
   						mainFrame.getOpreationPart().validate();

   						mainFrame.getOutputinformation().geTextArea().append("打开用例图: "+name+"\n");
                      }
                      
//                      userPreferencesService.addOpenedFile(graphFile);
//                      userPreferencesService.addRecentFile(graphFile);
              	  }
              	  
                }
                else {
             	 String filename=selectedFile.getFilename(); //导入名字
             	 String path = null;
             	 graphFile = new GraphFile(selectedFile);
             	 IWorkspace workspace = new Workspace(graphFile);
           		 
                 if(filename.contains(".ucase.violet.xml")){
                 	String name = filename.replace(".ucase.violet.xml", "");
               		path=mainFrame.getBathRoute()+"/UseCaseDiagram/VioletXML/";
//               		graphFile.AutoSave(selectedFile, path);
               		workspace.setName(name);
               		mainFrame.addTabbedPane(workspace);

               	    JTree usecasetree = mainFrame.getActiveModelPanel().getUsecaseTreePanel().getUsecasetree();
                     DefaultTreeModel usecasetreemodel = mainFrame.getActiveModelPanel().getUsecaseTreePanel().getUsecasetreemodel();
                  	 DefaultMutableTreeNode usecasetreerootnode = mainFrame.getActiveModelPanel().getUsecaseTreePanel().getUsecasetreerootnode();                        
						DefaultMutableTreeNode node=new DefaultMutableTreeNode(name);
						usecasetreemodel.insertNodeInto(node, usecasetreerootnode, usecasetreerootnode.getChildCount());
						Collection<INode> nodes = graphFile.getGraph().getAllNodes();
						int i=1;
						for(INode ucase : nodes)
						{
							if(ucase.getClass().getSimpleName().equals("UseCaseNode"))
							{   
								UseCaseNode useCaseNode = (UseCaseNode)ucase;
								String ucaseName = useCaseNode.getName().getText();
								DefaultMutableTreeNode ucaseNode=new DefaultMutableTreeNode(ucaseName); //用例图节点
								mainFrame.getActiveModelPanel().getUsecaseTreePanel().getNodeMap().put(ucaseNode, (UseCaseNode) ucase);
								usecasetreemodel.insertNodeInto(ucaseNode, node, node.getChildCount());
								List<String> sequenceNames = useCaseNode.getSceneConstraint().getSequenceName();
								if(sequenceNames.size() != 0)
								{
									for(String sename : sequenceNames)
   								{
   									DefaultMutableTreeNode seNode = new DefaultMutableTreeNode(sename); //用例图节点
   									usecasetreemodel.insertNodeInto(seNode, ucaseNode, ucaseNode.getChildCount());
   								}
								}
							}
						}
						TreePath path1=new TreePath(usecasetreerootnode.getPath());
						if(!usecasetree.isVisible(path1)){
							usecasetree.makeVisible(path1);
						}
						usecasetree.getSelectionModel().setSelectionPath(new TreePath(node.getPath()));
						Map<DefaultMutableTreeNode, IWorkspace> hashMap = mainFrame.getActiveModelPanel().getUsecaseTreePanel().getHashMap();
						int length = mainFrame.getActiveModelPanel().getUseCaseworkspaceList().size() - 1;
						hashMap.put(node, mainFrame.getActiveModelPanel().getUseCaseworkspaceList().get(length));

						//切换界面
						mainFrame.getStepOperationButton().getPromptLabel().setText("用例图是指由参与者（Actor）、用例（Use Case）以及它们之间的关系构成的用于描述系统功能的视图。");
						mainFrame.getOpreationPart().validate();

						mainFrame.getOutputinformation().geTextArea().append("打开用例图: "+name+"\n");
						

               		
               	}else if(filename.contains(".seq.violet.xml")){
               		String name = filename.replace(".seq.violet.xml", "");
               		path = mainFrame.getBathRoute() + "/SequenceDiagram/VioletXML/";
               		graphFile.AutoSave(selectedFile, path);
               		workspace.setName(name);
                     mainFrame.addTabbedPane(workspace);

              	    JTree sequencetree = mainFrame.getActiveModelPanel().getSequenceTreePanel().getSequencetree();
                 	DefaultTreeModel sequencetreemodel = mainFrame.getActiveModelPanel().getSequenceTreePanel().getSequencetreemodel();
                 	DefaultMutableTreeNode sequencetreerootnode = mainFrame.getActiveModelPanel().getSequenceTreePanel().getSequencetreerootnode();
                 	
						DefaultMutableTreeNode node=new DefaultMutableTreeNode(name);
						
						sequencetreemodel.insertNodeInto(node, sequencetreerootnode, sequencetreerootnode.getChildCount());
						TreePath path1=new TreePath(sequencetreerootnode.getPath());
						if(!sequencetree.isVisible(path1)){
							sequencetree.makeVisible(path1);
						}
						sequencetree.getSelectionModel().setSelectionPath(new TreePath(node.getPath()));
						Map<DefaultMutableTreeNode, IWorkspace> hashMap = mainFrame.getActiveModelPanel().getSequenceTreePanel().getHashMap();
						int length = mainFrame.getActiveModelPanel().getSequencespaceList().size() - 1;
						hashMap.put(node, mainFrame.getActiveModelPanel().getSequencespaceList().get(length));
						

						mainFrame.getStepOperationButton().getPromptLabel().removeAll();
						mainFrame.getStepOperationButton().getPromptLabel().setText("顺序图是将交互关系表示为一个二维图。纵向是时间轴，时间沿竖线向下延伸。横向轴代表了在协作中各独立对象的类元角色。");
		
						mainFrame.getOpreationPart().revalidate();  
						mainFrame.getOutputinformation().geTextArea().append("打开顺序图: "+name+"\n");
               }
              }
            }
    	}
    }
    /*
     * 张建
     */
    private void deleteFileFirstLine(File f){
    	try {
			BufferedReader br =new BufferedReader(new FileReader(f));
			StringBuffer sb =new StringBuffer(4096);
//			int line=0;
//			int num=0;
			String temp =null;
			while((temp =br.readLine())!=null){
//				line++;
//				if(line==num) continue;
//				sb.append(temp).append( "\r\n ");
				if(temp.toString().contains("encoding")&&temp.toString().contains("version"))
					continue;
					sb.append(temp).append( "\r\n ");
			}
			br.close();
			BufferedWriter bw =new BufferedWriter(new FileWriter(f));
			bw.write(sb.toString());
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /*
     * 张建   处理ea文件 生成EASequence.seq.violet类似的文件名
     */
    private StringBuffer dealEAFileName(IFile selectedFile) {
		String[] ss =selectedFile.getFilename().split("\\.");
		StringBuffer name=new StringBuffer();
		name.append("EA"+ss[0]);
		for (int i = 1; i < ss.length-2; i++) {
			name.append("."+ss[i]);
		}
		name.append("."+ss[ss.length-1]);
		return name;
	}
    
    /**
     * Init open menu entry。蔡新加
     */
    private boolean isVioletXML(String url)
	{
		Document document =XMLUtils.load(url);
		Element root=document.getRootElement();
		if(root.getName().equals("XMI")){
			return false;
		}
		else {
			return true;
		}
	}
    private String judgeVioletXML(String url)
    {
    	Document document =XMLUtils.load(url);
		Element root=document.getRootElement();
		if(root.getName().contains("UseCaseDiagramGraph"))
		{
			return "ucase";
		}
		else {
			return "seq";
		}
    }
	private List<Object> judgeEAXML(String url)
	{
		List<Object> list = new ArrayList<Object>();
		Document document =XMLUtils.load(url);
		Element root=document.getRootElement();
		Element extension= root.element("Extension");
		Element diagrams = extension.element("diagrams");
		List<Element> diagramList = diagrams.elements("diagram");
		String type = diagramList.get(0).element("properties").attributeValue("type");
		list.add(type);
			List<EADiagram> sequenceDiagrams = new ArrayList<EADiagram>();
			for(Element element : diagramList)
			{
				EADiagram sequenceDiagram = new EADiagram();
				sequenceDiagram.setName(element.element("properties").attributeValue("name"));
				sequenceDiagram.setID(element.element("model").attributeValue("package"));
				sequenceDiagram.setDiagramID(element.attributeValue("id"));
				sequenceDiagram.setType(element.element("properties").attributeValue("type"));
				if(element.element("elements") != null)
				{
					Element diagramElements = element.element("elements");
				    List<Element> elements = diagramElements.elements("element");
					for(Element elementID : elements)
					{
						sequenceDiagram.getElementid().add(elementID.attributeValue("subject"));
					}
					sequenceDiagrams.add(sequenceDiagram);
				}
		    }
			list.add(sequenceDiagrams);
		return list;
	}
	
	/*
	 * 平台用例图导入时将所有顺序图导入
	 */
	public List<IFile> getAllSequence()
	{
		List<IFile> sequences = new ArrayList<IFile>();
		String sequencePath = mainFrame.getBathRoute() + "/SequenceDiagram/VioletXML";
		File[] files = new File(sequencePath).listFiles();
		int index = mainFrame.getActiveModelPanel().getUseCaseworkspaceList().size() - 1;
		IWorkspace workspace = mainFrame.getActiveModelPanel().getUseCaseworkspaceList().get(index);
		Collection<INode> allNode = workspace.getGraphFile().getGraph().getAllNodes();
		for (INode ucase : allNode) {
			if (ucase.getClass().getSimpleName().equals("UseCaseNode")) {
				UseCaseNode useCaseNode = (UseCaseNode) ucase;
				String ucaseName = useCaseNode.getName().getText();
				List<String> sequenceNames = useCaseNode.getSceneConstraint().getSequenceName();
				if (sequenceNames.size() != 0) {
					for (String sename : sequenceNames) {
						for (File file : files) {
							if (file.getName().replace(".seq.violet.xml", "").equals(sename)) {
								IFile exchangeFile;
								try {
									exchangeFile = new LocalFile(file);
									sequences.add(exchangeFile);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
		return sequences;
	}
	/*
	 * 展示顺序图
	 */
	private void ShowSeq(IFile selectedFile)
	{
		try {
		String filename = selectedFile.getFilename();
		String name = filename.replace(".seq.violet.xml", "");
  		String path=mainFrame.getBathRoute()+"/SequenceDiagram/";
  		
  		IGraphFile graphFile;
		graphFile = new GraphFile(selectedFile);
   	    IWorkspace workspace = new Workspace(graphFile);
   	    workspace.setName(name);
  		graphFile.AutoSave(selectedFile, path+"VioletXML/");
  		mainFrame.addTabbedPane(workspace);
//  		String name = filename.replace(".seq.violet.xml", "");
//	   if(mainFrame.getListSequenceTabPanel().size() != 0){
//  		for(SequenceTabPanel sequenceTabPanel:mainFrame.getListSequenceTabPanel())
//  		{
//  			sequenceTabPanel.getPanel().setBackground(new Color(246,246,246));
//  			sequenceTabPanel.getDeletelabel().setIcon(null);
//  		}
//  		mainFrame.getListSequenceTabPanel().get(mainFrame.getListSequenceTabPanel().size()-1).getPanel().setBackground(Color.white);
//  		mainFrame.getListSequenceTabPanel().get(mainFrame.getListSequenceTabPanel().size()-1).getDeletelabel().setIcon(new ImageIcon("resources\\icons\\22x22\\beforeClose.png"));
//  		}
	    JTree sequencetree = mainFrame.getActiveModelPanel().getSequenceTreePanel().getSequencetree();
  	    DefaultTreeModel sequencetreemodel = mainFrame.getActiveModelPanel().getSequenceTreePanel().getSequencetreemodel();
  	    DefaultMutableTreeNode sequencetreerootnode = mainFrame.getActiveModelPanel().getSequenceTreePanel().getSequencetreerootnode();
  	    
		DefaultMutableTreeNode node=new DefaultMutableTreeNode(name);
		
		sequencetreemodel.insertNodeInto(node, sequencetreerootnode, sequencetreerootnode.getChildCount());
		TreePath path1=new TreePath(sequencetreerootnode.getPath());
		if(!sequencetree.isVisible(path1)){
			sequencetree.makeVisible(path1);
		}
		sequencetree.getSelectionModel().setSelectionPath(new TreePath(node.getPath()));
		Map<DefaultMutableTreeNode, IWorkspace> hashMap = mainFrame.getActiveModelPanel().getSequenceTreePanel().getHashMap();
		int length = mainFrame.getSequenceWorkspaceList().size() - 1;
		hashMap.put(node, mainFrame.getSequenceWorkspaceList().get(length));
			
		//切换界面

		mainFrame.getCenterTabPanel().removeAll();

		mainFrame.getStepOperationButton().getPromptLabel().removeAll();
		mainFrame.getStepOperationButton().getPromptLabel().setText("顺序图是将交互关系表示为一个二维图。纵向是时间轴，时间沿竖线向下延伸。横向轴代表了在协作中各独立对象的类元角色。");

		mainFrame.getOpreationPart().revalidate();  
       
		mainFrame.getOutputinformation().geTextArea().append("打开顺序图: "+name+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
   private boolean NeedOpen(IFile selectedFile,String url,boolean flag)
   {
		String filename = selectedFile.getFilename();
		DefaultMutableTreeNode ucaseTree = mainFrame.getActiveModelPanel().getUsecaseTreePanel().getUsecasetreerootnode();
		DefaultMutableTreeNode seqTree = mainFrame.getActiveModelPanel().getSequenceTreePanel().getSequencetreerootnode();
		//EA导入的XML 判断是否与已经存在平台XML的冲突
		if(flag == false)
		{
			List<Object> information = judgeEAXML(url);
			List<EADiagram> EADiagrams = (List<EADiagram>) information.get(1);
			if(EADiagrams.get(0).getType().equals("Use Case"))
			{
				//与平台的XML是否冲突
				String ucaseName = EADiagrams.get(0).getName().replace(".xml", "");
				int count = ucaseTree.getChildCount();	
				for(int i = 0;i < count;i++)
				{
					if(ucaseTree.getChildAt(i).toString().equals(ucaseName))
					{
						return false;
					}
				}
			}
			else {
				int count = seqTree.getChildCount();
				for (EADiagram eaDiagram : EADiagrams) {
					
					for(int i = 0;i < count;i++)
					{
						String name = eaDiagram.getName();
						if(seqTree.getChildAt(i).toString().equals(eaDiagram.getName().replace(".xml", "")))
						{
							return false; 
						} 
					}
				}
			}
		}
		else {
			if(filename.contains(".ucase.violet.xml"))
			{
				String ucaseName = filename.replace(".ucase.violet.xml", "");
				int count = ucaseTree.getChildCount();	
				for(int i = 0;i < count;i++)
				{
					if(ucaseTree.getChildAt(i).toString().equals(ucaseName))
					{
						return false;
					}
				}
			}
			else{
				int count = seqTree.getChildCount();
				String seqName = filename.replace(".seq.violet.xml", "");
				for(int i = 0;i < count;i++)
				{
					if(seqTree.getChildAt(i).toString().equals(seqName))
					{
						return false; 
					} 
				}
			}
		}
		return true;
   }
    /**
     * Init open menu entry。张建已改
     */
   public void initFileOpenItem()
    {
	   this.fileOpenItem.addActionListener(new ActionListener()
       {
           public void actionPerformed(ActionEvent event)
           {	
               IFile selectedFile = null;
               try
               {
                   ExtensionFilter[] filters = fileNamingService.getFileFilters();
                   IFileReader fileOpener = fileChooserService.chooseAndGetFileReader(filters);//弹出文件框
                   if (fileOpener == null)
                   {
                       // Action cancelled by user
                       return;
                   }
                   selectedFile = fileOpener.getFileDefinition();//返回一个绝对路径的文件   
                   String url = selectedFile.getDirectory() + "\\" + selectedFile.getFilename();
                   //判断类型
                   boolean flag = isVioletXML(url);//是EA格式的文件
                   //判断是否可以打开
                   boolean isNeedOpen = NeedOpen(selectedFile,url,flag);
                   
                   if(isNeedOpen == false)
                   {
                	   JOptionPane.showMessageDialog(null, "该模型与已经存在的模型冲突!","标题",JOptionPane.WARNING_MESSAGE); 
                   }
                   else {
                	   //如果是平台保存的XML文件
                       IGraphFile graphFile = null;
//                     //增加转换的方法11
                       if(flag == false)
                       {
                     	  List<IFile> eAFiles = openEAXML(selectedFile, graphFile, url);
                     	  //保存selectedFile默认位置
                     	  if(eAFiles.get(0).getFilename().contains(".seq.violet.xml"))
                     	  {
                     		  XMLUtils.AutoSave(url, mainFrame.getActiveModelPanel().getTemporarySeqFile(), selectedFile.getFilename());
                     	  }
                     	  else{
                     		 XMLUtils.AutoSave(url, mainFrame.getActiveModelPanel().getTemporaryUcaseFile(),selectedFile.getFilename());
                     	  }
                     	  for(IFile file : eAFiles)
                     	  {
                             String filename=file.getFilename(); //导入名字
                             if(filename.contains(".seq.violet.xml")){
                            	 String name = filename.replace(".seq.violet.xml", "");
                            	 graphFile = new GraphFile(file);
                                 
                                 //显示文件图形																																																																															
                                 IWorkspace workspace = new Workspace(graphFile);
                                 workspace.setName(name);
                                 mainFrame.addTabbedPane(workspace);

                          	    JTree sequencetree = mainFrame.getActiveModelPanel().getSequenceTreePanel().getSequencetree();
                             	DefaultTreeModel sequencetreemodel = mainFrame.getActiveModelPanel().getSequenceTreePanel().getSequencetreemodel();
                             	DefaultMutableTreeNode sequencetreerootnode = mainFrame.getActiveModelPanel().getSequenceTreePanel().getSequencetreerootnode();
                             	
          						DefaultMutableTreeNode node=new DefaultMutableTreeNode(name);
          						
          						sequencetreemodel.insertNodeInto(node, sequencetreerootnode, sequencetreerootnode.getChildCount());
          						TreePath path=new TreePath(sequencetreerootnode.getPath());
          						if(!sequencetree.isVisible(path)){
          							sequencetree.makeVisible(path);
          						}
          						sequencetree.getSelectionModel().setSelectionPath(new TreePath(node.getPath()));
          						Map<DefaultMutableTreeNode, IWorkspace> hashMap = mainFrame.getActiveModelPanel().getSequenceTreePanel().getHashMap();
          						int length = mainFrame.getActiveModelPanel().getSequencespaceList().size() - 1;
          						hashMap.put(node, mainFrame.getActiveModelPanel().getSequencespaceList().get(length));

          						mainFrame.getStepOperationButton().getPromptLabel().removeAll();
          						mainFrame.getStepOperationButton().getPromptLabel().setText("顺序图是将交互关系表示为一个二维图。纵向是时间轴，时间沿竖线向下延伸。横向轴代表了在协作中各独立对象的类元角色。");
          						mainFrame.getOpreationPart().revalidate();  
          						mainFrame.getOutputinformation().geTextArea().append("打开顺序图: "+name+"\n");
                             }
                             if(filename.contains(".ucase.violet.xml")){
                               String name = filename.replace(".ucase.violet.xml", "");
                               graphFile = new GraphFile(file);
                               
                               //显示文件图形																																																																															
                               IWorkspace workspace = new Workspace(graphFile);
                               mainFrame.addTabbedPane(workspace,name);

                          	    JTree usecasetree = mainFrame.getActiveModelPanel().getUsecaseTreePanel().getUsecasetree();
                             	DefaultTreeModel usecasetreemodel = mainFrame.getActiveModelPanel().getUsecaseTreePanel().getUsecasetreemodel();
                             	DefaultMutableTreeNode usecasetreerootnode = mainFrame.getActiveModelPanel().getUsecaseTreePanel().getUsecasetreerootnode();                        
          						DefaultMutableTreeNode node=new DefaultMutableTreeNode(name);
          						usecasetreemodel.insertNodeInto(node, usecasetreerootnode, usecasetreerootnode.getChildCount());
          						Collection<INode> nodes = graphFile.getGraph().getAllNodes();
          						int i=1;
          						for(INode ucase : nodes)
          						{
          							if(ucase.getClass().getSimpleName().equals("UseCaseNode"))
          							{   
          								UseCaseNode useCaseNode = (UseCaseNode)ucase;
          								String ucaseName = useCaseNode.getName().getText();
          								DefaultMutableTreeNode ucaseNode=new DefaultMutableTreeNode(ucaseName); //用例图节点
          								mainFrame.getActiveModelPanel().getUsecaseTreePanel().getNodeMap().put(ucaseNode, (UseCaseNode) ucase);
          								usecasetreemodel.insertNodeInto(ucaseNode, node, node.getChildCount());
          								List<String> sequenceNames = useCaseNode.getSceneConstraint().getSequenceName();
          								if(sequenceNames.size() != 0)
          								{
          									for(String sename : sequenceNames)
              								{
              									DefaultMutableTreeNode seNode = new DefaultMutableTreeNode(sename); //用例图节点
              									usecasetreemodel.insertNodeInto(seNode, ucaseNode, ucaseNode.getChildCount());
              								}
          								}
          							}
          						}
          						TreePath path=new TreePath(usecasetreerootnode.getPath());
          						if(!usecasetree.isVisible(path)){
          							usecasetree.makeVisible(path);
          						}
          						usecasetree.getSelectionModel().setSelectionPath(new TreePath(node.getPath()));
          						Map<DefaultMutableTreeNode, IWorkspace> hashMap = mainFrame.getActiveModelPanel().getUsecaseTreePanel().getHashMap();
          						int length = mainFrame.getActiveModelPanel().getUseCaseworkspaceList().size()-1;
          						hashMap.put(node, mainFrame.getActiveModelPanel().getUseCaseworkspaceList().get(length));
                                 
          					
          						DefaultMutableTreeNode rootnode = mainFrame.getKey(hashMap, mainFrame.getActiveModelPanel().getUseCaseworkspaceList().get(length)); 
       						
          						//切换界面
          						mainFrame.getStepOperationButton().getPromptLabel().setText("用例图是指由参与者（Actor）、用例（Use Case）以及它们之间的关系构成的用于描述系统功能的视图。");
          						mainFrame.getOpreationPart().validate();

          						mainFrame.getOutputinformation().geTextArea().append("打开用例图: "+name+"\n");
                             }
                             
                             userPreferencesService.addOpenedFile(graphFile);
                             userPreferencesService.addRecentFile(graphFile);
                     	  }
                     	  
                       }
                       else {
                    	 String filename=selectedFile.getFilename(); //导入名字
                    	 String path = null;
                    	 graphFile = new GraphFile(selectedFile);
                    	 IWorkspace workspace = new Workspace(graphFile);
                  		 
                        if(filename.contains(".ucase.violet.xml")){
                        	String name = filename.replace(".ucase.violet.xml", "");
                      		graphFile.AutoSave(selectedFile, mainFrame.getActiveModelPanel().getTemporaryUcaseFile());
                      		workspace.setName(name);
                      		mainFrame.addTabbedPane(workspace);
//                      		readUcaseXMLFormViolet ru =new readUcaseXMLFormViolet(url);
//                      		CreateUseCaseDiagramEAXml cu =new CreateUseCaseDiagramEAXml();
//                      		ff =new File(path+"EA");
//                      		if(!ff.exists()){
//                      			ff.mkdirs();
//                      		}
//                      		StringBuffer name = dealEAFileName(selectedFile);
//                      		cu.create(ru, path+"EA/"+name);
//                      		if(mainFrame.getListUsecaseTabPanel().size() != 0){
//                         		for(UsecaseTabPanel usecaseTabPanel:mainFrame.getListUsecaseTabPanel())
//                         		{
//                         			usecaseTabPanel.getPanel().setBackground(new Color(246,246,246));
//                         			usecaseTabPanel.getDeletelabel().setIcon(null);
//                         		}
//                         		mainFrame.getListUsecaseTabPanel().get(mainFrame.getListUsecaseTabPanel().size()-1).getPanel().setBackground(Color.white);
//                         		mainFrame.getListUsecaseTabPanel().get(mainFrame.getListUsecaseTabPanel().size()-1).getDeletelabel().setIcon(new ImageIcon("resources\\icons\\22x22\\beforeClose.png"));
//                         		}
                      	   JTree usecasetree = mainFrame.getActiveModelPanel().getUsecaseTreePanel().getUsecasetree();
                            DefaultTreeModel usecasetreemodel = mainFrame.getActiveModelPanel().getUsecaseTreePanel().getUsecasetreemodel();
                         	DefaultMutableTreeNode usecasetreerootnode = mainFrame.getActiveModelPanel().getUsecaseTreePanel().getUsecasetreerootnode();                        
      						DefaultMutableTreeNode node=new DefaultMutableTreeNode(name);
      						usecasetreemodel.insertNodeInto(node, usecasetreerootnode, usecasetreerootnode.getChildCount());
      						Collection<INode> nodes = graphFile.getGraph().getAllNodes();
      						int i=1;
      						for(INode ucase : nodes)
      						{
      							if(ucase.getClass().getSimpleName().equals("UseCaseNode"))
      							{   
      								UseCaseNode useCaseNode = (UseCaseNode)ucase;
      								String ucaseName = useCaseNode.getName().getText();
      								DefaultMutableTreeNode ucaseNode=new DefaultMutableTreeNode(ucaseName); //用例图节点
      								mainFrame.getActiveModelPanel().getUsecaseTreePanel().getNodeMap().put(ucaseNode, (UseCaseNode) ucase);
      								usecasetreemodel.insertNodeInto(ucaseNode, node, node.getChildCount());
      								List<String> sequenceNames = useCaseNode.getSceneConstraint().getSequenceName();
      								if(sequenceNames.size() != 0)
      								{
      									for(String sename : sequenceNames)
          								{
          									DefaultMutableTreeNode seNode = new DefaultMutableTreeNode(sename); //用例图节点
          									usecasetreemodel.insertNodeInto(seNode, ucaseNode, ucaseNode.getChildCount());
          								}
      								}
      							}
      						}
      						TreePath path1=new TreePath(usecasetreerootnode.getPath());
      						if(!usecasetree.isVisible(path1)){
      							usecasetree.makeVisible(path1);
      						}
      						usecasetree.getSelectionModel().setSelectionPath(new TreePath(node.getPath()));
      						Map<DefaultMutableTreeNode, IWorkspace> hashMap = mainFrame.getActiveModelPanel().getUsecaseTreePanel().getHashMap();
      						int length = mainFrame.getActiveModelPanel().getUseCaseworkspaceList().size() - 1;
      						hashMap.put(node, mainFrame.getActiveModelPanel().getUseCaseworkspaceList().get(length));

      						//切换界面
      						mainFrame.getStepOperationButton().getPromptLabel().setText("用例图是指由参与者（Actor）、用例（Use Case）以及它们之间的关系构成的用于描述系统功能的视图。");
      						mainFrame.getOpreationPart().validate();

      						mainFrame.getOutputinformation().geTextArea().append("打开用例图: "+name+"\n");
      						
//      						//导入相应的顺序图
//      						List<IFile> files = getAllSequence();
//      						if(files.size() != 0)
//      						{
//      							for (IFile iFile : files) {
//    								ShowSeq(iFile);
//    							}
//      						}
                      		
                      	}else if(filename.contains(".seq.violet.xml")){
                      		String name = filename.replace(".seq.violet.xml", "");
                      		graphFile.AutoSave(selectedFile, mainFrame.getActiveModelPanel().getTemporarySeqFile());
                            mainFrame.addTabbedPane(workspace,name);
//                     	   if(mainFrame.getListSequenceTabPanel().size() != 0){
//                        		for(SequenceTabPanel sequenceTabPanel:mainFrame.getListSequenceTabPanel())
//                        		{
//                        			sequenceTabPanel.getPanel().setBackground(new Color(246,246,246));
//                        			sequenceTabPanel.getDeletelabel().setIcon(null);
//                        		}
//                        		mainFrame.getListSequenceTabPanel().get(mainFrame.getListSequenceTabPanel().size()-1).getPanel().setBackground(Color.white);
//                        		mainFrame.getListSequenceTabPanel().get(mainFrame.getListSequenceTabPanel().size()-1).getDeletelabel().setIcon(new ImageIcon("resources\\icons\\22x22\\beforeClose.png"));
//                        		}
                     	    JTree sequencetree = mainFrame.getActiveModelPanel().getSequenceTreePanel().getSequencetree();
                        	DefaultTreeModel sequencetreemodel = mainFrame.getActiveModelPanel().getSequenceTreePanel().getSequencetreemodel();
                        	DefaultMutableTreeNode sequencetreerootnode = mainFrame.getActiveModelPanel().getSequenceTreePanel().getSequencetreerootnode();
                        	
     						DefaultMutableTreeNode node=new DefaultMutableTreeNode(name);
     						
     						sequencetreemodel.insertNodeInto(node, sequencetreerootnode, sequencetreerootnode.getChildCount());
     						TreePath path1=new TreePath(sequencetreerootnode.getPath());
     						if(!sequencetree.isVisible(path1)){
     							sequencetree.makeVisible(path1);
     						}
     						sequencetree.getSelectionModel().setSelectionPath(new TreePath(node.getPath()));
     						Map<DefaultMutableTreeNode, IWorkspace> hashMap = mainFrame.getActiveModelPanel().getSequenceTreePanel().getHashMap();
     						int length = mainFrame.getActiveModelPanel().getSequencespaceList().size() - 1;
     						hashMap.put(node, mainFrame.getActiveModelPanel().getSequencespaceList().get(length));
     						

     						mainFrame.getStepOperationButton().getPromptLabel().removeAll();
     						mainFrame.getStepOperationButton().getPromptLabel().setText("顺序图是将交互关系表示为一个二维图。纵向是时间轴，时间沿竖线向下延伸。横向轴代表了在协作中各独立对象的类元角色。");
     		
     						mainFrame.getOpreationPart().revalidate();  
     						mainFrame.getOutputinformation().geTextArea().append("打开顺序图: "+name+"\n");
                      	}
    				   }
				}
               }
               catch (StreamException se)
               {
                   dialogFactory.showErrorDialog(dialogOpenFileIncompatibilityMessage);
               }
               catch (Exception e)
               {
                   dialogFactory.showErrorDialog(dialogOpenFileErrorMessage + " : " + e.getMessage());
               }
           }
       });
       if (this.fileChooserService == null) 
    	   this.fileOpenItem.setEnabled(false);
    }
      
    /**
     * Init new menu entry
     */
    public void initFileNewMenu()
    {
        List<IDiagramPlugin> diagramPlugins = this.pluginRegistry.getDiagramPlugins();

        // Step 1 : sort diagram plugins by categories and names
        SortedMap<String, SortedSet<IDiagramPlugin>> diagramPluginsSortedByCategory = new TreeMap<String, SortedSet<IDiagramPlugin>>();
        for (final IDiagramPlugin aDiagramPlugin : diagramPlugins)
        {
            String category = aDiagramPlugin.getCategory();
            if (!diagramPluginsSortedByCategory.containsKey(category))
            {
                SortedSet<IDiagramPlugin> newSortedSet = new TreeSet<IDiagramPlugin>(new Comparator<IDiagramPlugin>()
                {
                    @Override
                    public int compare(IDiagramPlugin o1, IDiagramPlugin o2)
                    {
                        String n1 = o1.getName();
                        String n2 = o2.getName();
                        return n1.compareTo(n2);
                    }
                });
                diagramPluginsSortedByCategory.put(category, newSortedSet);
            }
            SortedSet<IDiagramPlugin> aSortedSet = diagramPluginsSortedByCategory.get(category);
            aSortedSet.add(aDiagramPlugin);
        }

        // Step 2 : populate menu entry
        for (String aCategory : diagramPluginsSortedByCategory.keySet())
        {
            String categoryName = aCategory.replaceFirst("[0-9]*\\.", "");
            JMenu categorySubMenu = new JMenu(categoryName);
            Dimension preferredSize = categorySubMenu.getPreferredSize();
            preferredSize = new Dimension((int) preferredSize.getWidth() + 30, (int) preferredSize.getHeight());
            categorySubMenu.setPreferredSize(preferredSize);
            fileNewMenu.add(categorySubMenu);
            SortedSet<IDiagramPlugin> diagramPluginsByCategory = diagramPluginsSortedByCategory.get(aCategory);
            for (final IDiagramPlugin aDiagramPlugin : diagramPluginsByCategory)
            {
                String name = aDiagramPlugin.getName();
                name = name.replaceFirst("[0-9]*\\.", "");
                final JMenuItem item = new JMenuItem(name);
                ImageIcon sampleDiagramImage = getSampleDiagramImage(aDiagramPlugin);
                if (sampleDiagramImage != null)
                {
                    item.setRolloverIcon(sampleDiagramImage);
                }
                item.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent event)
                    {
                    	DefaultMutableTreeNode ucaseTree = mainFrame.getActiveModelPanel().getUsecaseTreePanel().getUsecasetreerootnode();
                		DefaultMutableTreeNode seqTree = mainFrame.getActiveModelPanel().getSequenceTreePanel().getSequencetreerootnode();
                		boolean ucase = true;
                		boolean seq = true;
                    	String itemname=item.getText().substring(0, 2);
                    	String str = "";
                    	if(itemname.equals("Us")){
                    		Icon icon = new ImageIcon("resources/icons/22x22/open.png");
							str = (String) JOptionPane.showInputDialog(null,"请输入用例图名称:\n","用例图名称",JOptionPane.PLAIN_MESSAGE,icon,null,"在这输入");
							if(str == null)
								return;
							for(int i = 0;i < ucaseTree.getChildCount();i++)
							{
								if(ucaseTree.getChildAt(i).toString().equals(str))
								{
									ucase = false;
									JOptionPane.showMessageDialog(null, "该模型与已经存在的模型冲突!","标题",JOptionPane.WARNING_MESSAGE); 
								}
							}
                    	}
                    	if(itemname.equals("Se")){
                    		Icon icon = new ImageIcon("resources/icons/22x22/open.png");
							str = (String) JOptionPane.showInputDialog(null,"请输入顺序图名称:\n","顺序图名称",JOptionPane.PLAIN_MESSAGE,icon,null,"在这输入");
							if(str == null)
								return;
							
							for(int i = 0;i < seqTree.getChildCount();i++)
							{
								if(seqTree.getChildAt(i).toString().equals(str))
								{
									seq = false;
									JOptionPane.showMessageDialog(null, "该模型与已经存在的模型冲突!","标题",JOptionPane.WARNING_MESSAGE); 
								}
							}
                    	}
                    	if(itemname.equals("Se") && seq == true){
                    		Class<? extends IGraph> graphClass = aDiagramPlugin.getGraphClass();
                        	IGraphFile graphFile = new GraphFile(graphClass);
                            IWorkspace diagramPanel = new Workspace(graphFile);
                            diagramPanel.setName(str);
                            mainFrame.addTabbedPane(diagramPanel);
                            mainFrame.getOutputinformation().geTextArea().append("新建顺序图: "+str+"\n");
                            
                        	JTree sequencetree = mainFrame.getActiveModelPanel().getSequenceTreePanel().getSequencetree();
                        	DefaultTreeModel sequencetreemodel = mainFrame.getActiveModelPanel().getSequenceTreePanel().getSequencetreemodel();
                        	DefaultMutableTreeNode sequencetreerootnode = mainFrame.getActiveModelPanel().getSequenceTreePanel().getSequencetreerootnode();
							DefaultMutableTreeNode node=new DefaultMutableTreeNode(str);
							sequencetreemodel.insertNodeInto(node, sequencetreerootnode, sequencetreerootnode.getChildCount());
							TreePath path=new TreePath(sequencetreerootnode.getPath());
							if(!sequencetree.isVisible(path)){
								sequencetree.makeVisible(path);
							}
							sequencetree.getSelectionModel().setSelectionPath(new TreePath(node.getPath()));
							Map<DefaultMutableTreeNode, IWorkspace> hashMap = mainFrame.getActiveModelPanel().getSequenceTreePanel().getHashMap();
							int length = mainFrame.getActiveModelPanel().getSequencespaceList().size() - 1;
							hashMap.put(node, mainFrame.getActiveModelPanel().getSequencespaceList().get(length));

                    	}    
                    	if(itemname.equals("Us") && ucase == true){
                    		Class<? extends IGraph> graphClass = aDiagramPlugin.getGraphClass();
                        	IGraphFile graphFile = new GraphFile(graphClass);
                            IWorkspace diagramPanel = new Workspace(graphFile);
                            diagramPanel.setName(str);
                            mainFrame.addTabbedPane(diagramPanel);
                            mainFrame.getOutputinformation().geTextArea().append("新建用例图: "+str+"\n");
                            
                        	JTree usecasetree = mainFrame.getActiveModelPanel().getUsecaseTreePanel().getUsecasetree();
                        	DefaultTreeModel usecasetreemodel = mainFrame.getActiveModelPanel().getUsecaseTreePanel().getUsecasetreemodel();
                        	DefaultMutableTreeNode usecasetreerootnode = mainFrame.getActiveModelPanel().getUsecaseTreePanel().getUsecasetreerootnode();                        
							DefaultMutableTreeNode node=new DefaultMutableTreeNode(str);
							usecasetreemodel.insertNodeInto(node, usecasetreerootnode, usecasetreerootnode.getChildCount());
							TreePath path=new TreePath(usecasetreerootnode.getPath());
							if(!usecasetree.isVisible(path)){
								usecasetree.makeVisible(path);
							}
							usecasetree.getSelectionModel().setSelectionPath(new TreePath(node.getPath()));
							Map<DefaultMutableTreeNode, IWorkspace> hashMap = mainFrame.getActiveModelPanel().getUsecaseTreePanel().getHashMap();
							int length = mainFrame.getActiveModelPanel().getUseCaseworkspaceList().size() - 1;
							hashMap.put(node, mainFrame.getActiveModelPanel().getUseCaseworkspaceList().get(length));
                    	}
                    	mainFrame.renewPanel();
                    }
                    
                });
                categorySubMenu.add(item);
            }
        }
    }

    /**
     * Init recent menu entry
     */
    public void initFileRecentMenu()
    {
        // Set entries on startup
        refreshFileRecentMenu();
        // Refresh recent files list each time the global file menu gets the focus
        this.addFocusListener(new FocusListener()
        {

            public void focusGained(FocusEvent e)
            {
                refreshFileRecentMenu();
            }

            public void focusLost(FocusEvent e)
            {
                // Nothing to do
            }

        });
        if (this.fileChooserService == null || (this.fileChooserService != null && this.fileChooserService.isWebStart()))
        {
            this.fileRecentMenu.setEnabled(false);
        }
    }

    /**
     * Updates file recent menu
     */
    private void refreshFileRecentMenu()
    {
        fileRecentMenu.removeAll();
        for (final IFile aFile : userPreferencesService.getRecentFiles())
        {
            String name = aFile.getFilename();
            JMenuItem item = new JMenuItem(name);
            fileRecentMenu.add(item);
            item.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent event)
                {
                    try
                    {
                        IGraphFile graphFile = new GraphFile(aFile);
                        IWorkspace workspace = new Workspace(graphFile);
                        mainFrame.addTabbedPane(workspace);
                        userPreferencesService.addOpenedFile(aFile);
                        userPreferencesService.addRecentFile(aFile);
                    }
                    catch (Exception e)
                    {
                        dialogFactory.showErrorDialog(dialogOpenFileErrorMessage + " : " + e.getMessage());
                        userPreferencesService.removeOpenedFile(aFile);
                    }
                }
            });
        }
    }

    /**
     * @param diagramPlugin
     * @return an image exported from the sample diagram file attached to each plugin or null if no one exists
     * @throws IOException
     */
    private ImageIcon getSampleDiagramImage(IDiagramPlugin diagramPlugin)
    {
        try
        {
            String sampleFilePath = diagramPlugin.getSampleFilePath();
            InputStream resourceAsStream = getClass().getResourceAsStream("/" + sampleFilePath);
            if (resourceAsStream == null) {
                return null;
            }
            IGraph graph = this.filePersistenceService.read(resourceAsStream);
            BufferedImage image = FileExportService.getImage(graph);
            JLabel label = new JLabel();
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setIcon(new ImageIcon(image));
            label.setSize(new Dimension(600, 550));
            label.setBackground(Color.WHITE);
            label.setOpaque(true);
            Dimension size = label.getSize();
            BufferedImage image2 = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = image2.createGraphics();
            label.paint(g2);
            return new ImageIcon(image2);
        }
        catch (Exception e)
        {
        	
            // Failed to load sample. It doesn"t matter.
            return null;
        }
    }
   /*
    * 张建新加
    */
        private void initFileDsaveItem()
        {
            this.fileDsaveItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    IWorkspace workspace = mainFrame.getActiveWorkspace();
                    if (workspace != null)
                    {
                        IGraphFile graphFile = workspace.getGraphFile();
                        if(graphFile.getGraph().getClass().getSimpleName().contains("UseCase")) {
                        	String sourcePath = mainFrame.getBathRoute() + "/UseCaseDiagram/VioletXML/";
                        	graphFile.dsave(sourcePath);
						} 
                        else {
                        	String sourcePath = mainFrame.getBathRoute() + "/SequenceDiagram/VioletXML/";
                        	graphFile.dsave(sourcePath);
						}
                        
                        
                    }
                }
            });
            if (this.fileChooserService == null || (this.fileChooserService != null && this.fileChooserService.isWebStart()))
            {
                this.fileDsaveItem.setEnabled(false);
            }
        }
   
    
    public static String getDirectory() {
		return directory;
	}
    public static String getFileName() {
		return fileName;
	}

	public static String fileName;
	public static String directory;

	
	/** The file chooser to use with with menu */
    @InjectedBean
    public IFileChooserService fileChooserService;

    /** Application stopper */
    public ApplicationStopper stopper = new ApplicationStopper();

    /** Plugin registry */
    @InjectedBean
    private PluginRegistry pluginRegistry;

    /** DialogBox handler */
    @InjectedBean
    private DialogFactory dialogFactory;

    /** Access to user preferences */
    @InjectedBean
    private UserPreferencesService userPreferencesService;

    /** File services */
    @InjectedBean
    private FileNamingService fileNamingService;
    
    /** Service to convert IGraph to XML content (and XML to IGraph of course) */
    @InjectedBean
    private IFilePersistenceService filePersistenceService;

    /** Application main frame */
    private MainFrame mainFrame;

    @ResourceBundleBean(key = "file.new")
    private JMenu fileNewMenu;

    @ResourceBundleBean(key = "file.open")
    public JMenuItem fileOpenItem;

    @ResourceBundleBean(key="file.dsave")
    private JMenuItem fileDsaveItem;
    
    @ResourceBundleBean(key = "file.recent")
    private JMenu fileRecentMenu;

    @ResourceBundleBean(key = "file.close")
    private JMenuItem fileCloseItem;

    @ResourceBundleBean(key = "file.save")
    private JMenuItem fileSaveItem;

    @ResourceBundleBean(key = "file.save_as")
    private JMenuItem fileSaveAsItem;

    @ResourceBundleBean(key = "file.export_to_image")
    private JMenuItem fileExportToImageItem;

    @ResourceBundleBean(key = "file.export_to_clipboard")
    private JMenuItem fileExportToClipBoardItem;

    @ResourceBundleBean(key = "file.export_to_java")
    private JMenuItem fileExportToJavaItem;

    @ResourceBundleBean(key = "file.export_to_python")
    private JMenuItem fileExportToPythonItem;

    @ResourceBundleBean(key = "file.export")
    private JMenu fileExportMenu;

    @ResourceBundleBean(key = "file.print")
    private JMenuItem filePrintItem;

    @ResourceBundleBean(key = "file.exit")
    private JMenuItem fileExitItem;

    @ResourceBundleBean(key = "dialog.close.title")
    private String dialogCloseTitle;

    @ResourceBundleBean(key = "dialog.close.ok")
    private String dialogCloseMessage;

    @ResourceBundleBean(key = "dialog.close.icon")
    private ImageIcon dialogCloseIcon;

    @ResourceBundleBean(key = "dialog.open_file_failed.text")
    private String dialogOpenFileErrorMessage;

    @ResourceBundleBean(key = "dialog.open_file_content_incompatibility.text")
    private String dialogOpenFileIncompatibilityMessage;
    
    //测试
    private JMenuItem closeTab;

}
