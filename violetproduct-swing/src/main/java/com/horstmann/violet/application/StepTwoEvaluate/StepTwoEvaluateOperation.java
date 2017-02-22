package com.horstmann.violet.application.StepTwoEvaluate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.horstmann.violet.application.StepTwoModelExpand.ProgressUI;
import com.horstmann.violet.application.StepTwoModelExpand.ScenceTabelPanel;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoMatrixPanel;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoTabelPanel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class StepTwoEvaluateOperation extends JPanel{
       private JLabel promptLabel;
       private JProgressBar evaluateBar;
       private JButton evaluateButton;
       private JButton reEvaluateButton;
       private JPanel gapPanel;
       
       private MainFrame mainFrame;
       private Map<String, ScenceTabelPanel> tabelResultMap;
       private Map<String, ScenceTabelPanel> caseTabelMap;
       private String modelName;
       private List list;
       public StepTwoEvaluateOperation(MainFrame mainFrame)
       {
    	   this.mainFrame = mainFrame;
    	   init();
    	   this.setLayout(new GridBagLayout());
    	   this.add(promptLabel, new GBC(0, 0,3,1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 28, 10, 10));
    	   this.add(evaluateBar, new GBC(0, 1,3,1).setFill(GBC.BOTH).setWeight(1, 1).setInsets(0, 28, 10, 39));
    	   this.add(evaluateButton,new GBC(0, 2,1,1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 28, 10, 0));
    	   this.add(gapPanel,new GBC(1, 2,1,1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 0, 10, 0));
    	   this.add(reEvaluateButton,new GBC(2, 2,1,1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 0, 10, 39));
       }
       private void init()
       {
    	   promptLabel = new JLabel();
    	   promptLabel.setFont(new Font("宋体", Font.PLAIN, 16));
    	   promptLabel.setText("当前模型为:Primary。评估验证模型的一致性，即模型的归一性、确定性以及可达性。");
    	   
    	   evaluateBar = new JProgressBar();
    	   evaluateBar.setUI(new ProgressUI(evaluateBar,Color.green));
    	   evaluateBar.setPreferredSize(new Dimension(800,30));
    	   
    	   evaluateButton = new JButton("开始评估");
    	   reEvaluateButton = new JButton("重新评估");
    	   gapPanel = new JPanel();
    	   
    	   buttonListen();
       }
       private void buttonListen()
       {
    	   evaluateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mainFrame.getStepTwoEvaluateTabbedPane().getEvaluateResults().removeAll();
				//首先添加归一性信息
				tabelResultMap = mainFrame.getStepTwoModelOperation().getTabelResultMap();
				caseTabelMap = mainFrame.getStepTwoCaseOperation().getCaseTabelMap();
				modelName = mainFrame.getStepTwoModelOperation().getModel_name();
				StepTwoTabelPanel stepTwoTabelPanel = new StepTwoTabelPanel();
				stepTwoTabelPanel.getTitleLabel().setText(modelName+"模型归一性验证");
				//添加用例中场景迁移信息
				ScenceTabelPanel caseResultPanel = caseTabelMap.get(modelName); //用例场景概率
				StepTwoMatrixPanel stepTwoMatrixPanel = new StepTwoMatrixPanel();
				stepTwoMatrixPanel.getTabelPanel().add(caseResultPanel);
				stepTwoMatrixPanel.getTitleLabel().setText(modelName+"用例模型中场景发生概率之和为1");
				
				ScenceTabelPanel relationResultPanel = tabelResultMap.get(modelName); //用例迁移概率
				StepTwoMatrixPanel relationMatrixPanel = new StepTwoMatrixPanel();
				relationMatrixPanel.getTabelPanel().add(relationResultPanel);
				relationMatrixPanel.getTitleLabel().setText(modelName+"用例模型中后继用例迁移概率之和为1");
				
				stepTwoTabelPanel.getTabelPanel().add(stepTwoMatrixPanel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
				stepTwoTabelPanel.getTabelPanel().add(relationMatrixPanel, new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0));
				
				mainFrame.getStepTwoEvaluateTabbedPane().getEvaluateResults().add(stepTwoTabelPanel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
				
				//添加确定性信息
				list = mainFrame.getStepTwoModelOperation().getVerList();
				
			}
		});
       }
}
