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

package com.horstmann.violet.workspace.editorpart;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicBorders;

import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.edge.IHorizontalChild;
import com.horstmann.violet.product.diagram.abstracts.edge.SEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.editorpart.behavior.IEditorPartBehavior;

/**
 * Graph editor
 */
public class EditorPart extends JPanel implements IEditorPart
{

    /**
     * Default constructor
     * 
     * @param aGraph graph which will be drawn in this editor part
     */
    public EditorPart(IWorkspace workspace, IGraph aGraph)
    {
    	this.workspace=workspace;
        this.graph = aGraph;
        this.zoom = 1;
        this.grid = new PlainGrid(this);
        this.graph.setGridSticker(grid.getGridSticker());
        addMouseListener(new MouseAdapter()
        {

            public void mousePressed(MouseEvent event)
            {
                behaviorManager.fireOnMousePressed(event);
            }

            public void mouseReleased(MouseEvent event)
            {
                behaviorManager.fireOnMouseReleased(event);
            }

            public void mouseClicked(MouseEvent event)
            {
                behaviorManager.fireOnMouseClicked(event);
            }
        });

        addMouseWheelListener(new MouseWheelListener()
        {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e)
            {
                behaviorManager.fireOnMouseWheelMoved(e);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter()
        {
            public void mouseDragged(MouseEvent event)
            {
                behaviorManager.fireOnMouseDragged(event);
            }

            @Override
            public void mouseMoved(MouseEvent event)
            {
                behaviorManager.fireOnMouseMoved(event);
            }
        });
        setBounds(0, 0, 0, 0);
        setDoubleBuffered(false);
    }
    /*
     * (non-Javadoc)
     * 
     * @see com.horstmann.violet.framework.display.clipboard.IEditorPart#getGraph()
     */
    public IGraph getGraph()
    {
        return this.graph;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.horstmann.violet.framework.display.clipboard.IEditorPart#removeSelected()
     */
    public void removeSelected()
    {
        this.behaviorManager.fireBeforeRemovingSelectedElements();
        try
        {
            List<INode> selectedNodes = selectionHandler.getSelectedNodes();
            List<IEdge> selectedEdges = selectionHandler.getSelectedEdges();
            List<IHorizontalChild> selectedLines = selectionHandler.getSelectedLines();
            IEdge[] edgesArray = selectedEdges.toArray(new IEdge[selectedEdges.size()]);
            INode[] nodesArray = selectedNodes.toArray(new INode[selectedNodes.size()]);
            IHorizontalChild[] linesArrey = selectedLines.toArray(new IHorizontalChild[selectedLines.size()]);
            graph.removeNode(nodesArray);
            graph.removeEdge(edgesArray);
            graph.removeHorizontal(linesArrey);
        }
        finally
        {
            this.selectionHandler.clearSelection();
            this.behaviorManager.fireAfterRemovingSelectedElements();
        }
    }

    public List<INode> getSelectedNodes()
    {
        return selectionHandler.getSelectedNodes();
    }

    public void clearSelection()
    {
        selectionHandler.clearSelection();
    }

    public void selectElement(INode node)
    {
        selectionHandler.addSelectedElement(node);
    }
/*
 * 重写该方法,配置合适的大小，这里的方法实际上在每次paint时，会使editpart最大化
 */
    @Override
    public Dimension getPreferredSize()
    {
        Dimension parentSize = getParent().getSize();
        Rectangle2D bounds = graph.getClipBounds();
        
		if (graphstart == 0) {
			int zx, zy, pw, ph;
			zx = (int) (zoom * bounds.getMaxX());
			zy = (int) (zoom * bounds.getMaxY());
			pw = (int) parentSize.getWidth();
			ph = (int) parentSize.getHeight();

			if (pw != 0 && ph != 0) {
				while (zx > pw || zy > ph) {
//					if(ALL_STEP<-MAX_STEP){
//						break;
//					}
					changeZoom(-1, workspace);
					graphstart = 0;
					zx = (int) (zoom * bounds.getMaxX());
					zy = (int) (zoom * bounds.getMaxY());
				}
			}
        }
        
        int width = Math.max((int) (zoom * bounds.getMaxX()), (int) parentSize.getWidth());
        int height = Math.max((int) (zoom * bounds.getMaxY()), (int) parentSize.getHeight());
        
        return new Dimension(width, height);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.horstmann.violet.framework.display.clipboard.IEditorPart#changeZoom(int)
     */
    public void changeZoom(int steps, IWorkspace workspace)
    {
    	
    	JScrollPane scrollableEditorPart = workspace.getAWTComponent().getScrollableEditorPart();
		JScrollBar hbar = scrollableEditorPart.getVerticalScrollBar();
		JScrollBar vbar = scrollableEditorPart.getHorizontalScrollBar();
		oldhbarmax=hbar.getMaximum();
		oldvbarmax=vbar.getMaximum();
		oldhbarvalue=hbar.getValue();
		oldvbarvalue=vbar.getValue();
		zoomstate=true;
    	
    	ALL_STEP+=steps;

    	graphstart=1;
        final double FACTOR = Math.sqrt(Math.sqrt(2));
        for (int i = 1; i <= steps; i++)
            zoom *= FACTOR;
        for (int i = 1; i <= -steps; i++)
            zoom /= FACTOR;
        invalidate();
        repaint();
        
    }

    @Override
    public double getZoomFactor()
    {
        return this.zoom;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.horstmann.violet.product.workspace.editorpart.IEditorPart#getGrid()
     */
    public IGrid getGrid()
    {
        return this.grid;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.horstmann.violet.framework.workspace.editorpart.IEditorPart# growDrawingArea()
     */
    public void growDrawingArea()
    {
        IGraph g = getGraph();
        Rectangle2D bounds = g.getClipBounds();
        bounds.add(getBounds());
        g.setBounds(new Double(0, 0, GROW_SCALE_FACTOR * bounds.getWidth(), GROW_SCALE_FACTOR * bounds.getHeight()));
        invalidate();
        repaint();
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.horstmann.violet.framework.workspace.editorpart.IEditorPart# clipDrawingArea()
     */
    public void clipDrawingArea()
    {
        IGraph g = getGraph();
        g.setBounds(null);
        invalidate();
        repaint();
    }

    public JComponent getSwingComponent()
    {
    	lock.push(1);
        return this;
    }
    
    
    @Override
    public void paintImmediately(int x, int y, int w, int h)
    {
        getSwingComponent().invalidate();
        super.paintImmediately(x, y, w, h);
    }
    
    
    @Override
    protected void paintComponent(Graphics g)
    {
		
        boolean valid = getSwingComponent().isValid();
        if (valid)
        {
            return;
        }
        super.paintComponent(g);
        getSwingComponent().revalidate(); // to inform parent scrollpane container
        Graphics2D g2 = (Graphics2D) g;
        g2.scale(zoom, zoom);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (grid.isVisible()) grid.paint(g2);
        graph.draw(g2);
        for (IEditorPartBehavior behavior : this.behaviorManager.getBehaviors())
        {
            behavior.onPaint(g2);
        }
        
        if(zoomstate){
        	
        	JScrollPane scrollableEditorPart = workspace.getAWTComponent().getScrollableEditorPart();
    		JScrollBar hbar = scrollableEditorPart.getVerticalScrollBar();
    		JScrollBar vbar = scrollableEditorPart.getHorizontalScrollBar();
    		
    		if(hbar.getMaximum()==oldhbarmax&&vbar.getMaximum()==oldvbarmax){
    			
    		}
    		else{
    			newhbarmax=hbar.getMaximum();
    			newvbarmax=vbar.getMaximum();
    			newhbarvalue=hbar.getValue();
    			newvbarvalue=vbar.getValue();
    			

    			
    			int x = newhbarvalue,y = newvbarvalue;
    			
    			if(newhbarmax>oldhbarmax){
    				x=newhbarvalue+(newhbarmax-oldhbarmax)/2;
    			}
    			if(newvbarmax>oldvbarmax){
    				y=newvbarvalue+(newvbarmax-oldvbarmax)/2;
    			}
    			if(newhbarmax<oldhbarmax&&oldhbarvalue==newhbarvalue){
    				x=newhbarvalue-(oldhbarmax-newhbarmax)/2;
    			}
    			if(newvbarmax<oldvbarmax&&oldvbarvalue==newvbarvalue){
    				y=newvbarvalue-(oldvbarmax-newvbarmax)/2;
    			}
    			
    			hbar.setValue(x);
    			vbar.setValue(y);
    			
    			zoomstate=false;
    		}
    		
        }  
    }
    
    
    
    
    @Override
    public IEditorPartSelectionHandler getSelectionHandler()
    {
        return this.selectionHandler;
    }

    @Override
    public IEditorPartBehaviorManager getBehaviorManager()
    {
        return this.behaviorManager;
    }
    
    private IWorkspace workspace;

    private IGraph graph;

    private IGrid grid;

    private double zoom;
    
    private int graphstart=0;

    private IEditorPartSelectionHandler selectionHandler = new EditorPartSelectionHandler();

    /**
     * Scale factor used to grow drawing area
     */
     private static final double GROW_SCALE_FACTOR = Math.sqrt(2);

    private IEditorPartBehaviorManager behaviorManager = new EditorPartBehaviorManager();
    
    private static final int MAX_STEP=5;
    private int ALL_STEP=0;
    
    private boolean zoomstate=false;
    
    private int oldhbarmax;
    private int oldvbarmax;
    private int oldhbarvalue;
    private int oldvbarvalue;
    
    private int newhbarmax;
    private int newvbarmax;
    private int newhbarvalue;
    private int newvbarvalue;
    
    public static BlockingDeque<Integer> lock = new LinkedBlockingDeque<Integer>();
    
}