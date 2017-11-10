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

public class NoTimeCaseOperation extends JPanel{
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
       private String testCase;
       
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
	   private String pattern = "#0.000000";
	   
	   private String ModelName;
	   private String NoTimeMarkovRoute;
	   private String route;
	   
	   private String quota;
	   
       public NoTimeCaseOperation(MainFrame mainFrame)
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
    	   
    	   textField.setText("0.1");
    	   textField.setEditable(false);
    	   textField.setPreferredSize(new Dimension(40,30));
    	   topLabel.setFont(new Font("宋体", Font.PLAIN, 16));
    	   label1.setFont(new Font("宋体", Font.PLAIN, 16));
    	   
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
   						Thread.sleep(5000);
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
	   				mainFrame.getStepThreeLeftButton().getNoTimeSeq().setEnabled(false);
					
					markov = mainFrame.getNoTimeSeqOperation().getMarkov();
					gc = mainFrame.getNoTimeSeqOperation().getGc();
					root = mainFrame.getNoTimeSeqOperation().getRoot();
					dom = mainFrame.getNoTimeSeqOperation().getDom();
					
	   				progressBarIndex = 0;
					
	   				topLabel.removeAll();
					topLabel.setText("正在生成测试数据信息.....");
					
					RandomCase randomCase = new RandomCase();
					Calculate.getAllTransValues(markov);
					for (int i = 0; i < gc.testCasesExtend.size(); i++) {
						TCDetail.getInstance().setTestSequence(gc.abstractTS.get(i));
						String stimulateSequence = getStimulateSeq(gc.testCasesExtend
								.get(i));
						TCDetail.getInstance().setStimulateSequence(stimulateSequence);
						randomCase.getCase(gc.testCasesExtend.get(i), root);
					}
					
				    OutputFormat format = OutputFormat.createPrettyPrint();
				    XMLWriter writer = new XMLWriter(new FileOutputStream(
					mainFrame.getBathRoute()+"\\TestCase\\"+ModelName+"_Similarity#1.xml"), format);
				    writer.write(dom);
				    writer.close();	
				    
				    mainFrame.renewPanel();
				}catch (RuntimeException e) {
						// TODO: handle exception
	   					topLabel.removeAll();
	   					topLabel.setText(e.getLocalizedMessage());
	   					
	   					mainthread.interrupt();
	   					thread1.interrupt();
	   					thread2.interrupt();
	   					
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
				try{
				List<TCDetail> lists = DataBaseUtil.showTCDetailAll("select * from tcdetail");
				
				mainFrame.getStepThreeNoTimeTabbedPane().getTestData().removeAll();
				CasePagePanel casePagePanel = new CasePagePanel(lists,mainFrame);
				mainFrame.getStepThreeNoTimeTabbedPane().getTestData().add(casePagePanel);
				
				//生成测试数据
				topLabel.removeAll();
				topLabel.setText("正在生成测试数据信息........");
				
				JPanel TestDataPanel = new JPanel();
				TestDataPanel.setLayout(new GridBagLayout());

				int index = 500;
					if(lists.size() < 500)
					{
						index = lists.size();
					}

					casePagePanel.getCasePanel().add(new JPanel(),
							new GBC(0, index).setFill(GBC.BOTH).setWeight(1, 1));
					
						for(int j = 0;j < index;j++){
                    		StepThreeTabelPanel testTabelPanel = new StepThreeTabelPanel(lists.get(j).getTestCase(), 2,
            						mainFrame);
                    		casePagePanel.getCasePanel().add(testTabelPanel,
								new GBC(0, j).setFill(GBC.BOTH).setWeight(1, 0));
                    		
                    		casePagePanel.getCasePanel().repaint();
                    		mainFrame.getStepThreeNoTimeTabbedPane().getTestData().updateUI();
                    		
                    		progressBar.setValue(60 + (int) (((double) (j+1) / 500) * 60));
                    		Thread.sleep(10);
                    		mainFrame.renewPanel();
                    	}
                    	casePagePanel.getPageTestField().setText("1");
					
                mainFrame.getStepThreeNoTimeTabbedPane().getTestData().removeAll();
                mainFrame.getStepThreeNoTimeTabbedPane().getTestData().add(casePagePanel);
				mainFrame.renewPanel();
				
				button.setEnabled(true);
   				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
   				mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(true);
   				mainFrame.getStepThreeLeftButton().getNoTimeSeq().setEnabled(true);
				
				bigDecimal = new BigDecimal(markov.getDeviation());
				String ii = bigDecimal.toPlainString();
				double d = Double.valueOf(ii);
				topLabel.removeAll();
				topLabel.setText("测试用例生成完成, 共生成"+lists.size() + "条测试用例。"+"  可靠性测试用例生成比率与使用模型实际使用概率平均偏差:"+df.format(d));
				
				NoTimeTestCaseNode noTimeTestCaseLabel = new NoTimeTestCaseNode(ModelName+"_相似度", mainFrame);
				quota = "测试用例生成完成, 共生成"+lists.size() + "条测试用例。"+"  可靠性测试用例生成比率与使用模型实际使用概率平均偏差:"+df.format(d);
				noTimeTestCaseLabel.setQuota(quota);
				noTimeTestCaseLabel.setCasePagePanel(casePagePanel);
				mainFrame.getStepThreeLeftButton().getNoTimeCaseNodePanel().insertNodeLabel(noTimeTestCaseLabel,casePagePanel,quota);
				mainFrame.renewPanel();
				}catch (Exception e) {
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
		task2 = new FutureTask<>(callable2);
		thread2 = new Thread(task2);
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

}
