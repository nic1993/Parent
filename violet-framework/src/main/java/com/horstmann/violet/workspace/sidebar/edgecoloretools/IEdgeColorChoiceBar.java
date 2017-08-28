package com.horstmann.violet.workspace.sidebar.edgecoloretools;

import java.awt.Cursor;

import com.horstmann.violet.workspace.sidebar.ISideBarElement;

public interface IEdgeColorChoiceBar extends ISideBarElement{
	public static final Cursor CUTSOM_SURSOR=new Cursor(Cursor.CROSSHAIR_CURSOR);
	public void addEdgeColorChoiceChangeListener(IEdgeColorChoiceChangeListener listener);
    public void resetSelection();
}
