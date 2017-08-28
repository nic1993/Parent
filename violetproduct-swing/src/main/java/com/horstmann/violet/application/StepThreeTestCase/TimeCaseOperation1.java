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



import com.horstmann.violet.application.StepTwoModelExpand.ProgressUI;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoMatrixPanel;
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

public class TimeCaseOperation1 extends JPanel{
       private JLabel topLabel;
       private JLabel label1;
       private JLabel label2;

       private JProgressBar progressBar;
       private JTextField textField;
       private JButton button;
       
       private JPanel gapPanel;
       private MainFrame mainFrame;
       
       private String testSequence;//测试序列
       private String excitation;  //激励序列
       private String testCase;    //测试用例
       
       private List<String> paramterNameList;
       private List<String> paramterValueList;
    
       private int min;
       private int N;
       private double p;
   	   private double c;
   	   private int i;
       
       private ReadMarkov2 rm;
       private Markov markov;
       private XMLWriter writer;
       private int progressBarIndex = 0;
       
       private Callable<Integer> maincallable;
       private FutureTask<Integer> maintask;
   	   private Thread mainthread; 
   	   
   	   private List<String> constraintNameString;
       private List<Double> pros;
       private List<Integer> numbers;
       private List<Double> actualPercentsDoubles;
       
       private String ModelName;
       private String TimeMarkovRoute;
       private String route;
       
       private BigDecimal bigDecimal;
       private String quota;
       private DecimalFormat  df = new DecimalFormat();
	   private String pattern = "#0.0000";
       public TimeCaseOperation1(MainFrame mainFrame)
       {
    	   this.mainFrame = mainFrame;
    	   init();
    	   this.setBackground(new Color(233,233,233));
    	   this.setLayout(new GridBagLayout());
    	   this.add(topLabel,new GBC(0, 0, 5, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 15, 10, 0));
    	   this.add(progressBar,new GBC(0, 1, 5, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 15, 10, 15));
    	   this.add(label1,new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 15, 10, 5));
    	   this.add(textField,new GBC(1, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 0, 10, 5));
    	   this.add(label2,new GBC(2, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 0, 10, 5));
    	   this.add(gapPanel,new GBC(3, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 0, 10, 5));
    	   this.add(button,new GBC(4, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 0, 10, 15));
       }
       private void init()
       {
    	   topLabel = new JLabel("测试用例生成完成");
    	   label1 = new JLabel("测试用例条数:");
    	   label2= new JLabel("(不少于185条)");
    	   
    	   progressBar = new JProgressBar();
    	   textField = new JTextField();
    	   button = new JButton("开始生成");
    	   gapPanel = new JPanel();

    	   paramterNameList = new ArrayList<String>();
    	   paramterValueList = new ArrayList<String>();
           constraintNameString = new ArrayList<String>();
           actualPercentsDoubles = new ArrayList<Double>();
           pros = new ArrayList<Double>();
           numbers = new ArrayList<Integer>();
    	   
    	   textField.setPreferredSize(new Dimension(30,30));
    	   topLabel.setFont(new Font("宋体", Font.PLAIN, 16));
    	   label1.setFont(new Font("宋体", Font.PLAIN, 16));
    	   label2.setFont(new Font("宋体", Font.PLAIN, 16));
    	   
    	   progressBar.setUI(new ProgressUI(progressBar, Color.green));
    	   progressBar.setPreferredSize(new Dimension(830, 30));
    	   
    	   TimeMarkovRoute = mainFrame.getBathRoute()+"/ExtendMarkov/";
    	   
    	   df.applyPattern(pattern);
    	   listen();
       }
       private void listen()
       {
    	   button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				button.setEnabled(false);
   				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(false);
   				mainFrame.getStepThreeLeftButton().getTimeExpandLabel().setEnabled(false);
				
				mainFrame.getStepThreeNoTimeTabbedPane().getTestData().removeAll();
				mainFrame.getStepThreeNoTimeTabbedPane().updateUI();
				File files = new File(TimeMarkovRoute);
				for(File selectFile : files.listFiles())
				{
					if(selectFile.getName().contains(ModelName))
						route = selectFile.getAbsolutePath();
				}
				ReadMarkov2 rm = new ReadMarkov2();
				try {
				Markov markov = rm.readMarkov(route);
				min = mainFrame.getStepThreeLeftButton().getMin();
				if(isInt(textField.getText().toString()))
				{
					markov.setTcNumber(Integer.valueOf(textField.getText().toString()));
					Calculate.getAllTransValues(markov);
					Document dom = DocumentHelper.createDocument();
					Element root = dom.addElement("TCS");
					// 计算markov链的平稳分布
					double[] PI = CalculateDistribution.stationaryDistribution(markov);

					new CollectRoute().collect(markov);
					new BestAssign().assign(markov, root);

					List<Route> routes = markov.getRouteList();
					//获取参数的求解
					List<Route> routeList = markov.getRouteList();
					constraintNameString.clear();
					pros.clear();
					numbers.clear();
					for(Route route : routeList)
					{
						constraintNameString.add(route.getTcSequence());
						pros.add(route.getRouteProbability());
						numbers.add(route.getNumber());
						actualPercentsDoubles.add(route.getActualPercent());
					}

					StepThreeTabelPanel stepThreeTabelPanel = new StepThreeTabelPanel(constraintNameString, actualPercentsDoubles,pros, numbers);
					StepThreeTabelPanel testRoute = new StepThreeTabelPanel(constraintNameString, actualPercentsDoubles,pros, numbers);
					mainFrame.getStepThreeTimeCustomTabbedPane().getTestRoute().removeAll();
					mainFrame.getStepThreeTimeCustomTabbedPane().getTestRoute().add(stepThreeTabelPanel);
					
					OutputFormat format = OutputFormat.createPrettyPrint();
					XMLWriter writer;
					try {
						writer = new XMLWriter(new FileOutputStream(
								mainFrame.getBathRoute() + "/TestCase/"+ModelName+"_自定义.xml"), format);
						writer.write(dom);
						writer.close();
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				    topLabel.removeAll();
				    topLabel.setText("正在生成抽象测试序列信息........");
					List<TCDetail> lists = DataBaseUtil.showTCDetailAll("select * from tcdetail");
					i = 0;
					//生成测试数据
					topLabel.removeAll();
					topLabel.setText("正在生成测试数据信息........");
					mainFrame.getStepThreeNoTimeCustomTabbedPane().getTestData().removeAll();
					mainFrame.getStepThreeNoTimeCustomTabbedPane().getTestData().setLayout(new GridBagLayout());
					CaseTableHeaderPanel caseTableHeaderPanel2 = new CaseTableHeaderPanel();
					mainFrame.getStepThreeNoTimeCustomTabbedPane().getTestData().add(caseTableHeaderPanel2,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
					
					JPanel TestDataPanel = new JPanel();
					TestDataPanel.setLayout(new GridBagLayout());
					CaseTableHeaderPanel caseTableHeaderPanel3 = new CaseTableHeaderPanel();
					TestDataPanel.add(caseTableHeaderPanel3,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
				
					for(TCDetail tcDetail : lists)
					{
						StepThreeTabelPanel testTabelPanel = new StepThreeTabelPanel(tcDetail.getTestCase(),2,mainFrame);
//						StepTwoMatrixPanel stepTwoMatrixPanel = new StepTwoMatrixPanel();
//						stepTwoMatrixPanel.getTitleLabel().setText("测试用例信息");
//						stepTwoMatrixPanel.getTabelPanel().add(testTabelPanel);
						
						StepThreeTabelPanel testTabelPanel2 = new StepThreeTabelPanel(tcDetail.getTestCase(),2,mainFrame);
//						StepTwoMatrixPanel stepTwoMatrixPanel2 = new StepTwoMatrixPanel();
//						stepTwoMatrixPanel2.getTitleLabel().setText("测试用例信息");
//						stepTwoMatrixPanel2.getTabelPanel().add(testTabelPanel2);
						
						TestDataPanel.add(testTabelPanel, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
						
						mainFrame.getStepThreeNoTimeCustomTabbedPane().getTestData().add(testTabelPanel2, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
						mainFrame.getStepThreeNoTimeCustomTabbedPane().getTestData().updateUI();
						mainFrame.getStepThreeNoTimeCustomTabbedPane().getTestDataScroll().getVerticalScrollBar().setValue(
						mainFrame.getStepThreeNoTimeCustomTabbedPane().getTestDataScroll().getVerticalScrollBar().getMaximum());
						i++;
						progressBar.setValue(60 + (int)(((double)i/lists.size())*40));
						mainFrame.renewPanel();
					}
				    
					mainFrame.getStepThreeNoTimeTabbedPane().getTestData().add(new JPanel(), new GBC(0, i+1).setFill(GBC.BOTH).setWeight(1, 1));		
//					mainFrame.getStepThreeNoTimeTabbedPane().getCaseInformation().add(mainPanel);
					mainFrame.getStepThreeNoTimeTabbedPane().getTestData().updateUI();
					
					
					topLabel.removeAll();
					bigDecimal = new BigDecimal(markov.getDeviation());
					String ii = bigDecimal.toPlainString();
					double d = Double.valueOf(ii);
    				topLabel.setText("测试用例生成完成, 共生成"+textField.getText().toString()+"条!" + "可靠性测试用例数据库覆盖率:"+df.format(markov.getDbCoverage())+"  可靠性测试用例生成比率与使用模型实际使用概率平均偏差:"+df.format(d));
    				mainFrame.getStepThreeNoTimeTabbedPane().setSelectedIndex(1);
    				
    				NoTimeTestCaseNode noTimeTestCaseLabel = new NoTimeTestCaseNode(ModelName+"_自定义", mainFrame);
    				quota = "测试用例生成完成, 共生成"+textField.getText().toString()+"条!" + "可靠性测试用例数据库覆盖率:"+df.format(markov.getDbCoverage())+"  可靠性测试用例生成比率与使用模型实际使用概率平均偏差:"+df.format(d);
    				noTimeTestCaseLabel.setQuota(quota);
    				noTimeTestCaseLabel.setTestRoute(testRoute);
    				noTimeTestCaseLabel.setTestDataPanel(TestDataPanel);
    				mainFrame.getStepThreeLeftButton().getTimeCaseNodePanel().insertCustomNodeLabel(noTimeTestCaseLabel,TestDataPanel,testRoute,quota);
    				
    				
					button.setEnabled(true);
	   				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
	   				mainFrame.getStepThreeLeftButton().getTimeExpandLabel().setEnabled(true);
	   				
	   				
				}
				}catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}		
				}
		});
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
private boolean isInt(String str)
{
	   try
	   {
	      Integer.parseInt(str);
	      return true;
	   }
	   catch(NumberFormatException ex){}
	   return false;
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
