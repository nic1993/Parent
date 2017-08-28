package com.horstmann.violet.application.gui.util.tanchao.markovlayout;

import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;

public class UnionEdge {
   private DefaultEdge edge;
   private DefaultGraphCell sourceNode;
   private DefaultGraphCell targetNode;
public DefaultEdge getEdge() {
	return edge;
}
public void setEdge(DefaultEdge edge) {
	this.edge = edge;
}
public DefaultGraphCell getSourceNode() {
	return sourceNode;
}
public void setSourceNode(DefaultGraphCell sourceNode) {
	this.sourceNode = sourceNode;
}
public DefaultGraphCell getTargetNode() {
	return targetNode;
}
public void setTargetNode(DefaultGraphCell targetNode) {
	this.targetNode = targetNode;
}
   
}
