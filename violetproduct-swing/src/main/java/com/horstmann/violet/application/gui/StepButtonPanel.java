//package com.horstmann.violet.application.gui;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Component;
//import java.awt.Font;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.GridLayout;
//import java.awt.Insets;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import javax.swing.JButton;
//import javax.swing.JFileChooser;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JSplitPane;
//import javax.swing.JTabbedPane;
//import javax.swing.JTextArea;
//import javax.swing.JViewport;
//import javax.swing.SwingUtilities;
//
//import org.dom4j.DocumentException;
//
//import com.horstmann.violet.application.consolepart.ConsoleMessageTabbedPane;
//import com.horstmann.violet.application.consolepart.ConsolePart;
//import com.horstmann.violet.application.consolepart.ConsolePartDetailInfoTable;
//import com.horstmann.violet.application.consolepart.ConsolePartTextArea;
//import com.horstmann.violet.application.gui.util.chengzuo.Verfication.ClientRecThread;
//import com.horstmann.violet.application.gui.util.chengzuo.Verfication.ClientSocket;
//import com.horstmann.violet.application.gui.util.chengzuo.Verfication.ConsolePartTestCaseInfoTable;
//import com.horstmann.violet.application.gui.util.chengzuo.Verfication.JFreeChartTest;
//import com.horstmann.violet.application.gui.util.chengzuo.Verfication.TestCase;
//import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom1.GetAutomatic;
//import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom1.Main;
//import com.horstmann.violet.application.gui.util.wujun.SequenceTransfrom.SD2UppaalMain;
//import com.horstmann.violet.application.gui.util.wujun.TDVerification.ExistTest;
//import com.horstmann.violet.application.gui.util.xiaole.GraghLayout.LayoutUppaal;
//import com.horstmann.violet.application.gui.util.xiaole.UppaalTransfrom.ImportByDoubleClick;
//import com.horstmann.violet.application.gui.util.xiaole.UppaalTransfrom.TransToVioletUppaal;
//import com.horstmann.violet.application.menu.FileMenu;
//import com.horstmann.violet.application.menu.util.zhangjian.Database.AbstractState;
//import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.AbstractTestCaseInsertByTan;
//import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.AbstractTestCaseUppaalCreate;
//import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.AbstractTestCaseUppaalSequenceCreate;
//import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.AbstractTrasitionAndStateInsertByTan;
//import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.RealTestCaseXMLRead;
//import com.horstmann.violet.framework.file.GraphFile;
//import com.horstmann.violet.framework.file.IGraphFile;
//import com.horstmann.violet.workspace.IWorkspace;
//import com.horstmann.violet.workspace.Workspace;
//
//public class StepButtonPanel extends JPanel {
//	private JButton homebutton;
//	private JButton step1button;
//	private JButton step2button;
//	//by tan
//	private JButton ceshiButton;
//	private JButton step3button;
//	private JButton step4button;
//	private JButton step5button;
//	private List<JButton> stepButtonGroup;
//	private MainFrame mainFrame;
//    private ConsolePart consolePart;
//    private JPanel operationPanel;
//    StepThreeCenterTabbedPane stepThreeCenterTabbedPane;
//    StepSixCenterTabbedPane stepSixCenterTabbedPane=new StepSixCenterTabbedPane();
//    StepFourCenterTabbedPane stepFourCenterTabbedPane=new StepFourCenterTabbedPane();
//    private JButton Threestart=new JButton("开始");
//    private JButton Fourstart=new JButton("开始");
//    private JButton Twostart=new JButton("开始");
//    private JButton fivestart=new JButton("开始");
//    private JButton fiveshow=new JButton("显示");
//    private JButton Sixstart=new JButton("开始");
//	JTextArea StepTwoArea=new JTextArea();
//	JTextArea StepThreeArea=new JTextArea();
//	JTextArea StepFourArea=new JTextArea();
//	JTextArea StepFiveArea=new JTextArea();
//	JTextArea StepSixArea=new JTextArea();
//	public StepButtonPanel(MainFrame mainFrame) {
//		this.setBackground(Color.DARK_GRAY);
//		this.mainFrame=mainFrame;	
//		init();
//		stepThreeCenterTabbedPane=mainFrame.getStepThreeCenterTabbedPane();
//		
//		
//	}
//    
//	private void init() {
//		GridBagLayout layout = new GridBagLayout();
//		this.setLayout(layout);
//		initButton();
//		GridBagConstraints s = new GridBagConstraints();// 定义一个GridBagConstraints，
//		// 是用来控制添加进的组件的显示位置
//		s.fill = GridBagConstraints.BOTH;
//		s.anchor = GridBagConstraints.FIRST_LINE_START;
//		s.insets = new Insets(0, 0, 0,0);
//        this.add(homebutton);
//		this.add(step1button);
//		this.add(step2button);
//		this.add(ceshiButton);
//		this.add(step3button);
//		this.add(step4button);
//		this.add(step5button);
//		s.gridx = 0;
//		s.gridy = 0;
//		s.weighty = 1;
//		s.weightx=1;
//		layout.setConstraints(homebutton, s);// 设置组件
//		s.gridx = 0;
//		s.gridy = 1;
//		layout.setConstraints(step1button, s);
//		s.gridx = 0;
//		s.gridy = 2;
//		layout.setConstraints(step2button, s);
//		s.gridx = 0;
//		s.gridy = 3;
//		layout.setConstraints(step3button, s);
//		s.gridx = 0;
//		s.gridy = 4;
//		layout.setConstraints(step4button, s);
//		s.gridx = 0;
//		s.gridy = 5;
//		layout.setConstraints(step5button, s);
//		s.gridx = 0;
//		s.gridy = 6;
//		layout.setConstraints(ceshiButton, s);
//		// TODO Auto-generated method stub
//
//		SetButtonListener();
//
//	}
//
//	private void initButton() {
//		homebutton =new JButton();
//		step1button = new JButton();
//		step2button = new JButton();
//		ceshiButton=new JButton();
//		step3button = new JButton();
//		step4button = new JButton();
//		step5button = new JButton(); 
//		
//	    homebutton.setText("首页");
//	    homebutton.setForeground(Color.RED);
//		step1button.setText("第一步:UML模型建立及导入");
//		step1button.setBackground(Color.BLUE);
//		step2button.setText("第二步:UML模型转化时间自动机");
//		step3button.setText("第三步:抽象测试用例生成");
//		step4button.setText("第四步:测试用例实例化");
//		step5button.setText("第五步:测试用例实例化验证");
//		ceshiButton.setText("一致性测试");
//		stepButtonGroup = new ArrayList<JButton>();
//		stepButtonGroup.add(homebutton);
//		stepButtonGroup.add(step1button);
//		stepButtonGroup.add(step2button);
//		stepButtonGroup.add(step3button);
//		stepButtonGroup.add(step4button);
//		stepButtonGroup.add(step5button);
//		stepButtonGroup.add(ceshiButton);
//		operationPanel=mainFrame.getOpreationPart();
//		operationPanel.setLayout(new GridLayout(1,1));
//		consolePart=mainFrame.getConsolePart();	
//		// TODO Auto-generated method stub
//        step2button.setEnabled(false);//初始其他步骤按钮都不可点击
//        step3button.setEnabled(false);
//        step4button.setEnabled(false);
//        step5button.setEnabled(false);
//	}
//	//初始化阶段
//	public void clearSelection() {
//		for (JButton stepButton : stepButtonGroup) {			
//			stepButton.setForeground(Color.BLACK);
//		}
//	}
//	public void clearConsolePart(){
//		this.consolePart.getContentPane().removeAll();;
//	}
//  private void ClearOpreationPanel()
//  {
//	  this.operationPanel.removeAll();
//  }
//    //设置监听器
//	private void SetButtonListener() {
//		Twostart.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				StepTwoArea.append("UML模型正在转换中......\n");	
//				// TODO Auto-generated method stub
//			   	try {
//			   		//事件分发线程(gum处理事件和画图的时候)
//			   		SwingUtilities.invokeLater(new Runnable() {
//						
//						@Override
//						public void run() {
//							try {
//								//uml转化成事件自动机
//                                 //用于获得当前工作的sequence
//								String name=FileMenu.getFileName();//sequence.getGraphFile().getFilename();
//								String directoryPath=FileMenu.getDirectory();
//								String path=directoryPath+"\\"+name;//菜单栏打开文件的路径
//								System.out.println(path+"路劲");
//								if(name.contains("EA")){//打开ea平台的xml文件
//									SD2UppaalMain.transEA(path);//主要是将ea的xml转换成我们的wujun的xml(里面有他的路径)
//									//以下d盘中写的文件是死的路径，但是上面是动态生成的需要修改
//									LayoutUppaal.layout("D:\\ModelDriverProjectFile\\UPPAL\\2.UML_Model_Transfer\\UseCase1-Sequence1-Normal.xml");//("sequence.xml");
//								    String filename1=TransToVioletUppaal.TransToViolet();
//									//String filename1="uppaalTest1.uppaal.violet.xml";
//								    GraphFile fGraphFile1=ImportByDoubleClick.importFileByDoubleClick("UPPAAL",filename1);
//				    			    IWorkspace workspace1=new Workspace(fGraphFile1);  
//				    			    mainFrame.addTabbedPane(workspace1,2);
//				    			    mainFrame.repaint();
//				    			    Thread.sleep(5000);
//				    				//String filename2=TransToVioletUppaal.TransToViolet();
//				    			
//								   // GraphFile fGraphFile2=ImportByDoubleClick.importFileByDoubleClick("UPPAAL",filename2);
//				    			    //IWorkspace workspace2=new Workspace(fGraphFile2);  			    			  
//				    			    StepTwoArea.append("UML模型到时间自动机模型已经转换完成!\n");
//								}
//								else{//打开我们平台的xml文件
//									
//								}
////								SD2UppaalMain.transEA(path);//主要是将ea的xml转换成我们的wujun的xml(里面有他的路径)
////							    String filename1=TransToVioletUppaal.TransToViolet();
////							    GraphFile fGraphFile1=ImportByDoubleClick.importFileByDoubleClick("UPPAAL",filename1);
////			    			    IWorkspace workspace1=new Workspace(fGraphFile1);  
////			    			    mainFrame.addTabbedPane(workspace1,2);
////			    			    mainFrame.repaint();
////			    			    Thread.sleep(5000);
//								//先进行布局
//			    			    //将时间自动机展示在我们的平台上
////								LayoutUppaal.layout
////								("C:\\Users\\Admin\\Desktop\\项目最新代码\\violetumleditor-master\\violetproduct-swing\\sequence.xml");//("stabilize_run.xml");
////								String filename2=TransToVioletUppaal.TransToViolet();
////							    GraphFile fGraphFile2=ImportByDoubleClick.importFileByDoubleClick("UPPAAL",filename2);
////			    			    IWorkspace workspace2=new Workspace(fGraphFile2);  			    			  
////			    			    StepTwoArea.append("UML模型到时间自动机模型已经转换完成!\n");
//			    			    //mainFrame.addTabbedPane(workspace1,2);
//			    			   
//							} catch (Exception e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}														
//					}});			   				  		   
//				} catch (Exception e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//		});
//Threestart.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				StepThreeArea.append("抽象时间迁移的时间自动机正在生成.....\n");
//				
//				SwingUtilities.invokeLater(new Runnable() {
//					
//					@Override
//					public void run() {
//						
//						//GetAutomatic.getAutomatic("Draw MoneyForXStream(2).xml");
//						//by tan 将抽象测试用例的state加入到数据库中
//						//new AbstractStateInsertByTan().wqq2zhangExchange("Draw MoneyForXStream(2).xml");
//						//BY TAN 强抽象测试的迁移trasition加入到数据库中
//						//new AbstractTrasitionAndStateInsertByTan().w2zExchange("Draw MoneyForXStream(2).xml");
//						
//						System.out.println("----------------------------------");
//						try {
//							//获取第三步自动机的节点和边的信息，存放在squence。xml中，在调用布局算法，生成节点坐标。存放在stabilize_run.xml中。
//							new AbstractTestCaseUppaalSequenceCreate().createXML("D:\\ModelDriverProjectFile\\UPPAL\\2.UML_Model_Transfer\\UseCase1-Sequence1-NormalForXStream.xml");
//							LayoutUppaal.layout
//							("C:\\Users\\Admin\\Desktop\\项目最新代码\\violetumleditor-master\\violetproduct-swing\\sequence.xml");
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						};
//						System.out.println("-----------------------------------");
//						 //将信息存入数据库
//						new AbstractTestCaseUppaalCreate().createXML("D:\\ModelDriverProjectFile\\UPPAL\\2.UML_Model_Transfer\\UseCase1-Sequence1-NormalForXStream.xml", "D:\\ModelDriverProjectFile\\UPPAL\\2.UML_Model_Transfer\\abs.uppaal.violet.xml");
//						//new AbstractTestCaseInsertByTan().w2zAbstractTestCaseExchange("D:\\ModelDriverProjectFile\\UPPAL\\2.UML_Model_Transfer\\sequenceForXStream.xml");//已经整合到上面的方法中
//						//String absfilename="abs.uppaal.violet.xml";
//				        //String no_time_absfilename="no_time_abs.uppaal.violet.xml";
//					    
//					    //String filename2=TransToVioletUppaal.TransToViolet();
//						
//						GraphFile absfGraphFile=ImportByDoubleClick.importFileByDoubleClick("UPPAAL","abs.uppaal.violet.xml");
//						//GraphFile no_time_absfGraphFile=ImportByDoubleClick.importFileByDoubleClick("UPPAAL","a.xml");
//		 			    IWorkspace absworkspace=new Workspace(absfGraphFile);
//		 			    //IWorkspace no_time_absworkspace=new Workspace(no_time_absfGraphFile);
//		 			    //展示时间迁移的自动机
//		 			    mainFrame.addTabbedPane(absworkspace,3);
//		 			    
//		 			    StepThreeArea.append("抽象时间迁移的时间自动机生成完成!\n");
//		 			    StepThreeArea.append("不含时间迁移的时间自动机正在生成.....\n");
//		 			    //展示去时间迁移的自动机
//		 			    //mainFrame.addTabbedPane(no_time_absworkspace,3);
//		 			    
//		 			    StepThreeArea.append("不含时间迁移的时间自动机生成完成!\n");
//		 			    StepThreeArea.append("抽象测试用例正在生成.....\n");
//						//展示抽象测试用例的生成
//		 			    stepThreeCenterTabbedPane.getConsolePartScrollPane()
//						.getViewport().add(new ConsolePartDetailInfoTable(0));			
//					    stepThreeCenterTabbedPane.getConsolePartScrollPane().getViewport().repaint();
//					    StepThreeArea.append("抽象测试用例生成完成!.....\n");
//						
//					}
//				});
//				
//			}
//		});
//Fourstart.addActionListener(new ActionListener() {
//	
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
//		StepFourArea.append("实例化测试用例正在生成.....\n");
//		SwingUtilities.invokeLater(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				new RealTestCaseXMLRead("tcs.xml");
//				stepFourCenterTabbedPane.getConsolePartScrollPane()
//				.getViewport().add(new ConsolePartDetailInfoTable(1));			
//			    stepFourCenterTabbedPane.getConsolePartScrollPane().getViewport().repaint();
//			    StepFourArea.append("实例化测试用例生成成功!");
//			}
//		});
//		
//		
//	}
//});
//
//fivestart.addActionListener(new ActionListener() {
//	      
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		StepFiveArea.append("实例化测试用例验证正在进行中.....\n"); 
//		StepFiveArea.append("正在导入测试用例.....\n");
//		
//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				ClientSocket clientSocket= new ClientSocket("192.168.150.142",5555);
//				clientSocket.Connection();
//				JFileChooser jfc=new JFileChooser();
//				jfc.setMultiSelectionEnabled(true);
//				jfc.showDialog(new JLabel(),"选择测试用例");
//				File[] files=jfc.getSelectedFiles();
//				StepFiveArea.append("正在发送数据.....\n");
//				clientSocket.sendFile(files);
//                StepFiveArea.append("发送数据完成!\n");
//                StepFiveArea.append("正在获得数据.....\n");
//                StepFiveArea.append("数据已经获得!\n");
////                List<TestCase> list= ClientRecThread.getTestCaseList();
//                List<TestCase> list=new ArrayList<TestCase>() ;
//                for(int i=1;i<4;i++){
//                	TestCase tc=new TestCase();
//                	if(i==1){
//                		tc.setTestCaseID(String.valueOf(1));
//                		tc.setContent("content"+1);
//                		tc.setState("state"+1);
//                		tc.setResult("失败");
//                	}
//                	if(i==2){
//                		tc.setTestCaseID(String.valueOf(2));
//                		tc.setContent("content"+2);
//                		tc.setState("state"+2);
//                		tc.setResult("成功");
//                	}
//                	if(i==3){
//                		tc.setTestCaseID(String.valueOf(3));
//                		tc.setContent("content"+3);
//                		tc.setState("state"+3);
//                		tc.setResult("成功");
//                	}
//                	list.add(tc);
//                }
//                System.out.println(list.size()+"测******试");
//                for(TestCase tc:list){
//                	System.out.println(tc.getTestCaseID()+"  "+tc.getContent()+" "+tc.getState()+"  "+tc.getResult());
//                }
//                //mainFrame.getStepFiveCenterTabbedPane().getTestcaseFile().setLayout(new GridLayout(1, 1));
//                //获得root的容器
//                JPanel jp=mainFrame.getStepFiveCenterTabbedPane().getTestcaseFile();
//                //获得报表的容器
//                JPanel jp1=JFreeChartTest.getJFreeChartTest(list);
//                //将表格放到JScrollPane容器中
//                JScrollPane jsp=new JScrollPane(new ConsolePartTestCaseInfoTable(list));
//                JSplitPane js=new JSplitPane(JSplitPane.VERTICAL_SPLIT,
//                		jsp,jp1);
//        		js.setDividerLocation(300);
////			    jp.add(new ConsolePartTestCaseInfoTable(list), BorderLayout.NORTH);
////			    jp.add(jp1,BorderLayout.CENTER);
//        		jp.add(js);
//                mainFrame.getStepFiveCenterTabbedPane().getTestcaseFile().updateUI();
//
//			}
//		});
//	}
//});
//
//Sixstart.addActionListener(new ActionListener() {
//	
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		StepSixArea.append("一致性测试正在进行.....\n");
//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					JSplitPane jp=ExistTest.existTest();
//					mainFrame.getStepSixCenterTabbedPane().getConsolePartScrollPane().setLayout(new BorderLayout());
//					mainFrame.getStepSixCenterTabbedPane().getConsolePartScrollPane().validate();
//					mainFrame.getStepSixCenterTabbedPane().getConsolePartScrollPane().add(jp,BorderLayout.CENTER);
//					mainFrame.getStepSixCenterTabbedPane().getConsolePartScrollPane().revalidate();
//					 StepSixArea.append("一致性测试测试成功!");
//					//System.out.println(jvp);
//					//.add(jp);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			
//			   
//			}
//		});
//		
//		
//	}
//});
//		homebutton.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				clearSelection();		
//				homebutton.setForeground(Color.RED);	
//				JLabel jLabel=new JLabel();
//				jLabel.setText(homebutton.getText());
//				jLabel.setFont(new Font("宋体", Font.BOLD, 20));
//				jLabel.setForeground(Color.white);
//				JPanel labelpanel=mainFrame.getStepJLabel();
//				labelpanel.setLayout(new GridBagLayout());
//				labelpanel.removeAll();
//				labelpanel.add(jLabel,new GBC(0, 0).setWeight(1, 0));				     				
//			    mainFrame.getCenterTabPanel().removeAll();
//				mainFrame.getCenterTabPanel().add(mainFrame.getHomePanel());
//				mainFrame.getConsolePart().setVisible(false);
//				mainFrame.getOpreationPart().setVisible(false);	
//				mainFrame.getContentPane().repaint();
//			}
//		});						
//		step1button.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				clearSelection();
//				//显示控制面板和操作面板			  			
//				//步骤一按钮高亮			
//				step1button.setForeground(Color.RED);							
//				//首先移除欢迎界面
//			
//				//修改原来的标题面板
//				JLabel jLabel=new JLabel();
//				jLabel.setText(step1button.getText());
//				jLabel.setFont(new Font("宋体", Font.BOLD, 20));
//				jLabel.setForeground(Color.white);
//				JPanel labelpanel=mainFrame.getStepJLabel();
//				labelpanel.setLayout(new GridBagLayout());
//				labelpanel.removeAll();
//				labelpanel.add(jLabel,new GBC(0, 0).setWeight(1, 0));
//													
//			    //添加操作面板
//				ClearOpreationPanel();
//				operationPanel.add(mainFrame.getProjectTree());													
//				mainFrame.getCenterTabPanel().removeAll();
//				mainFrame.getCenterTabPanel().add(mainFrame.getStepOneCenterTabbedPane());	
//				
//				//添加过程信息组件	
//			    clearConsolePart();				  
//			    consolePart.setTitle("UML模型建立过程信息");
//				consolePart.add(new ConsoleMessageTabbedPane("详细信息",new JTextArea("UML模型正在建立中......\n\n\n\n\n\n")));				
//				wakeupUI();
//				mainFrame.getContentPane().repaint();
//				step2button.setEnabled(true);//第一步点击之后，第二步可点击
//			}
//		});
//		step2button.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				clearSelection();
//				step2button.setForeground(Color.RED);
//				//标题
//				JLabel jLabel=new JLabel();
//				jLabel.setText(step2button.getText());
//				jLabel.setFont(new Font("宋体", Font.BOLD, 20));
//				jLabel.setForeground(Color.white);
//				JPanel labelpanel=mainFrame.getStepJLabel();
//				labelpanel.setLayout(new GridBagLayout());
//				labelpanel.removeAll();
//				labelpanel.add(jLabel,new GBC(0, 0).setWeight(1, 0));
//				labelpanel.add(Twostart,new GBC(1, 0));
//				labelpanel.add(new JButton("暂停"),new GBC(2, 0));
//				
//				ClearOpreationPanel();
//			    operationPanel.add(mainFrame.getModelTransformationPanel());
//			    
//			    clearConsolePart();			    
//			    consolePart.setTitle("UML模型转化时间自动机过程信息");
//				
//				consolePart.add(new ConsoleMessageTabbedPane("详细信息",StepTwoArea));
//						
//				mainFrame.getCenterTabPanel().removeAll();
//				mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoCenterTabbedPane());
//			
//				
//							
//				mainFrame.getContentPane().repaint();
//				step3button.setEnabled(true);
//			}
//		});
//		step3button.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				clearSelection();
//			 
//				step3button.setForeground(Color.RED);
//			
//				mainFrame.getCenterTabPanel().removeAll();
//				
//				mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeCenterTabbedPane());
//				JLabel jLabel=new JLabel();
//				jLabel.setText(step3button.getText());
//				jLabel.setFont(new Font("宋体", Font.BOLD, 20));
//				jLabel.setForeground(Color.white);
//				JPanel labelpanel=mainFrame.getStepJLabel();
//				labelpanel.setLayout(new GridBagLayout());
//				labelpanel.removeAll();
//				labelpanel.add(jLabel,new GBC(0, 0).setWeight(1, 0));																			
//				labelpanel.add(Threestart,new GBC(1, 0));
//				labelpanel.add(new JButton("暂停"),new GBC(2, 0));	
//				
//				ClearOpreationPanel();//操作
//				operationPanel.add(new AbstractTestCaseGenerationPanel());				
//				clearConsolePart();//控制台			    
//				consolePart.setTitle("抽象测试用例生成过程信息");
//			    consolePart.add(new ConsoleMessageTabbedPane("详细信息",StepThreeArea));			
//				mainFrame.getContentPane().repaint();
//				step4button.setEnabled(true);
//			}
//		});
//		step4button.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				clearSelection();
//			
//				step4button.setForeground(Color.RED);
//				mainFrame.getMainPanel().remove(mainFrame.getWelcomePanel());
//				
//				JLabel jLabel=new JLabel();
//				jLabel.setText(step4button.getText());
//				jLabel.setFont(new Font("宋体", Font.BOLD, 20));
//				jLabel.setForeground(Color.white);
//				JPanel labelpanel=mainFrame.getStepJLabel();
//				labelpanel.setLayout(new GridBagLayout());
//				labelpanel.removeAll();
//				labelpanel.add(jLabel,new GBC(0, 0).setWeight(1, 0));
//				labelpanel.add(Fourstart,new GBC(1, 0));
//				labelpanel.add(new JButton("暂停"),new GBC(2, 0));
//				
//				ClearOpreationPanel();
//				operationPanel.add(new TestCaseInstantiationPanel());
//				
//				mainFrame.getCenterTabPanel().removeAll();
//				mainFrame.getCenterTabPanel().add(stepFourCenterTabbedPane);
//				
//				clearConsolePart();	
//			
//				consolePart.setTitle("抽象测试用例实例化过程信息");
//				consolePart.add(new ConsoleMessageTabbedPane("详细信息",StepFourArea));
//				mainFrame.getContentPane().repaint();
//				step5button.setEnabled(true);
//			}
//		});
//		step5button.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				clearSelection();		
//				step5button.setForeground(Color.RED);
//				mainFrame.getMainPanel().remove(mainFrame.getWelcomePanel());
//				
//				JLabel jLabel=new JLabel();
//				jLabel.setText(step5button.getText());
//				jLabel.setFont(new Font("宋体", Font.BOLD, 20));
//				jLabel.setForeground(Color.white);
//				JPanel labelpanel=mainFrame.getStepJLabel();
//				labelpanel.setLayout(new GridBagLayout());
//				labelpanel.removeAll();
//				labelpanel.add(jLabel,new GBC(0, 0).setWeight(1, 0));
//				labelpanel.add(fivestart,new GBC(1, 0));
//				labelpanel.add(fiveshow,new GBC(2, 0));
//				
////				ClearOpreationPanel();
////				operationPanel.add(new TestCaseConfirmationPanel());
//				
//				
//				mainFrame.getCenterTabPanel().removeAll();
//				mainFrame.getCenterTabPanel().add(mainFrame.getStepFiveCenterTabbedPane());
//				clearConsolePart();
//			
//				consolePart.setTitle("测试用例实例验证过程信息");
//    			consolePart.add(new ConsoleMessageTabbedPane("详细信息",StepFiveArea));	
//							
//				mainFrame.getContentPane().repaint();
//			}
//		});
//		//测试按钮的触发事件
//		ceshiButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				clearSelection();		
//				step5button.setForeground(Color.RED);
//				mainFrame.getMainPanel().remove(mainFrame.getWelcomePanel());
//				
//				JLabel jLabel=new JLabel();
//				jLabel.setText(ceshiButton.getText());
//				jLabel.setFont(new Font("宋体", Font.BOLD, 20));
//				jLabel.setForeground(Color.white);
//				JPanel labelpanel=mainFrame.getStepJLabel();
//				labelpanel.setLayout(new GridBagLayout());
//				labelpanel.removeAll();
//				labelpanel.add(jLabel,new GBC(0, 0).setWeight(1, 0));
//				labelpanel.add(Sixstart,new GBC(1, 0));
//				//labelpanel.add(new JButton("暂停"),new GBC(2, 0));
//				
//				ClearOpreationPanel();
//				//operationPanel.add(new TestExistenceTabbedPane());
//				
//				
//				mainFrame.getCenterTabPanel().removeAll();
//				mainFrame.getCenterTabPanel().add(mainFrame.getStepSixCenterTabbedPane());
//				clearConsolePart();
//			
//				consolePart.setTitle("一致性的验证");
//    			consolePart.add(new ConsoleMessageTabbedPane("详细信息",StepSixArea));	
//							
//				mainFrame.getContentPane().repaint();
//			}
//		});
//	}
//	/*
//	 * 使原来不可见的界面重新可见(除了首页,都需要重新可见,首页已使其他界面不可见)
//	 */
//	public void wakeupUI()
//	{
//		mainFrame.getConsolePart().setVisible(true);
//		mainFrame.getOpreationPart().setVisible(true);
//	}
//}
