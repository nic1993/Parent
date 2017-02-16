package com.horstmann.violet.application.StepTwoExpand;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class ScenceTabelPanel extends JPanel{
	   private JTable table;
       private List<String> titleList;
       private List<Double> dataList;
       private ScenceTableMode scenceTabelPanel;
	   private JScrollPane jScrollPane;
       public ScenceTabelPanel(List<String> titleList) {
		// TODO Auto-generated constructor stub
              this.titleList = titleList;
              init();
              this.setLayout(new GridLayout());
              this.add(jScrollPane);
	}
       public ScenceTabelPanel(List<String> titleList,List<Double> dataList) {
   		// TODO Auto-generated constructor stub
                 this.titleList = titleList;
                 this.dataList = dataList;
                 Verificationinit();
                 this.setLayout(new GridLayout());
                 this.add(jScrollPane);
   	}
       private void init()
       {
    	   scenceTabelPanel = new ScenceTableMode(titleList);
    	   table = new JTable();
    	   table.setFont(new Font("宋体",Font.PLAIN,18));
    	   table.setModel(scenceTabelPanel);  
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
       private void Verificationinit()
       {
    	   scenceTabelPanel = new ScenceTableMode(titleList,dataList);
    	   table = new JTable();
    	   table.setFont(new Font("宋体",Font.PLAIN,18));
    	   table.setModel(scenceTabelPanel);  
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
		return scenceTabelPanel;
	}
	public List<Double> getDataList() {
		return dataList;
	}
	public void setDataList(List<Double> dataList) {
		this.dataList = dataList;
	}
       
}
