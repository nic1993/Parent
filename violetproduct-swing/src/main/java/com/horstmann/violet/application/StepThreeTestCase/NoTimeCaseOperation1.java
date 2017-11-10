package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.horstmann.violet.application.StepTwoModelExpand.GradientProgressBarUI;
import com.horstmann.violet.application.StepTwoModelExpand.ProgressUI;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoMatrixPanel;
import com.horstmann.violet.application.gui.DisplayForm;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.gui.util.yangjie.BestAssign;
import com.horstmann.violet.application.gui.util.yangjie.Calculate;
import com.horstmann.violet.application.gui.util.yangjie.CalculateDistribution;
import com.horstmann.violet.application.gui.util.yangjie.CalculateSimilarity;
import com.horstmann.violet.application.gui.util.yangjie.CollectRoute;
import com.horstmann.violet.application.gui.util.yangjie.Constant;
import com.horstmann.violet.application.gui.util.yangjie.Markov;
import com.horstmann.violet.application.gui.util.yangjie.Parameter;
import com.horstmann.violet.application.gui.util.yangjie.ReadMarkov2;
import com.horstmann.violet.application.gui.util.yangjie.Route;
import com.horstmann.violet.application.gui.util.yangjie.State;
import com.horstmann.violet.application.gui.util.yangjie.Stimulate;
import com.horstmann.violet.application.gui.util.yangjie.TCDetail;
import com.horstmann.violet.application.gui.util.yangjie.Transition;
import com.horstmann.violet.application.menu.util.zhangjian.Database.DataBaseUtil;

public class NoTimeCaseOperation1 extends JPanel {
	private JLabel topLabel;
	private JLabel label1;
	private JLabel label2;

	private JProgressBar progressBar;
	private JTextField textField;
	private JButton button;

	private JPanel gapPanel;
	private MainFrame mainFrame;

	private String testSequence;// 测试序列
	private String excitation; // 激励序列
	private String testCase; // 测试用例

	private List<String> paramterNameList;
	private List<String> paramterValueList;

	private int min;
	private int N;
	private double p;
	private double c;
	private int i;

	private List<String> constraintNameString;
	private List<Double> pros;
	private List<Integer> numbers;
	private List<Double> actualPercentsDoubles;
	private Document dom;
	private Markov markov;
	private Element root;
	private double[] PI;
	private int minSeq;
	private XMLWriter writer;

	private int progressBarIndex = 0;

	private Callable<Integer> maincallable;
	private FutureTask<Integer> maintask;
	private Thread mainthread;
	private Callable<Integer> callable1;
	private FutureTask<Integer> task1;
	private Thread thread1;
	private Callable<Integer> callable2;
	private FutureTask<Integer> task2;
	private Thread thread2;

	private List<FutureTask<Integer>> futuretasklist;
	private List<Thread> threadlist;

	private BigDecimal bigDecimal;
	private DecimalFormat df = new DecimalFormat();
	private String pattern = "#0.00000000";

	private String ModelName;
	private String NoTimeMarkovRoute;
	private String route;

	private String quota;

	public NoTimeCaseOperation1(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		init();
		this.setBackground(new Color(233, 233, 233));
		this.setLayout(new GridBagLayout());
		this.add(topLabel, new GBC(0, 0, 5, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 15, 10, 0));
		this.add(progressBar, new GBC(0, 1, 5, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 15, 10, 15));
		this.add(label1, new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 15, 10, 5));
		this.add(textField, new GBC(1, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 0, 10, 5));
		this.add(label2, new GBC(2, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 0, 10, 5));
		this.add(gapPanel, new GBC(3, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 0, 10, 5));
		this.add(button, new GBC(4, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 0, 10, 15));
	}

	private void init() {
		df.applyPattern(pattern);

		topLabel = new JLabel("");
		label1 = new JLabel("生成测试用例条数:");
		label2 = new JLabel("");

		progressBar = new JProgressBar();
		textField = new JTextField();
		textField.setEditable(false);
		
		button = new JButton("开始生成");
		gapPanel = new JPanel();

		paramterNameList = new ArrayList<String>();
		paramterValueList = new ArrayList<String>();
		constraintNameString = new ArrayList<String>();
		actualPercentsDoubles = new ArrayList<Double>();
		pros = new ArrayList<Double>();
		numbers = new ArrayList<Integer>();

		textField.setPreferredSize(new Dimension(40, 30));
		topLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		label1.setFont(new Font("宋体", Font.PLAIN, 16));
		label2.setFont(new Font("宋体", Font.PLAIN, 16));

		progressBar.setUI(new ProgressUI(progressBar, Color.green));
		progressBar.setMinimumSize(new Dimension(800, 30));
		progressBar.setPreferredSize(new Dimension(800, 30));
		progressBar.setUI(new GradientProgressBarUI());
		progressBar.setValue(0);

		NoTimeMarkovRoute = mainFrame.getBathRoute() + "/NoTimeMarkov/";
		initThread();
		listen();
	}

	private void listen() {
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub1
				initThread();
				mainthread.start();
				thread1.start();
			}
		});
	}

	private void initThread() {
		maincallable = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				try {
					progressBarIndex = 0;
					progressBar.setValue(0);
					progressBar.setValue(progressBarIndex);
					while (progressBarIndex < 40) {
						if (task1.isDone()) {
							progressBarIndex++;
							progressBar.setValue(progressBarIndex);
							Thread.sleep(10);
						} else {
							progressBarIndex++;
							progressBar.setValue(progressBarIndex);
							Thread.sleep(1000);
						}
					}

					while (true) {
						if (task1.isDone()) {
							thread2.start();
							break;
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					topLabel.removeAll();
					topLabel.setText(e.getLocalizedMessage());

					button.setEnabled(true);
					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getNoTimeSeq().setEnabled(true);

					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
					
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
				try {
					button.setEnabled(false);
					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(false);
					mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(false);
					mainFrame.getStepThreeLeftButton().getNoTimeSeq().setEnabled(false);

					markov = mainFrame.getNoTimeSeqOperation1().getMarkov();
					dom = mainFrame.getNoTimeSeqOperation1().getDom();
					root = mainFrame.getNoTimeSeqOperation1().getRoot();
					PI = mainFrame.getNoTimeSeqOperation1().getPI();
					min = mainFrame.getStepThreeLeftButton().getMin();
					minSeq = mainFrame.getNoTimeSeqOperation1().getMinSeq();

					progressBarIndex = 0;
					progressBar.setValue(0);
					mainFrame.getStepThreeNoTimeTabbedPane().getTestData().removeAll();
					markov.setTcNumber(minSeq);

					Calculate.getAllTransValues(markov);

					new BestAssign().assign(markov, root);
					
					markov.setDeviation(CalculateSimilarity.statistic(markov, PI));

					OutputFormat format = OutputFormat.createPrettyPrint();

					writer = new XMLWriter(
							new FileOutputStream(mainFrame.getBathRoute() + "/TestCase/" + ModelName + "_Custom#1.xml"),
							format);
					writer.write(dom);
					writer.close();

					mainFrame.renewPanel();
				} catch (Exception e) {
					// TODO: handle exception
					topLabel.removeAll();
					topLabel.setText(e.getLocalizedMessage());

					button.setEnabled(true);
					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getNoTimeSeq().setEnabled(true);

					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
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
					// 获取参数的求解
					List<Route> routeList = markov.getRouteList();

					constraintNameString.clear();
					pros.clear();
					numbers.clear();
					for (Route route : routeList) {
						constraintNameString.add(route.getTcSequence());
						pros.add(route.getRouteProbability());
						numbers.add(route.getNumber());
						actualPercentsDoubles.add(route.getActualPercent());
					}

					StepThreeTabelPanel stepThreeTabelPanel = new StepThreeTabelPanel(constraintNameString,
							actualPercentsDoubles, pros, numbers);
					StepThreeTabelPanel testRoute = new StepThreeTabelPanel(constraintNameString, actualPercentsDoubles,
							pros, numbers);

					List<TCDetail> lists = DataBaseUtil.showTCDetailAll("select * from tcdetail");

					mainFrame.getStepThreeTimeTabbedPane().getTestData().removeAll();

					CasePagePanel casePagePanel = new CasePagePanel(lists, mainFrame);
					mainFrame.getStepThreeNoTimeTabbedPane().getTestData().add(casePagePanel);

					// 生成测试数据
					topLabel.removeAll();
					topLabel.setText("正在生成测试数据信息........");

					JPanel TestDataPanel = new JPanel();

					int index = 0;
					if (lists.size() < 500) {
						index = lists.size();
					} else {
						index = 500;
					}

					casePagePanel.getCasePanel().add(new JPanel(),
							new GBC(0, index).setFill(GBC.BOTH).setWeight(1, 1));
					
					for (int j = 0; j < index; j++) {
						StepThreeTabelPanel testTabelPanel = new StepThreeTabelPanel(lists.get(j).getTestCase(), 2,
								mainFrame);
						casePagePanel.getCasePanel().add(testTabelPanel,
								new GBC(0, j).setFill(GBC.BOTH).setWeight(1, 0));
						casePagePanel.getCasePanel().repaint();
						mainFrame.getStepThreeNoTimeTabbedPane().getTestData().repaint();
						
						progressBar.setValue(61 + (int) (((double) (j+1) / 500) * 40));
						Thread.sleep(10);
						mainFrame.renewPanel();
					}
					casePagePanel.getPageTestField().setText("1");

					mainFrame.getStepThreeNoTimeTabbedPane().getTestData().removeAll();
	                mainFrame.getStepThreeNoTimeTabbedPane().getTestData().add(casePagePanel);
	                mainFrame.renewPanel();
	                
					for (Route route : routeList) {
						mainFrame.getOutputinformation().geTextArea()
								.append("			测试序列：" + testSequence + "	 路径概率(指标-可靠性测试用例生成比率"
										+ route.getActualPercent() + "):   " + route.getRouteProbability() + "	此类用例包含"
										+ route.getNumber() + "个" + "\n");

						int length = DisplayForm.mainFrame.getOutputinformation().geTextArea().getText().length();
						DisplayForm.mainFrame.getOutputinformation().geTextArea().setCaretPosition(length);
						Thread.sleep(10);
					}

					bigDecimal = new BigDecimal(markov.getDeviation());
					String ii = bigDecimal.toPlainString();
					double d = Double.valueOf(ii);
					topLabel.removeAll();
					topLabel.setText("测试用例生成完成, 实际共生成" + lists.size() + "条!" + "可靠性测试用例数据库覆盖率:"
							+ df.format(markov.getDbCoverage()) + "  可靠性测试用例生成比率与使用模型实际使用概率平均偏差:" + df.format(markov.getDeviation()));

					
	                mainFrame.getOutputinformation().geTextArea().append("指标:可靠性测试用例数据库覆盖率 = 覆盖的马尔可夫链路径/总的马尔可夫链路径" + "\n");
					int length = mainFrame.getOutputinformation().geTextArea().getText().length(); 
	                mainFrame.getOutputinformation().geTextArea().setCaretPosition(length);
					
					NoTimeTestCaseNode noTimeTestCaseLabel = new NoTimeTestCaseNode(ModelName + "_自定义", mainFrame);
					quota = "测试用例生成完成, 实际共生成" + lists.size() + "条!" + "可靠性测试用例数据库覆盖率:" + df.format(markov.getDbCoverage())
							+ "  可靠性测试用例生成比率与使用模型实际使用概率平均偏差:" + df.format(d);
					noTimeTestCaseLabel.setQuota(quota);
					noTimeTestCaseLabel.setTestRoute(testRoute);
					noTimeTestCaseLabel.setCasePagePanel(casePagePanel);
					mainFrame.getStepThreeLeftButton().getNoTimeCaseNodePanel()
							.insertCustomNodeLabel(noTimeTestCaseLabel, casePagePanel, testRoute, quota);

					button.setEnabled(true);
					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getNoTimeSeq().setEnabled(true);
					
					mainFrame.renewPanel();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					topLabel.removeAll();
					topLabel.setText(e.getLocalizedMessage());

					button.setEnabled(true);
					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getNoTimeSeq().setEnabled(true);

					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
					mainFrame.renewPanel();
				}
				return 1;
			}
		};
		task2 = new FutureTask<>(callable2);
		thread2 = new Thread(task2);
	}

	private static boolean isSufficient(Markov markov) {

		for (State state : markov.getStates()) {

			for (Transition outTransition : state.getOutTransitions()) {

				if (outTransition.getAccessTimes() == 0) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException ex) {
		}
		return false;
	}

	public JLabel getLabel2() {
		return label2;
	}

	public JLabel getTopLabel() {
		return topLabel;
	}

	public String getModelName() {
		return ModelName;
	}

	public void setModelName(String modelName) {
		ModelName = modelName;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public JButton getButton() {
		return button;
	}

}
