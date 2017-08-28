package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import com.horstmann.violet.application.gui.MainFrame;

public class StepThreeTabelPanel extends JPanel{
	   private JTable table;
       private List<String> paramterNameList;
       private List<String> paramterValueList;
       private String testSequence;
       private String excitation;
       private String testCase;
       private StepThreeTabelModel scenceTabelPanel;
	   private JScrollPane jScrollPane;
	   private List<String> constraintNameString;
	   private List<Double> pros;
	   private List<Integer> numbers;
	   private List<Double> actualPercentsDoubles;
	   private DecimalFormat  df = new DecimalFormat();
	   private String pattern = "#0.0000";
	   
	   private int flag;
	   private MainFrame mainFrame;
       public StepThreeTabelPanel(List<String> paramterNameList,List<String> paramterValueList) {
		// TODO Auto-generated constructor stub
              this.paramterNameList = paramterNameList;
              this.paramterValueList = paramterValueList;
              df.applyPattern(pattern);
              init();
              this.setLayout(new GridLayout());
              this.add(jScrollPane);
	}
       public StepThreeTabelPanel(String testSequence,int flag,MainFrame mainFrame) {
   		// TODO Auto-generated constructor stub
                 this.testSequence = testSequence;
                 this.mainFrame = mainFrame;
                 this.flag = flag;
                 if(flag == 1)
                 {
                	 abstractinit();
                 }
                 else{
                	 testDatainit();
                 }
                 this.setLayout(new GridLayout());
                 this.add(jScrollPane);
   	}
       public StepThreeTabelPanel(List<String> constraintNameString,List<Double> actualPercentsDoubles,List<Double> pros,List<Integer> numbers) {
      		// TODO Auto-generated constructor stub
                    this.constraintNameString = constraintNameString;
                    this.pros = pros;
                    this.numbers = numbers;
                    this.actualPercentsDoubles = actualPercentsDoubles;
                    numberinit();
                    this.setLayout(new GridLayout());
                    this.add(jScrollPane);
      	}
       private void init()
       {
    	   scenceTabelPanel = new StepThreeTabelModel(paramterNameList,paramterValueList);
    	   table = new JTable();
    	   table.setFont(new Font("宋体",Font.PLAIN,18));
    	   table.setModel(scenceTabelPanel);  
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
       						mainFrame.getOutputinformation().geTextArea().append(table.getValueAt(row, i)+":");
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
           render.setHorizontalAlignment(SwingConstants.LEFT);  //文字居中
           table.setDefaultRenderer(Object.class, render);
           table.getTableHeader().setFont(new Font("宋体",Font.PLAIN,18));
           table.setRowHeight(25);
           table.setGridColor(Color.gray);
    	   jScrollPane = new JScrollPane();
    	   jScrollPane.setViewportView(table);
    	   table.setPreferredScrollableViewportSize(new Dimension(table.getWidth(),table.getRowHeight()* table.getRowCount()));
       }
       private void abstractinit()
       {
    	   scenceTabelPanel = new StepThreeTabelModel(testSequence,flag);
    	   table = new JTable();
    	   table.setFont(new Font("宋体",Font.PLAIN,18));
    	   table.setModel(scenceTabelPanel);  
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
       						mainFrame.getOutputinformation().geTextArea().append(table.getValueAt(row, i)+":");
       					}
       					else {
       						mainFrame.getOutputinformation().geTextArea().append(table.getValueAt(row, i)+"\n");
						}
       				}
    			}
    			mainFrame.renewPanel();
    		}
    	   });
    	   
    	   table.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(60);
    	   table.getTableHeader().getColumnModel().getColumn(0).setMinWidth(60);

   		   table.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(100);
   		   table.getTableHeader().getColumnModel().getColumn(1).setMinWidth(100);
   		   
    	   DefaultTableCellRenderer render = new DefaultTableCellRenderer();
           render.setHorizontalAlignment(SwingConstants.LEFT);  //文字居中
           table.setDefaultRenderer(Object.class, render);
          
           table.setRowHeight(25);
           FitTableColumns(table);
           table.setGridColor(Color.gray);
           
           table.getTableHeader().setVisible(false);
    	   jScrollPane = new JScrollPane();
    	   jScrollPane.setViewportView(table);
    	   table.setPreferredScrollableViewportSize(new Dimension(table.getWidth(),table.getRowHeight()* table.getRowCount()));
       }
       private void testDatainit()
       {
    	   scenceTabelPanel = new StepThreeTabelModel(testSequence,flag);
    	   table = new JTable();
    	   table.setFont(new Font("宋体",Font.PLAIN,18));
    	   table.setModel(scenceTabelPanel);  
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
       						mainFrame.getOutputinformation().geTextArea().append(table.getValueAt(row, i)+":");
       					}
       					else {
       						mainFrame.getOutputinformation().geTextArea().append(table.getValueAt(row, i)+"\n");
						}
       				}
    			}
    			mainFrame.renewPanel();
    		}
    	   });
    	   
    	   table.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(60);
    	   table.getTableHeader().getColumnModel().getColumn(0).setMinWidth(60);

   		   table.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(100);
   		   table.getTableHeader().getColumnModel().getColumn(1).setMinWidth(100);
   		   
    	   DefaultTableCellRenderer render = new DefaultTableCellRenderer();
           render.setHorizontalAlignment(SwingConstants.LEFT);  //文字居中
           table.setDefaultRenderer(Object.class, render);
          
           table.setRowHeight(25);
           FitTableColumns(table);
           table.setGridColor(Color.gray);
           
           table.getTableHeader().setVisible(false);
    	   jScrollPane = new JScrollPane();
    	   jScrollPane.setViewportView(table);
    	   table.setPreferredScrollableViewportSize(new Dimension(table.getWidth(),table.getRowHeight()* table.getRowCount()));
       }
       private void numberinit()
       {
    	   scenceTabelPanel = new StepThreeTabelModel(constraintNameString,actualPercentsDoubles,pros,numbers);
    	   table = new JTable();
    	   table.setFont(new Font("宋体",Font.PLAIN,18));
    	   table.setModel(scenceTabelPanel);  
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
       						mainFrame.getOutputinformation().geTextArea().append(table.getValueAt(row, i)+":");
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
           render.setHorizontalAlignment(SwingConstants.LEFT);  //文字居中
           table.setDefaultRenderer(Object.class, render);
           table.getTableHeader().setFont(new Font("宋体",Font.PLAIN,18));
           table.setRowHeight(25);
//           DefaultTableCellRenderer HeaderRender = new DefaultTableCellRenderer();
//           render.setHorizontalAlignment(SwingConstants.LEFT);  //文字居中
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
       public StepThreeTabelModel getScenceTabelPanel() {
		return scenceTabelPanel;
	}  
       public void FitTableColumns(JTable myTable){
    	   JTableHeader header = myTable.getTableHeader();
    	      int rowCount = myTable.getRowCount();
    	      Enumeration columns = myTable.getColumnModel().getColumns();
    	      while(columns.hasMoreElements()){
    	          TableColumn column = (TableColumn)columns.nextElement();
    	          int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
    	          int width = (int)myTable.getTableHeader().getDefaultRenderer()
    	                  .getTableCellRendererComponent(myTable, column.getIdentifier()
    	                          , false, false, -1, col).getPreferredSize().getWidth();
    	          for(int row = 0; row<rowCount; row++){
    	              int preferedWidth = (int)myTable.getCellRenderer(row, col).getTableCellRendererComponent(myTable,
    	                myTable.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
    	              width = Math.max(width, preferedWidth);
    	          }
    	          header.setResizingColumn(column); // 此行很重要
    	          column.setWidth(width+myTable.getIntercellSpacing().width);
    	      }
       }
}
