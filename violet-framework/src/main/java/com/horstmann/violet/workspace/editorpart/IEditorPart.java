package com.horstmann.violet.workspace.editorpart;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JComponent;

import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.workspace.IWorkspace;

/**
 * Defines the editor behaviour (an editor is something embedding an IGraph)
 * 
 * @author Alexandre de Pellegrin
 * 
 */
public interface IEditorPart
{

    /**
     * Returns the graph handled by the editor
     */
    public abstract IGraph getGraph();

    /**
     * Removes the selected nodes or edges.
     */
    public abstract void removeSelected();


    /**
     * @return currently selected nodes
     */
    public abstract List<INode> getSelectedNodes();
    
    /**
     * Clears nodes and edges selection 
     */
    public void clearSelection();
    
    /**
     * Selects a node
     * @param node
     */
    public void selectElement(INode node);

    /**
     * Changes the zoom of this editor. The zoom is 1 by default and is multiplied by sqrt(2) for each positive stem or divided by
     * sqrt(2) for each negative step.
     * 
     * @param steps the number of steps by which to change the zoom. A positive value zooms in, a negative value zooms out.
     */
    public abstract void changeZoom(int steps,IWorkspace workspace);
    
    /**
     * @return current zoom factor
     */
    public double getZoomFactor();
    
    /**
     * @return the grid used to keep elements aligned
     */
    public IGrid getGrid();
    
    /**
     * Grows drawing area
     */
    public void growDrawingArea();
    
    /**
     * Clips drawing area
     */
    public void clipDrawingArea();

    /**
     * @return the awt object displaying this editor part
     */
    public JComponent getSwingComponent();
    
    /**
     * @return object that manages nodes and edges selection
     */
    public IEditorPartSelectionHandler getSelectionHandler();
    
    /**
     * @return manager used to declare new editor behaviors and how to send events between behaviors
     */
    public IEditorPartBehaviorManager getBehaviorManager();

}