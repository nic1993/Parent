package com.horstmann.violet.application.StepFourTestCase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import com.horstmann.violet.application.StepTwoModelExpand.TabeListener;

public class TableTitleMode extends AbstractTableModel 
{         /**         *  @author 小悠         */ 
	private Vector TableData;//用来存放表格数据的线性表
    private Vector TableTitle;//表格的 列标题
     
    //注意构造函数是第一个执行的，用于初始化 TableData，TableTitle
    public TableTitleMode()
    {
           //先new 一下
           TableData = new Vector();
           TableTitle= new Vector();
            
           //这里我们假设当前的表格是 3x3的
           //先初始化 列标题,有3列
           TableTitle.add("第一列");
           TableTitle.add("第二列");
           TableTitle.add("第三列");
            
           //初始化3行数据，方便起见直接用String数组保存每一行的数据
           //第0行,都显示表格的坐标
           String []Line1 = {"(0,0)","(0,1)","(0,2)"};
           //第1行
           String []Line2 = {"(1,0)","(1,1)","(1,2)"};
           //第2行
           String []Line3 = {"(2,0)","(2,1)","(2,2)"};
           //将数据挂到线性表形成二维的数据表，形成映射
           TableData.add(Line1);
           TableData.add(Line2);
           TableData.add(Line3);
            
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
           return true;//super.isCellEditable(rowIndex, columnIndex);
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
}

