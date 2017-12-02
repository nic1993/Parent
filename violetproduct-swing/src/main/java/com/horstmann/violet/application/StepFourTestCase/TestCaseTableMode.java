package com.horstmann.violet.application.StepFourTestCase;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import com.horstmann.violet.application.StepTwoModelExpand.TabeListener;

public class TestCaseTableMode extends AbstractTableModel 
{         /**         *  @author 小悠         */ 
    private  Vector TableData;//用来存放表格数据的线性表
    private Vector TableTitle;//表格的 列标题
    
    private List<Integer> processID;
    private List<String> processName;
    private List<String> processParam;
    private List<String> processStatus;
    private List<Boolean> processExec;  
    
    private String TestCaseID;
    private String State;
    private String ExeState;
    
    private int success;
    private int fail;
    private int total;
    
    private int failType;
    private int failTotal;
    
    //注意构造函数是第一个执行的，用于初始化 TableData，TableTitle
    public TestCaseTableMode(List<Integer> processID,List<String> processName,
    		List<String> processParam,List<String> processStatus,List<Boolean> processExec)
    {

           this.processID = processID;
           this.processName = processName;
           this.processParam = processParam;
           this.processStatus = processStatus;
           this.processExec = processExec;
           //先new 一下
           TableData = new Vector();
           TableTitle= new Vector();
            
           TableTitle.add("激励ID");
           TableTitle.add("激励名称");
           TableTitle.add("激励参数");
           TableTitle.add("激励状态");
           TableTitle.add("激励执行情况");
           //这里我们假设当前的表格是 3x3的
           //先初始化列标题,有3列

           initData(processID,processName,processParam,processStatus,processExec);
    }
    public TestCaseTableMode(String TestCaseID,String State,String ExeState,int flag)
    {
    	this.TestCaseID = TestCaseID;     
 	    this.State = State;
 	    this.ExeState = ExeState;
 	    
 	    TableData = new Vector();
        TableTitle= new Vector();
        
        if(flag == 1)
        {
          TableTitle.add("");
          TableTitle.add(ExeState);
          TableTitle.add(State);
        }
        else {
        	TableTitle.add(TestCaseID);
            TableTitle.add("测试耗时:" + ExeState + "ms");
            TableTitle.add(State);
		}
        
    }
    
    public TestCaseTableMode(int success,int fail,int total)
    {
    	 this.success = success;     
  	     this.fail = fail;
  	     this.total = total;
  	     
  	     TableData = new Vector();
         TableTitle= new Vector();
         
         TableTitle.add("");
         TableTitle.add("");
         TableTitle.add("");
         TableTitle.add("");
         
//         TableData.add("合计");
//         TableData.add(String.valueOf(success));
//         TableData.add(String.valueOf(fail));
//         TableData.add(String.valueOf(total));
         countData(success, fail, total);
    }
    public TestCaseTableMode(int failType,int failTotal)
    {
    	 this.failType = failType;     
  	     this.failTotal = failTotal;
  	     
  	     TableData = new Vector();
         TableTitle= new Vector();
         
         TableTitle.add("");
         TableTitle.add("");
         TableTitle.add("");
         TableTitle.add("");
         
//         TableData.add("合计");
//         TableData.add(String.valueOf(success));
//         TableData.add(String.valueOf(fail));
//         TableData.add(String.valueOf(total));
         countFailData(failType, failTotal);
    }
    @Override
    public int getRowCount()
    {
           //这里是告知表格应该有多少行，我们返回TableData上挂的String数组个数
           return TableData.size();
    }

    @Override
    public int getColumnCount()
    {
           //告知列数，用标题数组的大小,这样表格就是TableData.size()行，TableTitle.size()列了
           return TableTitle.size();
    }
    //获取列名 很重要 没有这个方法列名无法更改
    public String getColumnName(int column){
        return (String) TableTitle.get(column);
}

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
           //获取了表格的大小，当然还要获取数据，根据坐标直接返回对应的数据
           //小心 都是从 0开始的，小心下标越界 的问题
           //我们之前是将 String[]挂到了线性表上，所以要先获取到String[]
           //
           //获取每一行对应的String[]数组
           String LineTemp[] = (String[])this.TableData.get(rowIndex);
           //提取出对 应的数据
           return LineTemp[columnIndex];
            
           //如果我们是将 线性表Vector挂到了Vector上要注意每次我们获取的是 一行线性表
           //例如
           //return ((Vector)TableData.get(rowIndex)).get(columnIndex);
    }

//    @Override
//    public boolean isCellEditable(int rowIndex, int columnIndex)
//    {
//           //这个函数式设置每个单元格的编辑属性的
//           //这个函数AbstractTableModel已经实现，默认的是 不允许编辑状态
//           //这里我们设置为允许编辑状态
//           return true;//super.isCellEditable(rowIndex, columnIndex);
//    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
           //当单元格的数据发生改变的时候掉用该函数重设单元格的数据
           //我们想一下，数据是放在TableData 中的，说白了修改数据就是修改的
           //TableData中的数据，所以我们仅仅在此处将TableData的对应数据修改即可
            
           ((String[])this.TableData.get(rowIndex))[columnIndex]=(String)aValue;
           super.setValueAt(aValue, rowIndex, columnIndex);
           //
           //其实这里super的方法是调用了fireTableCellUpdated()只对应更新了
           //对应单元格的数据
           //fireTableCellUpdated(rowIndex, columnIndex);
    }
    private void initData(List<Integer> processID,List<String> processName,
    		List<String> processParam,List<String> processStatus,List<Boolean> processExec)
    {
    	 int size = TableData.size();
    		 for(int j = 0;j < processID.size();j++)
    		 {   
    			 String[] line4 = new String[5];
    	   		 line4[0] = String.valueOf(processID.get(j));
    	   		 line4[1] = processName.get(j);
    	   		 line4[2] = processParam.get(j);
    	   		 line4[3] = processStatus.get(j);
    	   		 line4[4] = String.valueOf(processExec.get(j));
    	   		 TableData.add(line4);
    	   		 line4 = null;
    		 }
    	 fireTableRowsInserted(size, size);
    }
    
    public void countData(int success,int fail,int total)
    {
    	 int size = TableData.size();  
		 String[] line4 = new String[4];
	   	 line4[0] = "合计";
	   	 line4[1] = String.valueOf(success);
	   	 line4[2] = String.valueOf(fail);
	     line4[3] = String.valueOf(total);
	   	 TableData.add(line4);
	   	 
	   	 String[] line5 = new String[4];
	   	 line5[0] = "百分比";
	   	 line5[1] = countPercentage(success, total);
	   	 line5[2] = countPercentage(fail, total);
	     line5[3] = "100%";

	   	 TableData.add(line5);
	 fireTableRowsInserted(size, size);
    }
    public void countFailData(int failType,int failTotal)
    {
    	 int size = TableData.size();  
		 String[] line4 = new String[4];
	   	 line4[0] = "合计";
	   	 line4[1] = String.valueOf(failTotal);
	   	 line4[2] = String.valueOf(failType);
	     line4[3] = String.valueOf(failTotal);
	   	 TableData.add(line4);
	   	 
	   	 String[] line5 = new String[4];
	   	 line5[0] = "百分比";
	   	 line5[1] = "100%";
	   	 if(failTotal == 0)
	   	 {
	   		line5[2] = "0%";
	   	 }
	   	 else {
	   		line5[2] = countPercentage(failType, failTotal);
		}
	     line5[3] = "100%";

	   	 TableData.add(line5);
	 fireTableRowsInserted(size, size);
    }
    public Vector getTableTitle() {
		return TableTitle;
	}  
    
    public String countPercentage(int num1,int num2)
    {
		// 创建一个数值格式化对象

		NumberFormat numberFormat = NumberFormat.getInstance();

		// 设置精确到小数点后2位

		numberFormat.setMaximumFractionDigits(2);

		String result = numberFormat.format((float) num1 / (float) num2 * 100);

		return  result + "%";
    }
}

