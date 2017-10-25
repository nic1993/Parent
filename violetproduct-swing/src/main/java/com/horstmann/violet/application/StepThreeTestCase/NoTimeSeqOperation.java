package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import javax.swing.JTable;
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
import com.horstmann.violet.framework.util.GrabberUtils;

public class NoTimeSeqOperation extends JPanel{
       private JLabel topLabel;
       private JLabel label1;
       private JLabel label2;
       
       private JProgressBar progressBar;
       private JTextField textField;
       private JButton button;
       
       private JPanel gapPanel;
       private MainFrame mainFrame;
       
       private String testSequence;//测试序列
       
       private List<String> paramterNameList;
       private List<String> paramterValueList;
       
       private int i;
       
       private ReadMarkov2 rm;
       private Markov markov;
       private GenerateCases gc;
       private Element root;
       private Document dom;
       private List<Transition> transitions;
       
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
   	   
   	   private BigDecimal bigDecimal;
   	   private DecimalFormat  df = new DecimalFormat();
	   private String pattern = "#0.000";
	   
	   private String ModelName;
	   private String NoTimeMarkovRoute;
	   private String route;
	   
       public NoTimeSeqOperation(MainFrame mainFrame)
       {
    	   this.mainFrame = mainFrame;
    	   init();
    	   this.setBackground(new Color(233,233,233));
    	   this.setLayout(new GridBagLayout());
    	   this.add(topLabel,new GBC(0, 0, 4, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 15, 10, 0));
    	   this.add(progressBar,new GBC(0, 1, 4, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 15, 10, 15));
    	   this.add(label1,new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 15, 10, 5));
    	   this.add(textField,new GBC(1, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 0, 10, 5));
    	   this.add(gapPanel,new GBC(2, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 0, 10, 5));
    	   this.add(button,new GBC(3, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 0, 10, 15));
       }
       private void init()
       {
    	   df.applyPattern(pattern);
    	   topLabel = new JLabel();
    	   label1 = new JLabel("终止条件阈值:");

    	   progressBar = new JProgressBar();
    	   textField = new JTextField();
    	   button = new JButton("开始生成");
    	   gapPanel = new JPanel();
   	   
    	   paramterNameList = new ArrayList<String>();
    	   paramterValueList = new ArrayList<String>();
    	   
    	   textField.setPreferredSize(new Dimension(40,30));
    	   topLabel.setFont(new Font("宋体", Font.PLAIN, 16));
    	   label1.setFont(new Font("宋体", Font.PLAIN, 16));
    	   
    	   
    	   textField.setText("0.1");
    	   textField.setEditable(false);
    	   
    	   progressBar.setPreferredSize(new Dimension(800, 30));
    	   progressBar.setUI(new GradientProgressBarUI());
    	   progressBar.setValue(0);
    	   
    	   NoTimeMarkovRoute = mainFrame.getBathRoute()+"/NoTimeMarkov/";
    	   //初始化线程
    	   initThread();
    	   listen();
       }
       private void listen()
       {
    	   button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				initThread();
				mainthread.start();	
				thread1.start();
//				List<String> lists = new ArrayList<String>();
//				
//                mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().removeAll();
// 				
//				AbstractPagePanel abstractPagePanel = new AbstractPagePanel(lists, mainFrame);
//				mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().add(abstractPagePanel);
//				
//				mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().repaint();
			}
		});
       }
       private void initThread()
       {
    	   maincallable = new Callable<Integer>() {
   			@Override
   			public Integer call() throws Exception {
   				// TODO Auto-generated method stub
   				progressBarIndex = 0;
   				progressBar.setValue(0);
   				progressBar.setValue(progressBarIndex);
   				while (progressBarIndex < 40) {
   					if(task1.isDone())
   					{
   						progressBarIndex++;
   						progressBar.setValue(progressBarIndex);
   						Thread.sleep(100);
   					}
   					else{
   						progressBarIndex++;
   						progressBar.setValue(progressBarIndex);
   						Thread.sleep(500);
   					}
   				}
   			    
   				while(true){
   					if(task1.isDone())
   	   				{
   	   					thread2.start();
   	   					break;
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
				button.setEnabled(false);
   				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(false);
   				mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(false);
   				mainFrame.getStepThreeLeftButton().getNoTimeCase().setEnabled(false);
   				
   				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(false);
   				progressBarIndex = 0;
   				
   				mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().removeAll();
				File files = new File(NoTimeMarkovRoute);
				for(File selectFile : files.listFiles())
				{
					if(selectFile.getName().replace(".xml", "").equals(ModelName))
						route = selectFile.getAbsolutePath();
				}
				
				topLabel.removeAll();
				topLabel.setText("正在读取生成的markov链信息........");
				Thread.sleep(200);
				
   				ReadMarkov2 rm = new ReadMarkov2();
				markov = rm.readMarkov(route);
				
				dom = DocumentHelper.createDocument();
				root = dom.addElement("TCS");
				
				double[] PI = CalculateDistribution.stationaryDistribution(markov);
				
				for(int i = 0;i < PI.length;i++)
				{
					topLabel.removeAll();
					topLabel.setText("markov链的平稳分布:" + PI[i]);
					Thread.sleep(100);
				}

				double similarity = 999991;
				boolean sufficiency = false;
				gc = new GenerateCases();
				boolean flag = true;

				do {
					int numberOfTestCases = gc.generate(markov, root);
					// System.out.println(numberOfTestCases);
					if (flag) {

						sufficiency = isSufficient(markov);
					}
					// 迁移或者状态覆盖百分百

					if (!sufficiency) {
						continue;
					}
                    
					flag = false;
					similarity = CalculateSimilarity.statistic(markov, PI);
					topLabel.removeAll();
					topLabel.setText("markov链的使用链和测试链的相似度:" + similarity);
					Thread.sleep(200);
					
					markov.setDeviation(similarity);
					markov.setActualNum(numberOfTestCases);
                    
				} while (similarity > 0.1);
				}catch (RuntimeException e) {
					// TODO: handle exception
					topLabel.removeAll();
   					topLabel.setText(e.getLocalizedMessage());
					
					button.setEnabled(true);
	   				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
	   				mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(true);
	   				
	   				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
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
					mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().removeAll();
					
					AbstractPagePanel abstractPagePanel = new AbstractPagePanel(gc.abstractTS, mainFrame);
					mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().add(abstractPagePanel);
					
					topLabel.removeAll();
					topLabel.setText("正在生成抽象测试序列.....");

					int index = 500;
					if(gc.abstractTS.size() < 500)
					{
						index = gc.abstractTS.size();
					}
					
					abstractPagePanel.getAbstractPanel().add(new JPanel(), new GBC(0, index).setFill(GBC.BOTH).setWeight(1, 1));
					
					for(int j = 0; j < index;j++)
					{	
						StepThreeTabelPanel testTabelPanel = new StepThreeTabelPanel(gc.abstractTS.get(j), 1,mainFrame);
						
						abstractPagePanel.getAbstractPanel().add(testTabelPanel, new GBC(0, j).setFill(GBC.BOTH).setWeight(1, 0));

						progressBar.setValue(41 + (int)(((double)j/index)*60));
						
						gc.testCasesExtend.get(j);
						//输出激励序列
						String stimulateSequence = "";
						if (j != gc.testCasesExtend.size() - 1) {
							stimulateSequence = stimulateSequence
									+ gc.testCasesExtend.get(j).toString() + "-->>";
							// System.out.print(oneCaseExtend.get(i).toString() + "-->>");
						} else {
							stimulateSequence = stimulateSequence
									+ gc.testCasesExtend.get(j).toString();
							// System.out.println(oneCaseExtend.get(i).toString());
						}
						mainFrame.getOutputinformation().geTextArea().append("激励序列 " + stimulateSequence + "\n");
						int length = mainFrame.getOutputinformation().geTextArea().getText().length(); 
		                mainFrame.getOutputinformation().geTextArea().setCaretPosition(length);
						
						//输出测试路径
						String  testPath = "";
						if (j != gc.testPaths.size() - 1) {
							testPath = gc.testPaths.get(j) + "-->>";
						} else {
							testPath = gc.testPaths.get(j) + ""; 
						}
						mainFrame.getOutputinformation().geTextArea().append("测试路径: " + testPath + "\n\n");
		                int length2 = mainFrame.getOutputinformation().geTextArea().getText().length(); 
		                mainFrame.getOutputinformation().geTextArea().setCaretPosition(length);

						Thread.sleep(10);
						mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().repaint();
						mainFrame.renewPanel();
					}
					abstractPagePanel.getPageTestField().setText("1");					

					button.setEnabled(true);
	   				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
	   				mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(true);
	   				mainFrame.getStepThreeLeftButton().getNoTimeCase().setEnabled(true);
					mainFrame.getNoTimeCaseOperation().setModelName(ModelName);
					
					NoTimeSeqNode noTimeSeqNode = new NoTimeSeqNode(ModelName+"_相似度", mainFrame);
					noTimeSeqNode.setAbstractPagePanel(abstractPagePanel);
					
					mainFrame.getStepThreeLeftButton().getNoTimeSeqNodePanel().insertNodeLabel(noTimeSeqNode, abstractPagePanel);
					mainFrame.getStepThreeLeftButton().getNoTimeSeqNode().repaint();
					
					topLabel.removeAll();
					topLabel.setText("抽象测试序列生成完成，共生成" + gc.abstractTS.size()+"条抽象测试序列");
				}catch (RuntimeException e) {
					// TODO: handle exception
					topLabel.removeAll();
   					topLabel.setText(e.getLocalizedMessage());
					
					button.setEnabled(true);
	   				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
	   				mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(true);	
	   				
	   				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
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
public JLabel getTopLabel() {
	return topLabel;
}
public String getModelName() {
	return ModelName;
}
public void setModelName(String modelName) {
	ModelName = modelName;
}
public JButton getButton() {
	return button;
}
public Markov getMarkov() {
	return markov;
}
public GenerateCases getGc() {
	return gc;
}
public Element getRoot() {
	return root;
}
public Document getDom() {
	return dom;
}
}
