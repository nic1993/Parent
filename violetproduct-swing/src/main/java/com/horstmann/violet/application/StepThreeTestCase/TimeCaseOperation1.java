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
import com.horstmann.violet.application.gui.util.yangjie.Markov;
import com.horstmann.violet.application.gui.util.yangjie.Parameter;
import com.horstmann.violet.application.gui.util.yangjie.ReadMarkov2;
import com.horstmann.violet.application.gui.util.yangjie.Route;
import com.horstmann.violet.application.gui.util.yangjie.State;
import com.horstmann.violet.application.gui.util.yangjie.Stimulate;
import com.horstmann.violet.application.gui.util.yangjie.TCDetail;
import com.horstmann.violet.application.gui.util.yangjie.Transition;
import com.horstmann.violet.application.menu.util.zhangjian.Database.DataBaseUtil;

public class TimeCaseOperation1 extends JPanel {
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
	private String testCase; // 可靠性测试数据

	private List<String> paramterNameList;
	private List<String> paramterValueList;

	private int min;
	private int N;
	private double p;
	private double c;

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

	private List<String> constraintNameString;
	private List<Double> pros;
	private List<Integer> numbers;
	private List<Double> actualPercentsDoubles;

	private String ModelName;
	private String TimeMarkovRoute;
	private String route;

	private BigDecimal bigDecimal;
	private String quota;
	private DecimalFormat df = new DecimalFormat();
	private String pattern = "#0.000000";

	public TimeCaseOperation1(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		init();
		this.setBackground(new Color(233, 233, 233));
		this.setLayout(new GridBagLayout());
		this.add(topLabel, new GBC(0, 0, 5, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 15, 10, 0));
		this.add(progressBar, new GBC(0, 1, 4, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 15, 10, 15));
		this.add(label1, new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 15, 10, 5));
		this.add(textField, new GBC(1, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 0, 10, 5));
//		this.add(label2, new GBC(2, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 0, 10, 5));
		this.add(gapPanel, new GBC(2, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 0, 10, 5));
		this.add(button, new GBC(3, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 0, 10, 15));
	}

	private void init() {
		topLabel = new JLabel("可靠性测试数据生成完成");
		label1 = new JLabel("可靠性测试数据条数:");
//		label2 = new JLabel("(不少于185条)");

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

		textField.setPreferredSize(new Dimension(30, 30));
		textField.setMinimumSize(new Dimension(40, 30));
		textField.setMaximumSize(new Dimension(40, 30));
		textField.setText("230");
		topLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		label1.setFont(new Font("宋体", Font.PLAIN, 16));
//		label2.setFont(new Font("宋体", Font.PLAIN, 16));

		progressBar.setUI(new ProgressUI(progressBar, Color.green));
		progressBar.setUI(new GradientProgressBarUI());
		progressBar.setPreferredSize(new Dimension(800, 30));
		progressBar.setMinimumSize(new Dimension(800, 30));
		progressBar.setMaximumSize(new Dimension(800, 30));

		TimeMarkovRoute = mainFrame.getBathRoute() + "/ExtendMarkov/";

		df.applyPattern(pattern);
		listen();
	}

	private void listen() {
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
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
					while (progressBarIndex < 60) {
						if (task1.isDone()) {
							progressBarIndex++;
							progressBar.setValue(progressBarIndex);
							Thread.sleep(10);
						} else {
							progressBarIndex++;
							progressBar.setValue(progressBarIndex);
							Thread.sleep(10000);
						}
					}

					while (true) {
						if (task1.isDone()) {
							thread2.start();
							break;
						}
					}
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					button.setEnabled(true);
					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getModelExpand().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeModelLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeExpandLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeSeq().setEnabled(true);

					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);

					topLabel.removeAll();
					topLabel.setText("生成可靠性测试数据出错!");
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
					mainFrame.getStepThreeLeftButton().getModelExpand().setEnabled(false);
					
					mainFrame.getStepThreeLeftButton().getTimeModelLabel().setEnabled(false);
					mainFrame.getStepThreeLeftButton().getTimeExpandLabel().setEnabled(false);
					mainFrame.getStepThreeLeftButton().getTimeSeq().setEnabled(false);

					mainFrame.getStepThreeTimeTabbedPane().getTestData().removeAll();
					mainFrame.getStepThreeTimeTabbedPane().updateUI();

					markov = mainFrame.getTimeSeqOperation1().getMarkov();
					dom = mainFrame.getTimeSeqOperation1().getDom();
					root = mainFrame.getTimeSeqOperation1().getRoot();
					PI = mainFrame.getTimeSeqOperation1().getPI();
					minSeq = mainFrame.getTimeSeqOperation1().getMinSeq();

					topLabel.removeAll();
					topLabel.setText("正在生成可靠性测试数据(该过程需要较久时间,请耐心等待)....");
					Thread.sleep(150);

					Calculate.getAllTransValues(markov);
					
					new BestAssign().assign(markov, root);

					markov.setDeviation(CalculateSimilarity.statistic(markov, PI));
					
					OutputFormat format = OutputFormat.createPrettyPrint();
					
					writer = new XMLWriter(
							new FileOutputStream(mainFrame.getBathRoute() + "/TestCase/" + ModelName + "_Custom#3.xml"),
							format);
					writer.write(dom);
					writer.close();

					mainFrame.renewPanel();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					button.setEnabled(true);
					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getModelExpand().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeModelLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeExpandLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeSeq().setEnabled(true);

					topLabel.removeAll();
					topLabel.setText("生成可靠性测试数据出错!");

					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
					
					mainthread.stop();
					thread2.stop();
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
					// 生成测试数据
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
					mainFrame.getStepThreeTimeTabbedPane().getTestData().add(casePagePanel);
					

					

					JPanel TestDataPanel = new JPanel();
					TestDataPanel.setLayout(new GridBagLayout());

					int index = 500;
					if (lists.size() < 500) {
						index = lists.size();
					}

					casePagePanel.getCasePanel().add(new JPanel(),
							new GBC(0, index).setFill(GBC.BOTH).setWeight(1, 1));
					
					for (int k = 0; k < index; k++) {
						StepThreeTabelPanel testTabelPanel2 = new StepThreeTabelPanel(lists.get(k).getTestCase(), 2,
								mainFrame);

						casePagePanel.getCasePanel().add(testTabelPanel2,
								new GBC(0, k).setFill(GBC.BOTH).setWeight(1, 0));
						casePagePanel.getCasePanel().repaint();
						mainFrame.getStepThreeTimeTabbedPane().getTestData().updateUI();
						progressBar.setValue(40 + (int) (((double)(k+1) / index) * 60));
						topLabel.removeAll();
						topLabel.setText("正在生成第" + (k + 1) + "个可靠性测试数据....");
						Thread.sleep(10);
						mainFrame.renewPanel();
					}

					casePagePanel.getPageTestField().setText("1");
					mainFrame.getStepThreeTimeTabbedPane().getTestData().removeAll();
					mainFrame.getStepThreeTimeTabbedPane().getTestData().add(casePagePanel);
					mainFrame.getStepThreeTimeTabbedPane().getTestData().repaint();
					mainFrame.renewPanel();
					
//					for (Route route : routeList) {
//						mainFrame.getOutputinformation().geTextArea()
//								.append("			测试序列：" + testSequence + "	 路径概率(指标-可靠性可靠性测试数据生成比率"
//										+ route.getActualPercent() + "):   " + route.getRouteProbability() + "	此类用例包含"
//										+ route.getNumber() + "个" + "\n");
//
//						int length = DisplayForm.mainFrame.getOutputinformation().geTextArea().getText().length();
//						DisplayForm.mainFrame.getOutputinformation().geTextArea().setCaretPosition(length);
//						Thread.sleep(10);
//					}
					
					
					bigDecimal = new BigDecimal(markov.getDeviation());
					String ii = bigDecimal.toPlainString();
					double d = Double.valueOf(ii);					
					
					topLabel.removeAll();
					topLabel.setText("可靠性测试数据生成完成, 实际共生成" + lists.size() + "条!" + "可靠性测试用例数据库覆盖率:"
							+ df.format(markov.getDbCoverage()) + "  可靠性测试用例生成比率与使用模型实际使用概率平均偏差:" + df.format(d));

					TimeTestCaseNode timeTestCaseLabel = new TimeTestCaseNode(ModelName + "_自定义", mainFrame);
					quota = "可靠性测试数据生成完成, 实际共生成" + lists.size() + "条!" + "可靠性测试用例数据库覆盖率:"
							+ df.format(markov.getDbCoverage()) + "  可靠性测试用例生成比率与使用模型实际使用概率平均偏差:" + df.format(d);
					timeTestCaseLabel.setQuota(quota);
					timeTestCaseLabel.setTestRoute(testRoute);
					timeTestCaseLabel.setCasePagePanel(casePagePanel);
					mainFrame.getStepThreeLeftButton().getTimeCaseNodePanel().insertCustomNodeLabel(timeTestCaseLabel,
							casePagePanel, testRoute, quota);

					button.setEnabled(true);
					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeModelLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getModelExpand().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeExpandLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeSeq().setEnabled(true);
					mainFrame.renewPanel();

				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
					button.setEnabled(true);
					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getModelExpand().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeModelLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeExpandLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeSeq().setEnabled(true);

					topLabel.removeAll();
					topLabel.setText("生成可靠性测试数据出错!");

					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
					
					mainthread.stop();
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

	public JLabel getLabel2() {
		return label2;
	}

	private boolean isInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException ex) {
		}
		return false;
	}

	public JLabel getTopLabel() {
		return topLabel;
	}

	public String getModelName() {
		return ModelName;
	}
	public JTextField getTextField() {
		return textField;
	}

	public void setModelName(String modelName) {
		ModelName = modelName;
	}

}
