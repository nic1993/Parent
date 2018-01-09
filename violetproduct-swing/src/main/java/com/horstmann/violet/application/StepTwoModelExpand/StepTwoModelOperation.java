
package com.horstmann.violet.application.StepTwoModelExpand;
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
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.jws.soap.SOAPBinding.Use;
import javax.swing.BorderFactory;
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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.plaf.ProgressBarUI;
import javax.swing.text.html.ImageView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.dom4j.DocumentException;

import antlr.collections.impl.Vector;
import cn.edu.hdu.lab.config.StaticConfig;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceIsogenySD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceSD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceUCRelation;
import cn.edu.hdu.lab.service.interfaces.Work;
import cn.edu.hdu.lab.service.sd2tmc.WorkImpl;

import com.horstmann.violet.application.StepOneBuildModel.ModelPanel;
import com.horstmann.violet.application.StepOneBuildModel.Radio;
import com.horstmann.violet.application.gui.DisplayForm;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.menu.FileMenu;
import com.thoughtworks.xstream.mapper.Mapper.Null;

public class StepTwoModelOperation extends JPanel{
	private MainFrame mainFrame;
	private FileMenu fileMenu;
	private JLabel numberLabel;
	private JTextField numberTextField;
	private JButton startExpandButton;
	private JButton startVerificationButton;
	private JProgressBar verificationProgressBar;
	private JPanel rightpanel;
	private JPanel otherPanel; 
	private StepTwoModelExpandTabbedPane stepTwoModelExpandTabbedPane; 
	private JLabel label;
	private JPanel labelPanel;
	private Map<String, List<InterfaceUCRelation>> ucMap; 
	private List verList;
	private List<InterfaceIsogenySD> IISDList;
	
	private List<String> relations;  
	private List<Double> relationsData;
	private List<double[][]> tableDatas;
	private Work worker;
	private int number;
	private String Model_Name;
	private boolean isSameName = false;
	private Map<String, ScenceTabelPanel> tabelResultMap;
	private ScenceTabelPanel evaluatePanel;
	private List<StepTwoMatrixPanel> MatrixPanels; 
	private List<StepTwoMatrixPanel> EvaluateMatrixPanels;  
	private JPanel MatrixPanel;
	private boolean isNeedExpand = false;
	private boolean isFinish;
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
	
	private boolean Alive;
    
	private DecimalFormat df= new DecimalFormat("######0.000000");
	
	private static String EABathRoute;
	private static String VioletBathRoute;
	private static String EAsequenceBathRoute;
	private static String VioletsequenceBathRoute;
	
	private File EAFile;
	private File[] EAFiles;
	private File VioletFile;
	private File[] VioletFiles;
	
	private String currentUcase;
	
	private Map<String, String> UcaseRoute;
	private Map<String, String> SeqRoute;
	
	
	
	public StepTwoModelOperation(MainFrame mainFrame,FileMenu fileMenu)
	{
		this.mainFrame = mainFrame;
		this.fileMenu = fileMenu;
		stepTwoModelExpandTabbedPane = mainFrame.getStepTwoModelExpandTabbedPane();
		this.setBackground(new Color(233,233,233));
		init();
	}
	public void init()
	{
		this.EABathRoute = mainFrame.getBathRoute() + "/UseCaseDiagram/EAXML";
		this.VioletBathRoute = mainFrame.getBathRoute() + "/UseCaseDiagram/VioletXML";
		this.EAsequenceBathRoute = mainFrame.getBathRoute() + "/SequenceDiagram/EAXML/";
		this.VioletsequenceBathRoute = mainFrame.getBathRoute() + "/SequenceDiagram/VioletXML/";
		
		
		this.EAFile = new File(EABathRoute);
		this.VioletFile = new File(VioletBathRoute);
		this.VioletFiles = VioletFile.listFiles();
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
		relations = new ArrayList<String>();
		relationsData = new ArrayList<Double>();
		tableDatas = new ArrayList<double[][]>();
		numberLabel = new JLabel("用户数:");
		numberLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		
		numberTextField = new JTextField();
		numberTextField.setFont(new Font("宋体", Font.PLAIN, 16));
		numberTextField.setPreferredSize(new Dimension(30, 20));
		numberTextField.setMinimumSize(new Dimension(100, 20));
		numberTextField.setMaximumSize(new Dimension(100, 20));
		numberTextField.addCaretListener(new TextFieldInputListener());
		
		startExpandButton = new JButton("开始扩展");
		startVerificationButton = new JButton("开始验证");
		startVerificationButton.setEnabled(false);
		
		verificationProgressBar = new JProgressBar();
		verificationProgressBar.setUI(new GradientProgressBarUI());
		verificationProgressBar.setPreferredSize(new Dimension(750, 20));
		verificationProgressBar.setValue(0);
		verificationProgressBar.setBorder(BorderFactory.createLineBorder(new Color(187,204,187)));
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
	            g2.drawLine(0, 0, width, 0);
	          }
		};
		 
		label = new JLabel();
		label.setFont(new Font("宋体", Font.PLAIN, 16));
		label.setText("请选择需要扩展的模型!");
		labelPanel = new JPanel();

		otherPanel.setLayout(new GridBagLayout());
		otherPanel.add(label, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 0, 0, 0));
		otherPanel.add(labelPanel,new GBC(1, 0).setFill(GBC.BOTH).setWeight(1, 0));
		
		tabelResultMap = new HashMap<String,ScenceTabelPanel>();
		buttonListen();
        
	}
	private void initThread()
	{
		
		progressBarIndex = 0;
		verificationProgressBar.setValue(0);
		step = 1;
		maincallable = new Callable<Integer>() {
  			@Override
  			public Integer call() throws Exception {
  				// TODO Auto-generated method stub
  				startVerificationButton.setEnabled(false);
				startExpandButton.setEnabled(false);
  				while (progressBarIndex < 100) {
  					System.out.println(Alive);
  					if(!Alive)
  					{
  						break;
  					}
  					if(progressBarIndex == (int)((double)100/stepSum)*step)
  					{
  						if(futuretasklist.get(step - 1).isDone()){
							step++;
							progressBarIndex++;
							verificationProgressBar.setValue(progressBarIndex);
							threadlist.get(step - 1).start();
						}		
  					}
  					else if (!futuretasklist.get(step - 1).isDone()) {
  						progressBarIndex++;
						verificationProgressBar.setValue(progressBarIndex);
						Thread.sleep(100);
					}
  					else {
  						progressBarIndex++;
						verificationProgressBar.setValue(progressBarIndex);
						Thread.sleep(10);
					}
  					Thread.sleep(10);
				}
  				return 1;
  			}
  		};
  		maintask = new FutureTask<Integer>(maincallable);
  		mainthread = new Thread(maintask);	
	callable1 = new Callable<Integer>() {

	 @Override
	 public Integer call() throws Exception {
		// TODO Auto-generated method stub
		 try {
			 label.removeAll();
			    label.setText("正在初始化数据....");
			    Thread.sleep(300);
				stepTwoModelExpandTabbedPane.getValidationResults().removeAll();
				stepTwoModelExpandTabbedPane.getValidationResults().updateUI();
				MatrixPanels.clear();
			    EvaluateMatrixPanels.clear();
			    
			    label.removeAll();
			    label.setText("正在计算后继用例迁移关系概率.....");
				for(String key : ucMap.keySet())
				{
					
				    relations.clear();
					relationsData.clear();
					
//				    Thread.sleep(100);
					List<InterfaceUCRelation> interfaceUCRelations = ucMap.get(key);

					if(interfaceUCRelations.size() == 1)
					{
						relations.add(interfaceUCRelations.get(0).getUCRelation());
						interfaceUCRelations.get(0).setUCRelProb(1.000);
						relationsData.add(1.000);
						
						StepTwoMatrixPanel stepTwoMatrixPanel = new StepTwoMatrixPanel(mainFrame);
						stepTwoMatrixPanel.getTitleLabel().setText("执行用例:"+ key);
						
						StepTwoMatrixPanel stepTwoMatrixPanel1 = new StepTwoMatrixPanel(mainFrame);
						stepTwoMatrixPanel1.getTitleLabel().setText("执行用例:"+ key);
					    
					    ScenceTabelPanel scenceTabelPanel = new ScenceTabelPanel(relations, relationsData, 1, mainFrame);
					    stepTwoMatrixPanel.getTabelPanel().add(scenceTabelPanel);
					    MatrixPanels.add(stepTwoMatrixPanel);
					    
					    ScenceTabelPanel scenceTabelPanel1 = new ScenceTabelPanel(relations, relationsData, 1, mainFrame);
					    stepTwoMatrixPanel1.getTabelPanel().add(scenceTabelPanel1);
					    EvaluateMatrixPanels.add(stepTwoMatrixPanel1);
					}
					else 
					{    
						for(InterfaceUCRelation interfaceUCRelation:interfaceUCRelations)
						{
							relations.add(interfaceUCRelation.getUCRelation());
						}
					   	 for(int i = 0;i <number;i++)
					   	 {
					   		 int location = getplace(key);
					   		 if(location != -1)
					   		 {
					   			JPanel panel = ((StepTwoTabelPanel) stepTwoModelExpandTabbedPane.getmodelExpandPanel().getComponent(i)).getTabelPanel();
					   			JPanel tabelPanel = ((StepTwoMatrixPanel)panel.getComponent(location)).getTabelPanel();
					   			JTable table = ((ScenceTabelPanel)tabelPanel.getComponent(0)).getTable();
					   			int rows = table.getRowCount();
					   			int columns = table.getColumnCount();
					   			double a[][] = new double[rows][columns-1];
					   			for(int row = 0;row < rows;row++)
					   			{
					   				for(int column = 1;column < columns;column++)
					   				{
					   					if(!isDouble(table.getValueAt(row, column).toString()))
					   					{
										    
					   						label.removeAll();
					   						label.setText("计算扩展矩阵出错，请检查填写的扩展矩阵!");
					   						
//					   						startExpandButton.setEnabled(true);
					   						startVerificationButton.setEnabled(true);
										    
										    thread2.interrupt();
					   					    thread1.interrupt();
										    Alive = false;
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
							interfaceUCRelations.get(k).setUCRelProb(datas[k]);
							relationsData.add(datas[k]);
						}
						tableDatas.clear();
						stepTwoModelExpandTabbedPane.getValidationResults().updateUI();
						
						StepTwoMatrixPanel stepTwoMatrixPanel = new StepTwoMatrixPanel(mainFrame);
						stepTwoMatrixPanel.getTitleLabel().setText("执行用例:"+key);
						ScenceTabelPanel scenceTabelPanel = new ScenceTabelPanel(relations, relationsData, 1, mainFrame);
						stepTwoMatrixPanel.getTabelPanel().add(scenceTabelPanel);
						MatrixPanels.add(stepTwoMatrixPanel);
						
						StepTwoMatrixPanel stepTwoMatrixPanel1 = new StepTwoMatrixPanel(mainFrame);
						stepTwoMatrixPanel1.getTitleLabel().setText("执行用例:"+key);
						ScenceTabelPanel scenceTabelPanel1 = new ScenceTabelPanel(relations, relationsData, 1, mainFrame);
						stepTwoMatrixPanel1.getTabelPanel().add(scenceTabelPanel1);
						EvaluateMatrixPanels.add(stepTwoMatrixPanel1);
					}
					
					mainFrame.renewPanel();
				}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			label.removeAll();
			label.setText("用例扩展出错，请重新扩展!");
			
			startExpandButton.setEnabled(true);
			startVerificationButton.setEnabled(false);
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
			 label.removeAll();
			  label.setText("正在生成后继用例概率列表.....");
			  if(relations.size() > 0)
			  {
				
				    MatrixPanels.get(0).getTabelPanel().setVisible(true);
				    
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
					
					stepTwoModelExpandTabbedPane.getValidationResults().removeAll();
					stepTwoModelExpandTabbedPane.getValidationResults().add(panel);
					stepTwoModelExpandTabbedPane.getValidationResults().updateUI();
					stepTwoModelExpandTabbedPane.setSelectedIndex(1);
					mainFrame.renewPanel();					

					isFinish = true;
					for(String key : ucMap.keySet()) 
						{
							List<InterfaceUCRelation> interfaceUCRelations = ucMap.get(key);
							for(InterfaceUCRelation interfaceUCRelation : interfaceUCRelations){	

		    					if(interfaceUCRelation.getUCRelProb() <= 0.0 || interfaceUCRelation.getUCRelProb() > 1)
		    					{
		    						isFinish = false;
		    					}
		    					mainFrame.getOutputinformation().geTextArea().append(key+"用例: " + "关系: " + interfaceUCRelation.getUCRelation() + "概率: " + interfaceUCRelation.getUCRelProb() + "\n");
		    					int length = mainFrame.getOutputinformation().geTextArea().getText().length(); 
				                mainFrame.getOutputinformation().geTextArea().setCaretPosition(length);
		    					}
						}
					if(isFinish == true)
					{
						for(String string : mainFrame.getjRadionPanel().getRadios().keySet())
						{
							if(string.equals(Model_Name))
							{
								mainFrame.getjRadionPanel().getRadios().get(string).setScenceTabelPanel(panel);
								mainFrame.getjRadionPanel().getRadios().get(string).setQuota(Model_Name+"模型用例扩展验证通过，可以进行场景扩展！");
							}
						}
					}
				}	
			    mainFrame.renewPanel();
			    if(isFinish == false) 
			    {
			    	label.removeAll();
					label.setText(Model_Name+"模型用例扩展验证不通过，请重新填写扩展矩阵!");
					stepTwoModelExpandTabbedPane.setSelectedIndex(1);
					stepTwoModelExpandTabbedPane.getValidationResults().updateUI();
					mainFrame.getsteponeButton().getExpandCaseModel().setEnabled(false);
					                                                                                                                               
			    }
			    else {
			    	label.removeAll();
				    label.setText(Model_Name+"用例扩展验证通过，可以进行场景扩展！");
					stepTwoModelExpandTabbedPane.getValidationResults().updateUI();
					stepTwoModelExpandTabbedPane.setSelectedIndex(1);
					mainFrame.getsteponeButton().getExpandCaseModel().setEnabled(true);
					mainFrame.getStepTwoCaseOperation().getStartExpandButton().setEnabled(true);
				}
			    startExpandButton.setEnabled(true);
				startVerificationButton.setEnabled(false);
			    mainFrame.renewPanel();
		} catch (Exception e) {
			// TODO: handle exception
			label.removeAll();
			label.setText("用例扩展出错，请重新扩展!");
			
			startExpandButton.setEnabled(true);
			startVerificationButton.setEnabled(false);
		}
		  
		    return 1;
	    }
     };		
        task2 = new FutureTask<Integer>(callable2);
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
				if(((JButton)e.getSource()).isEnabled())
				{
					try {
					DisplayForm.mainFrame = mainFrame;
					isNeedExpand = false;
					isSanmeName();
					Model_Name = mainFrame.getjRadionPanel().getSelectName();
					
					verificationProgressBar.setValue(0);
					mainFrame.getStepTwoCaseOperation().setModel_Name(Model_Name);
					mainFrame.getStepTwoEvaluateOperation().setModel_Name(Model_Name);
					mainFrame.getStepTwoExchangeOperation().setModel_Name(Model_Name);
					
					mainFrame.getsteponeButton().getExpandCaseModel().setEnabled(false);
					mainFrame.getStepTwoExpand().getEstimateLabel().setEnabled(false);
					mainFrame.getStepTwoExpand().getExchangeLabel().setEnabled(false);
					
					mainFrame.getStepTwoCaseOperation().getStartExpandButton().setEnabled(false);
					mainFrame.getStepTwoEvaluateOperation().getEvaluateButton().setEnabled(false);
					mainFrame.getStepTwoExchangeOperation().getStartExchange().setEnabled(false);
					
					if(Model_Name == null){
						label.removeAll();
						label.setText("请在用例扩展中选择模型或在可靠性测试扩展中建立模型!");
						mainFrame.getStepTwoModelOperation().updateUI();
						mainFrame.renewPanel();
					}
					else if(numberTextField.getText().equals(""))
					{ 
					
						label.removeAll();
						label.setText("请填写正确的用户数量!");
						mainFrame.getStepTwoModelOperation().updateUI();
						mainFrame.renewPanel();
					}
					else{
	                        for(ModelPanel modelPanel : mainFrame.getModelPanelMap().keySet())
	                        {
	                        	if(modelPanel.getTitle().getText().equals(Model_Name))
	                        	{
	                        		File file = new File(modelPanel.getTemporaryUcaseFile());
	                        		currentUcase = file.listFiles()[0].getAbsolutePath();
	                        		if(currentUcase.contains(".ucase.violet.xml"))
	    						    {
	                        		StaticConfig.umlPathPrefixHDU = modelPanel.getTemporarySeqFile();
	    						    }
	                        		else {
	                        		StaticConfig.umlPathPrefix = modelPanel.getTemporarySeqFile();
									}
	                        	}
	                        }
	                        
						    worker=new WorkImpl();
						    
						    if(currentUcase.contains(".ucase.violet.xml"))
						    {
						    	worker.transInitialHDU(currentUcase);
						    }
						    else {
						    	worker.transInitial(currentUcase);
							}

						    relations.clear();
							
			                ucMap=worker.provideUCRelation(); 
							IISDList=worker.provideIsogencySD();	
							
							stepTwoModelExpandTabbedPane.getmodelExpandPanel().removeAll();
							number = Integer.parseInt(numberTextField.getText());
		                    int j;
							for(j = 0;j < number;++j){
								int i = 0;
								StepTwoTabelPanel stepTwoTabelPanel = new StepTwoTabelPanel(mainFrame);
								stepTwoTabelPanel.getTitleLabel().setText("用户"+(j+1));
								stepTwoTabelPanel.getTabelPanel().setLayout(new GridBagLayout());
								for(String key : ucMap.keySet())
								{
									List<InterfaceUCRelation> interfaceUCRelations = ucMap.get(key);
									if(interfaceUCRelations.size() > 1)
									{
										isNeedExpand = true;
										relations.add(key+"迁移关系");
										for(InterfaceUCRelation interfaceUCRelation : interfaceUCRelations)
										{
											relations.add(interfaceUCRelation.getUCRelation());
										}
										ScenceTabelPanel scenceTabelPanel = new ScenceTabelPanel(relations); 
										StepTwoMatrixPanel stepTwoMatrixPanel = new StepTwoMatrixPanel(mainFrame);
										stepTwoMatrixPanel.getTitleLabel().setText("用例名称:"+key);
										stepTwoMatrixPanel.getTitleLabel().setFont(new Font("微软雅黑", Font.BOLD, 16));
										stepTwoMatrixPanel.getTabelPanel().add(scenceTabelPanel);
										stepTwoTabelPanel.getTabelPanel().add(stepTwoMatrixPanel, new GBC(0,i).setFill(GBC.BOTH).setWeight(1, 0));
										i++;
										relations.clear();
									}
								}
								stepTwoModelExpandTabbedPane.getmodelExpandPanel().add(stepTwoTabelPanel, new GBC(0, j).setFill(GBC.BOTH).setWeight(1, 0));
							}
							stepTwoModelExpandTabbedPane.getmodelExpandPanel().add(new JPanel(), new GBC(0, ++j).setFill(GBC.BOTH).setWeight(1, 1));
							stepTwoModelExpandTabbedPane.getmodelExpandPanel().updateUI();
							
							label.setText(Model_Name+"用例模型进行扩展,填写矩阵即对后继用例进行两两对比打分,填写完成后进行验证!");	
							if(isNeedExpand == false)
							{
								stepTwoModelExpandTabbedPane.getmodelExpandPanel().removeAll();
								label.removeAll();
								label.setText(Model_Name+"模型不需要进行模型扩展,可以直接进行验证!"); 
							}
							mainFrame.getStepTwoModelExpandTabbedPane().getValidationResults().removeAll();
							mainFrame.getStepTwoModelExpandTabbedPane().setSelectedIndex(0);
							mainFrame.getStepTwoCaseExpandTabbedPane().getCaseExpandPanel().removeAll();
							mainFrame.getStepTwoCaseExpandTabbedPane().getValidationResults().removeAll();
							mainFrame.getStepTwoEvaluateTabbedPane().getHomogeneityResults().removeAll();
							mainFrame.getStepTwoExchangeTabbedPane().getExchangeResults().removeAll();
							mainFrame.getStepTwoExchangeTabbedPane().getExchangeResport().removeAll();
							
							startExpandButton.setEnabled(false);
							startVerificationButton.setEnabled(true);
							
							mainFrame.getStepTwoExpand().getEstimateLabel().setEnabled(false);
							mainFrame.getStepTwoExpand().getExchangeLabel().setEnabled(false);
							mainFrame.getsteponeButton().getExpandCaseModel().setEnabled(false);
						}  
						
					}catch (Throwable e1) {
							// TODO Auto-generated catch block
					    e1.printStackTrace();
						if(Model_Name == null){
							label.removeAll();
							label.setText("请在左侧用例列表中选择用例模型或在第一步中建立用例模型!");
							mainFrame.getStepTwoModelOperation().updateUI();
						}
						else if(numberTextField.getText().equals(""))
						{ 
						   
							label.removeAll();
							label.setText("请填写正确的用户数量!");
							mainFrame.getStepTwoModelOperation().updateUI();
						}
						else {
							label.removeAll();
							label.setText("请检查模型是否绘制正确或模型是否存在!");
						}
						}
				}	
			}
		});
		startVerificationButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(((JButton)e.getSource()).isEnabled())
				{
					Alive = true;
					
					initThread();
					mainthread.start();
					thread1.start();
				}
			}
		});	
		
		numberTextField.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
		});
		}

	private static boolean isNumeric(String str){ 
		  for (int i = str.length();--i>=0;){    
		   if (!Character.isDigit(str.charAt(i))){ 
		    return false; 
		   } 
		  } 
		  return true; 
		 } 
	private int getplace(String key)
	{
		JPanel panel = ((StepTwoTabelPanel) stepTwoModelExpandTabbedPane.getmodelExpandPanel().getComponent(0)).getTabelPanel();
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
	 private void isSanmeName(){
	    	if(Model_Name == null){
	    		Model_Name = mainFrame.getjRadionPanel().getSelectName();
	    		isSameName = false;
	    	}	
			else if (mainFrame.getjRadionPanel().getSelectName().equals(Model_Name)) {
				Model_Name = mainFrame.getjRadionPanel().getSelectName();
				isSameName = true;
			}
			else {
				isSameName = false;
			}
	    }
	public JPanel getOtherPanel(){
		return otherPanel;
	}
	public Map<String, ScenceTabelPanel> getTabelResultMap() {
		return tabelResultMap;
	}
	public String getModel_name() {
		return Model_Name;
	}
	public List getVerList() {
		return verList;
	}
	public Map<String, List<InterfaceUCRelation>> getUcMap() {
		return ucMap;
	}
	public List<InterfaceIsogenySD> getIISDList() {
		return IISDList;
	}
	public Work getWorker() {
		return worker;
	}
	
	public Work getNewWorker()
	{
		Work newworker = new WorkImpl();
		if(currentUcase.contains("\\UseCaseDiagram\\EAXML"))
	    {
	    	StaticConfig.umlPathPrefix = EAsequenceBathRoute;
	    	try {
	    		newworker.transInitial(currentUcase);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    }
	    else {
	    	StaticConfig.umlPathPrefix = VioletsequenceBathRoute;
	    	try {
	    		newworker.transInitialHDU(currentUcase);
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return newworker;
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
	public JLabel getLabel() {
		return label;
	}
	
	public boolean getIsSameName() {
		return isSameName;
	}

	public ScenceTabelPanel getEvaluatePanel() {
		return evaluatePanel;
	}
	
	public JPanel getMatrixPanel() {
		return MatrixPanel;
	}
	public JButton getStartExpandButton() {
		return startExpandButton;
	}
    
	public String getCurrentUcase() {
		return currentUcase;
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
	        if(text.charAt(0) == '0')
	        {
	        	SwingUtilities.invokeLater(new Runnable() {
	                @Override
	                public void run() {
	                
	                    numberTextField.setText(text.substring(0, text.length() - 1));
	                }
	            });
	        }
	        
	        else if(!(ch >= '1' && ch <= '9') 
	               ) { 
	            SwingUtilities.invokeLater(new Runnable() {
	                @Override
	                public void run() {
	                    
	                    numberTextField.setText(text.substring(0, text.length() - 1));
	                }
	            });
	        }
	        mainFrame.renewPanel();
	    }
}
}
