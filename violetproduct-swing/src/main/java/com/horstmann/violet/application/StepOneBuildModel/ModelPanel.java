package com.horstmann.violet.application.StepOneBuildModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import org.apache.commons.collections.functors.IfClosure;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.menu.FileMenu;
import com.horstmann.violet.workspace.IWorkspace;

public class ModelPanel extends JPanel{
      private SequenceTreePanel sequenceTreePanel;
      private UsecaseTreePanel usecaseTreePanel;
      private JPanel TreePanel;
      private JPanel gappanel;
      private JLabel title;
      
      private String temporaryUcaseFile;
      private String temporarySeqFile;
      private List<IWorkspace> UseCaseworkspaceList; //用例图    
      private List<IWorkspace> SequencespaceList;//顺序图
      
      private MainFrame mainFrame;
      private FileMenu fileMenu;
      
      private JPopupMenu popupMenu;
      private JMenuItem deleteDiagram;
      private JMenuItem saveDiagram;
      private JMenuItem changeName;
      
      private boolean isSave = false;
      
      private int index;
	  public ModelPanel(MainFrame mainFrame,FileMenu fileMenu) {
		this.mainFrame = mainFrame;
		this.fileMenu = fileMenu;
		init();
	  }
      private void init()
      {
    	sequenceTreePanel = new SequenceTreePanel(fileMenu, mainFrame,this);
    	usecaseTreePanel = new UsecaseTreePanel(fileMenu, mainFrame,this);
    	gappanel = new JPanel();
    	TreePanel = new JPanel();
    	Icon icon = new ImageIcon("resources/icons/16x16/smallDown.png");
    	title = new JLabel("",icon,JLabel.LEFT);
    	title.setFont(new Font("微软雅黑", Font.PLAIN, 16));
    	
    	UseCaseworkspaceList = new ArrayList<IWorkspace>(); //用例图 
    	SequencespaceList=new ArrayList<IWorkspace>();//顺序图
    	
    	TreePanel.setBackground(new Color(233, 233,233));
    	TreePanel.setLayout(new GridBagLayout());
    	TreePanel.add(usecaseTreePanel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
    	TreePanel.add(sequenceTreePanel,new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 0, 0, 0));
    	
    	this.setBackground(new Color(233, 233,233));
    	this.setLayout(new GridBagLayout());
    	this.add(title,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
    	this.add(TreePanel,new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(5, 16, 0, 0));
    	
    	listen();
      }
      
    private void listen()
    {
    	title.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseClicked(MouseEvent e) {
    			// TODO Auto-generated method stub
    			if(e.getButton() == e.BUTTON1)
    			{
    				if(TreePanel.isVisible())
        			{
        				TreePanel.setVisible(false);
        			}
        			else {
    					TreePanel.setVisible(true);
    				}
    				mainFrame.renewPanel();
    			}
    			else if (e.getButton() == e.BUTTON3) {
    				popupMenu = new JPopupMenu();
					deleteDiagram = new JMenuItem("删除     ",new ImageIcon("resources/icons/16x16/De.png"));
					saveDiagram = new JMenuItem("保存    ",new ImageIcon("resources/icons/16x16/save.png"));
					changeName = new JMenuItem("改名");
					deleteDiagram.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK));
					saveDiagram.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK));
					popupMenu.add(deleteDiagram);
					popupMenu.add(saveDiagram);
					popupMenu.add(changeName);
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
					
					deleteDiagram.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							ModelPanel selectmodel = null;
							for (ModelPanel modelPanel : mainFrame.getModelPanels()) {
								if(modelPanel.getTitle().getText().equals(title.getText()))
								{
									selectmodel = modelPanel;
								}
							}
							if(selectmodel != null)
							{
								if(!selectmodel.isSave())
								{
									int n = JOptionPane.showConfirmDialog(null, "该工程还未保存,是否删除?", "标题",JOptionPane.YES_NO_OPTION,0,new ImageIcon("icons/64x64/save.png"));
								    if(n == 0)
								    {
								    	mainFrame.removeModelPanel(selectmodel);
								    	mainFrame.getModelPanelMap().remove(selectmodel);
								    }
								}
								else {
									mainFrame.removeModelPanel(selectmodel);
									mainFrame.getModelPanelMap().remove(selectmodel);
								}								
							}		
							mainFrame.renewPanel();
						}
					});
					
					saveDiagram.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							ModelPanel selectmodel = null;
							for (ModelPanel modelPanel : mainFrame.getModelPanels()) {
								if(modelPanel.getTitle().getText().equals(title.getText()))
								{
									selectmodel = modelPanel;
								}
							}
							if(selectmodel != null)
							{
								
								if(!selectmodel.isSave()){
									mainFrame.saveModelPanel(selectmodel);
								}
								else {
									String ucasepath = selectmodel.getTemporaryUcaseFile();
							    	String seqpath = selectmodel.getTemporarySeqFile();
							    	File ucasefile = new File(ucasepath);
							    	File seqfile = new File(seqpath);
							    	if(ucasefile.exists() && seqfile.exists())
							    	{
							    		mainFrame.saveOldPlace(selectmodel);
							    	}else {
							    		mainFrame.saveModelPanel(selectmodel);
									}
								}
							}
							mainFrame.renewPanel();
						}
					});
					
                    changeName.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							Icon icon = new ImageIcon("resources/icons/22x22/open.png");
							String str = (String) JOptionPane.showInputDialog(null,"请输入名称:\n","名称",JOptionPane.PLAIN_MESSAGE,icon,null,"在这输入");
							if(str.equals(""))
							{
								JOptionPane.showMessageDialog(null, "工程名称不能为空!","标题",JOptionPane.WARNING_MESSAGE); 
							}
							else {
								title.removeAll();
								title.setText(str);
							}
							mainFrame.renewPanel();
						}
					});
				}
    			
    			
    			mainFrame.renewPanel();
    		}
		});
    }
	public SequenceTreePanel getSequenceTreePanel() {
		return sequenceTreePanel;
	}
	public UsecaseTreePanel getUsecaseTreePanel() {
		return usecaseTreePanel;
	}
	public JLabel getTitle() {
		return title;
	}
	public List<IWorkspace> getUseCaseworkspaceList() {
		return UseCaseworkspaceList;
	}
	public List<IWorkspace> getSequencespaceList() {
		return SequencespaceList;
	}
	public boolean isSave() {
		return isSave;
	}
	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}
	public String getTemporaryUcaseFile() {
		return temporaryUcaseFile;
	}
	public void setTemporaryUcaseFile(String temporaryUcaseFile) {
		this.temporaryUcaseFile = temporaryUcaseFile;
	}
	public String getTemporarySeqFile() {
		return temporarySeqFile;
	}
	public void setTemporarySeqFile(String temporarySeqFile) {
		this.temporarySeqFile = temporarySeqFile;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}      
     
}
