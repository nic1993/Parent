package com.horstmann.violet.application.gui.util.wujun.TDVerification;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GB extends GridBagConstraints  
{  
   //初始化左上角位置  
   public GB(int gridx, int gridy)  
   {  
      this.gridx = gridx;  
      this.gridy = gridy;  
   }  
  
   //初始化左上角位置和所占行数和列数  
   public GB(int gridx, int gridy, int gridwidth, int gridheight)  
   {  
      this.gridx = gridx;  
      this.gridy = gridy;  
      this.gridwidth = gridwidth;  
      this.gridheight = gridheight;  
   }  
  
   //对齐方式  
   public GB setAnchor(int anchor)  
   {  
      this.anchor = anchor;  
      return this;  
   }  
  
   //是否拉伸及拉伸方向  
   public GB setFill(int fill)  
   {  
      this.fill = fill;  
      return this;  
   }  
  
   //x和y方向上的增量  
   public GB setWeight(double weightx, double weighty)  
   {  
      this.weightx = weightx;  
      this.weighty = weighty;  
      return this;  
   }  
  
   //外部填充  
   public GB setInsets(int distance)  
   {  
      this.insets = new Insets(distance, distance, distance, distance);  
      return this;  
   }  
  
   //外填充  
   public GB setInsets(int top, int left, int bottom, int right)  
   {  
      this.insets = new Insets(top, left, bottom, right);  
      return this;  
   }  
  
   //内填充  
   public GB setIpad(int ipadx, int ipady)  
   {  
      this.ipadx = ipadx;  
      this.ipady = ipady;  
      return this;  
   }  
}  
