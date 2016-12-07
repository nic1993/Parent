package com.horstmann.violet.application.gui.util.chengzuo.Verfication;
import java.awt.Font;
import java.awt.Panel;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;

public class JFreeChartTest {
	//返回一个jpanel
  public  static JPanel getJFreeChartTest(List<TestCase> list) {
	  //用于记录成功和失败
	  int i=0;
	  int j=0;
	  //遍历看看成功或者失败的个数统计
	  for(TestCase info:list){
		  String str=info.getResult();
		  if("成功".equals(str)){
			  i++;
		  }
		  else{
			  j++;
		  }
	  }
	  JPanel jp=new JPanel(); 
	  DefaultPieDataset dpd=new DefaultPieDataset();
	  if(i==0&&j!=0){//如果只有成功
		  dpd.setValue("成功", 100);
	  }
	  else if(i!=0&&j==0){//如果只有失败
		  dpd.setValue("失败", 100);
	  }
	  else if(i!=0&&j!=0){//失败和成功的都有
		  double a=i/(double)(i+j)*100;
		  double b=j/(double)(i+j)*100;
		  DecimalFormat df=new DecimalFormat(".##");
		  String sa=df.format(a);
		  String sb=df.format(b);
		  a=Double.parseDouble(sa);
		  b=Double.parseDouble(sb);
		  dpd.setValue("成功",a);
		  dpd.setValue("失败",b);
	  }
   //  dpd.setValue("开发人员",45);
     //dpd.setValue("其他人员", 10);
  
     JFreeChart chart=ChartFactory.createPieChart("测试用例实例化验证数据",dpd,true,true,false); 
     //对里面的标题
     Font font = new Font("", 10, 20);
     TextTitle txtTitle = null;
     txtTitle = chart.getTitle();
     txtTitle.setFont(font);
//     this.add(chart);
     ChartPanel cp=new ChartPanel(chart);
     jp.add(cp);
     ChartFrame chartFrame=new ChartFrame("测试用例实例化验证数据",chart); 
     //对里面的字体
     Font font2 = new Font("", 10, 16);
     PiePlot pieplot = (PiePlot)chart.getPlot();
     pieplot.setLabelFont(font2); 
     chart.getLegend().setItemFont(font2); 
     
    // chart要放在Java容器组件中，ChartFrame继承自java的Jframe类。该第一个参数的数据是放在窗口左上角的，不是正中间的标题。
     //chartFrame.pack(); //以合适的大小展现图形
    // chartFrame.setVisible(true);//图形是否可见
//     this.pack();
//     this.setVisible(true);
     
     return jp;
  }
}
