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

public class CaseTableHeaderPanel extends JPanel{
	
	private JPanel attributepanel;

	private JTable attributetable;
	private DefaultTableModel attributetablemodel;
	
	private JPanel emptypanel;

	public CaseTableHeaderPanel() {
		
		init();

//		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));

		this.setBackground(new Color(255, 255, 255));

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

	}


	private void initAttributePanel() {
		// TODO Auto-generated method stub

		String[] columnNames = { "²âÊÔÃû³Æ","²âÊÔÐÅÏ¢"};
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
		attributetable.setSelectionBackground(new Color(250, 248, 236));
		attributetable.setGridColor(new Color(224, 226, 220));
		attributetable.setShowGrid(false);
		attributetable.setShowHorizontalLines(true);
		attributetable.setShowVerticalLines(false);
		attributetable.setFillsViewportHeight(true);
		attributetable.setRowHeight(27);
		attributetable.doLayout();
		attributetable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		attributetable.getColumn("²âÊÔÃû³Æ").setPreferredWidth(60);
		attributetable.getColumn("²âÊÔÃû³Æ").setMinWidth(60);
		attributetable.getColumn("²âÊÔÃû³Æ").setMaxWidth(60);

		attributetable.getColumn("²âÊÔÐÅÏ¢").setPreferredWidth(100);
		attributetable.getColumn("²âÊÔÐÅÏ¢").setMinWidth(100);
		
		

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setBackground(new Color(188, 188, 188));
//		renderer.setForeground(new Color(255, 255, 255));
		renderer.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		attributetable.getTableHeader().setDefaultRenderer(renderer);

		attributetable.getTableHeader().setPreferredSize(new Dimension(100, 30));


		attributepanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(188,188,188)));

		attributepanel.setLayout(new BorderLayout());
		attributepanel.add(attributetable.getTableHeader(), BorderLayout.NORTH);
		attributepanel.add(attributetable, BorderLayout.CENTER);

//		attributepanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		attributepanel.setOpaque(false);


	}

	
}
