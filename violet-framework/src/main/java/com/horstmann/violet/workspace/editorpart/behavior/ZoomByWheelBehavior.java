package com.horstmann.violet.workspace.editorpart.behavior;

import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.editorpart.IEditorPart;

public class ZoomByWheelBehavior extends AbstractEditorPartBehavior
{

    private IEditorPart editorPart;
    private IWorkspace workspace;

    public ZoomByWheelBehavior(IEditorPart editorPart,IWorkspace workspace)
    {
        this.editorPart = editorPart;
        this.workspace = workspace;
    }

    @Override
    public void onMouseWheelMoved(MouseWheelEvent event)
    {
    	
        boolean isCtrl = (event.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0;
        if (!isCtrl)
        {
            return;
        }
        int scroll = event.getUnitsToScroll();
        if (scroll < 0)
        {
            this.editorPart.changeZoom(1,workspace);
        }
        if (scroll > 0)
        {
            this.editorPart.changeZoom(-1,workspace);
        }
        
//        JScrollPane scrollableEditorPart = workspace.getAWTComponent().getScrollableEditorPart();
//        JScrollBar hbar=scrollableEditorPart.getVerticalScrollBar();
//		JScrollBar vbar=scrollableEditorPart.getHorizontalScrollBar();
//		
//		if(hbar != null)
//		{
//			hbar.setValue(hbar.getMaximum() / 2);  
//		}
//		 if (null != vbar) {  
//			 vbar.setValue(vbar.getMaximum() / 2);  
//	        }  
    }

}
