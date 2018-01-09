package com.horstmann.violet.application.StepTwoModelExpand;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;


	public class HeaderTableCell extends JLabel implements TableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			// TODO Auto-generated method stub
			this.setPreferredSize(new Dimension(120,55));
			this.setFont(new Font("ËÎÌו",Font.PLAIN,15));
			this.setBackground(Color.white);
			this.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, Color.gray));
			if(((String)value).contains("-"))
			{
				String splitValue[] = ((String)value).split("\\-");
				setText("<html><body>"+splitValue[0]+"<br>&nbsp&nbsp -<br>"+splitValue[1]+"</body></html>");
			}
			else {
				this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
				setText("<html><body>"+((String)value)+"</body></html>");
			}
			return this;
		}

//		@Override
//		public void setHorizontalAlignment(int alignment) {
//			// TODO Auto-generated method stub
//			super.setHorizontalAlignment(LEFT);
//		}
		
		

	}

