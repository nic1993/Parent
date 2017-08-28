package com.horstmann.violet.application.StepTwoModelExpand;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MyTableCell extends JLabel implements TableCellRenderer {
	int row,column;
    Color c;
	public MyTableCell()
	{
		
	}
	public MyTableCell(int row,int column,Color c)
	{
		this.row = row;
		this.column = column;
		this.c = c;
	}
	public void fillColor(JTable t,JLabel l,boolean isSelected ){
        //setting the background and foreground when JLabel is selected
        if(isSelected){
            l.setBackground(new Color(233,233,233));
            l.setForeground(t.getForeground());
        }
        else{
            l.setBackground(new Color(233,233,233));
            l.setForeground(t.getForeground());
        }
    }
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
//        setBackground(Color.red);
		if(((String)value).contains("-"))
		{
			String splitValue[] = ((String)value).split("\\-");
			setText("<html><body><left>"+splitValue[0]+"<br>"+"&nbsp&nbsp -"+"<br>"+splitValue[1]+"</left></body></html>");
		}
		else {
			setText("<html><body>"+((String)value)+"</body></html>");
		}
		setOpaque(true);
		
		fillColor(table, this, isSelected);
		
		return this;
	}


}
