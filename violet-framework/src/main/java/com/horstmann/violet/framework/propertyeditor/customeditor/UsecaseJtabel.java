package com.horstmann.violet.framework.propertyeditor.customeditor;

import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

public class UsecaseJtabel extends JTable{
       private UsecaseTableMode usecaseTableMode;
       private JComboBox jComboBox;
       private Vector comboBoxVector;
       public UsecaseJtabel(UsecaseTableMode TableModel) {
		// TODO Auto-generated constructor stub
    	   usecaseTableMode = (UsecaseTableMode) TableModel;
    	   this.setModel(usecaseTableMode);
    	   
           comboBoxVector = new Vector();
           comboBoxVector.add("post-conditon"); 
           comboBoxVector.add("pre-conditon"); 
           jComboBox = new JComboBox(comboBoxVector);
	   }
	   public void addcolumn()
       {
    	   usecaseTableMode.add();
    	   TableColumn tableColumn = this.getColumnModel().getColumn(this.getColumnCount() - 1);
    	   tableColumn.setCellEditor(new DefaultCellEditor(jComboBox));
       }
	   public void deletecolumn()
       {
		   int selectRow = this.getSelectedRow();
		   System.out.println("selectRow"+selectRow);
    	   usecaseTableMode.delete(selectRow);
       }
       

}
