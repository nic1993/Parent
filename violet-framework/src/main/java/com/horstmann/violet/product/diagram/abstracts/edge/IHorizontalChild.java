package com.horstmann.violet.product.diagram.abstracts.edge;

import java.awt.geom.Point2D;

import com.horstmann.violet.product.diagram.abstracts.node.INode;

public interface IHorizontalChild {
	

	void setStart(Point2D startpoint);
	
	void setEnd(Point2D endpoint);
	
	Point2D getStart();
	
	Point2D getEnd();
	
    String getState();
    
    void setState(String state);
    
    void setCondition(String condition);
   
    String getCondition();
    
    void setContinuetime(String continuetime);
    
    String getContinuetime();
    
    
    //后面4个 by tan 后期添加的
    int getStartPointTime();

	void setStartPointTime(int startPointTime) ;

	int getEndPointTime();

	void setEndPointTime(int endPointTime);

    
  
}
