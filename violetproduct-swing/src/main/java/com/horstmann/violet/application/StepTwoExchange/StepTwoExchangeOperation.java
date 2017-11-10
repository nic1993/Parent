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
import javax.swing.JOptionPane;
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
import com.horstmann.violet.application.gui.util.tanchao.TestCaseXMLToStringList;
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

public class StepTwoExchangeOperation extends JPanel {
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
	private boolean isAlive = true;
	private boolean isTime;
	private List<FutureTask<Integer>> futuretasklist;
	private List<Thread> threadlist;

	private String currentUcase;
	private String currentSeq;
	private String MarkovRoute;

	private Map<Object, String> nodeTextMap;
	private Map<Object, String> edgeTextMap;

	TestCaseXMLToStringList testCaseXMLToStringList = new TestCaseXMLToStringList();

	public StepTwoExchangeOperation(MainFrame mainFrame, FileMenu fileMenu) {
		this.mainFrame = mainFrame;
		this.fileMenu = fileMenu;
		init();
		this.setLayout(new GridBagLayout());
		this.add(toplabel, new GBC(0, 0, 3, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 15, 10, 0));
		this.add(ExchangeProgressBar, new GBC(0, 1, 3, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 15, 10, 15));
		this.add(startExchange, new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 15, 10, 0));
		this.add(gapPanel, new GBC(1, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 0, 10, 0));
		this.add(restartExchange, new GBC(2, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 0, 10, 15));
	}

	public void init() {
		exchangeNodePanel = new ExchangeNodePanel(mainFrame);
		toplabel = new JLabel();
		toplabel.setFont(new Font("宋体", Font.PLAIN, 16));

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

	private void initThread() {
		progressBarIndex = 0;
		ExchangeProgressBar.setValue(0);
		maincallable = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				while (progressBarIndex <= 100) {
					{
						if (task1.isDone()) {
							progressBarIndex++;
							ExchangeProgressBar.setValue(progressBarIndex);
							Thread.sleep(10);
						} else {
							progressBarIndex++;
							ExchangeProgressBar.setValue(progressBarIndex);
							Thread.sleep(12000);
						}
					}
				}
				return 1;
			}
		};
		maintask = new FutureTask<>(maincallable);
		mainthread = new Thread(maintask);

		callable1 = new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				try {
					isAlive = true;
					isTime = false;
					restartExchange.setEnabled(true);
					mainFrame.getStepTwoExpand().getEstimatepPanel().setVisible(false);
					mainFrame.getStepTwoExpand().getExchangepPanel().setVisible(false);
					mainFrame.getsteponeButton().getExpandCasePanel().setVisible(false);
					mainFrame.getsteponeButton().getExpandCaseModel().setVisible(false);
					
					mainFrame.getStepTwoExpand().getExchangeLabel().setEnabled(false);
					mainFrame.getStepTwoExpand().getEstimateLabel().setEnabled(false);
					mainFrame.getsteponeButton().getExpandModelLabel().setEnabled(false);
					mainFrame.getsteponeButton().getExpandCaseModel().setEnabled(false);

					mainFrame.getStepTwoModelOperation().getStartExpandButton().setEnabled(false);
					mainFrame.getStepTwoCaseOperation().getStartExpandButton().setEnabled(false);
					startExchange.setEnabled(false);
					
					mainFrame.getStepTwoCenterRightPanel().setVisible(false);
					
					

					mainFrame.getStepTwoExchangeTabbedPane().getExchangeResults().removeAll();
					mainFrame.getStepTwoExchangeTabbedPane().getExchangeResport().removeAll();
					mainFrame.getStepTwoExchangeTabbedPane().updateUI();

					currentUcase = mainFrame.getStepTwoModelOperation().getCurrentUcase();

					mainFrame.renewPanel();

					Work worker = mainFrame.getStepTwoModelOperation().getWorker();
					UCRMap = mainFrame.getStepTwoModelOperation().getUcMap();
					IISDList = mainFrame.getStepTwoModelOperation().getIISDList();

					toplabel.removeAll();
					toplabel.setText("正在获取用例迁移概率.....");
					Thread.sleep(200);

					worker.assignmentPro(IISDList);
					mainFrame.renewPanel();
					List<Object> verList = worker.transVerify();

					toplabel.removeAll();
					toplabel.setText("正在获取场景概率.....");
					Thread.sleep(200);

					List<String> seqNames = new ArrayList<String>();
					List<String> ucNames = new ArrayList<String>();
					
					worker.transToMarckov(UCRMap,seqNames, ucNames);

					worker.probabilityAndReachableTest();

					mainFrame.renewPanel();

					worker.writeMarkov(Model_Name, mainFrame, seqNames, ucNames);

					if (isTime == false) {
						MarkovRoute = mainFrame.getBathRoute() + "/NoTimeMarkov/";
						File file = new File(MarkovRoute);
						if (file.exists()) {
							file.mkdirs();
						}
					} else {
						MarkovRoute = mainFrame.getBathRoute() + "/TimeMarkov/";
						File file = new File(MarkovRoute);
						if (file.exists()) {
							file.mkdirs();
						}
					}
					mainFrame.renewPanel();

					// 生成Markov链
					GraphFile graphFile = null;

					for (InterfaceIsogenySD key : IISDList) {
						for (InterfaceSD seq : key.getISDList()) {

							for (String seqName : seqNames) {
								if (seqName.replace(".xml", "").equals(seq.getName())) {
									toplabel.setText("正在生成" + key.getUcName() + "用例的" + seq.getName() + "场景Markov链....");
									seqName = seqName.replace(".xml", "");
									TianWriteToVioletMarkov tian = new TianWriteToVioletMarkov();
									tian.find(MarkovRoute + seqName + ".xml");

									tian.writeVioletMarkov(MarkovRoute + seqName + ".markov.violet.xml");
									
									testCaseXMLToStringList.exchangeUTF(MarkovRoute + seqName + ".markov.violet.xml");
									
									GraphFile fileGraphFile = MarkovXML2GraphFile.toGraphFile(MarkovRoute,
											seqName + ".markov.violet.xml");

									LayoutMarkov.layout(MarkovRoute, MarkovRoute + seqName + ".markov.violet.xml",
											seqName + "layout.markov.violet.xml",mainFrame);
									
									graphFile = MarkovXML2GraphFile.toGraphFile(MarkovRoute,
											seqName + "layout.markov.violet.xml");
									IWorkspace workspace = new Workspace(graphFile);
									
									workspace.getAWTComponent().getScrollableSideBar().setVisible(false);
									mainFrame.addTabbedPane(workspace, "step2");
									workspace.getAWTComponent().updateUI();

									mainFrame.getStepTwoExchangeTabbedPane().setSelectedIndex(0);
									Thread.sleep(1500);
									mainFrame.renewPanel();
								}
							}
						}

						toplabel.removeAll();
						toplabel.setText("正在生成" + key.getUcName() + "用例Markov链.....");
						String ucName = key.getUcName();
						TianWriteToVioletMarkov tian = new TianWriteToVioletMarkov();
						tian.find(MarkovRoute + ucName + ".xml");

						tian.writeVioletMarkov(MarkovRoute + ucName + ".markov.violet.xml");
						testCaseXMLToStringList.exchangeUTF(MarkovRoute + ucName + ".markov.violet.xml");
						GraphFile fileGraphFile = MarkovXML2GraphFile.toGraphFile(MarkovRoute,
								ucName + ".markov.violet.xml");
						LayoutMarkov.layout(MarkovRoute, MarkovRoute + ucName + ".markov.violet.xml",
								ucName + "layout.markov.violet.xml",mainFrame);
						graphFile = MarkovXML2GraphFile.toGraphFile(MarkovRoute, ucName + "layout.markov.violet.xml");

						IWorkspace workspace = new Workspace(graphFile);
						workspace.getAWTComponent().getScrollableSideBar().setVisible(false);
						mainFrame.addTabbedPane(workspace, "step2");
						workspace.getAWTComponent().updateUI();

						mainFrame.getStepTwoExchangeTabbedPane().setSelectedIndex(0);
						Thread.sleep(1500);
						mainFrame.renewPanel();
					}

					toplabel.removeAll();
					toplabel.setText("正在生成" + Model_Name + "模型Markov链.....");
					TianWriteToVioletMarkov tian = new TianWriteToVioletMarkov();
					tian.find(MarkovRoute + Model_Name + ".xml");
					tian.writeVioletMarkov(MarkovRoute + Model_Name + ".markov.violet.xml");
					testCaseXMLToStringList.exchangeUTF(MarkovRoute + Model_Name + ".markov.violet.xml");
					GraphFile fileGraphFile = MarkovXML2GraphFile.toGraphFile(MarkovRoute,
							Model_Name + ".markov.violet.xml");
					LayoutMarkov.layout(MarkovRoute, MarkovRoute + Model_Name + ".markov.violet.xml",
							Model_Name + "layout.markov.violet.xml",mainFrame);
					graphFile = MarkovXML2GraphFile.toGraphFile(MarkovRoute, Model_Name + "layout.markov.violet.xml");
					XMLPanel = XMLToTree.getTree(MarkovRoute + Model_Name + "layout.markov.violet.xml");

					IWorkspace workspace = new Workspace(graphFile);
					workspace.getAWTComponent().getScrollableSideBar().setVisible(false);
					mainFrame.addTabbedPane(workspace, "step2");
					
					workspace.getAWTComponent().updateUI();
					mainFrame.getStepTwoExchangeTabbedPane().setSelectedIndex(0);
					mainFrame.renewPanel();
					Thread.sleep(1500);

					toplabel.removeAll();
					toplabel.setText("正在获取Markov节点信息.....");
					Thread.sleep(100);


					// //添加节点信息
					mainFrame.getStepTwoCenterRightPanel().getNodeTextMap().clear(); // 首先清除map
					nodeTextMap = new HashMap<Object, String>();
					Collection<INode> nodes = graphFile.getGraph().getAllNodes();
					for (INode node : nodes) {
						if (node.getClass().getSimpleName().equals("MarkovNode")) {
							if (((MarkovNode) node).getName().toString() == null) {
								nodeTextMap.put(node.hashCode(), " ");
							} else {
								nodeTextMap.put(node.hashCode(), ((MarkovNode) node).getName());
							}
							((MarkovNode) node).setName("");
						}
						if (node.getClass().getSimpleName().equals("MarkovStartNode")) {
							if (((MarkovStartNode) node).getName().toString() == null) {
								nodeTextMap.put(node.hashCode(), " ");
							} else {
								nodeTextMap.put(node.hashCode(), ((MarkovStartNode) node).getName());
							}
							((MarkovStartNode) node).setName("");
						}
					}

					toplabel.removeAll();
					toplabel.setText("正在获取Markov迁移信息.....");
					Thread.sleep(100);

					// 修改边的信息
					mainFrame.getStepTwoCenterRightPanel().getEdgeTextMap().clear();
					edgeTextMap = new HashMap<Object, String>();
					Collection<IEdge> edges = graphFile.getGraph().getAllEdges();
					for (IEdge edge : edges) {
						if (edge.getClass().getSimpleName().equals("MarkovTransitionEdge")) {
							if (((MarkovTransitionEdge) edge).getPro().toString() == null) {
								edgeTextMap.put(edge.hashCode(), " ");
							} else {
								edgeTextMap.put(edge.hashCode(), ((MarkovTransitionEdge) edge).getPro().toString());
							}
							((MarkovTransitionEdge) edge).setPro("");
						}
					}

					
					toplabel.removeAll();
					toplabel.setText("正在获取Markov链XML信息.....");
					mainFrame.getStepTwoExchangeTabbedPane().getExchangeResport().removeAll();
					mainFrame.getStepTwoExchangeTabbedPane().getExchangeResport()
							.add(XMLToTree.getTree(MarkovRoute + Model_Name + "layout.markov.violet.xml"));
					mainFrame.getStepTwoExchangeTabbedPane().setSelectedIndex(1);
					mainFrame.renewPanel();

					
                    // 清除布局XML
					//清除用例以及场景Markov
					for (File file : new File(MarkovRoute).listFiles()) {
						if (file.getName().contains(".markov.violet.xml")) {
							file.delete();
						}
						
						for (String seqName : seqNames) {
							 if(file.getName().equals(seqName))
							 {
								 file.delete();
							 }
						}
						
						for(String ucName : ucNames)
						{
							if(file.getName().equals(ucName))
							 {
								 file.delete();
							 }
						}
					}
                    
					ExchangeNodeLabel nodeLabel = new ExchangeNodeLabel(Model_Name, mainFrame);
					nodeLabel.setWorkspace(workspace);
					nodeLabel.setXMLPanel(XMLPanel);
					nodeLabel.setNodeTextMap(nodeTextMap);
					nodeLabel.setEdgeTextMap(edgeTextMap);

					exchangeNodePanel.insertNodeLabel(nodeLabel);
					mainFrame.getStepTwoExpand().getExchangepPanel().repaint();

					startExchange.setEnabled(true);
					mainFrame.getStepTwoCenterRightPanel().setVisible(true);
					mainFrame.getStepTwoExpand().getEstimatepPanel().setVisible(true);
					mainFrame.getStepTwoExpand().getExchangepPanel().setVisible(true);
					mainFrame.getsteponeButton().getExpandCasePanel().setVisible(true);
					mainFrame.getsteponeButton().getExpandCaseModel().setVisible(true);
					
					mainFrame.getStepTwoExpand().getExchangeLabel().setEnabled(true);
					mainFrame.getsteponeButton().getExpandModelLabel().setEnabled(true);
					mainFrame.getStepTwoExpand().getEstimateLabel().setEnabled(true);
					mainFrame.getsteponeButton().getExpandCaseModel().setEnabled(true);
					

					mainFrame.getStepTwoModelOperation().getStartExpandButton().setEnabled(true);
					mainFrame.getStepTwoCaseOperation().getStartExpandButton().setEnabled(false);
					mainFrame.getStepTwoEvaluateOperation().getEvaluateButton().setEnabled(false);
					startExchange.setEnabled(false);
					restartExchange.setEnabled(false);

					mainFrame.getStepThreeLeftButton().setModelName(Model_Name);
					mainFrame.getStepThreeLeftButton().setNew(true);
					toplabel.removeAll();
					toplabel.setText("Markov转换成功,可以生成测试用例!");
					
					mainFrame.renewPanel();
					
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();

					toplabel.removeAll();
					toplabel.setText("生成Markov链出错!请重新扩展或检查UML模型!");
					
					startExchange.setEnabled(true);
					mainFrame.getStepTwoExpand().getEstimatepPanel().setVisible(true);
					mainFrame.getStepTwoExpand().getExchangepPanel().setVisible(true);
					mainFrame.getsteponeButton().getExpandCasePanel().setVisible(true);
					mainFrame.getsteponeButton().getExpandCaseModel().setVisible(true);
					
					mainFrame.getStepTwoExpand().getExchangeLabel().setEnabled(true);
					mainFrame.getsteponeButton().getExpandModelLabel().setEnabled(true);
					mainFrame.getStepTwoExpand().getEstimateLabel().setEnabled(true);
					mainFrame.getsteponeButton().getExpandCaseModel().setEnabled(true);
					mainFrame.getStepTwoCenterRightPanel().setVisible(true);

					mainFrame.getStepTwoModelOperation().getStartExpandButton().setEnabled(true);
					mainFrame.getStepTwoCaseOperation().getStartExpandButton().setEnabled(false);
					startExchange.setEnabled(false);
					restartExchange.setEnabled(false);

					mainFrame.getStepThreeLeftButton().setModelName(Model_Name);
					mainFrame.getStepThreeLeftButton().setNew(true);
					
					mainFrame.renewPanel();
				}

				return 1;
			}
		};
		task1 = new FutureTask<>(callable1);
		thread1 = new Thread(task1);
	}

	private void buttonListen() {
		startExchange.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				initThread();
				mainthread.start();
				thread1.start();
			}
		});
		restartExchange.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (!isAlive) {
					restartExchange.setText("停止转换");
					mainthread.resume();
					thread1.resume();
					isAlive = true;
				} else {
					restartExchange.setText("开始转换");
					mainthread.suspend();
					thread1.suspend();
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

	public Map<Object, String> getEdgeTextMap() {
		return edgeTextMap;
	}

	public Map<Object, String> getNodeTextMap() {
		return nodeTextMap;
	}

	public void setEdgeTextMap(Map<Object, String> edgeTextMap) {
		this.edgeTextMap = edgeTextMap;
	}

	public void setNodeTextMap(Map<Object, String> nodeTextMap) {
		this.nodeTextMap = nodeTextMap;
	}

}
