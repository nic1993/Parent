package com.horstmann.violet.workspace.sidebar.edgecoloretools;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.horstmann.violet.framework.injection.resources.ResourceBundleInjector;
import com.horstmann.violet.framework.injection.resources.annotation.ResourceBundleBean;
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.sidebar.SideBar;
import com.horstmann.violet.workspace.sidebar.colortools.ColorToolsBarPanel;
import com.horstmann.violet.workspace.sidebar.colortools.ColorToolsBarPanelUI;

@ResourceBundleBean(resourceReference=SideBar.class)
public class EdgeColorToolsBarPanel extends JPanel implements IEdgeColorChoiceBar {
    
	public EdgeColorToolsBarPanel() {
              ResourceBundleInjector.getInjector().inject(this);
              this.ui=new EdgeColorToolsBarPanelUI(this);
              setUI(this.ui);
              setCursor(IEdgeColorChoiceBar.CUTSOM_SURSOR);
	 }
	@Override
	public void install(IWorkspace workspace) {
           this.diagramPanel=workspace;		
	}

	@Override
	public Component getAWTComponent() {
		return this;
	}

	@Override
	public void addEdgeColorChoiceChangeListener(IEdgeColorChoiceChangeListener listener) {
        this.edgeColorChoiceChangeListenerList.add(listener);		
	}

	@Override
	public void resetSelection() {
        this.ui.resetChoice	();	
	}
   
	public void fireEdgeColorChoiceChanged(EdgeColorChoice newEdgeColorChoice){
		for(IEdgeColorChoiceChangeListener aListener:this.edgeColorChoiceChangeListenerList){
			aListener.onEdgeColorChoiceChange(newEdgeColorChoice);
		}
	}
	
	protected static final List<EdgeColorChoice> EDGE_CHOICE_LIST=new ArrayList<EdgeColorChoice>();
	private List<IEdgeColorChoiceChangeListener> edgeColorChoiceChangeListenerList=new ArrayList<IEdgeColorChoiceChangeListener>();
	private EdgeColorToolsBarPanelUI ui;
	private IWorkspace diagramPanel;
	
	public static final EdgeColorChoice DEFAULT_COLOR=new EdgeColorChoice(Color.black);
	public static final EdgeColorChoice PASTEL_RED=new EdgeColorChoice(Color.RED);
	public static final EdgeColorChoice PASTEL_GREEN =new EdgeColorChoice(Color.green);
	public static final EdgeColorChoice PASTEL_BLUE=new EdgeColorChoice(Color.BLUE);
	public static final EdgeColorChoice PASTEL_CYAN=new EdgeColorChoice(Color.CYAN);
	
	static {
		EDGE_CHOICE_LIST.add(DEFAULT_COLOR);
		EDGE_CHOICE_LIST.add(PASTEL_RED);
		EDGE_CHOICE_LIST.add(PASTEL_GREEN);
		EDGE_CHOICE_LIST.add(PASTEL_BLUE);
		EDGE_CHOICE_LIST.add(PASTEL_CYAN);
	}
}
