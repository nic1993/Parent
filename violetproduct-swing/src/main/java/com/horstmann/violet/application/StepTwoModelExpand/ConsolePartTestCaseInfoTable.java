package com.horstmann.violet.application.StepTwoModelExpand;

import java.awt.Color;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class ConsolePartTestCaseInfoTable extends JTable{
  private static final long serialVersionUID = 1L;
  private DefaultTableModel defaultTableModel;
  private final Object[] columnNames={"序号","内容","状态"};
  //定义一个文本域  动态的展现实力画验证的结果
  private JTextArea jTextArea=new JTextArea();
  //便于返回JtextArea
  public JTextArea getjTextArea() {
	return jTextArea;
}

public ConsolePartTestCaseInfoTable(List<String> list){
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
//		for(final TestCase tc:list){
//			Thread t=new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//                       String a=tc.getTestCaseID();	
//                       String b=tc.getContent();
//                       String c=tc.getState();
//                       String d=tc.getResult();
//                       insertRowsData(a, b, c, d);
//                       jTextArea.setText(b);
//                       try{
//                    	   
//                    	   Thread.sleep(5000);
//                       }catch(InterruptedException e){
//                           e.printStackTrace();                    	   
//                       }
//				}
//			});
//		}
  }
  
  public void initRowsData(final List<TestCase> list){
	  this.removeRowsData();
	  jTextArea.append("测试用例的数量是:"+list.size()+"\n");
	  Thread t=new Thread(new Runnable() {
   
		@Override
		public void run() {
			// TODO Auto-generated method stub
		
			 Object[] rowData=new Object[columnNames.length];
				
			  if(list!=null&&!list.isEmpty()){
				  for(TestCase info:list){
					  rowData[0]=info.getTestCaseID();
					  rowData[1]=info.getContent();
					  rowData[2]=info.getState();
					  rowData[3]=info.getResult();
					  defaultTableModel.addRow(rowData);
					  defaultTableModel.fireTableDataChanged();
					  jTextArea.append(info.getDetail());
					  try{
						  Thread.sleep(3000);
					  }catch(InterruptedException e){
						  e.printStackTrace();
					  }
				  }
			  }
			
		}
	});
	  t.start();
	 
  }
  
  //往表格中添加数据(a,b,c,d) by tan
  
  public void insertRowsData(String a,String b,String c,String d){
	  Object[] rowData=new Object[columnNames.length];
	  rowData[0]=a;
	  rowData[1]=b;
	  rowData[2]=c;
	  rowData[3]=d;
	  defaultTableModel.addRow(rowData);
	  defaultTableModel.fireTableDataChanged();
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
