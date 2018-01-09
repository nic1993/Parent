package com.horstmann.violet.application.StepFourTestCase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
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
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
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
import com.horstmann.violet.application.gui.util.yangjie.HibernateUtils;
import com.horstmann.violet.application.menu.FileMenu;
import com.horstmann.violet.application.menu.util.UMLTransfrom.XMLUtils;
import com.horstmann.violet.chart.BarChart;
import com.horstmann.violet.chart.LineChart;
import com.horstmann.violet.chart.PieChart;
import com.horstmann.violet.framework.file.GraphFile;
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.Workspace;

public class StepFourOperation extends JPanel {
	public static int length;

	private JLabel Exchangelabel;
	private JLabel fileLabel;
	private JProgressBar ExchangeProgressBar;
	private JButton startExchange;
	private JButton FileButton;
	private JButton TestCaseButton;
	private JPanel gapPanel;
	private TestCaseReportTableHeaderPanel testCaseReportTableHeaderPanel;
	private MainFrame mainFrame;
	private String type;
	private String modelName;
	private String route;
	private String chooseRoute;
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
	private Callable<Integer> callable1;
	private FutureTask<Integer> task1;
	private Thread thread1;
	private Callable<Integer> callable2;
	private FutureTask<Integer> task2;
	private Thread thread2;

	private File file;
	private boolean close;
	private List<TestCase> XMLToTestCaseList;
	private List<SelectBox> selectBoxs;
	private List<TestCasePanel> TestCasePanels;
	
	private List<TestCase> testcaselist;

	public StepFourOperation(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.route = mainFrame.getBathRoute() + "/TestCase/";
		this.chooseRoute = mainFrame.getBathRoute() + "/ChooseTestCase/";
		init();
		this.setLayout(new GridBagLayout());
		this.add(Exchangelabel, new GBC(0, 0, 5, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 15, 10, 0));
		this.add(ExchangeProgressBar, new GBC(0, 1, 5, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 15, 10, 15));
		this.add(startExchange, new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 15, 10, 0));
		this.add(FileButton, new GBC(1, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 15, 10, 0));
		this.add(fileLabel, new GBC(2, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 15, 10, 0));
		this.add(gapPanel, new GBC(3, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 1).setInsets(0, 0, 10, 0));
		this.add(TestCaseButton, new GBC(4, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 0, 10, 15));
	}

	public void init() {
		Exchangelabel = new JLabel();
		Exchangelabel.setFont(new Font("宋体", Font.PLAIN, 16));
		Exchangelabel.setText("请选择需要执行的可靠性测试数据的模型");

		ExchangeProgressBar = new JProgressBar();
		ExchangeProgressBar.setUI(new GradientProgressBarUI());
		ExchangeProgressBar.setPreferredSize(new Dimension(600, 30));

		startExchange = new JButton("开始执行");                 
		gapPanel = new JPanel();

		testCaseReportTableHeaderPanel = new TestCaseReportTableHeaderPanel();

		FileButton = new JButton("选择文件");
		fileLabel = new JLabel();
		fileLabel.setFont(new Font("宋体", Font.PLAIN, 16));

		TestCaseButton = new JButton("读取数据");
		selectBoxs = new ArrayList<SelectBox>();
		TestCasePanels = new ArrayList<TestCasePanel>();
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

		FileButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				JFileChooser jFileChooser = new JFileChooser();
				int i = jFileChooser.showSaveDialog(null);
				if (i == jFileChooser.APPROVE_OPTION) { // 打开文件
					file = jFileChooser.getSelectedFile();
					fileLabel.setText(file.getAbsolutePath());
				}
				
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
		});
		
		TestCaseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mainFrame.getStepFourTabbedPane().getTestCaseinformation().removeAll();
				selectBoxs.clear();
				TestCasePanels.clear();
                mainFrame.renewPanel();
				
				modelName = mainFrame.getNameRadionPanel().getSelectName();
				if (modelName == null) {
					Exchangelabel.removeAll();
					Exchangelabel.setText("请选择需要读取的可靠性测试数据!");
					mainFrame.renewPanel(); 
				} else {
					File file = new File(route + modelName + ".xml");
					if (!file.exists()) {
						Exchangelabel.removeAll();
						Exchangelabel.setText("读取可靠性测试数据出错!");
						mainFrame.renewPanel();
					} else {
						extractFunctionalTestDataFromXml(file);
						if (XMLToTestCaseList.size() != 0) {
							TestCasePanels = CreateTestCaseTable(XMLToTestCaseList);
							TestCasePagePanel testCasePagePanel = new TestCasePagePanel(mainFrame, selectBoxs);
							int index = 500;
							if (TestCasePanels.size() < 500) {
								index = TestCasePanels.size();
							}
							testCasePagePanel.getTestCasePanel().add(new JPanel(),
									new GBC(0, index).setFill(GBC.BOTH).setWeight(1, 1));
							for (int i = 0; i < index; i++) {
								testCasePagePanel.getTestCasePanel().add(TestCasePanels.get(i),
										new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
							}
							testCasePagePanel.setTestCasePanels(TestCasePanels);
							testCasePagePanel.getPageTestField().setText("1");
							mainFrame.getStepFourTabbedPane().getTestCaseinformation().add(testCasePagePanel);
							mainFrame.getStepFourTabbedPane().getTestCaseinformation().repaint();
							
							Exchangelabel.removeAll();
							Exchangelabel.setText("可靠性测试数据共" + TestCasePanels.size() + "条!");
							mainFrame.renewPanel();
						} else {
							Exchangelabel.removeAll();
							Exchangelabel.setText("可靠性测试数据为空!");
						}
					}
					mainFrame.renewPanel();
				}
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
				while (progressBarIndex < 60) {
					if (task.isDone()) {
						progressBarIndex++;
						ExchangeProgressBar.setValue(progressBarIndex);
						Thread.sleep(50);
					} else {
						progressBarIndex++;
						ExchangeProgressBar.setValue(progressBarIndex);
						Thread.sleep(5000);	
					}
				}
				while (true) {
					 if(task.isDone())
		                {
		                	thread1.start();
		                	break;
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
					while (true) {
						File[] files = new File(System.getProperty("user.dir")
								+ "\\src\\main\\java\\com\\horstmann\\violet\\application\\gui\\util\\chenzuo\\File")
										.listFiles();
						if (files.length != 0) {
							for (File file : files) {
								file.delete();
							}
						}
						else {
							break;
						}
					}
					// 生成需要验证的可靠性测试数据xml
					if (getCurrentTestCase() == null || getCurrentTestCase().size() == 0 || modelName == null) {
						ExceptionStopRunThread();
						Exchangelabel.removeAll();
						Exchangelabel.setText("请选择需要执行的可靠性测试数据!");
					} else if (file == null) {
						ExceptionStopRunThread();
						Exchangelabel.removeAll();
						Exchangelabel.setText("请选择验证的可执行文件!");
					} else {
						extractDataToXml(chooseRoute + modelName + ".xml", getCurrentTestCase());
						
						File Casefile = new File(chooseRoute + modelName + ".xml");

						startExchange.setEnabled(false);
						FileButton.setEnabled(false);
						TestCaseButton.setEnabled(false);
						mainFrame.getStepFourTestCase().getjRadionPanel().setVisible(false);

						mainFrame.getStepFourTabbedPane().getTestCaseResults().removeAll();
						mainFrame.getStepFourTabbedPane().getTestCaseResport().removeAll();
						mainFrame.getStepFourTabbedPane().getWrongtestCaseResults().removeAll();

						mainFrame.renewPanel();

						
						Thread.sleep(100);
						if (!Casefile.exists()) {
							mainFrame.getStepFourTestCase().getjRadionPanel().setVisible(true);
							thread.interrupt();
							mainthread.interrupt();
							startExchange.setEnabled(true);
							FileButton.setEnabled(true);
							TestCaseButton.setEnabled(true);
							Exchangelabel.removeAll();
							Exchangelabel.setText("请选择需要执行的可靠性测试数据!");
							mainFrame.renewPanel();
						} else {
							type = type1;
							if (modelName.contains("#3")) {
								type = type3;
							}

							PropertyConfigurator.configure("src/log4j.properties");

							File file = new File(route + modelName + ".xml");

							length = getTestCaseSize(Casefile);

							Exchangelabel.removeAll();
							Exchangelabel.setText("正在连接服务器....");
							
							if (!Controller.Ready(2)) {
								Exchangelabel.removeAll();
								Exchangelabel.setText("服务器连接失败,请稍等片刻再尝试重新连接!");

								thread.interrupt();
								mainthread.interrupt();
								startExchange.setEnabled(true);
								FileButton.setEnabled(true);
								TestCaseButton.setEnabled(true);
								mainFrame.renewPanel();
							} else {
								Exchangelabel.removeAll();
								Exchangelabel.setText("服务器连接成功,发送可靠性测试数据....");
								Thread.sleep(400);
								Controller.Run(new Pair<String, File>(type, Casefile), mainFrame);
								Exchangelabel.removeAll();
								Exchangelabel.setText("正在验证可靠性测试数据,请耐心等待....");
								
								mainFrame.getStepFourTabbedPane().getTestCaseResults().removeAll();
								ValidatePagePanel validatePagePanel = new ValidatePagePanel(mainFrame);
								mainFrame.getStepFourTabbedPane().getTestCaseResults().add(validatePagePanel);
								mainFrame.getStepFourTabbedPane().getTestCaseResults().repaint();

								mainFrame.renewPanel();

								int size;
								int index = 0;
								int flag = 0;
								while (true) {
									try {
										try {
											if (Controller.handFutureList.size() > 0) {
												for (int i = 0; i < Controller.handFutureList.size(); i++) {
													if (Controller.handFutureList.get(i).isDone()) {
														try {
															Controller.handFutureList.get(i).get();
														} catch (ExecutionException e) {
															// TODO
															// Auto-generated
															e.printStackTrace();
															if (e.getMessage().contains("TestCaseException")) {
																ExceptionStopRunThread();
																Exchangelabel.removeAll();
																Exchangelabel.setText("可靠性测试数据执行出错,请稍等片刻再次执行!");
																flag = 1;
																mainFrame.renewPanel();
																break;
															}
														}
													}
												}
											}
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
										TimeUnit.SECONDS.sleep(2);
										
										if (ResultService.list.size() == length) {
											break;
										}
										
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}

								// 判断返回可靠性测试数据个数
								if (ResultService.list.size() != length) {
									ExceptionStopRunThread();
									Exchangelabel.removeAll();
									Exchangelabel.setText("可靠性测试数据执行出错,请稍等片刻再次执行!....");
									mainFrame.renewPanel();
								} else {
									Exchangelabel.removeAll();
									Exchangelabel.setText("正在生成可靠性测试数据执行结果....");
									testcaselist = ResultService.list;
									//进行编号
									for (i = 0; i < testcaselist.size(); i++) {
										TestCase testCase = testcaselist.get(i);
										testCase.setTestCaseID(getCurrentTestCase().get(i).getTestCaseID());
									}
									
									index = 500;
									if (testcaselist.size() < 500) {
										index = testcaselist.size();
									}

									mainFrame.getStepFourTabbedPane().getTestCaseResults().removeAll();
									ValidatePagePanel validatePagePanel1 = new ValidatePagePanel(mainFrame);
									mainFrame.getStepFourTabbedPane().getTestCaseResults().add(validatePagePanel1);
									mainFrame.getStepFourTabbedPane().getTestCaseResults().repaint();
									
									validatePagePanel1.getValidatePanel().add(new JPanel(),
											new GBC(0, index + 1).setFill(GBC.BOTH).setWeight(1, 1));
									List<TestCaseMatrixPanel> testCaseMatrixPanels = new ArrayList<TestCaseMatrixPanel>();
									for (i = 0; i < index; i++) {
										TestCase testCase = testcaselist.get(i);
//										testCase.setTestCaseID(getCurrentTestCase().get(i).getTestCaseID());
										TestCaseMatrixPanel testCaseMatrixPanel = new TestCaseMatrixPanel(mainFrame);
	
										String str1 = testCase.getState().split("ms")[0] + "ms";
										
										
										String str2 = testCase.getState().split("ms")[1];
										TestCaseTabelPanel titleCaseTabelPanel = new TestCaseTabelPanel(
												testCase.getTestCaseID(), testCase.getResult(),
												testCase.getExeTime(),0);
										if(type .equals(type3))
										{
											if(testCase.getResult().contains("有误"))
												testCaseMatrixPanel.getPredict().setForeground(Color.red);
											
											testCaseMatrixPanel.getPredict().setText("被测程序执行结果:电梯停靠在" + str2 + "层");
										}
										else {
											if (str2.equals("1")) {
												testCaseMatrixPanel.getPredict().setText("被测程序执行结果:无人机成功返航,并完成加锁");
											}
											else {
												testCaseMatrixPanel.getPredict().setText("被测程序执行结果:无人机运行异常");
												testCaseMatrixPanel.getPredict().setForeground(Color.red);
											}
											
										}
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
										TestCaseTabelPanel testCaseTabelPanel = new TestCaseTabelPanel(processID,
												processName, processParam, processStatus, processExec);
										testCaseMatrixPanel.getTabelPanel().add(testCaseTabelPanel,
												BorderLayout.CENTER);

										validatePagePanel1.getValidatePanel().add(testCaseMatrixPanel,
												new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
										Thread.sleep(10);
										mainFrame.renewPanel();
										testCaseMatrixPanels.add(testCaseMatrixPanel);
									}
									
									validatePagePanel1.getPageTestField().setText("1");
									validatePagePanel1.setLists(testCaseMatrixPanels);
									validatePagePanel1.setList(testcaselist);
									validatePagePanel1.setType(type);
									
									Exchangelabel.removeAll();
									Exchangelabel.setText("正在生成可靠性测试数据执行报告....");
									

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
//									List<Integer> failList = ResultService.getFailType(testcaselist);
//									TypeFailReportTableHeaderPanel typeFailReportTableHeaderPanel = new TypeFailReportTableHeaderPanel();
//									TestCaseTabelPanel failTypeTable = new TestCaseTabelPanel(failList.get(0),
//											failList.get(1));
//									CountMatrixPanel countFailMatrixPanel = new CountMatrixPanel(
//											typeFailReportTableHeaderPanel, failTypeTable);
//									mainFrame.getStepFourTabbedPane().getTestCaseResport().add(countFailMatrixPanel,
//											new GBC(0, 1, 2, 1).setFill(GBC.BOTH).setWeight(1, 0));
									//
							
									  
									Integer[] integers = { totalList.get(0), totalList.get(1) };
									ChartPanel chartPanel = createPiePanel(integers);
									ChartPanel barPanel = createLinePanel(integers);
									
									chartPanel.setMaximumSize(new Dimension(200, 200));
									chartPanel.setMaximumSize(new Dimension(200, 200));
									chartPanel.setPreferredSize(new Dimension(200, 200));
									barPanel.setMaximumSize(new Dimension(200, 200));
									barPanel.setMinimumSize(new Dimension(200, 200));
									barPanel.setPreferredSize(new Dimension(200, 200));

									mainFrame.getStepFourTabbedPane().getTestCaseResport().add(chartPanel,
											new GBC(0, 1, 1, 1).setFill(GBC.BOTH).setWeight(1, 0.9));

									mainFrame.getStepFourTabbedPane().getTestCaseResport().add(barPanel,
											new GBC(1, 1, 1, 1).setFill(GBC.BOTH).setWeight(1, 0.9));

									mainFrame.renewPanel();
								}
							}
						}
					}

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					Exchangelabel.removeAll();
					Exchangelabel.setText("可靠性测试数据执行失败!");

					mainFrame.getStepFourTestCase().getjRadionPanel().setVisible(true);
					startExchange.setEnabled(true);
					FileButton.setEnabled(true);
					TestCaseButton.setEnabled(true);
					
					thread.interrupt();
					mainthread.interrupt();
					
					File[] files = new File(System.getProperty("user.dir")
							+ "\\src\\main\\java\\com\\horstmann\\violet\\application\\gui\\util\\chenzuo\\File").listFiles();
					if (files.length != 0) {
						for (File file : files) {
							file.delete();
						}
					}

					mainFrame.renewPanel();
				}
				return 1;
			}
		};

		task = new FutureTask<>(callable);
		thread = new Thread(task);

		
		callable1 = new Callable<Integer>() {

			@SuppressWarnings("unchecked")
			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				//  生成失败可靠性测试数据
				
				try {
				Exchangelabel.removeAll();
				Exchangelabel.setText("正在生成失效数据....");
				List<TestCase> failtestcases = ResultService.getFail(testcaselist);
                
			
				if (failtestcases.size() != 0) {
				Collections.sort(failtestcases,new Comparator<TestCase>() {
						@Override
						public int compare(TestCase o1, TestCase o2) {
							// TODO Auto-generated method stub
							int id1 = Integer.valueOf(o1.getTestCaseID());
							int id2 = Integer.valueOf(o2.getTestCaseID());
							if(id1 > id2)
							{
								return 1;
							}
							else {
								return 0;
							}
						}
					});
					
					ValidatePagePanel wrongPage = new ValidatePagePanel(mainFrame);
					mainFrame.getStepFourTabbedPane().getWrongtestCaseResults().add(wrongPage);
					mainFrame.getStepFourTabbedPane().getWrongtestCaseResults().repaint();
					i = 0;
					int index = 500;
					if (failtestcases.size() < 500) {
						index = failtestcases.size();
					}
					
					List<TestCaseMatrixPanel> testCaseMatrixPanels = new ArrayList<TestCaseMatrixPanel>();
					wrongPage.getValidatePanel().add(new JPanel(),
							new GBC(0, index + 1).setFill(GBC.BOTH).setWeight(1, 1));					

					for (int j = 0; j < index; j++) {
						TestCase testCase = failtestcases.get(j);
						
						TestCaseMatrixPanel testCaseMatrixPanel = new TestCaseMatrixPanel(
								mainFrame);
						
						TestCaseTabelPanel titleCaseTabelPanel = new TestCaseTabelPanel(
								testCase.getTestCaseID(), testCase.getResult(),
								testCase.getExeTime(),0);

						String str1 = testCase.getState().split("ms")[0] + "ms";
						
						String str2 = testCase.getState().split("ms")[1];
						testCaseMatrixPanel.getTitleTabel().add(titleCaseTabelPanel);
						testCaseMatrixPanel.getPredict().removeAll();
						if(type.equals(type3))
						{
							testCaseMatrixPanel.getPredict().setText("被测程序执行结果:电梯停靠在" + str2 + "层");
							testCaseMatrixPanel.getPredict().setForeground(Color.red);
						}
						else {
								testCaseMatrixPanel.getPredict().setText("被测程序执行结果:无人机运行异常");
								testCaseMatrixPanel.getPredict().setForeground(Color.red);	
						}
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
						TestCaseTabelPanel testCaseTabelPanel1 = new TestCaseTabelPanel(processID,
								processName, processParam, processStatus, processExec);
						testCaseMatrixPanel.getTabelPanel().add(testCaseTabelPanel1,
								BorderLayout.CENTER);

						wrongPage.getValidatePanel().add(testCaseMatrixPanel,
								new GBC(0, j).setFill(GBC.BOTH).setWeight(1, 0));
						Exchangelabel.removeAll();
						Exchangelabel.setText("正在生成失效的可靠性测试数据列表....");
						ExchangeProgressBar.setValue(50 + (int)(((double)(j+1)/index)*30));
						Thread.sleep(50);
						mainFrame.renewPanel();
						testCaseMatrixPanels.add(testCaseMatrixPanel);
					}

					wrongPage.getPageTestField().setText("1");
					wrongPage.setList(failtestcases);
					wrongPage.setType(type);
					// 生成失败的可靠性测试数据xml
					String failCasePath = mainFrame.getBathRoute() + "/FailTestCase/" + modelName
							+ "_Fail.xml";
					FailTestCase.writeToXML(failCasePath, failtestcases);
					
//					Collections.sort(failtestcases, new Comparator(){  
//				        @Override  
//				        public int compare(Object o1, Object o2) {  
//				            TestCase t1=(TestCase)o1;  
//				            TestCase t2=(TestCase)o2;  
//				            if(Integer.valueOf(t1.getTestCaseID()) > Integer.valueOf(t2.getTestCaseID())){  
//				                return 1;  
//				            }else if(Integer.valueOf(t1.getTestCaseID()) == Integer.valueOf(t2.getTestCaseID())){  
//				                return 0;  
//				            }else{  
//				                return -1;  
//				            }  
//				        }             
//				    }); 
					Exchangelabel.removeAll();
					Exchangelabel.setText("正在存储失效数据....");
					saveFailTestCase(failtestcases); //存储失效数据
				}else {
					for(int i = 51; i <= 100;i++)
					{
						ExchangeProgressBar.setValue(i);
						Thread.sleep(20);
					}
				}

				// 清除分割的xml
//				String name = modelName.split("#")[0];
//				String type = modelName.split("#")[1];
//
//				String filename1 = name + "_1#" + type + ".xml";
//				String filename2 = name + "_2#" + type + ".xml";
//
//				File file1 = new File(route + filename1);
//				File file2 = new File(route + filename2);
//				if (file1.exists()) {
//					file1.delete();
//					file2.delete();
//				}

				for(int i = 81; i <= 100;i++)
				{
					ExchangeProgressBar.setValue(i);
					Thread.sleep(20);
				}
				Exchangelabel.removeAll();
				Exchangelabel.setText("可靠性测试数据执行报告生成完毕!");
				mainFrame.getStepFourTabbedPane().setSelectedIndex(0);
				
				mainFrame.getStepFourTestCase().getjRadionPanel().setVisible(true);
				startExchange.setEnabled(true);
				FileButton.setEnabled(true);
				TestCaseButton.setEnabled(true);
				mainFrame.renewPanel();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					Exchangelabel.removeAll();
					Exchangelabel.setText("可靠性测试数据执行失败!");

					mainFrame.getStepFourTestCase().getjRadionPanel().setVisible(true);
					startExchange.setEnabled(true);
					FileButton.setEnabled(true);
					TestCaseButton.setEnabled(true);
				}
				return 1;
			}
		};
		task1 = new FutureTask<>(callable1);
		thread1 = new Thread(task1);
	}

	public void ExceptionStopRunThread() {
		mainthread.interrupt();
		thread.interrupt();
		mainFrame.getStepFourTabbedPane().getTestCaseResults().removeAll();
		mainFrame.getStepFourTabbedPane().getTestCaseResport().removeAll();
		mainFrame.getStepFourTabbedPane().getWrongtestCaseResults().removeAll();

		// 清除分割的xml
//		if (modelName != null) {
//			String name = modelName.split("#")[0];
//			String type = modelName.split("#")[1];
//
//			String filename1 = name + "_1#" + type + ".xml";
//			String filename2 = name + "_2#" + type + ".xml";
//
//			File file1 = new File(route + filename1);
//			File file2 = new File(route + filename2);
//			if (file1.exists()) {
//				file1.delete();
//				file2.delete();
//			}
//		}

		mainFrame.getStepFourTestCase().getjRadionPanel().setVisible(true);
		startExchange.setEnabled(true);
		FileButton.setEnabled(true);
		TestCaseButton.setEnabled(true);
		
		File[] files = new File(System.getProperty("user.dir")
				+ "\\src\\main\\java\\com\\horstmann\\violet\\application\\gui\\util\\chenzuo\\File").listFiles();
		if (files.length != 0) {
			for (File file : files) {
				file.delete();
			}
		}
		mainFrame.renewPanel();
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

	public List<TestCase> extractFunctionalTestDataFromXml(File file) {
		// TODO Auto-generated method stub

		int i = 1, j = 1;

		XMLToTestCaseList = new ArrayList<TestCase>();
		List<myProcess> processList = new ArrayList<myProcess>();

		SAXReader reader = new SAXReader();
		try {

			Document dom = reader.read(file);

			Element TCS = dom.getRootElement();
			List<Element> testcaseElements = TCS.elements("testcase");
			for (Element testcase : testcaseElements) {

				List<Element> processElements = testcase.elements("process");

				for (Element process : processElements) {

					Element operation = process.element("operation");

					Element input = process.element("input");

					myProcess p = new myProcess();
					p.setProcessID(j++);
					p.setProcessName(operation.getData().toString());
					p.setProcessParam(input.getData().toString());
					// p.setProcessStatus(processStatus);

					processList.add(p);

				}
				j = 1;
				TestCase tc = new TestCase();
				tc.setTestCaseID(String.valueOf(i++));
				tc.setProcessList(processList);
				tc.setExpectResult("预期结果:可靠性测试数据正确");
				tc.setState("测试耗时:0ms");
				XMLToTestCaseList.add(tc);

				processList = new ArrayList<myProcess>();

			}

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return XMLToTestCaseList;
	}

	private void extractDataToXml(String path, List<TestCase> list) {
		// TODO Auto-generated method stub

		Document doc = DocumentHelper.createDocument();
		Element TCS = doc.addElement("TCS");

		for (TestCase tc : list) {
			Element testcase = TCS.addElement("testcase");
			// System.out.println(tc.getProcessList().size());
			for (myProcess p : tc.getProcessList()) {
				Element process = testcase.addElement("process");
				Element operation = process.addElement("operation");
				Element input = process.addElement("input");

				operation.setText(p.getProcessName());
				input.setText(p.getProcessParam());
			}
		}

		try {
			// 定义输出流的目的地
			// String baseUrl =
			// "D:\\ModelDriverProjectFile\\UPPAL\\4.Real_TestCase";
			FileWriter fw = new FileWriter(path);

			// 定义输出格式和字符集
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");

			// 定义用于输出xml文件的XMLWriter对象
			XMLWriter xmlWriter = new XMLWriter(fw, format);
			xmlWriter.write(doc);// *****
			xmlWriter.close();
			mainFrame.renewPanel();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<TestCasePanel> CreateTestCaseTable(List<TestCase> testcaselist) {
		List<TestCasePanel> lists = new ArrayList<TestCasePanel>();
		for (i = 0; i < testcaselist.size(); i++) {
			TestCase testCase = testcaselist.get(i);
			testCase.setTestCaseID(String.valueOf(i + 1));
			SelectBox selectBox = new SelectBox(testCase, mainFrame, testCase.getTestCaseID());
			selectBoxs.add(selectBox);
			TestCasePanel testCaseMatrixPanel = new TestCasePanel(mainFrame, selectBox);
			TestCaseTabelPanel titleCaseTabelPanel = new TestCaseTabelPanel(testCase.getTestCaseID(),
					testCase.getExpectResult(), testCase.getState(), selectBox,1);

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
			TestCaseTabelPanel testCaseTabelPanel = new TestCaseTabelPanel(processID, processName, processParam,
					processStatus, processExec);
			testCaseMatrixPanel.getTabelPanel().add(testCaseTabelPanel, BorderLayout.CENTER);
			lists.add(testCaseMatrixPanel);
			mainFrame.renewPanel();
		}
		return lists;
	}

	private List<TestCase> getCurrentTestCase() {
		List<TestCase> lists = new ArrayList<TestCase>();
		for (SelectBox selectBox : selectBoxs) {
			if (selectBox.isSelected()) {
				lists.add(selectBox.getTestCase());
			}
		}
		return lists;
	}

	
	private void saveFailTestCase(List<TestCase> testCases)
	{
		List<TestCase> newcases = delet(testCases);
		HibernateUtils utils = new HibernateUtils(0);
		for(int i = 0;i < newcases.size();i++)
		{
			
			utils.saveTestCase(newcases.get(i));	
		}
	}
	
	//去除重复的ID
	private List<TestCase> delet(List<TestCase> testCases)
	{
		List<TestCase> newcases = testCases;
		List<TestCase> deletecases = new ArrayList<TestCase>();
		for(int i = 0;i < newcases.size() - 1;i++)
		{
			if(newcases.get(i).getTestCaseID().equals(newcases.get(i+1).getTestCaseID()))
			{
				deletecases.add(newcases.get(i));
			}
		}
		
		for(int i = 0;i < deletecases.size();i++)
		{
			newcases.remove(deletecases.get(i));
		}
		return newcases;
	}
	public boolean isClose() {
		return close;
	}

	public void setClose(boolean close) {
		this.close = close;
	}

	public FutureTask<Integer> getTask() {
		return task;
	}

	public Thread getThread() {
		return thread;
	}

	public FutureTask<Integer> getMaintask() {
		return maintask;
	}

	public Thread getMainthread() {
		return mainthread;
	}

	public String getModelName() {
		return modelName;
	}

	public File getFile() {
		return file;
	}

}
