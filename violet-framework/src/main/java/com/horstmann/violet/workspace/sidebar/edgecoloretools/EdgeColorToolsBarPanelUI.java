package com.horstmann.violet.workspace.sidebar.edgecoloretools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.PanelUI;

import com.horstmann.violet.framework.theme.ThemeManager;

public class EdgeColorToolsBarPanelUI extends PanelUI{
	
	
     public EdgeColorToolsBarPanelUI(EdgeColorToolsBarPanel edgeColorToolsBarPanel){
    	 this.edgeColorToolsBarPanel=edgeColorToolsBarPanel;
     }
     
     public void installUI(JComponent c){
         c.removeAll();
         this.edgeColorToolsBarPanel.setBackground(ThemeManager.getInstance().getTheme().getSidebarElementBackgroundColor());
         this.edgeColorToolsBarPanel.add(getPanel());
     }
     
     private JPanel getPanel(){
    	 if(this.panel==null){
    		 this.panel=new JPanel();
    		 this.panel.setOpaque(false);
    		 this.panel.setBorder(new EmptyBorder(0,0,0,0));
    		 this.panel.setPreferredSize(new Dimension(215,100));
    		 FlowLayout layout=new FlowLayout(FlowLayout.LEFT,10,10);
    		 this.panel.setLayout(layout);
    		 for(EdgeColorTool aEdgeColorTool:getEdgeColorToolList()){
                        this.panel.add(aEdgeColorTool);
    		 }
    	 }
    	 return this.panel;
     }
     
     private List<EdgeColorTool> getEdgeColorToolList(){
    	 if(this.edgeColorToolList==null){
    		 this.edgeColorToolList=new ArrayList<EdgeColorTool>();
    		 for(EdgeColorChoice aChoice:EdgeColorToolsBarPanel.EDGE_CHOICE_LIST){
    			 EdgeColorTool aColorTool=getEdgeColorTool(aChoice);
    			 this.edgeColorToolList.add(aColorTool);
    		 }
    		 
    	 }
    	 return edgeColorToolList;
     }
     
     private EdgeColorTool getEdgeColorTool(final EdgeColorChoice edgeColorChoice){
    	 final EdgeColorTool aEdgeColorTool=new EdgeColorTool(edgeColorChoice);
    	 aEdgeColorTool.addMouseListener(new MouseAdapter() {
    		 @Override
    		 public void mouseEntered(MouseEvent e){
    			 aEdgeColorTool.setBorderPaintable(true);
    			 aEdgeColorTool.repaint();
    		 }
    		 
    		 public void mouseExited(MouseEvent e){
    			 if(!aEdgeColorTool.equals(currentEdgeTool)){
    				 aEdgeColorTool.setBorderPaintable(false);
    				 aEdgeColorTool.repaint();
    			 }
    		 }
    		 
    		 public void mouseClicked(MouseEvent e){
    			 edgeColorToolsBarPanel.fireEdgeColorChoiceChanged(edgeColorChoice);
    			 if(currentEdgeTool!=null){
    				 currentEdgeTool.setBorderPaintable(false);
    				 currentEdgeTool.repaint();
    			 }
    			 currentEdgeTool=aEdgeColorTool;
    			 
    		 }
		});
    	 return aEdgeColorTool;
    	 
     }
     
     protected void resetChoice(){
    	 if(currentEdgeTool!=null){
    		 currentEdgeTool.setBorderPaintable(false);
    		 currentEdgeTool.repaint();
    	 }
     }
     //内部类，定义一个EdgeColorTool
     private class EdgeColorTool extends JLabel{
    	 private boolean isBorderPaintable=false;
    	 private EdgeColorChoice edgeColorChoice;
    	 public EdgeColorTool(EdgeColorChoice edgeColorChoice){
    		 this.edgeColorChoice=edgeColorChoice;
    		 setPreferredSize(new Dimension(20,20));
    	 }
    	 @Override
    	 public void paint(Graphics g){
    		 Graphics2D g2=(Graphics2D)g;
    		 Color oldColor=g.getColor();
    		 g2.setColor(edgeColorChoice.getEdgeColor());
    		 g2.fillRect(0, 0, getWidth(), getHeight());
    		 if(this.isBorderPaintable){
    			 g.setColor(Color.BLACK);
    			 g.drawRect(0, 0, getWidth()-1, getHeight()-1);
    		 }
    		 g2.setColor(oldColor);
    	 }
    	 
    	 public void setBorderPaintable(boolean isBorderPaintable){
    		 this.isBorderPaintable=isBorderPaintable;
    	 }
     }
     
     private JPanel panel;
     private EdgeColorToolsBarPanel edgeColorToolsBarPanel;
     private List<EdgeColorTool> edgeColorToolList;
     private EdgeColorTool currentEdgeTool;
}
