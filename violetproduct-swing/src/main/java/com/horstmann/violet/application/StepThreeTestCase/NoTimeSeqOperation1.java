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

public class NoTimeSeqOperation1 extends JPanel{
       private JLabel topLabel;
       private JLabel label1;
       private JLabel label2;

       private JProgressBar progressBar;
       private JTextField textField;
       private JButton button;
       
       private JPanel gapPanel;
       private MainFrame mainFrame;
       
       private String excitation;  //激励序列
       
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
       
       private ReadMarkov2 rm;
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
	   
	   private String ModelName;
	   private String NoTimeMarkovRoute;
	   private String route;
	   
	   private String quota;
       public NoTimeSeqOperation1(MainFrame mainFrame)
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
    	   topLabel = new JLabel("");
    	   label1 = new JLabel("生成可靠性测试数据条数:");
    	   label2= new JLabel("");
    	   
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
    	   
    	   textField.setPreferredSize(new Dimension(40,30));
    	   textField.setMinimumSize(new Dimension(40,30));
    	   textField.setMaximumSize(new Dimension(40,30));
    	   
    	   topLabel.setFont(new Font("宋体", Font.PLAIN, 16));
    	   label1.setFont(new Font("宋体", Font.PLAIN, 16));
    	   label2.setFont(new Font("宋体", Font.PLAIN, 16));
    	   
    	   progressBar.setUI(new ProgressUI(progressBar, Color.green));
    	   progressBar.setPreferredSize(new Dimension(800, 30));
    	   progressBar.setMinimumSize(new Dimension(800, 30));
    	   progressBar.setMaximumSize(new Dimension(800, 30));
    	   progressBar.setUI(new GradientProgressBarUI());
    	   progressBar.setValue(0);
    	  	   
    	   NoTimeMarkovRoute = mainFrame.getBathRoute()+"/NoTimeMarkov/";
    	   initThread();
    	   listen();
       }
       private void listen()
       {
    	   button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				File files = new File(NoTimeMarkovRoute);
				for(File selectFile : files.listFiles())
				{
					if(selectFile.getName().replace(".xml", "").equals(ModelName))
						route = selectFile.getAbsolutePath();
				}
				ReadMarkov2 rm = new ReadMarkov2();
				try {
				markov = rm.readMarkov(route);
				min = mainFrame.getStepThreeLeftButton().getMin();
				if(isInt(textField.getText().toString()))
				{
					minSeq = Integer.parseInt(textField.getText().toString());
					if(minSeq < min)
					{
						topLabel.removeAll();
						topLabel.setText("请输入正确的测试用例个数!");
					}
					else {
						initThread();
						mainthread.start();
						thread1.start();
					}
				}
				else {
					topLabel.removeAll();
					topLabel.setText("请输入正确的测试用例个数!");
				}
				}catch (Exception e2) {
					// TODO Auto-generated catch block
					topLabel.setText("生成抽象测试用例出错!");
				}		
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
       						Thread.sleep(1500);
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
					try{
					button.setEnabled(false);
       				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(false);
       				mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(false);
       				mainFrame.getStepThreeLeftButton().getNoTimeCase().setEnabled(false);
       				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(false);
       				
      				JPanel mainPanel = new JPanel();
       				mainPanel.setLayout(new GridBagLayout());
      				
      				progressBarIndex = 0;
      				progressBar.setValue(0);
      				mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().removeAll();
    				
      				markov.setTcNumber(minSeq);
      				
      				topLabel.removeAll();
    				topLabel.setText("正在读取生成的markov链信息........");
    				Thread.sleep(200);
                    
					dom = DocumentHelper.createDocument();
					root = dom.addElement("TCS");
					// 计算markov链的平稳分布
					PI = CalculateDistribution.stationaryDistribution(markov);
					
					for(int i = 0;i < PI.length;i++)
					{
						topLabel.removeAll();
						topLabel.setText("markov链的平稳分布:" + PI[i]);
						Thread.sleep(200);
					}
					
					new CollectRoute().collect(markov);
					mainFrame.renewPanel();
				    } catch (RuntimeException e) {
						// TODO: handle exception
                  	topLabel.removeAll();
                  	topLabel.setText("生成抽象测试用例出错!");
     					
     				button.setEnabled(false);
         			mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
         			mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(true);
         			
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
						topLabel.removeAll();
						topLabel.setText("正在生成抽象测试序列(该过程需要较久时间,请耐心等待)....");
						Thread.sleep(150);
						// 获取抽象测试序列
						// showTestSequence(markov);
						List<String> lists = new ArrayList<String>();
						showTestSequence(markov,lists);
						
						AbstractPagePanel abstractPagePanel = new AbstractPagePanel(lists, mainFrame);
						mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().removeAll();
						mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().add(abstractPagePanel);
						
						JPanel seqPanel = new JPanel();
						seqPanel.setLayout(new GridBagLayout());

						int index = 500;
						if(lists.size() < 500)
						{
							index = lists.size();
						}
						
						abstractPagePanel.getAbstractPanel().add(new JPanel(), new GBC(0, index).setFill(GBC.BOTH).setWeight(1, 1));
						
						for(int k = 0;k < index;k++){
//							Route r = markov.getRouteList().get(k);

							StepThreeTabelPanel testTabelPanel = new StepThreeTabelPanel(lists.get(k), 1,mainFrame);
//							mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().add(testTabelPanel, new GBC(0, k).setFill(GBC.BOTH).setWeight(1, 0));
							abstractPagePanel.getAbstractPanel().add(testTabelPanel, new GBC(0, k).setFill(GBC.BOTH).setWeight(1, 0));
							abstractPagePanel.getAbstractPanel().repaint();
							mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().repaint();

//							for(int j = 0;j < r.getTransitionList().size();j++){
//								mainFrame.getOutputinformation().geTextArea().append("迁移序列 " + r.getTransitionList().get(j).toString() + "\n");
//								int length = mainFrame.getOutputinformation().geTextArea().getText().length(); 
//				                mainFrame.getOutputinformation().geTextArea().setCaretPosition(length);
//							}

							progressBar.setValue(40 + (int)(((double)(k+1)/index)*60));
							abstractPagePanel.getAbstractPanel().repaint();
							Thread.sleep(10);
						}
						abstractPagePanel.getPageTestField().setText("1");
						mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().removeAll();
						mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().add(abstractPagePanel);
						mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().repaint();

//						for(Route r : markov.getRouteList())
//						{
//							mainFrame.getOutputinformation().geTextArea().append("路径测试序列 " + r.getTcSequence() + " 路径概率: " + r.getRouteProbability() + " 固定测试用例个数时，此路径所占个数 " + r.getNumber()+ "\n");
//							int length = mainFrame.getOutputinformation().geTextArea().getText().length(); 
//			                mainFrame.getOutputinformation().geTextArea().setCaretPosition(length);
//			                
//			                progressBar.setValue(90 + (int)(((double)(j+1)/index)*10));
//							abstractPagePanel.getAbstractPanel().repaint();
//							Thread.sleep(10);
//							j++;
//						}
						
						button.setEnabled(true);
	       				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
	       				mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(true);
	       				mainFrame.getStepThreeLeftButton().getNoTimeCase().setEnabled(true);
						mainFrame.getNoTimeCaseOperation1().setModelName(ModelName);
						
						NoTimeSeqNode noTimeSeqNode = new NoTimeSeqNode(ModelName+"_自定义", mainFrame);
						noTimeSeqNode.setAbstractPagePanel(abstractPagePanel);
						noTimeSeqNode.setQuota("抽象测试序列生成完成，共生成" + lists.size()+"条抽象测试序列");
						
						mainFrame.getStepThreeLeftButton().getNoTimeSeqNodePanel().insertNodeLabel(noTimeSeqNode, abstractPagePanel);
						mainFrame.getStepThreeLeftButton().getNoTimeSeqNode().repaint();
						
						topLabel.removeAll();
						topLabel.setText("抽象测试序列生成完成，共生成" + lists.size()+"条抽象测试序列");
						
						mainFrame.renewPanel();
				    } catch (RuntimeException e) {
						// TODO: handle exception
                  	    topLabel.removeAll();
     					topLabel.setText("生成抽象测试用例出错!");
     					
     					button.setEnabled(true);
         				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
         				mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(true);
         				
         				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
         				mainFrame.renewPanel();
					}
					return 1;
				}
			};
			task2 = new FutureTask<>(callable2);
			thread2 = new Thread(task2);
       }
       
    // 获取所有的抽象测试序列mode2
    		private static void showTestSequence(Markov markov,List<String> lists) {
    			for (Route r : markov.getRouteList()) {

    				String testSequence = "";
    				for (int i = 0; i < r.getTransitionList().size(); i++) {
    					if (i != r.getTransitionList().size() - 1) {
    						testSequence = testSequence
    								+ r.getTransitionList().get(i).getName() + "-->>";
    						// System.out.print(oneCaseExtend.get(i).toString() +
    						// "-->>");
    					} else {
    						testSequence = testSequence
    								+ r.getTransitionList().get(i).getName();
    						// System.out.println(oneCaseExtend.get(i).toString());
    					}
    				}
    				r.setTcSequence(testSequence);
    				if(r.getNumber()==0){
    					lists.add(testSequence);
    				}

    				for (int i = 0; i < r.getNumber(); i++) {
    					// 显示抽象测试序列testSequence至列表
    					lists.add(testSequence);
    				}
    			}
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
public Markov getMarkov() {
	return markov;
}
public Document getDom() {
	return dom;
}
public Element getRoot() {
	return root;
}
public double[] getPI() {
	return PI;
}
public int getMinSeq() {
	return minSeq;
}
}
