package com.horstmann.violet.application.StepThreeTestCase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;

public class StepThreeTabelModel extends AbstractTableModel
{         /**         *  @author 小悠         */ 
    private  Vector TableData;//用来存放表格数据的线性表
    private Vector TableTitle;//表格的 列标题

	private List<String> paramterNameList;
    private List<String> paramterValueList;
    
    private String testSequence;
    private String excitation;
    private String testCase;
    
    private List<String> constraintNameString;
    private List<Double> pros;
    private List<Integer> numbers;
    private List<Double> actualPercentsDoubles;
    private DecimalFormat  df = new DecimalFormat();
	private String pattern = "#0.000";
    //注意构造函数是第一个执行的，用于初始化 TableData，TableTitle
    public StepThreeTabelModel(List<String> paramterNameList,List<String> paramterValueList)
    {
           this.paramterNameList = paramterNameList;
           this.paramterValueList = paramterValueList;
           //先new 一下
           TableData = new Vector();
           TableTitle= new Vector();
            
//           TableTitle.add("迁移关系");
           //这里我们假设当前的表格是 3x3的
           //先初始化列标题,有3列
           TableTitle.add("约束名称");
           TableTitle.add("求解集合");
           initData(paramterNameList,paramterValueList);
    }
    public StepThreeTabelModel(List<String> constraintNameString,List<Double> actualPercentsDoubles,List<Double> pros,List<Integer> numbers )
    {
           this.constraintNameString = constraintNameString;
           this.pros = pros;
           this.numbers =numbers;
           this.actualPercentsDoubles = actualPercentsDoubles;
           //先new 一下
           TableData = new Vector();
           TableTitle= new Vector();
            
//           TableTitle.add("迁移关系");
           //这里我们假设当前的表格是 3x3的
           //先初始化列标题,有3列
           //杨杰部分界面
           TableTitle.addElement("序号");
           TableTitle.addElement("测试用例()路径");
           TableTitle.addElement("路径概率");
           TableTitle.addElement("抽取组数");
           
//           TableTitle.add("测试序列集");
//           TableTitle.add("测试序列信息");
           initData(constraintNameString, actualPercentsDoubles,pros, numbers);
    }
    public StepThreeTabelModel(String testSequence,int flag)
    {
           this.testSequence = testSequence;
           //先new 一下
           TableData = new Vector();
           TableTitle= new Vector();
            
//           TableTitle.add("迁移关系");
           //这里我们假设当前的表格是 3x3的
           //先初始化列标题,有3列
           TableTitle.add("");
           TableTitle.add("");
           if(flag == 1)
           {
        	   initabstract(testSequence);
           }
           else {
			inittest(testSequence);
		}
           
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

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
           //这个函数式设置每个单元格的编辑属性的
           //这个函数AbstractTableModel已经实现，默认的是 不允许编辑状态
           //这里我们设置为允许编辑状态
           return false;//super.isCellEditable(rowIndex, columnIndex);
    }
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
    private void initData(List<String> paramterNameList,List<String> paramterValueList)
    {
    	int size = TableData.size();
    	for(int i = 0;i < paramterNameList.size();i++)
    	{
    		String[] line4 = new String[2];
   		 line4[0] = paramterNameList.get(i);
   		 line4[1] = paramterValueList.get(i);
   		 TableData.add(line4);
   		 line4 = null;
    	}
    	fireTableRowsInserted(size, size);
    }
    private void initData(List<String> constraintNameString,List<Double> actualPercentsDoubles,List<Double> pros,List<Integer> number)
    {
    	df.applyPattern(pattern);
    	int size = TableData.size();
    	for(int i = 0;i < constraintNameString.size();i++)
    	{
    		String[] line4 = new String[4];
   		 line4[0] = String.valueOf(i);
   		 line4[1] = constraintNameString.get(i);
   		 line4[2] = String.valueOf(pros.get(i));
   		 line4[3] = String.valueOf(number.get(i));
//   		 line4[1] = "路径概率为(可靠性测试用例生成比率"+df.format(actualPercentsDoubles.get(i))+"):  "+df.format(pros.get(i))+" "+"此类用例包含"+String.valueOf(number.get(i))+"个";
   		 TableData.add(line4);
   		 line4 = null;
    	}
    	fireTableRowsInserted(size, size);
    }
    private void initabstract(String testSequence)
    {
    	 int size = TableData.size();
    	 String[] line1 = new String[2];
   		 line1[0] = "测试序列";
   		 line1[1] = testSequence;
   		 TableData.add(line1);

    	
    	fireTableRowsInserted(size, size);
    }
    private void inittest(String testSequence)
    {
    	 int size = TableData.size();
   	     String[] line1 = new String[2];
  		 line1[0] = "测试用例";
  		 line1[1] = testSequence;
  		 TableData.add(line1);
    }
    
    public Vector getTableTitle() {
		return TableTitle;
	}
    
}

