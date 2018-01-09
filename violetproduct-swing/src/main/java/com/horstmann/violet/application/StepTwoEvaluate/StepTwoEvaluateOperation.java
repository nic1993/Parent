package com.horstmann.violet.application.StepTwoEvaluate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import cn.edu.hdu.lab.dao.interfacedao.InterfaceIsogenySD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceSD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceUCRelation;
import cn.edu.hdu.lab.service.interfaces.Work;

import com.horstmann.violet.application.StepTwoModelExpand.ExchangeNodeLabel;
import com.horstmann.violet.application.StepTwoModelExpand.EvaluateNodeLabel;
import com.horstmann.violet.application.StepTwoModelExpand.ExpandNodeLabel;
import com.horstmann.violet.application.StepTwoModelExpand.GradientProgressBarUI;
import com.horstmann.violet.application.StepTwoModelExpand.ProgressUI;
import com.horstmann.violet.application.StepTwoModelExpand.ScenceTabelPanel;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoMatrixPanel;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoTabelPanel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class StepTwoEvaluateOperation extends JPanel {
	private JLabel topLabel;
	private JProgressBar evaluateBar;
	private JButton evaluateButton;
	private JButton reEvaluateButton;
	private JPanel gapPanel;

	private MainFrame mainFrame;
	private Work worker;
	private EvaluateNodePanel evaluateNodePanel;

	private List list;

	private List<String> sceneNameList;
	private List<String> constraintName;
	private List<String> constraintValue;
	private String Model_Name;

	private JTree stepTwoEvaluateTree;
	private DefaultTreeModel EvaluateTreeModel;
	private DefaultMutableTreeNode EvaluateTreerootnode;

	private ScenceTabelPanel reachableTabel;
	private List<RoutePanel> routePanels;

	private boolean ispolarity = false;
	private boolean isconfirm = false;
	private boolean isreachable = false;

	private int progressBarIndex;

	private Callable<Integer> maincallable;
	private FutureTask<Integer> maintask;
	private Thread mainthread;
	private Callable<Integer> callable1;
	private FutureTask<Integer> task1;
	private Thread thread1;
	private boolean isAlive = true;

	public StepTwoEvaluateOperation(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.stepTwoEvaluateTree = mainFrame.getStepTwoEvaluateTree().getjTree();
		this.EvaluateTreeModel = mainFrame.getStepTwoEvaluateTree().getEvaluateTreeModel();
		this.EvaluateTreerootnode = mainFrame.getStepTwoEvaluateTree().getEvaluateTreerootnode();
		init();
		this.setLayout(new GridBagLayout());
		this.add(topLabel, new GBC(0, 0, 3, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 15, 10, 0));
		this.add(evaluateBar, new GBC(0, 1, 3, 1).setFill(GBC.BOTH).setWeight(1, 1).setInsets(0, 15, 10, 15));
		this.add(evaluateButton, new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 15, 10, 0));
		this.add(gapPanel, new GBC(1, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 0, 10, 0));
		this.add(reEvaluateButton, new GBC(2, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 0, 10, 15));
	}

	private void init() {
		evaluateNodePanel = new EvaluateNodePanel(mainFrame);
		topLabel = new JLabel();
		topLabel.setFont(new Font("宋体", Font.PLAIN, 16));

		evaluateBar = new JProgressBar();
		evaluateBar.setUI(new ProgressUI(evaluateBar, Color.green));
		evaluateBar.setPreferredSize(new Dimension(800, 30));
		evaluateBar.setUI(new GradientProgressBarUI());

		evaluateButton = new JButton("开始验证");
		reEvaluateButton = new JButton("停止验证");
		reEvaluateButton.setEnabled(false);
		gapPanel = new JPanel();

		sceneNameList = new ArrayList<String>();
		constraintName = new ArrayList<String>();
		constraintValue = new ArrayList<String>();

		buttonListen();
	}

	private void initThread() {
		progressBarIndex = 0;
		evaluateBar.setValue(0);

		maincallable = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				progressBarIndex = 0;
				evaluateBar.setValue(0);
				while (progressBarIndex <= 100) {
					{
						if (task1.isDone()) {
							progressBarIndex++;
							evaluateBar.setValue(progressBarIndex);
							Thread.sleep(10);
						} else {
							progressBarIndex++;
							evaluateBar.setValue(progressBarIndex);
							Thread.sleep(100);
						}
					}
				}
				if (ispolarity == true && isconfirm == true && isreachable == true){
					evaluateButton.setEnabled(true);
					reEvaluateButton.setEnabled(false);

					mainFrame.getStepTwoModelOperation().getStartExpandButton().setEnabled(true);
					mainFrame.getStepTwoCaseOperation().getStartExpandButton().setEnabled(true);
					mainFrame.getStepTwoExchangeOperation().getStartExchange().setEnabled(true);

					mainFrame.getsteponeButton().getExpandModelLabel().setEnabled(true);
					mainFrame.getsteponeButton().getExpandCaseModel().setEnabled(true);
					mainFrame.getStepTwoExpand().getExchangeLabel().setEnabled(true);
					mainFrame.renewPanel();
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
				JPanel HomogeneityPanel = new JPanel();
				JPanel CertaintyPanel = new JPanel();
				JPanel AccessibilityPanel = new JPanel();
				HomogeneityPanel.setLayout(new GridBagLayout());
				CertaintyPanel.setLayout(new GridBagLayout());
				AccessibilityPanel.setLayout(new GridBagLayout());
				mainFrame.getsteponeButton().getExpandModelLabel().setEnabled(false);
				mainFrame.getsteponeButton().getExpandCaseModel().setEnabled(false);
				mainFrame.getStepTwoExpand().getExchangeLabel().setEnabled(false);

				mainFrame.getStepTwoModelOperation().getStartExpandButton().setEnabled(false);
				mainFrame.getStepTwoCaseOperation().getStartExpandButton().setEnabled(false);
				evaluateButton.setEnabled(false);
				reEvaluateButton.setEnabled(true);

				mainFrame.getStepTwoEvaluateTabbedPane().getHomogeneityResults().removeAll();
				mainFrame.getStepTwoEvaluateTabbedPane().getHomogeneityResults().updateUI();
				mainFrame.getStepTwoEvaluateTabbedPane().getCertaintyResults().removeAll();
				mainFrame.getStepTwoEvaluateTabbedPane().getCertaintyResults().updateUI();
				mainFrame.getStepTwoEvaluateTabbedPane().getAccessibilityResults().removeAll();
				mainFrame.getStepTwoEvaluateTabbedPane().getAccessibilityResults().updateUI();

				mainFrame.getStepTwoExchangeTabbedPane().getExchangeResport().removeAll();
				mainFrame.getStepTwoExchangeTabbedPane().getExchangeResults().removeAll();
				mainFrame.renewPanel();

				sceneNameList.clear();
				constraintName.clear();
				constraintValue.clear();

				topLabel.removeAll();
				topLabel.setText("开始验证归一性信息....");
				Thread.sleep(300);
				// 首先添加归一性信息
				worker = mainFrame.getStepTwoModelOperation().getWorker();
//				System.out.println("evalute ccode: " + worker.hashCode());

				list = worker.transVerify();

				StepTwoTabelPanel stepTwoTabelPanel = new StepTwoTabelPanel(mainFrame);
				stepTwoTabelPanel.getTitleLabel().setText(Model_Name + "用例模型后继用例迁移概率和为1.0以及所有场景的转移概率之和为1.0,归一性验证通过！");
				stepTwoTabelPanel.getTitleLabel().setFont(new Font("微软雅黑", Font.BOLD, 18));
				stepTwoTabelPanel.getTitlePanel().setBackground(new Color(211, 211, 211));
				stepTwoTabelPanel.getGapPanel().setBackground(new Color(211, 211, 211));

				topLabel.removeAll();
				topLabel.setText("正在验证用例中场景概率....");
				Thread.sleep(300);

				// 添加用例中场景迁移信息
				JPanel caseResultPanel = mainFrame.getStepTwoCaseOperation().getMatrixPanel(); // 用例场景概率
				StepTwoMatrixPanel stepTwoMatrixPanel = new StepTwoMatrixPanel(mainFrame);
				stepTwoMatrixPanel.getTabelPanel().add(caseResultPanel);
				stepTwoMatrixPanel.getTitleLabel().setText(Model_Name + "用例模型中场景发生概率之和为1");
				stepTwoMatrixPanel.getTitleLabel().setFont(new Font("微软雅黑", Font.BOLD, 17));

				topLabel.removeAll();
				topLabel.setText("正在验证用例模型中后继用例迁移概率....");
				Thread.sleep(300);

				JPanel relationResultPanel = mainFrame.getStepTwoModelOperation().getMatrixPanel(); // 用例迁移概率
				StepTwoMatrixPanel relationMatrixPanel = new StepTwoMatrixPanel(mainFrame);
				relationMatrixPanel.getTabelPanel().add(relationResultPanel);
				relationMatrixPanel.getTitleLabel().setText(Model_Name + "用例模型中后继用例迁移概率之和为1");
				relationMatrixPanel.getTitleLabel().setFont(new Font("微软雅黑", Font.BOLD, 17));
				ispolarity = true;
				stepTwoTabelPanel.getTabelPanel().add(stepTwoMatrixPanel,
						new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
				stepTwoTabelPanel.getTabelPanel().add(relationMatrixPanel,
						new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0));

				HomogeneityPanel.add(stepTwoTabelPanel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
				HomogeneityPanel.add(new JPanel(), new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 1));
				mainFrame.getStepTwoEvaluateTabbedPane().getHomogeneityResults().removeAll();
				mainFrame.getStepTwoEvaluateTabbedPane().getHomogeneityResults().add(HomogeneityPanel);
				mainFrame.renewPanel();

				topLabel.removeAll();
				topLabel.setText("开始验证确定性信息....");
				Thread.sleep(300);

				// 确定性信息
				List<InterfaceIsogenySD> IISDList = mainFrame.getStepTwoModelOperation().getIISDList();

				for (InterfaceIsogenySD interfaceIsogenySD : IISDList) {
					List<InterfaceSD> ISDList = interfaceIsogenySD.getISDList();
					for (InterfaceSD interfaceSD : ISDList) {
						topLabel.removeAll();
						topLabel.setText("正在验证 " + interfaceSD.getName() + "后置条件信息....");
						Thread.sleep(300);
						sceneNameList.add(interfaceSD.getName());
						constraintName.add("PostCondition");
						constraintValue.add(interfaceSD.getpostCondition());
					}
				}
				topLabel.removeAll();
				topLabel.setText("正在生成确定性信息.....");
				Thread.sleep(300);

				StepTwoTabelPanel confirmPanel = new StepTwoTabelPanel(mainFrame);
				confirmPanel.getTitleLabel().setText(Model_Name + "用例模型中所有场景后置条件唯一，确定性验证通过！");
				confirmPanel.getTitleLabel().setFont(new Font("微软雅黑", Font.BOLD, 18));
				confirmPanel.getTitlePanel().setBackground(new Color(211, 211, 211));
				confirmPanel.getGapPanel().setBackground(new Color(211, 211, 211));

				isconfirm = true;
				ScenceTabelPanel scenceTabelPanel = new ScenceTabelPanel(sceneNameList, constraintName, constraintValue,
						mainFrame);
				StepTwoMatrixPanel confirmMatrixPanel = new StepTwoMatrixPanel(mainFrame);
				confirmMatrixPanel.getTabelPanel().add(scenceTabelPanel);
				confirmMatrixPanel.getTitleLabel().setText("场景后置条件");
				confirmMatrixPanel.getTitleLabel().setFont(new Font("微软雅黑", Font.BOLD, 17));
				confirmPanel.getTabelPanel().add(confirmMatrixPanel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));

				CertaintyPanel.add(confirmPanel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
				CertaintyPanel.add(new JPanel(), new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 1));
				mainFrame.getStepTwoEvaluateTabbedPane().getCertaintyResults().removeAll();
				mainFrame.getStepTwoEvaluateTabbedPane().getCertaintyResults().add(CertaintyPanel);
				// mainFrame.getStepTwoEvaluateTabbedPane().getCertaintyResults().add(confirmPanel,new
				// GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
				// mainFrame.getStepTwoEvaluateTabbedPane().getCertaintyResults().add(new
				// JPanel(),new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 1));
				mainFrame.getStepTwoEvaluateTabbedPane().setSelectedIndex(1);
				mainFrame.renewPanel();

				topLabel.removeAll();
				topLabel.setText("开始验证可达性信息....");
				Thread.sleep(300);

				topLabel.removeAll();
				topLabel.setText("正在生成用例执行关系邻接矩阵....");
				Thread.sleep(300);

				// 可达性信息
				if ((boolean) list.get(0) == true) {
					isreachable = true;
					StepTwoTabelPanel reachablePanel = new StepTwoTabelPanel(mainFrame);
					reachablePanel.getTitleLabel().setText(Model_Name + "用例模型中每个用例都可以被执行到,可达性验证通过！");
					confirmPanel.getTitleLabel().setFont(new Font("微软雅黑", Font.BOLD, 18));
					reachablePanel.getTitlePanel().setBackground(new Color(211, 211, 211));
					reachablePanel.getGapPanel().setBackground(new Color(211, 211, 211));
					// 获取路径
					Map<?, ?> prePath = (Map<?, ?>) list.get(3);
					Map<?, ?> postPath = (Map<?, ?>) list.get(4);
					routePanels = new ArrayList<RoutePanel>();
					StepTwoMatrixPanel prePathPanel = getRoute(prePath, "初始状态到每个用例的路径");
					StepTwoMatrixPanel postPathPanel = getRoute(postPath, "每个用例到结束状态的路径");
					reachablePanel.getTabelPanel().add(prePathPanel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
					reachablePanel.getTabelPanel().add(postPathPanel, new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0));

					// 获取标题
					List<String> nameList = new ArrayList<String>();
					nameList.add(Model_Name); // 添加用例图模型名称
					nameList.add("Initial");
					for (InterfaceIsogenySD interfaceIsogenySD : IISDList) {
						nameList.add(interfaceIsogenySD.getUcName());
					}
					nameList.add("Exit");

					reachableTabel = new ScenceTabelPanel(nameList, (int[][]) list.get(2));
					StepTwoMatrixPanel reachableMatrixPanel = new StepTwoMatrixPanel(mainFrame);
					reachableMatrixPanel.getTabelPanel().add(reachableTabel);
					reachableMatrixPanel.getTitleLabel().setText("用例执行关系邻接矩阵");
					reachableMatrixPanel.getTitleLabel().setFont(new Font("微软雅黑", Font.BOLD, 17));
					reachablePanel.getTabelPanel().add(reachableMatrixPanel,
							new GBC(0, 2).setFill(GBC.BOTH).setWeight(1, 0));

					AccessibilityPanel.add(reachablePanel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
					AccessibilityPanel.add(new JPanel(), new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 1));

					mainFrame.getStepTwoEvaluateTabbedPane().getAccessibilityResults().removeAll();
					mainFrame.getStepTwoEvaluateTabbedPane().getAccessibilityResults().add(AccessibilityPanel);
					mainFrame.getStepTwoEvaluateTabbedPane().setSelectedIndex(2);
					mainFrame.renewPanel();
				}
				if (isreachable == false) {
					topLabel.removeAll();
					topLabel.setText(Model_Name + "用例模型不满足一致性要求，请重新扩展！");
					evaluateButton.setEnabled(true);
					reEvaluateButton.setEnabled(false);
					mainFrame.getsteponeButton().getExpandModelLabel().setEnabled(true);
					mainFrame.getsteponeButton().getExpandCaseModel().setEnabled(true);

					mainFrame.getStepTwoModelOperation().getStartExpandButton().setEnabled(true);
					mainFrame.getStepTwoCaseOperation().getStartExpandButton().setEnabled(true);

					list.clear();
				} else if (ispolarity == true && isconfirm == true && isreachable == true) {
					topLabel.removeAll();

					topLabel.setText(Model_Name + "用例模型满足一致性要求！可以转换为Markov模型");
					EvaluateNodeLabel evaluateNodeLabel = new EvaluateNodeLabel(Model_Name, mainFrame);
					evaluateNodeLabel.setQuota(Model_Name + "用例模型满足一致性要求！可以转换为Markov模型");
					evaluateNodePanel.insertNodeLabel(evaluateNodeLabel, HomogeneityPanel, CertaintyPanel,
							AccessibilityPanel);
					mainFrame.getStepTwoExpand().getEstimatepPanel().repaint();
					mainFrame.renewPanel();

					list.clear();
				}
				return 1;
			}
		};
		task1 = new FutureTask<>(callable1);
		thread1 = new Thread(task1);
	}

	private void buttonListen() {
		evaluateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				initThread();
				mainthread.start();
				thread1.start();
			}
		});
		reEvaluateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (!isAlive) {
					reEvaluateButton.setText("停止验证");
					mainthread.resume();
					thread1.resume();
					isAlive = true;
				} else {
					reEvaluateButton.setText("开始验证");
					mainthread.suspend();
					thread1.suspend();
					isAlive = false;
				}
			}
		});
	}

	// 添加路径
	public StepTwoMatrixPanel getRoute(Map<?, ?> prePath, String title) {
		StepTwoMatrixPanel stepTwoTabelPanel = new StepTwoMatrixPanel(mainFrame);
		stepTwoTabelPanel.getTitleLabel().setText(title);
		stepTwoTabelPanel.getTitleLabel().setFont(new Font("微软雅黑", Font.BOLD, 17));
		stepTwoTabelPanel.getTabelPanel().setLayout(new GridBagLayout());
		int i = 0;
		for (Object en : prePath.keySet()) {
			StepTwoMatrixPanel stepTwoMatrixPanel = new StepTwoMatrixPanel(mainFrame);

			stepTwoMatrixPanel.getTitleLabel().setText(en.toString());

			RoutePanel routePanel = new RoutePanel(mainFrame);

			StringBuilder str = new StringBuilder();
			String[] path = prePath.get(en).toString().replace("[", "").replace("]", "").split(",");
			for (int j = 0; j < path.length; j++) {
				if (j != path.length - 1) {
					str.append(path[j]).append("-->");
				} else {
					str.append(path[j]);
				}
			}
			routePanel.getRoute().setText(str.toString());

			stepTwoMatrixPanel.getTabelPanel().add(routePanel);
			stepTwoMatrixPanel.getTabelPanel().setBackground(Color.white);

			stepTwoTabelPanel.getTabelPanel().add(stepTwoMatrixPanel, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
			i++;

			routePanels.add(routePanel);
		}
		return stepTwoTabelPanel;
	}

	public String getModel_Name() {
		return Model_Name;
	}

	public void setModel_Name(String model_Name) {
		Model_Name = model_Name;
	}

	public JLabel getTopLabel() {
		return topLabel;
	}

	public EvaluateNodePanel getEvaluateNodePanel() {
		return evaluateNodePanel;
	}

	public JButton getEvaluateButton() {
		return evaluateButton;
	}

	public ScenceTabelPanel getReachableTabel() {
		return reachableTabel;
	}

	public List<RoutePanel> getRoutePanels() {
		return routePanels;
	}
}
