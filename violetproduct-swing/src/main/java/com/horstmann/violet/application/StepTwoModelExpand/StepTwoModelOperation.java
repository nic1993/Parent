
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

import antlr.collections.impl.Vector;
import cn.edu.hdu.lab.config.StaticConfig;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceIsogenySD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceSD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceUCRelation;
import cn.edu.hdu.lab.service.interfaces.Work;
import cn.edu.hdu.lab.service.sd2tmc.WorkImpl;

import com.horstmann.violet.application.StepOneBuildModel.ModelPanel;
import com.horstmann.violet.application.StepOneBuildModel.Radio;
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
	
	private List<String> relations;  //获取关系集合
	private List<Double> relationsData;
	private List<double[][]> tableDatas;
	private Work worker;
	private int number;
	private String Model_Name;
	private boolean isSameName = false;
	private Map<String, ScenceTabelPanel> tabelResultMap;
	private ScenceTabelPanel evaluatePanel;
	private List<StepTwoMatrixPanel> MatrixPanels;  //场景扩展
	private List<StepTwoMatrixPanel> EvaluateMatrixPanels;  //模型评估
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
    
	private DecimalFormat df= new DecimalFormat("######0.00");
	
	private static String EABathRoute;
	private static String VioletBathRoute;
	private static String EAsequenceBathRoute;
	private static String VioletsequenceBathRoute;
	
	private File EAFile;
	private File[] EAFiles;
	private File VioletFile;
	private File[] VioletFiles;
	
	private String currentUcase;
	private String currentSeq;
	
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
		label.setText("请在左侧选择用例模型并填写用户数进行扩展！");
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
		step = 0;
		maincallable = new Callable<Integer>() {
  			@Override
  			public Integer call() throws Exception {
  				// TODO Auto-generated method stub
  				startVerificationButton.setEnabled(false);
				startExpandButton.setEnabled(false);
  				while (progressBarIndex < 100) {
  					if(progressBarIndex == (int)((double)100/stepSum)*step)
  					{
  						if(futuretasklist.get(step).isDone()){
							step++;
							progressBarIndex++;
							verificationProgressBar.setValue(verificationProgressBar.getValue()+1);
							threadlist.get(step).start();
						}		
  					}
  					else if(step != 1){
							progressBarIndex++;
							verificationProgressBar.setValue(verificationProgressBar.getValue()+1);	
					}
  					Thread.sleep(100);
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
		    label.removeAll();
		    label.setText("正在初始化数据.....");
		    Thread.sleep(200);
			stepTwoModelExpandTabbedPane.getValidationResults().removeAll();
			stepTwoModelExpandTabbedPane.getValidationResults().updateUI();
			MatrixPanels.clear();
		    EvaluateMatrixPanels.clear();
			for(String key : ucMap.keySet()) //求解 生成
			{
				label.removeAll();
			    label.setText("正在计算后继用例迁移关系概率.....");
			    relations.clear();
				relationsData.clear();
			    Thread.sleep(100);
				List<InterfaceUCRelation> interfaceUCRelations = ucMap.get(key);
				
				relations.clear();
				relationsData.clear();
				if(interfaceUCRelations.size() == 1)
				{
					relations.add(interfaceUCRelations.get(0).getUCRelation());
					interfaceUCRelations.get(0).setUCRelProb(1.000);
					relationsData.add(1.000);
					
					StepTwoMatrixPanel stepTwoMatrixPanel = new StepTwoMatrixPanel();
					stepTwoMatrixPanel.getTitleLabel().setText("执行用例:"+ key);
					
					StepTwoMatrixPanel stepTwoMatrixPanel1 = new StepTwoMatrixPanel();
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
				   					a[row][column-1] = Double.parseDouble(table.getValueAt(row, column).toString());
				   				}
				   			}
				   			tableDatas.add(a);
				   		 }
				   	 }
				}
				if(tableDatas.size() > 0)
				{
					List list=worker.calculateProb(tableDatas); //带入界面填写的矩阵数组集合，返回计算结果
					double[] datas = (double[]) list.get(1);
					for(int k = 0; k < datas.length;k++)
					{
						interfaceUCRelations.get(k).setUCRelProb(datas[k]);
						relationsData.add(datas[k]);
					}
					tableDatas.clear();
					stepTwoModelExpandTabbedPane.getValidationResults().updateUI();
					
					StepTwoMatrixPanel stepTwoMatrixPanel = new StepTwoMatrixPanel();
					stepTwoMatrixPanel.getTitleLabel().setText("执行用例:"+key);
					ScenceTabelPanel scenceTabelPanel = new ScenceTabelPanel(relations, relationsData, 1, mainFrame);
					stepTwoMatrixPanel.getTabelPanel().add(scenceTabelPanel);
					MatrixPanels.add(stepTwoMatrixPanel);
					
					StepTwoMatrixPanel stepTwoMatrixPanel1 = new StepTwoMatrixPanel();
					stepTwoMatrixPanel1.getTitleLabel().setText("执行用例:"+key);
					ScenceTabelPanel scenceTabelPanel1 = new ScenceTabelPanel(relations, relationsData, 1, mainFrame);
					stepTwoMatrixPanel1.getTabelPanel().add(scenceTabelPanel1);
					EvaluateMatrixPanels.add(stepTwoMatrixPanel1);
				}
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
		  label.removeAll();
		  label.setText("正在生成后继用例概率列表.....");
		  if(relations.size() > 0)
			{
			    while (progressBarIndex <= 100) {
			    	progressBarIndex++;
					verificationProgressBar.setValue(progressBarIndex);
					Thread.sleep(100);
				}
			    //设置第一个矩阵展开
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
						
				
				for(Radio radio : mainFrame.getjRadionPanel().getRadioList())
				{
					if(radio.getText().equals(Model_Name))
					{
						radio.setScenceTabelPanel(panel);
					}
				}

			}	
		  
		    isFinish = true;
		    for(double data : relationsData)
		    {
		    	if(data <= 0.0 || data > 1.0)
		    		isFinish = false;
		    }
		    if(isFinish == false)
		    {
		    	label.removeAll();
				label.setText(Model_Name+"用例模型进行扩展验证不通过，请重新填写扩展矩阵!");
				stepTwoModelExpandTabbedPane.setSelectedIndex(1);
				stepTwoModelExpandTabbedPane.getValidationResults().updateUI();
				mainFrame.getStepTwoExpand().getExpandCaseModel().setEnabled(false);
				startExpandButton.setEnabled(true);
				startVerificationButton.setEnabled(false);
		    }
		    else {
		    	label.removeAll();
			    label.setText(Model_Name+"用例模型进行扩展验证通过，可以进行用例扩展！");
				stepTwoModelExpandTabbedPane.getValidationResults().updateUI();
				stepTwoModelExpandTabbedPane.setSelectedIndex(1);
				mainFrame.getsteponeButton().getExpandCaseModel().setEnabled(true);
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
					isSanmeName();
					Model_Name = mainFrame.getjRadionPanel().getSelectName();
					
					mainFrame.getStepTwoCaseOperation().setModel_Name(Model_Name);
					mainFrame.getStepTwoEvaluateOperation().setModel_Name(Model_Name);
					mainFrame.getStepTwoExchangeOperation().setModel_Name(Model_Name);
					if(Model_Name == null){
						label.removeAll();
						label.setText("请在左侧用例列表中选择用例模型或在第一步中建立用例模型!");
						mainFrame.getStepTwoModelOperation().updateUI();
					}
					else if(numberTextField.getText().equals(""))
					{ 
					   //添加弹出�?
						label.removeAll();
						label.setText("请填写正确的用户数量!");
						mainFrame.getStepTwoModelOperation().updateUI();
					}
					else{
						try {
//							EAFiles = EAFile.listFiles();
//							for(File file : EAFiles)
//							{
//								String FileRoute = file.getName().replaceAll(".xml", "");
//								if(FileRoute.equals(Model_Name))
//								{
//									currentUcase = file.getAbsolutePath();
//								}
//							}
//							 //读取Violet的XML
//							for(File file : VioletFiles)
//							{
//								String VioletFileName = file.getName().replace("ucase.violet.xml", "");
//								if(VioletFileName.contains(Model_Name))
//								{
//									currentUcase = file.getAbsolutePath();
//								}
//							}
	                        for(ModelPanel modelPanel : mainFrame.getModelPanelMap().keySet())
	                        {
	                        	if(modelPanel.getTitle().getText().equals(Model_Name))
	                        	{
	                        		System.out.println(modelPanel.getTemporaryUcaseFile());
	                        		File file = new File(modelPanel.getTemporaryUcaseFile());
	                        		System.out.println(file.listFiles()[0].getAbsolutePath());
	                        		currentUcase = file.listFiles()[0].getAbsolutePath();
	                        		StaticConfig.umlPathPrefix = modelPanel.getTemporarySeqFile();
	                        	}
	                        }
							
						    worker=new WorkImpl();
						    
						    if(currentUcase.contains(".ucase.violet.xml"))
						    {
						    	currentSeq = EAsequenceBathRoute;
						    	worker.transInitialHDU(currentUcase);
						    }
						    else {
						    	currentSeq = EAsequenceBathRoute;
						    	worker.transInitial(currentUcase);
							}

						}  catch (Exception e2) {
							// TODO: handle exception
							e2.printStackTrace();
						} catch (Throwable e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						relations.clear();
						//瑙ｆ瀽UML妯�?��?�鐨刋ML鏂囦�?
		                ucMap=worker.provideUCRelation(); //鑾峰彇鐢ㄤ緥鎵ц椤哄簭鍏崇郴
						IISDList=worker.provideIsogencySD();//鑾峰彇鐢ㄤ緥鍦烘櫙淇℃伅
						for(InterfaceIsogenySD interfaceIsogenySD : IISDList)
						{
							System.out.println(interfaceIsogenySD.getUcName() + "用例场景个数: " + interfaceIsogenySD.getISDList().size());
							for(InterfaceSD interfaceSD : interfaceIsogenySD.getISDList())
							{
								System.out.println("场景名称： " + interfaceSD.getName());
							}
						}
						
						stepTwoModelExpandTabbedPane.getmodelExpandPanel().removeAll();
						number = Integer.parseInt(numberTextField.getText());
	                    int j;
						for(j = 0;j < number;++j){
							int i = 0; //标记位置
							StepTwoTabelPanel stepTwoTabelPanel = new StepTwoTabelPanel();
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
									ScenceTabelPanel scenceTabelPanel = new ScenceTabelPanel(relations); //矩阵
									StepTwoMatrixPanel stepTwoMatrixPanel = new StepTwoMatrixPanel();
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
						
						mainFrame.getStepTwoCaseOperation().getToplabel().setText("当前模型为:"+Model_Name);
						mainFrame.getStepTwoEvaluateOperation().getTopLabel().setText("当前模型为:"+Model_Name);
						mainFrame.getStepTwoExchangeOperation().getToplabel().setText("当前模型为:"+Model_Name);
						
						startExpandButton.setEnabled(false);
						startVerificationButton.setEnabled(true);
						
						mainFrame.getStepTwoExpand().getEstimateLabel().setEnabled(false);
						mainFrame.getStepTwoExpand().getExchangeLabel().setEnabled(false);
						mainFrame.getsteponeButton().getExpandCaseModel().setEnabled(false);	
					}
				}	
			}
		});
		startVerificationButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				stepTwoModelExpandTabbedPane.getValidationResults().removeAll();
//				relations.clear();
//				relationsData.clear();
//				for(String key : ucMap.keySet()) //求解 生成
//				{
//					List<InterfaceUCRelation> interfaceUCRelations = ucMap.get(key);
//					if(interfaceUCRelations.size() == 1)
//					{
//						relations.add(interfaceUCRelations.get(0).getUCRelation());
//						interfaceUCRelations.get(0).setUCRelProb(1.0);
//						relationsData.add(1.0);
//					}
////					else if (key.equals("Use Case3")) {   //后期修改 重复
////						for(InterfaceUCRelation interfaceUCRelation:interfaceUCRelations)
////						{
////							relations.add(interfaceUCRelation.getUCRelation());
////							relationsData.add(1.0);
////						}
////					}
//					else 
//					{    
//						for(InterfaceUCRelation interfaceUCRelation:interfaceUCRelations)
//						{
//							relations.add(interfaceUCRelation.getUCRelation());
//						}
//					   	 for(int i = 0;i <number;i++)
//					   	 {
//					   		 int location = getplace(key);
//					   		 if(location != -1)
//					   		 {
//					   			JPanel panel = ((StepTwoTabelPanel) stepTwoModelExpandTabbedPane.getmodelExpandPanel().getComponent(i)).getTabelPanel();
//					   			JPanel tabelPanel = ((StepTwoMatrixPanel)panel.getComponent(location)).getTabelPanel();
//					   			JTable table = ((ScenceTabelPanel)tabelPanel.getComponent(0)).getTable();
//					   			int rows = table.getRowCount();
//					   			int columns = table.getColumnCount();
//					   			double a[][] = new double[rows][columns-1];
//					   			for(int row = 0;row < rows;row++)
//					   			{
//					   				for(int column = 1;column < columns;column++)
//					   				{
//					   					a[row][column-1] = Double.parseDouble(table.getValueAt(row, column).toString());
//					   				}
//					   			}
//					   			tableDatas.add(a);
//					   		 }
//					   	 }
//					}
//					if(tableDatas.size() > 0)
//					{
//					
//						List list=worker.calculateProb(tableDatas); //带入界面填写的矩阵数组集合，返回计算结果
//						double[] datas = (double[]) list.get(1);
//						for(int k = 0; k < datas.length;k++)
//						{
//							interfaceUCRelations.get(k).setUCRelProb(datas[k]);
//							relationsData.add(datas[k]);
//						}
//						tableDatas.clear();
//						label.setText(model_name+"用例模型进行扩展验证通过，可以进行用例扩展验证！");
//						stepTwoModelExpandTabbedPane.getValidationResults().updateUI();
//					}
//				}
//				//生成验证报告
//				if(relations.size() > 0)
//				{
//					ScenceTabelPanel scenceTabelPanel = new ScenceTabelPanel(relations, relationsData,1);
//					stepTwoModelExpandTabbedPane.getValidationResults().add(scenceTabelPanel);				
//					tabelResultMap.put(model_name, scenceTabelPanel);
//				}	
//				stepTwoModelExpandTabbedPane.getValidationResults().updateUI();
//			}
				if(((JButton)e.getSource()).isEnabled())
				{
					initThread();
					mainthread.start();
					thread1.start();
				}
				
			}
		});	
		}

	private static boolean isNumeric(String str){  //判断输入的是否为数字函数
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
	public String getCurrentSeq() {
		return currentSeq;
	}

	class TextFieldInputListener implements CaretListener {
		 
	    @Override
	    public void caretUpdate(CaretEvent e) {
	        JTextField textField = (JTextField) e.getSource(); // 获得触发事件的 JTextField
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
	                    // 去掉 JTextField 中的末尾字符
	                    numberTextField.setText(text.substring(0, text.length() - 1));
	                }
	            });
	        }
	        
	        else if(!(ch >= '0' && ch <= '9') // 数字
	               ) { // 中文，最常用的范围是 U+4E00～U+9FA5，也有使用 U+4E00～ U+9FFF 的，但目前 U+9FA6～U+9FFF 之间的字符还属于空码，暂时还未定义，但不能保证以后不会被定义
	            SwingUtilities.invokeLater(new Runnable() {
	                @Override
	                public void run() {
	                    // 去掉 JTextField 中的末尾字符
	                    numberTextField.setText(text.substring(0, text.length() - 1));
	                }
	            });
	        }
	    }
}
}
