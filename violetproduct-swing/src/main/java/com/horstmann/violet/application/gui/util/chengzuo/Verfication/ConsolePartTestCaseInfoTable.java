package com.horstmann.violet.application.gui.util.chengzuo.Verfication;

import java.awt.Color;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class ConsolePartTestCaseInfoTable extends JTable{
  private static final long serialVersionUID = 1L;
  private DefaultTableModel defaultTableModel;
  private final Object[] columnNames={"ÐòºÅ","ÄÚÈÝ","×´Ì¬","½á¹û"};
  
  public ConsolePartTestCaseInfoTable(List<TestCase> list){
	  defaultTableModel=new DefaultTableModel(columnNames,0){
		  public boolean isCellEditable(int row,int column){
			  return false;
		  }
	  };
	    this.setModel(defaultTableModel);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setRowSelectionAllowed(true);
		this.setGridColor(Color.BLACK);
		this.setShowGrid(true);
		this.setShowHorizontalLines(true);
		this.setShowVerticalLines(true);
		this.setFillsViewportHeight(true);
		this.setDefaultEditor(Object.class, cellEditor);
		this.setColumnWidth(0, 50);
		this.doLayout();
		initRowsData(list);
  }
  
  public void initRowsData(List<TestCase> list){
	  this.removeRowsData();
	  Object[] rowData=new Object[columnNames.length];
	  int Index=0;
	  while(Index<list.size()){
		  if(list!=null&&!list.isEmpty()){
			  for(TestCase info:list){
				  rowData[0]=info.getTestCaseID();
				  rowData[1]=info.getContent();
				  rowData[2]=info.getState();
				  rowData[3]=info.getResult();
				  defaultTableModel.addRow(rowData);
				  defaultTableModel.fireTableDataChanged();
			  }
			  
		  }
		  Index++;
	  }
  }
  private void removeRowsData() {
		int count = defaultTableModel.getRowCount();
		for (count -= 1; count > -1; count--) {
			defaultTableModel.removeRow(count);
		}
	}
  private void setColumnWidth(int columnIndex, int width) {
		TableColumn column = this.getColumnModel().getColumn(columnIndex);
		column.setPreferredWidth(width);
		column.setMaxWidth(width);
		column.setMinWidth(width);
	}
}
