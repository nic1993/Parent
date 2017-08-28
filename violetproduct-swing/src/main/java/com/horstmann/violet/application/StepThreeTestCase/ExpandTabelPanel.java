package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.BorderLayout;
import java.awt.Color;
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

import com.horstmann.violet.application.StepTwoModelExpand.HeaderTableCell;
import com.horstmann.violet.application.StepTwoModelExpand.MyTableCell;
import com.realpersist.gef.model.Column;

public class ExpandTabelPanel extends JPanel{
	   private JTable table; 
       private List<String> stateNames;
       private List<String> stateLabels;
       private ExpandTableMode scenceTabelModel;
	   private JScrollPane jScrollPane;
	   
	   private List<String> parameterName;
	   private List<String> parameterDomainType;
	   private List<String> parameterDomain;
       public ExpandTabelPanel(List<String> stateNames,List<String> stateLabels) {
		// TODO Auto-generated constructor stub
              this.stateNames = stateNames;
              this.stateLabels = stateLabels;
              init();
              this.setLayout(new GridLayout());
              this.add(jScrollPane);
	}
       public ExpandTabelPanel(List<String> parameterName,List<String> parameterDomainType,List<String> parameterDomain) {
   		// TODO Auto-generated constructor stub
                 this.parameterName = parameterName;
                 this.parameterDomainType = parameterDomainType;
                 this.parameterDomain = parameterDomain;
                 transferinit();
                 this.setLayout(new GridLayout());
                 this.add(jScrollPane);
   	}
       private void init()
       {
    	   scenceTabelModel = new ExpandTableMode(stateNames,stateLabels);

    	   table = new JTable(scenceTabelModel);
    	   table.setFont(new Font("宋体",Font.PLAIN,18));
    	   DefaultTableCellRenderer render = new DefaultTableCellRenderer();
    	   render.setHorizontalAlignment(SwingConstants.LEFT);  //文字居中
    	   render.setFont(new Font("宋体",Font.PLAIN,14));
    	   table.getColumnModel().getColumn(0).setCellRenderer(render);
           table.setRowHeight(30);

           HeaderTableCell HeaderRender = new HeaderTableCell();
           HeaderRender.setHorizontalAlignment(SwingConstants.LEFT);  //文字居中
           table.getTableHeader().setDefaultRenderer(HeaderRender);
           table.setGridColor(Color.gray);
    	   jScrollPane = new JScrollPane();
    	   jScrollPane.setViewportView(table);
    	   table.setPreferredScrollableViewportSize(new Dimension(table.getWidth(),table.getRowHeight()* table.getRowCount()));
       }
       private void transferinit()
       {
    	   scenceTabelModel = new ExpandTableMode(parameterName,parameterDomain,parameterDomainType);
    	   table = new JTable(scenceTabelModel);
    	   table.setFont(new Font("宋体",Font.PLAIN,18));
    	   DefaultTableCellRenderer render = new DefaultTableCellRenderer();
    	   render.setHorizontalAlignment(SwingConstants.LEFT);  //文字居中
    	   render.setFont(new Font("宋体",Font.PLAIN,14));
    	   table.getColumnModel().getColumn(0).setCellRenderer(render);
           table.setRowHeight(30);

           HeaderTableCell HeaderRender = new HeaderTableCell();
           HeaderRender.setHorizontalAlignment(SwingConstants.LEFT);  //文字居中
           table.getTableHeader().setDefaultRenderer(HeaderRender);
           table.setGridColor(Color.gray);
    	   jScrollPane = new JScrollPane();
    	   jScrollPane.setViewportView(table);
    	   table.setPreferredScrollableViewportSize(new Dimension(table.getWidth(),table.getRowHeight()* table.getRowCount()));
       }
       public JTable getTable()
       {
    	   return this.table;
       }
       public ExpandTableMode getScenceTabelPanel() {
		return scenceTabelModel;
	}
}
