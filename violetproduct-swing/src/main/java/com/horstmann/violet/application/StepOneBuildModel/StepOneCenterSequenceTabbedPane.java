    package com.horstmann.violet.application.StepOneBuildModel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.workspace.IWorkspace;

public class StepOneCenterSequenceTabbedPane extends JTabbedPane{
	private List<JPanel> sequenceDiagramTabbedPane;
    private MainFrame mainFrame;
	public StepOneCenterSequenceTabbedPane(MainFrame mainFrame)
	{
		sequenceDiagramTabbedPane = new ArrayList<JPanel>();
		this.mainFrame = mainFrame;
		this.addChangeListener(new MyListener());
//		listen();
	}
	/*
	 * 通过传递Iworkspace参数来确定是在哪个TabbedPane下面添加图形
	 */
    public JPanel getUMLTabbedPane(IWorkspace workspace , int size)
    {
    	//这里分两种情况
    	//1、新建:判断是不是类似于N.XXX类型
    	//2、导入:判断文件名后缀是不是.XXX.violet.xml
       if(workspace.getTitle().toString().endsWith(".seq.violet.xml")
    		   ||workspace.getTitle().toString().substring(2,4).equals("Se"))
    	{
    		return sequenceDiagramTabbedPane.get(size);
        }

    	    return null;
    }
	public List<JPanel> getSequenceDiagramTabbedPane() {
		return sequenceDiagramTabbedPane;
	}

	class MyListener implements ChangeListener
	{
		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			mainFrame.renewPanel();
		}
		
	}
	
}
