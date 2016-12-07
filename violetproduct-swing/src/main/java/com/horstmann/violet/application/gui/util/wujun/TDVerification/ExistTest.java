package com.horstmann.violet.application.gui.util.wujun.TDVerification;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.horstmann.violet.application.gui.StepSixCenterTabbedPane;

public class ExistTest{
	public static Scanner cin = new Scanner(System.in);
	public static ArrayList <UppaalTemPlate> templates = new ArrayList <UppaalTemPlate> ();
	static String []inputTransitionNames = null;
	static ArrayList<UppaalTransition> transitions = new ArrayList<>();
	static double locationX = -1;
	static double transitionX = -1;
	static boolean f = false;
	static ArrayList<Double> time1 = new ArrayList<Double>();
	static ArrayList<Double> time2 = new ArrayList<Double>();
	static ArrayList<Double> time3 = new ArrayList<Double>();
	static ArrayList<Double> time2First = new ArrayList<Double>();
	static ArrayList<Double> time2Last = new ArrayList<Double>();
	
	static HashMap<String, UppaalLocation> locationByKey = new HashMap<>();
	static ArrayList<UppaalLocation> isVisitedLocaiton = new ArrayList<>();
	public static JLabel jl0;
	public static JLabel jl;
	public static JTextField jtf;
	public static JButton jb;
	public static JButton jb2;
	public static JButton jb3;
	public static JTextArea jta;
	public static JLabel jl1;
	public static int a;
	public static String s;
	public static String temp;
	//public static JScrollPane jsp;
	public  static JSplitPane existTest() throws Exception//void main(String[] args) throws Exception 
	{   
//		super("一致性的测试");
//		JPanel jpRoot=new JPanel();
		JRadioButton jrb1=new JRadioButton("1.存在性",false);
		JRadioButton jrb2=new JRadioButton("2.向前一致性",false);
		JRadioButton jrb3=new JRadioButton("3.逆向一致性",false);
		JRadioButton jrb4=new JRadioButton("4.双向一致性",false);
		JRadioButton jrb5=new JRadioButton("5.实时测试",false);
		ButtonGroup bg=new ButtonGroup();
		bg.add(jrb1);
		bg.add(jrb2);
		bg.add(jrb3);
		bg.add(jrb4);
		bg.add(jrb5);
		jl0=new JLabel();
		jl=new JLabel("输入的消息序列:");
		jtf=new JTextField(20);
		jb=new JButton("开始测试");
		jta=new JTextArea(150,150);
		//jsp=new JScrollPane(jta);
//		this.setLayout(new FlowLayout());
		final JPanel jp=new JPanel();
		jl1=new JLabel("选择测试方向:");
		jp.add(jl1);
		jp.add(jrb1);
		jp.add(jrb2);
		jp.add(jrb3);
		jp.add(jrb4);
		jp.add(jrb5);
		jp.add(jl);
		jp.add(jtf);
		jp.add(jb);
		JScrollPane jScrollPane=new JScrollPane(jta);
		//jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		JSplitPane js=new JSplitPane(JSplitPane.VERTICAL_SPLIT,jp,jScrollPane);
		js.setDividerLocation(100);
		
//		GridBagLayout layout = new GridBagLayout();
//		jpRoot.setLayout(layout);
//		jScrollPane.setPreferredSize(new Dimension(200, 200));
//		jpRoot.setPreferredSize(new Dimension(500, 500));
//		jpRoot.add(jp);
//		jpRoot.add(jScrollPane);
//        layout.setConstraints(jp, new GB(0, 0, 1, 3).setFill(GB.BOTH).setWeight(0, 0));
//        layout.setConstraints(jScrollPane, new GB(0, 2, 2, 3).setFill(GB.BOTH).setWeight(1, 1));
        
		//jta.setBackground(Color.BLUE);
		SAXReader reader=new SAXReader();//获取解析器
	    Document dom= reader.read("1.xml");//解析XML获取代表整个文档的dom对象
	    Element root=dom.getRootElement();//获取根节点
	    
	    Read uppaal=new Read();
	    uppaal.load(root);
	    
	    // 添加transiton到sourceLocation的transitionList中
	    templates = uppaal.getUppaalTemplates();
	    
	    for(UppaalLocation locationI : templates.get(0).getLocations()) {
	    	locationByKey.put(locationI.getId(), locationI);
	    }
	    for(UppaalTransition transitionI : templates.get(0).getTransitions()) {
	    	String sourceId = "id" + transitionI.getSource();
	    	UppaalLocation sourceLocation = locationByKey.get(sourceId);
	    	sourceLocation.getTransitions().add(transitionI);
	    }
	    
	    // 对transition 根据时间进行排序
	    transitions = templates.get(0).transitions;
	    Collections.sort(transitions, new Comparator<UppaalTransition>() {
	        @Override
	        public int compare(UppaalTransition o1, UppaalTransition o2) {
	          return (int)(o1.getTime() - o2.getTime());
	        }
	      });
	    //输出存在的测试用例
//	    for (UppaalTransition transition : templates.get(0).getTransitions()) {
//	 			jta.append(transition.getName() + " " + transition.getTime()+"\n");
//	 			
//	 		}
	    jta.append("选择要进行的一致性测试:1存在性；2前向一致性；3逆向一致性；4双向一致性测试；5实时测试\n");
	    jta.append("输入测试的用例\n");
	     //为第一个单选框设置 事件
		jrb1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			   a=Integer.parseInt(e.getActionCommand().substring(0,1));
        			jta.append("\n输入消息序列1  xxx xxx");
	    		jb.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						temp = jtf.getText();
						inputTransitionNames = temp.split(" ");
						 time1 = findTimeAccordingToInputMessage().get(0);
			   	    		if(time1.isEmpty())
			  	    			{
			   	    			  jta.append("***不存在此消息序列\n");
			  	    			  jta.append("选择要进行的一致性测试:1存在性；2前向一致性；3逆向一致性；4双向一致性测试；5实时测试 ");
			  	    			}
			 	    		else
			  	    			jta.append("***存在此消息序列\n");
						
					}
				});
			}
		});
		
		//为第二个单选框添加事件
		jrb2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				a=Integer.parseInt(e.getActionCommand().substring(0,1));
				jta.append("\n输入消息序列1  xxx xxx\n");
        		   
	    		 jb.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						  temp = jtf.getText();
		          		  inputTransitionNames = temp.split(" ");
						  time1 = findTimeAccordingToInputMessage().get(1);
				    		if(time1.isEmpty())
				    			{
				    			   jta.append("***不存在此消息序列\n"); 
				    			   jta.append("\n选择要进行的一致性测试:1存在性；2前向一致性；3逆向一致性；4双向一致性测试；5实时测试\n ");
 					    		 //  jta.append("\n输入消息序列1  xxx xxx\n");
				    			}
				    		else
				    	     	{
				    			  jta.append("***存在此消息序列\n");
				    			  jta.append("\n输入消息序列2  \n");
				    	     	}
				    		   jb.setVisible(false);
				    		   jb2=new JButton("开始测试1");
				    		   jp.add(jb2);
				    		   jb2.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									temp = jtf.getText();
									inputTransitionNames = temp.split(" ");
									
									time2 = findTimeAccordingToInputMessage().get(0);
									if(time2.isEmpty())
									   {
										jta.append("***不存在此消息序列\n"); 
										jta.append("\n选择要进行的一致性测试:1存在性；2前向一致性；3逆向一致性；4双向一致性测试；5实时测试\n ");
										}
									else
										jta.append("***存在此消息序列\n");
									   
									if(compare(time1,time2))
										{
										 jta.append("***符合前向一致性\n");
										}
									else
									   {
										jta.append("***不符合前向一致性\n");
									   }
									
								}
							});
					}
				});
			}
		});
		
		//为第三个单选框添加事件
		jrb3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jta.append("\n输入消息序列1  xxx xxx\n");
	    		jb.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						temp = jtf.getText();
		          		inputTransitionNames = temp.split(" ");
			    		
			    		 time2 = findTimeAccordingToInputMessage().get(0);
			    		if(time2.isEmpty())
			    			{
			    			 jta.append("***不存在此消息序列\n");
			    			 jta.append("\n输入要进行的一致性测试:1存在性；2前向一致性；3逆向一致性；4双向一致性测试；5实时测试 \n"); 
			    			}
			    		else
			    			{jta.append("***存在此消息序列\n");
			    		    jta.append("\n输入消息序列2  xxx xxx\n");
			    			}
			    		    jb.setVisible(false);
			    		    jb2=new JButton("开始测试");
			    		    jp.add(jb2);
			    		 jb2.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								temp = jtf.getText();
								inputTransitionNames = temp.split(" ");
								
								time1 = findTimeAccordingToInputMessage().get(1);
								if(time1.isEmpty())
								{
									jta.append("***不存在此消息序列\n"); 
									jta.append("\n选择要进行的一致性测试:1存在性；2前向一致性；3逆向一致性；4双向一致性测试；5实时测试 \n");	
								}
								else
									{
									jta.append("***存在此消息序列\n");
									}
								if(!compare(time1,time2))
									jta.append("***符合逆向一致性\n");
								else
									jta.append("***不符合逆向一致性\n");
								jta.append("\n选择要进行的一致性测试:1存在性；2前向一致性；3逆向一致性；4双向一致性测试；5实时测试 \n");	
								
							}
						});
					}
				});
				
			}
		});
		
		//为第四个单选框添加事件
		jrb4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jta.append("\n输入消息序列1  xxx xxx\n");
	    		 jb.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						 temp = jtf.getText();
			          	 inputTransitionNames = temp.split(" ");
			    		
			    		 time1 = findTimeAccordingToInputMessage().get(1);
			    		if(time1.isEmpty())
			    			{
			    			  jta.append("***不存在此消息序列\n"); 
			    			  jta.append("\n选择要进行的一致性测试:1存在性；2前向一致性；3逆向一致性；4双向一致性测试；5实时测试\n ");  
			    			}
			    		else
		             	    {jta.append("***存在此消息序列\n");
				    		  jta.append("\n输入消息序列2  xxx xxx\n");
		             	    }
			    		 jb.setVisible(false);
			    		 jb2=new JButton("开始测试1");
			    		 jp.add(jb2);
			    		 jb2.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								 temp = jtf.getText();
					          	 inputTransitionNames = temp.split(" ");
					    		
					    		 time2First = findTimeAccordingToInputMessage().get(0);
					    		 time2Last =findTimeAccordingToInputMessage().get(1);
					    		if(time2First.isEmpty())
					    			{
					    			  jta.append("***不存在此消息序列\n"); 
					    			  jta.append("\n选择要进行的一致性测试:1存在性；2前向一致性；3逆向一致性；4双向一致性测试；5实时测试 \n");  
					    			}
					    		else
					    			{jta.append("***存在此消息序列\n");
						    		 jta.append("\n输入消息序列3  xxx xxx\n");
					    			}
						    		 jb2.setVisible(false);
					    		     jb3=new JButton("开始测试");
					    		     jp.add(jb3);
					    		     jb3.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										temp = jtf.getText();
							    		 inputTransitionNames = temp.split(" ");
							    		
							    		 time3 = findTimeAccordingToInputMessage().get(0);
							    		if(time3.isEmpty())
							    			{
							    			  jta.append("***不存在此消息序列\n"); 
							    			  jta.append("输入要进行的一致性测试:1存在性；2前向一致性；3逆向一致性；4双向一致性测试；5实时测试 ");  
							    			}
							    		else
							    			jta.append("***存在此消息序列\n");
							    		
							    		if(compare(time1,time2First) && compare(time2Last,time3))
							    			jta.append("***符合双向一致性测试\n");
							    		else
							    			{jta.append("***不符合双向一致性测试\n");
							    		   jta.append("\n输入要进行的一致性测试:1存在性；2前向一致性；3逆向一致性；4双向一致性测试；5实时测试\n ");  
							    			}
							    	}
								});
					    		
								
							}
						});
			    		
						
					}
				});
				
			}
		});
         //为第五个单选框添加事件
		jrb5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 f = false;
	    		 locationX = 0;
	    		 transitionX = 0;
	    		 jta.append("\n输入 最小时间    最大时间\n");
	    		 jb.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
//						double min = cin.nextDouble();//#######
//			    		double max = cin.nextDouble();//#######
			    		temp = jtf.getText();
			    		 inputTransitionNames = temp.split(" ");
			    		 double min=Double.parseDouble(inputTransitionNames[0]);
			    		 double max=Double.parseDouble(inputTransitionNames[1]);
			    		UppaalLocation initLocation = templates.get(0).getLocations().get(0);
			    		UppaalLocation virtualLocation = new UppaalLocation();
			    		virtualLocation.init = initLocation.init;
			    		virtualLocation.setTransitions(initLocation.transitions);
			    		jta.append("初始状态：" + initLocation.getName());
			    		// isVisitedLocaiton.add(initLocation);
			    		isVisitedLocaiton.clear();
			    		if (searchPathWithRangeOfTime(new UppaalTransition(), virtualLocation, 0, 0, min, max)) {
			    			jta.append("***满足给定约束***\n");
						} else {
							jta.append("***不满足给定约束***\n");
						}
			    		jta.append("选择要进行的一致性测试:1存在性；2前向一致性；3逆向一致性；4双向一致性测试；5实时测试 ");  
					}
				});
				
			}
		});
           		 
		 
//	    while(cin.hasNext()) 
//	    {
//	    	
//	    	String c = cin.next();
//	    	switch(c)
//	    	{
//	    	case "1":
//	    		System.out.println("输入消息序列xxx xxx");
//	    		 temp = cin.nextLine();
//	    		 temp = cin.nextLine();
//	    		 inputTransitionNames = temp.split(" ");
//	    		
//	    		 time1 = findTimeAccordingToInputMessage().get(0);
//	    		if(time1.isEmpty())
//	    			{System.out.println("***不存在此消息序列"); break;}
//	    		else
//	    			System.out.println("***存在此消息序列");
//	    		break;
//	    	
//	    	case "2":
//	    		System.out.println("输入消息序列1  xxx xxx");
//	    		 temp = cin.nextLine();
//	    		 temp = cin.nextLine();
//	    		 inputTransitionNames = temp.split(" ");
//	    		
//	    		 time1 = findTimeAccordingToInputMessage().get(1);
//	    		if(time1.isEmpty())
//	    			{System.out.println("***不存在此消息序列"); break;}
//	    		else
//	    			System.out.println("***存在此消息序列");
//	    		System.out.println("输入消息序列2  xxx xxx");
//	    		 
//	    		 temp = cin.nextLine();
//	    		 inputTransitionNames = temp.split(" ");
//	    		
//	    		 time2 = findTimeAccordingToInputMessage().get(0);
//	    		if(time2.isEmpty())
//	    			{System.out.println("***不存在此消息序列"); break;}
//	    		else
//	    			System.out.println("***存在此消息序列");
//	    		
//	    		if(compare(time1,time2))
//	    			System.out.println("***符合前向一致性");
//	    		else
//	    			System.out.println("***不符合前向一致性");
//	    		break;
//	    	case "3":
//	    		System.out.println("输入消息序列1  xxx xxx");
//	    		 temp = cin.nextLine();
//	    		 temp = cin.nextLine();
//	    		 inputTransitionNames = temp.split(" ");
//	    		
//	    		 time2 = findTimeAccordingToInputMessage().get(0);
//	    		if(time2.isEmpty())
//	    			{System.out.println("***不存在此消息序列"); break;}
//	    		else
//	    			System.out.println("***存在此消息序列");
//	    		System.out.println("输入消息序列2  xxx xxx");
//	    		 
//	    		 temp = cin.nextLine();
//	    		 inputTransitionNames = temp.split(" ");
//	    		
//	    		 time1 = findTimeAccordingToInputMessage().get(1);
//	    		if(time1.isEmpty())
//	    			{System.out.println("***不存在此消息序列"); break;}
//	    		else
//	    			System.out.println("***存在此消息序列");
//	    		
//	    		if(!compare(time1,time2))
//	    			System.out.println("***符合逆向一致性");
//	    		else
//	    			System.out.println("***不符合逆向一致性");
//	    		break;
//	    	case "4":
//	    		System.out.println("输入消息序列1  xxx xxx");
//	    		 temp = cin.nextLine();
//	    		 temp = cin.nextLine();
//	    		 inputTransitionNames = temp.split(" ");
//	    		
//	    		 time1 = findTimeAccordingToInputMessage().get(1);
//	    		if(time1.isEmpty())
//	    			{System.out.println("***不存在此消息序列"); break;}
//	    		else
//	    			System.out.println("***存在此消息序列");
//	    		
//	    		System.out.println("输入消息序列2  xxx xxx");
//	    		 
//	    		 temp = cin.nextLine();
//	    		 inputTransitionNames = temp.split(" ");
//	    		
//	    		 time2First = findTimeAccordingToInputMessage().get(0);
//	    		 time2Last =findTimeAccordingToInputMessage().get(1);
//	    		if(time2First.isEmpty())
//	    			{System.out.println("***不存在此消息序列"); break;}
//	    		else
//	    			System.out.println("***存在此消息序列");
//	    		
//	    		System.out.println("输入消息序列3  xxx xxx");
//	    		 
//	    		 temp = cin.nextLine();
//	    		 inputTransitionNames = temp.split(" ");
//	    		
//	    		 time3 = findTimeAccordingToInputMessage().get(0);
//	    		if(time3.isEmpty())
//	    			{System.out.println("***不存在此消息序列"); break;}
//	    		else
//	    			System.out.println("***存在此消息序列");
//	    		
//	    		if(compare(time1,time2First) && compare(time2Last,time3))
//	    			System.out.println("***符合双向一致性测试");
//	    		else
//	    			System.out.println("***不符合双向一致性测试");
//	    		break;
//	    	case "5":
//	    		 f = false;
//	    		 locationX = 0;
//	    		 transitionX = 0;
//	    		System.out.println("输入 最小时间    最大时间");
//	    		double min = cin.nextDouble();
//	    		double max = cin.nextDouble();
//	    		UppaalLocation initLocation = templates.get(0).getLocations().get(0);
//	    		UppaalLocation virtualLocation = new UppaalLocation();
//	    		virtualLocation.init = initLocation.init;
//	    		virtualLocation.setTransitions(initLocation.transitions);
//	    		System.out.println("初始状态：" + initLocation.getName());
//	    		// isVisitedLocaiton.add(initLocation);
//	    		isVisitedLocaiton.clear();
//	    		if (searchPathWithRangeOfTime(new UppaalTransition(), virtualLocation, 0, 0, min, max)) {
//					System.out.println("***满足给定约束***");
//				} else {
//					System.out.println("***不满足给定约束***");
//				}
//	    		
//	    		break;
//	    	default:
//	    		System.out.println("输入错误！");
//	    		break;
//	    		
//	    	}
//	    
//	    	System.out.println("输入要进行的一致性测试:1存在性；2前向一致性；3逆向一致性；4双向一致性测试；5实时测试 ");
//		    
//	    }
	    
	    
//	    this.pack();
	    //this.setVisible(true);
//		 jpRoot.setVisible(true);
		 js.setVisible(true);
	    return js;
	}//constructor
	// 比较t1<t2 只要存在一条满足即可
	private static boolean compare(ArrayList<Double> t1, ArrayList<Double> t2) {
		for(int i = 0 ; i < t1.size(); i++)
			for(int j = 0; j < t2.size();j++)
			{
				if(t1.get(i) < t2.get(j))
					return true;
			}
		return false;
	}
	// 查找输入的消息序列  返回消息序列 的 开始时间 和 结束时间
	private static ArrayList<ArrayList<Double>> findTimeAccordingToInputMessage() {
		HashSet<String> isPrintedSet = new HashSet<>();
		ArrayList<ArrayList<Double>> res = new ArrayList<>();
		ArrayList<Double> startTimes =new ArrayList<Double>();
		ArrayList<Double> endTimes =new ArrayList<Double>();
		res.add(startTimes);
    	res.add(endTimes);
		double time0 = 0;
		double time1 = 0;
    	int count = 0;//计数第几个a
    	for (int i = 0; i < transitions.size(); i++) {
    		isPrintedSet.clear();
    		if (!(transitions.get(i).out && transitions.get(i).getName().charAt(transitions.get(i).getName().length() - 1) != '?')) {
				continue;
			}
			for (int j = i; j < transitions.size(); j++) {
				UppaalTransition transitionI = transitions.get(j);
				if(transitionI.out && transitionI.getName().charAt(transitionI.getName().length() - 1) != '?')
	    		{
	    			if (transitionI.getName().equals(inputTransitionNames[count])) { // 下一个外部消息等于输入的下一个
						
						if (count == 0) { // 记录第一个时间
							time0 = transitionI.getTime();
						}
	    				if (count == inputTransitionNames.length - 1) { // 记录最后一个时间
							time1 = transitionI.getTime();
						}
	    				if (!(isPrintedSet.contains(inputTransitionNames[count] + "--" + transitionI.getTime()))) {
	    					jta.append("********************\n");
	    					jta.append("*找到消息：" + inputTransitionNames[count]+"\n");
	    					jta.append("*该消息发生的时间为：" + transitionI.getTime()+"\n");
							isPrintedSet.add(inputTransitionNames[count] + "--" + transitionI.getTime());
						} 
	    				
						count++;
		    			if (count == inputTransitionNames.length) {
							break;
						}
					} else { // 下一个外部消息不等于输入的下一个
						jta.append("-映射第" + (count+1) + "条消息时不符合\n");
						jta.append("-第" + (count+1) + "条消息：" + transitionI.getName()+"\n");
						jta.append("-输入第" + (count+1) + "条消息：" + inputTransitionNames[count]+"\n");
						break;
					}
	    			
	    		} 
			}
			
			if (count == inputTransitionNames.length) {
				
				if (!startTimes.contains(time0)) { // 不是重复的路径
					startTimes.add(time0);
					endTimes.add(time1);
					jta.append("********************找到"+ startTimes.size() +"条符合输入消息序列的路径**********************\n");

				}
				
				
			}
			// 清空 重新找下一个开始
			count = 0;
		}	
    	
		return res;
	}
	static Comparator<UppaalTransition> comparator = new Comparator<UppaalTransition>() {
		@Override
		public int compare(UppaalTransition o1, UppaalTransition o2) {//排序？
			// TODO 自动生成的方法存根
			if(o1.getTime()>o2.getTime())
				return 1;
			else
				return 0;
		}     
	};
	private static boolean searchPathWithRangeOfTime(UppaalTransition lastTransition, UppaalLocation location, double locationMinTime, double transitionMaxTime, double requestMin, double resquestMax) {
		if (location.getFinl().equals("true")) { // 到达终止节点
			if (locationMinTime >= requestMin && locationMinTime + transitionMaxTime <= resquestMax) {
				jta.append("+终止状态：" + location.getName() + "满足给定的时间要求。\n");
				jta.append("+最小时间：" + locationMinTime + "  最大时间：" + (locationMinTime + transitionMaxTime)+"\n");
				jta.append("+满足时间要求的路径为：\n");
				UppaalLocation initLocation = templates.get(0).getLocations().get(0);
				jta.append("+" + initLocation.getName()+"\n");
				for(UppaalLocation locationI : isVisitedLocaiton) {
					jta.append("->" + locationI.getName()+"\n");
				}
				return true;
			} else {
				jta.append("-到达终止节点，但不满足给定的时间要求！\n");
				jta.append("-最小时间：" + locationMinTime + "  最大时间：" + (locationMinTime + transitionMaxTime)+"\n");
				return false;
			}
		}
		
		for(UppaalTransition transitionI : location.getTransitions()) {
			
			if (!transitionI.getName().equals("null")) {
				int nameLenth = transitionI.getName().length();
				if (transitionI.getName().charAt(nameLenth - 1) == '?') {
					if (!lastTransition.getName().equals(transitionI.getName().substring(0, nameLenth - 1))) {
						continue;
					}
				}
			}
			
			String targetId = "id" + transitionI.getTarget();
			UppaalLocation targetLocation = locationByKey.get(targetId);
			if (!isVisitedLocaiton.contains(targetLocation)) {
				jta.append("遍历状态：" + targetLocation.getName()+"\n");
				isVisitedLocaiton.add(targetLocation);
				if (searchPathWithRangeOfTime(transitionI, targetLocation, locationMinTime + location.getX(), 
						transitionMaxTime + transitionI.getDuration(), requestMin, resquestMax)) {
					return true;
				}
				isVisitedLocaiton.remove(isVisitedLocaiton.size() - 1); // huisu
			}
			
		}
		
		return false;
	}
	private static boolean DFS(String templateName, int count, String name, int i,double lx, double tx) {
		// TODO 自动生成的方法存根
		if(i == inputTransitionNames.length)
		{
			if(!f)
			{	
				locationX = lx;
				transitionX = tx;
			}
			return true;
		}
		int countb = 0;
		for(Object OTemplate:templates)
	    {
	    	UppaalTemPlate templateI = (UppaalTemPlate) OTemplate;
	    	if(templateI.getName().equals(templateName))//找到a？所在的template
	    	{
	    		Iterator transition_Iterator = (Iterator) templateI.getTransitions().iterator();
	    		while(transition_Iterator.hasNext()){
	    			
	    			UppaalTransition transitionI = (UppaalTransition) transition_Iterator.next();
	    			if(transitionI.out && transitionI.getName().equals(name))//是外部接口 并且 这个接口的message名是a？
	    			{
	    				count--;
	    				if(count == 0)//找到第count个a？
	    				{
	    					lx+= templateI.getLocations().get(transitionI.getTarget()).getX();//1+(5) .. (3)
	    					if(!transition_Iterator.hasNext())//没有下一个
	    						break;
	    					
	    					
		    				while(transition_Iterator.hasNext() )
	    					{
		    					transitionI = (UppaalTransition) transition_Iterator.next();
		    					
		    					tx+= transitionI.getX();//a!+(b+c!)
		    					
		    					
		    					if(transitionI.out == true)//下一个transition是接口
		    						break;
		    					else
		    						lx+= templateI.getLocations().get(transitionI.getTarget()).getX();//1+5+(6)
	    					}
		    				if(transitionI.out && transitionI.getName().equals(inputTransitionNames[i+1]+"!"))//下一个是b！
		    				{
		    					countb++;
		    						
		    						if(DFS(transitionI.getToName(), countb, inputTransitionNames[i+1]+"?", i+1,lx,tx))
		    						return true;
		    					
		    					
		    			
		    				}
	    			
	    				}	    			
	    			}
	    			
	    			if(i+1 == inputTransitionNames.length)
	    			{
	    				if(!f)
	    				{	
	    					locationX = lx;
	    					transitionX = tx;
	    				}
	    				return true;
	    			}
	    			
	    			if(transitionI.out && transitionI.getName().equals(inputTransitionNames[i+1]+"!"))
	    				countb++;
    	    	}
	    		
	    	}
	
	    }
		
		return false;
	}
	public static void main(String[] args) throws Exception {

			new ExistTest();
	
	}
}
