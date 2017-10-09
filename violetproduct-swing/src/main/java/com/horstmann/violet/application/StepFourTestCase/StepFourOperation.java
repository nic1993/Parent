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
import javax.swing.JProgressBar;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.log4j.PropertyConfigurator;
import org.dom4j.DocumentException;
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
		restartExchange = new JButton("暂停验证");
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
						Thread.sleep(1000);
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
				mainFrame.getStepFourTabbedPane().getTestCaseResults().removeAll();
				
				modelName = mainFrame.getNameRadionPanel().getSelectName();
				Exchangelabel.removeAll();
				Exchangelabel.setText("正在验证" + modelName + "生成的测试数据....");
				
				
				String type = type1;
				if(modelName.contains("_TimeExtend"))
				{
					type = type3;
				}


				PropertyConfigurator.configure("src/log4j.properties");

				
				
				File file = new File(System.getProperty("user.dir") + "//" + modelName);
				Controller.Run(new Pair<String, File>(type, file));
				int size;
				while(true)
				{
					size = ResultService.list.size();
					if(size != 0) break;
				}
			    
				int index = 0;
				while (true) {
					try {
						size = ResultService.list.size();
						
						if (index != size) {
								List<TestCase> testcaselist = ResultService.list;

								i = index;
								for (int j = index; j < testcaselist.size(); j++) {
									TestCase testCase = testcaselist.get(j);
									TestCaseMatrixPanel testCaseMatrixPanel = new TestCaseMatrixPanel();
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
									TestCaseTabelPanel testCaseTabelPanel = new TestCaseTabelPanel(processID,
											processName, processParam, processStatus, processExec);
									testCaseMatrixPanel.getTabelPanel().add(testCaseTabelPanel, BorderLayout.CENTER);

									mainFrame.getStepFourTabbedPane().getTestCaseResults().add(testCaseMatrixPanel,
											new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
									Exchangelabel.removeAll();
									Exchangelabel.setText("正在验证第" + (j+1)+ "个测试用例....");
									progressBarIndex = ExchangeProgressBar.getValue();
									mainFrame.getStepFourTabbedPane().getjScrollPane().getVerticalScrollBar()
											.setValue(mainFrame.getStepFourTabbedPane().getjScrollPane()
													.getVerticalScrollBar().getMaximum());
									mainFrame.renewPanel();
									Thread.sleep(10);
									i++;
							}
							index = size;
							Thread.sleep(100);
						}
						else{
							break;
						}
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}
				List<TestCase> testcaselist = ResultService.list;

				// List<TestCase> testcaselist=new ArrayList<TestCase>();
				//
				// String serialpath =
				// "C:\\Users\\ccc\\Desktop\\平台xml\\UAVForXStreamV9+-+=+++++-1+border+path+performserialtestcase.txt";
				// FileInputStream fis = new FileInputStream(serialpath);
				// ObjectInputStream ois = new ObjectInputStream(fis);
				//
				// while(true){//使用处理异常的方式来判断文件是否结束
				// try {
				// TestCase tc=(TestCase) ois.readObject();//文件读取完毕后，会抛异常
				// testcaselist.add(tc);
				// } catch (Exception e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// System.out.println("文件读取完毕!");
				// break;
				// }
				// }
				//
				// ois.close();
				// fis.close();



//				i = 0;
//				mainFrame.getStepFourTabbedPane().getTestCaseResults().removeAll();
//				// mainFrame.getStepFourTabbedPane().getTestCaseResults().add(testCaseReportTableHeaderPanel,new
//				// GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
//
//				for (int j = 0; j < testcaselist.size(); j++) {
//					TestCase testCase = testcaselist.get(j);
//					TestCaseMatrixPanel testCaseMatrixPanel = new TestCaseMatrixPanel();
//					TestCaseTabelPanel titleCaseTabelPanel = new TestCaseTabelPanel(testCase.getTestCaseID(),
//							testCase.getState(), testCase.getResult().getResultDetail());
//
//					testCaseMatrixPanel.getTitleTabel().add(titleCaseTabelPanel);
//					processID.clear();
//					processName.clear();
//					processParam.clear();
//					processExec.clear();
//					processStatus.clear();
//					for (myProcess p : testCase.getProcessList()) {
//						processID.add(p.getProcessID());
//						processName.add(p.getProcessName());
//						processParam.add(p.getProcessParam());
//						processStatus.add(p.getProcessStatus());
//						processExec.add(p.isProcessExec());
//					}
//					TestCaseTabelPanel testCaseTabelPanel = new TestCaseTabelPanel(processID, processName, processParam,
//							processStatus, processExec);
//					testCaseMatrixPanel.getTabelPanel().add(testCaseTabelPanel, BorderLayout.CENTER);
//
//					mainFrame.getStepFourTabbedPane().getTestCaseResults().add(testCaseMatrixPanel,
//							new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
//					Exchangelabel.removeAll();
//					Exchangelabel.setText("正在验证第" + j + "个测试用例....");
//					ExchangeProgressBar.setValue(20 + (int) (((double) j / testcaselist.size()) * 50));
//					progressBarIndex = ExchangeProgressBar.getValue();
//					mainFrame.getStepFourTabbedPane().getjScrollPane().getVerticalScrollBar().setValue(
//							mainFrame.getStepFourTabbedPane().getjScrollPane().getVerticalScrollBar().getMaximum());
//					mainFrame.renewPanel();
//					Thread.sleep(100);
//					i++;
//				}
				mainFrame.getStepFourTabbedPane().getTestCaseResults().add(new JPanel(),
						new GBC(0, ++i).setFill(GBC.BOTH).setWeight(1, 1));
				Exchangelabel.removeAll();
				Exchangelabel.setText("正在生成验证报告....");

				List<Integer> totalList = ResultService.getResults(testcaselist);
				// 统计成功失败
				FailReportTableHeaderPanel failReportTableHeaderPanel = new FailReportTableHeaderPanel();
				TestCaseTabelPanel testCaseTabelPanel = new TestCaseTabelPanel(totalList.get(0), totalList.get(1),
						totalList.get(2), mainFrame);
				CountMatrixPanel countMatrixPanel = new CountMatrixPanel(failReportTableHeaderPanel,
						testCaseTabelPanel);
				mainFrame.getStepFourTabbedPane().getTestCaseResport().add(countMatrixPanel,
						new GBC(0, 0, 2, 1).setFill(GBC.BOTH).setWeight(1, 0));

				// 统计失败类型
				TypeFailReportTableHeaderPanel typeFailReportTableHeaderPanel = new TypeFailReportTableHeaderPanel();
				List<Integer> failList = ResultService.getFailType(testcaselist);
				TestCaseTabelPanel failTypeTable = new TestCaseTabelPanel(failList.get(0), failList.get(1));
				CountMatrixPanel countFailMatrixPanel = new CountMatrixPanel(typeFailReportTableHeaderPanel,
						failTypeTable);
				mainFrame.getStepFourTabbedPane().getTestCaseResport().add(countFailMatrixPanel,
						new GBC(0, 1, 2, 1).setFill(GBC.BOTH).setWeight(1, 0));

				// 生成失败测试用例
				List<TestCase> failtestcases = ResultService.getFail(testcaselist);
				if (failtestcases.size() != 0) {
					i = 0;
					mainFrame.getStepFourTabbedPane().getWrongtestCaseResults().removeAll();
					for (int j = 0; j < failtestcases.size(); j++) {
						TestCase testCase = failtestcases.get(j);
						TestCaseMatrixPanel testCaseMatrixPanel = new TestCaseMatrixPanel();
						TestCaseTabelPanel titleCaseTabelPanel = new TestCaseTabelPanel(testCase.getTestCaseID(),
								testCase.getResult().getResultDetail(), testCase.getState());
						// testCaseMatrixPanel.getTitleLabel().setText(testCase.getTestCaseID()+"
						// "+
						// testCase.getExeState()+" "+testCase.getState());
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

						mainFrame.getStepFourTabbedPane().getWrongtestCaseResults().add(testCaseMatrixPanel,
								new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
						Exchangelabel.removeAll();
						Exchangelabel.setText("正在验证第" + j + "个错误的测试用例....");
						progressBarIndex = ExchangeProgressBar.getValue();
						mainFrame.getStepFourTabbedPane().getjScrollPane3().getVerticalScrollBar().setValue(mainFrame
								.getStepFourTabbedPane().getjScrollPane3().getVerticalScrollBar().getMaximum());
						mainFrame.renewPanel();
						Thread.sleep(100);
						i++;
					}
				}

				Integer[] integers = { totalList.get(0), totalList.get(1) };
				ChartPanel chartPanel = createPiePanel(integers);
				ChartPanel barPanel = createLinePanel(integers);

				mainFrame.getStepFourTabbedPane().getTestCaseResport().add(chartPanel,
						new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 1));

				mainFrame.getStepFourTabbedPane().getTestCaseResport().add(barPanel,
						new GBC(1, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 1));
				Exchangelabel.removeAll();
				Exchangelabel.setText("验证报告生成完毕!");
				// mainFrame.getStepFourTabbedPane().getTestCaseResport().add(new
				// JPanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 1));
				mainFrame.getStepFourTabbedPane().setSelectedIndex(1);
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
}
