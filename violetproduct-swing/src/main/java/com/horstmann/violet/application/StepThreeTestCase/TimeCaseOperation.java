package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
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
import com.horstmann.violet.application.gui.util.yangjie.ReadMarkov2;
import com.horstmann.violet.application.gui.util.yangjie.State;
import com.horstmann.violet.application.gui.util.yangjie.Stimulate;
import com.horstmann.violet.application.gui.util.yangjie.TCDetail;
import com.horstmann.violet.application.gui.util.yangjie.Transition;
import com.horstmann.violet.application.menu.util.zhangjian.Database.DataBaseUtil;

public class TimeCaseOperation extends JPanel{
       private JLabel topLabel;
       private JLabel label1;
       private JLabel label2;
       
       private JProgressBar progressBar;
       private JTextField textField;
       private JButton button;
       
       private JPanel gapPanel;
       private MainFrame mainFrame;
       
       private String testSequence;//��������
       private String excitation;  //��������
       private String testCase;
       
       private List<String> paramterNameList;
       private List<String> paramterValueList;
       
       private ReadMarkov2 rm;
       private Markov markov;
       private List<Transition> transitions;
       private int progressBarIndex;
       private Callable<Integer> maincallable;
       private FutureTask<Integer> maintask;
   	   private Thread mainthread; 
   	   
   	   private BigDecimal bigDecimal;
   	   private DecimalFormat  df = new DecimalFormat();
	   private String pattern = "#0.00000000";
       
       private int i ;
       private String ModelName;
       private String TimeMarkovRoute;
       private String route;
       
       private String quota;
       public TimeCaseOperation(MainFrame mainFrame)
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
    	   topLabel = new JLabel("���������������");
    	   label1 = new JLabel("��ֹ������ֵ:");

    	   
    	   progressBar = new JProgressBar();
    	   
    	   textField = new JTextField();
    	   button = new JButton("��ʼ����");
    	   gapPanel = new JPanel();

    	   paramterNameList = new ArrayList<String>();
    	   paramterValueList = new ArrayList<String>();
    	   
    	   textField.setPreferredSize(new Dimension(30,30));
    	   topLabel.setFont(new Font("����", Font.PLAIN, 16));
    	   label1.setFont(new Font("����", Font.PLAIN, 16));
    	   
    	   progressBar.setPreferredSize(new Dimension(800, 30));
    	   progressBar.setUI(new GradientProgressBarUI());
    	   progressBar.setValue(0);
    	   
    	   TimeMarkovRoute = mainFrame.getBathRoute()+"/ExtendMarkov/";
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
//				mainFrame.getStepThreeTimeTabbedPane().getCaseResults().removeAll();
//				mainFrame.getStepThreeTimeTabbedPane().getCaseInformation().removeAll();
//				mainFrame.getStepThreeTimeTabbedPane().updateUI();
//				File files = new File(TimeMarkovRoute);
//				for(File selectFile : files.listFiles())
//				{
//					if(selectFile.getName().contains(ModelName))
//						route = selectFile.getAbsolutePath();
//				}
//				ReadMarkov2 rm = new ReadMarkov2();
//				try {
//					Markov markov = rm.readMarkov(route);
//					Calculate.getAllTransValues(markov);
//
//					// ����һ��document�������ڴ洢��������
//					Document dom = DocumentHelper.createDocument();
//					Element root = dom.addElement("TCS");
//					// ����markov����ƽ�ȷֲ�
//					double[] PI = CalculateDistribution.stationaryDistribution(markov);
//
//					double similarity = 999991;
//					boolean sufficiency = false;
//					GenerateCases gc = new GenerateCases();
//					boolean flag = true;
//
//					do {
//						int numberOfTestCases = gc.generate(markov, root);
//						// System.out.println(numberOfTestCases);
//						if (flag) {
//
//							sufficiency = isSufficient(markov);
//						}
//
//						if (!sufficiency) {
//							continue;
//						}
//						flag = false;
//						// similarity = CalculateSimilarity.statistic_1(markov);
//						similarity = CalculateSimilarity.statistic(markov, PI);
//
//						System.out.println("\n��ǰʹ�����Ͳ����������ƶ�Ϊ:" + similarity);
//						System.out.println("\n��ǰ���ɵĲ��������Ͳ���·���ĸ���:" + numberOfTestCases
//								+ "\n\n");
//
//					} while (similarity > 0.001);
//
//					// WriteTestCases.writeCases(gc.getTestCases());
//
//					for (double d : PI) {
//						System.out.print(d + "  ");
//					}
//				// ���洢�ò���������document����д��XML�ļ�
//				OutputFormat format = OutputFormat.createPrettyPrint();
//				XMLWriter writer = new XMLWriter(new FileOutputStream(
//						mainFrame.getBathRoute()+"/TestCase/"+ModelName+".xml"), format);
//				writer.write(dom);
//				writer.close();
//                
//				//������������
//				List<TCDetail> lists = DataBaseUtil.showTCDetailAll("select * from tcdtail");
//				i = 0;
//				for(TCDetail tcDetail : lists)
//				{
//					StepThreeTabelPanel testTabelPanel = new StepThreeTabelPanel(tcDetail.getTestSequence(), tcDetail.getStimulateSequence(), tcDetail.getTestCase());
//					StepTwoMatrixPanel stepTwoMatrixPanel = new StepTwoMatrixPanel();
//					stepTwoMatrixPanel.getTitleLabel().setText("����������Ϣ");
//					stepTwoMatrixPanel.getTabelPanel().add(testTabelPanel);
//					mainFrame.getStepThreeTimeTabbedPane().getCaseInformation().add(stepTwoMatrixPanel, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
//					i++;
//				}
//				
//				mainFrame.getStepThreeTimeTabbedPane().getCaseInformation().add(new JPanel(), new GBC(0, i+1).setFill(GBC.BOTH).setWeight(1, 1));
//				//��ȡ���������
//				List<Transition> transitions = markov.getTransitions();
//				for(Transition transition : transitions)
//				{
//					Stimulate stimulate = transition.getStimulate();
//					List<Parameter> parameters = stimulate.getParameters();
//					for(Parameter parameter: parameters)
//					{
//						paramterNameList.add(transition.getName());
//						List<String> values = parameter.getValues();
//						String lastValue = parameter.getName()+"=[";
//						for(int i = 0;i < values.size();i++)
//						{
//							if(i == 0)
//							    lastValue += values.get(i).toString();
//							else {
//								lastValue += ","+values.get(i).toString();
//								}
//							}
//							lastValue += "]";
//							paramterValueList.add(lastValue);
//						}
//					}
//					StepThreeTabelPanel stepThreeTabelPanel = new StepThreeTabelPanel(paramterNameList, paramterValueList);
//					mainFrame.getStepThreeTimeTabbedPane().getCaseResults().add(stepThreeTabelPanel);
//					
//				} catch (Exception e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
			}
		});
       }
       private void initThread()
       {
    	   maincallable = new Callable<Integer>() {
   			@Override
   			public Integer call() throws Exception {
   				// TODO Auto-generated method stub
                button.setEnabled(false);
   				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(false);
   				mainFrame.getStepThreeLeftButton().getTimeExpandLabel().setEnabled(false);
   				
   				JPanel mainPanel = new JPanel();
   				mainPanel.setLayout(new GridBagLayout());
   				
   				mainFrame.getStepThreeTimeTabbedPane().getAbstractSequence().removeAll();
				mainFrame.getStepThreeTimeTabbedPane().getTestData().removeAll();
				
				System.out.println("modelname: " + ModelName);
				File files = new File(TimeMarkovRoute);
				for(File selectFile : files.listFiles())
				{
					if(selectFile.getName().contains(ModelName))
						route = selectFile.getAbsolutePath();
				}

   				progressBarIndex = 0;
   				
   				ReadMarkov2 rm = new ReadMarkov2();
				Markov markov = rm.readMarkov(route);
				
				topLabel.removeAll();
				topLabel.setText("���ڶ�ȡ:"+ModelName+"�ļ�..........");
				while (progressBarIndex < 10) {
					progressBar.setValue(progressBarIndex++);
					Thread.sleep(100);
				}
				Calculate.getAllTransValues(markov);
				topLabel.removeAll();
				topLabel.setText("���ڼ���"+ModelName+"�м�����Ϣ..........");
				while (progressBarIndex < 15) {
					progressBar.setValue(progressBarIndex++);
					Thread.sleep(100);
				}
				// ����һ��document�������ڴ洢��������
				Document dom = DocumentHelper.createDocument();
				Element root = dom.addElement("TCS");
				// ����markov����ƽ�ȷֲ�
				double[] PI = CalculateDistribution.stationaryDistribution(markov);
				
				topLabel.removeAll();
				topLabel.setText("���ڼ���markov����ƽ�ȷֲ�..........");
				while (progressBarIndex < 25) {
					progressBar.setValue(progressBarIndex++);
					Thread.sleep(100);
				}
				double similarity = 999991;
				boolean sufficiency = false;
				GenerateCases gc = new GenerateCases();
				boolean flag = true;

				do {
					int numberOfTestCases = gc.generate(markov, root);
					// System.out.println(numberOfTestCases);
					if (flag) {
						sufficiency = isSufficient(markov);
					}
					if (!sufficiency) {
						continue;
					}
					flag = false;
					// similarity = CalculateSimilarity.statistic_1(markov);
					similarity = CalculateSimilarity.statistic(markov, PI);
					topLabel.removeAll();
					topLabel.setText("��ǰʹ�����Ͳ����������ƶ�Ϊ:" + similarity);
					while (progressBarIndex < 30) {
						progressBar.setValue(progressBarIndex++);
						Thread.sleep(100);
					}
				} while (similarity > 0.001);

			// ���洢�ò���������document����д��XML�ļ�
				
			
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter writer = new XMLWriter(new FileOutputStream(
					mainFrame.getBathRoute()+"\\TestCase\\"+ModelName+"_���ƶ�.xml"), format);
			writer.write(dom);
			writer.close();
			
//			topLabel.removeAll();
//			topLabel.setText("�������ɲ��������еļ�����Ϣ........");
//			while (progressBarIndex < 45) {
//				progressBar.setValue(progressBarIndex++);
//				Thread.sleep(100);
//			}
//			transitions = markov.getTransitions();
//			for(Transition transition : transitions)
//			{
//				Stimulate stimulate = transition.getStimulate();
//				List<Parameter> parameters = stimulate.getParameters();
//				for(Parameter parameter: parameters)
//				{
//					paramterNameList.add(transition.getName());
//					List<String> values = parameter.getValues();
//					String lastValue = parameter.getName()+"=[";
//					for(int i = 0;i < values.size();i++)
//					{
//						if(i == 0)
//						    lastValue += values.get(i).toString();
//						else {
//							lastValue += ","+values.get(i).toString();
//							}
//						}
//						lastValue += "]";
//						paramterValueList.add(lastValue);
//					}
//				}
//				StepThreeTabelPanel stepThreeTabelPanel = new StepThreeTabelPanel(paramterNameList, paramterValueList);
//				mainFrame.getStepThreeTimeTabbedPane().getAbstractSequence().removeAll();
//				mainFrame.getStepThreeTimeTabbedPane().getAbstractSequence().add(stepThreeTabelPanel);
				
		    topLabel.removeAll();
		    topLabel.setText("�������ɳ������������Ϣ........");
			List<TCDetail> lists = DataBaseUtil.showTCDetailAll("select * from tcdetail");		
			i = 0;
			//���ɲ�������
			topLabel.removeAll();
			topLabel.setText("�������ɲ���������Ϣ........");
			mainFrame.getStepThreeNoTimeTabbedPane().getTestData().removeAll();
			mainFrame.getStepThreeNoTimeTabbedPane().getTestData().setLayout(new GridBagLayout());
			CaseTableHeaderPanel caseTableHeaderPanel2 = new CaseTableHeaderPanel();
			mainFrame.getStepThreeNoTimeTabbedPane().getTestData().add(caseTableHeaderPanel2,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
			
			JPanel TestDataPanel = new JPanel();
			TestDataPanel.setLayout(new GridBagLayout());
			CaseTableHeaderPanel caseTableHeaderPanel3 = new CaseTableHeaderPanel();
			TestDataPanel.add(caseTableHeaderPanel3,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
		
			for(TCDetail tcDetail : lists)
			{
				StepThreeTabelPanel testTabelPanel = new StepThreeTabelPanel(tcDetail.getTestCase(),2,mainFrame);
//				StepTwoMatrixPanel stepTwoMatrixPanel = new StepTwoMatrixPanel();
//				stepTwoMatrixPanel.getTitleLabel().setText("����������Ϣ");
//				stepTwoMatrixPanel.getTabelPanel().add(testTabelPanel);
				
				StepThreeTabelPanel testTabelPanel2 = new StepThreeTabelPanel(tcDetail.getTestCase(),2,mainFrame);
//				StepTwoMatrixPanel stepTwoMatrixPanel2 = new StepTwoMatrixPanel();
//				stepTwoMatrixPanel2.getTitleLabel().setText("����������Ϣ");
//				stepTwoMatrixPanel2.getTabelPanel().add(testTabelPanel2);
				
				TestDataPanel.add(testTabelPanel, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
				
				mainFrame.getStepThreeNoTimeTabbedPane().getTestData().add(testTabelPanel2, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
				mainFrame.getStepThreeNoTimeTabbedPane().getTestData().updateUI();
				mainFrame.getStepThreeNoTimeTabbedPane().getTestDataScroll().getVerticalScrollBar().setValue(
				mainFrame.getStepThreeNoTimeTabbedPane().getTestDataScroll().getVerticalScrollBar().getMaximum());
				i++;
				progressBar.setValue(60 + (int)(((double)i/lists.size())*40));
				mainFrame.renewPanel();
			}
		    
			mainFrame.getStepThreeNoTimeTabbedPane().getTestData().add(new JPanel(), new GBC(0, i+1).setFill(GBC.BOTH).setWeight(1, 1));		
//			mainFrame.getStepThreeNoTimeTabbedPane().getCaseInformation().add(mainPanel);
			mainFrame.getStepThreeNoTimeTabbedPane().getTestData().updateUI();
			mainFrame.renewPanel();
				
				
				topLabel.removeAll();
				bigDecimal = new BigDecimal(markov.getDeviation());
				String ii = bigDecimal.toPlainString();
				double d = Double.valueOf(ii);
				topLabel.setText("����������Ϣ�������,������"+lists.size() + "������������"+"  �ɿ��Բ����������ɱ�����ʹ��ģ��ʵ��ʹ�ø���ƽ��ƫ��:"+df.format(d));
                mainFrame.getStepThreeTimeTabbedPane().setSelectedIndex(1);
				
				NoTimeTestCaseNode noTimeTestCaseLabel = new NoTimeTestCaseNode(ModelName+"_���ƶ�", mainFrame);
				quota = "����������Ϣ�������,������"+lists.size() + "������������"+"  �ɿ��Բ����������ɱ�����ʹ��ģ��ʵ��ʹ�ø���ƽ��ƫ��:"+df.format(d);
				noTimeTestCaseLabel.setQuota(quota);
				noTimeTestCaseLabel.setTestDataPanel(TestDataPanel);
				
				mainFrame.getStepThreeLeftButton().getTimeCaseNodePanel().insertNodeLabel(noTimeTestCaseLabel,TestDataPanel, quota);
				
				button.setEnabled(true);
   				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
   				mainFrame.getStepThreeLeftButton().getTimeExpandLabel().setEnabled(true);
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