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

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.horstmann.violet.product.diagram.abstracts.AbstractGraph;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.edge.ISequenceTimeEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.common.NoteEdge;
import com.horstmann.violet.product.diagram.common.NoteNode;

/**
 * A UML sequence diagram.
 */
public class SequenceDiagramGraph extends AbstractGraph
{

    
    
    
    @Override
    public boolean addNode(INode newNode, Point2D p)//添加节点
    {
    	INode foundNode = findNode(p);//通过给定的点p来获取节点
        if (foundNode == null && newNode.getClass().isAssignableFrom(ActivationBarNode.class)) {
            return false;//如果没找到点并且新的节点是activationBar节点类型
        }
        return super.addNode(newNode, p);
    }
   
    
    public List<INode> getNodePrototypes()
    {
        return NODE_PROTOTYPES;
    }

    public List<IEdge> getEdgePrototypes()
    {
        return EDGE_PROTOTYPES;
    }

    private static final List<INode> NODE_PROTOTYPES = new ArrayList<INode>();

    private static final List<IEdge> EDGE_PROTOTYPES = new ArrayList<IEdge>();
    
  

    static
    {
        ResourceBundle rs = ResourceBundle.getBundle(SequenceDiagramConstant.SEQUENCE_DIAGRAM_STRINGS, Locale.getDefault());
        
        LifelineNode lifelineNode = new LifelineNode();//新创建一条生命线
        lifelineNode.setToolTip(rs.getString("node0.tooltip"));//Object lifeline
        NODE_PROTOTYPES.add(lifelineNode);
        
        ActivationBarNode activationBarNode = new ActivationBarNode();
        activationBarNode.setToolTip(rs.getString("node1.tooltip"));//Activation bar
        NODE_PROTOTYPES.add(activationBarNode);
        
        NoteNode noteNode = new NoteNode();
        noteNode.setToolTip(rs.getString("node2.tooltip"));
        NODE_PROTOTYPES.add(noteNode);
    
              
        CombinedFragment timeNode = new CombinedFragment();
        timeNode.setToolTip(rs.getString("node3.tooltip"));
        NODE_PROTOTYPES.add(timeNode);
        
        RefNode refNode = new RefNode();
        refNode.setToolTip(rs.getString("node4.tooltip"));
        NODE_PROTOTYPES.add(refNode);
        
        
        CallEdge callEdge = new CallEdge();
        callEdge.setToolTip(rs.getString("edge0.tooltip"));
        EDGE_PROTOTYPES.add(callEdge);
        
        ReturnEdge returnEdge = new ReturnEdge();
        returnEdge.setToolTip(rs.getString("edge1.tooltip"));
        EDGE_PROTOTYPES.add(returnEdge);
        
        NoteEdge noteEdge = new NoteEdge();
        noteEdge.setToolTip(rs.getString("edge2.tooltip"));
        EDGE_PROTOTYPES.add(noteEdge);
        
//        TimeEdge timeEdge= new TimeEdge();      
//        timeEdge.setToolTip(rs.getString("edge3.tooltip"));
//        EDGE_PROTOTYPES.add(timeEdge);
    }

}
