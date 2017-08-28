package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ExpandReportTableHeaderPanel extends JPanel{
	
	private JPanel attributepanel;

	private JTable attributetable;
	private DefaultTableModel attributetablemodel;
	
	private JPanel emptypanel;

	public ExpandReportTableHeaderPanel() {
		
		init();

		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));

		this.setBackground(new Color(233,233,233));

	}

	private void init() {
		// TODO Auto-generated method stub

		attributepanel = new JPanel();

		initAttributePanel();
		
		emptypanel=new JPanel();
		emptypanel.setPreferredSize(new Dimension(21, 21));
		emptypanel.setBackground(new Color(71, 80, 93));
		
//		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//		this.add(attributepanel);
		
		this.setLayout(new BorderLayout());
		this.add(attributepanel, BorderLayout.CENTER);
		this.add(emptypanel, BorderLayout.EAST);

	}


	private void initAttributePanel() {
		// TODO Auto-generated method stub

		String[] columnNames = { "Ç¨ÒÆÃû³Æ","Ç¨ÒÆ¸ÅÂÊ"};
		String[][] tabelValues = {};

		attributetablemodel = new DefaultTableModel(tabelValues, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		attributetable = new JTable(attributetablemodel);
		
		attributetable.setName("TestCaseReportTableHeaderPanel");

		attributetable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		attributetable.setSelectionBackground(new Color(233,233,233));
		attributetable.setGridColor(new Color(224, 226, 220));
		attributetable.setShowGrid(true);
		attributetable.setShowHorizontalLines(true);
		attributetable.setShowVerticalLines(true);
		attributetable.setFillsViewportHeight(true);
		attributetable.setRowHeight(27);
		attributetable.doLayout();
		attributetable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

//		attributetable.getColumnModel().getColumn(0).setCellRenderer(new MyAllLabelRenderer());
//		attributetable.getColumnModel().getColumn(1).setCellRenderer(new MyAllLabelRenderer());
//		attributetable.getColumnModel().getColumn(2).setCellRenderer(new MyAllLabelRenderer());
//		attributetable.getColumnModel().getColumn(3).setCellRenderer(new MyAllLabelRenderer());
//		attributetable.getColumnModel().getColumn(4).setCellRenderer(new MyAllLabelRenderer());

		attributetable.getColumn("Ç¨ÒÆÃû³Æ").setPreferredWidth(50);
		attributetable.getColumn("Ç¨ÒÆÃû³Æ").setMinWidth(50);
		attributetable.getColumn("Ç¨ÒÆÃû³Æ").setMaxWidth(50);

		attributetable.getColumn("Ç¨ÒÆ¸ÅÂÊ").setPreferredWidth(100);
		attributetable.getColumn("Ç¨ÒÆ¸ÅÂÊ").setMinWidth(100);


		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setBackground(new Color(233, 233, 233));
//		renderer.setForeground(new Color(255, 255, 255));
		renderer.setFont(new Font("ËÎÌå", Font.PLAIN, 14));
		renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		attributetable.getTableHeader().setDefaultRenderer(renderer);

		attributetable.getTableHeader().setPreferredSize(new Dimension(100, 27));


		attributetable.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(224, 225, 220)));

		attributepanel.setLayout(new BorderLayout());
		attributepanel.add(attributetable.getTableHeader(), BorderLayout.NORTH);
		attributepanel.add(attributetable, BorderLayout.CENTER);

//		attributepanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		attributepanel.setOpaque(false);


	}

	
}
