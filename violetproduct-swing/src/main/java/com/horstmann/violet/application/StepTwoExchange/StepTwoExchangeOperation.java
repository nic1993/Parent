package com.horstmann.violet.application.StepTwoExchange;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.dom4j.DocumentException;
import org.eclipse.draw2d.Clickable;

import cn.edu.hdu.lab.config.StaticConfig;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceIsogenySD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceSD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceUCRelation;
import cn.edu.hdu.lab.service.interfaces.Work;
import cn.edu.hdu.lab.service.parserEA.InvalidTagException;
import cn.edu.hdu.lab.service.sd2tmc.WorkImpl;

import com.horstmann.violet.application.StepTwoModelExpand.ExchangeNodeLabel;
import com.horstmann.violet.application.StepTwoModelExpand.GradientProgressBarUI;
import com.horstmann.violet.application.StepTwoModelExpand.ProgressUI;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.gui.XMLToTree;
import com.horstmann.violet.application.gui.util.tanchao.TanchaoMarkovTransitionEdge;
import com.horstmann.violet.application.gui.util.tanchao.MarkovXML2GraphFile;
import com.horstmann.violet.application.gui.util.tanchao.TianWriteToVioletMarkov;
import com.horstmann.violet.application.gui.util.tanchao.markovlayout.LayoutMarkov;
import com.horstmann.violet.application.gui.util.tanchao.markovlayout.PathProp;
import com.horstmann.violet.application.gui.util.tiancai.markov.Worker;
import com.horstmann.violet.application.menu.FileMenu;
import com.horstmann.violet.framework.file.GraphFile;
import com.horstmann.violet.framework.file.IGraphFile;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.markov.MarkovNode;
import com.horstmann.violet.product.diagram.markov.MarkovStartNode;
import com.horstmann.violet.product.diagram.markov.MarkovTransitionEdge;
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.Workspace;

public class StepTwoExchangeOperation extends JPanel{
       private JLabel toplabel;
       private JProgressBar ExchangeProgressBar;
       private JButton startExchange;
       private JButton restartExchange;
       private JPanel gapPanel;
       private MainFrame mainFrame;
       private FileMenu fileMenu;
       
       private String Model_Name;
       private Work worker;
       private Map<String, List<InterfaceUCRelation>> UCRMap;
       private List<InterfaceIsogenySD> IISDList;
       private ExchangeNodePanel exchangeNodePanel;
       private JScrollPane XMLPanel;
       
       private int progressBarIndex;
       private Callable<Integer> maincallable;
       private FutureTask<Integer> maintask;
   	   private Thread mainthread; 
       private Callable<Integer> callable1;
   	   private FutureTask<Integer> task1;
   	   private Thread thread1;
       private Callable<Integer> callable2;
       private FutureTask<Integer> task2;
   	   private Thread thread2;
   	   private boolean isAlive = true;
   	   private boolean isTime = false;
   	   private List<FutureTask<Integer>> futuretasklist;
   	   private List<Thread> threadlist;
   	   
   	   private String currentUcase;
	   private String currentSeq;
   	   private String MarkovRoute;
       public StepTwoExchangeOperation(MainFrame mainFrame,FileMenu fileMenu)
       {
            this.mainFrame = mainFrame;
            this.fileMenu = fileMenu;
            init();
            this.setLayout(new GridBagLayout());
            this.add(toplabel, new GBC(0, 0,  3, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 15, 10, 0));
            this.add(ExchangeProgressBar,new GBC(0, 1, 3, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 15, 10, 15));
            this.add(startExchange,new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 15, 10, 0));
            this.add(gapPanel, new GBC(1, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 0, 10, 0));
            this.add(restartExchange, new GBC(2, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 0, 10, 15));
       }
       public void init()	
       {
    	   exchangeNodePanel = new ExchangeNodePanel(mainFrame);
    	   toplabel = new JLabel();
    	   toplabel.setFont(new Font("宋体",Font.PLAIN,16));
    	   
    	   ExchangeProgressBar = new JProgressBar();
    	   ExchangeProgressBar.setUI(new ProgressUI(ExchangeProgressBar, Color.green));
    	   ExchangeProgressBar.setPreferredSize(new Dimension(600, 30));
    	   ExchangeProgressBar.setUI(new GradientProgressBarUI());
    	   
    	   startExchange = new JButton("开始转换");
    	   restartExchange = new JButton("停止转换");
    	   restartExchange.setEnabled(false);
    	   gapPanel = new JPanel();
    	   buttonListen();
       }
       private void initThread()
       {
    	   progressBarIndex = 0;
    	   ExchangeProgressBar.setValue(0);
   		   maincallable = new Callable<Integer>() {
     			@Override
     			public Integer call() throws Exception {
     				// TODO Auto-generated method stub
//     				try {
//     					isAlive = true;
//     				   	isTime = false;
//     					restartExchange.setEnabled(true);
//     					mainFrame.getStepTwoExpand().getEstimateLabel().setEnabled(false);
//     					mainFrame.getsteponeButton().getExpandModelLabel().setEnabled(false);
//     					mainFrame.getsteponeButton().getExpandCaseModel().setEnabled(false);
//     					
//     					mainFrame.getStepTwoModelOperation().getStartExpandButton().setEnabled(false);
//     					mainFrame.getStepTwoCaseOperation().getStartExpandButton().setEnabled(false);
//     					startExchange.setEnabled(false);
//     					
//     					mainFrame.getStepTwoExchangeTabbedPane().getExchangeResults().removeAll();
//     					mainFrame.getStepTwoExchangeTabbedPane().getExchangeResport().removeAll();
//     					mainFrame.getStepTwoExchangeTabbedPane().updateUI();
//     					
//     					currentUcase = mainFrame.getStepTwoModelOperation().getCurrentUcase();
//     					currentSeq = mainFrame.getStepTwoModelOperation().getCurrentSeq();
//     					
//     					worker=new WorkImpl();
//     					if(currentUcase.contains("\\UseCaseDiagram\\EAXML"))
//     				    {
//     				    	StaticConfig.umlPathPrefix = currentSeq;
//     				    	try {
//     				    		System.out.println("11111111111");
//     							worker.transInitial(currentUcase);
//     						} catch (Exception e1) {
//     							// TODO Auto-generated catch block
//     							e1.printStackTrace();
//     						}
//     				    }
//     				    else {
//     				    	StaticConfig.umlPathPrefix = currentSeq;
//     				    	try {
//     							worker.transInitialHDU(currentUcase);
//     						} catch (Throwable e1) {
//     							// TODO Auto-generated catch block
//     							e1.printStackTrace();
//     						}
//     					}
////     					
////     					worker = mainFrame.getStepTwoModelOperation().getWorker();
//     					UCRMap = mainFrame.getStepTwoModelOperation().getUcMap();
//     					IISDList = mainFrame.getStepTwoModelOperation().getIISDList();
//     					
//     					for(String key : UCRMap.keySet()) //求解 生成
//     					{
//     						List<InterfaceUCRelation> interfaceUCRelations = UCRMap.get(key);
//     						for(InterfaceUCRelation interfaceUCRelation : interfaceUCRelations){
//     							System.out.println(key+"用例: " + "关系: " + interfaceUCRelation.getUCRelation() + "概率: " + interfaceUCRelation.getUCRelProb());
//     						}
//     					}
//     					System.out.println("模型转换====================");
//						for(InterfaceIsogenySD interfaceIsogenySD : IISDList)
//						{
//							System.out.println(interfaceIsogenySD.getUcName() + "用例场景个数: " + interfaceIsogenySD.getISDList().size());
//							for(InterfaceSD interfaceSD : interfaceIsogenySD.getISDList())
//							{
//								System.out.println("场景名称： " + interfaceSD.getName());
//							}
//						}
// 
//     					toplabel.removeAll();
//     					toplabel.setText("用例迁移概率.....");
//     					while (progressBarIndex < 13) {
//     						progressBarIndex++;
//     						ExchangeProgressBar.setValue(progressBarIndex);
//     						Thread.sleep(200);
//						}
//     					worker.assignmentPro(IISDList);   
//     					toplabel.removeAll();
//     					toplabel.setText("正在获取场景概率.....");
//     					while (progressBarIndex < 26) {
//     						progressBarIndex++;
//     						ExchangeProgressBar.setValue(progressBarIndex);
//     						Thread.sleep(200);
//						}
//     					System.out.println("222222222222");
//     					try {
//    						worker.transToMarckov(UCRMap);
//    					} catch (Exception e1) {
//    						// TODO Auto-generated catch block
//    						e1.printStackTrace();
//    					}     						
//					
//     					System.out.println("3333333333333");
//     					worker.probabilityAndReachableTest();
//     					
//     					toplabel.removeAll();
//     					toplabel.setText("正在生成Markov链.....");
//     					while (progressBarIndex < 55) {
//     						progressBarIndex++;
//     						ExchangeProgressBar.setValue(progressBarIndex);
//     						Thread.sleep(200);
//						}
//     					
//     					worker.writeMarkov(mainFrame,Model_Name); 
//     					if(isTime == false){
//     						MarkovRoute = mainFrame.getBathRoute()+"/NoTimeMarkov/";
//     						 File file = new File(MarkovRoute);
//     				    	   if(file.exists())
//     				    	   {
//     				    		   file.mkdirs();
//     				    	   }
//     					}
//     					else {
//     						MarkovRoute = mainFrame.getBathRoute()+"/TimeMarkov/";
//     						 File file = new File(MarkovRoute);
//     				    	   if(file.exists())
//     				    	   {
//     				    		   file.mkdirs();
//     				    	   }
//						}
//     					
//     					TianWriteToVioletMarkov tian=new TianWriteToVioletMarkov();
//     					tian.find(MarkovRoute+Model_Name+".xml");
//     					tian.writeVioletMarkov(MarkovRoute+Model_Name+".markov.violet.xml");
//     					GraphFile fileGraphFile=MarkovXML2GraphFile.toGraphFile(MarkovRoute,Model_Name+".markov.violet.xml");
//     					LayoutMarkov.layout(MarkovRoute,MarkovRoute+Model_Name+".markov.violet.xml",Model_Name+"layout.markov.violet.xml");
//     					GraphFile graphFile=MarkovXML2GraphFile.toGraphFile(MarkovRoute,Model_Name+"layout.markov.violet.xml");
//     					XMLPanel = XMLToTree.getTree(MarkovRoute+Model_Name+"layout.markov.violet.xml");
//     					mainFrame.getStepTwoExchangeTabbedPane().getExchangeResport().removeAll();
//     					mainFrame.getStepTwoExchangeTabbedPane().getExchangeResport().add(XMLToTree.getTree(MarkovRoute+Model_Name+"layout.markov.violet.xml"));
//     					mainFrame.getStepTwoExchangeTabbedPane().setSelectedIndex(1);
//     					
//     					
//     					
//     					toplabel.removeAll();
//     					toplabel.setText("正在获取Markov节点信息.....");
//     					while (progressBarIndex < 55) {
//     						progressBarIndex++;
//     						ExchangeProgressBar.setValue(progressBarIndex);
//     						Thread.sleep(200);
//						}
//     					
////     					//添加节点信息
//     					mainFrame.getStepTwoCenterRightPanel().getNodeTextMap().clear(); //首先清除map
//     				    Map<Object, String> nodeTextMap = new HashMap<Object,String>();
//     					Collection<INode> nodes = graphFile.getGraph().getAllNodes();
//     					for(INode node : nodes)
//     					{
//     						if(node.getClass().getSimpleName().equals("MarkovNode"))
//     						{
//     							nodeTextMap.put(node.hashCode(), ((MarkovNode)node).getName());
//     							((MarkovNode)node).setName("");
//     						}
//     						if(node.getClass().getSimpleName().equals("MarkovStartNode")) {
//     							nodeTextMap.put(node.hashCode(), ((MarkovStartNode)node).getName());
//     							((MarkovStartNode)node).setName("");
//     						}
//     					}	
//     					
//     					toplabel.removeAll();
//     					toplabel.setText("正在获取Markov迁移信息.....");
//     					while (progressBarIndex < 70) {
//     						progressBarIndex++;
//     						ExchangeProgressBar.setValue(progressBarIndex);
//     						Thread.sleep(200);
//						}
//     					//修改边的信息
//     					mainFrame.getStepTwoCenterRightPanel().getEdgeTextMap().clear();
//     					Map<Object, String> edgeTextMap = new HashMap<Object,String>();
//     					Collection<IEdge> edges = graphFile.getGraph().getAllEdges();
//     					for(IEdge edge : edges)
//     					{
//     						if(edge.getClass().getSimpleName().equals("MarkovTransitionEdge"))
//     						{
//     							edgeTextMap.put(edge.hashCode(), ((MarkovTransitionEdge)edge).getPro().toString());
//     							((MarkovTransitionEdge)edge).setPro("");
//     						}
//     					}	
//     					
//     					toplabel.removeAll();
//     					toplabel.setText("正在生成Markov图形.....");
//     					while (progressBarIndex <= 90) {
//     						progressBarIndex++;
//     						ExchangeProgressBar.setValue(progressBarIndex);
//     						Thread.sleep(100);
//						}
//     					
//     					IWorkspace workspace=new Workspace(graphFile);
//     					mainFrame.addTabbedPane(workspace);
//     					workspace.getAWTComponent().getScrollableSideBar().setVisible(false);
//     					workspace.getAWTComponent().updateUI();	
//     					mainFrame.getStepTwoExchangeTabbedPane().setSelectedIndex(0);
//     					
//     					
////     					清除布局XML
//     					for(File file : new File(MarkovRoute).listFiles())
//     					{
//     						if(file.getName().contains(".markov.violet.xml")){
//     							file.delete();
//     						}
//     					}
//
//     					ExchangeNodeLabel nodeLabel = new ExchangeNodeLabel(Model_Name,mainFrame);
//     					nodeLabel.setWorkspace(workspace);
//     					nodeLabel.setXMLPanel(XMLPanel);
//     					nodeLabel.setNodeTextMap(nodeTextMap);
//     					nodeLabel.setEdgeTextMap(edgeTextMap);
////     					mainFrame.getStepTwoExpand().getExchangepPanel().add(nodeLabel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 25, 0, 0));
////     					mainFrame.getStepTwoExpand().getExchangepPanel().updateUI();
//     					exchangeNodePanel.insertNodeLabel(nodeLabel);
//     					
//     					mainFrame.getStepTwoCenterRightPanel().setNodeTextMap(nodeTextMap);
//     					mainFrame.getStepTwoCenterRightPanel().setEdgeTextMap(edgeTextMap);
//     					startExchange.setEnabled(true);
//     					mainFrame.getsteponeButton().getExpandModelLabel().setEnabled(true);
//     					mainFrame.getStepTwoExpand().getEstimateLabel().setEnabled(true);
//     					mainFrame.getsteponeButton().getExpandCaseModel().setEnabled(true);
//     					
//     					mainFrame.getStepTwoModelOperation().getStartExpandButton().setEnabled(false);
//     					mainFrame.getStepTwoCaseOperation().getStartExpandButton().setEnabled(false);
//     					restartExchange.setEnabled(false);
//     				    
//     					toplabel.removeAll();
//     					toplabel.setText("Markov转换成功,可以生成测试用例!");
//     					while (progressBarIndex <= 100) {
//     						progressBarIndex++;
//     						ExchangeProgressBar.setValue(progressBarIndex);
//     						Thread.sleep(100);
//						}
//     					} catch (DocumentException e2) {
//     						// TODO Auto-generated catch block
//     						e2.printStackTrace();
//     					}
     				
     				
     				try {
     					isAlive = true;
     				   	isTime = false;
     					restartExchange.setEnabled(true);
     					mainFrame.getStepTwoExpand().getEstimateLabel().setEnabled(false);
     					mainFrame.getsteponeButton().getExpandModelLabel().setEnabled(false);
     					mainFrame.getsteponeButton().getExpandCaseModel().setEnabled(false);
     					
     					mainFrame.getStepTwoModelOperation().getStartExpandButton().setEnabled(false);
     					mainFrame.getStepTwoCaseOperation().getStartExpandButton().setEnabled(false);
     					startExchange.setEnabled(false);
     					
     					mainFrame.getStepTwoExchangeTabbedPane().getExchangeResults().removeAll();
     					mainFrame.getStepTwoExchangeTabbedPane().getExchangeResport().removeAll();
     					mainFrame.getStepTwoExchangeTabbedPane().updateUI();
     					
     					currentUcase = mainFrame.getStepTwoModelOperation().getCurrentUcase();
     					currentSeq = mainFrame.getStepTwoModelOperation().getCurrentSeq();
     					
     					mainFrame.renewPanel();
     					
//     					Work worker=mainFrame.getStepTwoModelOperation().getNewWorker();
//     					if(currentUcase.contains("\\UseCaseDiagram\\EAXML"))
//     				    {
//     				    	StaticConfig.umlPathPrefix = currentSeq;
//     				    	try {
//     							worker.transInitial(currentUcase);
//     						} catch (Exception e1) {
//     							// TODO Auto-generated catch block
//     							e1.printStackTrace();
//     						}
//     				    }
//     				    else {
//     				    	StaticConfig.umlPathPrefix = currentSeq;
//     				    	try {
//     							worker.transInitialHDU(currentUcase);
//     						} catch (Throwable e1) {
//     							// TODO Auto-generated catch block
//     							e1.printStackTrace();
//     						}
//     					}


   					    
     					mainFrame.renewPanel();
     					Work worker = mainFrame.getStepTwoModelOperation().getWorker();
     					UCRMap = mainFrame.getStepTwoModelOperation().getUcMap();
     					IISDList = mainFrame.getStepTwoModelOperation().getIISDList();
     					
//      				   Map<String, List<InterfaceUCRelation>> UCR = worker.provideUCRelation();
//      				   List<InterfaceIsogenySD> scencs = worker.provideIsogencySD();
     					
     					for(String key : UCRMap.keySet()) //求解 生成
     					{
     						List<InterfaceUCRelation> interfaceUCRelations = UCRMap.get(key);
     						for(InterfaceUCRelation interfaceUCRelation : interfaceUCRelations){	
//     	    					List<InterfaceUCRelation> interfaceUCRelations1 = UCR.get(key);
//     	    					for(InterfaceUCRelation interfaceUCRelation1 : interfaceUCRelations1){
//     	    						if(interfaceUCRelation1.getUCRelation().equals(interfaceUCRelation.getUCRelation()))
//     	    						{
//     	    							interfaceUCRelation1.setUCRelProb(interfaceUCRelation.getUCRelProb());
//     	    						}
     	    					System.out.println(key+"用例: " + "关系: " + interfaceUCRelation.getUCRelation() + "概率: " + interfaceUCRelation.getUCRelProb());
     	    					}
     						}
     					

//     					System.out.println("模型转换====================");
    					for(InterfaceIsogenySD interfaceIsogenySD : IISDList)
    					{
    						for(InterfaceSD interfaceSD : interfaceIsogenySD.getISDList())
    						{
    							System.out.println(interfaceSD + "场景概率: " + interfaceSD.getPro());
    						}
    					}

    					
     					toplabel.removeAll();
     					toplabel.setText("用例迁移概率.....");
     					while (progressBarIndex < 13) {
     						progressBarIndex++;
     						ExchangeProgressBar.setValue(progressBarIndex);
     						Thread.sleep(200);
						}

     					worker.assignmentPro(IISDList);   
     					mainFrame.renewPanel();
     					List<Object> verList=worker.transVerify(); 
     					
     					toplabel.removeAll();
     					toplabel.setText("正在获取场景概率.....");
     					while (progressBarIndex < 26) {
     						progressBarIndex++;
     						ExchangeProgressBar.setValue(progressBarIndex);
     						Thread.sleep(200);
						}
     					try {
    						worker.transToMarckov(UCRMap);
    					} catch (Exception e1) {
    						// TODO Auto-generated catch block
    						e1.printStackTrace();
    					}     						
     					mainFrame.renewPanel();
     					try {
    						worker.probabilityAndReachableTest();
    					} catch (Exception e1) {
    						// TODO Auto-generated catch block
    						e1.printStackTrace();
    					}
     					mainFrame.renewPanel();
     					
     					toplabel.removeAll();
     					toplabel.setText("正在生成Markov链.....");
     					while (progressBarIndex < 55) {
     						progressBarIndex++;
     						ExchangeProgressBar.setValue(progressBarIndex);
     						Thread.sleep(200);
						}

     					
     					try {
    						worker.writeMarkov(Model_Name,mainFrame);
    					} catch (IOException e1) {
    						// TODO Auto-generated catch block
    						e1.printStackTrace();
    					} catch (Exception e1) {
    						// TODO Auto-generated catch block
    						e1.printStackTrace();
    					} 
     					if(isTime == false){
     						MarkovRoute = mainFrame.getBathRoute()+"/NoTimeMarkov/";
     						 File file = new File(MarkovRoute);
     				    	   if(file.exists())
     				    	   {
     				    		   file.mkdirs();
     				    	   }
     					}
     					else {
     						MarkovRoute = mainFrame.getBathRoute()+"/TimeMarkov/";
     						 File file = new File(MarkovRoute);
     				    	   if(file.exists())
     				    	   {
     				    		   file.mkdirs();
     				    	   }
    					}
     					mainFrame.renewPanel();
     					
     					TianWriteToVioletMarkov tian=new TianWriteToVioletMarkov();
     					tian.find(MarkovRoute+Model_Name+".xml");
     					tian.writeVioletMarkov(MarkovRoute+Model_Name+".markov.violet.xml");
     					GraphFile fileGraphFile=MarkovXML2GraphFile.toGraphFile(MarkovRoute,Model_Name+".markov.violet.xml");
     					LayoutMarkov.layout(MarkovRoute,MarkovRoute+Model_Name+".markov.violet.xml",Model_Name+"layout.markov.violet.xml");
     					GraphFile graphFile=MarkovXML2GraphFile.toGraphFile(MarkovRoute,Model_Name+"layout.markov.violet.xml");
     					XMLPanel = XMLToTree.getTree(MarkovRoute+Model_Name+"layout.markov.violet.xml");
     					mainFrame.getStepTwoExchangeTabbedPane().getExchangeResport().removeAll();
     					mainFrame.getStepTwoExchangeTabbedPane().getExchangeResport().add(XMLToTree.getTree(MarkovRoute+Model_Name+"layout.markov.violet.xml"));
     					mainFrame.getStepTwoExchangeTabbedPane().setSelectedIndex(1);
     					mainFrame.renewPanel();
     					
     					
     					toplabel.removeAll();
     					toplabel.setText("正在获取Markov节点信息.....");
     					while (progressBarIndex < 62) {
     						progressBarIndex++;
     						ExchangeProgressBar.setValue(progressBarIndex);
     						Thread.sleep(200);
						}
     					
//     					//添加节点信息
     					mainFrame.getStepTwoCenterRightPanel().getNodeTextMap().clear(); //首先清除map
     				    Map<Object, String> nodeTextMap = new HashMap<Object,String>();
     					Collection<INode> nodes = graphFile.getGraph().getAllNodes();
     					for(INode node : nodes)
     					{
     						if(node.getClass().getSimpleName().equals("MarkovNode"))
     						{
     							nodeTextMap.put(node.hashCode(), ((MarkovNode)node).getName());
     							((MarkovNode)node).setName("");
     						}
     						if(node.getClass().getSimpleName().equals("MarkovStartNode")) {
     							nodeTextMap.put(node.hashCode(), ((MarkovStartNode)node).getName());
     							((MarkovStartNode)node).setName("");
     						}
     					}	
     					
     					toplabel.removeAll();
     					toplabel.setText("正在获取Markov迁移信息.....");
     					while (progressBarIndex < 70) {
     						progressBarIndex++;
     						ExchangeProgressBar.setValue(progressBarIndex);
     						Thread.sleep(200);
						}

     					//修改边的信息
     					mainFrame.getStepTwoCenterRightPanel().getEdgeTextMap().clear();
     					Map<Object, String> edgeTextMap = new HashMap<Object,String>();
     					Collection<IEdge> edges = graphFile.getGraph().getAllEdges();
     					for(IEdge edge : edges)
     					{
     						if(edge.getClass().getSimpleName().equals("MarkovTransitionEdge"))
     						{
     							edgeTextMap.put(edge.hashCode(), ((MarkovTransitionEdge)edge).getPro().toString());
     							((MarkovTransitionEdge)edge).setPro("");
     						}
     					}	
     					
     					toplabel.removeAll();
     					toplabel.setText("正在生成Markov图形.....");
     					while (progressBarIndex <= 90) {
     						progressBarIndex++;
     						ExchangeProgressBar.setValue(progressBarIndex);
     						Thread.sleep(100);
						}
     					
     					IWorkspace workspace=new Workspace(graphFile);
     					mainFrame.addTabbedPane(workspace,"step2");
     					
     					workspace.getAWTComponent().getScrollableSideBar().setVisible(false);
     					workspace.getAWTComponent().updateUI();	
     					mainFrame.getStepTwoExchangeTabbedPane().setSelectedIndex(0);
     					mainFrame.renewPanel();
     					
//     					清除布局XML
     					for(File file : new File(MarkovRoute).listFiles())
     					{
     						if(file.getName().contains(".markov.violet.xml")){
     							file.delete();
     						}
     					}

     					ExchangeNodeLabel nodeLabel = new ExchangeNodeLabel(Model_Name,mainFrame);
     					nodeLabel.setWorkspace(workspace);
     					nodeLabel.setXMLPanel(XMLPanel);
     					nodeLabel.setNodeTextMap(nodeTextMap);
     					nodeLabel.setEdgeTextMap(edgeTextMap);
//     					mainFrame.getStepTwoExpand().getExchangepPanel().add(nodeLabel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 25, 0, 0));
//     					mainFrame.getStepTwoExpand().getExchangepPanel().updateUI();
     					exchangeNodePanel.insertNodeLabel(nodeLabel);
     					
     					mainFrame.getStepTwoCenterRightPanel().setNodeTextMap(nodeTextMap);
     					mainFrame.getStepTwoCenterRightPanel().setEdgeTextMap(edgeTextMap);
     					startExchange.setEnabled(true);
     					mainFrame.getsteponeButton().getExpandModelLabel().setEnabled(true);
     					mainFrame.getStepTwoExpand().getEstimateLabel().setEnabled(true);
     					mainFrame.getsteponeButton().getExpandCaseModel().setEnabled(true);
     					
     					mainFrame.getStepTwoModelOperation().getStartExpandButton().setEnabled(false);
     					mainFrame.getStepTwoCaseOperation().getStartExpandButton().setEnabled(false);
     					restartExchange.setEnabled(false);
     				    
     					mainFrame.getStepThreeLeftButton().setModelName(Model_Name);
     					mainFrame.getStepThreeLeftButton().setNew(true);
     					toplabel.removeAll();
     					toplabel.setText("Markov转换成功,可以生成测试用例!");
     					while (progressBarIndex <= 100) {
 						progressBarIndex++;
 						ExchangeProgressBar.setValue(progressBarIndex);
 						Thread.sleep(100);
					}

     					} catch (DocumentException e2) {
     						// TODO Auto-generated catch block
     						e2.printStackTrace();
     					}
     				return 1;
     			}
     		};
     	    maintask = new FutureTask<>(maincallable);
     	    mainthread = new Thread(maintask);
          }
       private void buttonListen()
       {
    	   startExchange.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				initThread();
				mainthread.start();
			}
		});
    	   restartExchange.addActionListener(new ActionListener() {
   			
   			@Override
   			public void actionPerformed(ActionEvent e) {
   				// TODO Auto-generated method stub
   				if(!isAlive){
   					restartExchange.setText("停止转换");
   					mainthread.resume();
   					isAlive = true;
   				}
   				else {
   					restartExchange.setText("开始转换");
   					mainthread.suspend();
   					isAlive = false;
   				}
   			}
   		});
    }
    
	public JButton getStartExchange() {
		return startExchange;
	}
	public String getModel_Name() {
		return Model_Name;
	}
	public void setModel_Name(String model_Name) {
		Model_Name = model_Name;
	}
	public JLabel getToplabel() {
		return toplabel;
	}
	public ExchangeNodePanel getExchangeNodePanel() {
		return exchangeNodePanel;
	}
	public boolean isTime() {
		return isTime;
	}
	public void setTime(boolean isTime) {
		this.isTime = isTime;
	}      
}

