package com.horstmann.violet.application.StepOneBuildModel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.plaf.ProgressBarUI;
import javax.swing.text.html.ImageView;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.menu.FileMenu;
import com.horstmann.violet.framework.file.GraphFile;
import com.horstmann.violet.framework.file.IGraphFile;
import com.horstmann.violet.framework.file.persistence.JFileWriter;
import com.horstmann.violet.framework.file.persistence.XStreamBasedPersistenceService;
import com.horstmann.violet.framework.plugin.IDiagramPlugin;
import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.workspace.IWorkspace;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class StepOneOperationButton extends JPanel{
	private JButton openButton;
	private JButton saveButton;
	private JButton saveasButton;
	private JButton newButton; 
	private JButton closeButton;
	private JPanel rightpanel;
	private MainFrame mainFrame;
	private FileMenu fileMenu;
	private JPanel otherPanel;
	private JLabel promptLabel;
	public StepOneOperationButton(MainFrame mainFrame, FileMenu fileMenu)
	{
		this.mainFrame = mainFrame;
		this.fileMenu = fileMenu;
		this.setBackground(new Color(233,233,233));
		init();
	}
	public void init()
	{
		initButton();
	    GridBagLayout layout = new GridBagLayout();
	    this.setLayout(layout);
		this.add(newButton);
		this.add(openButton);
		this.add(saveButton);
		this.add(saveasButton);
		this.add(rightpanel);
		this.add(otherPanel);
//		this.add(closeButton);
		
		layout.setConstraints(newButton, new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(20, 15, 20, 10));
		layout.setConstraints(openButton, new GBC(1, 0, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(20,10,20,10));
		layout.setConstraints(saveButton, new GBC(2, 0, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(20,10,20,10));
		layout.setConstraints(saveasButton, new GBC(3, 0, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(23, 10, 20, 10));
//		layout.setConstraints(closeButton, new GBC(4, 0, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(20,10,20,10));
		layout.setConstraints(rightpanel, new GBC(5, 0, 1, 1).setFill(GBC.BOTH).setWeight(1, 0));
		layout.setConstraints(otherPanel, new GBC(0, 1, 6, 1).setFill(GBC.BOTH).setWeight(1, 1).setInsets(0));
		buttonListen();
	}
	public void initButton()
	{
		openButton = new JButton();
		saveButton = new JButton();
		newButton =  new JButton();
		saveasButton = new JButton();
		closeButton = new JButton();
		rightpanel = new JPanel();
		promptLabel = new JLabel("请在左侧选择需要绘制的模型类型");
		promptLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		JPanel panel = new JPanel();
		panel.setBackground(new Color(233,233,233));
		rightpanel.add(panel,new GBC(1,0,2,1).setFill(GBC.BOTH).setWeight(1, 1));
		otherPanel = new JPanel()
		{
			public void paint(Graphics g){
	            super.paint(g);
	            java.awt.Rectangle rect = this.getBounds();
	            int width = (int) rect.getWidth() - 1;
	            int height = (int) rect.getHeight() - 1;
	            Graphics2D g2 = (Graphics2D)g;
	            g2.setStroke(new BasicStroke(2f));
	            g2.setColor(new Color(188,188,188));
	            g2.drawLine(15, 0, width - 15, 0);
	          }
		};
		otherPanel.setBackground(new Color(233,233,233));
		otherPanel.setLayout(new GridBagLayout());
		otherPanel.add(promptLabel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10,14,10,10));
			
		rightpanel.setBackground(new Color(233,233,233));
	    Icon openIcon = new ImageIcon("resources/icons/22x22/open.png");
	    openButton.setIcon(openIcon);
	    
	    openButton.setHorizontalTextPosition(SwingConstants.CENTER);
	    openButton.setVerticalTextPosition(SwingConstants.BOTTOM);
	    openButton.setFocusPainted(false);
		//saveButton.setSize(icon.getIconHeight(),icon.getIconWidth());
	    openButton.setMargin(new Insets(0, 0, 0, 0));
	    openButton.setContentAreaFilled(false);	
	    openButton.setFocusable(true);
	    openButton.setToolTipText("打开");
	    openButton.setBorderPainted(false);
	    openButton.setPreferredSize(new Dimension(30, 30));
	    openButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				openButton.setBorderPainted(false);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				openButton.setBorderPainted(true);
				openButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	    

		Icon icon = new ImageIcon("resources/icons/22x22/save.png");
		saveButton.setIcon(icon);
		saveButton.setHorizontalTextPosition(SwingConstants.CENTER);
		saveButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		saveButton.setFocusPainted(false);
		saveButton.setBorderPainted(false);
		//saveButton.setSize(icon.getIconHeight(),icon.getIconWidth());
		saveButton.setMargin(new Insets(0, 0, 0, 0));
		saveButton.setContentAreaFilled(false);
		saveButton.setFocusable(true);
		saveButton.setToolTipText("保存");
		saveButton.setPreferredSize(new Dimension(30, 30));
		saveButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				saveButton.setBorderPainted(false);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub.
				saveButton.setBorderPainted(true);
				saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	
	  
		

		Icon newIcon = new ImageIcon("resources/icons/22x22/new.png");
		newButton.setIcon(newIcon);
		newButton.setHorizontalTextPosition(SwingConstants.CENTER);
		newButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		newButton.setFocusPainted(false);
		newButton.setBorderPainted(false);
		//saveButton.setSize(icon.getIconHeight(),icon.getIconWidth());
		newButton.setMargin(new Insets(0, 0, 0, 0));
		newButton.setContentAreaFilled(false);
		newButton.setFocusable(true);
		newButton.setToolTipText("新建");
		newButton.setPreferredSize(new Dimension(30, 30));
		newButton.addMouseListener(new MouseListener() {	
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				newButton.setBorderPainted(false);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				newButton.setBorderPainted(true);
				newButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		saveasButton.setToolTipText("另存");
	    Icon saveasIcon = new ImageIcon("resources/icons/22x22/save_as.png");
	    saveasButton.setIcon(saveasIcon);
	    saveasButton.setHorizontalTextPosition(SwingConstants.CENTER);
	    saveasButton.setVerticalTextPosition(SwingConstants.BOTTOM);
	    saveasButton.setFocusPainted(false);
	    saveasButton.setMargin(new Insets(0, 0, 0, 0));
	    saveasButton.setContentAreaFilled(false);
	    saveasButton.setFocusable(true);		
	    saveasButton.setBorderPainted(false);
	    saveasButton.setPreferredSize(new Dimension(30, 30));
	    saveasButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				saveasButton.setBorderPainted(false);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				saveasButton.setBorderPainted(true);
				saveasButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    Icon closeIcon = new ImageIcon("resources/icons/22x22/close.png");
	    closeButton.setIcon(closeIcon);
	    closeButton.setHorizontalTextPosition(SwingConstants.CENTER);
	    closeButton.setVerticalTextPosition(SwingConstants.BOTTOM);
	    closeButton.setFocusPainted(false);
	    closeButton.setMargin(new Insets(0, 0, 0, 0));
	    closeButton.setContentAreaFilled(false);
	    closeButton.setFocusable(true);		
	    closeButton.setPreferredSize(new Dimension(30, 30));
	    closeButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				closeButton.setBorderPainted(false);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				closeButton.setBorderPainted(true);
				closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

        
	
	}
	public void buttonListen()
	{
		openButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				fileMenu.getItem(2).doClick();
				JFileChooser jFileChooser = new JFileChooser();
				jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int i = jFileChooser.showOpenDialog(null);
                if(i== jFileChooser.APPROVE_OPTION)
                {
                	File file = jFileChooser.getSelectedFile(); 
                    String path = jFileChooser.getSelectedFile().getAbsolutePath();
                    String name = jFileChooser.getSelectedFile().getName();
                    
                    for(ModelPanel modelPanel : mainFrame.getModelPanels())
                    {
                    	if(modelPanel.getTitle().getText().toString().equals(name))
                    	{
                    		JOptionPane.showMessageDialog(null, "模型已经存在!","标题",JOptionPane.WARNING_MESSAGE);
                        	return;	
                    	}
                    }
                    
                    File ucasefile = new File(path + "/用例图"); 
                    File seqfile = new File(path + "/顺序图");
                    
                    if(!ucasefile.exists() || !seqfile.exists())
                    {
                    	JOptionPane.showMessageDialog(null, "模型不存在!","标题",JOptionPane.WARNING_MESSAGE);
                    	return;
                    }
                    
                    File[] ucasefiles =  ucasefile.listFiles();
                    File[] seqfiles =  seqfile.listFiles();
                    
                    //创建新的包
                    ModelPanel modelPanel = new ModelPanel(mainFrame, fileMenu);
					modelPanel.getTitle().setText(name);
					modelPanel.setTemporaryUcaseFile(path + "/用例图/");
					modelPanel.setTemporarySeqFile(path + "/顺序图/");
					
					JPanel treePanel = mainFrame.getsteponeButton().getTreePanel();
					int index = treePanel.getComponentCount();
					System.out.println("index: " + index);
					treePanel.add(modelPanel,new GBC(0, index).setFill(GBC.BOTH).setWeight(1, 1));
					
					mainFrame.getModelPanels().add(modelPanel);
					mainFrame.setActiveModelPanel(modelPanel);
					mainFrame.getModelPanelMap().put(modelPanel, path+"/"+modelPanel.getTitle().getText());
                   
					//打开用例图 顺序图
					try {
						fileMenu.openFile(ucasefiles);
						fileMenu.openFile(seqfiles);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					 
					mainFrame.renewPanel();
                    
                    
                }
				mainFrame.renewPanel();
			}
		});
		
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!mainFrame.getActiveModelPanel().isSave()){
					mainFrame.saveModelPanel(mainFrame.getActiveModelPanel());
				}
				else {
					mainFrame.saveOldPlace(mainFrame.getActiveModelPanel());
				}
				mainFrame.renewPanel();
			}
		});
		
		saveasButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				fileMenu.getItem(6).doClick();
				if(mainFrame.getActiveModelPanel() != null)
				{
					mainFrame.saveModelPanel(mainFrame.getActiveModelPanel());
				}
				mainFrame.renewPanel();
			}
		});
        closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				fileMenu.getItem(3).doClick();
				
				//只删除当前用例图tab页
//				if(mainFrame.getCenterTabPanel().getComponentCount() != 0)
//				{
//					StepOneCenterUseCaseTabbedPane UseCaseTabbedPane = mainFrame.getStepOneCenterUseCaseTabbedPane();
//					StepOneCenterSequenceTabbedPane SequenceTabbedPane = mainFrame.getStepOneCenterSequenceTabbedPane();
//					if(mainFrame.getCenterTabPanel().getComponent(0).equals(UseCaseTabbedPane))
//					{
//						int index = UseCaseTabbedPane.getSelectedIndex();
//						UseCaseTabbedPane.remove(index);
//						if(UseCaseTabbedPane.getTabCount() == 0)
//						{
//							mainFrame.getCenterTabPanel().removeAll();
//						}
//					}
//					else if(mainFrame.getCenterTabPanel().getComponent(0).equals(SequenceTabbedPane))
//					{
//						int index = SequenceTabbedPane.getSelectedIndex();
//						SequenceTabbedPane.remove(index);
//						if(SequenceTabbedPane.getTabCount() == 0)
//						{
//							mainFrame.getCenterTabPanel().removeAll();
//						}
//					}
//				}
				
				mainFrame.renewPanel();
			}
		});
		
		newButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub		
				boolean isExist = false;
				Icon icon = new ImageIcon("resources/icons/22x22/open.png");
				String str = (String) JOptionPane.showInputDialog(null,"请输入工程名称:\n","工程名称",JOptionPane.PLAIN_MESSAGE,icon,null,"在这输入");
				if(str != null && str != "")
				{
					for(ModelPanel modelPanel : mainFrame.getModelPanels())
					{
						if(modelPanel.getTitle().getText().equals(str))
						{
							JOptionPane.showMessageDialog(null, "该工程与已经存在的工程冲突!","标题",JOptionPane.WARNING_MESSAGE);
							isExist = true;
						}
					}
					if(isExist == false)
					{
						ModelPanel modelPanel = new ModelPanel(mainFrame, fileMenu);
						mainFrame.getCenterTabPanel().removeAll();
						modelPanel.getTitle().setText(str);
						JPanel treePanel = mainFrame.getsteponeButton().getTreePanel();
						int index = treePanel.getComponentCount();
						treePanel.add(modelPanel,new GBC(0, index).setFill(GBC.BOTH).setWeight(1, 1));
						
						mainFrame.getModelPanels().add(modelPanel);
						mainFrame.saveTemporary(modelPanel);
						mainFrame.setActiveModelPanel(modelPanel);
						
						mainFrame.renewPanel();
					}
				}
				else {
					return;
				}
				
				
			}
		});
	}	
	public JPanel getOtherPanel(){
		return otherPanel;
	}
	public JButton getOpenButton() {
		return openButton;
	}
	public JButton getSaveButton() {
		return saveButton;
	}
	public JButton getSaveasButton() {
		return saveasButton;
	}
	public JButton getNewButton() {
		return newButton;
	}
    public JLabel getPromptLabel()
    {
    	return promptLabel;
    }
}
