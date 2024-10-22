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

package com.horstmann.violet.product.diagram.abstracts.edge;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.GenericArrayType;

import javax.swing.plaf.SeparatorUI;

import com.horstmann.violet.product.diagram.abstracts.Direction;
import com.horstmann.violet.product.diagram.abstracts.IEdgeColorable;
import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.IGridSticker;
import com.horstmann.violet.product.diagram.abstracts.Id;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * A class that supplies convenience implementations for a number of methods in the Edge interface
 */
public abstract class AbstractEdge implements IEdge
{
    public AbstractEdge()
    {
        // Nothing to do
    } 
    @Override
    public void setStartEdge(IEdge edge) {
    	// TODO Auto-generated method stub
    	this.startEdge=edge;
    }
    @Override
    public IEdge getStartEdge() {
    	return startEdge;
    }
    @Override
    public void setEndEdge(IEdge edge) {
    	// TODO Auto-generated method stub
    	this.endEdge=edge;
    }
    @Override
    public IEdge getEndEdge() {
    	// TODO Auto-generated method stub
    	return this.endEdge;
    }
	@Override
    public void setStart(INode startingNode)
    {
        this.start = startingNode;
    }
    @Override
    public Rectangle2D getLabelBounds() 
    {
	return null;
	}
    @Override
    public void setLabelLocation(Point2D pLocation) {};
    @Override
    public void setLabelBounds(Rectangle2D rectangle) {};
    @Override
    public INode getStart()
    {
    
        return start;
    }
    @Override
    public Point2D getLabelLocation() {
    	// TODO Auto-generated method stub
    	return null;
    }

    @Override
    public void setEnd(INode endingNode)
    {
        this.end = endingNode;
    }
   
    


	
	@Override
	public void setBelongtoStartFlag(int index) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getBelongtoStartFlag() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setBelongtoEndFlag(int index) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getBelongtoEndFlag() {
		// TODO Auto-generated method stub
		return 0;
	}	
	@Override
    public INode getEnd()
    {
        return end;
    }

    @Override
    public void setStartLocation(Point2D startLocation)
    {   	
        this.startLocation = startLocation;
    }
  
    @Override
    public Point2D getStartLocation()
    {
        return startLocation;
    }

    @Override
    public void setEndLocation(Point2D endLocation)
    {
        this.endLocation = endLocation;
    }

    @Override
    public Point2D getEndLocation()
    {
        return this.endLocation;
    }
    
    @Override
    public void setTransitionPoints(Point2D[] transitionPoints)
    {
        this.transitionPoints = transitionPoints;
    }
    
    @Override
    public Point2D[] getTransitionPoints()
    {
        if (this.transitionPoints == null) {
            return new Point2D[] {};
        }
        return this.transitionPoints;
    }
    
    @Override
    public boolean isTransitionPointsSupported()
    {
        return false;
    }

    @Override
    public Rectangle2D getBounds()
    {
        Line2D conn = getConnectionPoints();
        Rectangle2D r = new Rectangle2D.Double();
        r.setFrameFromDiagonal(conn.getX1(), conn.getY1(), conn.getX2(), conn.getY2());
        //基于两个指定的 Point2D 对象设置此 Shape 窗体矩形的对角线。
        return r;
    }
    
    @Override
    public Direction getDirection(INode node) {//获取这个节点边的方向
        Rectangle2D startBounds = start.getBounds();
        Rectangle2D endBounds = end.getBounds();
        Point2D startLocationOnGraph = start.getLocationOnGraph();
        Point2D endLocationOnGraph = end.getLocationOnGraph();
        Point2D startCenter = new Point2D.Double(startLocationOnGraph.getX() + startBounds.getWidth() / 2, startLocationOnGraph.getY() + startBounds.getHeight() / 2);
        Point2D endCenter = new Point2D.Double(endLocationOnGraph.getX() + endBounds.getWidth() / 2, endLocationOnGraph.getY() + endBounds.getHeight() / 2);
        if (node.equals(start)) {//如果节点是起始节点
            if (isTransitionPointsSupported() && this.transitionPoints != null && this.transitionPoints.length > 0) {
                Point2D firstTransitionPoint = this.transitionPoints[0];
                Direction fromStart = new Direction(firstTransitionPoint, startCenter);
                return fromStart;
            }
            Direction fromStart = new Direction(endCenter, startCenter);
            return fromStart;
        }
        if (node.equals(end)) {
            if (isTransitionPointsSupported() && this.transitionPoints != null && this.transitionPoints.length > 0) {
                Point2D lastTransitionPoint = this.transitionPoints[this.transitionPoints.length - 1];
                Direction toEnd = new Direction(lastTransitionPoint, endCenter);
                return toEnd;
            }
            Direction toEnd = new Direction(startCenter, endCenter);
            return toEnd;
        }
        return null;
    }

    @Override
    public Line2D getConnectionPoints()//获取连接2个节点的线段
    {
        INode startingNode = getStart();
        INode endingNode = getEnd();
       
        Point2D startingNodeLocation = startingNode.getLocation();
        Point2D endingNodeLocation = endingNode.getLocation();
        Point2D startingNodeLocationOnGraph = startingNode.getLocationOnGraph();
        Point2D endingNodeLocationOnGraph = endingNode.getLocationOnGraph();
        Point2D relativeStartingConnectionPoint = startingNode.getConnectionPoint(this);
        Point2D relativeEndingConnectionPoint = endingNode.getConnectionPoint(this);
        Point2D absoluteStartingConnectionPoint = new Point2D.Double(startingNodeLocationOnGraph.getX() - startingNodeLocation.getX() + relativeStartingConnectionPoint.getX(), startingNodeLocationOnGraph.getY() - startingNodeLocation.getY() + relativeStartingConnectionPoint.getY());
        Point2D absoluteEndingConnectionPoint = new Point2D.Double(endingNodeLocationOnGraph.getX() - endingNodeLocation.getX() + relativeEndingConnectionPoint.getX(), endingNodeLocationOnGraph.getY() - endingNodeLocation.getY() + relativeEndingConnectionPoint.getY());
       // IGraph graph = startingNode.getGraph();
       // IGridSticker positionCorrector = graph.getGridSticker();
       // absoluteStartingConnectionPoint = positionCorrector.snap(absoluteStartingConnectionPoint);
       // absoluteEndingConnectionPoint = positionCorrector.snap(absoluteEndingConnectionPoint);
        return new Line2D.Double(absoluteStartingConnectionPoint, absoluteEndingConnectionPoint);
    }

    @Override
    public Id getId()
    {
        if (this.idE == null) {
        	this.idE = new Id();
        }
    	return this.idE;
    }

    @Override
    public void setId(Id id)
    {
        this.idE = id;
    }

    @Override
    public AbstractEdge clone()
    {
        try
        {
            AbstractEdge cloned = (AbstractEdge) super.clone();
            cloned.idE = new Id();
            return cloned;
        }
        catch (CloneNotSupportedException ex)
        {
            return null;
        }
    }

    @Override
    public Integer getRevision()
    {
        if (this.revision == null) {
        	this.revision =  new Integer(0);
        }
    	return this.revision;
    }

    @Override
    public void setRevision(Integer newRevisionNumber)
    {
        this.revision = newRevisionNumber;
    }

    public void incrementRevision()
    {
        int i = getRevision().intValue();
        i++;
        this.revision = new Integer(i);
    }

    /**
     * Sets edge tool tip
     * 
     * @param s
     */
    public void setToolTip(String s)
    {
        this.toolTip = s;
    }

    @Override
    public String getToolTip()
    {
        if (this.toolTip == null) {
        	this.toolTip = "";
        }
    	return this.toolTip;
    }
@Override
    public String getID() {
		return ID;
	}
@Override
	public void setID(String iD) {
		ID = iD;
	}

	/** The node where the edge starts */
    private INode start;

    /** The node where the edge ends */
    private INode end;
     
    private IEdge startEdge;
    
    private IEdge endEdge;
    /** The point inside the starting node where this edge begins */
    private Point2D startLocation;

    /** The point inside the ending node where this edge  ends */
    private Point2D endLocation;
    
    /** Points for free path */
    @XStreamOmitField
    private Point2D[] transitionPoints;

    /** Edge's current id (unique in all the graph) */
    private Id idE;
    private String ID;
    /** Edge's current revision */
    @XStreamOmitField
    private Integer revision;
   
    /** Edge tool tip */
    private transient String toolTip;
}
