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
				mainFrame.getStepFourTabbedPane().getTestCaseResults().removeAll();
				
				modelName = mainFrame.getNameRadionPanel().getSelectName();
				Exchangelabel.removeAll();
				Exchangelabel.setText("正在连接服务器....");
				Thread.sleep(100);
				
				
				String type = type1;
				if(modelName.contains("#3"))
				{
					type = type3;
				}

				PropertyConfigurator.configure("src/log4j.properties");
				
				File file = new File(route + modelName+".xml");
				if(!Controller.Ready(1))
				{
					Exchangelabel.removeAll();
					Exchangelabel.setText("服务器连接失败,请尝试重新连接!");
					
					thread.interrupt();
					mainthread.interrupt();
					startExchange.setEnabled(true);
				}
				else {	
					Controller.Run(new Pair<String, File>(type, file));
					Exchangelabel.removeAll();
					Exchangelabel.setText("正在生成测试数据执行结果....");
					ValidatePagePanel validatePagePanel = new ValidatePagePanel(mainFrame);
					mainFrame.getStepFourTabbedPane().getTestCaseResults().add(validatePagePanel);
					
					
					int size;
					int index = 0;
					int flag = 0;
					while (true ) {
						try {
							size = ResultService.list.size();
							List<TestCase> testcaselist = ResultService.list;
							
//							if (index != size) {
//									i = index;
//									for (int j = index; j < size; j++) {
//										TestCase testCase = testcaselist.get(j);
//										testCase.setTestCaseID(String.valueOf(j+1));
//										TestCaseMatrixPanel testCaseMatrixPanel = new TestCaseMatrixPanel();
//										TestCaseTabelPanel titleCaseTabelPanel = new TestCaseTabelPanel(
//												testCase.getTestCaseID(), testCase.getResult().getResultDetail(),
//												testCase.getState());
//										
//										testCaseMatrixPanel.getTitleTabel().add(titleCaseTabelPanel);
//										
//										processID.clear();
//										processName.clear();
//										processParam.clear();
//										processExec.clear();
//										processStatus.clear();
//										for (myProcess p : testCase.getProcessList()) {
//											processID.add(p.getProcessID());
//											processName.add(p.getProcessName());
//											processParam.add(p.getProcessParam());
//											processStatus.add(p.getProcessStatus());
//											processExec.add(p.isProcessExec());
//										}
//										TestCaseTabelPanel testCaseTabelPanel = new TestCaseTabelPanel(processID,
//												processName, processParam, processStatus, processExec);
//										testCaseMatrixPanel.getTabelPanel().add(testCaseTabelPanel, BorderLayout.CENTER);
//
//										mainFrame.getStepFourTabbedPane().getTestCaseResults().add(testCaseMatrixPanel,
//												new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
//										
//										Exchangelabel.removeAll();
//										Exchangelabel.setText("正在验证第" + (j+1)+ "个测试用例....");
//										i++;
//								}
//								index = size;
//								
//								Thread.sleep(100);
//								mainFrame.renewPanel();
//								
//							}
							
							if(size > 500 && flag == 0)
							{
							validatePagePanel.getValidatePanel().add(new JPanel(),new GBC(0, 501).setFill(GBC.BOTH).setWeight(1, 1));
							   for(i = 0; i < 500;i++)
							   {
								   TestCase testCase = testcaselist.get(i);
									testCase.setTestCaseID(String.valueOf(i+1));
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

									validatePagePanel.getValidatePanel().add(testCaseMatrixPanel,
											new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
									Thread.sleep(10);
							   }
							   flag = 1;
							   validatePagePanel.getPageTestField().setText("1");
							}
							TimeUnit.SECONDS.sleep(2);
//							if(size == ResultService.list.size()) 
//							{
//								
//								break;
//							}
							if(Constants.ISFINISH.get()){
			                    break;
			                }
								
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
					List<TestCase> testcaselist = ResultService.list;
					if(testcaselist.size() < 500)
					{
						index = testcaselist.size();
						validatePagePanel.getValidatePanel().add(new JPanel(),new GBC(0, index + 1).setFill(GBC.BOTH).setWeight(1, 1));
					   for(i = 0; i < index;i++)
					   {
						   TestCase testCase = testcaselist.get(i);
							testCase.setTestCaseID(String.valueOf(i+1));
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

							validatePagePanel.getValidatePanel().add(testCaseMatrixPanel,
									new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
							Thread.sleep(10);
					   }
					   validatePagePanel.getPageTestField().setText("1");
					}
					
					validatePagePanel.setList(testcaselist);
					
					
					
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
					
//					// 生成失败测试用例
					List<TestCase> failtestcases = ResultService.getFail(testcaselist);
					
				
					mainFrame.getStepFourTabbedPane().getWrongtestCaseResults().add(new JPanel(),
							new GBC(0, failtestcases.size()).setFill(GBC.BOTH).setWeight(1, 1));
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
						
						//生成失败的测试用例xml
						String failCasePath = mainFrame.getBathRoute() + "/FailTestCase/" + modelName + "_Fail.xml";
						FailTestCase.writeToXML(failCasePath, failtestcases);
						
						
					}

					//清除分割的xml
					File file1 = new File(route + modelName+"_1#"+type+".xml");
					File file2 = new File(route + modelName+"_2#"+type+".xml");
					if(file1.exists())
					{
						file1.delete();
						file2.delete();
					}
					
					Exchangelabel.removeAll();
					Exchangelabel.setText("验证报告生成完毕!");
					// mainFrame.getStepFourTabbedPane().getTestCaseResport().add(new
					// JPanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 1));
					mainFrame.getStepFourTabbedPane().setSelectedIndex(0);
					
					startExchange.setEnabled(true);
				}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
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
}
