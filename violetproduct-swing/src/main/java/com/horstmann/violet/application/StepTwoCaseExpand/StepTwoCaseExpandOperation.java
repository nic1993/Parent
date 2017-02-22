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
import javax.swing.SwingConstants;
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
import cn.edu.hdu.lab.service.parser.InvalidTagException;
import cn.edu.hdu.lab.service.sd2tmc.WorkImpl;

import com.horstmann.violet.application.StepTwoModelExpand.ProgressUI;
import com.horstmann.violet.application.StepTwoModelExpand.ScenceTabelPanel;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoMatrixPanel;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoTabelPanel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.menu.FileMenu;

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
	private JLabel label;
	private JPanel labelPanel;
	private List<InterfaceIsogenySD> IISDList;
	private List<String> relations;  //获取关系集合
	private List<Double> relationsData;
	private List<double[][]> tableDatas;
	private Work worker;
	private int number;
	
	private Map<String, ScenceTabelPanel> caseTabelMap;
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
		
		layout.setConstraints(numberLabel, new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(10, 28, 10, 2));
		layout.setConstraints(numberTextField, new GBC(1, 0, 1, 1).setFill(GBC.BOTH).setWeight(0.1, 1).setInsets(10, 2, 10, 10));
		layout.setConstraints(startExpandButton, new GBC(2, 0, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(10));
		layout.setConstraints(startVerificationButton, new GBC(3, 0, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(10));
		layout.setConstraints(verificationProgressBar, new GBC(4, 0, 1, 1).setFill(GBC.BOTH).setWeight(1, 1).setInsets(10,10,10,21));
		layout.setConstraints(otherPanel, new GBC(0, 1, 5, 1).setFill(GBC.BOTH).setWeight(1, 1).setInsets(10, 28, 10, 0));
	}
	public void initComponent()
	{
		caseTabelMap = new HashMap<String, ScenceTabelPanel>();
		
		relations = new ArrayList<String>();
		relationsData = new ArrayList<Double>();
		tableDatas = new ArrayList<double[][]>();
		numberLabel = new JLabel("用户数:");
		numberLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		
		numberTextField = new JTextField();
		numberTextField.setFont(new Font("宋体", Font.PLAIN, 16));
		
		startExpandButton = new JButton("开始扩展");
		startVerificationButton = new JButton("开始验证");
		startVerificationButton.setEnabled(false);
		
		verificationProgressBar = new JProgressBar();
		verificationProgressBar.setUI(new ProgressUI(verificationProgressBar,Color.green));
		verificationProgressBar.setPreferredSize(new Dimension(750,30));
		
		System.out.println("======"+this.getPreferredSize());
		rightpanel = new JPanel();
//		rightpanel.setLayout(new GridBagLayout());
//		JLabel label = new JLabel("当前选择扩展的markov�?: �?");
//		label.setFont(new Font("宋体",Font.PLAIN,16));
//		rightpanel.add(label,new GBC(0, 0,1,1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(10, 40, 5, 0));
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
		
		label = new JLabel();
		label.setFont(new Font("宋体", Font.PLAIN, 16));
		label.setText("当前模型为:Primary");
		labelPanel = new JPanel();
		
		otherPanel.setLayout(new GridBagLayout());
		otherPanel.add(label, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 0, 0, 0));
		otherPanel.add(labelPanel,new GBC(1, 0).setFill(GBC.BOTH).setWeight(1, 0));
		buttonListen();

	}
	public void buttonListen()
	{
		startExpandButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 
				worker=new WorkImpl();
				try {
					worker.transInitial(StaticConfig.umlPath);
				} catch (InvalidTagException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	 //瑙ｆ瀽UML妯�?��?�鐨刋ML鏂囦�?
                IISDList=worker.provideIsogencySD();//获取用例场景信息
                
				if(numberTextField.getText() == null)
				{ 
				   //添加弹出�?
				}
				else if (!isNumeric(numberTextField.getText())) {
					//添加弹出�?
				}
				else {
					stepTwoCaseExpandTabbedPane.getCaseExpandPanel().removeAll();
					number = Integer.parseInt(numberTextField.getText());
                    int j;
					for(j = 0;j < number;++j){
						int i = 0; //标记位置
						StepTwoTabelPanel stepTwoTabelPanel = new StepTwoTabelPanel();
						stepTwoTabelPanel.getTitleLabel().setText("用户"+(j+1));
						stepTwoTabelPanel.getTabelPanel().setLayout(new GridBagLayout());
						for(InterfaceIsogenySD interfaceIsogenySD : IISDList)
						{
							if(interfaceIsogenySD.getISDList().size() > 1)
							{
								relations.add(interfaceIsogenySD.getUcName());  //添加用例名称
								List<InterfaceSD> ISDList = interfaceIsogenySD.getISDList();
								for(InterfaceSD interfaceSD : ISDList)
								{
									relations.add(interfaceSD.getName()); //添加用例场景名称
								}
								ScenceTabelPanel scenceTabelPanel = new ScenceTabelPanel(relations);
								StepTwoMatrixPanel stepTwoMatrixPanel = new StepTwoMatrixPanel();
								stepTwoMatrixPanel.getTitleLabel().setText("用例名称:"+interfaceIsogenySD.getUcName());
								stepTwoMatrixPanel.getTabelPanel().add(scenceTabelPanel);
								stepTwoTabelPanel.getTabelPanel().add(stepTwoMatrixPanel, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
								i++;
								relations.clear();
							}
						}
					    stepTwoCaseExpandTabbedPane.getCaseExpandPanel().add(stepTwoTabelPanel,new GBC(0, j).setFill(GBC.BOTH).setWeight(1, 0));
					}
					stepTwoCaseExpandTabbedPane.getCaseExpandPanel().add(new JPanel(), new GBC(0, ++j).setFill(GBC.BOTH).setWeight(1, 1));
					startVerificationButton.setEnabled(true);
				}
			}
		});
		startVerificationButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				relations.clear();
				for(InterfaceIsogenySD interfaceIsogenySD : IISDList)
				{
					List<InterfaceSD> ISDList = interfaceIsogenySD.getISDList();
					if(ISDList.size() == 1)
					{
						relations.add(ISDList.get(0).getName());
						ISDList.get(0).setPro(1.0);
						relationsData.add(1.0);
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
					   		 System.out.println("======"+location);
					   		 if(location != -1)
					   		 {
					   			JPanel panel = ((StepTwoTabelPanel) stepTwoCaseExpandTabbedPane.getCaseExpandPanel().getComponent(i)).getTabelPanel();
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
							ISDList.get(k).setPro(datas[k]);
							relationsData.add(datas[k]);
						}
						tableDatas.clear();
					}
				}
				//生成验证报告
				if(relations.size() > 0)
				{
					stepTwoCaseExpandTabbedPane.getValidationResults().removeAll();
					ScenceTabelPanel scenceTabelPanel = new ScenceTabelPanel(relations, relationsData);
					stepTwoCaseExpandTabbedPane.getValidationResults().add(scenceTabelPanel);
					caseTabelMap.put(mainFrame.getStepTwoModelOperation().getModel_name(), scenceTabelPanel);
					label.removeAll();
					label.setText("对Primary用例扩展验证完成,可以对该模型进行一致性验证!");
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
	
	public JPanel getOtherPanel(){
		return otherPanel;
	}
	public Map<String, ScenceTabelPanel> getCaseTabelMap() {
		return caseTabelMap;
	}
	
}
