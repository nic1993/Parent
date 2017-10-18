package com.horstmann.violet.application.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class NodeRenderer extends DefaultTreeCellRenderer{
	public Component getTreeCellRendererComponent(JTree tree, Object value,
		     boolean selected, boolean expanded, boolean leaf, int row,
		     boolean hasFocus) {
		    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		    super.getTreeCellRendererComponent(tree, value, selected, expanded,
		      leaf, row, hasFocus);
		    
		    
		    if (!leaf) {
		    	setForeground(new Color(153, 0, 0));  
		        setTextSelectionColor(Color.BLUE);
		        setBackgroundSelectionColor(new Color(0, 117, 214));
		        setBackgroundNonSelectionColor(Color.WHITE);
		       }
		    
//		    System.out.println(node.getUserObject().toString().trim());
		    
//		    if (node.getUserObject().toString().trim() != null) {
//		     setForeground(new Color(0, 64, 70));  
////		     setTextSelectionColor(new Color(0, 117, 214));
//		     setBackgroundSelectionColor(new Color(0, 117, 214));
//		     setBackgroundNonSelectionColor(Color.WHITE);
//		    }
		    

//			     setForeground(new Color(0, 64, 70));  
////			     setTextSelectionColor(new Color(0, 117, 214));
//			     setBackgroundSelectionColor(new Color(0, 117, 214));
//			     setBackgroundNonSelectionColor(Color.WHITE);
		    return this;
		   }

}
