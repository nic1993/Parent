package com.horstmann.violet.application.Stepone;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.horstmann.violet.workspace.IWorkspace;

public class StepOneCenterUseCaseTabbedPane extends JTabbedPane{
//	private JPanel sequenceDiagramTabbedPane;
//	private JPanel timingDiagramTabbedPane;
//	private JPanel markovDiagramTabbedPane;
	private List<JPanel> usecaseDiagramTabbedPane;
	/*
	 * 添加4个不同类型图的TabbedPane
	 */
	public StepOneCenterUseCaseTabbedPane()
	{
////		sequenceDiagramTabbedPane=new JPanel();
////		timingDiagramTabbedPane=new JPanel();
////		markovDiagramTabbedPane=new JPanel();
		usecaseDiagramTabbedPane = new ArrayList<JPanel>();
////		sequenceDiagramTabbedPane.setBorder(BorderFactory.createLineBorder(Color.white));
////		sequenceDiagramTabbedPane.setBackground(Color.white);
//		
////		timingDiagramTabbedPane.setBorder(BorderFactory.createLineBorder(Color.white));
////		timingDiagramTabbedPane.setBackground(Color.white);
////		
////		markovDiagramTabbedPane.setBorder(BorderFactory.createLineBorder(Color.white));
////		markovDiagramTabbedPane.setBackground(Color.white);
//		
////		sequenceDiagramTabbedPane.setLayout(new GridBagLayout());
////		timingDiagramTabbedPane.setLayout(new GridBagLayout());
////		markovDiagramTabbedPane.setLayout(new GridBagLayout());
////		usecaseDiagramTabbedPane.setLayout(new GridBagLayout());
//		    this.add("顺序图",sequenceDiagramTabbedPane);
////		this.add("时序图",timingDiagramTabbedPane);
////		this.add("Markov",markovDiagramTabbedPane);

	}
	/*
	 * 通过传递Iworkspace参数来确定是在哪个TabbedPane下面添加图形
	 */
    public JPanel getUMLTabbedPane(IWorkspace workspace, int size)
    {
    	//这里分两种情况
    	//1、新建:判断是不是类似于N.XXX类型
    	//2、导入:判断文件名后缀是不是.XXX.violet.xml
//       if(workspace.getTitle().toString().endsWith(".seq.violet.xml")
//    		   ||workspace.getTitle().toString().substring(2,4).equals("Se"))
//    	{
//    		return this.getSequenceDiagramTabbedPane();
//        }
    	if(workspace.getTitle().toString().endsWith(".ucase.violet.xml")
    			||workspace.getTitle().toString().substring(2,4).equals("Us"))
		{
	        return this.getUsecaseDiagramTabbedPane().get(size);
		}
//    	if(workspace.getTitle().toString().endsWith(".timing.violet.xml")
//    ||workspace.getTitle().toString().substring(2,4).equals("Ti"))
//		{
//	        return this.getTimingDiagramTabbedPane();
//		}
//    	if(workspace.getTitle().toString().endsWith(".markov.violet.xml")
//    			||workspace.getTitle().toString().substring(2,4).equals("Ma"))
//		{
//	        return this.getMarkovDiagramTabbedPane();
//		}
    	    return null;
    }
//	public JPanel getSequenceDiagramTabbedPane() {
//		return sequenceDiagramTabbedPane;
//	}
//	public void setSequenceDiagramTabbedPane(JPanel sequenceDiagramTabbedPane) {
//		this.sequenceDiagramTabbedPane = sequenceDiagramTabbedPane;
//	}
//	public JPanel getTimingDiagramTabbedPane() {
//		return timingDiagramTabbedPane;
//	}
//	public JPanel getMarkovDiagramTabbedPane() {
//		return markovDiagramTabbedPane;
//	}	
	public List<JPanel> getUsecaseDiagramTabbedPane() {
		return usecaseDiagramTabbedPane;
	}
	
}
