package com.horstmann.violet.framework.propertyeditor.customeditor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

public class ConditiontTable extends JTable{
       private ConditionTableModel conditionTabelModel;
       public ConditiontTable(ConditionTableModel TableModel) {
		// TODO Auto-generated constructor stub
    	   conditionTabelModel = (ConditionTableModel) TableModel;
    	   this.setModel(conditionTabelModel); 
	   }
	   public void addcolumn()
       {
		   conditionTabelModel.add();
    	   TableColumn tableColumn = this.getColumnModel().getColumn(this.getColumnCount() - 1);
       }
	   public void deletecolumn()
       {
		   int selectRow = this.getSelectedRow();
		   conditionTabelModel.delete(selectRow);
       }

}
