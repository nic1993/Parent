package com.horstmann.violet.application.StepFourTestCase;

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

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import org.junit.Test.None;

import com.horstmann.violet.application.StepTwoModelExpand.HeaderTableCell;
import com.horstmann.violet.application.StepTwoModelExpand.MyTableCell;
import com.horstmann.violet.application.gui.MainFrame;
import com.realpersist.gef.model.Column;

public class TestCaseTabelPanel extends JPanel{
	   private JTable table; 
	   private List<Integer> processID;
	    private List<String> processName;
	    private List<String> processParam;
	    private List<String> processStatus;
	    private List<Boolean> processExec; 
	    
	    private String TestCaseID;
	    private String State;
	    private String ExeState;
	    
	    private int success;
	    private int fail;
	    private int total;
	    
	    private int failType;
	    private int failTotal;
       private TestCaseTableMode scenceTabelModel;
       private DefaultTableModel attributetablemodel;
	   private JScrollPane jScrollPane;
	   
	   private MainFrame mainFrame;
       public TestCaseTabelPanel(List<Integer> processID,List<String> processName,
       		List<String> processParam,List<String> processStatus,List<Boolean> processExec) {
		// TODO Auto-generated constructor stub
    	   this.processID = processID;
           this.processName = processName;
           this.processParam = processParam;
           this.processStatus = processStatus;
           this.processExec = processExec;
              init();
              this.setLayout(new GridLayout());
              this.add(jScrollPane);
	}
       public TestCaseTabelPanel(String TestCaseID,String State,String ExeState,int flag)
       {
    	   this.TestCaseID = TestCaseID;     
    	   this.State = State;
    	   this.ExeState = ExeState;
    	   titleinit(flag);
    	   this.setLayout(new GridLayout());
           this.add(jScrollPane);
       }
       
       public TestCaseTabelPanel(String TestCaseID,String State,String ExeState,SelectBox selectBox,int flag)
       {
    	   this.TestCaseID = TestCaseID;     
    	   this.State = State;
    	   this.ExeState = ExeState;
    	   titleinit(selectBox,flag);
    	   this.setLayout(new GridLayout());
           this.add(jScrollPane);
       }
       
       public TestCaseTabelPanel(int failType,int failTotal)
       {
    	   this.failType = failType;     
    	   this.failTotal = failTotal;
    	   failcountinit();
    	   this.setLayout(new GridLayout());
           this.add(jScrollPane);
       }
       public TestCaseTabelPanel(int success,int fail,int total,MainFrame mainFrame)
       {
    	   this.mainFrame = mainFrame;
    	   this.success = success;     
    	   this.fail = fail;
    	   this.total = total;
    	   countinit();
    	   this.setLayout(new GridLayout());
           this.add(jScrollPane);
       }
       
       private void init()
       {
    	   scenceTabelModel = new TestCaseTableMode(processID,processName,processParam
    			   ,processStatus,processExec);
    	   table = new JTable(scenceTabelModel);
//    	   table.setFont(new Font("ËÎÌå",Font.PLAIN,16));
    	   DefaultTableCellRenderer render = new DefaultTableCellRenderer();
    	   render.setHorizontalAlignment(SwingConstants.LEFT);  //ÎÄ×Ö¾ÓÖÐ
    	   render.setFont(new Font("ËÎÌå",Font.PLAIN,14));
    	   
    	   table.getColumnModel().getColumn(0).setCellRenderer(render);
           table.setRowHeight(30);
   		   DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
   		   
   		   DefaultTableCellRenderer columnRender = new DefaultTableCellRenderer()
   		   {
    			  public Component getTableCellRendererComponent(JTable table,  
    		            Object value, boolean isSelected, boolean hasFocus,  
    		            int row, int column) {  
    		     if(value.toString().contains("false")) 
    		     setForeground(Color.red);  
    		     else  
    		     setForeground(Color.black);  
    		     return super.getTableCellRendererComponent(table, value,  
    		               isSelected, hasFocus, row, column);  
    		     
    		    }  
    		};
   		   renderer.setBackground(new Color(188, 188, 188));
//   		renderer.setForeground(new Color(255, 255, 255));
   		   renderer.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
   		   renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
   		   table.getTableHeader().setDefaultRenderer(renderer);
           table.setGridColor(Color.gray);
           
           table.setShowGrid(false);
           table.setShowHorizontalLines(true);
           table.setShowVerticalLines(false);
           table.setFillsViewportHeight(true);
           table.getColumn("¼¤ÀøID").setPreferredWidth(70);
           table.getColumn("¼¤ÀøID").setMinWidth(70);
           table.getColumn("¼¤ÀøID").setMaxWidth(70);
           table.getColumn("¼¤ÀøÃû³Æ").setPreferredWidth(100);
           table.getColumn("¼¤ÀøÃû³Æ").setMinWidth(100);
           table.getColumn("¼¤Àø²ÎÊý").setPreferredWidth(480);
           table.getColumn("¼¤Àø²ÎÊý").setMinWidth(480);
           table.getColumn("¼¤Àø×´Ì¬").setPreferredWidth(60);
           table.getColumn("¼¤Àø×´Ì¬").setMinWidth(60);
           table.getColumn("¼¤ÀøÖ´ÐÐÇé¿ö").setPreferredWidth(60);
           table.getColumn("¼¤ÀøÖ´ÐÐÇé¿ö").setMinWidth(60);
           table.getColumn("¼¤ÀøÖ´ÐÐÇé¿ö").setCellRenderer(columnRender);
    	   jScrollPane = new JScrollPane();
    	   jScrollPane.setViewportView(table);
    	   table.setPreferredScrollableViewportSize(new Dimension(table.getWidth(),table.getRowHeight()* table.getRowCount()));
       }
       
       private void titleinit(int flag)
       {
    	   scenceTabelModel = new TestCaseTableMode(TestCaseID,State,ExeState,flag);
    	   table = new JTable(scenceTabelModel);
    	  
    	   table.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(30);
    	   table.getTableHeader().getColumnModel().getColumn(0).setMinWidth(30);
   		   table.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(30);

   		   table.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(110);
   		   table.getTableHeader().getColumnModel().getColumn(1).setMinWidth(101);
   		   table.getTableHeader().getColumnModel().getColumn(2).setPreferredWidth(90);
   		   table.getTableHeader().getColumnModel().getColumn(2).setMinWidth(90);

  		   DefaultTableCellRenderer renderer = new DefaultTableCellRenderer()
  		  {
  			 public Component getTableCellRendererComponent(JTable table,  
   		            Object value, boolean isSelected, boolean hasFocus,  
   		            int row, int column) {  
   		     if(value.toString().contains("ÓÐÎó") && column == 2)  
   		     setForeground(Color.red);  
   		     else  
   		     setForeground(Color.black);  
   		     return super.getTableCellRendererComponent(table, value,  
   		               isSelected, hasFocus, row, column);  
   		     
   		     }  
  			};
  	       renderer.setBackground(new Color(233, 233, 233));
//		renderer.setForeground(new Color(255, 255, 255));
		   renderer.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		   renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		   table.getTableHeader().setDefaultRenderer(renderer);
		   table.getTableHeader().setPreferredSize(new Dimension(100, 30));
  		   table.setShowGrid(false);
  		   table.setShowHorizontalLines(false);
  		   table.setShowVerticalLines(false);
  		   table.getTableHeader().setBackground(Color.red);
  		   table.setGridColor(new Color(233,233,233));
  		   table.doLayout();
  		   table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
  		   jScrollPane = new JScrollPane();
  		   jScrollPane.setBorder(BorderFactory.createEmptyBorder());
  	       jScrollPane.setViewportView(table);
  	      
  	       table.setPreferredScrollableViewportSize(new Dimension(table.getWidth(),table.getRowHeight()* table.getRowCount()));
//  		   jScrollPane = new JScrollPane(table);
//  	       jScrollPane.setBorder(null);
//  	       table.setPreferredScrollableViewportSize(new Dimension(table.getWidth(),table.getRowHeight()* table.getRowCount()));
       }
       
       private void titleinit(SelectBox selectBox,int flag)
       {
    	   scenceTabelModel = new TestCaseTableMode(TestCaseID,State,ExeState,flag);
    	   table = new JTable(scenceTabelModel);
    	  
    	   table.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(20);
    	   table.getTableHeader().getColumnModel().getColumn(0).setMinWidth(20);
   		   table.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(20);
//   		   table.getTableHeader().getColumnModel().getColumn(0).setHeaderRenderer(selectBox);

   		   table.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(100);
   		   table.getTableHeader().getColumnModel().getColumn(1).setMinWidth(100);
   		   table.getTableHeader().getColumnModel().getColumn(2).setPreferredWidth(100);
   		   table.getTableHeader().getColumnModel().getColumn(2).setMinWidth(100);

  		   DefaultTableCellRenderer renderer = new DefaultTableCellRenderer()
  		  {
  			 public Component getTableCellRendererComponent(JTable table,  
   		            Object value, boolean isSelected, boolean hasFocus,  
   		            int row, int column) {  
   		     if(value.toString().contains("ÓÐÎó") && column == 2)  
   		     setForeground(Color.red);  
   		     else  
   		     setForeground(Color.black);  
   		     return super.getTableCellRendererComponent(table, value,  
   		               isSelected, hasFocus, row, column);  
   		     
   		     }  
  			};
  	       renderer.setBackground(new Color(233, 233, 233));
//		renderer.setForeground(new Color(255, 255, 255));
		   renderer.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		   renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		   table.getTableHeader().setDefaultRenderer(renderer);
		   table.getTableHeader().setPreferredSize(new Dimension(100, 30));
  		   table.setShowGrid(false);
  		   table.setShowHorizontalLines(false);
  		   table.setShowVerticalLines(false);
  		   table.getTableHeader().setBackground(Color.red);
  		   table.setGridColor(new Color(233,233,233));
  		   table.doLayout();
  		   table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
  		   jScrollPane = new JScrollPane();
  		   jScrollPane.setBorder(BorderFactory.createEmptyBorder());
  	       jScrollPane.setViewportView(table);
  	      
  	       table.setPreferredScrollableViewportSize(new Dimension(table.getWidth(),table.getRowHeight()* table.getRowCount()));
//  		   jScrollPane = new JScrollPane(table);
//  	       jScrollPane.setBorder(null);
//  	       table.setPreferredScrollableViewportSize(new Dimension(table.getWidth(),table.getRowHeight()* table.getRowCount()));
       }
       
       private void countinit()
       {
    	   scenceTabelModel = new TestCaseTableMode(success,fail,total);
    	   table = new JTable(scenceTabelModel);
    	   
    	   
    	   table.setRowHeight(30);
    	   table.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(50);
    	   table.getTableHeader().getColumnModel().getColumn(0).setMinWidth(50);
   		   table.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(50);

   		   table.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(100);
   		   table.getTableHeader().getColumnModel().getColumn(1).setMinWidth(100);
   		   table.getTableHeader().getColumnModel().getColumn(2).setPreferredWidth(100);
   		   table.getTableHeader().getColumnModel().getColumn(2).setMinWidth(100);
   		   table.getTableHeader().getColumnModel().getColumn(3).setPreferredWidth(100);
		   table.getTableHeader().getColumnModel().getColumn(3).setMinWidth(100);

  		   DefaultTableCellRenderer render = new DefaultTableCellRenderer();
  		   
  	      
  	       render.setBackground(new Color(233, 233, 233));
//		   renderer.setForeground(new Color(255, 255, 255));
		   render.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		   render.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
//		   table.getTableHeader().setDefaultRenderer(renderer);
//		   table.getTableHeader().setPreferredSize(new Dimension(100, 30));
		   table.getColumnModel().getColumn(0).setCellRenderer(render);
		   table.getColumnModel().getColumn(1).setCellRenderer(render);
		   table.getColumnModel().getColumn(2).setCellRenderer(render);
		   table.getColumnModel().getColumn(3).setCellRenderer(render);
		   table.getTableHeader().setVisible(false);
  		   table.setShowGrid(false);
  		   table.setShowHorizontalLines(false);
  		   table.setShowVerticalLines(false);
  		   
  		   table.setGridColor(new Color(233,233,233));
  		   table.doLayout();
  		   table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
  		   
  		   jScrollPane = new JScrollPane();
  		   jScrollPane.setBorder(BorderFactory.createEmptyBorder());
  	       jScrollPane.setViewportView(table);
  	      
  	       table.setPreferredScrollableViewportSize(new Dimension(table.getWidth(),table.getRowHeight()* table.getRowCount()));
//  		   jScrollPane = new JScrollPane(table);
//  	       jScrollPane.setBorder(null);
//  	       table.setPreferredScrollableViewportSize(new Dimension(table.getWidth(),table.getRowHeight()* table.getRowCount()));
       }
       private void failcountinit()
       {
    	   scenceTabelModel = new TestCaseTableMode(failType,failTotal);
    	   table = new JTable(scenceTabelModel);
    	   table.setRowHeight(30);
    	   table.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(50);
    	   table.getTableHeader().getColumnModel().getColumn(0).setMinWidth(50);
   		   table.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(50);

   		   table.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(100);
   		   table.getTableHeader().getColumnModel().getColumn(1).setMinWidth(100);
   		   table.getTableHeader().getColumnModel().getColumn(2).setPreferredWidth(100);
   		   table.getTableHeader().getColumnModel().getColumn(2).setMinWidth(100);
   		   table.getTableHeader().getColumnModel().getColumn(3).setPreferredWidth(100);
		   table.getTableHeader().getColumnModel().getColumn(3).setMinWidth(100);

  		   DefaultTableCellRenderer render = new DefaultTableCellRenderer();
  	       render.setBackground(new Color(233, 233, 233));
//		   renderer.setForeground(new Color(255, 255, 255));
		   render.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		   render.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
//		   table.getTableHeader().setDefaultRenderer(renderer);
//		   table.getTableHeader().setPreferredSize(new Dimension(100, 30));
		   table.getColumnModel().getColumn(0).setCellRenderer(render);
		   table.getColumnModel().getColumn(1).setCellRenderer(render);
		   table.getColumnModel().getColumn(2).setCellRenderer(render);
		   table.getColumnModel().getColumn(3).setCellRenderer(render);
		   table.getTableHeader().setVisible(false);
  		   table.setShowGrid(false);
  		   table.setShowHorizontalLines(false);
  		   table.setShowVerticalLines(false);
  		   
  		   table.setGridColor(new Color(233,233,233));
  		   table.doLayout();
  		   table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
  		   
  		   jScrollPane = new JScrollPane();
  		   jScrollPane.setBorder(BorderFactory.createEmptyBorder());
  	       jScrollPane.setViewportView(table);
  	      
  	       table.setPreferredScrollableViewportSize(new Dimension(table.getWidth(),table.getRowHeight()* table.getRowCount()));
//  		   jScrollPane = new JScrollPane(table);
//  	       jScrollPane.setBorder(null);
//  	       table.setPreferredScrollableViewportSize(new Dimension(table.getWidth(),table.getRowHeight()* table.getRowCount()));
       }
       public JTable getTable()
       {
    	   return this.table;
       }
       public TestCaseTableMode getScenceTabelPanel() {
		return scenceTabelModel;
	}
}
