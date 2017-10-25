package com.horstmann.violet.application.StepTwoModelExpand;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;


import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.product.diagram.abstracts.property.Condition;
import com.horstmann.violet.product.diagram.sequence.CombinedFragment;
import com.realpersist.gef.model.Column;

public class ScenceTabelPanel extends JPanel implements Cloneable{
	   private JTable table;
	   private int flag;
       private List<String> titleList;
       private List<Double> dataList;
       private int[][] adjoinMatrix;
       
       private List<String> sceneNameList;
       private List<String> constraintName;
       private List<String> constraintValue;
       private ScenceTableMode scenceTabelModel;
	   private JScrollPane jScrollPane;
	   private DecimalFormat  df = new DecimalFormat();
	   private String pattern = "#0.000";
	   
	   private MainFrame mainFrame;
	   private int selectcolumn = 0; 
	   private int selectrow = 0;
       public ScenceTabelPanel(List<String> titleList) {
		// TODO Auto-generated constructor stub
              this.titleList = titleList;
              df.applyPattern(pattern);
              init();
              this.setLayout(new GridLayout());
              this.add(jScrollPane);
	}
       public ScenceTabelPanel(List<String> titleList,List<Double> dataList,int flag,MainFrame mainFrame) {
   		// TODO Auto-generated constructor stub
                 this.titleList = titleList;
                 this.dataList = dataList;
                 this.flag = flag;
                 this.mainFrame = mainFrame;
                 Verificationinit();
                 this.setLayout(new GridLayout());
                 this.add(jScrollPane);
   	}
       public ScenceTabelPanel(List<String> sceneNameList,List<String> constraintName,List<String> constraintValue,MainFrame mainFrame) {
   		// TODO Auto-generated constructor stub
    	         this.sceneNameList = sceneNameList;
    	         this.constraintName = constraintName;
    	         this.constraintValue = constraintValue;
    	         this.mainFrame = mainFrame;
    	         Evaluateinit();
                 this.setLayout(new GridLayout());
                 this.add(jScrollPane);
   	}
       public ScenceTabelPanel(List<String> titleList,int[][] adjoinMatrix) {
      		// TODO Auto-generated constructor stub
                    this.titleList = titleList;
                    this.adjoinMatrix = adjoinMatrix;
                    Reachableinit();
                    this.setLayout(new GridLayout());
                    this.add(jScrollPane);
      	}
       private void init()
       {
    	   
    	   scenceTabelModel = new ScenceTableMode(titleList)
    	   {
    		   @Override
    		public boolean isCellEditable(int rowIndex, int columnIndex) {
    			// TODO Auto-generated method stub
    			if((columnIndex - rowIndex) == 1)
    				return false;
    			else {
					return true;
				}
    		}
    	   };
    	   table = new JTable(scenceTabelModel);
    	   table.addMouseListener(new MouseAdapter() {
    		   @Override
    		public void mousePressed(MouseEvent e) {
    			// TODO Auto-generated method stub
    			int cloumn = table.getColumnCount();
   				int row = table.getRowCount();
   				for(int i = 0;i < row;i++)
   				{
   					for(int j = i +1;j < cloumn;j++)
   					{
   						if(isDouble((String) table.getValueAt(i, j)))
  						{
  							if(j - i != 1)
  							{
  								double data =  Double.valueOf((String) table.getValueAt(i, j));
       							String newData = FormatDouble(data);
       							table.setValueAt(newData, j - 1, i + 1);
  							}	
  						}
  						else {
  							table.setValueAt("", i, j);
						}
   					}
   				}
   				
   				//对输入的数据进行格式化
   				if(isDouble((String) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn())))
   				{
   					double data =  Double.valueOf((String) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));
   	   				String newData = df.format(data);
   					table.setValueAt(newData,table.getSelectedRow(), table.getSelectedColumn());
   	   				
   				}
   				table.repaint();
    		}
		});
    	   table.addKeyListener(new KeyAdapter() {
    		   @Override
    		public void keyReleased(KeyEvent e) {
    			// TODO Auto-generated method stub
    			   int cloumn = table.getColumnCount();
      				int row = table.getRowCount();
      				for(int i = 0;i < row;i++)
      				{
      					for(int j = i +1;j < cloumn;j++)
      					{
      						if(isDouble((String) table.getValueAt(i, j)))
      						{
      							if(j - i != 1)
      							{
      								double data =  Double.valueOf((String) table.getValueAt(i, j));
           							String newData = FormatDouble(data);
           							table.setValueAt(newData, j - 1, i + 1);
      							}	
      						}
      						else {
      							table.setValueAt("", i, j);
							}
      					}
      				}
      				if(isDouble((String) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn())))
      				{
      					double data =  Double.valueOf((String) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));
        				String newData = df.format(data);
        				table.setValueAt(newData,table.getSelectedRow(), table.getSelectedColumn());
      				}
      				
      				table.repaint();
    		}
		});
    	   
    	   table.setFont(new Font("宋体",Font.PLAIN,15));
    	   int rowCount = table.getRowCount();
    	   for(int i = 0;i < rowCount;i++)
    	   {
    		   table.setValueAt("1.000", i, i+1);
    	   }  
    	   MyTableCell render = new MyTableCell();
    	   render.setHorizontalAlignment(SwingConstants.LEFT);  //文字居中
    	   render.setFont(new Font("宋体",Font.PLAIN,15));
    	   table.getColumnModel().getColumn(0).setCellRenderer(render);
           table.setRowHeight(55);
//           table.setShowGrid(false);
//           table.setShowHorizontalLines(true);
//           table.setShowVerticalLines(false);
           table.setSelectionBackground(new Color(233,233,233));
           HeaderTableCell HeaderRender = new HeaderTableCell();
           HeaderRender.setHorizontalAlignment(SwingConstants.LEFT);  //文字居中
           table.getTableHeader().setDefaultRenderer(HeaderRender);
           table.setGridColor(Color.gray);
    	   jScrollPane = new JScrollPane();
    	   jScrollPane.setViewportView(table);
    	   table.setPreferredScrollableViewportSize(new Dimension(table.getWidth(),table.getRowHeight()* table.getRowCount()));
       }
       private void Verificationinit()
       {
    	   scenceTabelModel = new ScenceTableMode(titleList,dataList,flag);
    	   table = new JTable();
    	   table.setFont(new Font("宋体",Font.PLAIN,18));
    	   table.setModel(scenceTabelModel);  
    	   table.addMouseListener(new MouseAdapter(){
    		   @Override
    		public void mouseClicked(MouseEvent e) {
    			// TODO Auto-generated method stub
    			if(e.getClickCount() == 2)
    			{
    				int cloumn = table.getColumnCount();
       				int row = table.getSelectedRow();
       				for(int i = 0;i < cloumn;i++)
       				{
       					if(i != cloumn-1){
       						mainFrame.getOutputinformation().geTextArea().append(table.getValueAt(row, i)+"概率:");
       					}
       					else {
       						mainFrame.getOutputinformation().geTextArea().append(table.getValueAt(row, i)+"\n");
						}
       				}
    			}
    			mainFrame.renewPanel();
    		}
    	   });
    	   
    	   DefaultTableCellRenderer render = new DefaultTableCellRenderer();
           render.setHorizontalAlignment(SwingConstants.CENTER);  //文字居中
           table.setDefaultRenderer(Object.class, render);
           table.getTableHeader().setFont(new Font("宋体",Font.PLAIN,15));
           table.setFont(new Font("宋体",Font.PLAIN,15));
           table.setRowHeight(25);
           
           table.getTableHeader().setResizingAllowed(true);
           table.getTableHeader().setPreferredSize(new Dimension(60, 25));
           
           table.setGridColor(Color.gray);
    	   jScrollPane = new JScrollPane();
    	   jScrollPane.setViewportView(table);
    	   table.setPreferredScrollableViewportSize(new Dimension(table.getWidth(),table.getRowHeight()* table.getRowCount()));
       }
       private void Reachableinit()
       {
    	   scenceTabelModel = new ScenceTableMode(titleList,adjoinMatrix);
    	   table = new JTable();
    	   table.setFont(new Font("宋体",Font.PLAIN,15));
    	   table.setModel(scenceTabelModel);  
    	   DefaultTableCellRenderer render = new DefaultTableCellRenderer()
           {
        	   public void setValue(Object value) { //重写setValue方法，从而可以动态设置列单元字体颜色      
                    if(value.toString().contains("true"))
                    {
                    	setBackground(Color.red);
//                    	setForeground(Color.red);
                    	
                    	setText((value == null) ? "" : value.toString().replace("true", ""));  
                    }
                    else{
//                    	setForeground(Color.black);  
                    	setBackground(Color.white);
                        setText((value == null) ? "" : value.toString());  
                    }
               }   
        		   };
           render.setHorizontalAlignment(SwingConstants.CENTER);  //文字居中
           table.setDefaultRenderer(Object.class, render);
           
           MyTableCell colorRender = new MyTableCell();
           colorRender.setFont(new Font("宋体",Font.PLAIN,15));
           colorRender.setHorizontalAlignment(SwingConstants.CENTER);  //文字居中
           table.getColumnModel().getColumn(0).setCellRenderer(colorRender);
           
           table.getTableHeader().setFont(new Font("宋体",Font.PLAIN,15));
           table.setRowHeight(25);
           DefaultTableCellRenderer HeaderRender = new DefaultTableCellRenderer();
           render.setHorizontalAlignment(SwingConstants.CENTER);  //文字居中
//           table.getTableHeader().setDefaultRenderer(HeaderRender);
           table.getTableHeader().setResizingAllowed(true);
           table.getTableHeader().setPreferredSize(new Dimension(60, 25));
           
           table.setGridColor(Color.gray);
    	   jScrollPane = new JScrollPane();
    	   jScrollPane.setViewportView(table);
    	   table.setPreferredScrollableViewportSize(new Dimension(table.getWidth(),table.getRowHeight()* table.getRowCount()));
    	   table.addMouseListener(new MouseAdapter() {
    		   
		});
       }
       private void Evaluateinit()
       {
    	   scenceTabelModel = new ScenceTableMode(sceneNameList,constraintName,constraintValue);
    	   table = new JTable();
    	   table.setFont(new Font("宋体",Font.PLAIN,18));
    	   table.setModel(scenceTabelModel);  
    	   table.addMouseListener(new MouseAdapter(){
    		   @Override
    		public void mouseClicked(MouseEvent e) {
    			// TODO Auto-generated method stub
    			if(e.getClickCount() == 2)
    			{
    				int cloumn = table.getColumnCount();
       				int row = table.getSelectedRow();
//       				for(int i = 0;i < cloumn;i++)
//       				{
//       					if(i != cloumn-1){
//       						mainFrame.getOutputinformation().geTextArea().append(table.getValueAt(row, i)+"");
//       					}
//       					else {
//       						mainFrame.getOutputinformation().geTextArea().append(table.getValueAt(row, i)+"\n");
//						}
//       				}
       				mainFrame.getOutputinformation().geTextArea().append("场景名称:"+table.getValueAt(row, 0));
       				mainFrame.getOutputinformation().geTextArea().append("后置条件名称:"+table.getValueAt(row, 1));
       				mainFrame.getOutputinformation().geTextArea().append("后置条件:"+table.getValueAt(row, 2));
    			}
    			mainFrame.renewPanel();
    		}
    	   });
    	   
    	   DefaultTableCellRenderer render = new DefaultTableCellRenderer();
           render.setHorizontalAlignment(SwingConstants.CENTER);  //文字居中
           table.setDefaultRenderer(Object.class, render);
           table.getTableHeader().setFont(new Font("宋体",Font.PLAIN,18));
           table.setRowHeight(25);
           DefaultTableCellRenderer HeaderRender = new DefaultTableCellRenderer();
           render.setHorizontalAlignment(SwingConstants.CENTER);  //文字居中
//           table.getTableHeader().setDefaultRenderer(HeaderRender);
           table.setGridColor(Color.gray);
    	   jScrollPane = new JScrollPane();
    	   jScrollPane.setViewportView(table);
    	   table.setPreferredScrollableViewportSize(new Dimension(table.getWidth(),table.getRowHeight()* table.getRowCount()));
       } 
       public JTable getTable()
       {
    	   return this.table;
       }
       public ScenceTableMode getScenceTabelPanel() {
		return scenceTabelModel;
	}
	public List<Double> getDataList() {
		return dataList;
	}
	public void setDataList(List<Double> dataList) {
		this.dataList = dataList;
	}

		public int getSelectcolumn() {
		return selectcolumn;
	}
	public void setSelectcolumn(int selectcolumn) {
		this.selectcolumn = selectcolumn;
	}
	public int getSelectrow() {
		return selectrow;
	}
	public void setSelectrow(int selectrow) {
		this.selectrow = selectrow;
	}
		private void tableChanged(TableModelEvent e) {
			// TODO Auto-generated method stub
			int row = e.getFirstRow();
			int column = e.getColumn();
			double data = Double.valueOf((String) table.getValueAt(row, column));
			String newData = String.valueOf(1.0/data);
			table.setValueAt(newData, column, row);
			table.repaint();
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
		private String FormatDouble(Double data)
		{
			Double Reciprocal = 1.00 / data;
			String formatData = df.format(Reciprocal);
			return formatData;
		}
        @Override
        public ScenceTabelPanel clone() throws CloneNotSupportedException {
        	// TODO Auto-generated method stub
        	ScenceTabelPanel cloned = (ScenceTabelPanel) super.clone();  
            return cloned;
        }
}
