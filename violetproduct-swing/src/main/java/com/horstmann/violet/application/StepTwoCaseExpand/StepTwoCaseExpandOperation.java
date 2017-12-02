package com.horstmann.violet.application.StepTwoCaseExpand;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.jws.soap.SOAPBinding.Use;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.plaf.ProgressBarUI;
import javax.swing.text.html.ImageView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import antlr.collections.impl.Vector;
import cn.edu.hdu.lab.config.StaticConfig;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceIsogenySD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceSD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceUCRelation;
import cn.edu.hdu.lab.service.interfaces.Work;
import cn.edu.hdu.lab.service.sd2tmc.WorkImpl;

import com.horstmann.violet.application.StepTwoModelExpand.ExchangeNodeLabel;
import com.horstmann.violet.application.StepTwoModelExpand.ExpandNodeLabel;
import com.horstmann.violet.application.StepTwoModelExpand.GradientProgressBarUI;
import com.horstmann.violet.application.StepTwoModelExpand.ProgressUI;
import com.horstmann.violet.application.StepTwoModelExpand.ScenceTabelPanel;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoMatrixPanel;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoTabelPanel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.menu.FileMenu;
import com.mysql.fabric.xmlrpc.base.Array;

public class StepTwoCaseExpandOperation extends JPanel{
	private JLabel numberLabel;
	private JTextField numberTextField;
	private JButton startExpandButton;
	private JButton startVerificationButton;
	private JProgressBar verificationProgressBar;
	private JPanel rightpanel;
	private MainFrame mainFrame;
	private JPanel otherPanel; 
	private StepTwoCaseExpandTabbedPane stepTwoCaseExpandTabbedPane; 
	private JLabel toplabel;
	private JPanel labelPanel;
	private List<InterfaceIsogenySD> IISDList;
	private List<String> relations;  
	private List<Double> relationsData;
	private List<StepTwoMatrixPanel> MatrixPanels;  
	private List<StepTwoMatrixPanel> EvaluateMatrixPanels;  
	private JPanel MatrixPanel;
	private List<double[][]> tableDatas;
	private Work worker;
	private int number;
	private String Model_Name;
	private boolean isFinish = false;
	
//	private Map<String, ScenceTabelPanel> caseTabelMap;
    private CaseExpandNodePanel caseExpandNodePanel;
	
	private boolean isNeedExpand;
	private int progressBarIndex;
    private int stepSum = 2;
    private int step;
    
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
	public StepTwoCaseExpandOperation(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;			
		stepTwoCaseExpandTabbedPane = mainFrame.getStepTwoCaseExpandTabbedPane();
		this.setBackground(new Color(233,233,233));
		init();
	}
	public void init()
	{
		initComponent();
	    GridBagLayout layout = new GridBagLayout();
	    this.setLayout(layout);
		this.add(numberLabel);
		this.add(numberTextField);
		this.add(startExpandButton);
		this.add(startVerificationButton);
		this.add(verificationProgressBar);
		this.add(rightpanel);
		this.add(otherPanel);
		
		layout.setConstraints(numberLabel, new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(20, 15, 10, 2));
		layout.setConstraints(numberTextField, new GBC(1, 0, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(20, 2, 10, 10));
		layout.setConstraints(startExpandButton, new GBC(2, 0, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(20,10,10,10));
		layout.setConstraints(startVerificationButton, new GBC(3, 0, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(20,10,10,10));
		layout.setConstraints(verificationProgressBar, new GBC(4, 0, 1, 1).setFill(GBC.BOTH).setWeight(1, 1).setInsets(20,10,10,0));
		layout.setConstraints(otherPanel, new GBC(0, 1, 5, 1).setFill(GBC.BOTH).setWeight(1, 1).setInsets(10, 15, 10, 0));
	}
	public void initComponent()
	{
		MatrixPanels = new ArrayList<StepTwoMatrixPanel>();
		EvaluateMatrixPanels = new ArrayList<StepTwoMatrixPanel>();
//		caseTabelMap = new HashMap<String, ScenceTabelPanel>();
		caseExpandNodePanel = new CaseExpandNodePanel(mainFrame);
		
		relations = new ArrayList<String>();
		relationsData = new ArrayList<Double>();
		tableDatas = new ArrayList<double[][]>();
		numberLabel = new JLabel("用户数:");
		numberLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		
		numberTextField = new JTextField();
		numberTextField.setFont(new Font("宋体", Font.PLAIN, 16));
		numberTextField.setPreferredSize(new Dimension(30,30));
		numberTextField.setMinimumSize(new Dimension(100, 30));
		numberTextField.setMaximumSize(new Dimension(100, 30));
		numberTextField.addCaretListener(new TextFieldInputListener());
		
		startExpandButton = new JButton("开始扩展");
		startVerificationButton = new JButton("开始验证");
		startVerificationButton.setEnabled(false);
		
		verificationProgressBar = new JProgressBar();
		verificationProgressBar.setUI(new ProgressUI(verificationProgressBar,Color.green));
		verificationProgressBar.setPreferredSize(new Dimension(750,30));
		verificationProgressBar.setUI(new GradientProgressBarUI());
		
		rightpanel = new JPanel();
		JPanel panel = new JPanel();
		panel.setBackground(new Color(233,233,233));
		rightpanel.add(panel,new GBC(1,0,2,1).setFill(GBC.BOTH).setWeight(1, 1));
		otherPanel = new JPanel()
		{
			public void paint(Graphics g){
	            super.paint(g);
	            java.awt.Rectangle rect = this.getBounds();
	            int width = (int) rect.getWidth() - 1;
	            int height = (int) rect.getHeight() - 1;
	            Graphics2D g2 = (Graphics2D)g;
	            g2.setStroke(new BasicStroke(2f));
	            g2.setColor(new Color(188,188,188));
	            g2.drawLine(0, 0, width - 20, 0);
	          }
		};
		
		toplabel = new JLabel();
		toplabel.setFont(new Font("宋体", Font.PLAIN, 16));
		labelPanel = new JPanel();
		
		otherPanel.setLayout(new GridBagLayout());
		otherPanel.add(toplabel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 0, 0, 0));
		otherPanel.add(labelPanel,new GBC(1, 0).setFill(GBC.BOTH).setWeight(1, 0));
		buttonListen();

	}
	private void initThread()
	{
		startExpandButton.setEnabled(false);
		startVerificationButton.setEnabled(false);
		
		mainFrame.getsteponeButton().getExpandModelLabel().setEnabled(false);
		
		progressBarIndex = 0;
		verificationProgressBar.setValue(0);
		step = 1;
		maincallable = new Callable<Integer>() {
  			@Override
  			public Integer call() throws Exception {
  				// TODO Auto-generated method stub
  				while (progressBarIndex < 100) {
  					if(progressBarIndex == (int)((double)100/stepSum)*step)
  					{
  						if(futuretasklist.get(step-1).isDone()){
							step++;
							progressBarIndex++;
							verificationProgressBar.setValue(verificationProgressBar.getValue()+1);
							threadlist.get(step - 1).start();
						}		
  					}
  					else if( !futuretasklist.get(step-1).isDone()){
							progressBarIndex++;
							verificationProgressBar.setValue(verificationProgressBar.getValue()+1);	
							Thread.sleep(100);
					}
  					else {
  						progressBarIndex++;
						verificationProgressBar.setValue(verificationProgressBar.getValue()+1);	
						Thread.sleep(10);
					}
  					Thread.sleep(10); 
				}
  				
  				startExpandButton.setEnabled(true);
				mainFrame.getsteponeButton().getExpandModelLabel().setEnabled(true);
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
					toplabel.removeAll();
				    toplabel.setText("正在初始化数据....");
				    Thread.sleep(500);

				    stepTwoCaseExpandTabbedPane.getValidationResults().removeAll();
				    stepTwoCaseExpandTabbedPane.getValidationResults().updateUI();
				    MatrixPanels.clear();
				    EvaluateMatrixPanels.clear();				
				    
					for(InterfaceIsogenySD interfaceIsogenySD : IISDList)
					{
						List<InterfaceSD> ISDList = interfaceIsogenySD.getISDList();
						relations.clear();
						relationsData.clear();
						tableDatas.clear();
						if(ISDList.size() == 1)
						{						
							relations.add(ISDList.get(0).getName());
							ISDList.get(0).setPro(1.0);
							relationsData.add(1.0);
							
							toplabel.removeAll();
						    toplabel.setText("正在计算" + ISDList.get(0).getName()+"场景发生概率....");
						    
						    StepTwoMatrixPanel stepTwoMatrixPanel = new StepTwoMatrixPanel(mainFrame);
							stepTwoMatrixPanel.getTitleLabel().setText("用例名称: "+ interfaceIsogenySD.getUcName());
							
							StepTwoMatrixPanel stepTwoMatrixPanel1 = new StepTwoMatrixPanel(mainFrame);
							stepTwoMatrixPanel1.getTitleLabel().setText("用例名称: "+interfaceIsogenySD.getUcName());
						    
						    ScenceTabelPanel scenceTabelPanel = new ScenceTabelPanel(relations, relationsData, 2, mainFrame);
						    stepTwoMatrixPanel.getTabelPanel().add(scenceTabelPanel);
						    MatrixPanels.add(stepTwoMatrixPanel);
						    
						    ScenceTabelPanel scenceTabelPanel1 = new ScenceTabelPanel(relations, relationsData, 2, mainFrame);
						    stepTwoMatrixPanel1.getTabelPanel().add(scenceTabelPanel1);
						    EvaluateMatrixPanels.add(stepTwoMatrixPanel1);
						    Thread.sleep(200);
						    
						}
						else 
						{    
							for(InterfaceSD interfaceSD:ISDList)
							{
								relations.add(interfaceSD.getName());
							}
						   	 for(int i = 0;i <number;i++)
						   	 {
						   		 int location = getplace(interfaceIsogenySD.getUcName());
						   		 if(location != -1)
						   		 {
						   			JPanel panel = ((StepTwoTabelPanel) stepTwoCaseExpandTabbedPane.getCaseExpandPanel().getComponent(i)).getTabelPanel();
						   			JPanel tabelPanel = ((StepTwoMatrixPanel)panel.getComponent(location)).getTabelPanel();
						   			JTable table = ((CaseExpandTable)tabelPanel.getComponent(0)).getTable();
						   			int rows = table.getRowCount();
						   			int columns = table.getColumnCount();
						   			double a[][] = new double[rows][columns-1];
						   			for(int row = 0;row < rows;row++)
						   			{
						   				for(int column = 1;column < columns;column++)
						   				{
						   					if(!isDouble(table.getValueAt(row, column).toString()))
						   					{
						   						toplabel.removeAll();
						   						toplabel.setText("计算扩展矩阵出错,请检查填写的扩展矩阵!");
											    startVerificationButton.setEnabled(true);
											    startExpandButton.setEnabled(true);
											    
											    thread2.stop();
						   					    thread1.stop();
											    mainthread.interrupt();
											    verificationProgressBar.setValue(0);
											    mainFrame.renewPanel();
											    break;
						   					}
						   					else {
						   						a[row][column-1] = Double.parseDouble(table.getValueAt(row, column).toString());
											}
						   				}
						   				
						   			}
						   			tableDatas.add(a);
						   		 }
						   	 }
						}

						if(tableDatas.size() > 0)
						{
							List list=worker.calculateProb(tableDatas); 
							double[] datas = (double[]) list.get(1);
							for(int k = 0; k < datas.length;k++)
							{
								ISDList.get(k).setPro(datas[k]);
								relationsData.add(datas[k]);
								
								toplabel.removeAll();
							    toplabel.setText("正在计算" + ISDList.get(k).getName()+"场景发生概率.....");
							    Thread.sleep(200);
							    mainFrame.renewPanel();
							}
							
							StepTwoMatrixPanel stepTwoMatrixPanel = new StepTwoMatrixPanel(mainFrame);
							stepTwoMatrixPanel.getTitleLabel().setText("用例名称:"+interfaceIsogenySD.getUcName());
							ScenceTabelPanel scenceTabelPanel = new ScenceTabelPanel(relations, relationsData, 2, mainFrame);
							stepTwoMatrixPanel.getTabelPanel().add(scenceTabelPanel);
							MatrixPanels.add(stepTwoMatrixPanel);
							
							StepTwoMatrixPanel stepTwoMatrixPanel1 = new StepTwoMatrixPanel(mainFrame);
							stepTwoMatrixPanel1.getTitleLabel().setText("用例名称:"+interfaceIsogenySD.getUcName());
							ScenceTabelPanel scenceTabelPanel1 = new ScenceTabelPanel(relations, relationsData, 2, mainFrame);
							stepTwoMatrixPanel1.getTabelPanel().add(scenceTabelPanel1);
							EvaluateMatrixPanels.add(stepTwoMatrixPanel1);
							
							tableDatas.clear();
						}
						 mainFrame.renewPanel();
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					toplabel.removeAll();
				    toplabel.setText("场景扩展出错!");
					
					startExpandButton.setEnabled(true);
					mainFrame.getsteponeButton().getExpandModelLabel().setEnabled(true);
				}
				
				return 1;
			}
		};
		task1 = new FutureTask<Integer>(callable1);
		thread1 = new Thread(task1);
		
		callable2 = new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				try {
					toplabel.removeAll();
					toplabel.setText("正在生成场景概率列表....");
					if(relations.size() > 0)
					{
						JPanel panel = new JPanel();
						panel.setLayout(new GridBagLayout());
						MatrixPanel = new JPanel();
						MatrixPanel.setLayout(new GridBagLayout());
						for(int i = 0;i < MatrixPanels.size();i++)
						{
							panel.add(MatrixPanels.get(i),new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
							MatrixPanel.add(EvaluateMatrixPanels.get(i),new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
						}
						panel.add(new JPanel(),new GBC(0, MatrixPanels.size()).setFill(GBC.BOTH).setWeight(1, 1));
						MatrixPanel.add(new JPanel(),new GBC(0, EvaluateMatrixPanels.size()).setFill(GBC.BOTH).setWeight(1, 1));
						
						stepTwoCaseExpandTabbedPane.getValidationResults().removeAll();
						stepTwoCaseExpandTabbedPane.getValidationResults().add(panel);
						stepTwoCaseExpandTabbedPane.getValidationResults().updateUI();
						stepTwoCaseExpandTabbedPane.setSelectedIndex(1);
						
						isFinish = true;
						for(InterfaceIsogenySD interfaceIsogenySD : IISDList)
						{
							List<InterfaceSD> ISDList = interfaceIsogenySD.getISDList();
							for(InterfaceSD interfaceSD : ISDList)
							{
								if(interfaceSD.getPro() <= 0.0 || interfaceSD.getPro() > 1.0)
									isFinish = false;
							}
						}

						if(isFinish == false)
						{
							toplabel.removeAll();
							toplabel.setText("对"+Model_Name+"场景扩展验证不通过,请重新填写扩展矩阵!");
							mainFrame.getStepTwoExpand().getEstimateLabel().setEnabled(false);
							mainFrame.getStepTwoExpand().getExpandCasePanel().repaint();
							
							startExpandButton.setEnabled(true);
							mainFrame.getsteponeButton().getExpandModelLabel().setEnabled(true);
							mainFrame.renewPanel();
						}
						else {
//							caseTabelMap.put(Model_Name, scenceTabelPanel);
							
							toplabel.removeAll();
							toplabel.setText("对"+Model_Name+"场景扩展验证完成,可以对该模型进行一致性验证!");
							mainFrame.getStepTwoExpand().getEstimateLabel().setEnabled(true);
							mainFrame.getStepTwoEvaluateOperation().getEvaluateButton().setEnabled(true);
							
							worker.assignmentPro(IISDList);
							ExpandNodeLabel expandNodeLabel = new ExpandNodeLabel(Model_Name,mainFrame);

							caseExpandNodePanel.insertNodeLabel(expandNodeLabel,panel);
							mainFrame.getsteponeButton().getExpandCasePanel().repaint();
							
							mainFrame.getStepTwoCaseExpandTabbedPane().setSelectedIndex(1);
							
							for(InterfaceIsogenySD interfaceIsogenySD : IISDList)
	    					{
	    						for(InterfaceSD interfaceSD : interfaceIsogenySD.getISDList())
	    						{
	    							
	    							mainFrame.getOutputinformation().geTextArea().append(interfaceSD.getName() + "场景概率: " + interfaceSD.getPro() + "\n");
	    	    					int length = mainFrame.getOutputinformation().geTextArea().getText().length(); 
	    			                mainFrame.getOutputinformation().geTextArea().setCaretPosition(length);
	    						}
	    					}
							mainFrame.renewPanel();
						}		
					}
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					toplabel.removeAll();
				    toplabel.setText("场景扩展出错!");
					
					startExpandButton.setEnabled(true);
					mainFrame.getsteponeButton().getExpandModelLabel().setEnabled(true);
				}
				
				return 1;
			}
		};
		task2 = new FutureTask<>(callable2);
		thread2 = new Thread(task2);
		
		futuretasklist = new ArrayList<FutureTask<Integer>>();
		futuretasklist.add(task1);
		futuretasklist.add(task2);
		
		threadlist = new ArrayList<Thread>();
		threadlist.add(thread1);
		threadlist.add(thread2);
  		
	}
	public void buttonListen()
	{
		startExpandButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					isFinish = false;
					isNeedExpand = false;
					relations.clear();
	                IISDList=mainFrame.getStepTwoModelOperation().getIISDList();//获取用例场景信息
	                worker = mainFrame.getStepTwoModelOperation().getWorker();
	                
	                verificationProgressBar.setValue(0);
	                
	               if(numberTextField.getText().equals(""))
					{ 
						toplabel.removeAll();
						toplabel.setText("请填写正确的用户数量!");
						mainFrame.getStepTwoModelOperation().updateUI();
						mainFrame.renewPanel();
					}
					else {
						stepTwoCaseExpandTabbedPane.getCaseExpandPanel().removeAll();
						number = Integer.parseInt(numberTextField.getText());
	                    int j = 0;
						for(j = 0;j < number;++j){
							int i = 0; //标记位置
							StepTwoTabelPanel stepTwoTabelPanel = new StepTwoTabelPanel(mainFrame);
							stepTwoTabelPanel.getTitleLabel().setText("用户"+(j+1));
							stepTwoTabelPanel.getTabelPanel().setLayout(new GridBagLayout());
							for(InterfaceIsogenySD interfaceIsogenySD : IISDList)
							{
								if(interfaceIsogenySD.getISDList().size() > 1)
								{
									isNeedExpand = true;
									relations.add(interfaceIsogenySD.getUcName());  //添加用例名称
									List<InterfaceSD> ISDList = interfaceIsogenySD.getISDList();
									for(InterfaceSD interfaceSD : ISDList)
									{
										relations.add(interfaceSD.getName()); //添加用例场景名称
									}
									CaseExpandTable scenceTabelPanel = new CaseExpandTable(relations);
									StepTwoMatrixPanel stepTwoMatrixPanel = new StepTwoMatrixPanel(mainFrame);
									stepTwoMatrixPanel.getTitleLabel().setText("用例名称:"+interfaceIsogenySD.getUcName());
									stepTwoMatrixPanel.getTabelPanel().add(scenceTabelPanel);
									stepTwoTabelPanel.getTabelPanel().add(stepTwoMatrixPanel, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
									i++;
									relations.clear();
									mainFrame.renewPanel();
								}
							}
						    stepTwoCaseExpandTabbedPane.getCaseExpandPanel().add(stepTwoTabelPanel,new GBC(0, j).setFill(GBC.BOTH).setWeight(1, 0));
						}
						stepTwoCaseExpandTabbedPane.getCaseExpandPanel().add(new JPanel(), new GBC(0, j).setFill(GBC.BOTH).setWeight(1, 1));
						stepTwoCaseExpandTabbedPane.getCaseExpandPanel().updateUI();
						startVerificationButton.setEnabled(true);
						toplabel.removeAll();
						toplabel.setText(Model_Name+"用例模型进行场景扩展,填写矩阵即对同源场景进行两两对比打分,填写完成后进行验证!");	
						if(isNeedExpand == false)
						{
							stepTwoCaseExpandTabbedPane.getCaseExpandPanel().removeAll();
							toplabel.removeAll();
							toplabel.setText(Model_Name+"模型不需要进行场景扩展，可以直接进行验证"); 
						}
						mainFrame.getStepTwoCaseExpandTabbedPane().setSelectedIndex(0);
						mainFrame.getStepTwoCaseExpandTabbedPane().getValidationResults().removeAll();
						mainFrame.getStepTwoEvaluateTabbedPane().getHomogeneityResults().removeAll();
						mainFrame.getStepTwoExchangeTabbedPane().getExchangeResults().removeAll();
						mainFrame.getStepTwoExchangeTabbedPane().getExchangeResport().removeAll();
						mainFrame.getStepTwoEvaluateOperation().getTopLabel().setText("当前模型为:"+Model_Name);
						mainFrame.getStepTwoExchangeOperation().getToplabel().setText("当前模型为:"+Model_Name);
						
						startExpandButton.setEnabled(false);
						startVerificationButton.setEnabled(true);
						
						mainFrame.getStepTwoExpand().getEstimateLabel().setEnabled(false);
						mainFrame.getStepTwoExpand().getExchangeLabel().setEnabled(false);
						
						mainFrame.renewPanel();
					}
				} catch (Exception e2) {
					// TODO: handle exception
					toplabel.removeAll();
					toplabel.setText("场景扩展出错!");
				}
			}
		});
		startVerificationButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
		    initThread();
		    mainthread.start();
		    thread1.start();
			}
			
		});
	}	
	

	private static boolean isNumeric(String str){ //判断输入的是否为数字函数
		  for (int i = str.length();--i>=0;){    
		   if (!Character.isDigit(str.charAt(i))){ 
		    return false; 
		   } 
		  } 
		  return true; 
		 } 
	private boolean isDouble(String str)
	{
	   try
	   {
	      Double.parseDouble(str);
	      return true;
	   }
	   catch(NumberFormatException ex){}
	   return false;
	}
	private int getplace(String key)
	{
		JPanel panel = ((StepTwoTabelPanel) stepTwoCaseExpandTabbedPane.getCaseExpandPanel().getComponent(0)).getTabelPanel();
		for(int i = 0;i < panel.getComponentCount();i++)
		{
			JLabel titleLabel = ((StepTwoMatrixPanel)panel.getComponent(i)).getTitleLabel();
			if(titleLabel.getText().contains(key))
			{
				return i;
			}
		}
		return -1;
	}
	class TextFieldInputListener implements CaretListener {
		 
	    @Override
	    public void caretUpdate(CaretEvent e) {
	        JTextField textField = (JTextField) e.getSource(); 
	        final String text = textField.getText();
	        if (text.length() == 0) {
	            return;
	        }
	        char ch = text.charAt(text.length() - 1);
	        if (!(ch >= '1' && ch <= '9') 
	               ) { 
	            SwingUtilities.invokeLater(new Runnable() {
	                @Override
	                public void run() {
	                    numberTextField.setText(text.substring(0, text.length() - 1));
	                }
	            });
	        }
	    }
}
	public JPanel getOtherPanel(){
		return otherPanel;
	}
	public List<InterfaceIsogenySD> getIISDList() {
		return IISDList;
	}
	public JPanel getMatrixPanel() {
		return MatrixPanel;
	}
	public String getModel_Name() {
		return Model_Name;
	}
	public void setModel_Name(String model_Name) {
		Model_Name = model_Name;
	}
	public JLabel getToplabel() {
		return toplabel;
	}
	public CaseExpandNodePanel getCaseExpandNodePanel() {
		return caseExpandNodePanel;
	}
	public boolean isFinish() {
		return isFinish;
	}
	public JButton getStartExpandButton() {
		return startExpandButton;
	}
	
}
