/*
 Violet - A program for editing UML diagrams.

 Copyright (C) 2007 Cay S. Horstmann (http://horstmann.com)
 Alexandre de Pellegrin (http://alexdp.free.fr);

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.horstmann.violet.product.diagram.sequence;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.type.NullType;

import com.horstmann.violet.product.diagram.abstracts.Direction;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.abstracts.node.RectangularNode;

/**
 * An activation bar in a sequence diagram. This activation bar is hang on a lifeline (implicit parameter)
 */
public class ActivationBarNode extends RectangularNode
{
    @Override
    public boolean addChild(INode n, Point2D p)//添加子节点
    {
        if (!n.getClass().isAssignableFrom(ActivationBarNode.class))
        	// 判定此 Class 对象所表示的类或接口与指定的 Class 
        	//参数所表示的类或接口是否相同，或是否是其超类或超接口。
        {
            return false;
        }
        n.setParent(this);//将ActivationBarNode置为n点的父节点
        n.setGraph(getGraph());
        n.setLocation(p);//将子节点设为传进来的P点的坐标
        addChild(n, getChildren().size());//在child集合最后继续添加子节点
        return true;
    }

    @Override
    public void removeChild(INode node)
    {
        super.removeChild(node);
    }
     //删除子节点    
    @Override
    public boolean addConnection(IEdge edge)//给边之间连线
    {
        INode endingNode = edge.getEnd();//获取起始节点和终止节点
        INode startingNode = edge.getStart();
        if (startingNode == endingNode)
        {
            return false;
        }//如果起始节点和终止节点相同。则返回错误
        if (edge instanceof CallEdge)//如果是发送消息的边
        {
            return isCallEdgeAcceptable((CallEdge) edge);

        }
        else if (edge instanceof ReturnEdge)//如果是返回消息的边
        {
            return isReturnEdgeAcceptable((ReturnEdge) edge);
        }
        return false;
    }

    @Override
    public void removeConnection(IEdge e)
    {
        super.removeConnection(e);
    }
//删除连接
    @Override
    public Point2D getConnectionPoint(IEdge e)//获取连接点
    {
        boolean isCallEdge = e.getClass().isAssignableFrom(CallEdge.class);
        boolean isReturnEdge = e.getClass().isAssignableFrom(ReturnEdge.class);//判断e是什么类型的边
        boolean isActivationBarNodeOnStart = e.getStart() != null//是起始的activationBar
                && e.getStart().getClass().isAssignableFrom(ActivationBarNode.class);//   
        boolean isActivationBarNodeOnEnd = e.getEnd() != null && e.getEnd().getClass().isAssignableFrom(ActivationBarNode.class);
        boolean isLifelineNodeOnEnd = e.getEnd() != null && e.getEnd().getClass().isAssignableFrom(LifelineNode.class);
        if (isCallEdge)//如果是发送消息的边
        {
            if (isActivationBarNodeOnStart && isActivationBarNodeOnEnd)//是在两个activation Bar上连接的边
            {
                ActivationBarNode startingNode = (ActivationBarNode) e.getStart();
                ActivationBarNode endingNode = (ActivationBarNode) e.getEnd();
                LifelineNode startingLifelineNode = startingNode.getImplicitParameter();
                LifelineNode endingLifelineNode = endingNode.getImplicitParameter();
                boolean isSameLifelineNode = startingLifelineNode != null && endingLifelineNode != null
                        && startingLifelineNode.equals(endingLifelineNode);//判断是否在相同的生命线上
                boolean isDifferentLifelineNodes = startingLifelineNode != null && endingLifelineNode != null
                        && !startingLifelineNode.equals(endingLifelineNode);
                // Case 1 : two activation bars connected on differents
                // LifeLines
                //第一种情况：两个activation连接在不同的生命线上
                if (isDifferentLifelineNodes && isActivationBarNodeOnStart && isActivationBarNodeOnEnd)
                {
                    boolean isStartingNode = this.equals(e.getStart());//判断这个activationBar是起始的还是终止的
//                    System.out.println(this);
//                    System.exit(0);
                    boolean isEndingNode = this.equals(e.getEnd());
                    if (isStartingNode)//如果是起始点
                    {
                        Point2D startingNodeLocation = getLocationOnGraph();//获取在图中的起始点位置
                        Point2D endingNodeLocation = e.getEnd().getLocationOnGraph();//获取终止节点在图中的位置
                        Direction d = e.getDirection(this);//d获取e连接的角度
                        if (d.getX() > 0)//如果在X上面的分量大于0
                        {
                            double x = startingNodeLocation.getX();//x为起始点的X坐标
                            double y = endingNodeLocation.getY();//y为终止点的Y坐标
                            Point2D p = new Point2D.Double(x, y);//P定为activationBar边角的点
                            e.setStartLocation(p);
                        
                          //  p = getGraph().getGridSticker().snap(p);
                            return p;
                        }
                        else
                        {//如果在X上面的分量小于0
                            double x = startingNodeLocation.getX() + DEFAULT_WIDTH;
                            double y = endingNodeLocation.getY();
                            Point2D p = new Point2D.Double(x, y);
                           // p = getGraph().getGridSticker().snap(p);
                           e.setStartLocation(p);
                            return p;
                        }
                    }
                    if (isEndingNode)//如果是结束节点
                    {
                        Point2D endingNodeLocation = getLocationOnGraph();
                        Direction d = e.getDirection(this);
                        if (d.getX() > 0)
                        {
                            double x = endingNodeLocation.getX();
                            double y = endingNodeLocation.getY();
                            Point2D p = new Point2D.Double(x, y);
                       //     p = getGraph().getGridSticker().snap(p);
                          e.setEndLocation(p);
                            return p;
                        }
                        else
                        {
                            double x = endingNodeLocation.getX() + DEFAULT_WIDTH;
                            double y = endingNodeLocation.getY();
                            Point2D p = new Point2D.Double(x, y);
                         //   p = getGraph().getGridSticker().snap(p);
                            e.setEndLocation(p);
                            return p;
                        }
                    }
                }
                // Case 2 : two activation bars connected on same lifeline (self
                // call)//自回调activationBars连接在同一生命线上
                if (isSameLifelineNode && isActivationBarNodeOnStart && isActivationBarNodeOnEnd)
                {
                	boolean isStartingNode = this.equals(e.getStart());
                    boolean isEndingNode = this.equals(e.getEnd());
                    if (isStartingNode)
                    {
                        Point2D startingNodeLocation = getLocationOnGraph();
                        Point2D endingNodeLocation = e.getEnd().getLocation();
                        double x = startingNodeLocation.getX() + DEFAULT_WIDTH;
                        double y = startingNodeLocation.getY() + endingNodeLocation.getY() - CALL_YGAP / 2 ;
                        Point2D p = new Point2D.Double(x, y);
                      //  p = getGraph().getGridSticker().snap(p);
                      
                        return p;
                    }
                    if (isEndingNode)
                    {
                        Point2D endingNodeLocation = getLocationOnGraph();
                        double x = endingNodeLocation.getX() + DEFAULT_WIDTH;
                        double y = endingNodeLocation.getY();
                        Point2D p = new Point2D.Double(x, y);
                       
                      //  p = getGraph().getGridSticker().snap(p);
                        return p;
                    }
                }
            }
            if (isActivationBarNodeOnStart && isLifelineNodeOnEnd)
            {
                Direction d = e.getDirection(this);
                Point2D startingNodeLocation = getLocationOnGraph();
                if (d.getX() > 0)
                {
                    double x = startingNodeLocation.getX();
                    double y = startingNodeLocation.getY() + CALL_YGAP / 2;
                    Point2D p = new Point2D.Double(x, y);
               //     p = getGraph().getGridSticker().snap(p);
                    e.setStartLocation(p);
                    return p;
                }
                else
                {
                    double x = startingNodeLocation.getX() + DEFAULT_WIDTH;
                    double y = startingNodeLocation.getY() + CALL_YGAP / 2;
                    Point2D p = new Point2D.Double(x, y);
              //      p = getGraph().getGridSticker().snap(p);
                    e.setEndLocation(p);
                    return p;
                }
            }
        }
        if (isReturnEdge)
        {
            if (isActivationBarNodeOnStart && isActivationBarNodeOnEnd)
            {
                ActivationBarNode startingNode = (ActivationBarNode) e.getStart();
                ActivationBarNode endingNode = (ActivationBarNode) e.getEnd();
                LifelineNode startingLifelineNode = startingNode.getImplicitParameter();
                LifelineNode endingLifelineNode = endingNode.getImplicitParameter();
                boolean isDifferentLifelineNodes = startingLifelineNode != null && endingLifelineNode != null
                        && !startingLifelineNode.equals(endingLifelineNode);
                // Case 1 : two activation bars connected on differents
                // LifeLines
                if (isDifferentLifelineNodes && isActivationBarNodeOnStart && isActivationBarNodeOnEnd)
                {
                    boolean isStartingNode = this.equals(e.getStart());
                    boolean isEndingNode = this.equals(e.getEnd());
                    if (isStartingNode)
                    {
                        Point2D startingNodeLocation = getLocationOnGraph();
                        Rectangle2D startingNodeBounds = getBounds();
                        Direction d = e.getDirection(this);
                        if (d.getX() > 0)
                        {
                            double x = startingNodeLocation.getX();
                            double y = startingNodeLocation.getY() + startingNodeBounds.getHeight();
                            Point2D p = new Point2D.Double(x, y);
                         //   p = getGraph().getGridSticker().snap(p);
                            e.setStartLocation(p);
                            return p;
                        }
                        else
                        {
                            double x = startingNodeLocation.getX() + DEFAULT_WIDTH;
                            double y = startingNodeLocation.getY() + startingNodeBounds.getHeight();
                            Point2D p = new Point2D.Double(x, y);
                       //     p = getGraph().getGridSticker().snap(p);
                           e.setStartLocation(p);
                            return p;
                        }
                    }
                    if (isEndingNode)
                    {
                        Point2D startingNodeLocation = e.getStart().getLocationOnGraph();
                        Rectangle2D startingNodeBounds = e.getStart().getBounds();
                        Point2D endingNodeLocation = getLocationOnGraph();
                        Direction d = e.getDirection(this);
                        if (d.getX() > 0)
                        {
                            double x = endingNodeLocation.getX();
                            double y = startingNodeLocation.getY() + startingNodeBounds.getHeight();
                            Point2D p = new Point2D.Double(x, y);
                       //     p = getGraph().getGridSticker().snap(p);
                           e.setEndLocation(p);
                            return p;
                        }
                        else
                        {
                            double x = endingNodeLocation.getX() + DEFAULT_WIDTH;
                            double y = startingNodeLocation.getY() + startingNodeBounds.getHeight();
                            Point2D p = new Point2D.Double(x, y);
                        //    p = getGraph().getGridSticker().snap(p);
                           e.setEndLocation(p);
                            return p;
                        }
                    }
                }
            }
        }
        // Default case
        Direction d = e.getDirection(this);
        if (d.getX() > 0)
        {
            double y = getBounds().getMinY();
            double x = getBounds().getMaxX();
            Point2D p = new Point2D.Double(x, y);
         //   p = getGraph().getGridSticker().snap(p);
           
            return p;
        }
        else
        {
            double y = getBounds().getMinY();
            double x = getBounds().getX();
            Point2D p = new Point2D.Double(x, y);
         //   p = getGraph().getGridSticker().snap(p);
           
            return p;
        }

    }



    /**
     * 
     * @return true if this activation bar is connected to another one from another lifeline with a CallEdge AND if this activation
     *         bar is the STARTING node of this edge
     */
    private boolean isCallingNode()//判断是否是发送消息的节点
    {
        LifelineNode currentLifelineNode = getImplicitParameter();
        for (IEdge edge : getGraph().getAllEdges())
        {
            if (edge.getStart() != this)
            {
                continue;
            }
            if (!edge.getClass().isAssignableFrom(CallEdge.class))
            {
                continue;
            }
            INode endingNode = edge.getEnd();
            if (!endingNode.getClass().isAssignableFrom(ActivationBarNode.class))
            {
                continue;
            }
            if (((ActivationBarNode) endingNode).getImplicitParameter() == currentLifelineNode)
            {
                continue;
            }
            return true;
        }
        return false;
    }

    /**
     * 
     * @return true if this activation bar has been called by another activation bar
     */
    private boolean isCalledNode()//判断是否是被发送消息的节点
    {
        LifelineNode currentLifelineNode = getImplicitParameter();
        for (IEdge edge : getGraph().getAllEdges())
        {
            if (edge.getEnd() != this)
            {
                continue;
            }
            if (!edge.getClass().isAssignableFrom(CallEdge.class))
            {
                continue;
            }
            INode startingNode = edge.getStart();
            if (!startingNode.getClass().isAssignableFrom(ActivationBarNode.class))
            {
                continue;
            }
            if (((ActivationBarNode) startingNode).getImplicitParameter() == currentLifelineNode)
            {
                continue;
            }
            return true;
        }
        return false;
    }

    @Override
    public Rectangle2D getBounds()//获取矩形边界
    {
        Point2D nodeLocation = getLocation();//获取节点的位置
        // Height
        double height = getHeight();//获取节点的高度
        // TODO : manage openbottom
        Rectangle2D currentBounds = new Rectangle2D.Double(nodeLocation.getX(), nodeLocation.getY(), DEFAULT_WIDTH, height);
      //  Rectangle2D snappedBounds = getGraph().getGridSticker().snap(currentBounds);
     
        return currentBounds;
    }

    public double getHeight()
    {
        double height = DEFAULT_HEIGHT;
        height = Math.max(height, getHeightWhenLinked());
        height = Math.max(height, getHeightWhenHasChildren());
        return height;
    }

    /**
     * If this activation bar calls another activation bar on another life line, its height must be greater than the activation bar
     * which is called
     * 
     * @return h
     */
    private double getHeightWhenLinked()//如果一个activationBar发送消息给另一个activationBar,则发送消息的节点要比接受消息的节点高
    {
    	double height = 0;
    	for (IEdge edge : getGraph().getAllEdges())
        {
            if (!edge.getClass().isAssignableFrom(CallEdge.class))
            {
                continue;
            }//获取所有的边，判断是否是消息发送的边
            
            if (edge.getStart() == this)
            {
            	
                INode endingNode = edge.getEnd();
                INode startingnode = edge.getStart();
                boolean isActivationBarNode = endingNode instanceof ActivationBarNode;
                //如果另外一个节点是activationBarNode
				if (isActivationBarNode)
                {
//					INode endnode = endingNode;
//
//					while(!endnode.getParent().getClass().getSimpleName().equals("LifelineNode"))
//					{
//						endnode = endnode.getParent();
//					}
//					INode startnode = startingnode;
//					while (!startnode.getParent().getClass().getSimpleName().equals("LifelineNode")) {
//						startnode = startnode.getParent();
//					}
//					
//					if(startnode.getParent().getId().equals(endnode.getParent().getId()))
//					{
//						continue;
//					}
//					else{
						Rectangle2D endingNodeBounds = endingNode.getBounds();//获取结束节点的边界
						double newHeight = CALL_YGAP / 2 + endingNodeBounds.getHeight() + (endingNode.getLocationOnGraph().getY() - this.getLocationOnGraph().getY()) + CALL_YGAP / 2;
						height = Math.max(height,  newHeight);
//					}
					
                }
            }
        }
        return Math.max(DEFAULT_HEIGHT, height);//根据CALLED NODE来调整该节点的高度
    }

    /**
     * 
     * @return
     */
    private double getHeightWhenHasChildren()
    {
        double height = DEFAULT_HEIGHT;
        int childVisibleNodesCounter = 0;
        for (INode aNode : getChildren())
        {
            if (aNode instanceof ActivationBarNode)
            {
                childVisibleNodesCounter++;
            }
        }
        if (childVisibleNodesCounter > 0)
        {
            for (INode aNode : getChildren())
            {
                if (aNode instanceof ActivationBarNode)
                {
                	ActivationBarNode anActivationBarNode = (ActivationBarNode) aNode;
                	double h = anActivationBarNode.getHeight();
                	double v = anActivationBarNode.getLocation().getY();
                	double maxY = v + h;
                    height = Math.max(height, maxY);
                }
            }
            height = height + CALL_YGAP;
        }
        return height;
    }
    
    @Override
    public void setLocation(Point2D aPoint)//设置位置，（以点的形式）
    {
        INode parentNode = getParent();//获取父节点
        // Special use case : when this node is connected to another activation bar on another life line,
        // we adjust its location to keep it relative to the node it is connected to
        if (parentNode != null && parentNode.getClass().isAssignableFrom(LifelineNode.class)) {
            LifelineNode lifelineNode = getImplicitParameter();
            Rectangle2D topRectangle = lifelineNode.getTopRectangle();
            if (aPoint.getY() <= topRectangle.getHeight() + CALL_YGAP) {
                aPoint = new Point2D.Double(aPoint.getX(), topRectangle.getHeight() + CALL_YGAP);
            }
        }
        super.setLocation(aPoint);
    }
    
    @Override
    public Point2D getLocation()
    {
        if (this.locationCache != null) {
        	return this.locationCache;
        }
    	INode parentNode = getParent();
        if (parentNode == null) {
        	this.locationCache = super.getLocation();
            return this.locationCache;
        }
        List<IEdge> connectedEdges = getConnectedEdges();
        boolean isChildOfActivationBarNode = (parentNode.getClass().isAssignableFrom(ActivationBarNode.class));
        boolean isChildOfLifelineNode = (parentNode.getClass().isAssignableFrom(LifelineNode.class));
        // Case 1 : just attached to a lifeline
        //情况1：仅仅在一个生命线上
        if (isChildOfLifelineNode && connectedEdges.isEmpty()) {
            Point2D rawLocation = super.getLocation();
            double horizontalLocation = getHorizontalLocation();
            double verticalLocation = rawLocation.getY();
            Point2D adjustedLocation = new Point2D.Double(horizontalLocation, verticalLocation);
           // adjustedLocation = getGraph().getGridSticker().snap(adjustedLocation);
            super.setLocation(adjustedLocation);
            this.locationCache = adjustedLocation;
            return adjustedLocation;
        }
        // Case 2 : is child of another activation bar
        //情况2：是另外一个activationBar的子节点
        if (isChildOfActivationBarNode && connectedEdges.isEmpty()) {
            Point2D rawLocation = super.getLocation();
            double horizontalLocation = getHorizontalLocation();
            double verticalLocation = rawLocation.getY();
            verticalLocation = Math.max(verticalLocation, CALL_YGAP);
            Point2D adjustedLocation = new Point2D.Double(horizontalLocation, verticalLocation);
          //  adjustedLocation = getGraph().getGridSticker().snap(adjustedLocation);
            super.setLocation(adjustedLocation);
            this.locationCache = adjustedLocation;
            return adjustedLocation;
        }
        // Case 3 : is connected
        //和其他的节点相连接
        if (!connectedEdges.isEmpty()) {
            Point2D rawLocation = super.getLocation();
            for (IEdge edge : getConnectedEdges()) {
                if (!edge.getClass().isAssignableFrom(CallEdge.class))
                {
                    continue;
                }
                if (edge.getEnd() == this)
                {
                    INode startingNode = edge.getStart();               
                    Point2D startingNodeLocationOnGraph = startingNode.getLocationOnGraph();
                    Point2D endingNodeParentLocationOnGraph = getParent().getLocationOnGraph();
                    double yGap = rawLocation.getY() - startingNodeLocationOnGraph.getY() + endingNodeParentLocationOnGraph.getY();
                    if (yGap < CALL_YGAP / 2) {
                        double horizontalLocation = getHorizontalLocation();
                        double minY = startingNodeLocationOnGraph.getY() - endingNodeParentLocationOnGraph.getY() + CALL_YGAP / 2;
                        Point2D adjustedLocation = new Point2D.Double(horizontalLocation, minY);
                      //  adjustedLocation = getGraph().getGridSticker().snap(adjustedLocation);
                        super.setLocation(adjustedLocation);
                        this.locationCache = adjustedLocation;
                        return adjustedLocation;
                    }
                    break;
                }
            }
        }
        // Case 4 : default case
        Point2D rawLocation = super.getLocation();
        double horizontalLocation = getHorizontalLocation();
        double verticalLocation = rawLocation.getY();
        Point2D adjustedLocation = new Point2D.Double(horizontalLocation, verticalLocation);
       // adjustedLocation = getGraph().getGridSticker().snap(adjustedLocation);
        super.setLocation(adjustedLocation);
        this.locationCache = adjustedLocation;
        return adjustedLocation;
    }

    @Override
    public void draw(Graphics2D g2)//抽象的画图操作
    {
        // Reset location cache;
    	this.locationCache = null;//初始化位置
    	// Backup current color;
        Color oldColor = g2.getColor();//获取当前的颜色
//        g2.setColor(Color.black);
        // Translate g2 if node has parent
        Point2D nodeLocationOnGraph = getLocationOnGraph();
        Point2D nodeLocation = getLocation();
        Rectangle2D b = getBounds();
        Point2D g2Location = new Point2D.Double(nodeLocationOnGraph.getX() - nodeLocation.getX(), nodeLocationOnGraph.getY()
                - nodeLocation.getY());
        g2.translate(g2Location.getX(), g2Location.getY());
        // Perform painting
        //执行画图
        super.draw(g2);
        g2.setColor(getBackgroundColor());
        g2.fill(b);
        g2.setColor(Color.black);
        g2.draw(b);
        // Restore g2 original location
        g2.translate(-g2Location.getX(), -g2Location.getY());//得到g2原始的位置
        // Restore first color
        g2.setColor(oldColor);
        // Reset location for next draw//为下一次画图重置位置
        // Draw its children
        for (INode node : getChildren())//对每个节点获取子节点
        {
            node.draw(g2);//画子节点
        }
    }

    /**
     * Gets the participant's life line of this call. Note : method's name is ot set to getLifeLine to keep compatibility with older
     * versions
     * 
     * @return the participant's life line
     */
    public LifelineNode getImplicitParameter()
    //获取发送消息的生命线节点
    {
        if (this.lifeline == null) {
        	INode aParent = this.getParent();
        	List<INode> nodeStack = new ArrayList<INode>();
        	nodeStack.add(aParent);
        	while (!nodeStack.isEmpty()) {
        		INode aNode = nodeStack.get(0);
        		if (LifelineNode.class.isInstance(aNode)) {
        			this.lifeline = (LifelineNode) aNode;
        		}
        		if (ActivationBarNode.class.isInstance(aNode)) {
        			INode aNodeParent = aNode.getParent();
        			nodeStack.add(aNodeParent);
        		}
        		nodeStack.remove(0);
        	}
        }
    	return lifeline;
    }

    /**
     * Sets the participant's life line of this call. Note : method's name is ot set to setLifeLine to keep compatibility with older
     * versions
     * 
     * @param newValue the participant's lifeline
     */
    public void setImplicitParameter(LifelineNode newValue)
    {
        // Nothing to do. Just here to keep compatibility.
    }

    private boolean isReturnEdgeAcceptable(ReturnEdge edge)//返回消息的边是否可以画
    {
        INode endingNode = edge.getEnd();//获取起点和终点
        INode startingNode = edge.getStart();
        if (startingNode == null || endingNode == null) {
        	return false;
        }
        Class<? extends INode> startingNodeClass = startingNode.getClass();
        Class<? extends INode> endingNodeClass = endingNode.getClass();
        if (!startingNodeClass.isAssignableFrom(ActivationBarNode.class)
                || !endingNodeClass.isAssignableFrom(ActivationBarNode.class))
        {
            return false;
        }
        ActivationBarNode startingActivationBarNode = (ActivationBarNode) startingNode;
        ActivationBarNode endingActivationBarNode = (ActivationBarNode) endingNode;
        if (startingActivationBarNode.getImplicitParameter() == endingActivationBarNode.getImplicitParameter())
        {
            return false;
        }
        if (!isCalledNode())
        {
            return false;
        }
        return true;
    }

    private boolean isCallEdgeAcceptable(CallEdge edge)//发送消息的边是否可以画
    {
        INode endingNode = edge.getEnd();//获取起点和终点
        INode startingNode = edge.getStart();
        Point2D endingNodePoint = edge.getEndLocation();//endingNodePoint置为边的最后一点
        Class<?> startingNodeClass = (startingNode != null ? startingNode.getClass() : NullType.class);
        Class<?> endingNodeClass = (endingNode != null ? endingNode.getClass() : NullType.class);
        // Case 1 : check is call edge doesn't connect already connected nodes
        //情况1：检查消息传递的边是不是连上了已经连在一起的节点
        if (startingNode != null && endingNode != null) {//如果有相连的两个节点
        	for (IEdge anEdge : getGraph().getAllEdges()) {//获取图中的所有边
        		if (!anEdge.getClass().isAssignableFrom(CallEdge.class)) {
        			continue;//如果不是callEdge类型的边，则继续进行下一次 for循环
        		}
        		INode anEdgeStartingNode = anEdge.getStart();
        		INode anEdgeEndingNode = anEdge.getEnd();
        		if (startingNode.equals(anEdgeStartingNode) && endingNode.equals(anEdgeEndingNode)) {
        			return false;
        		}//如果有了相连接的两个点，那么在这2个点之间画callEdge是不合适的
        		if (startingNode.equals(anEdgeEndingNode) && endingNode.equals(anEdgeStartingNode)) {
        			return false;
        		}
        	}
        }
        // Case 2 : classic connection between activation bars
        //情况2:典型的在activation bars之间的连接
        if (startingNodeClass.isAssignableFrom(ActivationBarNode.class)
                && endingNodeClass.isAssignableFrom(ActivationBarNode.class))
        {
        	INode endNode = edge.getEnd();
            INode startNode = edge.getStart();
			
			INode endnode = endNode;
			while(!endNode.getParent().getClass().getSimpleName().equals("LifelineNode"))
			{
				endNode = endNode.getParent();
			}
			
			INode startnode = startNode;
			while (!startnode.getParent().getClass().getSimpleName().equals("LifelineNode")) {
				startnode = startnode.getParent();
			}
			if(startnode.getParent().getID().equals(endnode.getParent().getID()))
			{
				return false;
			}
            return true;
        }
        // Case 3 : an activation bar creates a new class instance
        //情况3:一个activation创建一个新的类的实例
        if (startingNodeClass.isAssignableFrom(ActivationBarNode.class) && endingNodeClass.isAssignableFrom(LifelineNode.class))
        {
            ActivationBarNode startingActivationBarNode = (ActivationBarNode) startingNode;
            LifelineNode startingLifeLineNode = startingActivationBarNode.getImplicitParameter();
            LifelineNode endingLifeLineNode = (LifelineNode) endingNode;
            Rectangle2D topRectangle = endingLifeLineNode.getTopRectangle();
            if (startingLifeLineNode != endingLifeLineNode && topRectangle.contains(endingNodePoint))
            {
                return true;
            }
        }
        // Case 4 : classic connection between activation bars but the ending
        // bar doesn't exist and need to be automatically created
        //情况4：自动创建ending activation bar
        if (startingNodeClass.isAssignableFrom(ActivationBarNode.class) && endingNodeClass.isAssignableFrom(LifelineNode.class))
        {
            ActivationBarNode startingActivationBarNode = (ActivationBarNode) startingNode;
            LifelineNode startingLifeLineNode = startingActivationBarNode.getImplicitParameter();
            LifelineNode endingLifeLineNode = (LifelineNode) endingNode;
            Rectangle2D topRectangle = endingLifeLineNode.getTopRectangle();
            if (startingLifeLineNode != endingLifeLineNode && !topRectangle.contains(endingNodePoint))
            {
                ActivationBarNode newActivationBar = new ActivationBarNode();
                Point2D edgeStartLocation = edge.getEndLocation();
                double x = edgeStartLocation.getX();
                double y = edgeStartLocation.getY();
                Point2D newActivationBarLocation = new Point2D.Double(x, y);
                endingNode.addChild(newActivationBar, newActivationBarLocation);
                edge.setEnd(newActivationBar);
                return true;
            }
        }
        // Case 5 : self call on an activation bar
        //情况5：自回调边
        if (startingNodeClass.isAssignableFrom(ActivationBarNode.class) && endingNodeClass.isAssignableFrom(LifelineNode.class))
        {
            ActivationBarNode startingActivationBarNode = (ActivationBarNode) startingNode;
            LifelineNode startingLifeLineNode = startingActivationBarNode.getImplicitParameter();
            LifelineNode endingLifeLineNode = (LifelineNode) endingNode;
            Rectangle2D topRectangle = endingLifeLineNode.getTopRectangle();
            if (startingLifeLineNode == endingLifeLineNode && !topRectangle.contains(endingNodePoint))
            {
                ActivationBarNode newActivationBar = new ActivationBarNode();
                Point2D edgeStartLocation = edge.getStartLocation();
                double x = edgeStartLocation.getX();
                double y = edgeStartLocation.getY() + CALL_YGAP / 2  - 80;
                Point2D newActivationBarLocation = new Point2D.Double(x, y);
                startingNode.addChild(newActivationBar, newActivationBarLocation);
                edge.setEnd(newActivationBar);
                return true;
            }
        }
        // Case 6 : self call on an activation bar but its child which is called
        // doesn"t exist and need to be created automatically
        if (startingNodeClass.isAssignableFrom(ActivationBarNode.class) && endingNodeClass.isAssignableFrom(NullType.class))
        {
        	 ActivationBarNode newActivationBar = new ActivationBarNode();
             Point2D edgeStartLocation = edge.getStartLocation();
             double x = edgeStartLocation.getX();
             double y = edgeStartLocation.getY() - 80;
             Point2D newActivationBarLocation = new Point2D.Double(x, y);
             startingNode.addChild(newActivationBar, newActivationBarLocation);
             edge.setEnd(newActivationBar);
             return true;
//          ActivationBarNode newActivationBar = new ActivationBarNode();
//          edge.getStartLocation();
//          startingNode.addChild(newActivationBar, edge.getStartLocation());
//          edge.setEnd(newActivationBar);
        }
        return false;
    }

    /**
     * Finds an edge in the graph connected to start and end nodes
     * 
     * @param g the graph
     * @param start the start node
     * @param end the end node
     * @return the edge or null if no one is found
     */
    private IEdge findEdge(INode start, INode end)//通过起始节点和终止节点查找边
    {
        for (IEdge e : getGraph().getAllEdges())
        {
            if (e.getStart() == start && e.getEnd() == end) return e;
        }
        return null;
    }

    /**
     * @return x location relative to the parent
     */
    private double getHorizontalLocation()//获取相对于父节点的X方向的位置
    {                           
        INode parentNode = getParent();//获取父节点
        if (parentNode != null && parentNode.getClass().isAssignableFrom(ActivationBarNode.class))
        {//如果有父节点且父节点是activationBar
            return DEFAULT_WIDTH / 2;
        }//则水平方向相对于父节点平移一段距离
        if (parentNode != null && parentNode.getClass().isAssignableFrom(LifelineNode.class))
        {//如果父节点是生命线类型
            LifelineNode lifeLineNode = (LifelineNode) parentNode;
            Rectangle2D lifeLineTopRectangle = lifeLineNode.getTopRectangle();
            return lifeLineTopRectangle.getWidth() / 2 - DEFAULT_WIDTH / 2;
        }
        return 0;
    }
    /** The lifeline that embeds this activation bar in the sequence diagram */
    private transient LifelineNode lifeline;
    
    private transient Point2D locationCache;
    
    /** Default with */
    private static int DEFAULT_WIDTH = 16;

    /** Default height */
    private static int DEFAULT_HEIGHT = 40;

    /** Default vertical gap between two call nodes and a call node and an implicit node */
    public static int CALL_YGAP = 20;

	
}
