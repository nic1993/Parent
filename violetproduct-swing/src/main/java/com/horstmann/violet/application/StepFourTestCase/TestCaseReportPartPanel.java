package com.horstmann.violet.application.StepFourTestCase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.horstmann.violet.application.gui.util.chenzuo.Bean.TestCase;
import com.horstmann.violet.application.gui.util.chenzuo.Bean.myProcess;

public class TestCaseReportPartPanel extends JPanel {

	private JPanel titlepanel;
	private JPanel linepanel;
	private JPanel attributepanel;

	private JPanel titlelabelpanel;
	private JCheckBox toolcheckbox;
	private JLabel iconlabel;
	private JLabel titlelabel;
	private JButton toolbutton;

	private JLabel linelabel;

	private JTable attributetable;
	private DefaultTableModel attributetablemodel;
	
	private TestCase testcase;

	public TestCaseReportPartPanel(TestCase testcase) {

		this.testcase=testcase;
		init();
		this.setBackground(new Color(255, 255, 255));

	}

	private void init() {
		// TODO Auto-generated method stub

		titlepanel = new JPanel();
		linepanel = new JPanel();
		attributepanel = new JPanel();

		titlelabelpanel = new JPanel();
		iconlabel = new JLabel();
		titlelabel = new JLabel();
		toolbutton = new JButton();

		linelabel = new JLabel();

		initTitlePanel();

		initLinePanel();

		initAttributePanel();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(titlepanel);
		this.add(linepanel);
		this.add(attributepanel);
	}

	private void initTitlePanel() {
		// TODO Auto-generated method stub

		ImageIcon icon3 = new ImageIcon("resources/icons/16x16/smallDown.png");

		String title = "";
		title+="²âÊÔÓÃÀýID:"+testcase.getTestCaseID()+"     ";
//		if(testcase.getState()!=null){
//			title+=testcase.getState()+"     ";
//		}
//		else{
			title+="²âÊÔºÄÊ±:     ";
//		}
		title+="Ö´ÐÐ½á¹û:";
//		if(testcase.getResult()!=null){
//			title+=testcase.getResult().substring(0, testcase.getResult().indexOf("ºÄÊ±"));
//		}
		
		titlelabel.setText(title);
		titlelabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 12));
//		titlelabel.setForeground(new Color(60,0,255));
		titlelabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

//		iconlabel.setIcon(icon1);
		iconlabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
	
		titlelabelpanel.setLayout(new FlowLayout(0, 0, FlowLayout.LEFT));
		titlelabelpanel.add(titlelabel);
		titlelabelpanel.add(iconlabel);
		titlelabelpanel.setOpaque(false);

		toolbutton.setIcon(icon3);
		toolbutton.setFocusable(false);
		toolbutton.setContentAreaFilled(false);
		toolbutton.setBorderPainted(false);
//		toolbutton.addMouseListener(new ButtonMouseListener());
//		toolbutton.setPreferredSize(new Dimension(21, 21));
		toolbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (attributepanel.isVisible()) {
					attributepanel.setVisible(false);
				} else {
					attributepanel.setVisible(true);
				}
			}
		});
		titlepanel.setLayout(new BorderLayout());
		titlepanel.add(titlelabelpanel, BorderLayout.WEST);
		titlepanel.add(toolbutton, BorderLayout.EAST);
		// titlepanel.setPreferredSize(new Dimension(100, 30));

		titlepanel.setOpaque(false);

	}

	private void initLinePanel() {
		// TODO Auto-generated method stub

		linelabel.setText(
				"- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
//		linelabel.setPreferredSize(new Dimension(100, 3));
		linelabel.setForeground(new Color(223, 204, 221));

		linepanel.setLayout(new GridLayout());
		linepanel.add(linelabel);
		linepanel.setOpaque(false);

	}

	private void initAttributePanel() {
		// TODO Auto-generated method stub

		String[] columnNames = { "¼¤ÀøID", "¼¤ÀøÃû³Æ", "¼¤Àø²ÎÊý", "¼¤Àø×´Ì¬", "¼¤ÀøÖ´ÐÐÇé¿ö" };
		String[][] tabelValues = {};

		attributetablemodel = new DefaultTableModel(tabelValues, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		attributetable = new JTable(attributetablemodel);
		
		attributetable.setName("TestCaseReportPartPanel");

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

//		attributetable.getColumnModel().getColumn(0).setCellRenderer(new MyAllLabelRenderer());
//		attributetable.getColumnModel().getColumn(1).setCellRenderer(new MyAllLabelRenderer());
//		attributetable.getColumnModel().getColumn(2).setCellRenderer(new MyAllLabelRenderer());
//		attributetable.getColumnModel().getColumn(3).setCellRenderer(new MyAllLabelRenderer());
//		attributetable.getColumnModel().getColumn(4).setCellRenderer(new MyAllLabelRenderer());

		attributetable.getColumn("¼¤ÀøID").setPreferredWidth(70);
		attributetable.getColumn("¼¤ÀøID").setMinWidth(70);
		attributetable.getColumn("¼¤ÀøID").setMaxWidth(70);
		attributetable.getColumn("¼¤ÀøÃû³Æ").setPreferredWidth(100);
		attributetable.getColumn("¼¤ÀøÃû³Æ").setMinWidth(100);
		attributetable.getColumn("¼¤Àø²ÎÊý").setPreferredWidth(480);
		attributetable.getColumn("¼¤Àø²ÎÊý").setMinWidth(480);
		attributetable.getColumn("¼¤Àø×´Ì¬").setPreferredWidth(60);
		attributetable.getColumn("¼¤Àø×´Ì¬").setMinWidth(60);
		attributetable.getColumn("¼¤ÀøÖ´ÐÐÇé¿ö").setPreferredWidth(60);
		attributetable.getColumn("¼¤ÀøÖ´ÐÐÇé¿ö").setMinWidth(60);

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setBackground(new Color(71, 80, 93));
		renderer.setForeground(new Color(255, 255, 255));
		renderer.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		attributetable.getTableHeader().setDefaultRenderer(renderer);

//		attributetable.getTableHeader().setPreferredSize(new Dimension(100, 27));

//		DefaultTableCellRenderer renderer1 = new DefaultTableCellRenderer(){
//
//			@Override
//			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
//					boolean hasFocus, int row, int column) {
//				// TODO Auto-generated method stub
//				
//				setForeground(new Color(115, 110, 102));
//				setBackground(new Color(255, 255, 255));
//				setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
//				setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
//				
//				if(value.toString().equals("false")){
//					setForeground(Color.RED);
//					setBackground(Color.RED);
//					
////					table.getR
//				}
//				
//				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//			}
//			
//		};
		
//		DefaultTableCellRenderer renderer1 = new DefaultTableCellRenderer();
//		renderer1.setForeground(new Color(115, 110, 102));
//		renderer1.setBackground(new Color(255, 255, 255));
//		renderer1.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
//		renderer1.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
////		renderer1.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 0));
//		attributetable.setDefaultRenderer(Object.class, renderer1);
		
//		for (int i = 0; i < attributetable.getColumnCount(); i++) {  
//			attributetable.getColumn(attributetable.getColumnName(i)).setCellRenderer(renderer1);  
//        }

		attributetable.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(224, 225, 220)));

//		attributetable.setBackground(new Color(255, 255, 255));
//		attributetable.setBackground(Color.BLUE);

		// attributepanel.setLayout(new GridLayout());
		// attributepanel.add(attributetable);

		attributepanel.setLayout(new BorderLayout());
		attributepanel.add(attributetable.getTableHeader(), BorderLayout.NORTH);
		attributepanel.add(attributetable, BorderLayout.CENTER);

		attributepanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		attributepanel.setOpaque(false);

		for(myProcess p:testcase.getProcessList()){
			
			Object[] rowData={p.getProcessID(),p.getProcessName(),p.getProcessParam(),p.getProcessStatus(),p.isProcessExec()};
			attributetablemodel.addRow(rowData);
		}

	}

	public JPanel getAttributepanel() {
		return attributepanel;
	}

	public JButton getToolbutton() {
		return toolbutton;
	}

	public JCheckBox getToolcheckbox() {
		return toolcheckbox;
	}

	public void setToolcheckbox(JCheckBox toolcheckbox) {
		this.toolcheckbox = toolcheckbox;
	}

	public TestCase getTestcase() {
		return testcase;
	}

	public JLabel getIconlabel() {
		return iconlabel;
	}

	public JLabel getTitlelabel() {
		return titlelabel;
	}

	public JTable getAttributetable() {
		return attributetable;
	}

	public DefaultTableModel getAttributetablemodel() {
		return attributetablemodel;
	}

	public void setAttributetable(JTable attributetable) {
		this.attributetable = attributetable;
	}

	public void setAttributetablemodel(DefaultTableModel attributetablemodel) {
		this.attributetablemodel = attributetablemodel;
	}

	
	
	
}
