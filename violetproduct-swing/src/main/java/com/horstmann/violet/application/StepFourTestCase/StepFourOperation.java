package com.horstmann.violet.application.StepFourTestCase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.log4j.PropertyConfigurator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jfree.chart.ChartPanel;

import cn.edu.hdu.lab.config.StaticConfig;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceIsogenySD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceUCRelation;
import cn.edu.hdu.lab.service.interfaces.Work;

import com.horstmann.violet.application.StepTwoModelExpand.ExchangeNodeLabel;
import com.horstmann.violet.application.StepTwoModelExpand.GradientProgressBarUI;
import com.horstmann.violet.application.StepTwoModelExpand.ProgressUI;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.gui.util.chenzuo.Bean.Constants;
import com.horstmann.violet.application.gui.util.chenzuo.Bean.Pair;
import com.horstmann.violet.application.gui.util.chenzuo.Bean.TestCase;
import com.horstmann.violet.application.gui.util.chenzuo.Bean.myProcess;
import com.horstmann.violet.application.gui.util.chenzuo.Controller.Controller;
import com.horstmann.violet.application.gui.util.chenzuo.Service.ResultService;
import com.horstmann.violet.application.gui.util.tanchao.MarkovXML2GraphFile;
import com.horstmann.violet.application.gui.util.tanchao.TianWriteToVioletMarkov;
import com.horstmann.violet.application.gui.util.tanchao.markovlayout.LayoutMarkov;
import com.horstmann.violet.application.gui.util.tanchao.markovlayout.PathProp;
import com.horstmann.violet.application.menu.FileMenu;
import com.horstmann.violet.application.menu.util.UMLTransfrom.XMLUtils;
import com.horstmann.violet.chart.BarChart;
import com.horstmann.violet.chart.LineChart;
import com.horstmann.violet.chart.PieChart;
import com.horstmann.violet.framework.file.GraphFile;
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.Workspace;

public class StepFourOperation extends JPanel {
	private JLabel Exchangelabel;
	private JProgressBar ExchangeProgressBar;
	private JButton startExchange;
	private JButton restartExchange;
	private JPanel gapPanel;
	private TestCaseReportTableHeaderPanel testCaseReportTableHeaderPanel;
	private MainFrame mainFrame;
	private String type;
	private String modelName;
	private String route;
	private File selectFile;
	private DefaultTableModel attributetablemodel;

	private String type1 = "Coptermaster";
	private String type3 = "Time";

	private List<Integer> processID = new ArrayList<Integer>();
	private List<String> processName = new ArrayList<String>();
	private List<String> processParam = new ArrayList<String>();
	private List<String> processStatus = new ArrayList<String>();
	private List<Boolean> processExec = new ArrayList<Boolean>();
	private int i;

	private int progressBarIndex;
	private Callable<Integer> maincallable;
	private FutureTask<Integer> maintask;
	private Thread mainthread;
	private Callable<Integer> callable;
	private FutureTask<Integer> task;
	private Thread thread;

	
	public StepFourOperation(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.route = mainFrame.getBathRoute() + "/TestCase/";
		init();
		this.setLayout(new GridBagLayout());
		this.add(Exchangelabel, new GBC(0, 0, 3, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 15, 10, 0));
		this.add(ExchangeProgressBar, new GBC(0, 1, 3, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 15, 10, 15));
		this.add(startExchange, new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 15, 10, 0));
		this.add(gapPanel, new GBC(1, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 1).setInsets(0, 0, 10, 0));
		this.add(restartExchange, new GBC(2, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 0, 10, 15));
	}

	public void init() {
		Exchangelabel = new JLabel();
		Exchangelabel.setFont(new Font("宋体", Font.PLAIN, 16));
		Exchangelabel.setText("请选择需要测试用例的模型");

		ExchangeProgressBar = new JProgressBar();
		ExchangeProgressBar.setUI(new GradientProgressBarUI());
		ExchangeProgressBar.setPreferredSize(new Dimension(600, 30));

		startExchange = new JButton("开始验证");
		restartExchange = new JButton("结束验证");
		gapPanel = new JPanel();

		testCaseReportTableHeaderPanel = new TestCaseReportTableHeaderPanel();
		buttonListen();
	}

	private void buttonListen() {
		startExchange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// // TODO Auto-generated method stub
				initThread();
				mainthread.start();
				thread.start();
			}
		});

		restartExchange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mainthread.interrupt();
				thread.interrupt();

				startExchange.setEnabled(true);
				mainFrame.getStepFourTestCase().getjRadionPanel().setVisible(true);

				mainFrame.getStepFourTabbedPane().getTestCaseResults().removeAll();
				mainFrame.getStepFourTabbedPane().getTestCaseResport().removeAll();
				mainFrame.getStepFourTabbedPane().getWrongtestCaseResults().removeAll();
				
				File[] files = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\horstmann\\violet\\application\\gui\\util\\chenzuo\\File").listFiles();
				if(files != null)
				{
					for(File file : files)
					{
						file.delete();
					}
				}

				// 清除分割的xml
				File file1 = new File(route + modelName + "1#" + type + ".xml");
				File file2 = new File(route + modelName + "2#" + type + ".xml");
				if (file1.exists()) {
					file1.delete();
					file2.delete();
				}
				
				mainFrame.renewPanel();
			}
		});
	}

	private void initThread() {
		maincallable = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub

				progressBarIndex = 0;
				ExchangeProgressBar.setValue(0);
				ExchangeProgressBar.setValue(progressBarIndex);
				while (progressBarIndex < 100) {
					if (task.isDone()) {
						progressBarIndex++;
						ExchangeProgressBar.setValue(progressBarIndex);
						Thread.sleep(10);
					} else {
						progressBarIndex++;
						ExchangeProgressBar.setValue(progressBarIndex);
						Thread.sleep(3000);
					}
				}
				return 1;
			}
		};

		maintask = new FutureTask<>(maincallable);
		mainthread = new Thread(maintask);

		callable = new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				try {
					startExchange.setEnabled(false);
					mainFrame.getStepFourTestCase().getjRadionPanel().setVisible(false);

					mainFrame.getStepFourTabbedPane().getTestCaseResults().removeAll();
					mainFrame.getStepFourTabbedPane().getTestCaseResport().removeAll();
					mainFrame.getStepFourTabbedPane().getWrongtestCaseResults().removeAll();

					mainFrame.renewPanel();

					modelName = mainFrame.getNameRadionPanel().getSelectName();

					Exchangelabel.removeAll();
					Exchangelabel.setText("正在连接服务器....");
					Thread.sleep(100);
					if (modelName == null) {
						Exchangelabel.removeAll();
						Exchangelabel.setText("请选择需要执行的可靠性测试数据!");
						thread.interrupt();
						mainthread.interrupt();
						startExchange.setEnabled(true);
					}

					type = type1;
					if (modelName.contains("#3")) {
						type = type3;
					}

					PropertyConfigurator.configure("src/log4j.properties");

					File file = new File(route + modelName + ".xml");

					int length = getTestCaseSize(file);

					if (!Controller.Ready(2)) {
						Exchangelabel.removeAll();
						Exchangelabel.setText("服务器连接失败,请尝试重新连接!");

						thread.interrupt();
						mainthread.interrupt();
						startExchange.setEnabled(true);
					} else {
						Controller.Run(new Pair<String, File>(type, file));
						Exchangelabel.removeAll();
						Exchangelabel.setText("正在生成测试数据执行结果....");
						mainFrame.getStepFourTabbedPane().getTestCaseResults().removeAll();
						 ValidatePagePanel validatePagePanel = new ValidatePagePanel(mainFrame);
						 mainFrame.getStepFourTabbedPane().getTestCaseResults().add(validatePagePanel);
						 mainFrame.getStepFourTabbedPane().getTestCaseResults().repaint();

						 int size;
						 int index = 0;
						 int flag = 0;
						while (true) {
							try {
//								 size = ResultService.list.size();
//								
//								 List<TestCase> testcaselist =
//								 ResultService.list;
//								 if (index != size) {
//								 i = index;
//								 for (int j = index; j < size; j++) {
//								 TestCase testCase = testcaselist.get(j);
//								 testCase.setTestCaseID(String.valueOf(j+1));
//								 TestCaseMatrixPanel testCaseMatrixPanel = new
//								 TestCaseMatrixPanel();
//								 TestCaseTabelPanel titleCaseTabelPanel = new
//								 TestCaseTabelPanel(
//								 testCase.getTestCaseID(),
//								 testCase.getResult().getResultDetail(),
//								 testCase.getState());
//								//
//								 testCaseMatrixPanel.getTitleTabel().add(titleCaseTabelPanel);
//								//
//								 processID.clear();
//								 processName.clear();
//								 processParam.clear();
//								 processExec.clear();
//								 processStatus.clear();
//								 for (myProcess p : testCase.getProcessList())
//								 {
//								 processID.add(p.getProcessID());
//								 processName.add(p.getProcessName());
//								 processParam.add(p.getProcessParam());
//								 processStatus.add(p.getProcessStatus());
//								 processExec.add(p.isProcessExec());
//								 }
//								 TestCaseTabelPanel testCaseTabelPanel = new
//								 TestCaseTabelPanel(processID,
//								 processName, processParam, processStatus,
//								 processExec);
//								 testCaseMatrixPanel.getTabelPanel().add(testCaseTabelPanel,
//								 BorderLayout.CENTER);
//								
//								 mainFrame.getStepFourTabbedPane().getTestCaseResults().add(testCaseMatrixPanel,
//								 new GBC(0, i).setFill(GBC.BOTH).setWeight(1,
//								 0));
//								
//								 Exchangelabel.removeAll();
//								 Exchangelabel.setText("正在验证第" + (j+1)+
//								 "个测试用例....");
//								 i++;
//								 }
//								 Thread.sleep(10);
//								 mainFrame.renewPanel();
//								TimeUnit.SECONDS.sleep(2);
								
								//
								// }
//								 if(ResultService.list.size() > 500 && flag ==0)
//								 {
//								 List<TestCase> testcaselist = ResultService.list;
//								
//								 validatePagePanel.getValidatePanel().add(new
//								 JPanel(),new GBC(0,
//								 501).setFill(GBC.BOTH).setWeight(1, 1));
//								 for(i = 0; i < 500;i++)
//								 {
//								 TestCase testCase = testcaselist.get(i);
//								 testCase.setTestCaseID(String.valueOf(i+1));
//								 TestCaseMatrixPanel testCaseMatrixPanel = new
//								 TestCaseMatrixPanel();
//								 TestCaseTabelPanel titleCaseTabelPanel = new
//								 TestCaseTabelPanel(
//								 testCase.getTestCaseID(),
//								 testCase.getResult().getResultDetail(),
//								 testCase.getState());
//								
//								 testCaseMatrixPanel.getTitleTabel().add(titleCaseTabelPanel);
//								
//								 processID.clear();
//								 processName.clear();
//								 processParam.clear();
//								 processExec.clear();
//								 processStatus.clear();
//								 for (myProcess p : testCase.getProcessList())
//								 {
//								 processID.add(p.getProcessID());
//								 processName.add(p.getProcessName());
//								 processParam.add(p.getProcessParam());
//								 processStatus.add(p.getProcessStatus());
//								 processExec.add(p.isProcessExec());
//								 }
//								 TestCaseTabelPanel testCaseTabelPanel = new
//								 TestCaseTabelPanel(processID,
//								 processName, processParam, processStatus,
//								 processExec);
//								 testCaseMatrixPanel.getTabelPanel().add(testCaseTabelPanel,
//								 BorderLayout.CENTER);
//								
//								 validatePagePanel.getValidatePanel().add(testCaseMatrixPanel,
//								 new GBC(0, i).setFill(GBC.BOTH).setWeight(1,
//								 0));
//								 validatePagePanel.validate();
//								 Thread.sleep(10);
//								 mainFrame.renewPanel();
//								 }
//								 flag = 1;
//								 }
								 TimeUnit.SECONDS.sleep(2);
								 if(ResultService.list.size() == length){
								 break;
								 }
							}catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

						List<TestCase> testcaselist = ResultService.list;
						index = 500;
						if (testcaselist.size() < 500) {
							index = testcaselist.size();
						}
						
						mainFrame.getStepFourTabbedPane().getTestCaseResults().removeAll();
						ValidatePagePanel validatePagePanel1 = new ValidatePagePanel(mainFrame);
						mainFrame.getStepFourTabbedPane().getTestCaseResults().add(validatePagePanel1);
						mainFrame.getStepFourTabbedPane().getTestCaseResults().repaint();
//						index = testcaselist.size();
//						if(testcaselist.size() < 500)
//						{
							validatePagePanel1.getValidatePanel().add(new JPanel(),
									new GBC(0, index + 1).setFill(GBC.BOTH).setWeight(1, 1));
							for (i = 0; i < index; i++) {
								TestCase testCase = testcaselist.get(i);
								testCase.setTestCaseID(String.valueOf(i + 1));
								TestCaseMatrixPanel testCaseMatrixPanel = new TestCaseMatrixPanel(mainFrame);
								TestCaseTabelPanel titleCaseTabelPanel = new TestCaseTabelPanel(testCase.getTestCaseID(),
										testCase.getResult().getResultDetail(), testCase.getState());
								//
								testCaseMatrixPanel.getTitleTabel().add(titleCaseTabelPanel);
								//
								processID.clear();
								processName.clear();
								processParam.clear();
								processExec.clear();
								processStatus.clear();
								for (myProcess p : testCase.getProcessList()) {
									processID.add(p.getProcessID());
									processName.add(p.getProcessName());
									processParam.add(p.getProcessParam());
									processStatus.add(p.getProcessStatus());
									processExec.add(p.isProcessExec());
								}
								TestCaseTabelPanel testCaseTabelPanel = new TestCaseTabelPanel(processID, processName,
										processParam, processStatus, processExec);
								testCaseMatrixPanel.getTabelPanel().add(testCaseTabelPanel, BorderLayout.CENTER);

								validatePagePanel1.getValidatePanel().add(testCaseMatrixPanel,
										new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
								Thread.sleep(10);
								mainFrame.renewPanel();
							}
//						}
						validatePagePanel1.getPageTestField().setText("1");

						validatePagePanel1.setList(testcaselist);

						Exchangelabel.removeAll();
						Exchangelabel.setText("正在生成验证报告....");

						List<Integer> totalList = ResultService.getResults(testcaselist);
						// 统计成功失败
						FailReportTableHeaderPanel failReportTableHeaderPanel = new FailReportTableHeaderPanel();
						TestCaseTabelPanel testCaseTabelPanel = new TestCaseTabelPanel(totalList.get(0),
								totalList.get(1), totalList.get(2), mainFrame);
						CountMatrixPanel countMatrixPanel = new CountMatrixPanel(failReportTableHeaderPanel,
								testCaseTabelPanel);
						mainFrame.getStepFourTabbedPane().getTestCaseResport().add(countMatrixPanel,
								new GBC(0, 0, 2, 1).setFill(GBC.BOTH).setWeight(1, 0));
						mainFrame.renewPanel();

						// 统计失败类型
						List<Integer> failList = ResultService.getFailType(testcaselist);
						TypeFailReportTableHeaderPanel typeFailReportTableHeaderPanel = new TypeFailReportTableHeaderPanel();
						TestCaseTabelPanel failTypeTable = new TestCaseTabelPanel(failList.get(0), failList.get(1));
						CountMatrixPanel countFailMatrixPanel = new CountMatrixPanel(typeFailReportTableHeaderPanel,
								failTypeTable);
						mainFrame.getStepFourTabbedPane().getTestCaseResport().add(countFailMatrixPanel,
								new GBC(0, 1, 2, 1).setFill(GBC.BOTH).setWeight(1, 0));
						//
						Integer[] integers = { totalList.get(0), totalList.get(1) };
						ChartPanel chartPanel = createPiePanel(integers);
						ChartPanel barPanel = createLinePanel(integers);

						mainFrame.getStepFourTabbedPane().getTestCaseResport().add(chartPanel,
								new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 1));

						mainFrame.getStepFourTabbedPane().getTestCaseResport().add(barPanel,
								new GBC(1, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 1));

						mainFrame.renewPanel();
						
						// // 生成失败测试用例
						List<TestCase> failtestcases = ResultService.getFail(testcaselist);
						if (failtestcases.size() != 0) {
							ValidatePagePanel wrongPage = new ValidatePagePanel(mainFrame);
							mainFrame.getStepFourTabbedPane().getWrongtestCaseResults().add(wrongPage);
							mainFrame.getStepFourTabbedPane().getWrongtestCaseResults().repaint();
							i = 0;
							index = 500;
							if (failtestcases.size() < 500) {
								index = failtestcases.size();
							}
							
							wrongPage.getValidatePanel().add(new JPanel(),
									new GBC(0, index + 1).setFill(GBC.BOTH).setWeight(1, 1));

							for (int j = 0; j < index; j++) {
								TestCase testCase = failtestcases.get(j);
								TestCaseMatrixPanel testCaseMatrixPanel = new TestCaseMatrixPanel(mainFrame);
								TestCaseTabelPanel titleCaseTabelPanel = new TestCaseTabelPanel(
										testCase.getTestCaseID(), testCase.getResult().getResultDetail(),
										testCase.getState());

								testCaseMatrixPanel.getTitleTabel().add(titleCaseTabelPanel);
								processID.clear();
								processName.clear();
								processParam.clear();
								processExec.clear();
								processStatus.clear();
								for (myProcess p : testCase.getProcessList()) {
									processID.add(p.getProcessID());
									processName.add(p.getProcessName());
									processParam.add(p.getProcessParam());
									processStatus.add(p.getProcessStatus());
									processExec.add(p.isProcessExec());
								}
								TestCaseTabelPanel testCaseTabelPanel1 = new TestCaseTabelPanel(processID, processName,
										processParam, processStatus, processExec);
								testCaseMatrixPanel.getTabelPanel().add(testCaseTabelPanel1, BorderLayout.CENTER);

								wrongPage.getValidatePanel().add(testCaseMatrixPanel,
										new GBC(0, j).setFill(GBC.BOTH).setWeight(1, 0));
								Exchangelabel.removeAll();
								Exchangelabel.setText("正在生成失效的测试用例列表....");
								mainFrame.renewPanel();
								Thread.sleep(10);
								mainFrame.renewPanel();
							}

							wrongPage.getPageTestField().setText("1");
							// 生成失败的测试用例xml
							String failCasePath = mainFrame.getBathRoute() + "/FailTestCase/" + modelName + "_Fail.xml";
							FailTestCase.writeToXML(failCasePath, failtestcases);
						}

						// 清除分割的xml
						File file1 = new File(route + modelName + "1#" + type + ".xml");
						File file2 = new File(route + modelName + "2#" + type + ".xml");
						if (file1.exists()) {
							file1.delete();
							file2.delete();
						}

						Exchangelabel.removeAll();
						Exchangelabel.setText("验证报告生成完毕!");
						// mainFrame.getStepFourTabbedPane().getTestCaseResport().add(new
						// JPanel(),new
						// GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 1));
						mainFrame.getStepFourTabbedPane().setSelectedIndex(0);

						mainFrame.getStepFourTestCase().getjRadionPanel().setVisible(true);
						startExchange.setEnabled(true);
						mainFrame.renewPanel();
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();

					Exchangelabel.removeAll();
					Exchangelabel.setText("测试用例验证失败!");

					mainFrame.getStepFourTestCase().getjRadionPanel().setVisible(true);

					thread.interrupt();
					mainthread.interrupt();
				}
				return 1;
			}
		};

		task = new FutureTask<>(callable);
		thread = new Thread(task);

	}

	private ChartPanel createPiePanel(Integer[] integers) {
		PieChart pieChart = new PieChart(integers);
		pieChart.createDataset();
		return pieChart.createChart();
	}

	private ChartPanel createLinePanel(Integer[] integers) {
		BarChart barChart = new BarChart(integers);
		barChart.createDataset();
		return barChart.createChart();
	}

	private int getTestCaseSize(File path) {
		List<Element> lists = null;
		try {
			SAXReader saxReader = new SAXReader();
			Document document;

			document = saxReader.read(path);
			Element root = document.getRootElement();
			lists = root.elements("testcase");
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lists.size();
	}
}
