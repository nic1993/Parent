package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
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

import org.dom4j.DocumentException;

import com.horstmann.violet.application.StepTwoModelExpand.ExpandNodeLabel;
import com.horstmann.violet.application.StepTwoModelExpand.GradientProgressBarUI;
import com.horstmann.violet.application.StepTwoModelExpand.ProgressUI;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoMatrixPanel;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoTabelPanel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.gui.XMLToTree;
import com.horstmann.violet.application.gui.util.suna.TimeExtend.Arc;
import com.horstmann.violet.application.gui.util.suna.TimeExtend.ArcTimeToProb;

import com.horstmann.violet.application.gui.util.suna.TimeExtend.Model;
import com.horstmann.violet.application.gui.util.suna.TimeExtend.Parameter;
import com.horstmann.violet.application.gui.util.suna.TimeExtend.State;
import com.horstmann.violet.application.gui.util.suna.TimeExtend.StateTimeExtend;
import com.horstmann.violet.application.gui.util.suna.TimeExtend.Stimulate;
import com.horstmann.violet.application.gui.util.suna.TimeExtend.WriteToXML;
import com.horstmann.violet.application.gui.util.suna.TimeExtend.XMLParser;
import com.horstmann.violet.application.gui.util.tanchao.MarkovXML2GraphFile;
import com.horstmann.violet.application.gui.util.tanchao.TianWriteToVioletMarkov;
import com.horstmann.violet.application.gui.util.tanchao.markovlayout.LayoutMarkov;
import com.horstmann.violet.application.gui.util.tanchao.markovlayout.PathProp;

import com.horstmann.violet.application.menu.FileMenu;
import com.horstmann.violet.framework.file.GraphFile;
import com.horstmann.violet.product.diagram.abstracts.IColorable;
import com.horstmann.violet.product.diagram.abstracts.IEdgeColorable;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.markov.MarkovNode;
import com.horstmann.violet.product.diagram.markov.MarkovStartNode;
import com.horstmann.violet.product.diagram.markov.MarkovTransitionEdge;
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.Workspace;

public class TimeExpandOperation extends JPanel {

	private static String TimeMarkovRoute;
	private static String ExtendRoute;
	private JLabel expandlabel;
	private JProgressBar ExpandProgressBar;
	private JButton ModelExchange;
	private JButton restartExchange;
	private JPanel gapPanel;
	private MainFrame mainFrame;
	private String modelName;
	private File currentFile;

	private int step;
	private int stepSum = 4;
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
	private Callable<Integer> callable3;
	private FutureTask<Integer> task3;
	private Thread thread3;
	private Callable<Integer> callable4;
	private FutureTask<Integer> task4;
	private Thread thread4;
	private List<FutureTask<Integer>> futuretasklist;
	private List<Thread> threadlist;

	private List<String> stateNames = new ArrayList<String>();
	private List<String> stateLabels = new ArrayList<String>();

	private List<String> parameterName = new ArrayList<String>();
	private List<String> parameterDomainType = new ArrayList<String>();
	private List<String> parameterDomain = new ArrayList<String>();
	private int k;
	private DecimalFormat df = new DecimalFormat();
	private String pattern = "#0.000";

	private Model model;
	private GraphFile graphFile;
	private List<State> timeStateList;
	private List<Arc> timeArcList;

	private JScrollPane XMLPanel;
	private TimeExpandNodePanel timeExpandNodePanel;
	private ExpandNode expandNode;
	private boolean isAlive = true;

	private Map<Object, String> nodeTextMap;
	private Map<Object, String> edgeTextMap;

	// private List<Stimulate> stimuates = new ArrayList<Stimulate>();
	public TimeExpandOperation(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.modelName = mainFrame.getStepTwoModelOperation().getModel_name();
		this.TimeMarkovRoute = mainFrame.getBathRoute() + "/TimeMarkov/";
		this.ExtendRoute = mainFrame.getBathRoute() + "/ExtendMarkov/";
		init();
		this.setLayout(new GridBagLayout());
		this.add(expandlabel, new GBC(0, 0, 3, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 15, 10, 0));
		this.add(ExpandProgressBar, new GBC(0, 1, 3, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 15, 10, 15));
		this.add(ModelExchange, new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 15, 10, 0));
		this.add(gapPanel, new GBC(1, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 0, 10, 0));
		this.add(restartExchange, new GBC(2, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 0, 10, 15));
	}

	public void init() {
		timeExpandNodePanel = new TimeExpandNodePanel(mainFrame);
		df.applyPattern(pattern);
		;
		expandlabel = new JLabel("当前扩展的模型为:" + modelName);
		expandlabel.setFont(new Font("宋体", Font.PLAIN, 16));

		ExpandProgressBar = new JProgressBar();
		ExpandProgressBar.setUI(new ProgressUI(ExpandProgressBar, Color.green));
		ExpandProgressBar.setPreferredSize(new Dimension(600, 30));
		ExpandProgressBar.setUI(new GradientProgressBarUI());

		ModelExchange = new JButton("开始扩展");
		restartExchange = new JButton("停止扩展");
		restartExchange.setEnabled(false);
		gapPanel = new JPanel();
		buttonListen();
	}

	private void buttonListen() {
		ModelExchange.addActionListener(new ActionListener() {
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
					restartExchange.setText("停止扩展");
					mainthread.resume();
					thread1.resume();
					thread2.resume();
					thread3.resume();
					thread4.resume();
					isAlive = true;
				} else {
					restartExchange.setText("开始");
					mainthread.suspend();
					thread1.suspend();
					thread2.suspend();
					thread3.suspend();
					thread4.suspend();
					
					isAlive = false;
				}
				
			}
		});
	}

	private void initThread() {
		progressBarIndex = 0;
		ExpandProgressBar.setValue(0);
		step = 1;
		maincallable = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				while (progressBarIndex < 100) {

					if (progressBarIndex == (int) (25 * step)) {
						if (futuretasklist.get(step - 1).isDone()) {
							step++;
							progressBarIndex++;
							ExpandProgressBar.setValue(progressBarIndex);
							threadlist.get(step - 1).start();
						}
					} else {
						progressBarIndex++;
						ExpandProgressBar.setValue(progressBarIndex);
						Thread.sleep(10);
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
					ModelExchange.setEnabled(false);
					restartExchange.setEnabled(true);
					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(false);
					mainFrame.getStepThreeLeftButton().getTimeSeq().setEnabled(false);
					mainFrame.getStepThreeLeftButton().getTimeCase().setEnabled(false);

					mainFrame.getTimeExpandTabbedPane().getExpandResults().removeAll();
					mainFrame.getTimeExpandTabbedPane().getExpandResport().removeAll();
					
					mainFrame.getTimeExpandTabbedPane().repaint();
					mainFrame.renewPanel();

					progressBarIndex = 0;
					expandlabel.removeAll();
					expandlabel.setText("正在初始化" + modelName + "模型.....");
					Thread.sleep(200);
					File RootFile = new File(TimeMarkovRoute);
					File files[] = RootFile.listFiles();
					for (File file : files) {
						if (file.getName().equals(modelName + ".xml")) {
							currentFile = file;
						}
					}
					XMLParser xParser = new XMLParser(currentFile);
					StateTimeExtend sTimeExtend = new StateTimeExtend();
					expandlabel.removeAll();
					expandlabel.setText("正在扩展带有时间约束的节点.....");

					ArcTimeToProb arcTimeToProb = new ArcTimeToProb();
					Model extendModel = sTimeExtend.resolveStateTime(xParser.readXML());
					model = new Model();
					model = arcTimeToProb.resolveArcTime(extendModel);

					mainFrame.renewPanel();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					expandlabel.removeAll();
					expandlabel.setText("扩展Markov链出错!请检查模型!");

					ModelExchange.setEnabled(true);

					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);

					thread1.interrupt();
					thread2.interrupt();
					thread3.interrupt();
					thread4.interrupt();

					mainFrame.renewPanel();
				}
				return 1;
			}
		};
		task1 = new FutureTask<>(callable1);
		thread1 = new Thread(task1);

		callable2 = new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				try {
					expandlabel.removeAll();
					expandlabel.setText("正在生成扩展后的" + modelName + "模型.....");
					WriteToXML wtXML = new WriteToXML();
					wtXML.writeMarkov2XML(model, ExtendRoute + modelName + "_TimeExtend.xml");

					TianWriteToVioletMarkov tian = new TianWriteToVioletMarkov();
					tian.find(ExtendRoute + modelName + "_TimeExtend.xml");
					tian.writeVioletMarkov(ExtendRoute + modelName + "_TimeExtend.markov.violet.xml");
					GraphFile fileGraphFile = MarkovXML2GraphFile.toGraphFile(ExtendRoute,
							modelName + "_TimeExtend.markov.violet.xml");
					LayoutMarkov.layout(ExtendRoute, ExtendRoute + modelName + "_TimeExtend.markov.violet.xml",
							modelName + "_TimeExtendLayout.markov.violet.xml", mainFrame);

					graphFile = MarkovXML2GraphFile.toGraphFile(ExtendRoute,
							modelName + "_TimeExtendLayout.markov.violet.xml");

					mainFrame.renewPanel();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					expandlabel.removeAll();
					expandlabel.setText("扩展Markov链出错!请检查模型!");

					ModelExchange.setEnabled(true);

					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);

					mainthread.suspend();
					thread2.suspend();
					thread3.suspend();
					thread4.suspend();
					mainFrame.renewPanel();
				}
				return 1;
			}
		};
		task2 = new FutureTask<>(callable2);
		thread2 = new Thread(task2);

		callable3 = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				try {
					expandlabel.removeAll();
					expandlabel.setText("正在获取扩展的" + modelName + "模型信息.....");
					Thread.sleep(200);
					IWorkspace workspace = new Workspace(graphFile);

					Collection<IEdge> edges = workspace.getGraphFile().getGraph().getAllEdges();
					Collection<INode> nodes = workspace.getGraphFile().getGraph().getAllNodes();

					timeStateList = model.getTimeStateList();
					timeArcList = model.getTimeArcList();

					mainFrame.getStepTwoCenterRightPanel().getEdgeTextMap().clear();
					edgeTextMap = new HashMap<Object, String>();
					for (IEdge edge : edges) {
						for (Arc arc : timeArcList) {
							if (((MarkovTransitionEdge) edge).getPro().contains(arc.getName())) {
								IEdgeColorable edgeColorable = (IEdgeColorable) edge;
								edgeColorable.setEdgeColor(Color.red);
							}
						}

						for (State state : timeStateList) {
							for (Arc arc : state.getArcList()) {
								if (((MarkovTransitionEdge) edge).getPro().contains(arc.getName())) {
									IEdgeColorable edgeColorable = (IEdgeColorable) edge;
									edgeColorable.setEdgeColor(Color.red);
								}
							}
						}
					}
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

					mainFrame.getStepTwoCenterRightPanel().getNodeTextMap().clear(); // 首先清除map
					nodeTextMap = new HashMap<Object, String>();
					for (INode node : nodes) {
						for (State state : timeStateList) {
							if (node.getClass().getSimpleName().equals("MarkovNode")) {
								if (((MarkovNode) node).getName().contains(state.getName())) {
									IColorable colorableNode = (IColorable) node;
									colorableNode.setBackgroundColor(Color.RED);
									colorableNode.setBorderColor(Color.RED);
									colorableNode.setTextColor(Color.RED);
								}
							}
						}
					}

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

					expandlabel.removeAll();
					expandlabel.setText("正在生成扩展" + modelName + "Markov图形.....");
					mainFrame.addTabbedPane(workspace, "step3");
					workspace.getAWTComponent().getScrollableSideBar().setVisible(false);
					workspace.getAWTComponent().updateUI();

					mainFrame.getStepTwoCenterRightPanel().setNodeTextMap(nodeTextMap);
					mainFrame.getStepTwoCenterRightPanel().setEdgeTextMap(edgeTextMap);

					expandNode = new ExpandNode(modelName, mainFrame);
					expandNode.setWorkspace(workspace);
					expandNode.setNodeTextMap(nodeTextMap);
					expandNode.setEdgeTextMap(edgeTextMap);

					mainFrame.getStepThreeLeftButton().getTimeExpandNodePanel().insertNodeLabel(expandNode);
					mainFrame.renewPanel();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					expandlabel.removeAll();
					expandlabel.setText("扩展Markov链出错!请检查模型!");
					ModelExchange.setEnabled(true);

					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);

					mainthread.suspend();
					thread2.suspend();
					thread3.suspend();
					thread4.suspend();

					mainFrame.renewPanel();

				}
				return 1;
			}
		};
		task3 = new FutureTask<>(callable3);
		thread3 = new Thread(task3);

		callable4 = new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				try {

					expandlabel.removeAll();
					expandlabel.setText("正在生成转换XML信息.....");
					Thread.sleep(200);
					XMLPanel = XMLToTree.getTree(ExtendRoute + modelName + "_TimeExtendLayout.markov.violet.xml");
					expandNode.setXMLPanel(XMLPanel);
					mainFrame.getTimeExpandTabbedPane().getExpandResport().add(XMLPanel);

					// 清除布局XML
					for (File file : new File(ExtendRoute).listFiles()) {
						if (file.getName().contains(".markov.violet.xml")) {
							file.delete();
						}
					}

					expandlabel.removeAll();
					expandlabel.setText(modelName + "模型扩展完成，可以对扩展生成的Markov模型生成测试用例!");
					ModelExchange.setEnabled(true);
					restartExchange.setEnabled(false);
					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeSeq().setEnabled(true);
					mainFrame.getTimeSeqOperation().setModelName(modelName);
					mainFrame.getTimeSeqOperation1().setModelName(modelName);
					restartExchange.setText("停止扩展");
					mainFrame.renewPanel();

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();

					expandlabel.removeAll();
					expandlabel.setText("扩展Markov链出错!请检查模型!");

					ModelExchange.setEnabled(true);

					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);

					mainthread.suspend();
					thread4.suspend();
					mainFrame.renewPanel();

				}
				return 1;
			}
		};
		task4 = new FutureTask<>(callable4);
		thread4 = new Thread(task4);

		futuretasklist = new ArrayList<FutureTask<Integer>>();
		futuretasklist.add(task1);
		futuretasklist.add(task2);
		futuretasklist.add(task3);
		futuretasklist.add(task4);

		threadlist = new ArrayList<Thread>();
		threadlist.add(thread1);
		threadlist.add(thread2);
		threadlist.add(thread3);
		threadlist.add(thread4);
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public JLabel getExpandlabel() {
		return expandlabel;
	}

	public TimeExpandNodePanel getExpandNodePanel() {
		return timeExpandNodePanel;
	}

	public JButton getModelExchange() {
		return ModelExchange;
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
