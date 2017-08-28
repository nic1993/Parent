package com.horstmann.violet.product.diagram.abstracts;

import java.awt.Color;
//by tan 自己定义的一个属性 用于标记可以改变的颜色
public interface IEdgeColorable {
    public void setEdgeColor(Color color);
    public Color getEdgeColor();
}
