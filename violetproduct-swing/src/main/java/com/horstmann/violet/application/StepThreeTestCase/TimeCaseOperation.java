package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
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
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.gui.util.yangjie.Calculate;
import com.horstmann.violet.application.gui.util.yangjie.CalculateDistribution;
import com.horstmann.violet.application.gui.util.yangjie.CalculateSimilarity;
import com.horstmann.violet.application.gui.util.yangjie.GenerateCases;
import com.horstmann.violet.application.gui.util.yangjie.Markov;
import com.horstmann.violet.application.gui.util.yangjie.Parameter;
import com.horstmann.violet.application.gui.util.yangjie.RandomCase;
import com.horstmann.violet.application.gui.util.yangjie.ReadMarkov2;
import com.horstmann.violet.application.gui.util.yangjie.State;
import com.horstmann.violet.application.gui.util.yangjie.Stimulate;
import com.horstmann.violet.application.gui.util.yangjie.TCDetail;
import com.horstmann.violet.application.gui.util.yangjie.Transition;
import com.horstmann.violet.application.menu.util.zhangjian.Database.DataBaseUtil;



public class TimeCaseOperation extends JPanel {
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
	private String testCase;

	private List<String> paramterNameList;
	private List<String> paramterValueList;

	private ReadMarkov2 rm;
	private Markov markov;
	private GenerateCases gc;
	private Element root;
	private Document dom;
	private List<Transition> transitions;

	private int progressBarIndex;
	private Callable<Integer> maincallable;
	private FutureTask<Integer> maintask;
	private Thread mainthread;

	private BigDecimal bigDecimal;
	private DecimalFormat df = new DecimalFormat();
	private String pattern = "#0.00000000";

	private int i;
	private String ModelName;
	private String TimeMarkovRoute;
	private String route;

	private String quota;

	public TimeCaseOperation(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		init();
		this.setBackground(new Color(233, 233, 233));
		this.setLayout(new GridBagLayout());
		this.add(topLabel, new GBC(0, 0, 4, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 15, 10, 0));
		this.add(progressBar, new GBC(0, 1, 4, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 15, 10, 15));
		this.add(label1, new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 15, 10, 5));
		this.add(textField, new GBC(1, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 0, 10, 5));
		this.add(gapPanel, new GBC(2, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 0, 10, 5));
		this.add(button, new GBC(3, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 0, 10, 15));
	}

	private void init() {
		topLabel = new JLabel("测试用例生成完成");
		label1 = new JLabel("终止条件阈值:");

		progressBar = new JProgressBar();

		textField = new JTextField();
		button = new JButton("开始生成");
		gapPanel = new JPanel();

		paramterNameList = new ArrayList<String>();
		paramterValueList = new ArrayList<String>();

		textField.setPreferredSize(new Dimension(30, 30));
		topLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		label1.setFont(new Font("宋体", Font.PLAIN, 16));

		progressBar.setPreferredSize(new Dimension(800, 30));
		progressBar.setUI(new GradientProgressBarUI());
		progressBar.setValue(0);

		TimeMarkovRoute = mainFrame.getBathRoute() + "/ExtendMarkov/";
		listen();
	}

	private void listen() {
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				initThread();
				mainthread.start();
				// mainFrame.getStepThreeTimeTabbedPane().getCaseResults().removeAll();
				// mainFrame.getStepThreeTimeTabbedPane().getCaseInformation().removeAll();
				// mainFrame.getStepThreeTimeTabbedPane().updateUI();
				// File files = new File(TimeMarkovRoute);
				// for(File selectFile : files.listFiles())
				// {
				// if(selectFile.getName().contains(ModelName))
				// route = selectFile.getAbsolutePath();
				// }
				// ReadMarkov2 rm = new ReadMarkov2();
				// try {
				// Markov markov = rm.readMarkov(route);
				// Calculate.getAllTransValues(markov);
				//
				// // 创建一个document对象，用于存储测试用例
				// Document dom = DocumentHelper.createDocument();
				// Element root = dom.addElement("TCS");
				// // 计算markov链的平稳分布
				// double[] PI =
				// CalculateDistribution.stationaryDistribution(markov);
				//
				// double similarity = 999991;
				// boolean sufficiency = false;
				// GenerateCases gc = new GenerateCases();
				// boolean flag = true;
				//
				// do {
				// int numberOfTestCases = gc.generate(markov, root);
				// // System.out.println(numberOfTestCases);
				// if (flag) {
				//
				// sufficiency = isSufficient(markov);
				// }
				//
				// if (!sufficiency) {
				// continue;
				// }
				// flag = false;
				// // similarity = CalculateSimilarity.statistic_1(markov);
				// similarity = CalculateSimilarity.statistic(markov, PI);
				//
				// System.out.println("\n当前使用链和测试链的相似度为:" + similarity);
				// System.out.println("\n当前生成的测试用例和测试路径的个数:" + numberOfTestCases
				// + "\n\n");
				//
				// } while (similarity > 0.001);
				//
				// // WriteTestCases.writeCases(gc.getTestCases());
				//
				// for (double d : PI) {
				// System.out.print(d + " ");
				// }
				// // 将存储好测试用例的document对象写入XML文件
				// OutputFormat format = OutputFormat.createPrettyPrint();
				// XMLWriter writer = new XMLWriter(new FileOutputStream(
				// mainFrame.getBathRoute()+"/TestCase/"+ModelName+".xml"),
				// format);
				// writer.write(dom);
				// writer.close();
				//
				// //测试用例生成
				// List<TCDetail> lists = DataBaseUtil.showTCDetailAll("select *
				// from tcdtail");
				// i = 0;
				// for(TCDetail tcDetail : lists)
				// {
				// StepThreeTabelPanel testTabelPanel = new
				// StepThreeTabelPanel(tcDetail.getTestSequence(),
				// tcDetail.getStimulateSequence(), tcDetail.getTestCase());
				// StepTwoMatrixPanel stepTwoMatrixPanel = new
				// StepTwoMatrixPanel();
				// stepTwoMatrixPanel.getTitleLabel().setText("测试用例信息");
				// stepTwoMatrixPanel.getTabelPanel().add(testTabelPanel);
				// mainFrame.getStepThreeTimeTabbedPane().getCaseInformation().add(stepTwoMatrixPanel,
				// new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
				// i++;
				// }
				//
				// mainFrame.getStepThreeTimeTabbedPane().getCaseInformation().add(new
				// JPanel(), new GBC(0, i+1).setFill(GBC.BOTH).setWeight(1, 1));
				// //获取参数的求解
				// List<Transition> transitions = markov.getTransitions();
				// for(Transition transition : transitions)
				// {
				// Stimulate stimulate = transition.getStimulate();
				// List<Parameter> parameters = stimulate.getParameters();
				// for(Parameter parameter: parameters)
				// {
				// paramterNameList.add(transition.getName());
				// List<String> values = parameter.getValues();
				// String lastValue = parameter.getName()+"=[";
				// for(int i = 0;i < values.size();i++)
				// {
				// if(i == 0)
				// lastValue += values.get(i).toString();
				// else {
				// lastValue += ","+values.get(i).toString();
				// }
				// }
				// lastValue += "]";
				// paramterValueList.add(lastValue);
				// }
				// }
				// StepThreeTabelPanel stepThreeTabelPanel = new
				// StepThreeTabelPanel(paramterNameList, paramterValueList);
				// mainFrame.getStepThreeTimeTabbedPane().getCaseResults().add(stepThreeTabelPanel);
				//
				// } catch (Exception e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }
			}
		});
	}

	private void initThread() {
		maincallable = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				button.setEnabled(false);
				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(false);
				mainFrame.getStepThreeLeftButton().getModelExpand().setEnabled(false);
				mainFrame.getStepThreeLeftButton().getTimeSeq().setEnabled(false);

				JPanel mainPanel = new JPanel();
				mainPanel.setLayout(new GridBagLayout());

				mainFrame.getStepThreeTimeTabbedPane().getTestData().removeAll();

				progressBarIndex = 0;

				markov = mainFrame.getTimeSeqOperation().getMarkov();
				gc = mainFrame.getTimeSeqOperation().getGc();
				root = mainFrame.getTimeSeqOperation().getRoot();
				dom = mainFrame.getTimeSeqOperation().getDom();

				try {
					topLabel.removeAll();
					topLabel.setText("正在获取生成的抽象测试用例.....");
					Thread.sleep(100);
					Calculate.getAllTransValues(markov);

					for (int i = 0; i < gc.testCasesExtend.size(); i++) {
						TCDetail.getInstance().setTestSequence(gc.abstractTS.get(i));
						String stimulateSequence = getStimulateSeq(gc.testCasesExtend
								.get(i));
						TCDetail.getInstance().setStimulateSequence(stimulateSequence);
						RandomCase.getCase(gc.testCasesExtend.get(i), root);
					}
					
					OutputFormat format = OutputFormat.createPrettyPrint();
					XMLWriter writer = new XMLWriter(
							new FileOutputStream(mainFrame.getBathRoute() + "\\TestCase\\" + ModelName + "_相似度#3.xml"),
							format);
					writer.write(dom);
					writer.close();

					List<TCDetail> lists = DataBaseUtil.showTCDetailAll("select * from tcdetail");
					
					mainFrame.getStepThreeTimeTabbedPane().getTestData().removeAll();
					
					CasePagePanel casePagePanel = new CasePagePanel(lists,mainFrame);
					mainFrame.getStepThreeTimeTabbedPane().getTestData().add(casePagePanel);
					
					int index = 500;
					if(lists.size() < 500)
					{
						index = lists.size();
					}

					// 生成测试数据
					JPanel TestDataPanel = new JPanel();
					TestDataPanel.setLayout(new GridBagLayout());

                    	for(int i = 0;i < index;i++){
                    		StepThreeTabelPanel testTabelPanel = new StepThreeTabelPanel(lists.get(i).getTestCase(), 2,
            						mainFrame);
                    		casePagePanel.getCasePanel().add(testTabelPanel,
								new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
                    		i++;
                    		progressBar.setValue(60 + (int) (((double) i / index) * 40));
                    		Thread.sleep(10);
                    		mainFrame.renewPanel();
                    	}
                    	casePagePanel.getPageTestField().setText("1");

					mainFrame.getStepThreeTimeTabbedPane().getTestData().updateUI();
					mainFrame.renewPanel();

					topLabel.removeAll();
					bigDecimal = new BigDecimal(markov.getDeviation());
					String ii = bigDecimal.toPlainString();
					double d = Double.valueOf(ii);
					topLabel.setText("测试用例信息生成完成,共生成" + lists.size() + "条测试用例。" + "  可靠性测试用例生成比率与使用模型实际使用概率平均偏差:"
							+ df.format(d));
					mainFrame.getStepThreeTimeTabbedPane().setSelectedIndex(0);

					TimeTestCaseNode timeTestCaseLabel = new TimeTestCaseNode(ModelName + "_相似度", mainFrame);
					quota = "测试用例信息生成完成,共生成" + lists.size() + "条测试用例。" + "  可靠性测试用例生成比率与使用模型实际使用概率平均偏差:" + df.format(d);
					timeTestCaseLabel.setQuota(quota);
					timeTestCaseLabel.setCasePagePanel(casePagePanel);

					mainFrame.getStepThreeLeftButton().getTimeCaseNodePanel().insertNodeLabel(timeTestCaseLabel,
							casePagePanel, quota);

					button.setEnabled(true);
					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getModelExpand().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeExpandLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeSeq().setEnabled(true);
					
					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);

				} catch (Exception e) {
					// TODO: handle exception

				}
				return 1;
			}
		};
		maintask = new FutureTask<>(maincallable);
		mainthread = new Thread(maintask);
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
	
	private static String getStimulateSeq(List<Stimulate> oneCaseExtend) {
		String stimulateSequence = "";
		for (int i = 0; i < oneCaseExtend.size(); i++) {
			if (i != oneCaseExtend.size() - 1) {
				stimulateSequence = stimulateSequence
						+ oneCaseExtend.get(i).toString() + "-->>";
				// System.out.print(oneCaseExtend.get(i).toString() + "-->>");
			} else {
				stimulateSequence = stimulateSequence
						+ oneCaseExtend.get(i).toString();
				// System.out.println(oneCaseExtend.get(i).toString());
			}
		}
		return stimulateSequence;
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

}
